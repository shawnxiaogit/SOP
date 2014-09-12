package com.mobilercn.sop.item;

import java.io.Serializable;
import java.util.ArrayList;

import android.util.Log;

import com.mobilercn.base.JJBaseApplication;
import com.mobilercn.config.D2EConfigures;
import com.mobilercn.sop.activity.D2ESOPActivity;
import com.mobilercn.sop.data.TagItem;

/**
 * 客户对象
 * 
 * @author ShawnXiao
 * 
 */
public class D2ECustomer implements Serializable {
	private static final long serialVersionUID = -6919461967497580385L;
	/**
	 * 是否最最终保存了文件
	 */
	public boolean isfinalSaveFile = false;
	/**
	 * 客户编号
	 */
	public String mId;
	/**
	 * 客户名称
	 */
	public String mName;
	/**
	 * 客户类型
	 */
	public String mType;
	/**
	 * 施工单号
	 */
	public String mSerial;
	/**
	 * 任务ID
	 */
	public String mTaskId;
	/**
	 * 任务类型
	 */
	public int mTaskType;
	/**
	 * 备注
	 */
	public String mMemo;
	/**
	 * 施工人员列表
	 */
	public ArrayList<D2EPerson> mPersons = new ArrayList<D2EPerson>(2);
	/**
	 * 位置标签列表
	 */
	public ArrayList<TagItem> mPositions = new ArrayList<TagItem>(5);

	/**
	 * 构造函数
	 */
	public D2ECustomer() {

	}

	/**
	 * 设置选中的位置标签
	 * 
	 * @param selectPosition
	 */
	public void setSelectPositon(TagItem selectPosition) {
		int positionsSize = mPositions.size();
		TagItem existPosition = null;
		for (int i = 0; i < positionsSize; i++) {
			existPosition = mPositions.get(i);
			if (existPosition.mTitle.equalsIgnoreCase(selectPosition.mTitle)
					&& existPosition.mId.equalsIgnoreCase(selectPosition.mId)) {
				mPositions.set(i, selectPosition);
			}
		}
	}

	/**
	 * 获取选中的位置标签
	 * 
	 * @return
	 */
	public TagItem getSelectPositon() {
		int positionsSize = mPositions.size();
		TagItem existPosition = null;
		for (int i = 0; i < positionsSize; i++) {
			existPosition = mPositions.get(i);
			if (existPosition.mTitle
					.equalsIgnoreCase(JJBaseApplication.selectPosition.mTitle)
					&& existPosition.mId
							.equalsIgnoreCase(JJBaseApplication.selectPosition.mId)) {
				return existPosition;
			}
		}
		return null;
	}

	/**
	 * 设置选中的位置标签
	 * 
	 * @param selectPosition
	 */
	public void setSelectPoint(TagItem selectPoint) {
		int positionsSize = mPositions.size();
		TagItem existPosition = null;
		TagItem existPoint = null;
		for (int i = 0; i < positionsSize; i++) {
			existPosition = mPositions.get(i);
			int size = existPosition.mItems.size();
			for (int j = 0; j < size; j++) {
				existPoint = existPosition.mItems.get(j);
				if (existPoint.mTitle.equalsIgnoreCase(selectPoint.mTitle)
						&& existPoint.mId.equalsIgnoreCase(selectPoint.mId)) {
					existPosition.mItems.set(j, selectPoint);
				}
			}

		}
	}

	/**
	 * 设置选中的位置标签
	 * 
	 * @param selectPosition
	 */
	public TagItem getSelectPoint() {
		int positionsSize = mPositions.size();
		TagItem existPosition = null;
		TagItem existPoint = null;
		for (int i = 0; i < positionsSize; i++) {
			existPosition = mPositions.get(i);
			int size = existPosition.mItems.size();
			for (int j = 0; j < size; j++) {
				existPoint = existPosition.mItems.get(j);
				if (existPoint.mTitle
						.equalsIgnoreCase(JJBaseApplication.selectPoint.mTitle)
						&& existPoint.mId
								.equalsIgnoreCase(JJBaseApplication.selectPoint.mId)) {
					return existPoint;
				}
			}

		}
		return null;
	}

	/**
	 * 是否都检查完毕
	 * 
	 * @return
	 */
	public boolean allChecked() {
		// 额外施工，没有任何标签的情况
		if (mPositions.size() == 0 && mMemo != null && mMemo.length() > 0) {
			return true;
		}
		boolean result = true;
		if (D2EConfigures.TEST) {
			if (JJBaseApplication.customerTempt != null) {
				Log.e("JJBaseApplication.customerTempt.isfinalSaveFile-------->",
						"" + (JJBaseApplication.customerTempt.isfinalSaveFile));
			}
		}
		// 如果最终保存了文件
		if (JJBaseApplication.customerTempt != null) {
			if (JJBaseApplication.customerTempt.isfinalSaveFile) {
				int size = JJBaseApplication.customerTempt.mPositions.size();
				for (int i = 0; i < size; i++) {
					TagItem sop = JJBaseApplication.customerTempt.mPositions
							.get(i);
					if (!sop.isChecked()) {
						result = false;
						return result;
					}
				}
			}
		} else {
			int size = JJBaseApplication.g_customer.mPositions.size();
			for (int i = 0; i < size; i++) {
				TagItem sop = JJBaseApplication.g_customer.mPositions.get(i);
				if (!sop.isChecked()) {
					result = false;
					return result;
				}
			}
		}
		return result;
	}

	/**
	 * 清楚所有数据
	 */
	public void dealloc() {
		mPersons.clear();
		mPersons = null;
		mPositions.clear();
		mPositions = null;
	}

	/**
	 * 打印客户下面的所有信息
	 */
	public void printCustomerData() {
		Log.e("mId------------>", "" + mId);
		Log.e("mName------------>", "" + mName);
		Log.e("mType------------>", "" + mId);
		Log.e("mSerial------------>", "" + mSerial);
		Log.e("mTaskId------------>", "" + mTaskId);
		Log.e("mTaskType------------>", "" + mTaskType);
		Log.e("mMemo------------>", "" + mMemo);
		for (D2EPerson person : mPersons) {
			Log.e("员工信息：", "" + person.toString());
		}
		for (TagItem position : mPositions) {
			position.pringPositionData();
			for (TagItem point : position.mItems) {
				Log.e("作业点信息:", "" + point.toString());
			}
		}
	}

}
