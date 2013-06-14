package com.example.quiz;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class Mainquiz extends Activity {

	Context context = this;
	
	
	private Integer qno;
    private String answ;
    private Integer totq;
    private String timeleft;
    CheckBox chka;
	CheckBox chkb;
	CheckBox chkc;
	CheckBox chkd;
	TextView Q;
    TextView opta;
    TextView optb;
    TextView optc;
    TextView optd;
    MyDBAdapter ad;
    
    private void updateactivity()
    {    	
	    Cursor c1=ad.getQno(qno);
	    Q.setText(c1.getString(1));
	    opta.setText(c1.getString(2));
	    optb.setText(c1.getString(3));
	    optc.setText(c1.getString(4));
	    optd.setText(c1.getString(5));
	    answ=c1.getString(6).toString();
	    chka.setChecked(false);
	    chkb.setChecked(false);
	    chkc.setChecked(false);
	    chkd.setChecked(false);
	    Log.d("Debug","Updated");	    
    }
    
    private void Initialize()
    {
    	Q=(TextView) findViewById(R.id.textView1);
	    opta=(TextView) findViewById(R.id.textView2);
	    optb=(TextView) findViewById(R.id.textView3);
	    optc=(TextView) findViewById(R.id.textView4);
	    optd=(TextView) findViewById(R.id.textView5);
	    chka = (CheckBox)findViewById(R.id.checkBox1);
		chkb = (CheckBox)findViewById(R.id.checkBox2);
		chkc = (CheckBox)findViewById(R.id.checkBox3);
		chkd = (CheckBox)findViewById(R.id.checkBox4);
	    ad=new MyDBAdapter(context);

	    qno=1;											//Initialise the question number
	    answ="";										//Initialise the answ string
	    
    }
    
    private Boolean isquizover()
    {
    	if(qno>totq)
    	{
    		Log.d("Debug","Quiz Over.");
    		return true;
    	}
    	else if(timeleft.equals(""))
    	{
    		Log.d("Debug","Time up.");
    		return true;
    	}
    	else
    	{
    		Log.d("Debug","Continue to next question");
    		return false;
    	}
    }
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mainquiz);
		
		Bundle b = getIntent().getExtras();
		
		totq=b.getInt("totq");
		timeleft=b.getString("timeleft");
		
		Log.d("Debug","The total number of questions");
		Log.d("Debug",totq.toString());
		Log.d("Debug","Time");
		Log.d("Debug",timeleft);
	    
		Button submit=(Button) findViewById(R.id.button1);
	    
	    Initialize();
	    updateactivity();
	    
	    submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			    
			    String option = "";
			    if(chka.isChecked())
				{
					option=option.concat("A");
				}
				if(chkb.isChecked())
				{
					option=option.concat("B");
				}
				if(chkc.isChecked())
				{
					option=option.concat("C");
				}
				if(chkd.isChecked())
				{
					option=option.concat("D");
				}
		        //Inserts a String value into the mapping of this Bundle
				Log.d("Debug","Your answer");
				Log.d("Debug",option);
				Log.d("Debug","Correct answer");
				Log.d("Debug",answ);
			    if(!option.equals(""))
			    {
			    	if(option.equals(answ))	// Always the order will be A-B-C-D. :)
		        	{
		        		Toast.makeText(getApplicationContext(), "You are correct", Toast.LENGTH_SHORT).show();
		        		Log.d("Debug","Correct");
		        	}
		        	else 
		        	{		        		
		        		Toast.makeText(getApplicationContext(), "You are wrong", Toast.LENGTH_SHORT).show();
		        		Log.d("Debug","Incorrect");
		        	}
			    	qno=qno+1;
			    	if(!isquizover())
			    	{
			    		updateactivity();
			    		Log.d("Debug","Question Answered");
			    	}
			    	else
			    	{
			    		Toast.makeText(getApplicationContext(), "Congrats", Toast.LENGTH_SHORT).show();
		        		Log.d("Debug","Quiz Over! Congrats");
		        		Intent i=new Intent(getApplicationContext(), Admin.class);
						startActivity(i);
						finish();
			    	}
			    	
			    }
				else
				{
					Toast.makeText(getApplicationContext(), "You forgot to enter answer.", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mainquiz, menu);
		return true;
	}

}

