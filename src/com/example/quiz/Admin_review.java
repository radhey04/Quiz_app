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
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Admin_review extends Activity {

	Context context = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin_review);
		
		final MyDBAdapter ad = new MyDBAdapter(context);
		final AlertDialog.Builder builder = new AlertDialog.Builder(context);
    	
		Button delall=(Button) findViewById(R.id.button1);
		Button back=(Button) findViewById(R.id.button2);
		final TextView t=(TextView) findViewById(R.id.textView2);
		
		Cursor c1 = ad.getAllQs();
		String Qs="Question Paper => ";
		c1.moveToNext();
		Qs = Qs.concat(c1.getString(2));
		Qs = Qs.concat("\n");
	
		while(c1.moveToNext())
		{
			Qs = Qs.concat(c1.getString(1));	// Can fetch int as string.
			Qs = Qs.concat(". ");
			Qs = Qs.concat(c1.getString(2));
			Qs = Qs.concat("\n");
		}
		t.setText(Qs);
		
		delall.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.d("Debug_admin_review","Deleting the paper");
				builder.setTitle("Delete the entire question paper?");
				builder.setMessage("Are you sure? You will lose the settings too.");
		    	builder.setIcon(android.R.drawable.ic_dialog_alert);
		    	builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		    	    public void onClick(DialogInterface dialog, int which) {			      	
		    	    	//Yes button clicked, do something
		    	    	ad.deleteall();
						Toast.makeText(context,"Deleted the paper", Toast.LENGTH_SHORT).show();
						t.setText("");
						Log.d("Debug_admin_review","Deleted the paper");
						finish();			// No point staying back here. :)
		    	    }
		    	});
		    	builder.setNegativeButton("No", null);
		    	builder.show();		    	
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
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.admin_review, menu);
		return true;
	}

}

