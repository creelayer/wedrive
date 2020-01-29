package com.dev.wedrive;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.dev.wedrive.entity.ApiUser;

public class RegistrationActivity extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        signUp.setVisibility(View.GONE);
        signIn.setText("Register");
    }

    @Override
    public void onValidationSucceeded() {
        ApiUser newUser = new ApiUser(phone.getText().toString(), password.getText().toString());
        userService.register(newUser, (token) -> goToAndFinish(ConfirmRegistrationActivity.class), (e) ->
                Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
        );
    }
}
