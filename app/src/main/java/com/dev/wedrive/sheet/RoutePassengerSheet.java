package com.dev.wedrive.sheet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.wedrive.Constants;
import com.dev.wedrive.MapActivity;
import com.dev.wedrive.R;
import com.dev.wedrive.entity.ApiLocation;
import com.dev.wedrive.entity.ApiRequest;
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

public class RoutePassengerSheet extends Sheet implements View.OnClickListener {

    private RouteService routeService;
    private RequestService requestService;

    @Setter
    @Getter
    private ApiLocation location;
    private ApiRequest request;

    private TextView routeName;
    private TextView locationTime;

    private ImageView userImage;
    private TextView userName;

    private ImageView carImage;
    private TextView carBrand;
    private TextView carModel;
    private TextView carNumber;


    @Getter
    private EditText requestMessageInp;
    private TextView requestMessage;
    private TextView requestStatus;
    private Button requestBtn;
    private Button requestCancelBtn;

    public RoutePassengerSheet() {
        super();
        routeService = new RouteService();
        requestService = new RequestService();
        state = BottomSheetBehavior.STATE_COLLAPSED;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.sheet_passenger_route, container, false);

        userImage = view.findViewById(R.id.user_image);
        userName = view.findViewById(R.id.user_name);
        routeName = view.findViewById(R.id.route_name);
        locationTime = view.findViewById(R.id.location_time);
        carImage = view.findViewById(R.id.car_image);
        carBrand = view.findViewById(R.id.car_brand);
        carModel = view.findViewById(R.id.car_model);
        carNumber = view.findViewById(R.id.car_number);

        requestMessage = view.findViewById(R.id.request_message);
        requestMessageInp = view.findViewById(R.id.request_message_inp);
        requestStatus = view.findViewById(R.id.request_status);
        requestBtn = view.findViewById(R.id.request_btn);
        requestCancelBtn = view.findViewById(R.id.request_cancel_btn);
        requestBtn.setOnClickListener(this);
        requestCancelBtn.setOnClickListener(this);

        if (location != null)
            load();

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.request_btn) {
            ApiRequest request = new ApiRequest(this);
            requestService.createRequest(location, request, (mRequest) -> {

                this.request = mRequest;

                MapActivity activity = (MapActivity) getActivity();
                activity.getInformManager().show(new InformMessage().setHeader("Request send").setText("Your request send. Wait for reply.").setDelay(3000));

                showRequestInfo();
                collapse();
                return null;
            });
        }

        if (v.getId() == R.id.request_cancel_btn && request != null) {
            requestService.setStatus(request, ApiRequest.STATUS_CANCELED, (mRequest) -> {

                this.request = mRequest;

                MapActivity activity = (MapActivity) getActivity();
                activity.getInformManager().show(new InformMessage().setHeader("Request cancel!").setText("Your was canceled.").setDelay(3000));

                showRequestInfo();
                collapse();

                return null;
            });
        }

    }

    private void load() {
        routeService.getRoute(location.route.uuid, (route) -> {

            this.request = route.request;

            DriverLocationData locationData = new DriverLocationData((LinkedTreeMap<String, String>) location.data);
            if (route.user.profile.image != null)
                new DownloadImageTask(userImage).execute(Constants.API_URL + "/uploads/profile/" + FileHelper.getStyleName(route.user.profile.image, "sm"));

            userName.setText(route.user.profile.name + " " + route.user.profile.lastName);
            routeName.setText(route.name);
            locationTime.setText(locationData.hour + ":" + locationData.min);
            carBrand.setText(route.car.brand);
            carModel.setText(route.car.model);
            carNumber.setText(route.car.number);

            if (route.car.image != null)
                new DownloadImageTask(carImage).execute(Constants.API_URL + "/uploads/car/" + FileHelper.getStyleName(route.car.image, "sm"));

            showRequestInfo();
            return null;
        });
    }

    private void showRequestInfo() {

        if (request != null) {

            if (request.location.uuid.equals(location.uuid)) {
                requestMessage.setText(request.message == null ? "" : request.message.message);
                requestMessage.setVisibility(View.VISIBLE);

                if (request.status.equals(ApiRequest.STATUS_NEW))
                    requestCancelBtn.setVisibility(View.VISIBLE);

            } else {
                requestMessage.setVisibility(View.GONE);
                requestCancelBtn.setVisibility(View.GONE);
            }

            requestMessageInp.setVisibility(View.GONE);
            requestBtn.setVisibility(View.GONE);
            requestStatus.setVisibility(View.VISIBLE);
        } else {
            requestMessage.setVisibility(View.GONE);
            requestStatus.setVisibility(View.GONE);
            requestCancelBtn.setVisibility(View.GONE);
        }
    }


}
