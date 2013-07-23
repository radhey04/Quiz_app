package com.example.quiz;

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
				Intent i = new Intent(getApplicationContext(),DBList.class);
				Log.d("Debug","Exited the user base");
				startActivity(i);
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
}
