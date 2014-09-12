package com.mobilercn.sop.activity;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mobilercn.base.BadgeView;
import com.mobilercn.base.JJBaseActivity;
import com.mobilercn.base.JJBaseApplication;
import com.mobilercn.base.JJBaseService;
import com.mobilercn.config.D2EConfigures;
import com.mobilercn.sop.R;
import com.mobilercn.sop.data.MyWatchDataItem;
import com.mobilercn.sop.item.D2EListAdapter;
import com.mobilercn.sop.item.D2EListAdapterItam;
import com.mobilercn.task.JJTask;
import com.mobilercn.util.JJNetHelper;
import com.mobilercn.util.YTStringHelper;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Button;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.content.DialogInterface;
import android.content.Intent;
import android.app.AlertDialog.Builder;
import android.app.AlertDialog;

/**
 * 现场勘查数据收集界面
 * @author ShawnXiao
 *
 */
public class D2ENowPlaceDateCollectActivity extends JJBaseActivity {
	
	/**
	 * 客户名称编辑框
	 */
	private AutoCompleteTextView et_place_data_cust_name;
	/**
	 * 客户名称下拉按钮
	 */
	private ImageButton ib_place_data_cust_name;
	
	/**
	 * 勘查现场日期编辑框
	 */
	private EditText et_place_data_coll_date;
	/**
	 * 勘查人员编辑框
	 */
	private EditText et_place_data_coll_people;
	/**
	 * 勘查客户联系人编辑框
	 */
	private EditText et_place_data_coll_conn;
	/**
	 * 勘查现场地址编辑框
	 */
	private EditText et_place_data_coll_addr;
	/**
	 * 客户陪方陪同人员编辑框
	 */
	private EditText et_place_data_coll_peitong;
	
	
	/**
	 * 鼠类勘查功能容器
	 */
	private RelativeLayout panel_mouse_watch;
	/**
	 * 蟑螂勘查功能容器
	 */
	private RelativeLayout panel_cockroach_watch;
	/**
	 * 飞虫勘查功能容器
	 */
	private RelativeLayout panel_fly_insect_watch;
	/**
	 * 现场勘查发现的其他虫害功能容器
	 */
	private RelativeLayout panel_other_insect_watch;
	/**
	 * 重点区域勘查结果功能容器
	 */
	private RelativeLayout panel_watch_result;
	
	
	/**
	 * 提交功能按钮
	 */
	private Button btn_submit_data_coll_fun;
	/**
	 * 中间按钮
	 */
	private Button btn_reset_data_coll_fun;
	/**
	 * 返回功能按钮
	 */
	private Button btn_back_data_coll_fun;
	
	
	
	private BadgeView     mBadgeView;
	
	//--------------------------------------
	/**
	 * 客户名称
	 */
	private String mCustomerName;
	/**
	 * 勘查现场日期
	 */
	private String mWatchDate;
	/**
	 * 勘查人员
	 */
	private String mWatchPeople;
	/**
	 * 勘查客户联系人
	 */
	private String mWatchContact;
	/**
	 * 客户现场地址
	 */
	private String mWatchAddress;
	/**
	 * 客户方陪同人员
	 */
	private String mAccompany;
	
	//--------------------------------------
	
	
	//客户名称适配器
	private D2EListAdapter mTagsAdapter;
	
	//=========================================
	/**
	 * 请求鼠类勘查数据
	 */
	private static final int REQUEST_MOUSE_WATCH=1;
	/**
	 * 请求蟑螂勘查数据
	 */
	private static final int REQUEST_COCKROACH_WATCH=2;
	/**
	 * 请求飞虫勘查数据
	 */
	private static final int REQUEST_FLYING_WATCH=3;
	
	
	/**
	 * 其他虫害勘查数据项
	 */
	private MyWatchDataItem mOtherWatchItem;
	/**
	 * 重点区域勘查结果数据项
	 */
	private MyWatchDataItem mImportWatchItem;
	//=========================================
	
	/**
	 * 其他勘查的索引
	 */
	private int index=0;
	/**
	 * 重点区域勘查索引
	 */
	private int indexImport=0;
	
	
	/**
	 * 获取界面信息
	 */
	private void getDataFromView(){
		mCustomerName=et_place_data_cust_name.getText().toString();
		mWatchDate=et_place_data_coll_date.getText().toString();
		mWatchPeople=et_place_data_coll_people.getText().toString();
		mWatchContact=et_place_data_coll_conn.getText().toString();
		mWatchAddress=et_place_data_coll_addr.getText().toString();
		mAccompany=et_place_data_coll_peitong.getText().toString();
	}
	
