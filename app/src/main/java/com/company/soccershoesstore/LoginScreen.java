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

public class LoginScreen extends AppCompatActivity {

    EditText et_email,et_password;
    TextView tv_forgotpass,tv_signup;
    Button btn;
    ProgressBar pbar;
    FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            gotomain();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        mAuth = FirebaseAuth.getInstance();
        et_email=findViewById(R.id.et_login_email);
        et_password=findViewById(R.id.et_login_pasword);
        tv_forgotpass=findViewById(R.id.tv_login_forgotpassword);
        tv_signup=findViewById(R.id.tv_login_toSignup);
        pbar=findViewById(R.id.login_pbar);
        btn=findViewById(R.id.btn_login);
        tv_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),SignUpScreen.class);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                startActivity(intent);
//                finish();
            }
        });
        tv_forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ForgotPasswordScreen.class);
                startActivity(intent);

            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pbar.setVisibility(View.VISIBLE);
                String email,password;
                email=String.valueOf(et_email.getText());
                password=String.valueOf(et_password.getText());
                if(TextUtils.isEmpty(email)) {
                    Toast.makeText(LoginScreen.this,"Please fill email",Toast.LENGTH_SHORT).show();
                    return;
                }else if(TextUtils.isEmpty(password)) {
                    Toast.makeText(LoginScreen.this,"Please fill password",Toast.LENGTH_SHORT).show();
                    return;

                } else {
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    pbar.setVisibility(View.GONE);

                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
//                                        Log.d(TAG, "signInWithEmail:success");
                                        Toast.makeText(LoginScreen.this, "Login Successful.",
                                                Toast.LENGTH_SHORT).show();
                                        gotomain();
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                                        Toast.makeText(LoginScreen.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
//                                        updateUI(null);
                                    }
                                }
                            });
                }
            }
        });
    }
    private void gotomain() {
        Intent intent=new Intent(getApplicationContext(), BottomNavigationBar.class);
        startActivity(intent);
        finish();
    }
}