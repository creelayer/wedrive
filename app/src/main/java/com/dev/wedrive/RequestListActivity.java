package com.dev.wedrive;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.dev.wedrive.adapters.RequestListAdapter;
import com.dev.wedrive.entity.ApiRequest;
import com.dev.wedrive.service.RequestService;
import com.dev.wedrive.service.UserService;

public class RequestListActivity extends AppCompatActivity {

    private UserService userService;
    private RequestService requestService;

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

                RequestListAdapter adapter = new RequestListAdapter(this, user, requests);
                adapter.setListener((id, request) -> {

                    if (id == R.id.accept_btn)
                        requestService.setStatus(request, ApiRequest.STATUS_ACCEPTED, (mRequest) -> adapter.updateControlsState(mRequest));

                    if (id == R.id.denied_btn)
                        requestService.setStatus(request, ApiRequest.STATUS_DENIED, (mRequest) -> adapter.updateControlsState(mRequest));

                    if (id == R.id.cancel_btn)
                        requestService.setStatus(request, ApiRequest.STATUS_CANCELED, (mRequest) -> adapter.updateControlsState(mRequest));

                    if (id == R.id.message_btn)
                        startActivity(new Intent(this, MessageListActivity.class).putExtra("request", request.uuid));

                });

                ListView list = findViewById(R.id.request_list);
                list.setAdapter(adapter);
            });


        }, (error) -> {
        });


    }
}
