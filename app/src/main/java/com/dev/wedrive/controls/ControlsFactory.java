package com.dev.wedrive.controls;

import com.dev.wedrive.MapActivity;
import com.dev.wedrive.entity.ApiProfile;
import com.dev.wedrive.loaders.LoaderLocationManager;

public class ControlsFactory {

    public static ControlsInterface create(MapActivity activity, ApiProfile profile, LoaderLocationManager loaderCollection) {

        switch (profile.type) {
            case ApiProfile.TYPE_DRIVER:
                return new DriverControls(activity, loaderCollection);

            case ApiProfile.TYPE_PASSENGER:
                return new PassengerControls(activity, loaderCollection);
        }

        return new PassengerControls(activity, loaderCollection);
    }

}
