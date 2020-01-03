package com.dev.wedrive.service;

import androidx.arch.core.util.Function;
import androidx.core.util.Consumer;

import com.dev.wedrive.api.ApiResponse;
import com.dev.wedrive.api.Callback;
import com.dev.wedrive.entity.ApiInform;

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

    public void getLast(final Consumer<ApiInform> func) {
        ApiService.getInstance().inform().getLast().enqueue(new Callback<ApiResponse<ApiInform>>() {
            @Override
            public void onResult(ApiResponse response) {
                if (response instanceof ApiResponse.Success) {
                    func.accept((ApiInform) response.getData());
                }
            }
        });
    }

    public void setStatus(ApiInform inform, String status) {
        setStatus(inform, status, (i) -> null);
    }

    public void setStatus(ApiInform inform, String status, final Function<ApiInform, Void> func) {
        Map<String, String> mStatus = new HashMap<>();
        mStatus.put("status", status);
        ApiService.getInstance().inform().setStatus(inform.uuid, mStatus).enqueue(new Callback<ApiResponse<ApiInform>>() {
            @Override
            public void onResult(ApiResponse response) {
                if (response instanceof ApiResponse.Success) {
                    func.apply((ApiInform) response.getData());
                }
            }
        });
    }

}
