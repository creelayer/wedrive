package com.dev.wedrive.helpers;

import com.dev.wedrive.entity.ApiUser;

public class UserHelper {

    public static String getName(ApiUser user) {
        return user.profile.name + " " + user.profile.lastName;
    }

}
