package com.example.quiz;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

// Inheritance
public class markDBHelper extends SQLiteOpenHelper {

	public markDBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// id is an integer as the primary key and autoincrement
		// Default value of id starting from 0, I suppose
        String createQuery = "CREATE TABLE marks (no integer primary key autoincrement, slno integer, quizname, datetaken, score, outof);";                 
        db.execSQL(createQuery);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Write the version control code. :) If you want.
		
	}
}
