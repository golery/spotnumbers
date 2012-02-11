package com.greensuisse.android.spotnumbers.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Shader.TileMode;
import android.util.AttributeSet;
import android.view.View;

import com.greensuisse.android.spotnumbers.controller.GameController;

public class ScoreView extends View {

	private final Paint paint;
	private final float dp = getResources().getDisplayMetrics().density;

	public ScoreView(Context context, AttributeSet attrs) {
		super(context, attrs);

		paint = new Paint();
		paint.setTextAlign(Align.LEFT);
		paint.setTextSize(20);
		paint.setAntiAlias(true);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		int w = this.getWidth();
		int h = this.getHeight();
		float line1 = h / 2.0f - 1;
		float line2 = h - 1;

		paint.setColor(Color.WHITE);
		canvas.drawText(GameController.ALICE, 0, line1, paint);
		canvas.drawText(GameController.BOB, 0, line2, paint);

		float x = Math.max(paint.measureText(GameController.ALICE + "  "),
				paint.measureText(GameController.BOB + "  "));

		canvas.save();
		canvas.translate(x, 0);

		paintScores(canvas, (int) (w - x - 1), line1, line2);

	}

	private void paintScores(Canvas canvas, int barWidth, float line1,
			float line2) {
		float barHeight = 10 * dp;

		GameController gameController = GameController.getInstance();

		Paint paint = new Paint();
		paint.setStyle(Style.STROKE);
		paint.setColor(Color.DKGRAY);
		canvas.drawRect(0, line1 - barHeight, barWidth, line1, paint);
		canvas.drawRect(0, line2 - barHeight, barWidth, line2, paint);

		float percent;
		Paint paintGradient = new Paint();
		paintGradient.setShader(new LinearGradient(0, 0, 100, 100, Color.RED,
				Color.BLUE, TileMode.MIRROR));
		percent = gameController.getScoreAsPercent(Player.ALICE);
		canvas.drawRect(0, line1 - barHeight, barWidth * percent, line1,
				paintGradient);

		percent = gameController.getScoreAsPercent(Player.BOB);
		paintGradient.setShader(new LinearGradient(0, 0, 100, 100, Color.BLUE,
				Color.GREEN, TileMode.MIRROR));
		canvas.drawRect(0, line2 - barHeight, barWidth * percent, line2,
				paintGradient);
	}
}
