//package com.company.soccershoesstore;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class FavoritesFragmentManager {
//    private static List<AllCategoryFragmentProduct> favoriteProducts = new ArrayList<>();
//
//    public static void addProductToFavorites(AllCategoryFragmentProduct product) {
//        if (!favoriteProducts.contains(product)) { // Kiểm tra tránh thêm trùng lặp
//            favoriteProducts.add(product);
//        }
//    }
//
//    public static List<AllCategoryFragmentProduct> getFavoriteProducts() {
//        return new ArrayList<>(favoriteProducts); // Trả về bản sao để tránh thay đổi từ bên ngoài
//    }
//}

package com.company.soccershoesstore;

import android.util.Log;
import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FavoritesFragmentManager {
    private static List<AllCategoryFragmentProduct> favoriteProducts = new ArrayList<>();
    private static List<OnFavoritesChangedListener> listeners = new ArrayList<>();

    public interface OnFavoritesChangedListener {
        void onFavoritesChanged();
    }
    public static void addOnFavoritesChangedListener(OnFavoritesChangedListener listener) {
        listeners.add(listener);
    }
    public static void removeOnFavoritesChangedListener(OnFavoritesChangedListener listener) {
        listeners.remove(listener);
    }

    private static void notifyFavoritesChanged() {
        for (OnFavoritesChangedListener listener : listeners) {
            listener.onFavoritesChanged();
        }
    }

    public static boolean isProductInFavorites(AllCategoryFragmentProduct product) {
        return favoriteProducts.contains(product);
    }

    public static void addProductToFavorites(AllCategoryFragmentProduct product) {
        if (!favoriteProducts.contains(product)) {
            favoriteProducts.add(product);
            Log.d("FavoritesManager", "Product added to favorites: " + product.getName());
            notifyFavoritesChanged();
        }
    }

    public static void removeProductFromFavorites(AllCategoryFragmentProduct product) {
        favoriteProducts.remove(product);
        //Log.d("FavoritesManager", "Product removed from favorites: " + product.getName());
        notifyFavoritesChanged();
    }

    public static List<AllCategoryFragmentProduct> getFavoriteProducts() {
        return new ArrayList<>(favoriteProducts);
    }
}


