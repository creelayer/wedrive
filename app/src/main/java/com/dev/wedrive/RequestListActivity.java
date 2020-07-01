package com.dev.wedrive;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dev.wedrive.adapters.RecyclerViewScrollListener;
import com.dev.wedrive.adapters.RequestListAdapter;
import com.dev.wedrive.data.Pager;
import com.dev.wedrive.data.Slice;
import com.dev.wedrive.dialog.ConfirmDialog;
import com.dev.wedrive.entity.ApiRequest;
import com.dev.wedrive.entity.ApiUser;
import com.dev.wedrive.service.RequestService;
import com.dev.wedrive.service.UserService;

import java.util.ArrayList;
import java.util.HashMap;

public class RequestListActivity extends AbstractAuthActivity implements RequestListAdapter.OnItemClickListener {

    private UserService userService;
    private RequestService requestService;
    private RequestListAdapter adapter;
    private RecyclerView recyclerView;
    private ApiUser user;

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

            this.user = user;

            requestService.getMyRequests((requests) -> {
                recyclerView = findViewById(R.id.request_list);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                adapter = new RequestListAdapter(this, this.user, requests);
                adapter.setListener(this);
                recyclerView.setAdapter(adapter);

                recyclerView.addOnScrollListener(new RecyclerViewScrollListener<ApiRequest>((top) ->
                        requestService.getMyRequests(new Pager(0), (items) -> {
                            HashMap<String, ApiRequest> data = new HashMap<String, ApiRequest>();
                            for (ApiRequest item : items) {
                                data.put(item.getUuid(), item);
                            }
                            top.reset(data);
                        })
                        , (bottom) ->
                        requestService.getMyRequests(new Pager(bottom.getData().size()), (items) -> {
                            HashMap<String, ApiRequest> data = new HashMap<String, ApiRequest>();
                            for (ApiRequest item : items) {
                                data.put(item.getUuid(), item);
                            }
                            bottom.add(data);
                        })
                ));
            });


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


        if (view.getId() == R.id.cancel_btn) //TODO: denied_btn
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
            startActivity(new Intent(this, MessengerActivity.class).putExtra("request", adapter.getItem(position).uuid));

    }

}
