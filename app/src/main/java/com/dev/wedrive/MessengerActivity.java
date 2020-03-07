package com.dev.wedrive;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dev.wedrive.adapters.MessagesChatListAdapter;
import com.dev.wedrive.adapters.MessagesListAdapter;
import com.dev.wedrive.entity.ApiMessage;
import com.dev.wedrive.entity.ApiMessageChat;
import com.dev.wedrive.entity.ApiRequest;
import com.dev.wedrive.entity.ApiUser;
import com.dev.wedrive.helpers.FileHelper;
import com.dev.wedrive.service.MessagesService;
import com.dev.wedrive.service.RequestService;
import com.dev.wedrive.service.UserService;
import com.dev.wedrive.util.ProfileImageUtil;

import java.util.Timer;
import java.util.TimerTask;

import lombok.Getter;

public class MessengerActivity extends AbstractAuthActivity {

    private MessagesService messagesService;
    private RequestService requestService;
    private UserService userService;

    private Timer timer;
    private TimerTask timerTask;

    protected ViewFlipper messengerFlipper;

    private MessagesChatListAdapter messagesChatAdapter;
    private MessagesListAdapter messagesAdapter;


    private ImageButton messageHeaderBackBtn;
    private ImageView messageHeaderImage;
    private TextView messageHeaderName;
    private TextView messageHeaderTime;

    @Getter
    private EditText messageInp;
    private ImageButton messageBtn;

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
        setContentView(R.layout.activity_messenger);

        messengerFlipper = findViewById(R.id.messengerFlipper);
        messageHeaderBackBtn = findViewById(R.id.message_header_back_btn);
        messageHeaderImage = findViewById(R.id.message_header_image);
        messageHeaderName = findViewById(R.id.message_header_name);
        messageHeaderTime = findViewById(R.id.message_header_time);
        messageInp = findViewById(R.id.message_inp);
        messageBtn = findViewById(R.id.message_btn);


        messageHeaderBackBtn.setOnClickListener((v) -> {
            messagesAdapter = null;
            messengerFlipper.showPrevious();
        });

        loadConversationsList();

    }

    private void loadConversationsList() {
        messagesService.getConversations((chats) -> {
            if (messagesChatAdapter == null) {
                RecyclerView list = findViewById(R.id.message_chats_list);
                final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                list.setLayoutManager(linearLayoutManager);
                messagesChatAdapter = new MessagesChatListAdapter(this, chats);
                messagesChatAdapter.setListener((v, position) -> loadConversation(messagesChatAdapter.getItem(position)));
                list.setAdapter(messagesChatAdapter);
            } else
                messagesChatAdapter.setChats(chats).notifyDataSetChanged();
        });
    }

    private void loadConversation(ApiMessageChat chat) {

        messagesService.getConversation(chat, (messages) -> {

            if (messagesAdapter == null)
                messagesService.getConversationInfo(chat, (info) -> {

                    if (info.recipient.profile.image != null)
                        ProfileImageUtil
                                .get()
                                .load(Constants.API_URL + "/uploads/profile/" + FileHelper.getStyleName(info.recipient.profile.image, "sm"))
                                .into(messageHeaderImage);

                    messageHeaderName.setText(info.recipient.profile.name + info.recipient.profile.lastName);

                    RecyclerView list = findViewById(R.id.message_list);
                    final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                    linearLayoutManager.setReverseLayout(true);
                    list.setLayoutManager(linearLayoutManager);
                    messagesAdapter = new MessagesListAdapter(this, info.recipient, messages);
                    list.setAdapter(messagesAdapter);
                    messengerFlipper.showNext();
                    messageBtn.setOnClickListener((v) -> sendMessage(new ApiMessage(info.recipient, messageInp.getText().toString())));
                });
            else
                messagesAdapter.setMessages(messages).notifyDataSetChanged();

        });

    }

//    private void loadMessageList(ApiUser user, ApiUser recipient, ApiRequest request) {
//
//        if (recipient == null || user == null || request == null)
//            return;
//
//        messagesService.getConversation(recipient, request, (messages) -> {
//            if (messagesAdapter == null) {
//                RecyclerView list = findViewById(R.id.message_list);
//                final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//                linearLayoutManager.setReverseLayout(true);
//                list.setLayoutManager(linearLayoutManager);
//                messagesAdapter = new MessagesListAdapter(this, user, messages);
//                list.setAdapter(messagesAdapter);
//            } else
//                messagesAdapter.setMessages(messages).notifyDataSetChanged();
//        });
//
//        messagesService.viewConversation(recipient, request);
//
//    }

//    private void load() {
//
//        String requestUuid = getIntent().getStringExtra("request");
//
//        if (requestUuid == null)
//            return;
//
//        userService.current((user) -> {
//            this.user = user;
//            requestService.getRequest(requestUuid, (request) -> {
//                this.request = request;
//
//                if (request.userId == user.id)
//                    recipient = request.location.user;
//                else
//                    recipient = request.user;
//
//                loadMessageList(user, recipient, request);
//            });
//
//        });
//    }

    private void sendMessage(ApiMessage message) {

        if (message.recipientId == 0 || message.message.equals(""))
            return;

        messagesService.sendMessage(message, (mMessage) -> {
            messageInp.setText("");
            loadConversation(mMessage.chat);
        });

    }
}
