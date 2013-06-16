package com.example.quiz;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class User_landing extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_landing);
		final Bundle bund = getIntent().getExtras();
		
		Button b=(Button) findViewById(R.id.button1);
		TextView t=(TextView) findViewById(R.id.textView1);
		
		String a="No. of questions :";
		Integer z=bund.getInt("totq");
		a=a.concat(z.toString());
		a=a.concat("\n  Time allowed :");
		a=a.concat(bund.getString("timeleft"));
		
		t.setText(a);
		
		b.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i= new Intent(getApplicationContext(),Mainquiz.class);
				i.putExtras(bund);
				startActivity(i);
				finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user_landing, menu);
		return true;
	}

}
