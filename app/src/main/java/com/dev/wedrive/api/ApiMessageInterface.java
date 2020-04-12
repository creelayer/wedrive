package com.dev.wedrive.api;

import com.dev.wedrive.entity.ApiMessage;
import com.dev.wedrive.entity.ApiMessageChat;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiMessageInterface {


    @GET("/message/conversations?expand=recipient.profile,message")
    public Call<ApiResponse<List<ApiMessageChat>>> getConversations();

    @GET("/message/conversation")
    public Call<ApiResponse<List<ApiMessage>>> getConversation(@Query("chat") String uuid);

    @GET("/message/info?expand=user,recipient.profile")
    public Call<ApiResponse<ApiMessageChat>> getConversationInfo(@Query("chat") String uuid);

//    @GET("/message/conversation?expand=created_at,recipient")
//    public Call<ApiResponse<List<ApiMessage>>> getConversation(@Query("recipient") int id);
//
//    @GET("/message/conversation?expand=created_at")
//    public Call<ApiResponse<List<ApiMessage>>> getConversation(@Query("recipient") int id, @Query("request") String uuid);
//
//    @GET("/message/view-conversation")
//    public Call<ApiResponse> viewConversation(@Query("sender") int id);
//
//    @GET("/message/view-conversation")
//    public Call<ApiResponse> viewConversation(@Query("sender") int id, @Query("request") String uuid);

    @POST("/message/create?expand=chat")
    public Call<ApiResponse<ApiMessage>> createMessage(@Body ApiMessage apiMessage);
}
