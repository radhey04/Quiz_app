package com.example.quiz;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

// Inheritance
public class MyDBHelper extends SQLiteOpenHelper {

	public MyDBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		// id is an integer as the primary key and autoincrement
		// Default value of id starting from 0, I suppose
        String createQuery = "CREATE TABLE qbank (sno integer primary key autoincrement,Qno integer,quest,opta,optb,optc,optd,option);";                 
        db.execSQL(createQuery);		
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		// Write the version control code. :) If you want.
		
	}
}
