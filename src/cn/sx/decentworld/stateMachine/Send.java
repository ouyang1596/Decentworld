/**
 * 
 */
package cn.sx.decentworld.stateMachine;

import cn.sx.decentworld.entity.db.DWMessage;
import cn.sx.decentworld.logSystem.LogUtils;
import cn.sx.decentworld.stateMachine.type.StartSendState;

/**
 * @ClassName: Send.java
 * @Description: 发送消息状态
 * @author: cj
 * @date: 2016年4月25日 下午9:36:01
 */
public class Send
{
    private static final String TAG = "Send";
    private MsgContext context;
    private DWMessage dwMessage;

    /**
     * 构造发送函数状态机
     */
    public Send(DWMessage dwMessage)
    {
        LogUtils.d(TAG, "Send() "+ dwMessage.toString());
        this.context = new MsgContext();
        this.context.setMsgState(new StartSendState(dwMessage));
        this.dwMessage = dwMessage;
    }
    
    /**
     * 执行状态机
     */
    public void execute()
    {
        LogUtils.d(TAG, "execute() "+ dwMessage.toString()+",Thread:"+Thread.currentThread().getName());
        Send.this.context.startSend(Send.this.dwMessage.getTxtMsgID());
    }
}
