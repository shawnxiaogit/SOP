package com.mobilercn.sop.activity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.mobilercn.base.BadgeView;
import com.mobilercn.base.JJBaseActivity;
import com.mobilercn.base.JJBaseApplication;
import com.mobilercn.base.JJBaseService;
import com.mobilercn.config.D2EConfigures;
import com.mobilercn.sop.R;
import com.mobilercn.sop.SOPAndroidActivity;
import com.mobilercn.sop.bt.SOPBTCallHelper;
import com.mobilercn.sop.bt.SOPBluetoothService;
import com.mobilercn.sop.data.D2EMessageAdapter;
import com.mobilercn.sop.data.MessageItem;
import com.mobilercn.sop.data.MyBitmap;
import com.mobilercn.sop.data.MyCustomerListData;
import com.mobilercn.sop.data.MyMessageData;
import com.mobilercn.sop.data.PcoUser;
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

import com.mobilercn.widget.PullToRefreshListView;
import com.mobilercn.widget.PullToRefreshListView.OnRefreshListener;

public class D2EMessageActivity extends JJBaseActivity{
	private YTFileHelper ytfileHelper;
	
	public static final int    REQUEST_ENABLE_BT    = 1;
	public static final int    REQUESR_DEVICE       = 2;

	public static final String READER_PREFS_NAME    = "READERID";
	public static final String BT_MAC_KEY           = "BTMAC";
	public static final String READER_KEY           = "READER";
	
    public static        String EXTRA_DEVICE_ADDRESS = "device_address";
    public static        String EXTRA_READER_ID      = "reader_id";

    private boolean             mIsWarningOnce       = false;
    
    public static final String TAG = D2EMessageActivity.class.getName();
    
    /**
     * 用户判断是否点击报表，向网络请求了数据
     */
    public static boolean isReqestClientNet;
	private D2EListAdapterItam item;
	
	private MyMessageData myMessageData;
	
	
	private List<MessageItem> oldMessageItems=new ArrayList<MessageItem>();
	
	
	
	private D2EMessageAdapter mListAdapter;
	
	private int               mSelectMsgIndex = -1;
	
	ListView list;
	
	private BadgeView mBadgeView;
	
	private AdapterView.OnItemClickListener mListOnItemClick = new AdapterView.OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
						
			final MessageItem item = (MessageItem)mListAdapter.getItem(arg2 - 1);
			if(item != null){
				//如果是第一次对信息，即没有读过信息则把读信息时的数据传给后台 
				if(!item.isRead()){
					//add by shawn 2012-10-29
					//把阅读消息的相关信息传回后台
					String[] params = new String[]{
			        		"FunType", "setPushInfoRead", 
			        		"UserID",JJBaseApplication.user_ID,//员工ID
			        		"InfoID",item.getmID(),//消息ID
			        		"Memo",""
					};
			        YTStringHelper.array2string(params);
			        long id = JJBaseService.addMutilpartHttpTask("http://www.ohpest.com/ohpest-pco/webservices/get/data.html", params, TAG,D2EConfigures.TASK_READ_MSG);		       		        
			        setCurTaskID(id);
					//End
			        //如果没有读过则 ，点击的时候设置为读过
			        item.setRead(true);
				}
				
				
				//这里相当与看过了一条消息，要把这个状态也保存到文件中，以便下一次退出的时候看到的也是读过的
				MyMessageData messages=(MyMessageData) ytfileHelper.deSerialObject(JJBaseApplication.user_card_num+"msg.data");
				MessageItem itemExist=messages.getContainMessageItem(item);
				itemExist.setRead(true);
				//然后再次序列化保存到文件中
				ytfileHelper.serialObject(JJBaseApplication.user_card_num+"msg.data", messages);
		        LayoutInflater factory = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);;//LayoutInflater.from(this);
		        final View layout = factory.inflate(R.layout.d2e_read_msg_view, null);
		        
		        TextView txt = (TextView)layout.findViewById(R.id.msgcontentText);
		        txt.setText(item.getContent());
		        
		        TextView public_title=(TextView) layout.findViewById(R.id.public_title);
		        public_title.setText(getResources().getString(R.string.message_title));
		        
