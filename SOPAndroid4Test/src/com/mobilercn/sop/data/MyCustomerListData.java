package com.mobilercn.sop.data;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

import android.util.Log;

import com.mobilercn.config.D2EConfigures;
import com.mobilercn.sop.item.D2ECustomer;

/**
 * 客户列表信息
 *
 */
public class MyCustomerListData implements Serializable{
	private static final long serialVersionUID = -6919461967497580385L;
	
	private static MyCustomerListData myCustomerListData;
	//客户信息列表
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
	//是否包含指定工单号的客户信息
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
	//获取指定工单号的客户
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
	//添加客户信息
	public void addCustomer(D2ECustomer customer){
		mCustomerList.add(customer);
	}
	//删除客户信息
	public void deleteCustomer(D2ECustomer customer){
		mCustomerList.remove(customer);
	}
}
