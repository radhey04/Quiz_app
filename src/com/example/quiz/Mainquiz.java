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
		Cursor c = ad.getAllQs();
		Integer n = c.getCount();
		//TODO Uniqueness
		
	    Integer rand = (int) (n*Math.random()+0.5);
	    if (rand==0) {
	    	rand = 1;
	    }
	    final Cursor c1=ad.getQno(rand);
	    
	    TextView t1=(TextView) findViewById(R.id.textView1);
	    TextView t2=(TextView) findViewById(R.id.textView2);
	    TextView t3=(TextView) findViewById(R.id.textView3);
	    TextView t4=(TextView) findViewById(R.id.textView4);
	    TextView t5=(TextView) findViewById(R.id.textView5);
	    
	    t1.setText(c1.getString(1));
	    t2.setText(c1.getString(2));
	    t3.setText(c1.getString(3));
	    t4.setText(c1.getString(4));
	    t5.setText(c1.getString(5));
	    
	    Button submit=(Button) findViewById(R.id.button1);
	    /*String ans = "";
	    Log.d("Debug",c1.getString(1));
	    ans=ans.concat(c1.getString(6));
		Log.d("Debug","Going to c ans");
	    Log.d("Debug",ans);*/
	    Log.d("Debug",c1.getString(1));
	    String ans=c1.getString(6).toString();
	    Log.d("Debug","Going to c ans");
	    Log.d("Debug",ans);
	    
	    submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			    String ans="A";
				
			    final CheckBox chka = (CheckBox)findViewById(R.id.checkBox1);
				final CheckBox chkb = (CheckBox)findViewById(R.id.checkBox2);
				final CheckBox chkc = (CheckBox)findViewById(R.id.CheckBox01);
				final CheckBox chkd = (CheckBox)findViewById(R.id.CheckBox02);
			    String option = null;
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
					Toast.makeText(getApplicationContext(), "You forgot to enter answer.", Toast.LENGTH_SHORT).show();
				}
		        //Inserts a String value into the mapping of this Bundle
			    Log.d("Debug",option);
			    if(option.equals("A") || option.equals("B") || option.equals("C") || option.equals("D"))
			    {
		        	if(option.equals(ans))
		        	{
		        		Toast.makeText(getApplicationContext(), "You are correct", Toast.LENGTH_SHORT).show();
		        	}
		        	else {
		        		
		        		Toast.makeText(getApplicationContext(), "You are wrong", Toast.LENGTH_SHORT).show();
		        	}
			        
			        //start the DisplayActivity
			        //startActivity(intent);
			    }		        
			    Log.d("Debug","Bye");
			    
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
