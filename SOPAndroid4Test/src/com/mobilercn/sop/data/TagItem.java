package com.mobilercn.sop.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import android.graphics.Bitmap;
import android.util.Log;

import com.mobilercn.util.YTStringHelper;

public class TagItem implements Serializable{
	private static final long serialVersionUID =-6919461967497580385L;
	
	
	/**
	 * 是否是刷员工卡完成的
	 */
	public boolean checkByUserCard=false;
	/**
	 * 刷员工卡的字段
	 */
	public String checkType="";
	
	//用于文档
	public int index;
	
	
	/**
	 * 设施、实施名称
	 */
	private String mEquipName;
	
	private String mPointID;
	public String mTitle;
	public String mId;
	public  String mIndex;
	private String mPinYin;
	public String mTagNum;
	private String mPointId;
	//老鼠数量
	public String mMounseNumber="";
	//公司名字成员
	private String customName;
	//位置标签的名字
	private String positionName;
	//增加一个客户logo成员
	private Bitmap customLogoUrl;
	//Item所属的任务ID
	public String mTaskID;
	
	public String mType     = "";
	//绑定设备信息
	public String mState    = "";
	public String mStateInt = "";
	/**
	 * 备注
	 */
	public String mMemo     = "";
	
	//绑定区域作业
	public String mSuppliesID     = "";//药物
	/**
	 * 使用药品
	 */
	public String mSuppliesName   = "";
	public String mInstrumentID   = "";//器械
	/**
	 * 器械
	 */
	public String mInstrumentName = "";
	/**
	 * 设备
	 */
	public String mInstrument="N/A";
	public String mRate           = "";
	public String mVolume         = "";//用量
	/**
	 * 配比
	 */
	public String mRateFact       = "";
	/**
	 * 使用剂量
	 */
	public String mVolumeFact     = "";
	
	//绑定监测点部署
	public String mRoachNub = "";//
	public String mMouseNub = "";//
	
	//绑定监测点查验
	public String mRoachActive = "";
	public String mMouseActive = "";
	public String mRoachTotal  = "";
	public String mMouseTotal  = "";
	public String mMouseInit   = "";
	public String mRoachInit   = "";
	
	
	//判断是否是刷员工卡完成的
	private boolean isCheckByUser=false;
	
	//添加这个作业点的归属，谁完成的
	private String strCheckTypeUser="";
	
	
	//
	public boolean mChecked = false;
			
	public ArrayList<TagItem> mItems = new ArrayList<TagItem>();
	public HashMap<String,String> mItemTaskId=new HashMap<String,String>();
	public ArrayList<String> mItemPositionName=new ArrayList<String>();
	public TagItem(String title, String id){
		mTitle = title;
		mId    = id;
		
		try{
			mPinYin = YTStringHelper.cn2FirstSpell(title);
		}catch(Exception ex){}
	}
	
	public void pringPositionData(){
		Log.e("位置标签ID---------->", ""+mId);
		Log.e("位置标签名称---------->", ""+mTitle);
	}
	
	public final String getCustomName(){
		return customName;
	}
	public void setCustomName(String customName){
		this.customName=customName;
	}
	public final Bitmap getCustomLogo(){
		return customLogoUrl;
	}
	public void setCustomLogo(Bitmap customLogoUrl){
		this.customLogoUrl=customLogoUrl;
	}
	public final String getTitle(){
		return mTitle;
	}		
	
	public  String getId(){
		return mId;
	}
	public void setId(String id){
		this.mId=id;
	}
	
	public String getmPointId() {
		return mPointId;
	}
	public void setmPointId(String mPointId) {
		this.mPointId = mPointId;
	}
	public String getmId() {
		return mId;
	}
	public void setmId(String mId) {
		this.mId = mId;
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
	public String getTaskID(){
		return mTaskID;
	}
	public void setTaskId(String taskID){
		mTaskID=taskID;
	}
	public String getSOPPositionName(){
		return positionName;
	}
	public void setSOPPositionName(String positionName){
		this.positionName=positionName;
	}
	public String getmTitle() {
		return mTitle;
	}
	public void setmTitle(String mTitle) {
		this.mTitle = mTitle;
	}
	public String getmPointID() {
		return mPointID;
	}
	public void setmPointID(String mPointID) {
		this.mPointID = mPointID;
	}
	public String getStrCheckTypeUser() {
		return strCheckTypeUser;
	}
	public void setStrCheckTypeUser(String strCheckTypeUser) {
		this.strCheckTypeUser = strCheckTypeUser;
	}
	public boolean isCheckByUser() {
		return isCheckByUser;
	}
	public void setCheckByUser(boolean isCheckByUser) {
		this.isCheckByUser = isCheckByUser;
	}
	public String getmEquipName() {
		return mEquipName;
	}
	public void setmEquipName(String mEquipName) {
		this.mEquipName = mEquipName;
	}
	@Override
	public String toString() {
		return "TagItem [index=" + index + ", mEquipName=" + mEquipName
				+ ", mPointID=" + mPointID + ", mTitle=" + mTitle + ", mId="
				+ mId + ", mIndex=" + mIndex + ", mPinYin=" + mPinYin
				+ ", mTagNum=" + mTagNum + ", mPointId=" + mPointId
				+ ", mMounseNumber=" + mMounseNumber + ", customName="
				+ customName + ", positionName=" + positionName
				+ ", customLogoUrl=" + customLogoUrl + ", mTaskID=" + mTaskID
				+ ", mType=" + mType + ", mState=" + mState + ", mStateInt="
				+ mStateInt + ", mMemo=" + mMemo + ", mSuppliesID="
				+ mSuppliesID + ", mSuppliesName=" + mSuppliesName
				+ ", mInstrumentID=" + mInstrumentID + ", mInstrumentName="
				+ mInstrumentName + ", mInstrument=" + mInstrument + ", mRate="
				+ mRate + ", mVolume=" + mVolume + ", mRateFact=" + mRateFact
				+ ", mVolumeFact=" + mVolumeFact + ", mRoachNub=" + mRoachNub
				+ ", mMouseNub=" + mMouseNub + ", mRoachActive=" + mRoachActive
				+ ", mMouseActive=" + mMouseActive + ", mRoachTotal="
				+ mRoachTotal + ", mMouseTotal=" + mMouseTotal
				+ ", mMouseInit=" + mMouseInit + ", mRoachInit=" + mRoachInit
				+ ", isCheckByUser=" + isCheckByUser + ", strCheckTypeUser="
				+ strCheckTypeUser + ", mChecked=" + mChecked + ", mItems="
				+ mItems + ", mItemTaskId=" + mItemTaskId
				+ ", mItemPositionName=" + mItemPositionName + "]";
	}
	
	
}
