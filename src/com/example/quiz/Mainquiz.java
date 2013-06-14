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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mainquiz);
		
		final MyDBAdapter ad=new MyDBAdapter(context);
		
		Bundle b = getIntent().getExtras();
		final Integer qno=b.getInt("qno");
		final Integer totq=b.getInt("totq");
		final String timeleft=b.getString("timeleft");
		
		Log.d("Debug","The current values");
		Log.d("Debug",qno.toString());
		Log.d("Debug",totq.toString());
		Log.d("Debug",timeleft);
	    
		TextView t1=(TextView) findViewById(R.id.textView1);
	    TextView t2=(TextView) findViewById(R.id.textView2);
	    TextView t3=(TextView) findViewById(R.id.textView3);
	    TextView t4=(TextView) findViewById(R.id.textView4);
	    TextView t5=(TextView) findViewById(R.id.textView5);
	    Button submit=(Button) findViewById(R.id.button1);
	    
	    final Cursor c1=ad.getQno(qno);
	    t1.setText(c1.getString(1));
	    t2.setText(c1.getString(2));
	    t3.setText(c1.getString(3));
	    t4.setText(c1.getString(4));
	    t5.setText(c1.getString(5));
	    final String ans=c1.getString(6).toString();
	    
	    Log.d("Debug","Waiting for a new option");
	    submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			    
			    final CheckBox chka = (CheckBox)findViewById(R.id.checkBox1);
				final CheckBox chkb = (CheckBox)findViewById(R.id.checkBox2);
				final CheckBox chkc = (CheckBox)findViewById(R.id.checkBox3);
				final CheckBox chkd = (CheckBox)findViewById(R.id.checkBox4);
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
				Log.d("Debug",option);
			    if(!option.equals(""))
			    {
			    	Bundle bn=new Bundle();
			    	Integer qnon=qno+1;
		        	if(option.equals(ans))	// Always the order will be A-B-C-D. :)
		        	{
		        		Toast.makeText(getApplicationContext(), "You are correct", Toast.LENGTH_SHORT).show();		        				        		
		        	}
		        	else 
		        	{		        		
		        		Toast.makeText(getApplicationContext(), "You are wrong", Toast.LENGTH_SHORT).show();
		        	}
		        	bn.putInt("qno",qnon);
			        bn.putInt("totq",totq);
			        bn.putString("time",timeleft);
			        //Intent i= new Intent(Mainquiz.this,Mainquiz.class);
			        // Solution given above: 
			        Intent i = new Intent(getApplicationContext(), Mainquiz.class);
			        //Add the bundle to the intent.
			        i.putExtras(bn);
			        startActivity(i);
					finish();			        
			    }
				else
				{
					Toast.makeText(getApplicationContext(), "You forgot to enter answer.", Toast.LENGTH_SHORT).show();
				}
			    Log.d("Debug","Question Answered");
			    
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

