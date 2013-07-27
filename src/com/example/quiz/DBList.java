package com.example.quiz;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;


import android.os.AsyncTask;
import android.os.Bundle;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class DBList extends ListActivity {
	
	public Context context = this;
	public String pressed;
	DBhandling db=new DBhandling();
	SettingsDBAdapter set;
	String url;
	int i=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Log.d("DEBUG", "Click");
		DownloadWebPageTask task = new DownloadWebPageTask();
		// no more this
		// setContentView(R.layout.list_fruit);
		set=new SettingsDBAdapter(context);
		set.updatemem();
		url = set.URL+"listdir.php";
		Log.d("debug", url);
		task.execute(url);		
	}
	private class DownloadWebPage extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... urls) {
			Log.d("DEBUG", "Inside dwp");
			String response = "";
			for (String url : urls) {
				try {
					String path = db.chkdir();
					File root = new File(path);
			        Log.d("DEBUG", "ROOT"+root);
			        URL u = new URL(url);
					HttpURLConnection c = (HttpURLConnection) u.openConnection();
			        c.setRequestMethod("GET");
			        c.setDoOutput(true);
			        c.connect();
			        
			        FileOutputStream f = new FileOutputStream(new File(root, "Quiz.nab"));

			        InputStream in = c.getInputStream();

			        byte[] buffer = new byte[1024];
			        int len1 = 0;
			        while ((len1 = in.read(buffer)) > 0) {
			            //publishProgress("" + (int)((total*100)/lenghtOfFile));
			            f.write(buffer, 0, len1);
			        }
			        f.close();
			        MyDBAdapter ad=new MyDBAdapter(context);
			        Log.d("DEBUG", "123Test123");
			        Boolean suc=db.importDB("Quiz",path+"/Quiz.nab");
			        Log.d("DEBUG", "123Test123");
			        if(suc==true)
					{
			        	Log.d("DEBUG", "234Test234");
						if(ad.N==-1)
						{
							Log.d("Debug_user_base","No Question bank detected");
							
						}
						else
						{
							Log.d("DEBUG", "Before Cursor");
							Cursor cur=ad.getQBset();
							Log.d("DEBUG", "After Cursor");
							String qnos=cur.getString(3);
							cur.close();
							Integer qno=Integer.parseInt(qnos);
							if(qno==ad.N)
							{
								Log.d("Debug_user_base","Everything is perfect. Launching the quiz");
								Intent i = new Intent(getApplicationContext(), User_landing.class);
								startActivity(i);
								
								Timer timer_begin1= new Timer();
								timer_begin1.schedule(new TimerTask() {
									
									@Override
									public void run() {
										// TODO Auto-generated method stub
										finish();
									}
								},(long) (0.7*1000));
								
							}
							else
							{
								//Toast.makeText(context, "Invalid Question Bank", Toast.LENGTH_SHORT);
								Log.d("Debug_user_base","# of qnos didn't match. ad.N =>"+ad.N+"# promised =>"+qno);
							}
						}
					}
					else
					{
						//Toast.makeText(getApplicationContext(), "Couldn't find the question bank.", Toast.LENGTH_SHORT).show();
					}
				} catch (Exception e) {
					e.printStackTrace();
					Log.d("DEBUG", "Exception");
				}
			}
			Log.d("DEBUG", "Done dwp");
			return response;
	}
	
	}

	private class DownloadWebPageTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... urls) {
			Log.d("DEBUG", "Inside dwp");
			String response = "";
			for (String url : urls) {
				HttpGet httpGet = new HttpGet(url);
				HttpParams httpParameters = new BasicHttpParams();
				int timeoutConnection = 3000;
				HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
				int timeoutSocket = 5000;
				HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

				DefaultHttpClient client = new DefaultHttpClient(httpParameters);

				try {
					HttpResponse execute = client.execute(httpGet);
					InputStream content = execute.getEntity().getContent();
					Log.d("DEBUG", "trycatch block in dwp");
					BufferedReader buffer = new BufferedReader(
							new InputStreamReader(content));
					String s = "";
					while ((s = buffer.readLine()) != null) {
						if(s.contains(".nab")){
							//String name = s.substring(96);
							//name.trim();
							//String token[] =name.split(delim);
							//Log.d("DEBUG", token[0].replace('"', ' '));
							response += s;
							response += "#";
						}
					}
					Log.d("DEBUG", response);
				} catch (ConnectTimeoutException cte) {
					cte.printStackTrace();
					Log.d("DEBUG", "ConnectionTimeout");
					i=1;
				}
				catch (Exception e) {
					e.printStackTrace();
					Log.d("DEBUG", "Exception");
					i=1;
				}
			}
			Log.d("DEBUG", "Done dwp");
			return response;
		}

		@Override
		protected void onPostExecute(String result) {
			Log.d("DEBUG", "onPostExecute"+result);
			if(i==1){
				Toast.makeText(context,  "Connection to "+url+" could not be established" , Toast.LENGTH_LONG).show();
				finish();
			}
			
			Log.d("DEBUG", result);
			String[] FRUITS = result.split("#");
			
			setListAdapter(new ArrayAdapter<String>(context, R.layout.activity_dblist,FRUITS));
			ListView listView = getListView();
			listView.setTextFilterEnabled(true);
			
			registerForContextMenu(getListView());  
			listView.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// When clicked, show a toast with the TextView text

					pressed = ((TextView) view).getText().toString();
					String popupt="Do you want to take the test "+pressed+"?";
					AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
							context);
					// set title
					alertDialogBuilder.setTitle("Your Title");

					// set dialog message
					alertDialogBuilder
					
					.setMessage(popupt)
					.setCancelable(false)
					.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							// if this button is clicked, close
							// current activity
							//MainActivity.this.finish();
							DownloadWebPage task1 = new DownloadWebPage();
							
							final String address = set.URL+"uploads/"+pressed;
							Log.d("url to download from", "123"+address+"123");
							task1.execute(address);
							finish();
						}
					})
					.setNegativeButton("No",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							// if this button is clicked, just close
							// the dialog box and do nothing
							dialog.cancel();
						}
					});

					// create alert dialog
					AlertDialog alertDialog = alertDialogBuilder.create();

					// show it
					alertDialog.show();
					
				}
			});
			}
	}
}
