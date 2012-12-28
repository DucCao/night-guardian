package com.condorhero89.nightguardian.db;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.condorhero89.nightguardian.model.MyContact;

/*
 * Provide helper functions to interact with database
 */
public class DatabaseHelper extends SQLiteOpenHelper {
	public final String TAG = getClass().getSimpleName();
	
	private static final String DB_NAME = "night-guardian";
	
	private static final String TABLE_IMPORTANT_CONTACTS = "important_contacts";
	
	private SQLiteDatabase db;
	private static DatabaseHelper instance = null;

	public static DatabaseHelper getInstance(Context context) {
		if (instance == null) {
			try {
				instance = new DatabaseHelper(context);
				
				// create tables
				instance.createContactTable();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return instance;
	}
	
	private void createContactTable() {
		if (!isExistTable(TABLE_IMPORTANT_CONTACTS)) {
			db.execSQL("create table " + TABLE_IMPORTANT_CONTACTS + " (name, phone_number, important)");
		}
	}

	/*
	 * UTILITIES
	 */
	private DatabaseHelper(Context context) {
		super(context, DB_NAME, null, 1);
		db = context.openOrCreateDatabase(DB_NAME, 0, null);
	}

	public boolean isNotOpen() {
		return !db.isOpen();
	}

	@Override
	public void close() {
		synchronized (db) {
			if (db != null)
				db.close();
		}
		super.close();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}
	
	public boolean isExistTable(String tableName) {
		boolean isExist = true;
		
		Cursor cursor = db.rawQuery("SELECT * FROM SQLITE_MASTER WHERE NAME = '" + tableName + "'", null);
		if (cursor.getCount() == 0) {
			isExist = false;
		}
		if (cursor != null) cursor.close();
			
		return isExist;
	}
	
	public void beginTransaction() {
		db.beginTransaction();
	}
	
	public void endTransaction() {
		db.setTransactionSuccessful();
		db.endTransaction();
	}
	
	public void insertImportantContact(MyContact myContact) {
	    ContentValues values = new ContentValues();
	    values.put("name", myContact.getName());
	    values.put("phone_number", myContact.getPhoneNumber());
	    
	    db.insert(TABLE_IMPORTANT_CONTACTS, null, values);
	}
	
	public void deleteImportantContact(MyContact myContact) {
	    db.delete(TABLE_IMPORTANT_CONTACTS, "phone_number='" + myContact.getPhoneNumber() + "'", null);
	}
	
	public ArrayList<MyContact> getAllImportantContacts() {
		Cursor cursor = db.rawQuery("select * from " + TABLE_IMPORTANT_CONTACTS, null);
		
		ArrayList<MyContact> mAllImportantContacts = new ArrayList<MyContact>();
		try {
			if (cursor.moveToFirst()) {
			    MyContact myContact = null;
				int phone = cursor.getColumnIndex("phone_number");
				int name = cursor.getColumnIndex("name");
				do {
					myContact = new MyContact(cursor.getString(phone), cursor.getString(name));
					
					mAllImportantContacts.add(myContact);
				} while (cursor.moveToNext());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cursor != null) cursor.close();
		}
		
		return mAllImportantContacts;
	}
}
