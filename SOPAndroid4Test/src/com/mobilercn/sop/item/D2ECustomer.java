package com.mobilercn.sop.item;

import java.io.Serializable;
import java.util.ArrayList;

import android.util.Log;

import com.mobilercn.base.JJBaseApplication;
import com.mobilercn.config.D2EConfigures;
import com.mobilercn.sop.activity.D2ESOPActivity;
import com.mobilercn.sop.data.TagItem;

/**
 * �ͻ�����
 * 
 * @author ShawnXiao
 * 
 */
public class D2ECustomer implements Serializable {
	private static final long serialVersionUID = -6919461967497580385L;
	/**
	 * �Ƿ������ձ������ļ�
	 */
	public boolean isfinalSaveFile = false;
	/**
	 * �ͻ����
	 */
	public String mId;
	/**
	 * �ͻ�����
	 */
	public String mName;
	/**
	 * �ͻ�����
	 */
	public String mType;
	/**
	 * ʩ������
	 */
	public String mSerial;
	/**
	 * ����ID
	 */
	public String mTaskId;
	/**
	 * ��������
	 */
	public int mTaskType;
	/**
	 * ��ע
	 */
	public String mMemo;
	/**
	 * ʩ����Ա�б�
	 */
	public ArrayList<D2EPerson> mPersons = new ArrayList<D2EPerson>(2);
	/**
	 * λ�ñ�ǩ�б�
	 */
	public ArrayList<TagItem> mPositions = new ArrayList<TagItem>(5);

	/**
	 * ���캯��
	 */
	public D2ECustomer() {

	}

	/**
	 * ����ѡ�е�λ�ñ�ǩ
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
	 * ��ȡѡ�е�λ�ñ�ǩ
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
	 * ����ѡ�е�λ�ñ�ǩ
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
	 * ����ѡ�е�λ�ñ�ǩ
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
	 * �Ƿ񶼼�����
	 * 
	 * @return
	 */
	public boolean allChecked() {
		// ����ʩ����û���κα�ǩ�����
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
		// ������ձ������ļ�
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
	 * �����������
	 */
	public void dealloc() {
		mPersons.clear();
		mPersons = null;
		mPositions.clear();
		mPositions = null;
	}

	/**
	 * ��ӡ�ͻ������������Ϣ
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
			Log.e("Ա����Ϣ��", "" + person.toString());
		}
		for (TagItem position : mPositions) {
			position.pringPositionData();
			for (TagItem point : position.mItems) {
				Log.e("��ҵ����Ϣ:", "" + point.toString());
			}
		}
	}

}
