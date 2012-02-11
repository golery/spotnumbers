package com.greensuisse.android.spotnumbers.controller;

import java.util.HashMap;
import java.util.List;

import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;

import com.greensuisse.android.spotnumbers.controller.Number.State;
import com.greensuisse.android.spotnumbers.view.BoardView;
import com.greensuisse.android.spotnumbers.view.Player;

public class GameController {
	private static final int MAX_NUMBER = 50;

	public static final String ALICE = "Alice";
	public static final String BOB = "Bob";
	public static final float NUMBER_MIN_DISTANCE_DP = BoardView.TEXT_SIZE_DP * 3 / 2;
	public static final float BOARD_PADDING_DP = NUMBER_MIN_DISTANCE_DP / 2;
	private static final float MAX_TOUCH_DISTANCE_DP = NUMBER_MIN_DISTANCE_DP;
	private static GameController instance = new GameController();
	private List<Number> numbers;
	private int currentNumberIndex;
	private HashMap<Player, Float> scores;
	private Runnable listener;
	private int boardWidth;
	private int boardHeight;
	private float dp;
	private final Handler handler = new Handler();
	private final Runnable timer = new Timer();
	private boolean advanced;
	private Vibrator vibrator;;

	public static GameController getInstance() {
		return instance;
	}

	public GameController() {
		handler.post(timer);
	}

	public void setMode(boolean advanced) {
		this.advanced = advanced;
	}

	public void setVibrator(Vibrator vibrator) {
		this.vibrator = vibrator;
	}

	public void initGame(int boardWidth, int boardHeight, float dp) {
		this.boardWidth = boardWidth;
		this.boardHeight = boardHeight;
		this.dp = dp;

		handler.removeCallbacks(timer);
		if (advanced) {
			handler.post(timer);
		}
		reset();
	}

	public List<Number> getNumbers() {
		return numbers;
	}

	/** @return true if a number is correctly clicked */
	public boolean clickNumber(Player player, float x, float y) {
		if (currentNumberIndex >= numbers.size())
			return false;

		Number number = numbers.get(currentNumberIndex);
		float distance = distance(number.getX(), number.getY(), x, y);
		if (distance > MAX_TOUCH_DISTANCE_DP * dp) {
			if (vibrator != null)
				vibrator.vibrate(30);
			return false;
		}
		number.setState(State.REMOVED);
		scores.put(player, scores.get(player) + 1);
		++currentNumberIndex;
		listener.run();
		return true;
	}

	private float distance(float x1, float y1, float x2, float y2) {
		return (float) (Math
				.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2)));
	}

	public float getScoreAsPercent(Player player) {
		float s = scores.get(player).floatValue();
		return s / numbers.size();
	}

	public void setChangeListener(Runnable listener) {
		this.listener = listener;
	}

	public class Timer implements Runnable {
		public void run() {
			Log.i("reset", "after delay");
			rotateNumbers();
			listener.run();
			handler.postDelayed(this, 50);
		}
	}

	public void reset() {
		numbers = NumberFactory.createNumbers(boardWidth, boardHeight,
				BOARD_PADDING_DP * dp, NUMBER_MIN_DISTANCE_DP * dp, MAX_NUMBER);
		currentNumberIndex = 0;
		scores = new HashMap<Player, Float>();
		scores.put(Player.ALICE, new Float(0.0));
		scores.put(Player.BOB, new Float(0.0));
		listener.run();
	}

	public void rotateNumbers() {
		if (numbers != null)
			for (Number number : numbers) {
				if (number.getState() != State.REMOVED)
					number.rotate();
			}
	}

	public enum WinnerEnum {
		ALICE, BOB, TIE
	}

	/** @returnn null if there is no winner */
	public WinnerEnum getWinner() {

		float scoreAlice = scores.get(Player.ALICE).floatValue();
		float scoreBob = scores.get(Player.BOB).floatValue();
		if (numbers.size() - scoreAlice - scoreBob > 0.5)
			return null;

		if (scoreAlice > scoreBob)
			return WinnerEnum.ALICE;
		if (scoreBob > scoreAlice)
			return WinnerEnum.BOB;
		return WinnerEnum.TIE;
	}
}
