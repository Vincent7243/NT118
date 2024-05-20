package com.company.soccershoesstore;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class BannerViewPagerAdapter extends PagerAdapter {
    private  List<Banner> mBannerlist;
    public BannerViewPagerAdapter(List<Banner> mphotoList) {
        this.mBannerlist = mphotoList;
    }

    @Override
    public int getCount() {
        if(mBannerlist!=null) {
            return mBannerlist.size();
        } else {
            return 0;
        }
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view=LayoutInflater.from(container.getContext()).inflate(R.layout.item_banner,container,false);
        ImageView img=view.findViewById(R.id.img_item_banner);
        Banner banner=mBannerlist.get(position);
        img.setImageResource(banner.getImg());
        container.addView(view);
        return view;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view==o;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//        destroyItem(container, position, object);
        container.removeView((View) object);
    }
}
