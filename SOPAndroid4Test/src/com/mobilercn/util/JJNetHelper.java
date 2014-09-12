package com.mobilercn.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.mobilercn.base.JJBaseApplication;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;

public class JJNetHelper {
	
	public static boolean checkNet(Context context) {
		try {
			// ��ȡ�ֻ��������ӹ������(������wifi, net�����ӵĹ���)
			ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (connectivity != null) {
				// ��ȡ�������ӹ���Ķ���
				NetworkInfo info = connectivity.getActiveNetworkInfo();
				if (info != null && info.isConnected()) {
					// �жϵ�ǰ�����Ƿ��Ѿ�����
					if (info.getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static int checkNetAvailable(Context context){
		
		int result = JJBaseApplication.NO_ENABLED;
		try{
			ConnectivityManager connManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);  
			// ��ȡ��������״̬��NetWorkInfo����  
			//NetworkInfo networkInfo = connManager.getActiveNetworkInfo();  
			// ��ȡ��ǰ�����������Ƿ����  
			//result = networkInfo.isAvailable();  
			  
			State state = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();  
			if(State.CONNECTED==state){  
			    result = JJBaseApplication.GPRS_ENABLED;
			}  
			    
			state = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();  
			if(State.CONNECTED==state){  
				result = JJBaseApplication.WIFI_ENABLED;
			}  
			    
			// ��ת�������������ý���  
			//startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));  
			// ��ת������wifi�������ý���  
			//startActivity(new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS)); 			
		}
		catch(Exception ex){
		}
		return result;
	}
	
    public static byte[] readByByte(InputStream dis, long len) throws IOException {
    	byte[] result = null;
    	ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int bufferSize = 512 ;
        byte byteInput[] = new byte[bufferSize] ;
        int size = 0;
        if (len!=-1) {
            long askSize = 0;
            while (len > 0) {
                askSize = (len < (long)bufferSize) ? len : (long)bufferSize;
                if ((size = dis.read(byteInput, 0, (int)askSize)) != -1) {
                    len -= size;
                    buffer.write(byteInput, 0, size);
                } else {
                    break;
                }
            }
        } else {
            while ((size = dis.read(byteInput, 0, bufferSize)) != -1) {
                buffer.write(byteInput, 0, size);
            }
        }
        result = buffer.toByteArray();
        return result;
    }

}
