package com.example.quiz;

import java.io.ByteArrayOutputStream;

import com.example.quiz.R;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class MyDBAdapter {
	
	// Calls public void onCreate(SQLiteDatabase db)
	MyDBHelper db_helper;
	// Database naming
	String DB_NAME="Quiz";
	String TAB_NAME="qbank";
	// Do most of the communication
	// db_helper is just to do the upper-level 
	SQLiteDatabase db=null;
	String Q_Name,Admin_ID;
	
	public Integer N=1;			//The total count
	
	public MyDBAdapter(Context context) 
	{
		 db_helper = new MyDBHelper(context, DB_NAME, null, 1);
		 Cursor c1=getAllQs();
		 N=c1.getCount()-1;			// N now has the count
		 Log.d("Debug_mydbadapter","Total no. of elements =>"+N);
		 c1.close();
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
//		byte[] img={0};
//		Integer imgthere=0;
//		if(imagethere==true)
//		{
//			Bitmap icon = BitmapFactory.decodeResource(context.getResources(),R.drawable.eiffel);
//			ByteArrayOutputStream bos = new ByteArrayOutputStream();
//			icon.compress(Bitmap.CompressFormat.PNG, 100, bos);
//			
//			img = bos.toByteArray();
//			imgthere=1;
//			Log.d("Debug_myadapter","Image added");
//		}
//		Log.d("Debug_myadapter","No image added");
		// ContentValues which is like bundle
		ContentValues bag = new ContentValues();
		// Order matters. It should be as same as the columns
		// Contents of the bag will increase with every put statement
		bag.put("Qno", N+1);
		bag.put("quest", quest);
		bag.put("opta", opta);
		bag.put("optb", optb);
		bag.put("optc", optc);
		bag.put("optd", optd);
		bag.put("option",option);
//		bag.put("img",img);
//		bag.put("imagethere",imgthere);
		
		open();
		//Insert into the table qbank the contents of the bag.
		db.insert("qbank", null, bag);
		Log.d("Debug_mydbadapter","Inserted an entry");
		close();
		N=N+1;								// Update N
	}
	
	public void updateQ(Integer Qprev, Integer Qno, String quest,String opta,String optb,String optc,String optd,String option)
	{
		// ContentValues which is like bundle
		ContentValues bag = new ContentValues();
		// Order matters. It should be as same as the columns
		// Contents of the bag will increase with every put statement
		bag.put("Qno", Qno);
		bag.put("quest", quest);
		bag.put("opta", opta);
		bag.put("optb", optb);
		bag.put("optc", optc);
		bag.put("optd", optd);
		bag.put("option",option);
//		bag.put("img",img);
//		bag.put("imagethere",imagethere);

		open();
		//Insert into the table qbank the contents of the bag.
		db.update(TAB_NAME,bag,"Qno=?",new String []{Qprev.toString()});
		Log.d("Debug_mydbadapter","Updated "+Qprev+" to "+Qno);
		close();		
	}
	
	public void deleteEntry(Integer QN)
	{
		open();
		//Insert into the table fruits the contents of the bag.
		Log.d("Debug_mydbadapter","Deleting an entry");
		db.delete(TAB_NAME,"qno = ?", new String[]{QN.toString()});
		preparedel(QN);
		Log.d("Debug_mydbadapter",QN.toString());
		N=N-1;
		close();
	}
	
	void preparedel(Integer QN)
	{
		open();
		Cursor c=getAllQs();
		Integer qtemp=0;
		
		while(c.moveToNext())
		{
			qtemp=c.getInt(1);
			if(qtemp>QN)
			{
				updateQ(qtemp,qtemp-1,
						c.getString(2),
						c.getString(3),
						c.getString(4),
						c.getString(5),
						c.getString(6),
						c.getString(7));
//						c.getBlob(8),
//						c.getInt(9));
			}
		}
		c.close();
	}
	
	public void deleteall()
	{
		open();
		String query="DELETE FROM ";
		query=query.concat(TAB_NAME);
		db.execSQL(query);
		Log.d("Debug_settings","Dropped the question paper");
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
	
	public Cursor getQBset()
	{
		open();
		Log.d("Debug_mydbadapter","Got to fetch the settings");
		String query="SELECT * FROM "+TAB_NAME+" WHERE Qno=0";
		Cursor c=null;
		if ( N >= 0 )
		{
			c=db.rawQuery(query,null);
			c.moveToNext();				// Moving the cursor forward
			Log.d("Debug_mydbadapter","Got the settings");
		}
		else
		{
			Log.d("Debug_mydbadapter","No question bank");						
		}
		Q_Name=c.getString(2);
		Admin_ID=c.getString(7);
		close();
		return c;						// Returns null if failed
	}
	
	public Cursor getQno(Integer QN)
	{
		open();
		Log.d("Debug_scoredbadapter","Got to fetch "+QN.toString());
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
