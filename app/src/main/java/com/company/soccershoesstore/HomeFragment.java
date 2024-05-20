package com.company.soccershoesstore;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;


public class HomeFragment extends Fragment {
    private ViewPager mviewPager;
    RecyclerView rvnewest;
    RecyclerView rvhotsale;
    CircleIndicator mci;
    List<Banner> bannerList;
    private Handler mhandler= new Handler();
    private  Runnable mrunnable=new Runnable() {
        @Override
        public void run() {
            if(mviewPager.getCurrentItem()==bannerList.size()-1) {
                mviewPager.setCurrentItem(0);
            } else {
                mviewPager.setCurrentItem(mviewPager.getCurrentItem()+1);

            }
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        mhandler.removeCallbacks(mrunnable);
    }

    @Override
    public void onResume() {
        super.onResume();
        mhandler.postDelayed(mrunnable,2000);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        mviewPager=view.findViewById(R.id.viewPagerbanner);
        mci=view.findViewById(R.id.idc_banner);
        bannerList=new ArrayList<>();
        bannerList.add(new Banner(R.drawable.banner1));
        rvnewest=view.findViewById(R.id.rv_home_newest_product);
        rvhotsale=view.findViewById(R.id.rv_home_hotsale_product);
        bannerList.add(new Banner(R.drawable.banner2));
        bannerList.add(new Banner(R.drawable.banner3));
        ArrayList <ProductCard>productCards=new ArrayList<>();
        productCards.add(new ProductCard("sp1",R.drawable.teamplate_san_pham,"20","BASAS BUMPER GUM EXT NE - LOW "));
        productCards.add(new ProductCard("sp1",R.drawable.teamplate_san_pham,"20","BASAS BUMPER GUM EXT NE - LOW "));
        productCards.add(new ProductCard("sp1",R.drawable.teamplate_san_pham,"20","BASAS BUMPER GUM EXT NE - LOW "));
        productCards.add(new ProductCard("sp1",R.drawable.teamplate_san_pham,"20","BASAS BUMPER GUM EXT NE - LOW "));
        productCards.add(new ProductCard("sp1",R.drawable.teamplate_san_pham,"20","BASAS BUMPER GUM EXT NE - LOW "));
        productCards.add(new ProductCard("sp1",R.drawable.teamplate_san_pham,"20","BASAS BUMPER GUM EXT NE - LOW "));
        productCards.add(new ProductCard("sp1",R.drawable.teamplate_san_pham,"20","BASAS BUMPER GUM EXT NE - LOW "));
        CardProductAdapter adapternewest=new CardProductAdapter(view.getContext(),productCards);
        rvnewest.setAdapter(adapternewest);
        rvnewest.setLayoutManager(new LinearLayoutManager(view.getContext(),LinearLayoutManager.HORIZONTAL,false));
        rvhotsale.setAdapter(adapternewest);
        rvhotsale.setLayoutManager(new LinearLayoutManager(view.getContext(),LinearLayoutManager.HORIZONTAL,false));
        BannerViewPagerAdapter bannerViewPagerAdapter=new BannerViewPagerAdapter(bannerList);

        mviewPager.setAdapter(bannerViewPagerAdapter);
        mci.setViewPager(mviewPager);
        mhandler.postDelayed(mrunnable,2000);
        mviewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                mhandler.removeCallbacks(mrunnable);
                mhandler.postDelayed(mrunnable,2000);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        return view;
    }
}
