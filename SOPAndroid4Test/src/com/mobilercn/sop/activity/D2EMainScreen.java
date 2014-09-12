package com.mobilercn.sop.activity;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mobilercn.base.BadgeView;
import com.mobilercn.base.JJBaseActivity;
import com.mobilercn.base.JJBaseApplication;
import com.mobilercn.base.JJBaseService;
import com.mobilercn.config.D2EConfigures;
import com.mobilercn.sop.R;
import com.mobilercn.sop.bt.SOPBTCallHelper;
import com.mobilercn.sop.bt.SOPBluetoothService;
import com.mobilercn.sop.data.MessageItem;
import com.mobilercn.sop.data.MyCustomerListData;
import com.mobilercn.sop.data.MyMessageData;
import com.mobilercn.sop.data.TagItem;
import com.mobilercn.sop.item.D2ECustomer;
import com.mobilercn.sop.item.D2EDocumentItem;
import com.mobilercn.sop.item.D2EDocuments;
import com.mobilercn.sop.item.D2EListAdapterItam;
import com.mobilercn.sop.item.D2EPerson;
import com.mobilercn.task.JJTask;
import com.mobilercn.util.JJNetHelper;
import com.mobilercn.util.YTFileHelper;
import com.mobilercn.util.YTStringHelper;

public class D2EMainScreen extends JJBaseActivity {

	public static final int TYPE_MESSAGE = 1;
	public static final int TYPE_SOP = 2;
	public static final int TYPE_WORKSHEET = 3;
	public static final int TYPE_REPORT = 4;
	public static final int TYPE_SUPPORT = 5;
	public static final int TYPE_INIT = 6;
	public static final int TYPE_EXIT = 7;

	private static final int REQUEST_ENABLE_BT = 1;
	private static final int REQUESR_DEVICE = 2;

	private BadgeView mBadgeView;
	private GridView mGridView;
	private MenuAdapter mMenuAdapter;

