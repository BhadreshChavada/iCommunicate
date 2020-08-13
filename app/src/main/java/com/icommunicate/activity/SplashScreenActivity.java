package com.icommunicate.activity;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.icommunicate.R;
import com.icommunicate.common.IntentUtils;
import com.icommunicate.common.preferences.PreferenceUtil;

import butterknife.ButterKnife;


public class SplashScreenActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        ButterKnife.bind(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpView();
    }

    private void setUpView() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (PreferenceUtil.isUserLoggedIn().get()) {
                    IntentUtils.redirectWithFinishTo(SplashScreenActivity.this, MasterActivity.class);
                } else {
                    IntentUtils.redirectWithFinishTo(SplashScreenActivity.this, LoginActivity.class);
                }
            }
        }, 2000);
    }

}
