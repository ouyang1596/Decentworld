/**
 * 
 */
package cn.sx.decentworld.stateMachine;

import cn.sx.decentworld.entity.db.DWMessage.SendStatus;

/**
 * @ClassName: State.java
 * @Description: 状态
 * @author: cj
 * @date: 2016年4月26日 上午9:29:06
 */
public abstract class MsgState
{
    private static final String TAG = "MsgState";

    public MsgContext msgContext;

    public void setMsgContext(MsgContext msgContext)
    {
        this.msgContext = msgContext;
    }

    // 【1号状态】开始发送消息状态；
    public abstract void startSend(String packetID);

    // 【2号状态】等待服务器wealth回执状态，等待时间为 WAIT_WEALTH_TIME；
    public abstract void waitWealthAck(String packetID);

    // 【3号状态】规定 WAIT_WEALTH_TIME 内收到服务器的wealth回执，成功，结束状态机；
    public abstract void endSuccess(String packetID);

    // 【4号状态】规定 WAIT_WEALTH_TIME 内没有收到服务器的wealth回执，进入查询wealth回执的状态；
    public abstract void queryWealthAck(String packetID);

    // 【5号状态】查询结果为：服务器没有收到了1号消息，进入发送失败重发状态；
    public abstract void sendFailResend(String packetID);

    // 【6号状态】发送3次失败后结束状态机，标记为失败状态；
    public abstract void endFailure(String packetID);// /用户可手动点击进入1号状态

    // 【7号状态】调用接口3次，服务器无响应后，进入服务器无响应状态；
    public abstract void endServerNoResponse(String packetID);// /用户可手动点击进入4号状态
}
