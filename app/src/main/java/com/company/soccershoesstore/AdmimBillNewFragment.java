package com.company.soccershoesstore;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.play.integrity.internal.b;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class AdmimBillNewFragment extends Fragment {
   ListView lv;
   ArrayList <BillInfo> billInfos;
   AdapterAdminBill adapter;
   FirebaseFirestore db;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_admim_bill_new, container, false);
        lv=view.findViewById(R.id.lv_admin_bill_new);
        db=FirebaseFirestore.getInstance();
        billInfos=new ArrayList<>();
        adapter =new AdapterAdminBill(view.getContext(),R.layout.item_admin_bill_new,billInfos);

        db.collection("bills")
                .whereEqualTo("status","0")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for(DocumentSnapshot queryDocumentSnapshots:task.getResult()) {
                            billInfos.add(new BillInfo(queryDocumentSnapshots.getId(),queryDocumentSnapshots.get("id_user").toString(),"0",queryDocumentSnapshots.get("total").toString()));
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
        lv.setAdapter(adapter);

        return view;
    }
}