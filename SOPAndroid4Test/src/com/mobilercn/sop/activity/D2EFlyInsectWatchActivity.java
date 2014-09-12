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
 * 飞虫勘查数据收集界面
 * @author ShawnXiao
 *
 */
public class D2EFlyInsectWatchActivity extends JJBaseActivity {
	/**
	 * 现场勘察时发现的飞虫编辑框
	 */
	private AutoCompleteTextView at_fly_insect_watch_kind;
	/**
	 * 现场勘察时发现的飞虫下拉按钮
	 */
	private ImageButton ibtn_fly_insect_watch_kind;
	/**
	 * 室内门窗、通道是否符合防飞虫要求编辑框
	 */
	private AutoCompleteTextView at_fly_insect_watch_room_in_requ;
	/**
	 * 室内门窗、通道是否符合防飞虫要求下拉按钮
	 */
	private ImageButton ibtn_fly_insect_watch_room_in_requ;
	/**
	 * 室内垃圾是否暴露堆放编辑框
	 */
	private AutoCompleteTextView at_fly_insect_room_in_rubb_bare;
	/**
	 * 室内垃圾是否暴露堆放下拉按钮
	 */
	private ImageButton ibtn_fly_insect_room_in_rubb_bare;
	/**
	 * 室内卫生间是否符合卫生标准编辑框
	 */
	private AutoCompleteTextView at_panel_fly_insect_room_in_hygiene_stan; 
	/**
	 * 室内卫生间是否符合卫生标准下拉按钮
	 */
	private ImageButton ibtn_panel_fly_insect_room_in_hygiene_stan;
	/**
	 * 室内地面、下水道有否积水编辑框
	 */
	private AutoCompleteTextView at_fly_insect_room_in_hydrops;
	/**
	 * 室内地面、下水道有否积水下拉按钮
	 */
	private ImageButton ibtn_fly_insect_room_in_hydrops;
	/**
	 * 室内进出通道、出入口有否门帘、风幕机、灭虫灯等设施下拉框
	 */
	private AutoCompleteTextView at_fly_insect_room_in_has_equip;
	/**
	 * 室内进出通道、出入口有否门帘、风幕机、灭虫灯等设施下拉按钮
	 */
	private ImageButton ibtn_fly_insect_room_in_has_equip;
	/**
	 * 室外建筑周边有否垃圾、积水吸引飞虫编辑框
	 */
	private AutoCompleteTextView at_fly_insect_room_out_rubb;
	/**
	 * 室外建筑周边有否垃圾、积水吸引飞虫下拉按钮
	 */
	private ImageButton ibtn_fly_insect_room_out_rubb;
	/**
	 * 室外建筑周边有否路灯、广告牌吸引飞虫编辑框
	 */
	private AutoCompleteTextView at_fly_insect_room_out_light;
	/**
	 * 室外建筑周边有否路灯、广告牌吸引飞虫下拉按钮
	 */
	private ImageButton ibtn_fly_insect_room_out_light;
	/**
	 * 环境保洁制度是否完善编辑框
	 */
	private AutoCompleteTextView at_fly_insect_envir_syst_perf;
	/**
	 * 环境保洁制度是否完善下拉按钮
	 */
	private ImageButton ibtn_fly_insect_envir_syst_perf;
	/**
	 * 卫生设施是否符合防飞虫的要求编辑框
	 */
	private AutoCompleteTextView at_fly_insect_equip_requ;
	/**
	 * 卫生设施是否符合防飞虫的要求下拉按钮
	 */
	private ImageButton ibtn_fly_insect_equip_requ;
	/**
	 * 客户方生产流程有否飞虫入侵风险编辑框
	 */
	private AutoCompleteTextView at_fly_insect_pipeline_risk;
	/**
	 * 客户方生产流程有否飞虫入侵风险下拉按钮
	 */
	private ImageButton ibtn_fly_insect_pipeline_risk;
	/**
	 * 客户方有否飞虫综合防制的理念编辑框
	 */
	private AutoCompleteTextView at_fly_insect_prevention_think;
	/**
	 * 客户方有否飞虫综合防制的理念下拉按钮
	 */
	private ImageButton ibtn_fly_insect_prevention_think;
	
	
	//==============功能按钮=====================
	/**
	 * 保存按钮
	 */
	private Button btn_save_fly_insect_watch;
	/**
	 * 返回按钮
	 */
	private Button btn_back_fly_insect_watch;
	
	//=========================================
	
	
	/**
	 * 飞虫数据项
	 */
	private MyWatchDataItem mFlyInsectItem;
	/**
	 * 蟑螂类别适配器
	 */
	private MyWatchKindAdapter mFlyInsectKindAdapter=null;
	/**
	 * 数据
	 */
	private String[] mFlyKindData=null;
	/**
	 * 数据列表
	 */
	private List<HashMap<String,Object>> listData=null;
	/**
	 * 数据项统计列表
	 */
	private List<String> mCountList=null;
	/**
	 * 其他种类上次保存的信息
	 */
	private String previous_kind="";
	
	
	
