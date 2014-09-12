package com.mobilercn.sop.activity.view;


import com.mobilercn.sop.R;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.animation.AnimationUtils;
import android.view.MotionEvent;
import android.widget.ViewFlipper;

public class D2EViewFlipper extends ViewFlipper implements OnGestureListener{

	private GestureDetector mDetector;
	
	public D2EViewFlipper(Context context) {
		super(context);
		mDetector = new GestureDetector(this);
	}


	public D2EViewFlipper(Context context, AttributeSet attrs) {
		super(context, attrs);
		mDetector = new GestureDetector(this);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		return mDetector.onTouchEvent(event);
	}


	@Override
	public boolean onDown(MotionEvent e) {
		return false;
	}


	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {		
		if (e1.getY() - e2.getY() > 50){
			setInAnimation(AnimationUtils.loadAnimation(getContext(),
					R.anim.push_up_in));
			setOutAnimation(AnimationUtils.loadAnimation(getContext(),
					R.anim.push_up_out));
			showNext();
			
			return true;
		}
		else if (e1.getY() - e2.getY() < -50){
			setInAnimation(AnimationUtils.loadAnimation(getContext(),
					R.anim.push_down_in));
			setOutAnimation(AnimationUtils.loadAnimation(getContext(),
					R.anim.push_down_out));
			showPrevious();
			return true;
		}
		return false;		
	}


	@Override
	public void onLongPress(MotionEvent e) {
		
	}


	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		return false;
	}


	@Override
	public void onShowPress(MotionEvent e) {
		
	}


	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		return false;
	}

	
}
