package com.dev.wedrive.controls;

import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.dev.wedrive.MapActivity;
import com.dev.wedrive.R;
import com.dev.wedrive.dialoga.BottomDialogBuilder;
import com.dev.wedrive.dialoga.DriverRoutesListFragment;

import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * A simple {@link Fragment} subclass.
 */
public class DriverRoutesFragment extends Fragment implements View.OnClickListener {

    @Setter
    @Accessors(chain = true)
    private MapActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_driver_routes, container, false);

        v.findViewById(R.id.route_list_btn).setOnClickListener(this);

        return v;
    }


    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.route_list_btn) {
            new BottomDialogBuilder(activity)
                    .setFragment(new DriverRoutesListFragment().setActivity(activity))
                    .setHeight(ViewGroup.LayoutParams.MATCH_PARENT)
                    .create()
                    .setState(BottomSheetBehavior.STATE_EXPANDED);
        }
    }
}
