package com.mobilercn.sop.activity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


import java.util.ArrayList;
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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.mobilercn.base.BadgeView;
import com.mobilercn.base.JJBaseActivity;
import com.mobilercn.base.JJBaseApplication;
import com.mobilercn.base.JJBaseService;
import com.mobilercn.config.D2EConfigures;
import com.mobilercn.sop.R;
import com.mobilercn.sop.SOPAndroidActivity;
import com.mobilercn.sop.activity.D2EWorksheetList.WorkSheetItem;
import com.mobilercn.sop.bt.SOPBTCallHelper;
import com.mobilercn.sop.bt.SOPBluetoothService;
import com.mobilercn.sop.data.MyCustomerListData;
import com.mobilercn.sop.data.TagItem;
import com.mobilercn.sop.data.TagsAdapter;
import com.mobilercn.sop.item.D2ECustomer;
import com.mobilercn.sop.item.D2EDocuments;
import com.mobilercn.sop.item.D2EListAdapterItam;
import com.mobilercn.sop.item.D2EPerson;
import com.mobilercn.task.JJTask;
import com.mobilercn.util.JJHexHelper;
import com.mobilercn.util.JJNetHelper;
import com.mobilercn.util.YTFileHelper;
import com.mobilercn.util.YTStringHelper;
import com.mobilercn.widget.PullToRefreshListView;
import com.mobilercn.widget.YTEditText;
import com.mobilercn.widget.PullToRefreshListView.OnRefreshListener;

public class D2EHelpActivity extends JJBaseActivity {
	private static final int    REQUEST_ENABLE_BT    = 1;
	private static final int    REQUESR_DEVICE       = 2;

	private static final String READER_PREFS_NAME    = "READERID";
	private static final String BT_MAC_KEY           = "BTMAC";
	private static final String READER_KEY           = "READER";
	
