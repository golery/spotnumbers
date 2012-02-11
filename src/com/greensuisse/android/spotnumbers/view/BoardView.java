package com.greensuisse.android.spotnumbers.view;

import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.greensuisse.android.spotnumbers.controller.GameController;
import com.greensuisse.android.spotnumbers.controller.Number;
import com.greensuisse.android.spotnumbers.controller.Number.State;
import com.greensuisse.spotnumbers.R;

public class BoardView extends View {
	public static final int TEXT_SIZE_DP = 25;

	private final float dp = getResources().getDisplayMetrics().density;
	private final float sp = getResources().getDisplayMetrics().scaledDensity;
	private String userName;
	private Player player;

	private final Context context;

	private Runnable boardListener;

	public BoardView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		if (this.getId() == R.id.boardAlice) {
			userName = GameController.ALICE;
			player = Player.ALICE;
		} else {
			userName = GameController.BOB;
			player = Player.BOB;
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Paint paintCircle = new Paint();
		paintCircle.setStyle(Paint.Style.STROKE);

		Paint paintText = new Paint();
		paintText.setTextSize(TEXT_SIZE_DP * dp / sp);
		paintText.setTextAlign(Align.CENTER);
		float textHeight = paintText.getFontMetrics().ascent;

		List<Number> numbers = GameController.getInstance().getNumbers();
		if (numbers == null)
			return;

		for (Number number : numbers) {
			int color = Color.WHITE;
			if (number.getState() == State.REMOVED) {
				color = 0xFF89A7C1;
			}
			paintText.setColor(color);
			paintCircle.setColor(color);

			float x = number.getX();
			float y = number.getY();
			float ty = y - textHeight / 2;
			String text = "" + number.getValue();

			canvas.drawCircle(x, y, GameController.NUMBER_MIN_DISTANCE_DP / 2
					* dp, paintCircle);

			canvas.save();
			canvas.rotate(number.getRotate(), x, y);
			canvas.drawText(text, x, ty, paintText);
			canvas.restore();
		}
		paintUsername(canvas);
	}

	private void paintUsername(Canvas canvas) {
		TextPaint paint = new TextPaint();
		paint.setTextSize(20);
		paint.setColor(Color.WHITE);
		paint.setTextAlign(Align.LEFT);
		paint.setAntiAlias(true);
		paint.setColor(Color.DKGRAY);
		canvas.drawText(userName, 5 * dp, -paint.getFontMetrics().top * dp,
				paint);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		GameController gameController = GameController.getInstance();
		List<Number> numbers = gameController.getNumbers();
		if (numbers == null)
			return super.onTouchEvent(event);

		if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
			float x = event.getX();
			float y = event.getY();

			if (gameController.clickNumber(player, x, y)) {
				if (getParent() instanceof TwoBoardView) {
					((TwoBoardView) getParent()).invalidate();
				}
				if (boardListener != null)
					boardListener.run();
			}
		}
		return super.onTouchEvent(event);
	}

	public void setListener(Runnable boardListener) {
		this.boardListener = boardListener;
	}
}
