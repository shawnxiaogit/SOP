package com.mobilercn.sop.activity;

import java.io.InputStream;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.view.LayoutInflater;

import com.mobilercn.base.BadgeView;
import com.mobilercn.base.JJBaseActivity;
import com.mobilercn.base.JJBaseApplication;
import com.mobilercn.base.JJBaseService;
import com.mobilercn.sop.R;
import com.mobilercn.config.D2EConfigures;
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

import android.app.Dialog;

public class D2ENewCheckPointActivity extends JJBaseActivity{
	
	private static final int  OP_DEL     = 0;
	private static final int  OP_NORMAL  = 2;
	
	private int                mOPType = OP_NORMAL;
	
	private TagsAdapter        mTagsAdapter;
	private boolean            mDelete  = false;
	private TagItem            mDelItem = null;
	
	private TextView te;
	private EditText name;
	private EditText memo;
	
	private int mType=0;
	private String locationID;
	private String mCustomID;
	private String mLocationID;
	private AutoCompleteTextView text;
	private Button btn_retry;
	private boolean isExit=false;
	private BadgeView mBadgeView;
	/**
	 * 其他设施
	 */
	private int mOtherFacilities=12;
	/**
	 * 其他实施
	 */
	private int mOtherOpsite=13;
	
	
	
