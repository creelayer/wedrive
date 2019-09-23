package com.dev.wedrive.dialogs;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dev.wedrive.MapActivity;
import com.dev.wedrive.R;
import com.dev.wedrive.entity.ApiLocation;
import com.dev.wedrive.entity.DriverLocationData;
import com.dev.wedrive.entity.TypeInterface;
import com.google.gson.internal.LinkedTreeMap;

import java.util.HashMap;
import java.util.Map;

import lombok.Setter;

public class RouteInfoDialog extends DialogAbstract {

    private View view;

    @Setter
    private ApiLocation location;

    private String current;


    public RouteInfoDialog(MapActivity activity, ApiLocation location) {
        super(activity);
        this.location = location;
        this.current = location.uuid;
    }


    @Override
    public void show() {

        LayoutInflater inflater = activity.getLayoutInflater();
        view = inflater.inflate(R.layout.route_info_dialog, null);

        TextView name = view.findViewById(R.id.route_inform_name);
        name.setText(location.route.name);
        addRouteLocation();
        addControls();

        informLayout.removeAllViews();
        informLayout.addView(view);
        informLayout.setMinimumHeight(400);

        super.show();
    }


    private void addRouteLocation() {

        LinearLayout layout = view.findViewById(R.id.route_inform_list);
        layout.removeAllViews();

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(10, 0, 0, 10);

        for (ApiLocation item : location.route.locations) {

            if (!item.type.equals(TypeInterface.TYPE_DRIVER_LOCATION)) {
                continue;
            }

            layout.addView(createLabel(item), layoutParams);
        }
    }


    private void addControls() {

        LinearLayout layout = view.findViewById(R.id.route_inform_controls);

        Button okBtn = new Button(view.getContext());
        okBtn.setText("Поехали!");

        okBtn.setOnClickListener((v) -> {
            activity.getLoader().removeLast();
            this.hide();
        });

        layout.addView(okBtn);

        Button cancelBtn = new Button(view.getContext());
        cancelBtn.setText("Cancel");

        cancelBtn.setOnClickListener((v) -> {
            activity.getLoader().removeLast();
            this.hide();
        });

        layout.addView(cancelBtn);

    }

    private TextView createLabel(ApiLocation item) {
        DriverLocationData data = new DriverLocationData((LinkedTreeMap<String, String>) item.getData());
        TextView text = new TextView(view.getContext());

        String name = "   " + data.getHour() + ':' + data.getMin();

        if (item.uuid.equals(current)) {
            name += " текущий";
        }

        text.setText(name);
        text.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_driver_location, 0, 0, 0);
        text.setTag(item.uuid);
        text.setOnClickListener((v) -> {
            current = (String) v.getTag();
            addRouteLocation();
        });

        return text;
    }

}
