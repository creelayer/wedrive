package com.dev.wedrive.service;

import android.arch.core.util.Function;

import com.dev.wedrive.entity.ApiResponse;
import com.dev.wedrive.entity.ApiRoute;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RouteService {

    /**
     * @param func
     */
    public void getMyRouts(String type, final Function<List<ApiRoute>, Void> func) {
        ApiService.getInstance().route().getMyRoutes(type).enqueue(new Callback<ApiResponse<List<ApiRoute>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<ApiRoute>>> call, Response<ApiResponse<List<ApiRoute>>> response) {
                if (response.isSuccessful()) {
                    func.apply(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<ApiRoute>>> call, Throwable t) {

            }
        });
    }


    /**
     * @param name
     */
    public void createRoute(String name, String type, Function<ApiRoute, Void> func) {

        ApiRoute route = new ApiRoute();
        route.name = name;
        route.seats = 4;
        route.type = type;

        ApiService.getInstance().route().createRoute(route).enqueue(new Callback<ApiResponse<ApiRoute>>() {
            @Override
            public void onResponse(Call<ApiResponse<ApiRoute>> call, Response<ApiResponse<ApiRoute>> response) {
                if (response.isSuccessful()) {
                    func.apply(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<ApiRoute>> call, Throwable t) {

            }
        });
    }

    /**
     * @param route
     * @param func
     */
    public void updateRoute(ApiRoute route, Function<ApiRoute, Void> func) {

        ApiService.getInstance().route().updateRoute(route.uuid, route).enqueue(new Callback<ApiResponse<ApiRoute>>() {
            @Override
            public void onResponse(Call<ApiResponse<ApiRoute>> call, Response<ApiResponse<ApiRoute>> response) {
                if (response.isSuccessful()) {
                    func.apply(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<ApiRoute>> call, Throwable t) {

            }
        });
    }

    /**
     * @param route
     * @param func
     */
    public void deleteRoute(ApiRoute route, Function<ApiRoute, Void> func) {

        ApiService.getInstance().route().deleteRoute(route.uuid).enqueue(new Callback<ApiResponse<ApiRoute>>() {
            @Override
            public void onResponse(Call<ApiResponse<ApiRoute>> call, Response<ApiResponse<ApiRoute>> response) {
                if (response.isSuccessful()) {
                    func.apply(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<ApiRoute>> call, Throwable t) {

            }
        });
    }

}
