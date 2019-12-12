package com.dev.wedrive.api;

import lombok.Getter;
import lombok.Setter;

public class ApiResponse<T> {

    @Getter
    @Setter
    public String success;

    @Getter
    @Setter
    protected T data;

    @Getter
    protected ApiResponseError error;

    public final static class Success<T> extends ApiResponse{

        public Success(T data){
            this.data = data;
        }

    }

    public final static class Fail extends ApiResponse{

        public Fail(ApiResponseError error){
            this.error = error;
        }

    }

}
