package com.example.quiz;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class Settings extends Activity {

	Context context=this;
	
	private Integer Timer = 0;
	String Name="";
	String ID="";
	SettingsDBAdapter set;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		
		set = new SettingsDBAdapter(context);
		
		final EditText name = (EditText) findViewById(R.id.editText1);
		final EditText id = (EditText) findViewById(R.id.editText2);
		final CheckBox timer = (CheckBox) findViewById(R.id.checkBox1);
		Button sub = (Button) findViewById(R.id.button1);
		set.dropset();
		set.updatemem();
		if(set.Name.equals(""))
		{
			name.setHint("Your Name");
		}
		else
		{
			name.setText(set.Name);
		}
		if(set.ID.equals(""))
		{
			id.setHint("Your ID");
		}            
		else
		{
			id.setText(set.ID);
		}
		//Shifted the commented section here below
		
		sub.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				
				Name = name.getText().toString();
				ID = id.getText().toString();
				if(timer.isChecked()) {
					Timer = 1;
				}
				else {
					Timer = 0;
				}
				set.updateset(Name, ID, Timer);
				finish();
			}
		});
		
//		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}

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
