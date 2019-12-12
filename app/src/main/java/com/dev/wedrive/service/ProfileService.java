package com.dev.wedrive.service;

import android.arch.core.util.Function;

import com.dev.wedrive.api.ApiResponse;
import com.dev.wedrive.api.ApiResponseError;
import com.dev.wedrive.api.Callback;
import com.dev.wedrive.entity.ApiProfile;

public class ProfileService {

    /**
     * @param func
     * @param func2
     */
    public void getMyProfile(final Function<ApiProfile, Void> func, final Function<ApiResponseError, Void> func2) {
        ApiService.getInstance().profile().getMyProfile().enqueue(new Callback<ApiResponse<ApiProfile>>() {
            @Override
            public void onResult(ApiResponse response) {
                if (response instanceof ApiResponse.Success) {
                    func.apply((ApiProfile) response.getData());
                } else {
                    func2.apply(response.getError());
                }
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
            public void onResult(ApiResponse response) {
                if (response instanceof ApiResponse.Success) {
                    func.apply((ApiProfile) response.getData());
                } else {
                    func2.apply(response.getError());
                }
            }
        });
    }
}
