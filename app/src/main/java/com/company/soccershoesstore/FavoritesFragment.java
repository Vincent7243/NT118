package com.company.soccershoesstore;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FavoritesFragment extends Fragment implements FavoritesFragmentManager.OnFavoritesChangedListener {

    private RecyclerView recyclerView;
    private AllCategoryFragmentAdapter adapter;
    private List<AllCategoryFragmentProduct> favoriteProducts;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        recyclerView = view.findViewById(R.id.recycler_view_favorites);

        FavoritesFragmentManager.initialize(getContext());


        // Lấy danh sách các sản phẩm yêu thích từ FavoritesFragmentManager
        favoriteProducts = FavoritesFragmentManager.getFavoriteProducts();
        adapter = new AllCategoryFragmentAdapter(favoriteProducts, getContext());


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        FavoritesFragmentManager.addOnFavoritesChangedListener(this);
        // Cập nhật adapter ngay khi Fragment xuất hiện trở lại
        favoriteProducts.clear();
        favoriteProducts.addAll(FavoritesFragmentManager.getFavoriteProducts());
        adapter.notifyDataSetChanged();

//        FavoritesFragmentManager.addOnFavoritesChangedListener(this);
//        adapter.notifyDataSetChanged();
    }
    @Override
    public void onPause() {
        super.onPause();
        FavoritesFragmentManager.removeOnFavoritesChangedListener(this);
    }

    @Override
    public void onFavoritesChanged() {
        favoriteProducts.clear();
        favoriteProducts.addAll(FavoritesFragmentManager.getFavoriteProducts());
        adapter.notifyDataSetChanged();
    }
}
