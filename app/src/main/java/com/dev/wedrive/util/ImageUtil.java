package com.dev.wedrive.util;

import android.graphics.Color;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Transformation;

public class ImageUtil {

    private static ImageUtil imageUtil;

    private Transformation transformation;

    private ImageUtil(){
        transformation = new RoundedTransformationBuilder()
                .borderColor(Color.WHITE)
                .borderWidthDp(3)
                .cornerRadiusDp(50)
                .oval(false)
                .build();
    }

    public static ImageUtil get() {

        if (imageUtil == null)
            imageUtil = new ImageUtil();

        return imageUtil;
    }

    public RequestCreator load(String url){
        return  Picasso.get()
                .load(url)
                .fit()
                .transform(transformation);
    }

}
