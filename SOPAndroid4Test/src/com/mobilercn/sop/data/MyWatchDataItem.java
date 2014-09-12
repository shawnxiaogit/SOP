package com.mobilercn.sop.data;

import java.util.ArrayList;
import java.util.List;

/**
 * 现场勘查模块数据项
 * @author ShawnXiao
 *
 */
public class MyWatchDataItem {
	
	
	public List<MyWatchDataItem> mWatchDataList;
	public boolean isContainWatchDataItem(MyWatchDataItem itemGive){
		for(MyWatchDataItem itemExist:mWatchDataList){
			if(itemGive.getmMouseIndex()==itemExist.getmMouseIndex()){
				return true;
			}
		}
		return false;
	}
	public void deleContainWatchDataItem(MyWatchDataItem itemGive){
		for(int i=0;i<mWatchDataList.size();i++){
			MyWatchDataItem itemExist=mWatchDataList.get(i);
			if(itemGive.getmMouseIndex()==itemExist.getmMouseIndex()){
				mWatchDataList.remove(i);
			}
		}
	}
	
	/**
	 * 勘查类别
	 */
	private String mWatchType="";
	
	/**
	 * 现场勘查数据项索引
	 */
	private int mMouseIndex=0;
	/**
	 * 现场勘查数据项状态1
	 */
	private int mMouseKind1=0;
	/**
	 * 现场勘查数据项状态2
	 */
	private int mMouseKind2=0;
	/**
	 * 现场勘查数据项状态3
	 */
	private int mMouseKind3=0;
	/**
	 * 现场勘查数据项状态4
	 */
	private String mMouseKind4="";
	
	/**
	 * 检查数据状态5（用来表示新增的种类“无”）
	 */
	private int mMouseKind5=-1;
	
	
	
	private String mInsectName="";
	private String mRemark="";
	private String mArea="";
	
	public MyWatchDataItem(){
		mWatchDataList=new ArrayList<MyWatchDataItem>();
	}
	
	
	
	public String getmArea() {
		return mArea;
	}
	public void setmArea(String mArea) {
		this.mArea = mArea;
	}
	public String getmWatchType() {
		return mWatchType;
	}



	public int getmMouseKind5() {
		return mMouseKind5;
	}
	public void setmMouseKind5(int mMouseKind5) {
		this.mMouseKind5 = mMouseKind5;
	}
	public String getmInsectName() {
		return mInsectName;
	}
	public void setmInsectName(String mInsectName) {
		this.mInsectName = mInsectName;
	}
	public String getmRemark() {
		return mRemark;
	}
	public void setmRemark(String mRemark) {
		this.mRemark = mRemark;
	}
	
	
	
	public void setmWatchType(String mWatchType) {
		this.mWatchType = mWatchType;
	}

	

	public int getmMouseIndex() {
		return mMouseIndex;
	}



	public void setmMouseIndex(int mMouseIndex) {
		this.mMouseIndex = mMouseIndex;
	}



	public int getmMouseKind1() {
		return mMouseKind1;
	}
	public void setmMouseKind1(int mMouseKind1) {
		this.mMouseKind1 = mMouseKind1;
	}
	public int getmMouseKind2() {
		return mMouseKind2;
	}
	public void setmMouseKind2(int mMouseKind2) {
		this.mMouseKind2 = mMouseKind2;
	}
	public int getmMouseKind3() {
		return mMouseKind3;
	}
	public void setmMouseKind3(int mMouseKind3) {
		this.mMouseKind3 = mMouseKind3;
	}
	public String getmMouseKind4() {
		return mMouseKind4;
	}
	public void setmMouseKind4(String mMouseKind4) {
		this.mMouseKind4 = mMouseKind4;
	}
	@Override
	public String toString() {
		return "MyWatchDataItem [mWatchType=" + mWatchType + ", mMouseIndex="
				+ mMouseIndex + ", mMouseKind1=" + mMouseKind1
				+ ", mMouseKind2=" + mMouseKind2 + ", mMouseKind3="
				+ mMouseKind3 + ", mMouseKind4=" + mMouseKind4
				+ ", mMouseKind5=" + mMouseKind5 + ", mInsectName="
				+ mInsectName + ", mRemark=" + mRemark + "]";
	}
	
}