		        final PopupWindow pop = new PopupWindow(layout,LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT,true);//获取PopupWindow对象并设置窗体的大小
				pop.setAnimationStyle(R.style.PopupAnimation);
				ColorDrawable dw = new ColorDrawable(-00000);
				pop.setBackgroundDrawable(dw); //要放在setAtLocation前面
				pop.showAtLocation(layout, Gravity.CENTER|Gravity.CENTER,0,0); //设置窗体的位置
				pop.update();
				
				Button btn = (Button)layout.findViewById(R.id.read_msg_btn_cancel);
				btn.setOnClickListener(new OnClickListener(){

					public void onClick(View v) {
						
						pop.dismiss();
					}
					
				});
				
				
			}			
		}
		
	};
	
	
	String strError;
	
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.d2e_msg_list);
        Window window = getWindow();
        window.setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.d2e_titlebar);
        
        TextView tv = (TextView)findViewById(R.id.title);
        tv.setText(getResources().getString(R.string.message_title));
        
        mBadgeView = (BadgeView)findViewById(R.id.countTask);
        mBadgeView.setBadgePosition(BadgeView.POSITION_CENTER);
        mBadgeView.setBackgroundResource(R.drawable.badge_ifaux);
        mBadgeView.setVisibility(View.GONE);
        //消息文件存储工具
        ytfileHelper=  YTFileHelper.getInstance();
        ytfileHelper.setContext(getApplicationContext());
        if(JJBaseApplication.user_CountTask != null && JJBaseApplication.user_CountTask.length() > 0){
        	mBadgeView.setText(JJBaseApplication.user_CountTask);
        }
        myMessageData=new MyMessageData();
        
        mListAdapter = new D2EMessageAdapter(this.getApplicationContext());
//			add by shawn Begin
//			反序列化对象
			MyMessageData messages=(MyMessageData) ytfileHelper.deSerialObject(JJBaseApplication.user_card_num+"msg.data");
//			End
			if(D2EConfigures.TEST){
				Log.e("messages!=null==========>", ""+(messages!=null));
				
				
				if(messages!=null){
					int size=messages.getMessages().size();
					Log.e("size==========>", ""+size);
				for(int i=0;i<size;i++){
					if(messages.getMessages().get(i)!=null){
						String str=messages.getMessages().get(i).getContent().toString();
						Log.e("xxxxxxxxxx+++++++++++>", ""+str);
					}
				}
				}
			}
		if(messages!=null){
			if(messages.getMessages().size()>0){
				for(MessageItem item:messages.getMessages()){
						mListAdapter.addObject(item);
						mListAdapter.notifyDataSetChanged();
				}
				
			}
		}
        list = (ListView)findViewById(R.id.d2e_msg_list);
        isReqestClientNet=false;
        ((PullToRefreshListView) list).setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {

		        String[] params = new String[]{
		        		"FunType", "getPushInfoList", 
		        		"UserID",JJBaseApplication.user_SalesClientID};

		        YTStringHelper.array2string(params);
		        long id = JJBaseService.addMutilpartHttpTask("http://www.ohpest.com/ohpest-pco/webservices/get/pbitest.html", params, TAG,D2EConfigures.TASK_REFERSHMSG);		       		        
		        setCurTaskID(id);

            }
        });

        
		list.setAdapter(mListAdapter);
		list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		list.setOnItemClickListener(mListOnItemClick);
		list.setMinimumHeight(50);
		if(D2EConfigures.G_USEVIRCALDEVICE){
			mListAdapter.addObject("2012年5月28日上午肯德基工单安排,您的验证码是1234", "2012年5月28日上午肯德基工单安排,您的验证码是1234.");
			mListAdapter.addObject("2012年5月28日上午肯德基工单安排,您的验证码是1234", "2012年5月28日上午肯德基工单安排,您的验证码是1234.");
			mListAdapter.addObject("2012年5月28日上午肯德基工单安排,您的验证码是1234", "2012年5月28日上午肯德基工单安排,您的验证码是1234.");
			mListAdapter.notifyDataSetChanged();
		} 
		
    }
    
