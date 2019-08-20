package com.dev.wedrive.service;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.dev.wedrive.MapActivity;
import com.dev.wedrive.R;
import com.dev.wedrive.dialogs.DialogInterface;

public class InformService {

    private MapActivity mapActivity;
    private static InformService informService;
    private LinearLayout informLayout;
    private Animation animationIn;
    private Animation animationOut;

    /**
     * @param mapActivity
     */
    private InformService(MapActivity mapActivity) {
        this.mapActivity = mapActivity;
        informLayout = (LinearLayout) mapActivity.findViewById(R.id.inform_layout);
        animationIn = AnimationUtils.loadAnimation(mapActivity, R.anim.inform_slide_in);
        animationOut = AnimationUtils.loadAnimation(mapActivity, R.anim.inform_slide_out);
    }

    /**
     * @param mapActivity
     * @return
     */
    public static InformService getInstance(MapActivity mapActivity) {

        if (informService == null) {
            informService = new InformService(mapActivity);
        }

        return informService;
    }


    public InformService setDialog(DialogInterface dialog){
        informLayout.removeAllViews();
        //informLayout.addView(dialog.create());
        informLayout.setMinimumHeight(400);
        return this;
    }

    public void show() {
        informLayout.startAnimation(animationIn);
    }

    public void hide() {
        informLayout.startAnimation(animationOut);
    }
}
