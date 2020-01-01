package com.dev.wedrive;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.dev.wedrive.adapters.RoutesListAdapter;
import com.dev.wedrive.entity.ApiRoute;
import com.dev.wedrive.service.RouteService;

public class RouteListActivity extends AppCompatActivity {

    private RouteService routeService;

    public RouteListActivity() {
        super();
        routeService = new RouteService();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_list);

        findViewById(R.id.route_add_btn).setOnClickListener((v) -> {
            Intent myIntent = new Intent(this, CreateNewRouteActivity.class);
            startActivity(myIntent);
        });

        loadRoutesList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadRoutesList();
    }

    private void loadRoutesList() {
        routeService.getMyRouts((routes) -> {

            ListView list = findViewById(R.id.routes_list);
            list.setAdapter(new RoutesListAdapter(this, routes, (id, route) -> {


                if (id == R.id.smContentView) {
                    routeService.setStatus(route, ApiRoute.STATUS_CURRENT, (mRoute) -> {
                        finish();
                        return null;
                    });
                }

                if (id == R.id.list_item_delete) {
                    routeService.deleteRoute(route, (mRoute) -> {
                        finish();
                        return null;
                    });
                }

                if (id == R.id.list_item_edit) {
                    Intent intent = new Intent(this, CreateNewRouteActivity.class);
                    intent.putExtra("uuid", route.uuid);
                    startActivity(intent);
                }


            }));
            return null;
        });
    }
}
