package com.example.quiz;

import com.example.quiz.MyDBAdapter;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Admin_chkq extends Activity {
	
	Context context=this;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin_chkq);
		Log.d("DEBUG", "Hi");
		
		Button sub=(Button) findViewById(R.id.button1);
		Button back=(Button) findViewById(R.id.button2);
		final TextView t1=(TextView) findViewById(R.id.textView2);
		
		final Integer qno=1;
		// TODO exception not handled
		sub.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
								
				//final Integer qno=Integer.parseInt(e1.toString());
				MyDBAdapter ad=new MyDBAdapter(context);
				
				Cursor c=ad.getQno(qno);
				
				String Qs="";
				Qs=Qs.concat(c.getString(0));
				Qs=Qs.concat(" ");
				Qs=Qs.concat(c.getString(1));
				Qs=Qs.concat("\n");
				Qs=Qs.concat(c.getString(2));
				Qs=Qs.concat(" ");
				Qs=Qs.concat(c.getString(3));
				Qs=Qs.concat(" ");
				Qs=Qs.concat(c.getString(4));
				Qs=Qs.concat(" ");
				Qs=Qs.concat(c.getString(5));
				Qs=Qs.concat(" ");
				Qs=Qs.concat(c.getString(6));
				
				t1.setText(Qs);
			}
		});
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(), Admin_base.class);
	        	startActivity(intent);
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.admin_chkq, menu);
		return true;
	}

}

