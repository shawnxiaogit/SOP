package com.mobilercn.sop;

import java.util.Locale;

import com.mobilercn.base.JJBaseActivity;
import com.mobilercn.base.JJBaseApplication;
import com.mobilercn.base.JJBaseService;
import com.mobilercn.config.D2EConfigures;

import com.mobilercn.sop.R;
import com.mobilercn.sop.activity.D2EDeviceListActivity;
import com.mobilercn.sop.activity.D2ELoginActivity;
import com.mobilercn.sop.activity.D2EMessageActivity;
import com.mobilercn.sop.bt.SOPBluetoothService;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;

public class SOPAndroidActivity extends JJBaseActivity {
	private final static String TAG = "SOPAndroidActivity";

	public static boolean g_testBt = false;

	protected void onResume() {
		super.onResume();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (D2EConfigures.TEST) {
			Log.i(TAG, "--------------------onCreate()-----------------");
		}
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.d2e_logo);

		// 设定当前窗口UI的动画效果
		View view = this.findViewById(R.id.logo_screen);
		AlphaAnimation animation = new AlphaAnimation(0.1f, 1.0f);
		animation.setDuration(200); // 持续时间
		view.startAnimation(animation);
		animation.setAnimationListener(new AnimationListener() {
			// @Override
			public void onAnimationStart(Animation animation) {

			}

			// @Override
			public void onAnimationRepeat(Animation animation) {

			}

			// @Override
			public void onAnimationEnd(Animation animation) {

				// 判断是否支持蓝牙
				BluetoothAdapter bluetoothAdapter = BluetoothAdapter
						.getDefaultAdapter();
				if (bluetoothAdapter == null) {
					((JJBaseApplication) getApplication())
							.showMessage(getResources().getString(
									R.string.unsupport_bluetooth));
					if (D2EConfigures.NO_BLUTH) {
						Intent intent = new Intent(SOPAndroidActivity.this,
								D2ELoginActivity.class);
						SOPAndroidActivity.this.startActivity(intent);
					}

					return;

				}

				// 判断蓝牙是否开启
				if (!bluetoothAdapter.isEnabled()) {
					Intent enableBtIntent = new Intent(
							BluetoothAdapter.ACTION_REQUEST_ENABLE);
					startActivityForResult(enableBtIntent,
							D2EMessageActivity.REQUEST_ENABLE_BT);
				} else {
					initServices();
				}
			}
		});
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {

		case D2EMessageActivity.REQUEST_ENABLE_BT: {
			if (resultCode == Activity.RESULT_OK) {
				initServices();
			} else {
				((JJBaseApplication) getApplication())
						.showMessage(getResources().getString(
								R.string.bluetooth_start_failed));
				Intent intent = new Intent(SOPAndroidActivity.this,
						D2ELoginActivity.class);
				SOPAndroidActivity.this.startActivity(intent);
				finish();
			}
		}
			break;

		case D2EMessageActivity.REQUESR_DEVICE: {
			if (resultCode == Activity.RESULT_CANCELED) {

				JJBaseApplication.g_btState = false;
				Intent intent = new Intent(SOPAndroidActivity.this,
						D2ELoginActivity.class);
				SOPAndroidActivity.this.startActivity(intent);
				finish();
				return;
			}

			SOPBluetoothService.g_btMacAddress = data.getExtras().getString(
					D2EMessageActivity.EXTRA_DEVICE_ADDRESS);
			SOPBluetoothService.g_readerID = data.getExtras().getString(
					D2EMessageActivity.EXTRA_READER_ID);

			SharedPreferences settings = getSharedPreferences(
					D2EMessageActivity.READER_PREFS_NAME, 0);
			Editor editor = settings.edit();
			editor.putString(D2EMessageActivity.BT_MAC_KEY,
					SOPBluetoothService.g_btMacAddress);
			editor.putString(D2EMessageActivity.READER_KEY,
					SOPBluetoothService.g_readerID);
			editor.commit();

			enterMainActivity();

		}
			break;

		}
	}

	private void initServices() {
		// 判断是否本地保存蓝牙设置
		SharedPreferences settings = getSharedPreferences(
				D2EMessageActivity.READER_PREFS_NAME, 0);
		SOPBluetoothService.g_btMacAddress = settings.getString(
				D2EMessageActivity.BT_MAC_KEY, null);
		SOPBluetoothService.g_readerID = settings.getString(
				D2EMessageActivity.READER_KEY, null);
		if (SOPBluetoothService.g_btMacAddress == null
				|| SOPBluetoothService.g_readerID == null) {
			Intent serverIntent = new Intent(this, D2EDeviceListActivity.class);
			startActivityForResult(serverIntent,
					D2EMessageActivity.REQUESR_DEVICE);
		} else {
			enterMainActivity();
		}
	}

	private void enterMainActivity() {
		g_testBt = true;
		((JJBaseApplication) getApplication()).startJJService();

	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// logo界面不允许退出
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void backAciton() {

	}

	@Override
	public boolean isExit() {
		return false;
	}

	@Override
	public void init() {

	}

	@Override
	public void taskSuccess(Object... param) {

	}

	@Override
	public void taskFailed(Object... param) {
		g_testBt = false;

		int values = ((Integer) param[0]).intValue();
		if (values == JJBaseService.BT_SERVICE_INT) {
			int state = ((Integer) param[1]).intValue();
			if (state == SOPBluetoothService.BT_DISCONNECT) {
				((JJBaseApplication) getApplication()).stopJJService();
				Intent serverIntent = new Intent(this,
						D2EDeviceListActivity.class);
				startActivityForResult(serverIntent,
						D2EMessageActivity.REQUESR_DEVICE);
				JJBaseApplication.g_btState = false;
			} else if (state == SOPBluetoothService.BT_CONNECT) {
				JJBaseApplication.g_btState = true;
				Intent intent = new Intent(SOPAndroidActivity.this,
						D2ELoginActivity.class);
				SOPAndroidActivity.this.startActivity(intent);
				finish();
			}
		}
	}

	@Override
	public void taskProcessing(Object... param) {

	}

}