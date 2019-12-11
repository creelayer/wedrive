package com.dev.wedrive.dialoga;

import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;

import com.dev.wedrive.MapActivity;
import com.dev.wedrive.R;

import lombok.Setter;
import lombok.experimental.Accessors;

public class BottomDialogBuilder {

    private MapActivity activity;

    @Setter
    @Accessors(chain = true)
    private Fragment fragment;

    @Setter
    @Accessors(chain = true)
    private int height;

    public BottomDialogBuilder(MapActivity activity) {
        this.activity = activity;
        this.height = ViewGroup.LayoutParams.WRAP_CONTENT;
    }


    public BottomSheetBehavior create() {

        View layout = activity.findViewById(R.id.btmControls);
        ViewGroup.LayoutParams params = layout.getLayoutParams();
        params.height = height;
        layout.setLayoutParams(params);

        BottomSheetBehavior sheetBehavior = BottomSheetBehavior.from(layout);

        for (Fragment fragment : activity.getSupportFragmentManager().getFragments()) {
            activity.getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        }

        activity.getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.btmControls, fragment)
                .commit();

        return sheetBehavior;
    }
}
