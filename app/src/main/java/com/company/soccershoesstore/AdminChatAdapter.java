package com.company.soccershoesstore;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AdminChatAdapter extends ArrayAdapter<String> {
    private DatabaseReference databaseReference;
    private FirebaseFirestore db;

    public AdminChatAdapter(@NonNull Context context, int resource, @NonNull List<String> objects) {
        super(context, resource, objects);
        databaseReference= FirebaseDatabase.getInstance().getReference().child("messages");
        db=FirebaseFirestore.getInstance();
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.admin_item_chat_list, parent, false);
        }
        String iid=getItem(position);
        TextView tvname=convertView.findViewById(R.id.tv_admin_chatlist_item_name);
        TextView tvlastmessage=convertView.findViewById(R.id.tv_admin_chatlist_item_lastmessage);
        TextView tvtime=convertView.findViewById(R.id.tv_admin_chatlist_item_time);
        LinearLayout ll=convertView.findViewById(R.id.ll_item_chatlist_admin);
        savename(iid,tvname);
        savelastmessage(iid,tvlastmessage,tvtime);
        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),ChatActivity.class);
                intent.putExtra("mid",iid);
                intent.putExtra("mname",tvname.getText().toString());
                getContext().startActivity(intent);

            }
        });
        return convertView;
    }
    public void savename(String mid,TextView tv) {
        db.collection("users").document(mid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot user=task.getResult();
                   String res=user.get("name").toString();
                    tv.setText(res);
                }
            }
        });
    }
    public void savelastmessage(String mid,TextView tv,TextView tv_ti) {
        databaseReference.child(mid).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()) {
//                    Log.d("itemchatadmin",task.getResult().getValue().toString());
                    Message message=new Message("","",0);
                    for (DataSnapshot resdata:task.getResult().getChildren()) {
                         message=resdata.getValue(Message.class);
                    }
                    tv.setText(message.getText());
                    Date date = new Date(message.getTimestamp());
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                    String formattedDate = dateFormat.format(date);
                    tv_ti.setText(formattedDate);
                }else {
                    Toast.makeText(getContext(), "some error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
