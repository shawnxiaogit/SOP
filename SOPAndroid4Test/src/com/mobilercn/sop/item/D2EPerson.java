package com.mobilercn.sop.item;

import java.io.Serializable;

import android.util.Log;

public class D2EPerson implements Serializable{
	private static final long serialVersionUID = -6919461967497580385L;
	
	private String  mID;
	private String  mName;
	private String  mTagNub;
	private String  mTagNubCur;
	public  boolean mChecked;
	
	public D2EPerson(){
		
	}
		
	public D2EPerson(String id, String name, String tagNub){
		mID     = id;
		mName   = name;
		mTagNub = tagNub;
	}

	public void setTagNubCur(String cur){
		mTagNubCur = cur;
	}
	
	public String getTagNubCur(){
		return mTagNubCur;
	}
	
	public String getId(){
		return mID;
	}
	
	public String getName(){
		return mName;
	}
	
	public String getTagNub(){
		return mTagNub;
	}
	
	@Override
	public String toString() {
		return "D2EPerson [mID=" + mID + ", mName=" + mName + ", mTagNub="
				+ mTagNub + ", mTagNubCur=" + mTagNubCur + ", mChecked="
				+ mChecked + "]";
	}

	public void print(){
		Log.e("PersonID---------->", mID);
		Log.e("PersonName------------->", mName);
		Log.e("PersonISCheck-------->",""+ mChecked);
		Log.e("PersonTagNum-------->",""+ mTagNub);
	}
}
