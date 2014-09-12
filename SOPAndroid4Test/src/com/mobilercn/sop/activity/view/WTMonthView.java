/**
 * @author wu.s.w
 * @create 2012-03-21
 **/
package com.mobilercn.sop.activity.view;

import java.text.DateFormatSymbols;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.mobilercn.base.JJBaseApplication;
import com.mobilercn.config.D2EConfigures;
import com.mobilercn.sop.R;
import com.mobilercn.util.WTCalendarUtil;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DrawFilter;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.Paint.Align;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.SimpleOnGestureListener;

public class WTMonthView extends View {
	
	static final String TAG = "WTMonthView";
	
	static final int        WEEKHEIGHT  = 20;
	
	private WTMonthRenderer mRenderer;
	//当前显示的月份包含周数
	private int             mWeeksInMonth;
	
	//当前显示的月份以及当天高亮
	private Date            mHighlightDate    = new Date();	
	private Date            mToday            = new Date();
	private Calendar        mHighlightCalendar;
	//左上顶点日期,可能是上月
	//用于保存当前检索月份
	private Calendar        mIteratorCalendar;	
	private Calendar        mCachedCalendar = new GregorianCalendar();
	//滑动的距离
	private float           mScrollOffset ;
	private boolean         mScrolling;
	
	//color
	private int             mBackgroundColor;
	
	//listener
	private WTDateUpdateListener mListener;
	
	static final SimpleDateFormat FULL_MONTH_NAME_FORMATTER = new SimpleDateFormat("MMMM");
	
	static final SimpleDateFormat COMPARE_MONTH_NAME_FORMATTER = new SimpleDateFormat("yyMMdd");
	//小数字背景
	private Bitmap mBadgeImg;
	
	private boolean mIsTouched;
	
	//
	private Bitmap  mImgNow;
	//
	private boolean isShowBadge;
			
	public WTMonthView(Context context) {
		super(context);
		initlized();
	}

