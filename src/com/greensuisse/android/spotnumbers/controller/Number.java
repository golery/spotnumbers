package com.greensuisse.android.spotnumbers.controller;

public class Number {
	public enum State {
		ACTIVE, REMOVED
	};

	private State state = State.ACTIVE;
	private final int value;
	private final float x, y;
	private float rotate;
	private final float rotateVelocity;

	public Number(int value, float x, float y, float rotate,
			float rotateVelocity) {
		this.value = value;
		this.x = x;
		this.y = y;
		this.rotate = rotate;
		this.rotateVelocity = rotateVelocity;
	}

	public int getValue() {
		return value;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getRotate() {
		return rotate;
	}

	public void setState(State state) {
		this.state = state;
	}

	public State getState() {
		return state;
	}

	public float getRotateVelocity() {
		return rotateVelocity;
	}

	public void rotate() {
		this.rotate = (rotate + rotateVelocity * 15) % 360;
	}
}
