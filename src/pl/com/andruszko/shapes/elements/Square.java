package pl.com.andruszko.shapes.elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import pl.com.andruszko.shapes.MainActivity;
import pl.com.andruszko.shapes.animations.BaseAnimation;
import android.content.Context;
import android.graphics.Color;
import android.graphics.RectF;

public class Square extends RectF {
	private static final int DEFAULT_SIZE = 60;
	private int mColor = Color.RED;
	private float mRotation = 0;
	private int mOpacity = 100;
	private int mSize = DEFAULT_SIZE;
	private boolean mIsAnimating = false;
	private int randomRotationDirection = 0;
	private List<BaseAnimation> squareAnimations = new ArrayList<BaseAnimation>();
	
	public Square() {
        	super();
        	generateRotationDirection();
    	}
    
	public Square(int x, int y, int size) {
    		mSize = size;
    		super.set(x, y, x + size, y + size);
    		generateRotationDirection();
    	}

	public Square(int x, int y) {
    		super.set(x, y, x + mSize, y + mSize);
    		generateRotationDirection();
	}    
    
	private void generateRotationDirection() {
    		Random rnd = new Random();
    		randomRotationDirection = rnd.nextInt(2);
    	}
    
	public int getRotationDirection() {
    		return randomRotationDirection;
	}
    
	public void randomizeColor() {
    		Random rnd = new Random();
    		int color = Color.rgb((rnd.nextInt(256 - 10) + 10), (rnd.nextInt(256 - 10) + 10), (rnd.nextInt(256 - 10) + 10));
		this.setColor(color);   	
    	}
    
	public void randomizeParams(int canvasWidth, int canvasHeight) {
    		Random rnd = new Random();
		int x = (rnd.nextInt((canvasWidth - (mSize)) - 10) + 10);
		int y = (rnd.nextInt((canvasHeight - (mSize)) - 10) + 10);
		super.set(x, y, x + mSize, y + mSize);
		randomizeColor();
    	}
    
    	public int getSize() {
    		return this.mSize;
    	}
    
    	public void setSize(int size) {
    		mSize = size;
    		super.set(left, top, left + size, top + size);
	}
    
	public void setColor(String color) {
		this.mColor = Color.parseColor(color); 
	}
	
	public void setColor(int color) {
		this.mColor = color; 
	}
	
	public int getColor() {
		return this.mColor;
	}
    
	public void setRotation(float rotation) {
    		this.mRotation = rotation;
    	}
    
    	public float getRotation() {
    		return this.mRotation;
    	}
    
    	public void setAnimation(BaseAnimation animation){
    		squareAnimations.add(animation);
    	}
    
    	public void removeAnimation(BaseAnimation animation) {
    		squareAnimations.remove(animation);
    	}
    
    	public int getOpacity() {
    		return mOpacity;
    	}
    
    	public void setOpacity(int opacity) {
    		int tmpColor = this.getColor();
    		this.setColor(Color.argb(opacity, Color.red(tmpColor), Color.green(tmpColor), Color.blue(tmpColor)));
    		mOpacity = opacity;
    	}
    
    	public void invalidateAnimations(Context context) {
    		for(int i = 0; i < squareAnimations.size(); i++) {
    			BaseAnimation sqa = squareAnimations.get(i);
    			sqa.invalidate(this);
    			if(((MainActivity) context).getDrawView() != null) {
    				((MainActivity) context).getDrawView().invalidate();
    			}
    		}
    	}
     
    	public boolean isAnimating() {
    		return this.mIsAnimating;
    	}

}
