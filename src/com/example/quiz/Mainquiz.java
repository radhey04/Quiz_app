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
	int Tottime;
    long starttime = 0;

    Timer timer = new Timer();
    Button submit,prev,cimg;
 	private Integer qno,qmax;
    private String answ;
    private Integer totq;
    private String timeleft;
    private boolean Must_answer=false;
    private boolean prev_flag=false;
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
    SettingsDBAdapter set;
    
    final Handler h = new Handler(new Callback() {

        @Override
        public boolean handleMessage(Message msg) {
           long millis = System.currentTimeMillis() - starttime;
           int seconds = (int) (millis / 1000);
           int minutes = seconds / 60;
           seconds     = seconds % 60;
           
           int seconds_left = 59 - seconds;
           int minutes_left = Tottime - minutes - 1;
           
           if(minutes_left==0 && seconds_left==0){
        	   
        	   timer.cancel();
        	   timer.purge();
        	   Toast.makeText(getApplicationContext(), "Time up", Toast.LENGTH_SHORT).show();
       		   Intent j=new Intent(getApplicationContext(), User_publish.class);
       		   startActivity(j);
       		   finish();
           }
           if(set.ShowTimer==true)
        	   time.setText(String.format("%d:%02d", minutes_left, seconds_left));
           else
        	   time.setText("Timer running. <Set to invisible>");
           return false;
        }
    });

    class firstTask extends TimerTask {

        @Override
        public void run() {
            h.sendEmptyMessage(0);
        }
   };

    private void updateactivity()
    {    	
	    Cursor c1=ad.getQno(qno);
	    if(c1==null)
		{
			Toast.makeText(getApplicationContext(), "Invalid question no", Toast.LENGTH_SHORT).show();
		}
	    else
	    {
	    	Log.d("Debug_Mainquiz","Updating Question");
		    Q.setText(c1.getString(1)+". "+c1.getString(2));
		    opta.setText(c1.getString(3));
		    optb.setText(c1.getString(4));
		    optc.setText(c1.getString(5));
		    optd.setText(c1.getString(6));
		    answ=c1.getString(7).toString();
		    Integer imagethere=c1.getInt(8);
		    c1.close();
//		    Integer imagethere=0;
		    if(imagethere==0)
		    {
		    	cimg.setVisibility(4);	//Invisible
		    }
		    else
		    {
		    	cimg.setVisibility(0);	//Visible
		    }
		    // Clearing all checkboxes
	        chka.setChecked(false);
		    chkb.setChecked(false);
		    chkc.setChecked(false);
		    chkd.setChecked(false);
		    if(prev_flag==true)
		    {
				Cursor c=ads.getQno(qno);
		    	String mans=c.getString(2);//Marked ans
			    c.close();
		    	Log.d("Debug_Mainquiz","Have to decipher => "+mans);
		    	if(mans.contains("A")==true)
		    		chka.setChecked(true);
		    	if(mans.contains("B")==true)
		    		chkb.setChecked(true);
		    	if(mans.contains("C")==true)
		    		chkc.setChecked(true);
		    	if(mans.contains("D")==true)
		    		chkd.setChecked(true);
		    	Log.d("Debug_Mainquiz","Checkboxes Corrected");
		    }
		    Log.d("Debug","Updated");
	    }
	    if(ad.N==qno)
	    {
	    	submit.setText("Finish");
	    }
	    else
	    {
	    	submit.setText("Next");
	    }
	    if(qno>1)
	    {
	    	prev.setVisibility(0);//Visible
	    }
	    else
	    {
	    	prev.setVisibility(4);//Invisible
		}
    	Log.d("Debug_Mainquiz","Question updated");    	
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
	    ads.dropsheet();								//Drop the scoresheet
	    qno=1;											//Initialise the question number
	    answ="";										//Initialise the answ string
	    set=new SettingsDBAdapter(context);
	    set.updatemem();								//Update the settings
	    prev.setVisibility(4);							//Invisible
    }
    
    private Boolean isquizover()
    {
    	if(qno>totq)
    	{
    		Log.d("Debug","Quiz Over.");
    		timer.cancel();
    		timer.purge();
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
		Tottime = Integer.parseInt(timeleft);
        
		
		time = (TextView)findViewById(R.id.textView6);
		starttime = System.currentTimeMillis();
        timer = new Timer();
        timer.schedule(new firstTask(), 0,500);

		
		submit=(Button) findViewById(R.id.button1);
		prev=(Button) findViewById(R.id.button2);
	    cimg=(Button) findViewById(R.id.button3);
	    
	    Initialize();
	    updateactivity();
	    
	    prev.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(prev_flag==false)
				{
					qmax=qno;
					prev_flag=true;
				}					
				Log.d("Debug_Mainquiz","Previous button pressed. Prev_flag " +
						"=> "+prev_flag+" qno =>"+qno+" qmax => "+qmax);
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
				// Now, even if answers are unchecked Next key will work if  
				// Must_answer is set to false.
			    if(!option.equals("")||(Must_answer==false))
			    {
			    	Log.d("Debug_Mainquiz","Scoring the answer (prev)");
			    	Cursor c=ads.getQno(qno);
			    	if(c!=null)		// Qno can't be null...and if it is not null update
			    	{
				    	if(option.equals(answ))	// Always the order will be A-B-C-D. :)
			        	{
			        		ads.updateans(qno,option,answ,1);
			        		Log.d("Debug_Mainquiz","Corrected a previous question");						    
			        	}
			        	else 
			        	{		        		
			        		ads.updateans(qno,option,answ,0);
			        		Log.d("Debug_Mainquiz","Wronged a previous question");
			        	}
				    	c.close();		// Close only if null
				    	Log.d("Debug_Mainquiz","Closed it");				        
				    }
			    	else//The first previous button press..updating the answers in the current question
			    	{
				    	if(option.equals(answ))	// Always the order will be A-B-C-D. :)
			        	{
			        		ads.insertans(qno,option,answ,1);
			        		Log.d("Debug_Mainquiz","Going to a previous question but this question is correct");
			        	}
			        	else 
			        	{		        		
			        		ads.insertans(qno,option,answ,0);
			        		Log.d("Debug_Mainquiz","Going to a previous question but this question is wrong");
			        	}
				    }
			    	qno=qno-1;
			    	updateactivity();
			    	Log.d("Debug","Question Updated");
			    	// We will never hit quiz over from here.
			    }
				else
				{
					Toast.makeText(getApplicationContext(), "You forgot to enter answer.", Toast.LENGTH_SHORT).show();
				}		    	
			}
		});
	    
	    submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Log.d("Debug_Mainquiz","Prev_flag => "+prev_flag+" qno =>"+qno+" qmax => "+qmax);
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
				// Now, even if answers are unchecked Next key will work if  
				// Must_answer is set to false.
			    if(!option.equals("")||(Must_answer==false))
			    {
			    	Cursor c=ads.getQno(qno);
			    	Log.d("Debug_Mainquiz","Scoring the answer (next)");
			    	if(c!=null)		// Cursor is empty
			    	{
		    			if(option.equals(answ))	// Always the order will be A-B-C-D. :)
			        	{
			        		ads.updateans(qno,option,answ,1);
			        		Log.d("Debug_Mainquiz","In a previous question and made it correct");
			        	}
			        	else 
			        	{		        		
			        		ads.updateans(qno,option,answ,0);
			        		Log.d("Debug_Mainquiz","In a previous question and made it wrong");
			        	}
				    	if(qno==qmax)
				    	{
				    		prev_flag=false;	//Now whatever you will face is new
				    							//So don't have to check for checkboxes
				    	}
				    	c.close();
				    	Log.d("Debug_Mainquiz","Closed it");				        
				    }
				    else
				    {
				    	if(option.equals(answ))	// Always the order will be A-B-C-D. :)
			        	{
			        		ads.insertans(qno,option,answ,1);
			        		Log.d("Debug_Mainquiz","In a new question and made it correct");
			        	}
			        	else 
			        	{		        		
			        		ads.insertans(qno,option,answ,0);
			        		Log.d("Debug_Mainquiz","In a new question and made it wrong");
			        	}
				    }
			    	qno=qno+1;
					if(!isquizover())
			    	{
			    		updateactivity();
			    		Log.d("Debug","Question Answered");
			    	}
			    	else
			    	{
//			    		Toast.makeText(getApplicationContext(), "Quiz Over", Toast.LENGTH_SHORT).show();
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
	    
	    cimg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), FullscreenActivity.class);
	        	//Create a bundle object
		        Bundle b = new Bundle();
		        b.putInt("qno",qno);
		        //Add the bundle to the intent.
		        intent.putExtras(b);
		        //Toast.makeText(getApplicationContext(), "Done.", Toast.LENGTH_SHORT).show();
		        //start the DisplayActivity
		        startActivity(intent);
			}
		});
	}
}


