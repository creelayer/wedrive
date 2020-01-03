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

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
public class InformAbstract extends Fragment {

    protected Animation animationIn;
    protected Animation animationOut;

    @Setter
    @Getter
    private String group = String.valueOf(hashCode());

    @Setter
    @Getter
    protected long delay = 0;

    @Setter
    @Getter
    protected boolean priority = true;

    @Setter
    protected boolean animate = true;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        animationIn = AnimationUtils.loadAnimation(getContext(), R.anim.inform_slide_in);
        animationOut = AnimationUtils.loadAnimation(getContext(), R.anim.inform_slide_out);
        if (animate)
            view.startAnimation(animationIn);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    public void hide() {
        getView().startAnimation(animationOut);
        getView().setVisibility(View.INVISIBLE);
    }

}
