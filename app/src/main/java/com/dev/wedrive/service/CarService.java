package com.dev.wedrive.service;

import androidx.arch.core.util.Function;

import com.dev.wedrive.api.ApiResponse;
import com.dev.wedrive.api.Callback;
import com.dev.wedrive.entity.ApiCar;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

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
     * @param func
     */
    public void getCar(String uuid, final Function<ApiCar, Void> func) {
        ApiService.getInstance().car().getInfo(uuid).enqueue(new Callback<ApiResponse<ApiCar>>() {
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
    public void setCurrent(ApiCar car, final Function<ApiCar, Void> func) {
        ApiService.getInstance().car().setActive(car.uuid).enqueue(new Callback<ApiResponse<ApiCar>>() {
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

    /**
     * @param car
     * @param func
     */
    public void deleteCar(ApiCar car, Function<ApiCar, Void> func) {
        ApiService.getInstance().car().deleteCar(car.uuid).enqueue(new Callback<ApiResponse<ApiCar>>() {
            @Override
            public void onResult(ApiResponse response) {
                if (response instanceof ApiResponse.Success) {
                    func.apply((ApiCar) response.getData());
                }
            }
        });
    }

    public void uploadImage(ApiCar car, String uri, final Function<ApiCar, Void> func) {
        try {

            File file = new File(uri);
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part multipartBody = MultipartBody.Part.createFormData("Car[upload]", file.getName(), requestFile);

            ApiService.getInstance().car().uploadImage(car.uuid, multipartBody).enqueue(new Callback<ApiResponse<ApiCar>>() {
                @Override
                public void onResult(ApiResponse response) {
                    if (response instanceof ApiResponse.Success) {
                        func.apply((ApiCar) response.getData());
                    }
                }
            });

        } catch (Exception e) {
            return;
        }
    }

}
