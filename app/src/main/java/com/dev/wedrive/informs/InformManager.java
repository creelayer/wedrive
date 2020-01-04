package com.dev.wedrive.informs;

import android.content.Intent;
import android.os.CountDownTimer;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.dev.wedrive.R;
import com.dev.wedrive.RequestListActivity;
import com.dev.wedrive.entity.ApiInform;
import com.dev.wedrive.service.InformService;
import com.dev.wedrive.util.Timer;

import java.util.TimerTask;

import lombok.Getter;

public class InformManager {

    private FragmentActivity activity;

    @Getter
    private InformService informService;

    private Timer timer;

    private InformAbstract state;

    public InformManager(FragmentActivity activity) {
        super();
        this.activity = activity;
        this.timer = new Timer();
        informService = new InformService();
    }

    public void show(InformAbstract inform) {

        if (state != null && !inform.isPriority() && !state.getGroup().equals(inform.getGroup()))
            return;

        if (state != null && state.getGroup().equals(inform.getGroup()))
            inform.setAnimate(false);

        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.inform, inform);
        transaction.addToBackStack(null);
        transaction.commit();

        setTimeDown(inform);

        state = inform;

    }

    private void setTimeDown(InformAbstract inform) {

        if (inform.getDelay() <= 0)
            return;

        new CountDownTimer(inform.getDelay(), 1000) {

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                inform.hide();
                state = null;
            }

        }.start();
    }


    public void start() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                informService.getLast((inform) -> {

                    if (inform == null)
                        return;

                    show(new InformMessage()
                            .setHeader(inform.header)
                            .setText(inform.message)
                            .setOnClickListener(() -> {
                                informService.setStatus(inform, ApiInform.STATUS_VIEWED);

                                if (inform.type.equals(ApiInform.TYPE_REQUEST) || inform.type.equals(ApiInform.TYPE_REQUEST_MESSAGE))
                                    activity.startActivity(new Intent(activity, RequestListActivity.class));
                            })
                            .setPriority(false)
                            .setGroup("scheduleInform"));
                });
            }
        }, 0, 5000);
    }

    public void stop() {

        if (state != null)
            state.hide();

        timer.cancel();
    }


}
