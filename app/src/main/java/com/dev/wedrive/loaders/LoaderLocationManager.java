package com.dev.wedrive.loaders;

import com.dev.wedrive.service.MapService;

import java.util.ArrayDeque;
import java.util.Timer;
import java.util.TimerTask;

public class LoaderLocationManager extends ArrayDeque<LoaderInterface> {

    protected MapService mapService;

    private Timer timer;
    private TimerTask timerTask;

    public LoaderLocationManager(MapService mapService) {
        this.mapService = mapService;
        this.timer = new Timer();
    }

    @Override
    public boolean add(LoaderInterface loader) {
        return super.add(loader);
    }

    @Override
    public void clear() {
        super.clear();
        mapService.clearLocations();
    }

    public void start() {
        LoaderLocationManager that = this;
        timerTask = new TimerTask() {
            @Override
            public void run() {
                that.run();
            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, 10000);
    }

    public void stop() {
        if (timerTask != null)
            timerTask.cancel();
    }

    public void load() {
        if (!isEmpty())
            getLast().load((locations) -> mapService.updateLocations(locations));
    }

    public void run() {
        if (!isEmpty())
            getLast().run((locations) -> mapService.updateLocations(locations));

    }
}
