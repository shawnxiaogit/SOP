package com.mobilercn.sop.data;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

import android.util.Log;

import com.mobilercn.config.D2EConfigures;
import com.mobilercn.sop.item.D2ECustomer;

/**
 * �ͻ��б���Ϣ
 *
 */
public class MyCustomerListData implements Serializable{
	private static final long serialVersionUID = -6919461967497580385L;
	
	private static MyCustomerListData myCustomerListData;
	//�ͻ���Ϣ�б�
	private List<D2ECustomer> mCustomerList;
	
	public MyCustomerListData(){
		mCustomerList=new ArrayList<D2ECustomer>();
	}
	public static MyCustomerListData getInstance(){
		if(null==myCustomerListData){
			myCustomerListData=new MyCustomerListData();
		}
		return myCustomerListData;
	}
	//�Ƿ����ָ�������ŵĿͻ���Ϣ
	public boolean isContainWeekSerialCustomer(D2ECustomer customer){
		if(mCustomerList.size()>0){
			for(D2ECustomer existCustomer:mCustomerList){
				
				if(customer.mSerial!=null&&customer.mSerial.length()>0
						&&existCustomer.mSerial!=null&&existCustomer.mSerial.length()>0){
					if(existCustomer.mSerial.equalsIgnoreCase(customer.mSerial)){
						return true;
					}
				}
			}
		}
		return false;
	}
	//��ȡָ�������ŵĿͻ�
	public D2ECustomer getWeekSerialCustomer(D2ECustomer customer){
		D2ECustomer getCustomer=null;
		int size=mCustomerList.size();
		for(int i=0;i<size;i++){
			D2ECustomer exitCustomer=mCustomerList.get(i);
			if(customer.mSerial!=null&&customer.mSerial.length()>0
					&&exitCustomer.mSerial!=null&&exitCustomer.mSerial.length()>0){
			if(mCustomerList.get(i).mSerial.equalsIgnoreCase(customer.mSerial)){
				getCustomer=mCustomerList.get(i);
			}
			}
		}
		return getCustomer;
	}
	//��ӿͻ���Ϣ
	public void addCustomer(D2ECustomer customer){
		mCustomerList.add(customer);
	}
	//ɾ���ͻ���Ϣ
	public void deleteCustomer(D2ECustomer customer){
		mCustomerList.remove(customer);
	}
}
