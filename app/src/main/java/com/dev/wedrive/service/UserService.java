package com.dev.wedrive.service;

import android.arch.core.util.Function;

import com.dev.wedrive.entity.ApiResponse;
import com.dev.wedrive.entity.ApiResponseError;
import com.dev.wedrive.entity.ApiToken;
import com.dev.wedrive.entity.ApiUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserService {

    public void login(ApiUser user, final Function<ApiUser, Void> func, final Function<ApiResponseError, Void> func2) {

        ApiService.getInstance().user().login(user).enqueue(new Callback<ApiResponse<ApiUser>>() {
            @Override
            public void onResponse(Call<ApiResponse<ApiUser>> call, Response<ApiResponse<ApiUser>> response) {
                if (response.isSuccessful()) {
                    ApiService.getInstance().setToken(new ApiToken(response.body().getData().authKey));
                    func.apply(ApiService.getData(response));
                } else {
                    func2.apply(ApiService.parseError(response));
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<ApiUser>> call, Throwable t) {
                func2.apply(new ApiResponseError("Undefined error"));
            }
        });
    }

}
