package com.mobilercn.sop.item;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.mobilercn.sop.R;

public class D2EPersonAdapter extends BaseAdapter {
	private Context                  mContext;
	private ArrayList<D2EPerson> mMessages;
	
	public D2EPersonAdapter(Context context){
		mContext  = context;
		mMessages = new ArrayList<D2EPerson>();
	}

	public void addObject(String id, String name, String tag){
		mMessages.add(new D2EPerson(id, name, tag));
	}
	
	public void addObject(D2EPerson item){
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
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		
		if(convertView == null){
			convertView = LayoutInflater.from(mContext).inflate(R.layout.d2e_checkin_item, parent, false);
			holder = new ViewHolder();
			holder.imageView = (ImageView)convertView.findViewById(R.id.txt_iconView);
			holder.imageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.btn_checkpeople));//.setBackgroundResource(R.drawable.btn_checkpeople);
			
			holder.textView  = (TextView)convertView.findViewById(R.id.txt_stateView);
			holder.dateView  = (TextView)convertView.findViewById(R.id.txt_dateLabel);
			holder.titleView = (TextView)convertView.findViewById(R.id.txt_titleLabel);				
			convertView.setTag(holder);
		}
		else {
			holder = (ViewHolder)convertView.getTag();
		}
		D2EPerson item = (D2EPerson)mMessages.get(position);
		holder.dateView.setText("±àºÅ:"+item.getId());
		holder.titleView.setText(item.getName());
		if(!item.mChecked){
			Drawable draw = mContext.getResources().getDrawable(R.drawable.btn_checkin);
			holder.textView.setCompoundDrawablesWithIntrinsicBounds(null, draw, null,null); 
			holder.textView.setText("Ç©µ½");			
		}
		else {
			Drawable draw = mContext.getResources().getDrawable(R.drawable.btn_check_ok);
			holder.textView.setCompoundDrawablesWithIntrinsicBounds(null, draw, null,null); 
			holder.textView.setText("³É¹¦");
		}
		
		return convertView;
	}
	
    class ViewHolder{
		ImageView imageView;
		TextView  textView;
		TextView  dateView;
		TextView  titleView;
	}

}
