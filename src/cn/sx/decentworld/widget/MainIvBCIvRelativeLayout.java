package cn.sx.decentworld.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * 此类功能：显示主图片以及底部居中图片显示
 * */
public class MainIvBCIvRelativeLayout extends RelativeLayout {
	private AutoSizeImageView autoImgv;// 主要正方形图片
	private ImageView iv;// 底部中央图片
	private int autoImgvWidth, autoImgvHeight, ivWidth, ivHeight;

	public MainIvBCIvRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public MainIvBCIvRelativeLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MainIvBCIvRelativeLayout(Context context) {
		super(context);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		autoImgv = (AutoSizeImageView) getChildAt(0);
		if (null == autoImgv) {
			return;
		}
		autoImgvWidth = autoImgv.getMeasuredWidth();
		autoImgvHeight = autoImgv.getMeasuredHeight();
		iv = (ImageView) getChildAt(1);
		if (null == iv) {
			return;
		}
		ivWidth = iv.getMeasuredWidth();
		ivHeight = iv.getMeasuredHeight();
		setMeasuredDimension(autoImgvWidth, autoImgvHeight + ivHeight / 2);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		if (null == iv) {
			return;
		}
		iv.layout(autoImgvWidth / 2 - ivWidth / 2, autoImgvHeight - ivHeight / 2, autoImgvWidth / 2 + ivWidth / 2, autoImgvHeight
				+ ivHeight / 2);
	}

	public AutoSizeImageView getMainAutoSizeImgv() {
		return autoImgv;
	}

	public ImageView getBottomCenterImgv() {
		return iv;
	}
}
