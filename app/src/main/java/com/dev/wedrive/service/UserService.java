package com.dev.wedrive.service;

import androidx.arch.core.util.Function;

import com.dev.wedrive.api.ApiResponse;
import com.dev.wedrive.api.ApiResponseError;
import com.dev.wedrive.api.Callback;
import com.dev.wedrive.entity.ApiUser;

public class UserService {

    public void login(ApiUser user, final Function<ApiUser, Void> func, final Function<ApiResponseError, Void> func2) {

        ApiService.getInstance().user().login(user).enqueue(new Callback<ApiResponse<ApiUser>>() {
            @Override
            public void onResult(ApiResponse response) {
                if (response instanceof ApiResponse.Success) {
                    func.apply((ApiUser) response.getData());
                } else {
                    func2.apply(response.getError());
                }
            }
        });
    }

}
