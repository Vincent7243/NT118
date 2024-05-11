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

import com.company.soccershoesstore.util.Appdata;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Properties;
import java.util.Random;

import javax.activation.MimeType;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SignUpScreen extends AppCompatActivity {
    EditText etnanme,etemail,etpassword,etconfirmpassword;
    TextView tv_toLogin;
    Button btn_signup;
    ProgressBar progressBar;
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
                        String otp=sendotpmail(email);
                        Toast.makeText(getApplicationContext(), "Please check "+email, Toast.LENGTH_SHORT).show();
                        gotoSendotp(otp,email,password);
                    }
                    else {
                        // Nếu không đúng định dạng, hiển thị Toast thông báo
                        Toast.makeText(getApplicationContext(), "Invalid email format", Toast.LENGTH_SHORT).show();
                    }


                }
            }
        });

    }
private String sendotpmail(String email) {
    int length = 6;
    // Dãy ký tự mà mã OTP có thể chứa
    String numbers = "0123456789";
    // Random object
    Random random = new Random();
    // StringBuilder để xây dựng mã OTP
    StringBuilder motp = new StringBuilder();

    // Tạo mã OTP bằng cách chọn ngẫu nhiên các ký tự từ dãy ký tự
    for (int i = 0; i < length; i++) {
        motp.append(numbers.charAt(random.nextInt(numbers.length())));
    }
    String otp=motp.toString();
    Properties properties=System.getProperties();
    properties.put("mail.smtp.host", Appdata.Gmail_host);
    properties.put("mail.smtp.port","465");
    properties.put("mail.smtp.ssl.enable","true");
    properties.put("mail.smtp.auth","true");
    javax.mail.Session session= Session.getInstance(properties, new Authenticator() {
        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(Appdata.Sender_email_address,Appdata.Sender_email_password);
        }
    });
    MimeMessage message=new MimeMessage(session);
    try{
        message.addRecipient(Message.RecipientType.TO,new InternetAddress(email));
        message.setSubject("Verification in Soccer Shoes Shop");
        message.setText("Here is your OTP: "+otp);
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Transport.send(message);
                } catch (MessagingException e) {
                    Log.e("er send",e.toString());
                    Toast.makeText(getApplicationContext(), "er send:"+e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        thread.start();

    } catch (MessagingException e) {
        Log.e("er send",e.toString());
        Toast.makeText(getApplicationContext(), "er send:"+e.toString(), Toast.LENGTH_SHORT).show();

    }
    return otp;
}
private void gotoSendotp(String otp,String email,String password) {
        Intent intent=new Intent(SignUpScreen.this, Send_OTP_Screen.class);
        intent.putExtra("otp",otp);
        intent.putExtra("email",email);
        intent.putExtra("password",password);
        startActivity(intent);
}

}