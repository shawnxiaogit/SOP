package com.mobilercn.widget;

import com.mobilercn.sop.R;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.WindowManager;
import android.widget.EditText;

public class YTEditText extends EditText {
	
	static final String TAG = YTEditText.class.getSimpleName();
	
	public static final float DEFAULT_FONT_METRICS_BOTTOM = 3.25F;
	public static final float DEFAULT_FONT_METRICS_HEIGHT = 15.82F;
	public static final float DEFAULT_FONT_METRICS_TOP    = -12.57F;
	public static final int   DEFAULT_PADDING_LEFT        = 8;
	

	public YTEditText(Context context){
		super(context);
	}

	public YTEditText(Context context, AttributeSet attr){
		super(context, attr);
	}

	public YTEditText(Context context, AttributeSet attr, int def){
		super(context, attr, def);
	}
	
	protected void onDraw(Canvas canvas){
		int   width = ((WindowManager)getContext().getSystemService("window")).getDefaultDisplay().getWidth();
		Paint paint = new Paint();
		paint.setStyle(Style.FILL);
		paint.setColor(R.color.yt_text_view_color);
		
		int pt      = getPaddingTop();
		int pb      = getPaddingBottom(); 
		int sy      = getScrollY();
		int sx      = getScrollX() + width;
		
		int lineH   = getLineHeight();
		int totalH  = ((getHeight() + sy) - pt - pb) + lineH;
		
		float fontB = paint.getFontMetrics().bottom;
		float fontT = paint.getFontMetrics().top;
		float fontH = fontB - fontT;
		
		float space = DEFAULT_FONT_METRICS_HEIGHT - fontH;		
		int top = sy + lineH;
		int pre = sy % lineH;
		
		float beginH = top - pre - space;
		do{
			if(beginH < totalH){
				canvas.drawLine(DEFAULT_PADDING_LEFT, beginH + pt, sx - DEFAULT_PADDING_LEFT, beginH + pt, paint);
				beginH += lineH;
			}
			else{
				super.onDraw(canvas);
				return;
			}
		}while(true);
	}

}