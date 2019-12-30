package com.dev.wedrive.api;

import com.dev.wedrive.entity.ApiRoute;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiRouteInterface {

    @GET("/route")
    public Call<ApiResponse<List<ApiRoute>>> getMyRoutes(@Query("type") String type);

    @GET("/route/info")
    public Call<ApiResponse<ApiRoute>> getRoute(@Query("route") String uuid);

    @POST("/route/current")
    public Call<ApiResponse<ApiRoute>> getCurrentRoute();

    @POST("/route/status")
    public Call<ApiResponse<ApiRoute>> setStatus(@Query("route") String uuid, @Body Map<String, String> status);

    @POST("/route/create")
    public Call<ApiResponse<ApiRoute>> createRoute(@Body ApiRoute apiRoute);

    @POST("/route/update")
    public Call<ApiResponse<ApiRoute>> updateRoute(@Query("route") String uuid, @Body ApiRoute apiRoute);

    @POST("/route/delete")
    public Call<ApiResponse<ApiRoute>> deleteRoute(@Query("route") String uuid);
}
