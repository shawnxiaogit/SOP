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
 * �ֳ����������ռ�����
 * @author ShawnXiao
 *
 */
public class D2ENowPlaceDateCollectActivity extends JJBaseActivity {
	
	/**
	 * �ͻ����Ʊ༭��
	 */
	private AutoCompleteTextView et_place_data_cust_name;
	/**
	 * �ͻ�����������ť
	 */
	private ImageButton ib_place_data_cust_name;
	
	/**
	 * �����ֳ����ڱ༭��
	 */
	private EditText et_place_data_coll_date;
	/**
	 * ������Ա�༭��
	 */
	private EditText et_place_data_coll_people;
	/**
	 * ����ͻ���ϵ�˱༭��
	 */
	private EditText et_place_data_coll_conn;
	/**
	 * �����ֳ���ַ�༭��
	 */
	private EditText et_place_data_coll_addr;
	/**
	 * �ͻ��㷽��ͬ��Ա�༭��
	 */
	private EditText et_place_data_coll_peitong;
	
	
	/**
	 * ���࿱�鹦������
	 */
	private RelativeLayout panel_mouse_watch;
	/**
	 * ��뿱�鹦������
	 */
	private RelativeLayout panel_cockroach_watch;
	/**
	 * �ɳ濱�鹦������
	 */
	private RelativeLayout panel_fly_insect_watch;
	/**
	 * �ֳ����鷢�ֵ������溦��������
	 */
	private RelativeLayout panel_other_insect_watch;
	/**
	 * �ص����򿱲�����������
	 */
	private RelativeLayout panel_watch_result;
	
	
	/**
	 * �ύ���ܰ�ť
	 */
	private Button btn_submit_data_coll_fun;
	/**
	 * �м䰴ť
	 */
	private Button btn_reset_data_coll_fun;
	/**
	 * ���ع��ܰ�ť
	 */
	private Button btn_back_data_coll_fun;
	
	
	
	private BadgeView     mBadgeView;
	
	//--------------------------------------
	/**
	 * �ͻ�����
	 */
	private String mCustomerName;
	/**
	 * �����ֳ�����
	 */
	private String mWatchDate;
	/**
	 * ������Ա
	 */
	private String mWatchPeople;
	/**
	 * ����ͻ���ϵ��
	 */
	private String mWatchContact;
	/**
	 * �ͻ��ֳ���ַ
	 */
	private String mWatchAddress;
	/**
	 * �ͻ�����ͬ��Ա
	 */
	private String mAccompany;
	
	//--------------------------------------
	
	
	//�ͻ�����������
	private D2EListAdapter mTagsAdapter;
	
	//=========================================
	/**
	 * �������࿱������
	 */
	private static final int REQUEST_MOUSE_WATCH=1;
	/**
	 * ������뿱������
	 */
	private static final int REQUEST_COCKROACH_WATCH=2;
	/**
	 * ����ɳ濱������
	 */
	private static final int REQUEST_FLYING_WATCH=3;
	
	
	/**
	 * �����溦����������
	 */
	private MyWatchDataItem mOtherWatchItem;
	/**
	 * �ص����򿱲���������
	 */
	private MyWatchDataItem mImportWatchItem;
	//=========================================
	
