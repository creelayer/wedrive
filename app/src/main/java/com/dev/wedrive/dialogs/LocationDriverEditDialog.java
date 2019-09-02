package com.dev.wedrive.dialogs;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dev.wedrive.MapActivity;
import com.dev.wedrive.R;
import com.dev.wedrive.entity.ApiLocation;
import com.dev.wedrive.entity.ApiRoute;
import com.dev.wedrive.entity.DriverLocationData;
import com.dev.wedrive.service.LocationService;
import com.dev.wedrive.service.MapService;
import com.google.gson.internal.LinkedTreeMap;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Max;
import com.mobsandgeeks.saripaar.annotation.Min;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Optional;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class LocationDriverEditDialog extends DialogAbstract implements Validator.ValidationListener {


    private Validator validator;

    @Setter
    private MapService mapService;

    @Setter
    private LocationService routeLocationService;

    @Getter
    private ApiLocation apiLocation;

    @NotEmpty
    @Max(23)
    @Min(0)
    private EditText hour;

    @NotEmpty
    @Max(59)
    @Min(0)
    private EditText minute;

    @Optional
    @Max(15)
    @Min(0)
    private EditText gap;


    public LocationDriverEditDialog(MapActivity activity, ApiLocation apiLocation) {
        super(activity);
        this.mapService = activity.getMapService();
        this.apiLocation = apiLocation;
        this.routeLocationService = new LocationService();
    }

    @Override
    public void show() {

        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.route_location_dialog, null);

        validator = new Validator(this);
        validator.setValidationListener(this);


        hour = (EditText) view.findViewById(R.id.route_hour);
        minute = (EditText) view.findViewById(R.id.route_minute);
        gap = (EditText) view.findViewById(R.id.route_gap);


        Button okBtn = view.findViewById(R.id.dialog_ok_btn);
        Button cancelBtn = view.findViewById(R.id.dialog_cancel_btn);


        if (apiLocation.getUuid() != null) {

            DriverLocationData data = new DriverLocationData((LinkedTreeMap<String, String>) apiLocation.data);

            hour.setText(data.hour);
            minute.setText(data.min);
            gap.setText(data.gap);
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

        apiLocation.data = new DriverLocationData(
                hour.getText().toString() ,
                minute.getText().toString(),
                gap.getText().toString()
        );

        if (apiLocation.getUuid() == null) {
            routeLocationService.createLocation(apiLocation, (result) -> {
                activity.getLoader().getLast().load();
                return null;
            });
        } else {
            routeLocationService.updateLocation(apiLocation, (result) -> {
                activity.getLoader().getLast().load();
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
