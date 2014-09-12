package com.mobilercn.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SaveLoginParams {
	private static final String PREF_NAME_N="mySopfileN";
	private static final String PREF_NAME_P="mySopfileP";
	//�����¼�û���
	public static void saveParamsName(Context context,String username){
		SharedPreferences sp=
				context.getSharedPreferences(PREF_NAME_N, Activity.MODE_PRIVATE);
		sp.edit()
		.putString("una", username)
		.commit();
	} 
	//�����¼����
	public static void saveParamsPwd(Context context,String password){
		SharedPreferences sp=
				context.getSharedPreferences(PREF_NAME_P, Activity.MODE_PRIVATE);
		sp.edit()
		.putString("pwd", password)
		.commit();
	}
	//��ȡ��¼�û���
	public static String[] getParamsName(Context context){
		SharedPreferences sp=
				context.getSharedPreferences(PREF_NAME_N, Activity.MODE_PRIVATE);
		String loginParams[]=new String[1];
		loginParams[0]=sp.getString("una", "");
		return loginParams;
	} 
	//��ȡ��¼����
	public static String[] getParamsPwd(Context context){
		SharedPreferences sp=
				context.getSharedPreferences(PREF_NAME_P, Activity.MODE_PRIVATE);
		String loginParams[]=new String[1];
		loginParams[0]=sp.getString("pwd", "");
		return loginParams;
	}
	//ɾ����¼���û���
	public static void deletParamsName(Context context){
		SharedPreferences sp=
				context.getSharedPreferences(PREF_NAME_N, Activity.MODE_PRIVATE);
		sp.edit()
		.remove("una")
		.commit();
	}
	//ɾ����¼������
	public static void deletParamsPwd(Context context){
		SharedPreferences sp=
				context.getSharedPreferences(PREF_NAME_P, Activity.MODE_PRIVATE);
		sp.edit()
		.remove("pwd")
		.commit();
	}
}
