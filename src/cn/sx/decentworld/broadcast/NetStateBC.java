/**
 * 
 */
package cn.sx.decentworld.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;
import cn.sx.decentworld.listener.NetChangeListener;
import cn.sx.decentworld.utils.LogUtils;

/**
 * @ClassName: NetStatReceiver.java
 * @Description: 接收网络状态的改变
 * @author: cj
 * @date: 2015年12月22日 上午10:42:03
 */
public class NetStateBC extends BroadcastReceiver
{
    private static final String TAG = "NetStatReceiver";
    //监听器
    private NetChangeListener mChangeListener;

    /**
     * 设置监听
     * @param listener
     */
    public void setOnNetChangeListener(NetChangeListener listener)
    {
        this.mChangeListener = listener;
    }

    /**
     * 接收到网络切换的广播入口
     */
    @Override
    public void onReceive(Context context, Intent intent)
    {
        State wifiState = null;
        State mobileState = null;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        wifiState = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
        mobileState = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
        if (wifiState != null && mobileState != null && State.CONNECTED != wifiState && State.CONNECTED == mobileState)
        {
            // 手机网络连接成功（模拟器是进入此处）
            LogUtils.i(TAG, "4G network connected");
            if (null != mChangeListener)
            {
                mChangeListener.onNetConnected();
            }
        }
        else if (wifiState != null && mobileState != null && State.CONNECTED != wifiState && State.CONNECTED != mobileState)
        {
            // 手机没有任何的网络
            LogUtils.i(TAG, "network disconnected");
            if (null != mChangeListener)
            {
                mChangeListener.onNetDisconnected();
            }
        }
        else if (wifiState != null && State.CONNECTED == wifiState)
        {
            // 无线网络连接成功
            LogUtils.i(TAG, "wifi connected");
            if (null != mChangeListener)
            {
                mChangeListener.onNetConnected();
            }
        }
    }
}
