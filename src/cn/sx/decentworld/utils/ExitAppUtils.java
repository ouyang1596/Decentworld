package cn.sx.decentworld.utils;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;

/**
 * 
 * @ClassName: ExitAppUtils.java
 * @Description: 管理app的activity界面，方便程序退出时自动清理所有的Activity
 * @author: cj
 * @date: 2015年9月5日 下午6:50:13
 */
public class ExitAppUtils
{
	private static final String TAG = "ExitAppUtils";
	
	/**
	 * 转载Activity的容器
	 */
	public List<Activity> mActivityList = new LinkedList<Activity>();
	private static ExitAppUtils instance = new ExitAppUtils();

	/**
	 * 将构造函数私有化
	 */
	private ExitAppUtils()
	{
	};

	/**
	 * 获取ExitAppUtils的实例，保证只有一个ExitAppUtils实例存在
	 * 
	 * @return
	 */
	public static ExitAppUtils getInstance()
	{
		return instance;
	}

	/**
	 * 添加Activity实例到mActivityList中，在onCreate()中调用
	 * 
	 * @param activity
	 */
	public void addActivity(Activity activity)
	{
		mActivityList.add(activity);
	}

	/**
	 * 从容器中删除多余的Activity实例，在onDestroy()中调用
	 * 
	 * @param activity
	 */
	public void delActivity(Activity activity)
	{
		mActivityList.remove(activity);
	}
	
	/**
	 * 根据activity所在的位置，从容器中删除多余的Activity实例
	 * @param position 
	 */
	public void delActivity_position(int position){
		mActivityList.remove(position);
	}

	/**
	 * 退出程序的方法
	 * 将所有的Acitvity finish,然后退出
	 */
	public void exit()
	{
		for (Activity activity : mActivityList)
		{
			activity.finish();
		}
		System.exit(0);
	}

	//退出返回到登录界面
	public void loginout()
	{
		for (Activity activity : mActivityList)
		{
			if (!activity.getLocalClassName().equals("LoginActivity"))
			{
				activity.finish();
			}
		}
	}

	public List<Activity> getActivityList()
	{
		return mActivityList;
	}

	public void deleteByname(String ac)
	{
		for (Activity activity : mActivityList)
		{
			if (activity.getLocalClassName().equals(ac))
			{
				activity.finish();
				break;
			}
		}
	}
	
	
	//销毁所有页面回到主界面
	public void toMainActivity(String className)
	{
		for (Activity activity : mActivityList)
		{
			//格式
			//activity.MainActivity_
			//activity.SearchActivity_
			LogUtils.i(TAG, "此时："+activity.getLocalClassName());
			if (!activity.getLocalClassName().equals(className))
			{
				activity.finish();
			}
		}
	}
}
