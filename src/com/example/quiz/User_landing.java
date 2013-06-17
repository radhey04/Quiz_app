package com.example.quiz;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class User_landing extends Activity {

	Context context=this;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_landing);
		
		Button b=(Button) findViewById(R.id.button1);
		TextView t=(TextView) findViewById(R.id.textView1);
		
		final MyDBAdapter ad=new MyDBAdapter(context);
		Cursor c = ad.getQBset();
		
		String details="Hi. The following are the details of " +
				"the quiz you are about to take.";
		details=details.concat("\n\n Quiz Name: "+c.getString(2));
		details=details.concat("\n\n # of Qs: "+c.getString(3));
		details=details.concat("\n\n Duration: "+c.getString(4)+" mins");
		details=details.concat("\n\n Deadline: "+c.getString(6));
		details=details.concat("\n\n Syllabus:\n"+c.getString(5));
		t.setText(details);
		
		
		Integer totq= Integer.parseInt(c.getString(3));
		String timeleft=c.getString(4);
		final Bundle bund = new Bundle();
		
		// Bundle containing
		// Total Questions stored as totq (int)
		// Time as timeleft (String)
						
        bund.putInt("totq",totq);
        bund.putString("timeleft",timeleft);
        		        
        
		b.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
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
