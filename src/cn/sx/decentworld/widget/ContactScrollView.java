/**
 * 
 */
package cn.sx.decentworld.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;
import cn.sx.decentworld.utils.LogUtils;

/**
 * @ClassName: ContactScrollView.java
 * @author: yj
 * @date: 2016年2月1日 下午4:58:34
 */
public class ContactScrollView extends ScrollView {
	private int height;
	private int scrollY;

	public ContactScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

	}

	public ContactScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);

	}

	public ContactScrollView(Context context) {
		super(context);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		LogUtils.e("bm", "dispatchTouchEvent--height--" + height + "ev.getY()==" + ev.getY());
		int lvToTop = height - scrollY;
		if (ev.getY() < lvToTop) {
			return false;
		} else {
			return super.dispatchTouchEvent(ev);
		}
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
		scrollY = t;
		LogUtils.i("bm", "scrollY--" + scrollY);
	}

	public void setHeight(int height) {
		this.height = height;
	}
}
