package com.dev.wedrive.interfaces;

import com.dev.wedrive.entity.ApiCurrentLocation;
import com.dev.wedrive.entity.ApiResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiCurrentLocationInterface {

    @GET("/current-location")
    public Call<ApiResponse<List<ApiCurrentLocation>>> getLocations(@Query("type") String type);

    @POST("/current-location/update")
    public Call<ApiResponse<ApiCurrentLocation>> putLocation(@Body ApiCurrentLocation apiLocation);
}
