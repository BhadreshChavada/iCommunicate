package com.icommunicate;

import android.app.Application;
import android.content.Context;
import android.content.IntentFilter;

import com.icommunicate.common.preferences.CommonsCore;

public class ICommunicateApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CommonsCore.init(getApplicationContext());
    }
}