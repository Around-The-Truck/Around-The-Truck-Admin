package kr.co.aroundthetruck.admin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import kr.co.aroundthetruck.admin.R;
import kr.co.aroundthetruck.admin.activity.register.RegisterAdminActivity;
import kr.co.aroundthetruck.admin.ui.ATTActivity;

public class SplashActivity extends ATTActivity {
    private static final String TAG = SplashActivity.class.getSimpleName();

    private Button btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setLayout();
        initialize();
    }

    @Override
    public void setLayout() {
        btnStart = (Button) findViewById(R.id.activity_splash_btn_start);
    }

    @Override
    public void initialize() {
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SplashActivity.this, RegisterAdminActivity.class));
            }
        });
    }
}
