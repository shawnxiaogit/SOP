package com.mobilercn.sop.activity;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import com.mobilercn.base.BadgeView;
import com.mobilercn.base.JJBaseActivity;
import com.mobilercn.base.JJBaseApplication;
import com.mobilercn.base.JJBaseService;
import com.mobilercn.config.D2EConfigures;
import com.mobilercn.sop.R;
import com.mobilercn.sop.bt.SOPBluetoothService;
import com.mobilercn.sop.data.TagItem;
import com.mobilercn.sop.data.TagsAdapter;
import com.mobilercn.task.JJTask;
import com.mobilercn.util.JJNetHelper;
import com.mobilercn.util.YTStringHelper;

public class D2ECustomerListActivity extends JJBaseActivity {

	private TagsAdapter mTagsAdapter;
	private GridView mGridView;
	private BadgeView mBadgeView;
	private String mselectID;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.d2e_customer_list);
		Window window = getWindow();
		window.setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.d2e_titlebar);

		JJBaseApplication.g_grid_type = D2EConfigures.GRID_CUSTOM;

		// grid
		mTagsAdapter = new TagsAdapter(getApplicationContext());

		TextView tv = (TextView) findViewById(R.id.title);
		tv.setText(getResources().getString(R.string.menu_worksheet));
		for (Object ob : JJBaseApplication.g_customerlist) {
			mTagsAdapter.addObject((TagItem) ob);
			mTagsAdapter.notifyDataSetChanged();
		}

		mBadgeView = (BadgeView) findViewById(R.id.countTask);
		mBadgeView.setBadgePosition(BadgeView.POSITION_CENTER);
		mBadgeView.setBackgroundResource(R.drawable.badge_ifaux);
		mBadgeView.setVisibility(View.VISIBLE);
		if (JJBaseApplication.g_selecteDate != null
				&& JJBaseApplication.g_selecteDate.length() > 0) {
			String selectDate = JJBaseApplication.g_selecteDate.substring(2, 8);
			if (D2EConfigures.TEST) {
				Log.e("selectDate------------>", selectDate);
				Log.e("D2EConfigures.isD2EConfiguresG_worksheetcontainsDate(selectDate)------------->",
						""
								+ JJBaseApplication
										.isD2EConfiguresG_worksheetcontainsDate(selectDate));
			}
			if (JJBaseApplication
					.isD2EConfiguresG_worksheetcontainsDate(selectDate)) {
				mBadgeView.setText(JJBaseApplication.g_worksheet
						.get(selectDate));
			}
		}

		mGridView = (GridView) findViewById(R.id.public_gridview);
		mGridView.setAdapter(mTagsAdapter);
		mGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				if (D2EConfigures.G_USEVIRCALDEVICE) {
					TagItem obj = (TagItem) mTagsAdapter.getItem(arg2);
					JJBaseApplication.g_selecteCustomer = obj.getTitle();
					String response = "[{\"ID\":\"50\",\"Title\":\"\u9996\u6b21\u670d\u52a1\",\"SalesClientID\":\"19\",\"ReaderID\":\"48\","
							+ "\"ParentID\":\"49\",\"Description\":\"\",\"SignClauseID\":\"19\",\"OtherPayment\":null,\"BeginTime\":"
							+ "\"2012-06-11 08:00:00\",\"EndTime\":\"2012-06-11 10:00:00\",\"CreateTime\":\"2012-06-04 15:17:15\","
							+ "\"CreateUserID\":\"41\",\"CreateUserName\":\"Felix\",\"Status\":\"Create\","
							+ "\"CheckInTime\":null,\"CheckOutTime\":null,\"Division\":\"PCO\",\"DivisionID\":\"6\"}]";
					JJBaseApplication.g_worksheetlist.clear();
					if (response != null && response.length() > 0) {
						D2EWorksheetList dps = new D2EWorksheetList();
						try {
							String address = "浦东张江";
							int len = 1;
							for (int i = 0; i < len; i++) {
								D2EWorksheetList.WorkSheetItem item = dps.new WorkSheetItem(
										"起始时间:" + "10月31号 星期三 15:00",
										"工        时：" + "2小时",
										"服务内容:" + "常规服务", "客户地址:" + address);
								JJBaseApplication.g_worksheetlist.add(item);
							}

							Intent intent = new Intent(
									D2ECustomerListActivity.this,
									D2EWorksheetList.class);
							D2ECustomerListActivity.this.startActivity(intent);
							overridePendingTransition(R.anim.fade, R.anim.hold);
							finish();
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				} else {

					TagItem obj = (TagItem) mTagsAdapter.getItem(arg2);

					mselectID = obj.getId();
					JJBaseApplication.g_selecteCustomer = obj.getTitle();
					String[] params = new String[] { "FunType",
							"getTask_bySalesClient", "UserID",
							JJBaseApplication.user_ID, "SalesClientID",
							obj.getId(), "Date",
							JJBaseApplication.g_selecteDate };
					if (D2EConfigures.TEST) {
						YTStringHelper.array2string(params);
					}
					long id = JJBaseService
							.addMutilpartHttpTask(
									"http://www.ohpest.com/ohpest-pco/webservices/get/data.html",
									params, D2ECustomerListActivity.this
											.getClass().getName(),
									D2EConfigures.TASK_GETWORKSHEETBYDATE);

					setCurTaskID(id);
					showWaitDialog(getResources().getString(R.string.handling));
				}

			}

		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		mBadgeView.setText(JJBaseApplication.user_CountTask);
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
					Log.e("response-------->", "" + response);
				}
				ins.close();

				try {
					JSONObject j = new JSONObject(response);
					String err = j.getString("error");
					if (err != null && err.length() > 0) {
						((JJBaseApplication) getApplication()).showMessage(err);
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
			if (task.getTaskId() == D2EConfigures.TASK_GETWORKSHEETBYDATE) {
				// [{"ID":"50","Title":"\u9996\u6b21\u670d\u52a1","SalesClientID":"19","ReaderID":"48","ParentID":"49","Description":"","SignClauseID":"19","OtherPayment":null,"BeginTime":"2012-06-11 08:00:00","EndTime":"2012-06-11 10:00:00","CreateTime":"2012-06-04 15:17:15","CreateUserID":"41","CreateUserName":"Felix","Status":"Create","CheckInTime":null,"CheckOutTime":null,"Division":"PCO","DivisionID":"6"}]
				JJBaseApplication.g_worksheetlist.clear();
				if (response != null && response.length() > 0) {
					D2EWorksheetList dps = new D2EWorksheetList();
					try {
						JSONObject js = new JSONObject(response);
						String address = js.getString("Address");
						JSONArray ja = new JSONArray(js.getString("TaskData"));
						int len = ja.length();
						for (int i = 0; i < len; i++) {
							String tmp = ja.getString(i);
							JSONObject jo = new JSONObject(tmp);
							SimpleDateFormat mFormat = new SimpleDateFormat(
									"yyyy-MM-dd HH:mm:ss");
							Date beginTime1 = mFormat.parse(jo
									.getString("BeginTime"));
							Date endTime1 = mFormat.parse(jo
									.getString("EndTime"));
							// add by shawn 2012-10-29
							// 计算工时，并显示
							// 开始的毫秒数

							String strGongshi = getGongShi(beginTime1, endTime1);
							if (D2EConfigures.TEST) {
								Log.e("工时==================>", strGongshi);
							}
							// End

							if (D2EConfigures.TEST) {
								Log.e("BeginTime----------->", "" + beginTime1);
								Log.e("BeginTime----------->", "" + endTime1);
							}
							DateFormat mDateFormat = new SimpleDateFormat(
									"EEE HH:mm", java.util.Locale.US);// EEE表示星期
																		// US把星期改为英文简写
							D2EWorksheetList.WorkSheetItem item = dps.new WorkSheetItem(
									getResources().getString(
											R.string.worksheet_start_time)
											+ mDateFormat.format(beginTime1),
									getResources().getString(
											R.string.worksheet_gongshi)
											+ strGongshi
											+ getResources().getString(
													R.string.worksheet_hour),
									getResources().getString(
											R.string.worksheet_content)
											+ jo.getString("Title"),
									getResources().getString(
											R.string.worksheet_addr)
											+ address);
							// add by shawn 2012-10-29 Begin
							// 保存工单的id，用来区分工单的不同
							if (D2EConfigures.TEST) {
								Log.e("工单ID===========>", jo.getString("ID"));
							}
							item.setmId(jo.getString("ID"));
							// End
							// --------------------------------
							JJBaseApplication.g_worksheetlist.add(item);
						}

						Intent intent = new Intent(
								D2ECustomerListActivity.this,
								D2EWorksheetList.class);
						intent.putExtra("selectId", mselectID);
						D2ECustomerListActivity.this.startActivity(intent);
						overridePendingTransition(R.anim.fade, R.anim.hold);
						finish();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}

			}
		}
		dismissProcessDialog();
	}

	/**
	 * 计算工时
	 */
	private String getGongShi(Date beginTime, Date endTime) {
		long beginSecond = beginTime.getTime();
		// 结束的毫秒数
		long endSecond = endTime.getTime();
		double beinHour = (beginSecond / (1000 * 60 * 60 * 1.0));
		double endHour = (endSecond / (1000 * 60 * 60 * 1.0));
		double gongShi = (endHour - beinHour);
		DecimalFormat mDecimalFormat = new DecimalFormat("0.0");
		return mDecimalFormat.format(gongShi);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		super.onPrepareOptionsMenu(menu);
		menu.clear();
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.back_menu, menu);
		return true;
	}

	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {

		case R.id.menu_back: {
			backAciton();
		}
			break;

		}
		return true;
	}

	@Override
	public void backAciton() {
		Intent intent = new Intent(D2ECustomerListActivity.this,
				D2EWorksheetActivity.class);
		D2ECustomerListActivity.this.startActivity(intent);
		overridePendingTransition(R.anim.fade, R.anim.hold);
		finish();
	}

	@Override
	public boolean isExit() {
		return false;
	}

	@Override
	public void init() {

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
