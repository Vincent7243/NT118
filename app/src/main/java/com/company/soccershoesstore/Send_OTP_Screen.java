package com.company.soccershoesstore;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Send_OTP_Screen extends AppCompatActivity {
    EditText et;
    Button btn;
    String otp,email,password;
    ProgressBar progressBar;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_otp_screen);
        mAuth = FirebaseAuth.getInstance();
        et=findViewById(R.id.et_sendotp_otp);
        progressBar=findViewById(R.id.pb_sendotp);
        btn=findViewById(R.id.btn_sendotp_verify);
        otp=getIntent().getStringExtra("otp");
        email=getIntent().getStringExtra("email");
        password=getIntent().getStringExtra("password");

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(otp.equals(et.getText().toString())) {
                    Signupemialpassword(email,password);
                } else {
                    Toast.makeText(getApplicationContext(),"OTP is invalid!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void Signupemialpassword(String email,String password) {
        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
//                                        Log.d(TAG, "createUserWithEmail:success");
                            Toast.makeText(Send_OTP_Screen.this, "Sign up successful!",
                                    Toast.LENGTH_SHORT).show();
                            FirebaseAuth.getInstance().signOut();
                            progressBar.setVisibility(View.GONE);
                            Intent intent = new Intent(Send_OTP_Screen.this, LoginScreen.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            progressBar.setVisibility(View.GONE);

                            Toast.makeText(Send_OTP_Screen.this, task.getException().toString(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}