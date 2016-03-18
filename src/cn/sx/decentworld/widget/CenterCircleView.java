package cn.sx.decentworld.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class CenterCircleView extends ImageView {
	private int mViewWidth, mViewHeight;
	private float mPointX, mPointY;
	private Matrix mMatrix = new Matrix();
	private Paint mPaint;
	private Bitmap mBitmap;
	// private Bitmap b = BitmapFactory.decodeResource(getResources(),
	// R.drawable.camera).copy(Config.ARGB_8888, true);
	private Bitmap b;

	public CenterCircleView(Context context) {
		super(context);
		init();
	}

	public CenterCircleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		mPaint = new Paint();
		mPaint.setColor(Color.parseColor("#ffffff"));
		mPaint.setAntiAlias(true);
	}

	@SuppressLint("NewApi")
	@Override
	protected void onDraw(Canvas canvas) {
		mViewWidth = getWidth();
		mViewHeight = getHeight();
		mPointX = getX();
		mPointY = getY();
		drawTriangle(canvas);
		// Drawable drawable = getDrawable();
		// if (null == drawable) {
		// return;
		// }
		// b = drawable2Bitmap(drawable);
		// drawCircleBitmap(canvas);
	}

	private Bitmap drawable2Bitmap(Drawable drawable) {
		if (drawable instanceof BitmapDrawable) {
			return ((BitmapDrawable) drawable).getBitmap();
		} else if (drawable instanceof NinePatchDrawable) {
			Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(),
					drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
			// Canvas canvas = new Canvas(bitmap);
			// drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
			// drawable.getIntrinsicHeight());
			// drawable.draw(canvas);
			return bitmap;
		} else {
			return null;
		}
	}

	/**
	 * 绘制三角形
	 * */
	@SuppressLint("NewApi")
	private void drawTriangle(Canvas canvas) {
		Path path = new Path();
		path.moveTo(0, mViewHeight);// 此点为多边形的起点
		path.lineTo(0 + mViewWidth, mViewHeight);
		path.lineTo(mViewWidth, 0);
		path.close(); // 使这些点构成封闭的多边形
		canvas.drawPath(path, mPaint);
	}

	private void drawCircleBitmap(Canvas canvas) {
		int width;
		if (mViewHeight < mViewWidth) {
			width = mViewHeight * 4 / 5;
		} else {
			width = mViewWidth * 4 / 5;
		}
		mBitmap = getBitmap(width);
		drawCirclebackground(canvas, width);
		mMatrix.reset();
		mMatrix.postTranslate(mViewWidth / 2 - mBitmap.getWidth() / 2, mViewHeight / 2 - mBitmap.getHeight() / 2);
		canvas.drawBitmap(mBitmap, mMatrix, mPaint);
	}

	/**
	 * 绘制圆形背景
	 * */
	private void drawCirclebackground(Canvas canvas, int width) {
		Paint paint = new Paint();
		paint.setColor(Color.parseColor("#4c000000"));
		paint.setAntiAlias(true);
		canvas.drawCircle(mViewWidth / 2, mViewHeight / 2, width / 2 + 8, paint);
	}

	/**
	 * 绘制圆形
	 * 
	 * @return
	 * */
	public Bitmap getBitmap(int width) {
		Bitmap bitmap = Bitmap.createBitmap(width, width, Bitmap.Config.ARGB_8888);
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(Color.YELLOW);
		Canvas canvas = new Canvas(bitmap);
		// int i = canvas.saveLayer(0, 0, mWidth, mHeigth, paint,
		// Canvas.ALL_SAVE_FLAG);
		canvas.drawBitmap(b, null, new RectF(0, 0, width, width), null);
		paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
		canvas.drawBitmap(getMaskBitmap(width), 0, 0, paint);
		// canvas.restoreToCount(i);
		return bitmap;
	}

	/**
	 * 绘制圆形遮罩
	 * 
	 * @return
	 * */
	public Bitmap getMaskBitmap(int width) {
		Bitmap bitmap = Bitmap.createBitmap(width, width, Bitmap.Config.ARGB_8888);
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(Color.YELLOW);
		Canvas canvas = new Canvas(bitmap);
		canvas.drawCircle(width / 2, width / 2, width / 2, paint);
		return bitmap;
	}

}
