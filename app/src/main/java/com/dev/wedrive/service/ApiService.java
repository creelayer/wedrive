package com.dev.wedrive.service;

import com.dev.wedrive.Constants;
import com.dev.wedrive.api.ApiCarInterface;
import com.dev.wedrive.api.ApiInformInterface;
import com.dev.wedrive.api.ApiLocationInterface;
import com.dev.wedrive.api.ApiMessageInterface;
import com.dev.wedrive.api.ApiProfileInterface;
import com.dev.wedrive.api.ApiRequestInterface;
import com.dev.wedrive.api.ApiResponse;
import com.dev.wedrive.api.ApiResponseError;
import com.dev.wedrive.api.ApiRouteInterface;
import com.dev.wedrive.api.ApiUserInterface;
import com.dev.wedrive.entity.ApiMessage;
import com.dev.wedrive.entity.ApiToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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

    private Retrofit mRetrofit;

    private static ApiService apiServiceInstance;

    @Setter
    @Getter
    private ApiToken token;

    private ApiService() {

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
        Interceptor headerInterceptor = new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request.Builder newRequest = chain.request().newBuilder();

                //TODO: delete
               // token = new ApiToken("0eHLTLNOcozQZ8cgQqk3jdiv375gw0_2");

                if (token != null)
                    newRequest.addHeader("Authorization", "Bearer " + token.accessToken);


                return chain.proceed(newRequest.build());
            }
        };

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(headerInterceptor)
                .build();

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss.SSS").create();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(Constants.API_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
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
    public ApiCarInterface car() {
        return mRetrofit.create(ApiCarInterface.class);
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
     * @return
     */
    public ApiRequestInterface request() {
        return mRetrofit.create(ApiRequestInterface.class);
    }

    /**
     * @return
     */
    public ApiInformInterface inform() {
        return mRetrofit.create(ApiInformInterface.class);
    }

    /**
     * @return
     */
    public ApiMessageInterface messages() {
        return mRetrofit.create(ApiMessageInterface.class);
    }


    /**
     * @param response
     * @param <T>
     * @return
     */
    public static <T> T getData(Response<ApiResponse<T>> response) {
        return (T) response.body().getData();
    }

    /**
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
