package com.dev.wedrive;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dev.wedrive.adapters.RequestListAdapter;
import com.dev.wedrive.dialog.ConfirmDialog;
import com.dev.wedrive.entity.ApiRequest;
import com.dev.wedrive.service.RequestService;
import com.dev.wedrive.service.UserService;

public class RequestListActivity extends AppCompatActivity implements RequestListAdapter.OnItemClickListener {

    private UserService userService;
    private RequestService requestService;
    private RequestListAdapter adapter;

    public RequestListActivity() {
        userService = new UserService();
        requestService = new RequestService();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_list);

        loadRequestList();

    }

    private void loadRequestList() {

        userService.current((user) -> {

            requestService.getMyRequests((requests) -> {
                // set up the RecyclerView
                RecyclerView list = findViewById(R.id.request_list);
                list.setLayoutManager(new LinearLayoutManager(this));
                adapter = new RequestListAdapter(this, user, requests);
                adapter.setListener(this);
                list.setAdapter(adapter);
            });


        }, (error) -> {
        });
    }

    @Override
    public void onItemClick(View view, int position) {

        if (view.getId() == R.id.accept_btn)
            new ConfirmDialog(this)
                    .setHeaderText("Confirm")
                    .setMessageText("Confirm accept click")
                    .setOkListener((dialog) ->
                            requestService.setStatus(adapter.getItem(position), ApiRequest.STATUS_ACCEPTED, (request) -> {
                                adapter.updateItem(position, request);
                                dialog.cancel();
                            })
                    )
                    .create()
                    .show();


        if (view.getId() == R.id.denied_btn)
            new ConfirmDialog(this)
                    .setHeaderText("Confirm")
                    .setMessageText("Confirm denied click")
                    .setOkListener((dialog) ->
                            requestService.setStatus(adapter.getItem(position), ApiRequest.STATUS_DENIED, (request) -> {
                                adapter.updateItem(position, request);
                                dialog.cancel();
                            })
                    )
                    .create()
                    .show();


        if (view.getId() == R.id.cancel_btn)
            new ConfirmDialog(this)
                    .setHeaderText("Confirm")
                    .setMessageText("Confirm cancel click")
                    .setOkListener((dialog) ->
                            requestService.setStatus(adapter.getItem(position), ApiRequest.STATUS_CANCELED, (request) -> {
                                adapter.updateItem(position, request);
                                dialog.cancel();
                            })
                    )
                    .create()
                    .show();

        if (view.getId() == R.id.message_btn)
            startActivity(new Intent(this, MessageListActivity.class).putExtra("request", adapter.getItem(position).uuid));

    }

}
