package com.dev.wedrive.loaders;

import androidx.core.util.Consumer;

import com.dev.wedrive.collection.LocationCollection;

public interface LoaderInterface {

    public void load(final Consumer<LocationCollection> func);

    public void run(final Consumer<LocationCollection> func);

}
