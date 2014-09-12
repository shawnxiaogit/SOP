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
	 * �Ƿ���ˢԱ������ɵ�
	 */
	public boolean checkByUserCard=false;
	/**
	 * ˢԱ�������ֶ�
	 */
	public String checkType="";
	
	//�����ĵ�
	public int index;
	
	
	/**
	 * ��ʩ��ʵʩ����
	 */
	private String mEquipName;
	
	private String mPointID;
	public String mTitle;
	public String mId;
	public  String mIndex;
	private String mPinYin;
	public String mTagNum;
	private String mPointId;
	//��������
	public String mMounseNumber="";
	//��˾���ֳ�Ա
	private String customName;
	//λ�ñ�ǩ������
	private String positionName;
	//����һ���ͻ�logo��Ա
	private Bitmap customLogoUrl;
	//Item����������ID
	public String mTaskID;
	
	public String mType     = "";
	//���豸��Ϣ
	public String mState    = "";
	public String mStateInt = "";
	/**
	 * ��ע
	 */
	public String mMemo     = "";
	
	//��������ҵ
	public String mSuppliesID     = "";//ҩ��
	/**
	 * ʹ��ҩƷ
	 */
	public String mSuppliesName   = "";
	public String mInstrumentID   = "";//��е
	/**
	 * ��е
	 */
	public String mInstrumentName = "";
	/**
	 * �豸
	 */
	public String mInstrument="N/A";
	public String mRate           = "";
	public String mVolume         = "";//����
	/**
	 * ���
	 */
	public String mRateFact       = "";
	/**
	 * ʹ�ü���
	 */
	public String mVolumeFact     = "";
	
	//�󶨼��㲿��
	public String mRoachNub = "";//
	public String mMouseNub = "";//
	
	//�󶨼������
	public String mRoachActive = "";
	public String mMouseActive = "";
	public String mRoachTotal  = "";
	public String mMouseTotal  = "";
	public String mMouseInit   = "";
	public String mRoachInit   = "";
	
	
	//�ж��Ƿ���ˢԱ������ɵ�
	private boolean isCheckByUser=false;
	
	//��������ҵ��Ĺ�����˭��ɵ�
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
		Log.e("λ�ñ�ǩID---------->", ""+mId);
		Log.e("λ�ñ�ǩ����---------->", ""+mTitle);
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
