package com.mobilercn.sop.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
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
import android.widget.SimpleAdapter;
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
 * 鼠类勘查数据收集界面
 * @author ShawnXiao
 *
 */
public class D2EMouseWatchActivity extends JJBaseActivity {
	/**
	 * 现场勘查发现的鼠类编辑框
	 */
	private AutoCompleteTextView at_mouse_watch_kind;
	/**
	 * 现场勘查发现的鼠类下拉按钮
	 */
	private ImageButton ibtn_mouse_watch_kind;
	
	/**
	 * 室内门窗、通道是否符合防鼠要求编辑框
	 */
	private AutoCompleteTextView at_mouse_watch_room_in_requ;
	/**
	 * 室内门窗、通道是否符合防鼠要求下拉按钮
	 */
	private ImageButton ibtn_mouse_watch_room_in_requ;
	/**
	 * 室内有否鼠迹编辑框
	 */
	private AutoCompleteTextView at_mouse_trac;
	/**
	 * 室内有否鼠迹下拉框
	 */
	private ImageButton ibtn_mouse_trac;
	/**
	 * 室内有否鼠否鼠类的食源、水源编辑框
	 */
	private AutoCompleteTextView at_mouse_food_drink;
	/**
	 * 室内有否鼠否鼠类的食源、水源下拉按钮
	 */
	private ImageButton ibtn_mouse_food_drink;
	/**
	 * 室内有否鼠否鼠类筑巢条件编辑框
	 */
	private AutoCompleteTextView at_mouse_nest_cond;
	/**
	 * 室内有否鼠否鼠类筑巢条件下拉按钮
	 */
	private ImageButton ibtn_mouse_nest_cond;
	/**
	 * 近期室内有否发现过活鼠/鼠尸编辑框
	 */
	private AutoCompleteTextView at_mouse_live_corpse;
	/**
	 * 近期室内有否发现过活鼠/鼠尸下拉按钮
	 */
	private ImageButton ibtn_mouse_live_corpse;
	/**
	 * 室外建筑周边有否鼠迹编辑框
	 */
	private AutoCompleteTextView at_room_out_mouse_trac;
	/**
	 * 室外建筑周边有否鼠迹下拉按钮
	 */
	private ImageButton ibtn_room_out_mouse_trac;
	/**
	 * 室外有否鼠类的食源、水源编辑框
	 */
	private AutoCompleteTextView at_room_out_mouse_food_drink;
	/**
	 * 室外有否鼠类的食源、水源下拉按钮
	 */
	private ImageButton ibtn_room_out_mouse_food_drink;
	/**
	 * 室外有否鼠类筑巢条件编辑框
	 */
	private AutoCompleteTextView at_room_out_mouse_nest_cond;
	/**
	 * 室外有否鼠类筑巢条件下拉按钮
	 */
	private ImageButton ibtn_room_out_mouse_nest_cond;
	/**
	 * 近期室外有否发现过鼠类编辑框
	 */
	private AutoCompleteTextView at_room_out_has_mouse;
	/**
	 * 近期室外有否发现过鼠类下拉按钮
	 */
	private ImageButton ibtn_room_out_has_mouse;
	/**
	 * 环境保洁制度是否完善编辑框
	 */
	private AutoCompleteTextView at_envir_clean;
	/**
	 * 环境保洁制度是否完善下拉框
	 */
	private ImageButton ibtn_envir_clean;
	/**
	 * 卫生设施是否符合防鼠要求编辑框
	 */
	private AutoCompleteTextView at_equip_match_requ;
	/**
	 * 卫生设施是否符合防鼠要求下拉按钮
	 */
	private ImageButton ibtn_equip_match_requ;
	/**
	 * 客户方建筑及物品是否遭到鼠类侵害编辑框
	 */
	private AutoCompleteTextView at_things_be_breaked;
	/**
	 * 客户方建筑及物品是否遭到鼠类侵害下拉按钮
	 */
	private ImageButton ibtn_things_be_breaked;
	/**
	 * 客户方生产流程有否鼠类入侵风险编辑框
	 */
	private AutoCompleteTextView at_pipe_line_invade_mouse;
	/**
	 * 客户方生产流程有否鼠类入侵风险下拉按钮
	 */
	private ImageButton ibtn_pipe_line_invade_mouse;
	/**
	 * 客户方有否鼠害综合防制的理念编辑框
	 */
	private AutoCompleteTextView at_mouse_preve_cure;
	/**
	 * 客户方有否鼠害综合防制的理念下拉按钮
	 */
	private ImageButton ibtn_mouse_preve_cure;
	/**
	 * 客户方有否鼠类预防设施编辑框
	 */
	private AutoCompleteTextView at_mouse_preve_cure_equip;
	/**
	 * 客户方有否鼠类预防设施下拉按钮
	 */
	private ImageButton ibtn_mouse_preve_cure_equip;
	
	
	//-----------------功能按钮------------------
	/**
	 * 保存按钮
	 */
	private Button btn_save_mouse_watch;
	/**
	 * 返回按钮
	 */
	private Button btn_back_mouse_watch;
	
	/**
	 * 鼠类别适配器
	 */
	private MyWatchKindAdapter mMouseKindAdapter;
	/**
	 * 数据
	 */
	private String[] mMouseKindData=null;
	/**
	 * 数据列表
	 * 
	 */
	private List<HashMap<String,Object>> listData=null;
	/**
	 * 选中列表统计项
	 */
	private List<String> mListCount=null;
	
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
	private ArrayAdapter mOtherStateAdapter12;
	private ArrayAdapter mOtherStateAdapter13;
	private ArrayAdapter mOtherStateAdapter14;
	private ArrayAdapter mOtherStateAdapter15;
	
	
	/**
	 * 返回键是否被按下
	 */
	private boolean isBackPressed=false;
	//-------------------------------------
	/**
	 * 任务数量小图标
	 */
	private BadgeView     mBadgeView;
	
	/**
	 * 老鼠数据项
	 */
	private MyWatchDataItem mMouseItem;
	
	/**
	 * 适配器
	 */
	private SimpleAdapter mAdapter;
	
	
	//=======================做TextView选中才跑马灯需要的控件id=============
	
	/**
	 * 现场勘查发现的鼠类容器
	 */
	private RelativeLayout panel_mouse_watch_kind;
	/**
	 * 现场勘查发现的鼠类文本
	 */
	private TextView tv_mouse_watch_kind;
	
	//室内门窗、通道是否符合防鼠要求1
	private RelativeLayout panel_mouse_watch_room_in_requ;
	private TextView tv_mouse_watch_room_in_requ;
	
	//室内有否鼠迹2
	private RelativeLayout panel_mouse_trac;
	private TextView tv_mouse_trac;
	
	//室内有否鼠否鼠类的食源、水源 3
	private RelativeLayout panel_mouse_food_drink;
	private TextView tv_mouse_food_drink;
	
	
	//室内有否鼠否鼠类筑巢条件 4
	private RelativeLayout panel_mouse_nest_cond;
	private TextView tv_mouse_nest_cond;
	
	//近期室内有否发现过活鼠/鼠尸 5
	private RelativeLayout panel_mouse_live_corpse;
	private TextView tv_mouse_live_corpse;
	
	//室外建筑周边有否鼠迹 6
	private RelativeLayout panel_room_out_mouse_trac;
	private TextView tv_room_out_mouse_trac;
	
	//室外有否鼠类的食源、水源7
	private RelativeLayout panel_room_out_mouse_food_drink;
	private TextView tv_room_out_mouse_food_drink;
	
	//室外有否鼠类筑巢条件8
	private RelativeLayout panel_room_out_mouse_nest_cond;
	private TextView tv_room_out_mouse_nest_cond;	
	
	//近期室外有否发现过鼠类9
	private RelativeLayout panel_room_out_has_mouse;
	private TextView tv_room_out_has_mouse;

	//环境保洁制度是否完善10
	private RelativeLayout panel_envir_clean;
	private TextView tv_envir_clean;
	
	//卫生设施是否符合防鼠要求11
	private RelativeLayout panel_equip_match_requ;
	private TextView tv_equip_match_requ;

	//客户方建筑及物品是否遭到鼠类侵害12
	private RelativeLayout panel_things_be_breaked;
	private TextView tv_things_be_breaked;
	
	//客户方生产流程有否鼠类入侵风险 13
	private RelativeLayout panel_pipe_line_invade_mouse;
	private TextView tv_pipe_line_invade_mouse;

	//客户方有否鼠害综合防制的理念14
	private RelativeLayout panel_mouse_preve_cure;
	private TextView tv_mouse_preve_cure;

	//客户方有否鼠类预防设施 15
	private RelativeLayout panel_mouse_preve_cure_equip;
	private TextView tv_mouse_preve_cure_equip;
	
	//=======================做TextView选中才跑马灯需要的控件id=============
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//使用客户自定义标题栏
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.d2e_mouse_watch);
		//设置用户自定义标题栏
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.d2e_titlebar);
		TextView tv = (TextView)findViewById(R.id.title);
		//设置自己的标题名字
        tv.setText(getResources().getString(R.string.mouse_watch_title));
        mBadgeView = (BadgeView)findViewById(R.id.countTask);
        mBadgeView.setVisibility(View.GONE);
		//初始化控件
        initView();
        //为适配器添加数据
