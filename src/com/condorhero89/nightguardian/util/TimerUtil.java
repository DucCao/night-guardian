package com.condorhero89.nightguardian.util;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.condorhero89.nightguardian.service.NightGuardianService;
import com.condorhero89.nightguardian.service.NightGuardianStopperService;

public class TimerUtil {
    private static final int ONE_DAY = 1000 * 60 * 60 * 24;

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
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, myIntent, 0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), ONE_DAY, pendingIntent);
        
        return true;	// need start service
    }
    
    public static void stopTimer(Context context) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        calendar.set(Calendar.HOUR_OF_DAY, NightGuardianPreference.getStopTime(context));
        calendar.set(Calendar.MINUTE, NightGuardianPreference.getStopMinute(context));
        calendar.set(Calendar.SECOND, 0);
        
        Intent myIntent = new Intent(context, NightGuardianStopperService.class);
        PendingIntent pendingIntent = PendingIntent.getService(context, 1, myIntent, 0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), ONE_DAY, pendingIntent);
    }
    
    public static void cancel(Context context) {
    	AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    	
    	Intent myIntent = new Intent(context, NightGuardianService.class);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, myIntent, 0);
        alarmManager.cancel(pendingIntent);
        
        myIntent = new Intent(context, NightGuardianStopperService.class);
        pendingIntent = PendingIntent.getService(context, 1, myIntent, 0);
        alarmManager.cancel(pendingIntent);
    }
}
