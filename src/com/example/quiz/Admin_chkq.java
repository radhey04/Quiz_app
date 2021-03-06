package com.example.quiz;

import com.example.quiz.MyDBAdapter;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Admin_chkq extends Activity {
	
	Context context=this;
	MyDBAdapter ad;
	Button cimg;
	Integer qno;
	public Integer updatespace(EditText e1,TextView t1)
	{
		Log.d("Debug_admin_chkq","Updating the space");
		String e=e1.getText().toString();
		if(e.equals(""))
		{
			Log.d("Debug_admin_chkq","Empty String");
			return 0;
		}
		Integer qno=Integer.parseInt(e);
		Log.d("Debug_admin_chkq","Asked to fetch =>"+qno);
		Cursor c=ad.getQno(qno);
		
		if(c==null)
		{
			Toast.makeText(getApplicationContext(), "Invalid question no", Toast.LENGTH_SHORT).show();
			Log.d("Debug_admin_chkq","Invalid qno");			
		}
		else
		{
			Log.d("Debug_admin_chkq","Valid qno");
			String Qs="";
			Integer qnotemp=c.getInt(1);
			Qs=Qs.concat(qnotemp.toString());
			Qs=Qs.concat(". ");
			Qs=Qs.concat(c.getString(2));
			Qs=Qs.concat("\n\n");
			Qs=Qs.concat("A -> "+c.getString(3));
			Qs=Qs.concat("\n");
			Qs=Qs.concat("B -> "+c.getString(4));
			Qs=Qs.concat("\n");
			Qs=Qs.concat("C -> "+c.getString(5));
			Qs=Qs.concat("\n");
			Qs=Qs.concat("D -> "+c.getString(6));
			Qs=Qs.concat("\n\n Correct Answer => ");
			Qs=Qs.concat(c.getString(7));
			Integer imagethere=c.getInt(8);
		    if(imagethere==0)
		    {
		    	Qs=Qs.concat("\n\n The question has no image");
		    	cimg.setVisibility(4);	//Invisible
		    }
		    else
		    {
		    	Qs=Qs.concat("\n\n The question has an image");
		    	cimg.setVisibility(0);	//Visible
			}
		    t1.setText(Qs);
			Log.d("Debug_admin_chkq","Screen updated");
			c.close();			//Should close only if something was open
		}
		return qno;
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin_chkq);
		
		ad=new MyDBAdapter(context);
		Button sub=(Button) findViewById(R.id.button1);
		Button back=(Button) findViewById(R.id.button2);
		Button del=(Button) findViewById(R.id.button3);
		Button editq=(Button) findViewById(R.id.button4);
		cimg=(Button) findViewById(R.id.button5);
		cimg.setVisibility(4);	//Invisible
	    
		final TextView t1=(TextView) findViewById(R.id.textView2);
		final EditText e1=(EditText) findViewById(R.id.editText1);
		final AlertDialog.Builder builder = new AlertDialog.Builder(context);
    	
		
		sub.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				qno=updatespace(e1,t1);
				if(qno==0)
				{
					Toast.makeText(getApplicationContext(), "Invalid Question no.", Toast.LENGTH_SHORT).show();					
				}
			}
		});
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), Admin_base.class);
	        	startActivity(intent);
	        	finish();
			}
		});
		del.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				qno=updatespace(e1,t1);
				if((qno<=ad.N)&&(qno>0))
				{
					Log.d("Debug_admin_chkq","Prepare the dialog");
					builder.setTitle("Delete the question");
					builder.setMessage("Are you sure?");
			    	builder.setIcon(android.R.drawable.ic_dialog_alert);
			    	builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			    	    public void onClick(DialogInterface dialog, int which) {			      	
			    	    	//Yes button clicked, do something
			    	    	Log.d("Debug_admin_chkq","Prepare the dialog");
							Toast.makeText(getApplicationContext(), "Deleted the question", 
			                               Toast.LENGTH_SHORT).show();
							Log.d("Debug_admin_chkq","Prepare the dialog");
							ad.deleteEntry(qno);
							t1.setText(R.string.admin_chkq_hint);
							e1.setText("");
			    	    }
			    	});
			    	builder.setNegativeButton("No", null);
			    	Log.d("Debug_admin_chkq","Show the dialog");
					builder.show();
				}
				else
				{
					Toast.makeText(getApplicationContext(), "Invalid Question no.", Toast.LENGTH_SHORT).show();					
				}
			}
		});
		
		editq.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				qno=updatespace(e1,t1);
				if((qno<=ad.N)&&(qno>0))
				{
					Intent i=new Intent(context,EditQActivity.class);
					Bundle b=new Bundle();
					b.putInt("Qno", qno);
					i.putExtras(b);
					startActivity(i);
					finish();
				}
				else
				{
					Toast.makeText(getApplicationContext(), "Invalid Question no.", Toast.LENGTH_SHORT).show();					
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
		        Log.d("Debug_Admin_chkq", "Fetching the image @ "+qno);
		        //Add the bundle to the intent.
		        intent.putExtras(b);
		        //Toast.makeText(getApplicationContext(), "Done.", Toast.LENGTH_SHORT).show();
		        //start the DisplayActivity
		        startActivity(intent);
			}
		});
		
	}

}

