package com.company.soccershoesstore;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.lang.reflect.Array;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Locale;

public class CardProductAdapter extends RecyclerView.Adapter<CardProductAdapter.ViewHolder> {
     private Context mcontext;
    private ArrayList<ProductCard> mproductCards;
    FirebaseStorage storage;

    public CardProductAdapter(Context mcontext, ArrayList<ProductCard> mproductCards) {
        this.mcontext = mcontext;
        this.mproductCards = mproductCards;
        storage = FirebaseStorage.getInstance("gs://nt118-6829d.appspot.com");
    }

    @NonNull
    @Override
    public CardProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater=LayoutInflater.from(mcontext);
        View CardView=inflater.inflate(R.layout.item_productcard,viewGroup,false);
        ViewHolder viewHolder=new ViewHolder(CardView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CardProductAdapter.ViewHolder holder, int position) {

           ProductCard productCard=mproductCards.get(position);
           StorageReference storageRef = storage.getReferenceFromUrl(productCard.getImg());
        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String res= uri.toString();

                Glide.with(mcontext)
                        .load(res)
                        .into(holder.iv);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(mcontext,e.toString(),Toast.LENGTH_SHORT).show();
                Log.d("imgaefirebase",e.getMessage());
            }
        });

//           Glide.with(mcontext)
//                   .load(R.drawable.teamplate_san_pham)
//                   .into(holder.iv);
              holder.title.setText(productCard.getName());


           String price= productCard.getPrice();
           price=formatCurrency(price);
           holder.price.setText(price);
           holder.ll.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Intent intent=new Intent(mcontext,ProductDetailActivity.class);
                   intent.putExtra("idproduct",productCard.getId());
                   mcontext.startActivity(intent);
               }
           });


    }

    @Override
    public int getItemCount() {
        return mproductCards.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        LinearLayout ll;
        private TextView price;
        ImageView iv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.iv_home_cardproduct);
            price = itemView.findViewById(R.id.tv_price_productcard);
            title = itemView.findViewById(R.id.tv_title_productcard);
            ll=itemView.findViewById(R.id.ll_cardproduct);
        }
    }
    public static String formatCurrency(String numberString) {
        try {
            // Chuyển chuỗi số thành số nguyên
            long number = Long.parseLong(numberString);

            // Sử dụng NumberFormat để định dạng số với dấu phân cách hàng nghìn
            NumberFormat numberFormat = NumberFormat.getInstance(Locale.US);
            String formattedNumber = numberFormat.format(number);

            // Thay thế dấu phẩy bằng khoảng trắng
            formattedNumber = formattedNumber.replace(",", " ");

            // Thêm hậu tố "VND"
            return formattedNumber + "VND";
        } catch (NumberFormatException e) {
            // Xử lý lỗi nếu chuỗi không thể chuyển đổi thành số
            e.printStackTrace();
            return numberString + "VND"; // Trả về chuỗi ban đầu nếu có lỗi
        }
    }
}
