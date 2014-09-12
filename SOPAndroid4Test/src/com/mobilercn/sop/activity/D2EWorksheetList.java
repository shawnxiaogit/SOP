package com.mobilercn.sop.activity;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mobilercn.base.BadgeView;
import com.mobilercn.base.JJBaseActivity;
import com.mobilercn.base.JJBaseApplication;
import com.mobilercn.base.JJBaseService;
import com.mobilercn.config.D2EConfigures;

import com.mobilercn.sop.R;
import com.mobilercn.sop.bt.SOPBluetoothService;
import com.mobilercn.task.JJTask;
import com.mobilercn.util.JJNetHelper;
import com.mobilercn.util.YTStringHelper;
import com.mobilercn.widget.PullToRefreshListView;
import com.mobilercn.widget.PullToRefreshListView.OnRefreshListener;

public class D2EWorksheetList extends JJBaseActivity {
	
	private String mid;
	
	public class WorkSheetItem {
		private boolean mChecked;
		private String mGongShi;
		private String  mDate;
		private String  mAddress;
		private String  mContent;
		
		private String mId;
		
		public WorkSheetItem(String date, String gongshi,String content, String address){
			mDate    = date;
			mGongShi=gongshi;
			mAddress = address;
			mContent = content;
		}
		
		
		
		
		public String getmId() {
			return mId;
		}




		public void setmId(String mId) {
			this.mId = mId;
		}




		public String getmGongShi() {
			return mGongShi;
		}


		public String getContent(){
			return mContent;
		}
		
		public String getDate(){
			return mDate;
		}

		public String getAddress(){
			return mAddress;
		}

		
		public boolean isChecked(){
			return mChecked;
		}
		
		public void setChecked(boolean ck){
			mChecked = ck;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result
					+ ((mAddress == null) ? 0 : mAddress.hashCode());
			result = prime * result + (mChecked ? 1231 : 1237);
			result = prime * result
					+ ((mContent == null) ? 0 : mContent.hashCode());
			result = prime * result + ((mDate == null) ? 0 : mDate.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			WorkSheetItem other = (WorkSheetItem) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (mAddress == null) {
				if (other.mAddress != null)
					return false;
			} else if (!mAddress.equals(other.mAddress))
				return false;
			if (mChecked != other.mChecked)
				return false;
			if (mContent == null) {
				if (other.mContent != null)
					return false;
			} else if (!mContent.equals(other.mContent))
				return false;
			if (mDate == null) {
				if (other.mDate != null)
					return false;
			} else if (!mDate.equals(other.mDate))
				return false;
			return true;
		}

		private D2EWorksheetList getOuterType() {
			return D2EWorksheetList.this;
		}
		
	}
	
	private class D2EWorkSheetAdapter extends BaseAdapter {
		
		private Context                mContext;
		private ArrayList<WorkSheetItem> mMessages;
		
		public boolean isConatain(WorkSheetItem item){
			boolean result=false;
			for(D2EWorksheetList.WorkSheetItem itemExist:mMessages){
				if(itemExist.getmId().equalsIgnoreCase(item.getmId())){
					result=true;
				}
			}
			if(D2EConfigures.TEST){
				Log.e("result----------->", ""+result);
			}
			
			return result;
		}
		
		public D2EWorkSheetAdapter(Context context){
			mContext  = context;
			mMessages = new ArrayList<WorkSheetItem>();
		}

		public void addObject(WorkSheetItem item){
			mMessages.add(item);
		}
		
		@Override
		public int getCount() {
			return mMessages.size();
		}

