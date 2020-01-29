package com.dev.wedrive;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ListView;

import com.dev.wedrive.adapters.RoutesListAdapter;
import com.dev.wedrive.entity.ApiRoute;
import com.dev.wedrive.service.RouteService;

public class RouteListActivity extends AbstractAuthActivity {

    private RouteService routeService;
    private RoutesListAdapter adapter;

    public RouteListActivity() {
        super();
        routeService = new RouteService();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_list);

        findViewById(R.id.route_add_btn).setOnClickListener((v) -> startActivity(new Intent(this, CreateNewRouteActivity.class)));

        loadRoutesList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadRoutesList();
    }

    private void loadRoutesList() {
        routeService.getMyRouts((routes) -> {

            // set up the RecyclerView
            RecyclerView list = findViewById(R.id.routes_list);
            list.setLayoutManager(new LinearLayoutManager(this));
            adapter = new RoutesListAdapter(this, routes);
            adapter.setListener((view, position) -> {

                ApiRoute route = adapter.getItem(position);

                if (view.getId() == R.id.smContentView)
                    routeService.setStatus(route, ApiRoute.STATUS_CURRENT, (mRoute) -> finish());

                if (view.getId() == R.id.list_item_delete)
                    routeService.deleteRoute(route, (mRoute) -> finish());

                if (view.getId() == R.id.list_item_edit)
                    startActivity(new Intent(this, CreateNewRouteActivity.class).putExtra("uuid", route.uuid));

            });

            list.setAdapter(adapter);
        });
    }
}
