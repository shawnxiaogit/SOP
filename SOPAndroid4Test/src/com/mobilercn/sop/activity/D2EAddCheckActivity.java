package com.mobilercn.sop.activity;

import java.io.InputStream;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

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
import com.mobilercn.sop.item.D2EPerson;
import com.mobilercn.task.JJTask;
import com.mobilercn.util.JJNetHelper;
import com.mobilercn.util.YTFileHelper;
import com.mobilercn.util.YTStringHelper;

public class D2EAddCheckActivity extends JJBaseActivity {

	// ɨ��ҵ���ǩ
	public static final int OP_DEL_BY_POINT_CARD = 3;
	// ɨԱ����
	public static final int OP_DEL_BY_USER_CARD = 4;

	private static final int OP_DEL = 0;
	private static final int OP_NORMAL = 2;

	private int mOPType = OP_NORMAL;
	private TagItem mDelItem = null;

	private TagsAdapter mTagsAdapter;
	private GridView mGridView;

	private String mCustom;
	private String mCustomID;
	private String mPosition;
	private String mLocationID;
	private int mType = 0;
	private boolean mDelete = false;

	private BadgeView mBadgeView;
	String locationID;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.d2e_addcheckpage);
		Window window = getWindow();
		window.setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.d2e_titlebar);
		TextView tv = (TextView) findViewById(R.id.title);
		tv.setText(getResources().getString(R.string.help_init));

		mOPType = OP_NORMAL;
		JJBaseApplication.sop_location_boolean = false;
		JJBaseApplication.g_grid_type = D2EConfigures.GRID_CHECK;

		Intent intent = getIntent();
		try {
			mCustom = intent.getExtras().getString("custom");
			mPosition = intent.getExtras().getString("position");
			mLocationID = intent.getExtras().getString("locationID");
			mCustomID = intent.getExtras().getString("customid");
		} catch (Exception ex) {
		}

		TextView dt = (TextView) findViewById(R.id.tagpage_custom);
		dt.setText(JJBaseApplication.strSelectCustomerName);

		TextView dt1 = (TextView) findViewById(R.id.tagpage_position);
		dt1.setText(mPosition);

		//
		mBadgeView = (BadgeView) findViewById(R.id.countTask);
		mBadgeView.setBadgePosition(BadgeView.POSITION_CENTER);
		mBadgeView.setBackgroundResource(R.drawable.badge_ifaux);
		mBadgeView.setVisibility(View.GONE);
		if (JJBaseApplication.user_CountTask != null
				&& JJBaseApplication.user_CountTask.length() > 0) {
			mBadgeView.setText(JJBaseApplication.user_CountTask);
		}

		// grid
		mTagsAdapter = new TagsAdapter(getApplicationContext());
		if (D2EConfigures.G_USEVIRCALDEVICE) {
			mTagsAdapter.addObject("��̨", "1");
			mTagsAdapter.addObject("���", "1");
			mTagsAdapter.addObject("����", "1");
			mTagsAdapter.addObject("ˮ��", "1");
		}

		for (Object ob : JJBaseApplication.mPointList) {
			if (ob instanceof TagItem) {
				mTagsAdapter.addObject((TagItem) ob);
				mTagsAdapter.notifyDataSetChanged();
			}
		}

		mTagsAdapter.addObject("", D2EConfigures.ADD_VALUE);

		mGridView = (GridView) findViewById(R.id.public_gridview);
		mGridView.setAdapter(mTagsAdapter);
		mGridView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {

				TagItem obj = (TagItem) mTagsAdapter.getItem(arg2);
				mDelItem = obj;
				if ((obj.getTitle().toLowerCase().equals("") && obj.getId()
						.equals(D2EConfigures.ADD_VALUE))
						|| obj.getTitle().toLowerCase().equals("")
						&& obj.getId().equals(D2EConfigures.DONE_VALUE)) {
					return false;
				}

				mDelete = !mDelete;

				if (mDelete) {
					JJBaseApplication.g_grid_type = D2EConfigures.GRID_DEL_CHECK;
				} else {
					JJBaseApplication.g_grid_type = D2EConfigures.GRID_CHECK;
				}

				mTagsAdapter.notifyDataSetChanged();
				return true;
			}

		});

		mGridView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				final TagItem obj = (TagItem) mTagsAdapter.getItem(arg2);
				mDelItem = obj;
				if (mDelete) {
					if ((obj.getTitle().toLowerCase().equals("") && obj.getId()
							.equals(D2EConfigures.ADD_VALUE))
							|| obj.getTitle().toLowerCase().equals("")
							&& obj.getId().equals(D2EConfigures.DONE_VALUE)) {
						mDelete = false;
						JJBaseApplication.g_grid_type = D2EConfigures.GRID_CHECK;
						mTagsAdapter.notifyDataSetChanged();
					} else {
						// add by shawn 2012-9-6 Begin
						// �����޸�Ϊ���ַ�ʽɾ����ɨ��Ա������ԭ��ǩ
						Builder myBuilder = new Builder(
								D2EAddCheckActivity.this);
						myBuilder.setIcon(android.R.drawable.ic_dialog_info);
						myBuilder.setTitle(getResources().getString(
								R.string.choose_delete_way));
						myBuilder.setPositiveButton(
								getResources()
										.getString(R.string.way_user_card),
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										mOPType = OP_DEL_BY_USER_CARD;
										showProcessDialog(getResources()
												.getString(
														R.string.reading_card));
										SOPBluetoothService
												.getService()
												.doTask(SOPBTCallHelper.READ_TAG,
														D2EAddCheckActivity.class
																.getName(),
														SOPBTCallHelper
																.getInitOrder(SOPBluetoothService.g_readerID));

									}
								});
						myBuilder
								.setNegativeButton(
										getResources().getString(
												R.string.orignal_card),
										new DialogInterface.OnClickListener() {
											@Override
											public void onClick(
													DialogInterface dialog,
													int which) {
												mOPType = OP_DEL_BY_POINT_CARD;
												showProcessDialog(getResources()
														.getString(
																R.string.reading_card));
												SOPBluetoothService
														.getService()
														.doTask(SOPBTCallHelper.READ_TAG,
																D2EAddCheckActivity.class
																		.getName(),
																SOPBTCallHelper
																		.getInitOrder(SOPBluetoothService.g_readerID));
											}
										});
						myBuilder.show();

						return;
					}
				}

				if (obj.getTitle().toLowerCase().equals("")
						&& obj.getId().equals(D2EConfigures.ADD_VALUE)) { // ɨ���ǩ
					showProcessDialog(getResources().getString(
							R.string.reading_card));
					SOPBluetoothService
							.getService()
							.doTask(SOPBTCallHelper.READ_TAG,
									D2EAddCheckActivity.class.getName(),
									SOPBTCallHelper
											.getInitOrder(SOPBluetoothService.g_readerID));
				} else if (obj.getTitle().toLowerCase().equals("")
						&& obj.getId().equals(D2EConfigures.DONE_VALUE)) {
					showProcessDialog(getResources().getString(
							R.string.reading_card));
					SOPBluetoothService
							.getService()
							.doTask(SOPBTCallHelper.TAG_RETURN,
									D2EAddCheckActivity.class.getName(),
									SOPBTCallHelper
											.getInitOrder(SOPBluetoothService.g_readerID));
				} else { // ���ü����ǩ

				}

			}
		});

	}

	public TagsAdapter getTagsAdapter() {
		return mTagsAdapter;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (resultCode) {
		case RESULT_OK:
			Bundle bundle = data.getExtras();
			String ReadNum = bundle.getString("ReadNum");
			String TagNum = bundle.getString("TagNum");
			String Title = bundle.getString("Title");
			String Type = bundle.getString("Type");
			String Memo = bundle.getString("Memo");
			// add by shawn 2012-10-15
			// ��ȡ��ʩ���ƣ�ʵʩ����
			String TypeName = bundle.getString("TypeName");
			// End

			String[] params = new String[] { "FunType", "insertSOPPoint",
					"OrgID", JJBaseApplication.user_OrgID, "CSID",
					JJBaseApplication.strSelectLocationID, "PositionID",
					mLocationID, "ReadNum", ReadNum, "TagNum", TagNum, "Title",
					Title, "Type", Type, "TypeName", TypeName,// ����ʩ��ʵʩ����Ҳ���ݹ�ȥ
					"Memo", Memo };

			YTStringHelper.array2string(params);

			long id = JJBaseService
					.addMutilpartHttpTask(
							"http://www.ohpest.com/ohpest-pco/webservices/get/data.html",
							params, D2EAddCheckActivity.this.getClass()
									.getName(), D2EConfigures.TASK_ADDCHECK);

			setCurTaskID(id);
			showWaitDialog(getResources().getString(R.string.handling_wait));
			break;
		}
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {

		case R.id.menu_back: {
			if (D2EConfigures.TEST) {
				Log.e("back---------->", "backClick");
			}
			// add by shawn 2012-10-19 Begin
			// ����������������ȡλ�ñ�ǩ��Ϣ��
			String[] params = new String[] { "FunType", "getSOPPositionList",
					"OrgID", JJBaseApplication.user_OrgID, "SalesClientID",
					JJBaseApplication.clickCustomer.getId() };
			YTStringHelper.array2string(params);
			long id = JJBaseService
					.addMutilpartHttpTask(
							"http://www.ohpest.com/ohpest-pco/webservices/get/data.html",
							params, D2EAddCheckActivity.this.getClass()
									.getName(),
							D2EConfigures.TASK_GETLOCATIONBYCUS);
			setCurTaskID(id);
			showWaitDialog(getResources().getString(R.string.handling_wait));
			// End
		}
			break;
		case R.id.menu_back_home_page:
			Intent intent = new Intent(D2EAddCheckActivity.this,
					D2EMainScreen.class);
			D2EAddCheckActivity.this.startActivity(intent);
			overridePendingTransition(R.anim.fade, R.anim.hold);
			finish();
			break;
		case R.id.menu_back_previous:
			// add by shawn 2012-10-19 Begin
			// ����������������ȡλ�ñ�ǩ��Ϣ��
			String[] params = new String[] { "FunType", "getSOPPositionList",
					"OrgID", JJBaseApplication.user_OrgID, "SalesClientID",
					JJBaseApplication.clickCustomer.getId() };
			YTStringHelper.array2string(params);
			long id = JJBaseService
					.addMutilpartHttpTask(
							"http://www.ohpest.com/ohpest-pco/webservices/get/data.html",
							params, D2EAddCheckActivity.this.getClass()
									.getName(),
							D2EConfigures.TASK_GETLOCATIONBYCUS);
			setCurTaskID(id);
			showWaitDialog(getResources().getString(R.string.handling_wait));
			// //End
			break;

		}
		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		super.onPrepareOptionsMenu(menu);
		menu.clear();
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.back_menu_change, menu);
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
		if (D2EConfigures.TEST) {
			Log.e("taskSuccess=================>", "taskSuccess()");
		}

		// dismissProcessDialog();
		int values = ((Integer) param[0]).intValue();
		if (values == JJBaseService.BT_SERVICE_INT) {
			int state = ((Integer) param[1]).intValue();
			String response = (String) param[2];
			if (response.toLowerCase().equals(
					SOPBluetoothService.STATE_FAILED.toLowerCase())) {
				((JJBaseApplication) getApplication())
						.showMessage(getResources().getString(
								R.string.failed_read));
				dismissProcessDialog();
				return;
			} else {
				// success
				locationID = response.substring(14, 22);
				if (D2EConfigures.G_DEBUG) {
					locationID = "93edb887";// a2f0b888:��ҵ�� ����:93edb887
				}
				if (state == SOPBTCallHelper.TAG_RETURN) {
					String[] params = new String[] { "FunType",
							"getSalesClientDataWithTask", "OrgID",
							JJBaseApplication.user_OrgID, "ReadNum",
							SOPBluetoothService.g_readerID, "TagNum",
							locationID, "UserID", JJBaseApplication.user_ID };

					long id = JJBaseService
							.addMutilpartHttpTask(
									"http://www.ohpest.com/ohpest-pco/webservices/get/data.html",
									params, D2EAddCheckActivity.this.getClass()
											.getName(),
									D2EConfigures.TASK_GET_CUSTOM_INFO);

					setCurTaskID(id);
					showWaitDialog(getResources().getString(
							R.string.handling_wait));
					return;
				} else if (state == SOPBTCallHelper.READ_TAG) {
					if (mOPType == OP_DEL_BY_POINT_CARD) {
						mOPType = OP_NORMAL;
						AlertDialog.Builder builder = new AlertDialog.Builder(
								mGridView.getContext());
						builder.setTitle(getResources().getString(
								R.string.warm_prompt));
						builder.setMessage(getResources().getString(
								R.string.sure_delet));
						builder.setPositiveButton(
								getResources().getString(
										R.string.dialog_btn_yes_text),
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {

										if (D2EConfigures.G_USEVIRCALDEVICE) {
											mTagsAdapter.removeItem(mDelItem);
											mTagsAdapter.notifyDataSetChanged();
											((JJBaseApplication) getApplication())
													.showMessage(getResources()
															.getString(
																	R.string.delete_succe));
										} else {
											String[] params = new String[] {
													"FunType",
													"delPoint",
													"OrgID",
													JJBaseApplication.user_OrgID,
													"ReadNum",
													SOPBluetoothService.g_readerID,
													"TagNum", locationID };

											YTStringHelper.array2string(params);
											long id = JJBaseService
													.addMutilpartHttpTask(
															"http://www.ohpest.com/ohpest-pco/webservices/get/data.html",
															params,
															D2EAddCheckActivity.this
																	.getClass()
																	.getName(),
															D2EConfigures.TASK_DELITEM);
											setCurTaskID(id);
											showWaitDialog(getResources()
													.getString(
															R.string.handling_wait));
										}
									}
								});
						builder.setNegativeButton(
								getResources().getString(
										R.string.dialog_btn_cancle_text), null);
						builder.create().show();
					} else if (mOPType == OP_DEL_BY_USER_CARD) {
						mOPType = OP_NORMAL;
						AlertDialog.Builder builder = new AlertDialog.Builder(
								mGridView.getContext());
						builder.setTitle(getResources().getString(
								R.string.warm_prompt));
						builder.setMessage(getResources().getString(
								R.string.sure_delet));
						builder.setPositiveButton(
								getResources().getString(
										R.string.dialog_btn_yes_text),
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {

										if (D2EConfigures.G_USEVIRCALDEVICE) {
											mTagsAdapter.removeItem(mDelItem);
											mTagsAdapter.notifyDataSetChanged();
											((JJBaseApplication) getApplication())
													.showMessage(getResources()
															.getString(
																	R.string.delete_succe));
										} else {
											String[] params = new String[] {
													"FunType",
													"delPoint",
													"OrgID",
													JJBaseApplication.user_OrgID,
													"ReadNum",
													SOPBluetoothService.g_readerID,
													"TagNum", locationID,
													"PointID", mDelItem.getId() };

											YTStringHelper.array2string(params);
											long id = JJBaseService
													.addMutilpartHttpTask(
															"http://www.ohpest.com/ohpest-pco/webservices/get/data.html",
															params,
															D2EAddCheckActivity.this
																	.getClass()
																	.getName(),
															D2EConfigures.TASK_DELITEM);
											setCurTaskID(id);
											showWaitDialog(getResources()
													.getString(
															R.string.handling_wait));
										}
									}
								});
						builder.setNegativeButton(
								getResources().getString(
										R.string.dialog_btn_cancle_text), null);
						builder.create().show();
					} else {

						// add by shawn 2012-9-19 Begin
						// ����Ҫ�жϿ�Ƭ�Ƿ�ʹ�ù���
						String[] params = new String[] { "FunType", "checkTag",
								"OrgID", JJBaseApplication.user_OrgID,
								"ReadNum", SOPBluetoothService.g_readerID,
								"TagNum", locationID };

						YTStringHelper.array2string(params);
						long id = JJBaseService
								.addMutilpartHttpTask(
										"http://www.ohpest.com/ohpest-pco/webservices/get/data.html",
										params, D2EAddCheckActivity.this
												.getClass().getName(),
										D2EConfigures.TASK_CHECK_TAG);
						setCurTaskID(id);

						// End
						// ������ҵ���ǩ�����������̫�࣬����һ��������������Ϣ
					}

				}
			}
		} else if (values == JJBaseService.HTTP_SERVICE_INT) {
			JJTask task = (JJTask) param[1];
			String response = null;
			try {
				InputStream ins = task.getInputStream();
				byte[] bytes = JJNetHelper.readByByte(ins, -1);

				response = new String(bytes, "UTF-8");
				ins.close();
				if (D2EConfigures.TEST) {
					Log.e("response: >>>>>> ", response);
				}
				if (D2EConfigures.G_DEBUG) {
					Log.e("response: >>>>>> ", response);
				}
				try {

					JSONObject j = new JSONObject(response);
					String err = null;
					if (j.has("error")) {
						err = j.getString("error");
						// add by shawn 2012- 10-30
						// �޸�Ϊ�Զ��岼�ֵ�toast
						if (err != null && err.length() > 0) {
							String name = null;
							String type = null;
							if (j.has("Name")) {
								name = j.getString("Name");
								if (j.has("Type")) {
									type = j.getString("Type");
								}
								((JJBaseApplication) getApplication())
										.showMessage(err);
								LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
								View v = inflater.inflate(R.layout.self_toast,
										null);
								TextView toast_tv = (TextView) v
										.findViewById(R.id.toast_tv);
								toast_tv.setText(name);
								if (D2EConfigures.TEST) {
									Log.e("type===========>", "" + type);
								}
								if (type.equalsIgnoreCase(getResources()
										.getString(R.string.location_tag))) {
									v.setBackgroundResource(R.drawable.grid_red);
								} else {
									v.setBackgroundResource(R.drawable.grid_green);
								}
								Toast toast = new Toast(this);
								toast.setView(v);
								toast.setDuration(Toast.LENGTH_LONG);
								toast.show();
							} else {
								((JJBaseApplication) getApplication())
										.showMessage(err);
								dismissProcessDialog();
								return;
							}

							dismissProcessDialog();
							return;
						}

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
			if (task.getTaskId() == D2EConfigures.TASK_ADDCHECK) {
				try {
					JSONObject j = new JSONObject(response);
					// add by shawn 2012-9-7 Begin
					if (D2EConfigures.TEST) {
						Log.e("��ҵ��ID:", "" + (j.getString("ID")));
						Log.e("��ҵ������:", "" + (j.getString("Name")));
					}
					TagItem tmp = new TagItem(j.getString("Name"),
							j.getString("ID"));
					JJBaseApplication.mPointList.add(tmp);
					// End
					mTagsAdapter.insertObject(j.getString("Name"),
							j.getString("ID"), "");
					mTagsAdapter.notifyDataSetChanged();
				} catch (Exception ex) {
				}
			} else if (task.getTaskId() == D2EConfigures.TASK_GETLOCATIONBYCUS) {

				if (D2EConfigures.TEST) {
					Log.e("TASK_GETLOCATIONBYCUS----------------->",
							"TASK_GETLOCATIONBYCUS");
				}

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
							Intent intent2 = new Intent(
									D2EAddCheckActivity.this,
									D2EAddLocationActivity.class);
							D2EAddCheckActivity.this.startActivity(intent2);
							overridePendingTransition(R.anim.fade, R.anim.hold);
							finish();
						}
					} catch (Exception ex) {
					}
				}

			} else if (task.getTaskId() == D2EConfigures.TASK_GET_CUSTOM_INFO) {
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

						// ʩ����Ա
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

						// ������Ϣ
						JSONObject Task = new JSONObject(res.getString("Task"));
						JSONObject jt = new JSONObject(Task.getString("0"));
						JJBaseApplication.g_customer.mSerial = jt
								.getString("Serial");
						JJBaseApplication.g_customer.mMemo = jt
								.getString("Description");
						JJBaseApplication.g_customer.mTaskId = jt
								.getString("ID");

						// �����ķ�����1 ������2 �����3 ������4
						JJBaseApplication.g_customer.mTaskType = Task
								.getInt("TaskType");

						// }

						// λ�ñ�ǩ
						JSONArray SOPPositionList = new JSONArray(
								res.getString("SOPPositionList"));
						int len = SOPPositionList.length();
						for (int i = 0; i < len; i++) {
							String tmp = SOPPositionList.getString(i);
							JSONObject jo = new JSONObject(tmp);

							TagItem item = new TagItem(jo.getString("Name"),
									jo.getString("ID"));
							item.setTagNum(jo.getString("TagID"));
							JJBaseApplication.g_customer.mPositions.add(item);
						}

					} catch (Exception ex) {
					}
					// modify by shawn 2012-11-12 Begin
					// ����Ҫ�����εĿͻ���Ϣ����˰ѿͻ���Ϣ����Ϊ�б�ÿ��ȡ��ʱ���ȡָ�������ŵĿͻ���Ϣ
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
						intent.setClass(D2EAddCheckActivity.this,
								D2ESOPActivity.class);
						D2EAddCheckActivity.this.startActivity(intent);
						overridePendingTransition(R.anim.fade, R.anim.hold);
						finish();
					} else {
						((JJBaseApplication) getApplication())
								.showMessage(getString(R.string.no_weeksheet_data));
					}
				}

			}// �жϿ�Ƭ�Ƿ�ʹ���ˣ�ʹ������������ʾ��û��ʹ��������¸�ҳ��
			else if (task.getTaskId() == D2EConfigures.TASK_CHECK_TAG) {
				if (D2EConfigures.TEST) {
					Log.e("D2EConfigures.TASK_CHECK_TAG=========>",
							"D2EConfigures.TASK_CHECK_TAG");
				}
				if (response != null && response.length() > 0) {
					JSONObject jo = null;
					try {
						jo = new JSONObject(response);
					} catch (JSONException e) {
						e.printStackTrace();
					}
					if (D2EConfigures.TEST) {
						Log.e("response------------->", "" + response);
						Log.e("responseJSONOBJEct------------>",
								"" + jo.toString());
					}
					Intent intent_newcheckpoint = new Intent(
							D2EAddCheckActivity.this,
							D2ENewCheckPointActivity.class);
					intent_newcheckpoint.putExtra("locationID", locationID);
					intent_newcheckpoint.putExtra("mCustomID",
							JJBaseApplication.strSelectLocationID);
					intent_newcheckpoint.putExtra("mLocationID", mLocationID);
					Bundle bundle = new Bundle();
					intent_newcheckpoint.putExtras(bundle);
					D2EAddCheckActivity.this.startActivityForResult(
							intent_newcheckpoint, 0);
					overridePendingTransition(R.anim.fade, R.anim.hold);
				}
			} else if (task.getTaskId() == D2EConfigures.TASK_GETCHECKBYLOCATION) {

				if (response != null && response.length() > 0) {
					try {
						JJBaseApplication.g_list.clear();
						JSONArray ja = new JSONArray(response);
						int len = ja.length();
						for (int i = 0; i < len; i++) {
							String tmp = ja.getString(i);
							JSONObject jo = new JSONObject(tmp);
							TagItem item = new TagItem(jo.getString("Name"),
									jo.getString("ID"));
							JJBaseApplication.g_list.add(item);
						}
					} catch (Exception ex) {
					}
				}
			} else if (task.getTaskId() == D2EConfigures.TASK_DELITEM) {
				mDelete = false;
				JJBaseApplication.g_grid_type = D2EConfigures.GRID_CHECK;
				mOPType = OP_NORMAL;
				JJBaseApplication.mPointList.remove(mDelItem);
				mTagsAdapter.removeItem(mDelItem);
				mTagsAdapter.notifyDataSetChanged();
				dismissWaitDialog();

				try {
					JSONObject j = new JSONObject(response);
					String err = j.getString("success");
					if (err != null && err.length() > 0) {
						((JJBaseApplication) getApplication()).showMessage(err);
					}
				} catch (Exception ex) {
				}

			}
		}
		dismissProcessDialog();
		dismissWaitDialog();
	}

	@Override
	public void taskFailed(Object... param) {
		if (D2EConfigures.TEST) {
			Log.e("taskFailed=================>", "taskFailed()");
		}

		dismissProcessDialog();
		int values = ((Integer) param[0]).intValue();
		if (values == JJBaseService.HTTP_SERVICE_INT) {
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
