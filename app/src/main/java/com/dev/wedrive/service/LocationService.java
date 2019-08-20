package com.dev.wedrive.service;

import android.arch.core.util.Function;

import com.dev.wedrive.entity.ApiCurrentLocation;
import com.dev.wedrive.entity.ApiLocation;
import com.dev.wedrive.entity.ApiResponse;
import com.dev.wedrive.entity.ApiRoute;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocationService {

    /**
     * @param location
     * @param func
     */
    public void createLocation(ApiLocation location, Function<ApiLocation, Void> func) {

        ApiService.getInstance().location().createLocation(location).enqueue(new Callback<ApiResponse<ApiLocation>>() {
            @Override
            public void onResponse(Call<ApiResponse<ApiLocation>> call, Response<ApiResponse<ApiLocation>> response) {
                if (response.isSuccessful()) {
                    func.apply(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<ApiLocation>> call, Throwable t) {

            }
        });
    }

    /**
     * @param location
     * @param func
     */
    public void updateLocation(ApiLocation location, Function<ApiLocation, Void> func) {

        ApiService.getInstance().location().updateLocation(location.uuid, location).enqueue(new Callback<ApiResponse<ApiLocation>>() {
            @Override
            public void onResponse(Call<ApiResponse<ApiLocation>> call, Response<ApiResponse<ApiLocation>> response) {
                if (response.isSuccessful()) {
                    func.apply(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<ApiLocation>> call, Throwable t) {

            }
        });
    }

    /**
     * @param location
     * @param func
     */
    public void deleteLocation(ApiLocation location, Function<ApiLocation, Void> func) {

        ApiService.getInstance().location().deleteLocation(location.uuid).enqueue(new Callback<ApiResponse<ApiLocation>>() {
            @Override
            public void onResponse(Call<ApiResponse<ApiLocation>> call, Response<ApiResponse<ApiLocation>> response) {
                if (response.isSuccessful()) {
                    func.apply(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<ApiLocation>> call, Throwable t) {

            }
        });
    }


    /**
     * Update my location
     *
     * @param latLng
     */
    public void updateMyLocation(LatLng latLng) {
        ApiService.getInstance().currentLocation().putLocation(new ApiCurrentLocation(latLng)).enqueue(new Callback<ApiResponse<ApiCurrentLocation>>() {
            @Override
            public void onResponse(Call<ApiResponse<ApiCurrentLocation>> call, Response<ApiResponse<ApiCurrentLocation>> response) {

            }

            @Override
            public void onFailure(Call<ApiResponse<ApiCurrentLocation>> call, Throwable t) {

            }
        });
    }

    /**
     * @param func
     */
    public void getNearestCurrentLocations(String type, final Function<List<ApiCurrentLocation>, Void> func) {

        ApiService.getInstance().currentLocation().getLocations(type).enqueue(new Callback<ApiResponse<List<ApiCurrentLocation>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<ApiCurrentLocation>>> call, Response<ApiResponse<List<ApiCurrentLocation>>> response) {
                if (response.isSuccessful()) {
                    func.apply(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<ApiCurrentLocation>>> call, Throwable t) {

            }
        });
    }

    /**
     * @param route
     * @param func
     */
    public void getLocations(ApiRoute route, final Function<List<ApiLocation>, Void> func) {

        Call<ApiResponse<List<ApiLocation>>> call = null;

        if (route != null) {
            call = ApiService.getInstance().location().getLocations(route.uuid);
        } else {
            call = ApiService.getInstance().location().getLocations();
        }

        call.enqueue(new Callback<ApiResponse<List<ApiLocation>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<ApiLocation>>> call, Response<ApiResponse<List<ApiLocation>>> response) {
                if (response.isSuccessful()) {
                    func.apply(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<ApiLocation>>> call, Throwable t) {

            }
        });

    }

}
