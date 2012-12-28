package com.condorhero89.nightguardian.util;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.condorhero89.nightguardian.model.MyContact;

public class ContactUtil {

    public static ArrayList<MyContact> getAllContacts(Context context) {
        ArrayList<MyContact> mAllMyContacts = new ArrayList<MyContact>();
        
        Cursor mCursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
        while (mCursor.moveToNext()) {
            String name = mCursor.getString(mCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = mCursor.getString(mCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            phoneNumber = phoneNumber.replace("-", "");
            phoneNumber = phoneNumber.replace(" ", "");
            phoneNumber = phoneNumber.substring(1);
            
            mAllMyContacts.add(new MyContact(phoneNumber, name));
        }
        mCursor.close();
        
        return mAllMyContacts;
    }
}
