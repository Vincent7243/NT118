package com.company.soccershoesstore;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity implements AdapterCartItem.OnCartItemChangeListener {
    ListView lv;
    TextView tvToal;
    Button btnCheckout;
    AdapterCartItem adapterCartItem;
    ArrayList<CartItem> cartItems;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        Toolbar toolbar = findViewById(R.id.cart_toolbar);
        setSupportActionBar(toolbar);

        // Hiển thị nút back và thay đổi tiêu đề
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Cart"); // Đặt tiêu đề là "Chat"
        }

        lv=findViewById(R.id.lv_cart);
        tvToal=findViewById(R.id.tv_cart_total_all);
        btnCheckout=findViewById(R.id.btn_cart_checkout);

        cartItems=new ArrayList<>();

        adapterCartItem=new AdapterCartItem(this,R.layout.item_cart,cartItems);

        lv.setAdapter(adapterCartItem);




    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Xử lý sự kiện khi người dùng nhấn nút back
        if (item.getItemId() == android.R.id.home) {
            finish(); // Đóng activity hiện tại và quay về activity trước đó
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    void getData() {
        cartItems.clear();
        FirebaseFirestore.getInstance().collection("cart_items")
                .whereEqualTo("id_user", FirebaseAuth.getInstance().getCurrentUser().getUid())
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        long totallall=0;
                        for (DocumentSnapshot documentSnapshot:queryDocumentSnapshots) {
                            totallall+=Long.parseLong(documentSnapshot.get("total").toString());
                            cartItems.add(new CartItem(documentSnapshot.getId(),documentSnapshot.get("id_product").toString(),documentSnapshot.get("quan").toString(),documentSnapshot.get("total").toString()));
                        }
                        adapterCartItem.notifyDataSetChanged();
                        tvToal.setText(CardProductAdapter.formatCurrency(totallall+""));
                        long finalTotallall = totallall;
                        btnCheckout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                FirebaseFirestore.getInstance().collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                if(task.getResult().get("address").toString().equals("NULL")||task.getResult().get("phonenum").toString().equals("NULL")) {
                                                    Toast.makeText(CartActivity.this, "Please fill in all your information before checking out!", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Intent intent=new Intent(CartActivity.this,CheckoutActivity.class);
                                                    intent.putExtra("total", finalTotallall +"");
                                                    startActivity(intent);
                                                }
                                            }
                                        });
                            }
                        });
                    }
                });
    }

    @Override
    public void onCartItemChanged() {
        getData();
    }


}
