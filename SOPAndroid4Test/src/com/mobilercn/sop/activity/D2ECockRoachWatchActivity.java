package com.mobilercn.sop.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
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

import com.mobilercn.base.BadgeView;
import com.mobilercn.base.JJBaseActivity;
import com.mobilercn.base.JJBaseApplication;
import com.mobilercn.config.D2EConfigures;
import com.mobilercn.sop.R;
import com.mobilercn.sop.data.MyWatchDataItem;
import com.mobilercn.sop.data.MyWatchKindAdapter;
import com.mobilercn.sop.data.MyWatchKindAdapter.ViewHolder;

/**
 * ��뿱�������ռ�����
 * 
 * @author ShawnXiao
 * 
 */
public class D2ECockRoachWatchActivity extends JJBaseActivity {
	/**
	 * �ֳ�����ʱ���ֵ����༭��
	 */
	private AutoCompleteTextView at_cockroach_watch_kind;
	/**
	 * �ֳ�����ʱ���ֵ����������ť
	 */
	private ImageButton ibtn_cockroach_watch_kind;
	/**
	 * ����ˮ�ء���̨�������������з�󯼣�༭��
	 */
	private AutoCompleteTextView at_cockroach_watch_room_in_find;
	/**
	 * ����ˮ�ء���̨�������������з�󯼣������ť
	 */
	private ImageButton ibtn_cockroach_watch_room_in_find;
	/**
	 * ����ʳƷ���ִ������Ƿ��ܷ�༭��
	 */
	private AutoCompleteTextView at_cockroach_room_in_seal;
	/**
	 * ����ʳƷ���ִ������Ƿ��ܷ�������ť
	 */
	private ImageButton ibtn_cockroach_room_in_seal;
	/**
	 * ���ڸ��ֹ��ߡ����񡢼Ҿ��з�󯼣�༭��
	 */
	private AutoCompleteTextView at_cockroach_room_in_furni_find;
	/**
	 * ���ڸ��ֹ��ߡ����񡢼Ҿ��з�󯼣������ť
	 */
	private ImageButton ibtn_cockroach_room_in_furni_find;
	/**
	 * ���������з��ֹ����༭��
	 */
	private AutoCompleteTextView at_cockroach_room_in_curr_find;
	/**
	 * ���������з��ֹ����������ť
	 */
	private ImageButton ibtn_cockroach_room_in_curr_find;
	/**
	 * �����Ŵ���ͨ����ǽ�ڷ�϶�Ƿ���Ϸ����Ҫ��༭��
	 */
	private AutoCompleteTextView at_cockroach_room_in_requ;
	/**
	 * �����Ŵ���ͨ����ǽ�ڷ�϶�Ƿ���Ϸ����Ҫ��������ť
	 */
	private ImageButton ibtn_cockroach_room_in_requ;
	/**
	 * ���������ƶ��Ƿ����Ʊ༭��
	 */
	private AutoCompleteTextView at_cockroach_watch_syst_perf;
	/**
	 * ���������ƶ��Ƿ�����������ť
	 */
	private ImageButton ibtn_cockroach_watch_syst_perf;
	/**
	 * ������ʩ�Ƿ���Ϸ�����Ҫ��༭��
	 */
	private AutoCompleteTextView at_cockroach_watch_hygiene_requ;
	/**
	 * ������ʩ�Ƿ���Ϸ�����Ҫ��������ť
	 */
	private ImageButton ibtn_cockroach_watch_hygiene_requ;
	/**
	 * �ͻ������������з�������ַ��ձ༭��
	 */
	private AutoCompleteTextView at_cockroach_watch_pipeline_risk;
	/**
	 * �ͻ������������з�������ַ���������ť
	 */
	private ImageButton ibtn_cockroach_watch_pipeline_risk;
	/**
	 * �ͻ�����������Ʒ�Ƿ��⵽����ֺ��༭��
	 */
	private AutoCompleteTextView at_cockroach_watch_things_be_breaked;
	/**
	 * �ͻ�����������Ʒ�Ƿ��⵽����ֺ�������ť
	 */
	private ImageButton ibtn_cockroach_watch_things_be_breaked;
	/**
	 * �ͻ����з�����ۺϷ��Ƶ�����༭��
	 */
	private AutoCompleteTextView at_cockroach_watch_prevention_think;
	/**
	 * �ͻ����з�����ۺϷ��Ƶ�����������ť
	 */
	private ImageButton ibtn_cockroach_watch_prevention_think;

	// =============���ܰ�ť================
	/**
	 * ���水ť
	 */
	private Button btn_save_cockroach_watch;
	/**
	 * ���ذ�ť
	 */
	private Button btn_back_cockroach_watch;
	// ==================================

	/**
	 * ��������Сͼ��
	 */
	private BadgeView mBadgeView;

	/**
	 * ���������
	 */
	private MyWatchDataItem mCockRoachItem;
	/**
	 * ������������
	 */
	private MyWatchKindAdapter mCockRoachKindAdapter = null;
	/**
	 * ����
	 */
	private String[] mCockRoachKindData = null;
	/**
	 * �����б�
	 */
	private List<HashMap<String, Object>> listData = null;
	/**
	 * ������ͳ���б�
	 */
	private List<String> mCountList = null;
	/**
	 * ���������ϴα������Ϣ
	 */
	private String previous_kind = "";

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

	/**
	 * �Ƿ��·��ؼ�
	 */
	private boolean isBackPressed = false;

	// =====================�������Ҫ�ؼ�=========================
	// 0
	private RelativeLayout panel_cockroach_watch_kind;
	private TextView tv_cockroach_watch_kind;

	// 1
	private RelativeLayout panel_cockroach_watch_room_in_find;
	private TextView tv_cockroach_watch_room_in_find;

	// 2
	private RelativeLayout panel_cockroach_room_in_seal;
	private TextView tv_cockroach_room_in_seal;

	// 3
	private RelativeLayout panel_cockroach_room_in_furni_find;
	private TextView tv_cockroach_room_in_furni_find;

	// 4
	private RelativeLayout panel_cockroach_room_in_curr_find;
	private TextView tv_cockroach_room_in_curr_find;

	// 5
	private RelativeLayout panel_cockroach_room_in_requ;
	private TextView tv_cockroach_room_in_requ;

	// 6
	private RelativeLayout panel_cockroach_watch_syst_perf;
	private TextView tv_cockroach_watch_syst_perf;

	// 7
	private RelativeLayout panel_cockroach_watch_hygiene_requ;
	private TextView tv_cockroach_watch_hygiene_requ;

	// 8
	private RelativeLayout panel_cockroach_watch_pipeline_risk;
	private TextView tv_cockroach_watch_pipeline_risk;

	// 9
	private RelativeLayout panel_cockroach_watch_things_be_breaked;
	private TextView tv_cockroach_watch_things_be_breaked;

	// 10
	private RelativeLayout panel_cockroach_watch_prevention_think;
	private TextView tv_cockroach_watch_prevention_think;

