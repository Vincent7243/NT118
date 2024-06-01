package com.company.soccershoesstore;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;

public class CancelledFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private ProductListAdapter mAdapter;
    private ArrayList<Product> mCancelledProducts;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cancelled, container, false);
        mRecyclerView = view.findViewById(R.id.recycler_view_cancelled);

        mCancelledProducts = new ArrayList<>();
        mAdapter = new ProductListAdapter(getContext(), mCancelledProducts); // true indicates the delete button should be hidden
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    public void updateCancelledProducts(ArrayList<Product> cancelledProducts) {
        mCancelledProducts.clear();
        mCancelledProducts.addAll(cancelledProducts);
        mAdapter.notifyDataSetChanged();
    }
}