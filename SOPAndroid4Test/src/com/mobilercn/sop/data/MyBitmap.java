package com.mobilercn.sop.data;

import java.io.Serializable;

/**
 * �Զ���λͼ���������л��Ķ���
 * @author ShawnXiao
 *
 */
public class MyBitmap implements Serializable {
	private static final long serialVersionUID=-6433786313435044321L;
	private byte[] bitmapBytes=null;
	private String name=null;
	
	public MyBitmap(byte[] bitmapBytes,String name){
		this.bitmapBytes=bitmapBytes;
		this.name=name;
	}
	
	public byte[] getBitmapBytes(){
		return this.bitmapBytes;
	}
	public String getName(){
		return this.name;
	}
}
