package com.mobilercn.sop.data;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobilercn.config.D2EConfigures;
import com.mobilercn.sop.R;

public class PcoUserAdapter extends BaseAdapter {
	private Context                  mContext;
	private ArrayList<PcoUser> mMessages;
	
	public void clear(){
		mMessages.clear();
	}
	
	public PcoUserAdapter(Context context){
		mContext  = context;
		mMessages = new ArrayList<PcoUser>();
	}

	public void addObject(String id, String name, String tag){
		mMessages.add(new PcoUser(id, name, tag));
	}
	
	public void addObject(PcoUser item){
		mMessages.add(item);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
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
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		
		if(convertView == null){
			convertView = LayoutInflater.from(mContext).inflate(R.layout.d2e_checkin_item, parent, false);
			holder = new ViewHolder();
			holder.imageView = (ImageView)convertView.findViewById(R.id.txt_iconView);
			
			holder.textView  = (TextView)convertView.findViewById(R.id.txt_stateView);
			holder.dateView  = (TextView)convertView.findViewById(R.id.txt_dateLabel);
			holder.titleView = (TextView)convertView.findViewById(R.id.txt_titleLabel);				
			convertView.setTag(holder);
		}
		else {
			holder = (ViewHolder)convertView.getTag();
		}
		PcoUser item = (PcoUser)mMessages.get(position);
		if(D2EConfigures.TEST){
			Log.e("itemid------------------>", item.getStrId());
			Log.e("itemName------------------>", item.getStrName());
			Log.e("itemId------------------>", item.getStrTagId());
			Log.e("itemNumbers------------------>", item.getStrNumbers());
			Log.e("itemLogo------------------>", item.getStrLogo());
			Log.e("xxxxxxxxxxxxxxxxxx", "++++++++++++++++++++++++++");
		}
		holder.dateView.setText(mContext.getResources().getString(R.string.user_number)+item.getStrId());
		holder.titleView.setText(item.getStrName());
		if(item.getmPhoto()!=null){
			holder.imageView.setImageBitmap(item.getmPhoto());
		}else{
			holder.imageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.check_people));
		}
		Drawable draw = mContext.getResources().getDrawable(R.drawable.btn_check_green);
		holder.textView.setCompoundDrawablesWithIntrinsicBounds(null, draw, null,null); 
		holder.textView.setText(mContext.getResources().getString(R.string.doing_success));
		
		return convertView;
	}
	
    class ViewHolder{
		ImageView imageView;
		TextView  textView;
		TextView  dateView;
		TextView  titleView;
	}

}
