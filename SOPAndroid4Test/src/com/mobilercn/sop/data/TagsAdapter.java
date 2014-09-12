package com.mobilercn.sop.data;

import java.io.Serializable;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobilercn.base.BadgeView;
import com.mobilercn.base.JJBaseApplication;
import com.mobilercn.config.D2EConfigures;
import com.mobilercn.sop.R;
import com.mobilercn.sop.activity.D2ESOPActivity;
import com.mobilercn.util.YTFileHelper;

public class TagsAdapter extends BaseAdapter implements Filterable,Serializable{
	private static final long serialVersionUID=-6919461967497580385L;
		
	private Context            mContext;
	private ArrayList<TagItem> mTags;
	private ArrayList<TagItem> mTagsTmp;
	
	public int mType ;
	
	public void clear(){
		mTags.clear();
		mTagsTmp.clear();
	}
	public TagsAdapter(Context context){
		mContext = context;
		mTags    = new ArrayList<TagItem>();
		mTagsTmp = new ArrayList<TagItem>();
	}
	
	public void setType(int type){
		mType = type;
	}
	
	
	public void upDateTagItem(TagItem oldItem,TagItem newItem){
		oldItem.setId(newItem.getId());
		oldItem.setmTitle(newItem.getmTitle());
	}
	
	public void addObject(String title, String id){
		mTags.add(new TagItem(title, id));
		mTagsTmp.add(new TagItem(title, id));
	}

	public void addObject(String title, String id, int index){
		TagItem item = new TagItem(title, id);
		item.index = index;
		mTags.add(item );
		mTagsTmp.add(item);
	}

	
	public void addObject(TagItem item){
		mTags.add(item);
		mTagsTmp.add(item);
	}
	
	public void removeItem(TagItem item){
		if(mTags.contains(item)){
			mTags.remove(item);
		}
		if(mTagsTmp.contains(item)){
			mTagsTmp.remove(item);
		}
	}
	public void removeObjct(String title,String id){
		TagItem item=new TagItem(title,id);
		mTags.remove(item);
		mTagsTmp.remove(item);
	}
	
	public void insertObject(String title, String id, String tagnum){
		TagItem item = new TagItem(title, id);
		item.setTagNum(tagnum);
		mTags.add(0, item);
		mTagsTmp.add(0, item);
	}
	
	@Override
	public int getCount() {
		return mTagsTmp.size();
	}

