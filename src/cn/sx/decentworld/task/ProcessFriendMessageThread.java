/**
 * 
 */
package cn.sx.decentworld.task;

import org.jivesoftware.smack.packet.Message;

import android.content.Context;
import cn.sx.decentworld.engine.ContactEngine;
import cn.sx.decentworld.logSystem.LogUtils;

/**
 * @ClassName: ProcessAcceptFriendThread.java
 * @Description:
 * @author: cj
 * @date: 2015年11月28日 下午6:28:58
 */
public class ProcessFriendMessageThread extends DWPacketHandler
{
    public static final String TAG = "ProcessFriendMessageThread";

    public ProcessFriendMessageThread(Message msg, Context context)
    {
        super(msg, context);
    }

    public ProcessFriendMessageThread()
    {

    }

    @Override
    public void run()
    {
        Message message = (Message) packet;
        String fromDwId = message.getFrom().split("@")[0];
        String toDwId = message.getTo().split("@")[0];
        LogUtils.i(TAG, "fromDwId=" + fromDwId + ",toDwId=" + toDwId);

        String subject = message.getSubject();
        if ("add_friend_reason".equals(subject))
        {
            LogUtils.i(TAG, "监听到一条消息【申请加为好友】，" + message.getBody());
            ContactEngine.getInstance().addByOther(message);
        }
        else if ("accept_Friend".equals(subject))
        {
            LogUtils.i(TAG, "监听到一条消息【同意加为好友回执】，" + message.getBody());
            ContactEngine.getInstance().acceptByOther(fromDwId);
        }
        else if ("refuse_Friend".equals(subject))
        {
            LogUtils.i(TAG, "监听到一条消息【拒绝加为好友回执】，" + message.getBody());
            ContactEngine.getInstance().refuseByOther(message, fromDwId);
        }
        else if ("delete_Friend".equals(subject))
        {
            LogUtils.i(TAG, "监听到一条消息【被人删除】");
            ContactEngine.getInstance().deleteByOther(fromDwId);
        }
    }
}
