package com.dev.wedrive.interfaces;

import com.dev.wedrive.entity.ApiProfile;
import com.dev.wedrive.entity.ApiResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiProfileInterface {

    @GET("/profile")
    public Call<ApiResponse<ApiProfile>> getMyProfile();

    @POST("/profile/update")
    public Call<ApiResponse<ApiProfile>> updateProfile(@Body ApiProfile profile);
}
