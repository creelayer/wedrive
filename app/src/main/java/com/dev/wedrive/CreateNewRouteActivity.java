package com.dev.wedrive;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dev.wedrive.entity.ApiRoute;
import com.dev.wedrive.service.RouteService;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

import lombok.Setter;

public class CreateNewRouteActivity extends AppCompatActivity implements Validator.ValidationListener {

    private Validator validator;

    @Setter
    private RouteService routeService;

    @Setter
    private ApiRoute route;

    @NotEmpty
    @Length(max = 100)
    private EditText name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_route);

        routeService = new RouteService();
        validator = new Validator(this);
        validator.setValidationListener(this);
        name = findViewById(R.id.route_name_input);
        Button okBtn = findViewById(R.id.route_save_btn);
        okBtn.setOnClickListener((v) -> validator.validate());

        if (route != null) {
            name.setText(route.name);
        }
    }

    @Override
    public void onValidationSucceeded() {

        if (route == null) {
            routeService.createRoute(name.getText().toString(), ApiRoute.TYPE_DRIVER, (route) -> {
                finish();
                return null;
            });
        } else {
            route.name = name.getText().toString();
            routeService.updateRoute(route, (route) -> {
                finish();
                return null;
            });
        }
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();

            String message = error.getCollatedErrorMessage(this);

            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }
}
