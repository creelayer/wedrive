package com.dev.wedrive.helpers;

import android.widget.ImageView;

import com.dev.wedrive.Constants;
import com.dev.wedrive.entity.ApiCar;
import com.dev.wedrive.util.ProfileImageUtil;

public class CarHelper {

    public static void setCarImage(ApiCar car, ImageView view) {
        if (car.image != null)
            ProfileImageUtil
                    .get()
                    .load(Constants.API_URL + "/uploads/car/" + FileHelper.getStyleName(car.image, "sm"))
                    .into(view);
    }

}
