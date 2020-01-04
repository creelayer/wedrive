package com.dev.wedrive.api;

import com.dev.wedrive.entity.ApiMessage;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiMessageInterface {

    @GET("/message/conversation?expand=created_at")
    public Call<ApiResponse<List<ApiMessage>>> getConversation(@Query("recipient") int id);

    @GET("/message/conversation?expand=created_at")
    public Call<ApiResponse<List<ApiMessage>>> getConversation(@Query("recipient") int id, @Query("request") String uuid);

    @GET("/message/view-conversation")
    public Call<ApiResponse> viewConversation(@Query("sender") int id);

    @GET("/message/view-conversation")
    public Call<ApiResponse> viewConversation(@Query("sender") int id, @Query("request") String uuid);

    @POST("/message/create")
    public Call<ApiResponse<ApiMessage>> createMessage(@Body ApiMessage apiMessage);
}
