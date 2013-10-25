package com.example.ftp4j_demo2.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class ScreenListView extends ListView {

	public ScreenListView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public ScreenListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public ScreenListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		final int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		setMeasuredDimension(widthSize, getMeasuredHeight());
	}
	
}
