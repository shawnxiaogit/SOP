package com.mobilercn.sop.data;

import java.io.Serializable;
import java.util.ArrayList;
import android.graphics.Bitmap;

public class MessageItem implements Serializable{
	private static final long serialVersionUID=-6433786313435044311L;
	private boolean mState;//0:new 1:old
	private String  mTitle;
	private String  mContent;
	private String  mID;
	private String  mDate;
	private String mCreateUserName;
	private String mStrUserLogo;
	private byte[] mCreateUserLogo;
	private MyBitmap myBitmap;
	
	private ArrayList<MessageItem> messages=new ArrayList<MessageItem>();
	
	public MessageItem(String title, String content){
		mTitle   = title;
		mContent = content;
	}
	public boolean isContainMessageItem(MessageItem item){
		if(messages.size()>0){
			for(MessageItem itemExist:messages){
				if(item.getmDate().equalsIgnoreCase(itemExist.getmDate())){
					return true;
				}
			}
		}
		return false;
	}
	
	
	
	
	public MyBitmap getMyBitmap() {
		return myBitmap;
	}
	public void setMyBitmap(MyBitmap myBitmap) {
		this.myBitmap = myBitmap;
	}
	public String getmStrUserLogo() {
		return mStrUserLogo;
	}
	public void setmStrUserLogo(String mStrUserLogo) {
		this.mStrUserLogo = mStrUserLogo;
	}
	public String getTitle(){
		return mTitle;
	}
	
	public String getContent(){
		StringBuffer sb = new StringBuffer();
		sb.append(mDate).append("\n").append(mContent).append("\n");
		return sb.toString();
	}
	
	public boolean isRead(){
		return mState;
	}
	
	public void setRead(boolean read){
		mState = read;
	}
	
	public void setRead(String str){
		if(str != null && str.length() > 0){
			setRead(true);
		}
		else {
			setRead(false);
		}
	}
	
	
	
	public byte[] getmCreateUserLogo() {
		return mCreateUserLogo;
	}
	public void setmCreateUserLogo(byte[] mCreateUserLogo) {
		this.mCreateUserLogo = mCreateUserLogo;
	}
	public void setDate(String date){
		mDate = date;
	}
	
	public void setID(String id){
		mID = id;
	}
	
	
	

	public String getmCreateUserName() {
		return mCreateUserName;
	}
	public void setmCreateUserName(String mCreateUserName) {
		this.mCreateUserName = mCreateUserName;
	}
	
	public boolean ismState() {
		return mState;
	}
	public void setmState(boolean mState) {
		this.mState = mState;
	}
	public String getmTitle() {
		return mTitle;
	}
	public void setmTitle(String mTitle) {
		this.mTitle = mTitle;
	}
	public String getmContent() {
		return mContent;
	}
	public void setmContent(String mContent) {
		this.mContent = mContent;
	}
	public String getmID() {
		return mID;
	}
	public void setmID(String mID) {
		this.mID = mID;
	}
	public String getmDate() {
		return mDate;
	}
	public void setmDate(String mDate) {
		this.mDate = mDate;
	}
	public ArrayList<MessageItem> getMessages() {
		return messages;
	}

	public void setMessages(ArrayList<MessageItem> messages) {
		this.messages = messages;
	}
	@Override
	public String toString() {
		return "MessageItem [mState=" + mState + ", mTitle=" + mTitle
				+ ", mContent=" + mContent + ", mID=" + mID + ", mDate="
				+ mDate + ", mCreateUserName=" + mCreateUserName
				+ ", mCreateUserLogo=" + mCreateUserLogo + "]";
	}
	
	
}
