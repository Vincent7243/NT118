package com.company.soccershoesstore;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerAdapter_BottomNavBar extends FragmentStatePagerAdapter {
    public ViewPagerAdapter_BottomNavBar(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new CategoryFragment();
            case 1:
                return new FavoritesFragment();
            case 2:
                return new HomeFragment();
            case 3:
                return new NotificationsFragment();
            case 4:
                return new AccountFragment();
            default:
                return new HomeFragment();
        }
    }



    @Override
    public int getCount() {
        return 5;
    }
}
