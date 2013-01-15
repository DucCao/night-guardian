package com.condorhero89.nightguardian.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class NightGuardianPreference {
    private static String PREF_NAME = "night-guardian";
    private static final int DEFAULT_START_TIME = 23;
    private static final int DEFAULT_STOP_TIME = 7;

    public static void setStartTime(Context context, int startTime) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, 0);
        Editor editor = prefs.edit();
        editor.putInt("start-time", startTime);
        editor.commit();
    }
    
    public static int getStartTime(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, 0);
        return prefs.getInt("start-time", DEFAULT_START_TIME);
    }
    
    public static void setStartMinute(Context context, int startMinute) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, 0);
        Editor editor = prefs.edit();
        editor.putInt("start-minute", startMinute);
        editor.commit();
    }
    
    public static int getStartMinute(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, 0);
        return prefs.getInt("start-minute", 0);
    }
    
    public static void setStopTime(Context context, int stopTime) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, 0);
        Editor editor = prefs.edit();
        editor.putInt("stop-time", stopTime);
        editor.commit();
    }
    
    public static int getStopTime(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, 0);
        return prefs.getInt("stop-time", DEFAULT_STOP_TIME);
    }
    
    public static void setStopMinute(Context context, int stopMinute) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, 0);
        Editor editor = prefs.edit();
        editor.putInt("stop-minute", stopMinute);
        editor.commit();
    }
    
    public static int getStopMinute(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, 0);
        return prefs.getInt("stop-minute", 0);
    }
}
