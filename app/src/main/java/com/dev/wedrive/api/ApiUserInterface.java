package com.dev.wedrive.api;

import com.dev.wedrive.entity.ApiCode;
import com.dev.wedrive.entity.ApiToken;
import com.dev.wedrive.entity.ApiUser;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiUserInterface {

    @GET("/security/current")
    public Call<ApiResponse<ApiUser>> getCurrentUser();

    @GET("/user/view")
    public Call<ApiResponse<ApiUser>> getUser(@Query("user") int id);

    @GET("/user/find")
    public Call<ApiResponse<ApiUser>> getUser(@Query("phone") String phone);

    @GET("/security/code")
    public Call<ApiResponse<ApiCode>> getCode(@Query("user") int id);

    @POST("/security/register")
    public Call<ApiResponse<ApiToken>> register(@Body ApiUser user);

    @POST("/security/confirm-registration")
    public Call<ApiResponse<ApiUser>> confirmRegistration(@Body ApiCode code);

    @POST("/security/login")
    public Call<ApiResponse<ApiToken>> login(@Body ApiUser user);

    @POST("/security/refresh")
    public Call<ApiResponse<ApiToken>> refreshToken(@Body ApiToken token);
}
