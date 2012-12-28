package com.condorhero89.nightguardian.service;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

import com.condorhero89.nightguardian.receiver.SmsAndCallBroadcastReceiver;
import com.condorhero89.nightguardian.util.RingerUtil;
import com.condorhero89.nightguardian.util.RingerUtil.RingerMode;

public class NightGuardianService extends Service {
    private static final String TAG = NightGuardianService.class.getSimpleName();
    
    private SmsAndCallBroadcastReceiver mReceiver;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        RingerUtil.setRingerMode(this, RingerMode.SILENT);
        
        initReceiver();
        
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
    
    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        
        RingerUtil.setRingerMode(this, RingerMode.NORMAL);
        
        stopReceiver();
        
        super.onDestroy();
    }
    
    private void initReceiver() {
        Log.d(TAG, "initReceiver");
        
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.provider.Telephony.SMS_RECEIVED");
        filter.addAction("android.intent.action.PHONE_STATE");
        mReceiver = SmsAndCallBroadcastReceiver.getInstance();
        registerReceiver(mReceiver, filter);
    }
    
    private void stopReceiver() {
        if (mReceiver != null) {
            // TODO do we need unregisterPhoneStateListener?
            mReceiver.unregisterPhoneStateListener();
            unregisterReceiver(mReceiver);
        }
    }
}
