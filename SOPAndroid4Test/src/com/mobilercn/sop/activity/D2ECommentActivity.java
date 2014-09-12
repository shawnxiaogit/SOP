package com.mobilercn.sop.activity;

import java.io.InputStream;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.mobilercn.base.BadgeView;
import com.mobilercn.base.JJBaseActivity;
import com.mobilercn.base.JJBaseApplication;
import com.mobilercn.base.JJBaseService;
import com.mobilercn.config.D2EConfigures;
import com.mobilercn.sop.R;
import com.mobilercn.sop.activity.D2EWorksheetList.WorkSheetItem;
import com.mobilercn.sop.bt.SOPBluetoothService;
import com.mobilercn.sop.item.D2EListAdapter;
import com.mobilercn.sop.item.D2EListAdapterItam;
import com.mobilercn.task.JJTask;
import com.mobilercn.util.JJNetHelper;
import com.mobilercn.util.YTStringHelper;
import com.mobilercn.widget.YTEditText;

public class D2ECommentActivity extends JJBaseActivity {

	private BadgeView mBadgeView;

	private YTEditText mMemo;
	private RelativeLayout comment_panel_choseclient;
	private AutoCompleteTextView comment_ckpoint_state;
	private Button comment_loginImageButton02;
	private D2EListAdapter mTagsAdapter;
	TextWatcher mWatcher = new TextWatcher() {

		// @Override
		public void afterTextChanged(Editable arg0) {

		}

		// @Override
		public void beforeTextChanged(CharSequence str, int arg1, int arg2,
				int arg3) {

		}

		// @Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			mTagsAdapter.getFilter().filter(s);
			mTagsAdapter.notifyDataSetInvalidated();
		}
	};

	protected void onResume() {
		super.onResume();
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.d2e_report_memo);
		Window window = getWindow();
		window.setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.d2e_titlebar);

		TextView tv = (TextView) findViewById(R.id.title);
		tv.setText(getResources().getString(R.string.now_reflect));

		mMemo = (YTEditText) findViewById(R.id.report_memo);

		mBadgeView = (BadgeView) findViewById(R.id.countTask);
		mBadgeView.setBadgePosition(BadgeView.POSITION_CENTER);
		mBadgeView.setBackgroundResource(R.drawable.badge_ifaux);
		mBadgeView.setVisibility(View.GONE);
		if (JJBaseApplication.user_CountTask != null
				&& JJBaseApplication.user_CountTask.length() > 0) {
			mBadgeView.setText(JJBaseApplication.user_CountTask);
		}
		mTagsAdapter = new D2EListAdapter(getApplicationContext());
		comment_panel_choseclient = (RelativeLayout) this
				.findViewById(R.id.comment_panel_choseclient);
		comment_ckpoint_state = (AutoCompleteTextView) this
				.findViewById(R.id.comment_ckpoint_state);
		comment_loginImageButton02 = (Button) this
				.findViewById(R.id.comment_loginImageButton02);
		if (JJBaseApplication.g_customer != null
				&& JJBaseApplication.g_customer.mName != null
				&& JJBaseApplication.g_customer.mName.length() > 0) {
			comment_ckpoint_state.setText(JJBaseApplication.g_customer.mName);
		} else {
			comment_ckpoint_state.setText("");
		}

		if (D2EConfigures.G_USEVIRCALDEVICE) {
			comment_ckpoint_state.setText("可口可乐");
		}

		// modify by shawn 2012-9-4
		// ------------------
		// 如果保存客户的列表信息不为空，则用保存的信息
		if (D2EConfigures.TEST) {
			Log.e("JJBaseApplication.mClientList.size()>0===========>", ""
					+ (JJBaseApplication.mClientList.size() > 0));
		}
		if (JJBaseApplication.mClientList.size() > 0) {
			for (Object item : JJBaseApplication.mClientList) {
				if (item instanceof D2EListAdapterItam) {
					mTagsAdapter.addObject((D2EListAdapterItam) item);
					mTagsAdapter.notifyDataSetChanged();

				}
			}
		}
		// 否则请求网络重新获取客户信息
		else {
			String[] params = new String[] { "FunType", "getSalesClientList",
					"OrgID", JJBaseApplication.user_OrgID, "KeyWord", "" };

			if (D2EConfigures.TEST) {
				YTStringHelper.array2string(params);
			}
			long id = JJBaseService
					.addMutilpartHttpTask(
							"http://www.ohpest.com/ohpest-pco/webservices/get/data.html",
							params, D2ECommentActivity.this.getClass()
									.getName(),
							D2EConfigures.TASK_GETCUSTOMERSBYUSER);
			showWaitDialog(getResources().getString(R.string.get_custom_data));

			setCurTaskID(id);
		}
		// --------------------

		comment_ckpoint_state.setThreshold(1);
		comment_ckpoint_state.addTextChangedListener(mWatcher);
		comment_ckpoint_state.setAdapter(mTagsAdapter);
		comment_ckpoint_state.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long id) {
				D2EListAdapterItam item = (D2EListAdapterItam) mTagsAdapter
						.getItem(position);

				comment_ckpoint_state.setText(item.getTitle());

				JJBaseApplication.user_SalesClientID = item.getId();

			}

		});

		comment_loginImageButton02.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				// modify by shawn 2012-9-4
				// 选择了一个客户以后，再次点击下拉按钮的时候清楚原来的以便可以从新选择
				// ------------
				String strCoustom = comment_ckpoint_state.getText().toString();
				if (strCoustom != null && strCoustom.length() > 0) {
					comment_ckpoint_state.setText("");
				}
				// ----------------
				comment_ckpoint_state.showDropDown();
				comment_ckpoint_state.setThreshold(1);
			}
		});

	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		super.onPrepareOptionsMenu(menu);
		menu.clear();
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.back_submit_menu, menu);
		return true;
	}

	public boolean onMenuItemSelected(int featureId, MenuItem item) {

		switch (item.getItemId()) {

		case R.id.menu_back: {
			Intent intent = new Intent(D2ECommentActivity.this,
					D2EHelpActivity.class);
			D2ECommentActivity.this.startActivity(intent);
			overridePendingTransition(R.anim.fade, R.anim.hold);
			D2ECommentActivity.this.finish();
		}
			break;

		case R.id.menu_submit: {
			if (mMemo.getText().toString() != null
					&& mMemo.getText().toString().length() > 0) {

				if (D2EConfigures.G_USEVIRCALDEVICE) {
					((JJBaseApplication) getApplication())
							.showMessage(getResources().getString(
									R.string.submit_success));
				} else {
					String[] params = new String[] { "FunType",
							"insertCustomerAdvice", "OrgID",
							JJBaseApplication.user_OrgID, "SalesClientID",
							JJBaseApplication.user_SalesClientID, "UserID",
							JJBaseApplication.user_ID, "Memo",
							mMemo.getText().toString() };
					if (D2EConfigures.TEST) {
						YTStringHelper.array2string(params);
					}
					long id = JJBaseService
							.addMutilpartHttpTask(
									"http://www.ohpest.com/ohpest-pco/webservices/get/data.html",
									params, D2ECommentActivity.this.getClass()
											.getName(),
									D2EConfigures.TASK_SEND_PROBLEM);

					setCurTaskID(id);
					showWaitDialog(getResources().getString(
							R.string.handling_wait));
				}
			} else {
				((JJBaseApplication) getApplication())
						.showMessage(getResources().getString(
								R.string.please_comlete_data));
			}

		}
			break;

		}
		return true;
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
		int values = ((Integer) param[0]).intValue();
		if (values == JJBaseService.BT_SERVICE_INT) {
			dismissProcessDialog();
		} else if (values == JJBaseService.HTTP_SERVICE_INT) {
			JJTask task = (JJTask) param[1];
			String response = null;
			try {
				InputStream ins = task.getInputStream();
				byte[] bytes = JJNetHelper.readByByte(ins, -1);

				response = new String(bytes, "UTF-8");
				if (D2EConfigures.TEST) {
					Log.e("response-------------->", "" + response);
				}
				ins.close();

				try {
					JSONObject j = new JSONObject(response);
					String err = j.getString("error");
					if (err != null && err.length() > 0) {
						((JJBaseApplication) getApplication()).showMessage(err);
						dismissProcessDialog();
						comment_panel_choseclient.setVisibility(View.VISIBLE);
						mMemo.setVisibility(View.INVISIBLE);
						((JJBaseApplication) getApplication())
								.showMessage(getResources().getString(
										R.string.choose_a_custom));
						dismissProcessDialog();
						return;
					}
				} catch (Exception ex) {
				}
			} catch (Exception ex) {
			}
			if (response == null || response.length() == 0) {
				((JJBaseApplication) getApplication())
						.showMessage(getResources().getString(
								R.string.hand_failed_try_again));
				dismissProcessDialog();
				return;
			}
			if (task.getTaskId() == D2EConfigures.TASK_SEND_PROBLEM) {
				try {
					JSONObject jo = new JSONObject(response);
					String success = jo.getString("success");
					((JJBaseApplication) getApplication()).showMessage(success);
					// modify by shawn 2012-9-4
					// --------------------
					// 如果提交成功，则跳转上个界面
					if (success != null && success.length() > 0) {
						Intent intent = new Intent(D2ECommentActivity.this,
								D2EHelpActivity.class);
						D2ECommentActivity.this.startActivity(intent);
						overridePendingTransition(R.anim.fade, R.anim.hold);
						D2ECommentActivity.this.finish();
					}
					// ---------------------
				} catch (Exception ex) {
				}
			} else if (task.getTaskId() == D2EConfigures.TASK_GETCUSTOMERSBYUSER) {
				JJBaseApplication.mClientList.clear();
				try {
					JSONArray ja = new JSONArray(response);
					int len = ja.length();
					for (int i = 0; i < len; i++) {
						String tmp = ja.getString(i);
						JSONObject jo = new JSONObject(tmp);

						D2EListAdapterItam item = new D2EListAdapterItam(
								jo.getString("Name"), jo.getString("ID"));
						JJBaseApplication.mClientList.add(item);
					}
				} catch (Exception ex) {
				}
				for (Object item : JJBaseApplication.mClientList) {
					if (item instanceof D2EListAdapterItam) {
						mTagsAdapter.addObject((D2EListAdapterItam) item);
						mTagsAdapter.notifyDataSetChanged();

					}
				}
			}

		}
		dismissProcessDialog();
	}

	@Override
	public void taskFailed(Object... param) {
		dismissProcessDialog();
		int values = ((Integer) param[0]).intValue();
		if (values == JJBaseService.HTTP_SERVICE_INT) {
			((JJBaseApplication) getApplication()).showMessage(getResources()
					.getString(R.string.neterror));
		} else if (values == JJBaseService.BT_SERVICE_INT) {
			int state = ((Integer) param[1]).intValue();
			if (state != SOPBluetoothService.BT_DISCONNECT
					&& state != SOPBluetoothService.BT_CONNECT) {
				((JJBaseApplication) getApplication())
						.showMessage(getResources().getString(R.string.bterror));
			} else if (state == SOPBluetoothService.BT_DISCONNECT) {
				JJBaseApplication.g_btState = false;
			} else if (state == SOPBluetoothService.BT_CONNECT) {
				JJBaseApplication.g_btState = true;
			}
			btStateChange();
		}
	}

	@Override
	public void taskProcessing(Object... param) {

	}

}
