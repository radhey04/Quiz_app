package com.example.quiz;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
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
		
		t3.setText("Abraham");
		t4.setText("EE09B044");
		
		scoresDBAdapter ads=new scoresDBAdapter(context);
		
		Integer N=ads.N;
		Integer Score=ads.getscore();
		String res=Score.toString();
		res=res.concat("/");
		res=res.concat(N.toString());
		t6.setText(res);
		
		t7.setText(ads.perf);
		
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
