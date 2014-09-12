package com.mobilercn.sop.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.content.DialogInterface.OnKeyListener;

import com.mobilercn.base.BadgeView;
import com.mobilercn.base.JJBaseActivity;
import com.mobilercn.base.JJBaseApplication;
import com.mobilercn.config.D2EConfigures;
import com.mobilercn.sop.R;
import com.mobilercn.sop.data.MyWatchDataItem;
import com.mobilercn.sop.data.MyWatchKindAdapter;
import com.mobilercn.sop.data.MyWatchKindAdapter.ViewHolder;

/**
 * �ɳ濱�������ռ�����
 * @author ShawnXiao
 *
 */
public class D2EFlyInsectWatchActivity extends JJBaseActivity {
	/**
	 * �ֳ�����ʱ���ֵķɳ�༭��
	 */
	private AutoCompleteTextView at_fly_insect_watch_kind;
	/**
	 * �ֳ�����ʱ���ֵķɳ�������ť
	 */
	private ImageButton ibtn_fly_insect_watch_kind;
	/**
	 * �����Ŵ���ͨ���Ƿ���Ϸ��ɳ�Ҫ��༭��
	 */
	private AutoCompleteTextView at_fly_insect_watch_room_in_requ;
	/**
	 * �����Ŵ���ͨ���Ƿ���Ϸ��ɳ�Ҫ��������ť
	 */
	private ImageButton ibtn_fly_insect_watch_room_in_requ;
	/**
	 * ���������Ƿ�¶�ѷű༭��
	 */
	private AutoCompleteTextView at_fly_insect_room_in_rubb_bare;
	/**
	 * ���������Ƿ�¶�ѷ�������ť
	 */
	private ImageButton ibtn_fly_insect_room_in_rubb_bare;
	/**
	 * �����������Ƿ����������׼�༭��
	 */
	private AutoCompleteTextView at_panel_fly_insect_room_in_hygiene_stan; 
	/**
	 * �����������Ƿ����������׼������ť
	 */
	private ImageButton ibtn_panel_fly_insect_room_in_hygiene_stan;
	/**
	 * ���ڵ��桢��ˮ���з��ˮ�༭��
	 */
	private AutoCompleteTextView at_fly_insect_room_in_hydrops;
	/**
	 * ���ڵ��桢��ˮ���з��ˮ������ť
	 */
	private ImageButton ibtn_fly_insect_room_in_hydrops;
	/**
	 * ���ڽ���ͨ����������з���������Ļ�������Ƶ���ʩ������
	 */
	private AutoCompleteTextView at_fly_insect_room_in_has_equip;
	/**
	 * ���ڽ���ͨ����������з���������Ļ�������Ƶ���ʩ������ť
	 */
	private ImageButton ibtn_fly_insect_room_in_has_equip;
	/**
	 * ���⽨���ܱ��з���������ˮ�����ɳ�༭��
	 */
	private AutoCompleteTextView at_fly_insect_room_out_rubb;
	/**
	 * ���⽨���ܱ��з���������ˮ�����ɳ�������ť
	 */
	private ImageButton ibtn_fly_insect_room_out_rubb;
	/**
	 * ���⽨���ܱ��з�·�ơ�����������ɳ�༭��
	 */
	private AutoCompleteTextView at_fly_insect_room_out_light;
	/**
	 * ���⽨���ܱ��з�·�ơ�����������ɳ�������ť
	 */
	private ImageButton ibtn_fly_insect_room_out_light;
	/**
	 * ���������ƶ��Ƿ����Ʊ༭��
	 */
	private AutoCompleteTextView at_fly_insect_envir_syst_perf;
	/**
	 * ���������ƶ��Ƿ�����������ť
	 */
	private ImageButton ibtn_fly_insect_envir_syst_perf;
	/**
	 * ������ʩ�Ƿ���Ϸ��ɳ��Ҫ��༭��
	 */
	private AutoCompleteTextView at_fly_insect_equip_requ;
	/**
	 * ������ʩ�Ƿ���Ϸ��ɳ��Ҫ��������ť
	 */
	private ImageButton ibtn_fly_insect_equip_requ;
	/**
	 * �ͻ������������з�ɳ����ַ��ձ༭��
	 */
	private AutoCompleteTextView at_fly_insect_pipeline_risk;
	/**
	 * �ͻ������������з�ɳ����ַ���������ť
	 */
	private ImageButton ibtn_fly_insect_pipeline_risk;
	/**
	 * �ͻ����з�ɳ��ۺϷ��Ƶ�����༭��
	 */
	private AutoCompleteTextView at_fly_insect_prevention_think;
	/**
	 * �ͻ����з�ɳ��ۺϷ��Ƶ�����������ť
	 */
	private ImageButton ibtn_fly_insect_prevention_think;
	
	
	//==============���ܰ�ť=====================
	/**
	 * ���水ť
	 */
	private Button btn_save_fly_insect_watch;
	/**
	 * ���ذ�ť
	 */
	private Button btn_back_fly_insect_watch;
	
	//=========================================
	
	
	/**
	 * �ɳ�������
	 */
	private MyWatchDataItem mFlyInsectItem;
	/**
	 * ������������
	 */
	private MyWatchKindAdapter mFlyInsectKindAdapter=null;
	/**
	 * ����
	 */
	private String[] mFlyKindData=null;
	/**
	 * �����б�
	 */
	private List<HashMap<String,Object>> listData=null;
	/**
	 * ������ͳ���б�
	 */
	private List<String> mCountList=null;
	/**
	 * ���������ϴα������Ϣ
	 */
	private String previous_kind="";
	
	
	
	/**
	 * ����״̬������
	 */
	private ArrayAdapter mOtherStateAdapter1;
	private ArrayAdapter mOtherStateAdapter2;
	private ArrayAdapter mOtherStateAdapter3;
	private ArrayAdapter mOtherStateAdapter4;
	private ArrayAdapter mOtherStateAdapter5;
	private ArrayAdapter mOtherStateAdapter6;
	private ArrayAdapter mOtherStateAdapter7;
	private ArrayAdapter mOtherStateAdapter8;
	private ArrayAdapter mOtherStateAdapter9;
	private ArrayAdapter mOtherStateAdapter10;
	private ArrayAdapter mOtherStateAdapter11;
	/**
	 * �Ƿ��·��ؼ�
	 */
	private boolean isBackPressed=false;
	
	/**
	 * ��������Сͼ��
	 */
	private BadgeView     mBadgeView;
	//================�������ؿؼ�===============
	//0
	private RelativeLayout panel_fly_insect_watch_kind;
	private TextView tv_fly_insect_watch_kind;
	
	//1
	private RelativeLayout panel_fly_insect_watch_room_in_requ;
	private TextView tv_fly_insect_watch_room_in_requ;
	
	//2
	private RelativeLayout panel_fly_insect_room_in_rubb_bare;
	private TextView tv_fly_insect_room_in_rubb_bare;
	
	//3
	private RelativeLayout panel_fly_insect_room_in_hygiene_stan;
	private TextView tv_panel_fly_insect_room_in_hygiene_stan;
	
	//4
	private RelativeLayout panel_fly_insect_room_in_hydrops;
	private TextView tv_fly_insect_room_in_hydrops;
	
	//5
	private RelativeLayout panel_fly_insect_room_in_has_equip;
	private TextView tv_fly_insect_room_in_has_equip;
	
	//6
	private RelativeLayout panel_fly_insect_room_out_rubb;
	private TextView tv_fly_insect_room_out_rubb;
	
	//7
	private RelativeLayout panel_fly_insect_room_out_light;
	private TextView tv_fly_insect_room_out_light;
	
	//8
	private RelativeLayout panel_fly_insect_envir_syst_perf;
	private TextView tv_fly_insect_envir_syst_perf;
	
	//9
	private RelativeLayout panel_fly_insect_equip_requ;
	private TextView tv_fly_insect_equip_requ;
	
	//10
	private RelativeLayout panel_fly_insect_pipeline_risk;
	private TextView tv_fly_insect_pipeline_risk;
	
