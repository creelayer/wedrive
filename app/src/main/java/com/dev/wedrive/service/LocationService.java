package com.dev.wedrive.service;

import android.arch.core.util.Function;

import com.dev.wedrive.api.ApiResponse;
import com.dev.wedrive.api.Callback;
import com.dev.wedrive.entity.ApiLocation;
import com.dev.wedrive.entity.ApiRoute;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class LocationService {

    /**
     * @param location
     * @param func
     */
    public void createLocation(ApiLocation location, Function<ApiLocation, Void> func) {

        ApiService.getInstance().location().createLocation(location).enqueue(new Callback<ApiResponse<ApiLocation>>() {
            @Override
            public void onResult(ApiResponse response) {
                if (response instanceof ApiResponse.Success) {
                    func.apply((ApiLocation) response.getData());
                }
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
            public void onResult(ApiResponse response) {
                if (response instanceof ApiResponse.Success) {
                    func.apply((ApiLocation) response.getData());
                }
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
            public void onResult(ApiResponse response) {
                if (response instanceof ApiResponse.Success) {
                    func.apply((ApiLocation) response.getData());
                }
            }
        });
    }


    /**
     * Update my location
     *
     * @param latLng
     */
    public void updateMyLocation(LatLng latLng) {
        ApiService.getInstance().location().positionLocation(new ApiLocation(latLng)).enqueue(new Callback<ApiResponse<ApiLocation>>() {
            @Override
            public void onResult(ApiResponse response) {

            }
        });
    }

    /**
     * @param func
     */
    public void getNearestLocations(final Function<List<ApiLocation>, Void> func) {

        ApiService.getInstance().location().getNearestLocations().enqueue(new Callback<ApiResponse<List<ApiLocation>>>() {
            @Override
            public void onResult(ApiResponse response) {
                if (response instanceof ApiResponse.Success) {
                    func.apply((List<ApiLocation>) response.getData());
                }
            }
        });
    }

    /**
     * @param route
     * @param func
     */
    public void getLocationsByRoute(ApiRoute route, final Function<List<ApiLocation>, Void> func) {
        ApiService.getInstance().location().getRouteLocations(route.uuid).enqueue(new Callback<ApiResponse<List<ApiLocation>>>() {
            @Override
            public void onResult(ApiResponse response) {
                if (response instanceof ApiResponse.Success) {
                    func.apply((List<ApiLocation>) response.getData());
                }
            }
        });

    }

    /**
     * @param location
     * @param func
     */
    public void getLocationInfo(ApiLocation location, final Function<ApiLocation, Void> func) {
        ApiService.getInstance().location().getLocationInfo(location.uuid).enqueue(new Callback<ApiResponse<ApiLocation>>() {
            @Override
            public void onResult(ApiResponse response) {
                func.apply((ApiLocation) response.getData());
            }
        });

    }

}
