package pl.com.andruszko.shapes;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pl.com.andruszko.shapes.animations.IOnAnimationEnds;
import pl.com.andruszko.shapes.animations.PosAnimation;
import pl.com.andruszko.shapes.animations.SuckInAnimation;
import pl.com.andruszko.shapes.elements.BlackHole;
import pl.com.andruszko.shapes.elements.Square;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class ShapesManager implements IOnAnimationEnds {
	
	private Paint mPaint = new Paint();
	private List<Square> mSquares = new ArrayList<Square>();
	public Square mHoldedSquare = null;
	private Context mContext;
	private int mCanvasWidth = 0;
	private int mCanvasHeight = 0;
	private BlackHole mBlackHole = null;
	private SuckInAnimation suckInAnimation;

	public ShapesManager(Context context) {
		mContext = context;
		suckInAnimation = new SuckInAnimation();
	        suckInAnimation.addOnAnimationEndsListener(new IOnAnimationEnds() {
				
				@Override
				public void onAnimationEnds(Square square) {
					synchronized(mSquares) {
						getSquares().remove(square);
					}
				}
			});        
	}
	
	public BlackHole getBlackHole() {
		return mBlackHole;
	}
	
	public void setBlackHole() {
		mBlackHole = new BlackHole(0,0);
		mBlackHole.setPos( 10, (mCanvasHeight - mBlackHole.getSize()) - 10);
	}
	
	public void updateBlackHolePos() {
		mBlackHole.setPos( 10, (mCanvasHeight - mBlackHole.getSize()) - 10);
	}
	
	public void wakeUpBlackHole() {
		for(int i = 0; i < mSquares.size(); i++) {
			Square sqr = mSquares.get(i);
			PosAnimation posAnimation = new PosAnimation(getBlackHole().centerX() - (sqr.getSize() / 2), getBlackHole().centerY() - (sqr.getSize() / 2));
			posAnimation.addOnAnimationEndsListener(this);
			sqr.setAnimation(posAnimation);	
		}
	}
	
	public void generateSquares(int count) {
		for(int i = 0; i < count; i++) {
			Square sqr = new Square();
			sqr.randomizeParams(mCanvasWidth,mCanvasHeight);
			this.addSquare(sqr);
		}	
	}
	
	public void setCanavasDimmensions(int w, int h) {
		mCanvasWidth = w;
		mCanvasHeight = h;
	}
	
	public void setHoldedSquare(Square square) {
		mHoldedSquare = square;
	}
	
	public Square getHoldedSquare() {
		return mHoldedSquare;
	}
	
	public List<Square> getSquares() {
		return mSquares;
	}
	
	public void addSquare(Square sqr) {
		mSquares.add(sqr);
	}
	
	public void loadJSONSquares(String filename) {
		
		try {
		String jsonLocation = AssetsUtility.AssetJSONFile(mContext, filename);
		JSONObject jsonObject = new JSONObject(jsonLocation);
		JSONArray jsonArray = (JSONArray) jsonObject.getJSONArray("squares");
			for(int i = 0 ;i < jsonArray.length(); i++) {
				JSONObject jb = (JSONObject) jsonArray.get(i);
				Square tmp = new Square(jb.getInt("x"),jb.getInt("y"),jb.getInt("size"));
				tmp.setColor(jb.getString("colour"));
				this.addSquare(tmp);
			}
		} catch (JSONException e) {
		        e.printStackTrace();
		}
	}
	
	public void Draw(Canvas canvas) {
		mPaint.setAntiAlias(true);
		mPaint.setColor(Color.BLUE);    	
		
		if(mBlackHole != null) {
			mPaint.setColor(Color.BLACK);
    		canvas.drawCircle((mBlackHole.left + mBlackHole.getSize() / 2), (mBlackHole.top + mBlackHole.getSize() / 2), mBlackHole.getSize() /2, mPaint);
		}
		
		synchronized(mSquares) {
			//for loop optimization
			int size = mSquares.size();
			for(int i = (size - 1);i >= 0; i--) {
	    		canvas.save();
	    		Square sqr = mSquares.get(i);
	    		sqr.invalidateAnimations(mContext);
	    		canvas.rotate(sqr.getRotation(),sqr.centerX(),sqr.centerY());
	    		mPaint.setColor(sqr.getColor());
	    		canvas.drawRect(sqr, mPaint);
	    		canvas.restore();
	    	}		
		}
	}
	
    public Square getSquareByXY(float x, float y) {
        
	  	  if(getSquares().size() > 0){
			  for(int i = 0; i < getSquares().size(); i++) {
		    	  Square sqr = getSquares().get(i);
		    	  if(sqr.contains(x, y)) {
		    		  return sqr;
		    	  }
			  }
		  }
	  return null;    		
  }	

	public void blacHoleSuckGrabbedElement() {
		getHoldedSquare().setAnimation(suckInAnimation);		
	}
	
	@Override
	public void onAnimationEnds(Square square) {
		 square.setAnimation(suckInAnimation);
	}
	
}
