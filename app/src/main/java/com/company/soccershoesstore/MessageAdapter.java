package com.company.soccershoesstore;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class MessageAdapter extends ArrayAdapter<Message> {

    public MessageAdapter(Context context, List<Message> messages) {
        super(context, 0, messages);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Message message = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_message, parent, false);
        }

        TextView textView = convertView.findViewById(R.id.textViewMessage);
        TextView textView2 = convertView.findViewById(R.id.textViewSender);
        TextView textView3 = convertView.findViewById(R.id.tv_time);
        LinearLayout ll=convertView.findViewById(R.id.ll_chat_message);
//        textView2.setText(message.getSender());
        textView.setText(message.getText());
        Date date = new Date(message.getTimestamp());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String formattedDate = dateFormat.format(date);
        textView3.setText(formattedDate);
        if(!Objects.equals(message.getSender(), "0ORnvYdXD6damu7SPJei6WDYTsm1")) {
            textView2.setVisibility(View.GONE);
            ll.setGravity(Gravity.END);
        }else {
            textView2.setVisibility(View.VISIBLE);
            textView2.setText("Admin");
            ll.setGravity(Gravity.START);

        }


        return convertView;
    }
}

