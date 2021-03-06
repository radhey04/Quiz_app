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
	public String URL="";
	public Boolean ShowTimer=true;
	public Boolean disablehttp=true;
	
	
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
	
	public void updateset(String Name_upd,String ID_upd, Integer Timer, Integer dh, String url)
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
		bag.put("disablehttp", dh);
		bag.put("url", url);
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
		c1.close();
		Name=Name_upd;
		ID=ID_upd;
		URL=url;
		if(Timer==1)
			ShowTimer=true;
		else
			ShowTimer=false;
		if(dh==1)
			disablehttp=true;
		else
			disablehttp=false;
		Log.d("Debug_settings","New values");
		Log.d("Debug_settings",Name+" "+ID+" "+ShowTimer.toString()+disablehttp.toString()+" "+URL);
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
			ShowTimer=true;
			disablehttp=false;
			URL="";
			Log.d("Debug_settings","Table empty");
		}
		else
		{
			ca.moveToNext();				 //Move to the first element
			Name=ca.getString(1);
			ID=ca.getString(2);
			Integer timer=ca.getInt(3);
			Integer dh=ca.getInt(4);
			URL=ca.getString(5);
			if(timer==1)
				ShowTimer=true;
			else
				ShowTimer=false;
			if(dh==1)
				disablehttp=true;
			else
				disablehttp=false;
			Log.d("Debug_settings",Name+" "+ID+" "+ShowTimer.toString()+disablehttp.toString()+" "+URL);
			Log.d("Debug_settings","Settings updated");
		}
		ca.close();
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
