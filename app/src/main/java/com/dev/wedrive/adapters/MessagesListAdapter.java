package com.dev.wedrive.adapters;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dev.wedrive.R;
import com.dev.wedrive.entity.ApiMessage;
import com.dev.wedrive.entity.ApiUser;
import com.dev.wedrive.helpers.MessengerHelper;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import lombok.Setter;
import lombok.experimental.Accessors;

public class MessagesListAdapter extends RecyclerView.Adapter<MessagesListAdapter.ViewHolder> {

    public static int MESSAGE_TYPE_SENT = 1;
    public static int MESSAGE_TYPE_RECEIVED = 2;

    private Context context;
    private LayoutInflater inflater;

    ApiUser recipient;

    private ArrayList<ApiMessage> messages;

    public MessagesListAdapter(Context context, ApiUser recipient, ArrayList<ApiMessage> messages) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.recipient = recipient;
        this.messages = messages;

    }

    // total number of rows
    @Override
    public int getItemCount() {
        return messages.size();
    }


    @Override
    public int getItemViewType(int position) {
        ApiMessage message = messages.get(position);

        return message.userId == recipient.id ? MESSAGE_TYPE_SENT : MESSAGE_TYPE_RECEIVED;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;

        if (viewType == MESSAGE_TYPE_RECEIVED)
            view = inflater.inflate(R.layout.adapter_message_list_item_received, parent, false);
        else
            view = inflater.inflate(R.layout.adapter_message_list_item, parent, false);


        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ApiMessage message = messages.get(position);

        holder.listItemMessage.setText(message.message);
        holder.listItemTime.setText(MessengerHelper.formatShortTime(message.createdAt));

        if(message.userId == 2)
            holder.listItemMessageDayTime.setVisibility(View.VISIBLE);

//        if (position != 0 && !MessengerHelper.formatShortDate(messages.get(position - 1).createdAt).equals(MessengerHelper.formatShortDate(message.createdAt))) {
//            holder.listItemMessageDayTime.setText(MessengerHelper.formatShortDate(message.createdAt));
//            holder.listItemMessageDayTime.setVisibility(View.VISIBLE);
//        }

    }

    public void update(ArrayList<ApiMessage> messages) {
        this.messages = messages;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        LinearLayout listItem;
        TextView listItemMessageDayTime;
        TextView listItemMessage;
        TextView listItemTime;

        ViewHolder(View view) {
            super(view);
            listItem = view.findViewById(R.id.list_item);
            listItemMessageDayTime = view.findViewById(R.id.list_item_day_time);
            listItemMessage = view.findViewById(R.id.list_item_message);
            listItemTime = view.findViewById(R.id.list_item_time);
        }
    }
}
