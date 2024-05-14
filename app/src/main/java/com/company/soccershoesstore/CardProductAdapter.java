package com.company.soccershoesstore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

public class CardProductAdapter extends RecyclerView.Adapter<CardProductAdapter.ViewHolder> {
     private Context mcontext;
    private ArrayList<ProductCard> mproductCards;

    public CardProductAdapter(Context mcontext, ArrayList<ProductCard> mproductCards) {
        this.mcontext = mcontext;
        this.mproductCards = mproductCards;
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
        Glide.with(mcontext)
                .load(productCard.getImg())
                .into(holder.iv);
        holder.title.setText(productCard.getTitle());
        String txt=productCard.getPrice()+" vnd";
        holder.price.setText(txt);

    }

    @Override
    public int getItemCount() {
        return mproductCards.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView price;
        ImageView iv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.iv_home_cardproduct);
            price = itemView.findViewById(R.id.tv_price_productcard);
            title = itemView.findViewById(R.id.tv_title_productcard);
        }
    }
}
