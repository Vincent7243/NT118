package com.company.soccershoesstore;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class activity_admin_product_edit extends AppCompatActivity {
    String proid,proname,proimage,prodescription,proprice,probrand;
    ImageButton ib_back,ib_check;
    ImageView iv;
    Button btn_change;
    EditText et_brand,et_name,et_price,et_description;
    FirebaseStorage storage ;
    LinearLayout ll;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_product_edit);
        Intent intent=getIntent();
        proid=intent.getStringExtra("mid");
        proname=intent.getStringExtra("mname");
        prodescription=intent.getStringExtra("mdescription");
        proimage=intent.getStringExtra("mimage");
        proprice=intent.getStringExtra("mprice");
        probrand=intent.getStringExtra("mbrand");
        ib_back=findViewById(R.id.ib_admin_product_edit_back);
        ib_check=findViewById(R.id.ib_admin_product_edit_check);
        iv=findViewById(R.id.iv_admin_prouct_edit);
        btn_change=findViewById(R.id.btn_admin_product_edit_change);
        et_name=findViewById(R.id.et_admin_product_edit_name);
        et_brand=findViewById(R.id.et_admin_product_edit_brand);
        et_price=findViewById(R.id.et_admin_product_edit_price);
        et_description=findViewById(R.id.et_admin_product_edit_description);
        et_name.setText(proname);
        et_brand.setText(probrand);
        et_description.setText(prodescription);
        et_price.setText(proprice);
        ll=findViewById(R.id.ll_admin_edit_loading);
        storage= FirebaseStorage.getInstance("gs://nt118-6829d.appspot.com");
        db = FirebaseFirestore.getInstance();
        ib_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity_admin_product_edit.super.onBackPressed();
            }
        });
        if(!proimage.isEmpty()) {
            FirebaseStorage storage = FirebaseStorage.getInstance("gs://nt118-6829d.appspot.com");
            StorageReference storageRef = storage.getReferenceFromUrl(proimage);
            storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    String res= uri.toString();
                    Log.d("imgaefirebase","thành cong");

                    Glide.with(getApplicationContext())
                            .load(res)
                            .into(iv);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();
                    Log.d("imgaefirebase",e.getMessage());
                }
            });

        } else {
            iv.setImageResource(R.drawable.default_image_admin);
        }
        btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent();
                intent1.setType("image/*");
                intent1.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent1, "Chọn ảnh"),200);
            }
        });
        ib_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(et_brand.getText())||TextUtils.isEmpty(et_price.getText())||TextUtils.isEmpty(et_name.getText())||TextUtils.isEmpty(et_description.getText())) {
                    Toast.makeText(getApplicationContext(),"Plese fill all information!",Toast.LENGTH_SHORT).show();
                } else if(proid.isEmpty()) {
                    ll.setVisibility(View.VISIBLE);
                    addImage(iv,et_name.getText().toString(),et_description.getText().toString(),et_price.getText().toString(),et_brand.getText().toString());

                } else {
                    Toast.makeText(getApplicationContext(),"This is edit!",Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK) {
            if(requestCode==200) {
                Uri selected=data.getData();
                if(null!=selected) {
                    iv.setImageURI(selected);
                }
            }
        }
    }
    public void addImage(ImageView imageView,String name,String description,String price,String brand) {
        StorageReference storageRef = storage.getReference();
        StorageReference imageRef = storageRef.child(System.currentTimeMillis()+".png");
        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = imageRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(getApplicationContext(),"Lưu ảnh thất bại",Toast.LENGTH_SHORT).show();

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                String imagename="gs://nt118-6829d.appspot.com/"+imageRef.getName();
                addDocument(name,description,imagename,price,brand);
            }
        });
    }
    public void addDocument(String name,String description,String image,String price,String brand){
        Map<String, Object> product = new HashMap<>();
        product.put("name", name);
        product.put("description", description);
        product.put("image", image);
        product.put("price", price);
        product.put("brand", brand);

        db.collection("Products")
                .add(product)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        ll.setVisibility(View.GONE);
                        Log.d("add document product", "DocumentSnapshot added with ID: " + documentReference.getId());
                        Toast.makeText(getApplicationContext(),"Lưu thành công",Toast.LENGTH_SHORT).show();
                        activity_admin_product_edit.super.onBackPressed();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        ll.setVisibility(View.GONE);

                        Log.w("add document product", "Error adding document", e);
                        Toast.makeText(getApplicationContext(),"Lưu thất bại",Toast.LENGTH_SHORT).show();

                    }
                });
    }
}