	/**
	 * 判断信息是否填写完整
	 */
	private boolean isDataComplete(){
		getDataFromView();
		if(mCustomerName==null||!(mCustomerName.length()>0)){
			((JJBaseApplication)getApplication()).showMessage(getResources().getString(R.string.custom_name_less));
			return false;
		}
		if(mWatchDate==null||!(mWatchDate.length()>0)){
			((JJBaseApplication)getApplication()).showMessage(getResources().getString(R.string.watch_date_less));
			return false;
		}
		if(mWatchPeople==null||!(mWatchPeople.length()>0)){
			((JJBaseApplication)getApplication()).showMessage(getResources().getString(R.string.watch_people_less));
			return false;
		}
		if(mWatchContact==null||!(mWatchContact.length()>0)){
			((JJBaseApplication)getApplication()).showMessage(getResources().getString(R.string.watch_contact_less));
			return false;
		}
		if(mWatchAddress==null||!(mWatchAddress.length()>0)){
			((JJBaseApplication)getApplication()).showMessage(getResources().getString(R.string.watch_addr_less));
			return false;
		}
		if(mAccompany==null||!(mAccompany.length()>0)){
			((JJBaseApplication)getApplication()).showMessage(getResources().getString(R.string.watch_acco_less));
			return false;
		}
		if(mCustomerName!=null&&mCustomerName.length()>0
				&&mWatchDate!=null&&mWatchDate.length()>0
				&&mWatchPeople!=null&&mWatchPeople.length()>0
				&&mWatchContact!=null&&mWatchContact.length()>0
				&&mWatchAddress!=null&&mWatchAddress.length()>0
				&&mAccompany!=null&&mAccompany.length()>0
				){
			return true;
		}
		return false;
	}
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//使用客户自定义标题栏
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.d2e_palce_data_coll);
		//设置用户自定义标题栏
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.d2e_titlebar);
		TextView tv = (TextView)findViewById(R.id.title);
		//设置自己的标题名字
        tv.setText(getResources().getString(R.string.now_watch));
        mBadgeView = (BadgeView)findViewById(R.id.countTask);
        mBadgeView.setVisibility(View.GONE);
        //初始化控件
        initView();
        //获取客户列表信息
        getCustomerData();
        //为功能控件设置监听器
        setUpViewListener();
        
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		//如果添加了老鼠数据则不能再继续添加了
		if(JJBaseApplication.isAddMousWatch){
			panel_mouse_watch.setClickable(false);
		}
		//如果添加了蟑螂数据则不能再继续添加了
		if(JJBaseApplication.isAddCockroachWatch){
			panel_cockroach_watch.setClickable(false);
		}
		//如果添加了飞虫数据则不能再继续添加了
		if(JJBaseApplication.isAddFlyWatch){
			panel_fly_insect_watch.setClickable(false);
		}
	}
	
	/**
	 * 获取客户列表信息
	 */
	private void getCustomerData(){
		
		if(JJBaseApplication.mWatchClientList.size()>0){
			for(D2EListAdapterItam item:JJBaseApplication.mWatchClientList){
				mTagsAdapter.addObject(item);
			}
		}else{
			
			 String[] params = new String[]{
		        		"FunType", "getCustomerData", 
		        		"OrgID", JJBaseApplication.user_OrgID
		        		};
			 if(D2EConfigures.TEST){
		        	YTStringHelper.array2string(params);
		        }
	        long ids = JJBaseService.addMutilpartHttpTask("http://www.ohpest.com/ohpest-pco/webservices/get/data.html", params, D2ENowPlaceDateCollectActivity.this.getClass().getName(),D2EConfigures.TASK_GET_ALL_CUSTOM);
	        
	        showWaitDialog(getResources().getString(R.string.get_custom_data));
	        setCurTaskID(ids);
		}
        
		 
	}
	/**
	 * 为功能控件设置监听器
	 */
	private void setUpViewListener(){
		setUpCustomNameState();
		
		panel_mouse_watch.setOnClickListener(new MouseWatchListener());
		panel_cockroach_watch.setOnClickListener(new CockRoachListener());
		panel_fly_insect_watch.setOnClickListener(new FlyInsectWatchListener());
		
		panel_other_insect_watch.setOnClickListener(new OtherInsectWatchListener());
		panel_watch_result.setOnClickListener(new WatchResultListener());
		
		btn_submit_data_coll_fun.setOnClickListener(new SubmitListener());
		btn_back_data_coll_fun.setOnClickListener(new BackListener());
	}
	/**
	 * 为客户名称相关设置信息
	 */
	private void setUpCustomNameState(){
		et_place_data_cust_name.setThreshold(1);
		et_place_data_cust_name.setAdapter(mTagsAdapter);
		et_place_data_cust_name.addTextChangedListener(new MyTextChangeListener());
		et_place_data_cust_name.setOnItemClickListener(new MyCustomItemClickListener());
		ib_place_data_cust_name.setOnClickListener(new IbtnDropDownListner());
	}
	/**
	 * 客户下拉按钮监听器
	 */
	private class IbtnDropDownListner implements OnClickListener{

		@Override
		public void onClick(View v) {
			if(D2EConfigures.TEST){
				Log.e("ib_place_data_cust_name============>", "onClick()");
				Log.e("mTagsAdapter", ""+mTagsAdapter.getCount());
			}
			String customName=et_place_data_cust_name.getText().toString();
			if(customName!=null&&customName.length()>0){
				et_place_data_cust_name.setText("");
			}
			et_place_data_cust_name.showDropDown();
			et_place_data_cust_name.setThreshold(1);
		}
		
	}
	/**
	 * 客户列表项监听器
	 */
	private class MyCustomItemClickListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long id) {
			D2EListAdapterItam item=(D2EListAdapterItam) mTagsAdapter.getItem(position);
			et_place_data_cust_name.setText(item.getTitle());
			et_place_data_coll_conn.setText(item.getmCustomerContact());
			//所勘探客户的id
			JJBaseApplication.user_SalesClientID=item.getId();
		}
		
	}
	/**
	 * 文本改变监听器
	 */
	private class MyTextChangeListener implements TextWatcher{

		@Override
		public void afterTextChanged(Editable s) {
			
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			mTagsAdapter.getFilter().filter(s);
			mTagsAdapter.notifyDataSetInvalidated();
			
		}
		
	}
	
	/**
	 * 提交按钮监听器
	 */
	private class SubmitListener implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			sendDataToServer();
		}
		
	}
	/**
	 * 模拟数据
	 */
	private String simulateDate(){
		StringBuffer sb=new StringBuffer();
		sb.append("{");
		sb.append("\"").append("CustomInfo").append("\"").append(":");
		sb.append("{");
		sb.append("\"").append("CustomName").append("\"").append(":").append("\"").append("伟赛公司").append("\"").append(",");
		sb.append("\"").append("PorspectTime").append("\"").append(":").append("\"").append("2012-11-07 17:57").append("\"").append(",");
		sb.append("\"").append("ProspectPeople").append("\"").append(":").append("\"").append("ShawnXiao").append("\"").append(",");
		sb.append("\"").append("ProspectContact").append("\"").append(":").append("\"").append("Kris").append("\"").append(",");
		sb.append("\"").append("ProspectAddr").append("\"").append(":").append("\"").append("龙东大道300号").append("\"").append(",");
		sb.append("\"").append("ProspectAccompany").append("\"").append(":").append("\"").append("Tina").append("\"");
		sb.append("}").append(",");
		
		sb.append("\"").append("ProspectData").append("\"").append(":").append("{");
		sb.append("\"").append("Mouse").append("\"").append(":").append("{");
		
		sb.append("\"").append("0").append("\"").append(":").append("[");
		sb.append("\"").append("1").append("\"").append(",").append("\"").append("1").append("\"");
		sb.append(",").append("\"").append("1").append("\"").append(",").append("\"").append("非洲鼠").append("\"");
		sb.append("]").append(",");
		
		sb.append("\"").append("1").append("\"").append(":").append("[");
		sb.append("\"").append("1").append("\"").append(",").append("\"").append("1").append("\"");
		sb.append(",").append("\"").append("1").append("\"").append(",").append("\"").append("非洲鼠").append("\"");
		sb.append("]").append(",");
		
		sb.append("\"").append("2").append("\"").append(":").append("[");
		sb.append("\"").append("1").append("\"").append(",").append("\"").append("1").append("\"");
		sb.append(",").append("\"").append("1").append("\"").append(",").append("\"").append("非洲鼠").append("\"");
		sb.append("]").append(",");
		
		sb.append("\"").append("3").append("\"").append(":").append("[");
		sb.append("\"").append("1").append("\"").append(",").append("\"").append("1").append("\"");
		sb.append(",").append("\"").append("1").append("\"").append(",").append("\"").append("非洲鼠").append("\"");
		sb.append("]").append(",");
		
		sb.append("\"").append("4").append("\"").append(":").append("[");
		sb.append("\"").append("1").append("\"").append(",").append("\"").append("1").append("\"");
		sb.append(",").append("\"").append("1").append("\"").append(",").append("\"").append("非洲鼠").append("\"");
		sb.append("]").append(",");
		
		sb.append("\"").append("5").append("\"").append(":").append("[");
		sb.append("\"").append("1").append("\"").append(",").append("\"").append("1").append("\"");
		sb.append(",").append("\"").append("1").append("\"").append(",").append("\"").append("非洲鼠").append("\"");
		sb.append("]").append(",");
		
		sb.append("\"").append("6").append("\"").append(":").append("[");
		sb.append("\"").append("1").append("\"").append(",").append("\"").append("1").append("\"");
		sb.append(",").append("\"").append("1").append("\"").append(",").append("\"").append("非洲鼠").append("\"");
		sb.append("]").append(",");
		
		sb.append("\"").append("7").append("\"").append(":").append("[");
		sb.append("\"").append("1").append("\"").append(",").append("\"").append("1").append("\"");
		sb.append(",").append("\"").append("1").append("\"").append(",").append("\"").append("非洲鼠").append("\"");
		sb.append("]").append(",");
		
		sb.append("\"").append("8").append("\"").append(":").append("[");
		sb.append("\"").append("1").append("\"").append(",").append("\"").append("1").append("\"");
		sb.append(",").append("\"").append("1").append("\"").append(",").append("\"").append("非洲鼠").append("\"");
		sb.append("]").append(",");
		
		sb.append("\"").append("9").append("\"").append(":").append("[");
		sb.append("\"").append("1").append("\"").append(",").append("\"").append("1").append("\"");
		sb.append(",").append("\"").append("1").append("\"").append(",").append("\"").append("非洲鼠").append("\"");
		sb.append("]").append(",");
		
		sb.append("\"").append("10").append("\"").append(":").append("[");
		sb.append("\"").append("1").append("\"").append(",").append("\"").append("1").append("\"");
		sb.append(",").append("\"").append("1").append("\"").append(",").append("\"").append("非洲鼠").append("\"");
		sb.append("]").append(",");
		
		
		sb.append("\"").append("11").append("\"").append(":").append("[");
		sb.append("\"").append("1").append("\"").append(",").append("\"").append("1").append("\"");
		sb.append(",").append("\"").append("1").append("\"").append(",").append("\"").append("非洲鼠").append("\"");
		sb.append("]").append(",");
		
		sb.append("\"").append("12").append("\"").append(":").append("[");
		sb.append("\"").append("1").append("\"").append(",").append("\"").append("1").append("\"");
		sb.append(",").append("\"").append("1").append("\"").append(",").append("\"").append("非洲鼠").append("\"");
		sb.append("]").append(",");
		
		sb.append("\"").append("13").append("\"").append(":").append("[");
		sb.append("\"").append("1").append("\"").append(",").append("\"").append("1").append("\"");
		sb.append(",").append("\"").append("1").append("\"").append(",").append("\"").append("非洲鼠").append("\"");
		sb.append("]").append(",");
		
		sb.append("\"").append("14").append("\"").append(":").append("[");
		sb.append("\"").append("1").append("\"").append(",").append("\"").append("1").append("\"");
		sb.append(",").append("\"").append("1").append("\"").append(",").append("\"").append("非洲鼠").append("\"");
		sb.append("]").append(",");
		
		sb.append("\"").append("15").append("\"").append(":").append("[");
		sb.append("\"").append("1").append("\"").append(",").append("\"").append("1").append("\"");
		sb.append(",").append("\"").append("1").append("\"").append(",").append("\"").append("非洲鼠").append("\"");
		sb.append("]").append(",");
		
		sb.append("\"").append("16").append("\"").append(":").append("[");
		sb.append("\"").append("1").append("\"").append(",").append("\"").append("1").append("\"");
		sb.append(",").append("\"").append("1").append("\"").append(",").append("\"").append("非洲鼠").append("\"");
		sb.append("]");
		
		sb.append("}").append(",");
		
		sb.append("\"").append("CockRoach").append("\"").append(":").append("{");
		
		sb.append("\"").append("0").append("\"").append(":").append("[");
		sb.append("\"").append("1").append("\"").append(",");
		sb.append("\"").append("0").append("\"").append(",");
		sb.append("\"").append("0").append("\"").append(",");
		sb.append("\"").append("xxxx").append("\"");
		sb.append("]").append(",");
		
		sb.append("\"").append("1").append("\"").append(":").append("[");
		sb.append("\"").append("1").append("\"").append(",");
		sb.append("\"").append("0").append("\"").append(",");
		sb.append("\"").append("0").append("\"").append(",");
		sb.append("\"").append("xxxx").append("\"");
		sb.append("]").append(",");

		sb.append("\"").append("2").append("\"").append(":").append("[");
		sb.append("\"").append("1").append("\"").append(",");
		sb.append("\"").append("0").append("\"").append(",");
		sb.append("\"").append("0").append("\"").append(",");
		sb.append("\"").append("xxxx").append("\"");
		sb.append("]").append(",");
		
		sb.append("\"").append("3").append("\"").append(":").append("[");
		sb.append("\"").append("1").append("\"").append(",");
		sb.append("\"").append("0").append("\"").append(",");
		sb.append("\"").append("0").append("\"").append(",");
		sb.append("\"").append("xxxx").append("\"");
		sb.append("]").append(",");
		
		sb.append("\"").append("4").append("\"").append(":").append("[");
		sb.append("\"").append("1").append("\"").append(",");
		sb.append("\"").append("0").append("\"").append(",");
		sb.append("\"").append("0").append("\"").append(",");
		sb.append("\"").append("xxxx").append("\"");
		sb.append("]").append(",");
		
		sb.append("\"").append("5").append("\"").append(":").append("[");
		sb.append("\"").append("1").append("\"").append(",");
		sb.append("\"").append("0").append("\"").append(",");
		sb.append("\"").append("0").append("\"").append(",");
		sb.append("\"").append("xxxx").append("\"");
		sb.append("]").append(",");

		sb.append("\"").append("6").append("\"").append(":").append("[");
		sb.append("\"").append("1").append("\"").append(",");
		sb.append("\"").append("0").append("\"").append(",");
		sb.append("\"").append("0").append("\"").append(",");
		sb.append("\"").append("xxxx").append("\"");
		sb.append("]").append(",");
		
		sb.append("\"").append("7").append("\"").append(":").append("[");
		sb.append("\"").append("1").append("\"").append(",");
		sb.append("\"").append("0").append("\"").append(",");
		sb.append("\"").append("0").append("\"").append(",");
		sb.append("\"").append("xxxx").append("\"");
		sb.append("]").append(",");
		
		sb.append("\"").append("8").append("\"").append(":").append("[");
		sb.append("\"").append("1").append("\"").append(",");
		sb.append("\"").append("0").append("\"").append(",");
		sb.append("\"").append("0").append("\"").append(",");
		sb.append("\"").append("xxxx").append("\"");
		sb.append("]").append(",");
		
		sb.append("\"").append("9").append("\"").append(":").append("[");
		sb.append("\"").append("1").append("\"").append(",");
		sb.append("\"").append("0").append("\"").append(",");
		sb.append("\"").append("0").append("\"").append(",");
		sb.append("\"").append("xxxx").append("\"");
		sb.append("]").append(",");

		sb.append("\"").append("10").append("\"").append(":").append("[");
		sb.append("\"").append("1").append("\"").append(",");
		sb.append("\"").append("0").append("\"").append(",");
		sb.append("\"").append("0").append("\"").append(",");
		sb.append("\"").append("xxxx").append("\"");
		sb.append("]").append(",");
		
		sb.append("\"").append("11").append("\"").append(":").append("[");
		sb.append("\"").append("1").append("\"").append(",");
		sb.append("\"").append("0").append("\"").append(",");
		sb.append("\"").append("0").append("\"").append(",");
		sb.append("\"").append("xxxx").append("\"");
		sb.append("]");
		sb.append("}").append(",");
		
		
		sb.append("\"").append("Flying").append("\"").append(":").append("{");
		sb.append("\"").append("0").append("\"").append(":").append("[");
		sb.append("\"").append("1").append("\"").append(",");
		sb.append("\"").append("0").append("\"").append(",");
		sb.append("\"").append("0").append("\"").append(",");
		sb.append("\"").append("xxxx").append("\"");
		sb.append("]").append(",");
		
		sb.append("\"").append("1").append("\"").append(":").append("[");
		sb.append("\"").append("1").append("\"").append(",");
		sb.append("\"").append("0").append("\"").append(",");
		sb.append("\"").append("0").append("\"").append(",");
		sb.append("\"").append("xxxx").append("\"");
		sb.append("]").append(",");

		sb.append("\"").append("2").append("\"").append(":").append("[");
		sb.append("\"").append("1").append("\"").append(",");
		sb.append("\"").append("0").append("\"").append(",");
		sb.append("\"").append("0").append("\"").append(",");
		sb.append("\"").append("xxxx").append("\"");
		sb.append("]").append(",");
		
		sb.append("\"").append("3").append("\"").append(":").append("[");
		sb.append("\"").append("1").append("\"").append(",");
		sb.append("\"").append("0").append("\"").append(",");
		sb.append("\"").append("0").append("\"").append(",");
		sb.append("\"").append("xxxx").append("\"");
		sb.append("]").append(",");
		
		sb.append("\"").append("4").append("\"").append(":").append("[");
		sb.append("\"").append("1").append("\"").append(",");
		sb.append("\"").append("0").append("\"").append(",");
		sb.append("\"").append("0").append("\"").append(",");
		sb.append("\"").append("xxxx").append("\"");
		sb.append("]").append(",");
		
		sb.append("\"").append("5").append("\"").append(":").append("[");
		sb.append("\"").append("1").append("\"").append(",");
		sb.append("\"").append("0").append("\"").append(",");
		sb.append("\"").append("0").append("\"").append(",");
		sb.append("\"").append("xxxx").append("\"");
		sb.append("]").append(",");

		sb.append("\"").append("6").append("\"").append(":").append("[");
		sb.append("\"").append("1").append("\"").append(",");
		sb.append("\"").append("0").append("\"").append(",");
		sb.append("\"").append("0").append("\"").append(",");
		sb.append("\"").append("xxxx").append("\"");
		sb.append("]").append(",");
		
		sb.append("\"").append("7").append("\"").append(":").append("[");
		sb.append("\"").append("1").append("\"").append(",");
		sb.append("\"").append("0").append("\"").append(",");
		sb.append("\"").append("0").append("\"").append(",");
		sb.append("\"").append("xxxx").append("\"");
		sb.append("]").append(",");
		
		sb.append("\"").append("8").append("\"").append(":").append("[");
		sb.append("\"").append("1").append("\"").append(",");
		sb.append("\"").append("0").append("\"").append(",");
		sb.append("\"").append("0").append("\"").append(",");
		sb.append("\"").append("xxxx").append("\"");
		sb.append("]").append(",");
		
		sb.append("\"").append("9").append("\"").append(":").append("[");
		sb.append("\"").append("1").append("\"").append(",");
		sb.append("\"").append("0").append("\"").append(",");
		sb.append("\"").append("0").append("\"").append(",");
		sb.append("\"").append("xxxx").append("\"");
		sb.append("]").append(",");

		sb.append("\"").append("10").append("\"").append(":").append("[");
		sb.append("\"").append("1").append("\"").append(",");
		sb.append("\"").append("0").append("\"").append(",");
		sb.append("\"").append("0").append("\"").append(",");
		sb.append("\"").append("xxxx").append("\"");
		sb.append("]").append(",");
		
		sb.append("\"").append("11").append("\"").append(":").append("[");
		sb.append("\"").append("1").append("\"").append(",");
		sb.append("\"").append("0").append("\"").append(",");
		sb.append("\"").append("0").append("\"").append(",");
		sb.append("\"").append("xxxx").append("\"");
		sb.append("]").append(",");
		
		sb.append("\"").append("12").append("\"").append(":").append("[");
		sb.append("\"").append("1").append("\"").append(",");
		sb.append("\"").append("0").append("\"").append(",");
		sb.append("\"").append("0").append("\"").append(",");
		sb.append("\"").append("xxxx").append("\"");
		sb.append("]");
		sb.append("}").append(",");
		
		sb.append("\"").append("Other").append("\"").append(":").append("[");
		sb.append("{");
		sb.append("\"").append("Index").append("\"").append(":").append("\"").append("1").append("\"").append(",");
		sb.append("\"").append("Name").append("\"").append(":").append("\"").append("蝗灾").append("\"").append(",");
		sb.append("\"").append("Memo").append("\"").append(":").append("\"").append("张就爱你个").append("\"");
		sb.append("}").append(",");
		sb.append("{");
		sb.append("\"").append("Index").append("\"").append(":").append("\"").append("1").append("\"").append(",");
		sb.append("\"").append("Name").append("\"").append(":").append("\"").append("蝗灾").append("\"").append(",");
		sb.append("\"").append("Memo").append("\"").append(":").append("\"").append("张就爱你个").append("\"");
		sb.append("}");
		sb.append("]").append(",");
		
		sb.append("\"").append("ImportArea").append("\"").append(":").append("[");
		sb.append("{");
		sb.append("\"").append("Area").append("\"").append(":").append("\"").append("虹口区").append("\"").append(",");
		sb.append("\"").append("Memo").append("\"").append(":").append("\"").append("张就爱你个").append("\"");
		sb.append("}").append(",");
		
		sb.append("{");
		sb.append("\"").append("Area").append("\"").append(":").append("\"").append("虹口区").append("\"").append(",");
		sb.append("\"").append("Memo").append("\"").append(":").append("\"").append("张就爱你个").append("\"");
		sb.append("}");
		
		sb.append("]");
		sb.append("}");
		sb.append("}");
		
		
		return sb.toString();
	}
	/**
	 * 从其他界面获取数据
	 */
	private String getDataFromOtherView(){
		StringBuffer sb=new StringBuffer();
		sb.append("{");
		sb.append("\"").append("CustomInfo").append("\"").append(":");
		sb.append("{");
		sb.append("\"").append("CustomName").append("\"").append(":").append("\"").append(mCustomerName).append("\"").append(",");
		sb.append("\"").append("PorspectTime").append("\"").append(":").append("\"").append(mWatchDate).append("\"").append(",");
		sb.append("\"").append("ProspectPeople").append("\"").append(":").append("\"").append(mWatchPeople).append("\"").append(",");
		sb.append("\"").append("ProspectContact").append("\"").append(":").append("\"").append(mWatchContact).append("\"").append(",");
		sb.append("\"").append("ProspectAddr").append("\"").append(":").append("\"").append(mWatchAddress).append("\"").append(",");
		sb.append("\"").append("ProspectAccompany").append("\"").append(":").append("\"").append(mAccompany).append("\"");
		sb.append("}").append(",");
		
		sb.append("\"").append("ProspectData").append("\"").append(":").append("{");
		
		if(D2EConfigures.TEST){
			Log.e("JJBaseApplication.mWatchDataList.size()========>", ""+(JJBaseApplication.mWatchDataList.size()));
			Log.e("JJBaseApplication.mWatchDataList.size()>0========>", ""+(JJBaseApplication.mWatchDataList.size()>0));
		}
		if(JJBaseApplication.mWatchDataList.size()>0){
		StringBuffer sbMouse=new StringBuffer();
		for(MyWatchDataItem item:JJBaseApplication.mWatchDataList){
			if(item.getmWatchType()!=null&&item.getmWatchType().length()>0){
			sbMouse.append("\"").append(item.getmWatchType()).append("\"").append(":").append("{");
			for(MyWatchDataItem itemSub:item.mWatchDataList){
				
				//如果是种类的话，还要判断无是否有数据
				if(itemSub.getmMouseIndex()==0){
					if(itemSub.getmMouseKind5()!=-1){
						sbMouse.append("\"").append(itemSub.getmMouseIndex()).append("\"").append(":").append("[");
						sbMouse.append("\"").append("0").append("\"").append(",");
						sbMouse.append("\"").append("0").append("\"").append(",");
						sbMouse.append("\"").append("0").append("\"").append(",");
						sbMouse.append("\"").append(itemSub.getmMouseKind4()).append("\"");
					}else{
						sbMouse.append("\"").append(itemSub.getmMouseIndex()).append("\"").append(":").append("[");
						sbMouse.append("\"").append(itemSub.getmMouseKind1()).append("\"").append(",");
						sbMouse.append("\"").append(itemSub.getmMouseKind2()).append("\"").append(",");
						sbMouse.append("\"").append(itemSub.getmMouseKind3()).append("\"").append(",");
						sbMouse.append("\"").append(itemSub.getmMouseKind4()).append("\"");
					}
				}else{
					sbMouse.append("\"").append(itemSub.getmMouseIndex()).append("\"").append(":").append("[");
					sbMouse.append("\"").append(itemSub.getmMouseKind1()).append("\"").append(",");
					sbMouse.append("\"").append(itemSub.getmMouseKind2()).append("\"").append(",");
					sbMouse.append("\"").append(itemSub.getmMouseKind3()).append("\"").append(",");
					sbMouse.append("\"").append(itemSub.getmMouseKind4()).append("\"");
				}
				
				sbMouse.append("]").append(",");
			}
			sbMouse.deleteCharAt(sbMouse.length()-1);
			sbMouse.append("}").append(",");
			}
		}
		sbMouse.deleteCharAt(sbMouse.length()-1);
		sb.append(sbMouse.toString());
		}
		if(JJBaseApplication.mOtherWatchDataList.size()>0){
			if(JJBaseApplication.mWatchDataList.size()>0){
				sb.append(",");
			}
			StringBuffer sbOther=new StringBuffer();
			for(MyWatchDataItem item:JJBaseApplication.mOtherWatchDataList){
				if(item.getmWatchType()!=null&&item.getmWatchType().length()>0){
					sbOther.append("\"").append(item.getmWatchType()).append("\"").append(":").append("[");
					for(MyWatchDataItem itemSub:item.mWatchDataList){
						sbOther.append("{");
						sbOther.append("\"").append("Index").append("\"").append(":");
						sbOther.append("\"").append(itemSub.getmMouseIndex()).append("\"").append(",");
						sbOther.append("\"").append("Name").append("\"").append(":");
						sbOther.append("\"").append(itemSub.getmInsectName()).append("\"").append(",");
						sbOther.append("\"").append("Place").append("\"").append(":");
						sbOther.append("\"").append(itemSub.getmArea()).append("\"").append(",");
						sbOther.append("\"").append("Memo").append("\"").append(":");
						sbOther.append("\"").append(itemSub.getmRemark()).append("\"");
						sbOther.append("}").append(",");				
					}
					sbOther.deleteCharAt(sbOther.length()-1);
					sbOther.append("]").append(",");
				}
			}
			sbOther.deleteCharAt(sbOther.length()-1);
			sb.append(sbOther.toString());
		}
		
		
		
		if(JJBaseApplication.mImportWatchResulDatatList.size()>0){
			if(JJBaseApplication.mOtherWatchDataList.size()>0||JJBaseApplication.mWatchDataList.size()>0){
				sb.append(",");
			}
			StringBuffer sbImport=new StringBuffer();
			for(MyWatchDataItem item:JJBaseApplication.mImportWatchResulDatatList){
				if(item.getmWatchType()!=null&&item.getmWatchType().length()>0){
					sbImport.append("\"").append(item.getmWatchType()).append("\"").append(":").append("[");
					for(MyWatchDataItem itemSub:item.mWatchDataList){
						sbImport.append("{");
						sbImport.append("\"").append("Index").append("\"").append(":");
						sbImport.append("\"").append(itemSub.getmMouseIndex()).append("\"").append(",");
						sbImport.append("\"").append("Name").append("\"").append(":");
						sbImport.append("\"").append(itemSub.getmInsectName()).append("\"").append(",");
						sbImport.append("\"").append("Result").append("\"").append(":");
						sbImport.append("\"").append(itemSub.getmArea()).append("\"").append(",");
						sbImport.append("\"").append("Memo").append("\"").append(":");
						sbImport.append("\"").append(itemSub.getmRemark()).append("\"");
						sbImport.append("}").append(",");		
					}
					sbImport.deleteCharAt(sbImport.length()-1);
					sbImport.append("]").append(",");
				}
			}
			sbImport.deleteCharAt(sbImport.length()-1);
			sb.append(sbImport.toString());
		}
		
		
		
		sb.append("}");
		sb.append("}");
		return sb.toString();
	}
	
	/**
	 * 发送数据到服务器
	 */
	private void sendDataToServer(){
//		String data=simulateDate();
		if(isDataComplete()){
			if(JJBaseApplication.mWatchDataList.size()>0||JJBaseApplication.mOtherWatchDataList.size()>0||JJBaseApplication.mImportWatchResulDatatList.size()>0){
				String data=getDataFromOtherView();
				String[] params = new String[]{
		        		"FunType", "insertProspecting", 
		        		"OrgID", JJBaseApplication.user_OrgID,
		        		"SalesClientID", JJBaseApplication.user_SalesClientID,
		        		"UserID", JJBaseApplication.user_ID,
		        		"Data",data
		        		};
		        if(D2EConfigures.TEST){
		        	YTStringHelper.array2string(params);
		        }
		        long ids = JJBaseService.addMutilpartHttpTask("http://www.ohpest.com/ohpest-pco/webservices/get/data.html", params, D2ENowPlaceDateCollectActivity.this.getClass().getName(),D2EConfigures.TASK_INSERT_WATCH_DATA);
		        showWaitDialog(getResources().getString(R.string.submiting));
		        
		        setCurTaskID(ids);
			}else{
				((JJBaseApplication)getApplication()).showMessage(getResources().getString(R.string.watch_at_lest_one));
			}
		}
                   
	}
	/**
	 * 返回按钮监听器
	 */
	private class BackListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			//判断是否有数据未提交
			if(JJBaseApplication.mWatchDataList.size()>0||JJBaseApplication.mOtherWatchDataList.size()>0||JJBaseApplication.mImportWatchResulDatatList.size()>0){
				AlertDialog.Builder builder=new Builder(D2ENowPlaceDateCollectActivity.this);
				builder.setTitle(getResources().getString(R.string.data_is_not_submit));
				builder.setMessage(getResources().getString(R.string.is_submit_data));
				
				//确定的时候把数据提交
				builder.setPositiveButton(getResources().getString(R.string.dialog_btn_yes_text), new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						sendDataToServer();
					}
				});
				//取消的时候吧数据清空
				builder.setNegativeButton(getResources().getString(R.string.dialog_btn_cancle_text), new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						clearData();
					}
				});
				Dialog dialog=builder.create();
				dialog.show();
			}else{
				//这里判断从哪里进入的，如果是从主页进入的则，返回主页
				Intent intent=new Intent(D2ENowPlaceDateCollectActivity.this,D2EMainScreen.class);
				D2ENowPlaceDateCollectActivity.this.startActivity(intent);
				overridePendingTransition(R.anim.fade, R.anim.hold);
				D2ENowPlaceDateCollectActivity.this.finish();
			}
		}
		
	}
	/**
	 * 重点区域勘查结果监听器
	 */
	private class WatchResultListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			LayoutInflater inflater=getLayoutInflater();
			final View view=inflater.inflate(R.layout.d2e_watch_result, null);
			final EditText et_area=(EditText) view.findViewById(R.id.et_watch_result_area);
			final EditText et_remark=(EditText) view.findViewById(R.id.et_watch_result_mark);
			final EditText et_watch_result_result=(EditText) view.findViewById(R.id.et_watch_result_result);
			Builder builder=new Builder(D2ENowPlaceDateCollectActivity.this);
			builder.setTitle(getResources().getString(R.string.title_watch_result));
			builder.setIcon(null);
			builder.setView(view);
			builder.setPositiveButton(getResources().getString(R.string.dialog_btn_yes_text), new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					if(mImportWatchItem==null){
						mImportWatchItem=new MyWatchDataItem();
					}
					mImportWatchItem.setmWatchType("ImportArea");
					String name=et_area.getText().toString();
					String remark=et_remark.getText().toString();
					String result=et_watch_result_result.getText().toString();
					MyWatchDataItem item=new MyWatchDataItem();
					item.setmMouseIndex(indexImport);
					item.setmInsectName(name);
					item.setmRemark(remark);
					item.setmArea(result);
					mImportWatchItem.mWatchDataList.add(item);
					indexImport++;
					if(D2EConfigures.TEST){
						Log.e("mImportWatchItem.mWatchDataList========>", ""+(mImportWatchItem.mWatchDataList.size()));
						for(MyWatchDataItem item2:mImportWatchItem.mWatchDataList){
							Log.e("mImportWatchItem===========>", ""+item2.toString());
						}
					}
					
					if(mImportWatchItem!=null){
						if(D2EConfigures.TEST){
							Log.e("!(JJBaseApplication.mImportWatchResulDatatList.size()>0)======>", ""+(!(JJBaseApplication.mImportWatchResulDatatList.size()>0)));
						}
						if(!(JJBaseApplication.mImportWatchResulDatatList.size()>0)){
							JJBaseApplication.mImportWatchResulDatatList.add(mImportWatchItem);
						}
					}
					if(D2EConfigures.TEST){
						Log.e("JJBaseApplication.mImportWatchResulDatatList========>", ""+(JJBaseApplication.mImportWatchResulDatatList.size()));
					}
				}
			});
			builder.setNegativeButton(getResources().getString(R.string.dialog_btn_cancle_text), new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
				}
			});
			AlertDialog dialog=builder.create();
			dialog.show();
		}
		
	}
	/**
	 * 其他虫害勘查监听器
	 */
	private class OtherInsectWatchListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			LayoutInflater inflater=getLayoutInflater();
			final View view=inflater.inflate(R.layout.d2e_other_insect_watch, null);
			final EditText et_name=(EditText) view.findViewById(R.id.et_other_insect_name);
			final EditText et_other_insect_area=(EditText) view.findViewById(R.id.et_other_insect_area);
			final EditText et_remark=(EditText) view.findViewById(R.id.et_other_insect_mark);
			
			Builder builder=new Builder(D2ENowPlaceDateCollectActivity.this);
			builder.setTitle(getResources().getString(R.string.title_other_insect));
			builder.setIcon(null);
			builder.setView(view);
			builder.setPositiveButton(getResources().getString(R.string.dialog_btn_yes_text), new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					if(mOtherWatchItem==null){
						mOtherWatchItem=new MyWatchDataItem();
					}
					mOtherWatchItem.setmWatchType("Other");
					String name=et_name.getText().toString();
					String remark=et_remark.getText().toString();
					String area=et_other_insect_area.getText().toString();
					MyWatchDataItem item=new MyWatchDataItem();
					item.setmMouseIndex(index);
					item.setmInsectName(name);
					item.setmArea(area);
					item.setmRemark(remark);
					mOtherWatchItem.mWatchDataList.add(item);
					index++;
					if(D2EConfigures.TEST){
						Log.e("mOtherWatchItem.mWatchDataList========>", ""+(mOtherWatchItem.mWatchDataList.size()));
						for(MyWatchDataItem item2:mOtherWatchItem.mWatchDataList){
							Log.e("OtherWatchItem===========>", ""+item2.toString());
						}
					}
					
					
					if(mOtherWatchItem!=null){
						if(D2EConfigures.TEST){
							Log.e("!(JJBaseApplication.mOtherWatchDataList.size()>0========>", ""+(!(JJBaseApplication.mOtherWatchDataList.size()>0)));
						}
						if(!(JJBaseApplication.mOtherWatchDataList.size()>0)){
							JJBaseApplication.mOtherWatchDataList.add(mOtherWatchItem);
						}
					}
					if(D2EConfigures.TEST){
						Log.e("JJBaseApplication.mOtherWatchDataList========>", ""+(JJBaseApplication.mOtherWatchDataList.size()));
					}
				}
			});
			builder.setNegativeButton(getResources().getString(R.string.dialog_btn_cancle_text), new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
				}
			});
			AlertDialog dialog=builder.create();
			dialog.show();
		}
		
	}
	/**
	 * 飞虫勘查监听器
	 */
	private class FlyInsectWatchListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			Intent intent =new Intent(D2ENowPlaceDateCollectActivity.this,D2EFlyInsectWatchActivity.class);
			D2ENowPlaceDateCollectActivity.this.startActivity(intent);
			overridePendingTransition(R.anim.fade, R.anim.hold);
		}
		
	}
	/**
	 * 蟑螂勘查监听器
	 */
	private class CockRoachListener implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			Intent intent =new Intent(D2ENowPlaceDateCollectActivity.this,D2ECockRoachWatchActivity.class);
			D2ENowPlaceDateCollectActivity.this.startActivity(intent);
			overridePendingTransition(R.anim.fade, R.anim.hold);
		}
		
	}
	/**
	 * 鼠类勘查监听器
	 */
	private class MouseWatchListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			Intent intent =new Intent(D2ENowPlaceDateCollectActivity.this,D2EMouseWatchActivity.class);
			D2ENowPlaceDateCollectActivity.this.startActivity(intent);
			overridePendingTransition(R.anim.fade, R.anim.hold);
		}
		
	}
	
	
	/**
	 * 初始化共控件 通过id找到控件
	 */
	private void initView(){
		
		
		
		et_place_data_cust_name= (AutoCompleteTextView) this.findViewById(R.id.et_place_data_cust_name);
		et_place_data_cust_name.setEnabled(false);
		ib_place_data_cust_name=(ImageButton) this.findViewById(R.id.ib_place_data_cust_name);
		
		et_place_data_coll_date=(EditText) this.findViewById(R.id.et_place_data_coll_date);
		//日期直接获取系统的日期
		Date now=new Date();
		SimpleDateFormat mFormate=new SimpleDateFormat("yyyy-MM-dd");
		if(D2EConfigures.TEST){
			Log.e("now========>", ""+(mFormate.format(now)));
		}
		et_place_data_coll_date.setText(mFormate.format(now));
		
		et_place_data_coll_people=(EditText) this.findViewById(R.id.et_place_data_coll_people);
		et_place_data_coll_people.setText(JJBaseApplication.current_user_name);
		et_place_data_coll_conn=(EditText) this.findViewById(R.id.et_place_data_coll_conn);
		et_place_data_coll_addr=(EditText) this.findViewById(R.id.et_place_data_coll_addr);
		et_place_data_coll_peitong=(EditText) this.findViewById(R.id.et_place_data_coll_peitong);
		
		
		panel_mouse_watch=(RelativeLayout) this.findViewById(R.id.panel_mouse_watch);
		panel_cockroach_watch=(RelativeLayout) this.findViewById(R.id.panel_cockroach_watch);
		panel_fly_insect_watch=(RelativeLayout) this.findViewById(R.id.panel_fly_insect_watch);
		panel_other_insect_watch=(RelativeLayout) this.findViewById(R.id.panel_other_insect_watch);
		panel_watch_result=(RelativeLayout) this.findViewById(R.id.panel_watch_result);
		
		btn_submit_data_coll_fun=(Button) this.findViewById(R.id.btn_submit_data_coll_fun);
		btn_reset_data_coll_fun=(Button) this.findViewById(R.id.btn_reset_data_coll_fun);
		btn_back_data_coll_fun=(Button) this.findViewById(R.id.btn_back_data_coll_fun);
		
		
		
		mTagsAdapter= new D2EListAdapter(getApplicationContext());
		
	}

	@Override
	public void backAciton() {
		//这里判断从哪里进入的，如果是从主页进入的则，返回主页
		Intent intent=new Intent(D2ENowPlaceDateCollectActivity.this,D2EMainScreen.class);
		D2ENowPlaceDateCollectActivity.this.startActivity(intent);
		overridePendingTransition(R.anim.fade, R.anim.hold);
		D2ENowPlaceDateCollectActivity.this.finish();
		
		
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
		if(values == JJBaseService.HTTP_SERVICE_INT){
		    JJTask task = (JJTask)param[1];
		    String response = null;
		    try{
			    InputStream ins = task.getInputStream();
			    byte[] bytes = JJNetHelper.readByByte(ins, -1);
			
			    response = new String(bytes, "UTF-8");
			    if(D2EConfigures.TEST){
			    	Log.e("reponse-------->", ""+response);
			    }
                ins.close();
                if(response!=null&&response.length()>0){
                	JSONObject jo=new JSONObject(response);
	                if(D2EConfigures.TEST){
	                	Log.e("response: >>>>>> ", ""+jo);
	                	if(jo.has("success")){
	                		Log.e("success", jo.getString("success"));
	                	}
	                }
                }
		    }catch(Exception e){
		    
		    }
		    
		    
		    if(task.getTaskId() == D2EConfigures.TASK_INSERT_WATCH_DATA){
		    	if(D2EConfigures.TEST){
		    		Log.e("TASK_INSERT_WATCH_DATA------------->", "TASK_INSERT_WATCH_DATA");
		    	}
		    	 if(response != null && response.length() > 0){
		    		
		    		 String success=null;
		    		 String error=null;
	            	try{
	            		JSONObject jo=new JSONObject(response);
	            		if(jo.has("success")){
	            			success=jo.getString("success");
	            			((JJBaseApplication)getApplication()).showMessage(success);
	            		}
	            		
	            	}catch(Exception e){
	            		try {
	            			JSONObject jo=new JSONObject(response);
	            			if(jo.has("error")){
	            				error=jo.getString("error");
	            				((JJBaseApplication)getApplication()).showMessage(error);
	            			}
						} catch (JSONException e1) {
							e1.printStackTrace();
						}
	            		
            		}
	            	 //清空数据
		    		 clearData();
		    		 //提交成功要结束界面
					Intent intent=new Intent(D2ENowPlaceDateCollectActivity.this,D2EMainScreen.class);
					D2ENowPlaceDateCollectActivity.this.startActivity(intent);
					overridePendingTransition(R.anim.fade, R.anim.hold);
					D2ENowPlaceDateCollectActivity.this.finish();
		    }else if(task.getTaskId() == D2EConfigures.TASK_GET_ALL_CUSTOM){
		    	JJBaseApplication.mWatchClientList.clear();
		    	try{
		    		JSONArray ja=new JSONArray(response);
		    		int size=ja.length();
		    		for(int i=0;i<size;i++){
		    			String tmp=ja.getString(i);
		    			JSONObject jo=new JSONObject(tmp);
		    			D2EListAdapterItam item=new D2EListAdapterItam(jo.getString("Name"), jo.getString("ID"));
		    			item.setmCustomerContact(jo.getString("Manager"));
		    			JJBaseApplication.mWatchClientList.add(item);
		    		}
		    	}catch(Exception e){
		    		
		    	}
		    	for(D2EListAdapterItam item:JJBaseApplication.mWatchClientList){
					mTagsAdapter.addObject(item);
					mTagsAdapter.notifyDataSetChanged();
				}
		    }
		 }
		}
		
	}
	/**
	 * 清空数据
	 */
	private void clearData() {
		JJBaseApplication.isAddMousWatch=false;
		JJBaseApplication.isAddCockroachWatch=false;
		JJBaseApplication.isAddFlyWatch=false;
		JJBaseApplication.mWatchDataList.clear();
		mOtherWatchItem=null;
		mImportWatchItem=null;
		JJBaseApplication.mOtherWatchDataList.clear();
		JJBaseApplication.mImportWatchResulDatatList.clear();
	}

	@Override
	public void taskFailed(Object... param) {
		
	}

	@Override
	public void taskProcessing(Object... param) {
		
	}

}
