package cn.sx.decentworld;

import java.io.IOException;

import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.XMPPException.StreamErrorException;
import org.jivesoftware.smack.packet.StreamError;
import org.jivesoftware.smackx.ping.PingFailedListener;
import org.simple.eventbus.EventBus;

import cn.sx.decentworld.bean.NotifyByEventBus;
import cn.sx.decentworld.common.XmppHelper;
import cn.sx.decentworld.utils.LogUtils;
import cn.sx.decentworld.utils.NetworkUtils;

/**
 * 
 * @ClassName: ViConnectionListener.java
 * @Description: 目前仅用于监听挤下线的消息和网络断开（网络切换、服务器停机）
 * @author: cj
 * @date: 2016年1月19日 下午7:46:10
 */
public class ViConnectionListener implements ConnectionListener, PingFailedListener
{
    private static final String TAG = "ViConnectionListener";

    /**
     * 关闭连接（情况包括：正常关闭、重连遇到错误） packetReader.shutdown()调用
     */
    @Override
    public void connectionClosed()
    {
        LogUtils.i(TAG, "connectionClosed");
        // 此处也要加页面提示
        EventBus.getDefault().post(NetworkUtils.CONNECTION_CLOSED, NotifyByEventBus.NT_NETWORK_STATE_OF);
    }

    /**
     * 可能情况：被挤下线；IOException
     */
    @Override
    public void connectionClosedOnError(Exception e)
    {
        LogUtils.i(TAG, "connectionClosedOnError..." + e.toString());
        // 被挤下线，则将消息路由到MainActivity，弹出提示对话框
        if (e instanceof StreamErrorException)
        {
            LogUtils.i(TAG, "connectionClosedOnError...Conflict");
            StreamErrorException xmppEx = (StreamErrorException) e;
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
                    return;
                }
            }
        }

        // 发生IO异常时将消息路由到,可能情况是 1.网络断开 2.网络切换时
        if (e instanceof IOException)
        {
            LogUtils.i(TAG, "connectionClosedOnError...IOException");
            // 出现IOException异常，系统会自动重连，这时可以在界面做做一个提示，但不要影响用户体验；
            EventBus.getDefault().post(NetworkUtils.CONNECTION_CLOSED_ERROR, NotifyByEventBus.NT_NETWORK_STATE_OF);
        }
    }

    /**
     * 重连中
     */
    @Override
    public void reconnectingIn(int seconds)
    {
        LogUtils.i(TAG, "reconnectingIn");
        EventBus.getDefault().post(NetworkUtils.RECONNECTING_IN, NotifyByEventBus.NT_NETWORK_STATE_OF);
    }

    /**
     * 重连成功
     */
    @Override
    public void reconnectionSuccessful()
    {
        LogUtils.i(TAG, "reconnectionSuccessful");
        // 重连成功后发送上线的通知
        XmppHelper.sendPresence();
    }

    /**
     * 重连失败
     */
    @Override
    public void reconnectionFailed(Exception e)
    {
        LogUtils.i(TAG, "reconnectionFailed,Exception=" + e.toString());
        // 连接错误，请手动重连
        EventBus.getDefault().post(NetworkUtils.RECONNECTION_FAILED, NotifyByEventBus.NT_NETWORK_STATE_OF);
    }

    /**
     * 连接成功
     */
    @Override
    public void connected(XMPPConnection connection)
    {
        LogUtils.i(TAG, "connected...连接成功");
    }

    /**
     * 授权成功
     */
    @Override
    public void authenticated(XMPPConnection connection)
    {
        LogUtils.i(TAG, "authenticated...服务器授权成功");
        XmppHelper.sendPresence();
    }

    /**
     * 检测与服务器的连接
     */
    @Override
    public void pingFailed()
    {
        

    }
}
