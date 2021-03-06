/**
 * 
 */
package cn.sx.decentworld.adapter;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import cn.sx.decentworld.R;
import cn.sx.decentworld.logSystem.LogUtils;
import cn.sx.decentworld.utils.ImageLoaderHelper;

/**
 * @ClassName: IconPagerAdapter.java
 * @Description:
 * @author: cj
 * @date: 2016年1月11日 下午6:06:18
 */
public class IconPagerAdapter extends PagerAdapter {
	private static final String TAG = "IconPagerAdapter";
	private List<String> picUrls;
	private List<ImageView> imgvs;

	public IconPagerAdapter(List<String> picUrls, List<ImageView> imgvs) {
		this.picUrls = picUrls;
		this.imgvs = imgvs;
		LogUtils.d(TAG, "this.imgvs " + this.imgvs.size());
	}

	@Override
	public int getCount() {
		return imgvs.size();
	}

	@Override
	public Object instantiateItem(View arg0, int position) {
		LogUtils.d(TAG, "POSITION " + position);
		ImageView imageView = imgvs.get(position);
		if (position == 0) {
			ImageLoaderHelper.mImageLoader.displayImage(picUrls.get(picUrls.size() - 1), imageView, ImageLoaderHelper.mOptions);
		} else if (position == (imgvs.size() - 1)) {
			ImageLoaderHelper.mImageLoader.displayImage(picUrls.get(0), imageView, ImageLoaderHelper.mOptions);
		} else {
			ImageLoaderHelper.mImageLoader.displayImage(picUrls.get(position - 1), imageView, ImageLoaderHelper.mOptions);
		}
		((ViewPager) arg0).addView(imageView);
		return imageView;
	}

	@Override
	public void destroyItem(View arg0, int position, Object arg2) {
		ImageView view = imgvs.get(position);
		((ViewPager) arg0).removeView(view);
		view.setImageBitmap(null);
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public void finishUpdate(ViewGroup container) {
		super.finishUpdate(container);
	}

	@Override
	public int getItemPosition(Object object) {
		return POSITION_NONE;
	}

	public void clearUrl() {
		for (int i = 0; i < picUrls.size(); i++) {
			ImageLoaderHelper.clearCacheByUrl(picUrls.get(i));
		}
	}
}
