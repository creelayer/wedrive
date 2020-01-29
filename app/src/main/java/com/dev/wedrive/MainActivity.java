package com.dev.wedrive;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dev.wedrive.entity.ApiUser;
import com.dev.wedrive.service.UserService;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.util.List;

public class MainActivity extends AbstractActivity implements Validator.ValidationListener {


    protected UserService userService;

    private Validator validator;

    @NotEmpty
    protected EditText phone;

    @NotEmpty
    @Password(min = 4)
    protected EditText password;


    protected Button signIn;
    protected Button signUp;

    public MainActivity() {
        super();
        this.userService = new UserService();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        validator = new Validator(this);
        validator.setValidationListener(this);

        phone = findViewById(R.id.phone);
        password = findViewById(R.id.password);

        signIn = findViewById(R.id.sign_in_btn);
        signIn.setOnClickListener((v) -> validator.validate());

        signUp = findViewById(R.id.sign_up_btn);
        signUp.setOnClickListener((v) -> goToAndFinish(RegistrationActivity.class));


        //createNotificationChanel();

        //  startActivity(new Intent(this, MapActivity.class));

    }

    private void createNotificationChanel() {
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(com.dev.wedrive.service.NotificationManager.CHANNEL_ID, "My channel",
                    NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("My channel description");
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.enableVibration(false);
            notificationManager.createNotificationChannel(channel);
        }
    }


    @Override
    public void onValidationSucceeded() {
        ApiUser data = new ApiUser(phone.getText().toString(), password.getText().toString());
        userService.login(data, (token) -> {
            userService.getUser(token.userId, (user) -> {
                if (user.status.equals(ApiUser.STATUS_CREATED))
                    goToAndFinish(ConfirmRegistrationActivity.class);
                else
                    goToAndFinish(TypeActivity.class);
            }, (error) -> password.setError(error.getMessage()));
        }, (error) -> password.setError(error.getMessage()));
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();

            String message = error.getCollatedErrorMessage(this);

            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }
}
