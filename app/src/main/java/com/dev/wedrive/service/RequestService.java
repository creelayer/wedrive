package com.dev.wedrive.service;

import androidx.arch.core.util.Function;

import com.dev.wedrive.api.ApiResponse;
import com.dev.wedrive.api.Callback;
import com.dev.wedrive.entity.ApiLocation;
import com.dev.wedrive.entity.ApiRequest;

public class RequestService {

    public void createReuest(ApiLocation location, ApiRequest request, final Function<ApiRequest, Void> func) {
        ApiService.getInstance().request().createRequest(location.uuid, request).enqueue(new Callback<ApiResponse<ApiRequest>>() {
            @Override
            public void onResult(ApiResponse response) {
                if (response instanceof ApiResponse.Success) {
                    func.apply((ApiRequest) response.getData());
                }
            }
        });
    }

}
