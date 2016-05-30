/**
 * 
 */
package cn.sx.decentworld.stateMachine;

import cn.sx.decentworld.connect.XmppHelper;
import cn.sx.decentworld.entity.db.DWMessage;
import cn.sx.decentworld.listener.MsgProcessListener;
import cn.sx.decentworld.logSystem.LogUtils;
import cn.sx.decentworld.utils.TimeUtils;

/**
 * @ClassName: MsgProThread.java
 * @Description: 消息处理线程，
 * @author: cj
 * @date: 2016年1月5日 下午7:07:45
 */
public class MsgHandleThread extends Thread
{
    private static final String TAG = "MsgProThread";
    // 默认延迟时间----6s
    private static final long defaultDelayTime = 6 * 1000;
    private DWMessage dwMessage;
    private MsgHandleListener mMsgHandleListener;
    // 延迟时间
    private long delayTime;
    
    //完成
    public static final int FINISHED = 0;
    //未完成
    public static final int UNFINISHED = 1;
    

    /**
     * 构造函数
     * 
     * @param dwMessage
     * @param msgProCallBack
     */
    public MsgHandleThread(DWMessage dwMessage, MsgHandleListener msgProCallBack)
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
    public MsgHandleThread(DWMessage dwMessage, long delayTime, MsgHandleListener msgProCallBack)
    {
        this.dwMessage = dwMessage;
        this.mMsgHandleListener = msgProCallBack;
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
                LogUtils.w(TAG, "run() 发送消息之后等待" + delayTime + "ms后:\ndwMessage.toString()=" + dwMessage.toString() , true);
                dwMessage.setSendSuccess(DWMessage.SendStatus.SENDING.getIndex());
                dwMessage.save();
                mMsgHandleListener.unFinished(dwMessage);
            }
            else
            {
                mMsgHandleListener.finished(dwMessage);
            }
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

}
