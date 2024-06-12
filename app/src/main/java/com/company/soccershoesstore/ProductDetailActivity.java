package com.company.soccershoesstore;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductDetailActivity extends AppCompatActivity {
    String idproduct,price;
    TextView tvbrand,tvname,tvrate,tvprice,tvdescription;
    ImageView iv;
    ImageButton ib_back;
    Button btn_favorite,btn_addtocart,btn_checkout;
    List list1;

    boolean isFavorite,inCart ;

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
        btn_favorite=findViewById(R.id.btn_detail_favorite);
        btn_addtocart=findViewById(R.id.btn_detail_addtocart);
        list1=FavoritesFragmentManager.getFavoriteProducts();
        btn_checkout=findViewById(R.id.btn_detail_checkout);
        isFavorite=false;
        inCart=false;
        tvdescription.setMovementMethod(new ScrollingMovementMethod());
        checkFavorite();
        checkInCart();
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
                        price=documentSnapshot.get("price").toString();
                        tvdescription.setText(documentSnapshot.get("description").toString());
                    }
                });
        ib_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btn_addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!inCart) {
                    addToCart(idproduct, FirebaseAuth.getInstance().getCurrentUser().getUid().toString(),price);
                    inCart=true;
                    Drawable drawable = ContextCompat.getDrawable(ProductDetailActivity.this, R.drawable.ic_cart_fill_home);
                    btn_addtocart.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
                }else {
                    Toast.makeText(getApplicationContext(),"This product is already in the cart! ",Toast.LENGTH_SHORT).show();
                }
            }
        });
        
        btn_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseFirestore.getInstance().collection("Products").document(idproduct)
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot document) {
                                AllCategoryFragmentProduct product = document.toObject(AllCategoryFragmentProduct.class);
//                                boolean isFavorite = FavoritesFragmentManager.isProductInFavorites(product);
                                if (isFavorite) {
                                    // Nếu sản phẩm đã có trong danh sách yêu thích, xóa nó khỏi danh sách
                                    Toast.makeText(getApplicationContext(),"This product is already in the favorites list!",Toast.LENGTH_SHORT).show();
                                } else {
                                    // Nếu sản phẩm chưa có trong danh sách yêu thích, thêm nó vào danh sách
                                    isFavorite=true;
                                    FavoritesFragmentManager.addProductToFavorites(product, getApplicationContext());
                                    Toast.makeText(getApplicationContext(),"Added in the favorites list!",Toast.LENGTH_SHORT).show();
                                    list1.add(product);
                                    Drawable drawable = ContextCompat.getDrawable(ProductDetailActivity.this, R.drawable.ic_love_fill);
                                    btn_favorite.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
                                }


                            }
                        });




            }
        });
    }
    public void addToCart(String mIdProduct,String mIdUser,String pricem) {
        Map<String, Object> product = new HashMap<>();
        product.put("id_product", mIdProduct);
        product.put("id_user", mIdUser);
        product.put("quan", "1");
        product.put("total", pricem);


        FirebaseFirestore.getInstance().collection("cart_items").document("z"+System.currentTimeMillis())
                .set(product)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(),"Added to cart",Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"Err: "+e.getMessage(),Toast.LENGTH_SHORT).show();

                    }
                });


    }
    void checkFavorite() {
        FirebaseFirestore.getInstance().collection("Products").document(idproduct)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot document) {
                        AllCategoryFragmentProduct product = document.toObject(AllCategoryFragmentProduct.class);
//                                boolean isFavorite = FavoritesFragmentManager.isProductInFavorites(product);
                        for (Object item : list1) {
                            if (item instanceof AllCategoryFragmentProduct) {
                                AllCategoryFragmentProduct favProduct = (AllCategoryFragmentProduct) item;
                                if (favProduct.getImage().equals(product.getImage())) {
                                    isFavorite = true;

                                    break;
                                }
                            }
                        }
                        if(isFavorite) {
                            Drawable drawable = ContextCompat.getDrawable(ProductDetailActivity.this, R.drawable.ic_love_fill);
                            btn_favorite.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
                        }
                        else {
                            Drawable drawable = ContextCompat.getDrawable(ProductDetailActivity.this, R.drawable.ic_lovecart);
                            btn_favorite.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
                        }


                    }
                });
    }
    void checkInCart() {
        Log.d("test1234",FirebaseAuth.getInstance().getCurrentUser().getUid());
        Log.d("test1234",idproduct);
        FirebaseFirestore.getInstance().collection("cart_items")
                .whereEqualTo("id_user",FirebaseAuth.getInstance().getCurrentUser().getUid())
                .whereEqualTo("id_product",idproduct)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(!queryDocumentSnapshots.isEmpty()) {
                            inCart=true;
                            Drawable drawable = ContextCompat.getDrawable(ProductDetailActivity.this, R.drawable.ic_cart_fill_home);
                            btn_addtocart.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
                        }else  {
                            Drawable drawable = ContextCompat.getDrawable(ProductDetailActivity.this, R.drawable.ic_cart_home);
                            btn_addtocart.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
                        }
                    }
                });

    }
    
}