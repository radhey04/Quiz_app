package com.example.quiz;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class Settings extends Activity {

	Context context=this;
	
	private Integer Timer = 0, dh=0;
	String Name="";
	String ID="";
	String URL="";
	
	SettingsDBAdapter set;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		
		set = new SettingsDBAdapter(context);
		
		final EditText name = (EditText) findViewById(R.id.editText1);
		final EditText id = (EditText) findViewById(R.id.editText2);
		final CheckBox timer = (CheckBox) findViewById(R.id.checkBox1);
		final CheckBox httpdisb = (CheckBox) findViewById(R.id.checkBox2);
		final EditText url = (EditText) findViewById(R.id.editText3);
		Button sub = (Button) findViewById(R.id.button1);
		//set.dropset();
		set.updatemem();
		httpdisb.setChecked(true);
		timer.setChecked(true);
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
		if(set.ShowTimer==true)
		{
			timer.setChecked(true);
		}            
		else
		{
			timer.setChecked(false);
		}
		if(set.disablehttp==true)
		{
			httpdisb.setChecked(true);
		}            
		else
		{
			httpdisb.setChecked(false);
		}
		if(set.URL.equals(""))
		{
			url.setText("http://students.iitm.ac.in/mocktest/");
		}
		else
		{
			url.setText(set.URL);
		}
		//Shifted the commented section here below
		
		sub.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
								
				Name = name.getText().toString();
				ID = id.getText().toString();
				URL=url.getText().toString();
				if(timer.isChecked()) {
					Timer = 1;
				}
				else 
				{
					Timer = 0;
				}
				if(httpdisb.isChecked()) {
					dh=1;
				}
				else 
				{
					dh = 0;
				}
				if(Name.equals("") && ID.equals(""))
				{
					Toast.makeText(context, "You can't leave the fields empty!!!", Toast.LENGTH_SHORT).show();			
				}
				else if(Name.equals(""))
				{
					Toast.makeText(context, "You can't leave the Name field empty!!!", Toast.LENGTH_SHORT).show();			
				}
				else if(ID.equals(""))
				{
					Toast.makeText(context, "You can't leave the ID field empty!!!", Toast.LENGTH_SHORT).show();			
				}
				else if(URL.equals("") && (dh==0))
				{
					Toast.makeText(context, "You can't leave the URL field empty if you are not disabling HTTP!!!", Toast.LENGTH_SHORT).show();
				}
				else
				{
					if(!URL.endsWith("/")) {
						URL=URL+"/";  //Ensuring that the URL ends with a Backslash
					}
					set.updateset(Name, ID, Timer,dh,URL);
					Toast.makeText(getApplicationContext(), "Settings updated", Toast.LENGTH_SHORT).show();
					Intent i=new Intent(getApplicationContext(), Admin.class);
					startActivity(i);
					finish();
				}
			}
		});
	}
}