	@Override
	public Object getItem(int position) {
		return mTagsTmp.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		
		convertView = LayoutInflater.from(mContext).inflate(R.layout.d2e_gridview_item, parent, false);				
		holder = new ViewHolder();
		holder.textView  = (TextView)convertView.findViewById(R.id.tagpage_customname);			
		holder.imageview=(ImageView)convertView.findViewById(R.id.tagepage_customlogo);
		convertView.setTag(holder);
		
		TagItem item = (TagItem)mTagsTmp.get(position);
		
		if(item.mChecked){
			return null;
		}
		
	    if(item.getTitle().toLowerCase().equals("") && item.getId().equals(D2EConfigures.DONE_VALUE)){		    	
			holder.textView.setText("");
			convertView.setBackgroundResource(!JJBaseApplication.sop_location_boolean ? R.drawable.green_done : R.drawable.red_done/*R.drawable.complete*/);
		}
	    else if(item.getTitle().toLowerCase().equals("") && item.getId().equals(D2EConfigures.ADD_VALUE)){
			holder.textView.setText("");
			convertView.setBackgroundResource(JJBaseApplication.sop_location_boolean ? R.drawable.red_add : R.drawable.green_add/*R.drawable.addicon*/);		    	
	    }
		else {
			
			
			holder.textView.setText(item.getTitle());
			//读取客户logo文件
	        YTFileHelper filehelper = YTFileHelper.getInstance();
	        filehelper.setContext(mContext);

			byte[] imageBytes=filehelper.readFile(item.getCustomName()+"logo.jpg");
			if(imageBytes!=null){
				item.setCustomLogo(BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length));
			}
			
			if(item.getCustomLogo()!=null){
				holder.textView.setVisibility(View.INVISIBLE);
				holder.imageview.setVisibility(View.VISIBLE);
				//获取客户logo
				holder.imageview.setImageBitmap(item.getCustomLogo());
			}else{
				holder.textView.setVisibility(View.VISIBLE);
				holder.imageview.setVisibility(View.INVISIBLE);
			}
			if(JJBaseApplication.g_grid_type == D2EConfigures.GRID_LOCATION){
				convertView.setBackgroundResource(R.drawable.grid_red);
				
				if(D2EConfigures.G_USEVIRCALDEVICE){
					BadgeView cnt = new BadgeView(mContext, holder.textView);
					cnt.setText("4");
					cnt.show();
				}
				else {
					if(item.mItems.size() > 0){
						BadgeView cnt = new BadgeView(mContext, holder.textView);
						cnt.setText("" + item.mItems.size());
						cnt.show();
					}						
				}
				
			}
			else if(JJBaseApplication.g_grid_type == D2EConfigures.GRID_DOCUMENT){
				BadgeView cnt = new BadgeView(mContext, holder.textView);
				cnt.setText(item.mId);
				cnt.show();
				holder.textView.setText(item.getmTitle());
				if(item.getTitle() != null && item.getTitle().length() > 0){
					if(item.getTitle().equalsIgnoreCase("others")){
						convertView.setBackgroundResource(R.drawable.support_others);							
					}
					else if(item.getTitle().equalsIgnoreCase("flies")){
						convertView.setBackgroundResource(R.drawable.support_flies);	
					}
					else if(item.getTitle().equalsIgnoreCase("Termites")){
						convertView.setBackgroundResource(R.drawable.support_termites);	
					}
					else if(item.getTitle().equalsIgnoreCase("cockroaches")){
						convertView.setBackgroundResource(R.drawable.support_cockroaches);	
					}
					else if(item.getTitle().equalsIgnoreCase("mosquitoes")){
						convertView.setBackgroundResource(R.drawable.support_mosquitoes);	
					}
					else if(item.getTitle().equalsIgnoreCase("rodents")){
						convertView.setBackgroundResource(R.drawable.support_rodents);	
					}

				}
				
			}
			else if(JJBaseApplication.g_grid_type == D2EConfigures.GRID_CHECK){
				convertView.setBackgroundResource(R.drawable.grid_green);					
			}
			else if(JJBaseApplication.g_grid_type == D2EConfigures.GRID_CUSTOM){
				convertView.setBackgroundResource(R.drawable.grid_yellow);					
			}
			else if(JJBaseApplication.g_grid_type == D2EConfigures.GRID_DEL_LOCATION){
				convertView.setBackgroundResource(R.drawable.delete_red);					
			}
			else if(JJBaseApplication.g_grid_type == D2EConfigures.GRID_DEL_CHECK){
				convertView.setBackgroundResource(R.drawable.delete_blue);					
			}
			
		}

		
		return convertView;
	}
	
	public Filter getFilter() {
        return new Filter() {
        	
            protected void publishResults(CharSequence constraint,FilterResults results) {
            	mTagsTmp = (ArrayList<TagItem>) results.values;
                TagsAdapter.this.notifyDataSetChanged();
            }

            protected FilterResults performFiltering(CharSequence prefix) {
                  FilterResults results = new FilterResults();
                  ArrayList<TagItem> i = new ArrayList<TagItem>();

                  if (prefix!= null && prefix.toString().length() > 0) {

                      for (int index = 0; index < mTags.size();index++) {
                    	  TagItem si = mTags.get(index);
                          if(si.getPinYin().indexOf(prefix.toString()) == 0){
                              i.add(si);
                          }
                          if(si.getTitle().toLowerCase().equals("")){
                        	  i.add(si);
                          }
                      }
                      results.values = i;
                      results.count = i.size();
                  }
                  else{
                      synchronized (mTags){
                          results.values = mTags;
                          results.count = mTags.size();
                      }
                  }

                  return results;

            }
        };
    }
	
    class ViewHolder{
		TextView  textView;
		//增加一个客户logo图片
		ImageView imageview;
	}
	
}
