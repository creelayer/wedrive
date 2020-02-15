package com.dev.wedrive;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dev.wedrive.entity.ApiToken;
import com.dev.wedrive.service.ApiService;
import com.dev.wedrive.service.UserService;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public abstract class AbstractCodeActivity extends AbstractAuthActivity {

    public static int CODE_GENERATE_WAIT_SEC = 60;
    protected UserService userService;

    protected CountDownTimer waiter;

    ArrayList<EditText> inputs;

    protected TextView subTitle;
    protected TextView resendButton;
    protected Button verifyBtn;


    public AbstractCodeActivity() {
        this.userService = new UserService();
    }

    public abstract void send(String code);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code);

        subTitle = findViewById(R.id.pin_sub_title);
        resendButton = findViewById(R.id.pin_resend);
        verifyBtn = findViewById(R.id.pin_verify_btn);
        verifyBtn.setOnClickListener((v) -> send(""));

        inputs = new ArrayList<>();
        inputs.add(findViewById(R.id.cn1));
        inputs.add(findViewById(R.id.cn2));
        inputs.add(findViewById(R.id.cn3));
        inputs.add(findViewById(R.id.cn4));

        for (int i = 0; i < inputs.size(); i++) {
            inputs.get(i).addTextChangedListener(new TextWatcher(i + 1));
            inputs.get(i).setOnFocusChangeListener(new FocusWatcher());
        }


        generateNewCode(null);

    }


    public void enableVerifyButton(boolean state) {

        if (verifyBtn == null)
            verifyBtn = findViewById(R.id.pin_verify_btn);

//        if (!state)
//            verifyBtn.setAlpha((float) 0.5);
//        else
//            verifyBtn.setAlpha(1);

        verifyBtn.setEnabled(state);
    }


    public void generateNewCode(View v) {
        subTitle.setText(getResources().getString(R.string.pin_sub_title, ""));
        userService.getCode(ApiService.getInstance().getToken(), (codeSession) -> {
            subTitle.setText(getResources().getString(R.string.pin_sub_title, String.valueOf(codeSession.id)));
            runCodeGenerateWaiter();
        });
    }

    private void runCodeGenerateWaiter() {

        if (waiter != null)
            return;

        waiter = new CountDownTimer(CODE_GENERATE_WAIT_SEC * 1000, 1000) {

            public void onTick(long millisUntilFinished) {
                resendButton.setText(getResources().getString(R.string.pin_resend_wait, String.valueOf(millisUntilFinished / 1000 - CODE_GENERATE_WAIT_SEC / 1000)));
                resendButton.setEnabled(false);
            }

            public void onFinish() {
                resendButton.setText(getResources().getString(R.string.pin_resend));
                resendButton.setEnabled(true);
                waiter = null;
            }
        }.start();
    }

    protected class FocusWatcher implements View.OnFocusChangeListener {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus)
                ((EditText) v).setText("");

            enableVerifyButton(false);
        }
    }

    protected class TextWatcher implements android.text.TextWatcher {

        private int next;

        private TextWatcher(int next) {
            this.next = next;
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            if (count > 0 && next < inputs.size())
                inputs.get(next).requestFocus();

            for (int i = 0; i < inputs.size(); i++)
                if (inputs.get(i).getText().length() == 0)
                    return;

            enableVerifyButton(true);

        }

        @Override
        public void afterTextChanged(Editable s) {

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
    }
}
