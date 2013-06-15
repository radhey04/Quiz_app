package com.example.quiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

public class MyDBAdapter {
	
	// Calls public void onCreate(SQLiteDatabase db)
	MyDBHelper db_helper;
	// Database naming
	String DB_NAME="values";
	String TAB_NAME="qbank";
	// Do most of the communication
	// db_helper is just to do the upper-level 
	SQLiteDatabase db=null;
	
	public Integer N=1;			//The total count
	
	public MyDBAdapter(Context context) 
	{
		// TODO Auto-generated constructor stub
		 db_helper = new MyDBHelper(context, DB_NAME, null, 1);
		 Cursor c1=getAllQs();
		 N=c1.getCount();			// N now has the count
		 Log.d("Debug_mydbadapter","Total no. of elements =>"+N);
		 close();					// Closing the link for Qs();
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
		bag.put("option",option);
		
		open();
		//Insert into the table qbank the contents of the bag.
		db.insert("qbank", null, bag);
		Log.d("Debug_mydbadapter","Inserted an entry");
		close();
		N=N+1;								// Update N
	}
	
	public void deleteEntry(Integer QN)
	{
		open();
		//Insert into the table fruits the contents of the bag.
		Log.d("Debug_mydbadapter","Deleting an entry");
		db.delete(TAB_NAME,"qno = ?", new String[]{QN.toString()});
		Log.d("Debug_mydbadapter",QN.toString());
		N=N-1;
		close();
	}
		
	public Cursor getAllQs()
	{
		open();
		Log.d("Debug_mydbadapter","Asked to fetch the entire question bank");
		String query="SELECT * FROM ";
		query=query.concat(TAB_NAME);
		Cursor c1 = db.rawQuery(query, null);
		Log.d("Debug_mydbadapter","Fetched the entire question bank");
		return c1;
	}
	
	public Cursor getQno(Integer QN)
	{
		open();
		Log.d("Debug_mydbadapter","Got to fetch");
		Log.d("Debug_mydbadapter",QN.toString());
		String query="SELECT * FROM "+TAB_NAME+" WHERE Qno=?";
		Cursor c=null;
		if ( (QN <= N) && (QN > 0) )
		{
			c=db.rawQuery(query, new String[]{QN.toString()});
			c.moveToNext();				// Moving the cursor forward
			Log.d("Debug_mydbadapter","Got it");
		}
		else
		{
				Log.d("Debug_mydbadapter","Bad luck");						
		}
		close();
		return c;						// Returns null if failed
	}
}
