package com.mobilercn.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import org.json.JSONArray;

import com.mobilercn.config.D2EConfigures;
import com.mobilercn.sop.R;
import com.mobilercn.sop.activity.D2EWorksheetList;
import com.mobilercn.sop.bt.SOPBluetoothService;
import com.mobilercn.sop.data.MessageItem;
import com.mobilercn.sop.data.MyWatchDataItem;
import com.mobilercn.sop.data.PcoUser;
import com.mobilercn.sop.data.TagItem;
import com.mobilercn.sop.item.D2ECustomer;
import com.mobilercn.sop.item.D2EDocuments;
import com.mobilercn.sop.item.D2EListAdapterItam;

import com.mobilercn.task.JJAsyncTaskManager;
import com.mobilercn.util.JJNetHelper;
import com.mobilercn.util.YTFileHelper;
import com.mobilercn.util.YTUncaughtExceptionHandler;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.widget.Toast;

public class JJBaseApplication extends Application {
	private static final String TAG = "JJBaseApplication";
	public static final int WIFI_ENABLED = 1;
	public static final int GPRS_ENABLED = 2;
	public static final int NO_ENABLED = 3;

	public static boolean g_540x960 = false;
	public static boolean g_debug = false;

	private Intent g_Service;

	private Intent g_httpService;

	//
	public static boolean g_btState = false;

	public static int g_grid_type = D2EConfigures.GRID_CUSTOM;

	// ==========User Info========
	public static String user_OrgID = "";
	public static String user_SalesClientID = "";
	/**
	 * 员工ID
	 */
	public static String user_ID = "";
	/**
	 * 当前员工的员工卡号
	 */
	public static String user_card_num = "";
	/**
	 * 当前员工的名字
	 */
	public static String current_user_name = "";

	public static String user_CountTask = "";

	// ------------------全局变量-------------------

	public static D2ECustomer g_customer = null;// 当前服务客户

	// for test
	public static String sop_name = "";
	public static String sop_id = "";

	public static TagItem sop_location = null;
	public static TagItem sop_checkp = null;

	// 保存的一个临时变量客户
	public static D2ECustomer customerTempt = null;

	/**
	 * 选中的位置标签
	 */
	public static TagItem selectPosition = null;
	/**
	 * 选中的作业点标签
	 */
	public static TagItem selectPoint = null;

	/**
	 * 选中的客户
	 */
	public static TagItem clickCustomer = null;

	public static String g_selecteDate = "20120611";
	public static String g_selecteMonth = "06-11";

	public static String g_selecteCustomer = "";

	public static boolean sop_location_boolean = false;

	public static boolean isCheckedIn = false;

	/**
	 * 是否已经添加了老鼠勘查
	 */
	public static boolean isAddMousWatch = false;
	/**
	 * 是否已经添加了蟑螂勘查
	 */
	public static boolean isAddCockroachWatch = false;
	/**
	 * 是否已经添加了飞虫勘查
	 */
	public static boolean isAddFlyWatch = false;

	/**
	 * 勘查模块列表数据
	 */
	public static List<MyWatchDataItem> mWatchDataList = new ArrayList<MyWatchDataItem>();
	/**
	 * 其他虫害勘查数据
	 */
	public static List<MyWatchDataItem> mOtherWatchDataList = new ArrayList<MyWatchDataItem>();
	/**
	 * 重点区域勘查结果
	 */
	public static List<MyWatchDataItem> mImportWatchResulDatatList = new ArrayList<MyWatchDataItem>();
	// 选择的客户名称
	public static String strSelectCustomerName = "";
	// 选择的位置标签的id
	public static String strSelectLocationID = "";

	// ==========用于界面传递数据
	public static ArrayList g_list = new ArrayList(5);

	// 客户列表
	public static List<TagItem> mCustomerList = new ArrayList<TagItem>();
	public static List<TagItem> mPointList = new ArrayList<TagItem>();

	// 保存初始化用户的位置标签
	public static ArrayList<TagItem> g_sop_locations = new ArrayList<TagItem>(5);

	// 用于处理sop正常模块,保存所有的位置标签
	public static ArrayList<TagItem> g_soplist = new ArrayList<TagItem>(5);
	// 用于消息
	public static ArrayList<MessageItem> g_msglist = new ArrayList<MessageItem>(
			5);
	// 用于工单
	public static Hashtable<String, String> g_worksheet = new Hashtable<String, String>(
			5);
	// 用于天气
	public static Hashtable<String, String> g_weather = new Hashtable<String, String>(
			5);
	// 工单某天下的用户
	public static ArrayList<TagItem> g_customerlist = new ArrayList<TagItem>(5);
	// 工单列表
	public static ArrayList<D2EWorksheetList.WorkSheetItem> g_worksheetlist = new ArrayList<D2EWorksheetList.WorkSheetItem>(
			5);

	// 客户列表
	public static List<D2EListAdapterItam> mClientList = new ArrayList<D2EListAdapterItam>();

	// 勘查模块客户信息
	public static List<D2EListAdapterItam> mWatchClientList = new ArrayList<D2EListAdapterItam>();

