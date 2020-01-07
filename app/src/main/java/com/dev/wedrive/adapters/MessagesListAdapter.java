package com.dev.wedrive.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dev.wedrive.R;
import com.dev.wedrive.entity.ApiMessage;
import com.dev.wedrive.entity.ApiUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import lombok.Setter;
import lombok.experimental.Accessors;

public class MessagesListAdapter extends RecyclerView.Adapter<MessagesListAdapter.ViewHolder> {

    private LayoutInflater inflater;

    ApiUser recipient;

    @Accessors(chain = true)
    @Setter
    private ArrayList<ApiMessage> messages;

    // data is passed into the constructor
    public MessagesListAdapter(Context context, ApiUser recipient, ArrayList<ApiMessage> messages) {
        this.inflater = LayoutInflater.from(context);
        this.recipient = recipient;
        this.messages = messages;
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return messages.size();
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.adapter_message_list_item, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ApiMessage message = messages.get(position);

        holder.messageItem.setText(message.message);
        holder.messageTime.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(message.createdAt));

        holder.messageItem.setTextAlignment(message.userId == recipient.id ? View.TEXT_ALIGNMENT_TEXT_END : View.TEXT_ALIGNMENT_TEXT_START);
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView messageItem;
        TextView messageTime;

        ViewHolder(View view) {
            super(view);
            messageItem = view.findViewById(R.id.list_item_message);
            messageTime = view.findViewById(R.id.list_item_time);
        }
    }
}
