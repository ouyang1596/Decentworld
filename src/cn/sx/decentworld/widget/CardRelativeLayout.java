/**
 * 
 */
package cn.sx.decentworld.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * @ClassName: CardRelativeLayout.java
 * @Description:
 * @author: yj
 * @date: 2016年1月8日 上午8:31:09
 */
public class CardRelativeLayout extends RelativeLayout {

	public CardRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public CardRelativeLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CardRelativeLayout(Context context) {
		super(context);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		// int width = getMeasuredWidth() * 635 / 720;
		// int height = getMeasuredHeight() * 100 / 128;
		// LinearLayout.LayoutParams layoutParams = new
		// LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
		// ViewGroup.LayoutParams.WRAP_CONTENT);
		// layoutParams.setMargins(width / 2, height / 2, width / 2, height /
		// 2);// 4个参数按顺序分别是左上右下
		// setLayoutParams(layoutParams);
	}
}
