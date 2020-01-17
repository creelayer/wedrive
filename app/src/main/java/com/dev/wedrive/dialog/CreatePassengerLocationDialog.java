package com.dev.wedrive.dialog;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dev.wedrive.MapActivity;
import com.dev.wedrive.R;
import com.dev.wedrive.entity.ApiLocation;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import lombok.Getter;

public class CreatePassengerLocationDialog extends CreateDriverLocationDialog {


    @Getter
    @NotEmpty
    @Length(max = 255)
    protected EditText message;

    public CreatePassengerLocationDialog(MapActivity activity, ApiLocation apiLocation) {
        super(activity, apiLocation);
    }


    public AlertDialog create() {

        dialogBuilder = new AlertDialog.Builder(mActivity).create();

        LayoutInflater inflater = mActivity.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_passenger_location, null);

        Validator validator = new Validator(this);
        validator.setValidationListener(this);

        hour = dialogView.findViewById(R.id.location_hour);
        minute = dialogView.findViewById(R.id.location_min);
        interval = dialogView.findViewById(R.id.location_interval);
        message = dialogView.findViewById(R.id.location_message);

        Button ok = dialogView.findViewById(R.id.driver_location_ok_btn);
        Button delete = dialogView.findViewById(R.id.driver_location_delete_btn);

        ok.setOnClickListener((v) -> validator.validate());
        delete.setOnClickListener((v) -> deleteLocation());

        if (location.getUuid() != null) {
            hour.setText(location.hour);
            minute.setText(location.min);
            interval.setText(location.interval);
            message.setText(location.message);
        } else {
            delete.setVisibility(View.GONE);
        }

        dialogBuilder.setView(dialogView);
        return dialogBuilder;
    }

    private void deleteLocation() {
        locationService.deleteLocation(location, (location) -> {
            mActivity.getLoaderLocationManager().load();
            dialogBuilder.cancel();
        });
    }

    @Override
    public void onValidationSucceeded() {

        location.hour = Integer.parseInt(getHour().getText().toString());
        location.min = Integer.parseInt(getMinute().getText().toString());
        location.interval = Integer.parseInt(getInterval().getText().toString());
        location.message = getMessage().getText().toString();

        if (location.getUuid() == null) {
            locationService.createLocation(location, (result) -> {
                mActivity.getLoaderLocationManager().load();
                dialogBuilder.cancel();
            });
        } else {
            locationService.updateLocation(location, (result) -> dialogBuilder.cancel());
        }
    }
}
