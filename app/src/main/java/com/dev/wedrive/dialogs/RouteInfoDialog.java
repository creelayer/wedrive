package com.dev.wedrive.dialogs;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dev.wedrive.MapActivity;
import com.dev.wedrive.R;
import com.dev.wedrive.entity.ApiRoute;

import lombok.Setter;

public class RouteInfoDialog extends DialogAbstract {


    @Setter
    private ApiRoute route;

    public RouteInfoDialog(MapActivity activity, ApiRoute route) {
        super(activity);
        this.route = route;
    }


    @Override
    public void show() {

        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.route_info_dialog, null);

        TextView name = (TextView) view.findViewById(R.id.route_name);
        name.setText(route.name);

        Button okBtn = view.findViewById(R.id.dialog_ok_btn);
        Button editBtn = view.findViewById(R.id.dialog_edit_btn);

        okBtn.setOnClickListener((v) -> {
            this.hide();
        });

        editBtn.setOnClickListener((v) -> {
            new RouteDialog(this.activity, this.route).show();
        });


        informLayout.removeAllViews();
        informLayout.addView(view);
        informLayout.setMinimumHeight(400);

        super.show();
    }
}
