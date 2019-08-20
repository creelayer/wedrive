package com.dev.wedrive.interfaces;

import com.dev.wedrive.entity.ApiResponse;
import com.dev.wedrive.entity.ApiRoute;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiRouteInterface {

    @GET("/route")
    public Call<ApiResponse<List<ApiRoute>>> getMyRoutes();

    @POST("/route/create")
    public Call<ApiResponse<ApiRoute>> createRoute(@Body ApiRoute apiRoute);

    @POST("/route/update")
    public Call<ApiResponse<ApiRoute>> updateRoute(@Query("uuid") String uuid, @Body ApiRoute apiRoute);

    @POST("/route/delete")
    public Call<ApiResponse<ApiRoute>> deleteRoute(@Query("uuid") String uuid);
}
