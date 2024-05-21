package com.company.soccershoesstore;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder> {
private Context mcontext;
private ArrayList<Product> mproducts;

    public ProductListAdapter(Context mcontext, ArrayList<Product> mproducts) {
        this.mcontext = mcontext;
        this.mproducts = mproducts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(mcontext);
        View productview=inflater.inflate(R.layout.item_list_product_admin,parent,false);
        ViewHolder viewHolder=new ViewHolder(productview);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product=mproducts.get(position);

    }

    @Override
    public int getItemCount() {
        return mproducts.size()     ;
    }
public class ViewHolder extends RecyclerView.ViewHolder{
ImageView iv;
TextView tv_name,tv_brand,tv_price;
ImageButton ib_delete,ib_edit;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        iv=itemView.findViewById(R.id.iv_admin_product_item_image);
        tv_name=itemView.findViewById(R.id.tv_admin_product_item_name);
        tv_brand=itemView.findViewById(R.id.tv_admin_product_item_brand);
        tv_price=itemView.findViewById(R.id.tv_admin_product_item_price);
    }
}
}
