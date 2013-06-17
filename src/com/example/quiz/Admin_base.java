package com.example.quiz;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
		
		ad=new MyDBAdapter(context);
		
		newq.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
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
				// TODO Auto-generated method stub
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
				// TODO Auto-generated method stub
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

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.admin_base, menu);
		return true;
	}

}
