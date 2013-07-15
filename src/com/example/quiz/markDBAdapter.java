package com.example.quiz;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

@SuppressLint("SimpleDateFormat")
public class markDBAdapter {
	
	// Calls public void onCreate(SQLiteDatabase db)
	markDBHelper db_helper;
	// Database naming
	String DB_NAME="Marksheet";
	String TAB_NAME="marks";
	// Do most of the communication
	// db_helper is just to do the upper-level 
	SQLiteDatabase db;
	
	public Integer N=0;			//The total count
	public String perf="";
	public markDBAdapter(Context context) {
		 db_helper = new markDBHelper(context, DB_NAME, null, 1);
// Below code not required for the first run as anyways we are going to 
// drop the sheet. :) But since we are going to call it in user_publish, we
// need it.
		 Cursor c1=getAllperf();
		 N=c1.getCount();			// Initialized
		 c1.close();
		 Log.d("Debug_markdbadapter","Total no. of elements =>"+N);
		 close();					// Closing the link for getAllans();
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
	
	   //(no integer primary key autoincrement, Quizname, Datetaken, Score, Outof);";
	public void insertmark(Integer slno,String quizname, String score,String outof)
	{
		// ContentValues which is like bundle
		ContentValues bag = new ContentValues();
		// Order matters. It should be as same as the columns
		// Contents of the bag will increase with every put statement
		
		SimpleDateFormat datef= new SimpleDateFormat("dd MMM yyyy");
		
		Date d = new Date();
        String datetaken=datef.format(d.getTime());
    	bag.put("slno",slno);
		bag.put("quizname", quizname);
		bag.put("datetaken", datetaken);
		bag.put("score", score);
		bag.put("outof", outof);
		Log.d("Debug_markdbadapter","Inserting quizname: "+quizname);
		Log.d("Debug_markdbadapter","Inserting date: "+datetaken);
		Log.d("Debug_markdbadapter","Inserting score: "+score);
		Log.d("Debug_markdbadapter","Inserting Maxmarks: "+outof);
		
		open();
		//Insert into the table qbank the contents of the bag.
		db.insert(TAB_NAME, null, bag);
		close();
		N=N+1;
	}
	
	public Cursor getAllperf()
	{
		open();
		Log.d("Debug_markdbadapter","Asked to fetch the scoresheet");
		String query="SELECT * FROM ";
		query=query.concat(TAB_NAME);
		Cursor c1 = db.rawQuery(query, null);
		Log.d("Debug_markdbadapter","Fetched the scoresheet");
		return c1;
	}
	
	public void dropsheet()
	{
		open();
		String query="DELETE FROM ";
		query=query.concat(TAB_NAME);
		db.execSQL(query);
		Log.d("Debug_markdbadapter","Dropped scoresheet");
		close();
	}
}
