package com.dev.wedrive.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dev.wedrive.Constants;
import com.dev.wedrive.R;
import com.dev.wedrive.entity.ApiCar;
import com.dev.wedrive.entity.ApiMessageChat;
import com.dev.wedrive.helpers.FileHelper;
import com.dev.wedrive.util.ProfileImageUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import lombok.Setter;
import lombok.experimental.Accessors;

public class MessagesChatListAdapter extends RecyclerView.Adapter<MessagesChatListAdapter.ViewHolder> {

    private LayoutInflater inflater;

    @Setter
    private OnItemClickListener listener;

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

    public ApiMessageChat getItem(int id) {
        return chats.get(id);
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

        holder.listItemName.setText(chat.message.recipient.profile.name);
        holder.listItemMessage.setText(chat.message.message);
        holder.listItemTime.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(chat.updatedAt));

        holder.listItem.setOnClickListener((v) -> listener.onItemClick(v, position));

        if (chat.message.recipient.profile.image != null)
            ProfileImageUtil
                    .get()
                    .load(Constants.API_URL + "/uploads/profile/" + FileHelper.getStyleName(chat.message.recipient.profile.image, "sm"))
                    .into(holder.listItemImage);

    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout listItem;
        ImageView listItemImage;
        TextView listItemName;
        TextView listItemMessage;
        TextView listItemTime;

        ViewHolder(View view) {
            super(view);
            listItem = view.findViewById(R.id.list_item);
            listItemImage = view.findViewById(R.id.list_item_image);
            listItemName = view.findViewById(R.id.list_item_name);
            listItemMessage = view.findViewById(R.id.list_item_message);
            listItemTime = view.findViewById(R.id.list_item_time);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}
