package com.company.soccershoesstore;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;


public class HomeFragment extends Fragment {
    private ViewPager mviewPager;

    RecyclerView rvnewest;
    RecyclerView rvhotsale;
    CircleIndicator mci;
    List<Banner> bannerList;
    FirebaseFirestore db;
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
        db=FirebaseFirestore.getInstance();
        view.findViewById(R.id.tv_newest).setVisibility(View.GONE);
        view.findViewById(R.id.tv_hotsale).setVisibility(View.GONE);

        mci=view.findViewById(R.id.idc_banner);
        bannerList=new ArrayList<>();
        bannerList.add(new Banner(R.drawable.banner1));
        rvnewest=view.findViewById(R.id.rv_home_newest_product);
        rvhotsale=view.findViewById(R.id.rv_home_hotsale_product);
        bannerList.add(new Banner(R.drawable.banner2));
        bannerList.add(new Banner(R.drawable.banner3));
        ArrayList<ProductCard> newestproduct=new ArrayList<>();
        ArrayList<ProductCard> recommendproduct1=new ArrayList<>();
        CardProductAdapter adapternewest=new CardProductAdapter(getContext(),newestproduct);
        CardProductAdapter adapterrecommend=new CardProductAdapter(getContext(),recommendproduct1);
//        ArrayList <ProductCard>productCards=new ArrayList<>();
//        productCards.add(new ProductCard("sp1",R.drawable.teamplate_san_pham,"20","BASAS BUMPER GUM EXT NE - LOW "));
//        productCards.add(new ProductCard("sp1",R.drawable.teamplate_san_pham,"20","BASAS BUMPER GUM EXT NE - LOW "));
//        productCards.add(new ProductCard("sp1",R.drawable.teamplate_san_pham,"20","BASAS BUMPER GUM EXT NE - LOW "));
//        productCards.add(new ProductCard("sp1",R.drawable.teamplate_san_pham,"20","BASAS BUMPER GUM EXT NE - LOW "));
//        productCards.add(new ProductCard("sp1",R.drawable.teamplate_san_pham,"20","BASAS BUMPER GUM EXT NE - LOW "));
//        productCards.add(new ProductCard("sp1",R.drawable.teamplate_san_pham,"20","BASAS BUMPER GUM EXT NE - LOW "));
//        productCards.add(new ProductCard("sp1",R.drawable.teamplate_san_pham,"20","BASAS BUMPER GUM EXT NE - LOW "));
        db.collection("Products")
                .orderBy("image", Query.Direction.DESCENDING)
                .limit(5)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            newestproduct.clear();
                            for(QueryDocumentSnapshot documentSnapshot:task.getResult()) {
//                                Log.d("homeproductget",documentSnapshot.toString());
                                newestproduct.add(new ProductCard(documentSnapshot.getId(),documentSnapshot.get("image").toString(),documentSnapshot.get("price").toString(),documentSnapshot.get("name").toString()));
                            }
                            view.findViewById(R.id.tv_newest).setVisibility(View.VISIBLE);
                            adapternewest.notifyDataSetChanged();
                        }else {
                            Toast.makeText(getContext(), "Some error!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        db.collection("Products")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<ProductCard> recommendProduct = new ArrayList<>();
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                recommendProduct.add(new ProductCard(
                                        documentSnapshot.getId(),
                                        documentSnapshot.get("image").toString(),
                                        documentSnapshot.get("price").toString(),
                                        documentSnapshot.get("name").toString()
                                ));
                            }

                            // Sắp xếp danh sách theo giá trị "price" tăng dần
                            Collections.sort(recommendProduct, new Comparator<ProductCard>() {
                                @Override
                                public int compare(ProductCard p1, ProductCard p2) {
                                    // Chuyển đổi price từ String sang Double để so sánh
                                    return Double.compare(
                                            Double.parseDouble(p1.getPrice()),
                                            Double.parseDouble(p2.getPrice())
                                    );
                                }
                            });
                            view.findViewById(R.id.tv_hotsale).setVisibility(View.VISIBLE);

                            // Cập nhật adapter với danh sách đã sắp xếp

//                            adapterrecommend.setProducts(recommendProduct);
                            recommendproduct1.clear();
                            recommendproduct1.addAll(recommendProduct.subList(0,3));
                            recommendproduct1.addAll(recommendProduct.subList(recommendProduct.size()-2,recommendProduct.size()));
                            adapterrecommend.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getContext(), "Some error!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
//        CardProductAdapter adapternewest=new CardProductAdapter(view.getContext(),productCards);
        rvnewest.setAdapter(adapternewest);
        rvnewest.setLayoutManager(new LinearLayoutManager(view.getContext(),LinearLayoutManager.HORIZONTAL,false));
        rvhotsale.setAdapter(adapterrecommend);
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
