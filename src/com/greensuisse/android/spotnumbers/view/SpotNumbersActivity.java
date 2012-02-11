package com.greensuisse.android.spotnumbers.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.View.OnClickListener;

import com.greensuisse.android.spotnumbers.controller.GameController;
import com.greensuisse.android.spotnumbers.controller.GameController.WinnerEnum;
import com.greensuisse.spotnumbers.R;

public class SpotNumbersActivity extends Activity {
	private View mainView;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		boolean advanced = getIntent().getExtras().getBoolean("advanced");
		GameController.getInstance().setMode(advanced);
		GameController.getInstance().setVibrator(
				(Vibrator) getSystemService(Context.VIBRATOR_SERVICE));

		mainView = findViewById(R.id.main);
		GameController.getInstance().setChangeListener(new Runnable() {
			public void run() {
				mainView.invalidate();
			}
		});
		View helpButton = findViewById(R.id.helpButton);
		helpButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(SpotNumbersActivity.this,
						HelpActivity.class);
				intent.putExtra("text", getString(R.string.help));
				startActivity(intent);
			}
		});
		View resetButton = findViewById(R.id.button_reset);
		resetButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				GameController.getInstance().reset();
				resetTimer();
			}
		});

		BoardView board;
		Runnable boardListener = new Runnable() {

			public void run() {
				WinnerEnum winner = GameController.getInstance().getWinner();
				if (winner != null) {
					showWinner(winner);
					resetTimer();
				}
			}
		};
		board = (BoardView) findViewById(R.id.boardAlice);
		board.setListener(boardListener);
		board = (BoardView) findViewById(R.id.boardBob);
		board.setListener(boardListener);
	}

	private void showWinner(WinnerEnum winner) {
		AlertDialog dialog = new AlertDialog.Builder(this).create();
		if (winner == WinnerEnum.ALICE || winner == WinnerEnum.BOB) {
			dialog.setTitle("Winner");
			dialog.setMessage(winner + "! Congratulation ! You are the winner");
		} else if (winner == WinnerEnum.TIE) {
			dialog.setTitle("Tie");
			dialog.setMessage("Play one more time to know who is the winner");
		}
		dialog.show();
		GameController.getInstance().reset();
	}

	@Override
	protected void onResume() {
		super.onResume();
		resetTimer();
	}

	private void resetTimer() {
		// Chronometer chrono = (Chronometer) findViewById(R.id.chrono);
		// if (chrono != null) {
		// chrono.setBase(SystemClock.elapsedRealtime());
		// chrono.start();
		// }
	}

	// @Override
	// protected void onStop() {
	// super.onStop();
	// Chronometer chrono = (Chronometer) findViewById(R.id.chrono);
	// if (chrono != null)
	// chrono.stop();
	// }
}