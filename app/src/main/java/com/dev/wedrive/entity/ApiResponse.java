package com.dev.wedrive.entity;

import lombok.Getter;
import lombok.Setter;

public class ApiResponse<T> {

    public final static String SUCCESS = "success";

    @Getter
    @Setter
    public String success;

    @Getter
    @Setter
    public T data;

}
