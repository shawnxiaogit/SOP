package com.mobilercn.sop.activity;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

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
import com.mobilercn.sop.bt.SOPBTCallHelper;
import com.mobilercn.sop.bt.SOPBluetoothService;
import com.mobilercn.sop.data.MyCustomerListData;
import com.mobilercn.sop.data.TagItem;
import com.mobilercn.sop.data.TagsAdapter;
import com.mobilercn.sop.item.D2ECustomer;
import com.mobilercn.sop.item.D2EDocumentItem;
import com.mobilercn.sop.item.D2EDocuments;
import com.mobilercn.sop.item.D2EPerson;
import com.mobilercn.task.JJTask;
import com.mobilercn.util.JJNetHelper;
import com.mobilercn.util.YTFileHelper;
import com.mobilercn.util.YTStringHelper;

public class D2ECustomerActivity extends JJBaseActivity {

	public static final int TYPE_SOP = 1;
	public static final int TYPE_WORKSHEET = 2;
	public static final String PAGE_TYPE = "pagetype";

	private TagsAdapter mTagsAdapter;
	private GridView mGridView;
	private BadgeView mBadgeView;
	private int mPageType;

	private String mSelectID;
	private String mSelectName;

	protected void onResume() {
		super.onResume();
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.d2e_customer_page);
		Window window = getWindow();
		window.setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.d2e_titlebar);

		JJBaseApplication.g_grid_type = D2EConfigures.GRID_CUSTOM;

		// grid
		mTagsAdapter = new TagsAdapter(getApplicationContext());

		Intent intent = getIntent();
		mPageType = intent.getIntExtra(PAGE_TYPE, TYPE_SOP);

		TextView tv = (TextView) findViewById(R.id.title);
		if (mPageType == TYPE_WORKSHEET) {
			tv.setText(getResources().getString(R.string.menu_worksheet));
		} else {
			tv.setText(getResources().getString(R.string.help_init));
			for (Object ob : JJBaseApplication.mCustomerList) {
				if (ob instanceof TagItem) {
					mTagsAdapter.addObject((TagItem) ob);
					mTagsAdapter.notifyDataSetChanged();
				}
			}

		}

		mBadgeView = (BadgeView) findViewById(R.id.countTask);
		mBadgeView.setBadgePosition(BadgeView.POSITION_CENTER);
		mBadgeView.setBackgroundResource(R.drawable.badge_ifaux);
		mBadgeView.setVisibility(View.GONE);
		if (JJBaseApplication.user_CountTask != null
				&& JJBaseApplication.user_CountTask.length() > 0) {
			mBadgeView.setText(JJBaseApplication.user_CountTask);
		}

		if (D2EConfigures.G_USEVIRCALDEVICE) {
			mTagsAdapter.addObject("肯德基", "1");
			mTagsAdapter.addObject("麦当劳", "2");
			mTagsAdapter.addObject("小南国", "3");
			mTagsAdapter.addObject("宝莱纳", "4");
		}

		mGridView = (GridView) findViewById(R.id.public_gridview);
		mGridView.setAdapter(mTagsAdapter);
		mGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (mPageType == TYPE_WORKSHEET) {
					Intent intent = new Intent(D2ECustomerActivity.this,
							D2EWorksheetList.class);
					D2ECustomerActivity.this.startActivity(intent);
					overridePendingTransition(R.anim.fade, R.anim.hold);
					finish();
				} else {
					// add by shawn 2012-10-19 Begin
					// 将选中的客户保存为一个全局变量，在新增作业点标签界面，返回的时候重新请求网络获取客户下面的位置标签
					JJBaseApplication.clickCustomer = (TagItem) mTagsAdapter
							.getItem(arg2);
					// End
					TagItem obj = (TagItem) mTagsAdapter.getItem(arg2);
					if (obj.getTitle().equals("")
							&& obj.getId().equals(D2EConfigures.DONE_VALUE)) {// 进入SOP流程
						showProcessDialog(getResources().getString(
								R.string.reading_card));
						SOPBluetoothService
								.getService()
								.doTask(SOPBTCallHelper.READ_TAG,
										D2ECustomerActivity.class.getName(),
										SOPBTCallHelper
												.getInitOrder(SOPBluetoothService.g_readerID));
					} else {

						if (D2EConfigures.G_USEVIRCALDEVICE) {
							Intent intent = new Intent();
							intent.setClass(D2ECustomerActivity.this,
									D2EAddLocationActivity.class);
							intent.putExtra(D2EAddLocationActivity.CSID,
									mSelectID);
							intent.putExtra(D2EAddLocationActivity.CSNAME,
									"肯德基(KFC)");
							D2ECustomerActivity.this.startActivity(intent);
							overridePendingTransition(R.anim.fade, R.anim.hold);
						} else {
							// 取位置标签信息
							mSelectID = obj.getId();
							mSelectName = obj.getTitle();
							JJBaseApplication.strSelectLocationID = obj.getId();
							JJBaseApplication.strSelectCustomerName = mSelectName;
							String[] params = new String[] { "FunType",
									"getSOPPositionList", "OrgID",
									JJBaseApplication.user_OrgID,
									"SalesClientID", obj.getId() };
							YTStringHelper.array2string(params);
							long id = JJBaseService
									.addMutilpartHttpTask(
											"http://www.ohpest.com/ohpest-pco/webservices/get/data.html",
											params, D2ECustomerActivity.this
													.getClass().getName(),
											D2EConfigures.TASK_GETLOCATIONBYCUS);
							setCurTaskID(id);
							showWaitDialog(getResources().getString(
									R.string.handling));
						}
					}
				}
			}

		});
	}

	@Override
	public void backAciton() {
		if (mPageType == TYPE_WORKSHEET) {
			Intent intent = new Intent(D2ECustomerActivity.this,
					D2EWorksheetActivity.class);
			D2ECustomerActivity.this.startActivity(intent);
			overridePendingTransition(R.anim.fade, R.anim.hold);
			finish();
		} else {
			Intent intent = new Intent(D2ECustomerActivity.this,
					D2EHelpActivity.class);
			D2ECustomerActivity.this.startActivity(intent);
			overridePendingTransition(R.anim.fade, R.anim.hold);
			finish();
		}
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
			String response = (String) param[2];
			if (response.toLowerCase().equals(
					SOPBluetoothService.STATE_FAILED.toLowerCase())) {
				((JJBaseApplication) getApplication())
						.showMessage(getResources().getString(
								R.string.failed_read));
				dismissProcessDialog();
				return;
			} else {
				String locationID = response.substring(14, 22);

				if (D2EConfigures.G_DEBUG) {
					locationID = "93edb888";
				}

				if (D2EConfigures.G_USEVIRCALDEVICE) {

					response = "{"
							+ "\"Task\":[{\"ID\":\"64\",\"Title\":\"\u533a\u57df\u670d\u52a1\""
							+ ",\"SignClauseID\":\"19\",\"Serial\":\"DD2012062319-16\",\"Description\":\"\",\"BeginTime\":\"2012-06-23 19:30:00\",\"EndTime\":\"2012-06-23 20:30:00\"}],"
							+ "\"Assignment\":[{\"ID\":\"57\",\"Name\":\"\u5458\u5de56\"},{\"ID\":\"58\",\"Name\":\"\u5458\u5de57\"},{\"ID\":\"59\",\"Name\":\"\u5458\u5de58\"},"
							+ "{\"ID\":\"60\",\"Name\":\"\u8001\u5458\u5de5\"},{\"ID\":\"61\",\"Name\":\"\u65b0\u5458\u5de5\"}],"
							+ "\"SOPPositionList\":[{\"ID\":\"1\",\"Division\":\"PCO\",\"DivisionID\":\"6\",\"SalesClientID\":\"19\",\"TagID\":\"12\","
							+ "\"Name\":\"\u53ef\u53e3\u53ef\u4e50\u4ed3\u5e93\",\"Memo\":null,\"StateID\":null,\"CityID\":null,\"Address\":null,"
							+ "\"Status\":\"Open\",\"CreateTime\":\"2012-06-23 11:48:47\",\"DeleteTime\":null}],"
							+ "\"SalesClientData\":[{\"ID\":\"19\",\"Division\":\"PCO\",\"DivisionID\":\"6\","
							+ "\"Type\":\"Enterprise\",\"UserID\":\"87\",\"Name\":\"\u53ef\u53e3\u53ef\u4e50\",\"Longitude\":\"121.448326\",\"Latitude\":\"31.021942\",\"SalesClientType\":\"\u6d4b\u8bd5\u5ba2\u6237\"}]} ";

					if (response != null && response.length() > 0) {

						try {
							JSONObject res = new JSONObject(response);
							JSONArray salesClientAry = new JSONArray(
									res.getString("SalesClientData"));
							JSONObject salesClient = new JSONObject(
									salesClientAry.getString(0));

							if (JJBaseApplication.g_customer != null) {
								JJBaseApplication.g_customer.dealloc();
								JJBaseApplication.g_customer = null;
							}

							JJBaseApplication.g_customer = new D2ECustomer();
							JJBaseApplication.g_customer.mId = salesClient
									.getString("ID");
							JJBaseApplication.g_customer.mName = "肯德基餐厅";
							JJBaseApplication.g_customer.mType = salesClient
									.getString("SalesClientType");

							// 施工人员
							JSONArray Assignment = new JSONArray(
									res.getString("Assignment"));
							int cnt = Assignment.length();
							for (int i = 0; i < cnt; i++) {
								String tmp = Assignment.getString(i);
								JSONObject jo = new JSONObject(tmp);
								D2EPerson p = new D2EPerson(jo.getString("ID"),
										jo.getString("Name"), "");
								JJBaseApplication.g_customer.mPersons.add(p);
							}

							// 位置标签
							JSONArray SOPPositionList = new JSONArray(
									res.getString("SOPPositionList"));
							int len = SOPPositionList.length();
							for (int i = 0; i < len; i++) {
								String tmp = SOPPositionList.getString(i);
								JSONObject jo = new JSONObject(tmp);

								TagItem item = new TagItem(
										jo.getString("Name"),
										jo.getString("ID"));
								item.setTagNum(jo.getString("TagID"));
								JJBaseApplication.g_customer.mPositions
										.add(item);
							}

						} catch (Exception ex) {
						}

						Intent intent = new Intent();
						intent.setClass(D2ECustomerActivity.this,
								D2ESOPActivity.class);
						D2ECustomerActivity.this.startActivity(intent);
						overridePendingTransition(R.anim.fade, R.anim.hold);
						finish();
						return;
					}
				}

				String[] params = new String[] { "FunType",
						"getSalesClientDataWithTask", "OrgID",
						JJBaseApplication.user_OrgID, "ReadNum",
						SOPBluetoothService.g_readerID, "TagNum", locationID,
						"UserID", JJBaseApplication.user_ID };
				long id = JJBaseService
						.addMutilpartHttpTask(
								"http://www.ohpest.com/ohpest-pco/webservices/get/data.html",
								params, D2ECustomerActivity.this.getClass()
										.getName(),
								D2EConfigures.TASK_GET_CUSTOM_INFO);
				setCurTaskID(id);
				return;
			}
		} else if (values == JJBaseService.HTTP_SERVICE_INT) {
			JJTask task = (JJTask) param[1];
			String response = null;
			try {
				InputStream ins = task.getInputStream();
				byte[] bytes = JJNetHelper.readByByte(ins, -1);

				response = new String(bytes, "UTF-8");
				ins.close();

				if (D2EConfigures.G_DEBUG) {
					Log.e("response: >>>>>> ", response);
				}
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
			if (task.getTaskId() == D2EConfigures.TASK_GETLOCATIONBYCUS) {

				if (response != null && response.length() > 0) {
					try {
						JJBaseApplication.g_sop_locations.clear();
						JSONArray ja = new JSONArray(response);
						int len = ja.length();
						for (int i = 0; i < len; i++) {
							String tmp = ja.getString(i);
							JSONObject jo = new JSONObject(tmp);
							TagItem item = new TagItem(jo.getString("Name"),
									jo.getString("ID"));
							item.setTagNum(jo.getString("TagID"));

							JJBaseApplication.g_sop_locations.add(item);
						}
					} catch (Exception ex) {
					}
				}

				Intent intent = new Intent();
				intent.setClass(D2ECustomerActivity.this,
						D2EAddLocationActivity.class);
				intent.putExtra(D2EAddLocationActivity.CSID, mSelectID);
				intent.putExtra(D2EAddLocationActivity.CSNAME, mSelectName);
				D2ECustomerActivity.this.startActivity(intent);
				overridePendingTransition(R.anim.fade, R.anim.hold);
				finish();
			} else if (task.getTaskId() == D2EConfigures.TASK_GET_CUSTOM_INFO) {
				// {"SOPPositionList":[{"ID":"1","Division":"PCO","DivisionID":"6","SalesClientID":"19","TagID":"12","Name":"\u65a4\u65a4\u8ba1\u8f83","Memo":null,"StateID":null,"CityID":null,"Address":null,"Status":"Open","CreateTime":"2012-05-31 10:55:17","DeleteTime":null}],"SalesClientData":[{"ID":"19","Division":"PCO","DivisionID":"6","Type":"Enterprise","UserID":"87","Name":"\u5ba2\u670d1","Longitude":"","Latitude":""}]}

				if (response != null && response.length() > 0) {
					try {
						JSONObject res = new JSONObject(response);
						JSONArray salesClientAry = new JSONArray(
								res.getString("SalesClientData"));
						JSONObject salesClient = new JSONObject(
								salesClientAry.getString(0));

						if (JJBaseApplication.g_customer != null) {
							JJBaseApplication.g_customer.dealloc();
							JJBaseApplication.g_customer = null;
						}

						JJBaseApplication.g_customer = new D2ECustomer();
						JJBaseApplication.g_customer.mId = salesClient
								.getString("ID");
						JJBaseApplication.g_customer.mName = salesClient
								.getString("Name");
						JJBaseApplication.g_customer.mType = salesClient
								.getString("SalesClientType");

						// 施工人员
						JSONArray Assignment = new JSONArray(
								res.getString("Assignment"));
						int cnt = Assignment.length();
						for (int i = 0; i < cnt; i++) {
							String tmp = Assignment.getString(i);
							JSONObject jo = new JSONObject(tmp);
							D2EPerson p = new D2EPerson(jo.getString("ID"),
									jo.getString("Name"), "");
							JJBaseApplication.g_customer.mPersons.add(p);
						}

						// 工单信息
						JSONObject Task = new JSONObject(res.getString("Task"));
						JSONObject jt = new JSONObject(Task.getString("0"));
						JJBaseApplication.g_customer.mSerial = jt
								.getString("Serial");
						JJBaseApplication.g_customer.mMemo = jt
								.getString("Description");
						JJBaseApplication.g_customer.mTaskId = jt
								.getString("ID");

						// 正常的服务是1 部署是2 监测是3 额外是4
						JJBaseApplication.g_customer.mTaskType = Task
								.getInt("TaskType");

						// 位置标签
						JSONArray SOPPositionList = new JSONArray(
								res.getString("SOPPositionList"));
						int len = SOPPositionList.length();
						for (int i = 0; i < len; i++) {
							String tmp = SOPPositionList.getString(i);
							JSONObject jo = new JSONObject(tmp);

							TagItem item = new TagItem(jo.getString("Name"),
									jo.getString("ID"));
							// TageID是位置的Id
							item.setTagNum(jo.getString("TagID"));
							JJBaseApplication.g_customer.mPositions.add(item);
						}

					} catch (Exception ex) {
					}
					// modify by shawn 2012-11-12 Begin
					// 由于要保存多次的客户信息，因此把客户信息保存为列表，每次取的时候获取指定工单号的客户信息
					YTFileHelper ytfileHelper = YTFileHelper.getInstance();
					ytfileHelper.setContext(getApplicationContext());
					MyCustomerListData myCustomerListData = null;
					if ((MyCustomerListData) ytfileHelper
							.deSerialObject(getResources().getString(
									R.string.customer_list)) != null) {
						myCustomerListData = (MyCustomerListData) ytfileHelper
								.deSerialObject(getResources().getString(
										R.string.customer_list));
					} else {
						myCustomerListData = MyCustomerListData.getInstance();
					}

					if (!myCustomerListData
							.isContainWeekSerialCustomer(JJBaseApplication.g_customer)) {
						myCustomerListData
								.addCustomer(JJBaseApplication.g_customer);
					} else {
						JJBaseApplication.g_customer = myCustomerListData
								.getWeekSerialCustomer(JJBaseApplication.g_customer);
					}

					ytfileHelper.serialObject(
							getResources().getString(R.string.customer_list),
							myCustomerListData);
					// End

					if (JJBaseApplication.g_customer.mSerial != null
							&& JJBaseApplication.g_customer.mSerial.length() > 0) {
						Intent intent = new Intent();
						intent.setClass(D2ECustomerActivity.this,
								D2ESOPActivity.class);
						D2ECustomerActivity.this.startActivity(intent);
						overridePendingTransition(R.anim.fade, R.anim.hold);
						finish();
					} else {
						((JJBaseApplication) getApplication())
								.showMessage(getString(R.string.no_weeksheet_data));
					}
				}

			} else if (task.getTaskId() == D2EConfigures.TASK_GETSITE) {
				try {
					JSONObject jo = new JSONObject(response);
					JJBaseApplication.g_documents.clear();
					// 先解析类型
					JSONObject Support = new JSONObject(jo.getString("Support"));
					JSONObject types = new JSONObject(jo.getString("Type"));
					JSONArray names = types.names();
					int len = names.length();
					for (int i = 0; i < len; i++) {
						String name = names.getString(i);
						String value = types.getString(name);

						String itemvalues = null;
						try {
							itemvalues = Support.getString(name);
						} catch (Exception e) {
						}
						// 根据类型解析文档
						if (itemvalues != null && itemvalues.length() > 0) {

							D2EDocuments d = new D2EDocuments();
							d.mType = name;

							d.mTitle = value;

							JSONArray tmp = new JSONArray(
									Support.getString(name));
							int cnt = tmp.length();
							for (int m = 0; m < cnt; m++) {
								JSONObject s = new JSONObject(tmp.getString(m));

								D2EDocumentItem item = new D2EDocumentItem();
								item.mID = s.getString("ID");
								item.mMemo = s.getString("Memo");
								item.mTime = s.getString("CreateTime");
								item.mType = value;
								try {
									item.mTitle = s.getString("Title");
								} catch (Exception ex) {
								}

								d.addItem(item);
							}

							JJBaseApplication.g_documents.add(d);
						} else {
							continue;
						}
					}
					Intent intent = new Intent(D2ECustomerActivity.this,
							D2EHelpActivity.class);
					D2ECustomerActivity.this.startActivity(intent);
					overridePendingTransition(R.anim.fade, R.anim.hold);
					finish();
				} catch (Exception ex) {
					ex.printStackTrace();
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

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		super.onPrepareOptionsMenu(menu);
		menu.clear();
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.back_menu_change, menu);
		return true;
	}

	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		final int id = item.getItemId();

		switch (id) {
		case R.id.menu_back: {
			backAciton();
		}
			break;
		// add by shawn 2012-9-6 Begin
		case R.id.menu_back_home: {
			Intent intent = new Intent(D2ECustomerActivity.this,
					D2EMainScreen.class);
			D2ECustomerActivity.this.startActivity(intent);
			overridePendingTransition(R.anim.fade, R.anim.hold);
			finish();
		}
			break;
		// End

		case R.id.menu_message: {
			Intent intent = new Intent(D2ECustomerActivity.this,
					D2EMessageActivity.class);
			D2ECustomerActivity.this.startActivity(intent);
			overridePendingTransition(R.anim.fade, R.anim.hold);
			finish();
		}
			break;

		case R.id.menu_help: {

			if (JJBaseApplication.g_documents.size() > 0) {
				Intent intent = new Intent(D2ECustomerActivity.this,
						D2EHelpActivity.class);
				D2ECustomerActivity.this.startActivity(intent);
				overridePendingTransition(R.anim.fade, R.anim.hold);
				finish();
			} else {
				SimpleDateFormat dates = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");

				String[] params = new String[] { "FunType", "getWebSite",
						"OrgID", JJBaseApplication.user_OrgID, "Type", "",
						"DateTime", dates.format(new Date()) };
				long ids = JJBaseService
						.addMutilpartHttpTask(
								"http://www.ohpest.com/ohpest-pco/webservices/get/data.html",
								params, D2ECustomerActivity.this.getClass()
										.getName(), D2EConfigures.TASK_GETSITE);

				setCurTaskID(ids);
				showWaitDialog(getResources().getString(R.string.handling));
			}

		}
			break;

		case R.id.menu_workSheet: {
			Intent intent = new Intent(D2ECustomerActivity.this,
					D2EWorksheetActivity.class);
			D2ECustomerActivity.this.startActivity(intent);
			overridePendingTransition(R.anim.fade, R.anim.hold);
			finish();

		}
			break;

		case R.id.menu_report: {
			Intent intent = new Intent(D2ECustomerActivity.this,
					D2EReportActivity.class);
			D2ECustomerActivity.this.startActivity(intent);
			overridePendingTransition(R.anim.fade, R.anim.hold);
			finish();
		}
			break;

		case R.id.menu_relogin: {
			alertExit();
		}
			break;

		case R.id.menu_back_home_page:
			Intent intent = new Intent(D2ECustomerActivity.this,
					D2EMainScreen.class);
			D2ECustomerActivity.this.startActivity(intent);
			overridePendingTransition(R.anim.fade, R.anim.hold);
			finish();
			break;
		case R.id.menu_back_previous:
			Intent intent2 = new Intent(D2ECustomerActivity.this,
					D2EHelpActivity.class);
			D2ECustomerActivity.this.startActivity(intent2);
			overridePendingTransition(R.anim.fade, R.anim.hold);
			finish();
			break;

		}
		return true;
	}
}