    public static        String EXTRA_DEVICE_ADDRESS = "device_address";
    public static        String EXTRA_READER_ID      = "reader_id";

	
	private BadgeView     mBadgeView;
	private TagsAdapter mTagsAdapter;
	private GridView    mGridView;
	
	
	private AdapterView.OnItemClickListener mListOnItemClick = new AdapterView.OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			
			((JJBaseApplication)getApplication()).showMessage(getResources().getString(R.string.failed_read));	
			
		}
		
	};
	
	ListView mList;
	
    protected void onResume() {
    	super.onResume();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        
        setContentView(R.layout.d2e_help_grid);
        
        Window window = getWindow();
        window.setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.d2e_titlebar);
        
        TextView tv = (TextView)findViewById(R.id.title);
        tv.setText(getResources().getString(R.string.menu_support));
                
        mBadgeView = (BadgeView)findViewById(R.id.countTask);
        mBadgeView.setBadgePosition(BadgeView.POSITION_CENTER);
        mBadgeView.setBackgroundResource(R.drawable.badge_ifaux);
        mBadgeView.setVisibility(View.GONE);
        if(JJBaseApplication.user_CountTask != null && JJBaseApplication.user_CountTask.length() > 0){
        	mBadgeView.setText(JJBaseApplication.user_CountTask);
        }

        
        //grid
        JJBaseApplication.g_grid_type = D2EConfigures.GRID_DOCUMENT;
        mTagsAdapter =new TagsAdapter(getApplicationContext());
        if(D2EConfigures.G_USEVIRCALDEVICE){
        	mTagsAdapter.addObject("蟑螂", "1");
        	mTagsAdapter.addObject("鼠类", "2");
        	mTagsAdapter.addObject("蚊类", "3");
        	mTagsAdapter.addObject("白蚁", "4");
        }
        else {
        	for(D2EDocuments d : JJBaseApplication.g_documents){

            	mTagsAdapter.addObject(d.mTitle, "" + d.mItems.size(), JJBaseApplication.g_documents.indexOf(d));
        	}
        }
        
        mGridView = (GridView)findViewById(R.id.public_gridview);
        mGridView.setAdapter(mTagsAdapter);
        mGridView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				if(D2EConfigures.G_USEVIRCALDEVICE){
					
					Intent intent = new Intent(D2EHelpActivity.this, D2EDocumentListActivity.class);
					D2EHelpActivity.this.startActivity(intent);		
					overridePendingTransition(R.anim.fade, R.anim.hold);					
				}
				else {
					TagItem item = (TagItem)mTagsAdapter.getItem(arg2);
					JJBaseApplication.g_selectedDocument = JJBaseApplication.g_documents.get(item.index);
					String title=item.getTitle();
					Intent intent = new Intent(D2EHelpActivity.this, D2EDocumentListActivity.class);
					intent.putExtra("title", title);
					D2EHelpActivity.this.startActivity(intent);		
					overridePendingTransition(R.anim.fade, R.anim.hold);					
				}				
			}
        	
        });
    }
    
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch(item.getItemId()){
		
		case R.id.menu_home:{
			backAciton();
		}break;
		
		case R.id.menu_init:{
			
			if(D2EConfigures.G_USEVIRCALDEVICE){
				enterMainActivity();
			}
			else {
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
			}
		}break;
		
		case R.id.menu_problem:{
			Intent intent = new Intent(D2EHelpActivity.this, D2ECommentActivity.class);
			D2EHelpActivity.this.startActivity(intent);		
			overridePendingTransition(R.anim.fade, R.anim.hold);			
		}break;
		}
		return true;
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
				return ;
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

    	Builder builder = new Builder(D2EHelpActivity.this);  
        builder.setIcon(android.R.drawable.ic_dialog_info);  
        builder.setTitle(getResources().getString(R.string.warm_prompt));  
        builder.setMessage(getResources().getString(R.string.has_ness_inint));  
        builder.setPositiveButton(getResources().getString(R.string.jj_sure), new DialogInterface.OnClickListener(){  
            public void onClick(DialogInterface dialog,int which){  
            	
                //输入授权码
            	LayoutInflater inflater = getLayoutInflater();
                final View layout = inflater.inflate(R.layout.d2e_code, null);  
        		Builder buildertmp = new Builder(D2EHelpActivity.this);  
        		buildertmp.setIcon(android.R.drawable.ic_dialog_info);  
        		buildertmp.setTitle(getResources().getString(R.string.warm_prompt));  
        		buildertmp.setPositiveButton(getResources().getString(R.string.jj_sure), new DialogInterface.OnClickListener(){  
                    public void onClick(DialogInterface dialog,int which){  
    	            	if(D2EConfigures.G_USEVIRCALDEVICE){
    			            Intent intent = new Intent();
    			            intent.setClass(D2EHelpActivity.this,D2ECustomerActivity.class); 
    			            intent.putExtra(D2ECustomerActivity.PAGE_TYPE, D2ECustomerActivity.TYPE_SOP);
    			            D2EHelpActivity.this.startActivity(intent); 	
    			            overridePendingTransition(R.anim.fade, R.anim.hold);
    	            		return;
    	            	}
                    	
                        //输入授权码
                    	EditText et = (EditText)layout.findViewById(R.id.sec_code);

        		        String[] params = new String[]{
        		        		"FunType", "checkCode", 
        		        		"Code",et.getText().toString(),
        		        		"OrgID", JJBaseApplication.user_OrgID,
        		        		"Name",JJBaseApplication.current_user_name};
        		        YTStringHelper.array2string(params);
        		        long id = JJBaseService.addMutilpartHttpTask("http://www.ohpest.com/ohpest-pco/webservices/get/data.html", params, D2EHelpActivity.this.getClass().getName(),D2EConfigures.TASK_CHECKCODE);
        		        setCurTaskID(id);
        		        showWaitDialog(getResources().getString(R.string.handling));				
                    	return;
                    }  
                });    
        		buildertmp.setNegativeButton(getResources().getString(R.string.btn_d2eprompt_cancle),new DialogInterface.OnClickListener(){  
                    public void onClick(DialogInterface dialog,int which){  
                    }  
                });  
                  
                buildertmp.setView(layout);
        		buildertmp.show();
            }  
        });    
        builder.setNegativeButton(getResources().getString(R.string.btn_d2eprompt_cancle),new DialogInterface.OnClickListener(){  
            public void onClick(DialogInterface dialog,int which){  
            }  
        });  
        builder.show();
	}


	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		super.onPrepareOptionsMenu(menu);
		menu.clear();
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.help_page_menu, menu);   	
        return true;
    }

	
	@Override
	public void backAciton() {
		Intent intent = new Intent(D2EHelpActivity.this, D2EMainScreen.class);
		D2EHelpActivity.this.startActivity(intent);		
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
		        long id = JJBaseService.addMutilpartHttpTask("http://www.ohpest.com/ohpest-pco/webservices/get/data.html", params, D2EHelpActivity.this.getClass().getName(),D2EConfigures.TASK_GET_CUSTOM_INFO);
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
                
                if(D2EConfigures.G_DEBUG){
                    Log.e("response: >>>>>> ", response);                	
                }
                if(D2EConfigures.TEST){
                	Log.e("response: >>>>>> ", response);
                }
                try{
    	            JSONObject j     = new JSONObject(response);
    	            if(D2EConfigures.TEST){
    	            	Log.e("j=========>", ""+j);
    	            }
    	            String err = j.getString("error");
    	            if(err != null && err.length() > 0){
    	            	((JJBaseApplication)getApplication()).showMessage(err);
    	            	dismissProcessDialog();
    	            	return;
    	            }                	
                }catch(Exception ex){}
		    }
		    catch(Exception ex){}
		    if(response == null || response.length() == 0){
		    	((JJBaseApplication)getApplication()).showMessage(getString(R.string.hand_failed_try_again));
		    	dismissProcessDialog();
		    	return;
		    }
		    if(task.getTaskId() == D2EConfigures.TASK_CHECKCODE){
				//数据处理
				try{					
		            Intent intent = new Intent();
		            intent.setClass(D2EHelpActivity.this,D2ECustomerActivity.class); 
		            JJBaseApplication.sop_location_boolean = true;
		            intent.putExtra(D2ECustomerActivity.PAGE_TYPE, D2ECustomerActivity.TYPE_SOP);
		            JJBaseApplication.mCustomerList.clear();
		            if(response != null && response.length() > 0){
		            	try{
		    				JSONArray ja = new JSONArray(response);	
		    				int len = ja.length();
		    				for(int i = 0; i < len; i ++){
		    					String     tmp = ja.getString(i);
//		    					Log.i("tmp:----->", ""+tmp);
		    					JSONObject jo  = new JSONObject(tmp); 
		    					//1、这里获取了客户Logo的图片
		    					String url_clientlogo=jo.getString("Logo");
		    					String clientName=jo.getString("Name");
		    					Log.i("clientName----->", clientName);
		    					//2、接下来保存图片--这里要用异步来实现
		    					HashMap mLogoMap=new HashMap<String,String>();
		    					mLogoMap.put("url_clientlogo", url_clientlogo);
		    					mLogoMap.put("clientName", clientName);
		    					new DownLoadImage().execute(mLogoMap);
//		    					Bitmap customLogo=url2Bitmap(url_clientlogo);
//		    					Log.i("bitmap----->", ""+customLogo);
//		    					Log.i("url_clientlogo------>", ""+url_clientlogo);
		    					TagItem item =new TagItem(jo.getString("Name"), jo.getString("ID"));
		    					item.setCustomName(clientName);
//		    					item.setCustomLogo(customLogo);
//		    					D2EConfigures.g_list.add(item);
		    					JJBaseApplication.mCustomerList.add(item);
		    				}
		    			}
		    			catch(Exception ex){}
		            }
		            D2EHelpActivity.this.startActivity(intent); 	
		            overridePendingTransition(R.anim.fade, R.anim.hold);
		            finish();					
				}
				catch(Exception ex){					
				}
		    }
		    else if(task.getTaskId() == D2EConfigures.TASK_GET_CUSTOM_INFO){
		    	//{"SOPPositionList":[{"ID":"1","Division":"PCO","DivisionID":"6","SalesClientID":"19","TagID":"12","Name":"\u65a4\u65a4\u8ba1\u8f83","Memo":null,"StateID":null,"CityID":null,"Address":null,"Status":"Open","CreateTime":"2012-05-31 10:55:17","DeleteTime":null}],"SalesClientData":[{"ID":"19","Division":"PCO","DivisionID":"6","Type":"Enterprise","UserID":"87","Name":"\u5ba2\u670d1","Longitude":"","Latitude":""}]}  

	            if(response != null && response.length() > 0){
//	            	D2ESOPActivity dps = new D2ESOPActivity();
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
			            //if(Task.length() > 0){
			            	JSONObject jt  = new JSONObject(Task.getString("0"));
			            	JJBaseApplication.g_customer.mSerial = jt.getString("Serial");
			            	JJBaseApplication.g_customer.mMemo   = jt.getString("Description");
			            	JJBaseApplication.g_customer.mTaskId = jt.getString("ID");
			            	
			            	//正常的服务是1 部署是2 监测是3 额外是4
			            	JJBaseApplication.g_customer.mTaskType = Task.getInt("TaskType");
			            	
			            //}
			            
			            //位置标签
			            JSONArray SOPPositionList = new JSONArray(res.getString("SOPPositionList"));	    				
	    				int len = SOPPositionList.length();
	    				//D2EConfigures.g_soplist.clear();
	    				for(int i = 0; i < len; i ++){
	    					String     tmp = SOPPositionList.getString(i);
	    					JSONObject jo  = new JSONObject(tmp);  
	    					
	    					TagItem item =new TagItem(jo.getString("Name"), jo.getString("ID"));
	    					item.setTagNum(jo.getString("TagID"));
	    					JJBaseApplication.g_customer.mPositions.add(item);
	    					//D2EConfigures.g_soplist.add(item);
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
						intent.setClass(D2EHelpActivity.this, D2ESOPActivity.class);
						D2EHelpActivity.this.startActivity(intent);
						overridePendingTransition(R.anim.fade, R.anim.hold);
						finish();
					}else{
						((JJBaseApplication) getApplication())
						.showMessage(getString(R.string.no_weeksheet_data));
					}
	            }
		    	
		    }
		}
		dismissProcessDialog();
	}
	
	
	/**
	 * 异步保存图片
	 * @author ShawnXiao
	 *
	 */
	private class DownLoadImage extends AsyncTask<HashMap<String,String>,Integer,byte[] >{
		String clientName=null;
		@Override
		protected byte[] doInBackground(HashMap<String, String>... params) {
			HashMap mLoHashMap=params[0];
			String logoUrl=(String) mLoHashMap.get("url_clientlogo");
			clientName=(String) mLoHashMap.get("clientName");
			byte[] bytes=null;
			try{
				bytes = downloadImage(logoUrl);
			}catch(Exception e){
				e.printStackTrace();
			}
			
			return bytes;
		}
		@Override
		protected void onPostExecute(byte[] result) {
			super.onPostExecute(result);
			YTFileHelper fileHelper = YTFileHelper.getInstance();
			fileHelper.setContext(D2EHelpActivity.this);
			fileHelper.saveFile(clientName+"logo.jpg", result);
		}
		private byte[] downloadImage(String url){
			InputStream in=null;
			byte[] imageBytes=null;
			try {
				URL imgUrl=new URL(url);
				HttpURLConnection conn=(HttpURLConnection) imgUrl.openConnection();
				conn.setReadTimeout(10000);
				conn.setConnectTimeout(15000);
				conn.setDoInput(true);
				conn.connect();
				int response=conn.getResponseCode();
				if(response!=200){
					return null;
				}
				in=conn.getInputStream();
				imageBytes=JJNetHelper.readByByte(in, -1);
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			return imageBytes;
		}
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

}
