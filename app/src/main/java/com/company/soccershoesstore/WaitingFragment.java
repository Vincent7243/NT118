package com.company.soccershoesstore;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;

public class WaitingFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private WaitingProductAdapter mAdapter;
    private ArrayList<Product> mWaitingProducts;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_waiting, container, false);
        mRecyclerView = view.findViewById(R.id.recycler_view_waiting);

        mWaitingProducts = new ArrayList<>();
        mWaitingProducts.add(new Product("1", "Product 1", "100", "https://down-vn.img.susercontent.com/file/92ba29bd183671e6d8c70abd1faf3470", "Description 1", "Brand 1"));
        mWaitingProducts.add(new Product("2", "Product 2", "200", "https://down-vn.img.susercontent.com/file/vn-50009109-5c78d3bdc8ba0dddf624cf9bdd544bd0", "Description 2", "Brand 2"));
        mWaitingProducts.add(new Product("3", "Product 3", "300", "https://down-vn.img.susercontent.com/file/sg-11134201-22110-4wrhfphg3akv37", "Description 3", "Brand 3"));

        mAdapter = new WaitingProductAdapter(getContext(), mWaitingProducts, (OrderStatusActivity) getActivity());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }
}