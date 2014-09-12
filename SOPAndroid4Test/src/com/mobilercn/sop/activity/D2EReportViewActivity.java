package com.mobilercn.sop.activity;

import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mobilercn.base.BadgeView;
import com.mobilercn.base.JJBaseActivity;
import com.mobilercn.base.JJBaseApplication;
import com.mobilercn.config.D2EConfigures;
import com.mobilercn.sop.R;
import com.mobilercn.sop.data.MyCustomerListData;
import com.mobilercn.sop.data.TagItem;
import com.mobilercn.sop.item.D2ECustomer;
import com.mobilercn.util.YTFileHelper;
import com.mobilercn.widget.YTReportItems;
import com.mobilercn.widget.YTReportModel;
import com.mobilercn.widget.YTReportView;
import android.view.animation.Animation;

public class D2EReportViewActivity extends JJBaseActivity {

	public static final int VIEW_TYPE_TODO = 1;
	public static final int VIEW_TYPE_REPORT = 2;

	YTReportView mReportView;
	private BadgeView mBadgeView;
	ImageButton mButtonRight, mButtonLeft;

	private int mViewType;
	private YTFileHelper ytfileHelper;


	protected void onResume() {
		super.onResume();
		new AnimationTask(this).execute();
	}

	/**
	 * ��һ�첽������������
	 */
	class AnimationTask extends AsyncTask<String, String, String> {
		Activity act;

		AnimationTask(Activity activity) {
			this.act = activity;
		}

