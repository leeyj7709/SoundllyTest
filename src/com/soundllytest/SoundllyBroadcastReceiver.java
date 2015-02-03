package com.soundllytest;

import java.util.ArrayList;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.soundlly.sdk.Soundlly;
import com.soundlly.sdk.SoundllyCore;
import com.soundlly.sdk.SoundllyInterface;
import com.soundlly.sdk.net.model.AttributesModel;
import com.soundlly.sdk.net.model.ContentsModel;
import com.soundlly.sdk.service.SoundllyServiceInterface;

public class SoundllyBroadcastReceiver extends BroadcastReceiver {
    public String url = "";
    public String comment = "";
    public String code = "";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("TEST", "onReceive : " + intent.getAction());

        if ((context.getPackageName() + SoundllyServiceInterface.ACTION_RESULT).equals(intent.getAction())) {
            handleSoundllyResult(intent);
        } else if ((context.getPackageName() + SoundllyServiceInterface.ACTION_ON_BIND).equals(intent.getAction())) {
            Soundlly sm = SoundllyCore.getSoundlly();
            if (sm != null) {
                sm.setApplicationId(TestApplication.API_KEY);
            }
        } else {
            // nothing to do
        }
    }

    private void handleSoundllyResult(Intent intent) {
        switch (intent.getExtras().getInt(SoundllyInterface.EXTRA_STATUS_CODE)) {
            case SoundllyInterface.CODE_OK:
                doLoadContents(intent);
                break;
            case SoundllyInterface.CODE_NO_CONTENTS:
                // handling code for result
                break;
            case SoundllyInterface.CODE_SERVER_ERROR:
                // handling code for result
                break;
            case SoundllyInterface.CODE_TIME_OUT:
                // handling code for result
                break;
            case SoundllyInterface.CODE_UNAUTHORIZED:
                // handling code for result
                break;
            case SoundllyInterface.CODE_UNKNOWN_ERROR:
                // handling code for result
                break;
            case SoundllyInterface.CODE_NO_WATERMARK:
                // handling code for result
                break;
            case SoundllyInterface.CODE_MIC_ERROR:
                // handling code for result
                break;
            default:
                break;
        }
    }

    private void doLoadContents(Intent intent) {
        ContentsModel contents = intent.getParcelableExtra(SoundllyInterface.EXTRA_CONTENTS);
        ArrayList<AttributesModel> attributes = contents.getAttributes();
        if (attributes != null) {
            for (AttributesModel model : attributes) {
                Log.e("TEST", "type : " +model.getType() + ",    key : " + model.getKey() + ",    value : " + model.getValue());
                if (model.getType().equals("string")) {
                    if (model.getKey().equals("url")) {
                        url = model.getValue();
                    } else {
                        comment = model.getValue();
                    }
                } else if (model.getType().equals("integer")) {
                    if (model.getKey().equals("code")) {
                        code = model.getValue();
                    }
                }
            }
        }
    }
}
