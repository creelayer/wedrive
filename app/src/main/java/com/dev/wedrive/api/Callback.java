package com.dev.wedrive.api;

import com.dev.wedrive.entity.ApiToken;
import com.dev.wedrive.service.ApiService;

import org.json.JSONObject;

import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Response;

public abstract class Callback<T> implements retrofit2.Callback<T> {

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.isSuccessful()) {
            ApiResponse resp = (ApiResponse) response.body();
            ApiResponse.Success success = new ApiResponse.Success<>(resp.getData());
            onResult(success);

        } else {

            ApiResponseError error = new ApiResponseError();

            try {
                String data = response.errorBody().string();
                JSONObject jObjError = new JSONObject(data);
                error.setName(jObjError.getJSONObject("data").getString("name"));
                error.setMessage(jObjError.getJSONObject("data").getString("message"));
                error.setStatus(Integer.valueOf(jObjError.getJSONObject("data").getString("status")));

            } catch (Exception e) {
            }

            if (error.status == HttpURLConnection.HTTP_UNAUTHORIZED && ApiService.getInstance().getToken() != null) {

                ApiService.getInstance().user().refreshToken(ApiService.getInstance().getToken()).enqueue(new Callback<ApiResponse<ApiToken>>() {
                    @Override
                    public void onResult(ApiResponse response) {
                        if (response instanceof ApiResponse.Success) {
                            ApiToken token = (ApiToken) response.getData();
                            ApiService.getInstance().setToken(token);
                        } else
                            ApiService.getInstance().setToken(null);
                    }
                });

            } else {
                ApiResponse.Fail fail = new ApiResponse.Fail(error);
                onResult(fail);
            }

        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
    }

    public abstract void onResult(ApiResponse response);
}
