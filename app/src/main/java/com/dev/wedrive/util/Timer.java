package com.dev.wedrive.util;

import java.util.TimerTask;

import lombok.Getter;

public class Timer extends java.util.Timer {

    @Getter
    private boolean started = false;

    @Override
    public void scheduleAtFixedRate(TimerTask task, long delay, long period) {
        cancel();
        super.scheduleAtFixedRate(task, delay, period);
        started = true;
    }

    @Override
    public void cancel() {

        if (started)
            super.cancel();

        started = false;
    }
}
