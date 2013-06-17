package com.example.quiz;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	Context context=this;
	SettingsDBAdapter set;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		TextView t1 = (TextView) findViewById(R.id.textView1);
		TextView t2 = (TextView) findViewById(R.id.textView2);
		t1.setText("Designed by Ankit, Abraham & Nikhil");
		t2.setText("With the help of Insane Labs and AADL");
		
		Button b1 = (Button) findViewById(R.id.button1);
		
		b1.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
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
//				Intent i=new Intent(getApplicationContext(), Admin_cp.class);
					Intent i=new Intent(getApplicationContext(), Admin.class);
					startActivity(i);
					finish();
				}
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
