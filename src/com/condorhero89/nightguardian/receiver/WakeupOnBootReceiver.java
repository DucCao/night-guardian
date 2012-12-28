package com.condorhero89.nightguardian.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class WakeupOnBootReceiver extends BroadcastReceiver {
    private static final String TAG = "SMSBroadcastReceiver";

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.i(TAG, "Intent recieved: " + intent.getAction());
        
        if (intent.getAction() == "android.intent.action.BOOT_COMPLETED") {
            // TODO start the service NightGuardian
        }
	}
}