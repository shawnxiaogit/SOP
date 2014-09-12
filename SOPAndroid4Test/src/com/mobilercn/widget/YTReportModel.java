package com.mobilercn.widget;

import java.util.ArrayList;
import java.util.List;

import com.mobilercn.util.YTMathUtil;
import com.mobilercn.util.YTStringHelper;

import android.content.Context;
import android.graphics.Paint;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannedString;
import android.text.style.StyleSpan;
import android.util.Log;

/**
 *����һ���ģ�� 
 */
public class YTReportModel implements YTReportAdapter {
	
	//modify by shawn 2012-8-30
	
	private static final int HORIZONTAL = 8;
	private static final int VERTICAL   = 4; 
	
	/**
	 * ���ַ�������
	 */
	private String[]       mHeader = null;
	private String[]       mLeft   = null;
	/**
	 * �������б�
	 */
	private List<String[]> mItems  = new ArrayList<String[]>();
	
	/**
	 * �п�
	 */
	private float[]        mWidthOfColumn;
	private float[]        mWidthOfRow;
	
	/**
	 * �ܿ��
	 */
	private float          mTotalWidth;
	/**
	 * ����
	 */
	private int            mColumns;
	
	/**
	 * ����
	 */
	private Paint          mPaint;
	
	
	/**
	 * ������
	 */
	private Context        mContext;
	
	
	private float          mMaxHeaderWidth;
	
	public YTReportModel(Paint paint, Context context){
		
		mPaint         = paint;
		mContext       = context;
		mColumns       = 0;
		mTotalWidth    = 0f;
		mWidthOfColumn = null;
		mWidthOfRow    = null;
		
		mMaxHeaderWidth = -1;
		
	}
	/**
	 * ���������Ϣ
	 */
	public void clear(){
		
		mColumns       = 0;
		mTotalWidth    = 0f;
		mWidthOfColumn = null;
		mLeft = null;
		mHeader = null;
		mItems.clear();
		
	}
	
	// +-----------------------------------------------------------
	// |                ȡ��ǰ�а���������
	// +-----------------------------------------------------------
	/**
	 * ��ȡ��ǰ�б�������
	 * @param row
	 * ��ǰ��
	 * @return
	 * ���� ����
	 */
	public int getColumnOfRow(int row){
		
		if(row < 0 || row >= mItems.size()){
			return 0;
		}
		
		String[] tmp = mItems.get(row);		
		return tmp.length;
		
	}
	
	public float getMaxWidthOfRows(int index){
		if(index < 0 || index > mItems.size()){
			return 0;
		}
		return mWidthOfRow[index];
	}
	
	private void calculateWidthOfRow(String[] rows){
		
		
		
		int cnt = rows.length;
		float len = 0;
		
		if(cnt == 1){
			len = getMaxWidth();
		}
		else {
			for(int i = 0; i < cnt; i ++){
				
				len = Math.max(len, mPaint.measureText(rows[i]) + YTMathUtil.dipToPixels(HORIZONTAL, mContext.getResources()) * 2);
				
			}			
		}
		
		
		if(mWidthOfRow == null){
			mWidthOfRow = new float[1];
			mWidthOfRow[0] = len;
		}
		else {
			float[] tmp = new float[mWidthOfRow.length + 1];
			System.arraycopy(mWidthOfRow, 0, tmp, 0, mWidthOfRow.length);
			tmp[mWidthOfRow.length] = len;
			mWidthOfRow = null;
			mWidthOfRow = tmp;
		}

	}
	
