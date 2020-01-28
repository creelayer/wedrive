package com.dev.wedrive.api;

import com.dev.wedrive.entity.ApiDeviceToken;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiDeviceTokenInterface {

    @POST("/app/token")
    public Call<ApiResponse<ApiDeviceToken>> addToken(@Body ApiDeviceToken apiDeviceToken);

}
