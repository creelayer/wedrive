package com.dev.wedrive;

import android.content.Intent;
import android.widget.Toast;

import com.dev.wedrive.entity.ApiCode;

public class ConfirmRegistrationActivity extends AbstractCodeActivity {

    public void send(String code) {
        userService.confirmRegistration(user, new ApiCode(code),
                (user) -> startActivity(new Intent(this, TypeActivity.class)),
                (error) -> Toast.makeText(this, "Code invalid", Toast.LENGTH_LONG).show());
    }
}
