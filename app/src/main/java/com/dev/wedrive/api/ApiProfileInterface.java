package com.dev.wedrive.api;

import com.dev.wedrive.entity.ApiProfile;
import com.dev.wedrive.entity.ApiResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiProfileInterface {

    @GET("/profile")
    public Call<ApiResponse<ApiProfile>> getMyProfile();

    @POST("/profile/update")
    public Call<ApiResponse<ApiProfile>> updateProfile(@Body ApiProfile profile);
}
