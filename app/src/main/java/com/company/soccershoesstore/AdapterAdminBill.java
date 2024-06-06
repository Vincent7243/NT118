package com.company.soccershoesstore;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class AdapterAdminBill extends ArrayAdapter<BillInfo> {
    Context mcontext;
    ArrayList<BillInfo> billInfos;

    public AdapterAdminBill(@NonNull Context context, int resource, @NonNull List<BillInfo> objects) {
        super(context, resource, objects);
        this.mcontext = context;
        this.billInfos= (ArrayList<BillInfo>) objects;
    }

    @Override
    public int getCount() {
        return billInfos.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView =
                    LayoutInflater.from(mcontext).inflate(R.layout.item_admin_bill_new, null,
                            false);
        }

        BillInfo billInfo=billInfos.get(position);
        TextView tvid=convertView.findViewById(R.id.tv_admin_bill_new_idbill);
        TextView tvaddress=convertView.findViewById(R.id.tv_admin_bill_new_address);
        TextView tvtotal=convertView.findViewById(R.id.tv_admin_bill_new_total);
        TextView tvname=convertView.findViewById(R.id.tv_admin_bill_new_nameuser);
        TextView tvemail=convertView.findViewById(R.id.tv_admin_bill_new_email);
        TextView tvphone=convertView.findViewById(R.id.tv_admin_bill_new_phone);
        Button btnDetail=convertView.findViewById(R.id.btn_admin_bill_item_new_detail);
        Button btnApprove=convertView.findViewById(R.id.btn_admin_bill_item_approve);
        btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(mcontext.getApplicationContext(), Admin_detail_bill.class);
                intent.putExtra("idbill",billInfo.getIdBill());
                intent.putExtra("total",billInfo.getTotal());
                mcontext.startActivity(intent);
            }
        });
        btnApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mcontext,"approve", Toast.LENGTH_LONG).show();

            }
        });
        tvid.setText(billInfo.getIdBill());
        tvtotal.setText(CardProductAdapter.formatCurrency(billInfo.getTotal()));
        getUserInfo(billInfo.getIdUser(),tvaddress,tvname,tvemail,tvphone);

        return convertView;
    }
    public void getUserInfo(String iduser,TextView address, TextView name,TextView email, TextView phone) {

        FirebaseFirestore.getInstance().collection("users").document(iduser)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot documentSnapshot=task.getResult();
//                        Log.d("getinfouser","addressv"+documentSnapshot.get("address").toString());
//                        Log.d("getinfouser","email "+documentSnapshot.get("email").toString());
//                        Log.d("getinfouser","name "+documentSnapshot.get("name").toString());
//                        Log.d("getinfouser","phonenum "+documentSnapshot.get("phonenum").toString());
                        address.setText(documentSnapshot.get("address").toString());
                        name.setText(documentSnapshot.get("name").toString());
                        email.setText(documentSnapshot.get("email").toString());
                        phone.setText(documentSnapshot.get("phonenum").toString());
                    }
                });
    }
    public void filterList(ArrayList<BillInfo> bills) {
        billInfos=bills;
        notifyDataSetChanged();
    }
}
