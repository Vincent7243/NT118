package com.company.soccershoesstore;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

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
        ib_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity_admin_sale_edit.super.onBackPressed();
            }
        });
    }
}