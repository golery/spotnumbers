package com.greensuisse.android.spotnumbers.view;

import android.app.Activity;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.greensuisse.spotnumbers.R;

public class HelpActivity extends Activity {
	@Override
	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.help);

		String text = (String) getIntent().getExtras().get("text");
		TextView textView = (TextView) findViewById(R.id.helpTextView);
		textView.setText(Html.fromHtml(text));

		View okButton = findViewById(R.id.buttonHelpOk);
		okButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				finish();
			}
		});
	}
}
