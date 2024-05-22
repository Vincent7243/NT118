package com.company.soccershoesstore;

import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.ktx.Firebase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;


public class AdminProductFragment extends Fragment {
    ImageButton ib_add;
    RecyclerView rv;
    ArrayList<Product> products;
    ProductListAdapter adapter;
    FirebaseFirestore db;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_admin_product, container, false);
        ib_add=view.findViewById(R.id.ib_admin_product_add);

        rv=view.findViewById(R.id.rv_admin_product);
        db=FirebaseFirestore.getInstance();
        products=new ArrayList<>();


        
        db.collection("Products")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("firebasefirestore", document.getId() + " => " + document.getData());
                                Log.d("firebasefirestore",  document.getData().get("name").toString());
                                products.add(new Product(document.getId().toString(),document.getData().get("name").toString(),document.getData().get("price").toString(),document.getData().get("image").toString(),document.getData().get("description").toString(),document.getData().get("brand").toString()));
                                adapter=new ProductListAdapter(view.getContext(),products);
                                rv.setAdapter(adapter);
                                rv.setLayoutManager(new LinearLayoutManager(view.getContext()));
                            }
                        }else {
                            Log.w("firebasefirestore", "Error getting documents.", task.getException());
                        }
                    }
                });



        ib_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(view.getContext(),"success",Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}