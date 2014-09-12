package com.mobilercn.sop.activity;

import java.io.InputStream;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Toast;
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
import com.mobilercn.task.JJTask;
import com.mobilercn.util.JJNetHelper;
import com.mobilercn.util.YTFileHelper;
import com.mobilercn.util.YTStringHelper;

public class D2ECheckListActivity extends JJBaseActivity {
	D2ECustomer lastCustomer = null;
	private YTFileHelper ytfileHelper;
	private TagItem oldItem;
	private TagItem newItem;
	private TagsAdapter mTagsAdapter;
	private GridView mGridView;

	private String mCustom;
	private String mPosition;
	private String mPositionId;

	private String mTagNum;
	private boolean isChanged = false;
	private String mPositionID;
	private String mChangeTageID;

	private boolean isReplaceCard = false;

	// 新增一个用来保存超声波的老鼠数量的
	private String mMouseNumberStr;
	private int mMouseNumberInt;
	private TagItem mChangedItem;

	private BadgeView mBadgeView;
	private boolean haveSaved = false;

	// modify by shawn 2012-9-4
	// 新建一个Handler来处理子线程消息更新UI
	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {

			}

		}

	};

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.d2e_checkpage);
		Window window = getWindow();
		window.setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.d2e_titlebar);

		// 将位置标签的id获取
		Intent intent = getIntent();
		mPositionID = intent.getStringExtra("PositionID");
		if (D2EConfigures.TEST) {
			Log.e("mPositionID------------->", "" + mPositionID);
		}

		ytfileHelper = YTFileHelper.getInstance();
		ytfileHelper.setContext(getApplicationContext());

		TextView tv = (TextView) findViewById(R.id.title);
		tv.setText(getResources().getString(R.string.menu_sop));

		JJBaseApplication.sop_location_boolean = false;
		JJBaseApplication.g_grid_type = D2EConfigures.GRID_CHECK;

		TextView dt = (TextView) findViewById(R.id.tagpage_custom);
		if (D2EConfigures.G_USEVIRCALDEVICE) {
			try {
				mCustom = JJBaseApplication.sop_name;
				mPosition = JJBaseApplication.sop_id;
			} catch (Exception ex) {
			}
			dt.setText(mCustom);
		} else {
			dt.setText(JJBaseApplication.g_customer.mName);
		}

		TextView dt1 = (TextView) findViewById(R.id.tagpage_position);
		dt1.setText(JJBaseApplication.sop_location.getTitle());

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
		// 从保存的文件中获取选中的位置下面的作业点信息
		MyCustomerListData myCustomerListData = (MyCustomerListData) ytfileHelper
				.deSerialObject(getResources()
						.getString(R.string.customer_list));
		D2ECustomer customer = myCustomerListData
				.getWeekSerialCustomer(JJBaseApplication.g_customer);
		JJBaseApplication.sop_location = customer.getSelectPositon();
		for (Object ob : JJBaseApplication.sop_location.mItems) {
			if (!((TagItem) ob).mChecked) {
				mTagsAdapter.addObject((TagItem) ob);
				mTagsAdapter.notifyDataSetChanged();
			}
		}
		// End

		mGridView = (GridView) findViewById(R.id.public_gridview);
		mGridView.setAdapter(mTagsAdapter);

		mGridView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				JJBaseApplication.selectPoint = (TagItem) mTagsAdapter
						.getItem(arg2);
				mChangedItem = (TagItem) mTagsAdapter.getItem(arg2);
				oldItem = (TagItem) mTagsAdapter.getItem(arg2);
				JJBaseApplication.sop_checkp = mChangedItem;

				// modify by shawn 2012-9-12 Begin
				// 修改为刷员工卡->确定->继续/go 和 替换/go 取消
				Builder myBuilder = new Builder(D2ECheckListActivity.this);
				myBuilder.setIcon(android.R.drawable.ic_dialog_info);
				myBuilder.setTitle(getResources().getString(
						R.string.brush_user_card));
				myBuilder.setPositiveButton(
						getResources().getString(R.string.dialog_btn_yes_text),
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								showProcessDialog(getResources().getString(
										R.string.reading_card));
								SOPBluetoothService
										.getService()
										.doTask(SOPBTCallHelper.TAG_GET_EMPLOEE_NUM,
												D2ECheckListActivity.class
														.getName(),
												SOPBTCallHelper
														.getInitOrder(SOPBluetoothService.g_readerID));

							}
						});
				myBuilder
						.setNegativeButton(
								getResources().getString(
										R.string.btn_d2eprompt_cancle),
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {

									}
								});
				myBuilder.show();
				// End
				return true;
			}

		});

		mGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				JJBaseApplication.selectPoint = (TagItem) mTagsAdapter
						.getItem(arg2);
				TagItem obj = (TagItem) mTagsAdapter.getItem(arg2);
				if (D2EConfigures.TEST) {
					Log.e("isChanged----------->", "" + isChanged);
					Log.e("mChangeTageID----------->", "" + mChangeTageID);
					Log.e("obj.setmId(mChangeTageID)----------->", ""
							+ mChangeTageID);
				}
				// modify by shawn 2012-9-3
				// 如果替换过标签，则把标签id也修改了
				if (isChanged) {
					obj.setId(mChangeTageID);
				}
				if (D2EConfigures.TEST) {
					Log.e("obj.getId(mChangeTageID)----------->",
							"" + obj.getId());
				}
				JJBaseApplication.sop_checkp = obj;

				showProcessDialog(getResources().getString(
						R.string.reading_card));
				SOPBluetoothService.getService().doTask(
						SOPBTCallHelper.READ_TAG,
						D2ECheckListActivity.class.getName(),
						SOPBTCallHelper
								.getInitOrder(SOPBluetoothService.g_readerID));
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		// modify by shawn 2012-9-3
		// 如果替换过标签，则从新请求网络获取标签
		// --------------------
		if (D2EConfigures.TEST) {
			Log.e("D2ECheckListActivity------------->", "onResume()");
			Log.e("isChanged------>", "" + isChanged);
		}
		mTagsAdapter.clear();
		// modify by shawn 2012-11-21
		// 从保存的文件中获取选中的位置下面的作业点信息
		MyCustomerListData myCustomerListData = (MyCustomerListData) ytfileHelper
				.deSerialObject(getResources()
						.getString(R.string.customer_list));
		D2ECustomer customer = myCustomerListData
				.getWeekSerialCustomer(JJBaseApplication.g_customer);
		JJBaseApplication.sop_location = customer.getSelectPositon();
		for (Object ob : JJBaseApplication.sop_location.mItems) {
			if (!((TagItem) ob).mChecked) {
				mTagsAdapter.addObject((TagItem) ob);
				mTagsAdapter.notifyDataSetChanged();
			}
		}
		// End

	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {

		case R.id.menu_back: {
			if (mTagsAdapter.getCount() > 0) {
				Builder builder = new Builder(D2ECheckListActivity.this);
				builder.setIcon(android.R.drawable.ic_dialog_info);
				builder.setTitle(getResources().getString(R.string.warm_prompt));
				builder.setMessage(getResources().getString(
						R.string.check_is_not_finish));
				builder.setPositiveButton(
						getResources().getString(R.string.jj_sure),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								final Intent intent = new Intent(
										D2ECheckListActivity.this,
										D2ESOPActivity.class);
								D2ECheckListActivity.this.startActivity(intent);
								finish();
							}
						});
				builder.setNegativeButton(
						getResources().getString(
								R.string.dialog_btn_cancle_text),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
							}
						});
				builder.show();
			} else {
				Intent intent = new Intent(D2ECheckListActivity.this,
						D2ESOPActivity.class);
				D2ECheckListActivity.this.startActivity(intent);
				overridePendingTransition(R.anim.fade, R.anim.hold);
				finish();
			}

		}
			break;

		case R.id.menu_view: {
			final Intent intent = new Intent(D2ECheckListActivity.this,
					D2EReportViewActivity.class);
			intent.putExtra("type", D2EReportViewActivity.VIEW_TYPE_TODO);
			D2ECheckListActivity.this.startActivity(intent);
		}
			break;

		}
		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		super.onPrepareOptionsMenu(menu);
		menu.clear();
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.cklistpage_menu, menu);

		MenuItem item = menu.findItem(R.id.menu_back);

		return true;
	}

	@Override
	public void init() {

	}

	@Override
	public void taskSuccess(Object... param) {
		if (D2EConfigures.TEST) {
			Log.e("taskSuccess()----------------->", "taskSuccess()");
		}
		try {
			int values = ((Integer) param[0]).intValue();
			if (D2EConfigures.TEST) {
				Log.e("values--------------->", "" + values);
			}
			if (values == JJBaseService.BT_SERVICE_INT) {
				String response = (String) param[2];
				int state = ((Integer) param[1]).intValue();
				if (response.toLowerCase().equals(
						SOPBluetoothService.STATE_FAILED.toLowerCase())) {
					((JJBaseApplication) getApplication())
							.showMessage(getResources().getString(
									R.string.failed_read));
					dismissProcessDialog();
					return;
				} else {
					mTagNum = response.substring(14, 22);
					// 判断截取的信息是否为空
					if (response.substring(22, 30) != null) {
						// 读取老鼠的数量，截取卡中信息
						mMouseNumberStr = response.substring(22, 30);
						if (mMouseNumberStr != null
								&& mMouseNumberStr.length() > 0) {
							Log.i("mMouseNumberStr--------->", mMouseNumberStr);
						}

						// 将信息从十六进制转化为十进制,这个信息要传给下个页面
						mMouseNumberInt = Integer.parseInt(mMouseNumberStr, 16);
						if (mMouseNumberInt != 0) {
							Log.i("mMouseNumberInt------------->", ""
									+ mMouseNumberInt);
						}

					}
					if (D2EConfigures.G_DEBUG) {
						mTagNum = "a2f0b888";// "93edb887";//作业点: "a2f0b888";
					}

					if (D2EConfigures.G_USEVIRCALDEVICE) {
						response = "[{\"ID\":\"1\",\"SOPPositionID\":\"1\",\"SalesClientID\":\"19\",\"OrgID\":\"6\",\"TagID\":\"13\",\"Type\":\"MousePlate\",\"Description\":\"1\"}]";
						if (response != null && response.length() > 0) {
							// [{"ID":"1","SOPPositionID":"1","SalesClientID":"19","OrgID":"6","TagID":"13","Type":"MousePlate","Description":"1"}]
							try {

								JSONArray jr = new JSONArray(response);
								JSONObject jo = new JSONObject(jr.getString(0));
								JJBaseApplication.sop_checkp.mType = jo
										.getString("Type");

								if (JJBaseApplication.sop_checkp.mType
										.toLowerCase().equals(
												"OPSite".toLowerCase())) {
									JSONArray SuppliesID = new JSONArray(
											jo.getString("SuppliesID"));
									JJBaseApplication.sop_checkp.mSuppliesID = SuppliesID
											.getString(0);

									JSONArray InstrumentID = new JSONArray(
											jo.getString("InstrumentID"));
									JJBaseApplication.sop_checkp.mInstrumentID = InstrumentID
											.getString(0);

									JSONArray Supplies = new JSONArray(
											jo.getString("Supplies"));
									JJBaseApplication.sop_checkp.mSuppliesName = Supplies
											.getString(0);

									JSONArray Instrument = new JSONArray(
											jo.getString("Instrument"));
									JJBaseApplication.sop_checkp.mInstrumentName = Instrument
											.getString(0);

									JSONArray Rate = new JSONArray(
											jo.getString("Rate"));
									JJBaseApplication.sop_checkp.mRate = Rate
											.getString(0);

									JSONArray Volume = new JSONArray(
											jo.getString("Volume"));
									JJBaseApplication.sop_checkp.mVolume = Volume
											.getString(0);

								}
							} catch (Exception ex) {
							}
						}

						Intent intent = new Intent(D2ECheckListActivity.this,
								D2ECheckEquipsActivity.class);
						Bundle bundle = new Bundle();
						D2ECheckListActivity.this.startActivity(intent);
						overridePendingTransition(R.anim.fade, R.anim.hold);
						finish();

						return;
					}

					if (state == SOPBTCallHelper.READ_TAG) {
						String[] params = new String[] { "FunType",
								"chkSOPPoint", "OrgID",
								JJBaseApplication.user_OrgID, "PointTagNum",
								mTagNum, "TagID",
								JJBaseApplication.sop_checkp.getId() };

						YTStringHelper.array2string(params);

						long id = JJBaseService
								.addMutilpartHttpTask(
										"http://www.ohpest.com/ohpest-pco/webservices/get/data.html",
										params, D2ECheckListActivity.this
												.getClass().getName(),
										D2EConfigures.TASK_GETCHECKBYLOCATION);

						setCurTaskID(id);
						return;
					}
					// 这里通过蓝牙读取员工卡号
					else if (state == SOPBTCallHelper.TAG_GET_EMPLOEE_NUM) {
						// modify by shawn 2012-9-12 Begin
						// 弹出对话框，选择继续还是替换
						if(D2EConfigures.TEST){
							Log.e("TAG_GET_EMPLOEE_NUM=========>", "TAG_GET_EMPLOEE_NUM");
							Log.e("mTagNum", ""+mTagNum);
							Log.e("mTagNum != null && mTagNum.length() > 0", ""+(mTagNum != null && mTagNum.length() > 0));
						}
						if (mTagNum != null && mTagNum.length() > 0) {
							// add by shawn 2012-9-21 Begin
							// 表示数刷了员工卡
							// D2EConfigures.ISSLOTUSRCARD=true;-----应该是每个作业点都有可能用员工卡来完成

							JJBaseApplication.sop_checkp.setCheckByUser(true);
							// 并将刷卡员工的卡片保存到作业点的属性中
							JJBaseApplication.sop_checkp
									.setStrCheckTypeUser(mTagNum);
							// End
							if(D2EConfigures.TEST){
								Log.e("JJBaseApplication.isContainEmploeeTageNum(mTagNum)", ""+(JJBaseApplication
									.isContainEmploeeTageNum(mTagNum)));
							}
							if (JJBaseApplication
									.isContainEmploeeTageNum(mTagNum)) {
								Builder myBuilder2 = new Builder(
										D2ECheckListActivity.this);
								myBuilder2.setPositiveButton(getResources()
										.getString(R.string.doing_continue),
										new DialogInterface.OnClickListener() {
											@Override
											public void onClick(
													DialogInterface dialog,
													int which) {
												String[] params = new String[] {
														"FunType",
														"chkSOPPoint_byPointID", // 直接通过作业点id获取作业点状态
														"OrgID",
														JJBaseApplication.user_OrgID, // 爱委会id
														"TaskID",
														JJBaseApplication.g_customer.mTaskId,
														"PointID",
														mChangedItem
																.getmPointId(), // 作业点id
														"UserNum", mTagNum }; // 员工卡号(必须是签到过的)
												YTStringHelper
														.array2string(params);
												long id = JJBaseService
														.addMutilpartHttpTask(
																"http://www.ohpest.com/ohpest-pco/webservices/get/data.html",
																params,
																D2ECheckListActivity.this
																		.getClass()
																		.getName(),
																D2EConfigures.TASK_GET_POINT_BY_PIONT_ID);
												setCurTaskID(id);
												showWaitDialog(getResources()
														.getString(
																R.string.handling_wait));
											}
										});
								myBuilder2.setNegativeButton(getResources()
										.getString(R.string.doing_replace),
										new DialogInterface.OnClickListener() {
											@Override
											public void onClick(
													DialogInterface dialog,
													int which) {
												D2EConfigures.ISREPLACE = true;
												isReplaceCard = true;
												String[] params = new String[] {
														"FunType",
														"chkSOPPoint_byPointID", // 直接通过作业点id获取作业点状态
														"OrgID",
														JJBaseApplication.user_OrgID, // 爱委会id
														"TaskID",
														JJBaseApplication.g_customer.mTaskId,
														"PointID",
														mChangedItem
																.getmPointId(), // 作业点id
														"UserNum", mTagNum }; // 员工卡号(必须是签到过的)
												YTStringHelper
														.array2string(params);
												long id = JJBaseService
														.addMutilpartHttpTask(
																"http://www.ohpest.com/ohpest-pco/webservices/get/data.html",
																params,
																D2ECheckListActivity.this
																		.getClass()
																		.getName(),
																D2EConfigures.TASK_GET_POINT_BY_PIONT_ID);
												setCurTaskID(id);
												showWaitDialog(getResources()
														.getString(
																R.string.handling_wait));

											}
										});
								myBuilder2.show();
							} else {
								((JJBaseApplication) getApplication())
										.showMessage(getResources()
												.getString(
														R.string.reader_user_card_failed));
							}

							// End
						}

					} else if (state == SOPBTCallHelper.TAG_CHANGE) {
						LayoutInflater inflater = getLayoutInflater();
						final View layout = inflater.inflate(
								R.layout.d2e_changetag, null);
						final Button btn = (Button) layout
								.findViewById(R.id.button_send);
						final Button btnyes = (Button) layout
								.findViewById(R.id.btn_yes);
						final Button btnclear = (Button) layout
								.findViewById(R.id.btn_clear);
						final Button btnretry = (Button) layout
								.findViewById(R.id.btn_retry);
						final EditText newtag = (EditText) layout
								.findViewById(R.id.tag_new);

						final TextView nub = (TextView) layout
								.findViewById(R.id.location_id);
						nub.setText(mChangedItem.getId());

						final EditText name = (EditText) layout
								.findViewById(R.id.location_name);
						name.setText(mChangedItem.getTitle());

						newtag.setText(mTagNum);

						Builder buildertmp = new Builder(
								D2ECheckListActivity.this);
						buildertmp.setIcon(android.R.drawable.ic_dialog_info);
						buildertmp.setTitle(getResources().getString(
								R.string.update_card));
						buildertmp.setView(layout);
						final AlertDialog dialog = buildertmp.show();

						btn.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View arg0) {
								showProcessDialog(getResources().getString(
										R.string.reading_card));
								SOPBluetoothService
										.getService()
										.doTask(SOPBTCallHelper.TAG_CHANGE,
												D2ECheckListActivity.class
														.getName(),
												SOPBTCallHelper
														.getInitOrder(SOPBluetoothService.g_readerID));
								dialog.dismiss();
							}

						});

						btnretry.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View arg0) {
								showProcessDialog(getResources().getString(
										R.string.reading_card));
								SOPBluetoothService
										.getService()
										.doTask(SOPBTCallHelper.TAG_CHANGE,
												D2ECheckListActivity.class
														.getName(),
												SOPBTCallHelper
														.getInitOrder(SOPBluetoothService.g_readerID));
								dialog.dismiss();
							}

						});

						btnclear.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View arg0) {
								newtag.setText("");
							}

						});

						btnyes.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View arg0) {
								if (name.getText().length() == 0
										|| name.getText() == null
										|| newtag.getText().length() == 0
										|| newtag.getText() == null) {
									((JJBaseApplication) getApplication())
											.showMessage(getResources()
													.getString(
															R.string.please_comlete_data));
									return;
								}

								String[] params = new String[] { "FunType",
										"updataPoint", "OrgID",
										JJBaseApplication.user_OrgID,
										"ReadNum",
										SOPBluetoothService.g_readerID,
										"TagNum",
										newtag.getText().toString().trim(),
										"PointID", mChangedItem.getmPointId(),
										"Title",
										name.getText().toString().trim() };

								YTStringHelper.array2string(params);
								long id = JJBaseService
										.addMutilpartHttpTask(
												"http://www.ohpest.com/ohpest-pco/webservices/get/data.html",
												params,
												D2ECheckListActivity.this
														.getClass().getName(),
												D2EConfigures.TASK_CHANGETAG);

								setCurTaskID(id);
								showWaitDialog(getResources().getString(
										R.string.handling_wait));
								dialog.dismiss();
							}

						});
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
						String err = j.getString("error");
						if (err != null && err.length() > 0) {
							((JJBaseApplication) getApplication())
									.showMessage(err);
							dismissProcessDialog();
							return;
						}
					} catch (Exception ex) {
					}
				} catch (Exception ex) {
				}
				if (response == null) {
					((JJBaseApplication) getApplication())
							.showMessage(getResources().getString(
									R.string.hand_failed_try_again));
					dismissProcessDialog();
					return;
				}
				if (task.getTaskId() == D2EConfigures.TASK_GETCHECKBYLOCATION) {
					if (D2EConfigures.TEST) {
						Log.e("response != null && response.length() > 0", ""
								+ (response != null && response.length() > 0));
					}
					if (response != null && response.length() > 0) {
						// [{"ID":"1","SOPPositionID":"1","SalesClientID":"19","OrgID":"6","TagID":"13","Type":"MousePlate","Description":"1"}]
						try {
							JJBaseApplication.selectPoint = JJBaseApplication.sop_checkp;
							JSONArray jr = new JSONArray(response);
							JSONObject jo = new JSONObject(jr.getString(0));
							if (jo.getString("Type") != null
									&& jo.getString("Type").length() > 0) {
								JJBaseApplication.sop_checkp.mType = jo
										.getString("Type");
							}
							// add by shawn 2012-10-15 Begin
							// 新增设施、实施名称
							if (jo.getString("TypeName") != null
									&& jo.getString("TypeName").length() > 0) {
								JJBaseApplication.sop_checkp.setmEquipName(jo
										.getString("TypeName"));
							}
							// End

							if (JJBaseApplication.sop_checkp.mType
									.toLowerCase().equals(
											"OPSite".toLowerCase())) {
								if (jo.getString("SuppliesID") != null
										&& jo.getString("SuppliesID").length() > 0
										&& !jo.getString("SuppliesID")
												.equalsIgnoreCase("null")) {
									JSONArray SuppliesID = new JSONArray(
											jo.getString("SuppliesID"));
									JJBaseApplication.sop_checkp.mSuppliesID = SuppliesID
											.getString(0);
								} else {
									JJBaseApplication.sop_checkp.mSuppliesID = "-";
								}
								if (jo.getString("InstrumentID") != null
										&& jo.getString("InstrumentID")
												.length() > 0
										&& !jo.getString("InstrumentID")
												.equalsIgnoreCase("null")) {
									JSONArray InstrumentID = new JSONArray(
											jo.getString("InstrumentID"));
									JJBaseApplication.sop_checkp.mInstrumentID = InstrumentID
											.getString(0);
								} else {
									JJBaseApplication.sop_checkp.mInstrumentID = "-";
								}
								if (jo.getString("Supplies") != null
										&& jo.getString("Supplies").length() > 0
										&& !jo.getString("Supplies")
												.equalsIgnoreCase("null")) {
									JSONArray Supplies = new JSONArray(
											jo.getString("Supplies"));
									JJBaseApplication.sop_checkp.mSuppliesName = Supplies
											.getString(0);
								} else {
									JJBaseApplication.sop_checkp.mSuppliesName = "-";
								}
								if (jo.getString("Instrument") != null
										&& jo.getString("Instrument").length() > 0
										&& !jo.getString("Instrument")
												.equalsIgnoreCase("null")) {
									JSONArray Instrument = new JSONArray(
											jo.getString("Instrument"));
									JJBaseApplication.sop_checkp.mInstrumentName = Instrument
											.getString(0);
								} else {
									JJBaseApplication.sop_checkp.mInstrumentName = "-";
								}
								if (jo.getString("Rate") != null
										&& jo.getString("Rate").length() > 0
										&& !jo.getString("Rate")
												.equalsIgnoreCase("null")) {
									JSONArray Rate = new JSONArray(
											jo.getString("Rate"));
									JJBaseApplication.sop_checkp.mRate = Rate
											.getString(0);
								} else {
									JJBaseApplication.sop_checkp.mRate = "-";
								}
								if (jo.getString("Volume") != null
										&& jo.getString("Volume").length() > 0
										&& !jo.getString("Volume")
												.equalsIgnoreCase("null")) {
									JSONArray Volume = new JSONArray(
											jo.getString("Volume"));
									JJBaseApplication.sop_checkp.mVolume = Volume
											.getString(0);
								} else {
									JJBaseApplication.sop_checkp.mVolume = "-";
								}

							}

							// modify by shawn 2012-11-21 Begin
							// 从客户列表中取出客户，然后设置信息
							MyCustomerListData myCustomerListData = (MyCustomerListData) ytfileHelper
									.deSerialObject(getResources().getString(
											R.string.customer_list));
							myCustomerListData.getWeekSerialCustomer(
									JJBaseApplication.g_customer)
									.setSelectPoint(
											JJBaseApplication.selectPoint);
							ytfileHelper.serialObject(
									getResources().getString(
											R.string.customer_list),
									myCustomerListData);
							// End

							Intent intent = new Intent(
									D2ECheckListActivity.this,
									D2ECheckEquipsActivity.class);
							// 如果是侦测点的话需要传数据过去
							Bundle bundle = new Bundle();
							bundle.putString("mMouseNumberInt",
									String.valueOf(mMouseNumberInt));
							intent.putExtras(bundle);
							D2ECheckListActivity.this.startActivity(intent);
							overridePendingTransition(R.anim.fade, R.anim.hold);
							finish();
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					} else {
						((JJBaseApplication) getApplication())
								.showMessage(getResources().getString(
										R.string.get_data_faield));
					}

				}
				// modify by shawn 2012-9-3
				// 重新请求网络获取作业点标签
				else if (task.getTaskId() == D2EConfigures.TASK_GETCHECKBYLOCATION_TWO) {

					if (response != null && response.length() > 0) {
						try {
							JJBaseApplication.g_list.clear();
							// 从新清除依稀作业点标签
							JJBaseApplication.sop_location.mItems.clear();
							JSONArray ja = new JSONArray(response);
							int len = ja.length();
							for (int i = 0; i < len; i++) {
								String tmp = ja.getString(i);
								JSONObject jo = new JSONObject(tmp);

								TagItem item = new TagItem(
										jo.getString("Name"),
										jo.getString("TagID"));
								item.setSOPPositionName(JJBaseApplication.sop_location
										.getTitle());
								item.mIndex = jo.getString("ID");
								mChangeTageID = jo.getString("TagID");
								item.setmPointId(jo.getString("ID"));
								JJBaseApplication.sop_location.addItem(item);
								JJBaseApplication.g_list.add(item);
							}
						} catch (Exception ex) {
						}
					}
				}
				// 通过作业点id来获取作业点状态
				else if (task.getTaskId() == D2EConfigures.TASK_GET_POINT_BY_PIONT_ID) {
					if (D2EConfigures.TEST) {
						Log.e("D2EConfigures.TASK_GET_POINT_BY_PIONT_IDxxxxxxxxxxxxxxxx",
								"D2EConfigures.TASK_GET_POINT_BY_PIONT_ID");
						Log.e("response != null && response.length() > 0", ""
								+ (response != null && response.length() > 0));
					}
					if (response != null && response.length() > 0) {
						if (D2EConfigures.TEST) {
							Log.e("D2EConfigures.TASK_GET_POINT_BY_PIONT_IDxxxxxxxxxxxxxxxx",
									"D2EConfigures.TASK_GET_POINT_BY_PIONT_ID");
						}
						// [{"ID":"1","SOPPositionID":"1","SalesClientID":"19","OrgID":"6","TagID":"13","Type":"MousePlate","Description":"1"}]
						try {
							JJBaseApplication.selectPoint = JJBaseApplication.sop_checkp;
							JSONArray jr = new JSONArray(response);
							JSONObject jo = new JSONObject(jr.getString(0));
							JJBaseApplication.sop_checkp.mType = jo
									.getString("Type");
							// 是否是刷员工卡完成的
							JJBaseApplication.sop_checkp.checkByUserCard = true;
							// 保存所刷员工卡
							JJBaseApplication.sop_checkp.checkType = mTagNum;
							if (JJBaseApplication.sop_checkp.mType
									.toLowerCase().equals(
											"OPSite".toLowerCase())) {
								if (jo.getString("SuppliesID") != null
										&& jo.getString("SuppliesID").length() > 0
										&& !jo.getString("SuppliesID")
												.equalsIgnoreCase("null")) {
									JSONArray SuppliesID = new JSONArray(
											jo.getString("SuppliesID"));
									JJBaseApplication.sop_checkp.mSuppliesID = SuppliesID
											.getString(0);
								} else {
									JJBaseApplication.sop_checkp.mSuppliesID = "-";
								}
								if (jo.getString("InstrumentID") != null
										&& jo.getString("InstrumentID")
												.length() > 0
										&& !jo.getString("InstrumentID")
												.equalsIgnoreCase("null")) {
									JSONArray InstrumentID = new JSONArray(
											jo.getString("InstrumentID"));
									JJBaseApplication.sop_checkp.mInstrumentID = InstrumentID
											.getString(0);
								} else {
									JJBaseApplication.sop_checkp.mInstrumentID = "-";
								}
								if (jo.getString("Supplies") != null
										&& jo.getString("Supplies").length() > 0
										&& !jo.getString("Supplies")
												.equalsIgnoreCase("null")) {
									JSONArray Supplies = new JSONArray(
											jo.getString("Supplies"));
									JJBaseApplication.sop_checkp.mSuppliesName = Supplies
											.getString(0);
								} else {
									JJBaseApplication.sop_checkp.mSuppliesName = "-";
								}
								if (jo.getString("Instrument") != null
										&& jo.getString("Instrument").length() > 0
										&& !jo.getString("Instrument")
												.equalsIgnoreCase("null")) {
									JSONArray Instrument = new JSONArray(
											jo.getString("Instrument"));
									JJBaseApplication.sop_checkp.mInstrumentName = Instrument
											.getString(0);
								} else {
									JJBaseApplication.sop_checkp.mInstrumentName = "-";
								}
								if (jo.getString("Rate") != null
										&& jo.getString("Rate").length() > 0
										&& !jo.getString("Rate")
												.equalsIgnoreCase("null")) {
									JSONArray Rate = new JSONArray(
											jo.getString("Rate"));
									JJBaseApplication.sop_checkp.mRate = Rate
											.getString(0);
								} else {
									JJBaseApplication.sop_checkp.mRate = "-";
								}
								if (jo.getString("Volume") != null
										&& jo.getString("Volume").length() > 0
										&& !jo.getString("Volume")
												.equalsIgnoreCase("null")) {
									JSONArray Volume = new JSONArray(
											jo.getString("Volume"));
									JJBaseApplication.sop_checkp.mVolume = Volume
											.getString(0);
								} else {
									JJBaseApplication.sop_checkp.mVolume = "-";
								}
							}

							// modify by shawn 2012-11-21 Begin
							// 从客户列表中取出客户，然后设置信息
							MyCustomerListData myCustomerListData = (MyCustomerListData) ytfileHelper
									.deSerialObject(getResources().getString(
											R.string.customer_list));
							myCustomerListData.getWeekSerialCustomer(
									JJBaseApplication.g_customer)
									.setSelectPoint(
											JJBaseApplication.selectPoint);
							ytfileHelper.serialObject(
									getResources().getString(
											R.string.customer_list),
									myCustomerListData);
							// End

							Intent intent = new Intent(
									D2ECheckListActivity.this,
									D2ECheckEquipsActivity.class);
							// 如果是侦测点的话需要传数据过去
							Bundle bundle = new Bundle();
							bundle.putString("mMouseNumberInt",
									String.valueOf(mMouseNumberInt));
							bundle.putBoolean("isReplaceCard", isReplaceCard);
							intent.putExtras(bundle);
							D2ECheckListActivity.this.startActivity(intent);
							overridePendingTransition(R.anim.fade, R.anim.hold);
							finish();
						} catch (Exception ex) {
						}
					} else {
						((JJBaseApplication) getApplication())
								.showMessage(getResources().getString(
										R.string.get_data_faield));
						dismissWaitDialog();
						dismissProcessDialog();
					}

				}

				else if (task.getTaskId() == D2EConfigures.TASK_CHANGETAG) {
					try {
						JSONObject j = new JSONObject(response);
						String success = j.getString("success");
						if (success != null && success.length() > 0) {
							// 这里说明成功替换了标签，返回页面的时候应该重新获取作业点标签
							isChanged = true;
							((JJBaseApplication) getApplication())
									.showMessage(success);
						}
					} catch (Exception ex) {
					}
				}
			}

		} catch (Exception e) {
		}
		dismissProcessDialog();
		dismissWaitDialog();
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
	public boolean isExit() {
		return false;
	}

	@Override
	public void backAciton() {
		
	}

}
