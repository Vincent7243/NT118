package com.company.soccershoesstore;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

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
        price.setText("-"+CardProductAdapter.formatCurrency(sale.getMprice()));
        ib_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mcontext.getApplicationContext(), activity_admin_sale_edit.class);
                intent.putExtra("iid",sale.getMid());
                intent.putExtra("mcode",sale.getMcode());
                intent.putExtra("mprice",sale.getMprice());
                intent.putExtra("mquantity",sale.getMquantity());
                mcontext.startActivity(intent);
            }
        });
        ib_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(mcontext, "delete"+position, Toast.LENGTH_SHORT).show();
                new AlertDialog.Builder(mcontext)
                        .setTitle("Delete confirm")
                        .setMessage("Are you sure you want to delete this Sale?")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                deleteSale(sale.getMid());

                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(R.drawable.warning)
                        .show();

            }
        });
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mcontext.getApplicationContext(), activity_admin_sale_edit.class);
                intent.putExtra("iid",sale.getMid());
                intent.putExtra("mcode",sale.getMcode());
                intent.putExtra("mprice",sale.getMprice());
                intent.putExtra("mquantity",sale.getMquantity());
                mcontext.startActivity(intent);
            }
        });
        return convertView;
    }
    public interface OnSaleDeleteListener {
        void onSaleDeleted();
    }

    private OnSaleDeleteListener mListener;

    public void setOnSaleDeleteListener(OnSaleDeleteListener listener) {
        this.mListener = listener;
    }
    public void deleteSale(String iid) {
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        db.collection("sales").document(iid)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("delete sale", "DocumentSnapshot successfully deleted!");


                        if (mListener != null) {
                            mListener.onSaleDeleted();}


                        Toast.makeText(mcontext, "delete sucessful", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("delete sale", "Error deleting document", e);
                        Toast.makeText(mcontext, "delete failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