	// =====================�������Ҫ�ؼ�=========================
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ʹ�ÿͻ��Զ��������
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.d2e_cockroach_watch);
		// �����û��Զ��������
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.d2e_titlebar);
		TextView tv = (TextView) findViewById(R.id.title);
		// �����Լ��ı�������
		tv.setText(getResources().getString(R.string.cockroach_watch_title));
		mBadgeView = (BadgeView) findViewById(R.id.countTask);
		mBadgeView.setVisibility(View.GONE);
		// ��ʼ���ؼ�
		initView();
		// Ϊ�����AutoAutoCompleteTextView����������
		setUpAutoTextAdapter();
		// Ϊ���ܰ�ť���ü�����
		setUpBtnListener();

	}

	/**
	 * Ϊ���ܰ�ť���ü�����
	 */
	private void setUpBtnListener() {
		ibtn_cockroach_watch_kind
				.setOnClickListener(new IbtnCockRoachWatchKindListener());

		at_cockroach_watch_room_in_find
				.setOnItemClickListener(new AtCockRoachWatchRoomInFindListener());
		ibtn_cockroach_watch_room_in_find
				.setOnClickListener(new IbtnCockRoachWatchRoomInFindListener());

		at_cockroach_room_in_seal
				.setOnItemClickListener(new AtCockRoachRoomInSealListener());
		ibtn_cockroach_room_in_seal
				.setOnClickListener(new IbtnCockRoachRoomInSealListener());

		at_cockroach_room_in_furni_find
				.setOnItemClickListener(new AtCockRoachRoomInFurniFindListener());
		ibtn_cockroach_room_in_furni_find
				.setOnClickListener(new IbtnCockRoachRoomInFurniFindListener());

		at_cockroach_room_in_curr_find
				.setOnItemClickListener(new AtCockRoachRoomInCurrFindListener());
		ibtn_cockroach_room_in_curr_find
				.setOnClickListener(new IbtnCockRoachRoomInCurrFindListener());

		at_cockroach_room_in_requ
				.setOnItemClickListener(new AtCockRoachRoomInRequListener());
		ibtn_cockroach_room_in_requ
				.setOnClickListener(new IbtnCockRoachRoomInRequListener());

		at_cockroach_watch_syst_perf
				.setOnItemClickListener(new AtCockRoachWatchSystPerfListener());
		ibtn_cockroach_watch_syst_perf
				.setOnClickListener(new IbtnCockRoachWatchSystPerfListener());

		at_cockroach_watch_hygiene_requ
				.setOnItemClickListener(new AtCockRoackWatchHygieneRequListener());
		ibtn_cockroach_watch_hygiene_requ
				.setOnClickListener(new IbtnCockRoachWatchHygieneRequListener());

		at_cockroach_watch_pipeline_risk
				.setOnItemClickListener(new AtCockRoachWatchPipelineRiskListener());
		ibtn_cockroach_watch_pipeline_risk
				.setOnClickListener(new IbtnCockRoachWatchPipelineRiskListener());

		at_cockroach_watch_things_be_breaked
				.setOnItemClickListener(new AtCockRoachWatchThingsBeBreakedListener());
		ibtn_cockroach_watch_things_be_breaked
				.setOnClickListener(new IbtnCockRoachWatchThingsBeBreakedListener());

		at_cockroach_watch_prevention_think
				.setOnItemClickListener(new AtCockRoachWatchPreventionThinkListener());
		ibtn_cockroach_watch_prevention_think
				.setOnClickListener(new IbtnCockRoachWatchPreventionThinkListener());

		btn_save_cockroach_watch.setOnClickListener(new SaveListener());
		btn_back_cockroach_watch.setOnClickListener(new BackListener());

		// �����������صļ�����
		setUpLampListerner();
	}

	/**
	 * �ͻ����з�����ۺϷ��Ƶ����� 10�༭�������
	 */
	private class AtCockRoachWatchPreventionThinkListener implements
			OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			final MyWatchDataItem item = new MyWatchDataItem();
			item.setmMouseIndex(10);

			if (position == 0) {
				item.setmMouseKind1(1);
				item.setmMouseKind2(0);
				item.setmMouseKind3(0);
				item.setmMouseKind4("");

			} else if (position == 1) {
				item.setmMouseKind1(0);
				item.setmMouseKind2(1);
				item.setmMouseKind3(0);
				item.setmMouseKind4("");
			} else if (position == 2) {
				item.setmMouseKind1(0);
				item.setmMouseKind2(0);
				item.setmMouseKind3(1);
				item.setmMouseKind4("");
			} else if (position == 3) {
				// ����һ���Ի���������д��ע
				LayoutInflater inflater = getLayoutInflater();
				View dialog_view = inflater.inflate(R.layout.dialog2, null);
				final EditText et_dialog2 = (EditText) dialog_view
						.findViewById(R.id.et_dialog2);
				final Button btn_dialog2_yes = (Button) dialog_view
						.findViewById(R.id.btn_dialog2_yes);
				final Button btn_dialog2_no = (Button) dialog_view
						.findViewById(R.id.btn_dialog2_no);
				final Dialog otherFaciDialog = new Dialog(
						D2ECockRoachWatchActivity.this,
						R.style.my_other_point_dialog);
				otherFaciDialog.setContentView(dialog_view);
				otherFaciDialog.getWindow().setLayout(300, 150);
				otherFaciDialog.show();
				btn_dialog2_yes.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						at_cockroach_watch_prevention_think.setText(et_dialog2
								.getText().toString());
						item.setmMouseKind1(0);
						item.setmMouseKind2(0);
						item.setmMouseKind3(0);
						item.setmMouseKind4(et_dialog2.getText().toString());
						otherFaciDialog.dismiss();
						if (D2EConfigures.TEST) {
							Log.e("AtCockRoachWatchPreventionThinkxxxxxxxxxxxxx>>",
									"" + item.toString());
						}

					}
				});
				btn_dialog2_no.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						et_dialog2.setText("");
						at_cockroach_watch_prevention_think.setText("");
						otherFaciDialog.dismiss();
					}
				});

			}

			if (!mCockRoachItem.isContainWatchDataItem(item)) {
				mCockRoachItem.mWatchDataList.add(item);
			} else {
				mCockRoachItem.deleContainWatchDataItem(item);
				mCockRoachItem.mWatchDataList.add(item);
			}

			if (D2EConfigures.TEST) {
				Log.e("AtCockRoachWatchPreventionThinkxxxxxxxxxxxx>>", ""
						+ item.toString());
			}
		}

	}

	/**
	 * �ͻ����з�����ۺϷ��Ƶ����� 10������ť������
	 */
	private class IbtnCockRoachWatchPreventionThinkListener implements
			OnClickListener {

		@Override
		public void onClick(View v) {
			if (D2EConfigures.TEST) {
				Log.e("IbtnCockRoachWatchPreventionThinkListener=============>",
						"onClick(View v)");
			}
			String str = at_cockroach_watch_prevention_think.getText()
					.toString();
			if (str != null && str.length() > 0) {
				at_cockroach_watch_prevention_think.setText("");
			}
			at_cockroach_watch_prevention_think.showDropDown();
			at_cockroach_watch_prevention_think.setThreshold(1);
		}

	}

	/**
	 * �ͻ�����������Ʒ�Ƿ��⵽����ֺ� 9�༭�������
	 */
	private class AtCockRoachWatchThingsBeBreakedListener implements
			OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			final MyWatchDataItem item = new MyWatchDataItem();
			item.setmMouseIndex(9);

			if (position == 0) {
				item.setmMouseKind1(1);
				item.setmMouseKind2(0);
				item.setmMouseKind3(0);
				item.setmMouseKind4("");

			} else if (position == 1) {
				item.setmMouseKind1(0);
				item.setmMouseKind2(1);
				item.setmMouseKind3(0);
				item.setmMouseKind4("");
			} else if (position == 2) {
				item.setmMouseKind1(0);
				item.setmMouseKind2(0);
				item.setmMouseKind3(1);
				item.setmMouseKind4("");
			} else if (position == 3) {
				// ����һ���Ի���������д��ע
				LayoutInflater inflater = getLayoutInflater();
				View dialog_view = inflater.inflate(R.layout.dialog2, null);
				final EditText et_dialog2 = (EditText) dialog_view
						.findViewById(R.id.et_dialog2);
				final Button btn_dialog2_yes = (Button) dialog_view
						.findViewById(R.id.btn_dialog2_yes);
				final Button btn_dialog2_no = (Button) dialog_view
						.findViewById(R.id.btn_dialog2_no);
				final Dialog otherFaciDialog = new Dialog(
						D2ECockRoachWatchActivity.this,
						R.style.my_other_point_dialog);
				otherFaciDialog.setContentView(dialog_view);
				otherFaciDialog.getWindow().setLayout(300, 150);
				otherFaciDialog.show();
				btn_dialog2_yes.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						at_cockroach_watch_things_be_breaked.setText(et_dialog2
								.getText().toString());
						item.setmMouseKind1(0);
						item.setmMouseKind2(0);
						item.setmMouseKind3(0);
						item.setmMouseKind4(et_dialog2.getText().toString());
						otherFaciDialog.dismiss();
						if (D2EConfigures.TEST) {
							Log.e("AtCockRoachWatchThingsBeBreakedxxxxxxxxxxxxx>>",
									"" + item.toString());
						}

					}
				});
				btn_dialog2_no.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						et_dialog2.setText("");
						at_cockroach_watch_things_be_breaked.setText("");
						otherFaciDialog.dismiss();
					}
				});

			}

			if (!mCockRoachItem.isContainWatchDataItem(item)) {
				mCockRoachItem.mWatchDataList.add(item);
			} else {
				mCockRoachItem.deleContainWatchDataItem(item);
				mCockRoachItem.mWatchDataList.add(item);
			}

			if (D2EConfigures.TEST) {
				Log.e("AtCockRoachWatchThingsBeBreakedxxxxxxxxxxxx>>", ""
						+ item.toString());
			}
		}

	}

	/**
	 * �ͻ�����������Ʒ�Ƿ��⵽����ֺ� 9������ť������
	 */
	private class IbtnCockRoachWatchThingsBeBreakedListener implements
			OnClickListener {

		@Override
		public void onClick(View v) {
			if (D2EConfigures.TEST) {
				Log.e("IbtnCockRoachWatchThingsBeBreakedListener=============>",
						"onClick(View v)");
			}
			String str = at_cockroach_watch_things_be_breaked.getText()
					.toString();
			if (str != null && str.length() > 0) {
				at_cockroach_watch_things_be_breaked.setText("");
			}
			at_cockroach_watch_things_be_breaked.showDropDown();
			at_cockroach_watch_things_be_breaked.setThreshold(1);
		}

	}

	/**
	 * �ͻ������������з�������ַ��� 8�༭�������
	 */
	private class AtCockRoachWatchPipelineRiskListener implements
			OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			final MyWatchDataItem item = new MyWatchDataItem();
			item.setmMouseIndex(8);

			if (position == 0) {
				item.setmMouseKind1(1);
				item.setmMouseKind2(0);
				item.setmMouseKind3(0);
				item.setmMouseKind4("");

			} else if (position == 1) {
				item.setmMouseKind1(0);
				item.setmMouseKind2(1);
				item.setmMouseKind3(0);
				item.setmMouseKind4("");
			} else if (position == 2) {
				item.setmMouseKind1(0);
				item.setmMouseKind2(0);
				item.setmMouseKind3(1);
				item.setmMouseKind4("");
			} else if (position == 3) {
				// ����һ���Ի���������д��ע
				LayoutInflater inflater = getLayoutInflater();
				View dialog_view = inflater.inflate(R.layout.dialog2, null);
				final EditText et_dialog2 = (EditText) dialog_view
						.findViewById(R.id.et_dialog2);
				final Button btn_dialog2_yes = (Button) dialog_view
						.findViewById(R.id.btn_dialog2_yes);
				final Button btn_dialog2_no = (Button) dialog_view
						.findViewById(R.id.btn_dialog2_no);
				final Dialog otherFaciDialog = new Dialog(
						D2ECockRoachWatchActivity.this,
						R.style.my_other_point_dialog);
				otherFaciDialog.setContentView(dialog_view);
				otherFaciDialog.getWindow().setLayout(300, 150);
				otherFaciDialog.show();
				btn_dialog2_yes.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						at_cockroach_watch_pipeline_risk.setText(et_dialog2
								.getText().toString());
						item.setmMouseKind1(0);
						item.setmMouseKind2(0);
						item.setmMouseKind3(0);
						item.setmMouseKind4(et_dialog2.getText().toString());
						otherFaciDialog.dismiss();
						if (D2EConfigures.TEST) {
							Log.e("AtCockRoachWatchPipelineRiskxxxxxxxxxxxxx>>",
									"" + item.toString());
						}

					}
				});
				btn_dialog2_no.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						et_dialog2.setText("");
						at_cockroach_watch_pipeline_risk.setText("");
						otherFaciDialog.dismiss();
					}
				});

			}

			if (!mCockRoachItem.isContainWatchDataItem(item)) {
				mCockRoachItem.mWatchDataList.add(item);
			} else {
				mCockRoachItem.deleContainWatchDataItem(item);
				mCockRoachItem.mWatchDataList.add(item);
			}

			if (D2EConfigures.TEST) {
				Log.e("AtCockRoachWatchPipelineRiskxxxxxxxxxxxx>>",
						"" + item.toString());
			}
		}

	}

	// �������
	private void clearKindData() {
		// ѭ�������������飬��ѡ�еĶ���Ϊδѡ��
		for (int i = 0; i < listData.size(); i++) {
			if (MyWatchKindAdapter.mCheckBoxStateMap.get(i) == true) {
				MyWatchKindAdapter.mCheckBoxStateMap.put(i, false);
			}
		}
		// ��ռ�¼�������б�
		mCountList.clear();
		// ���ϴα��������������Ϣ���
		previous_kind = "";
		// ��at��������Ϊ""��ʾδ��ɼ��
		at_cockroach_watch_kind.setText("");

	}

	/**
	 * �ͻ������������з�������ַ��� 8������ť������
	 */
	private class IbtnCockRoachWatchPipelineRiskListener implements
			OnClickListener {

		@Override
		public void onClick(View v) {
			if (D2EConfigures.TEST) {
				Log.e("IbtnCockRoachWatchPipelineRiskListener=============>",
						"onClick(View v)");
			}
			String str = at_cockroach_watch_pipeline_risk.getText().toString();
			if (str != null && str.length() > 0) {
				at_cockroach_watch_pipeline_risk.setText("");
			}
			at_cockroach_watch_pipeline_risk.showDropDown();
			at_cockroach_watch_pipeline_risk.setThreshold(1);
		}

	}

	/**
	 * ������ʩ�Ƿ���Ϸ�����Ҫ�� 7�༭�������
	 */
	private class AtCockRoackWatchHygieneRequListener implements
			OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			final MyWatchDataItem item = new MyWatchDataItem();
			item.setmMouseIndex(7);

			if (position == 0) {
				item.setmMouseKind1(1);
				item.setmMouseKind2(0);
				item.setmMouseKind3(0);
				item.setmMouseKind4("");

			} else if (position == 1) {
				item.setmMouseKind1(0);
				item.setmMouseKind2(1);
				item.setmMouseKind3(0);
				item.setmMouseKind4("");
			} else if (position == 2) {
				item.setmMouseKind1(0);
				item.setmMouseKind2(0);
				item.setmMouseKind3(1);
				item.setmMouseKind4("");
			} else if (position == 3) {
				// ����һ���Ի���������д��ע
				LayoutInflater inflater = getLayoutInflater();
				View dialog_view = inflater.inflate(R.layout.dialog2, null);
				final EditText et_dialog2 = (EditText) dialog_view
						.findViewById(R.id.et_dialog2);
				final Button btn_dialog2_yes = (Button) dialog_view
						.findViewById(R.id.btn_dialog2_yes);
				final Button btn_dialog2_no = (Button) dialog_view
						.findViewById(R.id.btn_dialog2_no);
				final Dialog otherFaciDialog = new Dialog(
						D2ECockRoachWatchActivity.this,
						R.style.my_other_point_dialog);
				otherFaciDialog.setContentView(dialog_view);
				otherFaciDialog.getWindow().setLayout(300, 150);
				otherFaciDialog.show();
				btn_dialog2_yes.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						at_cockroach_watch_hygiene_requ.setText(et_dialog2
								.getText().toString());
						item.setmMouseKind1(0);
						item.setmMouseKind2(0);
						item.setmMouseKind3(0);
						item.setmMouseKind4(et_dialog2.getText().toString());
						otherFaciDialog.dismiss();
						if (D2EConfigures.TEST) {
							Log.e("AtCockRoackWatchHygieneRequxxxxxxxxxxxxx>>",
									"" + item.toString());
						}

					}
				});
				btn_dialog2_no.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						et_dialog2.setText("");
						at_cockroach_watch_hygiene_requ.setText("");
						otherFaciDialog.dismiss();
					}
				});

			}

			if (!mCockRoachItem.isContainWatchDataItem(item)) {
				mCockRoachItem.mWatchDataList.add(item);
			} else {
				mCockRoachItem.deleContainWatchDataItem(item);
				mCockRoachItem.mWatchDataList.add(item);
			}

			if (D2EConfigures.TEST) {
				Log.e("AtCockRoackWatchHygieneRequxxxxxxxxxxxx>>",
						"" + item.toString());
			}
		}

	}

	/**
	 * ������ʩ�Ƿ���Ϸ�����Ҫ�� 7������ť������
	 */
	private class IbtnCockRoachWatchHygieneRequListener implements
			OnClickListener {

		@Override
		public void onClick(View v) {
			if (D2EConfigures.TEST) {
				Log.e("IbtnCockRoachWatchHygieneRequListener=============>",
						"onClick(View v)");
			}
			String str = at_cockroach_watch_hygiene_requ.getText().toString();
			if (str != null && str.length() > 0) {
				at_cockroach_watch_hygiene_requ.setText("");
			}
			at_cockroach_watch_hygiene_requ.showDropDown();
			at_cockroach_watch_hygiene_requ.setThreshold(1);
		}

	}

	/**
	 * ���������ƶ��Ƿ����� 6�༭�������
	 */
	private class AtCockRoachWatchSystPerfListener implements
			OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			final MyWatchDataItem item = new MyWatchDataItem();
			item.setmMouseIndex(6);

			if (position == 0) {
				item.setmMouseKind1(1);
				item.setmMouseKind2(0);
				item.setmMouseKind3(0);
				item.setmMouseKind4("");

			} else if (position == 1) {
				item.setmMouseKind1(0);
				item.setmMouseKind2(1);
				item.setmMouseKind3(0);
				item.setmMouseKind4("");
			} else if (position == 2) {
				item.setmMouseKind1(0);
				item.setmMouseKind2(0);
				item.setmMouseKind3(1);
				item.setmMouseKind4("");
			} else if (position == 3) {
				// ����һ���Ի���������д��ע
				LayoutInflater inflater = getLayoutInflater();
				View dialog_view = inflater.inflate(R.layout.dialog2, null);
				final EditText et_dialog2 = (EditText) dialog_view
						.findViewById(R.id.et_dialog2);
				final Button btn_dialog2_yes = (Button) dialog_view
						.findViewById(R.id.btn_dialog2_yes);
				final Button btn_dialog2_no = (Button) dialog_view
						.findViewById(R.id.btn_dialog2_no);
				final Dialog otherFaciDialog = new Dialog(
						D2ECockRoachWatchActivity.this,
						R.style.my_other_point_dialog);
				otherFaciDialog.setContentView(dialog_view);
				otherFaciDialog.getWindow().setLayout(300, 150);
				otherFaciDialog.show();
				btn_dialog2_yes.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						at_cockroach_watch_syst_perf.setText(et_dialog2
								.getText().toString());
						item.setmMouseKind1(0);
						item.setmMouseKind2(0);
						item.setmMouseKind3(0);
						item.setmMouseKind4(et_dialog2.getText().toString());
						otherFaciDialog.dismiss();
						if (D2EConfigures.TEST) {
							Log.e("AtCockRoachWatchSystPerfxxxxxxxxxxxxx>>", ""
									+ item.toString());
						}

					}
				});
				btn_dialog2_no.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						et_dialog2.setText("");
						at_cockroach_watch_syst_perf.setText("");
						otherFaciDialog.dismiss();
					}
				});

			}

			if (!mCockRoachItem.isContainWatchDataItem(item)) {
				mCockRoachItem.mWatchDataList.add(item);
			} else {
				mCockRoachItem.deleContainWatchDataItem(item);
				mCockRoachItem.mWatchDataList.add(item);
			}

			if (D2EConfigures.TEST) {
				Log.e("AtCockRoachWatchSystPerfxxxxxxxxxxxx>>",
						"" + item.toString());
			}
		}

	}

	/**
	 * ���������ƶ��Ƿ����� 6������ť������
	 */
	private class IbtnCockRoachWatchSystPerfListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			if (D2EConfigures.TEST) {
				Log.e("IbtnCockRoachWatchSystPerfListener=============>",
						"onClick(View v)");
			}
			String str = at_cockroach_watch_syst_perf.getText().toString();
			if (str != null && str.length() > 0) {
				at_cockroach_watch_syst_perf.setText("");
			}
			at_cockroach_watch_syst_perf.showDropDown();
			at_cockroach_watch_syst_perf.setThreshold(1);
		}

	}

	/**
	 * �����Ŵ���ͨ����ǽ�ڷ�϶�Ƿ���Ϸ����Ҫ�� 5�༭�������
	 */
	private class AtCockRoachRoomInRequListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			final MyWatchDataItem item = new MyWatchDataItem();
			item.setmMouseIndex(5);

			if (position == 0) {
				item.setmMouseKind1(1);
				item.setmMouseKind2(0);
				item.setmMouseKind3(0);
				item.setmMouseKind4("");

			} else if (position == 1) {
				item.setmMouseKind1(0);
				item.setmMouseKind2(1);
				item.setmMouseKind3(0);
				item.setmMouseKind4("");
			} else if (position == 2) {
				item.setmMouseKind1(0);
				item.setmMouseKind2(0);
				item.setmMouseKind3(1);
				item.setmMouseKind4("");
			} else if (position == 3) {
				// ����һ���Ի���������д��ע
				LayoutInflater inflater = getLayoutInflater();
				View dialog_view = inflater.inflate(R.layout.dialog2, null);
				final EditText et_dialog2 = (EditText) dialog_view
						.findViewById(R.id.et_dialog2);
				final Button btn_dialog2_yes = (Button) dialog_view
						.findViewById(R.id.btn_dialog2_yes);
				final Button btn_dialog2_no = (Button) dialog_view
						.findViewById(R.id.btn_dialog2_no);
				final Dialog otherFaciDialog = new Dialog(
						D2ECockRoachWatchActivity.this,
						R.style.my_other_point_dialog);
				otherFaciDialog.setContentView(dialog_view);
				otherFaciDialog.getWindow().setLayout(300, 150);
				otherFaciDialog.show();
				btn_dialog2_yes.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						at_cockroach_room_in_requ.setText(et_dialog2.getText()
								.toString());
						item.setmMouseKind1(0);
						item.setmMouseKind2(0);
						item.setmMouseKind3(0);
						item.setmMouseKind4(et_dialog2.getText().toString());
						otherFaciDialog.dismiss();
						if (D2EConfigures.TEST) {
							Log.e("AtCockRoachRoomInRequxxxxxxxxxxxxx>>", ""
									+ item.toString());
						}

					}
				});
				btn_dialog2_no.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						et_dialog2.setText("");
						at_cockroach_room_in_requ.setText("");
						otherFaciDialog.dismiss();
					}
				});

			}

			if (!mCockRoachItem.isContainWatchDataItem(item)) {
				mCockRoachItem.mWatchDataList.add(item);
			} else {
				mCockRoachItem.deleContainWatchDataItem(item);
				mCockRoachItem.mWatchDataList.add(item);
			}

			if (D2EConfigures.TEST) {
				Log.e("AtCockRoachRoomInRequxxxxxxxxxxxx>>",
						"" + item.toString());
			}
		}

	}

	/**
	 * �����Ŵ���ͨ����ǽ�ڷ�϶�Ƿ���Ϸ����Ҫ�� 5������ť������
	 */
	private class IbtnCockRoachRoomInRequListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			if (D2EConfigures.TEST) {
				Log.e("IbtnCockRoachRoomInRequListener=============>",
						"onClick(View v)");
			}
			String str = at_cockroach_room_in_requ.getText().toString();
			if (str != null && str.length() > 0) {
				at_cockroach_room_in_requ.setText("");
			}
			at_cockroach_room_in_requ.setDropDownWidth(200);
			at_cockroach_room_in_requ.showDropDown();
			at_cockroach_room_in_requ.setThreshold(5);
		}

	}

	/**
	 * ���������з��ֹ���� 4�༭�������
	 */
	private class AtCockRoachRoomInCurrFindListener implements
			OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			final MyWatchDataItem item = new MyWatchDataItem();
			item.setmMouseIndex(4);

			if (position == 0) {
				item.setmMouseKind1(1);
				item.setmMouseKind2(0);
				item.setmMouseKind3(0);
				item.setmMouseKind4("");

			} else if (position == 1) {
				item.setmMouseKind1(0);
				item.setmMouseKind2(1);
				item.setmMouseKind3(0);
				item.setmMouseKind4("");
			} else if (position == 2) {
				item.setmMouseKind1(0);
				item.setmMouseKind2(0);
				item.setmMouseKind3(1);
				item.setmMouseKind4("");
			} else if (position == 3) {
				// ����һ���Ի���������д��ע
				LayoutInflater inflater = getLayoutInflater();
				View dialog_view = inflater.inflate(R.layout.dialog2, null);
				final EditText et_dialog2 = (EditText) dialog_view
						.findViewById(R.id.et_dialog2);
				final Button btn_dialog2_yes = (Button) dialog_view
						.findViewById(R.id.btn_dialog2_yes);
				final Button btn_dialog2_no = (Button) dialog_view
						.findViewById(R.id.btn_dialog2_no);
				final Dialog otherFaciDialog = new Dialog(
						D2ECockRoachWatchActivity.this,
						R.style.my_other_point_dialog);
				otherFaciDialog.setContentView(dialog_view);
				otherFaciDialog.getWindow().setLayout(300, 150);
				otherFaciDialog.show();
				btn_dialog2_yes.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						at_cockroach_room_in_curr_find.setText(et_dialog2
								.getText().toString());
						item.setmMouseKind1(0);
						item.setmMouseKind2(0);
						item.setmMouseKind3(0);
						item.setmMouseKind4(et_dialog2.getText().toString());
						otherFaciDialog.dismiss();
						if (D2EConfigures.TEST) {
							Log.e("AtCockRoachRoomInCurrFindxxxxxxxxxxxxx>>",
									"" + item.toString());
						}

					}
				});
				btn_dialog2_no.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						et_dialog2.setText("");
						at_cockroach_room_in_curr_find.setText("");
						otherFaciDialog.dismiss();
					}
				});

			}

			if (!mCockRoachItem.isContainWatchDataItem(item)) {
				mCockRoachItem.mWatchDataList.add(item);
			} else {
				mCockRoachItem.deleContainWatchDataItem(item);
				mCockRoachItem.mWatchDataList.add(item);
			}

			if (D2EConfigures.TEST) {
				Log.e("AtCockRoachRoomInCurrFindxxxxxxxxxxxx>>",
						"" + item.toString());
			}
		}

	}

	/**
	 * ���������з��ֹ���� 4������ť������
	 */
	private class IbtnCockRoachRoomInCurrFindListener implements
			OnClickListener {

		@Override
		public void onClick(View v) {
			if (D2EConfigures.TEST) {
				Log.e("IbtnCockRoachRoomInCurrFindListener=============>",
						"onClick(View v)");
			}
			String str = at_cockroach_room_in_curr_find.getText().toString();
			if (str != null && str.length() > 0) {
				at_cockroach_room_in_curr_find.setText("");
			}
			at_cockroach_room_in_curr_find.showDropDown();
			at_cockroach_room_in_curr_find.setThreshold(1);
		}

	}

	/**
	 * ���ڸ��ֹ��ߡ����񡢼Ҿ��з�󯼣3�༭�������
	 */
	private class AtCockRoachRoomInFurniFindListener implements
			OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			final MyWatchDataItem item = new MyWatchDataItem();
			item.setmMouseIndex(3);

			if (position == 0) {
				item.setmMouseKind1(1);
				item.setmMouseKind2(0);
				item.setmMouseKind3(0);
				item.setmMouseKind4("");

			} else if (position == 1) {
				item.setmMouseKind1(0);
				item.setmMouseKind2(1);
				item.setmMouseKind3(0);
				item.setmMouseKind4("");
			} else if (position == 2) {
				item.setmMouseKind1(0);
				item.setmMouseKind2(0);
				item.setmMouseKind3(1);
				item.setmMouseKind4("");
			} else if (position == 3) {
				// ����һ���Ի���������д��ע
				LayoutInflater inflater = getLayoutInflater();
				View dialog_view = inflater.inflate(R.layout.dialog2, null);
				final EditText et_dialog2 = (EditText) dialog_view
						.findViewById(R.id.et_dialog2);
				final Button btn_dialog2_yes = (Button) dialog_view
						.findViewById(R.id.btn_dialog2_yes);
				final Button btn_dialog2_no = (Button) dialog_view
						.findViewById(R.id.btn_dialog2_no);
				final Dialog otherFaciDialog = new Dialog(
						D2ECockRoachWatchActivity.this,
						R.style.my_other_point_dialog);
				otherFaciDialog.setContentView(dialog_view);
				otherFaciDialog.getWindow().setLayout(300, 150);
				otherFaciDialog.show();
				btn_dialog2_yes.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						at_cockroach_room_in_furni_find.setText(et_dialog2
								.getText().toString());
						item.setmMouseKind1(0);
						item.setmMouseKind2(0);
						item.setmMouseKind3(0);
						item.setmMouseKind4(et_dialog2.getText().toString());
						otherFaciDialog.dismiss();
						if (D2EConfigures.TEST) {
							Log.e("AtCockRoachRoomInFurniFindxxxxxxxxxxxxx>>",
									"" + item.toString());
						}

					}
				});
				btn_dialog2_no.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						et_dialog2.setText("");
						at_cockroach_room_in_furni_find.setText("");
						otherFaciDialog.dismiss();
					}
				});

			}

			if (!mCockRoachItem.isContainWatchDataItem(item)) {
				mCockRoachItem.mWatchDataList.add(item);
			} else {
				mCockRoachItem.deleContainWatchDataItem(item);
				mCockRoachItem.mWatchDataList.add(item);
			}

			if (D2EConfigures.TEST) {
				Log.e("AtCockRoachRoomInFurniFindxxxxxxxxxxxx>>",
						"" + item.toString());
			}
		}

	}

	/**
	 * ���ڸ��ֹ��ߡ����񡢼Ҿ��з�󯼣3������ť������
	 */
	private class IbtnCockRoachRoomInFurniFindListener implements
			OnClickListener {

		@Override
		public void onClick(View v) {
			if (D2EConfigures.TEST) {
				Log.e("IbtnCockRoachRoomInFurniFindListener=============>",
						"onClick(View v)");
			}
			String str = at_cockroach_room_in_furni_find.getText().toString();
			if (str != null && str.length() > 0) {
				at_cockroach_room_in_furni_find.setText("");
			}
			at_cockroach_room_in_furni_find.showDropDown();
			at_cockroach_room_in_furni_find.setThreshold(1);
		}

	}

	/**
	 * ����ʳƷ���ִ������Ƿ��ܷ� 2�༭�������
	 */
	private class AtCockRoachRoomInSealListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			final MyWatchDataItem item = new MyWatchDataItem();
			item.setmMouseIndex(2);

			if (position == 0) {
				item.setmMouseKind1(1);
				item.setmMouseKind2(0);
				item.setmMouseKind3(0);
				item.setmMouseKind4("");

			} else if (position == 1) {
				item.setmMouseKind1(0);
				item.setmMouseKind2(1);
				item.setmMouseKind3(0);
				item.setmMouseKind4("");
			} else if (position == 2) {
				item.setmMouseKind1(0);
				item.setmMouseKind2(0);
				item.setmMouseKind3(1);
				item.setmMouseKind4("");
			} else if (position == 3) {
				// ����һ���Ի���������д��ע
				LayoutInflater inflater = getLayoutInflater();
				View dialog_view = inflater.inflate(R.layout.dialog2, null);
				final EditText et_dialog2 = (EditText) dialog_view
						.findViewById(R.id.et_dialog2);
				final Button btn_dialog2_yes = (Button) dialog_view
						.findViewById(R.id.btn_dialog2_yes);
				final Button btn_dialog2_no = (Button) dialog_view
						.findViewById(R.id.btn_dialog2_no);
				final Dialog otherFaciDialog = new Dialog(
						D2ECockRoachWatchActivity.this,
						R.style.my_other_point_dialog);
				otherFaciDialog.setContentView(dialog_view);
				otherFaciDialog.getWindow().setLayout(300, 150);
				otherFaciDialog.show();
				btn_dialog2_yes.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						at_cockroach_room_in_seal.setText(et_dialog2.getText()
								.toString());
						item.setmMouseKind1(0);
						item.setmMouseKind2(0);
						item.setmMouseKind3(0);
						item.setmMouseKind4(et_dialog2.getText().toString());
						otherFaciDialog.dismiss();
						if (D2EConfigures.TEST) {
							Log.e("AtCockRoachRoomInSealxxxxxxxxxxxxx>>", ""
									+ item.toString());
						}

					}
				});
				btn_dialog2_no.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						et_dialog2.setText("");
						at_cockroach_room_in_seal.setText("");
						otherFaciDialog.dismiss();
					}
				});

			}

			if (!mCockRoachItem.isContainWatchDataItem(item)) {
				mCockRoachItem.mWatchDataList.add(item);
			} else {
				mCockRoachItem.deleContainWatchDataItem(item);
				mCockRoachItem.mWatchDataList.add(item);
			}

			if (D2EConfigures.TEST) {
				Log.e("AtCockRoachRoomInSealxxxxxxxxxxxx>>",
						"" + item.toString());
			}
		}

	}

	/**
	 * ����ʳƷ���ִ������Ƿ��ܷ� 2������ť������
	 */
	private class IbtnCockRoachRoomInSealListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			if (D2EConfigures.TEST) {
				Log.e("IbtnCockRoachRoomInSealListener=============>",
						"onClick(View v)");
			}
			String str = at_cockroach_room_in_seal.getText().toString();
			if (str != null && str.length() > 0) {
				at_cockroach_room_in_seal.setText("");
			}
			at_cockroach_room_in_seal.showDropDown();
			at_cockroach_room_in_seal.setThreshold(1);
		}

	}

	/**
	 * ����ˮ�ء���̨�������������з�󯼣 1�༭�������
	 */
	private class AtCockRoachWatchRoomInFindListener implements
			OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			final MyWatchDataItem item = new MyWatchDataItem();
			item.setmMouseIndex(1);

			if (position == 0) {
				item.setmMouseKind1(1);
				item.setmMouseKind2(0);
				item.setmMouseKind3(0);
				item.setmMouseKind4("");

			} else if (position == 1) {
				item.setmMouseKind1(0);
				item.setmMouseKind2(1);
				item.setmMouseKind3(0);
				item.setmMouseKind4("");
			} else if (position == 2) {
				item.setmMouseKind1(0);
				item.setmMouseKind2(0);
				item.setmMouseKind3(1);
				item.setmMouseKind4("");
			} else if (position == 3) {
				// ����һ���Ի���������д��ע
				LayoutInflater inflater = getLayoutInflater();
				View dialog_view = inflater.inflate(R.layout.dialog2, null);
				final EditText et_dialog2 = (EditText) dialog_view
						.findViewById(R.id.et_dialog2);
				final Button btn_dialog2_yes = (Button) dialog_view
						.findViewById(R.id.btn_dialog2_yes);
				final Button btn_dialog2_no = (Button) dialog_view
						.findViewById(R.id.btn_dialog2_no);
				final Dialog otherFaciDialog = new Dialog(
						D2ECockRoachWatchActivity.this,
						R.style.my_other_point_dialog);
				otherFaciDialog.setContentView(dialog_view);
				otherFaciDialog.getWindow().setLayout(300, 150);
				otherFaciDialog.show();
				btn_dialog2_yes.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						at_cockroach_watch_room_in_find.setText(et_dialog2
								.getText().toString());
						item.setmMouseKind1(0);
						item.setmMouseKind2(0);
						item.setmMouseKind3(0);
						item.setmMouseKind4(et_dialog2.getText().toString());
						otherFaciDialog.dismiss();
						if (D2EConfigures.TEST) {
							Log.e("AtCockRoachWatchRoomInFindxxxxxxxxxxxxx>>",
									"" + item.toString());
						}

					}
				});
				btn_dialog2_no.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						et_dialog2.setText("");
						at_cockroach_watch_room_in_find.setText("");
						otherFaciDialog.dismiss();
					}
				});

			}

			if (!mCockRoachItem.isContainWatchDataItem(item)) {
				mCockRoachItem.mWatchDataList.add(item);
			} else {
				mCockRoachItem.deleContainWatchDataItem(item);
				mCockRoachItem.mWatchDataList.add(item);
			}

			if (D2EConfigures.TEST) {
				Log.e("AtCockRoachWatchRoomInFindxxxxxxxxxxxx>>",
						"" + item.toString());
			}
		}

	}

	/**
	 * ����ˮ�ء���̨�������������з�󯼣 1������ť������
	 */
	private class IbtnCockRoachWatchRoomInFindListener implements
			OnClickListener {

		@Override
		public void onClick(View v) {
			if (D2EConfigures.TEST) {
				Log.e("IbtnCockRoachWatchRoomInFindListener=============>",
						"onClick(View v)");
			}
			String str = at_cockroach_watch_room_in_find.getText().toString();
			if (str != null && str.length() > 0) {
				at_cockroach_watch_room_in_find.setText("");
			}
			at_cockroach_watch_room_in_find.showDropDown();
			at_cockroach_watch_room_in_find.setThreshold(1);
		}

	}

	/**
	 * �ֳ�����ʱ���ֵ����༭�������
	 */
	private class AtCockRoachWatchKindListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			final MyWatchDataItem item = new MyWatchDataItem();
			item.setmMouseIndex(0);

			if (position == 0) {
				item.setmMouseKind1(1);
				item.setmMouseKind2(0);
				item.setmMouseKind3(0);
				item.setmMouseKind4("");

			} else if (position == 1) {
				item.setmMouseKind1(0);
				item.setmMouseKind2(1);
				item.setmMouseKind3(0);
				item.setmMouseKind4("");
			} else if (position == 2) {
				item.setmMouseKind1(0);
				item.setmMouseKind2(0);
				item.setmMouseKind3(1);
				item.setmMouseKind4("");
			} else if (position == 3) {
				// ����һ���Ի���������д��ע
				LayoutInflater inflater = getLayoutInflater();
				View dialog_view = inflater.inflate(R.layout.dialog2, null);
				final EditText et_dialog2 = (EditText) dialog_view
						.findViewById(R.id.et_dialog2);
				final Button btn_dialog2_yes = (Button) dialog_view
						.findViewById(R.id.btn_dialog2_yes);
				final Button btn_dialog2_no = (Button) dialog_view
						.findViewById(R.id.btn_dialog2_no);
				final Dialog otherFaciDialog = new Dialog(
						D2ECockRoachWatchActivity.this,
						R.style.my_other_point_dialog);
				otherFaciDialog.setContentView(dialog_view);
				otherFaciDialog.getWindow().setLayout(300, 150);
				otherFaciDialog.show();
				btn_dialog2_yes.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						at_cockroach_watch_kind.setText(et_dialog2.getText()
								.toString());
						item.setmMouseKind1(0);
						item.setmMouseKind2(0);
						item.setmMouseKind3(0);
						item.setmMouseKind4(et_dialog2.getText().toString());
						otherFaciDialog.dismiss();
						if (D2EConfigures.TEST) {
							Log.e("AtCockRoachWatchKindxxxxxxxxxxxxx>>", ""
									+ item.toString());
						}

					}
				});
				btn_dialog2_no.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						et_dialog2.setText("");
						at_cockroach_watch_kind.setText("");
						otherFaciDialog.dismiss();
					}
				});

			}

			if (!mCockRoachItem.isContainWatchDataItem(item)) {
				mCockRoachItem.mWatchDataList.add(item);
			} else {
				mCockRoachItem.deleContainWatchDataItem(item);
				mCockRoachItem.mWatchDataList.add(item);
			}

			if (D2EConfigures.TEST) {
				Log.e("AtCockRoachWatchKindxxxxxxxxxxxx>>",
						"" + item.toString());
			}
		}

	}

	/**
	 * �ֳ�����ʱ���ֵ����������ť������
	 */
	private class IbtnCockRoachWatchKindListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// ���ﵯ��һ���Ի�������Ȼ����һ���б���ѡ������
			AlertDialog.Builder builder = new Builder(
					D2ECockRoachWatchActivity.this);
			LayoutInflater inflater = LayoutInflater
					.from(D2ECockRoachWatchActivity.this);
			View view = inflater.inflate(R.layout.d2e_dialog_watch_kind, null);
			// final TextView tv_select_count=(TextView)
			// view.findViewById(R.id.tv_select_count);
			ListView lv = (ListView) view.findViewById(R.id.lv);
			final EditText et_other_kind = (EditText) view
					.findViewById(R.id.et_other_kind);
			Button btn_select_ensure = (Button) view
					.findViewById(R.id.btn_select_ensure);
			Button btn_cancle_select = (Button) view
					.findViewById(R.id.btn_cancle_select);
			builder.setTitle(getResources().getString(R.string.choose_kind));
			builder.setView(view);
			final Dialog dialog = builder.create();
			dialog.show();
			dialog.getWindow().setLayout(450, 650);
			// ��������et_other_kindΪĬ����һ�α������Ϣ
			et_other_kind.setText(previous_kind);
			lv.setAdapter(mCockRoachKindAdapter);
			lv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View view,
						int position, long arg3) {
					ViewHolder holder = (ViewHolder) view.getTag();
					// 1����list��һ��ѡ�е�ʱ�򣬸ı�CheckBox��ǰ��״̬
					holder.cb.toggle();
					// 2��ÿ�λ�ȡitem��ʱ�򱣴�CheckBox��״̬
					MyWatchKindAdapter.mCheckBoxStateMap.put(position,
							holder.cb.isChecked());
					// 3�����CheckBoxΪѡ��״̬��������¼�������б��в���һ��
					if (holder.cb.isChecked() == true) {
						mCountList.add(mCockRoachKindData[position]);
					} else {
						// ���CheckBoxΪ��ѡ��״̬����ɾ������
						mCountList.remove(mCockRoachKindData[position]);
					}
					// 4��֪ͨ��ʾ��TextView��ListView��������
					// tv_select_count.setText("��ѡ��"+mCountList.size()+"��");
					mCockRoachKindAdapter.notifyDataSetChanged();
					// �ж��Ƿ�ѡ���ˡ��ޡ�
					if (holder.tv.getText().toString().equalsIgnoreCase("��")) {
						if (holder.cb.isChecked() == true) {
							for (int i = 0; i < listData.size(); i++) {
								if (!String.valueOf(i).equalsIgnoreCase(
										holder.tv.getTag().toString())) {
									MyWatchKindAdapter.mCheckBoxStateMap.put(i,
											false);
								}
							}
							et_other_kind.setText("");
							et_other_kind.setEnabled(false);
							et_other_kind.setClickable(false);
							et_other_kind.setFocusable(false);
							et_other_kind.setFocusableInTouchMode(false);
						} else {
							et_other_kind.setClickable(true);
							et_other_kind.setEnabled(true);
							et_other_kind.setFocusable(true);
							et_other_kind.setFocusableInTouchMode(true);
						}
					} else {
						for (int i = 0; i < listData.size(); i++) {
							if (listData.get(i).get("item_tv").toString()
									.equalsIgnoreCase("��")) {
								MyWatchKindAdapter.mCheckBoxStateMap.put(i,
										false);
							}
						}
						et_other_kind.setFocusable(true);
						et_other_kind.setFocusableInTouchMode(true);
						et_other_kind.setClickable(true);
						et_other_kind.setEnabled(true);
					}

				}
			});

			// ȷ����ʱ�򱣴���Ϣ
			btn_select_ensure.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// kris
					boolean hasdata = false;

					// ѭ�������������飬��ѡ�е���Ϣ����
					// 1����ʵ����һ�����ݶ���
					MyWatchDataItem item = new MyWatchDataItem();
					item.setmMouseIndex(0);
					for (int i = 0; i < listData.size(); i++) {
						if (MyWatchKindAdapter.mCheckBoxStateMap.get(i) == true) {
							if (i == 0) {
								hasdata = true;
								item.setmMouseKind1(1);
							} else if (i == 1) {
								hasdata = true;
								item.setmMouseKind2(1);
							} else if (i == 2) {
								hasdata = true;
								item.setmMouseKind3(1);
							} else if (i == 3) {
								hasdata = true;
								item.setmMouseKind5(1);
							}
						}
					}
					String strOtherKind = et_other_kind.getText().toString();
					previous_kind = strOtherKind;
					// 2���ж�������������û����д
					if (strOtherKind != null && strOtherKind.length() > 0) {
						hasdata = true;
						item.setmMouseKind4(strOtherKind);
					}

					if (D2EConfigures.TEST) {
						Log.e("MouseKindItemxxxxxxxxxxxx>>",
								"" + item.toString());
					}

					// �ж��Ƿ�ԭ�������ݣ�����ɾ��ԭ���ģ��������ڵ�
					if (item != null) {
						if (!mCockRoachItem.isContainWatchDataItem(item)) {
							mCockRoachItem.mWatchDataList.add(item);
						} else {
							mCockRoachItem.deleContainWatchDataItem(item);
							mCockRoachItem.mWatchDataList.add(item);
						}
					}
					// ��at����һ���ı���"�����"
					if (mCockRoachItem.mWatchDataList != null
							&& mCockRoachItem.mWatchDataList.size() > 0
							&& hasdata) {
						at_cockroach_watch_kind.setText(getResources()
								.getString(R.string.doing_finish));
					} else {
						at_cockroach_watch_kind.setText("");
					}
					dialog.dismiss();
				}
			});

			// ȡ����ʱ��������ݣ��رնԻ���
			btn_cancle_select.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// ѭ�������������飬��ѡ�еĶ���Ϊδѡ��
					for (int i = 0; i < listData.size(); i++) {
						if (MyWatchKindAdapter.mCheckBoxStateMap.get(i) == true) {
							MyWatchKindAdapter.mCheckBoxStateMap.put(i, false);
						}
					}
					clearKindData();
					dialog.dismiss();
				}

			});

			dialog.setOnKeyListener(new OnKeyListener() {

				@Override
				public boolean onKey(DialogInterface dialog, int keyCode,
						KeyEvent event) {
					if (keyCode == KeyEvent.KEYCODE_BACK) {
						if (D2EConfigures.TEST) {
							Log.e("dialog--------->", "��������ϵͳ�ķ����¼�");
						}
						clearKindData();
						dialog.dismiss();
					}
					return false;
				}
			});

		}

	}

	/**
	 * �������ݲ��뿪����
	 */
	private void saveViewData() {
		// �Ѿ�������������
		JJBaseApplication.isAddCockroachWatch = true;
		// ��������ӵ��б���
		JJBaseApplication.mWatchDataList.add(mCockRoachItem);
		// ��������
		D2ECockRoachWatchActivity.this.finish();
	}

	/**
	 * ���水ť������
	 */
	private class SaveListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// ��������Ƿ񶼼������
			if (isAllCheck()) {
				// �������ݣ����뿪����
				saveViewData();
			}
		}

	}

	/**
	 * ���ذ�ť������
	 */
	private class BackListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// overView();
			isBackPressed = true;
			if (isAllCheck()) {
				showWarmDialog();
			} else {
				overView();
			}
		}

	}

	/**
	 * Ϊ�����AutoAutoCompleteTextView����������
	 */
	private void setUpAutoTextAdapter() {

		at_cockroach_watch_room_in_find.setThreshold(1);
		at_cockroach_watch_room_in_find.setAdapter(mOtherStateAdapter1);

		at_cockroach_room_in_seal.setThreshold(1);
		at_cockroach_room_in_seal.setAdapter(mOtherStateAdapter2);

		at_cockroach_room_in_furni_find.setThreshold(1);
		at_cockroach_room_in_furni_find.setAdapter(mOtherStateAdapter3);

		at_cockroach_room_in_curr_find.setThreshold(1);
		at_cockroach_room_in_curr_find.setAdapter(mOtherStateAdapter4);

		at_cockroach_room_in_requ.setThreshold(5);
		at_cockroach_room_in_requ.setAdapter(mOtherStateAdapter5);

		at_cockroach_watch_syst_perf.setThreshold(1);
		at_cockroach_watch_syst_perf.setAdapter(mOtherStateAdapter6);

		at_cockroach_watch_hygiene_requ.setThreshold(1);
		at_cockroach_watch_hygiene_requ.setAdapter(mOtherStateAdapter7);

		at_cockroach_watch_pipeline_risk.setThreshold(1);
		at_cockroach_watch_pipeline_risk.setAdapter(mOtherStateAdapter8);

		at_cockroach_watch_things_be_breaked.setThreshold(1);
		at_cockroach_watch_things_be_breaked.setAdapter(mOtherStateAdapter9);

		at_cockroach_watch_prevention_think.setThreshold(1);
		at_cockroach_watch_prevention_think.setAdapter(mOtherStateAdapter10);

	}

	/**
	 * ����һ���Ի���
	 */
	private void showWarmDialog() {
		isBackPressed = false;
		AlertDialog.Builder builder = new Builder(
				D2ECockRoachWatchActivity.this);
		builder.setTitle(getResources().getString(R.string.warm_prompt));
		builder.setMessage(getResources().getString(R.string.quit));
		builder.setIcon(R.drawable.msg_dlg_warning);
		// ȷ����ʱ�򱣴�����
		builder.setPositiveButton(getResources().getString(R.string.quit_ok),
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						overView();
					}
				});
		// ȡ����ʱ���˳�����
		builder.setNegativeButton(getResources()
				.getString(R.string.quit_cancel),
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						
					}
				});
		Dialog dialog = builder.create();
		dialog.show();
	}

	/**
	 * �ж��Ƿ���������еļ��
	 */
	private boolean isAllCheck() {
		String str0 = at_cockroach_watch_kind.getText().toString();
		String str1 = at_cockroach_watch_room_in_find.getText().toString();
		String str2 = at_cockroach_room_in_seal.getText().toString();
		String str3 = at_cockroach_room_in_furni_find.getText().toString();
		String str4 = at_cockroach_room_in_curr_find.getText().toString();
		String str5 = at_cockroach_room_in_requ.getText().toString();
		String str6 = at_cockroach_watch_syst_perf.getText().toString();
		String str7 = at_cockroach_watch_hygiene_requ.getText().toString();
		String str8 = at_cockroach_watch_pipeline_risk.getText().toString();
		String str9 = at_cockroach_watch_things_be_breaked.getText().toString();
		String str10 = at_cockroach_watch_prevention_think.getText().toString();
		if ((str0 != null && str0.length() > 0)
				|| (str1 != null && str1.length() > 0)
				|| (str2 != null && str2.length() > 0)
				|| (str3 != null && str3.length() > 0)
				|| (str4 != null && str4.length() > 0)
				|| (str5 != null && str5.length() > 0)
				|| (str6 != null && str6.length() > 0)
				|| (str7 != null && str7.length() > 0)
				|| (str8 != null && str8.length() > 0)
				|| (str9 != null && str9.length() > 0)
				|| (str10 != null && str10.length() > 0)) {
			return true;
		}
		return false;
	}

	/**
	 * ��ʼ���ؼ�
	 */
	private void initView() {
		at_cockroach_watch_kind = (AutoCompleteTextView) this
				.findViewById(R.id.at_cockroach_watch_kind);
		ibtn_cockroach_watch_kind = (ImageButton) this
				.findViewById(R.id.ibtn_cockroach_watch_kind);
		at_cockroach_watch_room_in_find = (AutoCompleteTextView) this
				.findViewById(R.id.at_cockroach_watch_room_in_find);
		ibtn_cockroach_watch_room_in_find = (ImageButton) this
				.findViewById(R.id.ibtn_cockroach_watch_room_in_find);
		at_cockroach_room_in_seal = (AutoCompleteTextView) this
				.findViewById(R.id.at_cockroach_room_in_seal);
		ibtn_cockroach_room_in_seal = (ImageButton) this
				.findViewById(R.id.ibtn_cockroach_room_in_seal);
		at_cockroach_room_in_furni_find = (AutoCompleteTextView) this
				.findViewById(R.id.at_cockroach_room_in_furni_find);
		ibtn_cockroach_room_in_furni_find = (ImageButton) this
				.findViewById(R.id.ibtn_cockroach_room_in_furni_find);
		at_cockroach_room_in_curr_find = (AutoCompleteTextView) this
				.findViewById(R.id.at_cockroach_room_in_curr_find);
		ibtn_cockroach_room_in_curr_find = (ImageButton) this
				.findViewById(R.id.ibtn_cockroach_room_in_curr_find);
		at_cockroach_room_in_requ = (AutoCompleteTextView) this
				.findViewById(R.id.at_cockroach_room_in_requ);
		ibtn_cockroach_room_in_requ = (ImageButton) this
				.findViewById(R.id.ibtn_cockroach_room_in_requ);
		at_cockroach_watch_syst_perf = (AutoCompleteTextView) this
				.findViewById(R.id.at_cockroach_watch_syst_perf);
		ibtn_cockroach_watch_syst_perf = (ImageButton) this
				.findViewById(R.id.ibtn_cockroach_watch_syst_perf);
		at_cockroach_watch_hygiene_requ = (AutoCompleteTextView) this
				.findViewById(R.id.at_cockroach_watch_hygiene_requ);
		ibtn_cockroach_watch_hygiene_requ = (ImageButton) this
				.findViewById(R.id.ibtn_cockroach_watch_hygiene_requ);
		at_cockroach_watch_pipeline_risk = (AutoCompleteTextView) this
				.findViewById(R.id.at_cockroach_watch_pipeline_risk);
		ibtn_cockroach_watch_pipeline_risk = (ImageButton) this
				.findViewById(R.id.ibtn_cockroach_watch_pipeline_risk);
		at_cockroach_watch_things_be_breaked = (AutoCompleteTextView) this
				.findViewById(R.id.at_cockroach_watch_things_be_breaked);
		ibtn_cockroach_watch_things_be_breaked = (ImageButton) this
				.findViewById(R.id.ibtn_cockroach_watch_things_be_breaked);
		at_cockroach_watch_prevention_think = (AutoCompleteTextView) this
				.findViewById(R.id.at_cockroach_watch_prevention_think);
		ibtn_cockroach_watch_prevention_think = (ImageButton) this
				.findViewById(R.id.ibtn_cockroach_watch_prevention_think);

		btn_save_cockroach_watch = (Button) this
				.findViewById(R.id.btn_save_cockroach_watch);
		btn_back_cockroach_watch = (Button) this
				.findViewById(R.id.btn_back_cockroach_watch);

		// ʵ����һ��������������������
		mCockRoachItem = new MyWatchDataItem();
		// �������Ϊ����
		mCockRoachItem.setmWatchType("CockRoach");
		int array = R.array.cockroach_kind;
		// ��ȡ�����������
		getCockRoachKindData();
		array = R.array.mouse_other_state;
		mOtherStateAdapter1 = ArrayAdapter.createFromResource(this, array,
				R.layout.simple_dropdown_item_1line);
		mOtherStateAdapter2 = ArrayAdapter.createFromResource(this, array,
				R.layout.simple_dropdown_item_1line);
		mOtherStateAdapter3 = ArrayAdapter.createFromResource(this, array,
				R.layout.simple_dropdown_item_1line);
		mOtherStateAdapter4 = ArrayAdapter.createFromResource(this, array,
				R.layout.simple_dropdown_item_1line);
		mOtherStateAdapter5 = ArrayAdapter.createFromResource(this, array,
				R.layout.simple_dropdown_item_1line);
		mOtherStateAdapter6 = ArrayAdapter.createFromResource(this, array,
				R.layout.simple_dropdown_item_1line);
		mOtherStateAdapter7 = ArrayAdapter.createFromResource(this, array,
				R.layout.simple_dropdown_item_1line);
		mOtherStateAdapter8 = ArrayAdapter.createFromResource(this, array,
				R.layout.simple_dropdown_item_1line);
		mOtherStateAdapter9 = ArrayAdapter.createFromResource(this, array,
				R.layout.simple_dropdown_item_1line);
		mOtherStateAdapter10 = ArrayAdapter.createFromResource(this, array,
				R.layout.simple_dropdown_item_1line);

		// ��ʼ���������ؿؼ�
		initLampView();

	}

	/**
	 * �����Լ�������TextView�Ľ���ȡ��
	 */
	private void disableOtherTextFocus(TextView v) {
		tv_cockroach_watch_kind.setSelected(false);
		tv_cockroach_watch_room_in_find.setSelected(false);
		tv_cockroach_room_in_seal.setSelected(false);
		tv_cockroach_room_in_furni_find.setSelected(false);
		tv_cockroach_room_in_curr_find.setSelected(false);
		tv_cockroach_room_in_requ.setSelected(false);
		tv_cockroach_watch_syst_perf.setSelected(false);
		tv_cockroach_watch_hygiene_requ.setSelected(false);
		tv_cockroach_watch_pipeline_risk.setSelected(false);
		tv_cockroach_watch_things_be_breaked.setSelected(false);
		tv_cockroach_watch_prevention_think.setSelected(false);
		v.setSelected(true);

	}

	/**
	 * �����������صļ�����
	 */
	private void setUpLampListerner() {
		// 0
		panel_cockroach_watch_kind.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				disableOtherTextFocus(tv_cockroach_watch_kind);
			}
		});
		// 1
		panel_cockroach_watch_room_in_find
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						disableOtherTextFocus(tv_cockroach_watch_room_in_find);
					}
				});
		// 2
		panel_cockroach_room_in_seal.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				disableOtherTextFocus(tv_cockroach_room_in_seal);
			}
		});
		// 3
		panel_cockroach_room_in_furni_find
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						disableOtherTextFocus(tv_cockroach_room_in_furni_find);
					}
				});
		// 4
		panel_cockroach_room_in_curr_find
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						disableOtherTextFocus(tv_cockroach_room_in_curr_find);
					}
				});
		// 5
		panel_cockroach_room_in_requ.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				disableOtherTextFocus(tv_cockroach_room_in_requ);
			}
		});
		// 6
		panel_cockroach_watch_syst_perf
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						disableOtherTextFocus(tv_cockroach_watch_syst_perf);
					}
				});
		// 7
		panel_cockroach_watch_hygiene_requ
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						disableOtherTextFocus(tv_cockroach_watch_hygiene_requ);
					}
				});
		// 8
		panel_cockroach_watch_pipeline_risk
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						disableOtherTextFocus(tv_cockroach_watch_pipeline_risk);
					}
				});
		// 9
		panel_cockroach_watch_things_be_breaked
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						disableOtherTextFocus(tv_cockroach_watch_things_be_breaked);
					}
				});
		// 10
		panel_cockroach_watch_prevention_think
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						disableOtherTextFocus(tv_cockroach_watch_prevention_think);
					}
				});
	}

	/**
	 * ��ʼ���������ؿؼ�
	 */
	private void initLampView() {
		// 0
		panel_cockroach_watch_kind = (RelativeLayout) this
				.findViewById(R.id.panel_cockroach_watch_kind);
		tv_cockroach_watch_kind = (TextView) this
				.findViewById(R.id.tv_cockroach_watch_kind);
		// 1
		panel_cockroach_watch_room_in_find = (RelativeLayout) this
				.findViewById(R.id.panel_cockroach_watch_room_in_find);
		tv_cockroach_watch_room_in_find = (TextView) this
				.findViewById(R.id.tv_cockroach_watch_room_in_find);
		// 2
		panel_cockroach_room_in_seal = (RelativeLayout) this
				.findViewById(R.id.panel_cockroach_room_in_seal);
		tv_cockroach_room_in_seal = (TextView) this
				.findViewById(R.id.tv_cockroach_room_in_seal);
		// 3
		panel_cockroach_room_in_furni_find = (RelativeLayout) this
				.findViewById(R.id.panel_cockroach_room_in_furni_find);
		tv_cockroach_room_in_furni_find = (TextView) this
				.findViewById(R.id.tv_cockroach_room_in_furni_find);
		// 4
		panel_cockroach_room_in_curr_find = (RelativeLayout) this
				.findViewById(R.id.panel_cockroach_room_in_curr_find);
		tv_cockroach_room_in_curr_find = (TextView) this
				.findViewById(R.id.tv_cockroach_room_in_curr_find);
		// 5
		panel_cockroach_room_in_requ = (RelativeLayout) this
				.findViewById(R.id.panel_cockroach_room_in_requ);
		tv_cockroach_room_in_requ = (TextView) this
				.findViewById(R.id.tv_cockroach_room_in_requ);
		// 6
		panel_cockroach_watch_syst_perf = (RelativeLayout) this
				.findViewById(R.id.panel_cockroach_watch_syst_perf);
		tv_cockroach_watch_syst_perf = (TextView) this
				.findViewById(R.id.tv_cockroach_watch_syst_perf);
		// 7
		panel_cockroach_watch_hygiene_requ = (RelativeLayout) this
				.findViewById(R.id.panel_cockroach_watch_hygiene_requ);
		tv_cockroach_watch_hygiene_requ = (TextView) this
				.findViewById(R.id.tv_cockroach_watch_hygiene_requ);
		// 8
		panel_cockroach_watch_pipeline_risk = (RelativeLayout) this
				.findViewById(R.id.panel_cockroach_watch_pipeline_risk);
		tv_cockroach_watch_pipeline_risk = (TextView) this
				.findViewById(R.id.tv_cockroach_watch_pipeline_risk);
		// 9
		panel_cockroach_watch_things_be_breaked = (RelativeLayout) this
				.findViewById(R.id.panel_cockroach_watch_things_be_breaked);
		tv_cockroach_watch_things_be_breaked = (TextView) this
				.findViewById(R.id.tv_cockroach_watch_things_be_breaked);
		// 10
		panel_cockroach_watch_prevention_think = (RelativeLayout) this
				.findViewById(R.id.panel_cockroach_watch_prevention_think);
		tv_cockroach_watch_prevention_think = (TextView) this
				.findViewById(R.id.tv_cockroach_watch_prevention_think);
	}

	/**
	 * ��ȡ�����������
	 */
	private void getCockRoachKindData() {
		mCockRoachKindData = getResources().getStringArray(
				R.array.cockroach_kind);
		listData = new ArrayList<HashMap<String, Object>>();
		mCountList = new ArrayList<String>();
		for (int i = 0; i < mCockRoachKindData.length; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("item_tv", mCockRoachKindData[i]);
			map.put("item_cb", false);
			listData.add(map);
		}
		mCockRoachKindAdapter = new MyWatchKindAdapter(this, listData,
				R.layout.d2e_at_watch_kind_item, new String[] { "item_tv",
						"item_cb" }, new int[] { R.id.tv_list_item,
						R.id.cb_list_item });

	}

	@Override
	public void backAciton() {
		isBackPressed = true;
		if (isAllCheck()) {
			showWarmDialog();
		} else {
			overView();
		}
	}

	/**
	 * ��������
	 */
	private void overView() {
		overridePendingTransition(R.anim.fade, R.anim.hold);
		D2ECockRoachWatchActivity.this.finish();
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
