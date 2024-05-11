package com.company.soccershoesstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class BottomNavigationBar extends AppCompatActivity {

    private BottomNavigationView mNavigationView;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation_bar);
        mNavigationView = findViewById(R.id.bottom_navigation_bar);
        mViewPager = findViewById(R.id.view_pager);

        setUpViewPager();
        mNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.action_cozy) {
                    item.setIcon(R.drawable.ic_cozy_fill);
                    Toast.makeText(BottomNavigationBar.this, "Categories", Toast.LENGTH_SHORT).show();
                } else if (item.getItemId() == R.id.action_love) {
                    item.setIcon(R.drawable.ic_love_fill);
                    Toast.makeText(BottomNavigationBar.this, "Favorites", Toast.LENGTH_SHORT).show();
                } else if (item.getItemId() == R.id.action_home) {
                    item.setIcon(R.drawable.ic_home_fill);
                    Toast.makeText(BottomNavigationBar.this, "Home", Toast.LENGTH_SHORT).show();
                } else if (item.getItemId() == R.id.action_notification) {
                    item.setIcon(R.drawable.ic_notification_fill);
                    Toast.makeText(BottomNavigationBar.this, "Notification", Toast.LENGTH_SHORT).show();
                } else if (item.getItemId() == R.id.action_person) {
                    item.setIcon(R.drawable.ic_person_fill);
                    Toast.makeText(BottomNavigationBar.this, "Person", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
    }

    private void setUpViewPager(){

    }
}