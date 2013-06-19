package com.example.quiz;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class User_publish extends Activity {

	Context context=this;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_publish);
		
		TextView t3=(TextView) findViewById(R.id.textView3);
		TextView t4=(TextView) findViewById(R.id.textView4);
		TextView t6=(TextView) findViewById(R.id.textView6);
		TextView t7=(TextView) findViewById(R.id.textView7);
		
		SettingsDBAdapter set = new SettingsDBAdapter(context);
		Log.d("Debug",set.Name);
		Log.d("Debug",set.ID);
		Log.d("Debug","Values to be printed on my scoresheet");
		set.updatemem();
		t3.setText(set.Name);
		t4.setText(set.ID);
		Log.d("Debug",set.Name);
		Log.d("Debug",set.ID);
		Log.d("Debug","Values to be printed on my scoresheet");
		/**/
		scoresDBAdapter ads=new scoresDBAdapter(context);
		
		Integer N=ads.N;
		Integer Score=ads.getscore();
		String res=Score.toString();
		res=res.concat("/");
		res=res.concat(N.toString());
		t6.setText(res);
		
		t7.setText(ads.perf);
		
		Button ret=(Button) findViewById(R.id.button1);
		
		MyDBAdapter ad=new MyDBAdapter(context);
		Cursor c=ad.getQBset();
		// Duration is given by c.getString(4);
		// Nikhil's score code comes here
		
		ret.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
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