	//11
	private RelativeLayout panel_fly_insect_prevention_think;
	private TextView tv_fly_insect_prevention_think;
	
	
	//================�������ؿؼ�===============
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ʹ�ÿͻ��Զ��������
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.d2e_fly_insect_watch);
		//�����û��Զ��������
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.d2e_titlebar);
		TextView tv = (TextView)findViewById(R.id.title);
		//�����Լ��ı�������
        tv.setText(getResources().getString(R.string.fly_insect_watch_title));
        mBadgeView = (BadgeView)findViewById(R.id.countTask);
        mBadgeView.setVisibility(View.GONE);
        
        //��ʼ���ؼ�
        initView();
        //Ϊ�����AutoAutoCompleteTextView����������
        setUpAutoTextAdapter();
        //Ϊ���ܰ�ť���ü�����
        setUpBtnListener();
	}
	/**
	 * Ϊ�����AutoAutoCompleteTextView����������
	 */
	private void setUpAutoTextAdapter(){
		
		at_fly_insect_watch_room_in_requ.setThreshold(1);
		at_fly_insect_watch_room_in_requ.setAdapter(mOtherStateAdapter1);
		
		at_fly_insect_room_in_rubb_bare.setThreshold(1);
		at_fly_insect_room_in_rubb_bare.setAdapter(mOtherStateAdapter2);
		
		at_panel_fly_insect_room_in_hygiene_stan.setThreshold(1);
		at_panel_fly_insect_room_in_hygiene_stan.setAdapter(mOtherStateAdapter3);
		
		at_fly_insect_room_in_hydrops.setThreshold(1);
		at_fly_insect_room_in_hydrops.setAdapter(mOtherStateAdapter4);
		
		at_fly_insect_room_in_has_equip.setThreshold(1);
		at_fly_insect_room_in_has_equip.setAdapter(mOtherStateAdapter5);
		
		at_fly_insect_room_out_rubb.setThreshold(1);
		at_fly_insect_room_out_rubb.setAdapter(mOtherStateAdapter6);
		
		at_fly_insect_room_out_light.setThreshold(1);
		at_fly_insect_room_out_light.setAdapter(mOtherStateAdapter7);
		
		at_fly_insect_envir_syst_perf.setThreshold(1);
		at_fly_insect_envir_syst_perf.setAdapter(mOtherStateAdapter8);
		
		at_fly_insect_equip_requ.setThreshold(1);
		at_fly_insect_equip_requ.setAdapter(mOtherStateAdapter9);
		
		at_fly_insect_pipeline_risk.setThreshold(1);
		at_fly_insect_pipeline_risk.setAdapter(mOtherStateAdapter10);
		
		at_fly_insect_prevention_think.setThreshold(1);
		at_fly_insect_prevention_think.setAdapter(mOtherStateAdapter11);
		
	}
	/**
	 * ��ʾ����Ի���
	 */
	private void showWarmDialog(){
		isBackPressed=false;
		AlertDialog.Builder builder=new Builder(D2EFlyInsectWatchActivity.this);
		builder.setTitle(getResources().getString(R.string.warm_prompt));
		builder.setMessage(getResources().getString(R.string.quit));
		builder.setIcon(R.drawable.msg_dlg_warning);
		//ȷ����ʱ�򱣴�����
		builder.setPositiveButton(getResources().getString(R.string.quit_ok), new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				overView();
			}
		});
		//ȡ����ʱ���˳�����
		builder.setNegativeButton(getResources().getString(R.string.quit_cancel), new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		Dialog dialog=builder.create();
		dialog.show();
	}
	/**
	 * �Ƿ���������еļ��
	 */
	private boolean isAllCheck(){
		String str0=at_fly_insect_watch_kind.getText().toString();
		String str1=at_fly_insect_watch_room_in_requ.getText().toString();
		String str2=at_fly_insect_room_in_rubb_bare.getText().toString();
		String str3=at_panel_fly_insect_room_in_hygiene_stan.getText().toString();
		String str4=at_fly_insect_room_in_hydrops.getText().toString();
		String str5=at_fly_insect_room_in_has_equip.getText().toString();
		String str6=at_fly_insect_room_out_rubb.getText().toString();
		String str7=at_fly_insect_room_out_light.getText().toString();
		String str8=at_fly_insect_envir_syst_perf.getText().toString();
		String str9=at_fly_insect_equip_requ.getText().toString();
		String str10=at_fly_insect_pipeline_risk.getText().toString();
		String str11=at_fly_insect_prevention_think.getText().toString();
		if((str0!=null&&str0.length()>0)||
				(str1!=null&&str1.length()>0)||
				(str2!=null&&str2.length()>0)||
				(str3!=null&&str3.length()>0)||
				(str4!=null&&str4.length()>0)||
				(str5!=null&&str5.length()>0)||
				(str6!=null&&str6.length()>0)||
				(str7!=null&&str7.length()>0)||
				(str8!=null&&str8.length()>0)||
				(str9!=null&&str9.length()>0)||
				(str10!=null&&str10.length()>0)||
				(str11!=null&&str11.length()>0)){
			return true;
		}
				
		return false;
	}
	/**
	 * Ϊ���ܰ�ť���ü�����
	 */
	private void setUpBtnListener(){
		ibtn_fly_insect_watch_kind.setOnClickListener(new IbtnFlyInsectWatchKindListener());
		
		at_fly_insect_watch_room_in_requ.setOnItemClickListener(new AtFlyInsectWatchRoomInRequListener());
		ibtn_fly_insect_watch_room_in_requ.setOnClickListener(new IbtnFlyInsectWatchRoomInRequListener());
		
		at_fly_insect_room_in_rubb_bare.setOnItemClickListener(new AtFlyInsectRoomInRubbBareListener());
		ibtn_fly_insect_room_in_rubb_bare.setOnClickListener(new IbntFlyInsectRoomInRubbBareListener());
		
		at_panel_fly_insect_room_in_hygiene_stan.setOnItemClickListener(new AtPanelFlyInsectRoomInHygieneStanListener());
		ibtn_panel_fly_insect_room_in_hygiene_stan.setOnClickListener(new IbtnPanelFlyInsectRoomInHygieneStanListener());
		
		at_fly_insect_room_in_hydrops.setOnItemClickListener(new AtFlyInsectRoomInHydropsListener());
		ibtn_fly_insect_room_in_hydrops.setOnClickListener(new IbtnFlyInsectRoomInHydropsListener());
		
		at_fly_insect_room_in_has_equip.setOnItemClickListener(new AtFlyInsectRoomInHasEquipListener());
		ibtn_fly_insect_room_in_has_equip.setOnClickListener(new IbtnFlyInsectRoomInHasEquipListener());
		
		at_fly_insect_room_out_rubb.setOnItemClickListener(new AtFlyInsectRoomOutRubbListener());
		ibtn_fly_insect_room_out_rubb.setOnClickListener(new IbtnFlyInsectRoomOutRubbListener());
		
		at_fly_insect_room_out_light.setOnItemClickListener(new AtFlyInsectRoomOutLightListener());
		ibtn_fly_insect_room_out_light.setOnClickListener(new IbtnFlyInsectRoomOutLightListener());
		
		at_fly_insect_envir_syst_perf.setOnItemClickListener(new AtFlyInsectEnvirSystPerfListener());
		ibtn_fly_insect_envir_syst_perf.setOnClickListener(new IbtnFlyInsectEnvirSystPerfListener());
		
		at_fly_insect_equip_requ.setOnItemClickListener(new AtFlyInsectEquipRequListener());
		ibtn_fly_insect_equip_requ.setOnClickListener(new IbtnFlyInsectEquipRequListener());
		
		at_fly_insect_pipeline_risk.setOnItemClickListener(new AtFlyInsectPipelineRiskListener());
		ibtn_fly_insect_pipeline_risk.setOnClickListener(new IbtnFlyInsectPipelineRiskListener());
		
		at_fly_insect_prevention_think.setOnItemClickListener(new AtFlyInsectPreventionThinkListener());
		ibtn_fly_insect_prevention_think.setOnClickListener(new IbtnFlyInsectPreventionThinkListener());
		
		
		btn_save_fly_insect_watch.setOnClickListener(new SaveListener());
		btn_back_fly_insect_watch.setOnClickListener(new BackListener());
		
		setUpLamptListener();
	}
	/**
	 * �ͻ����з�ɳ��ۺϷ��Ƶ����� 11�༭�������
	 */
	private class AtFlyInsectPreventionThinkListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			final MyWatchDataItem item=new MyWatchDataItem();
			item.setmMouseIndex(11);
			
			if(position==0){
				item.setmMouseKind1(1);
				item.setmMouseKind2(0);
				item.setmMouseKind3(0);
				item.setmMouseKind4("");
				
			}else if(position==1){
				item.setmMouseKind1(0);
				item.setmMouseKind2(1);
				item.setmMouseKind3(0);
				item.setmMouseKind4("");
			}else if(position==2){
				item.setmMouseKind1(0);
				item.setmMouseKind2(0);
				item.setmMouseKind3(1);
				item.setmMouseKind4("");
			}else if(position==3){
				//����һ���Ի���������д��ע
				LayoutInflater inflater=getLayoutInflater();
		    	View dialog_view=inflater.inflate(R.layout.dialog2, null);
		    	final EditText et_dialog2=(EditText) dialog_view.findViewById(R.id.et_dialog2);
		    	final Button btn_dialog2_yes=(Button) dialog_view.findViewById(R.id.btn_dialog2_yes);
		    	final Button btn_dialog2_no=(Button) dialog_view.findViewById(R.id.btn_dialog2_no);
				final Dialog otherFaciDialog=new Dialog(D2EFlyInsectWatchActivity.this,R.style.my_other_point_dialog);
				otherFaciDialog.setContentView(dialog_view);
				otherFaciDialog.getWindow().setLayout(300, 150);
				otherFaciDialog.show();
				btn_dialog2_yes.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						at_fly_insect_prevention_think.setText(et_dialog2.getText().toString());
						item.setmMouseKind1(0);
						item.setmMouseKind2(0);
						item.setmMouseKind3(0);
						item.setmMouseKind4(et_dialog2.getText().toString());
						otherFaciDialog.dismiss();
						if(D2EConfigures.TEST){
							Log.e("AtFlyInsectPreventionThinkxxxxxxxxxxxxx>>", ""+item.toString());
						}
						
					}
				});
				btn_dialog2_no.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						et_dialog2.setText("");
						at_fly_insect_prevention_think.setText("");
						otherFaciDialog.dismiss();				
					}
				});
				
			}
			
			if(!mFlyInsectItem.isContainWatchDataItem(item)){
				mFlyInsectItem.mWatchDataList.add(item);
			}else{
				mFlyInsectItem.deleContainWatchDataItem(item);
				mFlyInsectItem.mWatchDataList.add(item);
			}
			
			if(D2EConfigures.TEST){
				Log.e("AtFlyInsectPreventionThinkxxxxxxxxxxxx>>", ""+item.toString());
			}
		}
		
	}
	/**
	 * �ͻ����з�ɳ��ۺϷ��Ƶ����� 11�༭�������
	 */
	private class IbtnFlyInsectPreventionThinkListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			if(D2EConfigures.TEST){
				Log.e("IbtnFlyInsectPreventionThinkListener=============>", "onClick(View v)");
			}
			String str=at_fly_insect_prevention_think.getText().toString();
			if(str!=null&&str.length()>0){
				at_fly_insect_prevention_think.setText("");
			}
			at_fly_insect_prevention_think.showDropDown();
			at_fly_insect_prevention_think.setThreshold(1);
		}
		
	}
	/**
	 * �ͻ������������з�ɳ����ַ��� 10�༭�������
	 */
	private class AtFlyInsectPipelineRiskListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			final MyWatchDataItem item=new MyWatchDataItem();
			item.setmMouseIndex(10);
			
			if(position==0){
				item.setmMouseKind1(1);
				item.setmMouseKind2(0);
				item.setmMouseKind3(0);
				item.setmMouseKind4("");
				
			}else if(position==1){
				item.setmMouseKind1(0);
				item.setmMouseKind2(1);
				item.setmMouseKind3(0);
				item.setmMouseKind4("");
			}else if(position==2){
				item.setmMouseKind1(0);
				item.setmMouseKind2(0);
				item.setmMouseKind3(1);
				item.setmMouseKind4("");
			}else if(position==3){
				//����һ���Ի���������д��ע
				LayoutInflater inflater=getLayoutInflater();
		    	View dialog_view=inflater.inflate(R.layout.dialog2, null);
		    	final EditText et_dialog2=(EditText) dialog_view.findViewById(R.id.et_dialog2);
		    	final Button btn_dialog2_yes=(Button) dialog_view.findViewById(R.id.btn_dialog2_yes);
		    	final Button btn_dialog2_no=(Button) dialog_view.findViewById(R.id.btn_dialog2_no);
				final Dialog otherFaciDialog=new Dialog(D2EFlyInsectWatchActivity.this,R.style.my_other_point_dialog);
				otherFaciDialog.setContentView(dialog_view);
				otherFaciDialog.getWindow().setLayout(300, 150);
				otherFaciDialog.show();
				btn_dialog2_yes.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						at_fly_insect_pipeline_risk.setText(et_dialog2.getText().toString());
						item.setmMouseKind1(0);
						item.setmMouseKind2(0);
						item.setmMouseKind3(0);
						item.setmMouseKind4(et_dialog2.getText().toString());
						otherFaciDialog.dismiss();
						if(D2EConfigures.TEST){
							Log.e("AtFlyInsectPipelineRiskxxxxxxxxxxxxx>>", ""+item.toString());
						}
						
					}
				});
				btn_dialog2_no.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						et_dialog2.setText("");
						at_fly_insect_pipeline_risk.setText("");
						otherFaciDialog.dismiss();				
					}
				});
				
			}
			
			if(!mFlyInsectItem.isContainWatchDataItem(item)){
				mFlyInsectItem.mWatchDataList.add(item);
			}else{
				mFlyInsectItem.deleContainWatchDataItem(item);
				mFlyInsectItem.mWatchDataList.add(item);
			}
			
			if(D2EConfigures.TEST){
				Log.e("AtFlyInsectPipelineRiskxxxxxxxxxxxx>>", ""+item.toString());
			}
		}
		
	}
	/**
	 * �ͻ������������з�ɳ����ַ��� 10������ť������
	 */
	private class IbtnFlyInsectPipelineRiskListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			if(D2EConfigures.TEST){
				Log.e("IbtnFlyInsectPipelineRiskListener=============>", "onClick(View v)");
			}
			String str=at_fly_insect_pipeline_risk.getText().toString();
			if(str!=null&&str.length()>0){
				at_fly_insect_pipeline_risk.setText("");
			}
			at_fly_insect_pipeline_risk.showDropDown();
			at_fly_insect_pipeline_risk.setThreshold(1);
		}
		
	}
	/**
	 * ������ʩ�Ƿ���Ϸ��ɳ��Ҫ�� 9�༭�������
	 */
	private class AtFlyInsectEquipRequListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			final MyWatchDataItem item=new MyWatchDataItem();
			item.setmMouseIndex(9);
			
			if(position==0){
				item.setmMouseKind1(1);
				item.setmMouseKind2(0);
				item.setmMouseKind3(0);
				item.setmMouseKind4("");
				
			}else if(position==1){
				item.setmMouseKind1(0);
				item.setmMouseKind2(1);
				item.setmMouseKind3(0);
				item.setmMouseKind4("");
			}else if(position==2){
				item.setmMouseKind1(0);
				item.setmMouseKind2(0);
				item.setmMouseKind3(1);
				item.setmMouseKind4("");
			}else if(position==3){
				//����һ���Ի���������д��ע
				LayoutInflater inflater=getLayoutInflater();
		    	View dialog_view=inflater.inflate(R.layout.dialog2, null);
		    	final EditText et_dialog2=(EditText) dialog_view.findViewById(R.id.et_dialog2);
		    	final Button btn_dialog2_yes=(Button) dialog_view.findViewById(R.id.btn_dialog2_yes);
		    	final Button btn_dialog2_no=(Button) dialog_view.findViewById(R.id.btn_dialog2_no);
				final Dialog otherFaciDialog=new Dialog(D2EFlyInsectWatchActivity.this,R.style.my_other_point_dialog);
				otherFaciDialog.setContentView(dialog_view);
				otherFaciDialog.getWindow().setLayout(300, 150);
				otherFaciDialog.show();
				btn_dialog2_yes.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						at_fly_insect_equip_requ.setText(et_dialog2.getText().toString());
						item.setmMouseKind1(0);
						item.setmMouseKind2(0);
						item.setmMouseKind3(0);
						item.setmMouseKind4(et_dialog2.getText().toString());
						otherFaciDialog.dismiss();
						if(D2EConfigures.TEST){
							Log.e("AtFlyInsectEquipRequxxxxxxxxxxxxx>>", ""+item.toString());
						}
						
					}
				});
				btn_dialog2_no.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						et_dialog2.setText("");
						at_fly_insect_equip_requ.setText("");
						otherFaciDialog.dismiss();				
					}
				});
				
			}
			
			if(!mFlyInsectItem.isContainWatchDataItem(item)){
				mFlyInsectItem.mWatchDataList.add(item);
			}else{
				mFlyInsectItem.deleContainWatchDataItem(item);
				mFlyInsectItem.mWatchDataList.add(item);
			}
			
			if(D2EConfigures.TEST){
				Log.e("AtFlyInsectEquipRequxxxxxxxxxxxx>>", ""+item.toString());
			}
		}
		
	}
	/**
	 * ������ʩ�Ƿ���Ϸ��ɳ��Ҫ�� 9�༭�������
	 */
	private class IbtnFlyInsectEquipRequListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			if(D2EConfigures.TEST){
				Log.e("IbtnFlyInsectEquipRequListener=============>", "onClick(View v)");
			}
			String str=at_fly_insect_equip_requ.getText().toString();
			if(str!=null&&str.length()>0){
				at_fly_insect_equip_requ.setText("");
			}
			at_fly_insect_equip_requ.showDropDown();
			at_fly_insect_equip_requ.setThreshold(1);
		}
		
	}
	/**
	 * ���������ƶ��Ƿ����� 8�༭�������
	 */
	private class AtFlyInsectEnvirSystPerfListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			final MyWatchDataItem item=new MyWatchDataItem();
			item.setmMouseIndex(8);
			
			if(position==0){
				item.setmMouseKind1(1);
				item.setmMouseKind2(0);
				item.setmMouseKind3(0);
				item.setmMouseKind4("");
				
			}else if(position==1){
				item.setmMouseKind1(0);
				item.setmMouseKind2(1);
				item.setmMouseKind3(0);
				item.setmMouseKind4("");
			}else if(position==2){
				item.setmMouseKind1(0);
				item.setmMouseKind2(0);
				item.setmMouseKind3(1);
				item.setmMouseKind4("");
			}else if(position==3){
				//����һ���Ի���������д��ע
				LayoutInflater inflater=getLayoutInflater();
		    	View dialog_view=inflater.inflate(R.layout.dialog2, null);
		    	final EditText et_dialog2=(EditText) dialog_view.findViewById(R.id.et_dialog2);
		    	final Button btn_dialog2_yes=(Button) dialog_view.findViewById(R.id.btn_dialog2_yes);
		    	final Button btn_dialog2_no=(Button) dialog_view.findViewById(R.id.btn_dialog2_no);
				final Dialog otherFaciDialog=new Dialog(D2EFlyInsectWatchActivity.this,R.style.my_other_point_dialog);
				otherFaciDialog.setContentView(dialog_view);
				otherFaciDialog.getWindow().setLayout(300, 150);
				otherFaciDialog.show();
				btn_dialog2_yes.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						at_fly_insect_envir_syst_perf.setText(et_dialog2.getText().toString());
						item.setmMouseKind1(0);
						item.setmMouseKind2(0);
						item.setmMouseKind3(0);
						item.setmMouseKind4(et_dialog2.getText().toString());
						otherFaciDialog.dismiss();
						if(D2EConfigures.TEST){
							Log.e("AtFlyInsectEnvirSystPerfxxxxxxxxxxxxx>>", ""+item.toString());
						}
						
					}
				});
				btn_dialog2_no.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						et_dialog2.setText("");
						at_fly_insect_envir_syst_perf.setText("");
						otherFaciDialog.dismiss();				
					}
				});
				
			}
			
			if(!mFlyInsectItem.isContainWatchDataItem(item)){
				mFlyInsectItem.mWatchDataList.add(item);
			}else{
				mFlyInsectItem.deleContainWatchDataItem(item);
				mFlyInsectItem.mWatchDataList.add(item);
			}
			
			if(D2EConfigures.TEST){
				Log.e("AtFlyInsectEnvirSystPerfxxxxxxxxxxxx>>", ""+item.toString());
			}
		}
		
	}
	/**
	 * ���������ƶ��Ƿ����� 8������ť������
	 */
	private class IbtnFlyInsectEnvirSystPerfListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			if(D2EConfigures.TEST){
				Log.e("IbtnFlyInsectEnvirSystPerfListener=============>", "onClick(View v)");
			}
			String str=at_fly_insect_envir_syst_perf.getText().toString();
			if(str!=null&&str.length()>0){
				at_fly_insect_envir_syst_perf.setText("");
			}
			at_fly_insect_envir_syst_perf.showDropDown();
			at_fly_insect_envir_syst_perf.setThreshold(1);
		}
		
	}
	/**
	 * ���⽨���ܱ��з�·�ơ�����������ɳ� 7�༭�������
	 */
	private class AtFlyInsectRoomOutLightListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			final MyWatchDataItem item=new MyWatchDataItem();
			item.setmMouseIndex(7);
			
			if(position==0){
				item.setmMouseKind1(1);
				item.setmMouseKind2(0);
				item.setmMouseKind3(0);
				item.setmMouseKind4("");
				
			}else if(position==1){
				item.setmMouseKind1(0);
				item.setmMouseKind2(1);
				item.setmMouseKind3(0);
				item.setmMouseKind4("");
			}else if(position==2){
				item.setmMouseKind1(0);
				item.setmMouseKind2(0);
				item.setmMouseKind3(1);
				item.setmMouseKind4("");
			}else if(position==3){
				//����һ���Ի���������д��ע
				LayoutInflater inflater=getLayoutInflater();
		    	View dialog_view=inflater.inflate(R.layout.dialog2, null);
		    	final EditText et_dialog2=(EditText) dialog_view.findViewById(R.id.et_dialog2);
		    	final Button btn_dialog2_yes=(Button) dialog_view.findViewById(R.id.btn_dialog2_yes);
		    	final Button btn_dialog2_no=(Button) dialog_view.findViewById(R.id.btn_dialog2_no);
				final Dialog otherFaciDialog=new Dialog(D2EFlyInsectWatchActivity.this,R.style.my_other_point_dialog);
				otherFaciDialog.setContentView(dialog_view);
				otherFaciDialog.getWindow().setLayout(300, 150);
				otherFaciDialog.show();
				btn_dialog2_yes.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						at_fly_insect_room_out_light.setText(et_dialog2.getText().toString());
						item.setmMouseKind1(0);
						item.setmMouseKind2(0);
						item.setmMouseKind3(0);
						item.setmMouseKind4(et_dialog2.getText().toString());
						otherFaciDialog.dismiss();
						if(D2EConfigures.TEST){
							Log.e("AtFlyInsectRoomOutLightxxxxxxxxxxxxx>>", ""+item.toString());
						}
						
					}
				});
				btn_dialog2_no.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						et_dialog2.setText("");
						at_fly_insect_room_out_light.setText("");
						otherFaciDialog.dismiss();				
					}
				});
				
			}
			
			if(!mFlyInsectItem.isContainWatchDataItem(item)){
				mFlyInsectItem.mWatchDataList.add(item);
			}else{
				mFlyInsectItem.deleContainWatchDataItem(item);
				mFlyInsectItem.mWatchDataList.add(item);
			}
			
			if(D2EConfigures.TEST){
				Log.e("AtFlyInsectRoomOutLightxxxxxxxxxxxx>>", ""+item.toString());
			}
		}
		
	}
	/**
	 * ���⽨���ܱ��з�·�ơ�����������ɳ� 7������ť������
	 */
	private class IbtnFlyInsectRoomOutLightListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			if(D2EConfigures.TEST){
				Log.e("IbtnFlyInsectRoomOutLightListener=============>", "onClick(View v)");
			}
			String str=at_fly_insect_room_out_light.getText().toString();
			if(str!=null&&str.length()>0){
				at_fly_insect_room_out_light.setText("");
			}
			at_fly_insect_room_out_light.setDropDownWidth(200);
			at_fly_insect_room_out_light.showDropDown();
			at_fly_insect_room_out_light.setThreshold(1);
		}
		
	}
	/**
	 * ���⽨���ܱ��з���������ˮ�����ɳ� 6�༭�������
	 */
	private class AtFlyInsectRoomOutRubbListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			final MyWatchDataItem item=new MyWatchDataItem();
			item.setmMouseIndex(6);
			
			if(position==0){
				item.setmMouseKind1(1);
				item.setmMouseKind2(0);
				item.setmMouseKind3(0);
				item.setmMouseKind4("");
				
			}else if(position==1){
				item.setmMouseKind1(0);
				item.setmMouseKind2(1);
				item.setmMouseKind3(0);
				item.setmMouseKind4("");
			}else if(position==2){
				item.setmMouseKind1(0);
				item.setmMouseKind2(0);
				item.setmMouseKind3(1);
				item.setmMouseKind4("");
			}else if(position==3){
				//����һ���Ի���������д��ע
				LayoutInflater inflater=getLayoutInflater();
		    	View dialog_view=inflater.inflate(R.layout.dialog2, null);
		    	final EditText et_dialog2=(EditText) dialog_view.findViewById(R.id.et_dialog2);
		    	final Button btn_dialog2_yes=(Button) dialog_view.findViewById(R.id.btn_dialog2_yes);
		    	final Button btn_dialog2_no=(Button) dialog_view.findViewById(R.id.btn_dialog2_no);
				final Dialog otherFaciDialog=new Dialog(D2EFlyInsectWatchActivity.this,R.style.my_other_point_dialog);
				otherFaciDialog.setContentView(dialog_view);
				otherFaciDialog.getWindow().setLayout(300, 150);
				otherFaciDialog.show();
				btn_dialog2_yes.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						at_fly_insect_room_out_rubb.setText(et_dialog2.getText().toString());
						item.setmMouseKind1(0);
						item.setmMouseKind2(0);
						item.setmMouseKind3(0);
						item.setmMouseKind4(et_dialog2.getText().toString());
						otherFaciDialog.dismiss();
						if(D2EConfigures.TEST){
							Log.e("AtFlyInsectRoomOutRubbxxxxxxxxxxxxx>>", ""+item.toString());
						}
						
					}
				});
				btn_dialog2_no.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						et_dialog2.setText("");
						at_fly_insect_room_out_rubb.setText("");
						otherFaciDialog.dismiss();				
					}
				});
				
			}
			
			if(!mFlyInsectItem.isContainWatchDataItem(item)){
				mFlyInsectItem.mWatchDataList.add(item);
			}else{
				mFlyInsectItem.deleContainWatchDataItem(item);
				mFlyInsectItem.mWatchDataList.add(item);
			}
			
			if(D2EConfigures.TEST){
				Log.e("AtFlyInsectRoomOutRubbxxxxxxxxxxxx>>", ""+item.toString());
			}
		}
		
	}
	/**
	 * ���⽨���ܱ��з���������ˮ�����ɳ� 6������ť������
	 */
	private class IbtnFlyInsectRoomOutRubbListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			if(D2EConfigures.TEST){
				Log.e("IbtnFlyInsectRoomOutRubbListener=============>", "onClick(View v)");
			}
			String str=at_fly_insect_room_out_rubb.getText().toString();
			if(str!=null&&str.length()>0){
				at_fly_insect_room_out_rubb.setText("");
			}
			at_fly_insect_room_out_rubb.setDropDownWidth(200);
			at_fly_insect_room_out_rubb.showDropDown();
			at_fly_insect_room_out_rubb.setThreshold(1);
		}
		
	}
	/**
	 * ���ڽ���ͨ����������з���������Ļ�������Ƶ���ʩ 5�༭�������
	 */
	private class AtFlyInsectRoomInHasEquipListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			final MyWatchDataItem item=new MyWatchDataItem();
			item.setmMouseIndex(5);
			
			if(position==0){
				item.setmMouseKind1(1);
				item.setmMouseKind2(0);
				item.setmMouseKind3(0);
				item.setmMouseKind4("");
				
			}else if(position==1){
				item.setmMouseKind1(0);
				item.setmMouseKind2(1);
				item.setmMouseKind3(0);
				item.setmMouseKind4("");
			}else if(position==2){
				item.setmMouseKind1(0);
				item.setmMouseKind2(0);
				item.setmMouseKind3(1);
				item.setmMouseKind4("");
			}else if(position==3){
				//����һ���Ի���������д��ע
				LayoutInflater inflater=getLayoutInflater();
		    	View dialog_view=inflater.inflate(R.layout.dialog2, null);
		    	final EditText et_dialog2=(EditText) dialog_view.findViewById(R.id.et_dialog2);
		    	final Button btn_dialog2_yes=(Button) dialog_view.findViewById(R.id.btn_dialog2_yes);
		    	final Button btn_dialog2_no=(Button) dialog_view.findViewById(R.id.btn_dialog2_no);
				final Dialog otherFaciDialog=new Dialog(D2EFlyInsectWatchActivity.this,R.style.my_other_point_dialog);
				otherFaciDialog.setContentView(dialog_view);
				otherFaciDialog.getWindow().setLayout(300, 150);
				otherFaciDialog.show();
				btn_dialog2_yes.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						at_fly_insect_room_in_has_equip.setText(et_dialog2.getText().toString());
						item.setmMouseKind1(0);
						item.setmMouseKind2(0);
						item.setmMouseKind3(0);
						item.setmMouseKind4(et_dialog2.getText().toString());
						otherFaciDialog.dismiss();
						if(D2EConfigures.TEST){
							Log.e("AtFlyInsectRoomInHasEquipxxxxxxxxxxxxx>>", ""+item.toString());
						}
						
					}
				});
				btn_dialog2_no.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						et_dialog2.setText("");
						at_fly_insect_room_in_has_equip.setText("");
						otherFaciDialog.dismiss();				
					}
				});
				
			}
			
			if(!mFlyInsectItem.isContainWatchDataItem(item)){
				mFlyInsectItem.mWatchDataList.add(item);
			}else{
				mFlyInsectItem.deleContainWatchDataItem(item);
				mFlyInsectItem.mWatchDataList.add(item);
			}
			
			if(D2EConfigures.TEST){
				Log.e("AtFlyInsectRoomInHasEquipxxxxxxxxxxxx>>", ""+item.toString());
			}
		}
		
	}
	/**
	 * ���ڽ���ͨ����������з���������Ļ�������Ƶ���ʩ 5������ť������
	 */
	private class IbtnFlyInsectRoomInHasEquipListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			if(D2EConfigures.TEST){
				Log.e("IbtnFlyInsectRoomInHasEquipListener=============>", "onClick(View v)");
			}
			String str=at_fly_insect_room_in_has_equip.getText().toString();
			if(str!=null&&str.length()>0){
				at_fly_insect_room_in_has_equip.setText("");
			}
			at_fly_insect_room_in_has_equip.setDropDownWidth(200);
			at_fly_insect_room_in_has_equip.showDropDown();
			at_fly_insect_room_in_has_equip.setThreshold(1);
		}
		
	}
	/**
	 * ���ڵ��桢��ˮ���з��ˮ 4�༭�������
	 */
	private class AtFlyInsectRoomInHydropsListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			final MyWatchDataItem item=new MyWatchDataItem();
			item.setmMouseIndex(4);
			
			if(position==0){
				item.setmMouseKind1(1);
				item.setmMouseKind2(0);
				item.setmMouseKind3(0);
				item.setmMouseKind4("");
				
			}else if(position==1){
				item.setmMouseKind1(0);
				item.setmMouseKind2(1);
				item.setmMouseKind3(0);
				item.setmMouseKind4("");
			}else if(position==2){
				item.setmMouseKind1(0);
				item.setmMouseKind2(0);
				item.setmMouseKind3(1);
				item.setmMouseKind4("");
			}else if(position==3){
				//����һ���Ի���������д��ע
				LayoutInflater inflater=getLayoutInflater();
		    	View dialog_view=inflater.inflate(R.layout.dialog2, null);
		    	final EditText et_dialog2=(EditText) dialog_view.findViewById(R.id.et_dialog2);
		    	final Button btn_dialog2_yes=(Button) dialog_view.findViewById(R.id.btn_dialog2_yes);
		    	final Button btn_dialog2_no=(Button) dialog_view.findViewById(R.id.btn_dialog2_no);
				final Dialog otherFaciDialog=new Dialog(D2EFlyInsectWatchActivity.this,R.style.my_other_point_dialog);
				otherFaciDialog.setContentView(dialog_view);
				otherFaciDialog.getWindow().setLayout(300, 150);
				otherFaciDialog.show();
				btn_dialog2_yes.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						at_fly_insect_room_in_hydrops.setText(et_dialog2.getText().toString());
						item.setmMouseKind1(0);
						item.setmMouseKind2(0);
						item.setmMouseKind3(0);
						item.setmMouseKind4(et_dialog2.getText().toString());
						otherFaciDialog.dismiss();
						if(D2EConfigures.TEST){
							Log.e("AtFlyInsectRoomInHydropsxxxxxxxxxxxxx>>", ""+item.toString());
						}
						
					}
				});
				btn_dialog2_no.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						et_dialog2.setText("");
						at_fly_insect_room_in_hydrops.setText("");
						otherFaciDialog.dismiss();				
					}
				});
				
			}
			
			if(!mFlyInsectItem.isContainWatchDataItem(item)){
				mFlyInsectItem.mWatchDataList.add(item);
			}else{
				mFlyInsectItem.deleContainWatchDataItem(item);
				mFlyInsectItem.mWatchDataList.add(item);
			}
			
			if(D2EConfigures.TEST){
				Log.e("AtFlyInsectRoomInHydropsxxxxxxxxxxxx>>", ""+item.toString());
			}
		}
		
	}
	/**
	 * ���ڵ��桢��ˮ���з��ˮ 4������ť������
	 */
	private class IbtnFlyInsectRoomInHydropsListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			if(D2EConfigures.TEST){
				Log.e("IbtnFlyInsectRoomInHydropsListener=============>", "onClick(View v)");
			}
			String str=at_fly_insect_room_in_hydrops.getText().toString();
			if(str!=null&&str.length()>0){
				at_fly_insect_room_in_hydrops.setText("");
			}
			at_fly_insect_room_in_hydrops.showDropDown();
			at_fly_insect_room_in_hydrops.setThreshold(1);
		}
		
	}
	/**
	 * �����������Ƿ����������׼3�༭�������
	 */
	private class AtPanelFlyInsectRoomInHygieneStanListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			final MyWatchDataItem item=new MyWatchDataItem();
			item.setmMouseIndex(3);
			
			if(position==0){
				item.setmMouseKind1(1);
				item.setmMouseKind2(0);
				item.setmMouseKind3(0);
				item.setmMouseKind4("");
				
			}else if(position==1){
				item.setmMouseKind1(0);
				item.setmMouseKind2(1);
				item.setmMouseKind3(0);
				item.setmMouseKind4("");
			}else if(position==2){
				item.setmMouseKind1(0);
				item.setmMouseKind2(0);
				item.setmMouseKind3(1);
				item.setmMouseKind4("");
			}else if(position==3){
				//����һ���Ի���������д��ע
				LayoutInflater inflater=getLayoutInflater();
		    	View dialog_view=inflater.inflate(R.layout.dialog2, null);
		    	final EditText et_dialog2=(EditText) dialog_view.findViewById(R.id.et_dialog2);
		    	final Button btn_dialog2_yes=(Button) dialog_view.findViewById(R.id.btn_dialog2_yes);
		    	final Button btn_dialog2_no=(Button) dialog_view.findViewById(R.id.btn_dialog2_no);
				final Dialog otherFaciDialog=new Dialog(D2EFlyInsectWatchActivity.this,R.style.my_other_point_dialog);
				otherFaciDialog.setContentView(dialog_view);
				otherFaciDialog.getWindow().setLayout(300, 150);
				otherFaciDialog.show();
				btn_dialog2_yes.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						at_panel_fly_insect_room_in_hygiene_stan.setText(et_dialog2.getText().toString());
						item.setmMouseKind1(0);
						item.setmMouseKind2(0);
						item.setmMouseKind3(0);
						item.setmMouseKind4(et_dialog2.getText().toString());
						otherFaciDialog.dismiss();
						if(D2EConfigures.TEST){
							Log.e("AtPanelFlyInsectRoomInHygieneStanxxxxxxxxxxxxx>>", ""+item.toString());
						}
						
					}
				});
				btn_dialog2_no.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						et_dialog2.setText("");
						at_panel_fly_insect_room_in_hygiene_stan.setText("");
						otherFaciDialog.dismiss();				
					}
				});
				
			}
			
			if(!mFlyInsectItem.isContainWatchDataItem(item)){
				mFlyInsectItem.mWatchDataList.add(item);
			}else{
				mFlyInsectItem.deleContainWatchDataItem(item);
				mFlyInsectItem.mWatchDataList.add(item);
			}
			
			if(D2EConfigures.TEST){
				Log.e("AtPanelFlyInsectRoomInHygieneStanxxxxxxxxxxxx>>", ""+item.toString());
			}
		}
		
	}
	/**
	 * �����������Ƿ����������׼3������ť������
	 */
	private class IbtnPanelFlyInsectRoomInHygieneStanListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			if(D2EConfigures.TEST){
				Log.e("IbtnPanelFlyInsectRoomInHygieneStanListener=============>", "onClick(View v)");
			}
			String str=at_panel_fly_insect_room_in_hygiene_stan.getText().toString();
			if(str!=null&&str.length()>0){
				at_panel_fly_insect_room_in_hygiene_stan.setText("");
			}
			at_panel_fly_insect_room_in_hygiene_stan.showDropDown();
			at_panel_fly_insect_room_in_hygiene_stan.setThreshold(1);
		}
		
	}
	/**
	 * ���������Ƿ�¶�ѷ�2�༭�������
	 */
	private class AtFlyInsectRoomInRubbBareListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			final MyWatchDataItem item=new MyWatchDataItem();
			item.setmMouseIndex(2);
			
			if(position==0){
				item.setmMouseKind1(1);
				item.setmMouseKind2(0);
				item.setmMouseKind3(0);
				item.setmMouseKind4("");
				
			}else if(position==1){
				item.setmMouseKind1(0);
				item.setmMouseKind2(1);
				item.setmMouseKind3(0);
				item.setmMouseKind4("");
			}else if(position==2){
				item.setmMouseKind1(0);
				item.setmMouseKind2(0);
				item.setmMouseKind3(1);
				item.setmMouseKind4("");
			}else if(position==3){
				//����һ���Ի���������д��ע
				LayoutInflater inflater=getLayoutInflater();
		    	View dialog_view=inflater.inflate(R.layout.dialog2, null);
		    	final EditText et_dialog2=(EditText) dialog_view.findViewById(R.id.et_dialog2);
		    	final Button btn_dialog2_yes=(Button) dialog_view.findViewById(R.id.btn_dialog2_yes);
		    	final Button btn_dialog2_no=(Button) dialog_view.findViewById(R.id.btn_dialog2_no);
				final Dialog otherFaciDialog=new Dialog(D2EFlyInsectWatchActivity.this,R.style.my_other_point_dialog);
				otherFaciDialog.setContentView(dialog_view);
				otherFaciDialog.getWindow().setLayout(300, 150);
				otherFaciDialog.show();
				btn_dialog2_yes.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						at_fly_insect_room_in_rubb_bare.setText(et_dialog2.getText().toString());
						item.setmMouseKind1(0);
						item.setmMouseKind2(0);
						item.setmMouseKind3(0);
						item.setmMouseKind4(et_dialog2.getText().toString());
						otherFaciDialog.dismiss();
						if(D2EConfigures.TEST){
							Log.e("AtFlyInsectRoomInRubbBarexxxxxxxxxxxxx>>", ""+item.toString());
						}
						
					}
				});
				btn_dialog2_no.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						et_dialog2.setText("");
						at_fly_insect_room_in_rubb_bare.setText("");
						otherFaciDialog.dismiss();				
					}
				});
				
			}
			
			if(!mFlyInsectItem.isContainWatchDataItem(item)){
				mFlyInsectItem.mWatchDataList.add(item);
			}else{
				mFlyInsectItem.deleContainWatchDataItem(item);
				mFlyInsectItem.mWatchDataList.add(item);
			}
			
			if(D2EConfigures.TEST){
				Log.e("AtFlyInsectRoomInRubbBarexxxxxxxxxxxx>>", ""+item.toString());
			}
		}
		
	}
	/**
	 * ���������Ƿ�¶�ѷ�2������ť������
	 */
	private class IbntFlyInsectRoomInRubbBareListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			if(D2EConfigures.TEST){
				Log.e("IbntFlyInsectRoomInRubbBareListener=============>", "onClick(View v)");
			}
			String str=at_fly_insect_room_in_rubb_bare.getText().toString();
			if(str!=null&&str.length()>0){
				at_fly_insect_room_in_rubb_bare.setText("");
			}
			at_fly_insect_room_in_rubb_bare.showDropDown();
			at_fly_insect_room_in_rubb_bare.setThreshold(1);
		}
		
	}
	/**
	 * �����Ŵ���ͨ���Ƿ���Ϸ��ɳ�Ҫ��1�༭�������
	 */
	private class AtFlyInsectWatchRoomInRequListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			final MyWatchDataItem item=new MyWatchDataItem();
			item.setmMouseIndex(1);
			
			if(position==0){
				item.setmMouseKind1(1);
				item.setmMouseKind2(0);
				item.setmMouseKind3(0);
				item.setmMouseKind4("");
				
			}else if(position==1){
				item.setmMouseKind1(0);
				item.setmMouseKind2(1);
				item.setmMouseKind3(0);
				item.setmMouseKind4("");
			}else if(position==2){
				item.setmMouseKind1(0);
				item.setmMouseKind2(0);
				item.setmMouseKind3(1);
				item.setmMouseKind4("");
			}else if(position==3){
				//����һ���Ի���������д��ע
				LayoutInflater inflater=getLayoutInflater();
		    	View dialog_view=inflater.inflate(R.layout.dialog2, null);
		    	final EditText et_dialog2=(EditText) dialog_view.findViewById(R.id.et_dialog2);
		    	final Button btn_dialog2_yes=(Button) dialog_view.findViewById(R.id.btn_dialog2_yes);
		    	final Button btn_dialog2_no=(Button) dialog_view.findViewById(R.id.btn_dialog2_no);
				final Dialog otherFaciDialog=new Dialog(D2EFlyInsectWatchActivity.this,R.style.my_other_point_dialog);
				otherFaciDialog.setContentView(dialog_view);
				otherFaciDialog.getWindow().setLayout(300, 150);
				otherFaciDialog.show();
				btn_dialog2_yes.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						at_fly_insect_watch_room_in_requ.setText(et_dialog2.getText().toString());
						item.setmMouseKind1(0);
						item.setmMouseKind2(0);
						item.setmMouseKind3(0);
						item.setmMouseKind4(et_dialog2.getText().toString());
						otherFaciDialog.dismiss();
						if(D2EConfigures.TEST){
							Log.e("AtFlyInsectWatchRoomInRequxxxxxxxxxxxxx>>", ""+item.toString());
						}
						
					}
				});
				btn_dialog2_no.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						et_dialog2.setText("");
						at_fly_insect_watch_room_in_requ.setText("");
						otherFaciDialog.dismiss();				
					}
				});
				
			}
			
			if(!mFlyInsectItem.isContainWatchDataItem(item)){
				mFlyInsectItem.mWatchDataList.add(item);
			}else{
				mFlyInsectItem.deleContainWatchDataItem(item);
				mFlyInsectItem.mWatchDataList.add(item);
			}
			
			if(D2EConfigures.TEST){
				Log.e("AtFlyInsectWatchRoomInRequxxxxxxxxxxxx>>", ""+item.toString());
			}
		}
		
	}
	/**
	 * �����Ŵ���ͨ���Ƿ���Ϸ��ɳ�Ҫ��1������ť������
	 */
	private class IbtnFlyInsectWatchRoomInRequListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			if(D2EConfigures.TEST){
				Log.e("IbtnFlyInsectWatchRoomInRequListener=============>", "onClick(View v)");
			}
			String str=at_fly_insect_watch_room_in_requ.getText().toString();
			if(str!=null&&str.length()>0){
				at_fly_insect_watch_room_in_requ.setText("");
			}
			at_fly_insect_watch_room_in_requ.showDropDown();
			at_fly_insect_watch_room_in_requ.setThreshold(1);
		}
		
	}
	
	/**
	 * ���ذ�ť������
	 */
	private class BackListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			isBackPressed=true;
			if(isAllCheck()){
				showWarmDialog();
			}
			else {
				overView();
			}
