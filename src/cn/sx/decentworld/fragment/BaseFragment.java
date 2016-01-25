package cn.sx.decentworld.fragment;

import android.support.v4.app.Fragment;

/**
 * @ClassName: BaseFragment
 * @Description: 主界面fragment用到的基础fragment
 * @author yj
 * @date 2015年6月29日12:34:03
 * 
 */
public abstract class BaseFragment extends Fragment
{
	/** Fragment当前状态是否可见 */
	protected boolean isVisible;
	/** 标志位，标志已经初始化完成 */
	protected boolean isPrepared;
	/** 是否已被加载过一次，第二次就不再去请求数据了 */
	protected boolean mHasLoadedOnce;

	/** setUserVisibleHint是在onCreateView之前调用的 **/
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser)
	{
		super.setUserVisibleHint(isVisibleToUser);

		if (getUserVisibleHint())
		{
			isVisible = true;
			onVisible();
		} else
		{
			isVisible = false;
			onInvisible();
		}
	}

	/**
	 * 可见
	 */
	protected void onVisible()
	{
		lazyLoad();
	}

	/**
	 * 不可见
	 */
	protected void onInvisible()
	{
	}

	/**
	 * 延迟加载 子类必须重写此方法(每一次可见都会调用)
	 */
	protected abstract void lazyLoad();

	public abstract void turnToTab(int tab);

}
