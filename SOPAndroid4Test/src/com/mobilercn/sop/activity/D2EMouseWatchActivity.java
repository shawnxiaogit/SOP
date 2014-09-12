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
 * ���࿱�������ռ�����
 * @author ShawnXiao
 *
 */
public class D2EMouseWatchActivity extends JJBaseActivity {
	/**
	 * �ֳ����鷢�ֵ�����༭��
	 */
	private AutoCompleteTextView at_mouse_watch_kind;
	/**
	 * �ֳ����鷢�ֵ�����������ť
	 */
	private ImageButton ibtn_mouse_watch_kind;
	
	/**
	 * �����Ŵ���ͨ���Ƿ���Ϸ���Ҫ��༭��
	 */
	private AutoCompleteTextView at_mouse_watch_room_in_requ;
	/**
	 * �����Ŵ���ͨ���Ƿ���Ϸ���Ҫ��������ť
	 */
	private ImageButton ibtn_mouse_watch_room_in_requ;
	/**
	 * �����з��󼣱༭��
	 */
	private AutoCompleteTextView at_mouse_trac;
	/**
	 * �����з���������
	 */
	private ImageButton ibtn_mouse_trac;
	/**
	 * �����з���������ʳԴ��ˮԴ�༭��
	 */
	private AutoCompleteTextView at_mouse_food_drink;
	/**
	 * �����з���������ʳԴ��ˮԴ������ť
	 */
	private ImageButton ibtn_mouse_food_drink;
	/**
	 * �����з�����������������༭��
	 */
	private AutoCompleteTextView at_mouse_nest_cond;
	/**
	 * �����з����������������������ť
	 */
	private ImageButton ibtn_mouse_nest_cond;
	/**
	 * ���������з��ֹ�����/��ʬ�༭��
	 */
	private AutoCompleteTextView at_mouse_live_corpse;
	/**
	 * ���������з��ֹ�����/��ʬ������ť
	 */
	private ImageButton ibtn_mouse_live_corpse;
	/**
	 * ���⽨���ܱ��з��󼣱༭��
	 */
	private AutoCompleteTextView at_room_out_mouse_trac;
	/**
	 * ���⽨���ܱ��з���������ť
	 */
	private ImageButton ibtn_room_out_mouse_trac;
	/**
	 * �����з������ʳԴ��ˮԴ�༭��
	 */
	private AutoCompleteTextView at_room_out_mouse_food_drink;
	/**
	 * �����з������ʳԴ��ˮԴ������ť
	 */
	private ImageButton ibtn_room_out_mouse_food_drink;
	/**
	 * �����з��������������༭��
	 */
	private AutoCompleteTextView at_room_out_mouse_nest_cond;
	/**
	 * �����з�������������������ť
	 */
	private ImageButton ibtn_room_out_mouse_nest_cond;
	/**
	 * ���������з��ֹ�����༭��
	 */
	private AutoCompleteTextView at_room_out_has_mouse;
	/**
	 * ���������з��ֹ�����������ť
	 */
	private ImageButton ibtn_room_out_has_mouse;
	/**
	 * ���������ƶ��Ƿ����Ʊ༭��
	 */
	private AutoCompleteTextView at_envir_clean;
	/**
	 * ���������ƶ��Ƿ�����������
	 */
	private ImageButton ibtn_envir_clean;
	/**
	 * ������ʩ�Ƿ���Ϸ���Ҫ��༭��
	 */
	private AutoCompleteTextView at_equip_match_requ;
	/**
	 * ������ʩ�Ƿ���Ϸ���Ҫ��������ť
	 */
	private ImageButton ibtn_equip_match_requ;
	/**
	 * �ͻ�����������Ʒ�Ƿ��⵽�����ֺ��༭��
	 */
	private AutoCompleteTextView at_things_be_breaked;
	/**
	 * �ͻ�����������Ʒ�Ƿ��⵽�����ֺ�������ť
	 */
	private ImageButton ibtn_things_be_breaked;
	/**
	 * �ͻ������������з��������ַ��ձ༭��
	 */
	private AutoCompleteTextView at_pipe_line_invade_mouse;
	/**
	 * �ͻ������������з��������ַ���������ť
	 */
	private ImageButton ibtn_pipe_line_invade_mouse;
	/**
	 * �ͻ����з����ۺϷ��Ƶ�����༭��
	 */
	private AutoCompleteTextView at_mouse_preve_cure;
	/**
	 * �ͻ����з����ۺϷ��Ƶ�����������ť
	 */
	private ImageButton ibtn_mouse_preve_cure;
	/**
	 * �ͻ����з�����Ԥ����ʩ�༭��
	 */
	private AutoCompleteTextView at_mouse_preve_cure_equip;
	/**
	 * �ͻ����з�����Ԥ����ʩ������ť
	 */
	private ImageButton ibtn_mouse_preve_cure_equip;
	
	
	//-----------------���ܰ�ť------------------
	/**
	 * ���水ť
	 */
	private Button btn_save_mouse_watch;
	/**
	 * ���ذ�ť
	 */
	private Button btn_back_mouse_watch;
	
