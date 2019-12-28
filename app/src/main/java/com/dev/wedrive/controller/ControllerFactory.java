package com.dev.wedrive.controller;

import com.dev.wedrive.MapActivity;
import com.dev.wedrive.entity.ApiProfile;
import com.dev.wedrive.service.MapService;

public class ControllerFactory {

    public static ControllerInterface make(ApiProfile profile, MapActivity mapActivity, MapService mapService) {
//
//        switch (profile.getType()) {
//            case MMarker.TYPE_DRIVER:
//                return new DriverController(mapActivity);
//
//            case MMarker.TYPE_PASSENGER:
//                return new PassengerController(mapActivity);
//        }

        return null;
    }

}
