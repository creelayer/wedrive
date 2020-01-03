package com.dev.wedrive.service;

import androidx.core.util.Consumer;

import com.dev.wedrive.api.ApiResponse;
import com.dev.wedrive.api.ApiResponseError;
import com.dev.wedrive.api.Callback;
import com.dev.wedrive.entity.ApiProfile;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ProfileService {

    /**
     * @param func
     * @param func2
     */
    public void getMyProfile(final Consumer<ApiProfile> func, final Consumer<ApiResponseError> func2) {
        ApiService.getInstance().profile().getMyProfile().enqueue(new Callback<ApiResponse<ApiProfile>>() {
            @Override
            public void onResult(ApiResponse response) {
                if (response instanceof ApiResponse.Success) {
                    func.accept((ApiProfile) response.getData());
                } else {
                    func2.accept(response.getError());
                }
            }
        });
    }


    /**
     * @param func
     * @param func2
     */
    public void getProfile(String uuid, final Consumer<ApiProfile> func, final Consumer<ApiResponseError> func2) {
        ApiService.getInstance().profile().getMyProfile().enqueue(new Callback<ApiResponse<ApiProfile>>() {
            @Override
            public void onResult(ApiResponse response) {
                if (response instanceof ApiResponse.Success) {
                    func.accept((ApiProfile) response.getData());
                } else {
                    func2.accept(response.getError());
                }
            }
        });
    }

    /**
     * @param profile
     * @param func
     */
    public void updateProfile(ApiProfile profile, final Consumer<ApiProfile> func, final Consumer<ApiResponseError> func2) {
        ApiService.getInstance().profile().updateProfile(profile).enqueue(new Callback<ApiResponse<ApiProfile>>() {
            @Override
            public void onResult(ApiResponse response) {
                if (response instanceof ApiResponse.Success) {
                    func.accept((ApiProfile) response.getData());
                } else {
                    func2.accept(response.getError());
                }
            }
        });
    }

    public void uploadImage(String uri, final Consumer<ApiProfile> func) {
        try {

            File file = new File(uri);
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part multipartBody = MultipartBody.Part.createFormData("Profile[upload]", file.getName(), requestFile);

            ApiService.getInstance().profile().uploadImage(multipartBody).enqueue(new Callback<ApiResponse<ApiProfile>>() {
                @Override
                public void onResult(ApiResponse response) {
                    if (response instanceof ApiResponse.Success) {
                        func.accept((ApiProfile) response.getData());
                    }
                }
            });

        } catch (Exception e) {
            return;
        }
    }
}
