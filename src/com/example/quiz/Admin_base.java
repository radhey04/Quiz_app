package com.example.quiz;

import java.io.File;
import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class Admin_base extends Activity {

	Context context=this;
	private static final int REQUEST_PICK_FILE = 1;
	String newpath="";
	
	MyDBAdapter ad;
	SettingsDBAdapter set;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin_base);
		 
		Button newq=(Button) findViewById(R.id.button1);
		Button chkq=(Button) findViewById(R.id.button2);
		Button revw=(Button) findViewById(R.id.buttonx2);
		Button expq=(Button) findViewById(R.id.button3);
		Button delqb=(Button) findViewById(R.id.button4);
		ad=new MyDBAdapter(context);
		set=new SettingsDBAdapter(context);
		set.updatemem();
		
		if(ad.N==-1)										// No QB
		{
			newq.setText(R.string.admin_base_newdb);
			chkq.setText(R.string.user_landing_imp);		// Option for import
			expq.setVisibility(4);
			revw.setVisibility(4);
			delqb.setVisibility(4);							// Invisible
		}
		else
		{
			newq.setText(R.string.admin_base_newq);
		}
        
		
		
		newq.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Boolean k=(ad.N==-1);
				Log.d("Debug",k.toString());
				if(ad.N==-1)
				{
					Intent intent = new Intent(getApplicationContext(), Admin_cp.class);
			        startActivity(intent);
			        Log.d("Debug","Registering the test");
				}
				else
				{
					Intent intent = new Intent(getApplicationContext(), Admin_home.class);
			        startActivity(intent);
			        Log.d("Debug","Kickstart newq");
				}
		        finish();
			}
		});
		
		revw.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(ad.N==-1)
				{
					Log.d("Debug","No kickstart review questions as no q");
					Toast.makeText(context,"No questions in the question bank",Toast.LENGTH_SHORT).show();
				}
				else
				{
					Intent intent2 = new Intent(getApplicationContext(), Admin_review.class);
					startActivity(intent2);
			        Log.d("Debug","Kickstart revw");
			        finish();			        
				}
			}
		});
		
		chkq.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(ad.N>0)
				{
					Intent intent3 = new Intent(getApplicationContext(), Admin_chkq.class);
					startActivity(intent3);
					Log.d("Debug","Kickstart chkq");
					finish();
				}
				else
				{
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
						finish();
					}
				}
			}
		});
		
		expq.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				MyDBAdapter ad=new MyDBAdapter(context);
				if(ad.N==-1)
				{
					Log.d("Debug","Trying to export QB");
					Toast.makeText(context,"No questions in the question bank",Toast.LENGTH_SHORT).show();
				}
				else
				{
					Cursor c=ad.getQBset();
					String qnos=c.getString(3);
					c.close();
					Integer qno=Integer.parseInt(qnos);
					if(qno==ad.N)
					{
						Log.d("Debug_user_base","Everything is perfect. Exporting the quiz");
						c=ad.getQBset();
						String QuizName=c.getString(2);
						QuizName=QuizName.replace(" ", "");
						QuizName=QuizName.concat(".nab");
						c.close();
						DBhandling dbh = new DBhandling();
						dbh.exportDB("Quiz",QuizName);									
						Toast.makeText(getApplicationContext(), "Exported the question bank", Toast.LENGTH_SHORT).show();
					}
					else
					{
						Toast.makeText(context,"No. of questions in the bank didn't match the number promised.", Toast.LENGTH_LONG).show();
						Log.d("Debug_user_base","# of qnos didn't match. ad.N =>"+ad.N+"# promised =>"+qno);
					}
				}				
			}
		});
		
		delqb.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				Log.d("Debug_admin_review","Deleting the paper");
				builder.setTitle("Delete the entire question paper?");
				builder.setMessage("Are you sure? You will lose the settings too. Imported question papers will be retained in the NAB folder.");
		    	builder.setIcon(android.R.drawable.ic_dialog_alert);
		    	builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		    	    public void onClick(DialogInterface dialog, int which) {			      	
		    	    	//Yes button clicked, do something
		    	    	ad.deleteall();
						Toast.makeText(context,"Deleted the paper", Toast.LENGTH_SHORT).show();
						Log.d("Debug_admin_review","Deleted the paper");
						Intent i=new Intent(context,Admin_base.class);
						startActivity(i);
						finish();			// No point staying back here. :)
		    	    }
		    	});
		    	builder.setNegativeButton("No", null);
		    	builder.show();
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
					Intent i=new Intent(context,Admin_base.class);
					startActivity(i);
					finish();
					break;
				}
			}
		}
	}
}

