package com.dev.wedrive.api;

import org.json.JSONObject;

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
            } catch (Exception e) {}

            ApiResponse.Fail fail = new ApiResponse.Fail(error);
            onResult(fail);
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {

    }

    public abstract void onResult(ApiResponse response);
}
