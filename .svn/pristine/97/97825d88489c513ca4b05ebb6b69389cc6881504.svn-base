/**
 * 
 */
package cn.sx.decentworld.task;

import java.io.File;

import org.jivesoftware.smack.packet.Message;
import org.simple.eventbus.EventBus;

import android.content.Context;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.bean.ConversationList;
import cn.sx.decentworld.bean.DWMessage;
import cn.sx.decentworld.bean.NotifyByEventBus;
import cn.sx.decentworld.manager.MsgNotifyManager;
import cn.sx.decentworld.utils.ImageUtils;
import cn.sx.decentworld.utils.LogUtils;

import com.alibaba.fastjson.JSONObject;

/**
 * @ClassName: ProcessPictureThread.java
 * @Description: 
 * @author: cj
 * @date: 2015年11月28日 下午6:34:19
 */
public class ProcessPictureThread	extends DWPacketHandler
{

	public static final String TAG = "ProcessPictureThread";

	public ProcessPictureThread(Message msg,Context context) {
		super(msg, context);
	}
	/**
	 * 
	 */
	public ProcessPictureThread() {
		// TODO Auto-generated constructor stub
	}
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		Message message=(Message)packet;
		String body=message.getBody();
		LogUtils.i(TAG, "收到一张图：" + body);
		LogUtils.i(TAG, "message.getFrom=" + message.getFrom()
				+ ",message.getTo=" + message.getTo());
		// 接收图片
		JSONObject jsono = JSONObject
				.parseObject(message.getBody());
		String chatType = jsono.getString("chatType");

		if (Integer.valueOf(chatType) == DWMessage.CHAT_TYPE_MULTI) {
			LogUtils.i(TAG, "监听到一条来自聊天室的图片消息" + body);
			// 发图片的人的dwID
			String fromDwID = jsono.getString("fromID");
			String myDwID = DecentWorldApp.getInstance().getDwID();
			LogUtils.i(TAG, "fromDwID=" + fromDwID + ",myID="
					+ myDwID);
			if (!myDwID.equals(fromDwID)) {
				String img = jsono.getString("img");
				String uri = jsono.getString("uri");
				String wealth = jsono.getString("wealth");
				long id = jsono.getLongValue("id");
				// 缺少chatRelationship
				String roomID = message.getFrom().split("@")[0];

				final DWMessage dwMessage = new DWMessage(
						DWMessage.IMAGE, DWMessage.RECEIVE);
				dwMessage.setFrom(fromDwID);
				dwMessage.setTo(roomID);
				dwMessage.setWealth(wealth);
				dwMessage.setChatType(Integer.valueOf(chatType));
				File file = ImageUtils.AnalyticThumbnail(img);
				dwMessage.setUri(file.getAbsolutePath());
				dwMessage.setRemoteUrl(uri);
				dwMessage.setSendSuccess(1);
				dwMessage.setTxtMsgID(message.getPacketID());
				dwMessage.setMid(id);
				dwMessage.save();

				EventBus.getDefault().post(dwMessage,
						NotifyByEventBus.NT_CHAT_ROOM_MSG);
				//聊天室图片消息通知
				MsgNotifyManager.getInstance().MultiNotify(dwMessage);
			}
		} else {
			LogUtils.i(TAG, "监听到一条来自单聊的图片消息" + body);
			String img = jsono.getString("img");
			String uri = jsono.getString("uri");
			String wealth = jsono.getString("wealth");
			long id = jsono.getLongValue("id");
			// 缺少chatRelationship
			String fromDwId = message.getFrom().split("@")[0];
			// 构造DWMessage
			final DWMessage dwMessage = new DWMessage(
					DWMessage.IMAGE, DWMessage.RECEIVE);
			dwMessage.setFrom(fromDwId);
			dwMessage.setWealth(wealth);
			dwMessage.setChatType(Integer.valueOf(chatType));
			File file = ImageUtils.AnalyticThumbnail(img);
			dwMessage.setUri(file.getAbsolutePath());
			dwMessage.setRemoteUrl(uri);
			dwMessage.setSendSuccess(1);
			dwMessage.setTxtMsgID(message.getPacketID());
			dwMessage.setMid(id);
			dwMessage.save();
			processMsg(dwMessage);
		}
	}
		
		private void processMsg(DWMessage dwMessage) {
			if (dwMessage.getChatType() != DWMessage.CHAT_TYPE_MULTI) {
				//朋友间单聊
				if(dwMessage.getChatRelationship()== DWMessage.CHAT_RELATIONSHIP_FRIEND){
					LogUtils.i(TAG, "消息类型为单聊,发送更新消息列表的通知");
				
					EventBus.getDefault().post(dwMessage,
							NotifyByEventBus.NT_REFRESH_CONVERSATION);
			
				}
				if (dwMessage.getChatRelationship() == DWMessage.CHAT_RELATIONSHIP_STRANGER) {
					EventBus.getDefault().post(dwMessage,
							NotifyByEventBus.NT_NOTIFY_STRANGER_UPDATA);
					
				}
				EventBus.getDefault().post(dwMessage,
						NotifyByEventBus.NT_UPDATE_CHAT_LISTVIEW_RECEIVE_FILE);
				//消息通知
				MsgNotifyManager.getInstance().SingleNotify(dwMessage);
			} 
		}
}
