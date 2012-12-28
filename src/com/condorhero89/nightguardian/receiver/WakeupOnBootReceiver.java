package com.condorhero89.nightguardian.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.condorhero89.nightguardian.service.NightGuardianService;

public class WakeupOnBootReceiver extends BroadcastReceiver {
    private static final String TAG = "WakeupOnBootReceiver";

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.i(TAG, "Intent recieved: " + intent.getAction());
        
        context.startService(new Intent(context, NightGuardianService.class));
	}
}