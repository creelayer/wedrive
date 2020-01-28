package com.dev.wedrive.service;

import com.dev.wedrive.api.ApiResponse;
import com.dev.wedrive.api.Callback;
import com.dev.wedrive.entity.ApiDeviceToken;
import androidx.core.util.Consumer;

public class DeviceTokenService {

    public void add(ApiDeviceToken token, Consumer<ApiDeviceToken> func) {
        ApiService.getInstance().deviceToken().addToken(token).enqueue(new Callback<ApiResponse<ApiDeviceToken>>() {
            @Override
            public void onResult(ApiResponse response) {
                if (response instanceof ApiResponse.Success)
                    func.accept((ApiDeviceToken) response.getData());
            }
        });
    }

}
