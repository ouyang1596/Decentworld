/**
 * 
 */
package cn.sx.decentworld.service;

import cn.sx.decentworld.utils.LogUtils;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

/**
 * @ClassName: TestService.java
 * @Description:
 * @author: cj
 * @date: 2016年1月21日 下午4:28:40
 */
public class TestService extends Service
{
    private static final String TAG = "TestService";

    @Override
    public IBinder onBind(Intent intent)
    {
        LogUtils.i(TAG, "onBind");
        return null;
    }

    class MyBinder extends Binder
    {
        /**
         * 构造函数
         */
        public MyBinder()
        {
            LogUtils.i(TAG, "onBind");
        }
    }

    @Override
    public void onCreate()
    {

        super.onCreate();
        LogUtils.i(TAG, "onCreate");
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    Thread.sleep(2000);
                    LogUtils.i(TAG, "alive...>");
                }
                catch (InterruptedException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        LogUtils.i(TAG, "onStartCommand");
        flags = START_STICKY;
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy()
    {
        LogUtils.i(TAG, "onDestroy");
        super.onDestroy();
    }
    
    public void test()
    {
    }
    

    @Override
    public void onTrimMemory(int level)
    {
        // TODO Auto-generated method stub
        super.onTrimMemory(level);
    }

}
