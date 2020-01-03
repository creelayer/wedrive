package com.dev.wedrive.sheet;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.wedrive.CarListActivity;
import com.dev.wedrive.Constants;
import com.dev.wedrive.R;
import com.dev.wedrive.entity.ApiRoute;
import com.dev.wedrive.util.DownloadImageTask;
import com.dev.wedrive.helpers.FileHelper;
import com.dev.wedrive.service.RequestService;
import com.dev.wedrive.service.RouteService;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import lombok.Setter;

public class RouteDriverSheet extends Sheet implements View.OnClickListener {

    private RouteService routeService;
    private RequestService requestService;

    @Setter
    private ApiRoute route;

    private TextView routeName;
    private TextView routeStatus;

    private ImageView carImage;
    private TextView carBrand;
    private TextView carModel;
    private TextView carNumber;
    private Button changeCarBtn;

    private Button actionBtn;

    public RouteDriverSheet() {
        super();
        routeService = new RouteService();
        state = BottomSheetBehavior.STATE_COLLAPSED;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.sheet_driver_route, container, false);

        routeName = view.findViewById(R.id.route_name);
        routeStatus = view.findViewById(R.id.route_status);
        carImage = view.findViewById(R.id.car_image);
        carBrand = view.findViewById(R.id.car_brand);
        carModel = view.findViewById(R.id.car_model);
        carNumber = view.findViewById(R.id.car_number);
        changeCarBtn = view.findViewById(R.id.car_change_btn);
        changeCarBtn.setOnClickListener((v) -> startActivity(new Intent(getActivity(), CarListActivity.class)));

        actionBtn = view.findViewById(R.id.action_run);
        actionBtn.setOnClickListener(this);

        if (route != null)
            load();

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.action_run) {
            routeService.setStatus(route, route.status.equals(ApiRoute.STATUS_CURRENT) ? ApiRoute.STATUS_ACTIVE : ApiRoute.STATUS_CURRENT, (route) -> {
                this.route = route;
                routeStatus.setText(route.status);
                actionBtn.setText(route.status.equals(ApiRoute.STATUS_CURRENT) ? "Run" : "Stop");
                getActivity().findViewById(R.id.lftControls).setVisibility(route.status.equals(ApiRoute.STATUS_ACTIVE) ? View.INVISIBLE : View.VISIBLE);
                return null;
            });
        }

    }

    private void load() {
        routeService.getRoute(route.uuid, (route) -> {

            if (route.car == null) {
                startActivity(new Intent(getActivity(), CarListActivity.class));
                return null;
            }

            routeName.setText(route.name);
            routeStatus.setText(route.status);

            carBrand.setText(route.car.brand);
            carModel.setText(route.car.model);
            carNumber.setText(route.car.number);

            if (route.car.image != null)
                new DownloadImageTask(carImage).execute(Constants.API_URL + "/uploads/car/" + FileHelper.getStyleName(route.car.image, "sm"));


            actionBtn.setText(route.status.equals(ApiRoute.STATUS_CURRENT) ? "Run" : "Stop");
            getActivity().findViewById(R.id.lftControls).setVisibility(route.status.equals(ApiRoute.STATUS_ACTIVE) ? View.INVISIBLE : View.VISIBLE);
            return null;
        });
    }

}
