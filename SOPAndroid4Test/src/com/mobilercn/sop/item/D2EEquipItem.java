package com.mobilercn.sop.item;

public class D2EEquipItem {
	
	private int   mId;
	
	private String mName;
	
	private String mStatus;
	
	private String mMime;
	
	private boolean mChecked;
	
	public D2EEquipItem(int id, String name){
		mId   = id;
		mName = name;
	}
	
	public void setStatus(String status){
		mStatus = status;
	}
	
	public final String getStatus(){
		return mStatus;
	}
	
	public void setMime(String mime){
		mMime = mime;
	}
	
	public String getMime(){
		return mMime;
	}
	
	public boolean isChecked(){
		return false;
	}

}