	public static boolean isReqestClientNet;
	private String strError;
	private boolean mIsWarningOnce;
	private YTFileHelper ytfileHelper;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.d2e_main_1);
		Window window = getWindow();
		window.setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.d2e_titlebar);

		findViewById(R.id.countTask).setVisibility(View.GONE);
		TextView tv = (TextView) findViewById(R.id.title);
		tv.setText(getResources().getString(R.string.pco_system));
		tv.setGravity(Gravity.CENTER);
		ytfileHelper = YTFileHelper.getInstance();
													
		ytfileHelper.setContext(getApplicationContext());

		isReqestClientNet = false;
		// 消息
		ImageButton ib_main_msg = (ImageButton) findViewById(R.id.ib_main_msg);
		ib_main_msg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(D2EMainScreen.this, D2EMessageActivity.class);
				D2EMainScreen.this.startActivity(intent);
				overridePendingTransition(R.anim.fade, R.anim.hold);
			}
		});
		// SOP
		ImageButton ib_main_sop = (ImageButton) findViewById(R.id.ib_main_sop);
		ib_main_sop.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mIsWarningOnce = false;

				if (D2EConfigures.G_USEVIRCALDEVICE) {
					enterMainActivity();
					return;
				}
				// 判断是否支持蓝牙
				BluetoothAdapter bluetoothAdapter = BluetoothAdapter
						.getDefaultAdapter();
				if (bluetoothAdapter == null) {
					((JJBaseApplication) getApplication())
							.showMessage(getResources().getString(R.string.unsupport_bluetooth));
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
		});
		//工单
		ImageButton ib_main_work_sheet=(ImageButton) findViewById(R.id.ib_main_work_sheet);
		ib_main_work_sheet.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (D2EConfigures.G_USEVIRCALDEVICE) {
					Intent intent = new Intent(D2EMainScreen.this,
							D2EWorksheetActivity.class);
					D2EMainScreen.this.startActivity(intent);
					overridePendingTransition(R.anim.fade, R.anim.hold);
					finish();

				} else {
					String[] params = new String[] { "FunType",
							"getCountEveryDayTask", "UserID",
							JJBaseApplication.user_ID, "Day", "7" };
					YTStringHelper.array2string(params);
					long id = JJBaseService
							.addMutilpartHttpTask(
									"http://www.ohpest.com/ohpest-pco/webservices/get/data.html",
									params, D2EMainScreen.this.getClass()
											.getName(),
									D2EConfigures.TASK_GETWORKSHEET);

					setCurTaskID(id);
					showWaitDialog(getResources().getString(R.string.handling));
				}
			}
		});	
		
		//报表
		ImageButton ib_main_report=(ImageButton)findViewById(R.id.ib_main_report);
		ib_main_report.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (D2EConfigures.G_USEVIRCALDEVICE) {
					Intent intent = new Intent(D2EMainScreen.this,
							D2EReportActivity.class);
					D2EMainScreen.this.startActivity(intent);
					overridePendingTransition(R.anim.fade, R.anim.hold);
					finish();
				} else {
					String[] params = new String[] { "FunType",
							"getSalesClientList", "OrgID",
							JJBaseApplication.user_OrgID, "KeyWord", "" };

					if (D2EConfigures.TEST) {
						YTStringHelper.array2string(params);
					}
					long id = JJBaseService
							.addMutilpartHttpTask(
									"http://www.ohpest.com/ohpest-pco/webservices/get/data.html",
									params, D2EMainScreen.this.getClass()
											.getName(),
									D2EConfigures.TASK_GETCUSTOMERSBYUSER);

					setCurTaskID(id);
					showWaitDialog(getResources().getString(R.string.handling));
				}
			}
		});
		//支持
		ImageButton ib_main_support=(ImageButton)findViewById(R.id.ib_main_support);
		ib_main_support.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (D2EConfigures.G_USEVIRCALDEVICE) {
					Intent intent = new Intent(D2EMainScreen.this,
							D2EHelpActivity.class);
					D2EMainScreen.this.startActivity(intent);
					overridePendingTransition(R.anim.fade, R.anim.hold);
					finish();
				}
				// add by shawn 2012-11-1
				// 如果调试，没有网络则直接跳转页面
				if (D2EConfigures.NO_NET) {
					Intent intent = new Intent(D2EMainScreen.this,
							D2EHelpActivity.class);
					D2EMainScreen.this.startActivity(intent);
					overridePendingTransition(R.anim.fade, R.anim.hold);
					finish();
				}
				// End
				else {
					if (JJBaseApplication.g_documents.size() > 0) {
						Intent intent = new Intent(D2EMainScreen.this,
								D2EHelpActivity.class);
						D2EMainScreen.this.startActivity(intent);
						overridePendingTransition(R.anim.fade, R.anim.hold);
						finish();
					} else {
						SimpleDateFormat dates = new SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss");

						String[] params = new String[] { "FunType",
								"getWebSite", "OrgID",
								JJBaseApplication.user_OrgID, "Type", "",
								"DateTime", dates.format(new Date()) };
						if (D2EConfigures.TEST) {
							YTStringHelper.array2string(params);
						}
						long id = JJBaseService
								.addMutilpartHttpTask(
										"http://www.ohpest.com/ohpest-pco/webservices/get/data.html",
										params, D2EMainScreen.this.getClass()
												.getName(),
										D2EConfigures.TASK_GETSITE);

						setCurTaskID(id);
						showWaitDialog(getResources().getString(R.string.handling));
					}
				}
			}
		});
		//勘查
		ImageButton ib_main_watch=(ImageButton) findViewById(R.id.ib_main_watch);
		ib_main_watch.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//先判断客户列表是否有数据，有数据就不再请求网络获取了
				if(!(JJBaseApplication.mWatchClientList.size()>0)){
				//modify by shawn 2012-12-03 Begin
				//这里修改为先获取客户列表,成功了在跳转页面
				 String[] params = new String[]{
			        		"FunType", "getCustomerData", 
			        		"OrgID", JJBaseApplication.user_OrgID
			        		};
				 if(D2EConfigures.TEST){
			        	YTStringHelper.array2string(params);
			        }
		        long ids = JJBaseService.addMutilpartHttpTask("http://www.ohpest.com/ohpest-pco/webservices/get/data.html", params, D2EMainScreen.this.getClass().getName(),D2EConfigures.TASK_GET_ALL_CUSTOM);
		        
		        showWaitDialog(getResources().getString(R.string.get_custom_data));
		        setCurTaskID(ids);
				}else{
					Intent intent = new Intent(D2EMainScreen.this,
							D2ENowPlaceDateCollectActivity.class);
					D2EMainScreen.this.startActivity(intent);
					overridePendingTransition(R.anim.fade, R.anim.hold);
					D2EMainScreen.this.finish();
				}
				//End
			}
		});
		

	}

	@Override
	protected void onResume() {
		super.onResume();

	}

	@Override
	public void backAciton() {

	}

	@Override
	public boolean isExit() {
		return true;
	}

	@Override
	public void init() {

	}

	@Override
	public void taskSuccess(Object... param) {
		int values = ((Integer) param[0]).intValue();
		if (values == JJBaseService.BT_SERVICE_INT) {
			String response = (String) param[2];
			if (D2EConfigures.TEST) {
				Log.i("response=------>", response);
			}
			if (response.toLowerCase().equals(
					SOPBluetoothService.STATE_FAILED.toLowerCase())) {
				((JJBaseApplication) getApplication())
						.showMessage(getResources().getString(R.string.failed_read));
				dismissProcessDialog();
				return;
			} else {

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
								if (D2EConfigures.TEST) {
									Log.i("tmp----->", tmp);
								}
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
						intent.setClass(D2EMainScreen.this,D2ESOPActivity.class);
						D2EMainScreen.this.startActivity(intent);
						overridePendingTransition(R.anim.fade, R.anim.hold);
						finish();
						return;
					}
				}

				String locationID = response.substring(14, 22);

				if (D2EConfigures.G_DEBUG) {
					locationID = "93edb888";
				}

				String[] params = new String[] { "FunType",
						"getSalesClientDataWithTask", "OrgID",
						JJBaseApplication.user_OrgID, "ReadNum",
						SOPBluetoothService.g_readerID, "TagNum", locationID,
						"UserID", JJBaseApplication.user_ID };

				if (D2EConfigures.TEST) {
					YTStringHelper.array2string(params);
				}

				long id = JJBaseService
						.addMutilpartHttpTask(
								"http://www.ohpest.com/ohpest-pco/webservices/get/data.html",
								params,
								D2EMainScreen.this.getClass().getName(),
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
						.showMessage(getResources().getString(R.string.net_not_well));
				dismissProcessDialog();
				return;
			}
			if (task.getTaskId() == D2EConfigures.TASK_CHECKCODE) {
				// 数据处理
				try {
					Intent intent = new Intent();
					intent.setClass(D2EMainScreen.this,
							D2ECustomerActivity.class);
					JJBaseApplication.sop_location_boolean = true;
					intent.putExtra(D2ECustomerActivity.PAGE_TYPE,
							D2ECustomerActivity.TYPE_SOP);
					JJBaseApplication.g_list.clear();
					if (response != null && response.length() > 0) {
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
					D2EMainScreen.this.startActivity(intent);
					overridePendingTransition(R.anim.fade, R.anim.hold);
					finish();
				} catch (Exception ex) {
				}
			}
			//勘探界面获取客户列表
			else if(task.getTaskId() == D2EConfigures.TASK_GET_ALL_CUSTOM){
		    	JJBaseApplication.mWatchClientList.clear();
		    	try{
		    		JSONArray ja=new JSONArray(response);
		    		if(D2EConfigures.TEST){
		    			Log.e("ja===========>", ""+ja);
		    		}
		    		int size=ja.length();
		    		for(int i=0;i<size;i++){
		    			String tmp=ja.getString(i);
		    			JSONObject jo=new JSONObject(tmp);
		    			D2EListAdapterItam item=new D2EListAdapterItam(jo.getString("Name"), jo.getString("ID"));
		    			item.setmCustomerContact(jo.getString("Manager"));
		    			JJBaseApplication.mWatchClientList.add(item);
		    		}
		    		Intent intent = new Intent(D2EMainScreen.this,
							D2ENowPlaceDateCollectActivity.class);
					D2EMainScreen.this.startActivity(intent);
					overridePendingTransition(R.anim.fade, R.anim.hold);
					D2EMainScreen.this.finish();
		    	}catch(Exception e){
		    		
		    	}
		    }
			
			
			else if (task.getTaskId() == D2EConfigures.TASK_REFERSHMSG) {
				try {
					if (response != null && response.length() > 0) {
						MyMessageData messages = (MyMessageData) ytfileHelper
								.deSerialObject("msg.data");
						JSONArray ja = new JSONArray(response);
						int len = ja.length();
						for (int i = 0; i < len; i++) {
							String tmp = ja.getString(i);
							JSONObject jo = new JSONObject(tmp);
							MessageItem item = new MessageItem(
									jo.getString("Message"),
									jo.getString("Message"));
							item.setDate(jo.getString("CreateTime"));
							item.setID(jo.getString("ID"));
							if (jo.has("Read")) {
								item.setRead(jo.getString("Read"));
							}

							messages.getMessages().add(item);
						}
						// myMessageData.getMessages().clear();
						// for(MessageItem item3:oldMessageItems){
						// myMessageData.getMessages().add(item3);
						// }
						// 序列化先不实现

						ytfileHelper.serialObject("msg.data", messages);

						Intent intent = new Intent();
						intent.setClass(D2EMainScreen.this,
								D2EMessageActivity.class);
						D2EMainScreen.this.startActivity(intent);
						overridePendingTransition(R.anim.fade, R.anim.hold);

					} else {
						((JJBaseApplication) getApplication())
								.showMessage(getString(R.string.get_data_faield));
					}
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
						// =====================================
						// 这里实例化 了一个客户对象
						// 下面进行了客户对象属性的赋值
						// 如果要序列化的话,当对象的属性赋值完毕后，保存到手机硬盘上
						JJBaseApplication.g_customer = new D2ECustomer();
						JJBaseApplication.g_customer.mId = salesClient
								.getString("ID");
						JJBaseApplication.g_customer.mName = salesClient
								.getString("Name");
						JJBaseApplication.g_customer.mType = salesClient
								.getString("SalesClientType");
						Log.i("D2EConfigures.g_customer.mName---->", ""
								+ JJBaseApplication.g_customer.mName);

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
						if (D2EConfigures.G_DEBUG) {
							JJBaseApplication.g_customer.mTaskType = 1;
						} else {
							JJBaseApplication.g_customer.mTaskType = Task
									.getInt("TaskType");
						}
						JSONArray SOPPositionList = null;
						try {
							SOPPositionList = new JSONArray(
									res.getString("SOPPositionList"));
						} catch (Exception e) {
							e.printStackTrace();
							JSONObject error = new JSONObject(
									res.getString("SOPPositionList"));
							strError = error.getString("error");
						}
						int len = SOPPositionList.length();
						for (int i = 0; i < len; i++) {
							String tmp = SOPPositionList.getString(i);
							JSONObject jo = new JSONObject(tmp);
							TagItem item = new TagItem(jo.getString("Name"),
									jo.getString("ID"));
							item.setTagNum(jo.getString("TagID"));
							JJBaseApplication.g_customer.mPositions.add(item);
						}
						// ===========================================
					} catch (Exception ex) {
					}

					//modify by shawn 2012-11-12 Begin
					//由于要保存多次的客户信息，因此把客户信息保存为列表，每次取的时候获取指定工单号的客户信息
				
					MyCustomerListData myCustomerListData=null;
					if((MyCustomerListData) ytfileHelper.deSerialObject(getResources().getString(R.string.customer_list))!=null){
						myCustomerListData=(MyCustomerListData) ytfileHelper.deSerialObject(getResources().getString(R.string.customer_list));
					}else{
						myCustomerListData=MyCustomerListData.getInstance();
					}
					
					if(D2EConfigures.TEST){
						Log.e("JJBaseApplication.g_customer==null", ""+(JJBaseApplication.g_customer==null));
						Log.e("!myCustomerListData.isContainWeekSerialCustomer(JJBaseApplication.g_customer)", ""+(!myCustomerListData.isContainWeekSerialCustomer(JJBaseApplication.g_customer)));
					}
					if(!myCustomerListData.isContainWeekSerialCustomer(JJBaseApplication.g_customer)){
						myCustomerListData.addCustomer(JJBaseApplication.g_customer);
					}else{
						JJBaseApplication.g_customer=myCustomerListData.getWeekSerialCustomer(JJBaseApplication.g_customer);
					}
					
					ytfileHelper.serialObject(getResources().getString(R.string.customer_list), myCustomerListData);	
					//End
					if(D2EConfigures.TEST){
						Log.e("deSeriaObject=!=null======>", ""+((MyCustomerListData) ytfileHelper.deSerialObject(getResources().getString(R.string.customer_list))!=null));
					}
					if(D2EConfigures.TEST){
						Log.e("myCustomerListData==null=======>", ""+(myCustomerListData==null));
						Log.e("JJBaseApplication.g_customer!=null=======>", ""+(JJBaseApplication.g_customer!=null));
						Log.e("!myCustomerListData.isContainWeekSerialCustomer(JJBaseApplication.g_customer)", ""+(!myCustomerListData.isContainWeekSerialCustomer(JJBaseApplication.g_customer)));
					}
					
					//modify by shawn 2012-11-30 Begin
					//mei you gong danxinxi ze bu jinru xiage yemian 
					
					//end
					if(JJBaseApplication.g_customer.mSerial!=null&&JJBaseApplication.g_customer.mSerial.length()>0){
						Intent intent = new Intent();
						intent.setClass(D2EMainScreen.this, D2ESOPActivity.class);
						intent.putExtra("strError", strError);
						D2EMainScreen.this.startActivity(intent);
						overridePendingTransition(R.anim.fade, R.anim.hold);
						finish();
					}else{
						((JJBaseApplication) getApplication())
						.showMessage(getString(R.string.no_weeksheet_data));
					}
				}
			} else if (task.getTaskId() == D2EConfigures.TASK_GETWORKSHEET) {
				try {
					JJBaseApplication.g_worksheet.clear();
					JSONObject res = new JSONObject(response);
					// 没有工单信息也进入页面
					if (res.getString("Assignment") == null
							|| res.getString("Assignment").toLowerCase()
									.equals("null")) {
						((JJBaseApplication) getApplication())
								.showMessage(getResources().getString(R.string.no_weeksheet_data));
						dismissProcessDialog();
						Intent intent = new Intent(D2EMainScreen.this,
								D2EWorksheetActivity.class);
						intent.putExtra("type",
								D2EWorksheetActivity.TYPE_WORKWHEET);
						D2EMainScreen.this.startActivity(intent);
						overridePendingTransition(R.anim.fade, R.anim.hold);
						finish();
						// return;
					}

					JSONObject assignment = new JSONObject(
							res.getString("Assignment"));
					JSONArray jc = new JSONArray(
							assignment.getString("CountTask"));
					JSONArray jd = new JSONArray(
							assignment.getString("BeginTime"));

					int len = jc.length();
					int sum = 0;
					for (int i = 0; i < len; i++) {
						JJBaseApplication.g_worksheet.put(jd.getString(i),
								jc.getString(i));
						sum += Integer.valueOf(jc.getString(i));
						if (D2EConfigures.TEST) {
							Log.e("tasknum---------->", jc.getString(i));
						}
					}
					if (D2EConfigures.TEST) {
						Log.e("D2EConfigures.g_worksheet-------->", ""
								+ JJBaseApplication.g_worksheet.toString());
						Log.e("sum--------------->", "" + sum);
					}

					JJBaseApplication.user_CountTask = String.valueOf(sum);
					if (D2EConfigures.TEST) {
						Log.e("D2EConfigures.user_CountTask-------->",
								JJBaseApplication.user_CountTask);
					}
					// 用于天气
					JJBaseApplication.g_weather.clear();
					JSONArray weather = new JSONArray(res.getString("Weather"));
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
					Calendar c = new GregorianCalendar();
					c.setTime(new Date());
					for (int j = 0; j < D2EConfigures.WEATHER_COUNT; j++) {
						JSONObject tmp = new JSONObject(weather.getString(j));
						JJBaseApplication.g_weather.put(
								sdf.format(c.getTime()), tmp.getString("temp"));
						c.add(Calendar.DAY_OF_MONTH, 1);
					}

					Intent intent = new Intent(D2EMainScreen.this,
							D2EWorksheetActivity.class);
					intent.putExtra("type", D2EWorksheetActivity.TYPE_WORKWHEET);
					D2EMainScreen.this.startActivity(intent);
					overridePendingTransition(R.anim.fade, R.anim.hold);
					finish();
				} catch (Exception ex) {
				}
			} else if (task.getTaskId() == D2EConfigures.TASK_GETCUSTOMERSBYUSER) {
				isReqestClientNet = true;
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
				Intent intent = new Intent(D2EMainScreen.this,
						D2EReportActivity.class);
				D2EMainScreen.this.startActivity(intent);
				overridePendingTransition(R.anim.fade, R.anim.hold);
				finish();
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
								try {
									item.mTitle = s.getString("Title");
								} catch (Exception ex) {
								}
								item.mType = value;

								d.addItem(item);
							}

							JJBaseApplication.g_documents.add(d);
						} else {
							continue;
						}
					}
					Intent intent = new Intent(D2EMainScreen.this,
							D2EHelpActivity.class);
					D2EMainScreen.this.startActivity(intent);
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
				D2EMessageActivity.class.getName(),
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
			}
		}
			break;

		case REQUESR_DEVICE: {
			if (resultCode == Activity.RESULT_CANCELED) {
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
			startActivityForResult(serverIntent, REQUESR_DEVICE);
		} else {
			enterMainActivity();
		}
	}

	class MenuItem {

		public String mTitle;
		public int mSourceId;
		public int mType;

		public MenuItem(String title, int id, int type) {
			mTitle = title;
			mSourceId = id;
			mType = type;
		}

	}

	class MenuAdapter extends BaseAdapter {

		Context mContext;
		ArrayList<MenuItem> mItems;

		public MenuAdapter(Context context) {
			mContext = context;
			mItems = new ArrayList<MenuItem>();
		}

		public void addObject(MenuItem item) {
			mItems.add(item);
		}

		@Override
		public int getCount() {
			return mItems.size();
		}

		@Override
		public Object getItem(int index) {
			if (index < 0 || index >= mItems.size()) {
				return null;
			}
			return mItems.get(index);
		}

		@Override
		public long getItemId(int arg0) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (null == convertView) {
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.d2e_main_item, parent, false);
				holder = new ViewHolder();
				holder.mImage = (ImageView) convertView
						.findViewById(R.id.function_icon);
				holder.mText = (TextView) convertView
						.findViewById(R.id.function_title);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			MenuItem item = mItems.get(position);
			holder.mImage.setBackgroundResource(item.mSourceId);
			holder.mText.setText(item.mTitle);

			return convertView;
		}

	}

	class ViewHolder {
		ImageView mImage;
		TextView mText;
	}

}
