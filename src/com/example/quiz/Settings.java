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
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class Settings extends Activity {

	Context context=this;
	
	private Integer Timer = 0,dh=0;
	String Name="";
	String ID="";
	String URL="";
	String Pass="";
	
	SettingsDBAdapter set;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		
		set = new SettingsDBAdapter(context);
		
		final EditText name = (EditText) findViewById(R.id.editText1);
		final EditText id = (EditText) findViewById(R.id.editText2);
		final EditText url = (EditText) findViewById(R.id.editText3);
		final EditText pass = (EditText) findViewById(R.id.editText4);
		
		final CheckBox timer = (CheckBox) findViewById(R.id.checkBox1);
		final CheckBox httpdisb = (CheckBox) findViewById(R.id.checkBox2);
		//Made the checkbox invisible..use master's copy of settings* - all 3 files if you want to revert
		httpdisb.setVisibility(4);
		
		Button sub = (Button) findViewById(R.id.button1);
		Button gen = (Button) findViewById(R.id.button2);
		
		final AlertDialog.Builder builder= new AlertDialog.Builder(context);

		set.updatemem();
		timer.setChecked(true);
		if(set.Name.equals(""))
		{
			name.setHint("Your Name");
		}
		else
		{
			name.setText(set.Name);
		}
		if(set.ID.equals(""))
		{
			id.setHint("Your ID");
		}            
		else
		{
			id.setText(set.ID);
		}
		if(set.ShowTimer==true)
		{
			timer.setChecked(true);
		}            
		else
		{
			timer.setChecked(false);
		}
		if(set.URL.equals(""))
		{
			url.setText("http://192.168.1.10/app/");
		}
		else
		{
			url.setText(set.URL);
		}
		if(set.Pass.equals(""))
		{
			pass.setText("");
			pass.setHint("Password");
		}
		else
		{
			pass.setText(set.Pass);
		}//Shifted the commented section here below
		
		gen.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Pass=pass.getText().toString();
				// Make the string empty.
				if(Pass.isEmpty()==false)
				{
					Toast.makeText(context,"You seem to have provided me with some password. Kindly remove that.", Toast.LENGTH_SHORT).show();
				}
				else
				{
					builder.setTitle("Generate Password?");
					builder.setMessage("You need a password to access the application. If you " +
							"haven't requested one for yourself yet, click on yes to ask for one. " +
							"A numeric password will be sent to your smail account. If you have " +
							"already registered once, the system will NOT generate a new password. " +
							"Contact your branch councillor for assistance.");
			    	builder.setIcon(android.R.drawable.ic_dialog_alert);
			    	builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			    	    public void onClick(DialogInterface dialog, int which) {
							//TODO:Nikhil's authenticate request code comes here
			    	    	DownloadWebPageTask task = new DownloadWebPageTask();
			    	    	String Student_ID = "RollNumber='"+id.getText().toString()+"'";
							String Name = "Name='"+name.getText().toString()+"'";
							String url = "http://192.168.1.10/app/registration.php?"+Student_ID+"&"+Name;
							task.execute(url);
			    	    }
			    	});
			    	builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
			    	    public void onClick(DialogInterface dialog, int which) {
			    	    	Toast.makeText(context,"Please provide your correct password.", Toast.LENGTH_SHORT).show();
						}
			    	});
			    	builder.show();
				}
			}
		});
		
		sub.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
								
				Name = name.getText().toString();
				ID = id.getText().toString();
				URL=url.getText().toString();
				Pass=pass.getText().toString();
				if(timer.isChecked()) {
					Timer = 1;
				}
				else 
				{
					Timer = 0;
				}
				if(Name.equals("") || ID.equals("") || URL.equals("") || Pass.equals(""))
				{
					Toast.makeText(context, "You can't leave any field empty!!!", Toast.LENGTH_SHORT).show();			
				}
				else if(!isValidUrl(URL))
				{
					Toast.makeText(context, "Please enter a valid URL!!!", Toast.LENGTH_SHORT).show();
				}
				else
				{
					if(!URL.endsWith("/")) {
						URL=URL+"/";  //Ensuring that the URL ends with a Backslash
					}
					// The settings db is now consistent with the master code..dh is always zero
					// TODO: Authenticate the password here and call below functions only if 
					// successful authentication
					DownloadWebPageTask1 task1 = new DownloadWebPageTask1();
	    	    	String Student_ID = "RollNumber='"+ID+"'";
					String UserName = "Name='"+Name+"'";
					String Password = "Password='"+Pass+"'";
					String url = "http://192.168.1.10/mocktest/UserAuthenticate.php?"+Student_ID+"&"+UserName+"&"+Password;
					task1.execute(url);
				}
			}
		});
	}
	boolean isValidUrl(String url)
	{
		boolean validity=true;
		if(url.contains(" "))
					validity=false;
		else if(url.contains("$"))
				validity=false;
		else if(url.contains("^"))
				validity=false;
		else if(url.contains("*"))
				validity=false;
		else if(url.contains("\n"))
			validity=false;
		else if(url.contains("^"))
			validity=false;
		else if(url.contains("("))
			validity=false;
		else if(url.contains(")"))
			validity=false;
		return validity;
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
				Toast.makeText(context, "You have successfully registered. Check your smail for the password.", Toast.LENGTH_LONG).show();
			}
			else if(result.equals("2"))
			{
				Toast.makeText(context,  "You have already registered. Check your smail for the password.", Toast.LENGTH_LONG).show();
				// Calling finish will take you back to admin.java
			}
			else
			{
				Toast.makeText(context,  "The website cannot be reached", Toast.LENGTH_LONG).show();
			}
			finish();
		}
	}
	private class DownloadWebPageTask1 extends AsyncTask<String, Void, String> {
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
				set.updateset(Name, ID, Timer,dh,URL,Pass);
				Toast.makeText(getApplicationContext(), "Settings updated", Toast.LENGTH_SHORT).show();
				Intent i=new Intent(getApplicationContext(), Admin.class);
				startActivity(i);
				finish();
			}
			else if(result.equals("2"))
			{
				Toast.makeText(context,  "Your Roll Number and Password do not match. Please check your password.", Toast.LENGTH_LONG).show();
				// Calling finish will take you back to admin.java
			}
			else
			{
				Toast.makeText(context,  "The website cannot be reached", Toast.LENGTH_LONG).show();
			}
			finish();
		}
	}
}