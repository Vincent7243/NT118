package com.company.soccershoesstore;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FavoritesFragmentManager {

    private static final String FAVORITES_PREFS_NAME = "favorites_prefs";
    private static final String FAVORITES_KEY = "favorites";
    private static List<AllCategoryFragmentProduct> favoriteProducts = new ArrayList<>();
    private static SharedPreferences sharedPreferences;
    private static List<OnFavoritesChangedListener> listeners = new ArrayList<>();

    public static void initialize(Context context) {
        if (context != null && sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(FAVORITES_PREFS_NAME, Context.MODE_PRIVATE);
            loadFavorites();
        }
    }

    private static void loadFavorites() {
        String json = sharedPreferences.getString(FAVORITES_KEY, null);
        if (json != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<AllCategoryFragmentProduct>>() {}.getType();
            favoriteProducts = gson.fromJson(json, type);
        }
    }

    private static void saveFavorites() {
        if (sharedPreferences == null) {
            throw new IllegalStateException("FavoritesFragmentManager is not initialized, call initialize() method first.");
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(favoriteProducts);
        editor.putString(FAVORITES_KEY, json);
        editor.apply();
    }

    public static List<AllCategoryFragmentProduct> getFavoriteProducts() {
        return new ArrayList<>(favoriteProducts);
    }

    public static void addProductToFavorites(AllCategoryFragmentProduct product, Context context) {
        if (!favoriteProducts.contains(product)) {
            favoriteProducts.add(product);
            saveFavorites();
            notifyListeners();
        }
    }


    public static void removeProductFromFavorites(AllCategoryFragmentProduct product, Context context) {
        if (favoriteProducts.contains(product)) {
            favoriteProducts.remove(product);
            saveFavorites();
            notifyListeners();
        }
    }

    public static boolean isProductInFavorites(AllCategoryFragmentProduct product) {
        return favoriteProducts.contains(product);
    }

    public interface OnFavoritesChangedListener {
        void onFavoritesChanged();
    }

    public static void addOnFavoritesChangedListener(OnFavoritesChangedListener listener) {
        listeners.add(listener);
    }

    public static void removeOnFavoritesChangedListener(OnFavoritesChangedListener listener) {
        listeners.remove(listener);
    }

    private static void notifyListeners() {
        for (OnFavoritesChangedListener listener : listeners) {
            listener.onFavoritesChanged();
        }
    }
}
