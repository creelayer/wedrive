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
import com.dev.wedrive.helpers.DownloadImageTask;
import com.dev.wedrive.helpers.FileHelper;
import com.dev.wedrive.informs.InformMessageFragment;
import com.dev.wedrive.service.RequestService;
import com.dev.wedrive.service.RouteService;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import lombok.Getter;
import lombok.Setter;

public class RoutePassengerSheet extends Sheet implements View.OnClickListener {

    private RouteService routeService;
    private RequestService requestService;

    @Setter
    @Getter
    private ApiLocation location;

    private TextView routeName;
    private TextView routeStatus;

    private ImageView userImage;
    private TextView userName;

    private ImageView carImage;
    private TextView carBrand;
    private TextView carModel;
    private TextView carNumber;


    @Getter
    private EditText requestMessageInp;
    private TextView requestMessage;
    private Button requestBtn;

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
        routeStatus = view.findViewById(R.id.route_status);
        carImage = view.findViewById(R.id.car_image);
        carBrand = view.findViewById(R.id.car_brand);
        carModel = view.findViewById(R.id.car_model);
        carNumber = view.findViewById(R.id.car_number);

        requestMessage = view.findViewById(R.id.request_message);
        requestMessageInp = view.findViewById(R.id.request_message_inp);
        requestBtn = view.findViewById(R.id.request_btn);
        requestBtn.setOnClickListener(this);

        if (location != null)
            load();

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.request_btn) {
            ApiRequest request = new ApiRequest(this);
            requestService.createRequest(location, request, (mRequest) -> {
                showRequestInfo(mRequest);

                MapActivity activity = (MapActivity) getActivity();
                activity.setFragment(R.id.inform, new InformMessageFragment().setHeader("Request send").setText("Your request send. Wait for reply.").setDelay(3000));

                collapse();
                return null;
            });
        }

    }

    private void load() {
        routeService.getRoute(location.route.uuid, (route) -> {

            if (route.request != null)
                showRequestInfo(route.request);

            if (route.user.profile.image != null)
                new DownloadImageTask(userImage).execute(Constants.API_URL + "/uploads/profile/" + FileHelper.getStyleName(route.user.profile.image, "sm"));

            userName.setText(route.user.profile.name + " " + route.user.profile.lastName);

            routeName.setText(route.name);
            routeStatus.setText(route.status);

            carBrand.setText(route.car.brand);
            carModel.setText(route.car.model);
            carNumber.setText(route.car.number);

            if (route.car.image != null)
                new DownloadImageTask(carImage).execute(Constants.API_URL + "/uploads/car/" + FileHelper.getStyleName(route.car.image, "sm"));

            return null;
        });
    }

    private void hideForm() {
            getView().findViewById(R.id.request_form).setVisibility(View.GONE);
    }

    private void showRequestInfo(ApiRequest request) {
        hideForm();
        requestMessage.setText(request.message);
        getView().findViewById(R.id.request_info).setVisibility(View.VISIBLE);
    }


}
