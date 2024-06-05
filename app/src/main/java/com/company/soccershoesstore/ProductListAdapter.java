package com.company.soccershoesstore;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder> {
    private Context mcontext;
    private ArrayList<Product> mproducts;
    FirebaseFirestore db;
    FirebaseStorage storage;

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
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Product product=mproducts.get(position);
        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mcontext.getApplicationContext(),activity_admin_product_edit.class);
                intent.putExtra("mid",product.getMid());
                intent.putExtra("mname",product.getMname());
                intent.putExtra("mimage",product.getMimage());
                intent.putExtra("mdescription",product.getMdescription());
                intent.putExtra("mbrand",product.getMbrand());
                intent.putExtra("mprice",product.getMprice());

                mcontext.startActivity(intent);
            }
        });

        holder.tv_name.setText(product.getMname());
        holder.tv_brand.setText(product.getMbrand());
        holder.tv_price.setText(CardProductAdapter.formatCurrency(product.getMprice()));
        db=FirebaseFirestore.getInstance();
         storage = FirebaseStorage.getInstance("gs://nt118-6829d.appspot.com");
        StorageReference storageRef = storage.getReferenceFromUrl(product.getMimage());
        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String res= uri.toString();
                Log.d("imgaefirebase","thành cong");

                Glide.with(mcontext)
                        .load(res)
                        .into(holder.iv);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
               Toast.makeText(mcontext,e.toString(),Toast.LENGTH_SHORT).show();
                Log.d("imgaefirebase",e.getMessage());
            }
        });
        holder.ib_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mcontext.getApplicationContext(),activity_admin_product_edit.class);
                intent.putExtra("mid",product.getMid());
                intent.putExtra("mname",product.getMname());
                intent.putExtra("mimage",product.getMimage());
                intent.putExtra("mdescription",product.getMdescription());
                intent.putExtra("mbrand",product.getMbrand());
                intent.putExtra("mprice",product.getMprice());

                mcontext.startActivity(intent);
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
                                deleteImage(product.getMimage(),product.getMid());

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
LinearLayout ll;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        iv=itemView.findViewById(R.id.iv_admin_product_item_image);
        tv_name=itemView.findViewById(R.id.tv_admin_product_item_name);
        tv_brand=itemView.findViewById(R.id.tv_admin_product_item_brand);
        tv_price=itemView.findViewById(R.id.tv_admin_product_item_price);
        ib_delete=itemView.findViewById(R.id.ib_admin_product_item_delete);
        ib_edit=itemView.findViewById(R.id.ib_admin_product_item_edit);
        ll=itemView.findViewById(R.id.ii_admin_product_rowproduct);
    }
}
    public void deletProduct(String iid) {
        db.collection("Products").document(iid)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("deleteProduct", "DocumentSnapshot successfully deleted!");
                        if (mListener != null) {
                            mListener.onProductDeleted();
                            Toast.makeText(mcontext,"Delete successful!",Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("deleteProduct", "Error deleting document", e);
                        Toast.makeText(mcontext,"Delete failed!!",Toast.LENGTH_SHORT).show();
                    }
                });
    }
    public void deleteImage(String img,String iid) {
        StorageReference storageRef = storage.getReferenceFromUrl(img);
        storageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("deleteimage","delete image sucessfull");
                deletProduct(iid);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Uh-oh, an error occurred!
                Log.d("deleteimage","delete image failed+ "+exception);

            }
        });
    }
    public interface OnProductDeleteListener {
        void onProductDeleted();
    }

    private OnProductDeleteListener mListener;

    public void setOnProductDeleteListener(OnProductDeleteListener listener) {
        this.mListener = listener;
    }
}
