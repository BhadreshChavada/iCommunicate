package com.icommunicate.twillio;

import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.icommunicate.R;
import com.icommunicate.common.preferences.PreferenceUtil;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

import butterknife.ButterKnife;

public class SendSMSActivity extends AppCompatActivity {

    public static final String ACCOUNT_SID = "AC2cd94b986d79821800d20776df45e3c7";
    public static final String AUTH_TOKEN = "eefa9589dc5a2aaf1432ad4dbcc87e1e";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_duplicate_duplicate);
        ButterKnife.bind(this);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                callSms();
            }
        }, 1000);
    }

    private void callSms() {
        Twilio.init(PreferenceUtil.byDefultDailNumber().get().replace("+", ""), AUTH_TOKEN);
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber(PreferenceUtil.byDefultDailNumber().get()),
                new com.twilio.type.PhoneNumber("+917990609085"),
                "Hi there!")
                .create();

        System.out.println(message.getSid());
    }
}