    protected void onResume() {
    	super.onResume();
    }

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.d2e_entercheck);
		Window window = getWindow();
		window.setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.d2e_titlebar);
		TextView tv = (TextView) findViewById(R.id.title);
		tv.setText(getResources().getString(R.string.help_init));
		
		
		mBadgeView = (BadgeView) findViewById(R.id.countTask);
		mBadgeView.setBadgePosition(BadgeView.POSITION_CENTER);
		mBadgeView.setBackgroundResource(R.drawable.badge_ifaux);
		mBadgeView.setVisibility(View.GONE);
		if (JJBaseApplication.user_CountTask != null
				&& JJBaseApplication.user_CountTask.length() > 0) {
			mBadgeView.setText(JJBaseApplication.user_CountTask);
		}
		
		
		te = (TextView)this.findViewById(R.id.location_id);
		name = (EditText)this.findViewById(R.id.location_name);
		memo = (EditText)this.findViewById(R.id.location_memo);
		ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.equips, android.R.layout.simple_dropdown_item_1line);
		text =(AutoCompleteTextView) this.findViewById(R.id.ckpoint_state);
        text.setAdapter(adapter);
        text.setOnItemClickListener(new OnItemClickListener() {
        	@Override
            public void onItemClick(AdapterView<?> parent, View view,
                            int position, long id) {
            	mType = position;
            	//add by shawn 2012-10-14 Begin
            	//这里新增判断是否是其他，是其他则弹出一个输入框，让用户输入其他类型
            	//12为其他设施，13为其他实施
            	//如果是其他设施
            	if(mType==mOtherFacilities){
            		createDialog();
            		
            		
            	}
            	//如果是其他实施
            	if(mType==mOtherOpsite){
            		createDialog();
            	}
            	//End
            }
        });
        isExit=false;
        //grid
        mTagsAdapter=new TagsAdapter(getApplicationContext());
        Button btn1 = (Button)this.findViewById(R.id.loginImageButton02);
        btn1.setOnClickListener(new OnClickListener(){
			public void onClick(View arg0) {
				if(text.getText()!=null&&text.getText().length()>0){
					text.setText("");
					text.showDropDown();
					text.setThreshold(1000);
				}else{
					text.showDropDown();
					text.setThreshold(1000);
				}
			}        	
        });    
        Intent intent=getIntent();
        locationID=intent.getExtras().getString("locationID");
        mCustomID=intent.getExtras().getString("mCustomID");
        mLocationID=intent.getExtras().getString("mLocationID");
        te.setText(locationID);
		this.setTitle(getResources().getString(R.string.new_check_point));  	        		
		//确定提交
		Button btn_yes = (Button)this.findViewById(R.id.btn_yes);
		btn_yes.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				isExit=true;
				String strMyType= getResources().getStringArray(R.array.equips_type)[mType];
				//把结果传给D2EAddCheckActivity
				if(SOPBluetoothService.g_readerID!=null&&SOPBluetoothService.g_readerID.length()>0&&
						te.getText().toString()!=null&&te.getText().toString().length()>0&&
						name.getText().toString()!=null&&name.getText().toString().length()>0&&
								text.getText()!=null&&text.getText().length()>1){
					Intent intent=new Intent();
					Bundle bundle=new Bundle();
					bundle.putString("ReadNum", SOPBluetoothService.g_readerID);
					bundle.putString("TagNum", te.getText().toString());
					bundle.putString("Title", name.getText().toString());
					//add by shawn  2012-10-15
					//把其他设施，实施的名称也传递过去
					bundle.putString("TypeName", text.getText().toString());
					//End
					bundle.putString("Type", getResources().getStringArray(R.array.equips_type)[mType]);
					bundle.putString("Memo", memo.getText().toString());
					intent.putExtras(bundle);
					setResult(RESULT_OK,intent);
					finish();
				}else{
					((JJBaseApplication)getApplication()).showMessage(getResources().getString(R.string.please_comlete_data));
					return ;
				}
			}
		});

		//重置
		Button btn_clear = (Button)this.findViewById(R.id.btn_clear);
		btn_clear.setOnClickListener(new OnClickListener(){
			public void onClick(View arg0) {
            	text.setText("无");
            	name.setText("");
            	memo.setText("");
			}
		});
		
		btn_retry = (Button)this.findViewById(R.id.btn_retry);
		btn_retry.setOnClickListener(new OnClickListener(){
			public void onClick(View arg0) {
				D2ENewCheckPointActivity.this.finish();
            	showProcessDialog(getResources().getString(R.string.reading_card));
            	SOPBluetoothService.getService().doTask(SOPBTCallHelper.READ_TAG, D2EAddCheckActivity.class.getName(), SOPBTCallHelper.getInitOrder(SOPBluetoothService.g_readerID));
			}
		});
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
		int values = ((Integer)param[0]).intValue();
		if(values == JJBaseService.BT_SERVICE_INT){
			int state   = ((Integer)param[1]).intValue();
			String response = (String)param[2];
			if(response.toLowerCase().equals(SOPBluetoothService.STATE_FAILED.toLowerCase())){
				((JJBaseApplication)getApplication()).showMessage(getResources().getString(R.string.bterror));	
				dismissProcessDialog();
				return;
			}
			else {
				//success
				String locationID = response.substring(14,22);
				if(D2EConfigures.G_DEBUG){
					locationID = "93edb887";//a2f0b888:作业点  监测点:93edb887
				}

				if(state == SOPBTCallHelper.TAG_RETURN){
			        					
					long id = -99;
					if(mOPType == OP_DEL){
				        String[] params = new String[]{
				        		"FunType", "delPoint", 
				        		"OrgID", JJBaseApplication.user_OrgID, 
				        		"ReadNum",SOPBluetoothService.g_readerID,
				        		"TagNum", locationID};
				        
				        
				        id = JJBaseService.addMutilpartHttpTask("http://www.ohpest.com/ohpest-pco/webservices/get/data.html", params, D2ENewCheckPointActivity.this.getClass().getName(),D2EConfigures.TASK_DELITEM);

					}
					else {
				        
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

				        id = JJBaseService.addMutilpartHttpTask("http://www.ohpest.com/ohpest-pco/webservices/get/data.html", params, D2ENewCheckPointActivity.this.getClass().getName(),D2EConfigures.TASK_GET_CUSTOM_INFO);
						
					}
			        setCurTaskID(id);
			        return;
				}
				else {
					dismissProcessDialog();
					if(mOPType == OP_DEL){
				        String[] params = new String[]{
				        		"FunType", "delPoint", 
				        		"OrgID", JJBaseApplication.user_OrgID, 
				        		"ReadNum",SOPBluetoothService.g_readerID,
				        		"TagNum", locationID};
				        
				        long id = JJBaseService.addMutilpartHttpTask("http://www.ohpest.com/ohpest-pco/webservices/get/data.html", params, D2ENewCheckPointActivity.this.getClass().getName(),D2EConfigures.TASK_DELITEM);
				        setCurTaskID(id);
				        return;
					}
					//因为还会创建一个窗口，所以把原来的杀了
					D2ENewCheckPointActivity.this.finish();
					//新增作业点标签，输入的内容太多，换成一个界面来输入信息
					Intent intent_newcheckpoint=new Intent(D2ENewCheckPointActivity.this,D2ENewCheckPointActivity.class);
					intent_newcheckpoint.putExtra("locationID", locationID);
					intent_newcheckpoint.putExtra("mCustomID", mCustomID);
					intent_newcheckpoint.putExtra("mLocationID", mLocationID);
					D2ENewCheckPointActivity.this.startActivity(intent_newcheckpoint);
					
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
                if(D2EConfigures.G_DEBUG){
                    Log.e("response: >>>>>> ", response);                	
                }
                try{
                	JSONObject j     = new JSONObject(response);
                	String id=j.getString("ID");//如果有权限使用标签，则提示新增成功
    	            if(id!=null){
        		        ((JJBaseApplication)getApplication()).showMessage(getResources().getString(R.string.add_point_success));
        		        D2ENewCheckPointActivity.this.finish();
        		        mTagsAdapter.notifyDataSetChanged();
    	            }
                }catch(Exception e){}
                	
               
                try{
                	JSONObject j     = new JSONObject(response);
    	            String err = j.getString("error");
    	            if(err != null && err.length() > 0){
    	            	((JJBaseApplication)getApplication()).showMessage(err);
    	            	D2ENewCheckPointActivity.this.finish();
    	            	return;
    	            }
    	            
                }catch(Exception ex){
                	ex.printStackTrace();
                }
		    }
		    catch(Exception ex){
		    	ex.printStackTrace();
		    }
		    
		    if(response == null || response.length() == 0){
		    	((JJBaseApplication)getApplication()).showMessage(getResources().getString(R.string.hand_failed_try_again));
		    	dismissProcessDialog();
		    	return;
		    }
		    if(task.getTaskId() == D2EConfigures.TASK_ADDCHECK){
                try{
    	            JSONObject j     = new JSONObject(response);
    	            mTagsAdapter.insertObject(j.getString("Name"),j.getString("ID"), "");
    	            mTagsAdapter.notifyDataSetChanged();
                }catch(Exception ex){}
		    }
		    else if(task.getTaskId() == D2EConfigures.TASK_GET_CUSTOM_INFO){
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
	    					
	    					TagItem item =new TagItem(jo.getString("Name"), jo.getString("ID"));
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
						intent.setClass(D2ENewCheckPointActivity.this, D2ESOPActivity.class);
						D2ENewCheckPointActivity.this.startActivity(intent);
						overridePendingTransition(R.anim.fade, R.anim.hold);
						finish();
					}else{
						((JJBaseApplication) getApplication())
						.showMessage(getString(R.string.no_weeksheet_data));
					}
	            }
		    }		  
		    else if(task.getTaskId() == D2EConfigures.TASK_DELITEM){
				mDelete = false;
        		mTagsAdapter.removeItem(mDelItem);	 
        		mTagsAdapter.notifyDataSetChanged();
        		JJBaseApplication.g_grid_type = D2EConfigures.GRID_CHECK;
				mOPType = OP_NORMAL;
        		
                try{
    	            JSONObject j     = new JSONObject(response);
    	            String err = j.getString("success");
    	            if(err != null && err.length() > 0){
    	            	((JJBaseApplication)getApplication()).showMessage(err);
    	            }                	
                }catch(Exception ex){}

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

	/**
	 * 创建其他设施、实施对话框
	 */
	private void createDialog(){
		LayoutInflater inflater=getLayoutInflater();
    	View dialog_view=inflater.inflate(R.layout.dialog2, null);
    	final EditText et_dialog2=(EditText) dialog_view.findViewById(R.id.et_dialog2);
    	final Button btn_dialog2_yes=(Button) dialog_view.findViewById(R.id.btn_dialog2_yes);
    	final Button btn_dialog2_no=(Button) dialog_view.findViewById(R.id.btn_dialog2_no);
		final Dialog otherFaciDialog=new Dialog(D2ENewCheckPointActivity.this,R.style.my_other_point_dialog);
		otherFaciDialog.setContentView(dialog_view);
		otherFaciDialog.getWindow().setLayout(300, 150);
		otherFaciDialog.show();
		btn_dialog2_yes.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				text.setText(et_dialog2.getText().toString());
				otherFaciDialog.dismiss();
				
			}
		});
		btn_dialog2_no.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				text.setText("");
				otherFaciDialog.dismiss();				
			}
		});
	}
	
	@Override
	public void taskProcessing(Object... param) {
	}
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		//判断返回按钮是否被按下
		if (keyCode == KeyEvent.KEYCODE_BACK&&event.getAction()==KeyEvent.ACTION_DOWN) {
			//首先判断是否完成新增作业点标签，即确定按钮是否被按下或则主动取消给个标记,并提示"新增未完成，在点一次返回退出"
			if(!isExit){
				Toast.makeText(D2ENewCheckPointActivity.this, getResources().getString(R.string.add_is_not_finish), Toast.LENGTH_LONG).show();
				isExit=true;
			}else{
				//当在点一次的时候退出
				D2ENewCheckPointActivity.this.finish();
			}
		}
		return true;
	}
}
