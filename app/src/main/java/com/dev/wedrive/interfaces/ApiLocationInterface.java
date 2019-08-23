package com.dev.wedrive.interfaces;

import com.dev.wedrive.entity.ApiLocation;
import com.dev.wedrive.entity.ApiResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiLocationInterface {


    @GET("/location")
    public Call<ApiResponse<List<ApiLocation>>> getLocations(@Query("type") String type);

    @POST("/location/position")
    public Call<ApiResponse<ApiLocation>> positionLocation(@Body ApiLocation apiLocation);

    @GET("/location/route")
    public Call<ApiResponse<List<ApiLocation>>> getRouteLocations(@Query("route") String uuid);

    @POST("/location/create")
    public Call<ApiResponse<ApiLocation>> createLocation(@Body ApiLocation location);

    @POST("/location/update")
    public Call<ApiResponse<ApiLocation>> updateLocation(@Query("uuid") String uuid, @Body ApiLocation location);

    @POST("/location/delete")
    public Call<ApiResponse<ApiLocation>> deleteLocation(@Query("uuid") String uuid);
}
