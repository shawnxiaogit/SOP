package com.mobilercn.sop.activity;

import java.io.InputStream;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.mobilercn.base.BadgeView;
import com.mobilercn.base.JJBaseActivity;
import com.mobilercn.base.JJBaseApplication;
import com.mobilercn.base.JJBaseService;
import com.mobilercn.config.D2EConfigures;
import com.mobilercn.sop.R;
import com.mobilercn.sop.bt.SOPBTCallHelper;
import com.mobilercn.sop.bt.SOPBluetoothService;
import com.mobilercn.sop.data.MyCustomerListData;
import com.mobilercn.sop.item.D2ECustomer;
import com.mobilercn.task.JJTask;
import com.mobilercn.util.JJNetHelper;
import com.mobilercn.util.YTFileHelper;
import com.mobilercn.util.YTStringHelper;

public class D2ECheckEquipsActivity extends JJBaseActivity {
	private String mStringNone = "None";
	private String mPostionName;
	private String mMouseNumber;
	private String mCustom;
	private String mPosition;
	private String mTag;

	private BadgeView mBadgeView;

	private EditText mMemo;

	// ��ҵ��
	private EditText mRateFact;
	private EditText mVolumeFact;

	// ����-����
	private EditText mRoachEdt;
	private EditText mMouseEdt;

	// ����-����
	public EditText mRoachActiveNubEdt;
	public EditText mMouseActiveNubEdt;
	public EditText mRoachAdultsEdt;
	public EditText mMouseAdultsEdt;
	public EditText mRoachNymphsEdt;
	public EditText mMouseNymphsEdt;
	// ������
	public TextView mMounseNumeberEdt;
	public EditText mEquipPowerEdt;
	public Bundle bundle;
	private boolean isReplaceCard = false;
	private String mTagNum;

	// �滻��ǩ���
	FrameLayout panel_replace;
	Button btn;
	Button btnyes;
	Button btnclear;
	Button btnretry;
	TextView te;
	TextView nub;
	EditText name;

	private YTFileHelper ytfileHelper;

	// ��ҵ��
	TextView pointTV;
	TableRow panel_tagpage_tag;

	protected void onResume() {
		super.onResume();
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);

