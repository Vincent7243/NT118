package com.company.soccershoesstore;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class AdminBillViewPagerAdapter extends FragmentStateAdapter {

    public AdminBillViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new AdmimBillNewFragment();
            case 1:
                return new AdminBillApprovedFragment();
            case 2:
                return new AdminBillReceivedFragment();
            default:
                return new AdmimBillNewFragment();
        }
    }


    @Override
    public int getItemCount() {
        return 3;
    }
}
