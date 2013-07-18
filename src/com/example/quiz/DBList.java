package com.example.quiz;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;


import android.os.AsyncTask;
import android.os.Bundle;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
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
	String pressed;
	DBhandling db=new DBhandling();
	SettingsDBAdapter set;
	
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
		String url = set.URL+"app/uploads";
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
			        
			        FileOutputStream f = new FileOutputStream(new File(root, pressed));

			        InputStream in = c.getInputStream();

			        byte[] buffer = new byte[1024];
			        int len1 = 0;
			        while ((len1 = in.read(buffer)) > 0) {
			            //publishProgress("" + (int)((total*100)/lenghtOfFile));
			            f.write(buffer, 0, len1);
			        }
			        f.close();
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
				DefaultHttpClient client = new DefaultHttpClient();
				HttpGet httpGet = new HttpGet(url);
				try {
					HttpResponse execute = client.execute(httpGet);
					InputStream content = execute.getEntity().getContent();
					Log.d("DEBUG", "trycatch block in dwp");
					BufferedReader buffer = new BufferedReader(
							new InputStreamReader(content));
					String s = "";
					String delim = ">";
					while ((s = buffer.readLine()) != null) {
						if(s.contains(".nab")){
							String name = s.substring(96);
							name.trim();
							String token[] =name.split(delim);
							Log.d("DEBUG", token[0].replace('"', ' '));
							response += token[0].replace('"', ' ');
							response += "#";
						}
					}
					Log.d("DEBUG", response);
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
			
			i=1;
			Log.d("DEBUG", result);
			String[] FRUITS = result.split(" #");
			
			setListAdapter(new ArrayAdapter<String>(context, R.layout.activity_dblist,FRUITS));
			ListView listView = getListView();
			listView.setTextFilterEnabled(true);
			
			registerForContextMenu(getListView());  
			listView.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// When clicked, show a toast with the TextView text

					pressed = ((TextView) view).getText().toString();


					AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
							context);
					// set title
					alertDialogBuilder.setTitle("Your Title");

					// set dialog message
					alertDialogBuilder
					.setMessage("Do you want to download "+pressed+"?")
					.setCancelable(false)
					.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							// if this button is clicked, close
							// current activity
							//MainActivity.this.finish();
							Toast.makeText(getApplicationContext(),"You pressed yes", Toast.LENGTH_SHORT).show();
							DownloadWebPage task1 = new DownloadWebPage();
							
							final String address = set.URL+"app/uploads/"+pressed;
							Log.d("url to download from", "123"+address+"123");
							task1.execute(address);
						}
					})
					.setNegativeButton("No",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							// if this button is clicked, just close
							// the dialog box and do nothing
							Toast.makeText(getApplicationContext(),"You pressed no", Toast.LENGTH_SHORT).show();
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
