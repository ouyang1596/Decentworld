/**
 * 
 */
package cn.sx.decentworld.utils;

import java.util.HashMap;
import java.util.Map;

import org.jivesoftware.smack.ReconnectionManager;

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

    /**
     * 收集线程信息
     * @return
     */
    public static Map<String, String> getThreadInfo()
    {
        Map<String, String> map = new HashMap<String, String>();
        Thread ta[] = new Thread[Thread.activeCount()];
        Thread.enumerate(ta);
        for (Thread t : ta)
        {
            if (t != null)
            {
                map.put(t.getName(), t.getId()+"");
                LogUtils.i(TAG, "Thread's name:" + t.getName() + " id:" + t.getId());
            }
        }
        return map;
    }
    
    
    /**
     * 判断重连线程是否存在
     * @return
     */
    public static boolean isExitOfReconnectThread()
    {
        boolean isExit = false;
        Map<String, String> map = new HashMap<String, String>();
        Thread ta[] = new Thread[Thread.activeCount()];
        Thread.enumerate(ta);
        for (Thread t : ta)
        {
            if(t != null)
            {
                LogUtils.d(TAG, "The thread name is:"+t.getName());
            }
            if (t != null && t.getName().equals(ReconnectionManager.ReconnectThread))
            {
                isExit = true;
            }
        }
        return isExit;
    }

}
