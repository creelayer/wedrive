package com.dev.wedrive;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dev.wedrive.entity.ApiUser;
import com.dev.wedrive.service.UserService;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, Validator.ValidationListener {


    private UserService userService;

    private Validator validator;

    @NotEmpty
    @Email
    private EditText email;

    @NotEmpty
    @Password(min = 4//, scheme = Password.Scheme.ALPHA_NUMERIC_MIXED_CASE_SYMBOLS
    )
    private EditText password;

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

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        Button signIn = findViewById(R.id.signIn);
        signIn.setOnClickListener(this);

        Intent myIntent = new Intent(this, MapActivity.class);
        startActivity(myIntent);

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.signIn) {
            validator.validate();
        }

    }

    @Override
    public void onValidationSucceeded() {
        ApiUser data = new ApiUser(email.getText().toString(), password.getText().toString());
        userService.login(data, (user) -> startActivity(new Intent(this, TypeActivity.class)), (error) -> password.setError(error.getMessage()));
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
