package pl.com.andruszko.shapes;

import pl.com.andruszko.shapes.elements.BlackHole;
import pl.com.andruszko.shapes.elements.Square;
import pl.com.andruszko.shapes.gestures.GesturesListener;
import pl.com.andruszko.shapes.gestures.IGesturesListener;
import pl.com.andruszko.shapes.gestures.RotationGestureDetector;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewTreeObserver;

public class DrawView extends View implements IGesturesListener {

	Paint paint = new Paint();
	private GesturesListener gesturesListener; 
	private ShapesManager shapesManager;
	int mDragSquareOffsetX = 0;
	int mDragSquareOffsetY = 0;	
	float mScaleFactor;
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		if(shapesManager != null) {
	    		shapesManager.setCanavasDimmensions(w,h);
	    		shapesManager.updateBlackHolePos();
	    	}
	}
	
	public DrawView(final Context context) {
        	super(context);
        	init(context);
	}
    
	public DrawView(Context context, AttributeSet attrs) {
    		super(context, attrs);
    		init(context);
	}
    
	public DrawView(Context context, AttributeSet attrs, int defStyleAttr) {
    		super(context, attrs, defStyleAttr);
    		init(context);
	}

    @SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	public static void removeOnGlobalLayoutListener(View v, ViewTreeObserver.OnGlobalLayoutListener listener){
        	if(Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
        		v.getViewTreeObserver().removeGlobalOnLayoutListener(listener);
        	} else {
        		v.getViewTreeObserver().removeOnGlobalLayoutListener(listener);
        	}
    	}
    
	private void init(final Context context) {
	    	final DrawView that = this;
	    	gesturesListener = new GesturesListener(context);
	    	gesturesListener.setListener(this);
	      
	        this.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
			@Override
	        	public void onGlobalLayout() {
		            	removeOnGlobalLayoutListener(that,this);
		            	shapesManager = new ShapesManager(context);
		            	shapesManager.setCanavasDimmensions(that.getWidth(),that.getHeight());
		            	shapesManager.setBlackHole();
		            	shapesManager.loadJSONSquares("squares.json");
		            	//shapesManager.generateSquares(3);            	
	        	}
	        });        
	}

	@Override
    	public void onDraw(Canvas canvas) {
    		if(shapesManager != null) {
    			shapesManager.Draw(canvas);
    		}
	}

	public void addRandomShape() {
    		shapesManager.generateSquares(1);  
		invalidate();
	}
    
	public void suckIn() {
    		shapesManager.wakeUpBlackHole();
    		invalidate();
	}
    
    @SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent event) {
    		float eventX = event.getX();
    		float eventY = event.getY();
      
    		if(gesturesListener != null) {
    			gesturesListener.onTouchEvent(event);
    		}
    	
    		
		switch (event.getAction()) {
    			case MotionEvent.ACTION_DOWN:
    	  
	    	  		Square sqr = shapesManager.getSquareByXY(eventX, eventY);
	    	  		if(sqr != null){
	    		  		shapesManager.setHoldedSquare(sqr);
			      		mDragSquareOffsetX = (int)(eventX - sqr.left);
			      		mDragSquareOffsetY = (int)(eventY - sqr.top);
			      		return true;
	    	  		}
	    	  
	    	  		BlackHole blackHole = shapesManager.getBlackHole();
	    	  		if(blackHole != null) {
	    		  		if(blackHole.contains(eventX, eventY)) {
	    			  		shapesManager.wakeUpBlackHole();
	    			  		return true;
	    		  		}
	    	  		}
	        return true;
    		case MotionEvent.ACTION_MOVE:
	    		if(shapesManager.getHoldedSquare() != null) {
	    			shapesManager.getHoldedSquare().offsetTo(eventX - mDragSquareOffsetX, eventY - mDragSquareOffsetY);
	    	  	}
	        break;
    		case MotionEvent.ACTION_UP:
	    		if(shapesManager.getHoldedSquare() != null && shapesManager.getBlackHole() != null) {
	    			BlackHole bh = shapesManager.getBlackHole();
	    		  	Square hs = shapesManager.getHoldedSquare();

	    		  	if(intersects(bh,hs)) {
	    				shapesManager.blacHoleSuckGrabbedElement();  
	    		  	}
	    	  	}
	    	  
		    	shapesManager.setHoldedSquare(null);
		    	mDragSquareOffsetX = 0;
		    	mDragSquareOffsetY = 0;
		break;
		default:
	        return false;
	}

    	invalidate();
      	return super.onTouchEvent(event);
    }    
    
	public static boolean intersects(RectF rect, RectF otherRect) {
        	return  rect.left <= otherRect.right && otherRect.left <= rect.right
        		&& rect.top <= otherRect.bottom && otherRect.top <= rect.bottom;
    	}

	@Override
	public void OnRotation(RotationGestureDetector rotationDetector) {
		float angle = rotationDetector.getAngle();
		if(shapesManager.getHoldedSquare() != null) {
    			shapesManager.getHoldedSquare().setRotation(angle * (-1));
    		}
	}

	@Override
	public boolean onScale(ScaleGestureDetector detector) {
		mScaleFactor = detector.getScaleFactor();
		if(shapesManager.getHoldedSquare() != null) {
    			shapesManager.getHoldedSquare().setSize((int)(shapesManager.getHoldedSquare().getSize() * mScaleFactor));
    			invalidate();
    		}
		return false;
	}

	@Override
	public boolean onDoubleTap(MotionEvent event) {
    		Square sqr = shapesManager.getSquareByXY(event.getX(), event.getY());
    		if(sqr != null) {
    			sqr.randomizeColor();
    			invalidate();
    		}		
		return false;
	}    
    
}
