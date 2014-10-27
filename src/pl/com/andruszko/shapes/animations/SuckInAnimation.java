package pl.com.andruszko.shapes.animations;

import pl.com.andruszko.shapes.elements.Square;

public class SuckInAnimation extends BaseAnimation {

	public SuckInAnimation() {
	}

	@Override
	public void invalidate(Square square) {
		if((square.right - square.left) > 0 || (square.bottom - square.top) > 0) {
			float left,right,top,bottom;
			left = square.left;
			right = square.right;
			top = square.top;
			bottom = square.bottom;
			
			if(square.right - square.left > 0) {
				left += 2;
				right -= 2;
			} else {
				left = 0;
				right = 0;
			}
			if(square.bottom - square.top > 0) {
				top += 2;
				bottom -= 2;
			} else {
				top = 0;
				bottom = 0;
			}
			square.set(left, top, right, bottom);
		}
		if(square.getOpacity() > 0) {
			square.setOpacity(square.getOpacity() - 5);
		} else {
			square.setOpacity(0);
		}
		
		if(square.getRotationDirection() > 0) {
			square.setRotation((float)square.getRotation()-10);
		} else {
			square.setRotation((float)square.getRotation()+10);
		}
		
		if(square.getOpacity() <= 0) {
			square.removeAnimation(this);
			if (onAnimationEndsListeners.size() > 0) {
				for (IOnAnimationEnds event : onAnimationEndsListeners) {
					event.onAnimationEnds(square);
				}
			}			
		}
	}

}