	// 保存支持里的文档
	public static ArrayList<D2EDocuments> g_documents = new ArrayList<D2EDocuments>(
			5);
	public static D2EDocuments g_selectedDocument = null;
	// 用于报表查询
	public static ArrayList<TagItem> g_sopReprot = new ArrayList<TagItem>(5);
	public static ArrayList<TagItem> g_monitorReprot = new ArrayList<TagItem>(5);
	// 定义一个HashMap用来保存，工单号即任务id
	public static HashMap<String, ArrayList<TagItem>> taskIdMap = new HashMap<String, ArrayList<TagItem>>();
	// 定义一个ArrayList来保存，工单下的表
	public static ArrayList<HashMap<String, TagItem>> taskTableList = new ArrayList<HashMap<String, TagItem>>();

	// 定义一个HashMap来存放作业点工单下的数据
	public static HashMap<String, JSONArray> mTasKJSONArray = new HashMap<String, JSONArray>();
	public static HashMap<String, JSONArray> mMonitorTaskJSONArray = new HashMap<String, JSONArray>();

	// 定义一个保存签到员工的卡号信息
	public static List<String> emploeeSignTageNums = new ArrayList<String>();

	// add by shawn 2012-9-7 Begin
	public static List<PcoUser> pcoUserList = new ArrayList<PcoUser>();
	public static HashMap<String, PcoUser> pcoUsersMap = new HashMap<String, PcoUser>();

	// End

	// 判断员工卡列表中是否包含某个员工卡
	public static boolean isContainEmploeeTageNum(String tagNum) {
		if (tagNum != null && tagNum.length() > 0) {
			for (int i = 0; i < emploeeSignTageNums.size(); i++) {
				if (emploeeSignTageNums.get(i).toString()
						.equalsIgnoreCase(tagNum)) {
					return true;
				}
			}
		}
		return false;
	}

	// 获取当前登录的员工
	public static PcoUser getCurrentUserCardNum(String id) {
		int size = JJBaseApplication.pcoUserList.size();
		for (int i = 0; i < size; i++) {
			if (JJBaseApplication.pcoUserList.get(i).getStrId()
					.equalsIgnoreCase(id)) {
				return JJBaseApplication.pcoUserList.get(i);
			}
		}
		return null;
	}

	// 判断保存工单数量的HashMap中是否包含所选日期的工单
	public static boolean isD2EConfiguresG_worksheetcontainsDate(
			String selectDate) {
		if (selectDate != null && selectDate.length() > 0) {
			for (int i = 0; i < g_worksheet.size(); i++) {
				if (g_worksheet.get(selectDate) != null) {
					Log.e("D2EConfigures.g_worksheet.get(selectDate)-------->",
							"" + g_worksheet.get(selectDate));
					return true;
				}
			}
		}
		return false;
	}

	public static boolean isContainWokr(D2EWorksheetList.WorkSheetItem item) {
		for (D2EWorksheetList.WorkSheetItem itemExist : g_worksheetlist) {
			if (D2EConfigures.TEST) {
				Log.e("itemExist.getDate()--------------->",
						"" + (itemExist.getDate()));
				Log.e("item.getDate()--------------->", "" + (item.getDate()));
			}
			if (itemExist.getDate().trim()
					.equalsIgnoreCase(item.getDate().trim())) {
				return true;
			}
		}

		return false;
	}

	public JJBaseApplication() {

	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public void onCreate() {
		super.onCreate();
		if (D2EConfigures.TEST) {
			Log.i(TAG, "--------------------onCreate()-----------------");
		}

		if (JJNetHelper.checkNetAvailable(getApplicationContext()) == WIFI_ENABLED) {
			showMessage(getResources().getString(R.string.wify_net));
		} else if (JJNetHelper.checkNetAvailable(getApplicationContext()) == GPRS_ENABLED) {
			showMessage(getResources().getString(R.string.gprs_net));
		} else {
			showMessage(getResources().getString(R.string.no_net));
		}

		if (!JJBaseService.g_runState) {
			g_httpService = new Intent(this, JJBaseService.class);
			startService(g_httpService);
		}

		YTUncaughtExceptionHandler handler = YTUncaughtExceptionHandler
				.getInstance();
		handler.setup(getApplicationContext());

	}

	public void dealloc() {

		if (g_Service != null) {
			for (JJBaseActivity act : SOPBluetoothService.g_allActivity) {
				if (act != null) {
					act.finish();
					act = null;
				}
			}
			stopService(g_Service);
		}

		if (g_httpService != null) {
			for (JJBaseActivity act : JJBaseService.g_allActivity) {
				if (act != null) {
					act.finish();
					act = null;
				}
			}
			stopService(g_httpService);
		}
		System.gc();
		android.os.Process.killProcess(android.os.Process.myPid());

	}

	public void startJJService() {
		if (!SOPBluetoothService.g_runState) {
			g_Service = new Intent(this, SOPBluetoothService.class);
			startService(g_Service);
		}
	}

	public void stopJJService() {
		if (SOPBluetoothService.g_runState) {
			SOPBluetoothService.g_runState = false;
			stopService(g_Service);
		}

	}

	@Override
	public void onLowMemory() {
		YTFileHelper file = YTFileHelper.getInstance();
		file.setContext(getApplicationContext());
		file.saveFile("log", "onLowMemory".getBytes());

		super.onLowMemory();
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
	}

	public void showMessage(String message) {
		if (message == null || message.length() == 0 || message.equals("")) {
			return;
		}
		Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT)
				.show();
	}

	public void showMessageDialog(String message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(getResources().getString(R.string.jj_warm_prompt));
		builder.setMessage(message);
		builder.setNegativeButton(getResources().getString(R.string.jj_sure),
				null);
		builder.create().show();
	}
}
