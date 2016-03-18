package cn.sx.decentworld.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;
import cn.sx.decentworld.R;

public class PercentImageView extends ImageView {
	private int weightWidth = 2, weightHeight = 1;

	public PercentImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray typeArray = context.obtainStyledAttributes(attrs,
				R.styleable.PercentImageView);
		weightWidth = typeArray.getInt(
				R.styleable.PercentImageView_weightWidth, weightWidth);
		weightHeight = typeArray.getInt(
				R.styleable.PercentImageView_weightHeight, weightHeight);
		typeArray.recycle();
	}

	public PercentImageView(Context context) {
		super(context);
	}

	/**
	 * 可以自定义PercentImageView的长宽
	 * */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int measuredWidth = getMeasuredWidth();
		int measuredHeight = measuredWidth * weightHeight / weightWidth;
		setMeasuredDimension(measuredWidth, measuredHeight);
	}

}
