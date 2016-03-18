/**
 * 
 */
package cn.sx.decentworld.service;

import java.util.Collection;

import org.jivesoftware.smack.PacketCollector;
import org.jivesoftware.smack.XMPPConnection;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.task.CollectorThreadStarter;
import cn.sx.decentworld.utils.LogUtils;

/**
 * @ClassName: PackageListenerService.java
 * @Description:消息监听服务
 * @author: cj
 * @date: 2015年9月22日 下午4:05:47
 */
public class PacketListenerService extends Service {
	public static final String TAG = "PacketListenerService";
	private XMPPConnection con;
	private static final PacketListenerService instance = new PacketListenerService();

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		LogUtils.i(TAG, "onCreate");
		con = DecentWorldApp.getInstance().getConnectionImpl();
		if (con.isConnected()) {
			LogUtils.i(Constants.TAG,
					"con is connected,开启一个Packet监听的服务...onStartCommand");
			startCollectorHandlerThread();
		}
	}

	@Override
	public void onDestroy() {
		LogUtils.i(TAG, "关闭一个Packet监听的服务...onDestroy");
		super.onDestroy();
	}

	/**
	 * 开启线程监听消息线程
	 */
	private void startCollectorHandlerThread() {
		Collection<PacketCollector> collectors = con.getPacketCollectors();
		Context context = getApplicationContext();
		for (PacketCollector packetCollector : collectors) {
			new CollectorThreadStarter(packetCollector, context).start();
		}
	}
}