		@Override
		protected String doInBackground(String... params) {
			act.runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// add by shawn 2012-10-10 Begin
					// ������������ƶ���ť���ɶ���
					btn_sheshi_pre.setBackgroundResource(R.drawable.left_report);
					AnimationDrawable animationLeft = (AnimationDrawable) btn_sheshi_pre
							.getBackground();
					animationLeft.start();

					// End

					// add by shawn 2012-10-10 Begin
					// ������������ƶ���ť���ɶ���
					btn_sheshi_next.setBackgroundResource(R.drawable.report_right);
					AnimationDrawable animationRight = (AnimationDrawable) btn_sheshi_next
							.getBackground();
					animationRight.start();
					// End
					
					
					
					// add by shawn 2012-10-31 Begin
					// ������������ƶ���ť���ɶ���
					btn_pre_shishi.setBackgroundResource(R.drawable.left_report);
					AnimationDrawable animationLeft1 = (AnimationDrawable) btn_pre_shishi
							.getBackground();
					animationLeft1.start();

					// End

					// add by shawn 2012-10-31 Begin
					// ������������ƶ���ť���ɶ���
					btn_next_shishi.setBackgroundResource(R.drawable.report_right);
					AnimationDrawable animationRight1 = (AnimationDrawable) btn_next_shishi
							.getBackground();
					animationRight1.start();
					// End
					
					
					
					
				}
			});
			return null;
		}

	}

	// add by shawn 2012-10-30
	// �Ѷ౨�����л�����
	// ���ҵ��ؼ�
	/**
	 * ��ʩ�����л���ť
	 */
	private Button btn_sheshi;
	/**
	 * ʵʩ�����л���ť
	 */
	private Button btn_shishi;

	/**
	 * ��ʩ��������
	 */
	private RelativeLayout panel_report_sheshi;
	/**
	 * ʵʩ��������
	 */
	private RelativeLayout panel_report_shishi;

	/**
	 * ��ʩ����
	 */
	private YTReportView reportview_sheshi;
	/**
	 * ��ʩ���������л���ť
	 */
	private ImageButton btn_sheshi_pre;
	/**
	 * ��ʩ���������л���ť
	 */
	private ImageButton btn_sheshi_next;

	/**
	 * ʵʩ����
	 */
	private YTReportView reportview_shishi;
	/**
	 * ʵʩ�����������л���ť
	 */
	private ImageButton btn_pre_shishi;
	/**
	 * ʵʩ���������л���ť
	 */
	private ImageButton btn_next_shishi;

	ArrayList<TagItem> sopItem = null;
	ArrayList<TagItem> monitorItem = null;

	// End
	/**
	 * ��ʼ���л�����ؿؼ�
	 */
	private void initTabView() {
		btn_sheshi = (Button) this.findViewById(R.id.btn_sheshi);
		btn_shishi = (Button) this.findViewById(R.id.btn_shishi);

		panel_report_sheshi = (RelativeLayout) this
				.findViewById(R.id.panel_report_sheshi);
		panel_report_shishi = (RelativeLayout) this
				.findViewById(R.id.panel_report_shishi);

		reportview_sheshi = (YTReportView) this
				.findViewById(R.id.reportview_sheshi);
		reportview_shishi = (YTReportView) this
				.findViewById(R.id.reportview_shishi);

		btn_sheshi_pre = (ImageButton) this.findViewById(R.id.btn_sheshi_pre);
		btn_sheshi_next = (ImageButton) this.findViewById(R.id.btn_sheshi_next);

		btn_pre_shishi = (ImageButton) this.findViewById(R.id.btn_pre_shishi);
		btn_next_shishi = (ImageButton) this.findViewById(R.id.btn_next_shishi);
		
		// ��ʩ������
		sheshi_view_item = new YTReportModel(reportview_sheshi.getPaint(), this);
		// ʵʩ������
		shishi_view_item = new YTReportModel(reportview_shishi.getPaint(), this);
	}

	/**
	 * ��ȡԤ��������Դ
	 */
	private void getReportData() {
		// ����������Ϣ������Ԥ��
		if (mViewType == VIEW_TYPE_TODO) {
			// boolean isnull = true;
			// ���������ͽ���ɸѡ
			sopItem = new ArrayList<TagItem>(5);
			monitorItem = new ArrayList<TagItem>(5);
					// End
			//modify by shawn 2012-11-21 Begin
			//ͨ����ȡ���ر���Ŀͻ�������Ԥ������
			MyCustomerListData myCustomerListData=(MyCustomerListData) ytfileHelper.deSerialObject(getResources().getString(R.string.customer_list));
	        JJBaseApplication.g_customer=myCustomerListData.getWeekSerialCustomer(JJBaseApplication.g_customer);
			//End
			
			for (TagItem sub : JJBaseApplication.g_customer.mPositions) {
				for (TagItem item : sub.mItems) {
					if (!item.mChecked) {
						continue;
					}
					item.setSOPPositionName(sub.getTitle());
					if (item.mType.toLowerCase().equals(
							"Monitor".toLowerCase())) {
						monitorItem.add(item);

					} else if (item.mType.toLowerCase().equals(
							"Survey".toLowerCase())) {
						monitorItem.add(item);
					} else {
						item.setSOPPositionName(sub.getTitle());
						sopItem.add(item);
					}
				}
			}
			// Ԥ��������������
			viewReport();
			
		}else{
			//�������ȡ�ı���չʾ
			viewNetReport();
		}
	}
	/**
	 * ��ȡ������籨��
	 */
	private void viewNetReport(){
		if(JJBaseApplication.g_sopReprot!=null){
		if (JJBaseApplication.g_sopReprot.size() > 0) {
			// isnull=false;
			if (JJBaseApplication.mTasKJSONArray != null
					&& JJBaseApplication.mTasKJSONArray.size() > 0) {
				sheshi_view_item.setHeader(new String[] { 
						getResources().getString(R.string.check_place), 
						getResources().getString(R.string.check_type), 
						getResources().getString(R.string.check_check_point), 
						getResources().getString(R.string.service_state),
						getResources().getString(R.string.check_drug),
						getResources().getString(R.string.check_equip),
						getResources().getString(R.string.check_scale), 
						getResources().getString(R.string.check_dosage), 
						getResources().getString(R.string.check_facility_type), 
						getResources().getString(R.string.check_facility_num), 
						getResources().getString(R.string.check_fun_facility),
						getResources().getString(R.string.check_result),
						getResources().getString(R.string.check_memo) 
						});
				
				ArrayList<TagItem> mList = null;
				TagItem s = null;
				JSONArray SOPReport = null;
				for (int i = 0; i < JJBaseApplication.mTasKJSONArray.size(); i++) {
					SOPReport = JJBaseApplication.mTasKJSONArray
							.get("Task" + i);
					try {
						mList = reSolveJSONArray(SOPReport);
					} catch (JSONException e) {
						e.printStackTrace();
					}

					for (int k = 0; k < mList.size(); k++) {
						s = mList.get(k);
						if (null != s) {
							// add by shawn 2012-9-8 Begin

							// �ص㲻Ϊ�ղ�չʾ����
							if (!s.getTitle().equalsIgnoreCase("null")
									&& !s.getSOPPositionName()
											.equalsIgnoreCase("null")) {
								if (s.mType.toLowerCase().equals(
										"OPSite".toLowerCase())) {
									sheshi_view_item.addItem(new String[] {
											s.getSOPPositionName(), "ʵʩ",
											s.getTitle(), "-", s.mSuppliesName,
											s.mInstrumentName, s.mRateFact,
											s.mVolumeFact, "-", "-", "-", "-",
											s.mMemo });
								} else {
									String status = "-";
									String tmp = s.mType;

									if (s.mType.toLowerCase().equals(
											"Baitstation".toLowerCase())) {
										tmp = getResources()
												.getString(
														R.string.check_equip_mouse_bait_station);// 0
										try {
											String[] tmpa = getResources()
													.getStringArray(
															R.array.bait);
											status = tmpa[Integer
													.parseInt(s.mStateInt)];
										} catch (Exception e) {
										}
									} else if (s.mType.toLowerCase().equals(
											"Glueboard".toLowerCase())) {
										try {
											String[] tmpa = getResources()
													.getStringArray(
															R.array.sticky_mouse_board);
											status = tmpa[Integer
													.parseInt(s.mStateInt)];
										} catch (Exception e) {
										}
										tmp = getResources()
												.getString(
														R.string.check_equip_mouse_glue_board);// 1
									} else if (s.mType.toLowerCase().equals(
											"RodentTrap".toLowerCase())) {
										try {
											String[] tmpa = getResources()
													.getStringArray(
															R.array.mousetrap);
											status = tmpa[Integer
													.parseInt(s.mStateInt)];
										} catch (Exception e) {
										}
										tmp = getResources()
												.getString(
														R.string.check_equip_rodent_trap);// 2
									} else if (s.mType.toLowerCase().equals(
											"MouseTrap".toLowerCase())) {
										try {
											String[] tmpa = getResources()
													.getStringArray(
															R.array.mouse_trap);
											status = tmpa[Integer
													.parseInt(s.mStateInt)];
										} catch (Exception e) {
										}
										tmp = getResources()
												.getString(
														R.string.check_equip_mouse_trap);// 3
									} else if (s.mType.toLowerCase().equals(
											"Flycatch".toLowerCase())) {
										try {
											String[] tmpa = getResources()
													.getStringArray(
															R.array.pests);
											status = tmpa[Integer
													.parseInt(s.mStateInt)];
										} catch (Exception e) {
										}
										tmp = getResources().getString(
												R.string.check_equip_fly_catch);// 4
									} else if (s.mType.toLowerCase().equals(
											"InsectCatch".toLowerCase())) {
										try {
											String[] tmpa = getResources()
													.getStringArray(
															R.array.InsectCatch);
											status = tmpa[Integer
													.parseInt(s.mStateInt)];
										} catch (Exception e) {
										}
										tmp = getResources()
												.getString(
														R.string.check_equip_insect_catch);// 5
									} else if (s.mType.toLowerCase().equals(
											"BugCatcher".toLowerCase())) {
										try {
											String[] tmpa = getResources()
													.getStringArray(
															R.array.bug_catch);
											status = tmpa[Integer
													.parseInt(s.mStateInt)];
										} catch (Exception e) {
										}
										tmp = getResources()
												.getString(
														R.string.check_equip_bug_catcher);// 6
									}

									else if (s.mType.toLowerCase().equals(
											"BugGluePlate".toLowerCase())) {
										try {
											String[] tmpa = getResources()
													.getStringArray(
															R.array.bugglueplate);
											status = tmpa[Integer
													.parseInt(s.mStateInt)];
										} catch (Exception e) {
										}
										tmp = getResources()
												.getString(
														R.string.check_equip_bug_glue_plate);// 7
									}

									else if (s.mType.toLowerCase().equals(
											"XJK".toLowerCase())) {
										try {
											String[] tmpa = getResources()
													.getStringArray(
															R.array.xinjukang);
											status = tmpa[Integer
													.parseInt(s.mStateInt)];
										} catch (Exception e) {
										}
										tmp = getResources().getString(
												R.string.check_equip_xjk);// 10
									} else if (s.mType.toLowerCase().equals(
											"FacilitiesOther".toLowerCase())) {
										try {
											String[] tmpa = getResources()
													.getStringArray(
															R.array.facilities_other);
											status = tmpa[Integer
													.parseInt(s.mStateInt)];
										} catch (Exception e) {
										}
										if (!s.getmEquipName()
												.equalsIgnoreCase("null")) {// 12
											tmp = s.getmEquipName();
										} else {
											tmp = "-";
										}

									} else if (s.mType.toLowerCase().equals(
											"OPSiteOther".toLowerCase())) {
										try {
											String[] tmpa = getResources()
													.getStringArray(
															R.array.opsite_other);
											status = tmpa[Integer
													.parseInt(s.mStateInt)];
										} catch (Exception e) {
										}
										if (!s.getmEquipName()
												.equalsIgnoreCase("null")) {// 13
											tmp = s.getmEquipName();
										} else {
											tmp = "-";
										}
									} else {
										tmp = "-";
									}
									if (D2EConfigures.TEST) {
										Log.e("s.mType------->", "" + s.mType);
										Log.e("s.getSOPPositionName()-------->",
												"" + s.getSOPPositionName());
										Log.e("tmp============>", "" + tmp);
										Log.e("s.getTitle()--------------->",
												"" + s.getTitle());
										Log.e("status==========>", "" + status);
										Log.e("s.mMemo----------->", ""
												+ s.mMemo);
									}
									sheshi_view_item.addItem(new String[] {
											s.getSOPPositionName(), tmp,
											s.getTitle(), status, "-", "-",
											"-", "-", "-", "-", "-", "-",
											s.mMemo });
								}
							}
						}
						// End
					}
				}
			}
		}
		}

		// monitor
		if(JJBaseApplication.g_monitorReprot!=null){
		if (JJBaseApplication.g_monitorReprot.size() > 0) {
			if (JJBaseApplication.mMonitorTaskJSONArray != null
					&& JJBaseApplication.mMonitorTaskJSONArray.size() > 0) {

				shishi_view_item.setHeader(new String[] { 
						getResources().getString(R.string.check_place), 
						getResources().getString(R.string.check_type), 
						getResources().getString(R.string.check_check_point), 
						getResources().getString(R.string.service_state),
						getResources().getString(R.string.check_drug),
						getResources().getString(R.string.check_equip),
						getResources().getString(R.string.check_scale), 
						getResources().getString(R.string.check_dosage), 
						getResources().getString(R.string.check_facility_type), 
						getResources().getString(R.string.check_facility_num), 
						getResources().getString(R.string.check_fun_facility),
						getResources().getString(R.string.check_result),
						getResources().getString(R.string.check_memo) 
						});
				ArrayList<TagItem> mList = null;
				TagItem s = null;
				JSONArray monitroReport = null;
				for (int i = 0; i < JJBaseApplication.mMonitorTaskJSONArray
						.size(); i++) {
					monitroReport = JJBaseApplication.mMonitorTaskJSONArray
							.get("Task" + i);
					try {
						mList = resolveMonitorJSONArray(monitroReport);
					} catch (JSONException e) {
						e.printStackTrace();
					}

					for (int j = 0; j < mList.size(); j++) {
						s = mList.get(j);
						if (null != s) {
							// add by shawn 2012-9-8 Begin
							// �ص㲻Ϊ�ղ�չʾ����
							if (!s.getTitle().equalsIgnoreCase("null")
									&& !s.getSOPPositionName()
											.equalsIgnoreCase("null")) {
								if (s.mMouseActive != null
										&& s.mMouseActive.length() > 0) {
									shishi_view_item.addItem(new String[] {
											s.getSOPPositionName(), getResources().getString(R.string.survey_check),
											s.getTitle(), "-", "-", "-", "-",
											"-", getResources().getString(R.string.mouse_survey), "-", s.mMouseActive,
											s.mMouseTotal, s.mMemo, " " });
								}
								if (s.mRoachActive != null
										&& s.mRoachActive.length() > 0) {
									shishi_view_item.addItem(new String[] {
											s.getSOPPositionName(), getResources().getString(R.string.survey_check),
											s.getTitle(), "-", "-", "-", "-",
											"-", getResources().getString(R.string.cockroach_survey), "-", s.mRoachActive,
											s.mRoachTotal, s.mMemo, " " });
								}
								// �ж���������Ƿ��У�������ʾ
								if (s.mType.equalsIgnoreCase("Survey")) {
									shishi_view_item.addItem(new String[] {
											s.getSOPPositionName(), getResources().getString(R.string.survey_check),
											s.getTitle(), "-", "-", "-", "-",
											"-", getResources().getString(R.string.mouse_sense), "-", "-",
											s.mMounseNumber, s.mMemo, " " });
								}
							}
						}
						// End
					}
				}
			}
		}
		
		}
	}

	/**
	 * ������������
	 */
	private void viewReport() {
		
		if(sopItem!=null){
		if (sopItem.size() > 0) {
			sheshi_view_item.setHeader(new String[] { 
					getResources().getString(R.string.check_place), 
					getResources().getString(R.string.check_type), 
					getResources().getString(R.string.check_check_point), 
					getResources().getString(R.string.service_state),
					getResources().getString(R.string.check_drug),
					getResources().getString(R.string.check_equip),
					getResources().getString(R.string.check_scale), 
					getResources().getString(R.string.check_dosage), 
					getResources().getString(R.string.check_facility_type), 
					getResources().getString(R.string.check_facility_num), 
					getResources().getString(R.string.check_fun_facility),
					getResources().getString(R.string.check_result),
					getResources().getString(R.string.check_memo) 
					});

			for (TagItem s : sopItem) {
				// add by shawn 2012-9-8 Begin
				// �ص㲻Ϊ�ղ���ʾ����
				if (!s.getTitle().equalsIgnoreCase("null")
						&& !s.getSOPPositionName().equalsIgnoreCase("null")) {
					if (s.mType.toLowerCase().equals("OPSite".toLowerCase())) {
						// modify by kris 2012-09-09
						sheshi_view_item.addItem(new String[] {
								s.getSOPPositionName(), "ʵʩ", s.getTitle(),
								"-", s.mSuppliesName, s.mInstrumentName,
								s.mRateFact, s.mVolumeFact, "-", "-", "-", "-",
								s.mMemo });
					} else {
						/**
						 * �豸����
						 */
						String tmp = s.mType;

						if (s.mType.toLowerCase().equals(
								"Baitstation".toLowerCase())) {
							tmp = getResources().getString(
									R.string.check_equip_mouse_bait_station);// 0
						} else if (s.mType.toLowerCase().equals(
								"Glueboard".toLowerCase())) {
							tmp = getResources().getString(
									R.string.check_equip_mouse_glue_board);// 1
						} else if (s.mType.toLowerCase().equals(
								"RodentTrap".toLowerCase())) {
							tmp = getResources().getString(
									R.string.check_equip_rodent_trap);// 2
						} else if (s.mType.toLowerCase().equals(
								"MouseTrap".toLowerCase())) {
							tmp = getResources().getString(
									R.string.check_equip_mouse_trap);// 3
						} else if (s.mType.toLowerCase().equals(
								"Flycatch".toLowerCase())) {
							tmp = getResources().getString(
									R.string.check_equip_fly_catch);// 4
						} else if (s.mType.toLowerCase().equals(
								"InsectCatch".toLowerCase())) {
							tmp = getResources().getString(
									R.string.check_equip_insect_catch);// 5
						} else if (s.mType.toLowerCase().equals(
								"BugCatcher".toLowerCase())) {
							tmp = getResources().getString(
									R.string.check_equip_bug_catcher);// 6
						} else if (s.mType.toLowerCase().equals(
								"BugGluePlate".toLowerCase())) {
							tmp = getResources().getString(
									R.string.check_equip_bug_glue_plate);// 7
						} else if (s.mType.toLowerCase().equals(
								"OPSite".toLowerCase())) {
							tmp = getResources().getString(
									R.string.check_equip_opsite);// 8
						} else if (s.mType.toLowerCase().equals(
								"XJK".toLowerCase())) {
							tmp = getResources().getString(
									R.string.check_equip_xjk);// 10
						}
						// add by shawn 2012-10-15 Begin
						// �����ж�������ʩ������ʵʩ����ҵ����Ϊ��վ��ȡ����������

						else if (s.mType.toLowerCase().equals(
								"FacilitiesOther".toLowerCase())) {
							tmp = s.getmEquipName();
						} else if (s.mType.toLowerCase().equals(
								"OPSiteOther".toLowerCase())) {
							tmp = s.getmEquipName();
						}
						// End
						sheshi_view_item.addItem(new String[] {
								s.getSOPPositionName(), tmp, s.getTitle(),
								s.mState, "-", "-", "-", "-", "-", "-", "-",
								"-", s.mMemo });
					}
				}
				// End
			}
		}
		}

		// monitor
		if(monitorItem!=null){
		if (monitorItem.size() > 0) {
			shishi_view_item.setHeader(new String[] { 
					getResources().getString(R.string.check_place), 
					getResources().getString(R.string.check_type), 
					getResources().getString(R.string.check_check_point), 
					getResources().getString(R.string.service_state),
					getResources().getString(R.string.check_drug),
					getResources().getString(R.string.check_equip),
					getResources().getString(R.string.check_scale), 
					getResources().getString(R.string.check_dosage), 
					getResources().getString(R.string.check_facility_type), 
					getResources().getString(R.string.check_facility_num), 
					getResources().getString(R.string.check_fun_facility),
					getResources().getString(R.string.check_result),
					getResources().getString(R.string.check_memo) 
					});
			for (TagItem s : monitorItem) {
				// add by shawn 2012-9-8 Begin
				// �ص㲻Ϊ�ձ����չʾ
				if (!s.getTitle().equalsIgnoreCase("null")
						&& !s.getSOPPositionName().equalsIgnoreCase("null")) {
					if (JJBaseApplication.g_customer.mTaskType == D2EConfigures.MONITOR_INIT) {// ����
						if (s.mMouseNub != null && s.mMouseNub.length() > 0) {
							shishi_view_item.addItem(new String[] {
									s.getSOPPositionName(), getResources().getString(R.string.survey_deploy),
									s.getTitle(), "-", "-", "-", "-", "-",
									getResources().getString(R.string.mouse_survey), s.mMouseNub, "-", "-", s.mMemo,
									"  " });
						}
						if (s.mRoachNub != null && s.mRoachNub.length() > 0) {
							shishi_view_item.addItem(new String[] {
									s.getSOPPositionName(), getResources().getString(R.string.survey_deploy),
									s.getTitle(), "-", "-", "-", "-", "-",
									getResources().getString(R.string.cockroach_survey), s.mRoachNub, "-", "-", s.mMemo,
									"  " });
						}
					} else { // ���
						if (s.mMouseActive != null
								&& s.mMouseActive.length() > 0) {
							shishi_view_item.addItem(new String[] {
									s.getSOPPositionName(), getResources().getString(R.string.survey_check),
									s.getTitle(), "-", "-", "-", "-", "-",
									getResources().getString(R.string.mouse_survey), "-", s.mMouseActive, s.mMouseTotal,
									s.mMemo, "  " });
						}
						if (s.mRoachActive != null
								&& s.mRoachActive.length() > 0) {
							shishi_view_item
									.addItem(new String[] {

									s.getSOPPositionName(), getResources().getString(R.string.survey_check),
											s.getTitle(),

											"-", "-", "-", "-", "-", getResources().getString(R.string.cockroach_survey),
											"-", s.mRoachActive, s.mRoachTotal,
											s.mMemo, " " });
						}

						// �ж���������Ƿ��У�������ʾ
						if (s.mType.equalsIgnoreCase("Survey")) {
							shishi_view_item.addItem(new String[] {
									s.getSOPPositionName(), getResources().getString(R.string.survey_check),
									s.getTitle(), "-", "-", "-", "-", "-",
									getResources().getString(R.string.mouse_sense), "-", "-", s.mMounseNumber, s.mMemo,
									" " });
						}
					}
				}
			}
			// End
		}
		}

	}
	
	
	/**
	 * ��ʩ������
	 */
	YTReportModel sheshi_view_item;
	/**
	 * ��ʩ������
	 */
	YTReportModel shishi_view_item;
	
	/**
	 * �����ʾ����
	 */
	private void finalViewReport(){
		//modify by shawn 2012-11-21 Begin 
		//չʾ�����ʱ��Ҫ�����ǲ���Ԥ��
		//End
		reportview_sheshi.setModel(sheshi_view_item);
		reportview_shishi.setModel(shishi_view_item);
	
		
	}
	
	/**
	 * Ϊ�����л���ť���ü�����
	 */
	private void setUpTabBtnListener(){
		btn_sheshi.setOnClickListener(new TabSheshiListener());
		btn_shishi.setOnClickListener(new TabShishiListener());
		
	}
	
	/**
	 * ��ʩ�л�������
	 * @author ShawnXiao
	 *
	 */
	private class TabSheshiListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			
			btn_sheshi.setBackgroundResource(R.drawable.oper_click);
			btn_shishi.setBackgroundResource(R.drawable.moni_no_clik);
			
			
			panel_report_sheshi.setVisibility(View.VISIBLE);
			panel_report_shishi.setVisibility(View.GONE);
			
			
			reportview_sheshi.setModel(sheshi_view_item);
			
		}
		
	}
	/**
	 * ʵʩ�л�������
	 * @author ShawnXiao
	 *
	 */
	private class TabShishiListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			
			btn_shishi.setBackgroundResource(R.drawable.moni_clik);
			btn_sheshi.setBackgroundResource(R.drawable.oper_no_click);
			
			
			panel_report_shishi.setVisibility(View.VISIBLE);
			panel_report_sheshi.setVisibility(View.GONE);
			
			
			reportview_shishi.setModel(shishi_view_item);
			
		}
		
	}
	
	/**
	 * ��ʼ�������л���ť״̬
	 */
	private void initReportChangeButState(){
		btn_sheshi_pre.setVisibility(View.INVISIBLE);
		if(sheshi_view_item.getRows()>1){
			btn_sheshi_next.setVisibility(View.VISIBLE);
		}else{
			btn_sheshi_next.setVisibility(View.INVISIBLE);
		}
		
		btn_pre_shishi.setVisibility(View.INVISIBLE);
		if(shishi_view_item.getRows()>1){
			btn_next_shishi.setVisibility(View.VISIBLE);
		}else{
			btn_next_shishi.setVisibility(View.INVISIBLE);
		}
		
	}
	
	/**
	 * Ϊ�����л���ť���ü�����
	 */
	private void setUpReportChangeListener(){
		btn_sheshi_pre.setOnClickListener(new ReportSheshiPreListener());
		btn_sheshi_next.setOnClickListener(new ReportSheshiNextListener());
		
		btn_pre_shishi.setOnClickListener(new ReportShishiPreListener());
		btn_next_shishi.setOnClickListener(new ReportShishiNextListener());
	}
	
	
	/**
	 * ʵʩ���������л�������
	 * @author ShawnXiao
	 *
	 */
	private class ReportShishiNextListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			int tmp = reportview_shishi.scrollLeft(true);
			 if (tmp >= shishi_view_item.getRows() - 1) {
				 btn_next_shishi.setVisibility(View.INVISIBLE);
			 } else {
				 btn_next_shishi.setVisibility(View.VISIBLE);
			 }
			 btn_pre_shishi.setVisibility(View.VISIBLE);
		}
