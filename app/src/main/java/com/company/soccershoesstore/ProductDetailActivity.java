package com.company.soccershoesstore;

import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

public class ProductDetailActivity extends AppCompatActivity {
    String idproduct;
    TextView tvbrand,tvname,tvrate,tvprice,tvdescription;
    ImageView iv;
    ImageButton ib_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        idproduct=getIntent().getStringExtra("idproduct");
        tvbrand=findViewById(R.id.tv_detailProduct_brand);
        tvname=findViewById(R.id.tv_detailProduct_name);
        tvrate=findViewById(R.id.tv_detailProduct_rate);
        tvprice=findViewById(R.id.tv_detailProduct_price);
        tvdescription=findViewById(R.id.tv_detailProduct_description);
        iv=findViewById(R.id.iv_productDetail_imageProduct);
        ib_back=findViewById(R.id.ib_detailProduct_back);

        tvdescription.setMovementMethod(new ScrollingMovementMethod());

        FirebaseFirestore.getInstance().collection("Products").document(idproduct)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        FirebaseStorage.getInstance().getReferenceFromUrl(documentSnapshot.get("image").toString())
                                        .getDownloadUrl()
                                                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                    @Override
                                                    public void onSuccess(Uri uri) {
                                                        Glide.with(getApplicationContext())
                                                                .load(uri)
                                                                .into(iv);
                                                    }
                                                });
                        tvbrand.setText("ID: "+documentSnapshot.getId().toString());
                        tvname.setText(documentSnapshot.get("name").toString());
                        tvprice.setText(CardProductAdapter.formatCurrency(documentSnapshot.get("price").toString()));
                        tvdescription.setText(documentSnapshot.get("description").toString());
                    }
                });
        ib_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}