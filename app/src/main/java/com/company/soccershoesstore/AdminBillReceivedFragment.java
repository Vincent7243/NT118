package com.company.soccershoesstore;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Locale;


public class AdminBillReceivedFragment extends Fragment {


    ListView lv;
    ArrayList<BillInfo> billInfos;
    AdapterAdminBill adapter;
    FirebaseFirestore db;
    SearchView sv;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_admin_bill_received, container, false);
        lv=view.findViewById(R.id.lv_admin_bill_received);
        db=FirebaseFirestore.getInstance();
        billInfos=new ArrayList<>();
        sv=view.findViewById(R.id.sv_admin_bill_received);
        adapter = new AdapterAdminBill(view.getContext(), R.layout.item_admin_bill_new, billInfos, new AdapterAdminBill.OnApproveButtonClickListener() {
            @Override
            public void onApproveButtonClick() {
                // Khởi tạo lại AdminBillFragment
                if (getActivity() instanceof AdminActivity) {
                    ((AdminActivity) getActivity()).reloadAdminBillFragment();
                }
            }
        });

        db.collection("bills")
                .whereEqualTo("status","2")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for(DocumentSnapshot queryDocumentSnapshots:task.getResult()) {
                            billInfos.add(new BillInfo(queryDocumentSnapshots.getId(),queryDocumentSnapshots.get("id_user").toString(),"2",queryDocumentSnapshots.get("total").toString()));
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
        lv.setAdapter(adapter);
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<BillInfo> filteredList = new ArrayList<>();
                for (BillInfo item : billInfos) {
                    if (item.getIdBill().toLowerCase(Locale.ROOT).contains(newText.toLowerCase(Locale.ROOT)) ) {
                        filteredList.add(item);
                    }
                }
                adapter.filterList(filteredList);
                return true;
            }
        });

        return view;
    }
}