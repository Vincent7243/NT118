package com.company.soccershoesstore;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class WaitingFragment extends Fragment {

    private RecyclerView recyclerView;
    private BillItemAdapter adapter;
    private FirebaseFirestore db;
    private List<String> billIds;
    private List<BillItem> billItemList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_waiting, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        db = FirebaseFirestore.getInstance();
        billIds = new ArrayList<>();
        billItemList = new ArrayList<>();

        // Initialize the adapter with an empty list
        adapter = new BillItemAdapter(billItemList);
        recyclerView.setAdapter(adapter);

        loadBills();
        return view;
    }

    private void loadBills() {
        db.collection("bills")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult() != null) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    billIds.add(document.getId());
                                }
                                loadBillItems();
                            } else {
                                Toast.makeText(getContext(), "No bills found", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    private void loadBillItems() {
        if (!billIds.isEmpty()) {
            db.collection("bill_items")
                    .whereIn("id_bill", billIds)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                if (task.getResult() != null) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        BillItem billItem = document.toObject(BillItem.class);
                                        billItemList.add(billItem);
                                    }
                                    // Notify the adapter that data has changed
                                    adapter = new BillItemAdapter(billItemList);
                                    recyclerView.setAdapter(adapter);
                                }
                            } else {
                                Toast.makeText(getContext(), "Error getting bill items: " + task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            Toast.makeText(getContext(), "No bills found", Toast.LENGTH_SHORT).show();
        }
    }
}
