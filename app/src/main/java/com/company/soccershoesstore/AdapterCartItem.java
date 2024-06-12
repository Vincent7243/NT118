package com.company.soccershoesstore;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdapterCartItem extends ArrayAdapter<CartItem> {
    Context mcontext;
    ArrayList<CartItem> cartItems;
    OnCartItemChangeListener cartItemChangeListener;

    public AdapterCartItem(@NonNull Context context, int resource, @NonNull List<CartItem> objects) {
        super(context, resource, objects);
        this.mcontext=context;
        this.cartItems= (ArrayList<CartItem>) objects;
        cartItemChangeListener = (OnCartItemChangeListener) context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView==null) {
            convertView= LayoutInflater.from(mcontext).inflate(R.layout.item_cart,null,false);
        }
        CartItem cartItem=getItem(position);
        TextView tvbrand=convertView.findViewById(R.id.tv_cart_item_brand);
        TextView tvname=convertView.findViewById(R.id.tv_cart_item_name);
        TextView tvquan=convertView.findViewById(R.id.tv_cart_item_quan);
        TextView tvprice=convertView.findViewById(R.id.tv_cart_item_price);
        ImageView iv=convertView.findViewById(R.id.iv_cart_item_imageproduct);
        TextView tvtotal=convertView.findViewById(R.id.tv_cart_item_total);
        ImageButton ib_incre=convertView.findViewById(R.id.ib_cart_item_incre);
        ImageButton ib_decre=convertView.findViewById(R.id.ib_cart_item_decre);
        tvtotal.setText(CardProductAdapter.formatCurrency(cartItem.getTotal()));
        tvquan.setText(cartItem.getQuan());
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),ProductDetailActivity.class);
                intent.putExtra("idproduct",cartItem.idProduct);
                mcontext.startActivity(intent);
            }
        });
        getInfoProduct(cartItem.idProduct,tvbrand,tvname,tvprice,iv);
        ib_incre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(mcontext, "ddddddd", Toast.LENGTH_SHORT).show();
                incrementQuantity(cartItem);
            }
        });
        ib_decre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrementQuantity(cartItem);
            }
        });
        return convertView;
    }

    @Override
    public int getCount() {
        return cartItems.size();
    }
    void getInfoProduct(String midp, TextView brand,TextView name,TextView price,ImageView iv) {
        FirebaseFirestore.getInstance().collection("Products").document(midp)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        brand.setText(documentSnapshot.get("brand").toString());
                        name.setText(documentSnapshot.get("name").toString());
                        price.setText(CardProductAdapter.formatCurrency(documentSnapshot.get("price").toString()));
                        getImageProduct(documentSnapshot.get("image").toString(),iv);
                    }
                });
    }
    void getImageProduct(String link,ImageView iv) {
        FirebaseStorage.getInstance().getReferenceFromUrl(link)
                .getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Glide.with(mcontext)
                                .load(uri)
                                .into(iv);
                    }
                });
    }
    public interface OnCartItemChangeListener {
        void onCartItemChanged();
    }
    private void incrementQuantity(CartItem cartItem) {
        Map<String, Object> product = new HashMap<>();
        product.put("id_product", cartItem.getIdProduct());
        product.put("id_user", FirebaseAuth.getInstance().getCurrentUser().getUid());
        product.put("quan", (Long.parseLong(cartItem.getQuan())+1)+"");
        FirebaseFirestore.getInstance().collection("Products")
                        .document(cartItem.idProduct)
                                .get()
                                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                            @Override
                                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                product.put("total", Long.parseLong(cartItem.getTotal())+Long.parseLong(documentSnapshot.get("price").toString())+"");
                                                FirebaseFirestore.getInstance().collection("cart_items").document(cartItem.getIdCart())
                                                        .set(product)
                                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void unused) {
                                                                cartItemChangeListener.onCartItemChanged();
                                                                Toast.makeText(mcontext,"Increased!",Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
                                            }
                                        });


    }

    private void decrementQuantity(CartItem cartItem) {
        if(cartItem.getQuan().equals("1")) {
            Toast.makeText(mcontext, "xuly sau", Toast.LENGTH_SHORT).show();
        }else {
            Map<String, Object> product = new HashMap<>();
            product.put("id_product", cartItem.getIdProduct());
            product.put("id_user", FirebaseAuth.getInstance().getCurrentUser().getUid());
            product.put("quan", (Long.parseLong(cartItem.getQuan())-1)+"");
            FirebaseFirestore.getInstance().collection("Products")
                    .document(cartItem.idProduct)
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            product.put("total", Long.parseLong(cartItem.getTotal())-Long.parseLong(documentSnapshot.get("price").toString())+"");
                            FirebaseFirestore.getInstance().collection("cart_items").document(cartItem.getIdCart())
                                    .set(product)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            cartItemChangeListener.onCartItemChanged();
                                            Toast.makeText(mcontext,"Decreased!",Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    });
        }


    }
}
