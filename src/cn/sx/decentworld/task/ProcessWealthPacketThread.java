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
import cn.sx.decentworld.bean.manager.DWSMessageManager;
import cn.sx.decentworld.utils.LogUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @ClassName: ProcessWealthPacketThread.java
 * @Description:
 * @author: cj
 * @date: 2015年11月28日 下午3:13:10
 */
public class ProcessWealthPacketThread extends DWPacketHandler {

	private static String TAG = "ProcessWealthPacketThread";

	public ProcessWealthPacketThread(Message msg,Context context) {
		super(msg, context);
	}
	/**
	 * 
	 */
	public ProcessWealthPacketThread() {
		// TODO Auto-generated constructor stub
	}
	public void run() {
		
		Message message=(Message)packet;
		String packetId = message.getPacketID();
		JSONObject jsonObject = JSON.parseObject(message.getBody());
		String wealth = jsonObject.getString("wealth");
		long id = jsonObject.getLongValue("id");
		int chatType = jsonObject.getIntValue("chatType");
		LogUtils.i(TAG, "收到一条subject = wealth的消息，wealth=" + wealth + "，id="
				+ id + "，chatType=" + chatType);
		DWMessage msg = null;
		if (chatType == DWMessage.CHAT_TYPE_SINGLE
				|| chatType == DWMessage.CHAT_TYPE_SINGLE_ANONYMITY) {
			msg = DWSMessageManager.queryItem(packetId);
			if (msg != null) {
				// 先把数据库改了
				msg.setSendSuccess(1);
				msg.setMid(id);
				msg.save();
				// 通知界面更新
				EventBus.getDefault().post(message,
						NotifyByEventBus.NT_UPDATE_WEALTH);
			} else {
				LogUtils.i(TAG, "没有对应ID的消息");
				return;
			}
		} else if (chatType == DWMessage.CHAT_TYPE_MULTI) {
			msg = DWMMessageManager.queryItem(packetId);
			if (msg != null) {
				// 先把数据库改了
				msg.setSendSuccess(1);
				msg.setMid(id);
				msg.save();
				// 通知界面更新
				EventBus.getDefault().post(message,
						NotifyByEventBus.NT_UPDATE_WEALTH);
			} else {
				LogUtils.i(TAG, "没有对应ID的消息");
			}
		}
	}
}
