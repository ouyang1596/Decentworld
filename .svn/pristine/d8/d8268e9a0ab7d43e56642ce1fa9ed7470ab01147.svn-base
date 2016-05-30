/**
 * 
 */
package cn.sx.decentworld.handleMessage;

import org.jivesoftware.smack.packet.Message;
import org.simple.eventbus.EventBus;

import cn.sx.decentworld.common.NotifyByEventBus;
import cn.sx.decentworld.entity.dao.DWMessageDao;
import cn.sx.decentworld.entity.db.DWMessage;
import cn.sx.decentworld.entity.db.DWMessage.ChatType;
import cn.sx.decentworld.logSystem.LogUtils;
import cn.sx.decentworld.manager.ChatManager;
import cn.sx.decentworld.manager.SelfInfoManager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @ClassName: handleAck
 * @Description: TODO (在这里用一句话描述类的作用)
 * @author: Jackchen
 * @date: 2016年5月12日 上午10:34:59
 */
public class HandleAck
{
    private static final String TAG = "handleAck";

    private static HandleAck instance = new HandleAck();

    private HandleAck()
    {
        // 防止产生多个实例对象
    }

    /**
     * 获取单例
     */
    public static HandleAck getInstance()
    {
        return instance;
    }

    /**
     * 身家回执
     */
    public void wealth(Message message)
    {
        LogUtils.v(TAG, "wealth() (2号消息)，" + message.toString());
        String packetID = message.getPacketID();
        DWMessage temp = DWMessageDao.query(packetID);
        JSONObject jsonObject = JSON.parseObject(message.getBody());
        String wealth = jsonObject.getString("wealth");
        long maxID = jsonObject.getLongValue("id");
        int chatType = jsonObject.getIntValue("chatType");
        String currentStatus = jsonObject.getString("currentStatus");
        String nextStatus = jsonObject.getString("nextStatus");
        // 更新内存中的身家
        SelfInfoManager.getInstance().notifyWealthChanged(Float.valueOf(wealth));
        // 更新对应消息的状态，并更新界面
        if (chatType == ChatType.SINGLE.getIndex() || chatType == ChatType.SINGLE_ANONYMITY.getIndex() || chatType == ChatType.MULTI.getIndex())
        {
            DWMessage msg = DWMessageDao.query(packetID);
            if (msg != null)
            {
                // 先把数据库改了
                msg.setSendSuccess(DWMessage.SendStatus.SUCCESS_FINISH.getIndex());
                msg.setMid(maxID);
                msg.setCurrentStatus(currentStatus);
                msg.setNextStatus(nextStatus);
                msg.save();
                // 通知界面更新
                ChatManager.getInstance().getMessageCache(msg.getToDwId(), msg.getChatType()).recWealth(packetID, maxID, currentStatus, nextStatus);
            }
            else
            {
                LogUtils.w(TAG, "wealth()...数据库不存在对应消息packetId=" + packetID);
            }
        }
    }

    /**
     * 消息送达回执
     */
    public void confirm(Message message)
    {
        LogUtils.v(TAG, "confirm() subject=confirm(5号消息)，" + message.toString());
        // // 服务器将消息发送对方成功后回执
        // long mid = Long.valueOf(message.getBody());
        // String packetID = message.getPacketID();
        // DWMessage msg = DWMessageDao.queryItem(packetID);
        // if (msg != null)
        // {
        // ComfirmSystem.getInstance().recMsgDeliveredAck(mid);
        // EventBus.getDefault().post(String.valueOf(mid),
        // NotifyByEventBus.NT_SEND_SUCCESS_ACK);
        // }
        // else
        // {
        // LogUtils.w(TAG, "run() confirm...数据库不存对应消息mid=" + mid);
        // }
    }

    /**
     * 身家不足回执
     */
    public void error(Message message)
    {
        if (message.getBody().equals("找不到用户"))
        {
            LogUtils.e(TAG, "error() 【找不到用户】：" + message.toString());
        }
        else
        {
            // 发送消息时身价不足，服务器提示
            LogUtils.i(TAG, "error() 【身家不足】：" + message.toString());
            String packetID = message.getPacketID();
            // 改变数据库中信息的发送状态
            DWMessage msg = DWMessageDao.query(packetID);
            if (msg != null)
            {
                msg.setSendSuccess(DWMessage.SendStatus.FAILURE_LACK_WEALTH.getIndex());
                msg.save();
                // 将消息路由到ChatActivity和ChatRoomChatActivity，将刚才发送的消息的状态改为发送失败
                ChatManager.getInstance().getMessageCache(msg.getToDwId(), msg.getChatType()).recLackWealth(packetID);
            }
            else
            {
                LogUtils.e(TAG, "error() 接收到系统subject= error的消息，此时为身家不足，但是数据库中找不到对应的记录packetId=" + packetID);
            }
        }
    }
}
