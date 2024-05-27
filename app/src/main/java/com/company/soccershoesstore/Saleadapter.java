package com.company.soccershoesstore;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class Saleadapter extends ArrayAdapter<Sale> {
    private Context mcontext;

    public Saleadapter(@NonNull Context context, int resource, @NonNull List<Sale> objects) {
        super(context, resource, objects);
        this.mcontext = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView =
                    LayoutInflater.from(mcontext).inflate(R.layout.item_sale, null,
                            false);
        }
        Sale sale=getItem(position);
        TextView quan=convertView.findViewById(R.id.tv_admin_sale_item_quantity);
        TextView code=convertView.findViewById(R.id.tv_admin_sale_item_code);
        TextView price=convertView.findViewById(R.id.tv_admin_sale_item_price);
        ImageButton ib_edit=convertView.findViewById(R.id.ib_admin_sale_item_edit);
        ImageButton ib_delete=convertView.findViewById(R.id.ib_admin_sale_item_delete);
        quan.setText(sale.getMquantity());
        code.setText(sale.getMcode());
        price.setText("-"+sale.getMprice()+"vnd");
        ib_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mcontext, "edit"+position, Toast.LENGTH_SHORT).show();
            }
        });
        ib_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mcontext, "delete"+position, Toast.LENGTH_SHORT).show();

            }
        });
        return convertView;
    }
}
