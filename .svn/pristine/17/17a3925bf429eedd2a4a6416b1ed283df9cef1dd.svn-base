package cn.sx.decentworld.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class SideBar extends View {
	// 触摸事件
	private OnTouchingLetterChangedListener onTouchingLetterChangedListener;
	// 26个字母
	public static String[] b = { "陌", "特", "朋", "亲" };
	public int choose = 4;// 选中
	private Paint paint = new Paint();
	private TextView mTextDialog;
	public boolean ifNeedMeasure = true;// 是否需要执行onMeasure

	public void setTextView(TextView mTextDialog) {
		this.mTextDialog = mTextDialog;
	}

	public SideBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public SideBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		paint.setColor(Color.parseColor("#ff0000"));
		paint.setAntiAlias(true);
	}

	public SideBar(Context context) {
		super(context);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		if (ifNeedMeasure) {
			mY = getHeight();
		}
	}

	/**
	 * 重写这个方法
	 */
	@SuppressLint("NewApi")
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// 获取焦点改变背景颜色.
		int height = getHeight();// 获取对应高度
		int width = getWidth(); // 获取对应宽度

		Path path = new Path();
		path.moveTo(0, height);
		path.lineTo(width, height);
		path.lineTo(width, mY);
		path.lineTo(0, mY);
		path.close();
		canvas.drawPath(path, paint);

		int singleHeight = height / b.length;// 获取每一个字母的高度
		for (int i = 0; i < b.length; i++) {
			paint.setColor(Color.rgb(33, 65, 98));
			// paint.setColor(Color.WHITE);
			paint.setTypeface(Typeface.DEFAULT_BOLD);
			paint.setAntiAlias(true);
			paint.setTextSize(60);
			// 选中的状态
			if (i >= choose) {
				paint.setColor(Color.parseColor("#3399ff"));
				paint.setFakeBoldText(true);
			}
			// x坐标等于中间-字符串宽度的一半.
			float xPos = width / 2 - paint.measureText(b[i]) / 2;
			float yPos = singleHeight * i + singleHeight / 2F;
			canvas.drawText(b[i], xPos, yPos, paint);
			paint.reset();// 重置画笔
		}
	}

	public float mY;

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		final int action = event.getAction();
		final float y = event.getY();// 点击y坐标
		mY = y;
		invalidate();
		final int oldChoose = choose;
		final OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;
		final int c = (int) (y / getHeight() * b.length);// 点击y坐标所占总高度的比例*b数组的长度就等于点击b中的个数.
		switch (action) {
		case MotionEvent.ACTION_UP:
			setBackgroundDrawable(new ColorDrawable(0x00000000));
			// choose = 4;//复位需要用到
			// mY = getHeight();//复位需要用到
			if (null != mOnChooseChangeListener) {
				mOnChooseChangeListener.onChooseChange(choose);
			}
			ifNeedMeasure = false;
			invalidate();
			if (mTextDialog != null) {
				mTextDialog.setVisibility(View.INVISIBLE);
			}
			break;
		default:
			// setBackgroundResource(R.drawable.sidebar_background);
			if (oldChoose != c) {
				if (c >= 0 && c < b.length) {
					if (listener != null) {
						listener.onTouchingLetterChanged(b[c]);
					}
					if (mTextDialog != null) {
						mTextDialog.setText(b[c]);
						mTextDialog.setVisibility(View.VISIBLE);
					}
					choose = c;
					invalidate();
				}
			}

			break;
		}
		return true;
	}

	/**
	 * 向外公开的方法
	 */
	public void setOnTouchingLetterChangedListener(OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
		this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
	}

	/**
	 * 接口
	 */
	public interface OnTouchingLetterChangedListener {
		public void onTouchingLetterChanged(String s);
	}

	private OnChooseChangeListener mOnChooseChangeListener;

	public interface OnChooseChangeListener {
		void onChooseChange(int choose);
	}

	public void setOnChooseChangeListener(OnChooseChangeListener onChooseChangeListener) {
		mOnChooseChangeListener = onChooseChangeListener;
	}
}