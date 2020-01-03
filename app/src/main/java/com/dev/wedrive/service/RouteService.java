package com.dev.wedrive.service;

import androidx.core.util.Consumer;

import com.dev.wedrive.api.ApiResponse;
import com.dev.wedrive.api.Callback;
import com.dev.wedrive.entity.ApiRoute;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RouteService {

    public void getCurrentRoute(final Consumer<ApiRoute> func) {
        ApiService.getInstance().route().getCurrentRoute().enqueue(new Callback<ApiResponse<ApiRoute>>() {
            @Override
            public void onResult(ApiResponse response) {
                if (response instanceof ApiResponse.Success) {
                    func.accept((ApiRoute) response.getData());
                }
            }
        });
    }

    public void getMyRouts(final Consumer<ArrayList<ApiRoute>> func) {
        ApiService.getInstance().route().getMyRoutes().enqueue(new Callback<ApiResponse<List<ApiRoute>>>() {
            @Override
            public void onResult(ApiResponse response) {
                if (response instanceof ApiResponse.Success) {
                    func.accept((ArrayList<ApiRoute>) response.getData());
                }
            }
        });
    }

    public void getRoute(String uuid, final Consumer<ApiRoute> func) {
        ApiService.getInstance().route().getRoute(uuid).enqueue(new Callback<ApiResponse<ApiRoute>>() {
            @Override
            public void onResult(ApiResponse response) {
                if (response instanceof ApiResponse.Success) {
                    func.accept((ApiRoute) response.getData());
                }
            }
        });
    }

    public void setStatus(ApiRoute route, String status, final Consumer<ApiRoute> func) {

        Map<String, String> mStatus = new HashMap<>();
        mStatus.put("status", status);

        ApiService.getInstance().route().setStatus(route.uuid, mStatus).enqueue(new Callback<ApiResponse<ApiRoute>>() {
            @Override
            public void onResult(ApiResponse response) {
                func.accept((ApiRoute) response.getData());
            }
        });
    }

    public void createRoute(ApiRoute route, Consumer<ApiRoute> func) {
        ApiService.getInstance().route().createRoute(route).enqueue(new Callback<ApiResponse<ApiRoute>>() {
            @Override
            public void onResult(ApiResponse response) {
                if (response instanceof ApiResponse.Success) {
                    func.accept((ApiRoute) response.getData());
                }
            }
        });
    }

    public void updateRoute(ApiRoute route, Consumer<ApiRoute> func) {
        ApiService.getInstance().route().updateRoute(route.uuid, route).enqueue(new Callback<ApiResponse<ApiRoute>>() {
            @Override
            public void onResult(ApiResponse response) {
                if (response instanceof ApiResponse.Success) {
                    func.accept((ApiRoute) response.getData());
                }
            }
        });
    }

    public void deleteRoute(ApiRoute route, Consumer<ApiRoute> func) {
        ApiService.getInstance().route().deleteRoute(route.uuid).enqueue(new Callback<ApiResponse<ApiRoute>>() {
            @Override
            public void onResult(ApiResponse response) {
                if (response instanceof ApiResponse.Success) {
                    func.accept((ApiRoute) response.getData());
                }
            }
        });
    }

}
