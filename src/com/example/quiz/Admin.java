package com.example.quiz;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Admin extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin);
		
		Button b1 = (Button) findViewById(R.id.button1);
		b1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
//				Toast.makeText(getApplicationContext(), "Begin your Quiz", Toast.LENGTH_SHORT).show();
				
				Intent i = new Intent(getApplicationContext(), User_base.class);
				startActivity(i);
				// Commenting it out so that it acts as the safety net
				//finish();
			}
		});
		
		Button b2 = (Button) findViewById(R.id.button2);
		b2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
//				Toast.makeText(getApplicationContext(), "Settings", Toast.LENGTH_SHORT).show();
				Intent k = new Intent(getApplicationContext(), Settings.class);
				startActivity(k);
			}
		});
		
	}
}