	/**
	 * �����������
	 */
	private MyWatchKindAdapter mMouseKindAdapter;
	/**
	 * ����
	 */
	private String[] mMouseKindData=null;
	/**
	 * �����б�
	 * 
	 */
	private List<HashMap<String,Object>> listData=null;
	/**
	 * ѡ���б�ͳ����
	 */
	private List<String> mListCount=null;
	
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
	private ArrayAdapter mOtherStateAdapter12;
	private ArrayAdapter mOtherStateAdapter13;
	private ArrayAdapter mOtherStateAdapter14;
	private ArrayAdapter mOtherStateAdapter15;
	
	
	/**
	 * ���ؼ��Ƿ񱻰���
	 */
	private boolean isBackPressed=false;
	//-------------------------------------
	/**
	 * ��������Сͼ��
	 */
	private BadgeView     mBadgeView;
	
	/**
	 * ����������
	 */
	private MyWatchDataItem mMouseItem;
	
	/**
	 * ������
	 */
	private SimpleAdapter mAdapter;
	
	
	//=======================��TextViewѡ�в��������Ҫ�Ŀؼ�id=============
	
	/**
	 * �ֳ����鷢�ֵ���������
	 */
	private RelativeLayout panel_mouse_watch_kind;
	/**
	 * �ֳ����鷢�ֵ������ı�
	 */
	private TextView tv_mouse_watch_kind;
	
	//�����Ŵ���ͨ���Ƿ���Ϸ���Ҫ��1
	private RelativeLayout panel_mouse_watch_room_in_requ;
	private TextView tv_mouse_watch_room_in_requ;
	
	//�����з���2
	private RelativeLayout panel_mouse_trac;
	private TextView tv_mouse_trac;
	
	//�����з���������ʳԴ��ˮԴ 3
	private RelativeLayout panel_mouse_food_drink;
	private TextView tv_mouse_food_drink;
	
	
	//�����з���������������� 4
	private RelativeLayout panel_mouse_nest_cond;
	private TextView tv_mouse_nest_cond;
	
	//���������з��ֹ�����/��ʬ 5
	private RelativeLayout panel_mouse_live_corpse;
	private TextView tv_mouse_live_corpse;
	
	//���⽨���ܱ��з��� 6
	private RelativeLayout panel_room_out_mouse_trac;
	private TextView tv_room_out_mouse_trac;
	
	//�����з������ʳԴ��ˮԴ7
	private RelativeLayout panel_room_out_mouse_food_drink;
	private TextView tv_room_out_mouse_food_drink;
	
	//�����з�������������8
	private RelativeLayout panel_room_out_mouse_nest_cond;
	private TextView tv_room_out_mouse_nest_cond;	
	
	//���������з��ֹ�����9
	private RelativeLayout panel_room_out_has_mouse;
	private TextView tv_room_out_has_mouse;

	//���������ƶ��Ƿ�����10
	private RelativeLayout panel_envir_clean;
	private TextView tv_envir_clean;
	
	//������ʩ�Ƿ���Ϸ���Ҫ��11
	private RelativeLayout panel_equip_match_requ;
	private TextView tv_equip_match_requ;

	//�ͻ�����������Ʒ�Ƿ��⵽�����ֺ�12
	private RelativeLayout panel_things_be_breaked;
	private TextView tv_things_be_breaked;
	
	//�ͻ������������з��������ַ��� 13
	private RelativeLayout panel_pipe_line_invade_mouse;
	private TextView tv_pipe_line_invade_mouse;

	//�ͻ����з����ۺϷ��Ƶ�����14
	private RelativeLayout panel_mouse_preve_cure;
	private TextView tv_mouse_preve_cure;

	//�ͻ����з�����Ԥ����ʩ 15
	private RelativeLayout panel_mouse_preve_cure_equip;
	private TextView tv_mouse_preve_cure_equip;
	
