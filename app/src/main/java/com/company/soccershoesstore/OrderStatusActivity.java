package com.company.soccershoesstore;

import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import java.util.ArrayList;

public class OrderStatusActivity extends AppCompatActivity {

    private Button btnBack, btnWaiting, btnApproved, btnCancelled, btnDelivered;
    private WaitingFragment waitingFragment;
    private ApprovedFragment approvedFragment;
    private CancelledFragment cancelledFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);

        btnBack = findViewById(R.id.btn_back);
        btnWaiting = findViewById(R.id.btn_waiting);
        btnApproved = findViewById(R.id.btn_approved);
        btnCancelled = findViewById(R.id.btn_cancelled);
        btnDelivered = findViewById(R.id.btn_delivered);

        waitingFragment = new WaitingFragment();
        approvedFragment = new ApprovedFragment();
        cancelledFragment = new CancelledFragment();

        btnBack.setOnClickListener(v -> finish());
        btnWaiting.setOnClickListener(v -> loadFragment(waitingFragment));
        btnApproved.setOnClickListener(v -> loadFragment(approvedFragment));
        btnCancelled.setOnClickListener(v -> loadFragment(cancelledFragment));

        // Load default fragment
        loadFragment(waitingFragment);
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.order_status_container, fragment);
        transaction.commit();
    }

}