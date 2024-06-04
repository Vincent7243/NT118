package com.company.soccershoesstore;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private ListView listViewMessages;
    private EditText editTextMessage;
    private ImageButton ib_send;
    LinearLayout ll;
    private MessageAdapter messageAdapter;
    private MessageAdminAdapter messageAdminAdapter;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private List<Message> messages;
    private String miduser;
    private ProgressBar pb;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Toolbar toolbar = findViewById(R.id.chat_toolbar);
        setSupportActionBar(toolbar);

        Intent intent=getIntent();
        if(!intent.hasExtra("mid")) {
            // Hiển thị nút back và thay đổi tiêu đề
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
                getSupportActionBar().setTitle("Chat"); // Đặt tiêu đề là "Chat"
            }
            listViewMessages=findViewById(R.id.listViewMessages);
            editTextMessage=findViewById(R.id.editTextMessage);
            ib_send=findViewById(R.id.ib_send_message);
            mAuth = FirebaseAuth.getInstance();
            currentUser = mAuth.getCurrentUser();
            pb=findViewById(R.id.pb_chat);
            ll=findViewById(R.id.ll_chat);

            miduser=currentUser.getUid();
//            miduser=intent.getStringExtra("mid");
            databaseReference= FirebaseDatabase.getInstance().getReference().child("messages");
            messages=new ArrayList<Message>();


            messageAdapter=new MessageAdapter(ChatActivity.this,messages);
            listViewMessages.setAdapter(messageAdapter);
            ib_send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Message message=new Message(editTextMessage.getText().toString(),miduser,System.currentTimeMillis());
                    databaseReference.child(miduser).child(System.currentTimeMillis()+"s").setValue(message);
                    editTextMessage.setText("");
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(editTextMessage.getWindowToken(), 0);
                    }
                }
            });
            ll.setVisibility(View.GONE);
            pb.setVisibility(View.VISIBLE);
            databaseReference.child(miduser).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//              Log.d("rtdb",dataSnapshot.toString());
                    messages.clear();
                    for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()) {
//                  Message m1=dataSnapshot1.getValue(Message.class);
                        messages.add(dataSnapshot1.getValue(Message.class));
                        Log.d("rtdb",dataSnapshot1.toString());

                    }
                    messageAdapter.notifyDataSetChanged();
                    ll.setVisibility(View.VISIBLE);
                    pb.setVisibility(View.GONE);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.d("rtdb",databaseError.toString());
                    Toast.makeText(ChatActivity.this,"Some error",Toast.LENGTH_SHORT).show();
                    ll.setVisibility(View.VISIBLE);
                    pb.setVisibility(View.GONE);
                }
            });

        } else {
            String nameuser=intent.getStringExtra("mname");
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
                getSupportActionBar().setTitle(intent.getStringExtra("mname")); // Đặt tiêu đề là "Chat"
            }
            listViewMessages=findViewById(R.id.listViewMessages);
            editTextMessage=findViewById(R.id.editTextMessage);
            ib_send=findViewById(R.id.ib_send_message);
            mAuth = FirebaseAuth.getInstance();
            currentUser = mAuth.getCurrentUser();
            pb=findViewById(R.id.pb_chat);
            ll=findViewById(R.id.ll_chat);

//            miduser=currentUser.getUid();
            miduser=intent.getStringExtra("mid");
            databaseReference= FirebaseDatabase.getInstance().getReference().child("messages");
            messages=new ArrayList<Message>();


            messageAdminAdapter=new MessageAdminAdapter(ChatActivity.this,messages,nameuser);
            listViewMessages.setAdapter(messageAdminAdapter);
            ib_send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Message message=new Message(editTextMessage.getText().toString(),"0ORnvYdXD6damu7SPJei6WDYTsm1",System.currentTimeMillis());
                    databaseReference.child(miduser).child(System.currentTimeMillis()+"s").setValue(message);
                    editTextMessage.setText("");
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(editTextMessage.getWindowToken(), 0);
                    }
                }
            });
            ll.setVisibility(View.GONE);
            pb.setVisibility(View.VISIBLE);
            databaseReference.child(miduser).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//              Log.d("rtdb",dataSnapshot.toString());
                    messages.clear();
                    for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()) {
//                  Message m1=dataSnapshot1.getValue(Message.class);
                        messages.add(dataSnapshot1.getValue(Message.class));
                        Log.d("rtdb",dataSnapshot1.toString());

                    }
                    messageAdminAdapter.notifyDataSetChanged();
                    ll.setVisibility(View.VISIBLE);
                    pb.setVisibility(View.GONE);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.d("rtdb",databaseError.toString());
                    Toast.makeText(ChatActivity.this,"Some error",Toast.LENGTH_SHORT).show();
                    ll.setVisibility(View.VISIBLE);
                    pb.setVisibility(View.GONE);
                }
            });
        }




    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Xử lý sự kiện khi người dùng nhấn nút back
        if (item.getItemId() == android.R.id.home) {
            finish(); // Đóng activity hiện tại và quay về activity trước đó
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
