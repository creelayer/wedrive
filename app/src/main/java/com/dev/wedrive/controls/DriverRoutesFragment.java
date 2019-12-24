package com.dev.wedrive.controls;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dev.wedrive.MapActivity;
import com.dev.wedrive.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DriverRoutesFragment extends Fragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_driver_routes, container, false);

        view.findViewById(R.id.route_list_btn).setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.route_list_btn) {
            MapActivity activity = (MapActivity) getActivity();
            activity.setFragment(R.id.btmControls, new DriverRoutesListFragment());
        }
    }
}
