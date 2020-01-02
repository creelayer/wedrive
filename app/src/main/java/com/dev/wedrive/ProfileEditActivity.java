package com.dev.wedrive;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.wedrive.entity.ApiProfile;
import com.dev.wedrive.helpers.DownloadImageTask;
import com.dev.wedrive.helpers.FileHelper;
import com.dev.wedrive.service.ProfileService;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

import lombok.Getter;

public class ProfileEditActivity extends AbstractActivity implements Validator.ValidationListener {


    private Validator validator;
    private ProfileService profileService;

    private ApiProfile profile;
    private ImageView image;

    @Getter
    @NotEmpty
    @Length(max = 100)
    private EditText name;

    @Getter
    @NotEmpty
    @Length(max = 100)
    private EditText lastName;

    @Getter
    private TextView phone;

    @Getter
    private TextView email;

    public ProfileEditActivity() {
        super();
        validator = new Validator(this);
        profileService = new ProfileService();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        requestStoragePermission();

        validator.setValidationListener(this);

        image = findViewById(R.id.profile_image);
        name = findViewById(R.id.profile_name);
        lastName = findViewById(R.id.profile_last_name);
        phone = findViewById(R.id.profile_phone);
        email = findViewById(R.id.profile_email);
        ImageButton imageChoose = findViewById(R.id.profile_image_choose);
        imageChoose.setOnClickListener((v) -> showFileChooser());

        Button saveBtn = findViewById(R.id.profile_save_btn);
        saveBtn.setOnClickListener((v) -> validator.validate());

        load();

    }

    @Override
    public void onValidationSucceeded() {
        profileService.updateProfile(profile.load(this), (v) -> {
            finish();
            return null;
        }, (e) -> {
            return null;
        });
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

    private void load() {
        profileService.getMyProfile((profile) -> {
            this.profile = profile;
            name.setText(profile.name);
            lastName.setText(profile.lastName);
            phone.setText(profile.phone);
            email.setText(profile.email);

            if (profile.image != null)
                new DownloadImageTask(image).execute(Constants.API_URL + "/uploads/profile/" + FileHelper.getStyleName(profile.image, "sm"));

            return null;
        }, (error) -> {
            return null;
        });
    }

    //method to show file chooser
    public void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    //handling the image chooser activity result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            profileService.uploadImage(FileHelper.getRealPathFromUri(this, data.getData()), (profile) -> {
                new DownloadImageTask(image).execute(Constants.API_URL + "/uploads/profile/" + FileHelper.getStyleName(profile.image, "sm"));
                return null;
            });
        }
    }
}
