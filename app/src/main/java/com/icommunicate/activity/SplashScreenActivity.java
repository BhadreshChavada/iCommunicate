package com.icommunicate.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.icommunicate.R;
import com.icommunicate.bean.ContactBean;
import com.icommunicate.common.FetchContacts;
import com.icommunicate.common.IntentUtils;
import com.icommunicate.common.preferences.PreferenceUtil;

import java.util.List;

import butterknife.ButterKnife;


public class SplashScreenActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        ButterKnife.bind(this);


        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.READ_CONTACTS) ==
                PackageManager.PERMISSION_GRANTED) {

            try {
                new FetchContacts(this, new FetchContacts.OnContactFetchListener() {
                    @Override
                    public void onContactFetch(List<ContactBean> contacts) {
                    }
                }).execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


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
