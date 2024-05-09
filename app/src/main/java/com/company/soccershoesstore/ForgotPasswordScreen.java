package com.company.soccershoesstore;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ForgotPasswordScreen extends AppCompatActivity {

    private EditText editTextEmail;
    private Button buttonSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_screen);

        editTextEmail = findViewById(R.id.editTextEmail);
        buttonSend = findViewById(R.id.buttonSend);

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString().trim();

                // Biểu thức chính quy để kiểm tra định dạng email
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                // Kiểm tra chuỗi email có khớp với biểu thức chính quy không
                if(email.matches(emailPattern)) {
                    Toast.makeText(getApplicationContext(), "Valid email format", Toast.LENGTH_SHORT).show();
                }
                else {
                    // Nếu không đúng định dạng, hiển thị Toast thông báo
                    Toast.makeText(getApplicationContext(), "Invalid email format", Toast.LENGTH_SHORT).show();
                }
                // Thực hiện xử lý gửi email và đặt lại mật khẩu tại đây
                // Sau khi gửi email, chuyển đến activity khác hoặc hiển thị thông báo thành công
            }
        });
    }
}