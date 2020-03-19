package com.dev.wedrive.sheet;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dev.wedrive.CarListActivity;
import com.dev.wedrive.Constants;
import com.dev.wedrive.MapActivity;
import com.dev.wedrive.R;
import com.dev.wedrive.dialog.ConfirmDialog;
import com.dev.wedrive.dialog.CreateDriverLocationDialog;
import com.dev.wedrive.dialog.InformDialog;
import com.dev.wedrive.entity.ApiCar;
import com.dev.wedrive.entity.ApiLocation;
import com.dev.wedrive.entity.ApiRequest;
import com.dev.wedrive.entity.ApiRoute;
import com.dev.wedrive.entity.ApiUser;
import com.dev.wedrive.helpers.CarHelper;
import com.dev.wedrive.helpers.UserHelper;
import com.dev.wedrive.service.LocationService;
import com.dev.wedrive.service.UserService;
import com.dev.wedrive.util.DownloadImageTask;
import com.dev.wedrive.helpers.FileHelper;
import com.dev.wedrive.informs.InformMessage;
import com.dev.wedrive.service.RequestService;
import com.dev.wedrive.service.RouteService;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import lombok.Getter;
import lombok.Setter;

public class RouteSheet extends Sheet {

    protected RouteService routeService;
    protected RequestService requestService;
    protected UserService userService;
    protected LocationService locationService;

    @Setter
    @Getter
    private ApiLocation location;

    @Setter
    @Getter
    private ApiRoute route;

    private View view;

    private LinearLayout locationInfo;
    private TextView locationTime;
    private Button locationBtn;


    private Button routeActionBtn;

    private LinearLayout requestInfo;
    private EditText requestMessageInp;
    private TextView requestStatus;
    private TextView requestMessage;
    private Button requestBtn;
    private Button requestCancelBtn;

    public RouteSheet() {
        super();
        routeService = new RouteService();
        locationService = new LocationService();
        requestService = new RequestService();
        userService = new UserService();
        state = BottomSheetBehavior.STATE_COLLAPSED;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        view = inflater.inflate(R.layout.sheet_route, container, false);

        locationInfo = view.findViewById(R.id.location_info);
        locationTime = view.findViewById(R.id.location_time);
        locationBtn = view.findViewById(R.id.location_btn);

        routeActionBtn = view.findViewById(R.id.route_action_btn);
        requestInfo = view.findViewById(R.id.request_info);
        requestMessage = view.findViewById(R.id.request_message);
        requestStatus = view.findViewById(R.id.request_status);
        requestMessageInp = view.findViewById(R.id.request_message_inp);
        requestBtn = view.findViewById(R.id.request_btn);
        requestCancelBtn = view.findViewById(R.id.request_cancel_btn);


        userService.current((user) -> {
            routeService.getRoute(route != null ? route.uuid : location.routeUuid, (route) -> {
                this.route = route;

                createRouteLine(user, route);
                createCarLine(user, route.car);

                if (location != null) {
                    locationService.getLocation(location.uuid, (location) -> {
                        this.location = location;
                        createLocationLine(user, location);
                        createRequestLine(user, location);
                    });
                }

            });

        });


        return view;
    }


    private void createLocationLine(ApiUser user, ApiLocation location) {

        locationInfo.setVisibility(View.VISIBLE);

        locationTime.setText(location.hour + ":" + location.min);

        if (user.id.equals(location.userId)) {
            locationBtn.setOnClickListener((v) -> changeLocationDialog(location));
            locationBtn.setVisibility(View.VISIBLE);
        }

    }

    private void changeLocationDialog(ApiLocation location) {

        routeService.getCurrentRoute((route) -> {
            if (!route.status.equals(ApiRoute.STATUS_CURRENT))
                new InformDialog(getActivity()).setHeaderText("Inform").setMessageText("Route is active. Only current route location can edit.").create().show();
            else
                new CreateDriverLocationDialog((MapActivity) getActivity(), location).create().show();

        });
    }