//			overView();
		}
		
	}
	/**
	 * ����������ݲ��뿪����
	 */
	private void saveViewData(){
		//�Ѿ��������������
		JJBaseApplication.isAddFlyWatch=true;
		
		//��������ӵ��б���
		JJBaseApplication.mWatchDataList.add(mFlyInsectItem);
		//��������
		overridePendingTransition(R.anim.fade, R.anim.hold);
		D2EFlyInsectWatchActivity.this.finish();
	}
	/**
	 * ���水ť������
	 */
	private class SaveListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			if(isAllCheck()){
				//����������ݣ����뿪����
				saveViewData();
			}
		}
		
	}
	/**
	 * �ֳ�����ʱ���ֵķɳ�༭�������
	 */
	private class AtFlyInsectWatchKindListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			final MyWatchDataItem item=new MyWatchDataItem();
			item.setmMouseIndex(0);
			
			if(position==0){
				item.setmMouseKind1(1);
				item.setmMouseKind2(0);
				item.setmMouseKind3(0);
				item.setmMouseKind4("");
				
			}else if(position==1){
				item.setmMouseKind1(0);
				item.setmMouseKind2(1);
				item.setmMouseKind3(0);
				item.setmMouseKind4("");
			}else if(position==2){
				item.setmMouseKind1(0);
				item.setmMouseKind2(0);
				item.setmMouseKind3(1);
				item.setmMouseKind4("");
			}else if(position==3){
				//����һ���Ի���������д��ע
				LayoutInflater inflater=getLayoutInflater();
		    	View dialog_view=inflater.inflate(R.layout.dialog2, null);
		    	final EditText et_dialog2=(EditText) dialog_view.findViewById(R.id.et_dialog2);
		    	final Button btn_dialog2_yes=(Button) dialog_view.findViewById(R.id.btn_dialog2_yes);
		    	final Button btn_dialog2_no=(Button) dialog_view.findViewById(R.id.btn_dialog2_no);
				final Dialog otherFaciDialog=new Dialog(D2EFlyInsectWatchActivity.this,R.style.my_other_point_dialog);
				otherFaciDialog.setContentView(dialog_view);
				otherFaciDialog.getWindow().setLayout(300, 150);
				otherFaciDialog.show();
				btn_dialog2_yes.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						at_fly_insect_watch_kind.setText(et_dialog2.getText().toString());
						item.setmMouseKind1(0);
						item.setmMouseKind2(0);
						item.setmMouseKind3(0);
						item.setmMouseKind4(et_dialog2.getText().toString());
						otherFaciDialog.dismiss();
						if(D2EConfigures.TEST){
							Log.e("AtFlyInsectWatchKindxxxxxxxxxxxxx>>", ""+item.toString());
						}
						
					}
				});
				btn_dialog2_no.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						et_dialog2.setText("");
						at_fly_insect_watch_kind.setText("");
						otherFaciDialog.dismiss();				
					}
				});
				
			}
			
			if(!mFlyInsectItem.isContainWatchDataItem(item)){
				mFlyInsectItem.mWatchDataList.add(item);
			}else{
				mFlyInsectItem.deleContainWatchDataItem(item);
				mFlyInsectItem.mWatchDataList.add(item);
			}
			
			if(D2EConfigures.TEST){
				Log.e("AtFlyInsectWatchKindxxxxxxxxxxxx>>", ""+item.toString());
			}
		}
		
	}
	/**
	 * �ֳ�����ʱ���ֵķɳ�������ť������
	 */
	private class IbtnFlyInsectWatchKindListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			//���ﵯ��һ���Ի�������Ȼ����һ���б���ѡ������
			AlertDialog.Builder  builder=new Builder(D2EFlyInsectWatchActivity.this);
			LayoutInflater inflater=LayoutInflater.from(D2EFlyInsectWatchActivity.this);
			View view=inflater.inflate(R.layout.d2e_dialog_watch_kind, null);
			ListView lv=(ListView) view.findViewById(R.id.lv);
			final EditText et_other_kind=(EditText) view.findViewById(R.id.et_other_kind);
			Button btn_select_ensure=(Button) view.findViewById(R.id.btn_select_ensure);
			Button btn_cancle_select=(Button) view.findViewById(R.id.btn_cancle_select);
			builder.setTitle(getResources().getString(R.string.choose_kind));
			builder.setView(view);
			final Dialog dialog=builder.create();
			dialog.show();
			dialog.getWindow().setLayout(450, 650);
			//��������et_other_kindΪĬ����һ�α������Ϣ
			et_other_kind.setText(previous_kind);
			lv.setAdapter(mFlyInsectKindAdapter);
			lv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View view,
						int position, long arg3) {
					//1����ȡViewHolder����
					ViewHolder holder=(ViewHolder) view.getTag();
					//2�������listView��һ��ʱ���ı�CheckBox��״̬
					holder.cb.toggle();
					//3��ÿ�λ�ȡItem��ʱ�򱣴�CheckBox��״̬
					MyWatchKindAdapter.mCheckBoxStateMap.put(position, holder.cb.isChecked());
					//4�����CheckBoxΪѡ��״̬��������¼�������б������һ������
					if(MyWatchKindAdapter.mCheckBoxStateMap.get(position)==true){
						mCountList.add(mFlyKindData[position]);
					}else{
						//���CheckBoxΪ��ѡ��״̬���򽫼�¼�����б����Ƴ�����
						mCountList.remove(mFlyKindData[position]);
					}
					//5��֪ͨTextView��ListView��������
//					tv_select_count.setText("��ѡ��"+mCountList.size()+"��");
					mFlyInsectKindAdapter.notifyDataSetChanged();
					//�ж��Ƿ�ѡ����"��"
					//�ж��Ƿ�ѡ���ˡ��ޡ�
					if(holder.tv.getText().toString().equalsIgnoreCase("��")){
						if(holder.cb.isChecked()==true){
							for(int i=0;i<listData.size();i++){
								if(!String.valueOf(i).equalsIgnoreCase(holder.tv.getTag().toString())){
									MyWatchKindAdapter.mCheckBoxStateMap.put(i, false);
								}
							}
							et_other_kind.setText("");
							et_other_kind.setEnabled(false);
							et_other_kind.setClickable(false);
							et_other_kind.setFocusable(false);
							et_other_kind.setFocusableInTouchMode(false);
						}else{
							et_other_kind.setClickable(true);
							et_other_kind.setEnabled(true);
							et_other_kind.setFocusable(true);
							et_other_kind.setFocusableInTouchMode(true);
						}
					}else{
						for(int i=0;i<listData.size();i++){
							if(listData.get(i).get("item_tv").toString().equalsIgnoreCase("��")){
								MyWatchKindAdapter.mCheckBoxStateMap.put(i, false);
							}
						}
						et_other_kind.setFocusable(true);
						et_other_kind.setFocusableInTouchMode(true);
						et_other_kind.setClickable(true);
						et_other_kind.setEnabled(true);
					}
				}
			});
			//ȷ����ʱ�򱣴�����
			btn_select_ensure.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					//kris
					boolean hasdata = false;
					
					//1��ʵ����һ�����ݶ���
					MyWatchDataItem item=new MyWatchDataItem();
					item.setmMouseIndex(0);
					//2��ѭ��������������,��ѡ�е���Ϣ����
					for(int i=0;i<listData.size();i++){
						if(MyWatchKindAdapter.mCheckBoxStateMap.get(i)==true){
							if(i==0){
								hasdata = true;
								item.setmMouseKind1(1);
							}else if(i==1){
								hasdata = true;
								item.setmMouseKind2(1);
							}else if(i==2){
								hasdata = true;
								item.setmMouseKind3(1);
							}else if(i==3){
								hasdata = true;
								item.setmMouseKind5(1);
							}
						}
					}
					
					
					String strOtherKind=et_other_kind.getText().toString();
					previous_kind=strOtherKind;
					//2���ж�������������û����д
					if(strOtherKind!=null&&strOtherKind.length()>0){
						hasdata = true;
						item.setmMouseKind4(strOtherKind);
					}
					
					if(D2EConfigures.TEST){
						Log.e("MouseKindItemxxxxxxxxxxxx>>", ""+item.toString());
					}
					
					
					//�ж��Ƿ�ԭ�������ݣ�����ɾ��ԭ���ģ��������ڵ�
					if(item!=null){
						if(!mFlyInsectItem.isContainWatchDataItem(item)){
							mFlyInsectItem.mWatchDataList.add(item);
						}else{
							mFlyInsectItem.deleContainWatchDataItem(item);
							mFlyInsectItem.mWatchDataList.add(item);
						}
					}
					//��at����һ���ı���"�����"
					if(mFlyInsectItem.mWatchDataList != null && mFlyInsectItem.mWatchDataList.size() > 0 && hasdata){
						at_fly_insect_watch_kind.setText(getResources().getString(R.string.doing_finish));						
					}
					else {
						at_fly_insect_watch_kind.setText("");
					}
					dialog.dismiss();
				}
			});
			
			//ȡ����ʱ�����Ϣ���
			btn_cancle_select.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					clearDialogData();
					
					//�رնԻ���
					dialog.dismiss();
				}
			});
			dialog.setOnKeyListener(new OnKeyListener() {
				
				@Override
				public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
					clearDialogData();
					return false;
				}
			});
		}
		
	}
	//��նԻ����ռ�������
	private void clearDialogData(){
		//ѭ���������飬��ѡ�еĶ�����Ϊδѡ��
		for(int i=0;i<listData.size();i++){
			if(MyWatchKindAdapter.mCheckBoxStateMap.get(i)==true){
				MyWatchKindAdapter.mCheckBoxStateMap.put(i, false);
			}
		}
		//��ռ�¼�������б�
		mCountList.clear();
		//���ϴα����������Ϣ���
		previous_kind="";
		//��at��������Ϊ""��ʾδ��ɼ��
		at_fly_insect_watch_kind.setText("");
	}
	/**
	 * ��ʼ���ؼ�
	 */
	private void initView(){
		at_fly_insect_watch_kind=(AutoCompleteTextView) this.findViewById(R.id.at_fly_insect_watch_kind);
		ibtn_fly_insect_watch_kind=(ImageButton) this.findViewById(R.id.ibtn_fly_insect_watch_kind);
		at_fly_insect_watch_room_in_requ=(AutoCompleteTextView) this.findViewById(R.id.at_fly_insect_watch_room_in_requ);
		ibtn_fly_insect_watch_room_in_requ=(ImageButton) this.findViewById(R.id.ibtn_fly_insect_watch_room_in_requ);
		at_fly_insect_room_in_rubb_bare=(AutoCompleteTextView) this.findViewById(R.id.at_fly_insect_room_in_rubb_bare);
		ibtn_fly_insect_room_in_rubb_bare=(ImageButton) this.findViewById(R.id.ibtn_fly_insect_room_in_rubb_bare);
		at_panel_fly_insect_room_in_hygiene_stan=(AutoCompleteTextView) this.findViewById(R.id.at_panel_fly_insect_room_in_hygiene_stan);
		ibtn_panel_fly_insect_room_in_hygiene_stan=(ImageButton) this.findViewById(R.id.ibtn_panel_fly_insect_room_in_hygiene_stan);
		at_fly_insect_room_in_hydrops=(AutoCompleteTextView) this.findViewById(R.id.at_fly_insect_room_in_hydrops);
		ibtn_fly_insect_room_in_hydrops=(ImageButton) this.findViewById(R.id.ibtn_fly_insect_room_in_hydrops);
		at_fly_insect_room_in_has_equip=(AutoCompleteTextView) this.findViewById(R.id.at_fly_insect_room_in_has_equip);
		ibtn_fly_insect_room_in_has_equip=(ImageButton) this.findViewById(R.id.ibtn_fly_insect_room_in_has_equip);
		at_fly_insect_room_out_rubb=(AutoCompleteTextView) this.findViewById(R.id.at_fly_insect_room_out_rubb);
		ibtn_fly_insect_room_out_rubb=(ImageButton) this.findViewById(R.id.ibtn_fly_insect_room_out_rubb);
		at_fly_insect_room_out_light=(AutoCompleteTextView) this.findViewById(R.id.at_fly_insect_room_out_light);
		ibtn_fly_insect_room_out_light=(ImageButton) this.findViewById(R.id.ibtn_fly_insect_room_out_light);
		at_fly_insect_envir_syst_perf=(AutoCompleteTextView) this.findViewById(R.id.at_fly_insect_envir_syst_perf);
		ibtn_fly_insect_envir_syst_perf=(ImageButton) this.findViewById(R.id.ibtn_fly_insect_envir_syst_perf);
		at_fly_insect_equip_requ=(AutoCompleteTextView) this.findViewById(R.id.at_fly_insect_equip_requ);
		ibtn_fly_insect_equip_requ=(ImageButton) this.findViewById(R.id.ibtn_fly_insect_equip_requ);
		at_fly_insect_pipeline_risk=(AutoCompleteTextView) this.findViewById(R.id.at_fly_insect_pipeline_risk);
		ibtn_fly_insect_pipeline_risk=(ImageButton) this.findViewById(R.id.ibtn_fly_insect_pipeline_risk);
		at_fly_insect_prevention_think=(AutoCompleteTextView) this.findViewById(R.id.at_fly_insect_prevention_think);
		ibtn_fly_insect_prevention_think=(ImageButton) this.findViewById(R.id.ibtn_fly_insect_prevention_think);
		
		
		
		//ʵ����һ��������������������
		mFlyInsectItem=new MyWatchDataItem();
		//�������Ϊ����
		mFlyInsectItem.setmWatchType("Flying");
		int array=R.array.fly_insect_kind;
		//��ȡ�ɳ濱�����ݼ�������
		getFlyInsectAdapterData();
		array=R.array.mouse_other_state;
		mOtherStateAdapter1=ArrayAdapter.createFromResource(this, array, R.layout.simple_dropdown_item_1line);
		mOtherStateAdapter2=ArrayAdapter.createFromResource(this, array, R.layout.simple_dropdown_item_1line);
		mOtherStateAdapter3=ArrayAdapter.createFromResource(this, array, R.layout.simple_dropdown_item_1line);
		mOtherStateAdapter4=ArrayAdapter.createFromResource(this, array, R.layout.simple_dropdown_item_1line);
		mOtherStateAdapter5=ArrayAdapter.createFromResource(this, array, R.layout.simple_dropdown_item_1line);
		mOtherStateAdapter6=ArrayAdapter.createFromResource(this, array, R.layout.simple_dropdown_item_1line);
		mOtherStateAdapter7=ArrayAdapter.createFromResource(this, array, R.layout.simple_dropdown_item_1line);
		mOtherStateAdapter8=ArrayAdapter.createFromResource(this, array, R.layout.simple_dropdown_item_1line);
		mOtherStateAdapter9=ArrayAdapter.createFromResource(this, array, R.layout.simple_dropdown_item_1line);
		mOtherStateAdapter10=ArrayAdapter.createFromResource(this, array, R.layout.simple_dropdown_item_1line);
		mOtherStateAdapter11=ArrayAdapter.createFromResource(this, array, R.layout.simple_dropdown_item_1line);
		
		btn_save_fly_insect_watch=(Button) this.findViewById(R.id.btn_save_fly_insect_watch);
		btn_back_fly_insect_watch=(Button) this.findViewById(R.id.btn_back_fly_insect_watch);
		
		//��ʼ���������ؿؼ�
		initLamptView();
		
	}
	/**
	 * ������TextView�Ľ����������Լ���ý���
	 */
	private void disableOtherTextFocus(TextView v){
		tv_fly_insect_watch_kind.setSelected(false);
		tv_fly_insect_watch_room_in_requ.setSelected(false);
		tv_fly_insect_room_in_rubb_bare.setSelected(false);
		tv_panel_fly_insect_room_in_hygiene_stan.setSelected(false);
		tv_fly_insect_room_in_hydrops.setSelected(false);
		tv_fly_insect_room_in_has_equip.setSelected(false);
		tv_fly_insect_room_out_rubb.setSelected(false);
		tv_fly_insect_room_out_light.setSelected(false);
		tv_fly_insect_envir_syst_perf.setSelected(false);
		tv_fly_insect_equip_requ.setSelected(false);
		tv_fly_insect_pipeline_risk.setSelected(false);
		tv_fly_insect_prevention_think.setSelected(false);
		v.setSelected(true);
	}
	/**
	 * ��ʼ���������ؿؼ�
	 */
	private void initLamptView(){
		//0
		panel_fly_insect_watch_kind=(RelativeLayout) this.findViewById(R.id.panel_fly_insect_watch_kind);
		tv_fly_insect_watch_kind=(TextView) this.findViewById(R.id.tv_fly_insect_watch_kind);
		//1
		panel_fly_insect_watch_room_in_requ=(RelativeLayout) this.findViewById(R.id.panel_fly_insect_watch_room_in_requ);
		tv_fly_insect_watch_room_in_requ=(TextView) this.findViewById(R.id.tv_fly_insect_watch_room_in_requ);
		//2
		panel_fly_insect_room_in_rubb_bare=(RelativeLayout) this.findViewById(R.id.panel_fly_insect_room_in_rubb_bare);
		tv_fly_insect_room_in_rubb_bare=(TextView) this.findViewById(R.id.tv_fly_insect_room_in_rubb_bare);
		//3
		panel_fly_insect_room_in_hygiene_stan=(RelativeLayout) this.findViewById(R.id.panel_fly_insect_room_in_hygiene_stan);
		tv_panel_fly_insect_room_in_hygiene_stan=(TextView) this.findViewById(R.id.tv_panel_fly_insect_room_in_hygiene_stan);
		//4
		panel_fly_insect_room_in_hydrops=(RelativeLayout) this.findViewById(R.id.panel_fly_insect_room_in_hydrops);
		tv_fly_insect_room_in_hydrops=(TextView) this.findViewById(R.id.tv_fly_insect_room_in_hydrops);
		//5
		panel_fly_insect_room_in_has_equip=(RelativeLayout) this.findViewById(R.id.panel_fly_insect_room_in_has_equip);
		tv_fly_insect_room_in_has_equip=(TextView) this.findViewById(R.id.tv_fly_insect_room_in_has_equip);
		//6
		panel_fly_insect_room_out_rubb=(RelativeLayout) this.findViewById(R.id.panel_fly_insect_room_out_rubb);
		tv_fly_insect_room_out_rubb=(TextView) this.findViewById(R.id.tv_fly_insect_room_out_rubb);
		//7
		panel_fly_insect_room_out_light=(RelativeLayout) this.findViewById(R.id.panel_fly_insect_room_out_light);
		tv_fly_insect_room_out_light=(TextView) this.findViewById(R.id.tv_fly_insect_room_out_light);
		//8
		panel_fly_insect_envir_syst_perf=(RelativeLayout) this.findViewById(R.id.panel_fly_insect_envir_syst_perf);
		tv_fly_insect_envir_syst_perf=(TextView) this.findViewById(R.id.tv_fly_insect_envir_syst_perf);
		//9
		panel_fly_insect_equip_requ=(RelativeLayout) this.findViewById(R.id.panel_fly_insect_equip_requ);
		tv_fly_insect_equip_requ=(TextView) this.findViewById(R.id.tv_fly_insect_equip_requ);
		//10
		panel_fly_insect_pipeline_risk=(RelativeLayout) this.findViewById(R.id.panel_fly_insect_pipeline_risk);
		tv_fly_insect_pipeline_risk=(TextView) this.findViewById(R.id.tv_fly_insect_pipeline_risk);
		//11
		panel_fly_insect_prevention_think=(RelativeLayout) this.findViewById(R.id.panel_fly_insect_prevention_think);
		tv_fly_insect_prevention_think=(TextView) this.findViewById(R.id.tv_fly_insect_prevention_think);
	}
	/**
	 * Ϊ����ƿؼ����ü�����
	 */
	private void setUpLamptListener(){
		//0
		panel_fly_insect_watch_kind.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				disableOtherTextFocus(tv_fly_insect_watch_kind);
			}
		});
		//1
		panel_fly_insect_watch_room_in_requ.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				disableOtherTextFocus(tv_fly_insect_watch_room_in_requ);
			}
		});
		//2
		panel_fly_insect_room_in_rubb_bare.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				disableOtherTextFocus(tv_fly_insect_room_in_rubb_bare);
			}
		});
		//3
		panel_fly_insect_room_in_hygiene_stan.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				disableOtherTextFocus(tv_panel_fly_insect_room_in_hygiene_stan);
			}
		});
		//4
		panel_fly_insect_room_in_hydrops.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				disableOtherTextFocus(tv_fly_insect_room_in_hydrops);
			}
		});
		//5
		panel_fly_insect_room_in_has_equip.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				disableOtherTextFocus(tv_fly_insect_room_in_has_equip);
			}
		});
		//6
		panel_fly_insect_room_out_rubb.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				disableOtherTextFocus(tv_fly_insect_room_out_rubb);
			}
		});
		//7
		panel_fly_insect_room_out_light.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				disableOtherTextFocus(tv_fly_insect_room_out_light);
			}
		});
		//8
		panel_fly_insect_envir_syst_perf.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				disableOtherTextFocus(tv_fly_insect_envir_syst_perf);
			}
		});
		//9
		panel_fly_insect_equip_requ.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				disableOtherTextFocus(tv_fly_insect_equip_requ);
			}
		});
		//10
		panel_fly_insect_pipeline_risk.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				disableOtherTextFocus(tv_fly_insect_pipeline_risk);
			}
		});
		//11
		panel_fly_insect_prevention_think.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				disableOtherTextFocus(tv_fly_insect_prevention_think);
			}
		});
	}
	
	/**
	 * ��ȡ�ɳ濱�����ݼ�������
	 */
	private void getFlyInsectAdapterData() {
		mFlyKindData=getResources().getStringArray(R.array.fly_insect_kind);
		listData=new ArrayList<HashMap<String,Object>>();
		mCountList=new ArrayList<String>();
		for(int i=0;i<mFlyKindData.length;i++){
			HashMap<String,Object> map=new HashMap<String,Object>();
			map.put("item_tv", mFlyKindData[i]);
			map.put("item_cb", false);
			listData.add(map);
		}
		mFlyInsectKindAdapter=new MyWatchKindAdapter(
				this, 
				listData, 
				R.layout.d2e_at_watch_kind_item, 
				new String[]{"item_tv","item_cb"}, 
				new int[]{R.id.tv_list_item,R.id.cb_list_item});
	}
	@Override
	public void backAciton() {
		isBackPressed=true;
		if(isAllCheck()){
			showWarmDialog();
		}else{
			overView();
		}
	}
	/**
	 * ��������
	 */
	private void overView(){
		overridePendingTransition(R.anim.fade, R.anim.hold);
		D2EFlyInsectWatchActivity.this.finish();
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

	}

	@Override
	public void taskFailed(Object... param) {

	}

	@Override
	public void taskProcessing(Object... param) {

	}

}
