package com.company.soccershoesstore;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class DeliveredProductAdapter extends RecyclerView.Adapter<DeliveredProductAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<Product> mProducts;

    public DeliveredProductAdapter(Context context, ArrayList<Product> products) {
        this.mContext = context;
        this.mProducts = products;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View productView = inflater.inflate(R.layout.item_list_product_user, parent, false);
        return new ViewHolder(productView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = mProducts.get(position);
        holder.tvName.setText(product.getMname());
        holder.tvBrand.setText(product.getMbrand());
        holder.tvPrice.setText(product.getMprice() + " vnd");

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl(product.getMimage());
        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String res = uri.toString();
                Log.d("imageFirebase", "Thành công");
                Glide.with(mContext)
                        .load(res)
                        .into(holder.iv);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(mContext, e.toString(), Toast.LENGTH_SHORT).show();
                Log.d("imageFirebase", e.getMessage());
            }
        });

        holder.ibDelete.setVisibility(View.GONE);  // Hide delete button for delivered products
    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv;
        TextView tvName, tvBrand, tvPrice;
        ImageView ibDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.iv_user_product_item_image);
            tvName = itemView.findViewById(R.id.tv_user_product_item_name);
            tvBrand = itemView.findViewById(R.id.tv_user_product_item_brand);
            tvPrice = itemView.findViewById(R.id.tv_user_product_item_price);
            ibDelete = itemView.findViewById(R.id.ib_user_product_item_delete);
        }
    }
}