//    @Override
    protected void onResume() {
    	super.onResume();
    }
    
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch(item.getItemId()){
		
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
		
		case R.id.menu_help:{
			
			if(D2EConfigures.G_USEVIRCALDEVICE){
				Intent intent = new Intent(D2EMessageActivity.this, D2EHelpActivity.class);
				D2EMessageActivity.this.startActivity(intent);		
				overridePendingTransition(R.anim.fade, R.anim.hold);
				finish();	
			}
			else {
				if(JJBaseApplication.g_documents.size() > 0){
					Intent intent = new Intent(D2EMessageActivity.this, D2EHelpActivity.class);
					D2EMessageActivity.this.startActivity(intent);		
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
			        if(D2EConfigures.TEST){
			        	YTStringHelper.array2string(params);
			        }
			        long id = JJBaseService.addMutilpartHttpTask("http://www.ohpest.com/ohpest-pco/webservices/get/data.html", params, D2EMessageActivity.this.getClass().getName(),D2EConfigures.TASK_GETSITE);
			        	        
			        setCurTaskID(id);
			        showWaitDialog(getResources().getString(R.string.handling));								
				}				
			}						
			
		}break;
		
		case R.id.menu_workSheet:{
			if(D2EConfigures.G_USEVIRCALDEVICE){
				Intent intent = new Intent(D2EMessageActivity.this, D2EWorksheetActivity.class);
				D2EMessageActivity.this.startActivity(intent);		
				overridePendingTransition(R.anim.fade, R.anim.hold);
				finish();

			}
			else {				
		        String[] params = new String[]{
		        		"FunType", "getCountEveryDayTask", 
		        		"UserID", JJBaseApplication.user_ID, 
		        		"Day","7"};
		        YTStringHelper.array2string(params);
		        long id = JJBaseService.addMutilpartHttpTask("http://www.ohpest.com/ohpest-pco/webservices/get/data.html", params, D2EMessageActivity.this.getClass().getName(),D2EConfigures.TASK_GETWORKSHEET);
		        	        
		        setCurTaskID(id);
		        showWaitDialog(getResources().getString(R.string.handling));				
			}			
		}break;
		
		case R.id.menu_report:{
			
			if(D2EConfigures.G_USEVIRCALDEVICE){
				Intent intent = new Intent(D2EMessageActivity.this, D2EReportActivity.class);
				D2EMessageActivity.this.startActivity(intent);		
				overridePendingTransition(R.anim.fade, R.anim.hold);
				finish();			
			}
			else {
		        String[] params = new String[]{
		        		"FunType", "getSalesClientList", 
		        		"OrgID", JJBaseApplication.user_OrgID, 
		        		"KeyWord",""};
		        
		        
		        if(D2EConfigures.TEST){
		        	YTStringHelper.array2string(params);
		        }
		        long id = JJBaseService.addMutilpartHttpTask("http://www.ohpest.com/ohpest-pco/webservices/get/data.html", params, D2EMessageActivity.this.getClass().getName(),D2EConfigures.TASK_GETCUSTOMERSBYUSER);
		        	        
		        setCurTaskID(id);
		        showWaitDialog(getResources().getString(R.string.handling));								
			}			

		}break;
		
		
		case R.id.menu_home:{
			
			backAciton();
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
        inflater.inflate(R.menu.messagepage_menu, menu);   	
        return true;
    }



	@Override
	public void init() {
		
	}

	@Override
	public void taskSuccess(Object... param) {
		int values = ((Integer)param[0]).intValue();
		if(values == JJBaseService.BT_SERVICE_INT){
			String response = (String)param[2];
			if(D2EConfigures.TEST){
				Log.i("response=------>", response);
			}
			if(response.toLowerCase().equals(SOPBluetoothService.STATE_FAILED.toLowerCase())){
				((JJBaseApplication)getApplication()).showMessage(getResources().getString(R.string.failed_read));	
				dismissProcessDialog();
				return;
			}
			else {
				
				
		    	if(D2EConfigures.G_USEVIRCALDEVICE){

			    	response = "{" +
			    			"\"Task\":[{\"ID\":\"64\",\"Title\":\"\u533a\u57df\u670d\u52a1\""+
			    			",\"SignClauseID\":\"19\",\"Serial\":\"DD2012062319-16\",\"Description\":\"\",\"BeginTime\":\"2012-06-23 19:30:00\",\"EndTime\":\"2012-06-23 20:30:00\"}],"+
			    		"\"Assignment\":[{\"ID\":\"57\",\"Name\":\"\u5458\u5de56\"},{\"ID\":\"58\",\"Name\":\"\u5458\u5de57\"},{\"ID\":\"59\",\"Name\":\"\u5458\u5de58\"},"+
			    		"{\"ID\":\"60\",\"Name\":\"\u8001\u5458\u5de5\"},{\"ID\":\"61\",\"Name\":\"\u65b0\u5458\u5de5\"}],"+
			    		"\"SOPPositionList\":[{\"ID\":\"1\",\"Division\":\"PCO\",\"DivisionID\":\"6\",\"SalesClientID\":\"19\",\"TagID\":\"12\","+
			    		"\"Name\":\"\u53ef\u53e3\u53ef\u4e50\u4ed3\u5e93\",\"Memo\":null,\"StateID\":null,\"CityID\":null,\"Address\":null,"+
			    		"\"Status\":\"Open\",\"CreateTime\":\"2012-06-23 11:48:47\",\"DeleteTime\":null}],"+
			    		"\"SalesClientData\":[{\"ID\":\"19\",\"Division\":\"PCO\",\"DivisionID\":\"6\","+
			    		"\"Type\":\"Enterprise\",\"UserID\":\"87\",\"Name\":\"\u53ef\u53e3\u53ef\u4e50\",\"Longitude\":\"121.448326\",\"Latitude\":\"31.021942\",\"SalesClientType\":\"\u6d4b\u8bd5\u5ba2\u6237\"}]} ";
			    	
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
				            JJBaseApplication.g_customer.mName = "肯德基餐厅";
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
				            
				            	
				            //位置标签
				            JSONArray SOPPositionList = new JSONArray(res.getString("SOPPositionList"));	    				
		    				int len = SOPPositionList.length();
		    				for(int i = 0; i < len; i ++){
		    					String     tmp = SOPPositionList.getString(i);
		    					JSONObject jo  = new JSONObject(tmp);  
		    					if(D2EConfigures.TEST){
		    						Log.i("tmp----->", tmp);
		    					}
		    					TagItem item = new TagItem(jo.getString("Name"), jo.getString("ID"));
		    					item.setTagNum(jo.getString("TagID"));
		    					JJBaseApplication.g_customer.mPositions.add(item);
		    				}
		    				
		    				
		    			}
		    			catch(Exception ex){}
		            	
		            	//add by shawn 2012-10-18 Begin
		            	//这里保存获取的客户信息
		            	D2ECustomer d2ECustomer=JJBaseApplication.g_customer;
		            	ytfileHelper.serialObject("cust.data", d2ECustomer);
		            	
		            	//End
			            Intent intent = new Intent();
			            intent.setClass(D2EMessageActivity.this,D2ESOPActivity.class); 
			            D2EMessageActivity.this.startActivity(intent); 	
			            overridePendingTransition(R.anim.fade, R.anim.hold);
			            finish();					
			            return;
		            }
		    	}
				
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
		        
		        if(D2EConfigures.TEST){
		        	YTStringHelper.array2string(params);
		        }
		        
		        long id = JJBaseService.addMutilpartHttpTask("http://www.ohpest.com/ohpest-pco/webservices/get/data.html", params, D2EMessageActivity.this.getClass().getName(),D2EConfigures.TASK_GET_CUSTOM_INFO);
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
                	Log.e("response: >>>>>> ", response);
                }
                if(D2EConfigures.G_DEBUG){
                    Log.e("response: >>>>>> ", response);                	
                }
                try{
    	            JSONObject j     = new JSONObject(response);
    	            String err = j.getString("error");
    	            if(err != null && err.length() > 0){
    	            	((JJBaseApplication)getApplication()).showMessage(err);
    	            	if(task.getTaskId() == D2EConfigures.TASK_REFERSHMSG){
    	            		((PullToRefreshListView) list).onRefreshComplete();
    	            	}
    	            	dismissProcessDialog();
    	            	return;
    	            }  
    	            
                }catch(Exception ex){}
		    }
		    catch(Exception ex){}
		    if(response == null || response.length() == 0){
		    	((JJBaseApplication)getApplication()).showMessage(getResources().getString(R.string.net_not_well));
		    	dismissProcessDialog();
		    	return;
		    }
		    if(task.getTaskId() == D2EConfigures.TASK_CHECKCODE){
				//数据处理
				try{					
		            Intent intent = new Intent();
		            intent.setClass(D2EMessageActivity.this,D2ECustomerActivity.class); 
		            JJBaseApplication.sop_location_boolean = true;
		            intent.putExtra(D2ECustomerActivity.PAGE_TYPE, D2ECustomerActivity.TYPE_SOP);
		            JJBaseApplication.g_list.clear();
		            if(response != null && response.length() > 0){
		            	try{
		    				JSONArray ja = new JSONArray(response);	
		    				int len = ja.length();
		    				for(int i = 0; i < len; i ++){
		    					String     tmp = ja.getString(i);
		    					JSONObject jo  = new JSONObject(tmp);   	
		    					TagItem item =new TagItem(jo.getString("Name"), jo.getString("ID"));
		    					JJBaseApplication.g_list.add(item);
		    				}
		    			}
		    			catch(Exception ex){}
		            }
		            D2EMessageActivity.this.startActivity(intent); 	
		            overridePendingTransition(R.anim.fade, R.anim.hold);
		            finish();					
				}
				catch(Exception ex){					
				}
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
			            //=====================================
			            //这里实例化 了一个客户对象
			            //下面进行了客户对象属性的赋值
			            //如果要序列化的话,当对象的属性赋值完毕后，保存到手机硬盘上
			            JJBaseApplication.g_customer = new D2ECustomer();
			            JJBaseApplication.g_customer.mId = salesClient.getString("ID");
			            JJBaseApplication.g_customer.mName = salesClient.getString("Name");
			            JJBaseApplication.g_customer.mType = salesClient.getString("SalesClientType");
			            Log.i("D2EConfigures.g_customer.mName---->", ""+JJBaseApplication.g_customer.mName);
			            
			            //施工人员
			            JSONArray Assignment = new JSONArray(res.getString("Assignment"));
			            int cnt = Assignment.length();			            
			            JJBaseApplication.g_customer.mPersons.clear();
			            for(int i = 0; i < cnt; i ++){
	    					String     tmp = Assignment.getString(i);
	    					JSONObject jo  = new JSONObject(tmp);  
	    					D2EPerson p = new D2EPerson(jo.getString("ID"),jo.getString("Name"), "");
	    					JJBaseApplication.g_customer.mPersons.add(p);
			            }
			            
			            //工单信息
			            JSONObject Task = new JSONObject(res.getString("Task"));
			            //if(Task.length() > 0){
			            	JSONObject jt  = new JSONObject(Task.getString("0"));
			            	JJBaseApplication.g_customer.mSerial = jt.getString("Serial");
			            	JJBaseApplication.g_customer.mMemo   = jt.getString("Description");
			            	JJBaseApplication.g_customer.mTaskId = jt.getString("ID");
			            	
			            	//正常的服务是1 部署是2 监测是3 额外是4
		            	if(D2EConfigures.G_DEBUG){
		            		JJBaseApplication.g_customer.mTaskType = 1;//Task.getInt("TaskType");			            		
		            	}
		            	else {
		            		JJBaseApplication.g_customer.mTaskType = Task.getInt("TaskType");
		            	}
		            	 JSONArray SOPPositionList=null;
		            	try{	
			            		SOPPositionList = new JSONArray(res.getString("SOPPositionList"));
			            } catch(Exception e){
		                	e.printStackTrace();
		                	JSONObject error=new JSONObject(res.getString("SOPPositionList"));
		                	strError=error.getString("error");
		                }
	    				int len = SOPPositionList.length();
	    				for(int i = 0; i < len; i ++){
	    					String     tmp = SOPPositionList.getString(i);
	    					JSONObject jo  = new JSONObject(tmp);  
	    					TagItem item =new TagItem(jo.getString("Name"), jo.getString("ID"));
	    					item.setTagNum(jo.getString("TagID"));
	    					JJBaseApplication.g_customer.mPositions.add(item);
	    				}
	    				//===========================================
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
						intent.setClass(D2EMessageActivity.this, D2ESOPActivity.class);
						D2EMessageActivity.this.startActivity(intent);
						overridePendingTransition(R.anim.fade, R.anim.hold);
						finish();
					}else{
						((JJBaseApplication) getApplication())
						.showMessage(getString(R.string.no_weeksheet_data));
					}
	            }
		    }
		    else if(task.getTaskId() == D2EConfigures.TASK_REFERSHMSG){
				try{
					if(response!=null&&response.length()>0){
					JSONArray ja = new JSONArray(response);
					if(D2EConfigures.TEST){
						Log.e("response==>", ""+ja);
					}
					MyMessageData messages=null;
					if(D2EConfigures.TEST){
						Log.e("ytfileHelper.isFileExist(JJBaseApplication.user_car", ""+(ytfileHelper.isFileExist(JJBaseApplication.user_card_num+"msg.data")));
					}
					if(ytfileHelper.isFileExist(JJBaseApplication.user_card_num+"msg.data")){
						messages=(MyMessageData) ytfileHelper.deSerialObject(JJBaseApplication.user_card_num+"msg.data");
						if(messages==null){
							messages=new MyMessageData();
						}
					}else{
						messages=new MyMessageData();
					}
					if(D2EConfigures.TEST){
						Log.e("messages=null", ""+(messages==null));
					}
					int len = ja.length();
					HashMap<String,String> userLogoMap=new HashMap<String,String>();
					for(int i = 0; i < len; i ++){
						String     tmp = ja.getString(i);
						JSONObject jo  = new JSONObject(tmp);
						MessageItem item = new MessageItem(jo.getString("Message"), jo.getString("Message"));
						item.setDate(jo.getString("CreateTime"));
						item.setID(jo.getString("ID"));
						//modify by shawn 2012-12-04 Begin
						//这里下载保存用户的图片
						String userName=null;
						String urlLogo=null;
						if(jo.has("UserName")){
							userName=jo.getString("UserName");
							item.setmCreateUserName(userName);
						}
						if(jo.has("UserLogo")){
							urlLogo=jo.getString("UserLogo");
							item.setmStrUserLogo(urlLogo);
						}
						if(D2EConfigures.TEST){
							Log.e("userName!=null&&userName.length()>0", ""+(userName!=null&&userName.length()>0));
							Log.e("urlLogo!=null&&urlLogo.length()>0", ""+(urlLogo!=null&&urlLogo.length()>0));
							Log.e("xxxxxxxxxxxx", ""+(userName!=null&&userName.length()>0
								&&urlLogo!=null&&urlLogo.length()>0));
						}
						if(userName!=null&&userName.length()>0
								&&urlLogo!=null&&urlLogo.length()>0){
							userLogoMap.put("userName", userName);
							userLogoMap.put("userLogo", urlLogo);
							new AsyncDownImage(item).execute(userLogoMap);
							if(ytfileHelper.isFileExist(userName+"logo")){
								byte[] result=ytfileHelper.readFile(userName+"logo");
								MyBitmap bitmap=new MyBitmap(result, userName);
								if(D2EConfigures.TEST){
									Log.e("result=========>", ""+result.length);
									Log.e("bitmap=========>", ""+bitmap.getBitmapBytes().length);
								}
								item.setMyBitmap(bitmap);
							}
						}
						//End
						if(!messages.isContainMessageItem(item)){
							messages.getMessages().add(item);
						}
						
					}
					ytfileHelper.serialObject(JJBaseApplication.user_card_num+"msg.data", messages);
					}
					MyMessageData messages=(MyMessageData) ytfileHelper.deSerialObject(JJBaseApplication.user_card_num+"msg.data");
					if(messages!=null){
						if(messages.getMessages().size()>0){
							mListAdapter.clear();
							for(MessageItem item:messages.getMessages()){
								mListAdapter.addObject(item);
								mListAdapter.notifyDataSetChanged();
							}
						}
					}
				}
				catch(Exception ex){
					ex.printStackTrace();
				}
		    	((PullToRefreshListView) list).onRefreshComplete();
		    }
		    else if(task.getTaskId() == D2EConfigures.TASK_READ_MSG){
		    	if(D2EConfigures.TEST){
		    		Log.e("D2EConfigures.TASK_READ_MSG============>", "D2EConfigures.TASK_READ_MSG");
		    	}
		    	try{
					if(response!=null&&response.length()>0){
						JSONObject jo=new JSONObject(response);
						if(D2EConfigures.TEST){
							Log.e("阅读消息返回：", ""+jo);
						}
					}
		    	}catch(Exception e){
		    		
		    	}
		    }
		    
		    else if(task.getTaskId() == D2EConfigures.TASK_GETWORKSHEET){
		    	try{
		    		JJBaseApplication.g_worksheet.clear();
		    		JSONObject res = new JSONObject(response);
		    		//没有工单信息也让进入页面
		    		if(res.getString("Assignment") == null || res.getString("Assignment").toLowerCase().equals("null")){
		    			((JJBaseApplication)getApplication()).showMessage(getResources().getString(R.string.no_weeksheet_data));
		    			dismissProcessDialog();
		    			JJBaseApplication.user_CountTask="0";
		    			Intent intent = new Intent(D2EMessageActivity.this, D2EWorksheetActivity.class);
		    			intent.putExtra("type", D2EWorksheetActivity.TYPE_WORKWHEET);
		    			D2EMessageActivity.this.startActivity(intent);		
		    			overridePendingTransition(R.anim.fade, R.anim.hold);
		    			finish();
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
		    		
	    			Intent intent = new Intent(D2EMessageActivity.this, D2EWorksheetActivity.class);
	    			intent.putExtra("type", D2EWorksheetActivity.TYPE_WORKWHEET);
	    			D2EMessageActivity.this.startActivity(intent);		
	    			overridePendingTransition(R.anim.fade, R.anim.hold);
	    			finish();
		    	}
		    	catch(Exception ex){}
		    }
		    else if(task.getTaskId() == D2EConfigures.TASK_GETCUSTOMERSBYUSER){
		    	isReqestClientNet=true;
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
				Intent intent = new Intent(D2EMessageActivity.this, D2EReportActivity.class);
				D2EMessageActivity.this.startActivity(intent);		
				overridePendingTransition(R.anim.fade, R.anim.hold);
				finish();			
		    }
		    else if(task.getTaskId() == D2EConfigures.TASK_GETSITE){
				try{
					JSONObject jo = new JSONObject(response);
					JJBaseApplication.g_documents.clear();
					//先解析类型
					JSONObject Support = new JSONObject(jo.getString("Support"));
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
								try{
									item.mTitle = s.getString("Title");
								}catch(Exception ex){}
								item.mType = value;
								
								d.addItem(item);
							}
							
							JJBaseApplication.g_documents.add(d);
						}
						else {
							continue;
						}
					}
					Intent intent = new Intent(D2EMessageActivity.this, D2EHelpActivity.class);
					D2EMessageActivity.this.startActivity(intent);		
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


	//for download image 
		private class AsyncDownImage extends AsyncTask<HashMap<String,String>,Integer,byte[]>{
			String userName=null;
			String userLogo=null;
			MessageItem item;
			public AsyncDownImage(MessageItem item){
				this.item=item;
			}
			
			@Override
			protected byte[] doInBackground(HashMap<String, String>... params) {
				byte[] results=null;
				userName=params[0].get("userName");
				userLogo=params[0].get("userLogo");
				try{
					results=downloadImage(userLogo);
					
				}catch(Exception e){
					
				}
				
				return results;
			}
			@Override
			protected void onPostExecute(byte[] result) {
				super.onPostExecute(result);
				ytfileHelper.saveFile(userName+"logo", result);
			}
			
			
		}
		private byte[] downloadImage(String imgUrl) throws IOException {
			InputStream is = null;
	        try {
				URL url = new URL(imgUrl);        
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();        
				conn.setReadTimeout(10000 /* milliseconds */);        
				conn.setConnectTimeout(15000 /* milliseconds */);        
				conn.setRequestMethod("GET");       
				conn.setDoInput(true);        
				conn.connect();        
				int response = conn.getResponseCode();
				if(response != 200){
					return null;        				
				}
				is = conn.getInputStream();
				return JJNetHelper.readByByte(is, -1);
			} 
	        finally {       
				if (is != null) {            
					is.close();       
				}    
	        }
		}	
		
	
	@Override
	public boolean isExit() {
		return false;
	}



	@Override
	public void backAciton() {
		Intent intent = new Intent(D2EMessageActivity.this, D2EMainScreen.class);
		D2EMessageActivity.this.startActivity(intent);		
		overridePendingTransition(R.anim.fade, R.anim.hold);
		finish();									

	}
	
	//===============================================================================
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
	        	return;
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
	
	
	private void enterMainActivity(){
		((JJBaseApplication)getApplication()).startJJService();
		
    	if(SOPBluetoothService.getService() == null || !SOPBluetoothService.getService().mIsRun){
    		((JJBaseApplication)getApplication()).showMessage(getResources().getString(R.string.bluetooth_prompt));
    		return;
    	}


    	showProcessDialog(getResources().getString(R.string.reading_card));
    	SOPBluetoothService.getService().doTask(SOPBTCallHelper.READ_TAG, D2EMessageActivity.class.getName(), SOPBTCallHelper.getInitOrder(SOPBluetoothService.g_readerID));                          
		
	}
	public D2EListAdapterItam getD2EListAdapterItam(){
		return item;
	}
	public void setD2EListAdapterItam(D2EListAdapterItam item){
		this.item=item;
	}
}
