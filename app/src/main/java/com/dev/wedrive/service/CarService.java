package com.dev.wedrive.service;

import android.arch.core.util.Function;

import com.dev.wedrive.api.ApiResponse;
import com.dev.wedrive.api.Callback;
import com.dev.wedrive.entity.ApiCar;

import java.util.ArrayList;
import java.util.List;

public class CarService {

    /**
     * @param func
     */
    public void getMyCars(final Function<ArrayList<ApiCar>, Void> func) {
        ApiService.getInstance().car().getMyCars().enqueue(new Callback<ApiResponse<List<ApiCar>>>() {
            @Override
            public void onResult(ApiResponse response) {
                if (response instanceof ApiResponse.Success) {
                    func.apply((ArrayList<ApiCar>) response.getData());
                }
            }
        });
    }

    /**
     * @param car
     * @param func
     */
    public void setCurrent(ApiCar car, final Function<ApiCar, Void> func) {
        ApiService.getInstance().car().setCurrent(car.uuid).enqueue(new Callback<ApiResponse<ApiCar>>() {
            @Override
            public void onResult(ApiResponse response) {
                func.apply((ApiCar) response.getData());
            }
        });
    }

    /**
     * @param car
     * @param func
     */
    public void createCar(ApiCar car, Function<ApiCar, Void> func) {
        ApiService.getInstance().car().createCar(car).enqueue(new Callback<ApiResponse<ApiCar>>() {
            @Override
            public void onResult(ApiResponse response) {
                if (response instanceof ApiResponse.Success) {
                    func.apply((ApiCar) response.getData());
                }
            }
        });
    }

    /**
     * @param car
     * @param func
     */
    public void updateCar(ApiCar car, Function<ApiCar, Void> func) {
        ApiService.getInstance().car().updateCar(car.uuid, car).enqueue(new Callback<ApiResponse<ApiCar>>() {
            @Override
            public void onResult(ApiResponse response) {
                if (response instanceof ApiResponse.Success) {
                    func.apply((ApiCar) response.getData());
                }
            }
        });
    }

}
