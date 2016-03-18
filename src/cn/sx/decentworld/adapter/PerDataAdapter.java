/**
 * 
 */
package cn.sx.decentworld.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * @ClassName: PerDataAdapter.java
 * @Description:
 * @author: cj
 * @date: 2016年1月6日 下午3:41:09
 */
public class PerDataAdapter extends FragmentPagerAdapter {
	private Fragment[] mFragment;

	public PerDataAdapter(FragmentManager fm, Fragment[] fragment) {
		super(fm);
		mFragment = fragment;
	}

	@Override
	public Fragment getItem(int position) {
		return mFragment[position];
	}

	@Override
	public int getCount() {
		return 3;
	}

}
