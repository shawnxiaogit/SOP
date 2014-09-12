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
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
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
import com.mobilercn.sop.item.D2EPerson;
import com.mobilercn.task.JJTask;
import com.mobilercn.util.JJNetHelper;
import com.mobilercn.util.YTFileHelper;
import com.mobilercn.util.YTStringHelper;
import com.mobilercn.widget.PullToRefreshListView;

public class D2EAddLocationActivity extends JJBaseActivity {

	private static final int OP_DEL = 0;
	private static final int OP_NORMAL = 2;

	// 新增删除方式
	// 扫位置标签
	public static final int OP_DEL_BY_LOCATION_CARD = 3;
	// 扫员工卡
	public static final int OP_DEL_BY_USER_CARD = 4;

	private int mOPType = OP_NORMAL;
	private TagItem mDelItem = null;

	private TagsAdapter mTagsAdapter;
	private GridView mGridView;

	public static final String CSID = "csid";
	public static final String CSNAME = "csname";

	private String mCustom = "";
	private String mCustomID = "";

	private String mLocationName = "";
	private String mLocationID = "";
	private String mTagNum = "";

	private boolean mDelete = false;

	private BadgeView mBadgeView;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.d2e_init_page);
		Window window = getWindow();
		window.setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.d2e_titlebar);

		mOPType = OP_NORMAL;
		// extra
		Intent intent = this.getIntent();
		mCustomID = intent.getStringExtra(CSID);
		mCustom = intent.getStringExtra(CSNAME);

		JJBaseApplication.sop_location_boolean = true;
		JJBaseApplication.g_grid_type = D2EConfigures.GRID_LOCATION;

		TextView tv = (TextView) findViewById(R.id.title);
		tv.setText(getResources().getString(R.string.help_init));

		TextView ed = (TextView) findViewById(R.id.tagpage_custom);
		ed.setText(JJBaseApplication.strSelectCustomerName);

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
			mTagsAdapter.addObject("厨房", "1");
			mTagsAdapter.addObject("仓库", "2");
			mTagsAdapter.addObject("营业点", "3");
		}

		for (Object ob : JJBaseApplication.g_sop_locations) {
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
					JJBaseApplication.g_grid_type = D2EConfigures.GRID_DEL_LOCATION;
				} else {
					JJBaseApplication.g_grid_type = D2EConfigures.GRID_LOCATION;
				}

				mTagsAdapter.notifyDataSetChanged();
				return true;
			}

		});
		mGridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				if (D2EConfigures.TEST) {
					Log.e("D2EAddLocationActivity--------->", "onItemClick");
				}
				final TagItem obj = (TagItem) mTagsAdapter.getItem(arg2);
				mDelItem = obj;

				if (mDelete) {
					if ((obj.getTitle().toLowerCase().equals("") && obj.getId()
							.equals(D2EConfigures.ADD_VALUE))
							|| obj.getTitle().toLowerCase().equals("")
							&& obj.getId().equals(D2EConfigures.DONE_VALUE)) {
						mDelete = false;
						JJBaseApplication.g_grid_type = D2EConfigures.GRID_LOCATION;
						mTagsAdapter.notifyDataSetChanged();
					} else {

						Builder myBuilder = new Builder(
								D2EAddLocationActivity.this);
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
										AlertDialog.Builder builder = new AlertDialog.Builder(
												mGridView.getContext());
										builder.setTitle(getResources()
												.getString(R.string.warm_prompt));
										builder.setMessage(getResources()
												.getString(R.string.sure_delet));
										builder.setPositiveButton(
												getResources()
														.getString(
																R.string.dialog_btn_yes_text),
												new DialogInterface.OnClickListener() {
													public void onClick(
															DialogInterface dialog,
															int which) {

														if (D2EConfigures.G_USEVIRCALDEVICE) {
															mTagsAdapter
																	.removeItem(mDelItem);
															mTagsAdapter
																	.notifyDataSetChanged();
															((JJBaseApplication) getApplication())
																	.showMessage(getResources()
																			.getString(
																					R.string.delete_succe));
														} else {
															mOPType = OP_DEL_BY_USER_CARD;
															showProcessDialog(getResources()
																	.getString(
																			R.string.reading_card));
															SOPBluetoothService
																	.getService()
																	.doTask(SOPBTCallHelper.READ_TAG,
																			D2EAddLocationActivity.class
																					.getName(),
																			SOPBTCallHelper
																					.getInitOrder(SOPBluetoothService.g_readerID));
														}
													}
												});
										builder.setNegativeButton(
												getResources()
														.getString(
																R.string.btn_d2eprompt_cancle),
												null);
										builder.create().show();
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
												AlertDialog.Builder builder = new AlertDialog.Builder(
														mGridView.getContext());
												builder.setTitle(getResources()
														.getString(
																R.string.warm_prompt));
												builder.setMessage(getResources()
														.getString(
																R.string.sure_delet));
												builder.setPositiveButton(
														getResources()
																.getString(
																		R.string.dialog_btn_yes_text),
														new DialogInterface.OnClickListener() {
															public void onClick(
																	DialogInterface dialog,
																	int which) {

																if (D2EConfigures.G_USEVIRCALDEVICE) {
																	mTagsAdapter
																			.removeItem(mDelItem);
																	mTagsAdapter
																			.notifyDataSetChanged();
																	((JJBaseApplication) getApplication())
																			.showMessage(getResources()
																					.getString(
																							R.string.delete_succe));
																} else {
																	mOPType = OP_DEL_BY_LOCATION_CARD;
																	showProcessDialog(getResources()
																			.getString(
																					R.string.reading_card));
																	SOPBluetoothService
																			.getService()
																			.doTask(SOPBTCallHelper.READ_TAG,
																					D2EAddLocationActivity.class
																							.getName(),
																					SOPBTCallHelper
																							.getInitOrder(SOPBluetoothService.g_readerID));
																}
															}
														});
												builder.setNegativeButton(
														getResources()
																.getString(
																		R.string.btn_d2eprompt_cancle),
														null);
												builder.create().show();
											}
										});
						myBuilder.show();
						//

					}

					return;
				}

				mLocationName = obj.getTitle();
				mLocationID = obj.getId();
				if (D2EConfigures.TEST) {
					Log.e("obj.getTitle()--------->", "" + (obj.getTitle()));
					Log.e("obj.getId()--------->", "" + (obj.getId()));
				}
				if (obj.getTitle().toLowerCase().equals("")
						&& obj.getId().equals(D2EConfigures.ADD_VALUE)) { // 扫描标签
					if (D2EConfigures.TEST) {
						Log.e("D2EAddLocationActivity--------->",
								"D2EConfigures.ADD_VALUE");
					}
					showProcessDialog(getResources().getString(
							R.string.reading_card));
					SOPBluetoothService
							.getService()
							.doTask(SOPBTCallHelper.READ_TAG,
									D2EAddLocationActivity.class.getName(),
									SOPBTCallHelper
											.getInitOrder(SOPBluetoothService.g_readerID));
				} else if (obj.getTitle().toLowerCase().equals("")
						&& obj.getId().equals(D2EConfigures.DONE_VALUE)) {
					if (D2EConfigures.TEST) {
						Log.e("D2EAddLocationActivity--------->",
								"D2EConfigures.DONE_VALUE");
					}
					showProcessDialog(getResources().getString(
							R.string.reading_card));
					SOPBluetoothService
							.getService()
							.doTask(SOPBTCallHelper.TAG_RETURN,
									D2EAddLocationActivity.class.getName(),
									SOPBTCallHelper
											.getInitOrder(SOPBluetoothService.g_readerID));
				} else {

					if (D2EConfigures.G_USEVIRCALDEVICE) {

						Intent intent = new Intent(D2EAddLocationActivity.this,
								D2EAddCheckActivity.class);
						intent.putExtra("custom", mCustom);
						intent.putExtra("position", mLocationName);
						intent.putExtra("locationID", mLocationID);
						intent.putExtra("customid", mCustomID);
						D2EAddLocationActivity.this.startActivity(intent);
						overridePendingTransition(R.anim.fade, R.anim.hold);
					} else {
						// 设置检查点标签
						String[] params = new String[] { "FunType",
								"getSOPPointList_byPositionID", "OrgID",
								JJBaseApplication.user_OrgID, "PositionID",
								mLocationID };

						long id = JJBaseService
								.addMutilpartHttpTask(
										"http://www.ohpest.com/ohpest-pco/webservices/get/data.html",
										params, D2EAddLocationActivity.this
												.getClass().getName(),
										D2EConfigures.TASK_GETCHECKBYLOCATION);
						YTStringHelper.array2string(params);
						setCurTaskID(id);
						showWaitDialog(getResources().getString(
								R.string.handling_wait));
					}

				}

			}

		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (D2EConfigures.TEST) {
			Log.e("D2EAddLocationActivity------------->", "onResume()");
		}
		mTagsAdapter.clear();
		JJBaseApplication.g_grid_type = D2EConfigures.GRID_LOCATION;
		for (Object ob : JJBaseApplication.g_sop_locations) {
			if (ob instanceof TagItem) {
				mTagsAdapter.addObject((TagItem) ob);
				mTagsAdapter.notifyDataSetChanged();
			}
		}
		mTagsAdapter.addObject("", D2EConfigures.ADD_VALUE);
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (D2EConfigures.TEST) {
			Log.e("D2EAddLocationActivity------------->", "onPause()");
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (D2EConfigures.TEST) {
			Log.e("D2EAddLocationActivity------------->", "onDestroy()");
		}
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {

		case R.id.menu_back: {

			finish();
		}
			break;
		case R.id.menu_back_home_page:
			Intent intent = new Intent(D2EAddLocationActivity.this,
					D2EMainScreen.class);
			D2EAddLocationActivity.this.startActivity(intent);
			overridePendingTransition(R.anim.fade, R.anim.hold);
			finish();
			break;
		case R.id.menu_back_previous:
			Intent intent2 = new Intent(D2EAddLocationActivity.this,
					D2ECustomerActivity.class);
			D2EAddLocationActivity.this.startActivity(intent2);
			overridePendingTransition(R.anim.fade, R.anim.hold);
			finish();
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
				dismissProcessDialog();

				mTagNum = response.substring(14, 22);
				if (state == SOPBTCallHelper.READ_TAG) {

					// 扫原标签删除位置标签
					if (mOPType == OP_DEL_BY_LOCATION_CARD) {
						mOPType = OP_NORMAL;
						String[] params = new String[] { "FunType",
								"delPosition", "OrgID",
								JJBaseApplication.user_OrgID, "ReadNum",
								SOPBluetoothService.g_readerID, "TagNum",
								mTagNum };

						if (D2EConfigures.TEST) {
							YTStringHelper.array2string(params);
						}
						long id = JJBaseService
								.addMutilpartHttpTask(
										"http://www.ohpest.com/ohpest-pco/webservices/get/data.html",
										params, D2EAddLocationActivity.this
												.getClass().getName(),
										D2EConfigures.TASK_DELITEM);
						setCurTaskID(id);
						showWaitDialog(getResources().getString(
								R.string.handling_wait));
						return;
					} else if (mOPType == OP_DEL_BY_USER_CARD) {
						mOPType = OP_NORMAL;
						String[] params = new String[] { "FunType",
								"delPosition", "OrgID",
								JJBaseApplication.user_OrgID, "ReadNum",
								SOPBluetoothService.g_readerID, "TagNum",
								mTagNum, "PositionID", mDelItem.getId() };

						if (D2EConfigures.TEST) {
							YTStringHelper.array2string(params);
						}
						long id = JJBaseService
								.addMutilpartHttpTask(
										"http://www.ohpest.com/ohpest-pco/webservices/get/data.html",
										params, D2EAddLocationActivity.this
												.getClass().getName(),
										D2EConfigures.TASK_DELITEM);
						setCurTaskID(id);
						showWaitDialog(getResources().getString(
								R.string.handling_wait));
						return;
					}
					// add by shawn 2012-9-19 Begin
					// 判断卡片是否已经被使用了
					else {
						String[] params = new String[] { "FunType", "checkTag",
								"OrgID", JJBaseApplication.user_OrgID,
								"ReadNum", SOPBluetoothService.g_readerID,
								"TagNum", mTagNum };

						YTStringHelper.array2string(params);
						long id = JJBaseService
								.addMutilpartHttpTask(
										"http://www.ohpest.com/ohpest-pco/webservices/get/data.html",
										params, D2EAddLocationActivity.this
												.getClass().getName(),
										D2EConfigures.TASK_CHECK_TAG);
						setCurTaskID(id);
					}
					// End
				} else if (state == SOPBTCallHelper.TAG_LOCATION) {
					String[] params = new String[] { "FunType",
							"getSOPPointList", "OrgID",
							JJBaseApplication.user_OrgID, "LocTagNum", mTagNum };
					YTStringHelper.array2string(params);
					long id = JJBaseService
							.addMutilpartHttpTask(
									"http://www.ohpest.com/ohpest-pco/webservices/get/data.html",
									params, D2EAddLocationActivity.this
											.getClass().getName(),
									D2EConfigures.TASK_GETCHECKBYLOCATION);

					setCurTaskID(id);
					return;
				} else if (state == SOPBTCallHelper.TAG_RETURN) {
					String locationID = response.substring(14, 22);

					if (D2EConfigures.G_DEBUG) {
						locationID = "93edb888";
					}

					String[] params = new String[] { "FunType",
							"getSalesClientDataWithTask", "OrgID",
							JJBaseApplication.user_OrgID, "ReadNum",
							SOPBluetoothService.g_readerID, "TagNum",
							locationID, "UserID", JJBaseApplication.user_ID };
					long id = JJBaseService
							.addMutilpartHttpTask(
									"http://www.ohpest.com/ohpest-pco/webservices/get/data.html",
									params, D2EAddLocationActivity.this
											.getClass().getName(),
									D2EConfigures.TASK_GET_CUSTOM_INFO);
					setCurTaskID(id);
					return;
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
				if (D2EConfigures.G_DEBUG) {
					Log.e("response: >>>>>> ", response);
				}
				try {
					JSONObject j = new JSONObject(response);
					if (D2EConfigures.TEST) {
						Log.e("reponse=======>", "" + j);
					}
					String err = null;
					if (j.has("error")) {
						err = j.getString("error");
						// add by shawn 2012- 10-30
						// 修改为自定义布局的toast
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
			if (task.getTaskId() == D2EConfigures.TASK_ADDLOCATION) {
				try {
					JSONObject j = new JSONObject(response);
					JJBaseApplication.g_sop_locations.add(new TagItem(j
							.getString("Name"), j.getString("ID")));
					mTagsAdapter.insertObject(j.getString("Name"),
							j.getString("ID"), mTagNum);
					mTagsAdapter.notifyDataSetChanged();

				} catch (Exception ex) {
				}
			}
			// 判断是否使用了卡片
			else if (task.getTaskId() == D2EConfigures.TASK_CHECK_TAG) {
				if (D2EConfigures.TEST) {
					Log.e("D2EConfigures.TASK_CHECK_TAG=========>",
							"D2EConfigures.TASK_CHECK_TAG");
				}

				if (response != null && response.length() > 0) {
					JSONObject jo = null;
					try {
						jo = new JSONObject(response);
						if (D2EConfigures.TEST) {
							Log.e("response------------->", "" + response);
							Log.e("responseJSONOBJEct------------>",
									"" + jo.toString());
						}

						LayoutInflater inflater = getLayoutInflater();
						final View layout = inflater.inflate(
								R.layout.d2e_enterlocation, null);
						final TextView te = (TextView) layout
								.findViewById(R.id.location_id);
						final EditText name = (EditText) layout
								.findViewById(R.id.location_name);
						// 数据测试
						te.setText(mTagNum);
						Builder buildertmp = new Builder(
								D2EAddLocationActivity.this);
						buildertmp.setIcon(android.R.drawable.ic_dialog_info);
						buildertmp.setTitle(getResources().getString(
								R.string.new_location));
						buildertmp.setView(layout);
						final AlertDialog dialog = buildertmp.show();
						// add by shawn 2012-9-8 Begin
						// 设置对话框的大小
						dialog.getWindow().setLayout(450, 500);
						// End
						// 确定提交
						Button btn_yes = (Button) layout
								.findViewById(R.id.btn_yes);
						btn_yes.setOnClickListener(new OnClickListener() {

							public void onClick(View arg0) {

								if (D2EConfigures.G_USEVIRCALDEVICE) {

									mTagsAdapter.insertObject(name.getText()
											.toString(), "11", te.getText()
											.toString());
									mTagsAdapter.notifyDataSetChanged();
									((JJBaseApplication) getApplication())
											.showMessage(getResources()
													.getString(
															R.string.add_success));

								} else {
									// 先提交
									// $postOrgID,$postCSID,$postReadNum,$postTagNum,$postTitle
									if (te.getText().toString() != null
											&& te.getText().toString().length() > 0
											&& name.getText().toString() != null
											&& name.getText().toString()
													.length() > 0) {
										String[] params = new String[] {
												"FunType",
												"insertSOPPosition",
												"OrgID",
												JJBaseApplication.user_OrgID,
												"CSID",
												JJBaseApplication.strSelectLocationID,
												"ReadNum",
												SOPBluetoothService.g_readerID,
												"TagNum",
												te.getText().toString(),
												"Title",
												name.getText().toString() };
										if (D2EConfigures.TEST) {
											YTStringHelper.array2string(params);
										}
										long id = JJBaseService
												.addMutilpartHttpTask(
														"http://www.ohpest.com/ohpest-pco/webservices/get/data.html",
														params,
														D2EAddLocationActivity.this
																.getClass()
																.getName(),
														D2EConfigures.TASK_ADDLOCATION);
										setCurTaskID(id);
										showWaitDialog(getResources()
												.getString(
														R.string.handling_wait));
										dialog.dismiss();
									} else {
										((JJBaseApplication) getApplication())
												.showMessage(getResources()
														.getString(
																R.string.input_loca_name));
									}
								}
							}
						});

						// 重置
						Button btn_clear = (Button) layout
								.findViewById(R.id.btn_clear);
						btn_clear.setOnClickListener(new OnClickListener() {
							public void onClick(View arg0) {
								name.setText("");
							}
						});

						Button btn_retry = (Button) layout
								.findViewById(R.id.btn_retry);
						btn_retry.setOnClickListener(new OnClickListener() {
							public void onClick(View arg0) {
								showProcessDialog(getResources().getString(
										R.string.reading_card));
								SOPBluetoothService
										.getService()
										.doTask(SOPBTCallHelper.READ_TAG,
												D2EAddLocationActivity.class
														.getName(),
												SOPBTCallHelper
														.getInitOrder(SOPBluetoothService.g_readerID));
								dialog.dismiss();
							}
						});

					} catch (JSONException e) {
						e.printStackTrace();
					}

				}
			}

			else if (task.getTaskId() == D2EConfigures.TASK_GETCHECKBYLOCATION) {

				if (response != null && response.length() > 0) {
					D2ESOPActivity dps = new D2ESOPActivity();
					try {
						JJBaseApplication.mPointList.clear();
						JSONArray ja = new JSONArray(response);
						int len = ja.length();
						for (int i = 0; i < len; i++) {
							String tmp = ja.getString(i);
							JSONObject jo = new JSONObject(tmp);
							TagItem item = new TagItem(jo.getString("Name"),
									jo.getString("ID"));
							JJBaseApplication.mPointList.add(item);
						}
						Intent intent = new Intent(D2EAddLocationActivity.this,
								D2EAddCheckActivity.class);
						intent.putExtra("custom", mCustom);
						intent.putExtra("position", mLocationName);
						intent.putExtra("locationID", mLocationID);
						intent.putExtra("customid", mCustomID);
						D2EAddLocationActivity.this.startActivity(intent);
						overridePendingTransition(R.anim.fade, R.anim.hold);
						finish();
					}

					catch (Exception ex) {
					}
				} else {
					((JJBaseApplication) getApplication())
							.showMessage(getResources().getString(
									R.string.hand_failed_try_again));
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
						intent.setClass(D2EAddLocationActivity.this,
								D2ESOPActivity.class);
						D2EAddLocationActivity.this.startActivity(intent);
						overridePendingTransition(R.anim.fade, R.anim.hold);
						finish();
					} else {
						((JJBaseApplication) getApplication())
								.showMessage(getString(R.string.no_weeksheet_data));
					}
				}
			} else if (task.getTaskId() == D2EConfigures.TASK_DELITEM) {
				if (D2EConfigures.TEST) {
					Log.e("TASK_DELITEM-------->", "TASK_DELITEM");
				}
				mDelete = false;
				mOPType = OP_NORMAL;
				JJBaseApplication.g_sop_locations.remove(mDelItem);
				mTagsAdapter.removeItem(mDelItem);
				mTagsAdapter.notifyDataSetChanged();
				JJBaseApplication.g_grid_type = D2EConfigures.GRID_LOCATION;
				try {
					JSONObject j = new JSONObject(response);
					String success = null;
					String error = null;
					if (j.has("success")) {
						success = j.getString("success");
						((JJBaseApplication) getApplication())
								.showMessage(success);
					}
					if (j.has("error")) {
						error = j.getString("error");
						((JJBaseApplication) getApplication())
								.showMessage(error);
					}
				} catch (Exception ex) {
				}

			}
		}
		dismissProcessDialog();
	}

	@Override
	public void taskFailed(Object... param) {
		if (D2EConfigures.TEST) {
			Log.e("taskFailed=================>", "taskFailed()");
		}
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
