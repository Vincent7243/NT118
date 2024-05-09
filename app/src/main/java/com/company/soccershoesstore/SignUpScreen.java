package com.company.soccershoesstore;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpScreen extends AppCompatActivity {
    EditText etnanme,etemail,etpassword,etconfirmpassword;
    TextView tv_toLogin;
    Button btn_signup;
    ProgressBar progressBar;
     FirebaseAuth mAuth;
//    @Override
//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if(currentUser != null){
//            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
//            startActivity(intent);
//            finish();
//        }
//    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_screen);
        mAuth = FirebaseAuth.getInstance();
        etnanme=findViewById(R.id.et_signup_name);
        etemail=findViewById(R.id.et_login_email);
        etpassword=findViewById(R.id.et_login_pasword);
        etconfirmpassword=findViewById(R.id.et_signup_confirmpassword);
        tv_toLogin=findViewById(R.id.tv_login_toSignup);
        btn_signup=findViewById(R.id.btn_login);
        progressBar=findViewById(R.id.signup_pbar);
        tv_toLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUpScreen.super.onBackPressed();
            }
        });


        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String name,email,password,confirmpassword;
                name=String.valueOf(etnanme.getText());
                email=String.valueOf(etemail.getText());
                password=String.valueOf(etpassword.getText());
                confirmpassword=String.valueOf(etconfirmpassword.getText());
                if(TextUtils.isEmpty(name)) {
                    Toast.makeText(SignUpScreen.this,"Please fill name",Toast.LENGTH_SHORT).show();
                    return;
                }else if(TextUtils.isEmpty(email)) {
                    Toast.makeText(SignUpScreen.this,"Please fill email",Toast.LENGTH_SHORT).show();
                    return;

                }else if(TextUtils.isEmpty(password)) {
                    Toast.makeText(SignUpScreen.this,"Please fill password",Toast.LENGTH_SHORT).show();
                    return;

                }else if(TextUtils.isEmpty(confirmpassword)) {
                    Toast.makeText(SignUpScreen.this,"Please fill confirm password",Toast.LENGTH_SHORT).show();
                    return;

                }else if(!password.equals(confirmpassword)) {
                    Toast.makeText(SignUpScreen.this,"Confirm password is incorrect",Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                    // Kiểm tra chuỗi email có khớp với biểu thức chính quy không
                    if(email.matches(emailPattern)) {
                        Toast.makeText(getApplicationContext(), "Valid email format", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(),Send_OTP_Screen.class);
                        startActivity(intent);
                    }
                    else {
                        // Nếu không đúng định dạng, hiển thị Toast thông báo
                        Toast.makeText(getApplicationContext(), "Invalid email format", Toast.LENGTH_SHORT).show();
                    }

//                    mAuth.createUserWithEmailAndPassword(email, password)
//                            .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
//                                @Override
//                                public void onComplete(@NonNull Task<AuthResult> task) {
//                                    progressBar.setVisibility(View.GONE);
//
//                                    if (task.isSuccessful()) {
//                                        // Sign in success, update UI with the signed-in user's information
////                                        Log.d(TAG, "createUserWithEmail:success");
//                                        Toast.makeText(SignUpScreen.this, "Sign up successful!",
//                                                Toast.LENGTH_SHORT).show();
//                                        SignUpScreen.super.onBackPressed();
////                                        FirebaseUser user = mAuth.getCurrentUser();
//                                        FirebaseAuth.getInstance().signOut();
//
//
//                                    } else {
//                                        // If sign in fails, display a message to the user.
//                                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
//                                        Toast.makeText(SignUpScreen.this, "Sign up failed.",
//                                                Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//                            });
                }
            }
        });

    }
}