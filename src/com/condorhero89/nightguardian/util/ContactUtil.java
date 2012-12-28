package com.condorhero89.nightguardian.util;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

import com.condorhero89.nightguardian.db.DatabaseHelper;
import com.condorhero89.nightguardian.model.MyContact;

public class ContactUtil {
    private static ArrayList<MyContact> mAllMyContacts = null;
    private static ArrayList<MyContact> mImportantContacts = null;

    public static ArrayList<MyContact> getAllContacts(Context context) {
        if (ContactUtil.mAllMyContacts == null) {
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
            
            ArrayList<MyContact> mImportantContacts = getImportantContacts(context);
            for (MyContact myContact : mAllMyContacts) {
                if (mImportantContacts.contains(myContact)) {
                    myContact.setImportant(true);
                }
            }
            
            ContactUtil.mAllMyContacts = mAllMyContacts;
        }
        
        return ContactUtil.mAllMyContacts;
    }
    
    public static ArrayList<MyContact> getImportantContacts(Context context) {
        if (ContactUtil.mImportantContacts == null) {
            ContactUtil.mImportantContacts = DatabaseHelper.getInstance(context).getAllImportantContacts();
        }
        
        return ContactUtil.mImportantContacts;
    }
    
    public static void addImportContact(Context context, MyContact myContact) {
        getImportantContacts(context).add(myContact);
        DatabaseHelper.getInstance(context).insertImportantContact(myContact);
    }
    
    public static void removeImportantContact(Context context, MyContact myContact) {
        getImportantContacts(context).remove(myContact);
        DatabaseHelper.getInstance(context).deleteImportantContact(myContact);
    }
    
    public static boolean isAnImportantContact(Context context, String incomingPhoneNumber) {
        if (incomingPhoneNumber.startsWith("0")) {
            incomingPhoneNumber = incomingPhoneNumber.substring(1);
        }
        
        ArrayList<MyContact> mImportantContacts = getImportantContacts(context);
        for (MyContact myContact : mImportantContacts) {
            if (myContact.getPhoneNumber().contains(incomingPhoneNumber)) {
                return true;
            }
        }
        
        return false;
    }
}
