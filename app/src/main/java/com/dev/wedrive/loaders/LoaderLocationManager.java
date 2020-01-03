package com.dev.wedrive.loaders;

import com.dev.wedrive.service.MapService;
import com.dev.wedrive.util.Timer;

import java.util.ArrayDeque;
import java.util.TimerTask;

public class LoaderLocationManager extends ArrayDeque<LoaderInterface> {

    protected MapService mapService;

    private Timer timer;

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
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                that.run();
            }
        }, 0, 10000);
    }

    public void stop() {
        timer.cancel();
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
