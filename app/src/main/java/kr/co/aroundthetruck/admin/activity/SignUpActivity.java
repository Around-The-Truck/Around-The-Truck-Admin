package kr.co.aroundthetruck.admin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import kr.co.aroundthetruck.admin.R;
import kr.co.aroundthetruck.admin.ui.ATTActivity;


public class SignUpActivity extends ATTActivity {
    private static final String TAG = SignUpActivity.class.getSimpleName();

    private EditText editVerifyCode;
    private Button btnApply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setLayout();
        initialize();
    }

    @Override
    public void setLayout() {
        editVerifyCode = (EditText) findViewById(R.id.activity_main_edit_verify_code);
        btnApply = (Button) findViewById(R.id.activity_main_btn_apply);
    }

    @Override
    public void initialize() {
        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                finish();
            }
        });
    }
}
