package com.company.soccershoesstore;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class AdapterCartItem extends ArrayAdapter<CartItem> {
    Context mcontext;
    ArrayList<CartItem> cartItems;
    public AdapterCartItem(@NonNull Context context, int resource, @NonNull List<CartItem> objects) {
        super(context, resource, objects);
        this.mcontext=context;
        this.cartItems= (ArrayList<CartItem>) objects;
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
}
