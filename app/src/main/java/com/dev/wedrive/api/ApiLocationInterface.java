package com.dev.wedrive.api;

import com.dev.wedrive.entity.ApiLocation;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiLocationInterface {


    @GET("/location/nearest")
    public Call<ApiResponse<List<ApiLocation>>> getNearestLocations();

    @GET("/location/info")
    public Call<ApiResponse<ApiLocation>> getLocationInfo(@Query("location") String uuid);

    @POST("/location/position")
    public Call<ApiResponse<ApiLocation>> positionLocation(@Body ApiLocation apiLocation);

    @GET("/location/route")
    public Call<ApiResponse<List<ApiLocation>>> getRouteLocations(@Query("route") String uuid);

    @POST("/location/create")
    public Call<ApiResponse<ApiLocation>> createLocation(@Body ApiLocation location);

    @POST("/location/update")
    public Call<ApiResponse<ApiLocation>> updateLocation(@Query("location") String uuid, @Body ApiLocation location);

    @POST("/location/delete")
    public Call<ApiResponse<ApiLocation>> deleteLocation(@Query("location") String uuid);
}
