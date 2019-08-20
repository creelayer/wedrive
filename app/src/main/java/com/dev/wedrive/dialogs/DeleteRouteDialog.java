package com.dev.wedrive.dialogs;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dev.wedrive.MapActivity;
import com.dev.wedrive.R;
import com.dev.wedrive.controller.DriverController;
import com.dev.wedrive.entity.ApiRoute;
import com.dev.wedrive.service.RouteService;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

import lombok.Setter;

public class DeleteRouteDialog extends DialogAbstract {

    @Setter
    private RouteService routeService;

    @Setter
    private ApiRoute route;


    public DeleteRouteDialog(MapActivity activity, ApiRoute route) {
        super(activity);
        this.routeService = new RouteService();
        this.route = route;
    }

    public DeleteRouteDialog(MapActivity activity) {
        super(activity);
        this.routeService = new RouteService();
        this.route = new ApiRoute();
    }

    @Override
    public void show() {

        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.route_dialog, null);

        Button okBtn = view.findViewById(R.id.dialog_ok_btn);
        Button cancelBtn = view.findViewById(R.id.dialog_cancel_btn);

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                routeService.deleteRoute(route, (route) -> {
                    ((DriverController) activity.getController()).updateRouteControls(null);
                    return null;
                });
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("aaa", "zzzz");
            }
        });

        informLayout.removeAllViews();
        informLayout.addView(view);
        informLayout.setMinimumHeight(400);

        super.show();
    }
}
