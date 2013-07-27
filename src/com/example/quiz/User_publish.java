package com.example.quiz;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
//import android.widget.Toast;
import android.widget.Toast;

public class User_publish extends Activity {

	Context context=this;
	SettingsDBAdapter set;
	String url1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_publish);
		
		int currentOrientation = getResources().getConfiguration().orientation;
		if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
		   setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
		}
		else {
		   setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
		}
		
		TextView t3=(TextView) findViewById(R.id.textView3);
		TextView t4=(TextView) findViewById(R.id.textView4);
		TextView t6=(TextView) findViewById(R.id.textView6);
		TextView t7=(TextView) findViewById(R.id.textView7);
		
		set= new SettingsDBAdapter(context);
		Log.d("Debug",set.Name);
		Log.d("Debug",set.ID);
		Log.d("Debug","Values to be printed on my scoresheet");
		set.updatemem();
		t3.setText(set.Name);
		t4.setText(set.ID);
		Log.d("Debug",set.Name);
		Log.d("Debug",set.ID);
		Log.d("Debug","Values to be printed on my scoresheet");
		/**/
		scoresDBAdapter ads=new scoresDBAdapter(context);
		
		Integer N=ads.N;
		Integer Score=ads.getscore();
		String res=Score.toString();
		res=res.concat("/");
		res=res.concat(N.toString());
		t6.setText(res);
		
		t7.setText(ads.perf);
		
		Button ret=(Button) findViewById(R.id.button1);
		
		MyDBAdapter ad=new MyDBAdapter(context);
		Cursor c=ad.getQBset();
		// Duration is given by c.getString(4);
		// Nikhil's score code comes here
		String student_ID = set.ID;
		final String studentID = student_ID.replace(" ", "");
		String quiz_Name = c.getString(2);
		final String QuizName=quiz_Name.replace(" ", "");
		final String score = ads.getscore().toString();
		final String TimeLimit = c.getString(4)+"00";
		
		DownloadWebPageTask task = new DownloadWebPageTask();
		
		String Student_ID = "Student_ID='"+studentID+"'";
		String Quiz_Name = "Quiz_Name='"+QuizName+"'";
		String Scre  = "Score="+score;
		String Time_Limit = "TimeLimit="+TimeLimit;
		url1= set.URL+"app/score.php";
		String url = set.URL+"app/score.php?"+Student_ID+"&"+Scre+"&"+Time_Limit+"&"+Quiz_Name;
		Log.d("DEBUG", url);
		if(set.disablehttp==true)
		{
			Toast.makeText(getApplicationContext(), "Working in HTTP Disabled mode", Toast.LENGTH_LONG).show();
			Log.d("Debug_user_publish","Bypassing the http authentication");
			Toast.makeText(context,  "Scores Updated",Toast.LENGTH_SHORT).show();
		}
		else
		{
			task.execute(url);
		}
		
		markDBAdapter md=new markDBAdapter(context);
		md.insertmark(md.N+1,quiz_Name, score, c.getString(3));
		c.close();
		Toast.makeText(getApplicationContext(),"Scores uploaded", Toast.LENGTH_SHORT).show();
		
		ret.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// Intent intent = new Intent(getApplicationContext(), Admin.class);
	        	// startActivity(intent);
				// Safety net is there. So NO need to create another activity.
	        	finish();
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
						Log.d("DEBUG", s);
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
			Log.d("DEBUG", "onPostExecute");
			//Toast.makeText(context,  result, Toast.LENGTH_LONG).show();
			//Disabling http
			if(result.equals("1"))
			{
				Toast.makeText(context,  "Scores Updated",Toast.LENGTH_SHORT).show();
			}
			else if(result.equals("0"))
			{
				Toast.makeText(context,  "Your quiz is timed out",Toast.LENGTH_SHORT).show();
			}
			else
			{
				Toast.makeText(context,  "Connection to "+url1+" could not be established", Toast.LENGTH_LONG).show();
			}
		}
	}

}
