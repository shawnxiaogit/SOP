package com.mobilercn.sop.data;

import android.graphics.Bitmap;
import android.util.Log;

public class PcoUser {
	public boolean mChecked=false;
	private String strId;
	private String strName;
	private String strTagId;
	private String strLogo;
	private String strNumbers;
	private Bitmap mPhoto;
	
	public PcoUser(){
		
	}
	
	public PcoUser(String strId,String strName){
		this.strId=strId;
		this.strName=strName;
	}
	public PcoUser(String id,String name,String tag){
		this.strId=id;
		this.strName=name;
		this.strTagId=tag;
	}
	public String getStrId() {
		return strId;
	}

	public void setStrId(String strId) {
		this.strId = strId;
	}

	public String getStrName() {
		return strName;
	}

	public void setStrName(String strName) {
		this.strName = strName;
	}

	public String getStrTagId() {
		return strTagId;
	}

	public void setStrTagId(String strTagId) {
		this.strTagId = strTagId;
	}

	public String getStrLogo() {
		return strLogo;
	}

	public void setStrLogo(String strLogo) {
		this.strLogo = strLogo;
	}

	public String getStrNumbers() {
		return strNumbers;
	}

	public void setStrNumbers(String strNumbers) {
		this.strNumbers = strNumbers;
	}
	
	public Bitmap getmPhoto() {
		return mPhoto;
	}
	public void setmPhoto(Bitmap mPhoto) {
		this.mPhoto = mPhoto;
	}
	
	public void print(){
		Log.e("PcoUserId----------->", strId);
		Log.e("PcoUserstrName----------->", strName);
		Log.e("PcoUserstrTagId----------->", strTagId);
		Log.e("PcoUserstrNumbers----------->", strNumbers);
		Log.e("PcoUserstrLogo--------------->", strLogo);
		Log.e("--------------------------------------", "================================");
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((strId == null) ? 0 : strId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PcoUser other = (PcoUser) obj;
		if (strId == null) {
			if (other.strId != null)
				return false;
		} else if (!strId.equals(other.strId))
			return false;
		return true;
	}
	
}
