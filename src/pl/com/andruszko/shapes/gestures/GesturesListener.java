package pl.com.andruszko.shapes.gestures;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.OnScaleGestureListener;
import android.view.View;

public class GesturesListener implements View.OnTouchListener, OnGestureListener, OnScaleGestureListener, IGesturesListener {

	private GestureDetectorCompat mDetector; 
	private RotationGestureDetector mRotationDetector;
	private ScaleGestureDetector mScaleDetector;
	private IGesturesListener mListener;
	
	public void setListener(IGesturesListener listener) {
		if(listener != null) {
			mListener = listener;
		}
	}
	
	public GesturesListener(Context context) {
		mDetector = new GestureDetectorCompat(context, new GestureListener());
		mRotationDetector = new RotationGestureDetector(this);
		mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
	}
	
	public boolean onTouchEvent(MotionEvent event){
    		mScaleDetector.onTouchEvent(event);
		mDetector.onTouchEvent(event);
		mRotationDetector.onTouchEvent(event);
		return true;
    	}
		
	class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
		@Override
		public boolean onScale(ScaleGestureDetector detector) {
	        	if(mListener != null) {
	    			mListener.onScale(detector);
	    		}
			return true;
		}
	}
    
    	class GestureListener extends GestureDetector.SimpleOnGestureListener {
        	@Override
		public boolean onDoubleTap(MotionEvent event) {        
	        	if(mListener != null) {
	    			mListener.onDoubleTap(event);
	    		}
        		return super.onDoubleTap(event);
		}
    	}

	@Override
	public boolean onDoubleTap(MotionEvent event) {
		if(mListener != null) {
			mListener.onDoubleTap(event);
		}
		return false;
	}

	@Override
	public void OnRotation(RotationGestureDetector rotationDetector) {
		if(mListener != null) {
			mListener.OnRotation(rotationDetector);
		}
	}

	@Override
	public boolean onScale(ScaleGestureDetector detector) {
		return false;
	}

	@Override
	public boolean onScaleBegin(ScaleGestureDetector detector) {
		return false;
	}

	@Override
	public void onScaleEnd(ScaleGestureDetector detector) {
	}

	@Override
	public boolean onDown(MotionEvent e) {
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		return false;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		return false;
	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		return false;
	}    
}
