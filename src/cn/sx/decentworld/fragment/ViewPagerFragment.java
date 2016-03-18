/**
 * 
 */
package cn.sx.decentworld.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import cn.sx.decentworld.R;
import cn.sx.decentworld.activity.PictureClipActivity_;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.dialog.CameraAlbumDialogFragment;
import cn.sx.decentworld.dialog.CameraAlbumDialogFragment.OnCameraAlbumClickListener;
import cn.sx.decentworld.utils.ImageUtils;
import cn.sx.decentworld.utils.LogUtils;

/**
 * @ClassName: ViewPagerFragment.java
 * @Description:
 * @author: yj
 * @date: 2016年1月12日 下午3:44:55
 */
public class ViewPagerFragment extends Fragment implements OnClickListener, OnCameraAlbumClickListener {
	private LinearLayout llDots;
	private ViewPager vpShowPic;
	public HashMap<Integer, String> mPicPaths;
	private List<Bitmap> bitmaps;
	private List<ImageView> imageViews;
	private List<View> dots;
	private int currentItem = 0; // 当前图片的索引号
	private ChoosePicPageAdapter adapter;
	private int mPosition = -1;
	private int maxCount;
	private CameraAlbumDialogFragment cameraAlbumDialogFragment;
	private boolean onlyFirstCamera;
	private String condition;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_viewpager, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
	}

	private void initView() {
		llDots = (LinearLayout) getView().findViewById(R.id.ll_dots);
		vpShowPic = (ViewPager) getView().findViewById(R.id.vp_pic);
		bitmaps = new ArrayList<Bitmap>();
		imageViews = new ArrayList<ImageView>();
		mPicPaths = new HashMap<Integer, String>();
		dots = new ArrayList<View>();
		adapter = new ChoosePicPageAdapter();
		vpShowPic.setAdapter(adapter);
		vpShowPic.setOnPageChangeListener(new CustomPageChangeListener());
		addPicView();
	}

	private int picCount = 0;

	private void addPicView() {
		ImageView imageView = new ImageView(getActivity());
		imageView.setImageResource(R.drawable.camera);
		imageView.setOnClickListener(this);
		imageView.setTag(picCount);
		imageViews.add(imageView);
		addDot();
		// vpShowPic.setCurrentItem(adapter.getCount() - 1);
		adapter.notifyDataSetChanged();
		picCount++;
	}

	public View createDot() {
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(10, 10);
		lp.setMargins(10, 0, 10, 0);
		View view = new View(getActivity());
		view.setLayoutParams(lp);
		view.setBackgroundResource(R.drawable.dot_normal);
		return view;
	}

	private void addImageView(Bitmap bitmap) {
		ImageView imageView = new ImageView(getActivity());
		imageView.setImageBitmap(bitmap);
		imageView.setScaleType(ScaleType.CENTER_CROP);
		imageView.setOnClickListener(this);
		imageViews.add(imageView);
	}

	private void addDot() {
		View dot = createDot();
		llDots.addView(dot);
		dots.add(dot);
	}

	/**
	 * 获取bitmap，超过2M，进行缩小
	 * */
	private Bitmap getBitmap(String picPath, int fileLength) {
		Bitmap bitmap;
		if (fileLength > 1024 * 1024 * 1.5) {
			bitmap = ImageUtils.scalePic(picPath);
		} else {
			bitmap = BitmapFactory.decodeFile(picPath);
		}
		return bitmap;
	}

	/**
	 * 填充ViewPager页面的适配器
	 */
	private class ChoosePicPageAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return imageViews.size();
		}

		@Override
		public Object instantiateItem(View view, int position) {
			ImageView imageView = imageViews.get(position);
			((ViewPager) view).addView(imageView);
			return imageView;
		}

		@Override
		public void destroyItem(View view, int position, Object object) {
			((ViewPager) view).removeView((View) object);
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

		@Override
		public void finishUpdate(ViewGroup container) {
			super.finishUpdate(container);
			setCurrentDot();
		}

		@Override
		public int getItemPosition(Object object) {
			return POSITION_NONE;
		}

	}

	/**
	 * 当ViewPager中页面的状态发生改变时调用
	 */
	private class CustomPageChangeListener implements OnPageChangeListener {
		private int oldPosition = 0;

		public void onPageSelected(int position) {
			currentItem = position;
			if (dots.size() > oldPosition && dots.size() > position) {
				dots.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);
				dots.get(position).setBackgroundResource(R.drawable.dot_focused);
			}
			oldPosition = position;
			if (null != mOnPagerChangeListener) {
				mOnPagerChangeListener.OnPagerChange(position);
			}
		}

		public void onPageScrollStateChanged(int arg0) {

		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}
	}

	/**
	 * 设置默认的选中点
	 * */
	private void setCurrentDot() {
		if (imageViews.size() > 0 && currentItem >= 0 && dots.size() > currentItem) {
			currentItem = vpShowPic.getCurrentItem();
			dots.get(currentItem).setBackgroundResource(R.drawable.dot_focused);
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (null == data) {
			return;
		}
		if (requestCode == Constants.REQUEST_CODE) {
			String picPath = data.getStringExtra("filePath");
			int fileLength = ImageUtils.fileLength(picPath);
			Bitmap bitmap = getBitmap(picPath, fileLength);
			if (0 == mPosition) {
				LogUtils.i("bm", "condition--" + condition);
				bitmap = ImageUtils.drawTextToBitmap(condition, bitmap);
			}
			ImageUtils.saveBitmap(picPath, bitmap);
			imageViews.get(mPosition).setImageBitmap(bitmap);
			if (null == mPicPaths.get(mPosition)) {
				mPicPaths.put(mPosition, picPath);
				if (mPicPaths.size() < maxCount) {
					addPicView();
				}
			} else {
				mPicPaths.put(mPosition, picPath);
			}
			new TimeToGoAsyn().execute();
		}
	}

	class TimeToGoAsyn extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			vpShowPic.setCurrentItem(adapter.getCount() - 1);
		}
	}

	public void setMaxCount(int maxCount) {
		this.maxCount = maxCount;
	}

	@Override
	public void onClick(View v) {
		mPosition = (Integer) v.getTag();
		if (null == cameraAlbumDialogFragment) {
			cameraAlbumDialogFragment = new CameraAlbumDialogFragment();
		}
		if (onlyFirstCamera) {
			cameraAlbumDialogFragment.setPosition(mPosition);
		}
		cameraAlbumDialogFragment.setOnCameraAlbumClickListener(this);
		cameraAlbumDialogFragment.show(getActivity().getSupportFragmentManager(), "cameraAlbumDialogFragment");
	}

	@Override
	public void onCameraAlbumClick(View view) {
		Intent intent;
		switch (view.getId()) {
		case R.id.tv_camera:
			intent = new Intent(getActivity(), PictureClipActivity_.class);
			intent.putExtra(Constants.CAMERA_ALBUM, 1);
			intent.putExtra(Constants.ASPECTX, 1);
			intent.putExtra(Constants.ASPECTY, 1);
			intent.putExtra(Constants.OUTPUTX, 400);
			intent.putExtra(Constants.OUTPUTY, 400);
			startActivityForResult(intent, Constants.REQUEST_CODE);
			break;
		case R.id.tv_album:
			intent = new Intent(getActivity(), PictureClipActivity_.class);
			intent.putExtra(Constants.CAMERA_ALBUM, 0);
			intent.putExtra(Constants.ASPECTX, 1);
			intent.putExtra(Constants.ASPECTY, 1);
			intent.putExtra(Constants.OUTPUTX, 400);
			intent.putExtra(Constants.OUTPUTY, 400);
			startActivityForResult(intent, Constants.REQUEST_CODE);
			break;
		case R.id.tv_cancel:
			cameraAlbumDialogFragment.dismiss();
			break;
		}
	}

	public interface OnPagerChangeListener {
		void OnPagerChange(int position);
	}

	private OnPagerChangeListener mOnPagerChangeListener;

	public void setOnPagerChangeListener(OnPagerChangeListener onPagerChangeListener) {
		mOnPagerChangeListener = onPagerChangeListener;
	}

	/**
	 * 设置是否第一张只显示相机
	 * */
	public void setOnlyFirstCamera(boolean onlyFirstCamera) {
		this.onlyFirstCamera = onlyFirstCamera;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}
}
