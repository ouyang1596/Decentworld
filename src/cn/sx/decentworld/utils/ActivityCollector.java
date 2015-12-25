package cn.sx.decentworld.utils;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
/**
 * 
 * @ClassName: ActivityCollector.java
 * @Description: 随时随地退出程序,该类中有三个静态方法，
 * 可在BaseActivity的onCreate（）中加入ActivityCollector.addActivity(this);
 * 可在BaseActivity的onDestroy（）中加入ActivityCollector.removeActivity(this);
 * 从此以后，不管你想在什么地方退出程序，只需要调用ActivityCollector.finishAll()方法就可以了。
 * @author: Jackchen
 * @date: 2015年6月30日 下午9:22:38
 */
public class ActivityCollector
{
	public static List<Activity> activities = new ArrayList<Activity>();
	
	public static void clear()
	{
		activities.clear();
	}

	public static void addActivity(Activity activity)
	{
		activities.add(activity);
	}

	public static void removeActivity(Activity activity)
	{
		activities.remove(activity);
	}

	public static void finishAll()
	{
		for (Activity activity : activities)
		{
			if (!activity.isFinishing())
			{
				activity.finish();
			}
		}
	}
}
