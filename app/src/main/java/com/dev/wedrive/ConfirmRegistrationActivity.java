package com.dev.wedrive;

import com.dev.wedrive.entity.ApiCode;

public class ConfirmRegistrationActivity extends AbstractCodeActivity {

    public void send(String code) {
        userService.confirmRegistration(new ApiCode(code),
                (user) -> goToAndFinish(TypeActivity.class),
                (error) -> clear());
    }
}
