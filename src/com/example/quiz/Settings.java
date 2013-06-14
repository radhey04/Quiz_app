package com.example.quiz;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class Settings extends Activity {

	private int Timer = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		
		EditText name = (EditText) findViewById(R.id.editText1);
		EditText id = (EditText) findViewById(R.id.editText2);
		CheckBox timer = (CheckBox) findViewById(R.id.checkBox1);
		Button sub = (Button) findViewById(R.id.button1);
		
		/*SettingsDBAdapter set1 = new SettingsDBAdapter(getApplicationContext());
		Cursor c = null;
		c=set1.getAllSet();
		Integer n = c.getCount();
		if(n>0){
			name.setHint(c.getString(1));
			id.setHint(c.getString(2));
			int t=c.getInt(3);
			if(t>0){
				timer.setChecked(true);
			}
		}
		else{
			name.setHint("Enter Name");
			id.setHint("Enter ID");
		}*/
		
		final String Name = name.getText().toString();
		final String ID = id.getText().toString();
		
		if(timer.isChecked()) {
			Timer = 1;
		}
		else {
			Timer = 0;
		}
		
		sub.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				SettingsDBAdapter set = new SettingsDBAdapter(getApplicationContext());
				//set.deleteset();
				//if(set!=null){
				//	set.dropsheet();
				//}
				set = new SettingsDBAdapter(getApplicationContext());
				set.insertset(Name, ID, Timer);
				Intent i = new Intent(getApplicationContext(), Admin.class);
				startActivity(i);
				finish();
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
