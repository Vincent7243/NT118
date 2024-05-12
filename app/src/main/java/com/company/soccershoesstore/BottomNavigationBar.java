package com.company.soccershoesstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import android.os.Bundle;
import android.view.Menu;
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
//                // Gỡ bỏ fill của tất cả các tấm hình trước khi đặt fill cho tấm hình mới được chọn
                Menu menu = mNavigationView.getMenu();
                for (int i = 0; i < menu.size(); i++) {
                    MenuItem menuItem = menu.getItem(i);
                    if (menuItem.getItemId() != item.getItemId()) {
                        // Gỡ bỏ fill của các tấm hình khác
                        menuItem.setIcon(getUnfilledIcon(menuItem.getItemId()));
                    }
                }

                // Đặt fill cho tấm hình được chọn
                int itemId = item.getItemId();
                if (itemId == R.id.action_cozy) {
                    item.setIcon(R.drawable.ic_cozy_fill);
                    Toast.makeText(BottomNavigationBar.this, "Categories", Toast.LENGTH_SHORT).show();
                } else if (itemId == R.id.action_love) {
                    item.setIcon(R.drawable.ic_love_fill);
                    Toast.makeText(BottomNavigationBar.this, "Favorites", Toast.LENGTH_SHORT).show();
                } else if (itemId == R.id.action_home) {
                    item.setIcon(R.drawable.ic_home_fill);
                    Toast.makeText(BottomNavigationBar.this, "Home", Toast.LENGTH_SHORT).show();
                } else if (itemId == R.id.action_notification) {
                    item.setIcon(R.drawable.ic_notification_fill);
                    Toast.makeText(BottomNavigationBar.this, "Notification", Toast.LENGTH_SHORT).show();
                } else if (itemId == R.id.action_person) {
                    item.setIcon(R.drawable.ic_person_fill);
                    Toast.makeText(BottomNavigationBar.this, "Person", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
            private int getUnfilledIcon(int itemId) {
                if (itemId == R.id.action_cozy) {
                    return R.drawable.ic_cozy;
                } else if (itemId == R.id.action_love) {
                    return R.drawable.ic_love;
                } else if (itemId == R.id.action_home) {
                    return R.drawable.ic_home;
                } else if (itemId == R.id.action_notification) {
                    return R.drawable.ic_notification;
                } else if (itemId == R.id.action_person) {
                    return R.drawable.ic_person;
                } else {
                    return -1;
                }
            }

        });
    }

    private void setUpViewPager(){
        ViewPagerAdapter_BottomNavBar viewPagerAdapterBottomNavBar = new ViewPagerAdapter_BottomNavBar(getSupportFragmentManager(),getLifecycle());
        //mViewPager.setAdapter(viewPagerAdapterBottomNavBar);
    }
}