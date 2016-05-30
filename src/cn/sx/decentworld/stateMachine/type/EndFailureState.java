/**
 * 
 */
package cn.sx.decentworld.stateMachine.type;

import org.simple.eventbus.EventBus;

import cn.sx.decentworld.common.NotifyByEventBus;
import cn.sx.decentworld.entity.db.DWMessage;
import cn.sx.decentworld.logSystem.LogUtils;
import cn.sx.decentworld.stateMachine.MsgContext;
import cn.sx.decentworld.stateMachine.MsgState;
import cn.sx.decentworld.stateMachine.param.RefreshParam;

/**
 * @ClassName: StartSendState.java
 * @Description: 【6号状态】开始发送消息状态
 * @author: cj
 * @date: 2016年4月27日 下午3:50:58
 */
public class EndFailureState extends MsgState
{
    private static final String TAG = "StartSendState";

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
        // 刷新界面
        DWMessage dwMessage = MsgContext.getDWMessage(packetID);
        dwMessage.setSendSuccess(DWMessage.SendStatus.FAILURE.getIndex());
        dwMessage.save();
        EventBus.getDefault().post(new RefreshParam(packetID , DWMessage.SendStatus.FAILURE), NotifyByEventBus.NT_REFRESH_MSG_LIST);
        //结束状态机
        LogUtils.d(TAG, "endFailure() params[packetID=" + packetID + ",info="+"状态机结束]");
        //将消息从map中移除(暂时是移除，将来要手动触发时，将消息传递进来)
        MsgContext.removeDWMessage(packetID);
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