	/**
	 * �������������
	 */
	private int index=0;
	/**
	 * �ص����򿱲�����
	 */
	private int indexImport=0;
	
	
	/**
	 * ��ȡ������Ϣ
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
	 * �ж���Ϣ�Ƿ���д����
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
		//ʹ�ÿͻ��Զ��������
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.d2e_palce_data_coll);
		//�����û��Զ��������
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.d2e_titlebar);
		TextView tv = (TextView)findViewById(R.id.title);
		//�����Լ��ı�������
        tv.setText(getResources().getString(R.string.now_watch));
        mBadgeView = (BadgeView)findViewById(R.id.countTask);
        mBadgeView.setVisibility(View.GONE);
        //��ʼ���ؼ�
        initView();
        //��ȡ�ͻ��б���Ϣ
        getCustomerData();
        //Ϊ���ܿؼ����ü�����
        setUpViewListener();
        
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		//���������������������ټ��������
		if(JJBaseApplication.isAddMousWatch){
			panel_mouse_watch.setClickable(false);
		}
		//��������������������ټ��������
		if(JJBaseApplication.isAddCockroachWatch){
			panel_cockroach_watch.setClickable(false);
		}
		//�������˷ɳ����������ټ��������
		if(JJBaseApplication.isAddFlyWatch){
			panel_fly_insect_watch.setClickable(false);
		}
	}
	
	/**
	 * ��ȡ�ͻ��б���Ϣ
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
	 * Ϊ���ܿؼ����ü�����
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
	 * Ϊ�ͻ��������������Ϣ
	 */
	private void setUpCustomNameState(){
		et_place_data_cust_name.setThreshold(1);
		et_place_data_cust_name.setAdapter(mTagsAdapter);
		et_place_data_cust_name.addTextChangedListener(new MyTextChangeListener());
		et_place_data_cust_name.setOnItemClickListener(new MyCustomItemClickListener());
		ib_place_data_cust_name.setOnClickListener(new IbtnDropDownListner());
	}
	/**
	 * �ͻ�������ť������
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
	 * �ͻ��б��������
	 */
	private class MyCustomItemClickListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long id) {
			D2EListAdapterItam item=(D2EListAdapterItam) mTagsAdapter.getItem(position);
			et_place_data_cust_name.setText(item.getTitle());
			et_place_data_coll_conn.setText(item.getmCustomerContact());
			//����̽�ͻ���id
			JJBaseApplication.user_SalesClientID=item.getId();
		}
		
	}
	/**
	 * �ı��ı������
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
	 * �ύ��ť������
	 */
	private class SubmitListener implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			sendDataToServer();
		}
		
	}
	/**
	 * ģ������
	 */
	private String simulateDate(){
		StringBuffer sb=new StringBuffer();
		sb.append("{");
		sb.append("\"").append("CustomInfo").append("\"").append(":");
		sb.append("{");
		sb.append("\"").append("CustomName").append("\"").append(":").append("\"").append("ΰ����˾").append("\"").append(",");
		sb.append("\"").append("PorspectTime").append("\"").append(":").append("\"").append("2012-11-07 17:57").append("\"").append(",");
		sb.append("\"").append("ProspectPeople").append("\"").append(":").append("\"").append("ShawnXiao").append("\"").append(",");
		sb.append("\"").append("ProspectContact").append("\"").append(":").append("\"").append("Kris").append("\"").append(",");
		sb.append("\"").append("ProspectAddr").append("\"").append(":").append("\"").append("�������300��").append("\"").append(",");
		sb.append("\"").append("ProspectAccompany").append("\"").append(":").append("\"").append("Tina").append("\"");
		sb.append("}").append(",");
		
		sb.append("\"").append("ProspectData").append("\"").append(":").append("{");
		sb.append("\"").append("Mouse").append("\"").append(":").append("{");
		
		sb.append("\"").append("0").append("\"").append(":").append("[");
		sb.append("\"").append("1").append("\"").append(",").append("\"").append("1").append("\"");
		sb.append(",").append("\"").append("1").append("\"").append(",").append("\"").append("������").append("\"");
		sb.append("]").append(",");
		
		sb.append("\"").append("1").append("\"").append(":").append("[");
		sb.append("\"").append("1").append("\"").append(",").append("\"").append("1").append("\"");
		sb.append(",").append("\"").append("1").append("\"").append(",").append("\"").append("������").append("\"");
		sb.append("]").append(",");
		
		sb.append("\"").append("2").append("\"").append(":").append("[");
		sb.append("\"").append("1").append("\"").append(",").append("\"").append("1").append("\"");
		sb.append(",").append("\"").append("1").append("\"").append(",").append("\"").append("������").append("\"");
		sb.append("]").append(",");
		
		sb.append("\"").append("3").append("\"").append(":").append("[");
		sb.append("\"").append("1").append("\"").append(",").append("\"").append("1").append("\"");
		sb.append(",").append("\"").append("1").append("\"").append(",").append("\"").append("������").append("\"");
		sb.append("]").append(",");
		
		sb.append("\"").append("4").append("\"").append(":").append("[");
		sb.append("\"").append("1").append("\"").append(",").append("\"").append("1").append("\"");
		sb.append(",").append("\"").append("1").append("\"").append(",").append("\"").append("������").append("\"");
		sb.append("]").append(",");
		
		sb.append("\"").append("5").append("\"").append(":").append("[");
		sb.append("\"").append("1").append("\"").append(",").append("\"").append("1").append("\"");
		sb.append(",").append("\"").append("1").append("\"").append(",").append("\"").append("������").append("\"");
		sb.append("]").append(",");
		
		sb.append("\"").append("6").append("\"").append(":").append("[");
		sb.append("\"").append("1").append("\"").append(",").append("\"").append("1").append("\"");
		sb.append(",").append("\"").append("1").append("\"").append(",").append("\"").append("������").append("\"");
		sb.append("]").append(",");
		
		sb.append("\"").append("7").append("\"").append(":").append("[");
		sb.append("\"").append("1").append("\"").append(",").append("\"").append("1").append("\"");
		sb.append(",").append("\"").append("1").append("\"").append(",").append("\"").append("������").append("\"");
		sb.append("]").append(",");
		
		sb.append("\"").append("8").append("\"").append(":").append("[");
		sb.append("\"").append("1").append("\"").append(",").append("\"").append("1").append("\"");
		sb.append(",").append("\"").append("1").append("\"").append(",").append("\"").append("������").append("\"");
		sb.append("]").append(",");
		
		sb.append("\"").append("9").append("\"").append(":").append("[");
		sb.append("\"").append("1").append("\"").append(",").append("\"").append("1").append("\"");
		sb.append(",").append("\"").append("1").append("\"").append(",").append("\"").append("������").append("\"");
		sb.append("]").append(",");
		
		sb.append("\"").append("10").append("\"").append(":").append("[");
		sb.append("\"").append("1").append("\"").append(",").append("\"").append("1").append("\"");
		sb.append(",").append("\"").append("1").append("\"").append(",").append("\"").append("������").append("\"");
		sb.append("]").append(",");
		
		
		sb.append("\"").append("11").append("\"").append(":").append("[");
		sb.append("\"").append("1").append("\"").append(",").append("\"").append("1").append("\"");
		sb.append(",").append("\"").append("1").append("\"").append(",").append("\"").append("������").append("\"");
		sb.append("]").append(",");
		
		sb.append("\"").append("12").append("\"").append(":").append("[");
		sb.append("\"").append("1").append("\"").append(",").append("\"").append("1").append("\"");
		sb.append(",").append("\"").append("1").append("\"").append(",").append("\"").append("������").append("\"");
		sb.append("]").append(",");
		
		sb.append("\"").append("13").append("\"").append(":").append("[");
		sb.append("\"").append("1").append("\"").append(",").append("\"").append("1").append("\"");
		sb.append(",").append("\"").append("1").append("\"").append(",").append("\"").append("������").append("\"");
		sb.append("]").append(",");
		
		sb.append("\"").append("14").append("\"").append(":").append("[");
		sb.append("\"").append("1").append("\"").append(",").append("\"").append("1").append("\"");
		sb.append(",").append("\"").append("1").append("\"").append(",").append("\"").append("������").append("\"");
		sb.append("]").append(",");
		
		sb.append("\"").append("15").append("\"").append(":").append("[");
		sb.append("\"").append("1").append("\"").append(",").append("\"").append("1").append("\"");
		sb.append(",").append("\"").append("1").append("\"").append(",").append("\"").append("������").append("\"");
		sb.append("]").append(",");
		
		sb.append("\"").append("16").append("\"").append(":").append("[");
		sb.append("\"").append("1").append("\"").append(",").append("\"").append("1").append("\"");
		sb.append(",").append("\"").append("1").append("\"").append(",").append("\"").append("������").append("\"");
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
		sb.append("\"").append("Name").append("\"").append(":").append("\"").append("����").append("\"").append(",");
		sb.append("\"").append("Memo").append("\"").append(":").append("\"").append("�žͰ����").append("\"");
		sb.append("}").append(",");
		sb.append("{");
		sb.append("\"").append("Index").append("\"").append(":").append("\"").append("1").append("\"").append(",");
		sb.append("\"").append("Name").append("\"").append(":").append("\"").append("����").append("\"").append(",");
		sb.append("\"").append("Memo").append("\"").append(":").append("\"").append("�žͰ����").append("\"");
		sb.append("}");
		sb.append("]").append(",");
		
		sb.append("\"").append("ImportArea").append("\"").append(":").append("[");
		sb.append("{");
		sb.append("\"").append("Area").append("\"").append(":").append("\"").append("�����").append("\"").append(",");
		sb.append("\"").append("Memo").append("\"").append(":").append("\"").append("�žͰ����").append("\"");
		sb.append("}").append(",");
		
		sb.append("{");
		sb.append("\"").append("Area").append("\"").append(":").append("\"").append("�����").append("\"").append(",");
		sb.append("\"").append("Memo").append("\"").append(":").append("\"").append("�žͰ����").append("\"");
		sb.append("}");
		
		sb.append("]");
		sb.append("}");
		sb.append("}");
		
		
		return sb.toString();
	}
	/**
	 * �����������ȡ����
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
				
				//���������Ļ�����Ҫ�ж����Ƿ�������
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
	 * �������ݵ�������
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
	 * ���ذ�ť������
	 */
	private class BackListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			//�ж��Ƿ�������δ�ύ
			if(JJBaseApplication.mWatchDataList.size()>0||JJBaseApplication.mOtherWatchDataList.size()>0||JJBaseApplication.mImportWatchResulDatatList.size()>0){
				AlertDialog.Builder builder=new Builder(D2ENowPlaceDateCollectActivity.this);
				builder.setTitle(getResources().getString(R.string.data_is_not_submit));
				builder.setMessage(getResources().getString(R.string.is_submit_data));
				
				//ȷ����ʱ��������ύ
				builder.setPositiveButton(getResources().getString(R.string.dialog_btn_yes_text), new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						sendDataToServer();
					}
				});
				//ȡ����ʱ����������
				builder.setNegativeButton(getResources().getString(R.string.dialog_btn_cancle_text), new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						clearData();
					}
				});
				Dialog dialog=builder.create();
				dialog.show();
			}else{
				//�����жϴ��������ģ�����Ǵ���ҳ������򣬷�����ҳ
				Intent intent=new Intent(D2ENowPlaceDateCollectActivity.this,D2EMainScreen.class);
				D2ENowPlaceDateCollectActivity.this.startActivity(intent);
				overridePendingTransition(R.anim.fade, R.anim.hold);
				D2ENowPlaceDateCollectActivity.this.finish();
			}
		}
		
	}
	/**
	 * �ص����򿱲���������
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
	 * �����溦���������
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
	 * �ɳ濱�������
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
	 * ��뿱�������
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
	 * ���࿱�������
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
	 * ��ʼ�����ؼ� ͨ��id�ҵ��ؼ�
	 */
	private void initView(){
		
		
		
		et_place_data_cust_name= (AutoCompleteTextView) this.findViewById(R.id.et_place_data_cust_name);
		et_place_data_cust_name.setEnabled(false);
		ib_place_data_cust_name=(ImageButton) this.findViewById(R.id.ib_place_data_cust_name);
		
		et_place_data_coll_date=(EditText) this.findViewById(R.id.et_place_data_coll_date);
		//����ֱ�ӻ�ȡϵͳ������
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
		//�����жϴ��������ģ�����Ǵ���ҳ������򣬷�����ҳ
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
	            	 //�������
		    		 clearData();
		    		 //�ύ�ɹ�Ҫ��������
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
	 * �������
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
