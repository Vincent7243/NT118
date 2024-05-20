package com.company.soccershoesstore;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ViewPagerAdapter_TopNavBar extends FragmentPagerAdapter {
    public ViewPagerAdapter_TopNavBar(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new CartFragment();
            case 1:
                return new ChatFragment();
        default:
            return new CartFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