//        addAdapterData();
        //为界面的AutoAutoCompleteTextView设置适配器
        setUpAutoTextAdapter();
		//为功能按钮设置监听器
        setUpBtnListener();
		
	}
	/**
	 * 为界面的AutoCompleteTextView设置监听器
	 */
	private void setUpAutoTextAdapter(){
//		at_mouse_watch_kind.setThreshold(1);
//		at_mouse_watch_kind.setAdapter(mMouseKindAdapter);
		
		at_mouse_watch_room_in_requ.setThreshold(1);
		at_mouse_watch_room_in_requ.setAdapter(mOtherStateAdapter1);
		
		at_mouse_trac.setThreshold(1);
		at_mouse_trac.setAdapter(mOtherStateAdapter2);
		
		at_mouse_food_drink.setThreshold(1);
		at_mouse_food_drink.setAdapter(mOtherStateAdapter3);
		
		at_mouse_nest_cond.setThreshold(1);
		at_mouse_nest_cond.setAdapter(mOtherStateAdapter4);
		
		at_mouse_live_corpse.setThreshold(1);
		at_mouse_live_corpse.setAdapter(mOtherStateAdapter5);
		
		at_room_out_mouse_trac.setThreshold(1);
		at_room_out_mouse_trac.setAdapter(mOtherStateAdapter6);
		
		at_room_out_mouse_food_drink.setThreshold(1);
		at_room_out_mouse_food_drink.setAdapter(mOtherStateAdapter7);
		
		at_room_out_mouse_nest_cond.setThreshold(1);
		at_room_out_mouse_nest_cond.setAdapter(mOtherStateAdapter8);
		
		at_room_out_has_mouse.setThreshold(1);
		at_room_out_has_mouse.setAdapter(mOtherStateAdapter9);
		
		at_envir_clean.setThreshold(1);
		at_envir_clean.setAdapter(mOtherStateAdapter10);
		
		at_equip_match_requ.setThreshold(1);
		at_equip_match_requ.setAdapter(mOtherStateAdapter11);
		
		at_things_be_breaked.setThreshold(1);
		at_things_be_breaked.setAdapter(mOtherStateAdapter12);
		
		at_pipe_line_invade_mouse.setThreshold(1);
		at_pipe_line_invade_mouse.setAdapter(mOtherStateAdapter13);
		
		at_mouse_preve_cure.setThreshold(1);
		at_mouse_preve_cure.setAdapter(mOtherStateAdapter14);
		
		at_mouse_preve_cure_equip.setThreshold(1);
		at_mouse_preve_cure_equip.setAdapter(mOtherStateAdapter15);
		
	}
	/**
	 * 显示警告对话框
	 */
	private void showWarmDialog(){
		isBackPressed=false;
		AlertDialog.Builder builder=new Builder(D2EMouseWatchActivity.this);
		
		builder.setTitle(getResources().getString(R.string.warm_prompt));
		builder.setMessage(getResources().getString(R.string.quit));
		builder.setIcon(R.drawable.msg_dlg_warning);
		//确定的时候保存数据
		builder.setPositiveButton(getResources().getString(R.string.quit_ok), new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				//saveViewData();
				overView(); 
			}
		});
		//取消的时候退出界面
		builder.setNegativeButton(getResources().getString(R.string.quit_cancel), new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				//overView();
			}
		});
		Dialog dialog=builder.create();
		dialog.show();
	}
	
	/**
	 * 判断是否完成了所有的检查
	 */
	private boolean isCheckAll(){
		String str0=at_mouse_watch_kind.getText().toString();
		String str1=at_mouse_watch_room_in_requ.getText().toString();
		String str2=at_mouse_trac.getText().toString();
		String str3=at_mouse_food_drink.getText().toString();
		String str4=at_mouse_nest_cond.getText().toString();
		String str5=at_mouse_live_corpse.getText().toString();
		String str6=at_room_out_mouse_trac.getText().toString();
		String str7=at_room_out_mouse_food_drink.getText().toString();
		String str8=at_room_out_mouse_nest_cond.getText().toString();
		String str9=at_room_out_has_mouse.getText().toString();
		String str10=at_envir_clean.getText().toString();
		String str11=at_equip_match_requ.getText().toString();
		String str12=at_things_be_breaked.getText().toString();
		String str13=at_pipe_line_invade_mouse.getText().toString();
		String str14=at_mouse_preve_cure.getText().toString();
		String str15=at_mouse_preve_cure_equip.getText().toString();
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
				(str11!=null&&str11.length()>0)||
				(str12!=null&&str12.length()>0)||
				(str13!=null&&str13.length()>0)||
				(str14!=null&&str14.length()>0)||
				(str15!=null&&str15.length()>0)){
			return true;
		}/*else{
			((JJBaseApplication)getApplication()).showMessage(getResources().getString(R.string.please_complete_all_check));
		}*/
		return false;
	}
	
	/**
	 * 为功能按钮设置监听器
	 */
	private void setUpBtnListener(){
		
//		at_mouse_watch_kind.setOnItemClickListener(new MouseKindItemClickListener());
		ibtn_mouse_watch_kind.setOnClickListener(new IbtnMouseKindListener());
		
		at_mouse_watch_room_in_requ.setOnItemClickListener(new MouseRoomInRequListener());
		ibtn_mouse_watch_room_in_requ.setOnClickListener(new IbtnMouseWatchRoomInRequListener());
		
		at_mouse_trac.setOnItemClickListener(new MouseTracListener());
		ibtn_mouse_trac.setOnClickListener(new IbtnMouseTracListener());
		
		at_mouse_food_drink.setOnItemClickListener(new AtMouseFoodDrinkClickListener());
		ibtn_mouse_food_drink.setOnClickListener(new IbtnMouseFoodDrinkListener());
		
		at_mouse_nest_cond.setOnItemClickListener(new AtMouseNestCondListener());
		ibtn_mouse_nest_cond.setOnClickListener(new IbtnMouseNestCondListener());
		
		at_mouse_live_corpse.setOnItemClickListener(new AtMouseLiveCorpseListener());
		ibtn_mouse_live_corpse.setOnClickListener(new IbtnMouseLiveCorpseListener());
		
		at_room_out_mouse_trac.setOnItemClickListener(new AtRoomOutMouseTracListener());
		ibtn_room_out_mouse_trac.setOnClickListener(new IbtnRoomOutMouseTracListener());
		
		at_room_out_mouse_food_drink.setOnItemClickListener(new AtRoomOutMouseFoodDrinkListener());
		ibtn_room_out_mouse_food_drink.setOnClickListener(new IbtnRoomOutMouseFoodDrinkListener());
		
		at_room_out_mouse_nest_cond.setOnItemClickListener(new AtRoomOutMouseNestCondListener());
		ibtn_room_out_mouse_nest_cond.setOnClickListener(new IbtnRoomOutMouseNestCondListener());
		
		at_room_out_has_mouse.setOnItemClickListener(new AtRoomOutHasMouseListener());
		ibtn_room_out_has_mouse.setOnClickListener(new IbtnRoomOutHasMouseListener());
		
		at_envir_clean.setOnItemClickListener(new AtEnvirCleanListener());
		ibtn_envir_clean.setOnClickListener(new IbtnEnvirCleanListener());
		
		at_equip_match_requ.setOnItemClickListener(new AtEquipMatchRequListener());
		ibtn_equip_match_requ.setOnClickListener(new IbtnEquipMatchRequListener());
		
		at_things_be_breaked.setOnItemClickListener(new AtThingsBeBreakedListener());
		ibtn_things_be_breaked.setOnClickListener(new IbtnThingsBeBreakedListener());
		
		at_pipe_line_invade_mouse.setOnItemClickListener(new AtPipeLineInvadeMouseListener());
		ibtn_pipe_line_invade_mouse.setOnClickListener(new IbtnPipeLineInvadeMouseListener());
		
		at_mouse_preve_cure.setOnItemClickListener(new AtMousePreveCureListener());
		ibtn_mouse_preve_cure.setOnClickListener(new IbtnMousePreveCureListener());
		
		at_mouse_preve_cure_equip.setOnItemClickListener(new AtMousePreveCureEquipListener());
		ibtn_mouse_preve_cure_equip.setOnClickListener(new IbtnMousePreveCureEquipListener());
		
		btn_save_mouse_watch.setOnClickListener(new SaveDataListener());
		btn_back_mouse_watch.setOnClickListener(new BackListener());
		
		
		//设置走马灯需要的监听器
		setUpLampListener();
	}
	/**
	 * 返回按钮监听器
	 */
	private class BackListener implements OnClickListener{

		@Override
		public void onClick(View v) {
//			overView();
			isBackPressed=true;
			//如果完成了所有数据，弹出对画框，提示是否保存数据
			if(isCheckAll()){
				showWarmDialog();
			}
			else {
				overView();
			}
		}
		
	}
	/**
	 * 客户方有否鼠类预防设施 15
	 */
	private class AtMousePreveCureEquipListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			final MyWatchDataItem item=new MyWatchDataItem();
			item.setmMouseIndex(15);
			
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
				final Dialog otherFaciDialog=new Dialog(D2EMouseWatchActivity.this,R.style.my_other_point_dialog);
				otherFaciDialog.setContentView(dialog_view);
				otherFaciDialog.getWindow().setLayout(300, 150);
				otherFaciDialog.show();
				btn_dialog2_yes.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						at_mouse_preve_cure_equip.setText(et_dialog2.getText().toString());
						item.setmMouseKind1(0);
						item.setmMouseKind2(0);
						item.setmMouseKind3(0);
						item.setmMouseKind4(et_dialog2.getText().toString());
						otherFaciDialog.dismiss();
						if(D2EConfigures.TEST){
							Log.e("AtMousePreveCureEquipxxxxxxxxxxxxx>>", ""+item.toString());
						}
						
					}
				});
				btn_dialog2_no.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						et_dialog2.setText("");
						at_mouse_preve_cure_equip.setText("");
						otherFaciDialog.dismiss();				
					}
				});
				
			}
			
			if(!mMouseItem.isContainWatchDataItem(item)){
				mMouseItem.mWatchDataList.add(item);
			}else{
				mMouseItem.deleContainWatchDataItem(item);
				mMouseItem.mWatchDataList.add(item);
			}
			
			if(D2EConfigures.TEST){
				Log.e("AtMousePreveCureEquipxxxxxxxxxxxx>>", ""+item.toString());
			}
		}
		
	}
	/**
	 * 
	 */
	private class IbtnMousePreveCureEquipListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			if(D2EConfigures.TEST){
				Log.e("IbtnMousePreveCureEquipListener=============>", "onClick(View v)");
			}
			String str=at_mouse_preve_cure_equip.getText().toString();
			if(str!=null&&str.length()>0){
				at_mouse_preve_cure_equip.setText("");
			}
			at_mouse_preve_cure_equip.showDropDown();
			at_mouse_preve_cure_equip.setThreshold(1);
		}
		
	}
	/**
	 * 客户方有否鼠害综合防制的理念14编辑框监听器
	 */
	private class AtMousePreveCureListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			final MyWatchDataItem item=new MyWatchDataItem();
			item.setmMouseIndex(14);
			
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
				final Dialog otherFaciDialog=new Dialog(D2EMouseWatchActivity.this,R.style.my_other_point_dialog);
				otherFaciDialog.setContentView(dialog_view);
				otherFaciDialog.getWindow().setLayout(300, 150);
				otherFaciDialog.show();
				btn_dialog2_yes.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						at_mouse_preve_cure.setText(et_dialog2.getText().toString());
						item.setmMouseKind1(0);
						item.setmMouseKind2(0);
						item.setmMouseKind3(0);
						item.setmMouseKind4(et_dialog2.getText().toString());
						otherFaciDialog.dismiss();
						if(D2EConfigures.TEST){
							Log.e("AtMousePreveCurexxxxxxxxxxxxx>>", ""+item.toString());
						}
						
					}
				});
				btn_dialog2_no.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						et_dialog2.setText("");
						at_mouse_preve_cure.setText("");
						otherFaciDialog.dismiss();				
					}
				});
				
			}
			
			if(!mMouseItem.isContainWatchDataItem(item)){
				mMouseItem.mWatchDataList.add(item);
			}else{
				mMouseItem.deleContainWatchDataItem(item);
				mMouseItem.mWatchDataList.add(item);
			}
			
			if(D2EConfigures.TEST){
				Log.e("AtMousePreveCurexxxxxxxxxxxx>>", ""+item.toString());
			}
		}
		
	}
	/**
	 * 客户方有否鼠害综合防制的理念14下拉按钮监听器
	 */
	private class IbtnMousePreveCureListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			if(D2EConfigures.TEST){
				Log.e("IbtnMousePreveCureListener=============>", "onClick(View v)");
			}
			String str=at_mouse_preve_cure.getText().toString();
			if(str!=null&&str.length()>0){
				at_mouse_preve_cure.setText("");
			}
			at_mouse_preve_cure.showDropDown();
			at_mouse_preve_cure.setThreshold(1);
		}
		
	}
	/**
	 * 客户方生产流程有否鼠类入侵风险 13编辑框监听器
	 */
	private class AtPipeLineInvadeMouseListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			final MyWatchDataItem item=new MyWatchDataItem();
			item.setmMouseIndex(13);
			
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
				final Dialog otherFaciDialog=new Dialog(D2EMouseWatchActivity.this,R.style.my_other_point_dialog);
				otherFaciDialog.setContentView(dialog_view);
				otherFaciDialog.getWindow().setLayout(300, 150);
				otherFaciDialog.show();
				btn_dialog2_yes.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						at_pipe_line_invade_mouse.setText(et_dialog2.getText().toString());
						item.setmMouseKind1(0);
						item.setmMouseKind2(0);
						item.setmMouseKind3(0);
						item.setmMouseKind4(et_dialog2.getText().toString());
						otherFaciDialog.dismiss();
						if(D2EConfigures.TEST){
							Log.e("AtPipeLineInvadeMousexxxxxxxxxxxxx>>", ""+item.toString());
						}
						
					}
				});
				btn_dialog2_no.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						et_dialog2.setText("");
						at_pipe_line_invade_mouse.setText("");
						otherFaciDialog.dismiss();				
					}
				});
				
			}
			
			if(!mMouseItem.isContainWatchDataItem(item)){
				mMouseItem.mWatchDataList.add(item);
			}else{
				mMouseItem.deleContainWatchDataItem(item);
				mMouseItem.mWatchDataList.add(item);
			}
			
			if(D2EConfigures.TEST){
				Log.e("AtPipeLineInvadeMousexxxxxxxxxxxx>>", ""+item.toString());
			}
		}
		
	}
	/**
	 * 客户方生产流程有否鼠类入侵风险 13下拉按钮
	 */
	private class IbtnPipeLineInvadeMouseListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			if(D2EConfigures.TEST){
				Log.e("IbtnPipeLineInvadeMouseListener=============>", "onClick(View v)");
			}
			String str=at_pipe_line_invade_mouse.getText().toString();
			if(str!=null&&str.length()>0){
				at_pipe_line_invade_mouse.setText("");
			}
			at_pipe_line_invade_mouse.showDropDown();
			at_pipe_line_invade_mouse.setThreshold(1);
		}
		
	}
	/**
	 * 客户方建筑及物品是否遭到鼠类侵害12编辑框监听器
	 */
	private class AtThingsBeBreakedListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			final MyWatchDataItem item=new MyWatchDataItem();
			item.setmMouseIndex(12);
			
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
				final Dialog otherFaciDialog=new Dialog(D2EMouseWatchActivity.this,R.style.my_other_point_dialog);
				otherFaciDialog.setContentView(dialog_view);
				otherFaciDialog.getWindow().setLayout(300, 150);
				otherFaciDialog.show();
				btn_dialog2_yes.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						at_things_be_breaked.setText(et_dialog2.getText().toString());
						item.setmMouseKind1(0);
						item.setmMouseKind2(0);
						item.setmMouseKind3(0);
						item.setmMouseKind4(et_dialog2.getText().toString());
						otherFaciDialog.dismiss();
						if(D2EConfigures.TEST){
							Log.e("AtThingsBeBreakedxxxxxxxxxxxxx>>", ""+item.toString());
						}
						
					}
				});
				btn_dialog2_no.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						et_dialog2.setText("");
						at_things_be_breaked.setText("");
						otherFaciDialog.dismiss();				
					}
				});
				
			}
			
			if(!mMouseItem.isContainWatchDataItem(item)){
				mMouseItem.mWatchDataList.add(item);
			}else{
				mMouseItem.deleContainWatchDataItem(item);
				mMouseItem.mWatchDataList.add(item);
			}
			
			if(D2EConfigures.TEST){
				Log.e("AtThingsBeBreakedxxxxxxxxxxxx>>", ""+item.toString());
			}
		}
		
	}
	/**
	 * 客户方建筑及物品是否遭到鼠类侵害12下拉按钮监听器
	 */
	private class IbtnThingsBeBreakedListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			if(D2EConfigures.TEST){
				Log.e("IbtnThingsBeBreakedListener=============>", "onClick(View v)");
			}
			String str=at_things_be_breaked.getText().toString();
			if(str!=null&&str.length()>0){
				at_things_be_breaked.setText("");
			}
			at_things_be_breaked.showDropDown();
			at_things_be_breaked.setThreshold(1);
		}
		
	}
	/**
	 * 卫生设施是否符合防鼠要求11编辑框监听器
	 */
	private class AtEquipMatchRequListener implements OnItemClickListener{

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
				final Dialog otherFaciDialog=new Dialog(D2EMouseWatchActivity.this,R.style.my_other_point_dialog);
				otherFaciDialog.setContentView(dialog_view);
				otherFaciDialog.getWindow().setLayout(300, 150);
				otherFaciDialog.show();
				btn_dialog2_yes.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						at_equip_match_requ.setText(et_dialog2.getText().toString());
						item.setmMouseKind1(0);
						item.setmMouseKind2(0);
						item.setmMouseKind3(0);
						item.setmMouseKind4(et_dialog2.getText().toString());
						otherFaciDialog.dismiss();
						if(D2EConfigures.TEST){
							Log.e("AtEquipMatchRequxxxxxxxxxxxxx>>", ""+item.toString());
						}
						
					}
				});
				btn_dialog2_no.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						et_dialog2.setText("");
						at_equip_match_requ.setText("");
						otherFaciDialog.dismiss();				
					}
				});
				
			}
			
			if(!mMouseItem.isContainWatchDataItem(item)){
				mMouseItem.mWatchDataList.add(item);
			}else{
				mMouseItem.deleContainWatchDataItem(item);
				mMouseItem.mWatchDataList.add(item);
			}
			
			if(D2EConfigures.TEST){
				Log.e("AtEquipMatchRequxxxxxxxxxxxx>>", ""+item.toString());
			}
		}
		
	}
	/**
	 * 卫生设施是否符合防鼠要求11下拉按钮监听器
	 */
	private class IbtnEquipMatchRequListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			if(D2EConfigures.TEST){
				Log.e("IbtnEquipMatchRequListener=============>", "onClick(View v)");
			}
			String str=at_equip_match_requ.getText().toString();
			if(str!=null&&str.length()>0){
				at_equip_match_requ.setText("");
			}
			at_equip_match_requ.showDropDown();
			at_equip_match_requ.setThreshold(1);
		}
		
	}
	/**
	 * 环境保洁制度是否完善10编辑框监听器
	 */
	private class AtEnvirCleanListener implements OnItemClickListener{

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
				final Dialog otherFaciDialog=new Dialog(D2EMouseWatchActivity.this,R.style.my_other_point_dialog);
				otherFaciDialog.setContentView(dialog_view);
				otherFaciDialog.getWindow().setLayout(300, 150);
				otherFaciDialog.show();
				btn_dialog2_yes.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						at_envir_clean.setText(et_dialog2.getText().toString());
						item.setmMouseKind1(0);
						item.setmMouseKind2(0);
						item.setmMouseKind3(0);
						item.setmMouseKind4(et_dialog2.getText().toString());
						otherFaciDialog.dismiss();
						if(D2EConfigures.TEST){
							Log.e("AtEnvirCleanxxxxxxxxxxxxx>>", ""+item.toString());
						}
						
					}
				});
				btn_dialog2_no.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						et_dialog2.setText("");
						at_envir_clean.setText("");
						otherFaciDialog.dismiss();				
					}
				});
				
			}
			
			if(!mMouseItem.isContainWatchDataItem(item)){
				mMouseItem.mWatchDataList.add(item);
			}else{
				mMouseItem.deleContainWatchDataItem(item);
				mMouseItem.mWatchDataList.add(item);
			}
			
			if(D2EConfigures.TEST){
				Log.e("AtEnvirCleanxxxxxxxxxxxx>>", ""+item.toString());
			}
		}
		
	}
	/**
	 * 环境保洁制度是否完善10下拉按钮监听器
	 */
	private class IbtnEnvirCleanListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			if(D2EConfigures.TEST){
				Log.e("IbtnEnvirCleanListener=============>", "onClick(View v)");
			}
			String str=at_envir_clean.getText().toString();
			if(str!=null&&str.length()>0){
				at_envir_clean.setText("");
			}
			at_envir_clean.showDropDown();
			at_envir_clean.setThreshold(1);
		}
		
	}
	/**
	 * 近期室外有否发现过鼠类9编辑框监听器
	 */
	private class AtRoomOutHasMouseListener implements OnItemClickListener{

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
				final Dialog otherFaciDialog=new Dialog(D2EMouseWatchActivity.this,R.style.my_other_point_dialog);
				otherFaciDialog.setContentView(dialog_view);
				otherFaciDialog.getWindow().setLayout(300, 150);
				otherFaciDialog.show();
				btn_dialog2_yes.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						at_room_out_has_mouse.setText(et_dialog2.getText().toString());
						item.setmMouseKind1(0);
						item.setmMouseKind2(0);
						item.setmMouseKind3(0);
						item.setmMouseKind4(et_dialog2.getText().toString());
						otherFaciDialog.dismiss();
						if(D2EConfigures.TEST){
							Log.e("AtRoomOutHasMousexxxxxxxxxxxxx>>", ""+item.toString());
						}
						
					}
				});
				btn_dialog2_no.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						et_dialog2.setText("");
						at_room_out_has_mouse.setText("");
						otherFaciDialog.dismiss();				
					}
				});
				
			}
			
			if(!mMouseItem.isContainWatchDataItem(item)){
				mMouseItem.mWatchDataList.add(item);
			}else{
				mMouseItem.deleContainWatchDataItem(item);
				mMouseItem.mWatchDataList.add(item);
			}
			
			if(D2EConfigures.TEST){
				Log.e("AtRoomOutHasMousexxxxxxxxxxxx>>", ""+item.toString());
			}
		}
		
	}
	/**
	 * 近期室外有否发现过鼠类10下拉按钮监听器
	 */
	private class IbtnRoomOutHasMouseListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			if(D2EConfigures.TEST){
				Log.e("IbtnRoomOutHasMouseListener=============>", "onClick(View v)");
			}
			String str=at_room_out_has_mouse.getText().toString();
			if(str!=null&&str.length()>0){
				at_room_out_has_mouse.setText("");
			}
			at_room_out_has_mouse.showDropDown();
			at_room_out_has_mouse.setThreshold(1);
		}
		
	}
	/**
	 * 室外有否鼠类筑巢条件8编辑框监听器
	 */
	private class AtRoomOutMouseNestCondListener implements OnItemClickListener{

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
				final Dialog otherFaciDialog=new Dialog(D2EMouseWatchActivity.this,R.style.my_other_point_dialog);
				otherFaciDialog.setContentView(dialog_view);
				otherFaciDialog.getWindow().setLayout(300, 150);
				otherFaciDialog.show();
				btn_dialog2_yes.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						at_room_out_mouse_nest_cond.setText(et_dialog2.getText().toString());
						item.setmMouseKind1(0);
						item.setmMouseKind2(0);
						item.setmMouseKind3(0);
						item.setmMouseKind4(et_dialog2.getText().toString());
						otherFaciDialog.dismiss();
						if(D2EConfigures.TEST){
							Log.e("AtRoomOutMouseNestCondxxxxxxxxxxxxx>>", ""+item.toString());
						}
						
					}
				});
				btn_dialog2_no.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						et_dialog2.setText("");
						at_room_out_mouse_nest_cond.setText("");
						otherFaciDialog.dismiss();				
					}
				});
				
			}
			
			if(!mMouseItem.isContainWatchDataItem(item)){
				mMouseItem.mWatchDataList.add(item);
			}else{
				mMouseItem.deleContainWatchDataItem(item);
				mMouseItem.mWatchDataList.add(item);
			}
			
			if(D2EConfigures.TEST){
				Log.e("AtRoomOutMouseNestCondxxxxxxxxxxxx>>", ""+item.toString());
			}
		}
		
	}
	/**
	 * 室外有否鼠类筑巢条件8下拉按钮监听器
	 */
	private class IbtnRoomOutMouseNestCondListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			if(D2EConfigures.TEST){
				Log.e("IbtnRoomOutMouseNestCondListener=============>", "onClick(View v)");
			}
			String str=at_room_out_mouse_nest_cond.getText().toString();
			if(str!=null&&str.length()>0){
				at_room_out_mouse_nest_cond.setText("");
			}
			at_room_out_mouse_nest_cond.showDropDown();
			at_room_out_mouse_nest_cond.setThreshold(1);
		}
		
	}
	/**
	 * 室外有否鼠类的食源、水源7编辑框监听器
	 */
	private class AtRoomOutMouseFoodDrinkListener implements OnItemClickListener{

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
				final Dialog otherFaciDialog=new Dialog(D2EMouseWatchActivity.this,R.style.my_other_point_dialog);
				otherFaciDialog.setContentView(dialog_view);
				otherFaciDialog.getWindow().setLayout(300, 150);
				otherFaciDialog.show();
				btn_dialog2_yes.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						at_room_out_mouse_food_drink.setText(et_dialog2.getText().toString());
						item.setmMouseKind1(0);
						item.setmMouseKind2(0);
						item.setmMouseKind3(0);
						item.setmMouseKind4(et_dialog2.getText().toString());
						otherFaciDialog.dismiss();
						if(D2EConfigures.TEST){
							Log.e("AtRoomOutMouseFoodDrinkxxxxxxxxxxxxx>>", ""+item.toString());
						}
						
					}
				});
				btn_dialog2_no.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						et_dialog2.setText("");
						at_room_out_mouse_food_drink.setText("");
						otherFaciDialog.dismiss();				
					}
				});
				
			}
			
			if(!mMouseItem.isContainWatchDataItem(item)){
				mMouseItem.mWatchDataList.add(item);
			}else{
				mMouseItem.deleContainWatchDataItem(item);
				mMouseItem.mWatchDataList.add(item);
			}
			
			if(D2EConfigures.TEST){
				Log.e("AtRoomOutMouseFoodDrinkxxxxxxxxxxxx>>", ""+item.toString());
			}
		}
		
	}
	/**
	 * 室外有否鼠类的食源、水源7下拉按钮监听器
	 */
	private class IbtnRoomOutMouseFoodDrinkListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			if(D2EConfigures.TEST){
				Log.e("IbtnRoomOutMouseFoodDrinkListener=============>", "onClick(View v)");
			}
			String str=at_room_out_mouse_food_drink.getText().toString();
			if(str!=null&&str.length()>0){
				at_room_out_mouse_food_drink.setText("");
			}
			at_room_out_mouse_food_drink.showDropDown();
			at_room_out_mouse_food_drink.setThreshold(1);
		}
		
	}
	/**
	 * 室外建筑周边有否鼠迹 6编辑框监听器
	 */
	private class AtRoomOutMouseTracListener implements OnItemClickListener{

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
				final Dialog otherFaciDialog=new Dialog(D2EMouseWatchActivity.this,R.style.my_other_point_dialog);
				otherFaciDialog.setContentView(dialog_view);
				otherFaciDialog.getWindow().setLayout(300, 150);
				otherFaciDialog.show();
				btn_dialog2_yes.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						at_room_out_mouse_trac.setText(et_dialog2.getText().toString());
						item.setmMouseKind1(0);
						item.setmMouseKind2(0);
						item.setmMouseKind3(0);
						item.setmMouseKind4(et_dialog2.getText().toString());
						otherFaciDialog.dismiss();
						if(D2EConfigures.TEST){
							Log.e("AtRoomOutMouseTracxxxxxxxxxxxxx>>", ""+item.toString());
						}
						
					}
				});
				btn_dialog2_no.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						et_dialog2.setText("");
						at_room_out_mouse_trac.setText("");
						otherFaciDialog.dismiss();				
					}
				});
				
			}
			
			if(!mMouseItem.isContainWatchDataItem(item)){
				mMouseItem.mWatchDataList.add(item);
			}else{
				mMouseItem.deleContainWatchDataItem(item);
				mMouseItem.mWatchDataList.add(item);
			}
			
			if(D2EConfigures.TEST){
				Log.e("AtRoomOutMouseTracxxxxxxxxxxxx>>", ""+item.toString());
			}
		}
		
	}
	/**
	 * 室外建筑周边有否鼠迹 6下拉按钮监听器
	 */
	private class IbtnRoomOutMouseTracListener implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			if(D2EConfigures.TEST){
				Log.e("IbtnRoomOutMouseTracListener=============>", "onClick(View v)");
			}
			String str=at_room_out_mouse_trac.getText().toString();
			if(str!=null&&str.length()>0){
				at_room_out_mouse_trac.setText("");
			}
			at_room_out_mouse_trac.showDropDown();
			at_room_out_mouse_trac.setThreshold(1);
		}
		
	}
	/**
	 * 近期室内有否发现过活鼠/鼠尸 5编辑框监听器
	 */
	private class AtMouseLiveCorpseListener implements OnItemClickListener{

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
				final Dialog otherFaciDialog=new Dialog(D2EMouseWatchActivity.this,R.style.my_other_point_dialog);
				otherFaciDialog.setContentView(dialog_view);
				otherFaciDialog.getWindow().setLayout(300, 150);
				otherFaciDialog.show();
				btn_dialog2_yes.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						at_mouse_live_corpse.setText(et_dialog2.getText().toString());
						item.setmMouseKind1(0);
						item.setmMouseKind2(0);
						item.setmMouseKind3(0);
						item.setmMouseKind4(et_dialog2.getText().toString());
						otherFaciDialog.dismiss();
						if(D2EConfigures.TEST){
							Log.e("AtMouseLiveCorpsexxxxxxxxxxxxx>>", ""+item.toString());
						}
						
					}
				});
				btn_dialog2_no.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						et_dialog2.setText("");
						at_mouse_live_corpse.setText("");
						otherFaciDialog.dismiss();				
					}
				});
				
			}
			
			if(!mMouseItem.isContainWatchDataItem(item)){
				mMouseItem.mWatchDataList.add(item);
			}else{
				mMouseItem.deleContainWatchDataItem(item);
				mMouseItem.mWatchDataList.add(item);
			}
			
			if(D2EConfigures.TEST){
				Log.e("AtMouseLiveCorpsexxxxxxxxxxxx>>", ""+item.toString());
			}
		}
		
	}
	/**
	 * 近期室内有否发现过活鼠/鼠尸 5下拉按钮监听器
	 */
	private class IbtnMouseLiveCorpseListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			if(D2EConfigures.TEST){
				Log.e("IbtnMouseLiveCorpseListener=============>", "onClick(View v)");
			}
			String str=at_mouse_live_corpse.getText().toString();
			if(str!=null&&str.length()>0){
				at_mouse_live_corpse.setText("");
			}
			at_mouse_live_corpse.showDropDown();
			at_mouse_live_corpse.setThreshold(1);
		}
		
	}
	/**
	 * 室内有否鼠否鼠类筑巢条件编辑框监听器
	 */
	private class AtMouseNestCondListener implements OnItemClickListener{

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
				final Dialog otherFaciDialog=new Dialog(D2EMouseWatchActivity.this,R.style.my_other_point_dialog);
				otherFaciDialog.setContentView(dialog_view);
				otherFaciDialog.getWindow().setLayout(300, 150);
				otherFaciDialog.show();
				btn_dialog2_yes.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						at_mouse_nest_cond.setText(et_dialog2.getText().toString());
						item.setmMouseKind1(0);
						item.setmMouseKind2(0);
						item.setmMouseKind3(0);
						item.setmMouseKind4(et_dialog2.getText().toString());
						otherFaciDialog.dismiss();
						if(D2EConfigures.TEST){
							Log.e("AtMouseNestCondxxxxxxxxxxxxx>>", ""+item.toString());
						}
						
					}
				});
				btn_dialog2_no.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						et_dialog2.setText("");
						at_mouse_nest_cond.setText("");
						otherFaciDialog.dismiss();				
					}
				});
				
			}
			
			if(!mMouseItem.isContainWatchDataItem(item)){
				mMouseItem.mWatchDataList.add(item);
			}else{
				mMouseItem.deleContainWatchDataItem(item);
				mMouseItem.mWatchDataList.add(item);
			}
			
			if(D2EConfigures.TEST){
				Log.e("AtMouseNestCondxxxxxxxxxxxx>>", ""+item.toString());
			}
		}
		
	}
	/**
	 * 室内有否鼠否鼠类筑巢条件下拉按钮监听器
	 */
	private class IbtnMouseNestCondListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			if(D2EConfigures.TEST){
				Log.e("IbtnMouseNestCondListener=============>", "onClick(View v)");
			}
			String str=at_mouse_nest_cond.getText().toString();
			if(str!=null&&str.length()>0){
				at_mouse_nest_cond.setText("");
			}
			at_mouse_nest_cond.showDropDown();
			at_mouse_nest_cond.setThreshold(1);
		}
		
	}
	/**
	 * 
	 * 室内有否鼠否鼠类的食源、水源编辑框监听器
	 *
	 */
	private class AtMouseFoodDrinkClickListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long id) {
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
				final Dialog otherFaciDialog=new Dialog(D2EMouseWatchActivity.this,R.style.my_other_point_dialog);
				otherFaciDialog.setContentView(dialog_view);
				otherFaciDialog.getWindow().setLayout(300, 150);
				otherFaciDialog.show();
				btn_dialog2_yes.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						at_mouse_food_drink.setText(et_dialog2.getText().toString());
						item.setmMouseKind1(0);
						item.setmMouseKind2(0);
						item.setmMouseKind3(0);
						item.setmMouseKind4(et_dialog2.getText().toString());
						otherFaciDialog.dismiss();
						if(D2EConfigures.TEST){
							Log.e("AtMouseFoodDrinkxxxxxxxxxxxxx>>", ""+item.toString());
						}
						
					}
				});
				btn_dialog2_no.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						et_dialog2.setText("");
						at_mouse_food_drink.setText("");
						otherFaciDialog.dismiss();				
					}
				});
				
