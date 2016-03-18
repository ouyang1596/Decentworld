package cn.sx.decentworld.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * 此LinearLayout是让事件向下分发
 * */
public class CustomLineaLayout extends LinearLayout {

	public CustomLineaLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public CustomLineaLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		Log.i("bm", "-dispatchTouchEvent-" + super.dispatchTouchEvent(ev));
		return super.dispatchTouchEvent(ev);
	}

	@SuppressLint("NewApi")
	@Override
	public boolean onInterceptHoverEvent(MotionEvent event) {
		Log.i("bm", "-onInterceptHoverEvent-" + super.onInterceptHoverEvent(event));
		return false;
	}
}
