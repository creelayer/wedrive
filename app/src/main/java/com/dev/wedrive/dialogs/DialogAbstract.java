package com.dev.wedrive.dialogs;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.dev.wedrive.MapActivity;
import com.dev.wedrive.R;

public abstract class DialogAbstract implements DialogInterface{

    protected MapActivity activity;

    protected LinearLayout informLayout;
    private Animation animationIn;
    private Animation animationOut;

    public DialogAbstract(MapActivity activity){
        this.activity = activity;
        this.informLayout = activity.findViewById(R.id.inform_layout);
        this.animationIn = AnimationUtils.loadAnimation(activity, R.anim.inform_slide_in);
        this.animationOut = AnimationUtils.loadAnimation(activity, R.anim.inform_slide_out);
    }

    public void show() {
        informLayout.setVisibility(View.VISIBLE);
        informLayout.startAnimation(animationIn);
    }

    public void hide() {
        informLayout.startAnimation(animationOut);
        informLayout.setVisibility(View.INVISIBLE);
    }

}
