package com.example.ftp4j_demo2.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

public class PageListViewLayout extends LinearLayout implements Page {

	
	public PageListViewLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public PageListViewLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public PageListViewLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	
	void destroyHardwareLayer() {
        setLayerType(LAYER_TYPE_NONE, null);
    }

    void createHardwareLayer() {
        setLayerType(LAYER_TYPE_HARDWARE, null);
    }
	
	public int getPageChildCount() {
		
		return getChildCount();
	}

	public View getChildOnPageAt(int i) {
		// TODO Auto-generated method stub
		return getChildAt(i);
	}
	
	

	public void removeAllViewsOnPage() {
		removeAllViews();
		destroyHardwareLayer();
		// TODO Auto-generated method stub

	}

	public void removeViewOnPageAt(int index) {
		// TODO Auto-generated method stub
		removeViewAt(index);

	}

	public int indexOfChildOnPage(View v) {
		// TODO Auto-generated method stub
		return indexOfChild(v);
	}

}
