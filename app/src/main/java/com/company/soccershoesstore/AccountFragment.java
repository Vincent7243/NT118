package com.company.soccershoesstore;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;



public class AccountFragment extends Fragment {
    ImageButton imageButton;
    private ImageButton imgTTDH;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        imageButton = view.findViewById(R.id.img_doiten);
        imgTTDH = view.findViewById(R.id.img_TTDH);
        goToActivity();
        goToTTDH();
        return view;
    }

    public void goToActivity() {
        imageButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), Change_info.class);
            startActivity(intent);
        });
    }

    public void goToTTDH(){
        imgTTDH.setOnClickListener(v -> {
            Intent intent1 = new Intent(getActivity(), Change_info.class);
            startActivity(intent1);
})
    ;}
}

