package com.dev.wedrive.controls;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.dev.wedrive.MapActivity;
import com.dev.wedrive.R;
import com.dev.wedrive.dialoga.DriverRoutesListFragment;

import lombok.Setter;

/**
 * A simple {@link Fragment} subclass.
 */
public class DriverRoutesFragment extends Fragment implements View.OnClickListener {

    @Setter
    private MapActivity activity;

    public DriverRoutesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_driver_routes, container, false);
        return init(v);
    }

    public View init(View v) {
        ImageButton routeListBtn = v.findViewById(R.id.route_list_btn);
        routeListBtn.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.route_list_btn) {
            activity.getBottomDialog().setFragment(new DriverRoutesListFragment()).show();
        }
    }
}
