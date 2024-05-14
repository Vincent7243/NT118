package com.company.soccershoesstore;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HomeScreen extends AppCompatActivity implements View.OnClickListener{
    private RecyclerView recyclershooes;
    private Button btnnam,btnnu;
    Button button;

    GridLayoutManager gridLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        Anhxa();
        GotoInfo();

    }

    private void GotoInfo(){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(HomeScreen.this,InfoScreen.class);
                startActivity(myIntent);
            }
        });
    }



    private void Anhxa(){
        recyclershooes = findViewById(R.id.recycleview_shooes);
        button = findViewById(R.id.Info);
        btnnam = findViewById(R.id.btn_nam);
        btnnu = findViewById(R.id.btn_nu);
        gridLayoutManager = new GridLayoutManager(this, 2);

        recyclershooes.setLayoutManager(gridLayoutManager);
        ShooesAdapter adapter = new ShooesAdapter(getListShooes());
        recyclershooes.setAdapter(adapter);

        btnnam.setOnClickListener(this);
        btnnu.setOnClickListener(this);
    }

    private List<Shooes> getListShooes () {
        List<Shooes> list = new ArrayList<>();
        list.add(new Shooes(R.drawable.baseline_person_24, "Nam", Shooes.Type_nam));


        list.add(new Shooes(R.drawable.baseline_person_24, "Nam", Shooes.Type_nu));


        return list;
    }



    @Override
    public void onClick (View v){


        if(v.getId()== R.id.btn_nam){
            scrollToItem(0);}


        if(v.getId() == R.id.btn_nu){
            scrollToItem(9);}

    }

    private void scrollToItem ( int index){
        if (gridLayoutManager == null) {
            return;
        }
        gridLayoutManager.scrollToPositionWithOffset(index, 0);
    }
}