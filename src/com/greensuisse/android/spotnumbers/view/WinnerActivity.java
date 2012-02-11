package com.greensuisse.android.spotnumbers.view;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.greensuisse.spotnumbers.R;

public class WinnerActivity extends Activity {
	@Override
	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.winner);

		TextView text = (TextView) findViewById(R.id.text);

		View okButton = findViewById(R.id.ok);
		okButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				finish();
			}
		});
	}
}
