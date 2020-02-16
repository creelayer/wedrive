package com.dev.wedrive.util;

import android.graphics.Color;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Transformation;

public class ProfileImageUtil  {

    private static ProfileImageUtil profileImageUtil;

    private Transformation transformation;


    private ProfileImageUtil(){

        transformation = new RoundedTransformationBuilder()
                .borderColor(Color.WHITE)
                .borderWidthDp(2)
                .cornerRadiusDp(50)
                .oval(false)
                .build();
    }

    public static ProfileImageUtil get() {

        if (profileImageUtil == null)
            profileImageUtil = new ProfileImageUtil();

        return profileImageUtil;
    }

    public RequestCreator load(String url){
        return  Picasso.get()
                .load(url)
                .fit()
                .transform(transformation);
    }

}
