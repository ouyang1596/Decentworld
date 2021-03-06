/**
 * 
 */
package cn.sx.decentworld.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * @ClassName: AutoSizeRelativelayout.java
 * @Description:
 * @author: yj
 * @date: 2016年3月7日 上午11:00:53
 */
public class AutoSizeRelativeLayout extends RelativeLayout {

	public AutoSizeRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public AutoSizeRelativeLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public AutoSizeRelativeLayout(Context context) {
		super(context);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int measuredWidth = getMeasuredWidth();
		int measuredHeight = measuredWidth;
		setMeasuredDimension(measuredWidth, measuredHeight);
	}
}
