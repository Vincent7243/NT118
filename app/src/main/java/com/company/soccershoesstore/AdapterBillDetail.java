package com.company.soccershoesstore;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

import java.util.List;
import java.util.zip.Inflater;

public class AdapterBillDetail extends ArrayAdapter<BillDetail> {
    Context mcontext;

    public AdapterBillDetail(@NonNull Context context, int resource, @NonNull List<BillDetail> objects) {
        super(context, resource, objects);
        mcontext=context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView==null) {
            convertView= LayoutInflater.from(mcontext).inflate(R.layout.item_admin_bill_detail,null,false);
        }
        BillDetail billDetail=getItem(position);
        TextView tvbrand=convertView.findViewById(R.id.tv_admin_bill_detail_item_brand);
        TextView tvname=convertView.findViewById(R.id.tv_admin_bill_detail_item_name);
        TextView tvprice=convertView.findViewById(R.id.tv_admin_bill_detail_item_price);
        TextView tvquan=convertView.findViewById(R.id.tv_admin_bill_detail_item_quantity);
        TextView tvtotal=convertView.findViewById(R.id.tv_admin_bill_detail_item_total);
        ImageView iv=convertView.findViewById(R.id.iv_admin_bill_detail_item);
        tvquan.setText("Quantity: "+billDetail.getQuantity());
        tvtotal.setText("Total: "+CardProductAdapter.formatCurrency(billDetail.getTotal()));
        getInfoProduct(billDetail.getIdProduct(), tvbrand,tvname,tvprice,iv);

        return convertView;
    }
    public void getInfoProduct(String idproduct, TextView brand, TextView name, TextView price, ImageView iv) {
        FirebaseFirestore.getInstance().collection("Products").document(idproduct)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot documentSnapshot=task.getResult();
//                        Log.d("testgetproduct",idproduct);
                        brand.setText(documentSnapshot.get("brand").toString());
                        name.setText(documentSnapshot.get("name").toString());
                        price.setText(CardProductAdapter.formatCurrency(documentSnapshot.get("price").toString()));
                        getImage(documentSnapshot.get("image").toString(),iv);

                    }
                });
    }
    public void getImage(String source, ImageView iv) {
        StorageReference storageReference=FirebaseStorage.getInstance().getReferenceFromUrl(source);
        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(mcontext)
                        .load(uri)
                        .into(iv);
            }
        });

    }
}
