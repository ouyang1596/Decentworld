package cn.sx.decentworld.activity;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.TextView;
import cn.sx.decentworld.R;
import cn.sx.decentworld.fragment.ImageDetailFragment;
import cn.sx.decentworld.widget.HackyViewPager;

/**
 * 图片查看器
 */
public class ImagePagerActivity extends BaseFragmentActivity {
	private static final String STATE_POSITION = "STATE_POSITION";
	public static final String EXTRA_IMAGE_INDEX = "image_index";
	public static final String EXTRA_IMAGE_URLS = "image_urls";
	public static final String LOCAL_IMAGE_FILEPATH = "image_path";

	private HackyViewPager mPager;
	private int pagerPosition;
	private TextView indicator;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image_detail_pager);
		pagerPosition = getIntent().getIntExtra(EXTRA_IMAGE_INDEX, 0);

		// 存放网络URL
		ArrayList<String> urls = getIntent().getStringArrayListExtra(
				EXTRA_IMAGE_URLS);
		// 存放本地文件绝对路径
		ArrayList<String> image_paths = getIntent().getStringArrayListExtra(
				LOCAL_IMAGE_FILEPATH);

		mPager = (HackyViewPager) findViewById(R.id.pager);

		// 两种适配器择其一
		ImagePagerAdapter mAdapter = null;
		if (urls != null) {
			mAdapter = new ImagePagerAdapter(getSupportFragmentManager(), urls,
					cn.sx.decentworld.common.Constants.IMAGE_NET);
		}
		if (image_paths != null) {
			mAdapter = new ImagePagerAdapter(getSupportFragmentManager(),
					image_paths, cn.sx.decentworld.common.Constants.IMAGE_LOCAL);
		}

		mPager.setAdapter(mAdapter);
		indicator = (TextView) findViewById(R.id.indicator);

		CharSequence text = getString(R.string.viewpager_indicator, 1, mPager
				.getAdapter().getCount());
		indicator.setText(text);
		// 更新下标
		mPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageSelected(int arg0) {
				CharSequence text = getString(R.string.viewpager_indicator,
						arg0 + 1, mPager.getAdapter().getCount());
				indicator.setText(text);
			}
		});
		if (savedInstanceState != null) {
			pagerPosition = savedInstanceState.getInt(STATE_POSITION);
		}

		mPager.setCurrentItem(pagerPosition);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putInt(STATE_POSITION, mPager.getCurrentItem());
	}

	private class ImagePagerAdapter extends FragmentStatePagerAdapter {
		public ArrayList<String> fileList;
		public int localOrNet;

		public ImagePagerAdapter(FragmentManager fm,
				ArrayList<String> fileList, int localOrNet) {
			super(fm);
			this.fileList = fileList;
			this.localOrNet = localOrNet;
		}

		@Override
		public int getCount() {
			return fileList == null ? 0 : fileList.size();
		}

		@Override
		public Fragment getItem(int position) {
			String url = fileList.get(position);
			return ImageDetailFragment.newInstance(url, localOrNet);
		}
	}
}
