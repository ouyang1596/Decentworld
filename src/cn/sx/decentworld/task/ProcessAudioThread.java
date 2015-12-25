/**
 * 
 */
package cn.sx.decentworld.task;

import java.io.File;

import org.jivesoftware.smack.packet.Message;
import org.simple.eventbus.EventBus;

import android.content.Context;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.bean.DWMessage;
import cn.sx.decentworld.bean.NotifyByEventBus;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.manager.MsgNotifyManager;
import cn.sx.decentworld.utils.FileUtils;
import cn.sx.decentworld.utils.HttpDownloader;
import cn.sx.decentworld.utils.LogUtils;
import cn.sx.decentworld.utils.SoundPoolUtils;

import com.alibaba.fastjson.JSONObject;

/**
 * @ClassName: ProcessAudioThread.java
 * @Description:
 * @author: cj
 * @date: 2015年11月28日 下午6:36:49
 */
public class ProcessAudioThread extends DWPacketHandler {
	public static final String TAG = "ProcessAudioThread";

	public ProcessAudioThread(Message message, Context context) {
		super(message, context);
	}

	public ProcessAudioThread() {
	};

	@Override
	public void run() {
		Message message = (Message) packet;
		// 语音消息
		LogUtils.i(TAG, "收到一条语音：" + message.getBody());
		/**
		 * 解析所需参数
		 */
		JSONObject jsono = JSONObject.parseObject(message.getBody());
		String chatType = jsono.getString("chatType");
		if (Integer.valueOf(chatType) == DWMessage.CHAT_TYPE_MULTI) {
			// 聊天室的dwID
			String fromDwID = jsono.getString("fromID");
			String myDwID = DecentWorldApp.getInstance().getDwID();
			LogUtils.i(TAG, "fromDwID=" + fromDwID + ",myID=" + myDwID);
			String roomID = message.getFrom().split("@")[0];
			if (!myDwID.equals(fromDwID)) {
				LogUtils.i(TAG, "收到一条来自聊天室的语音");
				Float length = jsono.getFloat("length");
				String url = jsono.getString("uri");
				String wealth = jsono.getString("wealth");
				long id = jsono.getLongValue("id");
				// 缺少chatRelationship
				// 构造DWMessage
				final DWMessage dwMessage = new DWMessage(DWMessage.VOICE,
						DWMessage.RECEIVE);
				dwMessage.setFrom(fromDwID);
				dwMessage.setTo(roomID);
				dwMessage.setWealth(wealth);
				dwMessage.setChatType(Integer.valueOf(chatType));
				/**
				 * 下载语音并保存消息
				 */
				// String fileName = Constants.HomePath
				// + Constants.AudioReceivePath
				// + File.separator
				// + FileUtils.generateFileName() + ".mp3";
				// int result = downloadAudio(uri, fileName);
				dwMessage.setUri(url);
				dwMessage.ifFromNet = 0;
				dwMessage.setRemoteUrl(url);
				dwMessage.setVoiceTime(length);
				dwMessage.setSendSuccess(1);
				dwMessage.setTxtMsgID(message.getPacketID());
				dwMessage.setMid(id);
				dwMessage.save();
				EventBus.getDefault().post(dwMessage,
						NotifyByEventBus.NT_RECEIVE_CHATROOM_AUDIO);
				//聊天室语音消息通知
				MsgNotifyManager.getInstance().MultiNotify(dwMessage);
			}
		} else {
			Float length = jsono.getFloat("length");
			String url = jsono.getString("uri");
			String wealth = jsono.getString("wealth");
			String fromDwID = jsono.getString("fromID");
			long id = jsono.getLongValue("id");
			// 缺少chatRelationship
			// 构造DWMessage
			final DWMessage dwMessage = new DWMessage(DWMessage.VOICE,
					DWMessage.RECEIVE);
			dwMessage.setFrom(fromDwID);
			dwMessage.setWealth(wealth);
			dwMessage.setChatType(Integer.valueOf(chatType));
			/**
			 * 下载语音并保存消息
			 */
			String fileName = Constants.HomePath + Constants.AudioReceivePath
					+ File.separator + FileUtils.generateFileName() + ".mp3";
			// int result = downloadAudio(uri, fileName);
			// dwMessage.setUri(fileName);
			dwMessage.ifFromNet = 0;
			dwMessage.setUri(url);
			dwMessage.setRemoteUrl(url);
			dwMessage.setVoiceTime(length);
			dwMessage.setSendSuccess(1);
			dwMessage.setTxtMsgID(message.getPacketID());
			dwMessage.setMid(id);
			dwMessage.save();
			processMsg(dwMessage);
		}
	}

	private void processMsg(DWMessage dwMessage) 
	{
		EventBus.getDefault().post(dwMessage,NotifyByEventBus.NT_RECEIVE_SINGLE_AUDIO);
		//消息通知
		MsgNotifyManager.getInstance().SingleNotify(dwMessage);
	}

	/**
	 * 下载语音
	 */
	private int downloadAudio(String uri, String fileName) {
		// InputStream inputStream = null;
		int result = Constants.SUCC;
		if (!FileUtils.isFileExist(Constants.HomePath)) {
			FileUtils.createSDDir(Constants.HomePath);
		}
		if (!FileUtils.isFileExist(Constants.HomePath
				+ Constants.AudioReceivePath)) {
			FileUtils.createSDDir(Constants.HomePath
					+ Constants.AudioReceivePath);
		}
		result = HttpDownloader.downFile(uri, fileName);
		return result;
	}
}
