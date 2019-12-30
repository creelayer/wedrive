package com.dev.wedrive.loaders;

import android.arch.core.util.Function;

import com.dev.wedrive.collection.LocationCollection;

public interface LoaderInterface {

    public void load(final Function<LocationCollection, Void> func);

    public void run(final Function<LocationCollection, Void> func);

}
