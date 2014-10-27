package pl.com.andruszko.shapes.animations;

import pl.com.andruszko.shapes.elements.Square;

public class PosAnimation extends BaseAnimation {

	private float x = 0;
	private float y = 0;
	private int speed = 10;
	
	public PosAnimation(float x, float y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public void invalidate(Square square) {
		float left, top;
		left = square.left;
		top = square.top;
		
		float steps;
		if(Math.abs(this.y - top) > Math.abs(this.x - left)) {
			steps = Math.abs(this.y - top);
		} else {
			steps = Math.abs(this.x - left);
		}
		
		float diffX = this.x - left;
		float diffY = this.y - top;
		
		float moveX = diffX / (steps / speed);
		float moveY = diffY / (steps / speed);
		

		float myX = left + moveX;
		float myY = top + moveY;
		
		if(Math.abs(left - this.x) < moveX) {
			myX = this.x;
		}
		
		if(Math.abs(top - this.y) < moveY) {
			myY = this.y;
		}
		
		
		if(left != this.x || top != this.y){
			square.set(myX, myY, myX + square.getSize(), myY + square.getSize());
		} else {
			square.removeAnimation(this);
			if (onAnimationEndsListeners.size() > 0) {
				for (IOnAnimationEnds event : onAnimationEndsListeners) {
					event.onAnimationEnds(square);
				}
			}
		}
	}

}
