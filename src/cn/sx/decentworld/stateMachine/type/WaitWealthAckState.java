/**
 * 
 */
package cn.sx.decentworld.stateMachine.type;

import org.simple.eventbus.EventBus;

import android.os.Handler;
import android.os.Message;
import cn.sx.decentworld.common.NotifyByEventBus;
import cn.sx.decentworld.entity.db.DWMessage;
import cn.sx.decentworld.entity.db.DWMessage.SendStatus;
import cn.sx.decentworld.logSystem.LogUtils;
import cn.sx.decentworld.stateMachine.MsgContext;
import cn.sx.decentworld.stateMachine.MsgState;
import cn.sx.decentworld.stateMachine.StateConfig;
import cn.sx.decentworld.stateMachine.param.RefreshParam;

/**
 * @ClassName: StartSendState.java
 * @Description: 【2号状态】
 * @author: cj
 * @date: 2016年4月27日 下午3:50:58
 */
public class WaitWealthAckState extends MsgState
{
    private static final String TAG = "WaitWealthAckState";

    /**
     * 开始发送消息
     */
    @Override
    public void startSend(String packetID)
    {
        LogUtils.d(TAG, "startSend() params[packetID=" + packetID + ",info=do nothing" + "]");
        // TODO nothing

    }

    private Handler handler;

    /**
     * 等待wealth回执
     */
    @Override
    public void waitWealthAck(final String packetID)
    {
        LogUtils.d(TAG, "waitWealthAck() params[packetID=" + packetID + "]");
        // 处理等待的业务逻辑
        // 通知界面刷新状态
//        final DWMessage dwMessage = MsgContext.getDWMessage(packetID);
//        EventBus.getDefault().post(new RefreshParam(packetID , SendStatus.getType(dwMessage.getSendSuccess())), NotifyByEventBus.NT_REFRESH_MSG_LIST);
        // 休眠 WAIT_WEALTH_TIME 等待wealth回执
        handler = new Handler()
        {
            public void handleMessage(android.os.Message msg)
            {
                String packetID_ = msg.obj.toString();
                LogUtils.d(TAG, "waitWealthAck() 进入handler，packetID_=" + packetID_);
                DWMessage dwMessage = MsgContext.getDWMessage(packetID_);
                afterSleep(dwMessage, packetID_);
            };
        };
//        handler.sendEmptyMessageDelayed(0x110, StateConfig.WAIT_WEALTH_TIME);
        Message msg = Message.obtain();
        msg.obj = packetID;
        handler.sendMessageDelayed(msg, StateConfig.WAIT_WEALTH_TIME);
    }

    private void afterSleep(DWMessage dwMessage, String packetID)
    {
        // 判断是否收到wealth回执
        LogUtils.d(TAG, "afterSleep() params[packetID=" + packetID + "," + dwMessage.toString());
        if (dwMessage.getSendSuccess() == DWMessage.SendStatus.SENDING.getIndex())
        {
            // 没有收到回执,进入【4号状态】
            LogUtils.d(TAG, "waitWealthAck() params[packetID=" + packetID + ",info=" + "no wealth ack" + "]");
            super.msgContext.setMsgState(MsgContext.queryWealthAckState);
            super.msgContext.queryWealthAck(packetID);
        }
        else if (dwMessage.getSendSuccess() == DWMessage.SendStatus.FAILURE.getIndex())
        {
            LogUtils.d(TAG, "waitWealthAck() params[packetID=" + packetID + ",info=" + "DWMessage.SendStatus.FAILURE.getIndex" + "]");
            super.msgContext.setMsgState(MsgContext.endFailureState);
            super.msgContext.endFailure(packetID);
        }
        else if (dwMessage.getSendSuccess() == DWMessage.SendStatus.SUCCESS.getIndex() || dwMessage.getSendSuccess() == DWMessage.SendStatus.SUCCESS_FINISH.getIndex()
                || dwMessage.getSendSuccess() == DWMessage.SendStatus.FAILURE_LACK_WEALTH.getIndex())
        {
            // 收到了回执,进入【3号状态】
            LogUtils.d(TAG, "waitWealthAck() params[packetID=" + packetID + ",info=" + "receive wealth ack" + "]");
            super.msgContext.setMsgState(MsgContext.endSuccessState);
            super.msgContext.endSuccess(packetID);
        }
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
        LogUtils.d(TAG, "sendFailResend() params[packetID=" + packetID + ",info=do nothing" + "]");
        // TODO nothing

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
