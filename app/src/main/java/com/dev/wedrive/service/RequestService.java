package com.dev.wedrive.service;

import androidx.arch.core.util.Function;

import com.dev.wedrive.api.ApiResponse;
import com.dev.wedrive.api.Callback;
import com.dev.wedrive.entity.ApiLocation;
import com.dev.wedrive.entity.ApiRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RequestService {


    public void getRequests(final Function<ArrayList<ApiRequest>, Void> func) {
        ApiService.getInstance().request().getRequests().enqueue(new Callback<ApiResponse<ArrayList<ApiRequest>>>() {
            @Override
            public void onResult(ApiResponse response) {
                if (response instanceof ApiResponse.Success) {
                    func.apply((ArrayList<ApiRequest>) response.getData());
                }
            }
        });
    }

    public void createRequest(ApiLocation location, ApiRequest request, final Function<ApiRequest, Void> func) {
        ApiService.getInstance().request().createRequest(location.uuid, request).enqueue(new Callback<ApiResponse<ApiRequest>>() {
            @Override
            public void onResult(ApiResponse response) {
                if (response instanceof ApiResponse.Success) {
                    func.apply((ApiRequest) response.getData());
                }
            }
        });
    }

    public void setStatus(ApiRequest request, String status, final Function<ApiRequest, Void> func) {
        Map<String, String> mStatus = new HashMap<>();
        mStatus.put("status", status);
        ApiService.getInstance().request().setStatus(request.uuid, mStatus).enqueue(new Callback<ApiResponse<ApiRequest>>() {
            @Override
            public void onResult(ApiResponse response) {
                func.apply((ApiRequest) response.getData());
            }
        });
    }

}
