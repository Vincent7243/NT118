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

import java.util.ArrayList;
import java.util.List;

public class FavoritesFragmentManager {
    private static List<AllCategoryFragmentProduct> favoriteProducts = new ArrayList<>();

    public static void addProductToFavorites(AllCategoryFragmentProduct product) {
        if (!favoriteProducts.contains(product)) {
            favoriteProducts.add(product);
            Log.d("FavoritesManager", "Product added to favorites: " + product.getName());
        }
    }

    public static List<AllCategoryFragmentProduct> getFavoriteProducts() {
        return new ArrayList<>(favoriteProducts);
    }
}

