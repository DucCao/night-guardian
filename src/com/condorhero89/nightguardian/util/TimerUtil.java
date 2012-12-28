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
    private static final int START_TIME = 16;
    private static final int STOP_TIME = 17;

    public static void startTimer(Context context) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, START_TIME);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        
        Intent myIntent = new Intent(context, NightGuardianService.class);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, myIntent, 0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), ONE_DAY, pendingIntent);
    }
    
    public static void stopTimer(Context context) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, STOP_TIME);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        
        Intent myIntent = new Intent(context, NightGuardianStopperService.class);
        PendingIntent pendingIntent = PendingIntent.getService(context, 1, myIntent, 0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), ONE_DAY, pendingIntent);
    }
}
