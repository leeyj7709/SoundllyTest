package com.soundllytest;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.soundlly.sdk.Soundlly;
import com.soundlly.sdk.SoundllyCore;

public class MainActivity extends Activity {
    private Soundlly mSoundlly = null;

    private boolean mIsStart = false;

    private RelativeLayout mInitLayout = null;

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (TestApplication.INTENT_ACTION_SDK_ON_BIND.equals(action)) {
                if ( mInitLayout.getVisibility() == View.VISIBLE) {
                    mInitLayout.setVisibility(View.INVISIBLE);
                }
            } else if (TestApplication.INTENT_ACTION_RESULT_OK.equals(action)) {
                mIsStart = false;
                Button button = (Button)findViewById(android.R.id.button1);
                button.setText(R.string.start);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSoundlly = SoundllyCore.getSoundlly();
        mSoundlly.setDeveoloperMode();

        registerReceiver();

        initLayout();
    }

    private void initLayout() {
        setContentView(R.layout.main);

        mInitLayout = (RelativeLayout)findViewById(R.id.init_layout);
        TextView initText = (TextView) findViewById(R.id.txt_init);
        initText.startAnimation(AnimationUtils.loadAnimation(this, R.anim.alpha));
    }

    @Override
    protected void onResume() {
        super.onResume();

        mSoundlly.bindSoundllyService();
    }

    @Override
    protected void onPause() {
        super.onPause();

        mSoundlly.unbindSoundllyService();
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver();

        super.onDestroy();
    }

    private void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(TestApplication.INTENT_ACTION_SDK_ON_BIND);
        intentFilter.addAction(TestApplication.INTENT_ACTION_RESULT_OK);

        registerReceiver(mBroadcastReceiver, intentFilter);
    }

    private void unregisterReceiver() {
        unregisterReceiver(mBroadcastReceiver);
    }

    private void setListenWatermark(boolean isListen) {
        mSoundlly.setListenWatermark(TestApplication.API_KEY, isListen);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case android.R.id.button1:
                if (mIsStart) {
                    mSoundlly.stopListening(TestApplication.API_KEY);
                    ((Button)v).setText(R.string.start);
                } else {
                    mSoundlly.startListening(TestApplication.API_KEY);
                    ((Button)v).setText(R.string.stop);
                }

                mIsStart = !mIsStart;
                break;

            default:
                break;
        }
    }
}
