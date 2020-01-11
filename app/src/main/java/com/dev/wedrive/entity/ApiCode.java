package com.dev.wedrive.entity;

import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

public class ApiCode {


    @SerializedName("id")
    @Getter
    public int id;

    @SerializedName("code")
    @Getter
    public String code;

    @SerializedName("created_at")
    @Setter
    @Getter
    public Timestamp createdAt;

    public ApiCode() {
    }

    public ApiCode(String code) {
        this.code = code;
    }

}
