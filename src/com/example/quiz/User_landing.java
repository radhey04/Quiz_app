package com.example.quiz;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class User_landing extends Activity {
	
	Context context=this;
	Integer totq;
	String timeleft;
	String url1;
	SettingsDBAdapter set;
	String datef="";
	@SuppressLint("SimpleDateFormat")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_landing);
		Log.d("DEBUG","User Landing");
		Button b=(Button) findViewById(R.id.button1);
		TextView t=(TextView) findViewById(R.id.textView1);
		
		final MyDBAdapter ad=new MyDBAdapter(context);
		set=new SettingsDBAdapter(context);
		set.updatemem();
		Cursor c = ad.getQBset();
		String dates=c.getString(6);
		
		Date expiry=null;
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		    expiry = formatter.parse(dates);
		    Log.d("DEBUG","Reached before datef");
		    datef=new SimpleDateFormat("dd-MMM-yyyy").format(expiry.getTime());
		    Log.d("DEBUG","Cleared datef");
		} catch (Exception e) {
	        e.printStackTrace();
	        Log.d("DEBUG","Shit!");
	        Toast.makeText(context, "Invalid database as date not set correctly", Toast.LENGTH_LONG).show();
	        finish();
		}
		String details="Hi. The following are the details of " +
				"the quiz you are about to take.";
		details=details.concat("\n\n Quiz Name: "+c.getString(2));
		details=details.concat("\n\n # of Qs: "+c.getString(3));
		details=details.concat("\n\n Duration: "+c.getString(4)+" mins");
		details=details.concat("\n\n Deadline: "+datef);
		details=details.concat("\n\n Syllabus:\n"+c.getString(5));
		t.setText(details);
		
		
		totq= Integer.parseInt(c.getString(3));
		timeleft=c.getString(4);
		b.setText("Authenticate & Continue");		        
        c.close();
		b.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.d("DEBUG","button click");
				DownloadWebPageTask task = new DownloadWebPageTask();
				String studentID = set.ID;
				String name = set.Name;
				
				MyDBAdapter ad=new MyDBAdapter(context);
				Cursor c=ad.getQBset();
				String QuizName = c.getString(2);
				c.close();
				
				String Deadline = datef;
				QuizName= QuizName.replace(" ", "");
				name=name.replace(" ","");
				studentID=studentID.replace(" ","");
				
				Log.d("DEBUG", "SpaceRemoved:"+QuizName);
								
				String Student_ID = "Student_ID='"+studentID+"'";
				String Name = "Name='"+name+"'";
				String Deads= "Deadline='"+Deadline+"'";
				String Quiz_Name = "Quiz_Name='"+QuizName+"'";
								
				Log.d("DEBUG", "Deadline:"+Deads);
				url1= set.URL+"Authenticate.php";
				String url = set.URL+"Authenticate.php?"+Student_ID+"&"+Name+"&"+Quiz_Name+"&"+Deads;
				Log.d("debug", url);
				scoresDBAdapter ads=new scoresDBAdapter(context);
				ads.dropsheet();		// Clear the sheet
				if(set.disablehttp==true)
				{
					Toast.makeText(getApplicationContext(), "Working in HTTP Disabled mode | Orientation Locked", Toast.LENGTH_LONG).show();
					Log.d("Debug_user_landing","Bypassing the http authentication");
					proceed();
				}
				else
				{
					Toast.makeText(getApplicationContext(), "Orientation Locked", Toast.LENGTH_LONG).show();
					task.execute(url);
				}
				
			}
		});
	}

	private class DownloadWebPageTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... urls) {
			Log.d("DEBUG", "Inside dwp");
			String response = "";
			for (String url : urls) {
				DefaultHttpClient client = new DefaultHttpClient();
				HttpGet httpGet = new HttpGet(url);
				try {
					HttpResponse execute = client.execute(httpGet);
					InputStream content = execute.getEntity().getContent();

					BufferedReader buffer = new BufferedReader(
							new InputStreamReader(content));
					String s = "";
					while ((s = buffer.readLine()) != null) {
						response += s;
					}

				} catch (Exception e) {
					e.printStackTrace();
					Log.d("DEBUG", "Exception");
				}
			}
			Log.d("DEBUG", "Done dwp");
			return response;
		}

		@Override
		protected void onPostExecute(String result) {
			Log.d("DEBUG", "onPostExecute"+result);
			if(result.equals("1"))
			{
				proceed();
			}
			else if(result.equals("2"))
			{
				Toast.makeText(context,  "You have already taken the test. You being sent back to the previous page", Toast.LENGTH_LONG).show();
				// Calling finish will take you back to admin.java
			}
			else if(result.equals("3"))
			{
				Toast.makeText(context,  "The test is past the deadline. You being sent back to the previous page", Toast.LENGTH_LONG).show();
				// Calling finish will take you back to admin.java
			}
			else
			{
				Toast.makeText(context,  "The website "+url1+" cannot be reached", Toast.LENGTH_LONG).show();
			}
			finish();
		}
	}
	
	protected void proceed()
	{
		final Bundle bund = new Bundle();
		
		// Bundle containing
		// Total Questions stored as totq (int)
		// Time as timeleft (String)
						
	    bund.putInt("totq",totq);
	    bund.putString("timeleft",timeleft);
	    
	    //Toast.makeText(context,  "The test will start now. All the best!!!", Toast.LENGTH_SHORT).show();
		Intent i= new Intent(getApplicationContext(),Mainquiz.class);
		i.putExtras(bund);
		startActivity(i);
		finish();
	}
}
