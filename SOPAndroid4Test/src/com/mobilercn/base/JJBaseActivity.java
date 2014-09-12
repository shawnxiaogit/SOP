package com.mobilercn.base;


import com.mobilercn.sop.R;
import com.mobilercn.sop.SOPAndroidActivity;

import com.mobilercn.sop.bt.SOPBluetoothService;



import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.InflateException;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.LayoutInflater.Factory;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

public abstract class JJBaseActivity extends Activity {
	
	public static final int  TYPE_BT   = 1;
	public static final int  TYPE_HTTP = 2;
	
	public static final long TASK_CANCEL = -1;
	/** 等待进度框 */
	private static ProgressDialog mProgressDialog = null;
	/** 当前任务ID */
	private long           mCurTaskID = TASK_CANCEL;
	//当前是否是等待界面
	private boolean        mShowProgressView;

	private static PopupWindow           mPopupWindow;
	
	private int mWaitViewType ;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		JJBaseService.g_allActivity.add(this);
		
		SOPBluetoothService.g_allActivity.push(this);
		setVolumeControlStream(AudioManager.STREAM_MUSIC);
		
		init();
		mShowProgressView = false;
	}
	
	protected Handler myHandler = new Handler(){
		
        public void handleMessage(Message msg) {
        	showProcessDialog("test");
        }
    };     
    
	public void showWaitDialog(String message){
		mWaitViewType = TYPE_HTTP;
		mShowProgressView = true;
		mProgressDialog = ProgressDialog.show(JJBaseActivity.this, null, message, true);
		mProgressDialog.setOnKeyListener(new OnKeyListener(){
			public boolean onKey(DialogInterface arg0, int arg1, KeyEvent arg2) {
				cancelProcess();
				dismissWaitDialog();
				return false;
			}
			
		});
	}
	
	public void dismissWaitDialog(){
		mShowProgressView = false;
		JJBaseService.addCancelTask(mCurTaskID);
		if(mProgressDialog != null){
			mProgressDialog.dismiss();
			mProgressDialog = null;			
		}
	}

	private void cancelProcess(){
		mShowProgressView = false;
		JJBaseService.addCancelTask(mCurTaskID);
	}


	public void showProcessDialog(String message){
		mWaitViewType = TYPE_BT;
		mShowProgressView = true;
        LayoutInflater factory = LayoutInflater.from(this);
        final View mProgressView = factory.inflate(R.layout.d2e_progress, null);
        TextView mProcessMessage = (TextView)mProgressView.findViewById(R.id.loading_tip_textview);	        
        ImageView mImageView = (ImageView)mProgressView.findViewById(R.id.loading_anim_imgview);
		mProcessMessage.setText(message);
		final AnimationDrawable mAnimationDrawable = (AnimationDrawable)mImageView.getBackground();         
	   
		if(mPopupWindow == null){
			mPopupWindow = new PopupWindow(mProgressView,LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT,true);//获取PopupWindow对象并设置窗体的大小
			mPopupWindow.setAnimationStyle(R.anim.fade);
		}
		mPopupWindow.showAtLocation(mProgressView, Gravity.CENTER, 0, 0);			

		(new Thread() {
            public void run(){
            	try{
            	Thread.sleep(400);
            	}catch(Exception e){}
            	mAnimationDrawable.start();
            }
        }).start();		
	}

	
	public void dismissProcessDialog(){		
		mShowProgressView = false;
    	try{
			if(mPopupWindow != null){
				mPopupWindow.dismiss();
				mPopupWindow = null;
			}			
    	}catch(Exception e){}
    	
    	try{
			if(mProgressDialog != null){
				mProgressDialog.dismiss();
				mProgressDialog = null;			
			}
    	}
    	catch(Exception ex){}
		
	}
	
	protected void setMenuBackground(){

		if(getLayoutInflater().getFactory() != null){
			return;
		}
		getLayoutInflater().setFactory( new Factory() {
            
			public View onCreateView(String name, Context context,
					AttributeSet attrs) {
                if ( name.equalsIgnoreCase( "com.android.internal.view.menu.IconMenuItemView" ) ) {
                    
                    try { // Ask our inflater to create the view
                        LayoutInflater f = getLayoutInflater();
                        final View view = f.createView( name, null, attrs );
                        new Handler().post( new Runnable() {
                            public void run () {
                                view.setBackgroundColor(Color.WHITE);//设置背景色
                            }
                        } );
                        return view;
                    }
                    catch ( InflateException e ) {}
                    catch ( ClassNotFoundException e ) {}
                }
                return null;
			}
        });
    }
	
	public synchronized void setCurTaskID(long id){
		mCurTaskID = id;
	}
	
	public synchronized long getCurTaskID(){
		return mCurTaskID;
	}
	
	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onDestroy() {
		if(mPopupWindow != null){
			mPopupWindow.dismiss();
			mPopupWindow = null;
		}
		if(mProgressDialog != null){
			mProgressDialog.dismiss();
			mProgressDialog = null;
		}
		super.onDestroy();
		JJBaseService.g_allActivity.remove(this);
		SOPBluetoothService.g_allActivity.remove(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return super.onOptionsItemSelected(item);
	}

	// 处理按下返回键
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			
			if(mShowProgressView){
				cancelProcess();
				dismissProcessDialog();
				dismissWaitDialog();
				return true;
			}
			
			if(isExit()){
				alertExit();				
			}
			else {
				backAciton();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	public void alertExit() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(getResources().getString(R.string.warm_prompt));
		builder.setIcon(R.drawable.msg_dlg_warning);
		builder.setMessage(getResources().getString(R.string.is_sure_leave));
		builder.setPositiveButton(getResources().getString(R.string.leave), new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				
				((JJBaseApplication)getApplication()).dealloc();
			}
		});
		builder.setNegativeButton(getResources().getString(R.string.btn_d2eprompt_cancle), null);
		builder.create().show();
	}

	public JJBaseApplication getBaseApplication(){
		return (JJBaseApplication)getApplication();
	}

	public abstract void backAciton();
	
	public abstract boolean isExit();
	
	public abstract void init();

	public abstract void taskSuccess(Object... param);
	
	public abstract void taskFailed(Object... param);
	
	public abstract void taskProcessing(Object... param);
	
	
	protected void onResume(){
		super.onResume();
		btStateChange();
	}
	
	public void btStateChange(){
		try{
			ImageView iv = (ImageView)findViewById(R.id.taskimg);
			if(JJBaseApplication.g_btState){
				iv.setBackgroundResource(R.drawable.bt_on2);
				iv.setVisibility(View.VISIBLE);
			}			
			else {
				iv.setVisibility(View.INVISIBLE);
			}
		}
		catch(Exception e){}		
	}

}
