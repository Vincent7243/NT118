package com.company.soccershoesstore;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment{
    private RecyclerView recyclershooes;
    Button button;



    @Nullable


        public View onCreateView (@NonNull LayoutInflater inflater, @Nullable ViewGroup
        container, @Nullable Bundle savedInstanceState){
        recyclershooes = recyclershooes.findViewById(R.id.recycleview_shooes);
        button = button.findViewById(R.id.Info);
        GotoInfo();
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
        }

        private void GotoInfo () {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent myIntent = new Intent(getContext(),InfoScreen.class);
                    startActivity(myIntent);
                }
            });
        }

    }
