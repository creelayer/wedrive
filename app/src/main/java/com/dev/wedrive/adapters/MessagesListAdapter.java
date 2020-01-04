package com.dev.wedrive.adapters;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.dev.wedrive.R;
import com.dev.wedrive.entity.ApiMessage;
import com.dev.wedrive.entity.ApiUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MessagesListAdapter extends ListAdapter {

    ApiUser recipient;

    public MessagesListAdapter(Context context, ApiUser recipient, ArrayList<ApiMessage> messages) {
        super(context, R.layout.adapter_message_list_item, messages);
        this.recipient = recipient;
    }

    @Override
    protected View populate(int position, View convertView) {

        ApiMessage message = (ApiMessage) getItem(position);

        TextView messageItem = convertView.findViewById(R.id.list_item_message);
        messageItem.setText(message.message);
        TextView messageTime = convertView.findViewById(R.id.list_item_time);
        messageTime.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(message.createdAt).toString());

        if (message.userId == recipient.id)
            messageItem.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
        else
            messageItem.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);

        return convertView;
    }
}
