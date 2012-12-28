package com.condorhero89.nightguardian;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.condorhero89.nightguardian.adapter.MyContactAdapter;
import com.condorhero89.nightguardian.model.MyContact;
import com.condorhero89.nightguardian.util.ContactUtil;
import com.condorhero89.nightguardian.util.TimerUtil;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("TEST", "start app");
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
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
        
//        startService(new Intent(getApplicationContext(), NightGuardianService.class));
        
        TimerUtil.startTimer(getApplicationContext());
        TimerUtil.stopTimer(getApplicationContext());
    }

    @Override
    protected void onDestroy() {
//        stopService(new Intent(getApplicationContext(), NightGuardianService.class));
        super.onDestroy();
    }

}
