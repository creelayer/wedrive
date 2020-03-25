package com.dev.wedrive.helpers;

import android.widget.ImageView;

import com.dev.wedrive.Constants;
import com.dev.wedrive.entity.ApiUser;
import com.dev.wedrive.util.ImageUtil;

public class UserHelper {


    public static void setAvatarImage(ApiUser user, ImageView view) {
        if (user.profile.image != null)
            ImageUtil
                    .get()
                    .load(Constants.API_URL + "/uploads/profile/" + FileHelper.getStyleName(user.profile.image, "sm"))
                    .into(view);
    }

    public static String getName(ApiUser user) {
        return user.profile.name + " " + user.profile.lastName;
    }

}
