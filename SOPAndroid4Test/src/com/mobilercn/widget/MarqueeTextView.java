package com.mobilercn.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 自定义一直都获得焦点的TextView
 * @author ShawnXiao
 *
 */
public class MarqueeTextView extends TextView {

	public MarqueeTextView(Context context) {
		super(context);
	}
	
	public MarqueeTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public MarqueeTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
}
