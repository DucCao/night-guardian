package com.condorhero89.nightguardian;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;

import com.condorhero89.nightguardian.adapter.MyContactAdapter;
import com.condorhero89.nightguardian.model.MyContact;
import com.condorhero89.nightguardian.util.ContactUtil;
import com.condorhero89.nightguardian.util.NightGuardianPreference;
import com.condorhero89.nightguardian.util.TimeFormatUtil;
import com.condorhero89.nightguardian.util.TimerUtil;

public class MainActivity extends Activity {
    
    private TextView txtTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("TEST", "start app");
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        txtTimer = (TextView) findViewById(R.id.txtTimer);
        showTimerText();
        
        ListView listView = (ListView) findViewById(R.id.listView);
        final MyContactAdapter adapter = new MyContactAdapter(this, ContactUtil.getAllContacts(getApplicationContext()));
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                MyContact myContact = adapter.getItem(position);
                
                myContact.setImportant(!myContact.isImportant());
                adapter.notifyDataSetChanged();
                
                if (myContact.isImportant()) {
                    ContactUtil.addImportContact(getApplicationContext(), myContact);
                } else {
                    ContactUtil.removeImportantContact(getApplicationContext(), myContact);
                }
            }
        });
        
        boolean needStartService = TimerUtil.startTimer(getApplicationContext());
        if (needStartService) {
        	TimerUtil.stopTimer(getApplicationContext());
        }
    }
    
    private void showTimerText() {
        txtTimer.setText(String.format("Start %s:%s (today), Stop %s:%s (tomorrow)", 
                TimeFormatUtil.getTimeFormat(NightGuardianPreference.getStartTime(getApplicationContext())),
                TimeFormatUtil.getTimeFormat(NightGuardianPreference.getStartMinute(getApplicationContext())),
                TimeFormatUtil.getTimeFormat(NightGuardianPreference.getStopTime(getApplicationContext())),
                TimeFormatUtil.getTimeFormat(NightGuardianPreference.getStopMinute(getApplicationContext()))
        ));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	menu.add(0, 0, 0, "Timer");
    	menu.add(0, 1, 0, "Stop the guardian");
        return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	if (item.getItemId() == 0) {
    		showTimerDialog();
    	} else if (item.getItemId() == 1) {
    		TimerUtil.cancel(getApplicationContext());
    		
    		// TODO stop the service
    		
    		// TODO set the text
    	}
        return super.onOptionsItemSelected(item);
    }

    private void showTimerDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.timer_dialog, null);
        
        TimePicker tpStart = (TimePicker) view.findViewById(R.id.tpStart);
        tpStart.setCurrentHour(NightGuardianPreference.getStartTime(getApplicationContext()));
        tpStart.setCurrentMinute(NightGuardianPreference.getStartMinute(getApplicationContext()));
        tpStart.setOnTimeChangedListener(new OnTimeChangedListener() {
            
            @Override
            public void onTimeChanged(TimePicker arg0, int hourOfDay, int minute) {
                NightGuardianPreference.setStartTime(getApplicationContext(), hourOfDay);
                NightGuardianPreference.setStartMinute(getApplicationContext(), minute);
                
                Log.e("TEST", String.format("Set start time %d:%d", hourOfDay, minute));
            }
        });
        
        TimePicker tpStop = (TimePicker) view.findViewById(R.id.tpStop);
        tpStop.setCurrentHour(NightGuardianPreference.getStopTime(getApplicationContext()));
        tpStop.setCurrentMinute(NightGuardianPreference.getStopMinute(getApplicationContext()));
        tpStop.setOnTimeChangedListener(new OnTimeChangedListener() {
            
            @Override
            public void onTimeChanged(TimePicker arg0, int hourOfDay, int minute) {
                NightGuardianPreference.setStopTime(getApplicationContext(), hourOfDay);
                NightGuardianPreference.setStopMinute(getApplicationContext(), minute);
                
                Log.e("TEST", String.format("Set stop time %d:%d", hourOfDay, minute));
            }
        });
        
        AlertDialog.Builder builder = new Builder(this);
        builder.setView(view);
        AlertDialog dialog = builder.create(); 
        dialog.setOnDismissListener(new OnDismissListener() {
            
            @Override
            public void onDismiss(DialogInterface arg0) {
                showTimerText();
            }
        });
        dialog.show();
    }
}
