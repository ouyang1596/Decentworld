/**
 * 
 */
package cn.sx.decentworld.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * @ClassName: ImageViewForGridView.java
 * @Description: 根据griditem的宽高重写imageview的宽高 
 * @author: yj
 * @date: 2015年7月20日 下午4:28:29
 */

public class ImageViewForGridView extends ImageView {
	private OnMeasureListener onMeasureListener;

	public void setOnMeasureListener(OnMeasureListener onMeasureListener) {
		this.onMeasureListener = onMeasureListener;
	}

	public ImageViewForGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ImageViewForGridView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		if (onMeasureListener != null) {
			onMeasureListener.onMeasureSize(getMeasuredWidth(),
					getMeasuredHeight());
		}
	}

	public interface OnMeasureListener {
		public void onMeasureSize(int width, int height);
	}
}
