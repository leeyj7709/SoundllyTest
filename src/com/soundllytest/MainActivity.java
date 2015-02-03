package com.soundllytest;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.soundlly.sdk.Soundlly;
import com.soundlly.sdk.SoundllyCore;

public class MainActivity extends Activity {
    private Soundlly mSoundlly = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("TEST", "onCreate");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        mSoundlly = SoundllyCore.getSoundlly();
        mSoundlly.setDeveoloperMode();
    }

    @Override
    protected void onResume() {
        Log.e("TEST", "onResume");
        super.onResume();

//        setListenWatermark(false);
        mSoundlly.bindSoundllyService();
    }

    @Override
    protected void onPause() {
        Log.e("TEST", "onPause");
        super.onPause();

//        setListenWatermark(true);
        mSoundlly.unbindSoundllyService();
    }

    private void setListenWatermark(boolean isListen) {
        mSoundlly.setListenWatermark(TestApplication.API_KEY, isListen);
    }
}
