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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class EditQActivity extends Activity {
	
	Context context=this;
	String imgpath="",option="";
	Boolean imagethere=true;
	private static final int REQUEST_PICK_FILE = 1;
	CheckBox chka;
	CheckBox chkb;
	CheckBox chkc;
	CheckBox chkd;
	EditText Q;
	EditText opta;
	EditText optb;
	EditText optc;
	EditText optd;
    MyDBAdapter ad;
    Integer qno=0,imagethereint=0;
    
    private void updateactivity(Integer qno)
    {    	
	    Cursor c1=ad.getQno(qno);
	    if(c1==null)
		{
			Toast.makeText(getApplicationContext(), "Invalid question no", Toast.LENGTH_SHORT).show();
		}
	    else
	    {
	    	Log.d("Debug_EditQ","Updating Question "+c1.getString(1));
	    	Toast.makeText(context, "Question no. "+c1.getString(1), Toast.LENGTH_SHORT).show();
		    Q.setText(c1.getString(2));
		    opta.setText(c1.getString(3));
		    optb.setText(c1.getString(4));
		    optc.setText(c1.getString(5));
		    optd.setText(c1.getString(6));
		    imagethereint=c1.getInt(8);
		    String mans=c1.getString(7).toString();
		    c1.close();
    		Log.d("Debug_EditQ","Have to decipher => "+mans);
	    	if(mans.contains("A")==true)
	    		chka.setChecked(true);
	    	if(mans.contains("B")==true)
	    		chkb.setChecked(true);
	    	if(mans.contains("C")==true)
	    		chkc.setChecked(true);
	    	if(mans.contains("D")==true)
	    		chkd.setChecked(true);
	    	Log.d("Debug_EditQ","Checkboxes Corrected");
		}	        	
    }
    
    private void Initialize()
    {
    	Q=(EditText) findViewById(R.id.editText1);
	    opta=(EditText) findViewById(R.id.editText2);
	    optb=(EditText) findViewById(R.id.editText3);
	    optc=(EditText) findViewById(R.id.editText4);
	    optd=(EditText) findViewById(R.id.editText5);
	    chka = (CheckBox)findViewById(R.id.checkBox1);
		chkb = (CheckBox)findViewById(R.id.checkBox2);
		chkc = (CheckBox)findViewById(R.id.checkBox3);
		chkd = (CheckBox)findViewById(R.id.checkBox4);
	    ad=new MyDBAdapter(context);
	}
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_q);
		
		Initialize();
		
		Bundle b = getIntent().getExtras();
		qno=b.getInt("Qno");
		Log.d("Debug_editq","Asked to fetch =>"+qno);
		
		updateactivity(qno);
	    Log.d("Debug_EditQ","Updated");
	    
		Button submit=(Button) findViewById(R.id.button1);
		Button back=(Button) findViewById(R.id.button2);
		final AlertDialog.Builder builder= new AlertDialog.Builder(context);
		
		submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				option="";	//Necessary as now we have to start afresh. :)
				if(chka.isChecked())
				{
					option=option.concat("A");
				}
				if(chkb.isChecked())
				{
					option=option.concat("B");
				}
				if(chkc.isChecked())
				{
					option=option.concat("C");
				}
				if(chkd.isChecked())
				{
					option=option.concat("D");
				}
		        //Inserts a String value into the mapping of this Bundle
				if(!option.equals(""))
			    {
					String rmvimg="Remove Image";
			    	if(imagethereint==0)
			    	{//No image
			    		rmvimg="No image";
			    	}
			    	Log.d("Debug_editq","Do you want to change image?");
					builder.setTitle("What about the image?");
					builder.setMessage("Press back to abort edits.");
			    	builder.setIcon(android.R.drawable.ic_dialog_alert);
			    	builder.setPositiveButton(rmvimg, new DialogInterface.OnClickListener() {
			    	    public void onClick(DialogInterface dialog, int which) {			      	
			    	    	confirm();
			    	    }
			    	});
			    	builder.setNegativeButton("Modify/Retain Image", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							getimg();
						}
					});
			    	builder.show();
			    }
				else
				{
					Toast.makeText(getApplicationContext(), "You forgot to enter answer.", Toast.LENGTH_SHORT).show();
				}		        
			}
		});
		
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(context,Admin_chkq.class);
				startActivity(i);
				finish();
			}
		});
	}
	
	public void getimg()
	{
		final AlertDialog.Builder builder2= new AlertDialog.Builder(context);
		builder2.setTitle("Image");
    	builder2.setMessage("What do you want me to do?");
		builder2.setIcon(android.R.drawable.ic_dialog_alert);
    	builder2.setPositiveButton("Retain the image", new DialogInterface.OnClickListener() {
    	    public void onClick(DialogInterface dialog, int which) {
    	    	Cursor c=ad.getQno(qno);
    	    	ad.updateQ(qno,qno,Q.getText().toString(),opta.getText().toString(),optb.getText().toString(),optc.getText().toString(),optd.getText().toString(),option,c.getInt(8),c.getBlob(9));
				Toast.makeText(getApplicationContext(), "Question updated", Toast.LENGTH_SHORT).show();
			    Intent intent = new Intent(getApplicationContext(), Admin_chkq.class);
				startActivity(intent);
				finish();
    	    }
    	});
    	builder2.setNegativeButton("Cancel operation",null);
    	builder2.show();
		Intent intent = new Intent(context, FilePickerActivity.class);
		
		
		//Proceeding with image selection
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
						ad.updateQprev(qno,Q.getText().toString(),opta.getText().toString(),optb.getText().toString(),optc.getText().toString(),optd.getText().toString(),option,imagethere,imgpath);
						Toast.makeText(getApplicationContext(), "Question updated", Toast.LENGTH_SHORT).show();
					    Intent intent = new Intent(getApplicationContext(), Admin_chkq.class);
						startActivity(intent);
						finish();
					}
					else
					{
						Toast.makeText(getApplicationContext(), "Aborted", Toast.LENGTH_SHORT).show();
					}
					break;
				}
			}
		}
	}

	public void confirm()
	{
		imagethere=false;
    	imgpath="";
    	Log.d("Debug_editq","Updating the question without image");
		ad.updateQprev(qno,Q.getText().toString(),opta.getText().toString(),optb.getText().toString(),optc.getText().toString(),optd.getText().toString(),option,imagethere,imgpath);
		Toast.makeText(context, "Question updated", Toast.LENGTH_SHORT).show();
	    Intent intent = new Intent(context, Admin_chkq.class);
		startActivity(intent);
		finish();
	}
}
