package com.company.soccershoesstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class BottomNavigationBar extends AppCompatActivity {

    private BottomNavigationView mNavigationView;
    private ViewPager mViewPager;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation_bar);
        mNavigationView = findViewById(R.id.bottom_navigation_bar);
        mViewPager = findViewById(R.id.view_pager);

        setUpViewPager();
        mNavigationView.setSelectedItemId(R.id.action_home);
        mNavigationView.getMenu().getItem(2).setIcon(R.drawable.ic_home_fill);
        mViewPager.setCurrentItem(2);

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
                    mViewPager.setCurrentItem(0);
                } else if (itemId == R.id.action_love) {
                    item.setIcon(R.drawable.ic_love_fill);
                    mViewPager.setCurrentItem(1);
                } else if (itemId == R.id.action_home) {
                    item.setIcon(R.drawable.ic_home_fill);
                    mViewPager.setCurrentItem(2);
                } else if (itemId == R.id.action_notification) {
                    item.setIcon(R.drawable.ic_notification_fill);
                    mViewPager.setCurrentItem(3);
                } else if (itemId == R.id.action_person) {
                    item.setIcon(R.drawable.ic_person_fill);
                    mViewPager.setCurrentItem(4);
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
        ViewPagerAdapter_BottomNavBar viewPagerAdapterBottomNavBar=new ViewPagerAdapter_BottomNavBar(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mViewPager.setAdapter(viewPagerAdapterBottomNavBar);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Menu menu = mNavigationView.getMenu();
                for (int i = 0; i < menu.size(); i++) {
                    MenuItem menuItem = menu.getItem(i);
                    menuItem.setIcon(getUnfilledIcon(menuItem.getItemId()));
                    MenuItem selectedItem = menu.getItem(position);
                    selectedItem.setIcon(getFilledIcon(selectedItem.getItemId()));
                }
            }
            private int getFilledIcon(int itemId) {
                if (itemId == R.id.action_cozy) {
                    return R.drawable.ic_cozy_fill;
                } else if (itemId == R.id.action_love) {
                    return R.drawable.ic_love_fill;
                } else if (itemId == R.id.action_home) {
                    return R.drawable.ic_home_fill;
                } else if (itemId == R.id.action_notification) {
                    return R.drawable.ic_notification_fill;
                } else if (itemId == R.id.action_person) {
                    return R.drawable.ic_person_fill;
                } else {
                    return -1;
                }
            }


            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}