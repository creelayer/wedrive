package com.dev.wedrive;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
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

import lombok.Getter;

public class CreateNewRouteActivity extends AppCompatActivity implements Validator.ValidationListener {

    private Validator validator;

    private RouteService routeService;

    private ApiRoute route;

    @Getter
    @NotEmpty
    @Length(max = 100)
    private EditText name;

    public CreateNewRouteActivity() {
        super();
        routeService = new RouteService();
        validator = new Validator(this);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_route);

        validator.setValidationListener(this);

        name = findViewById(R.id.route_name_input);

        Button okBtn = findViewById(R.id.route_save_btn);
        okBtn.setOnClickListener((v) -> validator.validate());

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            load(bundle.getString("uuid"));
        }
    }

    @Override
    public void onValidationSucceeded() {

        if (route == null) {
            routeService.createRoute(new ApiRoute(this), (route) ->  finish());
        } else {
            routeService.updateRoute(route.load(this), (route) -> finish());
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

    private void load(String uuid) {
        routeService.getRoute(uuid, (route) -> {
            this.route = route;
            name.setText(route.name);
        });
    }
}
