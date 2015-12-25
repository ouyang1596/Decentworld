package cn.sx.decentworld.common;

import java.util.Stack;

import cn.sx.decentworld.utils.LogUtils;

import android.app.Activity;
import android.util.Log;

/**
 * activity堆栈式管理
 *
 * @author FireAnt（http://my.oschina.net/LittleDY）
 * @created 2014年10月30日 下午6:22:05
 * @date 2015.11.06 添加，来源就不改了
 */
public class AppManager {

    private static Stack<Activity> activityStack;
    private static AppManager instance;

    private AppManager() {
    }

    /**
     * 单一实例
     */
    public static AppManager getAppManager() {
        if (instance == null) {
            instance = new AppManager();
        }
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        return instance;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity currentActivity() {
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activityStack == null || activityStack.isEmpty()) {
            return;
        }
        if (activity != null && !activity.isFinishing()) {
            if (activityStack != null && !activityStack.isEmpty()) {
                activityStack.remove(activity);
            }
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        if (activityStack == null || activityStack.isEmpty()) {
            return;
        }
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
                break;
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        if (activityStack == null || activityStack.isEmpty()) {
            return;
        }
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                finishActivity(activityStack.get(i));
                break;
            }
        }
        activityStack.clear();
    }

    /**
     * 获取指定的Activity
     *
     * @author kymjs
     */
    public static Activity getActivity(Class<?> cls) {
        if (activityStack != null)
            for (Activity activity : activityStack) {
                if (activity.getClass().equals(cls)) {
                    return activity;
                }
            }
        return null;
    }

    /**
     * 退出应用程序
     */
    public void AppExit() {
        LogUtils.d(AppManager.class.getName(), "AppExit  called");
        try {
            finishAllActivity();
            // 杀死该应用进程
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        } catch (Exception e) {
            Log.e(AppManager.class.getName(), "退出出错！", e);
        }
    }
    
    /**
     * 退出登录，finish掉所有Activity，除了登录界面；（有点问题，暂时不能用）
     */
	public void loginout()
	{
		if (activityStack == null || activityStack.isEmpty())
		{
			return;
		}
		for (int i = 0, size = activityStack.size(); i < size; i++)
		{
			if (null != activityStack.get(i) && !activityStack.get(i).getLocalClassName().equals("LoginActivity"))
			{
				finishActivity(activityStack.get(i));
				break;
			}
		}
		activityStack.clear();
	}

	
	
	
}