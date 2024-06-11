package com.company.soccershoesstore;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.List;

public class NotificationsFragment extends Fragment {

    private RecyclerView recyclerView;
    private NotificationsAdapter adapter;
    private List<String> notificationList = new ArrayList<>();
    private int count = 0;
    private Button button;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);
        button = view.findViewById(R.id.btn);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new NotificationsAdapter(notificationList);
        recyclerView.setAdapter(adapter);
        button.setOnClickListener(v -> GetID());
        return view;
    }

    private void GetID() {
        if (count == 0) {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("notification")
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String contents = document.getString("content");
                                count++;
                                String formattedContent = "Message " + count + ": " + contents;
                                notificationList.add(formattedContent);


                                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_foreground);
                                Notification notification = new NotificationCompat.Builder(requireActivity(), Channel.CHANNEL_ID)
                                        .setContentTitle("Important")
                                        .setContentText(formattedContent)
                                        .setSmallIcon(R.drawable.ic_notification_fill)
                                        .setLargeIcon(bitmap)
                                        .build();

                                NotificationManager notificationManager = (NotificationManager) requireActivity().getSystemService(Context.NOTIFICATION_SERVICE);
                                if (notificationManager != null) {
                                    notificationManager.notify(count, notification);
                                }
                            }
                            adapter.notifyDataSetChanged(); // Cập nhật adapter sau khi thêm dữ liệu
                        }
                    });
        }
    }
}



