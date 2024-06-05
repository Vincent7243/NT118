package com.company.soccershoesstore;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class AdminBillFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_admin_bill, container, false);
        // Inflate the layout for this fragment
        TabLayout tabLayout = view.findViewById(R.id.adminbilltabLayout);
        ViewPager2 viewPager = view.findViewById(R.id.adminbillviewPager);
        AdminBillViewPagerAdapter adapter = new AdminBillViewPagerAdapter((FragmentActivity) getActivity());
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setText("New");
                        break;
                    case 1:
                        tab.setText("Approved");
                        break;
                    case 2:
                        tab.setText("Received");
                        break;
                }
            }
        }).attach();
        return view;

    }
}