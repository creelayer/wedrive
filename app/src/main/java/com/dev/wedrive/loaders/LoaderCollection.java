package com.dev.wedrive.loaders;

import java.util.ArrayList;

public class LoaderCollection {


    ArrayList<LoaderInterface> loaders;

    public void push(LoaderInterface loader) {
        loaders.add(loader);
    }

    public void pop(LoaderInterface loader) {
        loaders.add(loader);
    }

    public void run() {

        if (loaders.size() == 0) {
            return;
        }

        loaders.get(loaders.size() - 1).run();
    }

}
