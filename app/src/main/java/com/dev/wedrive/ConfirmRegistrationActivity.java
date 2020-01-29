package com.dev.wedrive;

import android.widget.Toast;

import com.dev.wedrive.entity.ApiCode;
import com.dev.wedrive.service.ApiService;

public class ConfirmRegistrationActivity extends AbstractCodeActivity {

    public void send(String code) {
        userService.confirmRegistration(ApiService.getInstance().getToken(), new ApiCode(code),
                (user) -> goToAndFinish(TypeActivity.class),
                (error) -> Toast.makeText(this, "Code invalid", Toast.LENGTH_LONG).show());
    }
}
