package cn.sx.decentworld.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

/**
 * @ClassName: ViewPagerAdapter
 * @Description: ViewPager适配器
 * @author yj
 * @date 2015年6月29日10:40:06
 * 
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {
	private List<Fragment> list;

	public ViewPagerAdapter(FragmentManager fm, List<Fragment> list) {
		super(fm);
		this.list = list;
	}

	@Override
	public Fragment getItem(int arg0) {
		return list.get(arg0);
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public int getItemPosition(Object object) {
		return super.getItemPosition(object);
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// 重写destroy方法，阻止销毁Fragment
	}

}
