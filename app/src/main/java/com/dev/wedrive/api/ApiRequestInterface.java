package com.dev.wedrive.api;

import com.dev.wedrive.entity.ApiRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiRequestInterface {

    @POST("/request/create")
    public Call<ApiResponse<ApiRequest>> createRequest(@Query("location") String uuid, @Body ApiRequest apiRequest);

}
