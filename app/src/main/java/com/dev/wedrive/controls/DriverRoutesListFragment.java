package com.dev.wedrive.controls;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.dev.wedrive.CreateNewRouteActivity;
import com.dev.wedrive.MapActivity;
import com.dev.wedrive.R;
import com.dev.wedrive.adapters.RoutesListAdapter;
import com.dev.wedrive.entity.ApiRoute;
import com.dev.wedrive.service.RouteService;

/**
 * A simple {@link Fragment} subclass.
 */
public class DriverRoutesListFragment extends DialogFragment implements View.OnClickListener {


    private RouteService routeService;

    public DriverRoutesListFragment() {
        super();
        routeService = new RouteService();
        height = ViewGroup.LayoutParams.MATCH_PARENT;
        state = BottomSheetBehavior.STATE_EXPANDED;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_driver_routes_list, container, false);
        view.findViewById(R.id.route_add_btn).setOnClickListener(this);

        loadRoutesList(view);

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.route_add_btn) {
            Intent myIntent = new Intent(getActivity(), CreateNewRouteActivity.class);
            startActivity(myIntent);
        }
    }

    private void loadRoutesList(View view) {
        routeService.getMyRouts(ApiRoute.TYPE_DRIVER, (routes) -> {
            ListView list = view.findViewById(R.id.routes_list);
            list.setAdapter(new RoutesListAdapter(getContext(), routes, (route) -> {
                routeService.setStatus(route, ApiRoute.STATUS_CURRENT, (mRoute) -> {
                    this.collapse();
                    return null;
                });

            }));
            return null;
        });
    }
}