		@Override
		public Object getItem(int position) {
			if(position < 0 || position > getCount()){
				return null;
			}
			return mMessages.get(position);
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			final ViewHolder holder;
			
			if(convertView == null){
				convertView = LayoutInflater.from(mContext).inflate(R.layout.d2e_worksheet_list_item, parent, false);
				holder = new ViewHolder();
				holder.textCustomer = (TextView)convertView.findViewById(R.id.worksheet_name);
				holder.textDate = (TextView)convertView.findViewById(R.id.worksheet_date);
				holder.worksheet_hour=(TextView)convertView.findViewById(R.id.worksheet_hour);
				holder.checkbox = (CheckBox)convertView.findViewById(R.id.worksheet_ckb);
				holder.textView  = (TextView)convertView.findViewById(R.id.worksheet_text);
				holder.textViewContent  = (TextView)convertView.findViewById(R.id.worksheet_content);
				holder.textViewAddress  = (TextView)convertView.findViewById(R.id.worksheet_address);
				
				convertView.setTag(holder);
			}
			else {
				holder = (ViewHolder)convertView.getTag();
			}
			final WorkSheetItem item = (WorkSheetItem)mMessages.get(position);
			holder.textView.setText(item.getDate());
			holder.textCustomer.setText(JJBaseApplication.g_selecteCustomer != null ? JJBaseApplication.g_selecteCustomer : "");
			
			String str = "";
			if(JJBaseApplication.g_selecteDate != null){
				try{
					SimpleDateFormat dates = new SimpleDateFormat("yyyyMMdd");
					Date tmp = dates.parse(JJBaseApplication.g_selecteDate);
					SimpleDateFormat dates1 = new SimpleDateFormat("yyyy.MM.dd");
					str = dates1.format(tmp);					
				}
				catch(Exception ex){}
			}
			
			holder.textDate.setText(str);
			holder.worksheet_hour.setText(item.getmGongShi());
			holder.textView.setSelected(true);
			holder.textViewContent.setText(item.getContent());
			holder.textViewAddress.setText(item.getAddress());
			holder.textViewAddress.setSelected(true);
			holder.checkbox.setChecked(item.isChecked());
			holder.checkbox.setOnClickListener(new View.OnClickListener() {  
	              
	            @Override  
	            public void onClick(View v) {  
	            	item.setChecked(holder.checkbox.isChecked());  
	                  
	            }  
	        });  
			
			return convertView;
		}
		
	    class ViewHolder{
			CheckBox  checkbox;
			TextView  textView;
			TextView worksheet_hour;
			TextView  textViewContent;
			TextView  textViewAddress;
			TextView  textCustomer;
			TextView  textDate;
			
		}
		
	} 

	
	private static final int SEND_SMS = 1;
	
	private BadgeView           mBadgeView;
	private D2EWorkSheetAdapter mListAdapter;
	
