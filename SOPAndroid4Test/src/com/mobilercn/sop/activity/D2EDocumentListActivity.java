package com.mobilercn.sop.activity;

import java.io.InputStream;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
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
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.mobilercn.base.BadgeView;
import com.mobilercn.base.JJBaseActivity;
import com.mobilercn.base.JJBaseApplication;
import com.mobilercn.base.JJBaseService;
import com.mobilercn.config.D2EConfigures;
import com.mobilercn.sop.R;
import com.mobilercn.sop.bt.SOPBluetoothService;
import com.mobilercn.sop.item.D2EDocumentItem;
import com.mobilercn.widget.PullToRefreshListView;
import com.mobilercn.widget.PullToRefreshListView.OnRefreshListener;

public class D2EDocumentListActivity extends JJBaseActivity {

	public class TextItem {
		private boolean mState = false;// 0:new 1:old
		private String mTitle = "";
		private String mContent = "";
		private String mID = "";
		private String mDate = "";
		private String mSize = "";

		public String mType = "";

		public TextItem(String id, String title, String content) {
			mTitle = title;
			mContent = content;
			mID = id;
		}

		public void setSize(String size) {
			mSize = size;
		}

		public String getSize() {
			return mSize;
		}

		public String getDate() {
			return mDate;
		}

		public String getTitle() {
			return mTitle;
		}

		public String getContent() {
			return mContent;
		}

		public boolean isRead() {
			return mState;
		}

		public void setRead(boolean read) {
			mState = read;
		}

		public void setRead(String str) {
			if (str != null && str.length() > 0) {
				setRead(true);
			} else {
				setRead(false);
			}
		}

		public void setDate(String date) {
			mDate = date;
		}

		public void setID(String id) {
			mID = id;
		}

		public String getID() {
			return mID;
		}
	}

	private class D2ETxtAdapter extends BaseAdapter {

		private Context mContext;
		private ArrayList<TextItem> mMessages;

		public D2ETxtAdapter(Context context) {
			mContext = context;
			mMessages = new ArrayList<TextItem>();
		}

		public void addObject(String id, String title, String content) {
			mMessages.add(new TextItem(id, title, content));
		}

		public void addObject(TextItem item) {
			mMessages.add(item);
		}

		@Override
		public int getCount() {
			return mMessages.size();
		}

		@Override
		public Object getItem(int position) {
			if (position < 0 || position > getCount()) {
				return null;
			}
			return mMessages.get(position);
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;

			if (convertView == null) {
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.d2e_txt_item, parent, false);
				holder = new ViewHolder();
				holder.textView = (TextView) convertView
						.findViewById(R.id.txt_stateView);
				holder.dateView = (TextView) convertView
						.findViewById(R.id.txt_dateLabel);
				holder.sizeView = (TextView) convertView
						.findViewById(R.id.txt_sizeLabel);
				holder.titleView = (TextView) convertView
						.findViewById(R.id.txt_titleLabel);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			TextItem item = (TextItem) mMessages.get(position);
			holder.dateView.setText(mContext.getResources().getString(
					R.string.select_date)
					+ item.mDate);
			holder.sizeView.setText(mContext.getResources().getString(
					R.string.select_type)
					+ item.mSize);
			holder.titleView.setText(item.mTitle);
			if (!item.mState) {
				holder.textView.setText(mContext.getResources().getString(
						R.string.select_browser));
				Drawable draw = mContext.getResources().getDrawable(
						R.drawable.read_icon);
				holder.textView.setCompoundDrawablesWithIntrinsicBounds(null,
						draw, null, null);
			} else {
				Drawable draw = getResources()
						.getDrawable(R.drawable.read_icon);
				holder.textView.setCompoundDrawablesWithIntrinsicBounds(null,
						draw, null, null);
				holder.textView.setText(mContext.getResources().getString(
						R.string.select_browser));
			}

			return convertView;
		}

		class ViewHolder {
			ImageView imageView;
			TextView textView;
			TextView sizeView;
			TextView dateView;
			TextView titleView;
		}

	}

	public static String EXTRA_DEVICE_ADDRESS = "device_address";
	public static String EXTRA_READER_ID = "reader_id";

	private BadgeView mBadgeView;
	private D2ETxtAdapter mListAdapter;

	private AdapterView.OnItemClickListener mListOnItemClick = new AdapterView.OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {

			TextItem item = (TextItem) mListAdapter.getItem(arg2);
			if (item != null) {
				LayoutInflater factory = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
				;// LayoutInflater.from(this);
				final View layout = factory.inflate(R.layout.d2e_read_msg_view,
						null);

				TextView txt = (TextView) layout
						.findViewById(R.id.msgcontentText);
				txt.setText(item.getContent());

				final PopupWindow pop = new PopupWindow(layout,
						LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT,
						true);// 鑾峰彇PopupWindow瀵硅薄骞惰缃獥浣撶殑澶у皬
				pop.setAnimationStyle(R.style.PopupAnimation);
				ColorDrawable dw = new ColorDrawable(-00000);
				pop.setBackgroundDrawable(dw);
				pop.showAtLocation(layout, Gravity.CENTER | Gravity.CENTER, 0,
						0);
				pop.update();

				Button btn = (Button) layout
						.findViewById(R.id.read_msg_btn_cancel);
				btn.setOnClickListener(new OnClickListener() {

					public void onClick(View v) {
						pop.dismiss();
					}

				});

			}

		}

	};

	ListView mList;

	protected void onResume() {
		super.onResume();
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.d2e_help_page);
		Window window = getWindow();
		window.setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.d2e_titlebar);

		TextView tv = (TextView) findViewById(R.id.title);
		Intent intent = getIntent();
		String title = intent.getStringExtra("title");
		tv.setText(title);

		mBadgeView = (BadgeView) findViewById(R.id.countTask);
		mBadgeView.setBadgePosition(BadgeView.POSITION_CENTER);
		mBadgeView.setBackgroundResource(R.drawable.badge_ifaux);
		mBadgeView.setVisibility(View.GONE);
		if (JJBaseApplication.user_CountTask != null
				&& JJBaseApplication.user_CountTask.length() > 0) {
			mBadgeView.setText(JJBaseApplication.user_CountTask);
		}

		mList = (ListView) findViewById(R.id.d2e_msg_list);

		mListAdapter = new D2ETxtAdapter(this.getApplicationContext());
		mList.setAdapter(mListAdapter);
		mList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		mList.setOnItemClickListener(mListOnItemClick);
		mList.setMinimumHeight(50);

		if (D2EConfigures.G_USEVIRCALDEVICE) {
			mListAdapter.addObject("1", "椁愰ギ琛屼笟濡備綍鐏紶", "椁愰ギ琛屼笟濡備綍鐏紶");
			mListAdapter.notifyDataSetChanged();
		} else {
			for (D2EDocumentItem item : JJBaseApplication.g_selectedDocument.mItems) {
				TextItem ob = new TextItem(item.mID, item.mTitle, item.mMemo);
				ob.mDate = item.mTime;
				ob.mSize = item.mType;
				mListAdapter.addObject(ob);
				mListAdapter.notifyDataSetChanged();
			}
		}
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
	public boolean onPrepareOptionsMenu(Menu menu) {
		super.onPrepareOptionsMenu(menu);
		menu.clear();
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.back_pre, menu);
		return true;
	}

	@Override
	public void backAciton() {
		// add by shawn 2012-10-16 Begin
		// 让用户可以点击系统的返回键
		finish();
		// End
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

}
