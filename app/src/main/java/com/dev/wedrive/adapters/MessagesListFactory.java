package com.dev.wedrive.adapters;

import androidx.core.util.Consumer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dev.wedrive.MessengerActivity;
import com.dev.wedrive.R;
import com.dev.wedrive.entity.ApiMessage;
import com.dev.wedrive.entity.ApiMessageChat;
import com.dev.wedrive.entity.ApiUser;

import java.util.ArrayList;

public class MessagesListFactory {

    private MessengerActivity activity;

    private ApiUser user;

    public MessagesListFactory(MessengerActivity activity, ApiUser user) {
        this.activity = activity;
        this.user = user;
    }

    public RecyclerView createChatList(ArrayList<ApiMessageChat> chats, final Consumer<ApiMessageChat> func) {
        RecyclerView list = activity.findViewById(R.id.message_chats_list);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        list.setLayoutManager(linearLayoutManager);
        MessagesChatListAdapter messagesChatAdapter = new MessagesChatListAdapter(activity, chats, user);
        messagesChatAdapter.setListener((v, position) -> func.accept(messagesChatAdapter.getItem(position)));
        list.setAdapter(messagesChatAdapter);
        return list;
    }

    public RecyclerView createMessageList(ArrayList<ApiMessage> messages, ApiUser recipient) {
        RecyclerView list = activity.findViewById(R.id.message_list);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        linearLayoutManager.setReverseLayout(true);
        list.setLayoutManager(linearLayoutManager);
        MessagesListAdapter messagesAdapter = new MessagesListAdapter(activity, recipient, messages);
        list.setAdapter(messagesAdapter);
        return list;
    }

}
