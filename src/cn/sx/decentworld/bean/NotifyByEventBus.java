/**
 * 
 */
package cn.sx.decentworld.bean;

/**
 * @ClassName: NotifyByEventBus.java
 * @Description: 消息路由类；1.在该类中定义TAG，命名分为两部分，前缀和后缀，前缀缩写为NT，代表NOTIFY_TAG，后缀为自定义部分；
 * @author: cj
 * @date: 2015年10月5日 下午2:46:41
 */
public class NotifyByEventBus {
	/**
	 * 用户身家
	 */
	public static final String NT_UPDATE_WEALTH = "NT_UPDATE_WEALTH";// 更新身家
	// 使用EventBus定义的变量
	public static final String NT_UPDATE_MESSAGELIST = "NT_UPDATE_MESSAGELIST";// 通知更新消息表界面
	public static final String NT_SHOW_FRIENDS_ADDED = "NT_SHOW_FRIENDS_ADDED";// 拦截消息到
	public static final String NT_APPEARANCE_CHECK_SUPPORT = "NT_APPEARANCE_CHECK_SUPPORT";// 拦截消息到支持
	public static final String NT_APPEARANCE_CHECK_UNSUPPORT = "NT_APPEARANCE_CHECK_UNSUPPORT";// 拦截消息到反对
	public static final String NT_CHECK_BEAUTIFY = "NT_CHECK_BEAUTIFY";// 貌推送

	public static final String NT_UPDATE_NEW_FRIEND_LIST = "NT_UPDATE_NEW_FRIEND_LIST";// 通知更新好友添加列表
	public static final String NT_UPDATE_FILE_SEND_PROGRESS = "NT_UPDATE_FILE_SEND_PROGRESS";// 通知更新文件传输进度
	public static final String NT_UPDATE_CHAT_LISTVIEW_RECEIVE_FILE = "NT_UPDATE_CHAT_LISTVIEW_RECEIVE_FILE";// 当处于chatActivity时，收到服务器发来的file
	
	
	public static final String NT_RESET_TXT = "NT_RESET_TXT";// 文字消息发送失败时，重新发送文字消息
	public static final String NT_RESET_VOICE = "NT_RESET_VOICE";// 语音发送失败时，重新发送语音
	public static final String NT_RESET_IMAGE = "NT_RESET_IMAGE";// 消息发送失败时，重新发送消息
	public static final String NT_RESET_CARD = "NT_RESET_CARD";// 名片发送失败时，重新发送名片

	public static final String NT_STRANGER_INFO = "NT_STRANGER_INFO";
	public static final String NT_CONNECTION = "NT_CONNECTION";

	public static final String NT_GOINTO_MAINACTIVITY = "NT_GOINTO_MAINACTIVITY";
	public static final String NT_NOTIFY_STRANGER_UPDATA = "NT_NOTIFY_STRANGER_UPDATA";
	/**
	 * 聊天列表
	 */
	public static final String NT_CLEAR_CONVERSATION_UNREAD = "NT_CLEAR_CONVERSATION_UNREAD";
	public static final String NT_CLEAR_STRANGER_CONVERSATION_UNREAD = "NT_CLEAR_STRANGER_CONVERSATION_UNREAD";
	public static final String NT_DELETE_STRANGER_CONVERSATION = "NT_DELETE_STRANGER_CONVERSATION";
	public static final String NT_DELETE_FRIEND_CONVERSATION = "NT_DELETE_FRIEND_CONVERSATION";
	public static final String NT_ADD_FRIEND_CONVERSATION = "NT_ADD_FRIEND_CONVERSATION";
	public static final String NT_ADD_STRANGER_CONVERSATION = "NT_ADD_STRANGER_CONVERSATION";

