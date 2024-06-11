package com.company.soccershoesstore;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class AccountFragment extends Fragment {
    private TextView tvName, tvEmail, tvPhoneNum, tvAddress;
    private FirebaseFirestore firestore;

    private ImageButton imgDoiten;
    private ImageButton imgTTDH;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        imgDoiten = view.findViewById(R.id.img_doiten);
        imgTTDH = view.findViewById(R.id.img_TTDH);
        tvName = view.findViewById(R.id.txt_user_name);
        tvEmail = view.findViewById(R.id.txt_user_email);
        tvPhoneNum = view.findViewById(R.id.txt_user_phonenum);
        tvAddress = view.findViewById(R.id.txt_user_address);
        firestore = FirebaseFirestore.getInstance();

        loadUserProfile();
        goToActivity();
        return view;
    }

    public void goToActivity() {
        imgDoiten.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), EditProfileActivity.class);
            startActivity(intent);
        });

        imgTTDH.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), OrderStatusActivity.class);
            startActivity(intent);
        });
    }

    private void loadUserProfile() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();

        if (currentUser != null) {
            firestore.collection("users").document(currentUser.getUid())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                               @Override
                                               public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                   if (task.isSuccessful()) {
                                                       DocumentSnapshot document = task.getResult();
                                                       if (document.exists()) {
                                                           String name = document.getString("name");
                                                           String email = document.getString("email");
                                                           String phoneNum = document.getString("phonenum");
                                                           String address = document.getString("address");

                                                           tvName.setText(name);
                                                           tvEmail.setText(email);
                                                           tvPhoneNum.setText(phoneNum);
                                                           tvAddress.setText(address);
                                                       }
                                                   }
                                               }
                                           }
                    );
        }
    }
}

