/**
 * 
 */
package cn.sx.decentworld.bean.manager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.simple.eventbus.EventBus;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import cn.sx.decentworld.bean.DWMessage;
import cn.sx.decentworld.bean.NotifyByEventBus;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.network.request.GetRoomInfo;
import cn.sx.decentworld.utils.FileUtils;
import cn.sx.decentworld.utils.HttpDownloader;
import cn.sx.decentworld.utils.ImageUtils;
import cn.sx.decentworld.utils.LogUtils;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.RootContext;

/**
 * @ClassName: DWMMessageManager.java
 * @Description: 群聊消息管理器
 * @author: cj
 * @date: 2015年11月22日 下午6:43:29
 */
@EBean
public class DWMMessageManager {
	@RootContext
	Context context;

	@RootContext
	Activity activity;

	@Bean
	ToastComponent toastComponent;

	@AfterViews
	void init() {

	}

	private static final String TAG = "DWMMessageManager";
	/**
	 * 每一页的消息条数，默认为十条
	 */
	private static int countSinglePage = 5;

	@Bean
	GetRoomInfo getRoomInfo;

	/**
	 * 私有构造方法，防止外部创建实例
	 */
	public DWMMessageManager() {

	}

	/**
	 * 根据文本消息的txtMsgID搜索唯一的DWMessage
	 * 
	 * @param txtMsgID
	 * @return
	 */
	public static DWMessage queryItem(String txtMsgID) {
		return new Select().from(DWMessage.class)
				.where("txtMsgID = ?", txtMsgID).executeSingle();
	}

	/**
	 * 首次进入聊天室，自动从服务器拿取历史记录
	 * 
	 * @param dwID
	 * @param toID
	 * @return
	 */
	public void autoGetRoomHistoryMsg(String roomID) {
		LogUtils.i(TAG, "autoGetRoomHistoryMsg...首次进入聊天室，自动从服务器拿取历史记录");
		getRoomInfo.autoGetRoomHistoryMsg(roomID, handler);
	}

	/**
	 * 手动刷新从服务器拿取历史记录
	 * 
	 * @param dwID
	 *            自己的dwID
	 * @param toID
	 *            对方的dwID
	 * @return
	 */
	public void manualGetRoomHistoryMsg(String dwID, String roomID,
			long minMsgID) {
		LogUtils.i(TAG, "manualGetRoomHistoryMsg...手动刷新从服务器拿取历史记录");
		getRoomInfo.manualGetRoomHistoryMsg(roomID, minMsgID, handler);
	}

	// 服务器返回数据后经过解析，在把结果通过EventBus传递到ChatRoomChatActivity中
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			// LogUtils.i(TAG, "从服务器获取消息记录成功，内容为："+msg.obj.toString());
			// 解析下列数据
			List<DWMessage> temp = new ArrayList<DWMessage>();
			switch (msg.what) {
			case 1:// 成功
				String result = msg.obj.toString();
				LogUtils.i(TAG, "获取用户聊天室历史记录成功，内容为：" + result);
				JSONObject jsonObject = JSON.parseObject(result);
				JSONArray array = jsonObject.getJSONArray("history");
				DWMessage dwMessage;
				for (int i = 0; i < array.size(); i++) {
					JSONObject ob = array.getJSONObject(i);
					int chatType = ob.getIntValue("chatType");
					long id = ob.getLongValue("id");
					int messageType = ob.getIntValue("messageType");
					String roomID = ob.getString("roomID");
					String senderID = ob.getString("senderID");
					String sendTime = ob.getString("sendTime");
					dwMessage = new DWMessage(DWMessage.CHAT_TYPE_MULTI);
					dwMessage.setChatType(chatType);
					dwMessage.setMid(id);
					dwMessage.setMessageType(messageType);
					dwMessage.setFrom(senderID);
					dwMessage.setTo(roomID);
					dwMessage.setTime(sendTime);
					dwMessage.setSendSuccess(1);

					if (messageType == DWMessage.TXT) {
						// 如果是文字，message就是body
						dwMessage.setBody(ob.getString("message"));
					} else if (messageType == DWMessage.VOICE) {
						// 如果是语音，需要uri,length
						String data = ob.getString("message");
						JSONObject object = JSON.parseObject(data);
						String uri = object.getString("uri");
						int length = Integer
								.valueOf(object.getString("length"));
						//
						// String fileName = Constants.HomePath
						// + Constants.AudioReceivePath + File.separator
						// + FileUtils.generateFileName() + ".mp3";
						// downloadAudio(uri, fileName);
						dwMessage.setUri(uri);
						// dwMessage.ifFromNet = 0;
						dwMessage.setRemoteUrl(uri);
						dwMessage.setVoiceTime(length);
					} else if (messageType == DWMessage.IMAGE) {
						// 如果是图片，需要uri,img
						String data = ob.getString("message");
						JSONObject object = JSON.parseObject(data);
						String uri = object.getString("uri");
						String img = object.getString("img");
						if (null != img) {
							File file = ImageUtils.AnalyticThumbnail(img);
							dwMessage.setUri(file.getAbsolutePath());
						}
						dwMessage.setRemoteUrl(uri);
					}
					// 将结果保存到数据库
					dwMessage.save();
					// 加到temp中
					temp.add(dwMessage);
				}
				break;
			case 2:// 失败
				LogUtils.i(TAG, "失败，case 2" + ",temp.size = " + temp.size());

				break;
			case 3:// 失败
				LogUtils.i(TAG, "失败，case 3" + ",temp.size = " + temp.size());
				break;
			default:
				break;
			}
			// 将结果通过EventBus传递到ChatActivity中
			EventBus.getDefault().post(temp,
					NotifyByEventBus.NT_UPDATE_ROOM_HISTORY_MSG);
		};
	};

	/**
	 * 下载语音
	 */
	private int downloadAudio(String uri, String fileName) {
		// InputStream inputStream = null;
		int result = Constants.SUCC;
		if (!FileUtils.isFileExist(Constants.HOME_PATH)) {
			FileUtils.createSDDir(Constants.HOME_PATH);
		}
		if (!FileUtils.isFileExist(Constants.HOME_PATH
				+ Constants.AUDIO_RECEIVE_PATH)) {
			FileUtils.createSDDir(Constants.HOME_PATH
					+ Constants.AUDIO_RECEIVE_PATH);
		}
		result = HttpDownloader.downFile(uri, fileName);
		return result;
	}

	/**
	 * 退出聊天室删除聊天记录
	 * 
	 * @param roomID
	 */
	public void deleteHistory(String roomID) {
		new Delete().from(DWMessage.class).where("toDwId = ?", roomID)
				.execute();
	}

}