    private void changeRouteState(ApiUser user, ApiRoute route) {

        new ConfirmDialog(getActivity())
                .setHeaderText("Confirm")
                .setMessageText(route.status.equals(ApiRoute.STATUS_CURRENT) ? "Run message..." : "Stop message...")
                .setOkListener((dialog) ->
                        routeService.setStatus(route, route.status.equals(ApiRoute.STATUS_CURRENT) ? ApiRoute.STATUS_ACTIVE : ApiRoute.STATUS_CURRENT, (mRoute) -> {
                            this.route = mRoute;
                            createRouteLine(user, mRoute);
                            dialog.cancel();
                        })
                )
                .create()
                .show();

    }

    private void createRequestLine(ApiUser user, ApiLocation location) {

        requestInfo.setVisibility(View.GONE);

        if (!user.id.equals(location.userId)) {

            requestInfo.setVisibility(View.VISIBLE);

            if (location.request != null) {
                requestCancelBtn.setVisibility(View.VISIBLE);
                requestStatus.setVisibility(View.VISIBLE);
                requestMessage.setVisibility(View.VISIBLE);
                requestBtn.setVisibility(View.GONE);
                requestMessageInp.setVisibility(View.GONE);

                requestMessage.setText(location.request.message.message);
                requestCancelBtn.setOnClickListener((v) -> cancelRequest(location.request));
            } else {
                requestStatus.setVisibility(View.GONE);
                requestMessage.setVisibility(View.GONE);
                requestCancelBtn.setVisibility(View.GONE);
                requestBtn.setVisibility(View.VISIBLE);
                requestMessageInp.setVisibility(View.VISIBLE);

                requestBtn.setOnClickListener((v) -> sendRequest(new ApiRequest(location.user, requestMessageInp.getText().toString())));
            }
        }

    }

    private void sendRequest(ApiRequest request) {
        requestService.createRequest(location, request, (mRequest) -> {
            MapActivity activity = (MapActivity) getActivity();
            activity.getInformManager().show(new InformMessage().setHeader("Request send").setText("Your request send. Wait for reply.").setDelay(3000));
            locationService.getLocation(location.uuid, location1 -> createRequestLine(mRequest.user, location1));
            collapse();
        });

    }

    private void cancelRequest(ApiRequest request) {
        requestService.setStatus(request, ApiRequest.STATUS_CANCELED, (mRequest) -> {
            MapActivity activity = (MapActivity) getActivity();
            activity.getInformManager().show(new InformMessage().setHeader("Request cancel!").setText("Your was canceled.").setDelay(3000));
            locationService.getLocation(location.uuid, location1 -> createRequestLine(mRequest.user, location1));
            collapse();
        });
    }

    private void createCarLine(ApiUser user, ApiCar car) {

        ImageView carImage = view.findViewById(R.id.car_image);
        TextView carBrand = view.findViewById(R.id.car_brand);
        TextView carModel = view.findViewById(R.id.car_model);
        TextView carNumber = view.findViewById(R.id.car_number);
        Button changeCarBtn = view.findViewById(R.id.car_change_btn);

        CarHelper.setCarImage(car, carImage);
        carBrand.setText(car.brand);
        carModel.setText(car.model);
        carNumber.setText(car.number);

        if (user.id.equals(car.userId)) {
            changeCarBtn.setOnClickListener((v) -> startActivity(new Intent(getActivity(), CarListActivity.class)));
            changeCarBtn.setVisibility(View.VISIBLE);
        }

    }

    private void createRouteLine(ApiUser user, ApiRoute route) {

        ImageView userImage = view.findViewById(R.id.user_image);
        TextView userName = view.findViewById(R.id.user_name);
        TextView routeName = view.findViewById(R.id.route_name);
        TextView routeStatus = view.findViewById(R.id.route_status);

        UserHelper.setAvatarImage(user, userImage);
        userName.setText(UserHelper.getName(user));
        routeName.setText(route.name);
        routeStatus.setText(route.status);

        if (user.id.equals(route.userId)) {
            routeActionBtn.setText(route.status.equals(ApiRoute.STATUS_CURRENT) ? getResources().getString(R.string.run_route) : getResources().getString(R.string.stop_route));
            routeActionBtn.setOnClickListener((v) -> changeRouteState(user, route));
            routeActionBtn.setVisibility(View.VISIBLE);
        }

        getActivity().findViewById(R.id.lftControls).setVisibility(route.status.equals(ApiRoute.STATUS_ACTIVE) ? View.INVISIBLE : View.VISIBLE);
    }

}
