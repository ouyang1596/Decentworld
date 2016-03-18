package cn.sx.decentworld.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;
import cn.sx.decentworld.R;

public class AutoSizeImageView extends ImageView {
	private int weightWidth = 1, weightHeight = 1;

	public AutoSizeImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray typeArray = context.obtainStyledAttributes(attrs, R.styleable.AutoSizeImageView);
		weightWidth = typeArray.getInt(R.styleable.AutoSizeImageView_weightWidth, weightWidth);
		weightHeight = typeArray.getInt(R.styleable.AutoSizeImageView_weightHeight, weightHeight);
		typeArray.recycle();
	}

	public AutoSizeImageView(Context context) {
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
