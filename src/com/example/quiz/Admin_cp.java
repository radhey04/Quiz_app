package com.example.quiz;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.example.quiz.R;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

@SuppressLint("SimpleDateFormat")
public class Admin_cp extends Activity {
	
	Context context=this;
	Calendar dateTime=Calendar.getInstance();
	DateFormat formatDateTime=DateFormat.getDateInstance();
	
//	SimpleDateFormat formatDateTime=new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
//	SimpleDateFormat fd = new SimpleDateFormat("HH:mm");
    
	Button setd;
	String datef="";
	EditText e1,e2,e3,e4;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin_cp);

		final MyDBAdapter ad=new MyDBAdapter(context);
		e1=(EditText) findViewById(R.id.editText1);
		e2=(EditText) findViewById(R.id.editText2);
		e3=(EditText) findViewById(R.id.editText3);
		e4=(EditText) findViewById(R.id.editText4);
		Button conf=(Button) findViewById(R.id.button1);
		Button pop=(Button) findViewById(R.id.button3);
		setd=(Button) findViewById(R.id.button2);
		
		setd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				chooseDate();
			}
		});
		conf.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(allset())
				{
					boolean imagethere=false;
					ad.insertQ(e1.getText().toString(), e2.getText().toString(), e3.getText().toString(), e4.getText().toString(), datef,"",imagethere,"");
					Intent intent = new Intent(context, Admin_home.class);
			        startActivity(intent);
			        Log.d("Debug","Registered the test");
			        finish();
				}
				else
				{
					Toast.makeText(getApplicationContext(), "You can't leave any field unspecified", Toast.LENGTH_SHORT).show();					
				}
			}
		});
		pop.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "Filled it! Plz tick a box.", Toast.LENGTH_SHORT).show();
				e1.setText("Test QP");
				e2.setText("1");
				e3.setText("2");
				e4.setText("This paper covers portions from different parts of Electrical Engineering.");				
			}
		});
	}
	
	public boolean allset()
	{
		if(e1.getText().toString().equals("")||e2.getText().toString().equals("")||e3.getText().toString().equals("")||e4.getText().toString().equals("")||datef.equals(""))
			return false;
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
    	SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
    	datef=sdf.format(dateTime.getTime());
        Integer datefint=Integer.parseInt(datef);

        Date d = new Date();
        String datet = sdf.format(d.getTime());
        Integer datetint=Integer.parseInt(datet);
    	
        Log.d("Debug_admin_cp","New date => "+datefint);
        Log.d("Debug_admin_cp","Date 2dy => "+datetint);
        if(datefint<datetint)
        {
    		Toast.makeText(context, "Please provide correct deadline", Toast.LENGTH_LONG).show();
    		datef="";
    		setd.setText("Set Date");
        }
    	else
    	{
    		String datedisp=new SimpleDateFormat("dd MMM yyyy").format(dateTime.getTime());
            setd.setText(datedisp);
    	}
    }
}
