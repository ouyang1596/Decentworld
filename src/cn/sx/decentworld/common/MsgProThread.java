/**
 * 
 */
package cn.sx.decentworld.common;

import cn.sx.decentworld.bean.DWMessage;
import cn.sx.decentworld.listener.MsgProcessListener;
import cn.sx.decentworld.logSystem.LogUtils;
import cn.sx.decentworld.utils.TimeUtils;

/**
 * @ClassName: MsgProThread.java
 * @Description: 消息处理线程，
 * @author: cj
 * @date: 2016年1月5日 下午7:07:45
 */
public class MsgProThread extends Thread
{
    private static final String TAG = "MsgProThread";
    // 默认延迟时间----6s
    private static final long defaultDelayTime = 6000;
    private DWMessage dwMessage;
    private MsgProcessListener msgProCallBack;
    // 延迟时间
    private long delayTime;

    /**
     * 构造函数
     * 
     * @param dwMessage
     * @param msgProCallBack
     */
    public MsgProThread(DWMessage dwMessage, MsgProcessListener msgProCallBack)
    {
        this(dwMessage, defaultDelayTime, msgProCallBack);
    }

    /**
     * 构造函数
     * 
     * @param dwMessage
     * @param delayTime
     * @param msgProCallBack
     */
    public MsgProThread(DWMessage dwMessage, long delayTime, MsgProcessListener msgProCallBack)
    {
        this.dwMessage = dwMessage;
        this.msgProCallBack = msgProCallBack;
        this.delayTime = delayTime;
    }

    @Override
    public void run()
    {
        try
        {
            Thread.sleep(delayTime);
            if (dwMessage.getSendSuccess() == DWMessage.SendStatus.SENDING.getIndex())
            {
                LogUtils.w(TAG, "发送消息之后等待" + delayTime + "ms后:\ndwMessage.toString()=" + dwMessage.toString() + "\nconnection=" + XmppHelper.getTestConn().toString(), true);
//                dwMessage.setSendSuccess(DWMessage.SendStatus.SUCCESS.getIndex());
//                dwMessage.save();
//                msgProCallBack.finish();
                dwMessage.setBody(dwMessage.getBody()+"\n"+"6s之后没有收到回执:"+"["+TimeUtils.getCurrentTime(TimeUtils.HH11mm11ss)+"]");
                dwMessage.setSendSuccess(DWMessage.SendStatus.FAILURE.getIndex());
                dwMessage.save();
                msgProCallBack.finish();
                
                //将发送失败的消息放入待发消息队列中
                
                
                //开启重连机制
                
            }
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

}
