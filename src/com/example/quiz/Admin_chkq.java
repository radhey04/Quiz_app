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
import android.widget.Toast;

public class Admin_chkq extends Activity {
	
	Context context=this;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin_chkq);
		
		Button sub=(Button) findViewById(R.id.button1);
		Button back=(Button) findViewById(R.id.button2);
		final TextView t1=(TextView) findViewById(R.id.textView2);
		final EditText e1=(EditText) findViewById(R.id.editText1);
		
		
		// TODO exception not handled
		sub.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
								
				MyDBAdapter ad=new MyDBAdapter(context);
				
				Integer qno=Integer.parseInt(e1.getText().toString());
				
				Cursor c=ad.getQno(qno);
				
				if(c==null)
				{
					Toast.makeText(getApplicationContext(), "Invalid question no", Toast.LENGTH_SHORT).show();
				}
				else
				{
					String Qs="";
					Qs=Qs.concat(c.getString(0));
					Qs=Qs.concat(" ");
					Qs=Qs.concat(c.getString(1));
					Qs=Qs.concat("\n");
					Qs=Qs.concat(c.getString(2));
					Qs=Qs.concat("\n");
					Qs=Qs.concat(c.getString(3));
					Qs=Qs.concat("\n");
					Qs=Qs.concat(c.getString(4));
					Qs=Qs.concat("\n");
					Qs=Qs.concat(c.getString(5));
					Qs=Qs.concat("\n Correct Answer => ");
					Qs=Qs.concat(c.getString(6));
					
					t1.setText(Qs);
				}
			}
		});
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(), Admin_base.class);
	        	startActivity(intent);
	        	finish();
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

