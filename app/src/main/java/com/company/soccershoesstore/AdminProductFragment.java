package com.company.soccershoesstore;

import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;


public class AdminProductFragment extends Fragment {
    ImageButton ib_add;
    RecyclerView rv;
    ArrayList<Product> products;
    ProductListAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_admin_product, container, false);
        ib_add=view.findViewById(R.id.ib_admin_product_add);
        rv=view.findViewById(R.id.rv_admin_product);
        products=new ArrayList<>();
        products.add(new Product("test","San pham 1","","","",""));
        products.add(new Product("test","San pham 1","","","",""));
        products.add(new Product("test","San pham 1","","","",""));
        products.add(new Product("test","San pham 1","","","",""));
        products.add(new Product("test","San pham 1","","","",""));
    adapter=new ProductListAdapter(view.getContext(),products);
    rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(view.getContext()));

        ib_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(view.getContext(),"success",Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}