//				String str=createMouseRoomInRequDialog();
//				if(str!=null&&str.length()>0){
//					item.setmMouseKind1(0);
//					item.setmMouseKind2(0);
//					item.setmMouseKind3(0);
//					item.setmMouseKind4(str);
//				}
				
			}
//			mMouseItem.mWatchDataList.add(item);
			if(!mMouseItem.isContainWatchDataItem(item)){
				mMouseItem.mWatchDataList.add(item);
			}else{
				mMouseItem.deleContainWatchDataItem(item);
				mMouseItem.mWatchDataList.add(item);
			}
//			if(D2EConfigures.TEST){
//				Log.e("MouseKindItemxxxxxxxxxxxx>>", ""+item.toString());
//			}
			if(D2EConfigures.TEST){
				Log.e("AtMouseFoodDrinkxxxxxxxxxxxx>>", ""+item.toString());
			}
		}
		
	}
	/**
	 * 室内有否鼠否鼠类的食源、水源下拉框监听器
	 */
	private class IbtnMouseFoodDrinkListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			if(D2EConfigures.TEST){
				Log.e("IbtnMouseFoodDrinkListener=============>", "onClick(View v)");
			}
			String str=at_mouse_food_drink.getText().toString();
			if(str!=null&&str.length()>0){
				at_mouse_food_drink.setText("");
			}
			at_mouse_food_drink.showDropDown();
			at_mouse_food_drink.setThreshold(1);
		}
		
	}
	/**
	 * 室内有否鼠迹下拉框监听器
	 */
	private class IbtnMouseTracListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			if(D2EConfigures.TEST){
				Log.e("IbtnMouseTracListener=============>", "onClick(View v)");
			}
			String str=at_mouse_trac.getText().toString();
			if(str!=null&&str.length()>0){
				at_mouse_trac.setText("");
			}
			at_mouse_trac.showDropDown();
			at_mouse_trac.setThreshold(1);
		}
		
	}
	/**
	 * 室内有否鼠迹编辑框监听器
	 */
	private class MouseTracListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long id) {
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
				final Dialog otherFaciDialog=new Dialog(D2EMouseWatchActivity.this,R.style.my_other_point_dialog);
				otherFaciDialog.setContentView(dialog_view);
				otherFaciDialog.getWindow().setLayout(300, 150);
				otherFaciDialog.show();
				btn_dialog2_yes.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						at_mouse_trac.setText(et_dialog2.getText().toString());
						item.setmMouseKind1(0);
						item.setmMouseKind2(0);
						item.setmMouseKind3(0);
						item.setmMouseKind4(et_dialog2.getText().toString());
						otherFaciDialog.dismiss();
						if(D2EConfigures.TEST){
							Log.e("MouseRoomInReqxxxxxxxxxxxxx>>", ""+item.toString());
						}
						
					}
				});
				btn_dialog2_no.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						et_dialog2.setText("");
						at_mouse_trac.setText("");
						otherFaciDialog.dismiss();				
					}
				});
				
