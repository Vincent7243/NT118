package com.company.soccershoesstore;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;

public class ApprovedFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private ProductListAdapter mAdapter;
    private ArrayList<Product> mApprovedProducts;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_approved, container, false);
        mRecyclerView = view.findViewById(R.id.recycler_view_approved);

        // Initialize your list of approved products here
        mApprovedProducts = new ArrayList<>();
        mApprovedProducts.add(new Product("1", "Approved Product 1", "150", "https://down-vn.img.susercontent.com/file/92ba29bd183671e6d8c70abd1faf3470", "Approved Description 1", "Approved Brand 1"));
        mApprovedProducts.add(new Product("2", "Approved Product 2", "250", "https://down-vn.img.susercontent.com/file/vn-50009109-5c78d3bdc8ba0dddf624cf9bdd544bd0", "Approved Description 2", "Approved Brand 2"));
        mApprovedProducts.add(new Product("3", "Approved Product 3", "350", "https://down-vn.img.susercontent.com/file/sg-11134201-22110-4wrhfphg3akv37", "Approved Description 3", "Approved Brand 3"));

        mAdapter = new ProductListAdapter(getContext(), mApprovedProducts);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }
}