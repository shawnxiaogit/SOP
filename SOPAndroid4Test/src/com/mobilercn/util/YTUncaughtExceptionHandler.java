package com.mobilercn.util;

import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;

public class YTUncaughtExceptionHandler implements UncaughtExceptionHandler {
	
	static final String TAG = YTUncaughtExceptionHandler.class.getSimpleName();
	
	private Thread.UncaughtExceptionHandler  mDefaultHandler;
	
	public static YTUncaughtExceptionHandler mSelf;
	
	private Context mContext;
	
	private YTUncaughtExceptionHandler(){
		
	}
	
	public static YTUncaughtExceptionHandler getInstance(){
		
		if(mSelf == null){
			mSelf = new YTUncaughtExceptionHandler();
		}
		
		return mSelf;
	}
	
	public void setup(Context context){
		mSelf.mContext  = context;
		mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler(); 
		Thread.setDefaultUncaughtExceptionHandler(this);
	}
	
	

	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		reportCrash(ex);
		
		if(mDefaultHandler != null){
			mDefaultHandler.uncaughtException(thread, ex);
		}
		
	}
	
	 protected void saveCrashReport2File(String aReport) {  
		 
		 YTFileHelper file = YTFileHelper.getInstance();
		 file.setContext(mContext);
		 file.saveFile("crash.txt", aReport.getBytes());
		 
	 }
	
	private void reportCrash(Throwable e){
        String theErrReport = getCrashReport(e);  
        saveCrashReport2File(theErrReport);  
	}

	public String getCrashReport(Throwable aException) {  
        NumberFormat theFormatter = new DecimalFormat("#0.");  
        String theErrReport = "";  
          
        theErrReport += mContext.getPackageName()+" generated the following exception:/n";  
        theErrReport += aException.toString()+"/n/n";  
        
        StackTraceElement[] theStackTrace = aException.getStackTrace();  
        if (theStackTrace.length > 0) {  
            theErrReport += "--------- Stack trace ---------/n";  
            for (int i=0; i<theStackTrace.length; i++) {  
                theErrReport += theFormatter.format(i+1)+"/t"+theStackTrace[i].toString()+"/n";  
            }//for  
            theErrReport += "-------------------------------/n/n";  
        }  
          
        //if the exception was thrown in a background thread inside  
        //AsyncTask, then the actual exception can be found with getCause  
        Throwable theCause = aException.getCause();  
        if (theCause!=null) {  
            theErrReport += "----------- Cause -----------/n";  
            theErrReport += theCause.toString() + "/n/n";  
            theStackTrace = theCause.getStackTrace();  
            for (int i=0; i<theStackTrace.length; i++) {  
                theErrReport += theFormatter.format(i+1)+"/t"+theStackTrace[i].toString()+"/n";  
            }//for  
            theErrReport += "-----------------------------/n/n";  
        }//if  
          
        //app environment  
        PackageManager pm = mContext.getPackageManager();  
        PackageInfo pi;  
        try {  
            pi = pm.getPackageInfo(mContext.getPackageName(), 0);  
        } catch (NameNotFoundException eNnf) {  
            //doubt this will ever run since we want info about our own package  
            pi = new PackageInfo();  
            pi.versionName = "unknown";  
            pi.versionCode = 69;  
        }  
        Date theDate = new Date();  
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd_HH.mm.ss_zzz");  
        theErrReport += "-------- Environment --------/n";  
        theErrReport += "Time/t="+sdf.format(theDate)+"/n";  
        theErrReport += "Device/t="+Build.FINGERPRINT+"/n";  
        try {  
            Field theMfrField = Build.class.getField("MANUFACTURER");  
            theErrReport += "Make/t="+theMfrField.get(null)+"/n";  
        } catch (SecurityException e) {  
        } catch (NoSuchFieldException e) {  
        } catch (IllegalArgumentException e) {  
        } catch (IllegalAccessException e) {  
        }  
        theErrReport += "Model/t="+Build.MODEL+"/n";  
        theErrReport += "Product/t="+Build.PRODUCT+"/n";  
        theErrReport += "App/t/t="+mContext.getPackageName()+", version "+pi.versionName+" (build "+pi.versionCode+")/n";  
        theErrReport += "Locale="+mContext.getResources().getConfiguration().locale.getDisplayName()+"/n";  
        theErrReport += "-----------------------------/n/n";  
      
        theErrReport += "END REPORT.";  
        return theErrReport;  
    }
}
