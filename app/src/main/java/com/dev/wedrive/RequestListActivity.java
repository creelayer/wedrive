package com.dev.wedrive;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.dev.wedrive.adapters.RequestListAdapter;
import com.dev.wedrive.entity.ApiRequest;
import com.dev.wedrive.service.RequestService;

public class RequestListActivity extends AppCompatActivity {

    private RequestService requestService;

    public RequestListActivity() {
        requestService = new RequestService();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_list);

        loadRequestList();

    }


    private void loadRequestList() {
        requestService.getRequests((requests) -> {
            ListView list = findViewById(R.id.request_list);
            list.setAdapter(new RequestListAdapter(this, requests, (id, request, adapter) -> {

                if (id == R.id.denied_btn)
                    requestService.setStatus(request, ApiRequest.STATUS_DENIED);

                if (id == R.id.accept_btn)
                    requestService.setStatus(request, ApiRequest.STATUS_ACCEPTED);

            }));
        });
    }
}
