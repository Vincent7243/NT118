package com.company.soccershoesstore;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Admin_detail_bill extends AppCompatActivity {
    ImageButton ib_back;
    String idbill;
    ListView lv;
    TextView tvTotal;
    ArrayList<BillDetail> billDetails;
    AdapterBillDetail adapterBillDetail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_detail_bill);
        idbill=getIntent().getStringExtra("idbill");

        ib_back=findViewById(R.id.ib_admin_detail_bill_back);
        getDetailBill(idbill);
        ib_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Admin_detail_bill.super.onBackPressed();
            }
        });
        lv=findViewById(R.id.lv_admin_detail_bill);
        tvTotal=findViewById(R.id.tv_admin_detail_bill_total);
        tvTotal.setText(CardProductAdapter.formatCurrency(getIntent().getStringExtra("total")));
        billDetails=new ArrayList<>();
//        billDetails.add(new BillDetail("","","",""));
//        billDetails.add(new BillDetail("","","",""));
        adapterBillDetail=new AdapterBillDetail(this,R.layout.item_admin_bill_detail,billDetails);
        lv.setAdapter(adapterBillDetail);
    }
    public void getDetailBill(String idBill) {
        FirebaseFirestore.getInstance().collection("bill_items")
                .whereEqualTo("id_bill",idBill)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for(DocumentSnapshot queryDocumentSnapshots:task.getResult()) {
                            billDetails.add(new BillDetail(idbill,queryDocumentSnapshots.get("id_product").toString(),queryDocumentSnapshots.get("quan").toString(),queryDocumentSnapshots.get("total").toString()));
                            adapterBillDetail.notifyDataSetChanged();
                         }
                    }
                });
    }
}