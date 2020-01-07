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
import com.dev.wedrive.entity.ApiLocation;
import com.dev.wedrive.entity.ApiRequest;
import com.dev.wedrive.entity.ApiRoute;
import com.dev.wedrive.entity.DriverLocationData;
import com.dev.wedrive.util.DownloadImageTask;
import com.dev.wedrive.helpers.FileHelper;
import com.dev.wedrive.informs.InformMessage;
import com.dev.wedrive.service.RequestService;
import com.dev.wedrive.service.RouteService;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.gson.internal.LinkedTreeMap;

import lombok.Getter;
import lombok.Setter;

public class RouteSheet extends Sheet {

    private RouteService routeService;
    private RequestService requestService;

    @Setter
    @Getter
    private ApiLocation location;

    @Setter
    @Getter
    private ApiRoute route;


    private TextView routeName;
    private TextView routeStatus;
    private TextView locationTime;
    private ImageView userImage;
    private TextView userName;
    private ImageView carImage;
    private TextView carBrand;
    private TextView carModel;
    private TextView carNumber;
    private Button changeCarBtn;
    private Button actionRouteBtn;

    private LinearLayout requestInfo;
    private EditText requestMessageInp;
    private TextView requestStatus;
    private TextView requestMessage;
    private Button requestBtn;
    private Button requestCancelBtn;

    public RouteSheet() {
        super();
        routeService = new RouteService();
        requestService = new RequestService();
        state = BottomSheetBehavior.STATE_COLLAPSED;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.sheet_route, container, false);

        userImage = view.findViewById(R.id.user_image);
        userName = view.findViewById(R.id.user_name);
        routeName = view.findViewById(R.id.route_name);
        routeStatus = view.findViewById(R.id.route_status);
        locationTime = view.findViewById(R.id.location_time);
        carImage = view.findViewById(R.id.car_image);
        carBrand = view.findViewById(R.id.car_brand);
        carModel = view.findViewById(R.id.car_model);
        carNumber = view.findViewById(R.id.car_number);
        changeCarBtn = view.findViewById(R.id.car_change_btn);
        changeCarBtn.setOnClickListener((v) -> startActivity(new Intent(getActivity(), CarListActivity.class)));
        actionRouteBtn = view.findViewById(R.id.action_run_btn);
        requestInfo = view.findViewById(R.id.request_info);
        requestMessage = view.findViewById(R.id.request_message);
        requestStatus = view.findViewById(R.id.request_status);
        requestMessageInp = view.findViewById(R.id.request_message_inp);
        requestBtn = view.findViewById(R.id.request_btn);
        requestCancelBtn = view.findViewById(R.id.request_cancel_btn);

        routeService.getRoute(route != null ? route.uuid : location.route.uuid, (route) -> {
            createRouteData(route, location);
            updateControlsState(route.request, location);
        });

        return view;
    }

    private void updateControlsState(ApiRequest request, ApiLocation location) {

        if (request == null) {
            requestCancelBtn.setVisibility(View.GONE);
            requestMessage.setVisibility(View.GONE);
            requestStatus.setVisibility(View.GONE);
            requestMessageInp.setVisibility(View.VISIBLE);
            requestBtn.setVisibility(View.VISIBLE);
        } else {
            requestMessageInp.setVisibility(View.GONE);
            requestBtn.setVisibility(View.GONE);
            requestStatus.setVisibility(View.VISIBLE);
            requestMessage.setVisibility(View.VISIBLE);
            requestCancelBtn.setVisibility(View.VISIBLE);
        }

        if (location == null) {
            requestInfo.setVisibility(View.GONE);
            actionRouteBtn.setVisibility(View.VISIBLE);
            changeCarBtn.setVisibility(View.VISIBLE);
        } else {
            actionRouteBtn.setVisibility(View.GONE);
            changeCarBtn.setVisibility(View.GONE);
            requestInfo.setVisibility(View.VISIBLE);
        }

    }

    private void changeRouteState(ApiRoute route) {

        new ConfirmDialog(getActivity())
                .setHeaderText("Confirm")
                .setMessageText(route.status.equals(ApiRoute.STATUS_CURRENT) ? "Run message..." : "Stop message...")
                .setOkListener((dialog) ->

                    routeService.setStatus(route, route.status.equals(ApiRoute.STATUS_CURRENT) ? ApiRoute.STATUS_ACTIVE : ApiRoute.STATUS_CURRENT, (mRoute) -> {
                        this.route = mRoute;
                        routeStatus.setText(mRoute.status);
                        actionRouteBtn.setText(mRoute.status.equals(ApiRoute.STATUS_CURRENT) ? "Run" : "Stop");
                        actionRouteBtn.setOnClickListener((v) -> changeRouteState(mRoute));

                        getActivity().findViewById(R.id.lftControls).setVisibility(mRoute.status.equals(ApiRoute.STATUS_ACTIVE) ? View.INVISIBLE : View.VISIBLE);
                        dialog.cancel();
                    })


                )
                .create()
                .show();

    }

    private void sendRequest(ApiRequest request) {
        requestService.createRequest(location, request, (mRequest) -> {
            MapActivity activity = (MapActivity) getActivity();
            activity.getInformManager().show(new InformMessage().setHeader("Request send").setText("Your request send. Wait for reply.").setDelay(3000));
            requestMessage.setText(mRequest.message == null ? "" : mRequest.message.message);
            requestCancelBtn.setOnClickListener((v) -> cancelRequest(mRequest));
            updateControlsState(mRequest, location);
            collapse();
        });

    }

    private void cancelRequest(ApiRequest request) {
        requestService.setStatus(request, ApiRequest.STATUS_CANCELED, (mRequest) -> {
            MapActivity activity = (MapActivity) getActivity();
            activity.getInformManager().show(new InformMessage().setHeader("Request cancel!").setText("Your was canceled.").setDelay(3000));
            updateControlsState(null, location);
            collapse();
        });
    }

    private void createRouteData(ApiRoute route, ApiLocation location) {

        if (route.user.profile.image != null)
            new DownloadImageTask(userImage).execute(Constants.API_URL + "/uploads/profile/" + FileHelper.getStyleName(route.user.profile.image, "sm"));

        if (route.car.image != null)
            new DownloadImageTask(carImage).execute(Constants.API_URL + "/uploads/car/" + FileHelper.getStyleName(route.car.image, "sm"));

        userName.setText(route.user.profile.name + " " + route.user.profile.lastName);
        routeName.setText(route.name);
        routeStatus.setText(route.status);
        actionRouteBtn.setText(route.status.equals(ApiRoute.STATUS_CURRENT) ? "Run" : "Stop");
        actionRouteBtn.setOnClickListener((v) -> changeRouteState(route));
        carBrand.setText(route.car.brand);
        carModel.setText(route.car.model);
        carNumber.setText(route.car.number);

        if (location != null) {
            DriverLocationData locationData = new DriverLocationData((LinkedTreeMap<String, String>) location.data);
            locationTime.setText(locationData.hour + ":" + locationData.min);
        }

        if (route.request != null) {
            requestMessage.setText(route.request.message == null ? "" : route.request.message.message);
            requestCancelBtn.setOnClickListener((v) -> cancelRequest(route.request));
        }

        requestBtn.setOnClickListener((v) -> sendRequest(new ApiRequest(route.user, requestMessageInp.getText().toString())));


        getActivity().findViewById(R.id.lftControls).setVisibility(route.status.equals(ApiRoute.STATUS_ACTIVE) ? View.INVISIBLE : View.VISIBLE);
    }

}
