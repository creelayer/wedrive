package com.dev.wedrive.helpers;

import android.widget.ImageView;

import com.dev.wedrive.Constants;
import com.dev.wedrive.entity.ApiProfile;
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

    public static void setAvatarImage(ApiProfile profile, ImageView view) {
        if (profile.image != null)
            ImageUtil
                    .get()
                    .load(Constants.API_URL + "/uploads/profile/" + FileHelper.getStyleName(profile.image, "sm"))
                    .into(view);
    }

    public static String getName(ApiUser user) {
        return user.profile.name + " " + user.profile.lastName;
    }

    public static String getName(ApiProfile profile) {
        return profile.name + " " + profile.lastName;
    }

    public static String phoneFormat(String phone) {

        if (phone.length() == 0) return "";

        return phone.replaceFirst("(\\d{2})(\\d{3})(\\d{3})(\\d{2})(\\d+)", "$1 ($2) $3-$4-$5");
    }

}
