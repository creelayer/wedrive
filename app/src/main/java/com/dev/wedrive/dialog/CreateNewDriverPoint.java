package com.dev.wedrive.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.dev.wedrive.R;
import com.dev.wedrive.entity.ApiLocation;

public class CreateNewDriverPoint implements DialogInterface {

    Activity mActivity;
    ApiLocation apiLocation;

    public CreateNewDriverPoint(Activity activity, ApiLocation apiLocation) {
        mActivity = activity;
        this.apiLocation = apiLocation;
    }

    public AlertDialog create() {

        AlertDialog dialogBuilder = new AlertDialog.Builder(mActivity).create();

        LayoutInflater inflater = mActivity.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_driver_point, null);

        dialogBuilder.setView(dialogView);
        return dialogBuilder;
    }

}
