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
import com.dev.wedrive.entity.DriverLocationData;
import com.dev.wedrive.service.LocationService;
import com.google.gson.internal.LinkedTreeMap;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Max;
import com.mobsandgeeks.saripaar.annotation.Min;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Optional;

import java.util.List;

public class CreateNewDriverLocationDialog implements DialogInterface, Validator.ValidationListener {

    MapActivity mActivity;
    ApiLocation apiLocation;

    LocationService locationService;

    AlertDialog dialogBuilder;

    @NotEmpty
    @Max(value = 23, message = "Max is 23 hour")
    @Min(value = 2, message = "Min is 00 hour")
    private EditText hour;

    @NotEmpty
    @Max(value = 59, message = "Max is 59 minutes")
    @Min(value = 0, message = "Min is 00 minutes")
    private EditText minute;

    @Optional
    @Max(value = 15, message = "Max gap is 15 minutes")
    @Min(value = 0, message = "Max gap is 0 minutes")
    private EditText gap;

    public CreateNewDriverLocationDialog(MapActivity activity, ApiLocation apiLocation) {
        mActivity = activity;
        this.apiLocation = apiLocation;
        locationService = new LocationService();
    }

    public AlertDialog create() {

        dialogBuilder = new AlertDialog.Builder(mActivity).create();

        LayoutInflater inflater = mActivity.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_driver_location, null);

        Validator validator = new Validator(this);
        validator.setValidationListener(this);

        hour = dialogView.findViewById(R.id.driver_location_hour);
        minute = dialogView.findViewById(R.id.driver_location_minute);
        gap = dialogView.findViewById(R.id.driver_location_gap);

        Button ok = dialogView.findViewById(R.id.driver_location_ok_btn);
        Button delete = dialogView.findViewById(R.id.driver_location_delete_btn);

        ok.setOnClickListener((v) -> validator.validate());

        if (apiLocation.getUuid() != null) {
            DriverLocationData data = new DriverLocationData((LinkedTreeMap<String, String>) apiLocation.data);
            hour.setText(data.hour);
            minute.setText(data.min);
            gap.setText(data.gap);
        }else{
            delete.setVisibility(View.GONE);
        }


        dialogBuilder.setView(dialogView);
        return dialogBuilder;
    }

    @Override
    public void onValidationSucceeded() {

        apiLocation.data = new DriverLocationData(
                hour.getText().toString(),
                minute.getText().toString(),
                gap.getText().toString()
        );

        if (apiLocation.getUuid() == null) {
            locationService.createLocation(apiLocation, (result) -> {
                mActivity.getLoader().load();
                dialogBuilder.cancel();
                return null;
            });
        } else {
            locationService.updateLocation(apiLocation, (result) -> {
                dialogBuilder.cancel();
                return null;
            });
        }

    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {

        for (ValidationError error : errors) {
            View view = error.getView();

            String message = error.getCollatedErrorMessage(dialogBuilder.getContext());

            if (view instanceof EditText) {
                EditText input = (EditText)view;
               // input.setError(message);
                input.setError(message, null);
            } else {
                Toast.makeText(dialogBuilder.getContext(), message, Toast.LENGTH_LONG).show();
            }
        }

    }
}
