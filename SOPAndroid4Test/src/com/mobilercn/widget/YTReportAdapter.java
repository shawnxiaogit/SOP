package com.mobilercn.widget;

public interface YTReportAdapter {
	
	//�����
	public int getColumns();
	
	//������
	public int getRows();
	
	//ָ��Ԫ��
	public Object getField(int row, int column);
	
	//ָ�����е�����
	public float getMaxWidthAtColumn(int column);
	
	//ָ�����е����߶�
	public float getMaxHeight();

	//ָ�����е����߶�
	public float getMaxWidth();

}