	//=======================��TextViewѡ�в��������Ҫ�Ŀؼ�id=============
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ʹ�ÿͻ��Զ��������
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.d2e_mouse_watch);
		//�����û��Զ��������
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.d2e_titlebar);
		TextView tv = (TextView)findViewById(R.id.title);
		//�����Լ��ı�������
        tv.setText(getResources().getString(R.string.mouse_watch_title));
        mBadgeView = (BadgeView)findViewById(R.id.countTask);
        mBadgeView.setVisibility(View.GONE);
		//��ʼ���ؼ�
        initView();
        //Ϊ�������������
//        addAdapterData();
        //Ϊ�����AutoAutoCompleteTextView����������
        setUpAutoTextAdapter();
		//Ϊ���ܰ�ť���ü�����
        setUpBtnListener();
		
	}
	/**
	 * Ϊ�����AutoCompleteTextView���ü�����
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
	 * ��ʾ����Ի���
	 */
	private void showWarmDialog(){
		isBackPressed=false;
		AlertDialog.Builder builder=new Builder(D2EMouseWatchActivity.this);
		
		builder.setTitle(getResources().getString(R.string.warm_prompt));
		builder.setMessage(getResources().getString(R.string.quit));
		builder.setIcon(R.drawable.msg_dlg_warning);
		//ȷ����ʱ�򱣴�����
		builder.setPositiveButton(getResources().getString(R.string.quit_ok), new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				//saveViewData();
				overView(); 
			}
		});
		//ȡ����ʱ���˳�����
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
	 * �ж��Ƿ���������еļ��
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
	 * Ϊ���ܰ�ť���ü�����
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
		
		
		//�����������Ҫ�ļ�����
		setUpLampListener();
	}
	/**
	 * ���ذ�ť������
	 */
	private class BackListener implements OnClickListener{

		@Override
		public void onClick(View v) {
//			overView();
			isBackPressed=true;
			//���������������ݣ������Ի�����ʾ�Ƿ񱣴�����
			if(isCheckAll()){
				showWarmDialog();
			}
			else {
				overView();
			}
		}
		
	}
	/**
	 * �ͻ����з�����Ԥ����ʩ 15
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
				//����һ���Ի���������д��ע
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
	 * �ͻ����з����ۺϷ��Ƶ�����14�༭�������
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
				//����һ���Ի���������д��ע
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
	 * �ͻ����з����ۺϷ��Ƶ�����14������ť������
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
	 * �ͻ������������з��������ַ��� 13�༭�������
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
				//����һ���Ի���������д��ע
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
	 * �ͻ������������з��������ַ��� 13������ť
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
	 * �ͻ�����������Ʒ�Ƿ��⵽�����ֺ�12�༭�������
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
				//����һ���Ի���������д��ע
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
	 * �ͻ�����������Ʒ�Ƿ��⵽�����ֺ�12������ť������
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
	 * ������ʩ�Ƿ���Ϸ���Ҫ��11�༭�������
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
				//����һ���Ի���������д��ע
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
	 * ������ʩ�Ƿ���Ϸ���Ҫ��11������ť������
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
	 * ���������ƶ��Ƿ�����10�༭�������
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
				//����һ���Ի���������д��ע
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
	 * ���������ƶ��Ƿ�����10������ť������
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
	 * ���������з��ֹ�����9�༭�������
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
				//����һ���Ի���������д��ע
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
	 * ���������з��ֹ�����10������ť������
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
	 * �����з�������������8�༭�������
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
				//����һ���Ի���������д��ע
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
	 * �����з�������������8������ť������
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
	 * �����з������ʳԴ��ˮԴ7�༭�������
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
				//����һ���Ի���������д��ע
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
	 * �����з������ʳԴ��ˮԴ7������ť������
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
	 * ���⽨���ܱ��з��� 6�༭�������
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
				//����һ���Ի���������д��ע
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
	 * ���⽨���ܱ��з��� 6������ť������
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
	 * ���������з��ֹ�����/��ʬ 5�༭�������
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
				//����һ���Ի���������д��ע
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
	 * ���������з��ֹ�����/��ʬ 5������ť������
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
	 * �����з�����������������༭�������
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
				//����һ���Ի���������д��ע
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
	 * �����з����������������������ť������
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
	 * �����з���������ʳԴ��ˮԴ�༭�������
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
				//����һ���Ի���������д��ע
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
	 * �����з���������ʳԴ��ˮԴ�����������
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
	 * �����з��������������
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
	 * �����з��󼣱༭�������
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
				//����һ���Ի���������д��ע
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
	 * �����Ŵ���ͨ���Ƿ���Ϸ���Ҫ�������������
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
	 * �����Ŵ���ͨ���Ƿ���Ϸ���Ҫ��༭�������
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
				//����һ���Ի���������д��ע
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
//	 * ��������������
//	 */
//	private class MouseKindItemClickListener implements OnItemClickListener{
//		@Override
//		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
//				long id) {
////			D2EListAdapterItam item=(D2EListAdapterItam) mMouseKindAdapter.getItem(position);
////			at_mouse_watch_kind.setText(item.getTitle());
//			//����Ҫ�ж�ѡ�����ʲô��Ȼ�����ø�ֵ������������
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
//			//��������������
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
	 * �������������ť������
	 */
	private class IbtnMouseKindListener implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			//���ﵯ��һ���Ի�������Ȼ����һ���б���ѡ������
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
			//��������et_other_kindΪĬ����һ�α������Ϣ
			et_other_kind.setText(previous_kind);
			lv.setAdapter(mMouseKindAdapter);
			lv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View view,
						int position, long arg3) {
					ViewHolder holder=(ViewHolder) view.getTag();
					//listViewһ��ѡ�е�ʱ��ı�CheckBox��״̬
					holder.cb.toggle();
					//ÿ�λ�ȡ�����itemʱ������CheckBox��״̬
					MyWatchKindAdapter.mCheckBoxStateMap.put(position, holder.cb.isChecked());
					//���CheckBox��״̬Ϊѡ�У�������¼�б��������һ��
					if(holder.cb.isChecked()){
						mListCount.add(mMouseKindData[position]);
					}else{
						//������ɾ��һ��
						mListCount.remove(mMouseKindData[position]);
					}
					//�ı�ѡ����Ŀ����ʾ��֪ͨlistView����
					mMouseKindAdapter.notifyDataSetChanged();
					
					//�����ж����Ƿ�ѡ��,ѡ��������CheckBox���޸�ΪΪѡ��
					if(holder.tv.getText().toString().equalsIgnoreCase("��")){
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
							if(listData.get(i).get("item_tv").toString().equalsIgnoreCase("��")){
								MyWatchKindAdapter.mCheckBoxStateMap.put(i, false);
							}
						}
						et_other_kind.setFocusable(true);
						et_other_kind.setFocusableInTouchMode(true);
						et_other_kind.setClickable(true);
						et_other_kind.setEnabled(true);
					}
