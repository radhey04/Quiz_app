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
	String DB_NAME="SETTINGS";
	// Do most of the communication
	// db_helper is just to do the upper-level 
	SQLiteDatabase db;
	
	public String Name="";
	public String ID="";
	
	
	public SettingsDBAdapter(Context context) {
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
	
	
	public void updateset(String Name_upd,String ID_upd, Integer Timer)
	{
		// ContentValues which is like bundle
		Log.d("Debug_settings","Updating settings");
		ContentValues bag = new ContentValues();
		// Order matters. It should be as same as the columns
		// Contents of the bag will increase with every put statement
		String loc="sno=1";			//sno is not autoincremented :)
		bag.put("Name", Name_upd);
		bag.put("ID", ID_upd);
		bag.put("Timer", Timer);
		//Insert into the table qbank the contents of the bag.
		open();
		Cursor c1=getAllSet();
		if(c1.getCount()==0)
		{
			db.insert("sett",null,bag);
			Log.d("Debug_settings","Settings inserted");
		}
		else
		{
			db.update("sett", bag,loc,null );
			Log.d("Debug_settings","Settings updated");
		}
		Cursor cf=getAllSet();
		Name=Name_upd;
		ID=ID_upd;
		Log.d("Debug_settings","New values");
		while(cf.moveToNext())
		{
	    	Log.d("Debug_settings",cf.getString(1)+" "+cf.getString(2)+"");
	    	Log.d("Debug_settings",Name+" "+ID+" ");
		}
	    close();
	}
	
	public void updatemem()
	{
		Log.d("Debug_settings","Updating data members");
		open();
		Cursor ca=getAllSet();
		if(ca.getCount()==0)
		{
			Name="";
			ID="";
			Log.d("Debug_settings","Table empty");
		}
		else
		{
			ca.moveToNext();				 //Move to the first element
			Name=ca.getString(1);
			ID=ca.getString(2);
			Log.d("Debug_settings",Name+" "+ID+' ');
			Log.d("Debug_settings","Settings updated");
		}
		close();
	}
	
	public void dropset()
	{
		open();
		String query="DELETE FROM ";
		query=query.concat("sett");
		db.execSQL(query);
		Log.d("Debug_settings","Dropped settings");
		close();
	}
	// Open before calling it and close it after using the cursor.
	public Cursor getAllSet()
	{
		// SELECT NAME FROM fruits WHERE NAME=?
		Cursor c1 = db.rawQuery("SELECT * FROM sett", null);
		return c1;
	}
}
