package com.dev.wedrive.dialoga;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dev.wedrive.CreateNewRouteActivity;
import com.dev.wedrive.MapActivity;
import com.dev.wedrive.R;

import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * A simple {@link Fragment} subclass.
 */
public class DriverRoutesListFragment extends Fragment implements View.OnClickListener {

    @Setter
    @Accessors(chain = true)
    private MapActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_driver_routes_list, container, false);

        v.findViewById(R.id.route_add_btn).setOnClickListener(this);

        return v;
    }



    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.route_add_btn) {
            Intent myIntent = new Intent(activity, CreateNewRouteActivity.class);
            startActivity(myIntent);
        }

    }
}
