package com.mobilercn.sop.activity;

import java.io.InputStream;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
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
import com.mobilercn.sop.item.D2EListAdapterItam;
import com.mobilercn.sop.item.D2EPerson;
import com.mobilercn.task.JJTask;
import com.mobilercn.util.JJNetHelper;
import com.mobilercn.util.YTFileHelper;
import com.mobilercn.util.YTStringHelper;

public class D2EWorksheetActivity extends JJBaseActivity implements WTDateUpdateListener{
	private boolean hasgetWorkSheet=false;
	private static int SUM=0;
	public static final int     TYPE_WORKWHEET = 1;
	
	private static final int    REQUEST_ENABLE_BT    = 1;
	private static final int    REQUESR_DEVICE       = 2;

	private static final String READER_PREFS_NAME    = "READERID";
	private static final String BT_MAC_KEY           = "BTMAC";
	private static final String READER_KEY           = "READER";
	
    public static        String EXTRA_DEVICE_ADDRESS = "device_address";
    public static        String EXTRA_READER_ID      = "reader_id";

    private boolean             mIsWarningOnce       = false;

    private int                 mViewType;
	
	private TextView mDateTitle;
	private WTMonthView mMonth;
	private BadgeView mBadgeView;
	
	private Handler mHandler=null;
	/**
	 * 刷新工单线程
	 */
	private Runnable reflashWorksheet=new Runnable() {
		
		@Override
		public void run() {
			getWorkSheet();
			Message msg=new Message();
			msg.what=0;
			msg.arg1=SUM;
			mHandler.sendMessage(msg);
		}
	};
	
	
	private void getWorkSheet(){
		String[] params = new String[]{
        		"FunType", "getCountEveryDayTask", 
        		"UserID", JJBaseApplication.user_ID, 
        		"Day","7"};
        YTStringHelper.array2string(params);
        long id = JJBaseService.addMutilpartHttpTask("http://www.ohpest.com/ohpest-pco/webservices/get/data.html", params, D2EWorksheetActivity.this.getClass().getName(),D2EConfigures.TASK_GETWORKSHEET);
        	        
        setCurTaskID(id);
        showWaitDialog(getResources().getString(R.string.handling));	
        mHandler.postDelayed(reflashWorksheet, 10000);
	}
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.d2e_worksheet_page);
        Window window = getWindow();
        window.setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.d2e_titlebar);
        
        TextView tv = (TextView)findViewById(R.id.title);
        tv.setText(getResources().getString(R.string.menu_worksheet));
        
        mBadgeView = (BadgeView)findViewById(R.id.countTask);
        mBadgeView.setBadgePosition(BadgeView.POSITION_CENTER);
        mBadgeView.setBackgroundResource(R.drawable.badge_ifaux);
        mBadgeView.setVisibility(View.VISIBLE);
        
        mHandler=new Handler(){
    		@Override
    		public void handleMessage(Message msg) {
    			super.handleMessage(msg);
    			switch(msg.what){
    			case 0:
    				if(D2EConfigures.TEST){
    					Log.e("SUM%%%%%%%%%%%%%%%%%%%%%%%%%>", ""+SUM);
    				}
    				mBadgeView.setText(String.valueOf(SUM));
    		    	mBadgeView.invalidate();
    				break;
    			default:
    				break;
    			}
    		}
    	};
        //add by shawn 2012-10-23 Begin
        //每隔10秒刷新工单信息
        mHandler.postDelayed(reflashWorksheet, 10000);
        //End
        
        
        if(JJBaseApplication.user_CountTask != null && JJBaseApplication.user_CountTask.length() > 0){
        	if(D2EConfigures.TEST){
        		Log.e("D2EConfigures.user_CountTask---------->", JJBaseApplication.user_CountTask);
        	}
        	mBadgeView.setText(JJBaseApplication.user_CountTask);
        }

        Intent intent = getIntent();
        mViewType = intent.getIntExtra("type", 1);
        
        //for calendar
        mMonth = (WTMonthView)this.findViewById(R.id.monthview);
        mMonth.setDateListener(this);
        mMonth.setBedge(true);
        
        mDateTitle = (TextView)findViewById(R.id.calendar_title_text);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		mDateTitle.setText(sdf.format(new Date()));

        Button pre = (Button)findViewById(R.id.calendar_arrow_pre);
        pre.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Date date = mMonth.changeMonth(false);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				mDateTitle.setText(sdf.format(date));
			}
        	
        });
        
        Button next = (Button)findViewById(R.id.calendar_arrow_next);
        next.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Date date = mMonth.changeMonth(true);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				mDateTitle.setText(sdf.format(date));
			}
        	
        });

    }
	
    
    @Override
    protected void onDestroy() {
    	super.onDestroy();
    	mHandler.removeCallbacks(reflashWorksheet);
    }
    
    @Override
    protected void onPause() {
    	super.onPause();
    	mHandler.removeCallbacks(reflashWorksheet);
    }
    
    
    @Override
    protected void onResume() {
    	super.onResume();
    	if(D2EConfigures.TEST){
    		Log.e("D2EWorksheetActivity=========>", "onResume()");
    	}
    	mBadgeView.setText(JJBaseApplication.user_CountTask);
    	mBadgeView.invalidate();
    }
    
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch(item.getItemId()){
		
		case R.id.menu_message:{
			Intent intent = new Intent(D2EWorksheetActivity.this, D2EMessageActivity.class);
			D2EWorksheetActivity.this.startActivity(intent);
			overridePendingTransition(R.anim.fade, R.anim.hold);
			finish();
		}break;
		
		case R.id.menu_sop:{
			
			mIsWarningOnce = false;
			
			if(D2EConfigures.G_USEVIRCALDEVICE){
				enterMainActivity();
				return true;
			}
			
			//判断是否支持蓝牙
			BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
	        if(bluetoothAdapter == null){
	        	((JJBaseApplication)getApplication()).showMessage(getResources().getString(R.string.unsupport_bluetooth));
	        }
	        
	        //判断蓝牙是否开启
	        if(!bluetoothAdapter.isEnabled()){
	    	    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
	    	    startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
	        }
	        else {
	        	initServices();
	        }				
			
		}break;
		
		case R.id.menu_report:{
			
	        String[] params = new String[]{
	        		"FunType", "getSalesClientList", 
	        		"OrgID", JJBaseApplication.user_OrgID, 
	        		"KeyWord",""};
	        	        
	        long id = JJBaseService.addMutilpartHttpTask("http://www.ohpest.com/ohpest-pco/webservices/get/data.html", params, D2EWorksheetActivity.this.getClass().getName(),D2EConfigures.TASK_GETCUSTOMERSBYUSER);
	        	        
	        setCurTaskID(id);
	        showWaitDialog(getResources().getString(R.string.handling));				

			
		}break;
		
		
		case R.id.menu_home:{
			backAciton();
		}break;

		case R.id.menu_help:{
			
			if(JJBaseApplication.g_documents.size() > 0){
				Intent intent = new Intent(D2EWorksheetActivity.this, D2EHelpActivity.class);
				D2EWorksheetActivity.this.startActivity(intent);		
				overridePendingTransition(R.anim.fade, R.anim.hold);
				finish();
			}
			else {
				SimpleDateFormat dates = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				
		        String[] params = new String[]{
		        		"FunType", "getWebSite", 
		        		"OrgID", JJBaseApplication.user_OrgID, 
		        		"Type","",
		        		"DateTime",dates.format(new Date())
		        		};
		        long id = JJBaseService.addMutilpartHttpTask("http://www.ohpest.com/ohpest-pco/webservices/get/data.html", params, D2EWorksheetActivity.this.getClass().getName(),D2EConfigures.TASK_GETSITE);
		        	        
		        setCurTaskID(id);
		        showWaitDialog(getResources().getString(R.string.handling));								
			}
			

		}break;

		
		}
		return true;
	}


	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		super.onPrepareOptionsMenu(menu);
		menu.clear();
        // Inflate the currently selected menu XML resource.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.worksheet_page_menu, menu);   	
        return true;
    }

    
	@Override
	public void backAciton() {
		Intent intent = new Intent(D2EWorksheetActivity.this, D2EMainScreen.class);
		D2EWorksheetActivity.this.startActivity(intent);		
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
		int values = ((Integer)param[0]).intValue();
		if(values == JJBaseService.BT_SERVICE_INT){
			String response = (String)param[2];
			if(response.toLowerCase().equals(SOPBluetoothService.STATE_FAILED.toLowerCase())){
				((JJBaseApplication)getApplication()).showMessage(getResources().getString(R.string.failed_read));	
				dismissProcessDialog();
				return;
			}
			else {
				String locationID = response.substring(14,22);
		        
				if(D2EConfigures.G_DEBUG){
					locationID = "93edb888";
				}
		        
				
		        String[] params = new String[]{
        		"FunType", "getSalesClientDataWithTask", 
        		"OrgID", JJBaseApplication.user_OrgID, 
        		"ReadNum",SOPBluetoothService.g_readerID,
        		"TagNum", locationID,
        		"UserID",JJBaseApplication.user_ID
        		};
		        long id = JJBaseService.addMutilpartHttpTask("http://www.ohpest.com/ohpest-pco/webservices/get/data.html", params, D2EWorksheetActivity.this.getClass().getName(),D2EConfigures.TASK_GET_CUSTOM_INFO);
		        setCurTaskID(id);
		        return;
			}
		}
		else if(values == JJBaseService.HTTP_SERVICE_INT){
		    JJTask task = (JJTask)param[1];
		    String response = null;
		    try{
			    InputStream ins = task.getInputStream();
			    byte[] bytes = JJNetHelper.readByByte(ins, -1);
			
			    response = new String(bytes, "UTF-8");
                ins.close();
                if(D2EConfigures.TEST){
                	Log.e("response: >>>>>> HTTP_SERVICE_INT: ", response);
                }
                if(D2EConfigures.G_DEBUG){
                    Log.e("response: >>>>>> HTTP_SERVICE_INT: ", response);                	
                }
    
                try{
    	            JSONObject j     = new JSONObject(response);
    	            String err = j.getString("error");
    	            if(err != null && err.length() > 0){
    	            	((JJBaseApplication)getApplication()).showMessage(err);
    	            	dismissProcessDialog();
    	            	return;
    	            }                	
                }catch(Exception ex){}
		    }
		    catch(Exception ex){}
		    if(response == null){
		    	((JJBaseApplication)getApplication()).showMessage(getResources().getString(R.string.hand_failed_try_again));
		    	dismissProcessDialog();
		    	return;
		    }
            if(task.getTaskId() == D2EConfigures.TASK_GETCUSTOMERBYDATE){
	           
		    	//[{"ID":"19","Division":"PCO","DivisionID":"6","Type":"Enterprise","UserID":"87","Name":"\u53ef\u53e3\u53ef\u4e50","Longitude":"121.448326","Latitude":"31.021942"}]  
            	JJBaseApplication.g_customerlist.clear();
	            if(response != null && response.length() > 0){
	            	try{
	    				JSONArray ja = new JSONArray(response);	
	    				int len = ja.length();
	    				for(int i = 0; i < len; i ++){
	    					String     tmp = ja.getString(i);
	    					JSONObject jo  = new JSONObject(tmp);   	
	    					TagItem item =new TagItem(jo.getString("Name"), jo.getString("ID"));
	    					JJBaseApplication.g_customerlist.add(item);
	    				}
	    						
	    				Intent intent = new Intent(D2EWorksheetActivity.this, D2ECustomerListActivity.class);				
	    				D2EWorksheetActivity.this.startActivity(intent);
	    				overridePendingTransition(R.anim.fade, R.anim.hold);
	    			}
	    			catch(Exception ex){}
	            }	            
		    } else if(task.getTaskId() == D2EConfigures.TASK_GETWORKSHEET){
		    	try{
		    		JJBaseApplication.g_worksheet.clear();
		    		JSONObject res = new JSONObject(response);
		    		//没有工单信息也让进入页面
		    		if(res.getString("Assignment") == null || res.getString("Assignment").toLowerCase().equals("null")){
		    			dismissProcessDialog();
		    			JJBaseApplication.user_CountTask="0";
		    			SUM=Integer.valueOf(JJBaseApplication.user_CountTask);
		    			if(D2EConfigures.TEST){
		    				Log.e("JJBaseApplication.user_CountTask============>", ""+(JJBaseApplication.user_CountTask));
		    				Log.e("Sumxxxxxxxxxxxxxxxxxxxxxxxxxxxx>", ""+SUM);
		    			}
		    			dismissProcessDialog();
		    			dismissWaitDialog();
		    		}
		    		
		    		JSONObject assignment = new JSONObject(res.getString("Assignment"));
		    		JSONArray  jc  = new JSONArray(assignment.getString("CountTask"));
		    		JSONArray  jd  = new JSONArray(assignment.getString("BeginTime"));
		    		
		    		int len = jc.length();
		    		int sum=0;
		    		for(int i = 0; i < len; i ++){
		    			JJBaseApplication.g_worksheet.put(jd.getString(i), jc.getString(i));
		    			sum+=Integer.valueOf(jc.getString(i));
		    			if(D2EConfigures.TEST){
		    				Log.e("tasknum---------->", jc.getString(i));	
		    			}		    			
		    		}
		    		if(D2EConfigures.TEST){
		    			Log.e("D2EConfigures.g_worksheet-------->", ""+JJBaseApplication.g_worksheet.toString());
		    			Log.e("sum--------------->", ""+sum);
		    		}
		    		
		    		JJBaseApplication.user_CountTask=String.valueOf(sum);
		    		SUM=Integer.valueOf(JJBaseApplication.user_CountTask);
		    		if(D2EConfigures.TEST){
	    				Log.e("Sum&&&&&&&&&&&&&&&&&&&&&&&&&&&&>", ""+SUM);
	    			}
		    		mHandler.sendEmptyMessage(0);
		    		if(D2EConfigures.TEST){
		    			Log.e("D2EConfigures.user_CountTask-------->", JJBaseApplication.user_CountTask);
		    		}
		    		//用于天气
		    		JJBaseApplication.g_weather.clear();
		    		JSONArray weather   =  new JSONArray(res.getString("Weather"));
		    		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		    		Calendar c = new GregorianCalendar();
		    		c.setTime(new Date());
		    		for(int j = 0;j < D2EConfigures.WEATHER_COUNT; j ++){
		    			JSONObject tmp = new JSONObject(weather.getString(j));
		    			JJBaseApplication.g_weather.put(sdf.format(c.getTime()), tmp.getString("temp"));
		    			c.add(Calendar.DAY_OF_MONTH, 1);
		    		}
		    		
		    	}
		    	catch(Exception ex){}
		    }
            
            
		    else if(task.getTaskId() == D2EConfigures.TASK_GETCUSTOMERSBYUSER){
		    	JJBaseApplication.mClientList.clear();
				try{
					JSONArray ja = new JSONArray(response);	
					int len = ja.length();
					for(int i = 0; i < len; i ++){
    					String     tmp = ja.getString(i);
    					JSONObject jo  = new JSONObject(tmp);  
    					
    					D2EListAdapterItam item = new D2EListAdapterItam(jo.getString("Name"), jo.getString("ID"));
    					JJBaseApplication.mClientList.add(item);
					}
				}
				catch(Exception ex){
				}
				Intent intent = new Intent(D2EWorksheetActivity.this, D2EReportActivity.class);
				D2EWorksheetActivity.this.startActivity(intent);		
				overridePendingTransition(R.anim.fade, R.anim.hold);
				finish();			
		    }
		    else if(task.getTaskId() == D2EConfigures.TASK_GET_CUSTOM_INFO){
		    	//{"SOPPositionList":[{"ID":"1","Division":"PCO","DivisionID":"6","SalesClientID":"19","TagID":"12","Name":"\u65a4\u65a4\u8ba1\u8f83","Memo":null,"StateID":null,"CityID":null,"Address":null,"Status":"Open","CreateTime":"2012-05-31 10:55:17","DeleteTime":null}],"SalesClientData":[{"ID":"19","Division":"PCO","DivisionID":"6","Type":"Enterprise","UserID":"87","Name":"\u5ba2\u670d1","Longitude":"","Latitude":""}]}  

	            if(response != null && response.length() > 0){
	            	try{
	    				JSONObject res            = new JSONObject(response);	    				
			            JSONArray salesClientAry  = new JSONArray(res.getString("SalesClientData"));
			            JSONObject salesClient    = new JSONObject(salesClientAry.getString(0));
			            
			            if(JJBaseApplication.g_customer != null){
			            	JJBaseApplication.g_customer.dealloc();
			            	JJBaseApplication.g_customer = null;
			            }
			            
			            JJBaseApplication.g_customer = new D2ECustomer();
			            JJBaseApplication.g_customer.mId = salesClient.getString("ID");
			            JJBaseApplication.g_customer.mName = salesClient.getString("Name");
			            JJBaseApplication.g_customer.mType = salesClient.getString("SalesClientType");
			            
			            //施工人员
			            JSONArray Assignment = new JSONArray(res.getString("Assignment"));
			            int cnt = Assignment.length();
			            for(int i = 0; i < cnt; i ++){
	    					String     tmp = Assignment.getString(i);
	    					JSONObject jo  = new JSONObject(tmp);  
	    					D2EPerson p = new D2EPerson(jo.getString("ID"),jo.getString("Name"), "");
	    					JJBaseApplication.g_customer.mPersons.add(p);
			            }
			            
			            //工单信息
			            JSONObject Task = new JSONObject(res.getString("Task"));
			            	JSONObject jt  = new JSONObject(Task.getString("0"));
			            	JJBaseApplication.g_customer.mSerial = jt.getString("Serial");
			            	JJBaseApplication.g_customer.mMemo   = jt.getString("Description");
			            	JJBaseApplication.g_customer.mTaskId = jt.getString("ID");
			            	
			            	//正常的服务是1 部署是2 监测是3 额外是4
			            	JJBaseApplication.g_customer.mTaskType = Task.getInt("TaskType");
			            	
			            
			            //位置标签
			            JSONArray SOPPositionList = new JSONArray(res.getString("SOPPositionList"));	    				
	    				int len = SOPPositionList.length();
	    				for(int i = 0; i < len; i ++){
	    					String     tmp = SOPPositionList.getString(i);
	    					JSONObject jo  = new JSONObject(tmp);  
	    					
	    					TagItem item = new TagItem(jo.getString("Name"), jo.getString("ID"));
	    					item.setTagNum(jo.getString("TagID"));
	    					JJBaseApplication.g_customer.mPositions.add(item);
	    				}
	    				
	    				
	    			}
	    			catch(Exception ex){}
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
						intent.setClass(D2EWorksheetActivity.this, D2ESOPActivity.class);
						D2EWorksheetActivity.this.startActivity(intent);
						overridePendingTransition(R.anim.fade, R.anim.hold);
						finish();
					}else{
						((JJBaseApplication) getApplication())
						.showMessage(getString(R.string.no_weeksheet_data));
					}
	            }
		    	
		    }
		    else if(task.getTaskId() == D2EConfigures.TASK_GETSITE){
				try{
					JSONObject jo = new JSONObject(response);
					JJBaseApplication.g_documents.clear();
					//先解析类型
					JSONObject Support = new JSONObject(jo.getString("Support"));
					//Hashtable<String, String> typekey = new Hashtable<String, String>();
					JSONObject types = new JSONObject(jo.getString("Type"));
					JSONArray names = types.names();
					int len = names.length();
					for(int i = 0; i < len; i ++){
						String name = names.getString(i);
						String value  = types.getString(name);
						
						String itemvalues = null;
						try{
							itemvalues = Support.getString(name);
						}catch(Exception e){}
						//根据类型解析文档
						if(itemvalues != null && itemvalues.length() > 0){
							
							
							D2EDocuments d = new D2EDocuments();
							d.mType  = name;
							d.mTitle = value;
														
							JSONArray tmp = new JSONArray(Support.getString(name));
							int cnt = tmp.length();
							for(int m = 0; m < cnt; m ++){
								JSONObject s = new JSONObject(tmp.getString(m));
								
								D2EDocumentItem item = new D2EDocumentItem();
								item.mID   = s.getString("ID");
								item.mMemo = s.getString("Memo");
								item.mTime = s.getString("CreateTime");
								item.mType = value;
								try{
									item.mTitle = s.getString("Title");
								}catch(Exception ex){}
								d.addItem(item);
							}
							
							JJBaseApplication.g_documents.add(d);
						}
						else {
							continue;
						}
					}
					Intent intent = new Intent(D2EWorksheetActivity.this, D2EHelpActivity.class);
					D2EWorksheetActivity.this.startActivity(intent);		
					overridePendingTransition(R.anim.fade, R.anim.hold);
					finish();					
				}
				catch(Exception ex){
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
		
		if(D2EConfigures.G_USEVIRCALDEVICE){
	    	String response= "[{\"ID\":\"19\",\"Division\":\"PCO\",\"DivisionID\":\"6\",\"Type\":\"Enterprise\",\"UserID\":\"87\",\"Name\":\"\u53ef\u53e3\u53ef\u4e50\",\"Longitude\":\"121.448326\",\"Latitude\":\"31.021942\"}]";  
	    	JJBaseApplication.g_customerlist.clear();
            if(response != null && response.length() > 0){
            	try{
    				JSONArray ja = new JSONArray(response);	
    				int len = ja.length();
    				for(int i = 0; i < len; i ++){
    					String     tmp = ja.getString(i);
    					JSONObject jo  = new JSONObject(tmp);   	
    					TagItem item = new TagItem(jo.getString("Name"), jo.getString("ID"));
    					JJBaseApplication.g_customerlist.add(item);
    				}
    						
    				Intent intent = new Intent(D2EWorksheetActivity.this, D2ECustomerListActivity.class);				
    				D2EWorksheetActivity.this.startActivity(intent);
    				overridePendingTransition(R.anim.fade, R.anim.hold);
    			}
    			catch(Exception ex){}
            }	            			
		}
		else {
			Date     now    = new Date();
			Calendar nowc   = new GregorianCalendar();
			nowc.setTime(now);
			
			Calendar selecc = new GregorianCalendar();
			selecc.setTime(date);
			if(selecc.get(Calendar.MONTH) != nowc.get(Calendar.MONTH)){
				
				//如果日期相差6天，则还是可以查看获取工单
				long selectTime=selecc.getTimeInMillis();
				long nowTime=nowc.getTimeInMillis();
				long differ=(selectTime-nowTime)/(24*60*60*1000);
				
				int differDate=Integer.valueOf(String.valueOf(differ));
				int myDiffer=Math.abs(differDate);
				//这里修改为6天内还是可以查看和修改的,实际是7天也可以查询
				if(D2EConfigures.TEST){
					Log.e("相差天数为：---------->",""+differ+"天");
					Log.e("相差的天数为：", ""+Math.abs(differDate)+"天");
				}
				if(myDiffer<=6){
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					mDateTitle.setText(sdf.format(date));
					

					SimpleDateFormat dates = new SimpleDateFormat("yyyyMMdd");
					JJBaseApplication.g_selecteDate = dates.format(date);
					
					SimpleDateFormat months = new SimpleDateFormat("MM-dd EEEE",Locale.US);
					JJBaseApplication.g_selecteMonth = months.format(date);
					
			        String[] params = new String[]{
			        		"FunType", "getSalesClientList_byStaff", 
			        		"UserID", JJBaseApplication.user_ID, 
			        		"Date",JJBaseApplication.g_selecteDate};
			        
			        if(D2EConfigures.TEST){
			        	Log.e("D2EConfigures.g_selecteDate------->", ""+JJBaseApplication.g_selecteDate);
			        	 YTStringHelper.array2string(params);
			        }
			        
			        long id = JJBaseService.addMutilpartHttpTask("http://www.ohpest.com/ohpest-pco/webservices/get/data.html", params, D2EWorksheetActivity.this.getClass().getName(),D2EConfigures.TASK_GETCUSTOMERBYDATE);			        	        
			        setCurTaskID(id);
			        showWaitDialog(getResources().getString(R.string.handling));			
				}else{
					((JJBaseApplication)getApplication()).showMessage(getResources().getString(R.string.date_error));
				}
				mMonth.today();
				return;
			}
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			mDateTitle.setText(sdf.format(date));
			

			SimpleDateFormat dates = new SimpleDateFormat("yyyyMMdd");
			JJBaseApplication.g_selecteDate = dates.format(date);
			
			SimpleDateFormat months = new SimpleDateFormat("MM-dd EEEE",Locale.US);
			JJBaseApplication.g_selecteMonth = months.format(date);
			
	        String[] params = new String[]{
	        		"FunType", "getSalesClientList_byStaff", 
	        		"UserID", JJBaseApplication.user_ID, 
	        		"Date",JJBaseApplication.g_selecteDate};
	        
	        Log.e("---------", "UserID: " + JJBaseApplication.user_ID + " and Date: " + JJBaseApplication.g_selecteDate);
	        if(D2EConfigures.TEST){
	        	Log.e("D2EConfigures.g_selecteDate------->", ""+JJBaseApplication.g_selecteDate);
	        	 YTStringHelper.array2string(params);
	        }
	        long id = JJBaseService.addMutilpartHttpTask("http://www.ohpest.com/ohpest-pco/webservices/get/data.html", params, D2EWorksheetActivity.this.getClass().getName(),D2EConfigures.TASK_GETCUSTOMERBYDATE);
	        	        
	        setCurTaskID(id);
	        
		}
		

	}

	private void enterMainActivity(){
		((JJBaseApplication)getApplication()).startJJService();
		
    	if(SOPBluetoothService.getService() == null || !SOPBluetoothService.getService().mIsRun){
    		((JJBaseApplication)getApplication()).showMessage(getResources().getString(R.string.bluetooth_prompt));
    		return;
    	}
    	showProcessDialog(getResources().getString(R.string.reading_card));
    	SOPBluetoothService.getService().doTask(SOPBTCallHelper.READ_TAG, D2EWorksheetActivity.class.getName(), SOPBTCallHelper.getInitOrder(SOPBluetoothService.g_readerID));                          

	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		switch(requestCode){
		
		case REQUEST_ENABLE_BT:{
			if(resultCode == Activity.RESULT_OK){
				initServices();
			}
			else {
				((JJBaseApplication)getApplication()).showMessage(getResources().getString(R.string.bluetooth_start_failed));
			}
		}break;
		
		case REQUESR_DEVICE:{
			if(resultCode == Activity.RESULT_CANCELED){
	        	finish();
				android.os.Process.killProcess(android.os.Process.myPid());		    				
			}
			
			SOPBluetoothService.g_btMacAddress = data.getExtras().getString(EXTRA_DEVICE_ADDRESS);
			SOPBluetoothService.g_readerID     = data.getExtras().getString(EXTRA_READER_ID);			
            
			SharedPreferences settings = getSharedPreferences(READER_PREFS_NAME, 0); 
			Editor editor = settings.edit();
			editor.putString(BT_MAC_KEY, SOPBluetoothService.g_btMacAddress);
			editor.putString(READER_KEY, SOPBluetoothService.g_readerID);
			editor.commit();
			
			enterMainActivity();
			
		}break;
		
		}
	}
	
	private void initServices(){
		//判断是否本地保存蓝牙设置
		SharedPreferences settings = getSharedPreferences(READER_PREFS_NAME, 0); 
		SOPBluetoothService.g_btMacAddress = settings.getString(BT_MAC_KEY, null);
		SOPBluetoothService.g_readerID     = settings.getString(READER_KEY, null);
		if(SOPBluetoothService.g_btMacAddress == null || SOPBluetoothService.g_readerID == null){
	        Intent serverIntent = new Intent(this, D2EDeviceListActivity.class);
	        startActivityForResult(serverIntent, REQUESR_DEVICE);
		}
		else {
			enterMainActivity();
		}
	}


}
