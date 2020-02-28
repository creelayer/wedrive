package com.dev.wedrive;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ViewFlipper;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dev.wedrive.adapters.MessagesChatListAdapter;
import com.dev.wedrive.adapters.MessagesListAdapter;
import com.dev.wedrive.entity.ApiMessage;
import com.dev.wedrive.entity.ApiRequest;
import com.dev.wedrive.entity.ApiUser;
import com.dev.wedrive.service.MessagesService;
import com.dev.wedrive.service.RequestService;
import com.dev.wedrive.service.UserService;

import java.util.Timer;
import java.util.TimerTask;

import lombok.Getter;

public class MessengerActivity extends AbstractAuthActivity {

    private MessagesService messagesService;
    private RequestService requestService;
    private UserService userService;

    private Timer timer;
    private TimerTask timerTask;

    @Getter
    private ApiUser user;

    @Getter
    private ApiUser recipient;

    @Getter
    private ApiRequest request;

    protected ViewFlipper messengerFlipper;

    private MessagesChatListAdapter messagesChatAdapter;
    private MessagesListAdapter messagesAdapter;

    @Getter
    private EditText messageInp;
    private Button messageBtn;

    public MessengerActivity() {
        messagesService = new MessagesService();
        requestService = new RequestService();
        userService = new UserService();
        timer = new Timer();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        timerTask = new TimerTask() {
//            @Override
//            public void run() {
//                loadMessageList(user, recipient, request);
//            }
//        };
//        timer.scheduleAtFixedRate(timerTask, 10000, 5000);
    }

    @Override
    protected void onPause() {
//        timerTask.cancel();
        super.onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);

        messengerFlipper = findViewById(R.id.messengerFlipper);

        messageInp = findViewById(R.id.message_inp);
        messageBtn = findViewById(R.id.message_btn);
        messageBtn.setOnClickListener((v) -> sendMessage(new ApiMessage(this)));
        loadConversationsList();
        //  load();
    }

    private void loadConversationsList() {
        messagesService.getConversations((chats) -> {
            if (messagesChatAdapter == null) {
                RecyclerView list = findViewById(R.id.message_chats_list);
                final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                linearLayoutManager.setReverseLayout(true);
                list.setLayoutManager(linearLayoutManager);
                messagesChatAdapter = new MessagesChatListAdapter(this, chats);
                list.setAdapter(messagesChatAdapter);
            } else
                messagesChatAdapter.setChats(chats).notifyDataSetChanged();
        });
    }

    private void loadMessageList(ApiUser user, ApiUser recipient, ApiRequest request) {

        if (recipient == null || user == null || request == null)
            return;

        messagesService.getConversation(recipient, request, (messages) -> {
            if (messagesAdapter == null) {
                RecyclerView list = findViewById(R.id.message_list);
                final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                linearLayoutManager.setReverseLayout(true);
                list.setLayoutManager(linearLayoutManager);
                messagesAdapter = new MessagesListAdapter(this, user, messages);
                list.setAdapter(messagesAdapter);
            } else
                messagesAdapter.setMessages(messages).notifyDataSetChanged();
        });

        messagesService.viewConversation(recipient, request);

    }

    private void load() {

        String requestUuid = getIntent().getStringExtra("request");

        if (requestUuid == null)
            return;

        userService.current((user) -> {
            this.user = user;
            requestService.getRequest(requestUuid, (request) -> {
                this.request = request;

                if (request.userId == user.id)
                    recipient = request.location.user;
                else
                    recipient = request.user;

                loadMessageList(user, recipient, request);
            });

        });
    }

    private void sendMessage(ApiMessage message) {

        if (message.recipientId == 0 || message.message.equals(""))
            return;

        messagesService.sendMessage(message, (mMessage) -> {
            messageInp.setText("");
            loadMessageList(user, recipient, request);
        });

    }
}
