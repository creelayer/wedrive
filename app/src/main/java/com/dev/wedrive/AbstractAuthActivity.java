package com.dev.wedrive;

import android.os.Bundle;

import com.dev.wedrive.service.ApiService;

public abstract class AbstractAuthActivity extends AbstractActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ApiService.getInstance().setOnTokenChangeListener((token) -> {
            if (token == null)
                goToAndFinish(MainActivity.class);
        });
    }
}
