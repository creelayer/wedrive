package com.dev.wedrive.api;

import lombok.Getter;
import lombok.Setter;

public class ApiResponseError {

    @Getter
    @Setter
    public String name;

    @Getter
    @Setter
    public String message;

    @Getter
    @Setter
    public int status;

    public ApiResponseError(){

    }

    public ApiResponseError(String message){
        this.message = message;
    }

}
