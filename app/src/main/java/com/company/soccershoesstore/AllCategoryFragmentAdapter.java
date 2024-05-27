//package com.company.soccershoesstore;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.util.List;
//
//public class AllCategoryFragmentAdapter extends RecyclerView.Adapter<AllCategoryFragmentAdapter.ProductViewHolder> {
//
//    private Context context;
//    private List<Product> productList;
//
//    public AllCategoryFragmentAdapter(Context context, List<Product> productList) {
//        this.context = context;
//        this.productList = productList;
//    }
//
//
//    // đoạn code dưỡi này bị lỗi
//    @NonNull
//    @Override
//    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(context).inflate(R.layout.activity_admin, parent, false);
//        return new ProductViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
//        Product product = productList.get(position);
//        holder.productName.setText(product.getName());
//        // Load image using a library like Glide or Picasso
//        // Glide.with(context).load(product.getImageUrl()).into(holder.productImage);
//    }
//
//    @Override
//    public int getItemCount() {
//        return productList.size();
//    }
//
//    public static class ProductViewHolder extends RecyclerView.ViewHolder {
//        ImageView productImage;
//        TextView productName;
//
//        public ProductViewHolder(@NonNull View itemView) {
//            super(itemView);
//            productImage = itemView.findViewById(R.id.product_image);
//            productName = itemView.findViewById(R.id.product_name);
//        }
//    }
//}