    public WTMonthView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initlized();	
	}
    
    
    public void setBedge(boolean show){
    	isShowBadge = show;
    }
    
    public void setDateListener(WTDateUpdateListener ls){
    	mListener = ls;
    }
    
    private void initlized(){
		mRenderer        = new WTMonthRenderer(getContext());
		mIsTouched = false;
		mBackgroundColor = getResources().getColor(R.color.calendar_background_normal);
		setMonthAndHightToday(new Date());   
		
		mBadgeImg = BitmapFactory.decodeResource(getResources(), R.drawable.badge_ifaux);
		
		mImgNow = BitmapFactory.decodeResource(getResources(), R.drawable.date_now);
    }  
    
    //animation
    private long  starttime = 0;
    private long  duration = 100;
    private float toValue;
    private float initValue = 0;
    private float step;
    private boolean forward;
    private boolean finished = false;
    
	private void beginAnimation(){
		if(toValue == 0 && initValue == 0 && !finished){
			invalidate();
			finished = true;
			return;
		}
		long time = System.currentTimeMillis();
		if(time - starttime > duration){
			starttime = time;
			
			if(!forward){
				if(initValue < toValue){
					initValue += step;
					mScrollOffset += step;					
				} else {
					setMonthAndHightToday(mHighlightDate);
					mScrollOffset = 0;
					toValue = 0;
					initValue = toValue;
				}
			}
			else {
				if(initValue > -toValue){
					initValue -= step;
					mScrollOffset += -step;					
				}
				else {
					setMonthAndHightToday(mHighlightDate);
					mScrollOffset = 0;
					toValue = 0;
					initValue = toValue;
				}
			}						
		} 
		invalidate();
	}
    
	void moveMonth(boolean forward) {
        this.forward = forward;
        Calendar c = (Calendar)mHighlightCalendar.clone();
        c.add(Calendar.MONTH, forward ? 1 : -1);
        int   weeks = WTCalendarUtil.calcSpannedWeeksForMonth(c);
        toValue   = (weeks - 1) * (mRenderer.getCellHeight() + mRenderer.mVerticalSpec);
        initValue = 0;
        step = (mRenderer.getCellHeight() + mRenderer.mVerticalSpec);//toValue / (ANIMATION / duration);
        finished = false;
        invalidate();
	}
	
	public Date changeMonth(boolean forward){
        this.forward = forward;
        Calendar c = (Calendar)mHighlightCalendar.clone();
        c.add(Calendar.MONTH, forward ? 1 : -1);
        
        mHighlightDate.setMonth(mHighlightDate.getMonth() + (forward ? 1 : -1));
        
        int   weeks = WTCalendarUtil.calcSpannedWeeksForMonth(c);
        toValue   = (weeks - 1) * (mRenderer.getCellHeight() + mRenderer.mVerticalSpec);
        initValue = 0;
        step = (mRenderer.getCellHeight() + mRenderer.mVerticalSpec);//toValue / (ANIMATION / duration);
        finished = false;
        invalidate();		
        return mHighlightDate;
	}

	public boolean onTouchEvent(MotionEvent event) {
		if(mScrolling){
			return true;
		}
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			mIsTouched = true;
			if(event.getY() > WEEKHEIGHT ){
				Date date = getDayFromPoint(new PointF(event.getX(), event.getY()));
					
				
				Calendar c = new GregorianCalendar();
				c.setTime(date);
				
				
				int weeks = WTCalendarUtil.calcSpannedWeeksForMonth(c);
				//判断是否是上月份或者上年
				if(date.getYear() < mHighlightCalendar.get(Calendar.YEAR) - 1900){
					moveMonth(false);
				}
				else if(date.getYear() == mHighlightCalendar.get(Calendar.YEAR) - 1900){
					if(date.getMonth() < mHighlightCalendar.get(Calendar.MONTH)){
						moveMonth(false);
					}
					else if(date.getMonth() > mHighlightCalendar.get(Calendar.MONTH)) {
						moveMonth(true);
					}
				}
				else {
					moveMonth(true);
				}
				
				highlightDay(date);	
				return true;
			}			
		}
		
		return super.onTouchEvent(event);
	}

    Date getDayFromPoint(PointF point) {

        int usable_width = getWidth() - (getPaddingLeft() + getPaddingRight());
    	int weekday_index = (int) (WTCalendarUtil.DAYS_PER_WEEK * (point.x - getPaddingLeft()) / usable_width);
    	
    	int height = getHeight() - (getPaddingTop() + getPaddingBottom()) - WEEKHEIGHT;
        int week_offset = (int) (mWeeksInMonth * 
        		(point.y - getPaddingTop() - mScrollOffset - WEEKHEIGHT) / height
        		);
        
		Calendar offset_calendar_date = (Calendar) mHighlightCalendar.clone();
		WTCalendarUtil.move2FirstDayOfFirstWeek(offset_calendar_date);

		offset_calendar_date.add(Calendar.DATE, WTCalendarUtil.DAYS_PER_WEEK * week_offset + weekday_index);
    	return offset_calendar_date.getTime();
    }

    
    public void setMonth(Calendar calendar){
    	mHighlightDate = null;
    	//设置当前显示月份
    	if(mHighlightCalendar == null){
    		mHighlightCalendar = new GregorianCalendar();
    	}
    	mHighlightCalendar.setTime(calendar.getTime());
    	//定位到该月第一天
    	WTCalendarUtil.move2FirstDayOfMonth(mHighlightCalendar);
    	
    	//确定第一周的第一天位置
    	Calendar c = new GregorianCalendar();
    	c.setTime(mHighlightCalendar.getTime());
    	WTCalendarUtil.move2FirstDayOfFirstWeek(c);
    	    	
    	
    	//绘制日历的时候起始日期
    	
    	mIteratorCalendar = new GregorianCalendar();
    	mIteratorCalendar.setTime(mHighlightCalendar.getTime());
    	WTCalendarUtil.move2FirstDayOfMonth(mIteratorCalendar);
    	WTCalendarUtil.move2FirstDayOfFirstWeek(mIteratorCalendar);
    	
    	mScrollOffset = 0;  	
    	mWeeksInMonth = WTCalendarUtil.calcSpannedWeeksForMonth(mHighlightCalendar);
    	
    	mRenderer.updateCellDimension();
    	

    	invalidate();
    }

    public void setMonthAndHightToday(Date date){ 
    	//Log.e(TAG, "setMonthAndHightToday: " + "[月份 = " + date.getMonth() + "][ 日期 = " + date.getDate() + " ][周 = " + date.getDay() + "]");
    	
    	Calendar cal = new GregorianCalendar();
		cal.setTime(date);
    	int day = cal.get(Calendar.DATE);
    	//定位到该月的第一天
    	//WTCalendarUtil.move2FirstDayOfMonth(cal);
    	if (!cal.equals(mHighlightCalendar)){
    		setMonth(cal);
    	}

		Calendar cal2 = (Calendar) cal.clone();
		cal2.set(Calendar.DATE, day);
        highlightDay(cal2.getTime());
       
    }
    
    private void highlightDay(Date date){
    	mHighlightDate = date;
		if(mListener!=null && mIsTouched){
			mIsTouched = false;
			mListener.onDateUpdate(mHighlightDate);
		}

    }
    
    public void today(){
    	mHighlightDate = new Date();
    }

	protected void onDraw(Canvas canvas) {
		
		
        super.onDraw(canvas);
        mRenderer.onDraw(canvas);
        
        beginAnimation();
    }

	abstract class WTCellRenderer {
		
		Canvas mCanvas;
		
		public void setCanvas(Canvas canvas){
			mCanvas = canvas;
		}
		
		abstract void onDrawCell(RectF cellRect);
	}
	
	class WTMonthRenderer{
		static final String MONTH_WATERMARK_FONT_PATH = "water.ttf";
		//绘制日期
		Paint     mTitlePaint;
		//绘制周
		Paint     mWeekPaint;
		//绘制年\月
		TextPaint mYearPaint;
		//测试水印
		TextPaint mWaterPaint;
		
		
		Resources mResources;
		
		float     mMaxCellWidth;
		float     mHorizontalSpace = 1;
		float     mVerticalSpec    = 1;
		
		
		float     mCellWidth;
		float     mCellHeight;
		boolean   mRecalculate;
		
		private DrawFilter mFastDF = new PaintFlagsDrawFilter(Paint.FILTER_BITMAP_FLAG |
    			Paint.DITHER_FLAG,
    			0);		
		
		//绘制单元格背景
		WTCellRenderer  mCellBackgroundRenderer = new WTCellRenderer(){
			@Override
			public void onDrawCell(RectF cellRect) {
				mCanvas.save();
				mCanvas.translate(cellRect.left, cellRect.top);				
				drawDayInfo(mCanvas, cellRect);
				mCanvas.restore();
			}
			
		};
		
		public WTMonthRenderer(Context context){
			mCellWidth   = -1;
			mCellHeight  = -1;
			mRecalculate = false;
			
			mResources = context.getResources();
			
			mTitlePaint = new Paint();
			mTitlePaint.setColor(Color.WHITE);
			mTitlePaint.setTextSize(24.0f);
			
    		mWaterPaint = new TextPaint();
    		mWaterPaint.setAntiAlias(true);
    		mWaterPaint.setColor(Color.GRAY);
    		mWaterPaint.setTextAlign(Align.RIGHT);

			
			mWeekPaint = new Paint();
			mWeekPaint.setTextSize(20.0f);
			
		}
				
		public void updateCellDimension(){
			int   width = getWidth() - getPaddingLeft() - getPaddingRight();
			mCellWidth  = (width - (WTCalendarUtil.DAYS_PER_WEEK - 1) * mHorizontalSpace) / WTCalendarUtil.DAYS_PER_WEEK;			

			int   height = getHeight() - WEEKHEIGHT - getPaddingTop() - getPaddingBottom();
			mCellHeight  = (height - (mWeeksInMonth - 1) * mVerticalSpec) / mWeeksInMonth;				
		}
		
		
		
		float getCellHeight(){
			if(mCellHeight < 0 || mRecalculate){
				int   height = getHeight() - WEEKHEIGHT - getPaddingTop() - getPaddingBottom();
				mCellHeight  = (height - (mWeeksInMonth - 1) * mVerticalSpec) / mWeeksInMonth;				
			}
			return mCellHeight;
		}
		
		float getCellWidth(){
			if(mCellWidth < 0 || mRecalculate){
				int   width = getWidth() - getPaddingLeft() - getPaddingRight();
				mCellWidth  = (width - (WTCalendarUtil.DAYS_PER_WEEK - 1) * mHorizontalSpace) / WTCalendarUtil.DAYS_PER_WEEK;			
			}
			return mCellWidth;
		}
		
		protected void drawDayInfo(Canvas canvas, RectF rect){
			
			boolean sameMonth = mCachedCalendar.get(Calendar.MONTH) == mHighlightDate.getMonth();
			if(sameMonth){
				mTitlePaint.setColor(mBackgroundColor);				
			}
			else {
				mTitlePaint.setColor(mResources.getColor(R.color.calendar_background_disable));
			}
			canvas.drawRoundRect(new RectF(0, 0, rect.width(), rect.height()), 1, 1, mTitlePaint);
			
			if(sameMonth){
				mTitlePaint.setColor(mResources.getColor(R.color.calendar_forground_normal));				
			}
			else {
				mTitlePaint.setColor(mResources.getColor(R.color.calendar_forground_disable));
			}
			
			mTitlePaint.setFakeBoldText(true);
			String text       = Integer.toString(mCachedCalendar.get(Calendar.DAY_OF_MONTH));
						
    		float  textHeight = mTitlePaint.getFontMetrics().ascent + mTitlePaint.getFontMetrics().descent;
    		mTitlePaint.setTextAlign(Align.CENTER);
    		canvas.drawText(text, 
    				getCellWidth() / 2, 
    				getCellHeight() / 2 - textHeight / 2 , 
    				mTitlePaint);	
    		
			if(mCachedCalendar.get(Calendar.MONTH) == mToday.getMonth() &&
					mCachedCalendar.get(Calendar.DATE) == mToday.getDate()){
				canvas.drawBitmap(mImgNow, 4, 0, mTitlePaint);
			}

    		
    		if(isShowBadge){
    			if(JJBaseApplication.g_worksheet.containsKey(COMPARE_MONTH_NAME_FORMATTER.format(mCachedCalendar.getTime()))){
    				
    				
    	    		canvas.drawBitmap(
    	    				mBadgeImg, 
    	    				null, 
    	    				new Rect(
    					    		  (int)(rect.width() - dipToPixels(18)), 
    								    0,//-6, 
    								    (int)rect.width(), 
    								    (int)(dipToPixels(18)) //- 6
    	    						), 
    	    				null); 
    	    		
    	    		Paint p = new Paint();
    	    		
    				p.setColor(Color.WHITE);
    				p.setTextAlign(Align.CENTER);
    				p.setTextSize(20);
    				
    				float fontHeight = p.getFontMetrics().bottom - p.getFontMetrics().top; 
    				// 计算文字baseline 
    				float textBaseY = (dipToPixels(18)) - (dipToPixels(18) - fontHeight) / 2 - p.getFontMetrics().bottom; 
    				canvas.drawText(
    						JJBaseApplication.g_worksheet.get(COMPARE_MONTH_NAME_FORMATTER.format(mCachedCalendar.getTime())),
    						rect.width() - dipToPixels(18) / 2, 
    						textBaseY, 
    						p);
    	    				
    			}
    		}
		}
				
		// ================================================
		private void drawDays(Canvas canvas){
			//这里不能直接操作mIteratorCalendar,会造成不断刷新导致日期错误
			float cellw = getCellWidth() ;
			float cellh = getCellHeight() ;
			canvas.save();
			canvas.translate(0, WEEKHEIGHT + 1 + getPaddingTop());

			
			int preWeeks = (int)Math.floor(- mScrollOffset / (cellh + mVerticalSpec));
			mCachedCalendar.setTime(mIteratorCalendar.getTime());
			mCachedCalendar.add(Calendar.DAY_OF_MONTH, preWeeks * WTCalendarUtil.DAYS_PER_WEEK);
			
			for(int i = 0; i <= mWeeksInMonth; i ++){
				float top =  (cellh + mVerticalSpec) * (i + preWeeks);
				for(int j = 0; j < WTCalendarUtil.DAYS_PER_WEEK; j ++){
					float left = getPaddingLeft() + (cellw + mHorizontalSpace) * j;
					RectF rect = new RectF();					
					rect.set(left, top, left + cellw, top + cellh);
					mCellBackgroundRenderer.setCanvas(canvas);
					mCellBackgroundRenderer.onDrawCell(rect);					
					mCachedCalendar.add(Calendar.DAY_OF_MONTH, 1);
				}
			}
			canvas.restore();
		}
		
		private void onDrawWeeks(Canvas canvas){
			canvas.save();
			int preWeeks = (int)Math.ceil( mScrollOffset / (getCellHeight() + mVerticalSpec));				
			canvas.translate(0, - preWeeks * (getCellHeight() + mVerticalSpec));

			mWeekPaint.setTextAlign(Align.CENTER);
    		float  textHeight = mWeekPaint.getFontMetrics().ascent + mWeekPaint.getFontMetrics().descent;
			float  top        = getPaddingTop();
			float  left       = getPaddingLeft();
			for(int i = 0; i < WTCalendarUtil.DAYS_PER_WEEK; i ++){
				float x = i * (getCellWidth() + mHorizontalSpace);
				mWeekPaint.setColor(mResources.getColor(R.color.calendar_week_background));
				canvas.drawRect(new RectF(left + x, top, left + x + getCellWidth(), top + WEEKHEIGHT), mWeekPaint);
				mWeekPaint.setColor(mResources.getColor(R.color.calendar_week_forground));			
				canvas.drawText(WTCalendarUtil.WEEKSARRY[i], left + x + getCellWidth() / 2, top + (WEEKHEIGHT - textHeight) / 2, mWeekPaint);
			}
			canvas.restore();
		}
		
   	    private float getMaxMonthWidth(Paint paint) {
        	
            DateFormatSymbols dfs = new DateFormatSymbols();
            float max_month_width = Float.MIN_VALUE;
        	Rect bounds = new Rect();
        	for (String month : dfs.getMonths()) {
        		paint.getTextBounds(month, 0, month.length(), bounds);
        		if (bounds.width() > max_month_width)
        			max_month_width = bounds.width();
        	}
        	
        	return max_month_width;
    	}

		
		private void onDrawMonthName(Canvas canvas){
			float scale = getHeight() / getMaxMonthWidth(mWaterPaint);
			int preWeeks = (int)Math.ceil( mScrollOffset / (getCellHeight() + mVerticalSpec));				
    		canvas.save();
    		canvas.translate(getWidth(), - preWeeks * (getCellHeight() + mVerticalSpec));
    		canvas.rotate(-90);
    		canvas.scale(scale, scale);
			canvas.drawText(FULL_MONTH_NAME_FORMATTER.format(mHighlightCalendar.getTime()), 
					0 , 
					0 , 
					mWaterPaint);
			canvas.restore();
			
		}
		
		
		protected void onDraw(Canvas canvas){
			canvas.setDrawFilter(mFastDF);			
			canvas.save();
			int preWeeks = (int)Math.ceil( mScrollOffset / (getCellHeight() + mVerticalSpec));	
						
			canvas.translate(0, preWeeks * (getCellHeight() + mVerticalSpec));
			onDrawWeeks(canvas);
			drawDays(canvas);
			
			canvas.restore();
		}
		
		//工具方法
		private float dipToPixels(float dip) {
			Resources r = getResources();
			float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, r.getDisplayMetrics());
			return px;
		}

	}
	
}