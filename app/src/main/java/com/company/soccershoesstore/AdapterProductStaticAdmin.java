package com.company.soccershoesstore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class AdapterProductStaticAdmin extends ArrayAdapter<ItemProductStaticAdmin> {
    Context mcontext;

    public AdapterProductStaticAdmin(@NonNull Context context, int resource, @NonNull List<ItemProductStaticAdmin> objects) {
        super(context, resource, objects);
        this.mcontext=context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView==null) {
            convertView= LayoutInflater.from(mcontext).inflate(R.layout.item_admin_static_product,null,false);
        }
        ItemProductStaticAdmin itemProductStaticAdmin=getItem(position);
        TextView tv_id=convertView.findViewById(R.id.tv_item_admin_static_product);
        TextView tv_num=convertView.findViewById(R.id.tv_item_admin_static_product_num);
        tv_id.setText(itemProductStaticAdmin.getMid());
        tv_num.setText(itemProductStaticAdmin.num+"");
        return convertView;
    }
}
