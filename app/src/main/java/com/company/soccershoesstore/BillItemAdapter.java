package com.company.soccershoesstore;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BillItemAdapter extends RecyclerView.Adapter<BillItemAdapter.BillItemViewHolder> {
    private List<BillItem> billItemList;

    public BillItemAdapter(List<BillItem> billItemList) {
        this.billItemList = billItemList;
    }

    @NonNull
    @Override
    public BillItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bill_item_view, parent, false);
        return new BillItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BillItemViewHolder holder, int position) {
        BillItem billItem = billItemList.get(position);
        holder.idBillTextView.setText(billItem.getId_bill());
        holder.quanTextView.setText(billItem.getQuan());
        holder.totalTextView.setText(billItem.getTotal());
    }

    @Override
    public int getItemCount() {
        return billItemList.size();
    }

    public static class BillItemViewHolder extends RecyclerView.ViewHolder {
        TextView idBillTextView, quanTextView, totalTextView;

        public BillItemViewHolder(@NonNull View itemView) {
            super(itemView);
            idBillTextView = itemView.findViewById(R.id.idBillTextView);
            quanTextView = itemView.findViewById(R.id.quanTextView);
            totalTextView = itemView.findViewById(R.id.totalTextView);
        }
    }
}
