package com.example.quiz;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class User_score extends Activity {

	Context context=this;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

// Getting the window sizes
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int fonts = 40;
        
        TableRow.LayoutParams params = new TableRow.LayoutParams(width,fonts);
        TableLayout table = new TableLayout(this);
        
        //TableLayout table = (TableLayout) findViewById(R.id.table1);
        
        TableRow row = new TableRow(this);
        
        TextView text = new TextView(this);
        params.gravity=Gravity.CENTER;
        
        text.setLayoutParams(params);
        text.setText("Your Marksheet (Most Recent on top)");
        Log.d("Debug","Tablelayout1");
        row.addView(text);
        Log.d("Debug","Tablelayout11");
        table.addView(row);
        Log.d("Debug","Tablelayout2");

        fonts = 50;
		TableRow.LayoutParams params0 = new TableRow.LayoutParams(0,fonts,.1f);
        TableRow.LayoutParams params1 = new TableRow.LayoutParams(0,fonts,.35f);
        TableRow.LayoutParams params2 = new TableRow.LayoutParams(0,fonts,.27f);
		TableRow.LayoutParams params3 = new TableRow.LayoutParams(0,fonts,.14f);
		TableRow.LayoutParams params4 = new TableRow.LayoutParams(0,fonts,.14f);
		// The table headers
		TableRow rowi = new TableRow(this);
        
        TextView text0 = new TextView(this);
        text0.setText("#");
        params0.gravity=Gravity.CENTER;
        text0.setLayoutParams(params0);
        
        TextView text1 = new TextView(this);
        text1.setText("Quiz Name");
        params1.gravity=Gravity.CENTER;
        text1.setLayoutParams(params1);
        
        TextView text2 = new TextView(this);
        text2.setText("Date taken");
        params2.gravity=Gravity.CENTER;
        text2.setLayoutParams(params2);
        
        TextView text3 = new TextView(this);
        text3.setText("Score");
        params3.gravity=Gravity.CENTER;
        text3.setLayoutParams(params3);
        
        
        TextView text4 = new TextView(this);
        text4.setText("Out of");
        params4.gravity=Gravity.CENTER;
        text4.setLayoutParams(params4);
        
        rowi.addView(text0);
        rowi.addView(text1);
        rowi.addView(text2);
        rowi.addView(text3);
        rowi.addView(text4);
        table.addView(rowi);
		
        final markDBAdapter md=new markDBAdapter(getApplicationContext());
        Cursor c=md.getAllperf();    
        c.moveToLast();
        Integer slno;
        do
        {
        	rowi = new TableRow(this);
            
            text0 = new TextView(this);
            slno=md.N-Integer.parseInt(c.getString(1))+1;
            text0.setText(slno.toString());
            params0.gravity=Gravity.CENTER;
            text0.setLayoutParams(params0);
            
            text1 = new TextView(this);
            text1.setText(c.getString(2));
            params1.gravity=Gravity.CENTER;
            text1.setLayoutParams(params1);
            
            text2 = new TextView(this);
            text2.setText(c.getString(3));
            params2.gravity=Gravity.CENTER;
            text2.setLayoutParams(params2);
            
            text3 = new TextView(this);
            text3.setText(c.getString(4));
            params3.gravity=Gravity.CENTER;
            text3.setLayoutParams(params3);
            
            text4 = new TextView(this);
            text4.setText(c.getString(5));
            params4.gravity=Gravity.CENTER;
            text4.setLayoutParams(params4);
            
            rowi.addView(text0);
            rowi.addView(text1);
            rowi.addView(text2);
            rowi.addView(text3);
            rowi.addView(text4);
            table.addView(rowi);
        }while(c.moveToPrevious());
        c.close();
		
        rowi = new TableRow(this);

        Button clr= new Button(context);
        fonts=80;
        TableRow.LayoutParams paramsb=new TableRow.LayoutParams(200,fonts);
        paramsb.gravity=Gravity.CENTER;
        clr.setLayoutParams(paramsb);
        clr.setText("Clear All");
        rowi.addView(clr);
        table.addView(rowi);
        //setContentView(table);
        
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
    	
        clr.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.d("Debug_admin_review","Deleting the records");
				builder.setTitle("Remove all scores?");
				builder.setMessage("Are you sure?");
		    	builder.setIcon(android.R.drawable.ic_dialog_alert);
		    	builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		    	    public void onClick(DialogInterface dialog, int which) {			      	
		    	    	//Yes button clicked, do something	    	
		    			md.dropsheet();	
						Toast.makeText(context,"Deleted the records", Toast.LENGTH_SHORT).show();
						Log.d("Debug_admin_review","Deleted the paper");
						finish();			// No point staying back here. :)
		    	    }
		    	});
		    	builder.setNegativeButton("No", null);
		    	builder.show();				
			}
		});
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user_score, menu);
		return true;
	}

}
