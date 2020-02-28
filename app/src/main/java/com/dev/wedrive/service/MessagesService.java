package com.dev.wedrive.service;

import androidx.core.util.Consumer;

import com.dev.wedrive.api.ApiResponse;
import com.dev.wedrive.api.Callback;
import com.dev.wedrive.entity.ApiInform;
import com.dev.wedrive.entity.ApiMessage;
import com.dev.wedrive.entity.ApiMessageChat;
import com.dev.wedrive.entity.ApiRequest;
import com.dev.wedrive.entity.ApiUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessagesService {


    public void getConversations( final Consumer<ArrayList<ApiMessageChat>> func) {
        ApiService.getInstance().messages().getConversations().enqueue(new Callback<ApiResponse<List<ApiMessageChat>>>() {
            @Override
            public void onResult(ApiResponse response) {
                func.accept((ArrayList<ApiMessageChat>) response.getData());
            }
        });
    }

    public void getConversation(ApiUser recipient, final Consumer<ArrayList<ApiMessage>> func) {
        ApiService.getInstance().messages().getConversation(recipient.id).enqueue(new Callback<ApiResponse<List<ApiMessage>>>() {
            @Override
            public void onResult(ApiResponse response) {
                if (response instanceof ApiResponse.Success) {
                    func.accept((ArrayList<ApiMessage>) response.getData());
                }
            }
        });
    }

    public void getConversation(ApiUser recipient, ApiRequest request, final Consumer<ArrayList<ApiMessage>> func) {
        ApiService.getInstance().messages().getConversation(recipient.id, request.uuid).enqueue(new Callback<ApiResponse<List<ApiMessage>>>() {
            @Override
            public void onResult(ApiResponse response) {
                if (response instanceof ApiResponse.Success) {
                    func.accept((ArrayList<ApiMessage>) response.getData());
                }
            }
        });
    }

    public void viewConversation(ApiUser sender) {
        ApiService.getInstance().messages().viewConversation(sender.id).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResult(ApiResponse response) {

            }
        });
    }

    public void viewConversation(ApiUser sender, ApiRequest request) {
        ApiService.getInstance().messages().viewConversation(sender.id, request.uuid).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResult(ApiResponse response) {

            }
        });
    }

    public void sendMessage(ApiMessage message, final Consumer<ApiMessage> func) {
        ApiService.getInstance().messages().createMessage(message).enqueue(new Callback<ApiResponse<ApiMessage>>() {
            @Override
            public void onResult(ApiResponse response) {
                func.accept((ApiMessage) response.getData());
            }
        });
    }


}
