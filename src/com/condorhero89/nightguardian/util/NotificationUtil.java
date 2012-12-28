package com.condorhero89.nightguardian.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.condorhero89.nightguardian.R;

public class NotificationUtil {

	private static final int NOTIFICATION_ID = 1593;
	
	public static void showNotification(Context context) {
		NotificationManager notifManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification note = new Notification(R.drawable.ic_launcher, "NightGuardian", System.currentTimeMillis());
		note.flags |= Notification.FLAG_NO_CLEAR ;

		PendingIntent intent = PendingIntent.getActivity(context, 0, new Intent(), 0);
		note.setLatestEventInfo(context, "NightGuardian", "NightGuardian's running", intent);
		
		notifManager.notify(NOTIFICATION_ID, note);
	}
	
	public static void clearNotification(Context context) {
	    NotificationManager notifManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
	    notifManager.cancel(NOTIFICATION_ID);
	}
}
