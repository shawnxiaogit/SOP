package com.mobilercn.sop.activity;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OptionalDataException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mobilercn.base.BadgeView;
import com.mobilercn.base.JJBaseActivity;
import com.mobilercn.base.JJBaseApplication;
import com.mobilercn.base.JJBaseService;
import com.mobilercn.config.D2EConfigures;
import com.mobilercn.sop.R;
import com.mobilercn.sop.SOPAndroidActivity;
import com.mobilercn.sop.bt.SOPBTCallHelper;
import com.mobilercn.sop.bt.SOPBluetoothService;
import com.mobilercn.sop.data.MyCustomerListData;
import com.mobilercn.sop.data.PcoUser;
import com.mobilercn.sop.data.PcoUserAdapter;
import com.mobilercn.sop.data.TagItem;
import com.mobilercn.sop.data.TagsAdapter;
import com.mobilercn.sop.item.D2ECustomer;
import com.mobilercn.sop.item.D2EDocumentItem;
import com.mobilercn.sop.item.D2EDocuments;
import com.mobilercn.sop.item.D2EListAdapterItam;
import com.mobilercn.sop.item.D2EPerson;
import com.mobilercn.sop.item.D2EPersonAdapter;
import com.mobilercn.task.JJTask;
import com.mobilercn.util.JJNetHelper;
import com.mobilercn.util.YTFileHelper;
import com.mobilercn.util.YTStringHelper;
import com.mobilercn.widget.YTEditText;

public class D2ESOPActivity extends JJBaseActivity {
	
	public TagsAdapter getTagsAdapter(){
		return mTagsAdapter;
	}
	private TagsAdapter mTagsAdapter;
	private GridView    mGridView;
	
	private String      mLocationName = "";
	private String      mLocationID   = "";
	
	private TagItem     mChangedItem;
	
	private String      mTagNum       = "";
	private String strSelectPositonID="";
	
	private boolean     mSubmit       = false;
	
	private BadgeView   mBadgeView;
	
	private D2EPerson   mCheckedPerson;
	
	private D2EPersonAdapter mPersonAdapter;
	private PcoUser mCheckPcoUser;
	private ArrayList<PcoUser> mCheckUserList=new  ArrayList<PcoUser>() ;
	private PcoUserAdapter mPcoUserAdapter;
	private boolean mIsShowSubmitMenu = false;
	
	private static boolean mIsCheckedIn = false;
	
