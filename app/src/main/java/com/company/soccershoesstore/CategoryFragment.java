package com.company.soccershoesstore;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.company.soccershoesstore.util.AdidasCategoryFragment;


public class CategoryFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate layout
        View view = inflater.inflate(R.layout.fragment_categories,container,false);

        // Ánh xạ các biểu tượng từ layout
        ImageView nikeIcon = view.findViewById(R.id.imageView_nike);
        ImageView adidasIcon = view.findViewById(R.id.imageView_adidas);
        ImageView pumaIcon = view.findViewById(R.id.imageView_puma);
        ImageView nbIcon = view.findViewById(R.id.imageView_nb);


//        FrameLayout đã được thêm ở activity_main.xml

        // Thiết lập trình nghe sự kiện cho từng biểu tượng
        nikeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo một phiên bản của NikeCategoryFragment
                NikeCategoryFragment nikeFragment = new NikeCategoryFragment();

                // Tạo FragmentTransaction
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();

                // Thêm NikeCategoryFragment vào back stack (nếu bạn muốn)
                transaction.addToBackStack(null);

                // Thay thế fragment hiện tại bằng NikeCategoryFragment
                transaction.replace(R.id.fragment_container, nikeFragment);

                // Hoàn thành và thực hiện giao dịch
                transaction.commit();
            }
        });

        adidasIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo một phiên bản của NikeCategoryFragment
                AdidasCategoryFragment adidasFragment = new AdidasCategoryFragment();

                // Tạo FragmentTransaction
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();

                // Thêm NikeCategoryFragment vào back stack (nếu bạn muốn)
                transaction.addToBackStack(null);

                // Thay thế fragment hiện tại bằng NikeCategoryFragment
                transaction.replace(R.id.fragment_container, adidasFragment);

                // Hoàn thành và thực hiện giao dịch
                transaction.commit();
            }
        });

        pumaIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo một phiên bản của NikeCategoryFragment
                PumaCategoryFragment pumaFragment = new PumaCategoryFragment();

                // Tạo FragmentTransaction
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();

                // Thêm NikeCategoryFragment vào back stack (nếu bạn muốn)
                transaction.addToBackStack(null);

                // Thay thế fragment hiện tại bằng NikeCategoryFragment
                transaction.replace(R.id.fragment_container, pumaFragment);

                // Hoàn thành và thực hiện giao dịch
                transaction.commit();
            }
        });

        nbIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo một phiên bản của NikeCategoryFragment
                NbCategoryFragment nbFragment = new NbCategoryFragment();

                // Tạo FragmentTransaction
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();

                // Thêm NikeCategoryFragment vào back stack (nếu bạn muốn)
                transaction.addToBackStack(null);

                // Thay thế fragment hiện tại bằng NikeCategoryFragment
                transaction.replace(R.id.fragment_container, nbFragment);

                // Hoàn thành và thực hiện giao dịch
                transaction.commit();
            }
        });

        // Trả về layout
        return view;
    }
}
