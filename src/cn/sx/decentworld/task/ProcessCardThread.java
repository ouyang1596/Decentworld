/**
 * 
 */
package cn.sx.decentworld.task;

import org.jivesoftware.smack.packet.Message;
import org.simple.eventbus.EventBus;

import android.content.Context;
import cn.sx.decentworld.bean.DWMessage;
import cn.sx.decentworld.bean.MsgAndInfo;
import cn.sx.decentworld.bean.NotifyByEventBus;
import cn.sx.decentworld.bean.UserSessionInfo;
import cn.sx.decentworld.bean.manager.DWMMessageManager;
import cn.sx.decentworld.bean.manager.DWSMessageManager;
import cn.sx.decentworld.manager.MsgNotifyManager;
import cn.sx.decentworld.network.utils.JsonUtils;
import cn.sx.decentworld.utils.LogUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @ClassName: ProcessWealthPacketThread.java
 * @Description:接收名片
 * @author: cj
 * @date: 2015年11月28日 下午3:13:10
 */
public class ProcessCardThread extends DWPacketHandler
{

	private static String TAG = "ProcessCardThread";

	public ProcessCardThread(Message msg, Context context)
	{
		super(msg, context);
	}

	public ProcessCardThread()
	{
	};

	public void run()
	{
		Message message = (Message) packet;
		/**
		 * 处理逻辑为 1.先将监听到的消息保存到数据库 2.通知更新会话列表 3.通知更新消息表（将消息路由到对应的ChatActivity界面）
		 */
		LogUtils.i(TAG, "监听到的消息subject= card：" + message.getBody());
		JSONObject jsonObject = JSON.parseObject(message.getBody());
        String s_msg=jsonObject.getString("msg");
        String s_userSessionInfo=jsonObject.getString("userSessionInfo");
        UserSessionInfo userSessionInfo = (UserSessionInfo) JsonUtils.json2Bean(s_userSessionInfo, UserSessionInfo.class);
        /** 添加 FriendID **/
        JSONObject jsonObject2 = JSONObject.parseObject(s_userSessionInfo);
        userSessionInfo.setFriendID(jsonObject2.getString("id"));
        
		DWMessage dwMessage = DWMessage.toDWMessage(s_msg);
		dwMessage.setSendSuccess(1);// 接收成功
		dwMessage.setDirect(DWMessage.RECEIVE);
		dwMessage.setTxtMsgID(message.getPacketID());
		dwMessage.save();
		processMsg(new MsgAndInfo(dwMessage, userSessionInfo));
	}

	/**
	 * 处理接收到的消息
	 * 
	 * @param dwMessage
	 */
	private void processMsg(MsgAndInfo msgAndInfo)
	{
	    DWMessage dwMessage = msgAndInfo.getDwMessage();
		if (dwMessage.getChatType() != DWMessage.CHAT_TYPE_MULTI)
		{
			// 朋友间单聊
			if (dwMessage.getChatRelationship() == DWMessage.CHAT_RELATIONSHIP_FRIEND)
			{
				LogUtils.i(TAG, "朋友单聊，收到朋友发送的名片，并发送更新会话列表的通知");
				EventBus.getDefault().post(msgAndInfo, NotifyByEventBus.NT_REFRESH_CONVERSATION);
				MsgNotifyManager.getInstance().SingleNotify(msgAndInfo);
			}
			if (dwMessage.getChatRelationship() == DWMessage.CHAT_RELATIONSHIP_STRANGER)
			{
				LogUtils.i(TAG, "陌生人单聊，收到陌生人发送的名片，并发送更新会话列表的通知");
				EventBus.getDefault().post(msgAndInfo, NotifyByEventBus.NT_REFRESH_STRANGER_CONVERSATION);
			}
			EventBus.getDefault().post(dwMessage, NotifyByEventBus.NT_UPDATE_CHAT_LISTVIEW_RECEIVE_MSG);
		}
	}
}
