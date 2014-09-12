package com.mobilercn.sop.data;

import java.io.Serializable;
import java.util.ArrayList;

import android.util.Log;

import com.mobilercn.config.D2EConfigures;

public class MyMessageData implements Serializable{
	private static final long serialVersionUID=-6433786313435044321L;
	private ArrayList<MessageItem> messages;
	
	public MyMessageData(){
		messages=new ArrayList<MessageItem>();
	}
	public boolean isContainMessageItem(MessageItem item){
		if(messages.size()>0){
			for(MessageItem existItem:messages){
				if(D2EConfigures.TEST){
					Log.e("item.getmDate()==========>", ""+(item.getmID()));
					Log.e("existItem.getmDate()==========>", ""+(existItem.getmID()));
				}
				if(item.getmID().equalsIgnoreCase(existItem.getmID())){
					return true;
				}
//				if(!item.getmDate().equalsIgnoreCase(existItem.getmDate())
//						&&!item.getmTitle().equalsIgnoreCase(existItem.getmTitle())
//						&&!item.getContent().equalsIgnoreCase(existItem.getContent())){
//					return true;
//				}
			}
		}
		return false;
	}
	
	/**
	 * 获取消息列表中包含的哪一项
	 * @param item
	 * @return
	 */
	public MessageItem getContainMessageItem(MessageItem item){
		MessageItem itemGet=null;
		int size=messages.size();
		for(int i=0;i<size;i++){
			if(messages.get(i).getmID().equalsIgnoreCase(item.getmID())){
				itemGet=messages.get(i);
			}
			
		}
//		if(messages.size()>0){
//			for(MessageItem existItem:messages){
//				if(D2EConfigures.TEST){
//					Log.e("item.getmDate()==========>", ""+(item.getmDate()));
//					Log.e("existItem.getmDate()==========>", ""+(existItem.getmDate()));
//				}
//				if(item.getmDate().trim().equalsIgnoreCase(existItem.getmDate().trim())){
//					return true;
//				}
////				if(!item.getmDate().equalsIgnoreCase(existItem.getmDate())
////						&&!item.getmTitle().equalsIgnoreCase(existItem.getmTitle())
////						&&!item.getContent().equalsIgnoreCase(existItem.getContent())){
////					return true;
////				}
//			}
//		}
		return itemGet;
	}
	public ArrayList<MessageItem> getMessages() {
		return messages;
	}

	public void setMessages(ArrayList<MessageItem> messages) {
		this.messages = messages;
	}
	public void print(){
		if(messages.size()>0){
		for(MessageItem item:messages){
			Log.e("item", item.toString());
		}
		}
	}
	
}
