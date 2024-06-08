package com.company.soccershoesstore;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class AdminStaticFragment extends Fragment {
    ListView lv_user,lv_product;
    ArrayList<String> users;
    ArrayList<ItemProductStaticAdmin> products;
    AdapterAdminStaticUser adapterAdminStaticUser;
    AdapterProductStaticAdmin adapterProductStaticAdmin;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_admin_static, container, false);
        lv_product=view.findViewById(R.id.lv_admin_static_products);
        lv_user=view.findViewById(R.id.lv_admin_static_users);
        users=new ArrayList<>();
        products=new ArrayList<>();
        adapterAdminStaticUser=new AdapterAdminStaticUser(getContext(),R.layout.item_admin_static_lv_user,users);
        adapterProductStaticAdmin=new AdapterProductStaticAdmin(getContext(),R.layout.item_admin_static_product,products);
        lv_user.setAdapter(adapterAdminStaticUser);
        lv_product.setAdapter(adapterProductStaticAdmin);
        getUser();
        getProduct();
        return view;
    }
    public void getUser() {
        FirebaseFirestore.getInstance().collection("users")
                .whereNotEqualTo("email","donhan23456@gmail.com")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for(DocumentSnapshot documentSnapshot:queryDocumentSnapshots) {
                            users.add(documentSnapshot.getId());
                        }
                        adapterAdminStaticUser.notifyDataSetChanged();
                    }
                });
    }
    public void getProduct() {
        FirebaseFirestore.getInstance().collection("Products")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for(DocumentSnapshot documentSnapshot:queryDocumentSnapshots) {
                            Log.d("getProductfb",documentSnapshot.getId());
                            sumproduct(documentSnapshot.getId(),documentSnapshot.get("name").toString());
                        }
                    }
                });
    }
    public void sumproduct(String mid,String name) {
        FirebaseFirestore.getInstance().collection("bill_items")
                .whereEqualTo("id_product", mid)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        long tt=0;
                        for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            tt += Long.parseLong(documentSnapshot.get("quan").toString());
                        }
                        products.add(new ItemProductStaticAdmin(mid,tt,name));
                        sortDescendingByNum(products);
                        adapterProductStaticAdmin.notifyDataSetChanged();
//                        products.forEach(itemProductStaticAdmin -> Log.d("itemProduct",itemProductStaticAdmin.getNum()+""));

                    }
                });
    }

    public static void sortDescendingByNum(ArrayList<ItemProductStaticAdmin> list) {
        Collections.sort(list, new Comparator<ItemProductStaticAdmin>() {
            @Override
            public int compare(ItemProductStaticAdmin o1, ItemProductStaticAdmin o2) {
                return Long.compare(o2.getNum(), o1.getNum());
            }
        });
    }
}