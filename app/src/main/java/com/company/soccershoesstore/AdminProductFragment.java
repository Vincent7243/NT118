package com.company.soccershoesstore;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Locale;

public class AdminProductFragment extends Fragment {
    ImageButton ib_add;
    RecyclerView rv;
    ArrayList<Product> products;
    public ProductListAdapter adapter;
    FirebaseFirestore db;
    float dX=0, dY=0;
    long lastDownTime = 0;
    long clickThreshold = 200;
    androidx.appcompat.widget.SearchView searchView;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_product, container, false);
        ib_add = view.findViewById(R.id.ib_admin_product_add);
        rv = view.findViewById(R.id.rv_admin_product);
        searchView = view.findViewById(R.id.search_view_admin_product);
        db = FirebaseFirestore.getInstance();
        products = new ArrayList<>();

        ib_add.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        dX = view.getX() - event.getRawX();
                        dY = view.getY() - event.getRawY();
                        lastDownTime = System.currentTimeMillis();
                        break;

                    case MotionEvent.ACTION_MOVE:
                        view.animate()
                                .x(event.getRawX() + dX)
                                .y(event.getRawY() + dY)
                                .setDuration(0)
                                .start();
                        break;

                    case MotionEvent.ACTION_UP:
                        long currentTime = System.currentTimeMillis();
                        if (currentTime - lastDownTime < clickThreshold) {
                            view.performClick();
                        }
                        break;

                    default:
                        return false;
                }
                return true;
            }
        });

        ib_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), activity_admin_product_edit.class);
                intent.putExtra("mid", "");
                intent.putExtra("mname", "");
                intent.putExtra("mimage", "");
                intent.putExtra("mdescription", "");
                intent.putExtra("mbrand", "");
                intent.putExtra("mprice", "");

                startActivity(intent);
            }
        });

        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });

        return view;
    }

    private void filter(String text) {
        ArrayList<Product> filteredList = new ArrayList<>();
        for (Product item : products) {
            if (item.getMname().toLowerCase(Locale.ROOT).contains(text.toLowerCase(Locale.ROOT)) ||
                    item.getMbrand().toLowerCase(Locale.ROOT).contains(text.toLowerCase(Locale.ROOT))) {
                filteredList.add(item);
            }
        }
        adapter.filterList(filteredList);
    }

    @Override
    public void onResume() {
        super.onResume();
        db.collection("Products")
                .orderBy("image", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            products.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                products.add(new Product(
                                        document.getId(),
                                        document.getString("name"),
                                        document.getString("price"),
                                        document.getString("image"),
                                        document.getString("description"),
                                        document.getString("brand")
                                ));
                            }
                            adapter = new ProductListAdapter(getContext(), products);
                            rv.setAdapter(adapter);
                            rv.setLayoutManager(new LinearLayoutManager(getContext()));
                            adapter.setOnProductDeleteListener(new ProductListAdapter.OnProductDeleteListener() {
                                @Override
                                public void onProductDeleted() {
                                    onResume();
                                }
                            });
                        } else {
                            Toast.makeText(getContext(), "Error getting documents.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
