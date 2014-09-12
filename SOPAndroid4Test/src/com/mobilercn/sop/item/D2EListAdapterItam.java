package com.mobilercn.sop.item;

import java.util.ArrayList;

import com.mobilercn.sop.data.TagItem;
import com.mobilercn.util.YTStringHelper;

public class D2EListAdapterItam {
	private String mTitle;
	private String mId;
	private String mPinYin;
	private String mTagNum;
	
	//绑定设备信息
	public String mType     = "";
	public String mState    = "";
	public String mStateInt = "";
	public String mMemo     = "";
	
	//勘查客户联系人
	private String mCustomerContact;
	
	//
	public boolean mChecked = false;
	
	public ArrayList<TagItem> mItems = new ArrayList<TagItem>();
	
	
	public D2EListAdapterItam(String title){
		mTitle=title;
	}
	
	public D2EListAdapterItam(String title, String id){
		mTitle = title;
		mId    = id;
		
		try{
			mPinYin = YTStringHelper.cn2FirstSpell(title);
		}catch(Exception ex){}
	}
	
	public final String getTitle(){
		return mTitle;
	}		
	
	public final String getId(){
		return mId;
	}
	
	public final String getPinYin(){
		return mPinYin;
	}
	
	public void addItem(TagItem item){
		mItems.add(item);
	}
	
	public boolean isChecked(){
		if(mItems.size() == 0){
			return false;
		}
		
		for(TagItem item : mItems){
			if(!item.mChecked){
				return false;
			}
		}
		return true;
	}
	
	public void setTagNum(String num){
		mTagNum = num;
	}
	
	public String getTagNum(){
		return mTagNum;
	}

	public String getmCustomerContact() {
		return mCustomerContact;
	}

	public void setmCustomerContact(String mCustomerContact) {
		this.mCustomerContact = mCustomerContact;
	}
	
}
