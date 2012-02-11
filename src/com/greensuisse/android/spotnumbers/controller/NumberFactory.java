package com.greensuisse.android.spotnumbers.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import android.util.Log;

public class NumberFactory {
	private static final int MAX_GEN = 1000;

	public static List<Number> createNumbers(int width, int height,
			float margin, float minDistance, int maxNumber) {
		if (width == 0 || height == 0) {
			Log.e("createNumbers", String.format(
					"Skip generate numbers.Width=%d, height=%d", width, height));
			return Collections.emptyList();
		}
		System.out.println("Init");
		Random rand = new Random();
		int nNumber = maxNumber;
		ArrayList<Number> numbers = new ArrayList<Number>();
		for (int i = 0; i < nNumber; i++) {

			int nGen = 0;
			float x, y;
			x = y = 0;
			boolean found = false;
			while (!found && nGen < MAX_GEN) {
				// generate new point
				x = Math.abs(rand.nextInt()) % width;
				y = Math.abs(rand.nextInt()) % height;

				// check
				found = true;
				if (x < margin || y < margin || x + margin >= width
						| y + margin >= height)
					found = false;
				else {
					// check previous points
					if (i > 0) {
						for (int j = 0; j < i && found; j++) {
							Number old = numbers.get(j);
							float distance = (float) Math.sqrt((Math.pow(
									old.getX() - x, 2.0) + Math.pow(old.getY()
									- y, 2.0)));
							if (distance < minDistance) {
								found = false;
							}
						}
					}
				}
				++nGen;
			}

			if (!found) {
				Log.i("Board.init", "Cannot allocate more number");
				break;
			} else
				Log.i("Board.init", "Found number after nGen=" + nGen);

			float rotate = rand.nextFloat() * 360;
			// it's very confuse if we rotate 6 and 9
			if ((i + 1) % 6 == 0 || (i + 1) % 9 == 0)
				rotate = 0;
			float rotateVelocity = rand.nextBoolean() ? 1.0f : -1.0f;
			Number number = new Number(i + 1, x, y, rotate, rotateVelocity);
			numbers.add(number);
		}
		System.out.println("Done Init. Nb of Number:" + numbers.size());
		return numbers;
	}
}
