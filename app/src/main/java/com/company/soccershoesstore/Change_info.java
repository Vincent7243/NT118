
package com.company.soccershoesstore;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Change_info extends AppCompatActivity {
    Button button;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_info);
        button = findViewById(R.id.Back);
        button.setOnClickListener(v -> finish());
    }
}