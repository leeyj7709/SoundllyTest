package com.soundllytest;

import java.util.ArrayList;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.soundlly.sdk.Soundlly;
import com.soundlly.sdk.SoundllyCore;
import com.soundlly.sdk.SoundllyInterface;
import com.soundlly.sdk.net.model.AttributesModel;
import com.soundlly.sdk.net.model.ContentsModel;
import com.soundlly.sdk.service.SoundllyServiceInterface;

public class SoundllyBroadcastReceiver extends BroadcastReceiver {
    private Context mContext = null;

    @Override
    public void onReceive(Context context, Intent intent) {
        mContext = context;

        if ((context.getPackageName() + SoundllyServiceInterface.ACTION_RESULT).equals(intent.getAction())) {
            handleSoundllyResult(intent);
        } else if ((context.getPackageName() + SoundllyServiceInterface.ACTION_ON_BIND).equals(intent.getAction())) {
            Soundlly soundlly = SoundllyCore.getSoundlly();

            if (soundlly != null) {
                soundlly.setApplicationId(TestApplication.API_KEY);
            }

            Intent newIntent = new Intent();
            newIntent.setAction(TestApplication.INTENT_ACTION_SDK_ON_BIND);
            context.sendBroadcast(newIntent);
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
            Soundlly soundlly = SoundllyCore.getSoundlly();

            if (soundlly != null) {
                soundlly.stopListening(TestApplication.API_KEY);
            }

            mContext.sendBroadcast(new Intent(TestApplication.INTENT_ACTION_RESULT_OK));

            String url = null;
            String comment = null;
            String code = null;

            for (AttributesModel model : attributes) {
                if ("string".equals(model.getType())) {
                    if ("url".equals(model.getKey())) {
                        url = model.getValue();
                    } else {
                        comment = model.getValue();
                    }
                } else if ("integer".equals(model.getType())) {
                    if ("code".equals(model.getKey())) {
                        code = model.getValue();
                    }
                }
            }

            if (url != null) {
                Intent newIntent = new Intent(mContext, ResultUrlActivity.class);
                newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                newIntent.putExtra("url", url);
                mContext.startActivity(newIntent);
            }
        }
    }
}