//					tv_select_count.setText("��ѡ��"+mListCount.size()+"��");
				}
			});
			btn_select_ensure.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					boolean hasdata = false;
					//ȷ����ʱ��ѭ�������б�,��ѡ�еķŵ������б���ȥ
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
					//�ж�������������û����д
					if(D2EConfigures.TEST){
						Log.e("MouseKindItemxxxxxxxxxxxx>>", ""+item.toString());
					}
					
					//����Ҫ�����ݱ��浽�б�
					//�ж��Ƿ�ԭ�������ݣ�����ɾ��ԭ���ģ��������ڵ�
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
					//��at����һ���ı���"�����"
					dialog.dismiss();
					
				}
			});
			btn_cancle_select.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					//ȡ����ʱ���CheckBox��ѡ�е�״̬������Ϊδѡ��
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
	//��նԻ����ռ�������
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
	 * �����������
	 */
	private void saveViewData(){
		//�Ѿ��������������
		JJBaseApplication.isAddMousWatch=true;
		
		//��������ӵ��б���
		JJBaseApplication.mWatchDataList.add(mMouseItem);
		//�뿪����
		D2EMouseWatchActivity.this.finish();
	}
	/**
	 * �������ݼ�����
	 */
	private class SaveDataListener implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			//�жϼ���Ƿ������
			if(isCheckAll()){
				//�����������
				saveViewData();
				//��������
			}
		}
		
	}
	/**
	 * ��ʼ�����
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
		
		
		//ʵ����һ��������������������
		mMouseItem=new MyWatchDataItem();
		//�������Ϊ����
		mMouseItem.setmWatchType("Mouse");
		int array=R.array.mouse_kind;
		
		//��ȡ�������������������
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
		
		//��ʼ���������Ҫ�Ŀؼ�
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
	 * ��������TextView�Ľ���ȥ��
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
	 * �����������Ҫ�ļ�����
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
	 * ��ȡ�������ݼ��������
	 */
	private void getMouseKindAdapterData() {
		mListCount=new ArrayList<String>();
		listData=new ArrayList<HashMap<String,Object>>();
		//����
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
		//���������������ݣ������Ի�����ʾ�Ƿ񱣴�����
		if(isCheckAll()){
			showWarmDialog();
		}else{
			overView();
		}
	}
	
	/**
	 * ����������
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
