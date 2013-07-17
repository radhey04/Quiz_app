package com.example.quiz;

import java.io.File;
import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class User_base extends Activity {

	Context context=this;
	private static final int REQUEST_PICK_FILE = 1;
	String newpath="";
	SettingsDBAdapter set;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_base);
		
		TextView t=(TextView) findViewById(R.id.textView1);
		Button takeq=(Button) findViewById(R.id.button1);
		Button vscr=(Button) findViewById(R.id.button2);
		Button impq=(Button) findViewById(R.id.button3);
		
		set=new SettingsDBAdapter(getApplicationContext());
		set.updatemem();
		
		t.setText("Hi "+set.Name+"!!! Please choose an option to continue.");
		takeq.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//Do the user sanity check
				MyDBAdapter ad=new MyDBAdapter(context);
				if(ad.N==-1)
				{
					Log.d("Debug_user_base","No Question bank detected");
					Toast.makeText(context,"No Question Bank Loaded", Toast.LENGTH_SHORT).show();
				}
				else
				{
					Cursor c=ad.getQBset();
					String qnos=c.getString(3);
					c.close();
					Integer qno=Integer.parseInt(qnos);
					if(qno==ad.N)
					{
						Log.d("Debug_user_base","Everything is perfect. Launching the quiz");
						Intent i = new Intent(getApplicationContext(), User_landing.class);
						startActivity(i);
						finish();
					}
					else
					{
						Toast.makeText(context,"Invalid Question Bank", Toast.LENGTH_SHORT).show();
						Log.d("Debug_user_base","# of qnos didn't match. ad.N =>"+ad.N+"# promised =>"+qno);
					}
				}
								
			}
		});
		
		impq.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(set.disablehttp==true)
				{
					importdb();	//Call the filepicker
					Toast.makeText(context, "Working in HTTP disable mode", Toast.LENGTH_SHORT).show();
					
				}
				else
				{
					Intent i = new Intent(getApplicationContext(),DBList.class);
					Log.d("Debug","Exited the user base");
					startActivity(i);
				}
			}
		});
		vscr.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				markDBAdapter md=new markDBAdapter(context);
				if(md.N==0)
					Toast.makeText(context,"No scores to display", Toast.LENGTH_SHORT).show();
				else
				{
					Intent i = new Intent(getApplicationContext(), User_score.class);
					startActivity(i);
				}
			}
		});
	}
	
    public void importdb() 
    {
    	Intent intent = new Intent(context, FilePickerActivity.class);
		
		// Set the initial directory to be the sdcard
		DBhandling db=new DBhandling();
		db.chkdir();
		Log.d("Debug_dbhandling",db.dirpath);
		intent.putExtra(FilePickerActivity.EXTRA_FILE_PATH,db.dirpath);
		
		// Only make .png files visible
		ArrayList<String> extensions = new ArrayList<String>();
		extensions.add(".png");
		extensions.add(".jpg");
		extensions.add(".nab");
		intent.putExtra(FilePickerActivity.EXTRA_ACCEPTED_FILE_EXTENSIONS, extensions);
		
		// Start the activity
		startActivityForResult(intent, REQUEST_PICK_FILE);
		Log.d("Debug_dbhandling","Triggered File picker");    	
    }
    
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		Log.d("Debug_dbhandling","Result");
		if(resultCode == RESULT_OK) 
		{
			switch(requestCode) 
			{
				case REQUEST_PICK_FILE:
				{
					if(data.hasExtra(FilePickerActivity.EXTRA_FILE_PATH)) 
					{
						// Get the file path
						File f = new File(data.getStringExtra(FilePickerActivity.EXTRA_FILE_PATH));
						newpath=f.getPath();
						Log.d("Debug_dbhandling","File picker worked");
						DBhandling dbh = new DBhandling();
						Boolean suc=dbh.importDB("Quiz",newpath);
						if(suc==true)
						{
							Toast.makeText(getApplicationContext(), "Loaded the quiz", Toast.LENGTH_SHORT).show();
						}
						else
						{
							Toast.makeText(getApplicationContext(), "Couldn't find the question bank.", Toast.LENGTH_SHORT).show();
						}
					}
					break;
				}
			}
		}
	}
}
