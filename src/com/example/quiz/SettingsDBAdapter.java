package com.example.quiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class SettingsDBAdapter {
	
	// Calls public void onCreate(SQLiteDatabase db)
	SettingsDBHelper db_helper;
	// Database naming
	String DB_NAME="values";
	// Do most of the communication
	// db_helper is just to do the upper-level 
	SQLiteDatabase db;
	
	public SettingsDBAdapter(Context context) {
		// TODO Auto-generated constructor stub
		 db_helper = new SettingsDBHelper(context, DB_NAME, null, 1);
	}
	
	 public void open() throws SQLException 
	   {
	      //open database in reading/writing mode
	      db = db_helper.getWritableDatabase();
	   } 

	   public void close() 
	   {
	      if (db != null)
	         db.close();
	   }	
	
	
	public void insertset(String Name,String ID, Integer Timer)
	{
		// ContentValues which is like bundle
		ContentValues bag = new ContentValues();
		// Order matters. It should be as same as the columns
		// Contents of the bag will increase with every put statement
		bag.put("Name", Name);
		bag.put("ID", ID);
		bag.put("Timer", Timer);
		open();
		//Insert into the table qbank the contents of the bag.
		db.insert("sett", null, bag);
		close();
	}
	
	public void dropsheet()
	{
		String query="DELETE FROM ";
		query=query.concat("sett");
		db.execSQL(query);
		Log.d("Debug","Dropped scoresheet");
	}
	
	public Cursor getAllSet()
	{
		open();
		// SELECT NAME FROM fruits WHERE NAME=?
		Cursor c1 = db.rawQuery("SELECT * FROM sett", null);
		return c1;
	}
}
