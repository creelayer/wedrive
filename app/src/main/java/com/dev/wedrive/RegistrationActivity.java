package com.dev.wedrive;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.dev.wedrive.entity.ApiUser;

public class RegistrationActivity extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        activityId = R.layout.activity_registration;
        super.onCreate(savedInstanceState);
    }

    public void showPasswordInput(View view) {

        String ccp = ccpInput.getDefaultCountryCode();
        String phone = phoneInput.getText().toString();

        if (phone.isEmpty()) {
            phoneInput.setError(getResources().getString(R.string.error_empty));
            return;
        }

        if (!phone.matches("[0-9]{9}")) {
            phoneInput.setError(getResources().getString(R.string.invalid_phone));
            return;
        }

        userService.getUser(ccp + phone, (user) -> phoneInput.setError(getResources().getString(R.string.user_exist)), (error) -> loginFlipper.showNext());

    }

    public void register(View view) {
        String ccp = ccpInput.getDefaultCountryCode();
        String phone = phoneInput.getText().toString();
        String password = passwordInput.getText().toString();

        if (password.isEmpty()) {
            passwordInput.setError(getResources().getString(R.string.error_empty));
            return;
        }

        if (password.length() < PASSWORD_MIN_LEN) {
            passwordInput.setError(getResources().getString(R.string.error_len, String.valueOf(PASSWORD_MIN_LEN)));
            return;
        }

        ApiUser data = new ApiUser(ccp + phone, password);
        userService.register(data, (token) -> goToAndFinish(ConfirmRegistrationActivity.class), (e) ->
                Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
        );

    }

    public void goToLogin(View view) {
        goToAndFinish(MainActivity.class);
    }
}
