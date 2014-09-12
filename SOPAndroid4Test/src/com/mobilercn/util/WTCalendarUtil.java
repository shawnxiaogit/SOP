/**
 * 
 **/
package com.mobilercn.util;

import java.util.Calendar;
import java.util.GregorianCalendar;

import com.mobilercn.base.JJBaseApplication;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;

public class WTCalendarUtil {
	public static final long  MILLISECONDS_PER_DAY  = 1000 * 24 * 60 * 60L;
	public static final long  MILLISECONDS_PER_YEAR = 365 * MILLISECONDS_PER_DAY;
	public static final int   MONTH_PER_YEAR        = 12;
	public static final int   DAYS_PER_WEEK         = 7;

	public static final float    TIMELINE_SPAN      = 4.5f;
	public static final int      TIMELINE_WIDTH     = 50;
	public static final Calendar GGregorianCalendar = new GregorianCalendar();
	
	public static final String   WEEKSARRY[] = {
		"周日", 
		"周一", 
		"周二", 
		"周三", 
		"周四", 
		"周五", 
		"周六"
		};
	// ================================================
	public static void move2FirstDayOfFirstWeek(Calendar calendar){
    	while (calendar.get(Calendar.DAY_OF_WEEK) != calendar.getFirstDayOfWeek()){
    		calendar.add(Calendar.DAY_OF_MONTH, -1);
    	}
	}
	
    // ===============================================
    public static void move2FirstDayOfMonth(Calendar calendar) {
    	int month = calendar.get(Calendar.MONTH);
    	int year = calendar.get(Calendar.YEAR);
    	calendar.clear();
    	calendar.set(year, month, calendar.getMinimum(Calendar.DAY_OF_MONTH));
    }
    
     // ===============================================
    public int getMonthsBetweenCalendar(Calendar c1, Calendar c2){
    	int year1 = c1.get(Calendar.YEAR);
    	int year2 = c2.get(Calendar.YEAR);
    	
    	int months1 = c1.get(Calendar.MONTH);
    	int months2 = c2.get(Calendar.MONTH);
    	
    	return (year2 - year1) * MONTH_PER_YEAR + months2 - months1;
    }
    
	// ================================================
    public static int calcSpannedWeeksForMonth(Calendar calendar) {

    	Calendar c = (Calendar) calendar.clone();
    	move2FirstDayOfMonth(c);

    	int weeks = 0;
    	int next = getNextMonth(calendar);
    	    	
    	while (c.get(Calendar.MONTH) != next) {
    		c.add(Calendar.DAY_OF_MONTH, DAYS_PER_WEEK);
    		weeks++;
    	}
    	
    	return weeks + 1;
    }

    
	// ================================================
	public static int getNextMonth(Calendar calendar){
		Calendar c = new GregorianCalendar();
    	c.setTime(calendar.getTime());
    	c.add(Calendar.MONTH, 1);
    	return c.get(Calendar.MONTH);		
	}
	
    // ========================================================================
    static int interpolateInt(int src, int dst, float alpha) {
		return (int) ((dst - src)*alpha) + src;
	}

    // ========================================================================
	public static int interpolateColor(int src_color, int dst_color, float fraction) {
		int red = interpolateInt(Color.red(src_color), Color.red(dst_color), fraction);
		int green = interpolateInt(Color.green(src_color), Color.green(dst_color), fraction);
		int blue = interpolateInt(Color.blue(src_color), Color.blue(dst_color), fraction);
		int alpha = interpolateInt(Color.alpha(src_color), Color.alpha(dst_color), fraction);
		return Color.argb(alpha, red, green, blue);
	}
	
    // ========================================================================
	static int invertColor(int src_color) {
		return Color.argb(
				Color.alpha(src_color),
				0xFF - Color.red(src_color),
				0xFF - Color.green(src_color),
				0xFF - Color.blue(src_color));
	}

}
