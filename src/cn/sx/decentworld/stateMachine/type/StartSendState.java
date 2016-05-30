/**
 * 
 */
package cn.sx.decentworld.stateMachine.type;

import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.packet.Message;

import cn.sx.decentworld.engine.ChatSingleEngine;
import cn.sx.decentworld.entity.db.DWMessage;
import cn.sx.decentworld.logSystem.LogUtils;
import cn.sx.decentworld.stateMachine.MsgContext;
import cn.sx.decentworld.stateMachine.MsgState;

/**
 * @ClassName: StartSendState.java
 * @Description: 【1号状态】开始发送消息状态
 * @author: cj
 * @date: 2016年4月27日 下午3:50:58
 */
public class StartSendState extends MsgState
{
    private static final String TAG = "StartSendState";

    public StartSendState()
    {

    }

    /**
     * 构造函数
     * 
     * @param dwMessage
     *            需要同步的消息
     */
    public StartSendState(DWMessage dwMessage)
    {
        LogUtils.d(TAG, "StartSendState() " + dwMessage.toString());
        MsgContext.addDWMessag(dwMessage);
    }

    /**
     * 开始发送消息
     */
    @Override
    public void startSend(String packetID)
    {
        LogUtils.d(TAG, "startSend() params[packetID="+packetID+"]");
        //从MsgContext拿取消息处理
        DWMessage dwMessage = MsgContext.getDWMessage(packetID);
        if(dwMessage == null)
            LogUtils.e(TAG, "startSend() the result is null,params[packetID="+packetID+"]");
        else
        {
            ChatSingleEngine chatSingleHelper = ChatSingleEngine.getInstance();
            try
            {
                // 构造Message
                Message message = chatSingleHelper.createXmppMessage(dwMessage.getTo());
                message.setPacketID(dwMessage.getTxtMsgID());
                LogUtils.d(TAG, "startSend() "+message.toString());
                chatSingleHelper.sendTextMessage(dwMessage,message);
                //进入【2号状态】
                super.msgContext.setMsgState(MsgContext.waitWealthAckState);
                super.msgContext.getMsgState().waitWealthAck(packetID);
            }
            catch (NotConnectedException e)
            {
                //进入【6号状态】
                super.msgContext.setMsgState(MsgContext.endFailureState);
                super.msgContext.getMsgState().endFailure(packetID);
            }
        }

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
