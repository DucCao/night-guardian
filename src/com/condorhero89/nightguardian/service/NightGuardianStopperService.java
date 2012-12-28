package com.condorhero89.nightguardian.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class NightGuardianStopperService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        stopService(new Intent(getApplicationContext(), NightGuardianService.class));
        stopSelf();
        
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.e("stopper", "stopper");
        super.onDestroy();
    }
}
