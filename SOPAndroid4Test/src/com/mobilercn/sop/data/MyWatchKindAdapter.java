package com.mobilercn.sop.data;

import java.util.HashMap;
import java.util.List;

import com.mobilercn.config.D2EConfigures;
import com.mobilercn.sop.R;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.content.Context;
import android.view.LayoutInflater;

/**
 * 勘查种类适配器
 * @author ShawnXiao
 *
 */
public class MyWatchKindAdapter extends BaseAdapter {
	/**
	 * 记录CheckBox是否被选中的集合
	 */
	public static HashMap<Integer,Boolean> mCheckBoxStateMap=null;
	/**
	 * 数据列表
	 */
	private List<HashMap<String,Object>> mDataList=null;
	/**
	 * 上下问
	 */
	private Context mContext=null;
	/**
	 * 布局产生器
	 */
	private LayoutInflater mInflater=null;
	/**
	 * 关键字数组
	 */
	private String[] mKeyStringList=null;
	/**
	 * id数组
	 */
	private int[] mIdList=null;
	/**
	 *项的文本
	 */
	private String mItemString=null;
	
	
	/**
	 * 构造函数
	 */
	public MyWatchKindAdapter(Context context,List<HashMap<String,Object>> list ,
			int resources,String[] keys,int[] ids){
		this.mContext=context;
		this.mDataList=list;
		mInflater=LayoutInflater.from(context);
		mKeyStringList=new String[keys.length];
		mIdList=new int[ids.length];
		System.arraycopy(keys, 0, mKeyStringList, 0, keys.length);
		System.arraycopy(ids, 0, mIdList, 0, ids.length);
		initCheckBoxState();
	}
	/**
	 * 初始化所有的CheckBox都为为选中状态
	 */
	private void initCheckBoxState(){
		mCheckBoxStateMap=new HashMap<Integer,Boolean>();
		for(int i=0;i<mDataList.size();i++){
			mCheckBoxStateMap.put(i, false);
		}
	}
	
	@Override
	public int getCount() {
		return mDataList.size();
	}

	@Override
	public Object getItem(int position) {
		return mDataList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView==null){
			convertView=mInflater.inflate(R.layout.d2e_at_watch_kind_item, null);
			holder=new ViewHolder();
			holder.tv=(TextView) convertView.findViewById(R.id.tv_list_item);
			holder.cb=(CheckBox) convertView.findViewById(R.id.cb_list_item);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		if(holder!=null){
			holder.setTag(position);
			HashMap<String,Object> map=mDataList.get(position);
			if(map!=null){
				if(D2EConfigures.TEST){
					
					Log.e("mKeyStringList[0]", ""+(mKeyStringList[0]));
					
					Log.e("map.get(mKeyStringList[0])========>", ""+(map.get(mKeyStringList[0])));
				}
				mItemString=(String) map.get(mKeyStringList[0]);
				
				holder.tv.setText(mItemString);
				holder.cb.setChecked(mCheckBoxStateMap.get(position));
			}
		}
		return convertView;
	}
	
	public class ViewHolder{
		public TextView tv;
		public CheckBox cb;
		
		void setTag(int position){
			tv.setTag(position);
			cb.setTag(position);
		}
	}

}
