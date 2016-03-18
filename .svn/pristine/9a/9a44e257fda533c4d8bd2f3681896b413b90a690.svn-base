/**
 * 
 */
package cn.sx.decentworld.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;
import cn.sx.decentworld.utils.LogUtils;

/**
 * @ClassName: NetStatReceiver.java
 * @Description: 接收网络状态的改变
 * @author: cj
 * @date: 2015年12月22日 上午10:42:03
 */
public class NetStateReceiver extends BroadcastReceiver {
    private static final String TAG = "NetStatReceiver";
    //public static final String NET_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";
    private OnNetChangeListener mChangeListener;

    public void setOnNetChangeListener(OnNetChangeListener listener) {
        this.mChangeListener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        //		if (intent.getAction().equals(NET_ACTION))
        //		{
        //			LogUtils.i(TAG, "网络发生变化了");
        //			// 有网络变化
        //			// Intent中ConnectivityManager.EXTRA_NO_CONNECTIVITY这个关键字表示着当前是否连接上了网络
        //			boolean isBreak = intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);
        //			if (!isBreak)
        //			{
        //				// 有网络
        //				// 3G跟WIFI切换的时候，会收到三个这样的广播，第一个广播把wifi连接上，第二个广播到3G关闭掉，第三个广播把wifi连接上。但是我这里只想提醒用户一次啊，怎么把另外两次提醒屏蔽掉呢？谁有好的方法，android方面着手的，或者java方面着手的。
        //			}
        //			else
        //			{
        //
        //			}
        //		}
        
        LogUtils.i(TAG, "");

        State wifiState = null;
        State mobileState = null;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        wifiState = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
        mobileState = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
        if (wifiState != null && mobileState != null && State.CONNECTED != wifiState && State.CONNECTED == mobileState) {
            // 手机网络连接成功  
            LogUtils.i(TAG, "4G network connected");
            if (null != mChangeListener) {
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
