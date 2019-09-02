package com.dev.wedrive.dialogs;

import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dev.wedrive.MapActivity;
import com.dev.wedrive.R;
import com.dev.wedrive.entity.ApiLocation;
import com.dev.wedrive.entity.MMarker;
import com.dev.wedrive.service.LocationService;
import com.dev.wedrive.service.MapService;
import com.google.android.gms.maps.model.LatLng;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Max;
import com.mobsandgeeks.saripaar.annotation.Min;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

import lombok.Getter;
import lombok.Setter;


public class LocationPassengerEditDialog extends DialogAbstract implements Validator.ValidationListener {

    private Validator validator;
    private AlertDialog dialog;

    @Setter
    private MapService mapService;

    @Setter
    private LocationService locationService;

    @Getter
    ApiLocation apiLocation;

    @Setter
    private LatLng latLng;

    @Setter
    private MMarker mMarker;

    @NotEmpty
    @Max(23)
    @Min(0)
    private EditText hour;

    @NotEmpty
    @Max(59)
    @Min(0)
    private EditText minute;

    public LocationPassengerEditDialog(MapActivity activity, ApiLocation apiLocation) {
        super(activity);
        this.locationService = new LocationService();
        this.apiLocation = apiLocation;
    }

    @Override
    public void show() {

        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.passenger_location_dialog, null);

        validator = new Validator(this);
        validator.setValidationListener(this);

        hour = (EditText) view.findViewById(R.id.route_hour);
        minute = (EditText) view.findViewById(R.id.route_minute);

        Button okBtn = view.findViewById(R.id.dialog_ok_btn);
        Button cancelBtn = view.findViewById(R.id.dialog_cancel_btn);


        if (apiLocation.getUuid() != null) {
//            String[] time = apiLocation.getTime().split(":");
//            hour.setText(time[0]);
//            minute.setText(time[1]);
            cancelBtn.setText("Delete");
        }


        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validator.validate();
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

    @Override
    public void onValidationSucceeded() {

        // apiLocation.time = hour.getText().toString() + minute.getText().toString();

        if (mMarker == null) {
            locationService.createLocation(apiLocation, (result) -> {
                //TODO Load route locations with loader. See more in driverDialog
                return null;
            });
        } else {
            locationService.updateLocation(apiLocation, (result) -> {
                //TODO Load route locations with loader. See more in driverDialog
                return null;
            });
        }

        super.hide();

    }

    public void hide() {
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
