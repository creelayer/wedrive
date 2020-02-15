package com.dev.wedrive;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ViewFlipper;

import com.dev.wedrive.entity.ApiDeviceToken;
import com.dev.wedrive.entity.ApiUser;
import com.dev.wedrive.service.DeviceTokenService;
import com.dev.wedrive.service.UserService;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.hbb20.CountryCodePicker;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

public class MainActivity extends AbstractActivity {


    protected UserService userService;
    protected DeviceTokenService deviceTokenService;

    private ViewFlipper loginFlipper;

    public CountryCodePicker ccpInput;

    @NotEmpty
    @Length(min = 9, max = 9)
    protected EditText phoneInput;

    @NotEmpty
    @Password(min = 4)
    protected EditText passwordInput;


    public MainActivity() {
        super();
        this.userService = new UserService();
        this.deviceTokenService = new DeviceTokenService();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginFlipper = findViewById(R.id.loginFlipper);

        ccpInput = findViewById(R.id.ccp);
        phoneInput = findViewById(R.id.phone);
        passwordInput = findViewById(R.id.password);

       // FirebaseApp.initializeApp(this);

    }

    public void showPasswordInput(View view) {

        String ccp = ccpInput.getDefaultCountryCode();
        String phone = phoneInput.getText().toString();

        if (phone.isEmpty()) {
            phoneInput.setError(getResources().getString(R.string.error_empty));
            return;
        }

        userService.getUser(ccp + phone, (user) -> loginFlipper.showNext(), (error) -> phoneInput.setError(error.getMessage()));

    }

    public void login(View view) {

        String ccp = ccpInput.getDefaultCountryCode();
        String phone = phoneInput.getText().toString();
        String password = passwordInput.getText().toString();

        if (password.isEmpty()) {
            phoneInput.setError(getResources().getString(R.string.error_empty));
            return;
        }

        ApiUser data = new ApiUser(ccp + phone, password);

        userService.login(data, (token) ->

                        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener((instanceIdResult ->

                                deviceTokenService.add(new ApiDeviceToken(instanceIdResult.getToken()), (deviceToken) ->

                                        userService.getUser(token.userId, (user) -> {
                                            if (user.status.equals(ApiUser.STATUS_CREATED))
                                                goToAndFinish(ConfirmRegistrationActivity.class);
                                            else
                                                goToAndFinish(TypeActivity.class);
                                        }, (error) -> phoneInput.setError(error.getMessage()))

                                )

                        ))

                , (error) -> passwordInput.setError(error.getMessage()));

    }
}