	/**
	 * 上下线
	 */
	public static final String NT_CRUSH_OFF_LINE = "NT_CRUSH_OFF_LINE";// 被挤下线的通知
	/**
	 * 接受聊天语音
	 * */
	public static final String NT_RECEIVE_SINGLE_AUDIO = "NT_RECEIVE_SINGLE_AUDIO";
	public static final String NT_RECEIVE_CHATROOM_AUDIO = "NT_RECEIVE_CHATROOM_AUDIO";
	/**
	 * 聊天室
	 */
	public static final String NT_CHAT_ROOM_JOIN = "NT_CHAT_ROOM_JOIN";// 聊天室成员上线通知
	public static final String NT_CHAT_ROOM_LEAVE = "NT_CHAT_ROOM_LEAVE";// 聊天室成员下线通知
	public static final String NT_CHAT_ROOM_TEXT = "NT_CHAT_ROOM_TEXT";// 聊天室文字通知
	public static final String NT_CHAT_ROOM_IMAGE = "NT_CHAT_ROOM_IMAGE";// 聊天室图片通知
	public static final String NT_CHAT_ROOM_VOICE = "NT_CHAT_ROOM_VOICE";// 聊天室语音通知

	public static final String NT_CHAT_ROOM_MSG = "NT_CHAT_ROOM_MSG";// 聊天室消息（文字、语音、图片）通知

	public static final String NT_UPDATE_MSG_STATUS = "NT_UPDATE_MSG_STATUS";// 更新消息状态的通知（见DWMessage中SendSuccess字段）
	public static final String NT_START_SERVICE = "NT_START_SERVICE";// 开启服务
	public static final String NT_STOP_SERVICE = "NT_STOP_SERVICE";// 关闭服务
	public static final String NT_RESTART_SERVICE = "NT_RESTART_SERVICE";// 重启服务

	public static final String NT_CHECK_NETWORK = "NT_CHECK_NETWORK";// 检测网络
	public static final String NT_REFRESH_CONVERSATION = "NT_REFRESH_CONVERSATION";// 刷新回话列表
	public static final String NT_REFRESH_CONTACT = "NT_REFRESH_CONTACT";// 刷新联系人列表

	public static final String NT_WEALTH_SHORTAGE = "NT_WEALTH_SHORTAGE";// 身家不足，消息状态改为发送失败

	public static final String NT_UPDATE_SINGLE_HISTORY_MSG = "NT_UPDATE_SINGLE_HISTORY_MSG";// 获取单聊历史消息记录并更新界面
	public static final String NT_UPDATE_ROOM_HISTORY_MSG = "NT_UPDATE_ROOM_HISTORY_MSG";// 获取聊天历史消息记录并更新界面

	public static final String NT_SYSTEM_PUSH_WORTH = "NT_SYSTEM_PUSH_WORTH";// 系统推送的对方身价改变
	public static final String NT_STRANGER_TO_FRIEND = "NT_STRANGER_TO_FRIEND";// 从陌生人变为朋友

	public static final String NT_INIT_FRIEND_CONVERSATION = "NT_INIT_FRIEND_CONVERSATION";// 初始化好友界面的会话列表
	public static final String NT_INIT_STRANGER_CONVERSATION = "NT_INIT_STRANGER_CONVERSATION";// 初始化陌生人界面的会话列表

	/**
	 * 网络连接
	 */

	private Object object;
	private String what;// 描述要通知订阅者所做的事情 //本类中定义的常量

	/**
	 * 
	 */
	public NotifyByEventBus() {

	}

	/**
	 * @param object
	 * @param what
	 */
	public NotifyByEventBus(Object object, String what) {
		super();
		this.object = object;
		this.what = what;
	}

	/**
	 * @return the object
	 */
	public Object getObject() {
		return object;
	}

	/**
	 * @param object
	 *            the object to set
	 */
	public void setObject(Object object) {
		this.object = object;
	}

	/**
	 * @return the what
	 */
	public String getWhat() {
		return what;
	}

	/**
	 * @param what
	 *            the what to set
	 */
	public void setWhat(String what) {
		this.what = what;
	}

}
