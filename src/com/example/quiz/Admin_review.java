package com.example.quiz;

import com.example.quiz.MyDBAdapter;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Admin_review extends Activity {

	Context context = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin_review);
		
		MyDBAdapter ad = new MyDBAdapter(context);
		
		Button back=(Button) findViewById(R.id.button1);
		
		EditText t=(EditText) findViewById(R.id.editText1);
		
		Cursor c1 = ad.getAllQs();
		
		String Qs="";
		
		while(c1.moveToNext())
		{
			Qs = Qs.concat(c1.getString(0));
			Qs = Qs.concat(" ");
			Qs = Qs.concat(c1.getString(1));
			Qs = Qs.concat("\n");
		}
		t.setText(Qs);
		
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(), Admin_base.class);
				startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.admin_review, menu);
		return true;
	}

}

