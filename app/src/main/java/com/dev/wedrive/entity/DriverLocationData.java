package com.dev.wedrive.entity;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class DriverLocationData {

    @SerializedName("time")
    @Getter
    @Setter
    @Accessors(chain = true)
    public String time;

    @SerializedName("gap")
    @Getter
    @Setter
    @Accessors(chain = true)
    public String gap;

}
