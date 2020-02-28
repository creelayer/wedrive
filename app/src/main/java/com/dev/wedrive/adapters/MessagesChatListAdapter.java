package com.dev.wedrive.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dev.wedrive.R;
import com.dev.wedrive.entity.ApiMessage;
import com.dev.wedrive.entity.ApiMessageChat;
import com.dev.wedrive.entity.ApiUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import lombok.Setter;
import lombok.experimental.Accessors;

public class MessagesChatListAdapter extends RecyclerView.Adapter<MessagesChatListAdapter.ViewHolder> {

    private LayoutInflater inflater;


    @Accessors(chain = true)
    @Setter
    private ArrayList<ApiMessageChat> chats;

    // data is passed into the constructor
    public MessagesChatListAdapter(Context context, ArrayList<ApiMessageChat> chats) {
        this.inflater = LayoutInflater.from(context);
        this.chats = chats;
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return chats.size();
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.adapter_message_chat_list_item, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ApiMessageChat chat = chats.get(position);

        holder.messageItem.setText(chat.message.message);
        holder.messageTime.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(chat.updatedAt));
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