//				String str=createMouseRoomInRequDialog();
//				if(str!=null&&str.length()>0){
//					item.setmMouseKind1(0);
//					item.setmMouseKind2(0);
//					item.setmMouseKind3(0);
//					item.setmMouseKind4(str);
//				}
				
			}
//			mMouseItem.mWatchDataList.add(item);
			if(!mMouseItem.isContainWatchDataItem(item)){
				mMouseItem.mWatchDataList.add(item);
			}else{
				mMouseItem.deleContainWatchDataItem(item);
				mMouseItem.mWatchDataList.add(item);
			}
//			if(D2EConfigures.TEST){
//				Log.e("MouseKindItemxxxxxxxxxxxx>>", ""+item.toString());
//			}
			if(D2EConfigures.TEST){
				Log.e("MouseTracxxxxxxxxxxxxx>>", ""+item.toString());
			}
		}
		
	}
	/**
	 * 室内门窗、通道是否符合防鼠要求下拉框监听器
	 */
	private class IbtnMouseWatchRoomInRequListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			String str=at_mouse_watch_room_in_requ.getText().toString();
			if(str!=null&&str.length()>0){
				at_mouse_watch_room_in_requ.setText("");
			}
			at_mouse_watch_room_in_requ.showDropDown();
			at_mouse_watch_room_in_requ.setThreshold(1);
		}
		
	}
	/**
	 * 室内门窗、通道是否符合防鼠要求编辑框监听器
	 */
	private class MouseRoomInRequListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long id) {
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
				final Dialog otherFaciDialog=new Dialog(D2EMouseWatchActivity.this,R.style.my_other_point_dialog);
				otherFaciDialog.setContentView(dialog_view);
				otherFaciDialog.getWindow().setLayout(300, 150);
				otherFaciDialog.show();
				btn_dialog2_yes.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						at_mouse_watch_room_in_requ.setText(et_dialog2.getText().toString());
						item.setmMouseKind1(0);
						item.setmMouseKind2(0);
						item.setmMouseKind3(0);
						item.setmMouseKind4(et_dialog2.getText().toString());
						otherFaciDialog.dismiss();
						if(D2EConfigures.TEST){
							Log.e("MouseRoomInReqxxxxxxxxxxxxx>>", ""+item.toString());
						}
						
					}
				});
				btn_dialog2_no.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						et_dialog2.setText("");
						at_mouse_watch_room_in_requ.setText("");
						otherFaciDialog.dismiss();				
					}
				});
				
