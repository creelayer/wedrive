package com.dev.wedrive.service;

import com.dev.wedrive.entity.ApiResponse;
import com.dev.wedrive.entity.ApiResponseError;
import com.dev.wedrive.entity.ApiToken;
import com.dev.wedrive.api.ApiLocationInterface;
import com.dev.wedrive.api.ApiProfileInterface;
import com.dev.wedrive.api.ApiRouteInterface;
import com.dev.wedrive.api.ApiUserInterface;

import org.json.JSONObject;

import java.io.IOException;

import lombok.Getter;
import lombok.Setter;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiService {

    private static final String API_URL = "http://wedrive.smarter.com.ua";

    private Retrofit mRetrofit;

    private static ApiService apiServiceInstance;

    @Setter
    @Getter
    private ApiToken token;

    /**
     *
     */
    private ApiService() {

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
        Interceptor headerInterceptor = new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request.Builder newRequest = chain.request().newBuilder();

                token = new ApiToken("0eHLTLNOcozQZ8cgQqk3jdiv375gw0_1");

                if (token != null) {
                    newRequest.addHeader("Authorization", "Bearer " + token.accessToken);
                }

                return chain.proceed(newRequest.build());
            }
        };


        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(headerInterceptor)
                .build();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    /**
     * @return
     */
    public static ApiService getInstance() {
        if (apiServiceInstance == null) {
            apiServiceInstance = new ApiService();
        }

        return apiServiceInstance;
    }

    /**
     * @return
     */
    public ApiRouteInterface route() {
        return mRetrofit.create(ApiRouteInterface.class);
    }

    /**
     * @return
     */
    public ApiLocationInterface location() {
        return mRetrofit.create(ApiLocationInterface.class);
    }

    /**
     * @return
     */
    public ApiProfileInterface profile() {
        return mRetrofit.create(ApiProfileInterface.class);
    }

    /**
     * @return
     */
    public ApiUserInterface user() {
        return mRetrofit.create(ApiUserInterface.class);
    }


    /**
     *
     * @param response
     * @param <T>
     * @return
     */
    public static <T> T getData( Response<ApiResponse<T>> response){
        return (T) response.body().getData();
    }

    /**
     *
     * @param response
     * @return
     */
    public static ApiResponseError parseError(Response response) {

        ApiResponseError error = new ApiResponseError();


        try {
            JSONObject jObjError = new JSONObject(response.errorBody().string());

            error.setName(jObjError.getJSONObject("data").getString("name"));
            error.setMessage(jObjError.getJSONObject("data").getString("message"));

        } catch (Exception e) {

        }

        return error;
    }

}
