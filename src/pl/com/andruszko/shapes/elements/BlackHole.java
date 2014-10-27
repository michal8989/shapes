package pl.com.andruszko.shapes.elements;

import android.graphics.RectF;

public class BlackHole extends RectF {
	private int mSize = 90;
	
	public BlackHole(int x, int y) {
		set(x, y, x + mSize, y + mSize);
	}
	
	public void setPos(int x, int y) {
		set(x, y, x + mSize, y + mSize);
	}
	
	public int getSize() {
		return mSize;
	}
	
}
