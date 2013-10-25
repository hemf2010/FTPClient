package com.example.ftp4j_demo2.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;

public class ScreenHorizontalScrollView extends HorizontalScrollView {

	public ScreenHorizontalScrollView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	

	public ScreenHorizontalScrollView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}



	public ScreenHorizontalScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}



	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
		 if (getChildCount() > 0) {
	            final View child = getChildAt(0);
	            int width = getMeasuredWidth();
	                final FrameLayout.LayoutParams lp = (LayoutParams) child.getLayoutParams();

	                int childHeightMeasureSpec = getChildMeasureSpec(heightMeasureSpec, 0
	                        + 0, lp.height);

	                int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);

	                child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
	            }
	}
	
	

}
