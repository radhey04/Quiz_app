package com.example.quiz;

import com.example.quiz.MyDBAdapter;

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


public class Admin_quest_confirm extends Activity {
	Context context = this;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin_quest_confirm);

		Button upd = (Button)findViewById(R.id.button1);


		Bundle b = getIntent().getExtras();
		
		TextView t2 = (TextView)findViewById(R.id.textView2);
		TextView t3 = (TextView)findViewById(R.id.textView3);
		TextView t4 = (TextView)findViewById(R.id.textView4);
		TextView t5 = (TextView)findViewById(R.id.textView5);
		TextView t6 = (TextView)findViewById(R.id.textView6);
		TextView t7 = (TextView)findViewById(R.id.textView7);
		 
		final String quest = b.getCharSequence("quest").toString();
		final String opta = b.getCharSequence("opta").toString();
		final String optb = b.getCharSequence("optb").toString();
		final String optc = b.getCharSequence("optc").toString();
		final String optd = b.getCharSequence("optd").toString();
		final String ans = b.getCharSequence("option").toString();
		String corans="Correct Option: ";
		corans=corans.concat(ans);
				 
		t2.setText(quest);
		t3.setText(opta);
		t4.setText(optb);
		t5.setText(optc);
		t6.setText(optd);
		t7.setText(corans);
		
		upd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MyDBAdapter ad = new MyDBAdapter(context);
				
				// Add entry
				ad.insertQ(quest,opta,optb,optc,optd,ans);
				
				Toast.makeText(getApplicationContext(), "Question updated", Toast.LENGTH_SHORT).show();
		        
				Intent intent = new Intent(getApplicationContext(), Admin_base.class);
	        	startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.admin_quest_confirm, menu);
		return true;
	}

}