//		s
	}
	
	/**
	 * ʵʩ���������л�������
	 * @author ShawnXiao
	 *
	 */
	private class ReportShishiPreListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			 int tmp = reportview_shishi.scrollLeft(false);
			 if (tmp <= 0) {
				 btn_pre_shishi.setVisibility(View.INVISIBLE);
			 } else {
				 btn_pre_shishi.setVisibility(View.VISIBLE);
			 }
			 btn_next_shishi.setVisibility(View.VISIBLE);
		}
		
	}
	
	/**
	 * ��ʩ���������л�������
	 * @author ShawnXiao
	 *
	 */
	private class ReportSheshiPreListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			 int tmp = reportview_sheshi.scrollLeft(false);
			 if (tmp <= 0) {
				 btn_sheshi_pre.setVisibility(View.INVISIBLE);
			 } else {
				 btn_sheshi_pre.setVisibility(View.VISIBLE);
			 }
			 btn_sheshi_next.setVisibility(View.VISIBLE);
		}
		
	}
	/**
	 * ��ʩ���������л�������
	 * @author ShawnXiao
	 *
	 */
	private class ReportSheshiNextListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			int tmp = reportview_sheshi.scrollLeft(true);
			 if (tmp >= sheshi_view_item.getRows() - 1) {
				 btn_sheshi_next.setVisibility(View.INVISIBLE);
			 } else {
				 btn_sheshi_next.setVisibility(View.VISIBLE);
			 }
			 btn_sheshi_pre.setVisibility(View.VISIBLE);

		}
		
	}
	
	

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.d2e_viewreport_page);
		Window window = getWindow();
		window.setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.d2e_titlebar);

		TextView tv = (TextView) findViewById(R.id.title);
		tv.setText(getResources().getString(R.string.menu_report));

		mBadgeView = (BadgeView) findViewById(R.id.countTask);
		mBadgeView.setBadgePosition(BadgeView.POSITION_CENTER);
		mBadgeView.setBackgroundResource(R.drawable.badge_ifaux);
		mBadgeView.setVisibility(View.GONE);

		if (JJBaseApplication.user_CountTask != null
				&& JJBaseApplication.user_CountTask.length() > 0) {
			mBadgeView.setText(JJBaseApplication.user_CountTask);
		}

		mReportView = (YTReportView) findViewById(R.id.reportview);

		try {
			Intent intent = getIntent();
			mViewType = intent.getIntExtra("type", 1);
		} catch (Exception ex) {
		}
		ytfileHelper = YTFileHelper.getInstance();
		ytfileHelper.setContext(getApplicationContext());

		// add by shawn 20120-10-30 Begin
		// ��ʼ���л�����ؿؼ�
		initTabView();
		// End

		// ��ȡԤ��������Դ
		getReportData();

		//ΪTab��ť���ü�����
		setUpTabBtnListener();
		//��ʼ�������л���ť״̬
		initReportChangeButState();
		//Ϊ�����л���ť���ü�����
		setUpReportChangeListener();
		
		//���չʾ����
		finalViewReport();

	}

	// ����SOP����
	public ArrayList<TagItem> reSolveJSONArray(JSONArray sopReport)
			throws JSONException {
		ArrayList<TagItem> itemsList = new ArrayList<TagItem>();
		TagItem items = null;
		JSONObject jsop = null;
		for (int j = 0; j < sopReport.length(); j++) {

			jsop = new JSONObject(sopReport.getString(j));
			items = new TagItem(jsop.getString("SOPPointName"),
					jsop.getString("SOPPointID"));

			if (jsop.has("PointTypeName")) {
				if (jsop.getString("PointTypeName") != null
						&& jsop.getString("PointTypeName").length() > 0) {
					items.setmEquipName(jsop.getString("PointTypeName"));
				} else {
					items.setmEquipName(jsop.getString("-"));
				}
			}
			items.mMemo = jsop.getString("Memo");
			items.mType = jsop.getString("SOPPointType");
			items.mStateInt = jsop.getString("State");
			// ������Id��������,���item�������һ���ֶ�TaskID
			items.mTaskID = jsop.getString("TaskID");
			// ������Id�ŵ�һ��������
			items.mItemTaskId.put("taskId", items.mTaskID);
			// ��λ�ñ�ǩ������Ҳ�����������Ա��ȡ
			items.setSOPPositionName(jsop.getString("SOPPositionName"));
			if (jsop.has("Instrument")) {
				if (!jsop.getString("Instrument").equalsIgnoreCase("null")) {
					items.mInstrumentName = jsop.getString("Instrument");
				} else {
					items.mInstrumentName = "-";
				}
			} else {
				items.mInstrumentName = "-";
			}
			if (jsop.has("Supplies")) {
				if (!jsop.getString("Supplies").equalsIgnoreCase("null")) {
					items.mSuppliesName = jsop.getString("Supplies");
				} else {
					items.mSuppliesName = "-";
				}
			} else {
				items.mSuppliesName = "-";
			}
			if (jsop.has("RealRate")) {
				if (!jsop.getString("RealRate").equalsIgnoreCase("null")) {
					items.mRateFact = jsop.getString("RealRate");
				} else {
					items.mRateFact = "-";
				}
			} else {
				items.mRateFact = "-";
			}
			if (jsop.has("RealVolume")) {
				if (!jsop.getString("RealVolume").equalsIgnoreCase("null")) {
					items.mVolumeFact = jsop.getString("RealVolume");
				} else {
					items.mVolumeFact = "-";
				}
			} else {
				items.mVolumeFact = "-";
			}
			itemsList.add(items);
		}

		return itemsList;
	}

	// ����Monitor����
	public ArrayList<TagItem> resolveMonitorJSONArray(JSONArray monitorReport)
			throws JSONException {
		ArrayList<TagItem> itemsList = new ArrayList<TagItem>();
		TagItem items = null;
		for (int k = 0; k < monitorReport.length(); k++) {
			JSONObject jsop = new JSONObject(monitorReport.getString(k));
			items = new TagItem(jsop.getString("SOPPointName"),
					jsop.getString("SOPPointID"));
			items.mMemo = jsop.getString("Memo");
			items.mType = jsop.getString("SOPPointType");
			items.setSOPPositionName(jsop.getString("SOPPositionName"));
			// �����������������ȡ
			if (jsop.has("Results")) {
				items.mMounseNumber = jsop.getString("Results");
			}
			if (jsop.has("TaskID")) {
				items.mTaskID = jsop.getString("TaskID");
			} else if (jsop.has("TaskID1")) {
				items.mTaskID = jsop.getString("TaskID1");
			}
			String mytype = null;
			try {
				mytype = jsop.getString("Type");
			} catch (Exception e) {
			}

			if (mytype != null && mytype.equals("1")) {
				try {
					items.mMouseInit = jsop.getString("Facilities");
				} catch (Exception e) {
				}

				try {
					items.mMouseActive = jsop.getString("EffFacilities");
				} catch (Exception e) {
				}

				try {
					items.mMouseTotal = jsop.getString("Results");
				} catch (Exception e) {
				}

			} else if (mytype != null && mytype.equals("2")) {
				try {
					items.mRoachInit = jsop.getString("Facilities");
				} catch (Exception e) {
				}

				try {
					items.mRoachActive = jsop.getString("EffFacilities");
				} catch (Exception e) {
				}

				try {
					items.mRoachTotal = jsop.getString("Results");
				} catch (Exception e) {
				}
			}
			itemsList.add(items);
		}
		return itemsList;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		super.onPrepareOptionsMenu(menu);
		menu.clear();
		// Inflate the currently selected menu XML resource.
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.reportview_menu, menu);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {

		case R.id.menu_back: {
			finish();
		}
			break;

		}
		return true;
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

	}

	@Override
	public void taskFailed(Object... param) {

	}

	@Override
	public void taskProcessing(Object... param) {

	}

}
