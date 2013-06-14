package com.example.quiz;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class User_publish extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_publish);
		
		TextView t3=(TextView) findViewById(R.id.textView3);
		TextView t4=(TextView) findViewById(R.id.textView4);
		TextView t6=(TextView) findViewById(R.id.textView6);
		TextView t7=(TextView) findViewById(R.id.textView7);
		
		t3.setText("Abraham");
		t4.setText("EE09B044");
		t6.setText("10/10");
		t7.setText("You rock dude");
		
		Button ret=(Button) findViewById(R.id.button1);
		
		ret.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// Intent intent = new Intent(getApplicationContext(), Admin.class);
	        	// startActivity(intent);
				// Safety net is there. So NO need to create another activity.
	        	finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user_publish, menu);
		return true;
	}

}
