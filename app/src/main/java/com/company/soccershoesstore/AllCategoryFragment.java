package com.company.soccershoesstore;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AllCategoryFragment extends Fragment implements FavoritesFragmentManager.OnFavoritesChangedListener {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private RecyclerView recyclerView;
    private AllCategoryFragmentAdapter adapter;
    private List<AllCategoryFragmentProduct> productList;
    private Button backButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_category, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        backButton = view.findViewById(R.id.back_button);

        productList = new ArrayList<>();
        adapter = new AllCategoryFragmentAdapter(productList, getContext());  // Pass context

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        // Initialize FavoritesFragmentManager
        FavoritesFragmentManager.initialize(getContext());

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().popBackStack();
            }
        });

        String brand = getArguments().getString("brand");
        fetchProductsByBrand(brand);

        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        FavoritesFragmentManager.addOnFavoritesChangedListener((FavoritesFragmentManager.OnFavoritesChangedListener) this);
    }

    @Override
    public void onPause() {
        super.onPause();
        FavoritesFragmentManager.removeOnFavoritesChangedListener((FavoritesFragmentManager.OnFavoritesChangedListener) this);
    }

    private void fetchProductsByBrand(String brand) {
        Log.d("FETCH_PRODUCTS", "Fetching products for brand: " + brand);
        db.collection("Products")
                .whereEqualTo("brand", brand)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Log.d("FETCH_PRODUCTS", "Query successful: " + task.getResult().size() + " results");
                            productList.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                AllCategoryFragmentProduct product = document.toObject(AllCategoryFragmentProduct.class);
                                productList.add(product);
                            }
                            adapter.notifyDataSetChanged();
                        } else {
                            Log.d("FETCH_PRODUCTS", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public void searchProducts(String query) {
        Log.d("SEARCH", "Searching for: " + query);
        db.collection("Products")
                .whereEqualTo("brand", getArguments().getString("brand"))
                .whereGreaterThanOrEqualTo("name", query)
                .whereLessThanOrEqualTo("name", query + '\uf8ff')
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Log.d("SEARCH", "Query successful: " + task.getResult().size() + " results");
                            productList.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                AllCategoryFragmentProduct product = document.toObject(AllCategoryFragmentProduct.class);
                                productList.add(product);
                            }
                            adapter.notifyDataSetChanged();
                        } else {
                            Log.d("SEARCH", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }


    @Override
    public void onFavoritesChanged() {
        adapter.notifyDataSetChanged();
    }
}
