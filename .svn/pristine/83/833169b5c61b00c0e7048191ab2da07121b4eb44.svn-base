/**
 * 
 */
package cn.sx.decentworld.utils;

/**
 * @ClassName: ThreadUtils.java
 * @Description:
 * @author: cj
 * @date: 2015年12月3日 上午9:38:35
 */
public class ThreadUtils
{
	private static final String TAG = "ThreadUtils";
	public static void printAllThreads()
	{
		Thread ta[] = new Thread[Thread.activeCount()];
		Thread.enumerate(ta);
		LogUtils.i(TAG, "Active thread's number:" + ta.length);
		for (Thread t : ta)
		{
			if (t != null)
				LogUtils.i(TAG, "Thread's name:" + t.getName() + " id:" + t.getId());
		}
	}
}
