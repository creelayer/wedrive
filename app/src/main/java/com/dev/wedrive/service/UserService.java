package com.dev.wedrive.service;

import androidx.core.util.Consumer;

import com.dev.wedrive.api.ApiResponse;
import com.dev.wedrive.api.ApiResponseError;
import com.dev.wedrive.api.Callback;
import com.dev.wedrive.entity.ApiUser;

public class UserService {

    public void login(ApiUser user, final Consumer<ApiUser> func, final Consumer<ApiResponseError> func2) {

        ApiService.getInstance().user().login(user).enqueue(new Callback<ApiResponse<ApiUser>>() {
            @Override
            public void onResult(ApiResponse response) {
                if (response instanceof ApiResponse.Success) {
                    func.accept((ApiUser) response.getData());
                } else {
                    func2.accept(response.getError());
                }
            }
        });
    }

}
