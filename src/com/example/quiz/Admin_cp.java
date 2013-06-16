package com.example.quiz;

import java.text.DateFormat;
import java.util.Calendar;

import android.os.Bundle;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

public class Admin_cp extends Activity {
	
	Calendar dateTime=Calendar.getInstance();
	DateFormat formatDateTime=DateFormat.getDateTimeInstance();
	Button setd;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin_cp);

		Button conf=(Button) findViewById(R.id.button1);
		setd=(Button) findViewById(R.id.button2);
		
		setd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				chooseDate();
			}
		});
	}
		    
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.admin_cp, menu);
		return true;
	}
    
    DatePickerDialog.OnDateSetListener d=new DatePickerDialog.OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {
			dateTime.set(Calendar.YEAR,year);
			dateTime.set(Calendar.MONTH, monthOfYear);
			dateTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
			updateLabel();
		}
	};
	
    public void chooseDate(){
    	new DatePickerDialog(Admin_cp.this, d, dateTime.get(Calendar.YEAR),dateTime.get(Calendar.MONTH), dateTime.get(Calendar.DAY_OF_MONTH)).show();
    }
    
    public void updateLabel()
    {
    	Toast.makeText(getApplicationContext(), "Yes", Toast.LENGTH_SHORT).show();
    	setd.setText(formatDateTime.format(dateTime.get());    	
    }
}
