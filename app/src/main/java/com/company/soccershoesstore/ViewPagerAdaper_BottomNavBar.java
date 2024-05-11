package com.company.soccershoesstore;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerAdaper_BottomNavBar extends FragmentStateAdapter {
    public ViewPagerAdaper_BottomNavBar(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new Fragment();
            case 1:
                return new Fragment();
            case 2:
                return new Fragment();
            case 3:
                return new Fragment();
            case 4:
                return new Fragment();
            default:
                return new Fragment();
        }

    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
