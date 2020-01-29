package com.dev.wedrive.controls;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.dev.wedrive.R;
import com.dev.wedrive.RouteListActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class DriverRoutesFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_driver_routes, container, false);

        view.findViewById(R.id.route_list_btn).setOnClickListener((v) -> startActivity(new Intent(getActivity(), RouteListActivity.class)));

        return view;
    }
}
