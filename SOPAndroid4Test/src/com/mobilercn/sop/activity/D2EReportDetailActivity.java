package com.mobilercn.sop.activity;

import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import com.mobilercn.base.JJBaseActivity;
import com.mobilercn.sop.R;

public class D2EReportDetailActivity extends JJBaseActivity {
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.d2e_report_detail_page);
        Window window = getWindow();
        window.setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.d2e_titlebar);
        
        TextView tv = (TextView)findViewById(R.id.title);
        tv.setText("±¨±í");
    }
    
    protected void onResume() {
    	super.onResume();
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
