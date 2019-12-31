package com.dev.wedrive.sheet;

import android.os.Bundle;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.dev.wedrive.R;
import com.dev.wedrive.entity.ApiRoute;
import com.dev.wedrive.service.RouteService;

import lombok.Setter;

public class RouteSheet extends Sheet implements View.OnClickListener {

    private RouteService routeService;

    @Setter
    private ApiRoute route;

    private TextView name;
    private TextView status;
    private Button actionBtn;

    public RouteSheet() {
        super();
        routeService = new RouteService();
        height = ViewGroup.LayoutParams.WRAP_CONTENT;
        state = BottomSheetBehavior.STATE_COLLAPSED;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.sheet_current_route, container, false);

        name = view.findViewById(R.id.sheet_current_route_name);
        status = view.findViewById(R.id.sheet_current_route_status);
        actionBtn = view.findViewById(R.id.sheet_curret_route_action_btn);

        afterCreate();

        return view;
    }

    private void afterCreate() {
        if (route != null) {
            name.setText(route.name);
            status.setText(route.status);
            actionBtn.setText(route.status.equals(ApiRoute.STATUS_CURRENT) ? "Run" : "Stop");
            actionBtn.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.sheet_curret_route_action_btn) {
            routeService.setStatus(route, route.status.equals(ApiRoute.STATUS_CURRENT) ? ApiRoute.STATUS_ACTIVE : ApiRoute.STATUS_CURRENT, (route) -> {

                this.route = route;
                name.setText(route.name);
                status.setText(route.status);
                actionBtn.setText(route.status.equals(ApiRoute.STATUS_CURRENT) ? "Run" : "Stop");

                getActivity().findViewById(R.id.lftControls).setVisibility(route.status.equals(ApiRoute.STATUS_ACTIVE) ? View.INVISIBLE : View.VISIBLE);

                return null;
            });
        }
    }

}
