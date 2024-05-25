//package com.company.soccershoesstore;
//
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.RecyclerView;
//import androidx.recyclerview.widget.GridLayoutManager;
//import java.util.ArrayList;
//
//
//public class AllCategoryFragment extends Fragment {
//    public Button BackPress;
//    private RecyclerView recyclerView;
//    private AllCategoryFragmentAdapter adapter;
//    private ArrayList<Product> productList;
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_all_category, container, false);
//        BackPress = view.findViewById(R.id.back_button);
//
//        BackPress.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getParentFragmentManager().popBackStack();
//            }
//        });
//        return view;
//
////        if (getArguments() != null) {
////            productList = getArguments().getParcelableArrayList("products");
////        }
////        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
////        adapter = new AllCategoryFragmentAdapter(getContext(), productList);
////        recyclerView.setAdapter(adapter);
////
////        return view;
//    }
//}
