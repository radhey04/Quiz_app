package com.example.quiz;

import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Handler.Callback;
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
	
	//Timer
	TextView time;
    long starttime = 0;
    final Handler h = new Handler(new Callback() {

        @Override
        public boolean handleMessage(Message msg) {
           long millis = System.currentTimeMillis() - starttime;
           int seconds = (int) (millis / 1000);
           int minutes = seconds / 60;
           seconds     = seconds % 60;

           time.setText(String.format("%d:%02d", minutes, seconds));
            return false;
        }
    });

    class firstTask extends TimerTask {

        @Override
        public void run() {
            h.sendEmptyMessage(0);
        }
   };

   Timer timer = new Timer();
	
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
    scoresDBAdapter ads;
    private void updateactivity()
    {    	
	    Cursor c1=ad.getQno(qno);
	    if(c1==null)
		{
			Toast.makeText(getApplicationContext(), "Invalid question no", Toast.LENGTH_SHORT).show();
		}
	    else
	    {
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
	    ads=new scoresDBAdapter(context);
	    //ads.deleteEntry(1);
	    ads.dropsheet();
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
	    
		time = (TextView)findViewById(R.id.textView6);
        
        starttime = System.currentTimeMillis();
        timer = new Timer();
        timer.schedule(new firstTask(), 0,500);

		
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
		        		ads.insertans(option,answ,1);
		        	}
		        	else 
		        	{		        		
		        		Toast.makeText(getApplicationContext(), "You are wrong", Toast.LENGTH_SHORT).show();
		        		Log.d("Debug","Incorrect");
		        		ads.insertans(option,answ,0);
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
		        		Intent i=new Intent(getApplicationContext(), User_publish.class);
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

