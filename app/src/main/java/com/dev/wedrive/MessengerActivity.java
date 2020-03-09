package com.dev.wedrive;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.recyclerview.widget.RecyclerView;

import com.dev.wedrive.adapters.MessagesChatListAdapter;
import com.dev.wedrive.adapters.MessagesListAdapter;
import com.dev.wedrive.adapters.MessagesListFactory;
import com.dev.wedrive.entity.ApiMessage;
import com.dev.wedrive.entity.ApiMessageChat;
import com.dev.wedrive.helpers.FileHelper;
import com.dev.wedrive.helpers.UserHelper;
import com.dev.wedrive.service.MessagesService;
import com.dev.wedrive.service.RequestService;
import com.dev.wedrive.service.UserService;
import com.dev.wedrive.util.ProfileImageUtil;

import java.util.Timer;
import java.util.TimerTask;

public class MessengerActivity extends AbstractAuthActivity {

    private MessagesService messagesService;
    private RequestService requestService;
    private UserService userService;

    private Timer timer;
    private TimerTask timerTask;

    private MessagesListFactory messagesListFactory;
    private RecyclerView chatsList;
    private RecyclerView messagesList;
    private ApiMessageChat currentChat;

    protected ViewFlipper messengerFlipper;
    private ImageView messageHeaderImage;
    private TextView messageHeaderName;
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
//
//                if(currentChat != null)
//                    loadConversation(currentChat);
//                else
//                    loadConversationsList();
//            }
//        };
//        timer.scheduleAtFixedRate(timerTask, 10000, 5000);
    }

    @Override
    protected void onPause() {
        //   timerTask.cancel();
        super.onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);

        userService.current((user) -> {
            messagesListFactory = new MessagesListFactory(this, user);
            loadConversationsList();
        });


        messengerFlipper = findViewById(R.id.messengerFlipper);
        messageHeaderImage = findViewById(R.id.message_header_image);
        messageHeaderName = findViewById(R.id.message_header_name);
        messageInp = findViewById(R.id.message_inp);
        messageBtn = findViewById(R.id.message_btn);

        findViewById(R.id.message_header_back_btn).setOnClickListener((v) -> {
            messengerFlipper.showPrevious();
            loadConversationsList();
        });

        findViewById(R.id.message_header_exit_btn).setOnClickListener((v) -> finish());


    }

    private void loadConversationsList() {

        currentChat = null;
        messagesList = null;

        messagesService.getConversations((chats) -> {
            if (chatsList != null)
                ((MessagesChatListAdapter) chatsList.getAdapter()).update(chats);
            else
                chatsList = messagesListFactory.createChatList(chats, (chat) -> {
                    messengerFlipper.showNext();
                    loadConversation(chat);
                });
        });
    }

    private void loadConversation(ApiMessageChat chat) {

        currentChat = chat;
        messagesService.getConversation(chat, (messages) -> {
            if (messagesList == null) {
                messagesService.getConversationInfo(chat, (info) -> {
                    if (info.recipient.profile.image != null)
                        ProfileImageUtil
                                .get()
                                .load(Constants.API_URL + "/uploads/profile/" + FileHelper.getStyleName(info.recipient.profile.image, "sm"))
                                .into(messageHeaderImage);

                    messageHeaderName.setText(UserHelper.getName(info.recipient));
                    messagesList = messagesListFactory.createMessageList(messages, info.recipient);
                    messageBtn.setOnClickListener((v) -> sendMessage(new ApiMessage(info.recipient, messageInp.getText().toString())));
                });
            } else
                ((MessagesListAdapter) messagesList.getAdapter()).update(messages);

        });

    }

    private void sendMessage(ApiMessage message) {

        if (message.recipientId == 0 || message.message.equals(""))
            return;

        messagesService.sendMessage(message, (mMessage) -> {
            messageInp.setText("");
            loadConversation(mMessage.chat);
        });

    }
}
