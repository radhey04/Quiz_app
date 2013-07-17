package com.example.quiz;

import com.example.quiz.MyDBAdapter;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Admin_review extends Activity {

	Context context = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin_review);
		
		final MyDBAdapter ad = new MyDBAdapter(context);
		Button editqb=(Button) findViewById(R.id.button1);
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
		c1.close();
		
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), Admin_base.class);
				startActivity(intent);
				finish();
			}
		});
		
		editqb.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), EditQBActivity.class);
				startActivity(intent);
				finish();
			}
		});
	}
}

