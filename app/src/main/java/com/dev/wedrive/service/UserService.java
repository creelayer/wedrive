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
        ApiService.getInstance().user().getUser(id).enqueue(new Callback<ApiResponse<ApiUser>>() {
            @Override
            public void onResult(ApiResponse response) {
                if (response instanceof ApiResponse.Success)
                    func.accept((ApiUser) response.getData());
                else
                    func2.accept(response.getError());
            }
        });
    }

    public void getUser(String phone, final Consumer<ApiUser> func, final Consumer<ApiResponseError> func2) {
        ApiService.getInstance().user().getUser(phone).enqueue(new Callback<ApiResponse<ApiUser>>() {
            @Override
            public void onResult(ApiResponse response) {
                if (response instanceof ApiResponse.Success)
                    func.accept((ApiUser) response.getData());
                else
                    func2.accept(response.getError());
            }
        });
    }

    public void login(ApiUser user, final Consumer<ApiToken> func, final Consumer<ApiResponseError> func2) {
        ApiService.getInstance().user().login(user).enqueue(new Callback<ApiResponse<ApiToken>>() {
            @Override
            public void onResult(ApiResponse response) {
                if (response instanceof ApiResponse.Success) {
                    ApiToken token = (ApiToken) response.getData();
                    ApiService.getInstance().setToken(token);
                    func.accept(token);
                } else
                    func2.accept(response.getError());

            }
        });
    }

    public void register(ApiUser user, final Consumer<ApiToken> func, final Consumer<ApiResponseError> func2) {

        ApiService.getInstance().user().register(user).enqueue(new Callback<ApiResponse<ApiToken>>() {
            @Override
            public void onResult(ApiResponse response) {
                if (response instanceof ApiResponse.Success) {
                    ApiToken token = (ApiToken) response.getData();
                    ApiService.getInstance().setToken(token);
                    func.accept(token);
                } else
                    func2.accept(response.getError());

            }
        });
    }

    public void confirmRegistration(ApiToken token, ApiCode apiCode, final Consumer<ApiUser> func, final Consumer<ApiResponseError> func2) {

        ApiService.getInstance().user().confirmRegistration(token.userId, apiCode).enqueue(new Callback<ApiResponse<ApiUser>>() {
            @Override
            public void onResult(ApiResponse response) {
                if (response instanceof ApiResponse.Success)
                    func.accept((ApiUser) response.getData());
                else
                    func2.accept(response.getError());
            }
        });
    }


    public void getCode(ApiToken token, final Consumer<ApiCode> func) {

        ApiService.getInstance().user().getCode(token.userId).enqueue(new Callback<ApiResponse<ApiCode>>() {
            @Override
            public void onResult(ApiResponse response) {
                if (response instanceof ApiResponse.Success)
                    func.accept((ApiCode) response.getData());
            }
        });
    }

    public void generateCode(ApiToken token, final Consumer<ApiCode> func) {

        ApiService.getInstance().user().getCode(token.userId).enqueue(new Callback<ApiResponse<ApiCode>>() {
            @Override
            public void onResult(ApiResponse response) {
                if (response instanceof ApiResponse.Success)
                    func.accept((ApiCode) response.getData());
            }
        });
    }

}
