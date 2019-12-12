package com.dev.wedrive.api;

import com.dev.wedrive.entity.ApiUser;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiUserInterface {

    @POST("/security/login")
    public Call<ApiResponse<ApiUser>> login(@Body ApiUser user);
}
