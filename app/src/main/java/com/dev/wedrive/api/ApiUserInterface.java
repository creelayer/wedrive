package com.dev.wedrive.api;

import com.dev.wedrive.entity.ApiUser;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiUserInterface {

    @GET("/security/current")
    public Call<ApiResponse<ApiUser>> getCurrentUser();

    @GET("/security/info")
    public Call<ApiResponse<ApiUser>> getInfo(@Query("user") int id);

    @POST("/security/login")
    public Call<ApiResponse<ApiUser>> login(@Body ApiUser user);
}
