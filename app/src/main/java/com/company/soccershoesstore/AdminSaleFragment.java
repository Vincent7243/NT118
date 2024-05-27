package com.company.soccershoesstore;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class AdminSaleFragment extends Fragment {
    ListView lv;
    Saleadapter saleadapter;
    ArrayList<Sale> sales;
    ImageButton ib_add;
    float dX=0,dY=0;
    long lastDownTime = 0;
    long clickThreshold = 200;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_admin_sale, container, false);
        lv=view.findViewById(R.id.lv_admin_sale);
        ib_add=view.findViewById(R.id.ib_admin_sale_add);
        sales=new ArrayList<>();
        sales.add(new Sale("1","hihihi","100000","3"));
        sales.add(new Sale("2","hihihi1","100000","3"));
        sales.add(new Sale("3","hihihi2","100000","3"));
        sales.add(new Sale("4","hihihi3","100000","3"));
        sales.add(new Sale("5","hihihi4","100000","3"));
        sales.add(new Sale("6","hihihi5","100000","3"));
        sales.add(new Sale("1","hihihi","100000","3"));
        sales.add(new Sale("2","hihihi1","100000","3"));
        sales.add(new Sale("3","hihihi2","100000","3"));
        sales.add(new Sale("4","hihihi3","100000","3"));
        sales.add(new Sale("5","hihihi4","100000","3"));
        sales.add(new Sale("6","hihihi5","100000","3"));
        saleadapter=new Saleadapter(view.getContext(),R.layout.item_sale,sales);
        lv.setAdapter(saleadapter);

        ib_add.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        dX = view.getX() - event.getRawX();
                        dY = view.getY() - event.getRawY();
                        lastDownTime = System.currentTimeMillis();
                        break;

                    case MotionEvent.ACTION_MOVE:
                        view.animate()
                                .x(event.getRawX() + dX)
                                .y(event.getRawY() + dY)
                                .setDuration(0)
                                .start();
                        break;

                    case MotionEvent.ACTION_UP:
                        long currentTime = System.currentTimeMillis();
                        if (currentTime - lastDownTime < clickThreshold) {
                            view.performClick();
                        }
                        break;

                    default:
                        return false;
                }
                return true;
            }
        });
        ib_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý sự kiện click ở đây
                Toast.makeText(getActivity(), "ImageButton được click!", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

}