package com.mobilercn.widget;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

public class YTReportItems implements YTReportAdapter{
	
	private static final int SIZE = 8;
	
	private static final int DEFAULT_HEIGHT = 40;
	
	private List<ReportItem> mItems = new ArrayList<ReportItem>();
	
	private Paint          mPaint;
	
	private float[]        mWidthOfColumn;
	
	private float          mSingleCharWidth;
	
	public YTReportItems(Paint paint){
		mPaint = paint;
		mWidthOfColumn = new float[SIZE];
		mSingleCharWidth = mPaint.measureText("¾ü");
	}
	
	public void addReportItem(String column1, String column2, String column3, String column4, String column5, String column6, String column7,String column8){
		ReportItem ri = new ReportItem(SIZE);
		ri.mTexts[0] = column1;
		ri.mTexts[1] = column2;
		ri.mTexts[2] = column3;
		ri.mTexts[3] = column4;
		ri.mTexts[4] = column5;
		ri.mTexts[5] = column6;
		ri.mTexts[6] = column7;
		ri.mTexts[7] = column8;
		
		mItems.add(ri);
		
		mWidthOfColumn[0] = Math.max(mWidthOfColumn[0], getTextWidth(column1, mPaint) + mSingleCharWidth * 1);
		mWidthOfColumn[1] = Math.max(mWidthOfColumn[1], getTextWidth(column2, mPaint) + mSingleCharWidth * 1);
		mWidthOfColumn[2] = Math.max(mWidthOfColumn[2], getTextWidth(column3, mPaint) + mSingleCharWidth * 1);
		mWidthOfColumn[3] = Math.max(mWidthOfColumn[3], getTextWidth(column4, mPaint) + mSingleCharWidth * 1);
		mWidthOfColumn[4] = Math.max(mWidthOfColumn[4], getTextWidth(column5, mPaint) + mSingleCharWidth * 1);
		mWidthOfColumn[5] = Math.max(mWidthOfColumn[5], getTextWidth(column6, mPaint) + mSingleCharWidth * 1);
		mWidthOfColumn[6] = Math.max(mWidthOfColumn[6], getTextWidth(column7, mPaint) + mSingleCharWidth * 1);
		mWidthOfColumn[7] = Math.max(mWidthOfColumn[7], getTextWidth(column8, mPaint) + mSingleCharWidth * 1);
	}

	public float getTextWidth(String str, Paint paint){		
		
		return paint.measureText(str);
		
	}
	
	public float getSingleCharWidth() {
		return mSingleCharWidth;
	}
	
	public float getMaxWidth(){
		float total = 0;
		for(int i = 0; i < SIZE; i ++){
			total += getMaxWidthAtColumn(i);
		}
		return total;
	}
	
	public int getColumns() {
		return SIZE;
	}

	public int getCount() {
		return mItems.size();
	}

	public Object getField(int row, int column) {
		
		ReportItem itm = mItems.get(row);
		
		return itm.getTextAt(column);
	}

	public float getMaxWidthAtColumn(int column) {
		if(column < 0 || column >= getColumns()){
			return 0;
		}
		return mWidthOfColumn[column];
	}

	public float getMaxHeightAtRow(int row) {
		return DEFAULT_HEIGHT;
	}
	
	//-------------------------------------------------
	//
	//=================================================
	public class ReportItem {
		
		public String[] mTexts;
		
		public ReportItem(int size){
			mTexts = new String[size];
		}
		
		public String getTextAt(int index){
			return mTexts[index];
		}
		
	}

	@Override
	public float getMaxHeight() {
		return DEFAULT_HEIGHT * getCount();
	}

	@Override
	public int getRows() {
		return 0;
	}

}
