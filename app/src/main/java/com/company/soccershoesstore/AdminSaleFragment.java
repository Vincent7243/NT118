package com.company.soccershoesstore;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class AdminSaleFragment extends Fragment {


    ListView lv;
    Saleadapter saleadapter;
    ArrayList<Sale> sales;
    ImageButton ib_add;
    float dX=0,dY=0;
    long lastDownTime = 0;
    long clickThreshold = 200;
    FirebaseFirestore db;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_admin_sale, container, false);
        lv=view.findViewById(R.id.lv_admin_sale);
        ib_add=view.findViewById(R.id.ib_admin_sale_add);
        db=FirebaseFirestore.getInstance();
        sales=new ArrayList<>();
        saleadapter=new Saleadapter(getContext(),R.layout.item_sale,sales);
        lv.setAdapter(saleadapter);
//        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
////                Sale sale=sales.get(position);
////                Intent intent=new Intent(view.getContext(), activity_admin_sale_edit.class);
////                intent.putExtra("iid",sale.getMid());
////                intent.putExtra("mcode",sale.getMcode());
////                intent.putExtra("mprice",sale.getMprice());
////                intent.putExtra("mquantity",sale.getMquantity());
////                startActivity(intent);
//                Log.d("testclick","hi");
//            }
//
//        });

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
                Intent intent=new Intent(getContext(), activity_admin_sale_edit.class);
                intent.putExtra("iid","");
                intent.putExtra("mcode","");
                intent.putExtra("mprice","");
                intent.putExtra("mquantity","");
                startActivity(intent);

            }
        });
        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        db.collection("sales")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            sales.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("firebasefirestore", document.getId() + " => " + document.getData());

                                sales.add(new Sale(document.getId().toString(),document.getData().get("code").toString(),document.getData().get("price").toString(),document.getData().get("quantity").toString()));

                            }

                            saleadapter.notifyDataSetChanged();


                        }else {
                            Log.w("firebasefirestore", "Error getting documents.", task.getException());


                        }
                    }
                });
    }

}