//				String str=createMouseRoomInRequDialog();
//				if(str!=null&&str.length()>0){
//					item.setmMouseKind1(0);
//					item.setmMouseKind2(0);
//					item.setmMouseKind3(0);
//					item.setmMouseKind4(str);
//				}
				
			}
//			mMouseItem.mWatchDataList.add(item);
			if(!mMouseItem.isContainWatchDataItem(item)){
				mMouseItem.mWatchDataList.add(item);
			}else{
				mMouseItem.deleContainWatchDataItem(item);
				mMouseItem.mWatchDataList.add(item);
			}
//			if(D2EConfigures.TEST){
//				Log.e("MouseKindItemxxxxxxxxxxxx>>", ""+item.toString());
//			}
			if(D2EConfigures.TEST){
				Log.e("MouseRoomInReqxxxxxxxxxxxxx>>", ""+item.toString());
			}
		}
		
	}
//	/**
//	 * 老鼠类别项监听器
//	 */
//	private class MouseKindItemClickListener implements OnItemClickListener{
//		@Override
//		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
//				long id) {
////			D2EListAdapterItam item=(D2EListAdapterItam) mMouseKindAdapter.getItem(position);
////			at_mouse_watch_kind.setText(item.getTitle());
//			//这里要判断选择的是什么，然后做好赋值给保存数据项
//			if(D2EConfigures.TEST){
//				Log.e("position============>", ""+position);
//				Log.e("position==0============>", ""+(position==0));
//			}
//			final MyWatchDataItem item=new MyWatchDataItem();
//			item.setmMouseIndex(0);
//			if(position==0){
//				item.setmMouseKind1(1);
//				item.setmMouseKind2(0);
//				item.setmMouseKind3(0);
//				item.setmMouseKind4("");
//				
//			}else if(position==1){
//				item.setmMouseKind1(0);
//				item.setmMouseKind2(1);
//				item.setmMouseKind3(0);
//				item.setmMouseKind4("");
//			}else if(position==2){
//				item.setmMouseKind1(0);
//				item.setmMouseKind2(0);
//				item.setmMouseKind3(1);
//				item.setmMouseKind4("");
//			}else if(position==3){
//				LayoutInflater inflater=getLayoutInflater();
//		    	View dialog_view=inflater.inflate(R.layout.dialog2, null);
//		    	final EditText et_dialog2=(EditText) dialog_view.findViewById(R.id.et_dialog2);
//		    	final Button btn_dialog2_yes=(Button) dialog_view.findViewById(R.id.btn_dialog2_yes);
//		    	final Button btn_dialog2_no=(Button) dialog_view.findViewById(R.id.btn_dialog2_no);
//				final Dialog otherFaciDialog=new Dialog(D2EMouseWatchActivity.this,R.style.my_other_point_dialog);
//				otherFaciDialog.setContentView(dialog_view);
//				otherFaciDialog.getWindow().setLayout(300, 150);
//				otherFaciDialog.show();
//				btn_dialog2_yes.setOnClickListener(new OnClickListener() {
//					
//					@Override
//					public void onClick(View v) {
//						at_mouse_watch_kind.setText(et_dialog2.getText().toString());
//						item.setmMouseKind1(0);
//						item.setmMouseKind2(0);
//						item.setmMouseKind3(0);
//						item.setmMouseKind4(et_dialog2.getText().toString());
//						otherFaciDialog.dismiss();
//						if(D2EConfigures.TEST){
//							Log.e("MouseKindItemxxxxxxxxxxxx>>", ""+item.toString());
//						}
//						
//					}
//				});
//				btn_dialog2_no.setOnClickListener(new OnClickListener() {
//					
//					@Override
//					public void onClick(View v) {
//						at_mouse_watch_kind.setText("");
//						otherFaciDialog.dismiss();				
//					}
//				});
//				
//				
//			}
//			//如果不包含则添加
//			if(D2EConfigures.TEST){
//				Log.e("!mMouseItem.isContainWatchDataItem(item)=====>", ""+(!mMouseItem.isContainWatchDataItem(item)));
//			}
//			if(!mMouseItem.isContainWatchDataItem(item)){
//				mMouseItem.mWatchDataList.add(item);
//			}else{
//				mMouseItem.deleContainWatchDataItem(item);
//				mMouseItem.mWatchDataList.add(item);
//			}
//			if(D2EConfigures.TEST){
//				Log.e("MouseKindItemxxxxxxxxxxxx>>", ""+item.toString());
//			}
//		}
//		
//	}
	/**
	 * 老鼠类别下拉按钮监听器
	 */
	private class IbtnMouseKindListener implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			//这里弹出一个对话框来，然后是一个列表，来选择种类
			AlertDialog.Builder  builder=new Builder(D2EMouseWatchActivity.this);
			LayoutInflater inflater=LayoutInflater.from(D2EMouseWatchActivity.this);
			View view=inflater.inflate(R.layout.d2e_dialog_watch_kind, null);
