package com.company.soccershoesstore;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class AdminChatFragment extends Fragment {
    ListView lv;
    private DatabaseReference databaseReference;
    private FirebaseFirestore db;
    ArrayList<String> names;
    AdminChatAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_admin_chat, container, false);
        lv=view.findViewById(R.id.lv_admin_chatlist);
        databaseReference= FirebaseDatabase.getInstance().getReference().child("messages");

        db=FirebaseFirestore.getInstance();
        names=new ArrayList<String>();
        adapter=new AdminChatAdapter(getContext(), R.layout.admin_item_chat_list,names);
        lv.setAdapter(adapter);
        databaseReference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()) {

                    for(DataSnapshot idname:task.getResult().getChildren()) {
                        names.add(idname.getKey());
                        adapter.notifyDataSetChanged();
//                        Log.d("chadmin",idname.toString());
//                        db.collection("users").document(idname.getKey()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                            @Override
//                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                                DocumentSnapshot user=task.getResult();
//                                if(user.exists()) {
////                                    Log.d("chadmin",user.get("name").toString());
//                                    names.add(user.get("name").toString());
//                                    adapter.notifyDataSetChanged();
//
//                                }else {
//                                    Toast.makeText(getContext(), "User don't exist", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        });
                    }

//                    String res=task.getResult().getKey().toString();
//                    Log.d("chadmin",res);
                }else {
                    Toast.makeText(getContext(),"Some error!",Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
}