	private AdapterView.OnItemClickListener mListOnItemClick = new AdapterView.OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			WorkSheetItem item = (WorkSheetItem)mListAdapter.getItem(arg2 - 1);
			item.setChecked(!item.mChecked);
		}
		
	};
	
	ListView mList;
	
    protected void onResume() {
    	super.onResume();
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.d2e_worksheet_list_page);
        Window window = getWindow();
        window.setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.d2e_titlebar);
        
        mBadgeView = (BadgeView)findViewById(R.id.countTask);
        mBadgeView.setBadgePosition(BadgeView.POSITION_CENTER);
        mBadgeView.setBackgroundResource(R.drawable.badge_ifaux);
        mBadgeView.setVisibility(View.VISIBLE);
        
        Intent intent=getIntent();
        mid=intent.getStringExtra("selectId");
        if(D2EConfigures.TEST){
        	if(mid!=null&&mid.length()>0){
        		Log.e("mid---------------->", ""+mid);
        	}
        }
        
        if(JJBaseApplication.g_selecteDate != null && JJBaseApplication.g_selecteDate.length() > 0){
        	String selectDate=JJBaseApplication.g_selecteDate.substring(2, 8);
        	Log.e("selectDate------------>", selectDate);
        	Log.e("D2EConfigures.isD2EConfiguresG_worksheetcontainsDate(selectDate)------------->", ""+JJBaseApplication.isD2EConfiguresG_worksheetcontainsDate(selectDate));
        	if(JJBaseApplication.isD2EConfiguresG_worksheetcontainsDate(selectDate)){
            	mBadgeView.setText(JJBaseApplication.g_worksheet.get(selectDate));        		
        	}
        }

        
        TextView tv = (TextView)findViewById(R.id.title);
        if(JJBaseApplication.g_selecteCustomer != null && JJBaseApplication.g_selecteCustomer.length() > 0){
        	StringBuffer sb = new StringBuffer(JJBaseApplication.g_selecteMonth);
        	
        	if(JJBaseApplication.g_weather.containsKey(JJBaseApplication.g_selecteDate)){
        		sb.append(" ");
        		sb.append(getResources().getString(R.string.weather)).append(JJBaseApplication.g_weather.get(JJBaseApplication.g_selecteDate));
        	}
        	
            tv.setText(sb.toString());      
            tv.setTextSize(16.0f);
        }
        else {
            tv.setText(getResources().getString(R.string.menu_worksheet));
        }
        
        //
        mListAdapter = new D2EWorkSheetAdapter(this.getApplicationContext());

        mList = (ListView)findViewById(R.id.d2e_worksheet_list);
        
        ((PullToRefreshListView) mList).setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Do work to refresh the list here.
                new GetDataTask().execute();
                //add by shawn 2012-9-9
                //这里重新获取工单详情
                String[] params = new String[]{
		        		"FunType", "getTask_bySalesClient", 
		        		"UserID", JJBaseApplication.user_ID, 
		        		"SalesClientID",mid,
		        		"Date",JJBaseApplication.g_selecteDate};
		        if(D2EConfigures.TEST){
		        	YTStringHelper.array2string(params);
		        }		        		        
		        long id = JJBaseService.addMutilpartHttpTask("http://www.ohpest.com/ohpest-pco/webservices/get/data.html", params, D2EWorksheetList.this.getClass().getName(),D2EConfigures.TASK_GETWORKSHEETBYDATE);
		        	        
		        setCurTaskID(id);
            }
        });

        
        mList.setAdapter(mListAdapter);
        mList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        mList.setOnItemClickListener(mListOnItemClick);
        mList.setMinimumHeight(50);
        
        for(Object ob : JJBaseApplication.g_worksheetlist){
        	mListAdapter.addObject((D2EWorksheetList.WorkSheetItem)ob);
        	mListAdapter.notifyDataSetChanged();
        }

    }
    
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch(item.getItemId()){
		
		case R.id.menu_back:{
			backAciton();
		}break;
		
		case R.id.menu_sms:{
			showDialog(SEND_SMS);
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
        inflater.inflate(R.menu.worksheet_list_menu, menu);   	
        return true;
    }

	@Override
	protected Dialog onCreateDialog(int id) {
        LayoutInflater factory = LayoutInflater.from(this);
        final View textEntryView = factory.inflate(R.layout.d2e_sendsms, null);
        final EditText dt = (EditText)textEntryView.findViewById(R.id.mobile_num);

        return new AlertDialog.Builder(D2EWorksheetList.this)
            .setTitle(getResources().getString(R.string.send_msg))
            .setView(textEntryView)
            .setPositiveButton(getResources().getString(R.string.send), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                	final String text = dt.getText().toString();
                	StringBuffer buffer = new StringBuffer();
                	if(text != null && text.length() > 0){
                		
                		int len = mListAdapter.getCount();
                		int j = 1;
                		for(int i = 0; i < len; i ++){
                			WorkSheetItem item = (WorkSheetItem)mListAdapter.getItem(i);
                			if(item != null && item.mChecked){
                				buffer.append(getResources().getString(R.string.menu_worksheet) + j + ":");
                				buffer.append(item.mDate);
                				buffer.append(item.mContent);
                				buffer.append(item.mAddress);
                				j ++;
                			}
                		}
                		
                		final String sms = buffer.toString();
                		if(sms != null && sms.length() > 0){
                			(new Thread() {
                	            public void run(){
                            		sendSMS(text, sms);                			
                	            }
                			}).start();
                		}
                		else {
                			((JJBaseApplication)getApplication()).showMessage(getResources().getString(R.string.send_failed_chose_tool));
                		}
                	}
                }
            })
            .setNegativeButton(getResources().getString(R.string.btn_d2eprompt_cancle), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                	
                }
            })
            .create();			
	}


	@Override
	public void backAciton() {
		Intent intent = new Intent(D2EWorksheetList.this, D2ECustomerListActivity.class);
		D2EWorksheetList.this.startActivity(intent);
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
			dismissProcessDialog();
		}
		else if(values == JJBaseService.HTTP_SERVICE_INT){
		    JJTask task = (JJTask)param[1];
		    String response = null;
		    try{
			    InputStream ins = task.getInputStream();
			    byte[] bytes = JJNetHelper.readByByte(ins, -1);
			
			    response = new String(bytes, "UTF-8");
			    if(D2EConfigures.TEST){
			    	Log.e("response------------->", ""+response);
			    }
                ins.close();
                                
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
		    if(response == null || response.length() == 0){
		    	((JJBaseApplication)getApplication()).showMessage(getResources().getString(R.string.hand_failed_try_again));
		    	dismissProcessDialog();
		    	return;
		    }
		
		
		if(task.getTaskId() == D2EConfigures.TASK_GETWORKSHEETBYDATE){
	    	//[{"ID":"50","Title":"\u9996\u6b21\u670d\u52a1","SalesClientID":"19","ReaderID":"48","ParentID":"49","Description":"","SignClauseID":"19","OtherPayment":null,"BeginTime":"2012-06-11 08:00:00","EndTime":"2012-06-11 10:00:00","CreateTime":"2012-06-04 15:17:15","CreateUserID":"41","CreateUserName":"Felix","Status":"Create","CheckInTime":null,"CheckOutTime":null,"Division":"PCO","DivisionID":"6"}]  
			if(D2EConfigures.TEST){
			Log.e("D2EConfigures.TASK_GETWORKSHEETBYDATE========>", "D2EConfigures.TASK_GETWORKSHEETBYDATE");	
			}
            if(response != null && response.length() > 0){
            	D2EWorksheetList dps = new D2EWorksheetList();
            	try{
    				JSONObject js = new JSONObject(response);	
    				if(D2EConfigures.TEST){
    					Log.e("reponsexxxxxxxxxxxxxxxxxxxx>", ""+js);
    				}
    				String address = js.getString("Address");	    				
    				JSONArray ja = new JSONArray(js.getString("TaskData"));	    				
    				int len = ja.length();
    				for(int i = 0; i < len; i ++){
    					String     tmp = ja.getString(i);
    					JSONObject jo  = new JSONObject(tmp);
    					//modify by shawn 2012-9-5
    					//--------------------------------
    					//时间秒不要了
    					SimpleDateFormat mFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    					if(D2EConfigures.TEST){
    						Log.e("BeginTime----------->", ""+(jo.getString("BeginTime")));
    						Log.e("EndTime----------->", ""+(jo.getString("EndTime")));
    					
    					}
    					Date beginTime1=mFormat.parse(jo.getString("BeginTime"));
    					Date endTime1=mFormat.parse(jo.getString("EndTime"));
    					if(D2EConfigures.TEST){
    						Log.e("BeginTime----------->", ""+beginTime1);
    						Log.e("BeginTime----------->", ""+endTime1);
    					}
    					//add by shawn 20   b     2-10-29
    					// 计算工时，并显示
    					//开始的毫秒数
    					
    					String strGongshi=getGongShi(beginTime1,endTime1);
    					if(D2EConfigures.TEST){
    						Log.e("工时==================>", strGongshi);
    					}
    					//End
    					
    					DateFormat mDateFormat=new SimpleDateFormat("EEE HH:mm",java.util.Locale.US);//EEE表示星期 US把星期改为英文简写
    					D2EWorksheetList.WorkSheetItem item = dps.new WorkSheetItem(
    							getResources().getString(R.string.worksheet_start_time) + mDateFormat.format(beginTime1), 
    							getResources().getString(R.string.worksheet_gongshi)+strGongshi+getResources().getString(R.string.worksheet_hour),
    							getResources().getString(R.string.worksheet_content) + jo.getString("Title"),
    							getResources().getString(R.string.worksheet_addr) + address
    							);
    					
    					//add by shawn 2012-10-29 Begin
    					//保存工单的id，用来区分工单的不同
    					if(D2EConfigures.TEST){
    						Log.e("工单ID===========>", jo.getString("ID"));
    					}
    					item.setmId(jo.getString("ID"));
    					//End
    					if(D2EConfigures.TEST){
    						Log.e("!mListAdapter.isConatain(item)------------>", ""+(!mListAdapter.isConatain(item)));
    						Log.e("D2EConfigures.user_CountTask------------>", ""+(JJBaseApplication.user_CountTask));
    					}
    					//这里要判断列表是否已经有了
    					if(!mListAdapter.isConatain(item)){
    						JJBaseApplication.g_worksheetlist.add(item);
    						try{
    						    int num = Integer.parseInt(JJBaseApplication.g_worksheet.get(JJBaseApplication.g_selecteDate.substring(2, 8))) + 1;
    						    JJBaseApplication.g_worksheet.remove(JJBaseApplication.g_selecteDate.substring(2, 8));
    						    JJBaseApplication.g_worksheet.put(JJBaseApplication.g_selecteDate.substring(2, 8), "" + num);
    						}
    						catch(Exception e){}
        					mListAdapter.addObject(item);
        		        	mListAdapter.notifyDataSetChanged();
        		        	int mcount=Integer.valueOf(JJBaseApplication.user_CountTask);
        		        	int currentnum=mcount+1;
        		        	JJBaseApplication.user_CountTask=String.valueOf(currentnum);
        		        	mBadgeView.setText(String.valueOf(currentnum));
    					}
    					
    				}
    			
    			
    			}
    			catch(Exception ex){ex.printStackTrace();}
            }

	    	
	    	}
		}

	}
	
	
	/**
	 * 计算工时
	 */
	private String getGongShi(Date beginTime,Date endTime){
		long beginSecond=beginTime.getTime();
		//结束的毫秒数
		long endSecond=endTime.getTime();
		double beinHour=(beginSecond/(1000*60*60*1.0));
		double endHour=(endSecond/(1000*60*60*1.0));
		double gongShi=(endHour-beinHour);
		DecimalFormat mDecimalFormat=new DecimalFormat("0.0");
		return mDecimalFormat.format(gongShi);
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

	private void sendSMS(String phoneNumber, String message){        
		String SENT      = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";
 
        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0,
            new Intent(SENT), 0);
 
        PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0,
            new Intent(DELIVERED), 0);
 
        //监测短信发送后的处理
        registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), getResources().getString(R.string.msg_has_send), 
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(getBaseContext(), getResources().getString(R.string.error_happened), 
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(getBaseContext(), "No service", 
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(getBaseContext(), "Null PDU", 
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(getBaseContext(), "Radio off", 
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(SENT));
 
        
        registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode()){
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), getResources().getString(R.string.msg_has_been_send), 
                                Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(getBaseContext(), getResources().getString(R.string.msg_send_failed), 
                                Toast.LENGTH_SHORT).show();
                        break;                        
                }
            }
        }, new IntentFilter(DELIVERED));      
 
        SmsManager sms = SmsManager.getDefault();
  
        List<String> texts = sms.divideMessage(message);  
        for (String text : texts) {  
        	sms.sendTextMessage(phoneNumber, null, text, sentPI, deliveredPI); //sms.sendTextMessage(phoneNumber, null, text, null, null);  
        }  
    }
	
    private class GetDataTask extends AsyncTask<Void, Void, String[]> {

        @Override
        protected String[] doInBackground(Void... params) {
            // Simulates a background job.
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                
            }
            return null;
        }

        @Override
        protected void onPostExecute(String[] result) {
        	//mListAdapter.addObject("2012年5月30日上午工单安排", "123");
        	//addFirst("Added after refresh...");
            // Call onRefreshComplete when the list has been refreshed.
            ((PullToRefreshListView) mList).onRefreshComplete();

            super.onPostExecute(result);
        }
    }

}
