package com.condorhero89.nightguardian.util;

import android.content.Context;
import android.media.AudioManager;

public class RingerUtil {
    public enum RingerMode {
        NORMAL, VIBRATE, SILENT
    }

    public static void setRingerMode(Context context, RingerMode mRingerMode) {
        int ringerMode = AudioManager.RINGER_MODE_NORMAL;
        if (mRingerMode.equals(RingerMode.NORMAL)) {
            ringerMode = AudioManager.RINGER_MODE_NORMAL;
        } else if (mRingerMode.equals(RingerMode.VIBRATE)) {
            ringerMode = AudioManager.RINGER_MODE_VIBRATE;
        } else if (mRingerMode.equals(RingerMode.SILENT)) {
            ringerMode = AudioManager.RINGER_MODE_SILENT;
        }
        
        AudioManager mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        mAudioManager.setRingerMode(ringerMode);
    }
}
