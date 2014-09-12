package com.mobilercn.sop.activity;

import java.io.InputStream;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mobilercn.base.BadgeView;
import com.mobilercn.base.JJBaseActivity;
import com.mobilercn.base.JJBaseApplication;
import com.mobilercn.base.JJBaseService;
import com.mobilercn.config.D2EConfigures;
import com.mobilercn.sop.R;
import com.mobilercn.sop.SOPAndroidActivity;
import com.mobilercn.sop.activity.view.WTDateUpdateListener;
import com.mobilercn.sop.activity.view.WTMonthView;
import com.mobilercn.sop.bt.SOPBTCallHelper;
import com.mobilercn.sop.bt.SOPBluetoothService;
import com.mobilercn.sop.data.MyCustomerListData;
import com.mobilercn.sop.data.TagItem;
import com.mobilercn.sop.item.D2ECustomer;
import com.mobilercn.sop.item.D2EDocumentItem;
import com.mobilercn.sop.item.D2EDocuments;
import com.mobilercn.sop.item.D2EListAdapter;
import com.mobilercn.sop.item.D2EListAdapterItam;
import com.mobilercn.sop.item.D2EPerson;
import com.mobilercn.task.JJTask;
import com.mobilercn.util.JJNetHelper;
import com.mobilercn.util.YTFileHelper;
import com.mobilercn.util.YTStringHelper;

