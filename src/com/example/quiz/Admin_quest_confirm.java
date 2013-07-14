package com.example.quiz;

import java.io.File;
import java.util.ArrayList;

import com.example.quiz.MyDBAdapter;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class Admin_quest_confirm extends Activity {
	
	Context context = this;
	String imgpath="",quest="",opta="",optb="",optc="",optd="",ans="";
	Boolean imagethere=true;
	MyDBAdapter ad; 
	private static final int REQUEST_PICK_FILE = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin_quest_confirm);
		ad= new MyDBAdapter(context);
		
		Button upd = (Button)findViewById(R.id.button1);


		Bundle b = getIntent().getExtras();
		
		TextView t2 = (TextView)findViewById(R.id.textView2);
		TextView t3 = (TextView)findViewById(R.id.textView3);
		TextView t4 = (TextView)findViewById(R.id.textView4);
		TextView t5 = (TextView)findViewById(R.id.textView5);
		TextView t6 = (TextView)findViewById(R.id.textView6);
		TextView t7 = (TextView)findViewById(R.id.textView7);
		 
		quest = b.getCharSequence("quest").toString();
		opta = b.getCharSequence("opta").toString();
		optb = b.getCharSequence("optb").toString();
		optc = b.getCharSequence("optc").toString();
		optd = b.getCharSequence("optd").toString();
		ans = b.getCharSequence("option").toString();
		String corans="Correct Option: ";
		corans=corans.concat(ans);
		final Integer qn=ad.N+1;
		t2.setText(qn.toString()+". "+quest);
		t3.setText("A -> "+opta);
		t4.setText("B -> "+optb);
		t5.setText("C -> "+optc);
		t6.setText("D -> "+optd);
		t7.setText(corans);
		
		final AlertDialog.Builder builder= new AlertDialog.Builder(context);
		
		upd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// Add entry

				builder.setTitle("Do you want to add an image?");
				builder.setMessage("Press no to continue with the question as it is.");
		    	builder.setIcon(android.R.drawable.ic_dialog_alert);
		    	builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		    	    public void onClick(DialogInterface dialog, int which) {			      	
		    	    	getimg();
		    	    }
		    	});
		    	builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
		    	    public void onClick(DialogInterface dialog, int which) {
		    	    	imagethere=false;
		    	    	imgpath="";
		    	    	ad.insertQ(quest,opta,optb,optc,optd,ans,imagethere,imgpath);
						Toast.makeText(context, "Question updated", Toast.LENGTH_SHORT).show();
					    Intent intent = new Intent(context, Admin_base.class);
						startActivity(intent);
						finish();
		    	    }
		    	});
		    	builder.show();
			}
		});
	}
	
	public void getimg()
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
						imgpath=f.getPath();
						Log.d("Debug_admin_qu",imgpath);
						ad.insertQ(quest,opta,optb,optc,optd,ans,imagethere,imgpath);
						Toast.makeText(getApplicationContext(), "Question updated", Toast.LENGTH_SHORT).show();
					    Intent intent = new Intent(getApplicationContext(), Admin_base.class);
						startActivity(intent);
						finish();

					}
					break;
				}
			}
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.admin_quest_confirm, menu);
		return true;
	}

}

