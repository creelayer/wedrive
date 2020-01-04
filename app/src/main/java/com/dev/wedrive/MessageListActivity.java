package com.dev.wedrive;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.dev.wedrive.adapters.MessagesListAdapter;
import com.dev.wedrive.entity.ApiMessage;
import com.dev.wedrive.entity.ApiRequest;
import com.dev.wedrive.entity.ApiUser;
import com.dev.wedrive.service.MessagesService;
import com.dev.wedrive.service.RequestService;
import com.dev.wedrive.service.UserService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

import lombok.Getter;

public class MessageListActivity extends AppCompatActivity {

    private MessagesService messagesService;
    private RequestService requestService;
    private UserService userService;

    private Timer timer;
    private TimerTask timerTask;

    private ApiUser user;
    private ApiUser recipient;
    private ApiRequest request;
    private ApiMessage state;

    @Getter
    private EditText messageInp;
    private Button messageBtn;

    public MessageListActivity() {
        messagesService = new MessagesService();
        requestService = new RequestService();
        userService = new UserService();
        timer = new Timer();
    }

    @Override
    protected void onResume() {
        super.onResume();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                loadMessageList();
            }
        };
        timer.scheduleAtFixedRate(timerTask, 10000, 5000);
    }

    @Override
    protected void onPause() {
        timerTask.cancel();
        super.onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);

        messageInp = findViewById(R.id.message_inp);
        messageBtn = findViewById(R.id.message_btn);
        messageBtn.setOnClickListener((v) -> sendMessage());

        load();
    }

    private void load() {

        Bundle bundle = getIntent().getExtras();

        userService.current((user) -> {

            this.user = user;

            if (bundle != null && bundle.getString("recipient") != null)
                userService.getUser(Integer.parseInt(bundle.getString("recipient")), (mUser) -> {
                    recipient = mUser;
                    loadMessageList();
                }, (error) -> {
                });

            if (bundle != null && bundle.getString("request") != null)
                requestService.getRequest(bundle.getString("request"), (mRequest) -> {
                    request = mRequest;
                    if (request.userId == user.id)
                        recipient = request.location.user;
                    else
                        recipient = request.user;
                    loadMessageList();
                });

        }, (error) -> {
        });


    }

    private void sendMessage() {

        if (recipient == null || user == null || messageInp.getText().toString().trim().equals(""))
            return;

        ApiMessage message = new ApiMessage(this, recipient, request);

        messagesService.sendMessage(message, (mMessage) -> {
            messageInp.setText("");
            loadMessageList();
        });

    }

    private void loadMessageList() {

        if (recipient == null || user == null)
            return;

        if (request != null) {
            messagesService.getConversation(recipient, request, (messages) -> showList(messages));
            messagesService.viewConversation(recipient, request);
        } else {
            messagesService.getConversation(recipient, (messages) -> showList(messages));
            messagesService.viewConversation(recipient);
        }
    }


    private void showList(ArrayList<ApiMessage> messages) {


        ApiMessage message = messages.get(1);

        if (state != null && message != null && state.uuid.equals(message.uuid))
            return;

        state = message;

        Collections.reverse(messages);
        MessagesListAdapter adapter = new MessagesListAdapter(this, request.location.user, messages);
        ListView list = findViewById(R.id.message_list);
        list.post(() -> list.setSelection(adapter.getCount() - 1));
        list.setAdapter(adapter);
    }
}
