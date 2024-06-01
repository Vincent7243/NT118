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

import java.util.ArrayList;

public class DeliveredFragment extends Fragment {

    private RecyclerView recyclerViewDelivered;
    private DeliveredProductAdapter deliveredProductAdapter;
    private ArrayList<Product> deliveredProducts;

    public DeliveredFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_delivered, container, false);

        recyclerViewDelivered = view.findViewById(R.id.recycler_view_delivered);
        deliveredProducts = new ArrayList<>();

        // Add some sample products to deliveredProducts list
        deliveredProducts.add(new Product("1", "Delivered Product 1", "100000", "https://down-vn.img.susercontent.com/file/92ba29bd183671e6d8c70abd1faf3470", "Description 1", "Brand A"));
        deliveredProducts.add(new Product("2", "Delivered Product 2", "200000", "https://down-vn.img.susercontent.com/file/vn-50009109-5c78d3bdc8ba0dddf624cf9bdd544bd0", "Description 2", "Brand B"));
        deliveredProducts.add(new Product("3", "Delivered Product 3", "300000", "https://down-vn.img.susercontent.com/file/sg-11134201-22110-4wrhfphg3akv37", "Description 3", "Brand C"));

        deliveredProductAdapter = new DeliveredProductAdapter(getContext(), deliveredProducts);
        recyclerViewDelivered.setAdapter(deliveredProductAdapter);
        recyclerViewDelivered.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }
}