	private AdapterView.OnItemClickListener mListOnItemClick = new AdapterView.OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			
			mCheckedPerson = (D2EPerson)arg0.getAdapter().getItem(arg2);
			if(D2EConfigures.TEST){
				mCheckedPerson.print();
			}
        	SOPBluetoothService.getService().doTask(SOPBTCallHelper.TAG_CHECKIN,
        			D2ESOPActivity.class.getName(), SOPBTCallHelper.getInitOrder(SOPBluetoothService.g_readerID));                          
		
		}
		
	};
	private String strMyError;
	Button btn_back;
	Button btn_submit;
	
	TextView newtag  ;
	EditText name   ;
	private YTFileHelper ytfileHelper;
	
	D2ECustomer mcustomer;
	D2ECustomer lastCustomer;
	
	private Button btn_check_in;
	
	private ImageView smile_sub;
	private ImageView tagepage_custom_logo;
	
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.d2e_tagpage);
        Window window = getWindow();
        window.setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.d2e_titlebar);
        
        JJBaseApplication.sop_location_boolean = true;
        ytfileHelper = YTFileHelper.getInstance();
        ytfileHelper.setContext(getApplicationContext());
        
        if(D2EConfigures.TEST){
        	Log.e("D2ESOPActivity----------------->", "onCreate()");
        	Log.e("mIsCheckedIn------------->", ""+mIsCheckedIn);
        }
        
        
        Intent intent=getIntent();
        strMyError=intent.getStringExtra("strError");
        
        mSubmit = false;
        
        JJBaseApplication.g_grid_type = D2EConfigures.GRID_LOCATION;
        
        TextView tv = (TextView)findViewById(R.id.title);
        tv.setText(getResources().getString(R.string.menu_sop));
        smile_sub=(ImageView) this.findViewById(R.id.smile_sub);
        tagepage_custom_logo=(ImageView) this.findViewById(R.id.tagepage_custom_logo);
        smile_sub.setOnClickListener(new SubmitReportListener());
        
        //判断客户logo是否存在，存在则显示
        YTFileHelper fielhelper = YTFileHelper.getInstance();
        fielhelper.setContext(getApplicationContext());
        //modify by shawn 2012-10-10 Begin
        //这里出现过空指针异常,应该先判断是否为空
        byte[] imagelogo=  fielhelper.readFile(JJBaseApplication.g_customer.mName+"logo.jpg");
        if(D2EConfigures.TEST){
        	Log.e("fielhelper----------->", ""+(fielhelper!=null));
        	Log.e("JJBaseApplication.g_customer.mName!=null------>", ""+(JJBaseApplication.g_customer.mName!=null));
        }
        if(JJBaseApplication.g_customer.mName!=null&&JJBaseApplication.g_customer.mName.length()>0){
	        if(fielhelper.readFile(JJBaseApplication.g_customer.mName+"logo.jpg")!=null){
	        	imagelogo=fielhelper.readFile(JJBaseApplication.g_customer.mName+"logo.jpg");
	        	tagepage_custom_logo.setImageBitmap(BitmapFactory.decodeByteArray(imagelogo, 0, imagelogo.length));
	        }	
        }
        //End
        //客户名
        TextView ed = (TextView)findViewById(R.id.tagpage_custom);
        if(D2EConfigures.G_USEVIRCALDEVICE){
        	ed.setText("肯德基餐厅");
        }
        else {
        	ed.setText((JJBaseApplication.g_customer != null && JJBaseApplication.g_customer.mName != null && JJBaseApplication.g_customer.mName.length() > 0) ? 
        			JJBaseApplication.g_customer.mName :
        				"-");
        	
        }
        
    	ed = (TextView)findViewById(R.id.tagpage_stype);
        if(D2EConfigures.G_USEVIRCALDEVICE){
        	ed.setText("餐饮");
        }
        else {
        	//类型
        	ed.setText((JJBaseApplication.g_customer != null && JJBaseApplication.g_customer.mType != null && JJBaseApplication.g_customer.mType.length() > 0) ? 
        			JJBaseApplication.g_customer. mType : 
        				"-");
        	
        }
        
        
    	//施工单号
    	ed = (TextView)findViewById(R.id.tagpage_serial);
        if(D2EConfigures.G_USEVIRCALDEVICE){
        	ed.setText("VSI-001-0002");
        }
        else {
        	ed.setText((JJBaseApplication.g_customer != null && JJBaseApplication.g_customer.mSerial != null && JJBaseApplication.g_customer.mSerial.length() > 0) ? 
        			JJBaseApplication.g_customer.mSerial :
        				"-");
        	
        }
        
        
    	//施工人数
    	ed = (TextView)findViewById(R.id.tagpage_person);
        if(D2EConfigures.G_USEVIRCALDEVICE){
        	ed.setText("1人");
        }
        else {
        	ed.setText(JJBaseApplication.g_customer != null ? ""+JJBaseApplication.g_customer.mPersons.size() : "N/A");
        }
    	
    	//额外施工信息
    	ed = (TextView)findViewById(R.id.tagpage_memo);
        if(D2EConfigures.G_USEVIRCALDEVICE){
        	ed.setText("-");
        }
        else {
        	ed.setText((JJBaseApplication.g_customer != null && JJBaseApplication.g_customer.mMemo != null && JJBaseApplication.g_customer.mMemo.length() > 0) ? 
        			JJBaseApplication.g_customer.mMemo :
        				"-");
        }
    	

        
    	
        btn_check_in= (Button)findViewById(R.id.btn_checkin);
      //如果签到成功了就把签到按钮隐藏
        if(JJBaseApplication.isCheckedIn){
        	btn_check_in.setVisibility(View.GONE);
        }
        btn_check_in.setOnClickListener(new OnClickListener(){
			public void onClick(View arg0) {
				
				if(D2EConfigures.G_USEVIRCALDEVICE){
	            	LayoutInflater inflater = getLayoutInflater();
	                final View layout = inflater.inflate(R.layout.d2e_checkin, null);
	                
	                ListView list = (ListView)layout.findViewById(R.id.d2e_check_list);
	                mPersonAdapter = new D2EPersonAdapter(getApplicationContext());
	                
	                list.setAdapter(mPersonAdapter);
	                list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
	                list.setOnItemClickListener(mListOnItemClick);
	                list.setMinimumHeight(50);

	        		if(D2EConfigures.G_USEVIRCALDEVICE){
	        			mPersonAdapter.addObject("1","Kris", "79600011");
	        			mPersonAdapter.addObject("2","Tina", "79600012");
	        			mPersonAdapter.addObject("3","Shown", "79600013");
	        			mPersonAdapter.notifyDataSetChanged();
	        		}               
	                
	        		Builder buildertmp = new Builder(D2ESOPActivity.this);  
	        		buildertmp.setIcon(android.R.drawable.ic_dialog_info);       		
	        		buildertmp.setTitle(getResources().getString(R.string.scene_people_sign_in));  	        		
	                buildertmp.setView(layout);
	        		final AlertDialog dialog = buildertmp.show();					

	                final Button btn_send = (Button)layout.findViewById(R.id.btn_yes);
	                btn_send.setOnClickListener(new OnClickListener(){
	                	                	
						public void onClick(View arg0) {
							
	                		((JJBaseApplication)getApplication()).showMessage(getResources().getString(R.string.handle_success));
	                		dialog.dismiss();
						}
	                	
	                });
				}
				else {
					if(JJBaseApplication.g_customer != null &&JJBaseApplication.g_customer.mPersons.size() == 0){
						((JJBaseApplication)getApplication()).showMessage(getResources().getString(R.string.no_scene_people));	
						return;
					}
					
	            	LayoutInflater inflater = getLayoutInflater();
	                final View layout = inflater.inflate(R.layout.d2e_checkin, null);
	                
	                ListView list = (ListView)layout.findViewById(R.id.d2e_check_list);
	                mPcoUserAdapter=new PcoUserAdapter(getApplicationContext());
	                list.setAdapter(mPcoUserAdapter);
	                list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
	                list.setMinimumHeight(100);

	        		if(D2EConfigures.G_USEVIRCALDEVICE){
	        			mPersonAdapter.addObject("1","Kris", "79600011");
	        			mPersonAdapter.addObject("2","Tina", "79600012");
	        			mPersonAdapter.addObject("3","Shown", "79600013");
	        			mPersonAdapter.notifyDataSetChanged();
	        		}         
	        		
	        		//add by shawn 2012-9-8 Begin
	        		//如果签到过，则提示已经签到
	        		if(JJBaseApplication.isCheckedIn){
        				((JJBaseApplication)getApplication()).showMessage(getResources().getString(R.string.had_sign_in));	
        				return;
	        		}
	        		
	        		//End
	                
	        		Builder buildertmp = new Builder(D2ESOPActivity.this);  
	        		buildertmp.setIcon(android.R.drawable.ic_dialog_info);       		
	        		buildertmp.setTitle(getResources().getString(R.string.scene_people_sign_in));  	        		
	                buildertmp.setView(layout);
	        		final AlertDialog dialog = buildertmp.show();	
	        		
	        		final Button btn_card=(Button)layout.findViewById(R.id.btn_checkin_card);
	        		btn_back=(Button)layout.findViewById(R.id.btn_checkin_back);
	        		btn_submit=(Button)layout.findViewById(R.id.btn_checkin_submit);
	        		//如果人员大于1，则显示提交按钮
	        		if(mCheckUserList.size()>0){
	        			btn_back.setVisibility(View.GONE);
						btn_submit.setVisibility(View.VISIBLE);
	        		}
	        		
	        		btn_card.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							
							SOPBluetoothService.getService().doTask(SOPBTCallHelper.TAG_CHECKIN,
				        			D2ESOPActivity.class.getName(), SOPBTCallHelper.getInitOrder(SOPBluetoothService.g_readerID));
							if(D2EConfigures.TEST){
								Log.e("mPcoUserAdapter.getCount()----------->", ""+(mPcoUserAdapter.getCount()));
								Log.e("mPcoUserAdapter.getCount()>0----------->", ""+(mPcoUserAdapter.getCount()>0));
							}
							if(mPcoUserAdapter.getCount()>0){
								btn_back.setVisibility(View.GONE);
								btn_submit.setVisibility(View.VISIBLE);
							}
						}
					});
	        		btn_back.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							mCheckUserList.clear();
							mPcoUserAdapter.clear();
							mPcoUserAdapter.notifyDataSetChanged();
							btn_back.setVisibility(View.VISIBLE);
							btn_submit.setVisibility(View.GONE);
							dialog.dismiss();
						}
					});
	        		btn_submit.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							int cnt=mCheckUserList.size();
							StringBuffer sb = new StringBuffer();
		                	sb.append("[");
		                	for(int i = 0; i < cnt; i ++){
		                		PcoUser person = mCheckUserList.get(i);
		                		sb.append("\"").append(person.getStrNumbers()).append("\"").append(",");
		                	}
		                	sb.deleteCharAt(sb.length() - 1);
		                	sb.append("]");
							
	        		        String[] params = new String[]{
	        		        		"FunType", "CheckIn", 
	        		        		"TaskID",JJBaseApplication.g_customer.mTaskId,
	        		        		"CheckTag",sb.toString()
	        		        		};
	        		        long id = JJBaseService.addMutilpartHttpTask("http://www.ohpest.com/ohpest-pco/webservices/get/data.html", params, D2ESOPActivity.this.getClass().getName(),D2EConfigures.TASK_CHECKIN);
	        		        
	        		        YTStringHelper.array2string(params);
	        		        
	        		        setCurTaskID(id);
	        		        dialog.dismiss();
	        		        showWaitDialog(getResources().getString(R.string.handling_wait));				
						}
	                	
	                });
	        		
	        		//add by shawn 2012-9-8 Begin
	        		//监听对话框关闭的事件
	        		dialog.setOnKeyListener(new OnKeyListener() {
						@Override
						public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
							if(D2EConfigures.TEST){
								Log.e("dialog is click =====================--------->", "000000000000000000000");
								
							}
							if (keyCode == KeyEvent.KEYCODE_BACK) {
								if(D2EConfigures.TEST){
									Log.e("backisClick=======================>", "********************");
								}
								dialog.dismiss();
								mCheckUserList.clear();
								mPcoUserAdapter.clear();
								mPcoUserAdapter.notifyDataSetChanged();
								btn_back.setVisibility(View.VISIBLE);
								btn_submit.setVisibility(View.GONE);
							}
							return false;
						}
					});
	        		//End
				}
				
			}        	
        });    
    	
    	
        
        mBadgeView = (BadgeView)findViewById(R.id.countTask);
        mBadgeView.setBadgePosition(BadgeView.POSITION_CENTER);
        mBadgeView.setBackgroundResource(R.drawable.badge_ifaux);
        mBadgeView.setVisibility(View.GONE);
        if(JJBaseApplication.user_CountTask != null && JJBaseApplication.user_CountTask.length() > 0){
        	mBadgeView.setText(JJBaseApplication.user_CountTask);
        }

        
        //grid
        mTagsAdapter = new TagsAdapter(getApplicationContext());
        if(mTagsAdapter==null){
        	mIsShowSubmitMenu = true;
        }
        MyCustomerListData myCustomerListData=(MyCustomerListData) ytfileHelper.deSerialObject(getResources().getString(R.string.customer_list));
        if(myCustomerListData.getWeekSerialCustomer(JJBaseApplication.g_customer)!=null){
        	JJBaseApplication.g_customer=myCustomerListData.getWeekSerialCustomer(JJBaseApplication.g_customer);
        }
    	for(TagItem sop : JJBaseApplication.g_customer.mPositions){
    		if(!sop.isChecked()){
        		mTagsAdapter.addObject(sop);        			
    		}
    	}
        //End
        
                
        if(
        		(JJBaseApplication.g_customer != null && JJBaseApplication.g_customer.mPositions.size() > 0 && JJBaseApplication.g_customer.allChecked()) ||
        		(JJBaseApplication.g_customer != null && JJBaseApplication.g_customer.mPositions.size() == 0 && JJBaseApplication.g_customer.mMemo != null && 
        				JJBaseApplication.g_customer.mMemo.length() > 0)
        		){
        	LinearLayout info = (LinearLayout)findViewById(R.id.public_information);
        	info.setVisibility(View.VISIBLE);
        }
        else {
        	LinearLayout info = (LinearLayout)findViewById(R.id.public_information);
        	info.setVisibility(View.INVISIBLE);        	
        }
        
        mGridView = (GridView)findViewById(R.id.public_gridview);
        mGridView.setAdapter(mTagsAdapter);
        mGridView.setOnItemLongClickListener(new OnItemLongClickListener(){

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				
				if(!JJBaseApplication.isCheckedIn){
					((JJBaseApplication)getApplication()).showMessage(getResources().getString(R.string.pleace_sign_in_first));
					return false;
				}
				
				if(D2EConfigures.G_USEVIRCALDEVICE){
	            	LayoutInflater inflater = getLayoutInflater();
	                final View layout = inflater.inflate(R.layout.d2e_changetag, null);
	                                
	        		Builder buildertmp = new Builder(D2ESOPActivity.this);  
	        		buildertmp.setIcon(android.R.drawable.ic_dialog_info);       		
	        		buildertmp.setTitle(getResources().getString(R.string.update_card));  	        		
	                buildertmp.setView(layout);
	        		final AlertDialog dialog = buildertmp.show();			

	        		return true;
				}
				JJBaseApplication.selectPosition=(TagItem)mTagsAdapter.getItem(arg2);
				mChangedItem = (TagItem)mTagsAdapter.getItem(arg2);
				strSelectPositonID=mChangedItem.getId();
				JJBaseApplication.sop_location=mChangedItem;
				//modify by shawn 2012-9-12 Begin
				//把授权码去掉,改为刷员工卡
				Builder myBuilder=new Builder(D2ESOPActivity.this);
				myBuilder.setIcon(android.R.drawable.ic_dialog_info);
				myBuilder.setTitle(getResources().getString(R.string.brush_user_card));
				myBuilder.setPositiveButton(getResources().getString(R.string.dialog_btn_yes_text), new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						showProcessDialog(getResources().getString(R.string.reading_card));
						SOPBluetoothService.getService().doTask(SOPBTCallHelper.TAG_GET_EMPLOEE_NUM,
			        			D2ECheckListActivity.class.getName(), SOPBTCallHelper.getInitOrder(SOPBluetoothService.g_readerID));
						
					}
				});
				myBuilder.setNegativeButton(getResources().getString(R.string.btn_d2eprompt_cancle), new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
					}
				});
				myBuilder.show();
				
				//End
				
				return true;
			}
        	
        });

        mGridView.setOnItemClickListener(new OnItemClickListener(){
 
        	@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
        		
				if(!JJBaseApplication.isCheckedIn){
					((JJBaseApplication)getApplication()).showMessage(getResources().getString(R.string.pleace_sign_in_first));
					return ;
				}

				JJBaseApplication.selectPosition=(TagItem)mTagsAdapter.getItem(arg2);
				TagItem obj = (TagItem)mTagsAdapter.getItem(arg2);
				if(D2EConfigures.G_USEVIRCALDEVICE){
					Intent intent = new Intent(D2ESOPActivity.this, D2ECheckListActivity.class);
					JJBaseApplication.sop_name = "肯德基";
					JJBaseApplication.sop_id   = obj.getTitle();
					
					D2ESOPActivity.this.startActivity(intent);
					JJBaseApplication.sop_location = obj;
					TagItem item =new TagItem("灶台", "15");
					JJBaseApplication.sop_location.addItem(item);
					finish();				

				}
				else {
									
					mLocationName = obj.getTitle();
					mLocationID   = obj.getId();
					
					
					JJBaseApplication.sop_location = obj;
					
					strSelectPositonID = obj.getId();
					JJBaseApplication.sop_location.mTagNum = mTagNum;
					
					//case 1:作业点检查部分
					if(obj.mItems != null && obj.mItems.size() > 0){
						Intent intent = new Intent(D2ESOPActivity.this, D2ECheckListActivity.class);
						intent.putExtra("custom", JJBaseApplication.g_customer.mName);
						intent.putExtra("position", mLocationName);
						intent.putExtra("PositionID", strSelectPositonID);
						intent.putExtra("locationID", mLocationID);
						intent.putExtra("customid", JJBaseApplication.g_customer.mId);
						D2ESOPActivity.this.startActivity(intent);
						overridePendingTransition(R.anim.fade, R.anim.hold);
						finish();		
					}
					//case 2:作业点全新
					else {
						String[] params=new String[]{
								"FunType", "getSOPPointListWithTask",
								"TaskID",JJBaseApplication.g_customer.mTaskId,
								"PositionID",strSelectPositonID
							};
							YTStringHelper.array2string(params);
							long id = JJBaseService.addMutilpartHttpTask("http://www.ohpest.com/ohpest-pco/webservices/get/data.html", params, D2ESOPActivity.this.getClass().getName(),D2EConfigures.TASK_GETCHECKBYLOCATION);
							setCurTaskID(id);
					        showWaitDialog(getResources().getString(R.string.handling));								
					}					
				}
				
			}
        	
        });
    }
    
    
    /**
     * 提交按钮功能
     * @author ShawnXiao
     *
     */
    private class SubmitReportListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			submitReport();
		}
    	
    }
    
    @Override
    protected void onDestroy() {
    	super.onDestroy();
    	if(D2EConfigures.TEST){
    		Log.e("D2ESOPActivity-------------------->", "onDestroy()");
    		
    	}
    }
    /**
     * 提交报表数据
     */
    private void submitReport(){
    	//输入本次任务备注
    	LayoutInflater inflater = getLayoutInflater();
        final View layout = inflater.inflate(R.layout.d2e_report_memo, null);  
        final YTEditText et = (YTEditText)layout.findViewById(R.id.report_memo);
		final RelativeLayout comment_panel_choseclient=(RelativeLayout) layout.findViewById(R.id.comment_panel_choseclient);
		comment_panel_choseclient.setVisibility(View.GONE);
		Builder buildertmp = new Builder(D2ESOPActivity.this);  
		buildertmp.setIcon(android.R.drawable.ic_dialog_info);  
		buildertmp.setTitle(getResources().getString(R.string.excution_feedback));  
		buildertmp.setPositiveButton(getResources().getString(R.string.dialog_btn_yes_text), new DialogInterface.OnClickListener(){  
            public void onClick(DialogInterface dialog,int which){  
		        StringBuffer sb = new StringBuffer();
		        sb.append("[");
		        	for(TagItem location : JJBaseApplication.g_customer.mPositions){
    	        		for(TagItem sub : location.mItems){
    	        			//做过一个改动，为了测试把字段"Option"换成了"Mark"
    	        			if(sub.mType.toLowerCase().equals("OPSite".toLowerCase())){
    		        			sb.append("{\"").append("SOPPositionID").append("\":").append("\"").append(location.getId()).append("\",");
    		        			sb.append("\"").append("SOPPointID").append("\":").append("\"").append(sub.mIndex).append("\",");

    		        			sb.append("\"").append("Type").append("\":").append("\"").append(sub.mType).append("\",");        		        			
    		        			if(sub.checkByUserCard){
    		        				sb.append("\"").append("CheckType").append("\":").append("\"").append(sub.checkType).append("\",");
    		        			}
    		        			sb.append("\"").append("SuppliesID").append("\":").append("\"").append(sub.mSuppliesID).append("\",");
    		        			sb.append("\"").append("InstrumentID").append("\":").append("\"").append(sub.mInstrumentID).append("\",");
    		        			sb.append("\"").append("Rate").append("\":").append("\"").append(sub.mRateFact).append("\",");
    		        			sb.append("\"").append("Volume").append("\":").append("\"").append(sub.mVolumeFact).append("\",");
    		        			
    		        			sb.append("\"").append("Memo").append("\":").append("\"").append(sub.mMemo).append("\"},");
    	        			}
    	        			else if(sub.mType.toLowerCase().equals("Monitor".toLowerCase())){
    	        	            if(JJBaseApplication.g_customer.mTaskType == D2EConfigures.MONITOR_INIT){
    	        	            	if(sub.mRoachNub != null && sub.mRoachNub.length() > 0){
    	    		        			sb.append("{\"").append("SOPPositionID").append("\":").append("\"").append(location.getId()).append("\",");
    	    		        			sb.append("\"").append("SOPPointID").append("\":").append("\"").append(sub.mIndex).append("\",");
            		        			sb.append("\"").append("Type").append("\":").append("\"").append(sub.mType).append("\",");        		        			
            		        			if(sub.checkByUserCard){
            		        				sb.append("\"").append("CheckType").append("\":").append("\"").append(sub.checkType).append("\",");
            		        			}
    	    		         			sb.append("\"").append("MonitorType").append("\":").append("\"").append(D2EConfigures.MONITOR_ROACH).append("\",");
    	    		         			sb.append("\"").append("Name").append("\":").append("\"").append(location.getTitle()).append("\",");
    	    		         			sb.append("\"").append("Facilities").append("\":").append("\"").append(sub.mRoachNub).append("\",");
    	    		         			sb.append("\"").append("Mark").append("\":").append("\"").append(D2EConfigures.MONITOR_INIT_DATA).append("\",");
    	    		 	    		        			
    	    		        			
    	    		        			sb.append("\"").append("Memo").append("\":").append("\"").append(sub.mMemo).append("\"},");	        				
    	        	            	}
    	        	            	if(sub.mMouseNub != null && sub.mMouseNub.length() > 0) {
    	    		        			sb.append("{\"").append("SOPPositionID").append("\":").append("\"").append(location.getId()).append("\",");
    	    		        			sb.append("\"").append("SOPPointID").append("\":").append("\"").append(sub.mIndex).append("\",");
            		        			sb.append("\"").append("Type").append("\":").append("\"").append(sub.mType).append("\",");        		        			
            		        			if(sub.checkByUserCard){
            		        				sb.append("\"").append("CheckType").append("\":").append("\"").append(sub.checkType).append("\",");
            		        			}
    	    		         			sb.append("\"").append("MonitorType").append("\":").append("\"").append(D2EConfigures.MONITOR_MOUSE).append("\",");
    	    		         			sb.append("\"").append("Name").append("\":").append("\"").append(location.getTitle()).append("\",");
    	    		         			sb.append("\"").append("Facilities").append("\":").append("\"").append(sub.mMouseNub).append("\",");
    	    		         			sb.append("\"").append("Mark").append("\":").append("\"").append(D2EConfigures.MONITOR_INIT_DATA).append("\",");
    	    		 	    		        			
    	    		        			
    	    		        			sb.append("\"").append("Memo").append("\":").append("\"").append(sub.mMemo).append("\"},");
    	        	            	}
    	        	            }
    	        	            //查验
    	        	            else {
    	        	     	        	            	
    	        	            	if(sub.mRoachActive != null && sub.mRoachActive.length() > 0 &&
    	        	            			sub.mRoachTotal != null && sub.mRoachTotal.length() > 0){
    	    		        			sb.append("{\"").append("SOPPositionID").append("\":").append("\"").append(location.getId()).append("\",");
    	    		        			sb.append("\"").append("SOPPointID").append("\":").append("\"").append(sub.mIndex).append("\",");
            		        			sb.append("\"").append("Type").append("\":").append("\"").append(sub.mType).append("\",");        		        			
            		        			if(sub.checkByUserCard){
            		        				sb.append("\"").append("CheckType").append("\":").append("\"").append(sub.checkType).append("\",");
            		        			}
    	    		         			sb.append("\"").append("MonitorType").append("\":").append("\"").append(D2EConfigures.MONITOR_ROACH).append("\",");
    	    		         			sb.append("\"").append("EffFacilities").append("\":").append("\"").append(sub.mRoachActive).append("\",");
    	    		         			sb.append("\"").append("Results").append("\":").append("\"").append(sub.mRoachTotal).append("\",");
    	    		         			sb.append("\"").append("Mark").append("\":").append("\"").append(D2EConfigures.MONITOR_CHECK_DATA).append("\",");
    	    		 	    		        			
    	    		        			
    	    		        			sb.append("\"").append("Memo").append("\":").append("\"").append(sub.mMemo).append("\"},");
    	        	            		
    	        	            	}
    	        	            	if(sub.mMouseActive != null && sub.mMouseActive.length() > 0 &&
    	        	            			sub.mMouseTotal != null && sub.mMouseTotal.length() > 0) {
    	    		        			sb.append("{\"").append("SOPPositionID").append("\":").append("\"").append(location.getId()).append("\",");
    	    		        			sb.append("\"").append("SOPPointID").append("\":").append("\"").append(sub.mIndex).append("\",");
    	    		        			
            		        			sb.append("\"").append("Type").append("\":").append("\"").append(sub.mType).append("\",");        		        			
            		        			if(sub.checkByUserCard){
            		        				sb.append("\"").append("CheckType").append("\":").append("\"").append(sub.checkType).append("\",");
            		        			}
            		        			sb.append("\"").append("MonitorType").append("\":").append("\"").append(D2EConfigures.MONITOR_MOUSE).append("\",");
    	    		         			sb.append("\"").append("EffFacilities").append("\":").append("\"").append(sub.mMouseActive).append("\",");
    	    		         			sb.append("\"").append("Results").append("\":").append("\"").append(sub.mMouseTotal).append("\",");
    	    		         			sb.append("\"").append("Mark").append("\":").append("\"").append(D2EConfigures.MONITOR_CHECK_DATA).append("\",");
    	    		 	    		        			
    	    		        			
    	    		        			sb.append("\"").append("Memo").append("\":").append("\"").append(sub.mMemo).append("\"},");
    	        	            	}	        	            	
    	        	            }
    	        	           
    	        	            

    	        			}
    	        			//Modify By Shawn 2012-8-30
    	        			//超声波属于监测点，所以提交报表的时候，数据要和监测点的数据一样
    	        			else if(sub.mType.toLowerCase().equals("Survey".toLowerCase())){
    	        				sb.append("{\"").append("SOPPositionID").append("\":").append("\"").append(location.getId()).append("\",");
    		        			sb.append("\"").append("Type").append("\":").append("\"").append(sub.mType).append("\",");
    		        			if(sub.checkByUserCard){
    		        				sb.append("\"").append("CheckType").append("\":").append("\"").append(sub.checkType).append("\",");
    		        			}
    		        			//数量
    		        			sb.append("\"").append("Results").append("\":").append("\"").append(sub.mMounseNumber).append("\",");
    		        			sb.append("\"").append("SOPPointID").append("\":").append("\"").append(sub.mIndex).append("\",");
    		        			sb.append("\"").append("State").append("\":").append("\"").append(sub.mStateInt).append("\",");
    		        			sb.append("\"").append("Memo").append("\":").append("\"").append(sub.mMemo).append("\"},");	 
    	        			}
    	        			else {
    		        			sb.append("{\"").append("SOPPositionID").append("\":").append("\"").append(location.getId()).append("\",");
    		        			sb.append("\"").append("Type").append("\":").append("\"").append(sub.mType).append("\",");        		        			
    		        			if(sub.checkByUserCard){
    		        				sb.append("\"").append("CheckType").append("\":").append("\"").append(sub.checkType).append("\",");
    		        			}
    		        			sb.append("\"").append("SOPPointID").append("\":").append("\"").append(sub.mIndex).append("\",");
    		        			sb.append("\"").append("State").append("\":").append("\"").append(sub.mStateInt).append("\",");
    		        			sb.append("\"").append("Memo").append("\":").append("\"").append(sub.mMemo).append("\"},");	        				
    	        			}
    	        			
    	        		}
    	        	}
	        	
	        	sb.deleteCharAt(sb.toString().length() - 1);
	        	sb.append("]");
	        	
		        String[] params = new String[]{
		        		"FunType", "insertSOPReport", 
		        		"TaskID", JJBaseApplication.g_customer.mTaskId,
		        		"Memo", YTStringHelper.replaceBlank(et.getText().toString()),
		        		"Data", sb.toString()};
		        //"TaskID"
		        
		        YTStringHelper.array2string(params);
		        
		        long ids = JJBaseService.addMutilpartHttpTask("http://www.ohpest.com/ohpest-pco/webservices/get/data.html", params, D2ESOPActivity.this.getClass().getName(),D2EConfigures.TASK_SUBMIT);
		        setCurTaskID(ids);  
		        showWaitDialog(getResources().getString(R.string.handling));
            }  
        });   
		
        buildertmp.setView(layout);
		buildertmp.show();
    }
    
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		final int id = item.getItemId();
		if(mIsShowSubmitMenu){
			
			switch(id){
			
			case R.id.menu_view:{
				final Intent intent = new Intent(D2ESOPActivity.this, D2EReportViewActivity.class);	
				intent.putExtra("type", D2EReportViewActivity.VIEW_TYPE_TODO);
				D2ESOPActivity.this.startActivity(intent);
			}break;
			
			case R.id.menu_submit:{
				
				submitReport();
				
			}break;
			
			}
		}
		else {
			
			if(mTagsAdapter.getCount() > 0){
				Builder builder = new Builder(D2ESOPActivity.this);  
		        builder.setIcon(android.R.drawable.ic_dialog_info);  
		        builder.setTitle(getResources().getString(R.string.warm_prompt));  
		        builder.setMessage(getResources().getString(R.string.check_no_fini_leave));  
		        builder.setPositiveButton(getResources().getString(R.string.jj_sure), new DialogInterface.OnClickListener(){  
		            public void onClick(DialogInterface dialog,int which){  
		            	JJBaseApplication.isCheckedIn=false;
		            	menuSelected(id);
		            }

		        });    
		        builder.setNegativeButton(getResources().getString(R.string.btn_d2eprompt_cancle),new DialogInterface.OnClickListener(){  
	                public void onClick(DialogInterface dialog,int which){  
	                }  
	            });  
		        builder.show();								
			}
			else {
				menuSelected(id);
			}
			
			
			
		}

				
		return true;
	}

	private void menuSelected(int id){
		switch(id){
		
		//case R.id.menu_back:
		case R.id.menu_message:{
			Intent intent = new Intent(D2ESOPActivity.this, D2EMessageActivity.class);
			D2ESOPActivity.this.startActivity(intent);		
			overridePendingTransition(R.anim.fade, R.anim.hold);
			finish();		
		}break;
		
		case R.id.menu_help:{
			if(JJBaseApplication.g_documents.size() > 0){
				Intent intent = new Intent(D2ESOPActivity.this, D2EHelpActivity.class);
				D2ESOPActivity.this.startActivity(intent);		
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
		        long ids = JJBaseService.addMutilpartHttpTask("http://www.ohpest.com/ohpest-pco/webservices/get/data.html", params, D2ESOPActivity.this.getClass().getName(),D2EConfigures.TASK_GETSITE);
		        	        
		        setCurTaskID(ids);
		        showWaitDialog(getResources().getString(R.string.handling_wait));								
			}
		}break;
		
		case R.id.menu_workSheet:{
			
			
	        String[] params = new String[]{
	        		"FunType", "getCountEveryDayTask", 
	        		"UserID", JJBaseApplication.user_ID, 
	        		"Day","7"};
	        long ids = JJBaseService.addMutilpartHttpTask("http://www.ohpest.com/ohpest-pco/webservices/get/data.html", params, D2ESOPActivity.this.getClass().getName(),D2EConfigures.TASK_GETWORKSHEET);
	        	        
	        setCurTaskID(ids);
	        showWaitDialog(getResources().getString(R.string.handling_wait));	
	        if(D2EConfigures.G_USEVIRCALDEVICE){
				Intent intent = new Intent(D2ESOPActivity.this, D2EWorksheetActivity.class);
				D2ESOPActivity.this.startActivity(intent);		
				overridePendingTransition(R.anim.fade, R.anim.hold);
				finish();
	        }
			
		}break;
		
		case R.id.menu_report:{
			
	        String[] params = new String[]{
	        		"FunType", "getSalesClientList", 
	        		"OrgID", JJBaseApplication.user_OrgID, 
	        		"KeyWord",""};
	        	        
	        long ids = JJBaseService.addMutilpartHttpTask("http://www.ohpest.com/ohpest-pco/webservices/get/data.html", params, D2ESOPActivity.this.getClass().getName(),D2EConfigures.TASK_GETCUSTOMERSBYUSER);
	        	        
	        setCurTaskID(ids);
	        showWaitDialog(getResources().getString(R.string.handling_wait));				


	        if(D2EConfigures.G_USEVIRCALDEVICE){
				Intent intent = new Intent(D2ESOPActivity.this, D2EReportActivity.class);
				D2ESOPActivity.this.startActivity(intent);		
				overridePendingTransition(R.anim.fade, R.anim.hold);
				finish();				        	
	        }
		}break;
		
		
		case R.id.menu_home:{
			Intent intent = new Intent(D2ESOPActivity.this, D2EMainScreen.class);
			D2ESOPActivity.this.startActivity(intent);		
			overridePendingTransition(R.anim.fade, R.anim.hold);
			finish();				        	
		}break;
		
		}		
	}
	

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		super.onPrepareOptionsMenu(menu);
		menu.clear();
        // Inflate the currently selected menu XML resource.
        MenuInflater inflater = getMenuInflater();
        if(D2EConfigures.TEST){
        	Log.e("D2ESOPActivity-------------->", "onPrepareOptionsMenu");
        	Log.e("D2ESOPActivity-------------->", ""+mSubmit);
        }
        if(!mSubmit){
        	if(D2EConfigures.TEST){
        		Log.e("lastCustomer!=null----------->", ""+(lastCustomer!=null));
        		if(lastCustomer!=null){
        		Log.e("lastCustomer.mPositions.size()>0------------>", ""+(lastCustomer.mPositions.size()>0));
        		}
        	}
        	if(lastCustomer!=null){
	        	if(lastCustomer.mPositions.size()>0){
	        		if(D2EConfigures.TEST){
	            		Log.e("lastCustomer.allChecked()------------>", ""+(lastCustomer.allChecked()));
	            	}
	        		if(lastCustomer.allChecked()){
	        			inflater.inflate(R.menu.submit_menu, menu);
	        			mIsShowSubmitMenu = true;
	        		}
	        		else {
	        			inflater.inflate(R.menu.tagspage_menu, menu);
	        			mIsShowSubmitMenu = false;
	        		}
	        	}
        	}else if(JJBaseApplication.g_customer.mPositions.size() > 0){
        		if(JJBaseApplication.g_customer.allChecked()){
        			inflater.inflate(R.menu.submit_menu, menu);
        			mIsShowSubmitMenu = true;
        		}
        		else {
        			inflater.inflate(R.menu.tagspage_menu, menu);
        			mIsShowSubmitMenu = false;
        		}
        	}
        	else {
        		if(JJBaseApplication.g_customer.mMemo == null || JJBaseApplication.g_customer.mMemo.length() == 0){
        			inflater.inflate(R.menu.tagspage_menu, menu);
        			mIsShowSubmitMenu = false;
        		}
        		else {
        			inflater.inflate(R.menu.submit_menu, menu); 
        			mIsShowSubmitMenu = true;
        		}
        	}
        }
        else {
        	inflater.inflate(R.menu.tagspage_menu, menu); 
        	mIsShowSubmitMenu = false;
        }
        
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
			int state   = ((Integer)param[1]).intValue();
			if(response.toLowerCase().equals(SOPBluetoothService.STATE_FAILED.toLowerCase())){
				((JJBaseApplication)getApplication()).showMessage(getResources().getString(R.string.failed_read));	
				dismissProcessDialog();
				return;
			}
			else {
				if(D2EConfigures.G_DEBUG){
					mTagNum = "a2f0b888";
				}
				else {
				    mTagNum = response.substring(14,22);
				}
				if(state == SOPBTCallHelper.TAG_LOCATION){
					JJBaseApplication.sop_location.mTagNum = mTagNum;
			        String[] params = new String[]{
			        		"FunType", "getSOPPointList", 
			        		"OrgID", JJBaseApplication.user_OrgID,
			        		"LocTagNum", mTagNum
	 		        		};
			        
			        long id = JJBaseService.addMutilpartHttpTask("http://www.ohpest.com/ohpest-pco/webservices/get/data.html", params, D2ESOPActivity.this.getClass().getName(),D2EConfigures.TASK_GETCHECKBYLOCATION);
			             		        
			        setCurTaskID(id);
			        return;
				}
				else if(state == SOPBTCallHelper.TAG_CHECKIN){
					//这里保存员工卡号信息，保存为一个列表
					JJBaseApplication.emploeeSignTageNums.add(mTagNum);
					//add by shawn 2012-9-7 Begin
					if(JJBaseApplication.pcoUsersMap.get(mTagNum)!=null){
						mCheckPcoUser=JJBaseApplication.pcoUsersMap.get(mTagNum);
						
						//这里要判断是否已经签到过了
						if(D2EConfigures.TEST){
							Log.e("!isContainUser(mCheckPcoUser)------------->", ""+(!isContainUser(mCheckPcoUser)));
						}
						if(!isContainUser(mCheckPcoUser)){
							btn_back.setVisibility(View.GONE);
							btn_submit.setVisibility(View.VISIBLE);
							mCheckUserList.add(mCheckPcoUser);
							mPcoUserAdapter.addObject(mCheckPcoUser);
							mPcoUserAdapter.notifyDataSetChanged();
						}
					}else{
						((JJBaseApplication)getApplication()).showMessage(getResources().getString(R.string.user_card_not_exist));
					}
					//End
				}
				else if(state == SOPBTCallHelper.TAG_GET_EMPLOEE_NUM){
					//modify by shawn 2012-9-12 Begin 
					//弹出对话框，选择继续还是替换
					if(mTagNum!=null&&mTagNum.length()>0){
						if(JJBaseApplication.isContainEmploeeTageNum(mTagNum)){
					   Builder myBuilder2=new Builder(D2ESOPActivity.this);
						myBuilder2.setNegativeButton(getResources().getString(R.string.doing_replace), new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
				            	LayoutInflater inflater = getLayoutInflater();
				                final View layout      = inflater.inflate(R.layout.d2e_changetag, null);
				                final Button btn       = (Button)layout.findViewById(R.id.button_send);
				                final Button btnyes    = (Button)layout.findViewById(R.id.btn_yes);
				                final Button btnclear  = (Button)layout.findViewById(R.id.btn_clear);
				                final Button btnretry  = (Button)layout.findViewById(R.id.btn_retry);
				                newtag  = (TextView)layout.findViewById(R.id.tag_new);
				                
				                name     = (EditText)layout.findViewById(R.id.location_name);
				                name.setText(mChangedItem.mTitle);
				                
				                newtag.setText("");
				                
				                
				        		Builder buildertmp = new Builder(D2ESOPActivity.this);  
				        		buildertmp.setIcon(android.R.drawable.ic_dialog_info);       		
				        		buildertmp.setTitle(getResources().getString(R.string.update_card));  	        		
				                buildertmp.setView(layout);
				        		final AlertDialog dialog2 = buildertmp.show();	
				        		
				                btn.setOnClickListener(new OnClickListener(){

									@Override
									public void onClick(View arg0) {
				                    	SOPBluetoothService.getService().doTask(SOPBTCallHelper.TAG_CHANGE, D2ESOPActivity.class.getName(), SOPBTCallHelper.getInitOrder(SOPBluetoothService.g_readerID));         
									}
				                	
				                });
				                      
				                btnretry.setOnClickListener(new OnClickListener(){

									@Override
									public void onClick(View arg0) {
				                    	SOPBluetoothService.getService().doTask(SOPBTCallHelper.TAG_CHANGE, D2ESOPActivity.class.getName(), SOPBTCallHelper.getInitOrder(SOPBluetoothService.g_readerID));         
									}
				                	
				                });
				                
				                btnclear.setOnClickListener(new OnClickListener(){

									@Override
									public void onClick(View arg0) {
										newtag.setText("");
									}
				                	
				                });
				        		

				                btnyes.setOnClickListener(new OnClickListener(){

									@Override
									public void onClick(View arg0) {
										if(name.getText().length() == 0 || name.getText() == null
												|| newtag.getText().length() == 0 || newtag.getText() == null){
											((JJBaseApplication)getApplication()).showMessage(getResources().getString(R.string.please_comlete_data));
											return;
										}
										
								        String[] params = new String[]{
								        		"FunType", "updataPosition", 
								        		"OrgID", JJBaseApplication.user_OrgID,
								        		"ReadNum", SOPBluetoothService.g_readerID,
								        		"TagNum",newtag.getText().toString().trim(),
								        		"PositionID",mChangedItem.getId(),
								        		"Title",name.getText().toString().trim()
						 		        		};
								        if(D2EConfigures.TEST){
								        	YTStringHelper.array2string(params);
								        }
								        long id = JJBaseService.addMutilpartHttpTask("http://www.ohpest.com/ohpest-pco/webservices/get/data.html", params, D2ESOPActivity.this.getClass().getName(),D2EConfigures.TASK_CHANGETAG);
								             		        
								        setCurTaskID(id);
								        showWaitDialog(getResources().getString(R.string.handling));		
								        dialog2.dismiss();
									}
				                	
				                });
							}
						});
						myBuilder2.setPositiveButton(getResources().getString(R.string.doing_continue), new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								if(mChangedItem.mItems != null && mChangedItem.mItems.size() > 0){
									JJBaseApplication.g_list.clear();
									for(Object ob : JJBaseApplication.sop_location.mItems){
										JJBaseApplication.g_list.add(ob);
									}
									Intent intent = new Intent(D2ESOPActivity.this, D2ECheckListActivity.class);
									intent.putExtra("custom", JJBaseApplication.g_customer.mName);
									intent.putExtra("position", mLocationName);
									intent.putExtra("PositionID", strSelectPositonID);
									intent.putExtra("locationID", mLocationID);
									intent.putExtra("customid", JJBaseApplication.g_customer.mId);
									D2ESOPActivity.this.startActivity(intent);
									overridePendingTransition(R.anim.fade, R.anim.hold);
									finish();		
								}else{
								
								
								String[] params=new String[]{
										"FunType", "getSOPPointListWithTask",
										"TaskID",JJBaseApplication.g_customer.mTaskId,
										"PositionID",strSelectPositonID
									};
									YTStringHelper.array2string(params);
									long id = JJBaseService.addMutilpartHttpTask("http://www.ohpest.com/ohpest-pco/webservices/get/data.html", params, D2ESOPActivity.this.getClass().getName(),D2EConfigures.TASK_GETCHECKBYLOCATION);
									setCurTaskID(id);
							        showWaitDialog(getResources().getString(R.string.handling_wait));	
								}
					                
							}
						});
						myBuilder2.show();
						}else{
							((JJBaseApplication)getApplication()).showMessage(getResources().getString(R.string.reader_user_card_failed));
						}
						
					//End
					}
				}
				else if(state == SOPBTCallHelper.TAG_CHANGE){
					
					
					if(mTagNum!=null&&mTagNum.length()>0){
						newtag.setText(mTagNum);
					}else{
						((JJBaseApplication)getApplication()).showMessage(getResources().getString(R.string.read_card_failed));
					}
				}
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
    	            	//这里如果签到失败则显示：签到非本公司员工
    	            	((JJBaseApplication)getApplication()).showMessage(err);
    	            	if(D2EConfigures.TEST){
    	            		Log.e("error------------->", err);
    	            		Log.e("error.equals(参数错误)------------->", ""+err.equalsIgnoreCase("参数错误"));
    	            	}
    	            	if(err.equalsIgnoreCase(getResources().getString(R.string.parameter_error))){
    	            		//参数错误把进度对话框关了
    	            		
    	            		//把签到成功修改为false
    	            		JJBaseApplication.isCheckedIn=false;
    						mCheckUserList.clear();
    						mPcoUserAdapter.clear();
    						mPcoUserAdapter.notifyDataSetChanged();
    						dismissWaitDialog();
    						dismissProcessDialog();
        	            	return;
    	            	}
    	            }                	
                }catch(Exception ex){}
		    }
		    catch(Exception ex){}
		    if(response == null){
		    	((JJBaseApplication)getApplication()).showMessage(getResources().getString(R.string.hand_failed_try_again));
		    	dismissProcessDialog();
		    	return;
		    }
		    if(task.getTaskId() == D2EConfigures.TASK_GETCHECKBYLOCATION){
		    	if(D2EConfigures.TEST){
		    		Log.e("TASK_GETCHECKBYLOCATION==================>", "TASK_GETCHECKBYLOCATION");
		    		Log.e("response != null && response.length() > 0", ""+(response != null && response.length() > 0));
		    	}
		    	
	            if(response != null && response.length() > 0){
	            	try{
	    				JSONArray ja = new JSONArray(response);	
	    				int len = ja.length();
	    				for(int i = 0; i < len; i ++){
	    					//在这里设置选择的位置点
	    					JJBaseApplication.selectPosition=JJBaseApplication.sop_location;
		    				
		    				
	    					String     tmp = ja.getString(i);
	    					JSONObject jo  = new JSONObject(tmp);   	
	    					
	    					
	    					TagItem item = new TagItem(jo.getString("Name"), jo.getString("TagID"));
	    					item.mIndex = jo.getString("ID");
	    					item.setmPointId(jo.getString("ID"));
	    					if(D2EConfigures.TEST){
	    						Log.e("设备名称为：", ""+(jo.getString("TypeName")));
	    					}
	    					item.setmEquipName(jo.getString("TypeName"));
	    					JJBaseApplication.sop_location.addItem(item);
	    					
	    				}
	    				//modify by shawn 2012-9-8
		            	//有数据返回的时候才进入下个页面
		            	//否则提示，获取数据失败
	    				
	    				//add by shawn 2012-10-18 Begin
	    				//这里保存获取到的作业点信息
	    				JJBaseApplication.selectPosition=JJBaseApplication.sop_location;
	    				//modify by shawn 2012-11-21 Begin 
	    				//从客户列表中取出客户，然后设置信息
	    				MyCustomerListData myCustomerListData=(MyCustomerListData) ytfileHelper.deSerialObject(getResources().getString(R.string.customer_list));
	    				myCustomerListData.getWeekSerialCustomer(JJBaseApplication.g_customer).setSelectPositon(JJBaseApplication.selectPosition);
	    				ytfileHelper.serialObject(getResources().getString(R.string.customer_list), myCustomerListData);
	    				//End
		            	Intent intent = new Intent(D2ESOPActivity.this, D2ECheckListActivity.class);
						intent.putExtra("custom", JJBaseApplication.g_customer.mName);
						intent.putExtra("position", mLocationName);
						intent.putExtra("PositionID", strSelectPositonID);
						intent.putExtra("locationID", mLocationID);
						intent.putExtra("customid", JJBaseApplication.g_customer.mId);
						D2ESOPActivity.this.startActivity(intent);
						overridePendingTransition(R.anim.fade, R.anim.hold);
						finish();	
	    			}
	    			catch(Exception ex){
	    				ex.printStackTrace();
	    			}
	            	
	            }else{
	            	((JJBaseApplication)getApplication()).showMessage(getResources().getString(R.string.get_data_failed_try_again));	
	            }
	            
		    }
			else if(task.getTaskId() == D2EConfigures.TASK_SUBMIT){
				if(response != null && response.length() > 0){
				try{
					if(D2EConfigures.TEST){
						Log.e("xxxxxxxxxxxxx", "task_submit--------in");
						Log.e("jo==============》", "task_submit--------in");
					}
					JSONObject jo  = new JSONObject(response);
					String success = jo.getString("success");
					((JJBaseApplication)getApplication()).showMessage(success);
					if(D2EConfigures.TEST){
						Log.e("joxxxxxxxxxxxx", ""+jo);
						Log.e("success==============》", ""+success);
					}
					mSubmit = true;
					mIsShowSubmitMenu = false;
					mIsCheckedIn=false;
					//add by shawn 2012-9-10 Begin
					//签到成功，列表数据要清清空下，并且把签到标志改为false
					JJBaseApplication.isCheckedIn=false;
					mCheckUserList.clear();
					//Modify By Shawn 2012-8-30
					//工单完成之后默认跳转到消息界面
					if(D2EConfigures.TEST){
						Log.e("success!=null&&success.length()>0========>", ""+(success!=null&&success.length()>0));
					}
					if(success!=null&&success.length()>0){
						JJBaseApplication.g_customer=null;
						MyCustomerListData myCustomerListData=(MyCustomerListData) ytfileHelper.deSerialObject(getResources().getString(R.string.customer_list));
						myCustomerListData.deleteCustomer(JJBaseApplication.g_customer);
						ytfileHelper.serialObject(getResources().getString(R.string.customer_list), myCustomerListData);
						Intent intent=new Intent(D2ESOPActivity.this,D2EMainScreen.class);
						D2ESOPActivity.this.startActivity(intent);
						D2ESOPActivity.this.finish();
					}
				}
				catch(Exception ex){
					JSONObject jo;
					try {
						
						jo = new JSONObject(response);
						String error ;
						if(jo.has("error")){
							if(jo.getString("error")!=null&&jo.getString("error").length()>0){
								error= jo.getString("error");
								
								if(D2EConfigures.TEST){
									Log.e("error----------->", ""+error);
								}
							}
						}
						Intent intent=new Intent(D2ESOPActivity.this,D2EMainScreen.class);
						D2ESOPActivity.this.startActivity(intent);
						D2ESOPActivity.this.finish();
						dismissWaitDialog();
					} catch (JSONException e) {
						e.printStackTrace();
					}
//					
					dismissWaitDialog();
				}
			 }else{
	            	((JJBaseApplication)getApplication()).showMessage(getResources().getString(R.string.get_data_failed_try_again));	
	         }
			}
			else if(task.getTaskId() == D2EConfigures.TASK_CHECKIN){
				JSONObject jo=null;
				try{
					jo  = new JSONObject(response);
					String success = jo.getString("success");
					//modify by shawn 2012-9-3
					//如果签到成功，才能点击位置标签
					if(success!=null&&success.length()>0){
						mIsCheckedIn = true;
						//只有返回成功才算签到成功
						mCheckPcoUser.mChecked=true;
						//通知界面
						mPcoUserAdapter.notifyDataSetChanged();
						JJBaseApplication.isCheckedIn=true;
						((JJBaseApplication)getApplication()).showMessage(success);
						btn_check_in.setVisibility(View.GONE);
						//add by shawn 2012-9-8 Begin
						//签到成功过了，就要把数据给清了
						
						//End
					}else{
						dismissProcessDialog();
						return ;
					}
					
					
					
					
					
					if(strMyError!=null&&strMyError.length()>0){
						//没有任何位置标签信息，也让提交，提交空数据
//						if(strMyError.equalsIgnoreCase(getResources().getString(R.string.no_location_tag))){
							LayoutInflater inflater = getLayoutInflater();
			                final View layout = inflater.inflate(R.layout.d2e_report_memo, null);  
			                final YTEditText et = (YTEditText)layout.findViewById(R.id.report_memo);
			                final RelativeLayout comment_panel_choseclient=(RelativeLayout) layout.findViewById(R.id.comment_panel_choseclient);
			                comment_panel_choseclient.setVisibility(View.GONE);
			                Builder buildertmp = new Builder(D2ESOPActivity.this);  
			        		buildertmp.setIcon(android.R.drawable.ic_dialog_info);  
			        		buildertmp.setTitle(getResources().getString(R.string.excution_feedback));  
			        		buildertmp.setPositiveButton(getResources().getString(R.string.dialog_btn_yes_text), new DialogInterface.OnClickListener(){  
			                    public void onClick(DialogInterface dialog,int which){  
			        		        showWaitDialog(getResources().getString(R.string.handling));	
			        		        
			        		        StringBuffer sb = new StringBuffer();
			        		        
			        		        //add by shawn 2012-9-8 Begin
			        		        //但获取到的作业点不存在或删除的时候，根据工单的类型不同插入不同的空报表
			        		        if(D2EConfigures.TEST){
			        		        	Log.e("D2EConfigures.g_customer.mTaskType--------->", ""+JJBaseApplication.g_customer.mTaskType);
			        		        }
			        		        
//			        		        //1、表示正常服务,4、表示额外服务，都是作业点的报表
			        	        	if(JJBaseApplication.g_customer.mTaskType==1||JJBaseApplication.g_customer.mTaskType==4){
			        		        sb.append("[");
			        		        sb.append("{\"").append("SOPPositionID").append("\":").append("\"").append("-").append("\",");
        		        			sb.append("\"").append("Type").append("\":").append("\"").append("-").append("\",");        		        			
        		        			sb.append("\"").append("SOPPointID").append("\":").append("\"").append("-").append("\",");
        		        			sb.append("\"").append("State").append("\":").append("\"").append("-").append("\",");
        		        			sb.append("\"").append("Memo").append("\":").append("\"").append("-").append("\"}");
			        		        sb.append("]");
			        	        	}
			        	        	//2、表示监测部署，3、表示监测查验，都是监测点的报表
			        	        	else{
			        	        		sb.append("[");
				        		        sb.append("{\"").append("SOPPositionID").append("\":").append("\"").append("-").append("\",");
		    		        			sb.append("\"").append("SOPPointID").append("\":").append("\"").append("-").append("\",");
	        		        			sb.append("\"").append("Type").append("\":").append("\"").append("-").append("\",");        		        			
		    		        			
		    		         			sb.append("\"").append("MonitorType").append("\":").append("\"").append("-").append("\",");
		    		         			sb.append("\"").append("Name").append("\":").append("\"").append("-").append("\",");
		    		         			sb.append("\"").append("Facilities").append("\":").append("\"").append("-").append("\",");
		    		         			sb.append("\"").append("Mark").append("\":").append("\"").append("1").append("\"");
				        		        
				        		        sb.append("]");
			        	        	}
			        	        	//End
			        	        	
			        		        String[] params = new String[]{
			        		        		"FunType", "insertSOPReport", 
			        		        		"TaskID", JJBaseApplication.g_customer.mTaskId,
			        		        		"Memo", et.getText().toString(),
			        		        		"Data", sb.toString()};
			        		        //"TaskID"
			        		        
			        		        if(D2EConfigures.TEST){
			        		        	YTStringHelper.array2string(params);
			        		        }
			        		        long ids = JJBaseService.addMutilpartHttpTask("http://www.ohpest.com/ohpest-pco/webservices/get/data.html", params, D2ESOPActivity.this.getClass().getName(),D2EConfigures.TASK_SUBMIT);
			        		        
			        		        
			        		        setCurTaskID(ids);           
			                    }
			        		});
			        		buildertmp.setView(layout);
			        		buildertmp.show();
						}
				}
				catch(Exception ex){
					//modify by shawn 2012-9-3 
					//签到非本公司员工，则提示签到失败
					String error=null;
					try {
						if(jo.getString("error")!=null&&jo.getString("error").length()>0){
							error=jo.getString("error");
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(error!=null&&error.length()>0){
						Toast.makeText(D2ESOPActivity.this, getResources().getString(R.string.check_in_failed)+error, Toast.LENGTH_LONG).show();
						mIsCheckedIn=false;
					}
					
				}
				
			}
		    else if(task.getTaskId() == D2EConfigures.TASK_GETWORKSHEET){
		    	//{"CountTask":["1"],"BeginTime":["120611"]} 
		    	try{
		    		JJBaseApplication.g_worksheet.clear();
		    		JSONObject res = new JSONObject(response);
		    		
		    		if(res.getString("Assignment") == null || res.getString("Assignment").toLowerCase().equals("null")){
		    			((JJBaseApplication)getApplication()).showMessage(getResources().getString(R.string.no_weeksheet_data));
		    			dismissProcessDialog();
		    			return;
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
		    		
	    			Intent intent = new Intent(D2ESOPActivity.this, D2EWorksheetActivity.class);
	    			intent.putExtra("type", D2EWorksheetActivity.TYPE_WORKWHEET);
	    			D2ESOPActivity.this.startActivity(intent);		
	    			overridePendingTransition(R.anim.fade, R.anim.hold);
	    			finish();
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
				Intent intent = new Intent(D2ESOPActivity.this, D2EReportActivity.class);
				D2ESOPActivity.this.startActivity(intent);		
				overridePendingTransition(R.anim.fade, R.anim.hold);
				finish();			
		    }
		    else if(task.getTaskId() == D2EConfigures.TASK_CHANGETAG){
                try{
    	            JSONObject j     = new JSONObject(response);
    	            if(D2EConfigures.TEST){
    	            	Log.e("Task_changeEtag-=============>", "TASK_CHANGETAG");
    	            	Log.e("response------------>", ""+response);
    	            	Log.e("response---------->", ""+j);
    	            }
    	            String success = j.getString("success");
    	            if(success != null && success.length() > 0){
    	            	((JJBaseApplication)getApplication()).showMessage(success);
    	            	dismissProcessDialog();
    	            	return;
    	            }                	
                }catch(Exception ex){}
		    }
		    else if(task.getTaskId() == D2EConfigures.TASK_CHECKCODE){
				//数据处理
				try{	
	            	LayoutInflater inflater = getLayoutInflater();
	                final View layout      = inflater.inflate(R.layout.d2e_changetag, null);
	                final Button btn       = (Button)layout.findViewById(R.id.button_send);
	                final Button btnyes    = (Button)layout.findViewById(R.id.btn_yes);
	                final Button btnclear  = (Button)layout.findViewById(R.id.btn_clear);
	                final Button btnretry  = (Button)layout.findViewById(R.id.btn_retry);
	                final EditText te      = (EditText)layout.findViewById(R.id.tag_new);
	                
	                final TextView nub     = (TextView)layout.findViewById(R.id.location_id);
	                nub.setText(mChangedItem.mTagNum);
	                
	                final EditText name     = (EditText)layout.findViewById(R.id.location_name);
	                name.setText(mChangedItem.mTitle);
	                
	                
	        		Builder buildertmp = new Builder(D2ESOPActivity.this);  
	        		buildertmp.setIcon(android.R.drawable.ic_dialog_info);       		
	        		buildertmp.setTitle(getResources().getString(R.string.update_card));  	        		
	                buildertmp.setView(layout);
	        		final AlertDialog dialog = buildertmp.show();	
	        		
	                btn.setOnClickListener(new OnClickListener(){

						@Override
						public void onClick(View arg0) {
	                    	showProcessDialog(getResources().getString(R.string.reading_card));
	                    	SOPBluetoothService.getService().doTask(SOPBTCallHelper.TAG_CHANGE, D2ESOPActivity.class.getName(), SOPBTCallHelper.getInitOrder(SOPBluetoothService.g_readerID));         
	                    	dialog.dismiss();
						}
	                	
	                });
	                      
	                btnretry.setOnClickListener(new OnClickListener(){

						@Override
						public void onClick(View arg0) {
	                    	showProcessDialog(getResources().getString(R.string.reading_card));
	                    	SOPBluetoothService.getService().doTask(SOPBTCallHelper.TAG_CHANGE, D2ESOPActivity.class.getName(), SOPBTCallHelper.getInitOrder(SOPBluetoothService.g_readerID));         
	                    	dialog.dismiss();
						}
	                	
	                });
	                
	                btnclear.setOnClickListener(new OnClickListener(){

						@Override
						public void onClick(View arg0) {
							te.setText("");
						}
	                	
	                });
	        		

	                btnyes.setOnClickListener(new OnClickListener(){

						@Override
						public void onClick(View arg0) {
							((JJBaseApplication)getApplication()).showMessage(getResources().getString(R.string.read_a_white_card));
							mChangedItem.setTagNum(nub.getText().toString());
							mTagsAdapter.notifyDataSetChanged();							
						}
	                	
	                });

				}
				catch(Exception ex){
					ex.printStackTrace();
				}
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
					Intent intent = new Intent(D2ESOPActivity.this, D2EHelpActivity.class);
					D2ESOPActivity.this.startActivity(intent);		
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
	public boolean isExit() {
		return false;
	}

	@Override
	public void backAciton() {
	}

	/**
	 * 是否包含已经签到的员工
	 * @param pcoUser
	 * @return
	 */
	private boolean isContainUser(PcoUser pcoUser){
		int m=mCheckUserList.size();
		for(int i=0;i<m;i++){
			String exit=mCheckUserList.get(i).getStrNumbers();
			String newU=pcoUser.getStrNumbers();
			if(D2EConfigures.TEST){
				Log.e("exit--------->", ""+exit);
				Log.e("newU--------->", ""+newU);
				Log.e("exit.equalsIgnoreCase(newU)--------->", ""+(exit.equalsIgnoreCase(newU)));
			}
			if(exit.equalsIgnoreCase(newU)){
				return true;
			}
		}
		return false;
	}
	
	private boolean isDoneAll(){
		for(TagItem item : JJBaseApplication.g_soplist){
			if(!item.isChecked()){
				return false;
			}
		}
		return true;
	}


}
