package com.company.soccershoesstore;

import android.content.Context;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AllCategoryFragmentAdapter extends RecyclerView.Adapter<AllCategoryFragmentAdapter.ViewHolder> {

    private List<AllCategoryFragmentProduct> products;
    private Context context;
    //private boolean isFilled = false;

    public AllCategoryFragmentAdapter(List<AllCategoryFragmentProduct> products, Context context) {
        this.products = products;
        this.context = this.context;

        FavoritesFragmentManager.initialize(context);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_all_category_fragment_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AllCategoryFragmentProduct product = products.get(position);
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
                double currentPrice = Double.parseDouble(products.get(position).getPrice());
                double newPrice = currentPrice + 1; // Ví dụ tăng giá tiền lên 1 đơn vị
                products.get(position).setPrice(String.valueOf(newPrice));
                // Sắp xếp lại danh sách sản phẩm theo giá tiền tăng dần
                Collections.sort(products, new Comparator<AllCategoryFragmentProduct>() {
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
                double currentPrice = Double.parseDouble(products.get(position).getPrice());
                double newPrice = currentPrice - 1; // Ví dụ giảm giá tiền đi 1 đơn vị
                if (newPrice < 0) {
                    newPrice = 0; // Đảm bảo giá tiền không âm
                }
                products.get(position).setPrice(String.valueOf(newPrice));
                // Sắp xếp lại danh sách sản phẩm theo giá tiền giảm dần
                Collections.sort(products, new Comparator<AllCategoryFragmentProduct>() {
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
        // Thực hiện kiểm tra trạng thái yêu thích
        boolean isFavorite = FavoritesFragmentManager.isProductInFavorites(product);
        Drawable filledHeart = ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.ic_love_fill);
        Drawable outlineHeart = ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.ic_love);
        holder.favoriteButton.setBackground(isFavorite ? filledHeart : outlineHeart);
        holder.favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Thực hiện chuyển đổi giữa hình ảnh background cho nút favorite
//                Drawable filledHeart = ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.ic_love_fill);
//                Drawable outlineHeart = ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.ic_love);

                // Kiểm tra xem sản phẩm đã có trong danh sách yêu thích chưa
                boolean isFavorite = FavoritesFragmentManager.isProductInFavorites(product);

                if (isFavorite) {
                    // Nếu sản phẩm đã có trong danh sách yêu thích, xóa nó khỏi danh sách
                    FavoritesFragmentManager.removeProductFromFavorites(product, context);
                    holder.favoriteButton.setBackground(outlineHeart);
                } else {
                    // Nếu sản phẩm chưa có trong danh sách yêu thích, thêm nó vào danh sách
                    FavoritesFragmentManager.addProductToFavorites(product, context);
                    holder.favoriteButton.setBackground(filledHeart);
                }
//                notifyItemRemoved(position);
//                notifyItemRangeChanged(position, products.size());

                // Chuyển đổi sang FavoritesFragment
//                Fragment favoritesFragment = new FavoritesFragment();
//                FragmentActivity activity = null;
//                if (v.getContext() instanceof FragmentActivity) {
//                    activity = (FragmentActivity) v.getContext();
//                }
//
//                if (activity != null) {
//                    FragmentManager fragmentManager = activity.getSupportFragmentManager();
//                    FragmentTransaction transaction = fragmentManager.beginTransaction();
//                    transaction.replace(R.id.fragment_fav_container, favoritesFragment);
//                    transaction.addToBackStack(null);
//                    transaction.commit();
//                }

                // Cập nhật lại adapter ngay lập tức
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView productBrand;
        public TextView productName;
        public TextView productDescription;
        public TextView productPrice;
        public ImageView productImage;
        public Button increasePriceButton;
        public Button decreasePriceButton;
        public Button favoriteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.product_name);
            productBrand = itemView.findViewById(R.id.product_brand);
            productDescription = itemView.findViewById(R.id.product_description);
            productPrice = itemView.findViewById(R.id.product_price);
            productImage = itemView.findViewById(R.id.product_image);
            increasePriceButton = itemView.findViewById(R.id.increase_price_button);
            decreasePriceButton = itemView.findViewById(R.id.decrease_price_button);
            favoriteButton = itemView.findViewById(R.id.favorite_button);
        }
    }
}
