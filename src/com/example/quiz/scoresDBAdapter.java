package com.example.quiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class scoresDBAdapter {
	
	// Calls public void onCreate(SQLiteDatabase db)
	scoresDBHelper db_helper;
	// Database naming
	String DB_NAME="scores";
	String TAB_NAME="scores";
	// Do most of the communication
	// db_helper is just to do the upper-level 
	SQLiteDatabase db;
	
	public Integer N=0;			//The total count
	public String perf="";
	public scoresDBAdapter(Context context) {
		 db_helper = new scoresDBHelper(context, DB_NAME, null, 1);
// Below code not required for the first run as anyways we are going to 
// drop the sheet. :) But since we are going to call it in user_publish, we
// need it.
		 Cursor c1=getAllans();
		 N=c1.getCount();			// Initialized
		 Log.d("Debug_scoreadapter","Total no. of elements =>"+N);
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
	
	
	public void insertans(Integer qno, String act_ans,String corr_ans,Integer correct)
	{
		// ContentValues which is like bundle
		ContentValues bag = new ContentValues();
		// Order matters. It should be as same as the columns
		// Contents of the bag will increase with every put statement
		bag.put("qno", qno);
		bag.put("act_ans", act_ans);
		bag.put("corr_ans", corr_ans);
		bag.put("correct", correct);
		Log.d("Debug_scoredbadapter","Inserting qno/act_ans/corr_ans/correct");
		Log.d("Debug_scoredbadapter",qno.toString());
		Log.d("Debug_scoredbadapter",act_ans);
		Log.d("Debug_scoredbadapter",corr_ans);
		Log.d("Debug_scoredbadapter",correct.toString());
		
		open();
		//Insert into the table qbank the contents of the bag.
		db.insert(TAB_NAME, null, bag);
		close();
		if(qno==N+1)
		{
			Log.d("Debug_scoredbadapter","Scoring is correct");
		}
		else
		{
			Log.d("Debug_scoredbadapter","Scoring is wrong");
		}	
		N=N+1;
	}
	
	public void updateans(Integer qno, String act_ans,String corr_ans,Integer correct)
	{
		// ContentValues which is like bundle
		ContentValues bag = new ContentValues();
		// Order matters. It should be as same as the columns
		// Contents of the bag will increase with every put statement
		String loc="qno="+qno.toString();			//sno is not autoincremented :)
		bag.put("qno", qno);
		bag.put("act_ans", act_ans);
		bag.put("corr_ans", corr_ans);
		bag.put("correct", correct);
		Log.d("Debug_scoredbadapter","Updated qno/act_ans/corr_ans/correct");
		Log.d("Debug_scoredbadapter",qno.toString());
		Log.d("Debug_scoredbadapter",act_ans);
		Log.d("Debug_scoredbadapter",corr_ans);
		Log.d("Debug_scoredbadapter",correct.toString());
		
		open();
		//Insert into the table qbank the contents of the bag.
		db.update(TAB_NAME,bag,loc, null);
		close();
	}
	
	public Cursor getAllans()
	{
		open();
		Log.d("Debug_scoredbadapter","Asked to fetch the scoresheet");
		String query="SELECT * FROM ";
		query=query.concat(TAB_NAME);
		Cursor c1 = db.rawQuery(query, null);
		Log.d("Debug_scoredbadapter","Fetched the scoresheet");
		return c1;
	}
	
	public Integer getscore()
	{
		open();
		Cursor c1 = getAllans();
		Integer score=0,qno=0,score_temp=0;
		perf="";
		while(c1.moveToNext())
		{
			qno=c1.getInt(1);
			perf=perf.concat(qno.toString());
			perf=perf.concat(". ");
			perf=perf.concat(c1.getString(2));
			perf=perf.concat("\t\t\t\t");
			perf=perf.concat(c1.getString(3));
			perf=perf.concat("\t\t\t\t");
			score_temp=c1.getInt(4);
			score=score+score_temp;
			if(score_temp!=0)
			{
				perf=perf.concat(" Correct");				
			}
			else
			{
				perf=perf.concat(" Incorrect");				
			}			
			perf=perf.concat("\n");
		}
		close();
		return score;
	}
	
	public void dropsheet()
	{
		open();
		String query="DELETE FROM ";
		query=query.concat(TAB_NAME);
		db.execSQL(query);
		Log.d("Debug_scoredbadapter","Dropped scoresheet");
		close();
	}
	
	public Cursor getQno(Integer QN)
	{
		open();
		Log.d("Debug_scoredbadapter","Got to fetch "+QN.toString());
		String query="SELECT * FROM "+TAB_NAME+" WHERE qno="+QN.toString();
		Cursor c=null;
		if ( (QN <= N) && (QN > 0) )
		{
			c=db.rawQuery(query,null);
			c.moveToNext();
			Log.d("Debug_scoreadapter","Got it "+c.getString(1));
		}
		else
		{
			Log.d("Debug_scoreadapter","Bad luck");						
		}
		close();
		return c;						// Returns null if failed
	}
}
