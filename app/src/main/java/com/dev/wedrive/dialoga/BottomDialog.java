package com.dev.wedrive.dialoga;

import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.dev.wedrive.MapActivity;
import com.dev.wedrive.R;

import lombok.Setter;
import lombok.experimental.Accessors;

public class BottomDialog {

   // public static final String  TYPE_HEIGHT_  = 28;

    private MapActivity activity;

    @Setter
    @Accessors(chain = true)
    private Fragment fragment;

    private BottomSheetBehavior sheetBehavior;
    private FrameLayout layout;

    public BottomDialog(MapActivity activity) {
        this.activity = activity;
        layout = activity.findViewById(R.id.btmControls);
        ViewGroup.LayoutParams params = layout.getLayoutParams();
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        layout.setLayoutParams(params);
        sheetBehavior = BottomSheetBehavior.from(layout);
    }


    public void show(){
        activity.getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.btmControls, fragment)
                .commit();
    }
}
