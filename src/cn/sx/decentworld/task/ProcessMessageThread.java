/**
 * 
 */
package cn.sx.decentworld.task;

import org.jivesoftware.smack.packet.Message;
import org.simple.eventbus.EventBus;

import android.content.Context;
import cn.sx.decentworld.bean.DWMessage;
import cn.sx.decentworld.bean.NotifyByEventBus;
import cn.sx.decentworld.manager.MsgNotifyManager;
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
		Message message = (Message) packet;
		/**
		 * 处理逻辑为 1.先将监听到的消息保存到数据库 2.通知更新会话列表 3.通知更新消息表（将消息路由到对应的ChatActivity界面）
		 */
		LogUtils.i(TAG, "监听到的消息subject= chat：" + message.getBody());
		DWMessage dwMessage = DWMessage.toDWMessage(message.getBody());
		dwMessage.setSendSuccess(1);// 接收成功
		dwMessage.setMessageType(DWMessage.TXT);
		dwMessage.setDirect(DWMessage.RECEIVE);
		dwMessage.save();
		processMsg(dwMessage);
	}

	/**
	 * 处理接收到的消息
	 * 
	 * @param dwMessage
	 */
	private void processMsg(DWMessage dwMessage) {
		if (dwMessage.getChatType() != DWMessage.CHAT_TYPE_MULTI) {
			// 朋友间单聊
			if (dwMessage.getChatRelationship() == DWMessage.CHAT_RELATIONSHIP_FRIEND) {
				LogUtils.i(TAG, "消息类型为单聊,发送更新消息列表的通知");
				// 这里用postSticky是因为可能受到离线消息，这时ChatFragment还没有初始化，即没有注册EventBus
				EventBus.getDefault().post(dwMessage,
						NotifyByEventBus.NT_REFRESH_CONVERSATION);
				LogUtils.v("bm", "message--" + dwMessage.getBody());
			}
			if (dwMessage.getChatRelationship() == DWMessage.CHAT_RELATIONSHIP_STRANGER) {
				EventBus.getDefault().post(dwMessage,
						NotifyByEventBus.NT_NOTIFY_STRANGER_UPDATA);
			}
			EventBus.getDefault().post(dwMessage,
					NotifyByEventBus.NT_UPDATE_CHAT_LISTVIEW_RECEIVE_FILE);
			// 消息通知
			MsgNotifyManager.getInstance().SingleNotify(dwMessage);
		}
	}
}
