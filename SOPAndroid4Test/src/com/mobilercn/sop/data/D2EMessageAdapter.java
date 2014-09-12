package com.mobilercn.sop.data;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.mobilercn.base.JJBaseApplication;
import com.mobilercn.config.D2EConfigures;
import com.mobilercn.sop.R;
import com.mobilercn.util.YTFileHelper;

public class D2EMessageAdapter extends BaseAdapter {
	
	private Context                mContext;
	private ArrayList<MessageItem> mMessages;
	public D2EMessageAdapter(Context context){
		mContext  = context;
		mMessages = new ArrayList<MessageItem>();
	}
	public void clear(){
		mMessages.clear();
	}
	public void addObject(String title, String content){
		mMessages.add(new MessageItem(title, content));
	}
	
	public void addObject(MessageItem item){
		mMessages.add(item);
	}
	@Override
	public int getCount() {
		return mMessages.size();
	}

	@Override
	public Object getItem(int position) {
		if(position < 0 || position > getCount()){
			return null;
		}
		return mMessages.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if(D2EConfigures.TEST){
			Log.e("getView============>", "getView()");
		}
		final MessageItem item = (MessageItem)mMessages.get(position);
		final YTFileHelper filehelper = YTFileHelper.getInstance();
        filehelper.setContext(mContext);
		final MyMessageData messages=(MyMessageData) filehelper.deSerialObject(JJBaseApplication.user_card_num+"msg.data");
		
		ViewHolder holder;
		if(convertView == null){
			convertView = LayoutInflater.from(mContext).inflate(R.layout.d2e_msg_list_item, parent, false);
			holder = new ViewHolder();
			holder.imageView = (ImageView)convertView.findViewById(R.id.msg_state);
			holder.textView  = (TextView)convertView.findViewById(R.id.msg_text);
			holder.msg_cb=(ImageButton)convertView.findViewById(R.id.msg_cb);
			holder.iv_create_user=(ImageView) convertView.findViewById(R.id.iv_create_user);
			holder.tv_create_user=(TextView) convertView.findViewById(R.id.tv_create_user);
			
			convertView.setTag(holder);
		}
		else {
			holder = (ViewHolder)convertView.getTag();
		}
		if(holder!=null){
			holder.textView.setText(item.getTitle());
			//modify by shawn 2012-12-04 Begin
			//加载图片用异步来做处理
			//add by shawn 2012-12-05 Begin
			//添加一个Tag，来确保图片加载正确
			holder.iv_create_user.setTag(item.getmCreateUserName());
			//End
			if(item.getMyBitmap()!=null){
				if(D2EConfigures.TEST){
					Log.e("item.getMyBitmap()==========>", item.getMyBitmap().getName());
				}
				byte[] img=item.getMyBitmap().getBitmapBytes();
				holder.iv_create_user.setImageBitmap(BitmapFactory.decodeByteArray(img, 0, img.length));
			}else{
				holder.iv_create_user.setImageBitmap(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.check_people));
			}
			holder.tv_create_user.setText(item.getmCreateUserName());
			if(item.isRead()){
				holder.imageView.setVisibility(View.INVISIBLE);
			}else{
				holder.imageView.setVisibility(View.VISIBLE);
			}
			holder.msg_cb.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if(mMessages.size()>0){
						mMessages.remove(mMessages.get(position));
						messages.getMessages().remove(messages.getMessages().get(position));
						filehelper.serialObject(JJBaseApplication.user_card_num+"msg.data", messages);
						D2EMessageAdapter.this.notifyDataSetChanged();
					}
				}
			});
		}
		
		
		return convertView;
	}
	
    static class ViewHolder{
		ImageView imageView;
		TextView  textView;
		ImageButton msg_cb;
		ImageView iv_create_user;
		TextView tv_create_user;
	}
} 