//			final TextView tv_select_count=(TextView) view.findViewById(R.id.tv_select_count);
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
			lv.setAdapter(mMouseKindAdapter);
			lv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View view,
						int position, long arg3) {
					ViewHolder holder=(ViewHolder) view.getTag();
					//listView一项选中的时候改变CheckBox的状态
					holder.cb.toggle();
					//每次获取点击的item时，保存CheckBox的状态
					MyWatchKindAdapter.mCheckBoxStateMap.put(position, holder.cb.isChecked());
					//如果CheckBox的状态为选中，则往记录列表内中添加一项
					if(holder.cb.isChecked()){
						mListCount.add(mMouseKindData[position]);
					}else{
						//否则，则删除一项
						mListCount.remove(mMouseKindData[position]);
					}
					//改变选中条目的显示和通知listView更新
					mMouseKindAdapter.notifyDataSetChanged();
					
					//这里判断无是否被选中,选中则其他CheckBox都修改为为选中
					if(holder.tv.getText().toString().equalsIgnoreCase("无")){
//						holder.cb.toggle();
						if(holder.cb.isChecked()==true){
							for(int i=0;i<listData.size();i++){
								if(D2EConfigures.TEST){
									Log.e("String.valueOf(i)==========>", ""+(String.valueOf(i)));
									Log.e("holder.tv.getTag().toString()xxxxxxxxxxx>", ""+(holder.tv.getTag().toString()));
									Log.e("String.valueOf(i).equalsIgnoreCase(holder.tv.getTag().toString())&&&&&&&&&>", ""+(String.valueOf(i).equalsIgnoreCase(holder.tv.getTag().toString())));
								}
								if(!String.valueOf(i).equalsIgnoreCase(holder.tv.getTag().toString())){
									MyWatchKindAdapter.mCheckBoxStateMap.put(i, false);
//									view.setEnabled(false);
								}
							}
							et_other_kind.setText("");
							et_other_kind.setEnabled(false);
							et_other_kind.setClickable(false);
							et_other_kind.setFocusable(false);
							et_other_kind.setFocusableInTouchMode(false);
						}else{
//							view.setEnabled(true);
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
//					tv_select_count.setText("已选中"+mListCount.size()+"项");
				}
			});
			btn_select_ensure.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					boolean hasdata = false;
					//确定的时候，循环遍历列表,把选中的放到数据列表中去
					MyWatchDataItem item=new MyWatchDataItem();
					item.setmMouseIndex(0);
					for(int i=0;i<listData.size();i++){
						if(D2EConfigures.TEST){
							Log.e("MyWatchKindAdapter.mCheckBoxStateMap.get(i)===========>", ""+i+"xxxx"+(MyWatchKindAdapter.mCheckBoxStateMap.get(i)));
						}
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
					if(strOtherKind!=null&&strOtherKind.length()>0){
						hasdata = true;
						item.setmMouseKind4(strOtherKind);
					}
					//判断其他的种类有没有填写
					if(D2EConfigures.TEST){
						Log.e("MouseKindItemxxxxxxxxxxxx>>", ""+item.toString());
					}
					
					//这里要把数据保存到列表
					//判断是否原先有数据，有则删除原来的，保存现在的
					if(item!=null){
						if(!mMouseItem.isContainWatchDataItem(item)){
							mMouseItem.mWatchDataList.add(item);
						}else{
							mMouseItem.deleContainWatchDataItem(item);
							mMouseItem.mWatchDataList.add(item);
						}
					}
					if(mMouseItem.mWatchDataList != null && mMouseItem.mWatchDataList.size() > 0 && hasdata){
						at_mouse_watch_kind.setText(getResources().getString(R.string.doing_finish));						
					}
					else{
						at_mouse_watch_kind.setText("");						
					}
					//把at设置一个文本，"已完成"
					dialog.dismiss();
					
				}
			});
			btn_cancle_select.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					//取消的时候把CheckBox的选中的状态都设置为未选中
					clearDialogData();
					dialog.dismiss();
				}
			});
			dialog.setOnKeyListener(new OnKeyListener() {
				
				@Override
				public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
					clearDialogData();
					dialog.dismiss();
					return false;
				}
			});
		}
		
	}
	//清空对话框收集的资料
	private void clearDialogData(){
		for(int i=0;i<listData.size();i++){
			if(MyWatchKindAdapter.mCheckBoxStateMap.get(i)==true){
				MyWatchKindAdapter.mCheckBoxStateMap.put(i, false);
			}
		}
		mListCount.clear();
		previous_kind="";
		at_mouse_watch_kind.setText("");
	}
	/**
	 * 保存界面数据
	 */
	private void saveViewData(){
		//已经添加了老鼠数据
		JJBaseApplication.isAddMousWatch=true;
		
		//将数据添加到列表中
		JJBaseApplication.mWatchDataList.add(mMouseItem);
		//离开界面
		D2EMouseWatchActivity.this.finish();
	}
	/**
	 * 保存数据监听器
	 */
	private class SaveDataListener implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			//判断检查是否都完成了
			if(isCheckAll()){
				//保存界面数据
				saveViewData();
				//结束界面
			}
		}
		
	}
	/**
	 * 初始化组件
	 */
	private void initView(){
		at_mouse_watch_kind=(AutoCompleteTextView) this.findViewById(R.id.at_mouse_watch_kind);
		ibtn_mouse_watch_kind=(ImageButton) this.findViewById(R.id.ibtn_mouse_watch_kind);
		panel_mouse_watch_kind=(RelativeLayout) this.findViewById(R.id.panel_mouse_watch_kind);
		tv_mouse_watch_kind=(TextView) this.findViewById(R.id.tv_mouse_watch_kind);
		
		at_mouse_watch_room_in_requ=(AutoCompleteTextView) this.findViewById(R.id.at_mouse_watch_room_in_requ);
		ibtn_mouse_watch_room_in_requ=(ImageButton) this.findViewById(R.id.ibtn_mouse_watch_room_in_requ);
		at_mouse_trac=(AutoCompleteTextView) this.findViewById(R.id.at_mouse_trac);
		ibtn_mouse_trac=(ImageButton) this.findViewById(R.id.ibtn_mouse_trac);
		at_mouse_food_drink=(AutoCompleteTextView) this.findViewById(R.id.at_mouse_food_drink);
		ibtn_mouse_food_drink=(ImageButton) this.findViewById(R.id.ibtn_mouse_food_drink);
		at_mouse_nest_cond=(AutoCompleteTextView) this.findViewById(R.id.at_mouse_nest_cond);
		ibtn_mouse_nest_cond=(ImageButton) this.findViewById(R.id.ibtn_mouse_nest_cond);
		at_mouse_live_corpse=(AutoCompleteTextView) this.findViewById(R.id.at_mouse_live_corpse);
		ibtn_mouse_live_corpse=(ImageButton) this.findViewById(R.id.ibtn_mouse_live_corpse);
		at_room_out_mouse_trac=(AutoCompleteTextView) this.findViewById(R.id.at_room_out_mouse_trac);
		ibtn_room_out_mouse_trac=(ImageButton) this.findViewById(R.id.ibtn_room_out_mouse_trac);
		at_room_out_mouse_food_drink=(AutoCompleteTextView) this.findViewById(R.id.at_room_out_mouse_food_drink);
		ibtn_room_out_mouse_food_drink=(ImageButton) this.findViewById(R.id.ibtn_room_out_mouse_food_drink);
		at_room_out_mouse_nest_cond=(AutoCompleteTextView) this.findViewById(R.id.at_room_out_mouse_nest_cond);
		ibtn_room_out_mouse_nest_cond=(ImageButton) this.findViewById(R.id.ibtn_room_out_mouse_nest_cond);
		at_room_out_has_mouse=(AutoCompleteTextView) this.findViewById(R.id.at_room_out_has_mouse);
		ibtn_room_out_has_mouse=(ImageButton) this.findViewById(R.id.ibtn_room_out_has_mouse);
		at_envir_clean=(AutoCompleteTextView) this.findViewById(R.id.at_envir_clean);
		ibtn_envir_clean=(ImageButton) this.findViewById(R.id.ibtn_envir_clean);
		at_equip_match_requ=(AutoCompleteTextView) this.findViewById(R.id.at_equip_match_requ);
		ibtn_equip_match_requ=(ImageButton) this.findViewById(R.id.ibtn_equip_match_requ);
		at_things_be_breaked=(AutoCompleteTextView) this.findViewById(R.id.at_things_be_breaked);
		ibtn_things_be_breaked=(ImageButton) this.findViewById(R.id.ibtn_things_be_breaked);
		at_pipe_line_invade_mouse=(AutoCompleteTextView) this.findViewById(R.id.at_pipe_line_invade_mouse);
		ibtn_pipe_line_invade_mouse=(ImageButton) this.findViewById(R.id.ibtn_pipe_line_invade_mouse);
		at_mouse_preve_cure=(AutoCompleteTextView) this.findViewById(R.id.at_mouse_preve_cure);
		ibtn_mouse_preve_cure=(ImageButton) this.findViewById(R.id.ibtn_mouse_preve_cure);
		at_mouse_preve_cure_equip=(AutoCompleteTextView) this.findViewById(R.id.at_mouse_preve_cure_equip);
		ibtn_mouse_preve_cure_equip=(ImageButton) this.findViewById(R.id.ibtn_mouse_preve_cure_equip);
		
		btn_save_mouse_watch=(Button) this.findViewById(R.id.btn_save_mouse_watch);
		btn_back_mouse_watch=(Button) this.findViewById(R.id.btn_back_mouse_watch);
		
		
		//实例化一个对象来保存界面的数据
		mMouseItem=new MyWatchDataItem();
		//设置类别为老鼠
		mMouseItem.setmWatchType("Mouse");
		int array=R.array.mouse_kind;
		
		//获取老鼠适配器及相关数据
		getMouseKindAdapterData();
		
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
		mOtherStateAdapter12=ArrayAdapter.createFromResource(this, array, R.layout.simple_dropdown_item_1line);
		mOtherStateAdapter13=ArrayAdapter.createFromResource(this, array, R.layout.simple_dropdown_item_1line);
		mOtherStateAdapter14=ArrayAdapter.createFromResource(this, array, R.layout.simple_dropdown_item_1line);
		array=R.array.mouse_has_equip;
		mOtherStateAdapter15=ArrayAdapter.createFromResource(this, array, R.layout.simple_dropdown_item_1line);
		
		//初始化走马灯需要的控件
		//0
		panel_mouse_watch_kind=(RelativeLayout) this.findViewById(R.id.panel_mouse_watch_kind);
		tv_mouse_watch_kind=(TextView) this.findViewById(R.id.tv_mouse_watch_kind);
		//1
		panel_mouse_watch_room_in_requ=(RelativeLayout) this.findViewById(R.id.panel_mouse_watch_room_in_requ);
		tv_mouse_watch_room_in_requ=(TextView) this.findViewById(R.id.tv_mouse_watch_room_in_requ);
		//2
		panel_mouse_trac=(RelativeLayout) this.findViewById(R.id.panel_mouse_trac);
		tv_mouse_trac=(TextView) this.findViewById(R.id.tv_mouse_trac);
		//3
		panel_mouse_food_drink=(RelativeLayout) this.findViewById(R.id.panel_mouse_food_drink);
		tv_mouse_food_drink=(TextView) this.findViewById(R.id.tv_mouse_food_drink);
		//4
		panel_mouse_nest_cond=(RelativeLayout) this.findViewById(R.id.panel_mouse_nest_cond);
		tv_mouse_nest_cond=(TextView) this.findViewById(R.id.tv_mouse_nest_cond);
		//5
		panel_mouse_live_corpse=(RelativeLayout) this.findViewById(R.id.panel_mouse_live_corpse);
		tv_mouse_live_corpse=(TextView) this.findViewById(R.id.tv_mouse_live_corpse);
		//6
		panel_room_out_mouse_trac=(RelativeLayout) this.findViewById(R.id.panel_room_out_mouse_trac);
		tv_room_out_mouse_trac=(TextView) this.findViewById(R.id.tv_room_out_mouse_trac);
		//7
		panel_room_out_mouse_food_drink=(RelativeLayout) this.findViewById(R.id.panel_room_out_mouse_food_drink);
		tv_room_out_mouse_food_drink=(TextView) this.findViewById(R.id.tv_room_out_mouse_food_drink);
		//8
		panel_room_out_mouse_nest_cond=(RelativeLayout) this.findViewById(R.id.panel_room_out_mouse_nest_cond);
		tv_room_out_mouse_nest_cond=(TextView) this.findViewById(R.id.tv_room_out_mouse_nest_cond);
		//9
		panel_room_out_has_mouse=(RelativeLayout) this.findViewById(R.id.panel_room_out_has_mouse);
		tv_room_out_has_mouse=(TextView) this.findViewById(R.id.tv_room_out_has_mouse);
		//10
		panel_envir_clean=(RelativeLayout) this.findViewById(R.id.panel_envir_clean);
		tv_envir_clean=(TextView) this.findViewById(R.id.tv_envir_clean);
		//11
		panel_equip_match_requ=(RelativeLayout) this.findViewById(R.id.panel_equip_match_requ);
		tv_equip_match_requ=(TextView) this.findViewById(R.id.tv_equip_match_requ);
		//12
		panel_things_be_breaked=(RelativeLayout) this.findViewById(R.id.panel_things_be_breaked);
		tv_things_be_breaked=(TextView) this.findViewById(R.id.tv_things_be_breaked);
		//13
		panel_pipe_line_invade_mouse=(RelativeLayout) this.findViewById(R.id.panel_pipe_line_invade_mouse);
		tv_pipe_line_invade_mouse=(TextView) this.findViewById(R.id.tv_pipe_line_invade_mouse);
		//14
		panel_mouse_preve_cure=(RelativeLayout) this.findViewById(R.id.panel_mouse_preve_cure);
		tv_mouse_preve_cure=(TextView) this.findViewById(R.id.tv_mouse_preve_cure);
		//15
		panel_mouse_preve_cure_equip=(RelativeLayout) this.findViewById(R.id.panel_mouse_preve_cure_equip);
		tv_mouse_preve_cure_equip=(TextView) this.findViewById(R.id.tv_mouse_preve_cure_equip);
		
	}
	/**
	 * 把其他的TextView的焦点去除
	 */
	private void disableOtherTextFocus(TextView tv){
		tv_mouse_watch_kind.setSelected(false);
		tv_mouse_watch_room_in_requ.setSelected(false);
		tv_mouse_trac.setSelected(false);
		tv_mouse_food_drink.setSelected(false);
		tv_mouse_nest_cond.setSelected(false);
		tv_mouse_live_corpse.setSelected(false);
		tv_room_out_mouse_trac.setSelected(false);
		tv_room_out_mouse_food_drink.setSelected(false);
		tv_room_out_mouse_nest_cond.setSelected(false);
		tv_room_out_has_mouse.setSelected(false);
		tv_envir_clean.setSelected(false);
		tv_equip_match_requ.setSelected(false);
		tv_things_be_breaked.setSelected(false);
		tv_pipe_line_invade_mouse.setSelected(false);
		tv_mouse_preve_cure.setSelected(false);
		tv_mouse_preve_cure_equip.setSelected(false);
		tv.setSelected(true);
	}
	
	/**
	 * 设置走马灯需要的监听器
	 */
	private void setUpLampListener(){
		//0
		panel_mouse_watch_kind.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				disableOtherTextFocus(tv_mouse_watch_kind);
			}
		});
		//1
		panel_mouse_watch_room_in_requ.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				disableOtherTextFocus(tv_mouse_watch_room_in_requ);
			}
		});
		//2
		panel_mouse_trac.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				disableOtherTextFocus(tv_mouse_trac);
			}
		});
		//3
		panel_mouse_food_drink.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				disableOtherTextFocus(tv_mouse_food_drink);
			}
		});
		//4
		panel_mouse_nest_cond.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				disableOtherTextFocus(tv_mouse_nest_cond);
			}
		});
		//5
		panel_mouse_live_corpse.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				disableOtherTextFocus(tv_mouse_live_corpse);
			}
		});
		//6
		panel_room_out_mouse_trac.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				disableOtherTextFocus(tv_room_out_mouse_trac);
			}
		});
		//7
		panel_room_out_mouse_food_drink.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				disableOtherTextFocus(tv_room_out_mouse_food_drink);
			}
		});
		//8
		panel_room_out_mouse_nest_cond.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				disableOtherTextFocus(tv_room_out_mouse_nest_cond);
			}
		});
		//9
		panel_room_out_has_mouse.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				disableOtherTextFocus(tv_room_out_has_mouse);
			}
		});
		//10
		panel_envir_clean.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				disableOtherTextFocus(tv_envir_clean);
			}
		});
		//11
		panel_equip_match_requ.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				disableOtherTextFocus(tv_equip_match_requ);
			}
		});
		//12
		panel_things_be_breaked.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				disableOtherTextFocus(tv_things_be_breaked);
			}
		});
		//13
		panel_pipe_line_invade_mouse.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				disableOtherTextFocus(tv_pipe_line_invade_mouse);
			}
		});
		//14
		panel_mouse_preve_cure.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				disableOtherTextFocus(tv_mouse_preve_cure);
			}
		});
		//15
		panel_mouse_preve_cure_equip.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				disableOtherTextFocus(tv_mouse_preve_cure_equip);
			}
		});
	}
	
	/**
	 * 获取老鼠数据及相关数据
	 */
	private void getMouseKindAdapterData() {
		mListCount=new ArrayList<String>();
		listData=new ArrayList<HashMap<String,Object>>();
		//数据
		mMouseKindData=getResources().getStringArray(R.array.mouse_kind);
		for(int i=0;i<mMouseKindData.length;i++){
			HashMap<String,Object> map=new HashMap<String,Object>();
			map.put("item_tv", mMouseKindData[i]);
			map.put("item_cb", false);
			listData.add(map);
		}
		
		mMouseKindAdapter=new MyWatchKindAdapter(D2EMouseWatchActivity.this, 
				listData, 
				R.layout.d2e_at_watch_kind_item, 
				new String[]{"item_tv","item_cb"}, 
				new int[]{R.id.tv_list_item,R.id.cb_list_item});
	}
	@Override
	public void backAciton() {
		isBackPressed=true;
		//如果完成了所有数据，弹出对画框，提示是否保存数据
		if(isCheckAll()){
			showWarmDialog();
		}else{
			overView();
		}
	}
	
	/**
	 * 结束本界面
	 */
	private void overView(){
		overridePendingTransition(R.anim.fade, R.anim.hold);
		D2EMouseWatchActivity.this.finish();
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
