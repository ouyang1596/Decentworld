/**
 * 
 */
package cn.sx.decentworld.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import cn.sx.decentworld.utils.LogUtils;

/**
 * @ClassName: PackageListenerService.java
 * @Description:消息监听服务
 * @author: cj
 * @date: 2015年9月22日 下午4:05:47
 */
public class PacketListenerService extends Service
{
    public static final String TAG = "PacketListenerService";
    public int a = 5;

    /**
     * Binder
     */
    public class MyBinder extends Binder
    {
        /**
         * 返回服务对象
         * 
         * @return
         */
        public PacketListenerService getService()
        {
            LogUtils.i(TAG, "getService");
            return PacketListenerService.this;
        }
    }

    // /////////////////////////////////////////服务的生命周期////////////////////////////////
    /**
     * 创建服务
     */
    @Override
    public void onCreate()
    {
        super.onCreate();
        LogUtils.i(TAG, "onCreate");
    }

    /**
     * 绑定服务
     */
    @Override
    public IBinder onBind(Intent intent)
    {
        LogUtils.i(TAG, "onBind");
        return new MyBinder();
    }

    @Override
    public void onStart(Intent intent, int startId)
    {
        super.onStart(intent, startId);
        LogUtils.i(TAG, "onStart");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        LogUtils.i(TAG, "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 销毁服务
     */
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        LogUtils.i(TAG, "关闭一个Packet监听的服务...onDestroy");
    }

    // /////////////////////////////////////////服务的对外公开的方法////////////////////////////////

    
    ////////////////////////////////////////////////////////////XMPPConnection////////////////////////

    
    
//    public XMPPTCPConnection getConnection()
//    {
//        return getConnection(null);
//    }
    
//    public XMPPTCPConnection getConnection(CommCallback callback)
//    {
//
//    }
    
//    public void sendPacket(final Packet packet)
//    {
//      
//    }
    
//    public void closeConnection()
//    {
//
//    }
//    
//    
//    public void sendPresence()
//    {
//       
//    }
//    
    
    
    
    

    // /////////////////////////////////////////服务的私有方法////////////////////////////////

}
