/**
 * 
 */
package cn.sx.decentworld.adapter;

import java.util.List;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import cn.sx.decentworld.utils.ImageLoaderHelper;

/**
 * @ClassName: SlideImageAdapter.java
 * @Description: 我的设置界面显示用户的所有头像的ViewPager适配器
 * @author: cj
 * @date: 2015年10月20日 上午9:54:59
 */
public class SlideImageAdapter extends PagerAdapter {
	private Context context;
	private List<ImageView> mDatas;
	private List<String> imgUrl;

	/**
	 * 
	 */
	public SlideImageAdapter(Context context, List<ImageView> mDatas,
			List<String> imgUrl) {
		this.context = context;
		this.mDatas = mDatas;
		this.imgUrl = imgUrl;
	}

	@Override
	public int getCount() {
		return mDatas.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// return false;
		return arg0 == arg1;
	}

	@Override
	public Object instantiateItem(View container, int position) {
		// return super.instantiateItem(container, position);
		ImageView imageView = mDatas.get(position);
		// Picasso.with(context).load(imgUrl.get(position)).resize(720,
		// 720).into(imageView);
		ImageLoaderHelper.mImageLoader.displayImage(imgUrl.get(position),
				imageView, ImageLoaderHelper.mOptions);
		((ViewPager) container).addView(imageView);
		return imageView;
	}

	@Override
	public void destroyItem(View container, int position, Object object) {
		// super.destroyItem(container, position, object);
		((ViewPager) container).removeView((View) object);
	}

	@Override
	public int getItemPosition(Object object) {
		return POSITION_NONE;
	}

}
