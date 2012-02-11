package com.greensuisse.android.spotnumbers.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.greensuisse.android.spotnumbers.controller.GameController;
import com.greensuisse.spotnumbers.R;

public class TwoBoardView extends LinearLayout {
	private final float dp = getResources().getDisplayMetrics().density;

	public TwoBoardView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		BoardView aliceView = (BoardView) findViewById(R.id.boardAlice);
		GameController.getInstance().initGame(aliceView.getMeasuredWidth(),
				aliceView.getMeasuredHeight(), dp);
	}

}
