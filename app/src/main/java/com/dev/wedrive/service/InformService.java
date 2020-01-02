package com.dev.wedrive.service;

import androidx.arch.core.util.Function;

import com.dev.wedrive.api.ApiResponse;
import com.dev.wedrive.api.Callback;
import com.dev.wedrive.entity.ApiInform;
import com.dev.wedrive.entity.ApiRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InformService {

    public void getInforms(final Function<ArrayList<ApiInform>, Void> func) {
        ApiService.getInstance().inform().getInforms().enqueue(new Callback<ApiResponse<List<ApiInform>>>() {
            @Override
            public void onResult(ApiResponse response) {
                if (response instanceof ApiResponse.Success) {
                    func.apply((ArrayList<ApiInform>) response.getData());
                }
            }
        });
    }

    public void getLast(final Function<ApiInform, Void> func) {
        ApiService.getInstance().inform().getLast().enqueue(new Callback<ApiResponse<ApiInform>>() {
            @Override
            public void onResult(ApiResponse response) {
                if (response instanceof ApiResponse.Success) {
                    func.apply((ApiInform) response.getData());
                }
            }
        });
    }

    public void setStatus(ApiInform inform, String status, final Function<ApiInform, Void> func) {
        Map<String, String> mStatus = new HashMap<>();
        mStatus.put("status", status);
        ApiService.getInstance().request().setStatus(inform.uuid, mStatus).enqueue(new Callback<ApiResponse<ApiRequest>>() {
            @Override
            public void onResult(ApiResponse response) {
                if (response instanceof ApiResponse.Success) {
                    func.apply((ApiInform) response.getData());
                }
            }
        });
    }

}
