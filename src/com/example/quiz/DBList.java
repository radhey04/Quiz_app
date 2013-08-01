package com.example.quiz;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
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
import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
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
	ListView listview;
	public static final int progress_bar_type = 0; 
	private ProgressDialog pDialog;
	
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
		Toast.makeText(context, "Connecting to the server. Timeout set for 20 seconds", Toast.LENGTH_SHORT).show();
		listview = getListView();
		listview.setTextFilterEnabled(true);
		task.execute(url);
	}
	protected Dialog onCreateDialog(int id) {
		switch (id) {
			case progress_bar_type: // we set this to 0
				pDialog = new ProgressDialog(this);
				pDialog.setMessage("Downloading file. Please wait...");
				pDialog.setIndeterminate(false);
				pDialog.setMax(100);
				pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
				pDialog.setCancelable(true);
				pDialog.show();
				return pDialog;
			default:
				return null;
		}
	}
	private class DownloadWebPage extends AsyncTask<String, String, String> {
		@Override
		 protected void onPreExecute() {
		  super.onPreExecute();
		  showDialog(progress_bar_type);
		 }
		@Override
		protected String doInBackground(String... urls) {
			Log.d("DEBUG", "Inside dwp");
			String response = "";
			int count;
			for (String url : urls) {
				try {
					String path = db.chkdir();
					File root = new File(path);
			        Log.d("DEBUG", "ROOT"+root);
			        URL u = new URL(url);

			        URLConnection conection = u.openConnection();
		            conection.connect();
		            // getting file length
		            int lenghtOfFile = conection.getContentLength();
		            Log.d("DEBUG", ""+lenghtOfFile);
		            // input stream to read file - with 8k buffer
		            InputStream input = new BufferedInputStream(u.openStream(), 8192);
		            
		            // Output stream to write file
		            OutputStream output = new FileOutputStream(new File(root, "Quiz.nab"));
		            
		            byte data[] = new byte[1024];
		 
		            long total = 0;
		 
		            while ((count = input.read(data)) != -1) {
		                total += count;
		                // publishing the progress....
		                // After this onProgressUpdate will be called
		                Log.d("DEBUG", ""+(int)((total*100)/lenghtOfFile));
		                publishProgress(""+(int)((total*100)/lenghtOfFile));
		                // writing data to file
		                output.write(data, 0, count);
		            }
		 
		            // flushing output
		            output.flush();
		 
		            // closing streams
		            output.close();
		            input.close();
			        /*
			        HttpURLConnection c = (HttpURLConnection) u.openConnection();
			        c.setRequestMethod("GET");
			        c.setDoOutput(true);
			        c.connect();
			        int lenghtOfFile = c.getContentLength();
			        FileOutputStream f = new FileOutputStream(new File(root, "Quiz.nab"));

			        InputStream in = c.getInputStream();
			        long total = 0;
			        byte[] buffer = new byte[1024];
			        int len1 = 0;
			        while ((len1 = in.read(buffer)) > 0) {
			            total+=len1;
			        	publishProgress("" + (int)((total*100)/lenghtOfFile));
			            f.write(buffer, 0, len1);
			        }
			        f.close();*/
			        Log.d("DEBUG", "123Test123");
			        Boolean suc=db.importDB("Quiz",path+"/Quiz.nab");
			        MyDBAdapter ad=new MyDBAdapter(context);
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
										dismissDialog(progress_bar_type);
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
		protected void onProgressUpdate(String... progress) {
			// setting progress percentage
			Log.d("Debug", "Done:"+progress[0]);
			if(progress[0] != "0");
			{
				pDialog.setProgress(Integer.parseInt(progress[0]));
			}
					
		//pDialog.setProgress(Integer.parseInt(progress[0]));
		
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
				int timeoutConnection = 20000;
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
				Toast.makeText(context,  "Connection could not be established" , Toast.LENGTH_LONG).show();
				finish();
			}
			else if(result.equals(""))
			{
				Toast.makeText(context, "Wrong server", Toast.LENGTH_LONG).show();
				finish();
			}
			
			String[] FRUITS = result.split("#");
			
			setListAdapter(new ArrayAdapter<String>(context, R.layout.activity_dblist,FRUITS));
			//ListView listView = getListView();
					
			registerForContextMenu(getListView());  
			listview.setOnItemClickListener(new OnItemClickListener() {
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
							//finish();
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
