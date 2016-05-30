/**
 * 
 */
package cn.sx.decentworld.stateMachine.type;

import org.simple.eventbus.EventBus;

import cn.sx.decentworld.common.NotifyByEventBus;
import cn.sx.decentworld.connect.XmppHelper;
import cn.sx.decentworld.entity.db.DWMessage;
import cn.sx.decentworld.listener.LoginListener;
import cn.sx.decentworld.logSystem.LogUtils;
import cn.sx.decentworld.stateMachine.MsgContext;
import cn.sx.decentworld.stateMachine.MsgState;
import cn.sx.decentworld.stateMachine.param.RefreshParam;
import cn.sx.decentworld.utils.NetworkUtils;
import cn.sx.decentworld.utils.ToastUtil;

/**
 * @ClassName: StartSendState.java
 * @Description: 【5号状态】
 * @author: cj
 * @date: 2016年4月27日 下午3:50:58
 */
public class SendFailResendState extends MsgState
{
    private static final String TAG = "SendFailResendState";


    /**
     * 开始发送消息
     */
    @Override
    public void startSend(String packetID)
    {
        LogUtils.d(TAG, "startSend() params[packetID=" + packetID + ",info=do nothing" + "]");
        // TODO nothing

    }

    /**
     * 等待wealth回执
     */
    @Override
    public void waitWealthAck(String packetID)
    {
        LogUtils.d(TAG, "waitWealthAck() params[packetID=" + packetID + ",info=do nothing" + "]");
        // TODO nothing

    }

    /**
     * 等待时间内接收到wealth回执
     */
    @Override
    public void endSuccess(String packetID)
    {
        LogUtils.d(TAG, "endSuccess() params[packetID=" + packetID + ",info=do nothing" + "]");
        // TODO nothing

    }

    /**
     * 等待时间内没有收到服务器wealth回执，查询
     */
    @Override
    public void queryWealthAck(String packetID)
    {
        LogUtils.d(TAG, "queryWealthAck() params[packetID=" + packetID + ",info=do nothing" + "]");
        // TODO nothing

    }

    /**
     * 查询结果是服务器没有收到1号消息
     */
    @Override
    public void sendFailResend(String packetID)
    {
//        send_fail_resend_count++;
        int[] is = MsgContext.countMap.get(packetID);
        is[2] = is[2]+1;
        LogUtils.d(TAG, "sendFailResend() params[packetID=" + packetID + ",send_fail_resend_count=" + is[2] + "]");
        if (is[2] > 3)
        {
            // 重发超过三次
            LogUtils.d(TAG, "sendFailResend() params[packetID=" + packetID + ",send failure]");
            //通知界面显示网络错误
            EventBus.getDefault().post(NetworkUtils.RECONNECTING_IN, NotifyByEventBus.NT_NETWORK_STATE_OF);
            //重连
            XmppHelper.reConnect(new LoginListener()
            {
                @Override
                public void onLoginSuccess(String info)
                {
                    //界面显示连接成功
                    LogUtils.w(TAG, "sendFailResend() reConnect新建连接成功");
                    ToastUtil.showToast("新建连接成功");
                }
                
                @Override
                public void onLoginFailure(Exception exception)
                {
                    //界面显示连接失败
                    ToastUtil.showToast("新建连接失败");
                    EventBus.getDefault().post(NetworkUtils.RECONNECTION_FAILED, NotifyByEventBus.NT_NETWORK_STATE_OF);
                }
            }, true);
            // 进入【6号状态】
            super.msgContext.setMsgState(MsgContext.endFailureState);
            super.msgContext.endFailure(packetID);
        }
        else
        {
            // resend
            LogUtils.d(TAG, "sendFailResend() params[packetID=" + packetID + ",resend]");
            // 进入【1号状态】
            super.msgContext.setMsgState(MsgContext.startSendState);
            super.msgContext.startSend(packetID);
        }
    }

    /**
     * 发送失败，用户可手动重发
     */
    @Override
    public void endFailure(String packetID)
    {
        LogUtils.d(TAG, "endFailure() params[packetID=" + packetID + ",info=do nothing" + "]");
        // TODO nothing

    }

    /**
     * 服务器无响应，用户可手动重发
     */
    @Override
    public void endServerNoResponse(String packetID)
    {
        LogUtils.d(TAG, "endServerNoResponse() params[packetID=" + packetID + ",info=do nothing" + "]");
        // TODO nothing
    }

}
