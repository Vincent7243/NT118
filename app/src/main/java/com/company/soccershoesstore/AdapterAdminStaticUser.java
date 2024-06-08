package com.company.soccershoesstore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class AdapterAdminStaticUser extends ArrayAdapter<String> {
    Context mcontext;
    public AdapterAdminStaticUser(@NonNull Context context, int resource, @NonNull List<String> objects) {
        super(context, resource, objects);
        this.mcontext=context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView==null) {
            convertView= LayoutInflater.from(mcontext).inflate(R.layout.item_admin_static_lv_user,null,false);
        }
        String mid=getItem(position);
        TextView tv_id=convertView.findViewById(R.id.tv_item_admin_static_id_user);
        TextView tv_name=convertView.findViewById(R.id.tv_item_admin_static_name_user);
        TextView tv_total=convertView.findViewById(R.id.tv_item_admin_static_total_user);
        tv_id.setText(mid);
        getname(tv_name,mid);
        gettotal(tv_total,mid);
        return convertView;
    }
    public void getname(TextView tv,String mid) {
        FirebaseFirestore.getInstance().collection("users").document(mid)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        tv.setText(documentSnapshot.get("name").toString());
                    }
                });
    }
    public void gettotal(TextView tv,String mid) {

        FirebaseFirestore.getInstance().collection("bills")
                .whereEqualTo("id_user",mid)
                .whereEqualTo("status","2")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        Long total= 0L;
                        for(DocumentSnapshot documentSnapshot:queryDocumentSnapshots) {
                            total+=Long.parseLong(documentSnapshot.get("total").toString());
                        }
                        tv.setText(CardProductAdapter.formatCurrency(total.toString()));
                    }
                });
    }
}
