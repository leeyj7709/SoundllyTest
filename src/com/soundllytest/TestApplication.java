package com.soundllytest;

import android.app.Application;

import com.soundlly.sdk.SoundllyCore;

public class TestApplication extends Application {
    public static final String API_KEY = "54c5c5b4-0a9205df-da905901-d05fifb8";

    public static final String INTENT_ACTION_SDK_ON_BIND = "soundlly.intent.action.SDK_ON_BIND ";
    public static final String INTENT_ACTION_RESULT_OK = "soundlly.intent.action.RESULT_OK";

    @Override
    public void onCreate() {
        super.onCreate();

        SoundllyCore.init(this);
    }
}
