package com.dev.wedrive.service;

import androidx.core.util.Consumer;

import com.dev.wedrive.api.ApiResponse;
import com.dev.wedrive.api.ApiResponseError;
import com.dev.wedrive.api.Callback;
import com.dev.wedrive.entity.ApiCode;
import com.dev.wedrive.entity.ApiToken;
import com.dev.wedrive.entity.ApiUser;

public class UserService {

    public void current(final Consumer<ApiUser> func) {
        ApiService.getInstance().user().getCurrentUser().enqueue(new Callback<ApiResponse<ApiUser>>() {
            @Override
            public void onResult(ApiResponse response) {
                if (response instanceof ApiResponse.Success)
                    func.accept((ApiUser) response.getData());
            }
        });
    }

    public void getUser(int id, final Consumer<ApiUser> func, final Consumer<ApiResponseError> func2) {
        ApiService.getInstance().user().getInfo(id).enqueue(new Callback<ApiResponse<ApiUser>>() {
            @Override
            public void onResult(ApiResponse response) {
                if (response instanceof ApiResponse.Success)
                    func.accept((ApiUser) response.getData());
                else
                    func2.accept(response.getError());
            }
        });
    }

    public void login(ApiUser user, final Consumer<ApiUser> func, final Consumer<ApiResponseError> func2) {
        ApiService.getInstance().user().login(user).enqueue(new Callback<ApiResponse<ApiUser>>() {
            @Override
            public void onResult(ApiResponse response) {
                if (response instanceof ApiResponse.Success) {
                    ApiUser user = (ApiUser) response.getData();
                    ApiService.getInstance().setToken(new ApiToken(user.authKey));
                    func.accept(user);
                } else {
                    ApiService.getInstance().setToken(null);
                    func2.accept(response.getError());
                }
            }
        });
    }

    public void register(ApiUser user, final Consumer<ApiUser> func, final Consumer<ApiResponseError> func2) {

        ApiService.getInstance().user().register(user).enqueue(new Callback<ApiResponse<ApiUser>>() {
            @Override
            public void onResult(ApiResponse response) {
                if (response instanceof ApiResponse.Success) {
                    ApiUser user = (ApiUser) response.getData();
                    ApiService.getInstance().setToken(new ApiToken(user.authKey));
                    func.accept(user);
                } else {
                    ApiService.getInstance().setToken(null);
                    func2.accept(response.getError());
                }
            }
        });
    }

    public void confirmRegistration(ApiUser user, ApiCode apiCode, final Consumer<ApiUser> func, final Consumer<ApiResponseError> func2) {

        ApiService.getInstance().user().confirmRegistration(user.id, apiCode).enqueue(new Callback<ApiResponse<ApiUser>>() {
            @Override
            public void onResult(ApiResponse response) {
                if (response instanceof ApiResponse.Success)
                    func.accept((ApiUser) response.getData());
                else
                    func2.accept(response.getError());
            }
        });
    }

    public void getCode(ApiUser user, final Consumer<ApiCode> func) {

        ApiService.getInstance().user().getCode(user.id).enqueue(new Callback<ApiResponse<ApiCode>>() {
            @Override
            public void onResult(ApiResponse response) {
                if (response instanceof ApiResponse.Success)
                    func.accept((ApiCode) response.getData());
            }
        });
    }

}