	/**
	 * �����п�	
	 * @param rows
	 * �ַ������� ������
	 */
	private void calculateWidthOfColumn(String[] rows){
		
		float total = 0;
		if(mColumns < rows.length){
			
			mColumns = rows.length;
			
			if(mWidthOfColumn != null){
				float[] tmp = new float[mColumns];
				System.arraycopy(mWidthOfColumn, 0, tmp, 0, mWidthOfColumn.length);
				mWidthOfColumn = null;
				mWidthOfColumn = tmp;				
			} 
			else {
				mWidthOfColumn = new float[mColumns];
			}
			
			total = 0;
			for(int i = 0; i < mColumns; i ++ ){
				mWidthOfColumn[i] = Math.max(mWidthOfColumn[i], mPaint.measureText(rows[i]) + YTMathUtil.dipToPixels(HORIZONTAL, mContext.getResources()) * 2);
				total += mWidthOfColumn[i];
			}		
		}
		else {
			mColumns = rows.length;
			total = 0;
			for(int i = 0; i < mColumns; i ++ ){						
				mWidthOfColumn[i] = Math.max(mWidthOfColumn[i], mPaint.measureText(rows[i]) + YTMathUtil.dipToPixels(HORIZONTAL, mContext.getResources()) * 2);
				total += mWidthOfColumn[i];
			}					
		}
		
		mTotalWidth = Math.max(mTotalWidth, total);
			
	}
	/**
	 * ���ñ�ͷ����
	 * @param header
	 * �ַ��������ͷ����
	 */
	public void setHeader(String[] header){
		if(header == null || header.length == 0){
			return;
		}
		mHeader=header;
		calculateWidthOfColumn(header);
		
	}
	/**
	 * ��ȡ��ͷ
	 * @return
	 * �ַ��������ͷ����
	 */
	public String[] getHeader(){
		
		return mHeader;
		
	}
	
	public float getMaxHeaderWidth(){
		
		if(mMaxHeaderWidth > 0){
			return mMaxHeaderWidth;
		}
		
		int cnt = mHeader.length;
		for(int i = 0; i < cnt; i ++){
			
			mMaxHeaderWidth = Math.max(mMaxHeaderWidth, mPaint.measureText(mHeader[i]) + YTMathUtil.dipToPixels(HORIZONTAL, mContext.getResources()) * 2);
			
		}
		
		return mMaxHeaderWidth;
		
	}
	
	/**
	 * �ж��Ƿ��б�ͷ
	 * @return
	 * ������
	 */
	public boolean hasHeader(){
		
		return mHeader != null;
		
	}
	/**
	 * �����������һ����Ϣ
	 * @param row
	 * ����Ϣ
	 * @param index
	 * ָ����
	 */
	public void addItemAt(String[] row, int index){
		
		
		if(row == null || row.length == 0){
			return;
		}
		
		if(index < 0 || index > mItems.size() ){
			return;
		}
		
		if(row.length > 1){
			calculateWidthOfColumn(row);			
			
		}
		calculateWidthOfRow(row);
		
		mItems.add(index, row);
	}
	/**
	 * �������һ����Ϣ
	 * @param row
	 */
	public void addItem(String[] row){
		
		addItemAt(row, mItems.size());
	}
	/**
	 * ��ȡ�ܿ��
	 * @return
	 * �������ܿ��
	 */
	public float getTotalWidth(){
		
		return mTotalWidth;
		
	}
	/**
	 * �����ܿ��
	 * @param f
	 * �����Ϳ��
	 */
	public void setTotalWidth(float f){
		
		mTotalWidth = f;
		
	}
	
	/**
	 * ��ȡ����
	 */
	@Override
	public int getColumns() {
		return mColumns;
	}
	/**
	 * ��ȡ����
	 */
	@Override
	public int getRows() {
		return mItems.size();
	}
	/**
	 * ��ȡָ���к��еĶ���
	 * @param row
	 * ָ����
	 * @param column
	 * ָ����
	 */
	@Override
	public Object getField(int row, int column) {
		
		
		if(row < 0 || row > mItems.size()){
			return null;
		}
		
				
		String[] tmp = mItems.get(row);
		
		
		if(column < 0 || column >= tmp.length){
			return null;
		}
		
		
		return tmp[column];
	}
	/**
	 * ��ȡָ���е������
	 * @param column 
	 * ����ָ����
	 */
	@Override
	public float getMaxWidthAtColumn(int column) {
		
		if(column < 0 || column >= getColumns()){
			return 0;
		}
		
		return mWidthOfColumn[column];
	}
	
	public void setMaxWidthAtColumn(int column, float f){
		
		if(column < 0 || column >= getColumns()){
			return ;
		}
		
		mWidthOfColumn[column] = f;

		
	}
	/**
	 * ��ȡ���߶�
	 */
	@Override
	public float getMaxHeight() {
		return mPaint.getFontMetrics().bottom - mPaint.getFontMetrics().top + YTMathUtil.dipToPixels(VERTICAL, mContext.getResources()) * 3;
	}
	
	/**
	 * ��ȡ�����
	 */
	@Override
	public float getMaxWidth() {
		return YTMathUtil.dipToPixels(40, mContext.getResources());
	}
	
	
	
}
