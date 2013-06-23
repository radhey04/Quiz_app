package com.example.quiz;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class Admin_base extends Activity {

	Context context=this;
	MyDBAdapter ad;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin_base);
		 
		Button newq=(Button) findViewById(R.id.button1);
		Button chkq=(Button) findViewById(R.id.button2);
		Button revw=(Button) findViewById(R.id.buttonx2);
		Button expq=(Button) findViewById(R.id.button3);
		
		ad=new MyDBAdapter(context);
		
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
					Log.d("Debug","No kickstart revw as no q");
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
					Log.d("Debug","No kickstart chkq as no q");
					Toast.makeText(context,"No questions in the question bank",Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		expq.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
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
					Integer qno=Integer.parseInt(qnos);
					if(qno==ad.N)
					{
						Log.d("Debug_user_base","Everything is perfect. Exporting the quiz");
						DBhandling dbh = new DBhandling();
						dbh.exportDB("Quiz");									
						Toast.makeText(getApplicationContext(), "Exported the question bank", Toast.LENGTH_SHORT).show();
						c.close();
					}
					else
					{
						Toast.makeText(context,"No. of questions in the bank didn't match the number promised.", Toast.LENGTH_LONG).show();
						Log.d("Debug_user_base","# of qnos didn't match. ad.N =>"+ad.N+"# promised =>"+qno);
					}
				}				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.admin_base, menu);
		return true;
	}

}
