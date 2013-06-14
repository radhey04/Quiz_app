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
import android.widget.Toast;

public class Admin_home extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin_home);
		// Getting the login button details
		Button submit= (Button) findViewById(R.id.button1);
		Button pop= (Button) findViewById(R.id.button2);
		
		// Getting the password
		final EditText quest=(EditText) findViewById(R.id.editText1);
		final EditText opta=(EditText) findViewById(R.id.editText2);
		final EditText optb=(EditText) findViewById(R.id.editText3);
		final EditText optc=(EditText) findViewById(R.id.editText4);
		final EditText optd=(EditText) findViewById(R.id.editText5);
		final CheckBox chka = (CheckBox)findViewById(R.id.checkBox1);
		final CheckBox chkb = (CheckBox)findViewById(R.id.checkBox2);
		final CheckBox chkc = (CheckBox)findViewById(R.id.CheckBox01);
		final CheckBox chkd = (CheckBox)findViewById(R.id.CheckBox02);
		
		submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String quest_contents= quest.getText().toString();
				String opta_contents = opta.getText().toString();
				String optb_contents = optb.getText().toString();
				String optc_contents = optc.getText().toString();
				String optd_contents = optd.getText().toString();
				String option=null;
				if(chka.isChecked())
				{
					option="A";
				}
				else if(chkb.isChecked())
				{
					option="B";
				}
				else if(chkc.isChecked())
				{
					option="C";
				}
				else if(chkd.isChecked())
				{
					option="D";
				}
				else
				{
					Toast.makeText(getApplicationContext(), "You forgot to tell the correct option.", Toast.LENGTH_SHORT).show();
				}
		        //Inserts a String value into the mapping of this Bundle
				if(option.equals("A") || option.equals("B") || option.equals("C") || option.equals("D"))
			    {
		        	Intent intent = new Intent(getApplicationContext(), Admin_quest_confirm.class);
		        	//Create a bundle object
			        Bundle b = new Bundle();
			        b.putString("quest",quest_contents);
			        b.putString("opta",opta_contents);
			        b.putString("optb",optb_contents);
			        b.putString("optc",optc_contents);
			        b.putString("optd",optd_contents);
			        b.putString("option", option);
			        //Add the bundle to the intent.
			        intent.putExtras(b);
			        //Toast.makeText(getApplicationContext(), "Done.", Toast.LENGTH_SHORT).show();
			        //start the DisplayActivity
			        startActivity(intent);
			        finish();
			    }		        
			}
		});
		
		pop.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "Filled it! Plz tick a box.", Toast.LENGTH_SHORT).show();
				quest.setText("Test Question");
				opta.setText("Optiona");
				optb.setText("Optionb");
				optc.setText("Optionc ");
				optd.setText("Optiond");
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.admin_home, menu);
		return true;
	}

}
