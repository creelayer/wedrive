package com.dev.wedrive.dialoga;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.dev.wedrive.CreateNewRouteActivity;
import com.dev.wedrive.MapActivity;
import com.dev.wedrive.R;
import com.dev.wedrive.entity.ApiRoute;
import com.dev.wedrive.service.RouteService;

import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * A simple {@link Fragment} subclass.
 */
public class DriverRoutesListFragment extends Fragment implements View.OnClickListener {


    private RouteService routeService;

    @Setter
    @Accessors(chain = true)
    private MapActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        routeService = new RouteService();

        View v = inflater.inflate(R.layout.fragment_driver_routes_list, container, false);

        v.findViewById(R.id.route_add_btn).setOnClickListener(this);

        return v;
    }


    private void load() {
        routeService.getMyRouts(ApiRoute.TYPE_DRIVER, (routes) -> {
            for (ApiRoute route : routes) {

            }
            return null;
        });
    }


    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.route_add_btn) {
            Intent myIntent = new Intent(activity, CreateNewRouteActivity.class);
            startActivity(myIntent);
        }

    }
}
