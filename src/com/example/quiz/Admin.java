package com.example.quiz;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class Admin extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin);
		
		Button b1 = (Button) findViewById(R.id.button1);
		b1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				Toast.makeText(getApplicationContext(), "Begin your Quiz", Toast.LENGTH_SHORT).show();
				Intent i = new Intent(getApplicationContext(), Mainquiz.class);
				startActivity(i);
				// Commenting it out so that it acts as the saftey net
				//finish();
			}
		});
		
		Button b2 = (Button) findViewById(R.id.button2);
		b2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Toast.makeText(getApplicationContext(), "Admin", Toast.LENGTH_SHORT).show();
				Intent j = new Intent(getApplicationContext(), Admin_login.class);
				startActivity(j);
				// Commenting it out so that it acts as the saftey net
				//finish();				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.admin, menu);
		return true;
	}

}
