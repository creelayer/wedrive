package com.dev.wedrive.entity;

import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.LinkedTreeMap;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class DriverLocationData {

    @SerializedName("hour")
    @Accessors(chain = true)
    @Getter
    @Setter
    public String hour;

    @SerializedName("min")
    @Accessors(chain = true)
    @Getter
    @Setter
    public String min;

    @SerializedName("gap")
    @Getter
    @Setter
    @Accessors(chain = true)
    public String gap;


    public DriverLocationData(){

    }

    public DriverLocationData(String hour, String min, String gap){
        this.hour =  hour;
        this.min =  min;
        this.gap = gap;
    }

    public DriverLocationData(LinkedTreeMap<String, String> map){
    }

}
