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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdapterAdminBill extends ArrayAdapter<BillInfo> {
    Context mcontext;
    ArrayList<BillInfo> billInfos;
    String status;
    private OnApproveButtonClickListener listener;

    public AdapterAdminBill(@NonNull Context context, int resource, @NonNull List<BillInfo> objects, OnApproveButtonClickListener listener) {
        super(context, resource, objects);
        this.mcontext = context;
        this.billInfos = (ArrayList<BillInfo>) objects;
        this.listener = listener;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mcontext).inflate(R.layout.item_admin_bill_new, null, false);
        }
        Log.d("testlistview",parent.getContext().toString());
        BillInfo billInfo = billInfos.get(position);
        status=billInfo.getStatus();

        TextView tvid = convertView.findViewById(R.id.tv_admin_bill_new_idbill);
        TextView tvaddress = convertView.findViewById(R.id.tv_admin_bill_new_address);
        TextView tvtotal = convertView.findViewById(R.id.tv_admin_bill_new_total);
        TextView tvname = convertView.findViewById(R.id.tv_admin_bill_new_nameuser);
        TextView tvemail = convertView.findViewById(R.id.tv_admin_bill_new_email);
        TextView tvphone = convertView.findViewById(R.id.tv_admin_bill_new_phone);
        Button btnDetail = convertView.findViewById(R.id.btn_admin_bill_item_new_detail);
        Button btnApprove = convertView.findViewById(R.id.btn_admin_bill_item_approve);
if(status.equals("0")) {
    btnApprove.setText("Approve");
    btnApprove.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Map<String, Object> docData = new HashMap<>();
            docData.put("id_user", billInfo.getIdUser());
            docData.put("status", "1");
            docData.put("total", billInfo.getTotal());

            FirebaseFirestore.getInstance().collection("bills").document(billInfo.getIdBill())
                    .set(docData)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            if (listener != null) {
                                listener.onApproveButtonClick();
                            }
                        }
                    });

        }
    });
} else if(status.equals("1")) {
    btnApprove.setText("Recieve");
    btnApprove.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Map<String, Object> docData = new HashMap<>();
            docData.put("id_user", billInfo.getIdUser());
            docData.put("status", "2");
            docData.put("total", billInfo.getTotal());

            FirebaseFirestore.getInstance().collection("bills").document(billInfo.getIdBill())
                    .set(docData)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            if (listener != null) {
                                listener.onApproveButtonClick();
                            }
                        }
                    });

        }
    });

}else {
    btnApprove.setVisibility(View.GONE);
}
        btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mcontext.getApplicationContext(), Admin_detail_bill.class);
                intent.putExtra("idbill", billInfo.getIdBill());
                intent.putExtra("total", billInfo.getTotal());
                mcontext.startActivity(intent);
            }
        });



        tvid.setText(billInfo.getIdBill());
        tvtotal.setText(CardProductAdapter.formatCurrency(billInfo.getTotal()));
        getUserInfo(billInfo.getIdUser(), tvaddress, tvname, tvemail, tvphone);

        return convertView;
    }

    public void getUserInfo(String iduser, TextView address, TextView name, TextView email, TextView phone) {
        FirebaseFirestore.getInstance().collection("users").document(iduser)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot documentSnapshot = task.getResult();
                        address.setText(documentSnapshot.get("address").toString());
                        name.setText(documentSnapshot.get("name").toString());
                        email.setText(documentSnapshot.get("email").toString());
                        phone.setText(documentSnapshot.get("phonenum").toString());
                    }
                });
    }

    public void filterList(ArrayList<BillInfo> bills) {
        billInfos = bills;
        notifyDataSetChanged();
    }

    public interface OnApproveButtonClickListener {
        public void onApproveButtonClick();
    }

    @Override
    public int getCount() {
        return billInfos.size();
    }
}
