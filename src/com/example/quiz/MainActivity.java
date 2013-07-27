package com.example.quiz;

import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

public class MainActivity extends Activity {

	Context context=this;
	SettingsDBAdapter set;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		TextView t1 = (TextView) findViewById(R.id.textView1);
		TextView t2 = (TextView) findViewById(R.id.textView2);
		t1.setText("Designed by Abraham, Ankit & Nikhil");
		t2.setText("With the help of Insane Labs and AADL");
		
		Timer timer_begin= new Timer();
		timer_begin.schedule(new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				set=new SettingsDBAdapter(context);
				set.updatemem();
				if(set.Name.equals(""))
				{
//					Toast.makeText(context,"Please fill in the details.",Toast.LENGTH_LONG).show();
					Intent i=new Intent(context, Settings.class);
					startActivity(i);
					// Control will come back here. So wait till name has come.
					finish();					
				}
				else // If name already there
				{
					Intent i=new Intent(context, Admin.class);
					startActivity(i);
					finish();
				}
				
				
			}
		},3*1000);
				
		
/*		Button b1 = (Button) findViewById(R.id.button1);
		
		b1.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				set=new SettingsDBAdapter(context);
				set.updatemem();
				if(set.Name.equals(""))
				{
					Toast.makeText(context,"Please fill in the details.",Toast.LENGTH_SHORT).show();
					Intent i=new Intent(getApplicationContext(), Admin.class);
					startActivity(i);
					//Inception level. :D Finally you will see settings on top.
					i=new Intent(getApplicationContext(), Settings.class);
					startActivity(i);
					// Control will come back here. So wait till name has come.
					finish();					
				}
				else // If name already there
				{
//				Intent i=new Intent(getApplicationContext(), User_landing.class);
					Intent i=new Intent(getApplicationContext(), Admin.class);
					startActivity(i);
					finish();
				}
			}
		});*/
		
	}
}
