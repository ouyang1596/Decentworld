/**
 * 
 */
package cn.sx.decentworld.adapter;

import java.util.ArrayList;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import cn.sx.decentworld.utils.ImageLoaderHelper;
import cn.sx.decentworld.utils.LogUtils;

/**
 * @ClassName: PerPicPagerAdapter.java
 * @Description:
 * @author: yj
 * @date: 2016年1月6日 下午4:27:07
 */
public class PerPicPagerAdapter extends PagerAdapter {
	private ArrayList<String> mUrls;
	/**
	 * 装ImageView数组
	 */
	private ImageView[][] mImageViews;

	public PerPicPagerAdapter(ArrayList<String> urls, ImageView[][] imageViews) {
		mUrls = urls;
		mImageViews = imageViews;
	}

	@Override
	public int getCount() {
		return Integer.MAX_VALUE;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public void destroyItem(View container, int position, Object object) {
		if (mUrls.size() == 1)
			((ViewPager) container).removeView(mImageViews[position / mUrls.size() % 2][0]);
		else
			((ViewPager) container).removeView(mImageViews[position / mUrls.size() % 2][position % mUrls.size()]);
	}

	/**
	 * 载入图片进去，用当前的position 除以 图片数组长度取余数是关键
	 */
	@Override
	public Object instantiateItem(View container, int position) {
		if (mUrls.size() == 1) {
			((ViewPager) container).addView(mImageViews[position / mUrls.size() % 2][0]);
			ImageLoaderHelper.mImageLoader.displayImage(mUrls.get(position % 3), mImageViews[position / mUrls.size() % 2][0],
					ImageLoaderHelper.mOptions);
			LogUtils.i("bm", "position--" + position);
			return mImageViews[position / mUrls.size() % 2][0];
		} else
			((ViewPager) container).addView(mImageViews[position / mUrls.size() % 2][position % mUrls.size()], 0);
		ImageLoaderHelper.mImageLoader.displayImage(mUrls.get(position % 3), mImageViews[position / mUrls.size() % 2][position
				% mUrls.size()], ImageLoaderHelper.mOptions);
		LogUtils.i("bm", "position--" + position);
		return mImageViews[position / mUrls.size() % 2][position % mUrls.size()];
	}
}
