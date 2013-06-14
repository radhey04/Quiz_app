package com.example.quiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class MyDBAdapter {
	
	// Calls public void onCreate(SQLiteDatabase db)
	MyDBHelper db_helper;
	// Database naming
	String DB_NAME="values";
	// Do most of the communication
	// db_helper is just to do the upper-level 
	SQLiteDatabase db;
	
	public MyDBAdapter(Context context) {
		// TODO Auto-generated constructor stub
		 db_helper = new MyDBHelper(context, DB_NAME, null, 1);
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
	
	
	public void insertQ(String quest,String opta,String optb,String optc,String optd,String option)
	{
		// ContentValues which is like bundle
		ContentValues bag = new ContentValues();
		// Order matters. It should be as same as the columns
		// Contents of the bag will increase with every put statement
		bag.put("quest", quest);
		bag.put("opta", opta);
		bag.put("optb", optb);
		bag.put("optc", optc);
		bag.put("optd", optd);
		
		open();
		//Insert into the table qbank the contents of the bag.
		db.insert("qbank", null, bag);
		close();
	}
	
	public void deleteFruit(String fruit2bdeleted)
	{
		open();
		//Insert into the table fruits the contents of the bag.
		db.delete("qbank","qno = ?", new String[]{fruit2bdeleted});
		close();
	}
	
	public Cursor getAllQs()
	{
		open();
		// SELECT NAME FROM fruits WHERE NAME=?
		Cursor c1 = db.rawQuery("SELECT * FROM qbank", null);
		return c1;
	}
	
	public Cursor getQno(Integer QN)
	{
		open();
		//Cursor c1 = db.rawQuery("SELECT FROM qbank WHERE Qno = ?", new String[]{QN.toString()});
		Cursor c1 = null;
		Cursor cw=getAllQs();
		Integer QNt=0;
		while(cw.moveToNext())
		{
			QNt=cw.getInt(0);
			if(QNt==QN)
			{
				c1=cw;
				break;
			}
		}
		return c1;
	}
	
	
	

}
