/**
 * 
 */
package cn.sx.decentworld.task;

import org.jivesoftware.smack.packet.Message;
import org.simple.eventbus.EventBus;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import android.content.Context;
import cn.sx.decentworld.bean.DWMessage;
import cn.sx.decentworld.bean.MsgAndInfo;
import cn.sx.decentworld.bean.NotifyByEventBus;
import cn.sx.decentworld.bean.UserDetailInfo;
import cn.sx.decentworld.bean.UserSessionInfo;
import cn.sx.decentworld.manager.MsgNotifyManager;
import cn.sx.decentworld.network.utils.JsonUtils;
import cn.sx.decentworld.utils.LogUtils;

/**
 * @ClassName: ProcessMessageThread.java
 * @Description:
 * @author: cj
 * @date: 2015年11月28日 上午11:48:00
 */
public class ProcessMessageThread extends DWPacketHandler {
	public static final String TAG = "ProcessMessageThread";

	public ProcessMessageThread(Message msg, Context context) {
		super(msg, context);
	}

	public ProcessMessageThread() {
	}

	@Override
	public void run() {
	    /** subject ="chat" **/
		Message message = (Message) packet;
		/**
		 * 处理逻辑为 1.先将监听到的消息保存到数据库 2.通知更新会话列表 3.通知更新消息表（将消息路由到对应的ChatActivity界面）
		 */
		LogUtils.i(TAG, "监听到一条文字消息subject= chat：" + message.getBody());
		JSONObject jsonObject = JSON.parseObject(message.getBody());
		String s_msg=jsonObject.getString("msg");
        String s_userSessionInfo=jsonObject.getString("userSessionInfo");
        
        UserSessionInfo userSessionInfo = (UserSessionInfo) JsonUtils.json2Bean(s_userSessionInfo, UserSessionInfo.class);
        /** 添加 FriendID **/
        JSONObject jsonObject2 = JSONObject.parseObject(s_userSessionInfo);
        userSessionInfo.setFriendID(jsonObject2.getString("id"));
        
		DWMessage dwMessage = DWMessage.toDWMessage(s_msg);
		dwMessage.setSendSuccess(1);// 接收成功
		dwMessage.setMessageType(DWMessage.TXT);
		dwMessage.setDirect(DWMessage.RECEIVE);
		dwMessage.save();
		processMsg(new MsgAndInfo(dwMessage, userSessionInfo));
	}

	/**
	 * 处理接收到的消息
	 * @param dwMessage
	 */
	private void processMsg(MsgAndInfo msgAndInfo) {
	   
	    DWMessage dwMessage = msgAndInfo.getDwMessage();
        if (dwMessage.getChatType() != DWMessage.CHAT_TYPE_MULTI)
        {
            /** 朋友消息 **/
            if (dwMessage.getChatRelationship() == DWMessage.CHAT_RELATIONSHIP_FRIEND)
            {
                LogUtils.i(TAG, "消息类型为单聊,发送更新会话列表的通知");
                EventBus.getDefault().post(msgAndInfo, NotifyByEventBus.NT_REFRESH_CONVERSATION);
                /** 消息通知 **/
                MsgNotifyManager.getInstance().SingleNotify(msgAndInfo);
            }
            /** 陌生人消息 **/
            if (dwMessage.getChatRelationship() == DWMessage.CHAT_RELATIONSHIP_STRANGER)
            {
                EventBus.getDefault().post(msgAndInfo, NotifyByEventBus.NT_REFRESH_STRANGER_CONVERSATION);
            }
            EventBus.getDefault().post(dwMessage, NotifyByEventBus.NT_UPDATE_CHAT_LISTVIEW_RECEIVE_MSG);
            
        }
	}
}
