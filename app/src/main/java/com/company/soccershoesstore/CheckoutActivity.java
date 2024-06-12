package com.company.soccershoesstore;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class CheckoutActivity extends AppCompatActivity {
    TextView tv_idbill,tv_total,tv_checkvoucher,tv_labeltotal,tv_total_aftervoucher;
    EditText et_voucher;
    Button btn_checkvoucher,btn_confirmcheckout;
    ImageButton ib_back;
    LinearLayout ll_afterVoucher;
    String total;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        tv_idbill=findViewById(R.id.tv_checkout_idbill);
        tv_total=findViewById(R.id.tv_checkout_totalbill);
        tv_checkvoucher=findViewById(R.id.tv_checkout_checkcoucher);
        et_voucher=findViewById(R.id.et_checkout_voucher);
        btn_checkvoucher=findViewById(R.id.btn_checkout_checkvoucher);
        btn_confirmcheckout=findViewById(R.id.btn_checkout_confirm);
        ib_back=findViewById(R.id.ib_checkout_back);
        ll_afterVoucher=findViewById(R.id.ll_checkout_aftervoucher);
        tv_labeltotal=findViewById(R.id.tv_checkout_labeltotal);
        tv_total_aftervoucher=findViewById(R.id.tv_total_aftervoucher);
        total=getIntent().getStringExtra("total");


        tv_total.setText(CardProductAdapter.formatCurrency(getIntent().getStringExtra("total")));
        tv_idbill.setText("z"+System.currentTimeMillis());
        ib_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btn_checkvoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_voucher.getText().toString().isEmpty()){
                    Toast.makeText(CheckoutActivity.this, "Please enter your voucher!", Toast.LENGTH_SHORT).show();
                }else {
                    FirebaseFirestore.getInstance().collection("sales")
                            .whereEqualTo("code",et_voucher.getText().toString())
                            .get()
                            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @SuppressLint("ResourceAsColor")
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                    if(queryDocumentSnapshots.isEmpty()) {
                                        tv_checkvoucher.setText("This voucher is incorrect or expired!");
                                        et_voucher.setText("");
                                        int color = ContextCompat.getColor(getApplicationContext(), R.color.ic_delete);
                                        tv_checkvoucher.setTextColor(color);
                                        ll_afterVoucher.setVisibility(View.INVISIBLE);
                                        // Để xóa gạch ngang khỏi tv_total
                                        tv_total.setPaintFlags(tv_total.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));

// Để xóa gạch ngang khỏi tv_labeltotal
                                        tv_labeltotal.setPaintFlags(tv_labeltotal.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                                        total=getIntent().getStringExtra("total");
                                    }else {
                                        tv_checkvoucher.setText("The voucher is accepted!");
                                        int color = ContextCompat.getColor(getApplicationContext(), R.color.greenn);
                                        tv_checkvoucher.setTextColor(color);
                                        tv_total.setPaintFlags(tv_total.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                                        tv_labeltotal.setPaintFlags(tv_labeltotal.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                                        ll_afterVoucher.setVisibility(View.VISIBLE);
                                         total=(Long.parseLong(getIntent().getStringExtra("total").toString())- Long.parseLong(queryDocumentSnapshots.getDocuments().get(0).get("price").toString())+"");
                                        tv_total_aftervoucher.setText(CardProductAdapter.formatCurrency(total));
                                    }
                                }
                            });
                }
            }
        });
        btn_confirmcheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(getIntent().hasExtra("buynow")) {
                    if(!et_voucher.getText().toString().isEmpty()) {
                        decreaseVoucher();
                    }
                    Map<String, Object> bill = new HashMap<>();
                    bill.put("id_user", FirebaseAuth.getInstance().getCurrentUser().getUid());
                    bill.put("status", "0");
                    bill.put("total", total);


                    FirebaseFirestore.getInstance().collection("bills").document(tv_idbill.getText().toString())
                            .set(bill);

                    Map<String, Object> billItem = new HashMap<>();
                    billItem.put("id_bill", tv_idbill.getText().toString());
                    billItem.put("id_product", getIntent().getStringExtra("idproduct"));
                    billItem.put("quan", "1");
                    billItem.put("total", getIntent().getStringExtra("total"));
                    FirebaseFirestore.getInstance().collection("bill_items").document("zz"+System.currentTimeMillis())
                            .set(billItem);
                    Intent intent=new Intent(CheckoutActivity.this,SuccessFullActivity.class);
                    startActivity(intent);
                }else {
                    addBill();
                }
            }
        });

    }
    public void addBill() {
        Map<String, Object> bill = new HashMap<>();
        bill.put("id_user", FirebaseAuth.getInstance().getCurrentUser().getUid());
        bill.put("status", "0");
        bill.put("total", total);


        FirebaseFirestore.getInstance().collection("bills").document(tv_idbill.getText().toString())
                .set(bill)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(!et_voucher.getText().toString().isEmpty()) {
                            decreaseVoucher();
                        }
                        addBillItem();
                        deleteCart();
                        Intent intent=new Intent(CheckoutActivity.this,SuccessFullActivity.class);
                        startActivity(intent);
                    }
                });
    }
    public void decreaseVoucher() {
        FirebaseFirestore.getInstance().collection("sales")
                .whereEqualTo("code",et_voucher.getText().toString())
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(!queryDocumentSnapshots.isEmpty()) {
                            DocumentSnapshot documentSnapshot=queryDocumentSnapshots.getDocuments().get(0);
                            String quan=documentSnapshot.get("quantity").toString();
                            if(quan.equals("1")) {

                                FirebaseFirestore.getInstance().collection("sales")
                                        .whereEqualTo("code",et_voucher.getText().toString())
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                DocumentSnapshot documentSnapshot1=task.getResult().getDocuments().get(0);
                                                FirebaseFirestore.getInstance().collection("sales").document(documentSnapshot1.getId()).delete();
                                            }
                                        });
                            }else {


                                Map<String, Object> Sale = new HashMap<>();
                                Sale.put("code", documentSnapshot.get("code").toString());
                                Sale.put("price", documentSnapshot.get("price").toString());
                                Sale.put("quantity", (Long.parseLong(documentSnapshot.get("quantity").toString())-1)+"");
                                FirebaseFirestore.getInstance().collection("sales").document(documentSnapshot.getId())
                                        .set(Sale);
                            }
                        }
                    }
                });
    }
    public void addBillItem() {
        FirebaseFirestore.getInstance().collection("cart_items")
                .whereEqualTo("id_user",FirebaseAuth.getInstance().getCurrentUser().getUid())
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for(DocumentSnapshot documentSnapshot:queryDocumentSnapshots) {
                            Map<String, Object> billItem = new HashMap<>();
                            billItem.put("id_bill", tv_idbill.getText().toString());
                            billItem.put("id_product", documentSnapshot.get("id_product").toString());
                            billItem.put("quan", documentSnapshot.get("quan").toString());
                            billItem.put("total", documentSnapshot.get("total").toString());
                            FirebaseFirestore.getInstance().collection("bill_items").document("zz"+System.currentTimeMillis())
                                    .set(billItem);
                        }

                    }
                });
    }
    public void deleteCart() {
        FirebaseFirestore.getInstance().collection("cart_items")
                .whereEqualTo("id_user",FirebaseAuth.getInstance().getCurrentUser().getUid())
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        // Lặp qua các document và xóa chúng
                        for (DocumentSnapshot document : queryDocumentSnapshots) {
                            FirebaseFirestore.getInstance().collection("cart_items").document(document.getId()).delete();

                        }
                    }
                });
    }
}