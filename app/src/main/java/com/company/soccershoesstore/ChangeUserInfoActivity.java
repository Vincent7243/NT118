package com.company.soccershoesstore;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ChangeUserInfoActivity extends AppCompatActivity {

    private EditText etUserName, etUserEmail, etUserPhone;
    private Button btnSave, btnCancel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_user_info);

        etUserName = findViewById(R.id.et_user_name);
        etUserEmail = findViewById(R.id.et_user_email);
        etUserPhone = findViewById(R.id.et_user_phone);
        btnSave = findViewById(R.id.btn_save);
        btnCancel = findViewById(R.id.btn_cancel);

        // Retrieve and display current user info
        // You would typically fetch this from a database or shared preferences
        // For demonstration purposes, we're using hardcoded values
        etUserName.setText("Current User Name");
        etUserEmail.setText("user@example.com");
        etUserPhone.setText("1234567890");

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = etUserName.getText().toString();
                String userEmail = etUserEmail.getText().toString();
                String userPhone = etUserPhone.getText().toString();

                if (userName.isEmpty() || userEmail.isEmpty() || userPhone.isEmpty()) {
                    Toast.makeText(ChangeUserInfoActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else {
                    // Save user info logic here
                    // Typically you would save this information to a database or shared preferences

                    Toast.makeText(ChangeUserInfoActivity.this, "User info updated successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}