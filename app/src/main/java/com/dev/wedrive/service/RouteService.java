package com.dev.wedrive.service;

import android.arch.core.util.Function;

import com.dev.wedrive.api.ApiResponse;
import com.dev.wedrive.api.Callback;
import com.dev.wedrive.entity.ApiRoute;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RouteService {

    public void getMyRouts(String type, final Function<ArrayList<ApiRoute>, Void> func) {
        ApiService.getInstance().route().getMyRoutes(type).enqueue(new Callback<ApiResponse<List<ApiRoute>>>() {
            @Override
            public void onResult(ApiResponse response) {
                if (response instanceof ApiResponse.Success) {
                    func.apply((ArrayList<ApiRoute>) response.getData());
                }
            }
        });
    }

    public void getRoute(String uuid, final Function<ApiRoute, Void> func) {
        ApiService.getInstance().route().getRoute(uuid).enqueue(new Callback<ApiResponse<ApiRoute>>() {
            @Override
            public void onResult(ApiResponse response) {
                if (response instanceof ApiResponse.Success) {
                    func.apply((ApiRoute) response.getData());
                }
            }
        });
    }

    public void getCurrentRoute(final Function<ApiRoute, Void> func) {
        ApiService.getInstance().route().getCurrentRoute().enqueue(new Callback<ApiResponse<ApiRoute>>() {
            @Override
            public void onResult(ApiResponse response) {
                if (response instanceof ApiResponse.Success) {
                    func.apply((ApiRoute) response.getData());
                }
            }
        });
    }

    public void setStatus(ApiRoute route, String status, final Function<ApiRoute, Void> func) {

        Map<String, String> mStatus = new HashMap<>();
        mStatus.put("status", status);

        ApiService.getInstance().route().setStatus(route.uuid, mStatus).enqueue(new Callback<ApiResponse<ApiRoute>>() {
            @Override
            public void onResult(ApiResponse response) {
                func.apply((ApiRoute) response.getData());
            }
        });
    }

    public void createRoute(ApiRoute route, Function<ApiRoute, Void> func) {
        ApiService.getInstance().route().createRoute(route).enqueue(new Callback<ApiResponse<ApiRoute>>() {
            @Override
            public void onResult(ApiResponse response) {
                if (response instanceof ApiResponse.Success) {
                    func.apply((ApiRoute) response.getData());
                }
            }
        });
    }

    public void updateRoute(ApiRoute route, Function<ApiRoute, Void> func) {
        ApiService.getInstance().route().updateRoute(route.uuid, route).enqueue(new Callback<ApiResponse<ApiRoute>>() {
            @Override
            public void onResult(ApiResponse response) {
                if (response instanceof ApiResponse.Success) {
                    func.apply((ApiRoute) response.getData());
                }
            }
        });
    }

    public void deleteRoute(ApiRoute route, Function<ApiRoute, Void> func) {
        ApiService.getInstance().route().deleteRoute(route.uuid).enqueue(new Callback<ApiResponse<ApiRoute>>() {
            @Override
            public void onResult(ApiResponse response) {
                if (response instanceof ApiResponse.Success) {
                    func.apply((ApiRoute) response.getData());
                }
            }
        });
    }

}