		if (JJBaseApplication.sop_checkp.mType.toLowerCase().equals(
				"OPSite".toLowerCase())) {
			setContentView(R.layout.d2e_opsitepage);
		} else if (JJBaseApplication.sop_checkp.mType.toLowerCase().equals(
				"Monitor".toLowerCase())) {
			if (JJBaseApplication.g_customer.mTaskType == D2EConfigures.MONITOR_INIT) {
				setContentView(R.layout.d2e_check_set);
			} else {
				setContentView(R.layout.d2e_check_check);
			}
		}
		// ����������ģ�����
		else if (JJBaseApplication.sop_checkp.mType.toLowerCase().equals(
				"Survey".toLowerCase())) {
			setContentView(R.layout.d2e_check_mouse_number);
		}
		// add by shawn 2012-10-14
		// �����ж�������ʩ����
		// �����ж�����ʵʩ����
		// End
		// �ľӿ������������Ͷ������������
		else {
			setContentView(R.layout.d2e_ckpointspage);
		}
		Window window = getWindow();
		window.setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.d2e_titlebar);

		TextView tv = (TextView) findViewById(R.id.title);
		tv.setText("SOP");

		mBadgeView = (BadgeView) findViewById(R.id.countTask);
		mBadgeView.setBadgePosition(BadgeView.POSITION_CENTER);
		mBadgeView.setBackgroundResource(R.drawable.badge_ifaux);
		mBadgeView.setVisibility(View.GONE);
		if (JJBaseApplication.user_CountTask != null
				&& JJBaseApplication.user_CountTask.length() > 0) {
			mBadgeView.setText(JJBaseApplication.user_CountTask);
		}

		ytfileHelper = YTFileHelper.getInstance();
		ytfileHelper.setContext(getApplicationContext());

		mCustom = JJBaseApplication.g_customer.mName;
		mPosition = JJBaseApplication.sop_location.getTitle();
		mTag = JJBaseApplication.sop_checkp.getTitle();

		Intent intent = getIntent();
		// ��ȡ�ϸ�ҳ���ȡ��Ƭ��ʱ�������������Ϣ
		bundle = intent.getExtras();
		mMouseNumber = (String) bundle.get("mMouseNumberInt");
		mPostionName = bundle.getString("mPosition");
		isReplaceCard = bundle.getBoolean("isReplaceCard");
		if (D2EConfigures.TEST) {
			if (mPostionName != null && mPostionName.length() > 0) {
				Log.e("mPostionName---------------->", mPostionName);
			}
			if (D2EConfigures.TEST) {
				Log.e("isReplaceCard---------------------->", ""
						+ isReplaceCard);
			}
		}

		int array = R.array.sticky_mouse_board;
		if (JJBaseApplication.sop_checkp.mType.toLowerCase().equals(
				"OPSite".toLowerCase())) {
			TextView dt = (TextView) findViewById(R.id.tagpage_custom);
			dt.setText(mCustom);

			dt = (TextView) findViewById(R.id.tagpage_position);
			dt.setText(mPosition);

			panel_tagpage_tag = (TableRow) findViewById(R.id.panel_tagpage_tag);
			pointTV = (TextView) findViewById(R.id.tagpage_tag);
			pointTV.setText(mTag);

			dt = (TextView) findViewById(R.id.tagpage_Supplies);
			if (JJBaseApplication.sop_checkp.mSuppliesName != null
					&& JJBaseApplication.sop_checkp.mSuppliesName.length() > 0) {
				dt.setText(JJBaseApplication.sop_checkp.mSuppliesName);
			} else {
				dt.setText("-");
			}

			dt = (TextView) findViewById(R.id.tagpage_Instrument);
			if (JJBaseApplication.sop_checkp.mInstrumentName != null
					&& JJBaseApplication.sop_checkp.mInstrumentName.length() > 0) {
				dt.setText(JJBaseApplication.sop_checkp.mInstrumentName);
			} else {
				dt.setText("-");
			}

			dt = (TextView) findViewById(R.id.tagpage_Rate);
			if (JJBaseApplication.sop_checkp.mRate != null
					&& JJBaseApplication.sop_checkp.mRate.length() > 0) {
				dt.setText(JJBaseApplication.sop_checkp.mRate + " " + "X");
			} else {
				dt.setText("-");
			}

			dt = (TextView) findViewById(R.id.tagpage_Volume);
			if (JJBaseApplication.sop_checkp.mVolume != null
					&& JJBaseApplication.sop_checkp.mVolume.length() > 0) {
				dt.setText(JJBaseApplication.sop_checkp.mVolume);
			} else {
				dt.setText("-");
			}

			mRateFact = (EditText) findViewById(R.id.tagpage_Rate_fact);
			mVolumeFact = (EditText) findViewById(R.id.tagpage_Volume_fact);

		} else if (JJBaseApplication.sop_checkp.mType.toLowerCase().equals(
				"Monitor".toLowerCase())) {
			TextView dt = (TextView) findViewById(R.id.tagpage_custom);
			dt.setText(mCustom);

			dt = (TextView) findViewById(R.id.tagpage_position);
			dt.setText(mPosition);

			panel_tagpage_tag = (TableRow) findViewById(R.id.panel_tagpage_tag);
			pointTV = (TextView) findViewById(R.id.tagpage_tag);
			pointTV.setText(mTag);

			if (JJBaseApplication.g_customer.mTaskType == D2EConfigures.MONITOR_INIT) {
				mRoachEdt = (EditText) findViewById(R.id.tagpage_Roach);
				mMouseEdt = (EditText) findViewById(R.id.tagpage_Mouse);
			} else {
				mRoachActiveNubEdt = (EditText) findViewById(R.id.tagpage_RoachActive);
				mMouseActiveNubEdt = (EditText) findViewById(R.id.tagpage_MouseActive);
				mRoachAdultsEdt = (EditText) findViewById(R.id.tagpage_RoachAdults);
				mMouseAdultsEdt = (EditText) findViewById(R.id.tagpage_MouseAdults);
				mRoachNymphsEdt = (EditText) findViewById(R.id.tagpage_RoachNymphs);
				mMouseNymphsEdt = (EditText) findViewById(R.id.tagpage_MouseNymphs);
			}
		}
		// ������豸��� ��Survey����������һ��
		else if (JJBaseApplication.sop_checkp.mType.toLowerCase().equals(
				"Survey".toLowerCase())) {
			// �ͻ�����
			TextView dt = (TextView) findViewById(R.id.tagpage_custom);
			dt.setText(mCustom);
			// λ�ñ�ǩ
			dt = (TextView) findViewById(R.id.tagpage_position);
			dt.setText(mPosition);

			panel_tagpage_tag = (TableRow) findViewById(R.id.panel_tagpage_tag);
			// ��ҵ������
			pointTV = (TextView) findViewById(R.id.tagpage_tag);
			pointTV.setText(mTag);
			// ��������
			mMounseNumeberEdt = (TextView) findViewById(R.id.tagpage_Mouse_number);
			mMounseNumeberEdt.setText(mMouseNumber);
			// �豸����
			mEquipPowerEdt = (EditText) findViewById(R.id.tagpage_Equip_power);

		}
		// add by shawn 2012-10-14 Begin
		// ֱ���������ж��Ƿ���������ʩ������ʵʩ�������״̬�����أ�����Ҫ�����⴦��
		// ���а�"�豸����"�޸�Ϊ"��ʩ����"��"��ʩ����"
		// 1��������ʩ
		else if (JJBaseApplication.sop_checkp.mType.toLowerCase().equals(
				"FacilitiesOther".toLowerCase())) {
			// �ͻ�����
			TextView dt = (TextView) findViewById(R.id.tagpage_custom);
			dt.setText(mCustom);
			// λ�ñ�ǩ
			TextView dt1 = (TextView) findViewById(R.id.tagpage_position);
			dt1.setText(mPosition);

			// ��ҵ������
			panel_tagpage_tag = (TableRow) findViewById(R.id.panel_tagpage_tag);
			// ��ҵ������
			pointTV = (TextView) findViewById(R.id.tagpage_tag);
			pointTV.setText(mTag);

			// �豸����չʾ
			TextView tv_equip_name_view = (TextView) findViewById(R.id.tv_equip_name_view);
			tv_equip_name_view.setText(getResources().getString(
					R.string.check_equip_facilities_name));

			// �豸����
			TextView dt3 = (TextView) findViewById(R.id.equit_name);
			dt3.setText(JJBaseApplication.sop_checkp.getmEquipName());
			// ���豸״̬����
			TableRow panel_equip_state = (TableRow) findViewById(R.id.panel_equip_state);
			panel_equip_state.setVisibility(View.GONE);

		}
		// 2������ʵʩ
		else if (JJBaseApplication.sop_checkp.mType.toLowerCase().equals(
				"OPSiteOther".toLowerCase())) {
			// �ͻ�����
			TextView dt = (TextView) findViewById(R.id.tagpage_custom);
			dt.setText(mCustom);
			// λ�ñ�ǩ
			TextView dt1 = (TextView) findViewById(R.id.tagpage_position);
			dt1.setText(mPosition);

			// ��ҵ������
			panel_tagpage_tag = (TableRow) findViewById(R.id.panel_tagpage_tag);
			// ��ҵ������
			pointTV = (TextView) findViewById(R.id.tagpage_tag);
			pointTV.setText(mTag);

			// �豸����չʾ
			TextView tv_equip_name_view = (TextView) findViewById(R.id.tv_equip_name_view);
			tv_equip_name_view.setText(getResources().getString(
					R.string.check_equip_opsite_name));

			// �豸����
			TextView dt3 = (TextView) findViewById(R.id.equit_name);
			dt3.setText(JJBaseApplication.sop_checkp.getmEquipName());
			// ���豸״̬����
			TableRow panel_equip_state = (TableRow) findViewById(R.id.panel_equip_state);
			panel_equip_state.setVisibility(View.GONE);

		}
		// End
		else {
			// �ͻ�����
			TextView dt = (TextView) findViewById(R.id.tagpage_custom);
			dt.setText(mCustom);
			// λ�ñ�ǩ
			TextView dt1 = (TextView) findViewById(R.id.tagpage_position);
			dt1.setText(mPosition);

			// ��ҵ������
			panel_tagpage_tag = (TableRow) findViewById(R.id.panel_tagpage_tag);
			// ��ҵ������
			pointTV = (TextView) findViewById(R.id.tagpage_tag);
			pointTV.setText(mTag);

			// �豸����
			TextView dt3 = (TextView) findViewById(R.id.equit_name);
			if (JJBaseApplication.sop_checkp.mType.toLowerCase().equals(
					"Baitstation".toLowerCase())) {
				dt3.setText(getResources().getString(
						R.string.check_equip_mouse_bait_station));
				array = R.array.bait;
			} else if (JJBaseApplication.sop_checkp.mType.toLowerCase().equals(
					"Glueboard".toLowerCase())) {
				dt3.setText(getResources().getString(
						R.string.check_equip_mouse_glue_board));
				array = R.array.sticky_mouse_board;
			} else if (JJBaseApplication.sop_checkp.mType.toLowerCase().equals(
					"Flycatch".toLowerCase())) {
				dt3.setText(getResources().getString(
						R.string.check_equip_fly_catch));
				array = R.array.pests;
			} else if (JJBaseApplication.sop_checkp.mType.toLowerCase().equals(
					"RodentTrap".toLowerCase())) {
				dt3.setText(getResources().getString(
						R.string.check_equip_rodent_trap));
				array = R.array.mousetrap;
			} else if (JJBaseApplication.sop_checkp.mType.toLowerCase().equals(
					"InsectCatch".toLowerCase())) {
				dt3.setText(getResources().getString(
						R.string.check_equip_insect_catch));
				array = R.array.InsectCatch;
			} else if (JJBaseApplication.sop_checkp.mType.toLowerCase().equals(
					"XJK".toLowerCase())) {
				dt3.setText(getResources().getString(R.string.check_equip_xjk));
				array = R.array.xinjukang;
			} else if (JJBaseApplication.sop_checkp.mType.toLowerCase().equals(
					"Survey".toLowerCase())) {
				dt3.setText(getResources().getString(
						R.string.check_equip_survey));
			}// ������ʩ"���" add by shawn 2012-10-12
			else if (JJBaseApplication.sop_checkp.mType.toLowerCase().equals(
					"MouseTrap".toLowerCase())) {
				dt3.setText(getResources().getString(
						R.string.check_equip_mouse_trap));
				array = R.array.mouse_trap;
			}
			// ������ʩ"���沶׽��" add by shawn 2012-10-12
			else if (JJBaseApplication.sop_checkp.mType.toLowerCase().equals(
					"BugCatcher".toLowerCase())) {
				dt3.setText(getResources().getString(
						R.string.check_equip_bug_catcher));
				array = R.array.bug_catch;
			}
			// ������ʩ"ճ���" add by shawn 2012-10-12
			else if (JJBaseApplication.sop_checkp.mType.toLowerCase().equals(
					"BugGluePlate".toLowerCase())) {
				dt3.setText(getResources().getString(
						R.string.check_equip_bug_glue_plate));
				array = R.array.bugglueplate;
			}

			if (D2EConfigures.TEST) {
				Log.e("array------------>", "" + array);
			}
			ArrayAdapter adapter = ArrayAdapter.createFromResource(this, array,
					R.layout.simple_dropdown_item_1line);
			// �豸״̬
			final AutoCompleteTextView text = (AutoCompleteTextView) this
					.findViewById(R.id.ckpoint_state);
			text.setAdapter(adapter);

			text.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					if (text.getText().toString() == null
							|| text.getText().toString().length() == 0
							|| text.getText().toString()
									.equalsIgnoreCase(mStringNone)) {
						((JJBaseApplication) getApplication())
								.showMessage(getResources().getString(
										R.string.please_comlete_data));
						return;
					} else {
						JJBaseApplication.selectPoint = JJBaseApplication.sop_checkp;
						JJBaseApplication.sop_checkp.mState = text.getText()
								.toString();
						JJBaseApplication.sop_checkp.mStateInt = String
								.valueOf(position);
						// add by shawn 2012-10-18 Begin
						// ������ҵ�����Ϣ
						// modify by shawn 2012-11-21
						// ������ҵ����Ϣ�����пͻ��б��е�ѡ�������ŵĿͻ�����
						MyCustomerListData myCustomerListData = (MyCustomerListData) ytfileHelper
								.deSerialObject(getResources().getString(
										R.string.customer_list));
						D2ECustomer customer = myCustomerListData
								.getWeekSerialCustomer(JJBaseApplication.g_customer);
						customer.setSelectPoint(JJBaseApplication.selectPoint);
						ytfileHelper.serialObject(
								getResources()
										.getString(R.string.customer_list),
								myCustomerListData);
						// End
						// End
					}

				}

			});

			Button btn1 = (Button) findViewById(R.id.loginImageButton02);
			btn1.setOnClickListener(new OnClickListener() {
				public void onClick(View arg0) {
					if (text.getText() != null && text.length() > 0) {
						text.setText("");
						text.showDropDown();
						text.setThreshold(1000);
					} else {
						text.showDropDown();
						text.setThreshold(1000);
					}
				}
			});

		}

		mMemo = (EditText) findViewById(R.id.tagpage_memo);

		if (isReplaceCard) {
			// add by shawn 2012-9-9 Begin
			// ����ҵ����������
			panel_tagpage_tag.setVisibility(View.GONE);
			// End
			panel_replace = (FrameLayout) findViewById(R.id.panel_replace);
			panel_replace.setVisibility(View.VISIBLE);
			btn = (Button) findViewById(R.id.button_send);
			btnclear = (Button) findViewById(R.id.btn_clear);
			btnretry = (Button) findViewById(R.id.btn_retry);
			te = (TextView) findViewById(R.id.tag_new);

			name = (EditText) findViewById(R.id.location_name);
			name.setText(JJBaseApplication.sop_checkp.getTitle());

			btn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					showProcessDialog(getResources().getString(
							R.string.reading_card));
					SOPBluetoothService
							.getService()
							.doTask(SOPBTCallHelper.TAG_CHANGE,
									D2ECheckListActivity.class.getName(),
									SOPBTCallHelper
											.getInitOrder(SOPBluetoothService.g_readerID));

				}

			});

			btnretry.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					showProcessDialog(getResources().getString(
							R.string.reading_card));
					SOPBluetoothService
							.getService()
							.doTask(SOPBTCallHelper.TAG_CHANGE,
									D2ECheckListActivity.class.getName(),
									SOPBTCallHelper
											.getInitOrder(SOPBluetoothService.g_readerID));
				}

			});

			btnclear.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					te.setText("");
				}

			});

		}

	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {

		switch (item.getItemId()) {

		case R.id.menu_back: {
			if (!JJBaseApplication.sop_checkp.mChecked) {
				Builder builder = new Builder(D2ECheckEquipsActivity.this);
				builder.setIcon(android.R.drawable.ic_dialog_info);
				builder.setTitle(getResources().getString(R.string.warm_prompt));
				builder.setMessage(getResources().getString(
						R.string.is_need_save));
				builder.setPositiveButton(
						getResources().getString(R.string.jj_sure),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								JJBaseApplication.selectPoint = JJBaseApplication.sop_checkp;
								// ȫ����Ϣ�����˵��ո�ͻس�
								JJBaseApplication.sop_checkp.mMemo = YTStringHelper
										.replaceBlank(mMemo.getText()
												.toString());
								JJBaseApplication.sop_checkp.mChecked = true;

								if (D2EConfigures.G_DEBUG) {
									Log.e("------------",
											" D2EConfigures.sop_checkp.mMemo: "
													+ JJBaseApplication.sop_checkp.mMemo);
								}

								// �����ж�����������
								if (JJBaseApplication.sop_checkp.mType
										.toLowerCase().equals(
												"OPSite".toLowerCase())) {// ��ҵ��
									JJBaseApplication.sop_checkp.mRateFact = YTStringHelper
											.replaceBlank(mRateFact.getText()
													.toString());
									JJBaseApplication.sop_checkp.mVolumeFact = YTStringHelper
											.replaceBlank(mVolumeFact.getText()
													.toString());

									if (D2EConfigures.G_DEBUG) {
										Log.e("------------",
												JJBaseApplication.sop_checkp.mRateFact
														+ " and "
														+ JJBaseApplication.sop_checkp.mVolumeFact);
									}
								}
								// add by shawn 2012-10-15 Begin
								// �����������ʩ������ʵʩ����������Ϊ��"��ҵ���"��stateIntΪ0
								else if (JJBaseApplication.sop_checkp.mType
										.toLowerCase()
										.equals("FacilitiesOther".toLowerCase())) {
									JJBaseApplication.sop_checkp.mStateInt = getResources()
											.getString(
													R.string.check_equip_facilities_other_state_int);
									JJBaseApplication.sop_checkp.mState = getResources()
											.getString(
													R.string.check_equip_facilities_other_state);
								} else if (JJBaseApplication.sop_checkp.mType
										.toLowerCase().equals(
												"OPSiteOther".toLowerCase())) {
									JJBaseApplication.sop_checkp.mStateInt = getResources()
											.getString(
													R.string.check_equip_opsite_other_state_int);
									JJBaseApplication.sop_checkp.mState = getResources()
											.getString(
													R.string.check_equip_opsite_other_state);
								}
								// End

								// �����ʱ��ҲҪ�жϣ��ǲ����豸�����һ��
								else if (JJBaseApplication.sop_checkp.mType
										.toLowerCase().equals(
												"Survey".toLowerCase())) {// �豸��⣬������ģ��
									// ���泬����ģ�����ݣ��ύ�����ʱ��ҲҪ�����ж�
									JJBaseApplication.sop_checkp.mMounseNumber = YTStringHelper
											.replaceBlank(mMounseNumeberEdt
													.getText().toString());
									if (D2EConfigures.TEST) {
										Log.i("D2EConfigures.sop_checkp.mMounseNumber-------->",
												JJBaseApplication.sop_checkp.mMounseNumber);
									}
								} else if (JJBaseApplication.sop_checkp.mType
										.toLowerCase().equals(
												"Monitor".toLowerCase())) {
									if (JJBaseApplication.g_customer.mTaskType == D2EConfigures.MONITOR_INIT) {
										JJBaseApplication.sop_checkp.mRoachNub = YTStringHelper
												.replaceBlank(mRoachEdt
														.getText().toString());
										JJBaseApplication.sop_checkp.mMouseNub = YTStringHelper
												.replaceBlank(mMouseEdt
														.getText().toString());

										if (D2EConfigures.G_DEBUG) {
											Log.e("------------",
													" D2EConfigures.sop_checkp.mRoachNub: "
															+ JJBaseApplication.sop_checkp.mRoachNub
															+ " and "
															+ " D2EConfigures.sop_checkp.mMouseNub: "
															+ JJBaseApplication.sop_checkp.mMouseNub);
										}
									} else {
										JJBaseApplication.sop_checkp.mRoachActive = YTStringHelper
												.replaceBlank(mRoachActiveNubEdt
														.getText().toString());
										JJBaseApplication.sop_checkp.mMouseActive = YTStringHelper
												.replaceBlank(mMouseActiveNubEdt
														.getText().toString());

										int sum = 0;
										if (mRoachAdultsEdt.getText()
												.toString() != null
												&& mRoachAdultsEdt.getText()
														.toString().length() > 0) {
											try {
												sum += Integer
														.parseInt(mRoachAdultsEdt
																.getText()
																.toString());
											} catch (Exception e) {
											}
										}
										if (mRoachNymphsEdt.getText()
												.toString() != null
												&& mRoachNymphsEdt.getText()
														.toString().length() > 0) {
											try {
												sum += Integer
														.parseInt(mRoachNymphsEdt
																.getText()
																.toString());
											} catch (Exception e) {
											}
										}
										JJBaseApplication.sop_checkp.mRoachTotal = YTStringHelper
												.replaceBlank(String
														.valueOf(sum));

										sum = 0;
										if (mMouseAdultsEdt.getText()
												.toString() != null
												&& mMouseAdultsEdt.getText()
														.toString().length() > 0) {
											try {
												sum += Integer
														.parseInt(mMouseAdultsEdt
																.getText()
																.toString());
											} catch (Exception e) {
											}
										}
										if (mMouseNymphsEdt.getText()
												.toString() != null
												&& mMouseNymphsEdt.getText()
														.toString().length() > 0) {
											try {
												sum += Integer
														.parseInt(mMouseNymphsEdt
																.getText()
																.toString());
											}

											catch (Exception e) {
											}
										}
										JJBaseApplication.sop_checkp.mMouseTotal = YTStringHelper
												.replaceBlank(String
														.valueOf(sum));

										if (D2EConfigures.G_DEBUG) {
											Log.e("------------",
													" D2EConfigures.sop_checkp.mRoachActive: "
															+ JJBaseApplication.sop_checkp.mRoachActive
															+ " and "
															+ " D2EConfigures.sop_checkp.mMouseActive: "
															+ JJBaseApplication.sop_checkp.mMouseActive
															+ " and "
															+ " D2EConfigures.sop_checkp.mRoachTotal: "
															+ JJBaseApplication.sop_checkp.mRoachTotal
															+ " and "
															+ " D22EConfigures.sop_checkp.mMouseTotal: "
															+ JJBaseApplication.sop_checkp.mMouseTotal);
										}

									}
								}
								// modify by shawn 2012-11-21
								// ������ҵ����Ϣ�����пͻ��б��е�ѡ�������ŵĿͻ�����
								MyCustomerListData myCustomerListData = (MyCustomerListData) ytfileHelper
										.deSerialObject(getResources()
												.getString(
														R.string.customer_list));
								D2ECustomer customer = myCustomerListData
										.getWeekSerialCustomer(JJBaseApplication.g_customer);
								customer.setSelectPoint(JJBaseApplication.selectPoint);
								ytfileHelper.serialObject(getResources()
										.getString(R.string.customer_list),
										myCustomerListData);
								// End
								final Intent intent = new Intent(
										D2ECheckEquipsActivity.this,
										D2ECheckListActivity.class);
								D2ECheckEquipsActivity.this
										.startActivity(intent);
								finish();
							}
						});
				builder.setNegativeButton(
						getResources().getString(R.string.btn_d2eprompt_cancle),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								final Intent intent = new Intent(
										D2ECheckEquipsActivity.this,
										D2ECheckListActivity.class);
								D2ECheckEquipsActivity.this
										.startActivity(intent);
								finish();
							}
						});
				builder.show();
			} else {
				final Intent intent = new Intent(D2ECheckEquipsActivity.this,
						D2ECheckListActivity.class);
				D2ECheckEquipsActivity.this.startActivity(intent);
				finish();
			}
		}
			break;

		case R.id.menu_view: {
			final Intent intent = new Intent(D2ECheckEquipsActivity.this,
					D2EReportViewActivity.class);
			intent.putExtra("type", D2EReportViewActivity.VIEW_TYPE_TODO);
			D2ECheckEquipsActivity.this.startActivity(intent);
		}
			break;

		case R.id.menu_save: {
			// add by shawn 2012-9-9 Begin
			// ������滻����������,����ɹ�֮��ű��汨����Ϣ,�����¸�ҳ��
			// �滻��ǩ������
			if (isReplaceCard) {
				// add by shawn 2012-9-9 Begin
				// �����ڱ�ע����д��ǩ��ʧ���,������ʾ"���ڱ�ע����д�滻ԭ��"
				if (mMemo.getText().toString() != null
						&& mMemo.getText().toString().length() > 0) {
					if (name.getText().length() == 0 || name.getText() == null
							|| te.getText().length() == 0
							|| te.getText() == null) {
						((JJBaseApplication) getApplication())
								.showMessage(getResources().getString(
										R.string.please_comlete_data));
						break;
					} else {
						String[] params = new String[] { "FunType",
								"updataPoint", "OrgID",
								JJBaseApplication.user_OrgID, "ReadNum",
								SOPBluetoothService.g_readerID, "TagNum",
								te.getText().toString().trim(), "PointID",
								JJBaseApplication.sop_checkp.getmPointId(),
								"Title", name.getText().toString().trim() };

						YTStringHelper.array2string(params);
						long id = JJBaseService
								.addMutilpartHttpTask(
										"http://www.ohpest.com/ohpest-pco/webservices/get/data.html",
										params, D2ECheckEquipsActivity.this
												.getClass().getName(),
										D2EConfigures.TASK_CHANGETAG);

						setCurTaskID(id);
						showWaitDialog(getResources().getString(
								R.string.handling_wait));
					}
				} else {
					((JJBaseApplication) getApplication())
							.showMessage(getResources().getString(
									R.string.replace_reason));
				}
				// End
			} else {

				// End

				if (D2EConfigures.G_DEBUG) {
					Log.e("------------", " D2EConfigures.sop_checkp.mMemo: "
							+ JJBaseApplication.sop_checkp.mMemo);
				}
				JJBaseApplication.selectPoint = JJBaseApplication.sop_checkp;
				// �����ж�����������
				if (JJBaseApplication.sop_checkp.mType.toLowerCase().equals(
						"OPSite".toLowerCase())) {// ��ҵ��
					if (mRateFact.getText().toString() == null
							|| mRateFact.getText().toString().length() == 0
							|| mVolumeFact.getText().toString() == null
							|| mVolumeFact.getText().toString().length() == 0) {
						((JJBaseApplication) getApplication())
								.showMessage(getResources().getString(
										R.string.please_comlete_data));
						break;
					} else {
						JJBaseApplication.sop_checkp.mRateFact = YTStringHelper
								.replaceBlank(mRateFact.getText().toString());
						JJBaseApplication.sop_checkp.mVolumeFact = YTStringHelper
								.replaceBlank(mVolumeFact.getText().toString());
					}

					if (D2EConfigures.G_DEBUG) {
						Log.e("------------",
								JJBaseApplication.sop_checkp.mRateFact
										+ " and "
										+ JJBaseApplication.sop_checkp.mVolumeFact);
					}
				}

				// add by shawn 2012-10-15 Begin
				// �����������ʩ������ʵʩ����������Ϊ��"-"
				else if (JJBaseApplication.sop_checkp.mType.toLowerCase()
						.equals("FacilitiesOther".toLowerCase())) {
					JJBaseApplication.sop_checkp.mStateInt = getResources()
							.getString(
									R.string.check_equip_facilities_other_state_int);
					JJBaseApplication.sop_checkp.mState = getResources()
							.getString(
									R.string.check_equip_facilities_other_state);
				} else if (JJBaseApplication.sop_checkp.mType.toLowerCase()
						.equals("OPSiteOther".toLowerCase())) {
					JJBaseApplication.sop_checkp.mStateInt = getResources()
							.getString(
									R.string.check_equip_opsite_other_state_int);
					JJBaseApplication.sop_checkp.mState = getResources()
							.getString(R.string.check_equip_opsite_other_state);
				}
				// End
				// �����ʱ��ҲҪ�жϣ��ǲ����豸�����һ��
				else if (JJBaseApplication.sop_checkp.mType.toLowerCase()
						.equals("Survey".toLowerCase())) {// �豸��⣬������ģ��
					JJBaseApplication.sop_checkp.mMounseNumber = YTStringHelper
							.replaceBlank(mMounseNumeberEdt.getText()
									.toString());
				} else if (JJBaseApplication.sop_checkp.mType.toLowerCase()
						.equals("Monitor".toLowerCase())) {
					if (JJBaseApplication.g_customer.mTaskType == D2EConfigures.MONITOR_INIT) {
						if (mRoachEdt.getText().toString() == null
								|| mRoachEdt.getText().toString().length() == 0
								|| mMouseEdt.getText().toString() == null
								|| mMouseEdt.getText().toString().length() == 0) {
							((JJBaseApplication) getApplication())
									.showMessage(getResources().getString(
											R.string.please_comlete_data));
							break;
						} else {
							JJBaseApplication.sop_checkp.mRoachNub = YTStringHelper
									.replaceBlank(mRoachEdt.getText()
											.toString());
							JJBaseApplication.sop_checkp.mMouseNub = YTStringHelper
									.replaceBlank(mMouseEdt.getText()
											.toString());
						}
						if (D2EConfigures.G_DEBUG) {
							Log.e("------------",
									" D2EConfigures.sop_checkp.mRoachNub: "
											+ JJBaseApplication.sop_checkp.mRoachNub
											+ " and "
											+ " D2EConfigures.sop_checkp.mMouseNub: "
											+ JJBaseApplication.sop_checkp.mMouseNub);
						}
					} else {
						if (mRoachActiveNubEdt.getText().toString() == null
								|| mRoachActiveNubEdt.getText().toString()
										.length() == 0
								|| mMouseActiveNubEdt.getText().toString() == null
								&& mMouseActiveNubEdt.getText().toString()
										.length() == 0
								|| mRoachAdultsEdt.getText().toString() == null
								|| mRoachAdultsEdt.getText().toString()
										.length() == 0
								|| mRoachNymphsEdt.getText().toString() == null
								|| mRoachNymphsEdt.getText().toString()
										.length() == 0
								|| mMouseAdultsEdt.getText().toString() == null
								|| mMouseAdultsEdt.getText().toString()
										.length() == 0
								|| mMouseNymphsEdt.getText().toString() == null
								|| mMouseNymphsEdt.getText().toString()
										.length() == 0) {
							((JJBaseApplication) getApplication())
									.showMessage(getResources().getString(
											R.string.please_comlete_data));
							break;
						} else {
							JJBaseApplication.sop_checkp.mRoachActive = YTStringHelper
									.replaceBlank(mRoachActiveNubEdt.getText()
											.toString());
							JJBaseApplication.sop_checkp.mMouseActive = YTStringHelper
									.replaceBlank(mMouseActiveNubEdt.getText()
											.toString());

							int sum = 0;
							if (mRoachAdultsEdt.getText().toString() != null
									&& mRoachAdultsEdt.getText().toString()
											.length() > 0) {
								try {
									sum += Integer.parseInt(mRoachAdultsEdt
											.getText().toString());
								} catch (Exception e) {
								}
							}
							if (mRoachNymphsEdt.getText().toString() != null
									&& mRoachNymphsEdt.getText().toString()
											.length() > 0) {
								try {
									sum += Integer.parseInt(mRoachNymphsEdt
											.getText().toString());
								} catch (Exception e) {
								}
							}
							JJBaseApplication.sop_checkp.mRoachTotal = YTStringHelper
									.replaceBlank(String.valueOf(sum));

							sum = 0;
							if (mMouseAdultsEdt.getText().toString() != null
									&& mMouseAdultsEdt.getText().toString()
											.length() > 0) {
								try {
									sum += Integer.parseInt(mMouseAdultsEdt
											.getText().toString());
								} catch (Exception e) {
								}
							}
							if (mMouseNymphsEdt.getText().toString() != null
									&& mMouseNymphsEdt.getText().toString()
											.length() > 0) {
								try {
									sum += Integer.parseInt(mMouseNymphsEdt
											.getText().toString());
								} catch (Exception e) {
								}
							}
							JJBaseApplication.sop_checkp.mMouseTotal = YTStringHelper
									.replaceBlank(String.valueOf(sum));
						}
						if (D2EConfigures.G_DEBUG) {
							Log.e("------------",
									" D2EConfigures.sop_checkp.mRoachActive: "
											+ JJBaseApplication.sop_checkp.mRoachActive
											+ " and "
											+ " D2EConfigures.sop_checkp.mMouseActive: "
											+ JJBaseApplication.sop_checkp.mMouseActive
											+ " and "
											+ " D2EConfigures.sop_checkp.mRoachTotal: "
											+ JJBaseApplication.sop_checkp.mRoachTotal
											+ " and "
											+ " D2EConfigures.sop_checkp.mMouseTotal: "
											+ JJBaseApplication.sop_checkp.mMouseTotal);
						}

					}
				}
				// modify by shawn 2012-9-24 Begin
				// ������ŵ�����Ա�����ж���ҵ���Ƿ������
				JJBaseApplication.sop_checkp.mMemo = YTStringHelper
						.replaceBlank(mMemo.getText().toString());
				JJBaseApplication.sop_checkp.mChecked = true;
				// //End
				// modify by shawn 2012-11-21
				// ������ҵ����Ϣ�����пͻ��б��е�ѡ�������ŵĿͻ�����
				MyCustomerListData myCustomerListData = (MyCustomerListData) ytfileHelper
						.deSerialObject(getResources().getString(
								R.string.customer_list));
				D2ECustomer customer = myCustomerListData
						.getWeekSerialCustomer(JJBaseApplication.g_customer);
				customer.setSelectPoint(JJBaseApplication.selectPoint);
				ytfileHelper.serialObject(
						getResources().getString(R.string.customer_list),
						myCustomerListData);
				// End
				final Intent intent = new Intent(D2ECheckEquipsActivity.this,
						D2ECheckListActivity.class);
				D2ECheckEquipsActivity.this.startActivity(intent);
				finish();
			}
		}
			break;

		}
		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		super.onPrepareOptionsMenu(menu);
		menu.clear();
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.ckpointspage_menu, menu);
		return true;
	}

	@Override
	public void init() {

	}

	@Override
	public void taskSuccess(Object... param) {
		try {
			int values = ((Integer) param[0]).intValue();
			if (values == JJBaseService.BT_SERVICE_INT) {
				String response = (String) param[2];
				int state = ((Integer) param[1]).intValue();
				if (response.toLowerCase().equals(
						SOPBluetoothService.STATE_FAILED.toLowerCase())) {
					((JJBaseApplication) getApplication())
							.showMessage(getResources().getString(
									R.string.bterror));
					dismissProcessDialog();
					return;
				} else {
					mTagNum = response.substring(14, 22);

					if (state == SOPBTCallHelper.READ_TAG) {
						String[] params = new String[] { "FunType",
								"chkSOPPoint", "OrgID",
								JJBaseApplication.user_OrgID, "PointTagNum",
								mTagNum, "TagID",
								JJBaseApplication.sop_checkp.getId() };

						YTStringHelper.array2string(params);

						long id = JJBaseService
								.addMutilpartHttpTask(
										"http://www.ohpest.com/ohpest-pco/webservices/get/data.html",
										params, D2ECheckEquipsActivity.this
												.getClass().getName(),
										D2EConfigures.TASK_GETCHECKBYLOCATION);

						setCurTaskID(id);
						showWaitDialog(getResources().getString(
								R.string.handling_wait));
						return;
					}

					else if (state == SOPBTCallHelper.TAG_CHANGE) {
						te.setText(mTagNum);
					}

				}

			} else if (values == JJBaseService.HTTP_SERVICE_INT) {
				JJTask task = (JJTask) param[1];
				String response = null;
				try {
					InputStream ins = task.getInputStream();
					byte[] bytes = JJNetHelper.readByByte(ins, -1);

					response = new String(bytes, "UTF-8");
					ins.close();
					Log.e("response: >>>>>> ", response);
					if (D2EConfigures.G_DEBUG) {
						Log.e("response: >>>>>> ", response);
					}
					try {
						JSONObject j = new JSONObject(response);
						String err = j.getString("error");
						if (err != null && err.length() > 0) {
							((JJBaseApplication) getApplication())
									.showMessage(err);
							dismissProcessDialog();
							return;
						}
					} catch (Exception ex) {
					}
				} catch (Exception ex) {
				}
				if (response == null) {
					((JJBaseApplication) getApplication())
							.showMessage(getResources().getString(
									R.string.hand_failed_try_again));
					dismissProcessDialog();
					return;
				}
				if (task.getTaskId() == D2EConfigures.TASK_CHANGETAG) {
					if (D2EConfigures.TEST) {
						Log.e("+++++++++++++++++", "xxxxxxxxxxxxxxxxxxxxxxxxxx");
					}
					try {
						JSONObject j = new JSONObject(response);
						String strTitle = j.getString("Title");
						String strTagID = j.getString("TagID");
						if (D2EConfigures.TEST) {
							Log.e("strTitle------------>", strTitle);
							Log.e("strTagID------------>", strTagID);
						}
						if (strTitle != null && strTitle.length() > 0
								&& strTagID != null && strTagID.length() > 0) {
							// add by shawn 2012-9-9 Begin
							// ���³ɹ���ʾ��"�滻��ǩ�ɹ�"
							((JJBaseApplication) getApplication())
									.showMessage(getResources().getString(
											R.string.replace_card_success));
							// End
							if (D2EConfigures.TEST) {
								Log.e("===============",
										"=====================");
							}
							// add by shawn 2012-10-18 Begin
							// ������ҵ�����Ϣ
							JJBaseApplication.selectPoint = JJBaseApplication.sop_checkp;
							D2ECustomer finalmcustomer = null;
							if (ytfileHelper.isFileExist("finallastcust.data")) {
								if (D2EConfigures.TEST) {
									Log.e("===============",
											"=====================");
								}
								finalmcustomer = (D2ECustomer) ytfileHelper
										.deSerialObject("finallastcust.data");
								finalmcustomer
										.setSelectPoint(JJBaseApplication.selectPoint);
							}
							if (ytfileHelper.isFileExist("finallastcust.data")) {
								if (D2EConfigures.TEST) {
									Log.e("===============",
											"=====================");
								}
								finalmcustomer.getSelectPoint().mTitle = strTitle;
								if (D2EConfigures.TEST) {
									Log.e("�ı���ҵ������֣�",
											""
													+ (finalmcustomer
															.getSelectPoint().mTitle));
								}
								finalmcustomer.getSelectPoint().mId = strTagID;
								if (D2EConfigures.TEST) {
									Log.e("�ı���ҵ���ID��",
											""
													+ (finalmcustomer
															.getSelectPoint().mId));
								}
								ytfileHelper.serialObject("finallastcust.data",
										finalmcustomer);
							} else {
								D2ECustomer mcustomer = (D2ECustomer) ytfileHelper
										.deSerialObject("cust.data");
								mcustomer
										.setSelectPoint(JJBaseApplication.selectPoint);
								mcustomer.getSelectPoint().mTitle = strTitle;
								mcustomer.getSelectPoint().mId = strTagID;
								ytfileHelper.serialObject("cust.data",
										mcustomer);
							}
							if (ytfileHelper.isFileExist("finallastcust.data")) {
								finalmcustomer = (D2ECustomer) ytfileHelper
										.deSerialObject("finallastcust.data");
								if (D2EConfigures.TEST) {
									finalmcustomer.printCustomerData();
								}
							}

							JJBaseApplication.selectPoint = JJBaseApplication.sop_checkp;

							JJBaseApplication.sop_checkp.setmTitle(strTitle);
							JJBaseApplication.sop_checkp.setId(strTagID);
							// add by shawn 2012-9-9 Begin
							// ����˵���滻�ɹ��ˣ����ڿ��Ա��汨����Ϣ��
							JJBaseApplication.sop_checkp.mMemo = mMemo
									.getText().toString();
							JJBaseApplication.sop_checkp.mChecked = true;

							if (D2EConfigures.G_DEBUG) {
								Log.e("------------",
										" D2EConfigures.sop_checkp.mMemo: "
												+ JJBaseApplication.sop_checkp.mMemo);
							}

							// �����ж�����������
							if (JJBaseApplication.sop_checkp.mType
									.toLowerCase().equals(
											"OPSite".toLowerCase())) {// ��ҵ��
								JJBaseApplication.sop_checkp.mRateFact = mRateFact
										.getText().toString();
								JJBaseApplication.sop_checkp.mVolumeFact = mVolumeFact
										.getText().toString();

								if (D2EConfigures.G_DEBUG) {
									Log.e("------------",
											JJBaseApplication.sop_checkp.mRateFact
													+ " and "
													+ JJBaseApplication.sop_checkp.mVolumeFact);
								}
							}
							// �����ж��ľӿ�
							else if (JJBaseApplication.sop_checkp.mType
									.toLowerCase().equals("XJK".toLowerCase())) {
							}
							// �����ʱ��ҲҪ�жϣ��ǲ����豸�����һ��
							else if (JJBaseApplication.sop_checkp.mType
									.toLowerCase().equals(
											"Survey".toLowerCase())) {// �豸��⣬������ģ��
								// ���泬����ģ�����ݣ��ύ�����ʱ��ҲҪ�����ж�
								JJBaseApplication.sop_checkp.mMounseNumber = mMounseNumeberEdt
										.getText().toString();

								Log.i("D2EConfigures.sop_checkp.mMounseNumber-------->",
										JJBaseApplication.sop_checkp.mMounseNumber);
							} else if (JJBaseApplication.sop_checkp.mType
									.toLowerCase().equals(
											"Monitor".toLowerCase())) {
								if (JJBaseApplication.g_customer.mTaskType == D2EConfigures.MONITOR_INIT) {
									JJBaseApplication.sop_checkp.mRoachNub = mRoachEdt
											.getText().toString();
									JJBaseApplication.sop_checkp.mMouseNub = mMouseEdt
											.getText().toString();

									if (D2EConfigures.G_DEBUG) {
										Log.e("------------",
												" D2EConfigures.sop_checkp.mRoachNub: "
														+ JJBaseApplication.sop_checkp.mRoachNub
														+ " and "
														+ " D2EConfigures.sop_checkp.mMouseNub: "
														+ JJBaseApplication.sop_checkp.mMouseNub);
									}
								} else {
									JJBaseApplication.sop_checkp.mRoachActive = mRoachActiveNubEdt
											.getText().toString();
									JJBaseApplication.sop_checkp.mMouseActive = mMouseActiveNubEdt
											.getText().toString();

									int sum = 0;
									if (mRoachAdultsEdt.getText().toString() != null
											&& mRoachAdultsEdt.getText()
													.toString().length() > 0) {
										try {
											sum += Integer
													.parseInt(mRoachAdultsEdt
															.getText()
															.toString());
										} catch (Exception e) {
										}
									}
									if (mRoachNymphsEdt.getText().toString() != null
											&& mRoachNymphsEdt.getText()
													.toString().length() > 0) {
										try {
											sum += Integer
													.parseInt(mRoachNymphsEdt
															.getText()
															.toString());
										} catch (Exception e) {
										}
									}
									JJBaseApplication.sop_checkp.mRoachTotal = String
											.valueOf(sum);

									sum = 0;
									if (mMouseAdultsEdt.getText().toString() != null
											&& mMouseAdultsEdt.getText()
													.toString().length() > 0) {
										try {
											sum += Integer
													.parseInt(mMouseAdultsEdt
															.getText()
															.toString());
										} catch (Exception e) {
										}
									}
									if (mMouseNymphsEdt.getText().toString() != null
											&& mMouseNymphsEdt.getText()
													.toString().length() > 0) {
										try {
											sum += Integer
													.parseInt(mMouseNymphsEdt
															.getText()
															.toString());
										} catch (Exception e) {
										}
									}
									JJBaseApplication.sop_checkp.mMouseTotal = String
											.valueOf(sum);
									if (D2EConfigures.TEST) {
										Log.e("------------",
												" D2EConfigures.sop_checkp.mRoachActive: "
														+ JJBaseApplication.sop_checkp.mRoachActive
														+ " and "
														+ " D2EConfigures.sop_checkp.mMouseActive: "
														+ JJBaseApplication.sop_checkp.mMouseActive
														+ " and "
														+ " D2EConfigures.sop_checkp.mRoachTotal: "
														+ JJBaseApplication.sop_checkp.mRoachTotal
														+ " and "
														+ " D2EConfigures.sop_checkp.mMouseTotal: "
														+ JJBaseApplication.sop_checkp.mMouseTotal);
									}
									if (D2EConfigures.G_DEBUG) {
										Log.e("------------",
												" D2EConfigures.sop_checkp.mRoachActive: "
														+ JJBaseApplication.sop_checkp.mRoachActive
														+ " and "
														+ " D2EConfigures.sop_checkp.mMouseActive: "
														+ JJBaseApplication.sop_checkp.mMouseActive
														+ " and "
														+ " D2EConfigures.sop_checkp.mRoachTotal: "
														+ JJBaseApplication.sop_checkp.mRoachTotal
														+ " and "
														+ " D2EConfigures.sop_checkp.mMouseTotal: "
														+ JJBaseApplication.sop_checkp.mMouseTotal);
									}

								}
							}
							// add by shawn 2012-10-22 Begin
							// �滻ҲҪ������Ϣ

							// End
							// add by shawn 2012-10-18 Begin
							// ������ҵ�����Ϣ
							if (D2EConfigures.TEST) {
								Log.e("=================",
										"xxxxxxxxxxxxxxxxxxxxxxxxxx");
							}
							D2ECustomer finalmcustomer2 = null;
							if (ytfileHelper.isFileExist("finallastcust.data")) {
								finalmcustomer2 = (D2ECustomer) ytfileHelper
										.deSerialObject("finallastcust.data");
								if (D2EConfigures.TEST) {
									finalmcustomer2.printCustomerData();
								}
							}
							if (ytfileHelper.isFileExist("finallastcust.data")) {
								finalmcustomer2
										.setSelectPoint(JJBaseApplication.selectPoint);
								finalmcustomer2.isfinalSaveFile = true;
								ytfileHelper.serialObject("finallastcust.data",
										finalmcustomer2);
							} else {
								D2ECustomer mcustomer = (D2ECustomer) ytfileHelper
										.deSerialObject("cust.data");
								mcustomer
										.setSelectPoint(JJBaseApplication.selectPoint);
								ytfileHelper.serialObject("cust.data",
										mcustomer);
								// add by shawn 2012-10-19 Begin
								D2ECustomer finalLastCustomer = mcustomer;
								finalLastCustomer.isfinalSaveFile = true;
								ytfileHelper.serialObject("finallastcust.data",
										finalLastCustomer);
							}
							// ����һ�������ļ�����ͻ�����
							// End
							// End
							final Intent intent = new Intent(
									D2ECheckEquipsActivity.this,
									D2ECheckListActivity.class);
							D2ECheckEquipsActivity.this.startActivity(intent);
							finish();
							// End

						}

					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			}

		} catch (Exception e) {
		}
		dismissProcessDialog();

	}

	@Override
	public void taskFailed(Object... param) {
		dismissProcessDialog();
		int values = ((Integer) param[0]).intValue();
		if (values == JJBaseService.HTTP_SERVICE_INT) {
			((JJBaseApplication) getApplication()).showMessage(getResources()
					.getString(R.string.neterror));
		} else if (values == JJBaseService.BT_SERVICE_INT) {
			int state = ((Integer) param[1]).intValue();
			if (state != SOPBluetoothService.BT_DISCONNECT
					&& state != SOPBluetoothService.BT_CONNECT) {
				((JJBaseApplication) getApplication())
						.showMessage(getResources().getString(R.string.bterror));
			} else if (state == SOPBluetoothService.BT_DISCONNECT) {
				JJBaseApplication.g_btState = false;
			} else if (state == SOPBluetoothService.BT_CONNECT) {
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

}
