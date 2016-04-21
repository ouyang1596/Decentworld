/**
 * 
 */
package cn.sx.decentworld.utils;

import java.util.List;

import cn.sx.decentworld.logSystem.LogUtils;
import android.app.ActivityManager;
import android.content.Context;

/**
 * @ClassName: ServiceUtils.java
 * @Description: 对Android 的服务Service进行操作的工具类
 * @author: cj
 * @date: 2015年12月3日 上午8:11:00
 */
public class ServiceUtils
{
	private static final String TAG  = "ServiceUtils";
	/**
	 * 判断服务是否运行
	 * @param context
	 * @param serviceName
	 * @return
	 */
	public static boolean isServiceRunning(Context context,String serviceName)
	{
		boolean isRunning = false;
		ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningServiceInfo>  list = activityManager.getRunningServices(30);
		if(!(list.size()>0))
		{
			LogUtils.i(TAG, "系统运行的服务为0个");
			return false;
		}
		for(int i =0 ;i<list.size();i++)
		{
			LogUtils.i(TAG, "系统运行的服务有，第"+i+" "+list.get(i).service.getClassName());
			if(list.get(i).service.getClassName().equals(serviceName)==true)
			{
				isRunning = true;
				break;
			}
		}
		return isRunning;
	}
}
