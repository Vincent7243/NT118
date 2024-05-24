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

import java.util.ArrayList;

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
                replaceFragmentWithProducts(getNikeProducts());
            }
        });

        adidasIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragmentWithProducts(getAdidasProducts());
            }
        });

        pumaIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragmentWithProducts(getPumaProducts());
            }
        });

        nbIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragmentWithProducts(getNBProducts());
            }
        });

        // Trả về layout
        return view;
    }

    private void replaceFragmentWithProducts(ArrayList<Product> products) {
        AllCategoryFragment fragment = new AllCategoryFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("products", products);
        fragment.setArguments(bundle);

        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.categories_fragment, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private ArrayList<Product> getNikeProducts() {
        // Tạo danh sách sản phẩm Nike
        ArrayList<Product> products = new ArrayList<>();
        products.add(new Product("Nike Shoe 1", "url_to_image_1"));
        products.add(new Product("Nike Shoe 2", "url_to_image_2"));
        // Thêm các sản phẩm khác
        return products;
    }

    private ArrayList<Product> getAdidasProducts() {
        // Tạo danh sách sản phẩm Adidas
        ArrayList<Product> products = new ArrayList<>();
        products.add(new Product("Adidas Shoe 1", "url_to_image_1"));
        products.add(new Product("Adidas Shoe 2", "url_to_image_2"));
        // Thêm các sản phẩm khác
        return products;
    }

    private ArrayList<Product> getPumaProducts() {
        // Tạo danh sách sản phẩm Puma
        ArrayList<Product> products = new ArrayList<>();
        products.add(new Product("Puma Shoe 1", "url_to_image_1"));
        products.add(new Product("Puma Shoe 2", "url_to_image_2"));
        // Thêm các sản phẩm khác
        return products;
    }

    private ArrayList<Product> getNBProducts() {
        // Tạo danh sách sản phẩm NB
        ArrayList<Product> products = new ArrayList<>();
        products.add(new Product("NB Shoe 1", "url_to_image_1"));
        products.add(new Product("NB Shoe 2", "url_to_image_2"));
        // Thêm các sản phẩm khác
        return products;
    }
}
