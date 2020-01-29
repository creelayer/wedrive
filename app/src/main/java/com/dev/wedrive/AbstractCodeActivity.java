package com.dev.wedrive;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.dev.wedrive.entity.ApiUser;
import com.dev.wedrive.service.ApiService;
import com.dev.wedrive.service.UserService;

public abstract class AbstractCodeActivity extends AbstractAuthActivity {

    protected UserService userService;

    TextView codeId;
    EditText cn1;
    EditText cn2;
    EditText cn3;
    EditText cn4;
    EditText cn5;
    Button sendBtn;
    Button generateBtn;

    public AbstractCodeActivity() {
        this.userService = new UserService();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code);

        codeId = findViewById(R.id.code_id);
        cn1 = findViewById(R.id.cn1);
        cn2 = findViewById(R.id.cn2);
        cn3 = findViewById(R.id.cn3);
        cn4 = findViewById(R.id.cn4);
        cn5 = findViewById(R.id.cn5);

        sendBtn = findViewById(R.id.code_btn);
        generateBtn = findViewById(R.id.code_generate_btn);
        sendBtn.setOnClickListener((v) -> send(getInputCode()));
        generateBtn.setOnClickListener((v) -> userService.getCode(ApiService.getInstance().getToken(), (codeSession) -> codeId.setText(codeSession.id)));

        userService.getCode(ApiService.getInstance().getToken(), (codeSession) -> codeId.setText(String.valueOf(codeSession.id)));

    }


    protected void cleanCode() {
        cn1.setText("");
        cn2.setText("");
        cn3.setText("");
        cn4.setText("");
        cn5.setText("");
    }

    protected String getInputCode() {
        String code = "";
        code += cn1.getText().toString();
        code += cn2.getText().toString();
        code += cn3.getText().toString();
        code += cn4.getText().toString();
        code += cn5.getText().toString();

        return code;
    }

    public abstract void send(String code);
}
