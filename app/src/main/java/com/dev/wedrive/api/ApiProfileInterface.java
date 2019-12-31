package com.dev.wedrive.api;

import com.dev.wedrive.entity.ApiProfile;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiProfileInterface {

    @GET("/profile")
    public Call<ApiResponse<ApiProfile>> getMyProfile();

    @GET("/profile/info")
    public Call<ApiResponse<ApiProfile>> getProfile(@Query("profile") String uuid);

    @POST("/profile/update")
    public Call<ApiResponse<ApiProfile>> updateProfile(@Body ApiProfile profile);

    @Multipart
    @POST("/profile/image")
    public Call<ApiResponse<ApiProfile>> uploadImage(@Part MultipartBody.Part file);
}
