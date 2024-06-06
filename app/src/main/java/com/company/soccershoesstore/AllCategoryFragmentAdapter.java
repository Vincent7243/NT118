package com.company.soccershoesstore;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AllCategoryFragmentAdapter extends RecyclerView.Adapter<AllCategoryFragmentAdapter.ViewHolder> {

    private List<AllCategoryFragmentProduct> productList;
    private boolean isFilled = false;

    public AllCategoryFragmentAdapter(List<AllCategoryFragmentProduct> productList) {
        this.productList = productList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_all_category_fragment_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AllCategoryFragmentProduct product = productList.get(position);
        holder.productName.setText(product.getName());
        holder.productBrand.setText(product.getBrand());
        holder.productDescription.setText(product.getDescription());
        holder.productPrice.setText(product.getPrice());

        // Lấy URL của hình ảnh từ Firebase Storage
        StorageReference storageRef = FirebaseStorage.getInstance().getReferenceFromUrl(product.getImage());
        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Sử dụng Glide, Picasso hoặc Fresco để tải và hiển thị hình ảnh từ URL
                Glide.with(holder.itemView.getContext())
                        .load(uri)
                        .into(holder.productImage);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Xử lý khi có lỗi xảy ra khi lấy URL của hình ảnh
            }
        });
        if (position == 0) {
            holder.increasePriceButton.setVisibility(View.VISIBLE);
            holder.decreasePriceButton.setVisibility(View.VISIBLE);
        } else {
            holder.increasePriceButton.setVisibility(View.GONE);
            holder.decreasePriceButton.setVisibility(View.GONE);
        }

        holder.increasePriceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tăng giá tiền của sản phẩm
                double currentPrice = Double.parseDouble(productList.get(position).getPrice());
                double newPrice = currentPrice + 1; // Ví dụ tăng giá tiền lên 1 đơn vị
                productList.get(position).setPrice(String.valueOf(newPrice));
                // Sắp xếp lại danh sách sản phẩm theo giá tiền tăng dần
                Collections.sort(productList, new Comparator<AllCategoryFragmentProduct>() {
                    @Override
                    public int compare(AllCategoryFragmentProduct o1, AllCategoryFragmentProduct o2) {
                        double price1 = Double.parseDouble(o1.getPrice());
                        double price2 = Double.parseDouble(o2.getPrice());
                        return Double.compare(price1, price2);
                    }
                });
                notifyDataSetChanged();
            }
        });

        holder.decreasePriceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Giảm giá tiền của sản phẩm
                double currentPrice = Double.parseDouble(productList.get(position).getPrice());
                double newPrice = currentPrice - 1; // Ví dụ giảm giá tiền đi 1 đơn vị
                if (newPrice < 0) {
                    newPrice = 0; // Đảm bảo giá tiền không âm
                }
                productList.get(position).setPrice(String.valueOf(newPrice));
                // Sắp xếp lại danh sách sản phẩm theo giá tiền giảm dần
                Collections.sort(productList, new Comparator<AllCategoryFragmentProduct>() {
                    @Override
                    public int compare(AllCategoryFragmentProduct o1, AllCategoryFragmentProduct o2) {
                        double price1 = Double.parseDouble(o1.getPrice());
                        double price2 = Double.parseDouble(o2.getPrice());
                        return Double.compare(price2, price1);
                    }
                });
                notifyDataSetChanged();
            }
        });
        holder.favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Thực hiện chuyển đổi giữa hình ảnh background cho nút favorite
                Drawable filledHeart = ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.ic_love_fill);
                Drawable outlineHeart = ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.ic_love);

                if (!isFilled) {
                    holder.favoriteButton.setBackground(filledHeart);
                    isFilled = true;
                } else {
                    holder.favoriteButton.setBackground(outlineHeart);
                    isFilled = false;
                }
            }
        });



    }


    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView productBrand;
        public TextView productName;
        public TextView productDescription;
        public TextView productPrice;
        public ImageView productImage;
        public Button increasePriceButton; // Thêm thuộc tính increasePriceButton
        public Button decreasePriceButton; // Thêm thuộc tính decreasePriceButton
        public Button favoriteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.product_name);
            productBrand = itemView.findViewById(R.id.product_brand);
            productDescription = itemView.findViewById(R.id.product_description);
            productPrice = itemView.findViewById(R.id.product_price);
            productImage = itemView.findViewById(R.id.product_image);
            increasePriceButton = itemView.findViewById(R.id.increase_price_button); // Ánh xạ nút tăng giá tiền
            decreasePriceButton = itemView.findViewById(R.id.decrease_price_button); // Ánh xạ nút giảm giá tiền
            favoriteButton = itemView.findViewById(R.id.favorite_button);
        }
    }
}
