package pl.com.andruszko.shapes.animations;

import java.util.ArrayList;
import java.util.List;

import pl.com.andruszko.shapes.elements.Square;

public abstract class BaseAnimation {
	protected List<IOnAnimationEnds> onAnimationEndsListeners = new ArrayList<IOnAnimationEnds>();
	public abstract void invalidate(Square square);
	
	public void addOnAnimationEndsListener(IOnAnimationEnds listener) {
		if (listener != null) {
			onAnimationEndsListeners.add(listener);
		}
	}
	
}
