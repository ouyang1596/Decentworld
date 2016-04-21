/**
 * 
 */
package cn.sx.decentworld.task;

import org.jivesoftware.smack.packet.Message;
import org.simple.eventbus.EventBus;

import android.content.Context;
import cn.sx.decentworld.bean.DWMessage;
import cn.sx.decentworld.bean.NotifyByEventBus;
import cn.sx.decentworld.bean.manager.DWMMessageManager;
import cn.sx.decentworld.engine.ChatEngine;
import cn.sx.decentworld.engine.UserInfoEngine;
import cn.sx.decentworld.entity.dao.DWMessageDao;
import cn.sx.decentworld.logSystem.LogUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @ClassName: ProcessWealthPacketThread.java
 * @Description:
 * @author: cj
 * @date: 2015年11月28日 下午3:13:10
 */
public class ProcessWealthPacketThread extends DWPacketHandler
{
    private static String TAG = "ProcessWealthPacketThread";

    public ProcessWealthPacketThread(Message msg, Context context)
    {
        super(msg, context);
    }

    public ProcessWealthPacketThread()
    {
        
    }

    public void run()
    {
        Message message = (Message) packet;
        LogUtils.d(TAG, "message:"+message.toString());
        if (message.getSubject().equals("wealth"))
        {
            String packetId = message.getPacketID();
            JSONObject jsonObject = JSON.parseObject(message.getBody());

            String wealth = jsonObject.getString("wealth");
            long id = jsonObject.getLongValue("id");
            int chatType = jsonObject.getIntValue("chatType");
            // 更新内存中的身家
            UserInfoEngine.getInstance().notifyWealthChanged(Float.valueOf(wealth));
            LogUtils.i(TAG, "收到一条subject = wealth的消息，wealth=" + wealth + "，id=" + id + "，chatType=" + chatType);
            // 更新对应消息的状态，并更新界面
            if (chatType == DWMessage.CHAT_TYPE_SINGLE || chatType == DWMessage.CHAT_TYPE_SINGLE_ANONYMITY || chatType == DWMessage.CHAT_TYPE_MULTI)
            {
                DWMessage msg = DWMessageDao.queryItem(packetId);
                if (msg != null)
                {
                    // 先把数据库改了
                    msg.setSendSuccess(1);
                    msg.setMid(id);
                    msg.save();
                    // 通知界面更新
                    EventBus.getDefault().post(message, NotifyByEventBus.NT_UPDATE_WEALTH);
                    // 发送名片的回执
                    EventBus.getDefault().post(packetId, NotifyByEventBus.NT_SEND_CARD_ACK);
                }
                else
                {
                    LogUtils.w(TAG, "wealth...数据库不存在packetId=" + packetId + "的消息");
                }
            }
        }
        else if (message.getSubject().equals("confirm"))
        {
            LogUtils.d(TAG, "confirm..."+message.toString());
            // 服务器将消息发送对方成功后回执
            long mid = Long.valueOf(message.getBody());
            DWMessage msg = DWMessageDao.queryItem(mid);
            if (msg != null)
            {
                ComfirmSystem.getInstance().recMsgDeliveredAck(mid);
                EventBus.getDefault().post(String.valueOf(mid), NotifyByEventBus.NT_SEND_SUCCESS_ACK);
            }
            else
            {
                LogUtils.w(TAG, "confirm...数据库不存在mid=" + mid + "的消息");
            }
        }
    }
}
