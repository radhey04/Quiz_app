package com.example.quiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

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
		// TODO Auto-generated constructor stub
		 db_helper = new scoresDBHelper(context, DB_NAME, null, 1);
		 Cursor c1=getAllans();
		 N=c1.getCount();			// Initialized
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
	
	
	public void insertans(String act_ans,String corr_ans,Integer correct)
	{
		// ContentValues which is like bundle
		ContentValues bag = new ContentValues();
		// Order matters. It should be as same as the columns
		// Contents of the bag will increase with every put statement
		bag.put("act_ans", act_ans);
		bag.put("corr_ans", corr_ans);
		bag.put("correct", correct);
		Log.d("Debug","Inserting act_ans/corr_ans/correct");
		Log.d("Debug",act_ans);
		Log.d("Debug",corr_ans);
		Log.d("Debug",correct.toString());
		open();
		//Insert into the table qbank the contents of the bag.
		db.insert(TAB_NAME, null, bag);
		close();
		N=N+1;
	}
	
	public Cursor getAllans()
	{
		open();
		String query="SELECT * FROM ";
		query=query.concat(TAB_NAME);
		Cursor c1 = db.rawQuery(query, null);
		return c1;
	}
	
	public Integer getscore()
	{
		open();
		String query="SELECT * FROM ";
		query=query.concat(TAB_NAME);
		Cursor c1 = db.rawQuery(query, null);
		Integer score=0,qno=0,score_temp=0;
		perf="";
		while(c1.moveToNext())
		{
			qno=c1.getInt(0);
			perf=perf.concat(qno.toString());
			perf=perf.concat(". ");
			perf=perf.concat(c1.getString(1));
			perf=perf.concat("\t\t\t\t");
			perf=perf.concat(c1.getString(2));
			perf=perf.concat("\t\t\t\t");
			score_temp=c1.getInt(3);
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
		return score;
	}
	
	public void deleteEntry(Integer QN)
	{
		open();
		//Insert into the table fruits the contents of the bag.
		db.delete(TAB_NAME,"qno = ?", new String[]{QN.toString()});
		close();
	}
	public void dropsheet()
	{
		String query="DELETE FROM ";
		query=query.concat(TAB_NAME);
		db.execSQL(query);
		// Setting the autoincrementing qno to zero. and the id is ++qno.
		query="UPDATE SQLITE_SEQUENCE SET qno = 0 WHERE name = ";
		query=query.concat(TAB_NAME);
		Log.d("Debug","Dropped scoresheet");
	}
}
