package com.company.soccershoesstore;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.Image;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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
        holder.tv_name.setText(product.getMname());
        holder.tv_brand.setText(product.getMbrand());
        holder.tv_price.setText(product.getMprice()+"vnd");
        FirebaseStorage storage = FirebaseStorage.getInstance("gs://nt118-6829d.appspot.com");
        StorageReference storageRef = storage.getReferenceFromUrl(product.getMimage());
        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String res= uri.toString();
                Glide.with(mcontext)
                        .load(res)
                        .into(holder.iv);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
               Toast.makeText(mcontext,e.toString(),Toast.LENGTH_SHORT).show();

            }
        });
        holder.ib_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mcontext,""+position, Toast.LENGTH_SHORT).show();
            }
        });
        holder.ib_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(mcontext)
                        .setTitle("Delete confirm")
                        .setMessage("Are you sure you want to delete this product?")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Continue with delete operation
                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(R.drawable.warning)
                        .show();
            }
        });
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
        ib_delete=itemView.findViewById(R.id.ib_admin_product_item_delete);
        ib_edit=itemView.findViewById(R.id.ib_admin_product_item_edit);
    }
}
}
