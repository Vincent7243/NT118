package com.company.soccershoesstore;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ShooesAdapter extends RecyclerView.Adapter<ShooesAdapter.ShooesViewHolder>{

    private List<Shooes> sListShooes;

    public ShooesAdapter(List<Shooes> sListShooes) {
        this.sListShooes = sListShooes;
    }

    @NonNull
    @Override
    public ShooesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shooes,parent,false);
        return new ShooesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShooesViewHolder holder, int position) {
        Shooes shooes = sListShooes.get(position);
        if(shooes == null){
            return;
        }
        holder.imgShooes.setImageResource(shooes.getImage());
        holder.tvNameFood.setText(shooes.getName());
    }

    @Override
    public int getItemCount() {
        if(sListShooes != null){
            return sListShooes.size();
        }
        return 0;
    }

    public class ShooesViewHolder extends RecyclerView.ViewHolder{

        private ImageView imgShooes;
        private TextView tvNameFood;
        public ShooesViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
