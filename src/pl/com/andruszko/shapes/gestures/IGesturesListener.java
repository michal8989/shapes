package pl.com.andruszko.shapes.gestures;

import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
 
public interface IGesturesListener {

	public boolean onScale(ScaleGestureDetector detector);
	public boolean onDoubleTap(MotionEvent event);
	public void OnRotation(RotationGestureDetector rotationDetector);
	
}
