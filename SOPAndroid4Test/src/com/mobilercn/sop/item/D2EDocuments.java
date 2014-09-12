package com.mobilercn.sop.item;

import java.util.ArrayList;

public class D2EDocuments {
	
	public ArrayList<D2EDocumentItem> mItems = new ArrayList<D2EDocumentItem>(4);
	
	public String mType;
	
	public String mTitle;
	
	public D2EDocuments(){
		
	}
	
	public void addItem(D2EDocumentItem item){
		mItems.add(item);
	}
}
