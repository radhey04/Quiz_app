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
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class User_landing extends Activity {
	
	Context context=this;
	Integer totq;
	String timeleft;
	@SuppressLint("SimpleDateFormat")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_landing);
		Log.d("DEBUG","User Landing");
		Button b=(Button) findViewById(R.id.button1);
		TextView t=(TextView) findViewById(R.id.textView1);
		
		final MyDBAdapter ad=new MyDBAdapter(context);
		Cursor c = ad.getQBset();
		
		String dates=c.getString(6);
		Date expiry=null;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
	    try {
	        expiry = formatter.parse(dates);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    String datef=new SimpleDateFormat("dd MMM yyyy").format(expiry.getTime());    
		
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
        
		b.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.d("DEBUG","button click");
				DownloadWebPageTask task = new DownloadWebPageTask();
				SettingsDBAdapter set = new SettingsDBAdapter(context);
				set.updatemem();
				String studentID = set.ID;
				String name = set.Name;
				
				MyDBAdapter ad=new MyDBAdapter(context);
				Cursor c=ad.getQBset();
				String QuizName = c.getString(2);
				String Deadline = c.getString(6);
				QuizName= QuizName.replace(" ", "");
				name=name.replace(" ","");
				
				Log.d("DEBUG", "SpaceRemoved:"+QuizName);
								
				String Student_ID = "Student_ID='"+studentID+"'";
				String Name = "Name='"+name+"'";
				String Deads= "Deadline='"+Deadline+"'";
				String Quiz_Name = "Quiz_Name='"+QuizName+"'";
								
				String url = "http://10.0.0.4/app/Authenticate.php?"+Student_ID+"&"+Name+"&"+Quiz_Name+"&"+Deads;
				Log.d("debug", url);
				task.execute(url);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user_landing, menu);
		return true;
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
			}
			else
			{
				Toast.makeText(context,  "You have already taken the test. You being sent back to the previous page", Toast.LENGTH_LONG).show();
				// Calling finish will take you back to admin.java
			}
			finish();
		}
	}
}
