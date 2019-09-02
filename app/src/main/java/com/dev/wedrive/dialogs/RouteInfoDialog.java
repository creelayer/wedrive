package com.dev.wedrive.dialogs;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dev.wedrive.MapActivity;
import com.dev.wedrive.R;
import com.dev.wedrive.entity.ApiLocation;
import com.dev.wedrive.entity.ApiRoute;
import com.dev.wedrive.entity.DriverLocationData;
import com.dev.wedrive.entity.TypeInterface;
import com.dev.wedrive.loaders.RouteLoader;
import com.google.gson.internal.LinkedTreeMap;

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

        informLayout.removeAllViews();
        addName(view);
        addRoutes(view);
        addControls(view);
        informLayout.setMinimumHeight(400);

        super.show();
    }

    private void addName(View view){
        TextView label = new TextView(view.getContext());
        label.setText("Назва");
        TextView name = new TextView(view.getContext());
        name.setText(route.name);
        informLayout.addView(label);
        informLayout.addView(name);
    }

    private void addRoutes(View view){

        LinearLayout layout = new LinearLayout(view.getContext());
        layout.setOrientation(LinearLayout.VERTICAL);

        for (ApiLocation location: route.locations){

            if(!location.type.equals(TypeInterface.TYPE_DRIVER_LOCATION)){
                continue;
            }

            DriverLocationData data = new DriverLocationData((LinkedTreeMap<String, String>) location.getData());

            TextView text = new TextView(view.getContext());
            text.setText("... "+data.getHour()+':'+data.getMin());
            layout.addView(text);
        }

        informLayout.addView(layout);
    }


    private void addControls(View view){

        LinearLayout layout = new LinearLayout(view.getContext());

        Button okBtn = new Button(view.getContext());
        okBtn.setText("Ok");

        okBtn.setOnClickListener((v) -> {
            activity.getLoader().removeLast();
            this.hide();
        });

        layout.addView(okBtn);

        Button editBtn = new Button(view.getContext());
        editBtn.setText("Edit");

        editBtn.setOnClickListener((v) -> {
            new RouteEditDialog(this.activity, this.route).show();
        });

        layout.addView(editBtn);
        informLayout.addView(layout);
    }


}
