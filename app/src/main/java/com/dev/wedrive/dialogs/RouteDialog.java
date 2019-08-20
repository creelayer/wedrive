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

public class RouteDialog extends DialogAbstract implements Validator.ValidationListener {

    private Validator validator;

    @Setter
    private RouteService routeService;

    @Setter
    private ApiRoute route;

    @NotEmpty
    @Length(max = 30)
    private EditText name;


    public RouteDialog(MapActivity activity, ApiRoute route) {
        super(activity);
        this.routeService = new RouteService();
        this.route = route;
    }

    public RouteDialog(MapActivity activity) {
        super(activity);
        this.routeService = new RouteService();
    }

    @Override
    public void show() {

        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.route_dialog, null);

        validator = new Validator(this);
        validator.setValidationListener(this);

        name = (EditText) view.findViewById(R.id.route_name);

        Button okBtn = view.findViewById(R.id.dialog_ok_btn);
        Button cancelBtn = view.findViewById(R.id.dialog_cancel_btn);


        if (route != null) {
            name.setText(route.name);
            cancelBtn.setText("Delete");
        }

        okBtn.setOnClickListener((v) -> validator.validate());

        cancelBtn.setOnClickListener((v) -> this.hide());

        informLayout.removeAllViews();
        informLayout.addView(view);
        informLayout.setMinimumHeight(400);

        super.show();
    }


    @Override
    public void onValidationSucceeded() {

        if (route == null) {
            routeService.createRoute(name.getText().toString(), (route) -> {
                ((DriverController) activity.getController()).updateRouteControls(route);
                return null;
            });
        } else {
            route.name = name.getText().toString();
            routeService.updateRoute(route, (route) -> {
                ((DriverController) activity.getController()).updateRouteControls(route);
                return null;
            });
        }

        super.hide();

    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();

            String message = error.getCollatedErrorMessage(activity);

            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
            }
        }
    }
}
