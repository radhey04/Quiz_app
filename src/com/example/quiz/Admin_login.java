package com.example.quiz;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Admin_login extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin_login);
		
		// Getting the login button details
		Button login= (Button) findViewById(R.id.button1);
		// Getting the password
		final EditText pwd=(EditText) findViewById(R.id.editText2);
		
		login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String pwd_contents = pwd.getText().toString();
		        //Inserts a String value into the mapping of this Bundle
		        if(pwd_contents.equals("a"))
			    {
		        	Intent intent = new Intent(getApplicationContext(), Admin_base.class);
		        	//Create a bundle object
			        Bundle b = new Bundle();
			        b.putString("pwd",pwd_contents);
			        //Add the bundle to the intent.
			        intent.putExtras(b);
			        //start the DisplayActivity
			        startActivity(intent);
			        finish();
			    }
		        else
		        {
		        	Toast.makeText(getApplicationContext(), "Invalid Password", Toast.LENGTH_LONG).show();   	
		        }
		        
			}
		});
	}

}