	/**
	 * 其他状态适配器
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
	 * 是否按下返回键
	 */
	private boolean isBackPressed=false;
	
	/**
	 * 任务数量小图标
	 */
	private BadgeView     mBadgeView;
	//================走马灯相关控件===============
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
	
	
	//================走马灯相关控件===============
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//使用客户自定义标题栏
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.d2e_fly_insect_watch);
		//设置用户自定义标题栏
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.d2e_titlebar);
		TextView tv = (TextView)findViewById(R.id.title);
		//设置自己的标题名字
        tv.setText(getResources().getString(R.string.fly_insect_watch_title));
        mBadgeView = (BadgeView)findViewById(R.id.countTask);
        mBadgeView.setVisibility(View.GONE);
        
        //初始化控件
        initView();
        //为界面的AutoAutoCompleteTextView设置适配器
        setUpAutoTextAdapter();
        //为功能按钮设置监听器
        setUpBtnListener();
	}
	/**
	 * 为界面的AutoAutoCompleteTextView设置适配器
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
	 * 显示警告对话框
	 */
	private void showWarmDialog(){
		isBackPressed=false;
		AlertDialog.Builder builder=new Builder(D2EFlyInsectWatchActivity.this);
		builder.setTitle(getResources().getString(R.string.warm_prompt));
		builder.setMessage(getResources().getString(R.string.quit));
		builder.setIcon(R.drawable.msg_dlg_warning);
		//确定的时候保存数据
		builder.setPositiveButton(getResources().getString(R.string.quit_ok), new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				overView();
			}
		});
		//取消的时候退出界面
		builder.setNegativeButton(getResources().getString(R.string.quit_cancel), new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		Dialog dialog=builder.create();
		dialog.show();
	}
	/**
	 * 是否完成了所有的检查
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
	 * 为功能按钮设置监听器
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
	 * 客户方有否飞虫综合防制的理念 11编辑框监听器
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
				//弹出一个对话框让人填写备注
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
	 * 客户方有否飞虫综合防制的理念 11编辑框监听器
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
	 * 客户方生产流程有否飞虫入侵风险 10编辑框监听器
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
				//弹出一个对话框让人填写备注
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
	 * 客户方生产流程有否飞虫入侵风险 10下拉按钮监听器
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
	 * 卫生设施是否符合防飞虫的要求 9编辑框监听器
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
				//弹出一个对话框让人填写备注
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
	 * 卫生设施是否符合防飞虫的要求 9编辑框监听器
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
	 * 环境保洁制度是否完善 8编辑框监听器
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
				//弹出一个对话框让人填写备注
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
	 * 环境保洁制度是否完善 8下拉按钮监听器
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
	 * 室外建筑周边有否路灯、广告牌吸引飞虫 7编辑框监听器
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
				//弹出一个对话框让人填写备注
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
	 * 室外建筑周边有否路灯、广告牌吸引飞虫 7下拉按钮监听器
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
	 * 室外建筑周边有否垃圾、积水吸引飞虫 6编辑框监听器
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
				//弹出一个对话框让人填写备注
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
	 * 室外建筑周边有否垃圾、积水吸引飞虫 6下拉按钮监听器
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
	 * 室内进出通道、出入口有否门帘、风幕机、灭虫灯等设施 5编辑框监听器
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
				//弹出一个对话框让人填写备注
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
	 * 室内进出通道、出入口有否门帘、风幕机、灭虫灯等设施 5下拉按钮监听器
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
	 * 室内地面、下水道有否积水 4编辑框监听器
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
				//弹出一个对话框让人填写备注
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
	 * 室内地面、下水道有否积水 4下拉按钮监听器
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
	 * 室内卫生间是否符合卫生标准3编辑框监听器
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
				//弹出一个对话框让人填写备注
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
	 * 室内卫生间是否符合卫生标准3下拉按钮监听器
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
	 * 室内垃圾是否暴露堆放2编辑框监听器
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
				//弹出一个对话框让人填写备注
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
	 * 室内垃圾是否暴露堆放2下拉按钮监听器
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
	 * 室内门窗、通道是否符合防飞虫要求1编辑框监听器
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
				//弹出一个对话框让人填写备注
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
	 * 室内门窗、通道是否符合防飞虫要求1下拉按钮监听器
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
	 * 返回按钮监听器
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
	 * 保存界面数据并离开界面
	 */
	private void saveViewData(){
		//已经添加了老鼠数据
		JJBaseApplication.isAddFlyWatch=true;
		
		//将数据添加到列表中
		JJBaseApplication.mWatchDataList.add(mFlyInsectItem);
		//结束界面
		overridePendingTransition(R.anim.fade, R.anim.hold);
		D2EFlyInsectWatchActivity.this.finish();
	}
	/**
	 * 保存按钮监听器
	 */
	private class SaveListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			if(isAllCheck()){
				//保存界面数据，并离开界面
				saveViewData();
			}
		}
		
	}
	/**
	 * 现场勘察时发现的飞虫编辑框监听器
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
				//弹出一个对话框让人填写备注
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
	 * 现场勘察时发现的飞虫下拉按钮监听器
	 */
	private class IbtnFlyInsectWatchKindListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			//这里弹出一个对话框来，然后是一个列表，来选择种类
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
			//这里设置et_other_kind为默认上一次保存的信息
			et_other_kind.setText(previous_kind);
			lv.setAdapter(mFlyInsectKindAdapter);
			lv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View view,
						int position, long arg3) {
					//1、获取ViewHolder对象
					ViewHolder holder=(ViewHolder) view.getTag();
					//2、当点击listView的一项时，改变CheckBox的状态
					holder.cb.toggle();
					//3、每次获取Item的时候保存CheckBox的状态
					MyWatchKindAdapter.mCheckBoxStateMap.put(position, holder.cb.isChecked());
					//4、如果CheckBox为选中状态，则往记录数量的列表中添加一项数据
					if(MyWatchKindAdapter.mCheckBoxStateMap.get(position)==true){
						mCountList.add(mFlyKindData[position]);
					}else{
						//如果CheckBox为非选中状态，则将记录数量列表中移除该项
						mCountList.remove(mFlyKindData[position]);
					}
					//5、通知TextView和ListView更新数据
//					tv_select_count.setText("已选中"+mCountList.size()+"项");
					mFlyInsectKindAdapter.notifyDataSetChanged();
					//判断是否选中了"无"
					//判断是否选中了“无”
					if(holder.tv.getText().toString().equalsIgnoreCase("无")){
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
							if(listData.get(i).get("item_tv").toString().equalsIgnoreCase("无")){
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
			//确定的时候保存数据
			btn_select_ensure.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					//kris
					boolean hasdata = false;
					
					//1、实例化一个数据对象
					MyWatchDataItem item=new MyWatchDataItem();
					item.setmMouseIndex(0);
					//2、循环遍历数据数组,把选中的信息保存
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
					//2、判断其他的种类有没有填写
					if(strOtherKind!=null&&strOtherKind.length()>0){
						hasdata = true;
						item.setmMouseKind4(strOtherKind);
					}
					
					if(D2EConfigures.TEST){
						Log.e("MouseKindItemxxxxxxxxxxxx>>", ""+item.toString());
					}
					
					
					//判断是否原先有数据，有则删除原来的，保存现在的
					if(item!=null){
						if(!mFlyInsectItem.isContainWatchDataItem(item)){
							mFlyInsectItem.mWatchDataList.add(item);
						}else{
							mFlyInsectItem.deleContainWatchDataItem(item);
							mFlyInsectItem.mWatchDataList.add(item);
						}
					}
					//把at设置一个文本，"已完成"
					if(mFlyInsectItem.mWatchDataList != null && mFlyInsectItem.mWatchDataList.size() > 0 && hasdata){
						at_fly_insect_watch_kind.setText(getResources().getString(R.string.doing_finish));						
					}
					else {
						at_fly_insect_watch_kind.setText("");
					}
					dialog.dismiss();
				}
			});
			
			//取消的时候把信息清空
			btn_cancle_select.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					clearDialogData();
					
					//关闭对话框
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
	//清空对话框收集的数据
	private void clearDialogData(){
		//循环遍历数组，把选中的都设置为未选中
		for(int i=0;i<listData.size();i++){
			if(MyWatchKindAdapter.mCheckBoxStateMap.get(i)==true){
				MyWatchKindAdapter.mCheckBoxStateMap.put(i, false);
			}
		}
		//清空记录数量的列表
		mCountList.clear();
		//将上次保存的其他信息清空
		previous_kind="";
		//将at的数据设为""表示未完成检查
		at_fly_insect_watch_kind.setText("");
	}
	/**
	 * 初始化控件
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
		
		
		
		//实例化一个对象来保存界面的数据
		mFlyInsectItem=new MyWatchDataItem();
		//设置类别为老鼠
		mFlyInsectItem.setmWatchType("Flying");
		int array=R.array.fly_insect_kind;
		//获取飞虫勘查数据及适配器
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
		
		//初始化走马灯相关控件
		initLamptView();
		
	}
	/**
	 * 把其他TextView的焦点消除，自己获得焦点
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
	 * 初始化走马灯相关控件
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
	 * 为走马灯控件设置监听器
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
	 * 获取飞虫勘查数据及适配器
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
	 * 结束界面
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
