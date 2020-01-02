package com.dev.wedrive.api;

import com.dev.wedrive.entity.ApiInform;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInformInterface {

    @GET("/inform")
    public Call<ApiResponse<List<ApiInform>>> getInforms();

    @POST("/inform/last")
    public Call<ApiResponse<ApiInform>> getLast();

    @POST("/inform/status")
    public Call<ApiResponse<ApiInform>> setStatus(@Query("inform") String uuid, @Body Map<String, String> status);
}
