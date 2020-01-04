package com.dev.wedrive.api;

import com.dev.wedrive.entity.ApiRequest;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiRequestInterface {

    @GET("/request")
    public Call<ApiResponse<ArrayList<ApiRequest>>> getRequests();

    @GET("/request/info?expand=location.user")
    public Call<ApiResponse<ApiRequest>> getInfo(@Query("request") String uuid);

    @POST("/request/create")
    public Call<ApiResponse<ApiRequest>> createRequest(@Query("location") String uuid, @Body ApiRequest apiRequest);

    @POST("/request/status")
    public Call<ApiResponse<ApiRequest>> setStatus(@Query("request") String uuid, @Body Map<String, String> status);

}
