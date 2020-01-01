package com.dev.wedrive.informs;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.dev.wedrive.R;

import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
public class InformAbstract extends Fragment {

    protected Animation animationIn;
    protected Animation animationOut;

    @Setter
    protected long delay = 0;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        animationIn = AnimationUtils.loadAnimation(getContext(), R.anim.inform_slide_in);
        animationOut = AnimationUtils.loadAnimation(getContext(), R.anim.inform_slide_out);
        view.startAnimation(animationIn);
        setTimeDown(delay);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        setTimeDown(delay);
    }

    private void setTimeDown(long delay) {

        if (delay <= 0)
            return;

        new CountDownTimer(delay, 1000) {

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                hide();
            }

        }.start();
    }


    public void hide() {
        getView().startAnimation(animationOut);
        getView().setVisibility(View.INVISIBLE);
    }

}
