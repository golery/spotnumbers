package com.greensuisse.android.spotnumbers.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

import com.greensuisse.spotnumbers.R;

public class HomeActivity extends Activity {
	@Override
	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);

		View button;

		button = findViewById(R.id.button_introduction);
		button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(HomeActivity.this,
						HelpActivity.class);
				intent.putExtra("text", getString(R.string.introduction));
				startActivity(intent);
			}
		});

		button = findViewById(R.id.button_playNow);
		button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(HomeActivity.this,
						SpotNumbersActivity.class);
				intent.putExtra("advanced", false);
				startActivity(intent);
			}
		});

		button = findViewById(R.id.button_playAdvanced);
		button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(HomeActivity.this,
						SpotNumbersActivity.class);
				intent.putExtra("advanced", true);
				startActivity(intent);
			}
		});

		button = findViewById(R.id.button_exit);
		button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});

		button = findViewById(R.id.button_about);
		button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				AlertDialog alertDialog = new AlertDialog.Builder(
						HomeActivity.this).create();
				alertDialog.setTitle("About");
				alertDialog.setMessage(getString(R.string.about));
				alertDialog.show();
			}
		});
	}
}
