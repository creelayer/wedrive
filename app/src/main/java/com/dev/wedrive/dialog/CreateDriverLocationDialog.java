package com.dev.wedrive.dialog;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dev.wedrive.MapActivity;
import com.dev.wedrive.R;
import com.dev.wedrive.entity.ApiLocation;
import com.dev.wedrive.loaders.ActiveLocationsLoader;
import com.dev.wedrive.service.LocationService;
import com.dev.wedrive.service.RouteService;
import com.dev.wedrive.sheet.RouteSheet;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Max;
import com.mobsandgeeks.saripaar.annotation.Min;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Optional;

import java.util.List;

import lombok.Getter;

public class CreateDriverLocationDialog implements DialogInterface, Validator.ValidationListener {


    protected MapActivity mActivity;
    protected ApiLocation location;

    protected LocationService locationService;
    protected RouteService routeService;

    protected AlertDialog dialogBuilder;

    @Getter
    @NotEmpty
    @Max(value = 23, message = "Max is 23 hour")
    @Min(value = 2, message = "Min is 00 hour")
    protected EditText hour;

    @Getter
    @NotEmpty
    @Max(value = 59, message = "Max is 59 minutes")
    @Min(value = 0, message = "Min is 00 minutes")
    protected EditText minute;

    @Getter
    @Optional
    @Max(value = 20, message = "Max gap is 15 minutes")
    @Min(value = 0, message = "Max gap is 0 minutes")
    protected EditText interval;

    public CreateDriverLocationDialog(MapActivity activity, ApiLocation apiLocation) {
        mActivity = activity;
        location = apiLocation;
        locationService = new LocationService();
        routeService = new RouteService();
    }

    public AlertDialog create() {

        dialogBuilder = new AlertDialog.Builder(mActivity).create();

        LayoutInflater inflater = mActivity.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_driver_location, null);

        Validator validator = new Validator(this);
        validator.setValidationListener(this);

        hour = dialogView.findViewById(R.id.location_hour);
        minute = dialogView.findViewById(R.id.location_min);
        interval = dialogView.findViewById(R.id.location_interval);

        Button ok = dialogView.findViewById(R.id.driver_location_ok_btn);
        Button delete = dialogView.findViewById(R.id.driver_location_delete_btn);

        ok.setOnClickListener((v) -> validator.validate());
        delete.setOnClickListener((v) -> deleteLocation());

        if (location.getUuid() != null) {
            hour.setText(String.valueOf(location.hour));
            minute.setText(String.valueOf(location.min));
            interval.setText(String.valueOf(location.interval));
        } else {
            delete.setVisibility(View.GONE);
        }

        dialogBuilder.setView(dialogView);
        return dialogBuilder;
    }

    private void deleteLocation() {

        locationService.deleteLocation(location, (location) ->

                routeService.getCurrentRoute((route) -> {

                    mActivity.getLoaderLocationManager().reset(new ActiveLocationsLoader());
                    RouteSheet sheet = new RouteSheet();
                    sheet.setRoute(route);
                    mActivity.setFragment(R.id.btmControls, sheet);
                    dialogBuilder.cancel();

                })
        );

    }

    @Override
    public void onValidationSucceeded() {

        location.hour = Integer.parseInt(getHour().getText().toString());
        location.min = Integer.parseInt(getMinute().getText().toString());
        location.interval = Integer.parseInt(getInterval().getText().toString());

        if (location.getUuid() == null) {
            locationService.createLocation(location, (result) -> {
                mActivity.getLoaderLocationManager().reset(new ActiveLocationsLoader());
                dialogBuilder.cancel();
            });
        } else {
            locationService.updateLocation(location, (result) -> dialogBuilder.cancel());
        }

    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {

        for (ValidationError error : errors) {
            View view = error.getView();

            String message = error.getCollatedErrorMessage(dialogBuilder.getContext());

            if (view instanceof EditText) {
                EditText input = (EditText) view;
                input.setError(message, null);
            } else {
                Toast.makeText(dialogBuilder.getContext(), message, Toast.LENGTH_LONG).show();
            }
        }

    }
}
