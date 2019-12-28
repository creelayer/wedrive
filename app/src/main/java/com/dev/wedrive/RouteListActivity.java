package com.dev.wedrive;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.dev.wedrive.adapters.RoutesListAdapter;
import com.dev.wedrive.entity.ApiRoute;
import com.dev.wedrive.service.RouteService;

public class RouteListActivity extends AppCompatActivity implements View.OnClickListener {

    private RouteService routeService;

    public RouteListActivity() {
        super();
        routeService = new RouteService();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_list);

        findViewById(R.id.route_add_btn).setOnClickListener(this);
        loadRoutesList();
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.route_add_btn) {
            Intent myIntent = new Intent(this, CreateNewRouteActivity.class);
            startActivity(myIntent);
        }
    }

    private void loadRoutesList() {
        routeService.getMyRouts(ApiRoute.TYPE_DRIVER, (routes) -> {

            ListView list = findViewById(R.id.routes_list);
            list.setAdapter(new RoutesListAdapter(this, routes, (route) -> {
                routeService.setStatus(route, ApiRoute.STATUS_CURRENT, (mRoute) -> {
                    finish();
                    return null;
                });
            }));
            return null;
        });
    }
}
