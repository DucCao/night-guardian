package com.condorhero89.nightguardian.util;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.condorhero89.nightguardian.service.NightGuardianService;
import com.condorhero89.nightguardian.service.NightGuardianStopperService;

public class TimerUtil {
	private static final String TAG = TimerUtil.class.getSimpleName();
    private static final int ONE_DAY = 1000 * 60 * 60 * 24;
    private static final int REQUEST_CODE_START = 0;
    private static final int REQUEST_CODE_STOP = 1;

    public static boolean startTimer(Context context) {
    	boolean isServiceRunning = ServiceUtil.isServiceRunning(context, NightGuardianService.class.getSimpleName());
    	if (isServiceRunning) {
    		return false;	// no need start service
    	}
    	
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, NightGuardianPreference.getStartTime(context));
        calendar.set(Calendar.MINUTE, NightGuardianPreference.getStartMinute(context));
        calendar.set(Calendar.SECOND, 0);
        
        Intent myIntent = new Intent(context, NightGuardianService.class);
        PendingIntent pendingIntent = PendingIntent.getService(context, REQUEST_CODE_START, myIntent, 0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), ONE_DAY, pendingIntent);
        
        return true;	// need start service
    }
    
    public static void stopTimer(Context context) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.DAY_OF_YEAR, 1);	// tomorrow = today + 1
        calendar.set(Calendar.HOUR_OF_DAY, NightGuardianPreference.getStopTime(context));
        calendar.set(Calendar.MINUTE, NightGuardianPreference.getStopMinute(context));
        calendar.set(Calendar.SECOND, 0);
        
        Intent myIntent = new Intent(context, NightGuardianStopperService.class);
        PendingIntent pendingIntent = PendingIntent.getService(context, REQUEST_CODE_STOP, myIntent, 0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), ONE_DAY, pendingIntent);
    }
    
    public static void cancel(Context context) {
    	AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    	
    	boolean isNightGuardianServiceRunning = ServiceUtil.isServiceRunning(context, NightGuardianService.class.getSimpleName());
    	if (isNightGuardianServiceRunning) {
    		Log.i(TAG, "cancel timer for NightGuardianService");
    		Intent myIntent = new Intent(context, NightGuardianService.class);
    		PendingIntent pendingIntent = PendingIntent.getService(context, REQUEST_CODE_START, myIntent, 0);
    		alarmManager.cancel(pendingIntent);
    		
    		// also stop the service
    		context.startService(new Intent(context, NightGuardianStopperService.class));
    		
    		Log.i(TAG, "cancel timer for NightGuardianStopperService");
    		myIntent = new Intent(context, NightGuardianStopperService.class);
    		pendingIntent = PendingIntent.getService(context, REQUEST_CODE_STOP, myIntent, 0);
    		alarmManager.cancel(pendingIntent);
    	}
    }
}
