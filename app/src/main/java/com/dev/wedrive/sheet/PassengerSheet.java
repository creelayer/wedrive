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
import com.dev.wedrive.helpers.FileHelper;
import com.dev.wedrive.informs.InformMessage;
import com.dev.wedrive.service.LocationService;
import com.dev.wedrive.service.RequestService;
import com.dev.wedrive.service.RouteService;
import com.dev.wedrive.service.UserService;
import com.dev.wedrive.util.DownloadImageTask;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import lombok.Getter;
import lombok.Setter;

public class PassengerSheet extends Sheet {

    protected RouteService routeService;
    protected RequestService requestService;
    protected UserService userService;
    protected LocationService locationService;

    @Setter
    @Getter
    private ApiLocation location;

    private LinearLayout locationInfo;
    private TextView locationTime;
    private Button locationBtn;
    private ImageView userImage;
    private TextView userName;

    private LinearLayout requestInfo;
    private EditText requestMessageInp;
    private TextView requestStatus;
    private TextView requestMessage;
    private Button requestBtn;
    private Button requestCancelBtn;

    public PassengerSheet() {
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

        View view = inflater.inflate(R.layout.sheet_passenger, container, false);

        userImage = view.findViewById(R.id.user_image);
        userName = view.findViewById(R.id.user_name);
        locationInfo = view.findViewById(R.id.location_info);
        locationTime = view.findViewById(R.id.location_time);
        locationBtn = view.findViewById(R.id.location_btn);
        requestInfo = view.findViewById(R.id.request_info);
        requestMessage = view.findViewById(R.id.request_message);
        requestStatus = view.findViewById(R.id.request_status);
        requestMessageInp = view.findViewById(R.id.request_message_inp);
        requestBtn = view.findViewById(R.id.request_btn);
        requestCancelBtn = view.findViewById(R.id.request_cancel_btn);

        userService.current((user) -> {
            locationService.getLocation(location.uuid, (location) -> {
                this.location = location;
                createUserLine(user);
                createLocationLine(user, location);
                createRequestLine(user, location);
            });
        });

        return view;
    }


    private void createLocationLine(ApiUser user, ApiLocation location) {
        locationInfo.setVisibility(View.VISIBLE);
        locationTime.setText(location.hour + ":" + location.min);
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

    private void createUserLine(ApiUser user) {

        if (user.profile.image != null)
            new DownloadImageTask(userImage).execute(Constants.API_URL + "/uploads/profile/" + FileHelper.getStyleName(user.profile.image, "sm"));

        userName.setText(user.profile.name + " " + user.profile.lastName);
    }

}
