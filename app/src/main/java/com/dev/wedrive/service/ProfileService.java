package com.dev.wedrive.service;

import android.arch.core.util.Function;

import com.dev.wedrive.entity.ApiProfile;
import com.dev.wedrive.entity.ApiResponse;
import com.dev.wedrive.entity.ApiResponseError;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileService {


    /**
     * @param func
     * @param func2
     */
    public void getMyProfile(final Function<ApiProfile, Void> func, final Function<ApiResponseError, Void> func2) {
        ApiService.getInstance().profile().getMyProfile().enqueue(new Callback<ApiResponse<ApiProfile>>() {
            @Override
            public void onResponse(Call<ApiResponse<ApiProfile>> call, Response<ApiResponse<ApiProfile>> response) {
                if (response.isSuccessful()) {
                    func.apply(ApiService.getData(response));
                } else {
                    func2.apply(ApiService.parseError(response));
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<ApiProfile>> call, Throwable t) {
                func2.apply(new ApiResponseError("Undefined error"));
            }
        });
    }

    /**
     * @param profile
     * @param func
     */
    public void updateProfile(ApiProfile profile, final Function<ApiProfile, Void> func, final Function<ApiResponseError, Void> func2) {
        ApiService.getInstance().profile().updateProfile(profile).enqueue(new Callback<ApiResponse<ApiProfile>>() {
            @Override
            public void onResponse(Call<ApiResponse<ApiProfile>> call, Response<ApiResponse<ApiProfile>> response) {
                if (response.isSuccessful()) {
                    func.apply(ApiService.getData(response));
                } else {
                    func2.apply(ApiService.parseError(response));
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<ApiProfile>> call, Throwable t) {
                func2.apply(new ApiResponseError("Undefined error"));
            }
        });
    }
}
