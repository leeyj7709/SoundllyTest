package com.soundllytest;

import android.app.Application;
import android.util.Log;

import com.soundlly.sdk.SoundllyCore;

public class TestApplication extends Application {
    public static final String API_KEY = "54c5c5b4-0a9205df-da905901-d05fifb8";

    @Override
    public void onCreate() {
        Log.e("TEST", "TestApplication : onCreate");
        super.onCreate();

        SoundllyCore.init(this);
    }
}
