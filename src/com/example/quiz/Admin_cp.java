package com.example.quiz;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class Admin_cp extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin_cp);
		
	}
		    
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.admin_cp, menu);
		return true;
	}

}
