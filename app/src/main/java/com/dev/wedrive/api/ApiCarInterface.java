package com.dev.wedrive.api;

import com.dev.wedrive.entity.ApiCar;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiCarInterface {

    @GET("/car")
    public Call<ApiResponse<List<ApiCar>>> getMyCars();

    @POST("/car/info")
    public Call<ApiResponse<ApiCar>> getInfo(@Query("car") String uuid);

    @GET("/car/current")
    public Call<ApiResponse<ApiCar>> getCurrentCar();

    @POST("/car/set-active")
    public Call<ApiResponse<ApiCar>> setActive(@Query("car") String uuid);

    @POST("/car/create")
    public Call<ApiResponse<ApiCar>> createCar(@Body ApiCar apiCar);

    @POST("/car/update")
    public Call<ApiResponse<ApiCar>> updateCar(@Query("car") String uuid, @Body ApiCar apiCar);

    @POST("/car/delete")
    public Call<ApiResponse<ApiCar>> deleteCar(@Query("car") String uuid);

    @Multipart
    @POST("/car/image")
    public Call<ApiResponse<ApiCar>> uploadImage(@Query("car") String uuid, @Part MultipartBody.Part file);
}
