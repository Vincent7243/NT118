package com.company.soccershoesstore;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.util.HashMap;
import java.util.Map;

public class activity_admin_sale_edit extends AppCompatActivity {
    String saleid,salecode,saleprice,salequantity;
    ImageButton ib_back,ib_check;


    EditText et_code,et_price,et_quantity;

    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_admin_sale_edit);
        Intent intent=getIntent();
        saleid=intent.getStringExtra("iid");
        salecode=intent.getStringExtra("mcode");
        saleprice=intent.getStringExtra("mprice");
        salequantity=intent.getStringExtra("mquantity");
        ib_back=findViewById(R.id.ib_admin_sale_edit_back);
        ib_check=findViewById(R.id.ib_admin_sale_edit_check);
        et_code=findViewById(R.id.et_admin_sale_edit_code);
        et_price=findViewById(R.id.et_admin_sale_edit_price);
        et_quantity=findViewById(R.id.et_admin_sale_edit_quantity);
        et_code.setText(salecode);
        et_price.setText(saleprice);
        et_quantity.setText(salequantity);
        db=FirebaseFirestore.getInstance();
        ib_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity_admin_sale_edit.super.onBackPressed();
            }
        });
        ib_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(et_code.getText())||TextUtils.isEmpty(et_price.getText())||TextUtils.isEmpty(et_quantity.getText())) {
                    Toast.makeText(getApplicationContext(),"Plese fill all information",Toast.LENGTH_SHORT).show();
                    return;
                }else if(saleid.isEmpty()) {
                    addsale(et_code.getText().toString(),et_price.getText().toString(),et_quantity.getText().toString());
                } else {

                    editsale(saleid,et_code.getText().toString(),et_price.getText().toString(),et_quantity.getText().toString());
                }
            }
        });
    }
    public void addsale(String code,String price,String quantity) {
        Map<String, Object> sale = new HashMap<>();
        sale.put("code", code);
        sale.put("price", price);
        sale.put("quantity", quantity);

        String miid=System.currentTimeMillis()+"";
        db.collection("sales").document(miid)
                .set(sale)
//                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                    @Override
//                    public void onSuccess(DocumentReference documentReference) {
//                        Log.d("sale add", "DocumentSnapshot written with ID: " + documentReference.getId());
//                        Toast.makeText(getApplicationContext(),"Add sale successful!",Toast.LENGTH_SHORT).show();
//                        goToSale();
//                    }
//                })
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("addsale", "DocumentSnapshot successfully written!");
                        Toast.makeText(getApplicationContext(),"Add sale succesful!",Toast.LENGTH_SHORT).show();
                        goToSale();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("sale add", "Error adding document", e);
                        Toast.makeText(getApplicationContext(),"Add sale failed!",Toast.LENGTH_SHORT).show();

                    }
                });
    }
    public void editsale(String iid ,String code,String price,String quantity) {
        Map<String, Object> sale = new HashMap<>();
        sale.put("code", code);
        sale.put("price", price);
        sale.put("quantity", quantity);
        db.collection("sales").document(iid)
                .set(sale)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("editsale", "DocumentSnapshot successfully written!");
                        Toast.makeText(getApplicationContext(),"Edit sale succesful!",Toast.LENGTH_SHORT).show();
                        goToSale();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("editsale", "Error writing document", e);
                        Toast.makeText(getApplicationContext(),"Edit sale failed!",Toast.LENGTH_SHORT).show();

                    }
                });


    }

    public void goToSale() {
        onBackPressed();
    }
}