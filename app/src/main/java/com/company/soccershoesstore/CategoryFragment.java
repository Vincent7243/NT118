package com.company.soccershoesstore;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class CategoryFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate layout
        View view = inflater.inflate(R.layout.fragment_categories, container, false);

        // Ánh xạ các biểu tượng từ layout
        ImageView nikeIcon = view.findViewById(R.id.imageView_nike);
        ImageView adidasIcon = view.findViewById(R.id.imageView_adidas);
        ImageView pumaIcon = view.findViewById(R.id.imageView_puma);
        ImageView nbIcon = view.findViewById(R.id.imageView_nb);

        // Thiết lập trình nghe sự kiện cho từng biểu tượng
        nikeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new AllCategoryFragment());
            }
        });

        adidasIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new AllCategoryFragment());
            }
        });

        pumaIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new AllCategoryFragment());
            }
        });

        nbIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new AllCategoryFragment());
            }
        });

        // Trả về layout
        return view;
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.categories_fragment, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