public class D2EReportActivity extends JJBaseActivity implements
		WTDateUpdateListener {
	

	private TextView mDateTitle;

	private static final int REQUEST_ENABLE_BT = 1;
	private static final int REQUESR_DEVICE = 2;

	private static final String READER_PREFS_NAME = "READERID";
	private static final String BT_MAC_KEY = "BTMAC";
	private static final String READER_KEY = "READER";

	public static String EXTRA_DEVICE_ADDRESS = "device_address";
	public static String EXTRA_READER_ID = "reader_id";

	private boolean mIsWarningOnce = false;
	private BadgeView mBadgeView;

	private D2EListAdapter mTagsAdapter;

	private String mID = "";

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

	/**
	 * 判断受否选择了客户
	 */
	public static boolean isACTVselect;
	public static String mTemptID;

	
	
    protected void onResume() {
    	super.onResume();
    }

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.d2e_report_page);
		Window window = getWindow();
		window.setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.d2e_titlebar);
		isACTVselect = false;
		mTemptID = null;
		TextView tv = (TextView) findViewById(R.id.title);
		tv.setText(getResources().getString(R.string.menu_report));

		mBadgeView = (BadgeView) findViewById(R.id.countTask);
		mBadgeView.setBadgePosition(BadgeView.POSITION_CENTER);
		mBadgeView.setBackgroundResource(R.drawable.badge_ifaux);
		mBadgeView.setVisibility(View.GONE);

		if (JJBaseApplication.user_CountTask != null
				&& JJBaseApplication.user_CountTask.length() > 0) {
			mBadgeView.setText(JJBaseApplication.user_CountTask);
		}

		// grid
		mTagsAdapter = new D2EListAdapter(getApplicationContext());

		final AutoCompleteTextView atv = (AutoCompleteTextView) findViewById(R.id.ckpoint_state);
		if (JJBaseApplication.g_customer != null
				&& JJBaseApplication.g_customer.mName != null
				&& JJBaseApplication.g_customer.mName.length() > 0) {
			atv.setText(JJBaseApplication.g_customer.mName);
			mID = JJBaseApplication.g_customer.mId;
		} else {
			atv.setText("");
		}

		if (D2EConfigures.G_USEVIRCALDEVICE) {
			atv.setText("可口可乐");
		}

		atv.setThreshold(1);
		atv.addTextChangedListener(mWatcher);
		atv.setAdapter(mTagsAdapter);
		atv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long id) {
				isACTVselect = true;
				D2EListAdapterItam item = (D2EListAdapterItam) mTagsAdapter
						.getItem(position);

				atv.setText(item.getTitle());
				//将光标置于末尾
				atv.setSelection(item.getTitle().length());
				mID = item.getId();
				mTemptID = mID;
			}

		});

		Button btn1 = (Button) findViewById(R.id.loginImageButton02);
		btn1.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				if(D2EConfigures.TEST){
					Log.e("atv.getText()--------->", ""+(atv.getText()));
					Log.e("btn_img_btn--------->", "OnClick()");
				}
				if(atv.getText()!=null&&atv.getText().length()>0){
					atv.setText("");
				}
				atv.showDropDown();
				atv.setThreshold(1);
			}
		});

		for (Object item : JJBaseApplication.mClientList) {
			if (item instanceof D2EListAdapterItam) {
				mTagsAdapter.addObject((D2EListAdapterItam) item);
				mTagsAdapter.notifyDataSetChanged();

			}

		}

		// for calendar
		final WTMonthView month = (WTMonthView) this
				.findViewById(R.id.monthview);
		month.setDateListener(this);

		mDateTitle = (TextView) findViewById(R.id.calendar_title_text);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		mDateTitle.setText(sdf.format(new Date()));

		Button pre = (Button) findViewById(R.id.calendar_arrow_pre);
		pre.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Date date = month.changeMonth(false);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				mDateTitle.setText(sdf.format(date));
			}

		});

		Button next = (Button) findViewById(R.id.calendar_arrow_next);
		next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Date date = month.changeMonth(true);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				mDateTitle.setText(sdf.format(date));
			}

		});

	}

	@Override
	public void backAciton() {
		Intent intent = new Intent(D2EReportActivity.this,
				D2EMainScreen.class);
		D2EReportActivity.this.startActivity(intent);
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
	public void taskSuccess(Object... param) {
		int values = ((Integer) param[0]).intValue();
		if (values == JJBaseService.BT_SERVICE_INT) {
			String response = (String) param[2];
			if (response.toLowerCase().equals(
					SOPBluetoothService.STATE_FAILED.toLowerCase())) {
				((JJBaseApplication) getApplication())
						.showMessage(getResources().getString(R.string.failed_read));
				dismissProcessDialog();
				return;
			} else {
				String locationID = response.substring(14, 22);

				if (D2EConfigures.G_DEBUG) {
					locationID = "93edb888";
				}

				String[] params = new String[] { "FunType",
						"getSalesClientDataWithTask", "OrgID",
						JJBaseApplication.user_OrgID, "ReadNum",
						SOPBluetoothService.g_readerID, "TagNum", locationID,
						"UserID", JJBaseApplication.user_ID };
				long id = JJBaseService
						.addMutilpartHttpTask(
								"http://www.ohpest.com/ohpest-pco/webservices/get/data.html",
								params, D2EReportActivity.this.getClass()
										.getName(),
								D2EConfigures.TASK_GET_CUSTOM_INFO);
				
				setCurTaskID(id);

			}
		} else if (values == JJBaseService.HTTP_SERVICE_INT) {
			JJTask task = (JJTask) param[1];
			String response = null;
			try {
				InputStream ins = task.getInputStream();
				byte[] bytes = JJNetHelper.readByByte(ins, -1);

				response = new String(bytes, "UTF-8");
				ins.close();

				Log.e("response: >>>>>> ", response);
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
			if (response == null) {
				((JJBaseApplication) getApplication()).showMessage(getResources().getString(R.string.hand_failed_try_again));
				dismissProcessDialog();
				return;
			}
			if (task.getTaskId() == D2EConfigures.TASK_CHECKCODE) {
				// 数据处理
				try {

					Intent intent = new Intent();
					intent.setClass(D2EReportActivity.this,
							D2ECustomerActivity.class);
					intent.putExtra(D2ECustomerActivity.PAGE_TYPE,
							D2ECustomerActivity.TYPE_SOP);

					if (response != null && response.length() > 0) {
						JJBaseApplication.g_list.clear();
						try {
							JSONArray ja = new JSONArray(response);
							int len = ja.length();
							for (int i = 0; i < len; i++) {
								String tmp = ja.getString(i);
								JSONObject jo = new JSONObject(tmp);
								TagItem item = new TagItem(
										jo.getString("Name"),
										jo.getString("ID"));
								JJBaseApplication.g_list.add(item);
							}
						} catch (Exception ex) {
						}
					}
					D2EReportActivity.this.startActivity(intent);
					overridePendingTransition(R.anim.fade, R.anim.hold);
					finish();
				} catch (Exception ex) {
				}
			} else if (task.getTaskId() == D2EConfigures.TASK_GETREPORTS) {
				try {
					Intent intent = new Intent();
					intent.setClass(D2EReportActivity.this,
							D2EReportViewActivity.class);
					intent.putExtra("type",
							D2EReportViewActivity.VIEW_TYPE_REPORT);
					if (response != null && response.length() > 0) {
						JSONObject jo = new JSONObject(response);
						//首先清空报表列表
						JJBaseApplication.g_sopReprot.clear();
						// 作业点报表
						if (!jo.getString("SOPReport").equalsIgnoreCase("null")) {
							JSONObject report = null;
							report = new JSONObject(jo.getString("SOPReport"));
							if(D2EConfigures.TEST){
								Log.e("report------->", "" + report);
							}
							JSONArray SOPReport = null;
							if (null != report && report.length() > 0) {
								for (int i = 0; i < report.length(); i++) {
									if(!report.getString("Task"+i).equalsIgnoreCase("null")){
										SOPReport = new JSONArray(
												report.getString("Task" + i));
										JJBaseApplication.mTasKJSONArray.put("Task"+i, SOPReport);
									}
									
									if (SOPReport != null) {
										TagItem items = null;
										int len = SOPReport.length();
										for (int j = 0; j < len; j++) {
											JSONObject jsop = new JSONObject(
													SOPReport.getString(j));
											items = new TagItem(
													jsop.getString("SOPPointName"),
													jsop.getString("SOPPointID"));
											items.mMemo = jsop
													.getString("Memo");
											items.mType = jsop
													.getString("SOPPointType");
											items.mStateInt = jsop
													.getString("State");
											// 将任务Id记入下来,因此item必须添加一个字段TaskID
											items.mTaskID = jsop
													.getString("TaskID");
											// 将任务Id放到一个集合中
											items.mItemTaskId.put("taskId",
													items.mTaskID);
											// 将位置标签的名字也保存下来，以便读取
											items.setSOPPositionName(jsop
													.getString("SOPPositionName"));
											
											//add by shawn 2012-10-15 Begin
											//获取的时候判断是否有PointTypeName字段(这是为了处理其他设施、其他实施添加的)
											if(D2EConfigures.TEST){
												Log.e("PointTypeName========>", ""+(jsop.getString("PointTypeName")));
											}
											if(jsop.has("PointTypeName")){
												if(jsop.getString("PointTypeName")!=null&&jsop.getString("PointTypeName").length()>0){
													items.setmEquipName(jsop.getString("PointTypeName"));
												}else{
													items.setmEquipName(jsop.getString("-"));
												}
											}
											//
											
											if(jsop.has("Instrument")){
												if(jsop.getString("Instrument")!=null&&jsop.getString("Instrument").length()>0){
													items.mInstrumentName = jsop.getString("Instrument");
												}else{
													items.mInstrumentName = jsop.getString("-");
												}
												
											}else{
												items.mInstrumentName="-";
											}
											if(jsop.has("Supplies")){
												if(jsop.getString("Supplies")!=null&&jsop.getString("Supplies").length()>0){
													items.mSuppliesName = jsop.getString("Supplies");
												}else{
													items.mSuppliesName = jsop.getString("-");
												}
												
											}else{
												items.mSuppliesName="-";
											}
											if(jsop.has("RealRate")){
												if(jsop.getString("RealRate")!=null&&jsop.getString("RealRate").length()>0){
													items.mRateFact = jsop.getString("RealRate");
												}else{
													items.mRateFact = jsop.getString("-");
												}
												
											}else{
												items.mRateFact="-";
											}
											if(jsop.has("RealVolume")){
												if(jsop.getString("RealVolume")!=null&&jsop.getString("RealVolume").length()>0){
													items.mVolumeFact = jsop.getString("RealVolume");
												}else{
													items.mVolumeFact = jsop.getString("-");
												}
											}else{
												items.mVolumeFact="-";
											}
											JJBaseApplication.g_sopReprot.add(items);
										}
									}
									JJBaseApplication.taskIdMap.put("Task"+ i,JJBaseApplication.g_sopReprot);
								}
								
							}
						}
						//首先清空报表列表
						JJBaseApplication.g_monitorReprot.clear();
						// 监测点报表
						if (!jo.getString("MonitorReport").equalsIgnoreCase("null")) {
							JSONObject monitorReport = null;
							monitorReport = new JSONObject(jo.getString("MonitorReport"));
							if(D2EConfigures.TEST){
								Log.e("monitorReport-------------->", ""+ monitorReport);
								Log.e("monitorReport.Length()-------------->", ""+ monitorReport.length());
							}
							JSONArray MonitorReport=null;
							if (monitorReport != null&&monitorReport.length()>0) {
								int size = monitorReport.length();
								for (int i = 0; i < size; i++) {
									if(D2EConfigures.TEST){
										Log.e("(monitorReport.getString(Task+0)", ""+monitorReport.getString("Task"+i));
									}
									if(monitorReport.getString("Task"+i)!=null&&monitorReport.getString("Task"+i).length()>0){
										MonitorReport=new JSONArray(monitorReport.getString("Task"+i));
										
									}else{
										MonitorReport=new JSONArray();
									}
									JJBaseApplication.mMonitorTaskJSONArray.put("Task"+i, MonitorReport);
									if(D2EConfigures.TEST){
										Log.i("MonitorReport------------>", ""+MonitorReport);
									}
									for(int k=0;k<MonitorReport.length();k++){
										JSONObject jsop=new JSONObject(MonitorReport.getString(k));
										TagItem items =new TagItem(
												jsop.getString("SOPPointName"),jsop.getString("SOPPointID"));
										items.mMemo = jsop.getString("Memo");
										items.mType = jsop.getString("SOPPointType");
										items.setSOPPositionName(jsop
												.getString("SOPPositionName"));
										//如果有老鼠的数量则获取
										if(jsop.has("Results")){
											if(jsop.getString("Results")!=null&&jsop.getString("Results").length()>0){
												items.mMounseNumber=jsop.getString("Results");
											}else{
												
											}
										}else{
											items.mMounseNumber=jsop.getString("-");
										}
										if(jsop.has("TaskID")){
											if(jsop.getString("TaskID")!=null&&jsop.getString("TaskID").length()>0){
												items.mTaskID = jsop.getString("TaskID");
											}else{
												items.mTaskID = jsop.getString("-");
											}
											
										}else if(jsop.has("TaskID1")){
											if(jsop.getString("TaskID1")!=null&&jsop.getString("TaskID1").length()>0){
												items.mTaskID=jsop.getString("TaskID1");
											}else{
												items.mTaskID=jsop.getString("-");
											}
										}else{
											items.mTaskID=jsop.getString("-");
										}
										
										String mytype = null;
										try {
											if(jsop.getString("Type")!=null&&jsop.getString("Type").length()>0){
												mytype = jsop.getString("Type");
											}else{
												mytype = jsop.getString("-");
											}
										} catch (Exception e) {
										}

										if (mytype != null && mytype.equals("1")) {
											try {
												if(jsop.getString("Facilities")!=null&&jsop.getString("Facilities").length()>0){
													items.mMouseInit = jsop.getString("Facilities");
												}else{
													items.mMouseInit = jsop.getString("-");
												}
												
											} catch (Exception e) {
											}

											try {
												if(jsop.getString("EffFacilities")!=null&&jsop.getString("EffFacilities").length()>0){
													items.mMouseActive = jsop.getString("EffFacilities");
												}else{
													items.mMouseActive = jsop.getString("-");
												}
											} catch (Exception e) {
											}

											try {
												if(jsop.getString("Results")!=null&&jsop.getString("Results").length()>0){
													items.mMouseTotal = jsop.getString("Results");
												}else{
													items.mMouseTotal = jsop.getString("-");
												}
											} catch (Exception e) {
											}

										} else if (mytype != null
												&& mytype.equals("2")) {
											try {
												if(jsop.getString("Facilities")!=null&&jsop.getString("Facilities").length()>0){
													items.mRoachInit = jsop.getString("Facilities");
												}else{
													items.mRoachInit = jsop.getString("-");
												}
											} catch (Exception e) {
											}

											try {
												if(jsop.getString("EffFacilities")!=null&&jsop.getString("EffFacilities").length()>0){
													items.mRoachActive = jsop.getString("EffFacilities");
												}else{
													items.mRoachActive = jsop.getString("-");
												}
											} catch (Exception e) {
											}

											try {
												if(jsop.getString("Results")!=null&&jsop.getString("Results").length()>0){
													items.mRoachTotal = jsop.getString("Results");
												}else{
													items.mRoachTotal = jsop.getString("-");
												}
											} catch (Exception e) {
											}
										}
										JJBaseApplication.g_monitorReprot.add(items);
									}
								}
									
							}
							
						}
					}
					//没有报表数据不跳转界面，提示没有报表信息
					if((JJBaseApplication.g_sopReprot!=null&&JJBaseApplication.g_sopReprot.size()>0)
							||(JJBaseApplication.g_monitorReprot!=null&&JJBaseApplication.g_monitorReprot.size()>0)){
						D2EReportActivity.this.startActivity(intent);
					}else{
						((JJBaseApplication) getApplication())
						.showMessage(getResources().getString(R.string.the_date_no_work_sheet));
					}
					overridePendingTransition(R.anim.fade, R.anim.hold);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (task.getTaskId() == D2EConfigures.TASK_GETWORKSHEET) {
				// {"CountTask":["1"],"BeginTime":["120611"]}
				try {
					JJBaseApplication.g_worksheet.clear();
					JSONObject res = new JSONObject(response);

					if (res.getString("Assignment") == null
							|| res.getString("Assignment").toLowerCase()
									.equals("null")) {
						((JJBaseApplication) getApplication())
								.showMessage(getResources().getString(R.string.no_weeksheet_data));
						dismissProcessDialog();
						return;
					}

					JSONObject assignment = new JSONObject(
							res.getString("Assignment"));
					JSONArray jc = new JSONArray(
							assignment.getString("CountTask"));
					JSONArray jd = new JSONArray(
							assignment.getString("BeginTime"));

					int len = jc.length();
					int sum=0;
					for (int i = 0; i < len; i++) {
						JJBaseApplication.g_worksheet.put(jd.getString(i),
								jc.getString(i));
						sum+=Integer.valueOf(jc.getString(i));
					}
					if(D2EConfigures.TEST){
		    			Log.e("D2EConfigures.g_worksheet-------->", ""+JJBaseApplication.g_worksheet.toString());
		    			Log.e("sum--------------->", ""+sum);
		    		}
		    		
		    		JJBaseApplication.user_CountTask=String.valueOf(sum);
		    		if(D2EConfigures.TEST){
		    			Log.e("D2EConfigures.user_CountTask-------->", JJBaseApplication.user_CountTask);
		    		}
					// 用于天气
					JJBaseApplication.g_weather.clear();
					JSONArray weather = new JSONArray(res.getString("Weather"));
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
					Calendar c = new GregorianCalendar();
					c.setTime(new Date());
					for (int j = 0; j < D2EConfigures.WEATHER_COUNT; j++) {
						JSONObject tmp = new JSONObject(weather.getString(j));
						JJBaseApplication.g_weather.put(sdf.format(c.getTime()),
								tmp.getString("temp"));
						c.add(Calendar.DAY_OF_MONTH, 1);
					}

					Intent intent = new Intent(D2EReportActivity.this,
							D2EWorksheetActivity.class);
					intent.putExtra("type", D2EWorksheetActivity.TYPE_WORKWHEET);
					D2EReportActivity.this.startActivity(intent);
					overridePendingTransition(R.anim.fade, R.anim.hold);
					finish();
				} catch (Exception ex) {
				}
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
						JJBaseApplication.g_customer.mTaskId = jt.getString("ID");

						// 正常的服务是1 部署是2 监测是3 额外是4
						JJBaseApplication.g_customer.mTaskType = Task
								.getInt("TaskType");

						// }

						// 位置标签
						JSONArray SOPPositionList = new JSONArray(
								res.getString("SOPPositionList"));
						int len = SOPPositionList.length();
						for (int i = 0; i < len; i++) {
							String tmp = SOPPositionList.getString(i);
							JSONObject jo = new JSONObject(tmp);

							TagItem item =new TagItem(
									jo.getString("Name"), jo.getString("ID"));
							item.setTagNum(jo.getString("TagID"));
							JJBaseApplication.g_customer.mPositions.add(item);
						}

					} catch (Exception ex) {
					}
					
					//modify by shawn 2012-11-12 Begin
					//由于要保存多次的客户信息，因此把客户信息保存为列表，每次取的时候获取指定工单号的客户信息
					YTFileHelper ytfileHelper=YTFileHelper.getInstance();
					ytfileHelper.setContext(getApplicationContext());			
					MyCustomerListData myCustomerListData=null;
					if((MyCustomerListData) ytfileHelper.deSerialObject(getResources().getString(R.string.customer_list))!=null){
						myCustomerListData=(MyCustomerListData) ytfileHelper.deSerialObject(getResources().getString(R.string.customer_list));
					}else{
						myCustomerListData=MyCustomerListData.getInstance();
					}


					if(!myCustomerListData.isContainWeekSerialCustomer(JJBaseApplication.g_customer)){
						myCustomerListData.addCustomer(JJBaseApplication.g_customer);
					}else{
						JJBaseApplication.g_customer=myCustomerListData.getWeekSerialCustomer(JJBaseApplication.g_customer);
					}

					ytfileHelper.serialObject(getResources().getString(R.string.customer_list), myCustomerListData);	
					//End

					if(JJBaseApplication.g_customer.mSerial!=null&&JJBaseApplication.g_customer.mSerial.length()>0){
						Intent intent = new Intent();
						intent.setClass(D2EReportActivity.this, D2ESOPActivity.class);
						D2EReportActivity.this.startActivity(intent);
						overridePendingTransition(R.anim.fade, R.anim.hold);
						finish();
					}else{
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
					// Hashtable<String, String> typekey = new Hashtable<String,
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
					Intent intent = new Intent(D2EReportActivity.this,
							D2EHelpActivity.class);
					D2EReportActivity.this.startActivity(intent);
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
		int values = ((Integer)param[0]).intValue();
		if(values == JJBaseService.HTTP_SERVICE_INT){
			((JJBaseApplication)getApplication()).showMessage(getResources().getString(R.string.neterror));
		}
		else if(values == JJBaseService.BT_SERVICE_INT){
			int state = ((Integer)param[1]).intValue();			
			if(state != SOPBluetoothService.BT_DISCONNECT && state !=  SOPBluetoothService.BT_CONNECT){
				((JJBaseApplication)getApplication()).showMessage(getResources().getString(R.string.bterror));
			}
			else if(state == SOPBluetoothService.BT_DISCONNECT){
				JJBaseApplication.g_btState = false;
			}
			else if(state ==  SOPBluetoothService.BT_CONNECT){
				JJBaseApplication.g_btState = true;
			}			
			btStateChange();
		}
	}

	@Override
	public void taskProcessing(Object... param) {

	}

	@Override
	public void onDateUpdate(Date date) {

		if (D2EConfigures.G_USEVIRCALDEVICE) {
			((JJBaseApplication) getApplication()).showMessage(getResources().getString(R.string.current_no_work_sheet));
			return;
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		mDateTitle.setText(sdf.format(date));

		if (mID == null || mID.length() == 0) {
			((JJBaseApplication) getApplication()).showMessage(getResources().getString(R.string.please_chose_a_customer));
			return;
		}

		SimpleDateFormat dates = new SimpleDateFormat("yyyyMMdd");
		String[] params = new String[] { "FunType", "getSalesClientReport",
				"SalesClientID", mID, "PositionID", "", "Date",
				dates.format(date) };

		YTStringHelper.array2string(params);
		long id = JJBaseService.addMutilpartHttpTask(
				"http://www.ohpest.com/ohpest-pco/webservices/get/data.html",
				params, D2EReportActivity.this.getClass().getName(),
				D2EConfigures.TASK_GETREPORTS);

		setCurTaskID(id);
		showWaitDialog(getResources().getString(R.string.handling));

	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {

		case R.id.menu_sop: {

			mIsWarningOnce = false;

			if (D2EConfigures.G_USEVIRCALDEVICE) {
				enterMainActivity();
				return true;
			}

			// 判断是否支持蓝牙
			BluetoothAdapter bluetoothAdapter = BluetoothAdapter
					.getDefaultAdapter();
			if (bluetoothAdapter == null) {
				((JJBaseApplication) getApplication()).showMessage(getResources().getString(R.string.unsupport_bluetooth));
			}

			// 判断蓝牙是否开启
			if (!bluetoothAdapter.isEnabled()) {
				Intent enableBtIntent = new Intent(
						BluetoothAdapter.ACTION_REQUEST_ENABLE);
				startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
			} else {
				initServices();
			}

		}
			break;

		case R.id.menu_home: {
			backAciton();
		}
			break;

		case R.id.menu_help: {

			if (JJBaseApplication.g_documents.size() > 0) {
				Intent intent = new Intent(D2EReportActivity.this,
						D2EHelpActivity.class);
				D2EReportActivity.this.startActivity(intent);
				overridePendingTransition(R.anim.fade, R.anim.hold);
				finish();
			} else {
				SimpleDateFormat dates = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");

				String[] params = new String[] { "FunType", "getWebSite",
						"OrgID", JJBaseApplication.user_OrgID, "Type", "",
						"DateTime", dates.format(new Date()) };
				long id = JJBaseService
						.addMutilpartHttpTask(
								"http://www.ohpest.com/ohpest-pco/webservices/get/data.html",
								params, D2EReportActivity.this.getClass()
										.getName(), D2EConfigures.TASK_GETSITE);

				setCurTaskID(id);
				showWaitDialog(getResources().getString(R.string.handling));
			}
		}
			break;

		case R.id.menu_workSheet: {

			String[] params = new String[] { "FunType", "getCountEveryDayTask",
					"UserID", JJBaseApplication.user_ID, "Day", "7" };
			long id = JJBaseService
					.addMutilpartHttpTask(
							"http://www.ohpest.com/ohpest-pco/webservices/get/data.html",
							params,
							D2EReportActivity.this.getClass().getName(),
							D2EConfigures.TASK_GETWORKSHEET);

			setCurTaskID(id);
			showWaitDialog(getResources().getString(R.string.handling));

		}
			break;

		case R.id.menu_message: {
			Intent intent = new Intent(D2EReportActivity.this,
					D2EMessageActivity.class);
			D2EReportActivity.this.startActivity(intent);
			overridePendingTransition(R.anim.fade, R.anim.hold);
			finish();
		}
			break;

		}
		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		super.onPrepareOptionsMenu(menu);
		menu.clear();
		// Inflate the currently selected menu XML resource.
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.reportpage_menu, menu);
		return true;
	}

	private void enterMainActivity() {
		((JJBaseApplication) getApplication()).startJJService();

		if (SOPBluetoothService.getService() == null
				|| !SOPBluetoothService.getService().mIsRun) {
			((JJBaseApplication) getApplication())
					.showMessage(getResources().getString(R.string.bluetooth_prompt));
			return;
		}
		showProcessDialog(getResources().getString(R.string.reading_card));
		SOPBluetoothService.getService().doTask(SOPBTCallHelper.READ_TAG,
				D2EReportActivity.class.getName(),
				SOPBTCallHelper.getInitOrder(SOPBluetoothService.g_readerID));

	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {

		case REQUEST_ENABLE_BT: {
			if (resultCode == Activity.RESULT_OK) {
				initServices();
			} else {
				((JJBaseApplication) getApplication()).showMessage(getResources().getString(R.string.bluetooth_start_failed));
				// finish();
			}
		}
			break;

		case REQUESR_DEVICE: {
			if (resultCode == Activity.RESULT_CANCELED) {
				finish();
				android.os.Process.killProcess(android.os.Process.myPid());
			}

			SOPBluetoothService.g_btMacAddress = data.getExtras().getString(
					EXTRA_DEVICE_ADDRESS);
			SOPBluetoothService.g_readerID = data.getExtras().getString(
					EXTRA_READER_ID);

			SharedPreferences settings = getSharedPreferences(
					READER_PREFS_NAME, 0);
			Editor editor = settings.edit();
			editor.putString(BT_MAC_KEY, SOPBluetoothService.g_btMacAddress);
			editor.putString(READER_KEY, SOPBluetoothService.g_readerID);
			editor.commit();

			enterMainActivity();

		}
			break;

		}
	}

	private void initServices() {
		// 判断是否本地保存蓝牙设置
		SharedPreferences settings = getSharedPreferences(READER_PREFS_NAME, 0);
		SOPBluetoothService.g_btMacAddress = settings.getString(BT_MAC_KEY,
				null);
		SOPBluetoothService.g_readerID = settings.getString(READER_KEY, null);
		if (SOPBluetoothService.g_btMacAddress == null
				|| SOPBluetoothService.g_readerID == null) {
			Intent serverIntent = new Intent(this, D2EDeviceListActivity.class);
			startActivityForResult(serverIntent, REQUESR_DEVICE);
		} else {
			enterMainActivity();
		}
	}

}
