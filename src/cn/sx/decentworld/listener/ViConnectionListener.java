package cn.sx.decentworld.listener;

import java.io.IOException;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.StreamError;
import org.simple.eventbus.EventBus;

import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.bean.NotifyByEventBus;
import cn.sx.decentworld.utils.LogUtils;

/**
 * 
 * @ClassName: ViConnectionListener.java
 * @Description: 目前仅用于监听挤下线的消息
 * @author: cj
 * @date: 2016年1月19日 下午7:46:10
 */
public class ViConnectionListener implements ConnectionListener
{
	private static final String TAG = "ViConnectionListener";
	/**
	 * 关闭连接
	 */
	@Override
	public void connectionClosed()
	{
		LogUtils.i(TAG, "connectionClosed");
		//此处也要加页面提示
	}

	/**
	 * 发生错误时关闭连接 错误的情况有： 1.被挤下线 2.网络断开
	 */
	@Override
	public void connectionClosedOnError(Exception e)
	{
		LogUtils.i(TAG, "connectionClosedOnError..."+e.toString());
		if (e instanceof XMPPException)
		{
			XMPPException xmppEx = (XMPPException) e;
			StreamError error = xmppEx.getStreamError();
			// Make sure the error is not null
			if (error != null)
			{
				String reason = error.getCode();
				if ("conflict".equals(reason))
				{
					LogUtils.i(TAG, "被挤下线");
					// 将消息路由到MainActivity
					EventBus.getDefault().post("该账号在异地登陆，即将退出！", NotifyByEventBus.NT_CRUSH_OFF_LINE);
				}
			}
		}
		/** 发生IO异常时将消息路由到 **/
		if(e instanceof IOException){
			EventBus.getDefault().post("连接断开，即将重连，或手动点击重连！", NotifyByEventBus.NT_OFF_LINE);
		}
	}

	/**
	 * 重连中
	 */
	@Override
	public void reconnectingIn(int seconds)
	{
		LogUtils.i(TAG, "reconnectingIn");
	}
	

	/**
	 * 重连成功
	 */
	@Override
	public void reconnectionSuccessful()
	{
		LogUtils.i(TAG, "reconnectionSuccessful");
//		EventBus.getDefault().post("重连成功！", NotifyByEventBus.NT_RECONNECT);
		//此处应写代码提示
	}

	/**
	 * 重连失败
	 */
	@Override
	public void reconnectionFailed(Exception e)
	{
		LogUtils.i(TAG, "reconnectionFailed");
//		sendEmptyMessage(REQUEST_RECONNECT);
	}


}
