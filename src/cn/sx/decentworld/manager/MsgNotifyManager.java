/**
 * 
 */
package cn.sx.decentworld.manager;

import java.io.File;

import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.R;
import cn.sx.decentworld.activity.PrivacySettingActivity_;
import cn.sx.decentworld.activity.RecommendBenefitActivity_;
import cn.sx.decentworld.bean.ContactUser;
import cn.sx.decentworld.bean.DWMessage;
import cn.sx.decentworld.bean.RecommendBenefitList;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * @ClassName: MsgNotification.java
 * @Description: 消息通知管理器
 * @author: cj
 * @date: 2015年12月17日 上午11:25:41
 */
public class MsgNotifyManager
{
	public static final String TAG = "MsgNotification";
	public static final MsgNotifyManager instance = new MsgNotifyManager();
	private static Context context;
	private static String service_type = "";
	/**
	 * 通知铃声类型
	 */
	private static Uri sound_type_single;
	private static Uri sound_type_multi;
	private static Uri sound_type_room_menber_online;
	private static Uri sound_type_room_menber_offline;
	private static Uri sound_type_other_worth_changed;
	private static Uri sound_type_new_benefit;

	/**
	 * 静态模块
	 */
	static
	{
		context = DecentWorldApp.getGlobalContext();
		service_type = Context.NOTIFICATION_SERVICE;
		// 初始化声音
		sound_type_single = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.received_message);
		sound_type_multi = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.received_message);
		sound_type_room_menber_online = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.received_message);
		sound_type_room_menber_offline = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.received_message);
		sound_type_other_worth_changed = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.received_message);
		sound_type_new_benefit = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.received_message);
	}
	/**
	 * 通知的消息类型
	 */
	public static final int msg_type_single_txt = 1;// 单聊文字信息
	public static final int msg_type_single_voice = 2;// 单聊语音信息
	public static final int msg_type_single_image = 3;// 单聊图片信息
	public static final int msg_type_single_card = 4;// 单聊名片信息

	public static final int msg_type_multi_txt = 5;// 聊天室文字信息
	public static final int msg_type_multi_voice = 6;// 聊天室语音信息
	public static final int msg_type_multi_image = 7;// 聊天室图片信息
	public static final int msg_type_multi_card = 8;// 聊天室名片信息

	public static final int msg_type_room_menber_online = 9;// 聊天室成员上线
	public static final int msg_type_room_menber_offline = 10;// 聊天室成员下线
	public static final int msg_type_other_worth_changed = 11;// 对方身价改变
	public static final int msg_type_new_benefit = 12;// 有新的收益

	// public static final int msg_type_add_friend = 13;//添加朋友

	/**
	 * 通知的类型
	 */
	public static final int notity_type_single = 1;// 单聊
	public static final int notity_type_multi = 2;// 聊天室
	public static final int notity_type_other_worth_changed = 3;// 对方身价改变
	public static final int notity_type_new_benefit = 4;// 有新的收益

	/**
	 * 获取单例
	 * 
	 * @return
	 */
	public static MsgNotifyManager getInstance()
	{
		return instance;
	}

	/**
	 * 单聊消息通知
	 * 
	 * @param dwMessage
	 */
	public void SingleNotify(DWMessage dwMessage)
	{
		// 消息通知栏
		// 定义NotificationManager
		NotificationManager notificationManager = (NotificationManager) context.getSystemService(service_type);
		// 定义通知栏展现的内容信息
		int icon = R.drawable.ic_launcher;
		CharSequence tickerText = "亲，新的消息到了";
		long when = System.currentTimeMillis();
		Notification notification = new Notification(icon , tickerText , when);
		// 定义下拉通知栏时要展现的内容信息
		CharSequence title = "消息类型";
		CharSequence content = "消息内容";
		if (dwMessage.getMessageType() == DWMessage.TXT)
		{
			title = "新的文字消息";
			content = dwMessage.getBody();
		}
		else if (dwMessage.getMessageType() == DWMessage.VOICE)
		{
			title = "新的语音消息";
			content = "语音";
		}
		else if (dwMessage.getMessageType() == DWMessage.IMAGE)
		{
			title = "新的图片消息";
			content = "图片";
		}
		else if (dwMessage.getMessageType() == DWMessage.CARD)
		{
			title = "你有一条新的名片消息";
			content = "名片";
		}
		Intent notificationIntent = new Intent();
		
		
		//启动ChatActivity需要传递5个参数；
//		notificationIntent.putExtra("user_dwID", dwMessage.getFrom());
//		if(ContactUser.isContact(dwMessage.getFrom()))
//		{
//			notificationIntent.putExtra("chatRelationship", DWMessage.CHAT_RELATIONSHIP_FRIEND);
//		}
//		else
//		{
//			notificationIntent.putExtra("chatRelationship", DWMessage.CHAT_RELATIONSHIP_STRANGER);
//		}
//		notificationIntent.putExtra("chatType", dwMessage.getChatType());
//		notificationIntent.putExtra("user_nickname", "");
//		notificationIntent.putExtra("user_worth", "");
		
		
		PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);

		notification.setLatestEventInfo(context, title, content, contentIntent);// 只有一条消息，相同的ID会覆盖
		notification.sound = sound_type_single;// 设置声音
		notification.flags = Notification.FLAG_AUTO_CANCEL;// 点击后自动取消
		// 用notificationManager的notify方法通知用户生成标题栏消息通知
		notificationManager.notify(notity_type_single, notification);
	}

	/**
	 * 聊天室消息通知
	 * 
	 * @param dwMessage
	 */
	public void MultiNotify(DWMessage dwMessage)
	{
		// 消息通知栏
		// 定义NotificationManager
		NotificationManager notificationManager = (NotificationManager) context.getSystemService(service_type);
		// 定义通知栏展现的内容信息
		int icon = R.drawable.ic_launcher;
		CharSequence tickerText = "亲，聊天室消息到了";
		long when = System.currentTimeMillis();
		Notification notification = new Notification(icon , tickerText , when);
		// 定义下拉通知栏时要展现的内容信息
		CharSequence title = "消息类型";
		CharSequence content = "消息内容";
		if (dwMessage.getMessageType() == DWMessage.TXT)
		{
			title = "新的文字消息";
			content = dwMessage.getBody();
		}
		else if (dwMessage.getMessageType() == DWMessage.VOICE)
		{
			title = "新的语音消息";
			content = "语音";
		}
		else if (dwMessage.getMessageType() == DWMessage.IMAGE)
		{
			title = "新的图片消息";
			content = "图片";
		}
		else if (dwMessage.getMessageType() == DWMessage.CARD)
		{
			title = "新的名片消息";
			content = "名片";
		}
		
		Intent notificationIntent = new Intent();
		PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);

		notification.setLatestEventInfo(context, title, content, contentIntent);// 只有一条消息，相同的ID会覆盖
		notification.sound = sound_type_single;// 设置声音
		notification.flags = Notification.FLAG_AUTO_CANCEL;// 点击后自动取消
		// 用notificationManager的notify方法通知用户生成标题栏消息通知
		notificationManager.notify(notity_type_multi, notification);
	}

	/**
	 * 聊天室成员上线
	 */
	public void roomMemberOnline(String memberName, String roomName)
	{
		// 消息通知栏
		// 定义NotificationManager
		NotificationManager notificationManager = (NotificationManager) context.getSystemService(service_type);
		// 定义通知栏展现的内容信息
		int icon = R.drawable.ic_launcher;
		CharSequence tickerText = memberName + "进入了" + roomName + "聊天室";
		long when = System.currentTimeMillis();
		Notification notification = new Notification(icon , tickerText , when);
		// 定义下拉通知栏时要展现的内容信息
		CharSequence title = "上线通知";
		CharSequence content = memberName + "进入了" + roomName + "聊天室";
		Intent notificationIntent = new Intent();
		PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
		notification.setLatestEventInfo(context, title, content, contentIntent);// 只有一条消息，相同的ID会覆盖
		notification.sound = sound_type_room_menber_online;// 设置声音
		notification.flags = Notification.FLAG_AUTO_CANCEL;// 点击后自动取消
		// 用notificationManager的notify方法通知用户生成标题栏消息通知
		notificationManager.notify(notity_type_multi, notification);
	}

	/**
	 * 聊天室成员下线
	 */
	public void roomMemberOffline(String memberName, String roomName)
	{
		// 消息通知栏
		// 定义NotificationManager
		NotificationManager notificationManager = (NotificationManager) context.getSystemService(service_type);
		// 定义通知栏展现的内容信息
		int icon = R.drawable.ic_launcher;
		CharSequence tickerText = memberName + "退出了" + roomName + "聊天室";
		long when = System.currentTimeMillis();
		Notification notification = new Notification(icon , tickerText , when);
		// 定义下拉通知栏时要展现的内容信息
		CharSequence title = "下线通知";
		CharSequence content = memberName + "退出了" + roomName + "聊天室";
		Intent notificationIntent = new Intent();
		PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
		notification.setLatestEventInfo(context, title, content, contentIntent);// 只有一条消息，相同的ID会覆盖
		notification.sound = sound_type_room_menber_offline;// 设置声音
		notification.flags = Notification.FLAG_AUTO_CANCEL;// 点击后自动取消
		// 用notificationManager的notify方法通知用户生成标题栏消息通知
		notificationManager.notify(notity_type_multi, notification);
	}

	/**
	 * 对方身价改变
	 */
	public void otherWorthChanged(String name,String worth)
	{
		// 消息通知栏
		// 定义NotificationManager
		NotificationManager notificationManager = (NotificationManager) context.getSystemService(service_type);
		// 定义通知栏展现的内容信息
		int icon = R.drawable.ic_launcher;
		CharSequence tickerText = name + "身价变成" + worth + "￥";
		long when = System.currentTimeMillis();
		Notification notification = new Notification(icon , tickerText , when);
		// 定义下拉通知栏时要展现的内容信息
		CharSequence title = "对方身价改变";
		CharSequence content = tickerText;
		Intent notificationIntent = new Intent();
		PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
		notification.setLatestEventInfo(context, title, content, contentIntent);// 只有一条消息，相同的ID会覆盖
		notification.sound = sound_type_other_worth_changed;// 设置声音
		notification.flags = Notification.FLAG_AUTO_CANCEL;// 点击后自动取消
		// 用notificationManager的notify方法通知用户生成标题栏消息通知
		notificationManager.notify(notity_type_other_worth_changed, notification);
	}

	/**
	 * 新的收益，由被推荐人员返回
	 */
	public void newBenefit(String msg)
	{		// 消息通知栏
		// 定义NotificationManager
		NotificationManager notificationManager = (NotificationManager) context.getSystemService(service_type);
		// 定义通知栏展现的内容信息
		int icon = R.drawable.ic_launcher;
		CharSequence tickerText ="你有新的收益"+msg;
		long when = System.currentTimeMillis();
		Notification notification = new Notification(icon , tickerText , when);
		// 定义下拉通知栏时要展现的内容信息
		CharSequence title = "新的收益";
		CharSequence content = msg;
		Intent notificationIntent = new Intent(context , RecommendBenefitActivity_.class);
		PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
		notification.setLatestEventInfo(context, title, content, contentIntent);// 只有一条消息，相同的ID会覆盖
		notification.sound = sound_type_new_benefit;// 设置声音
		notification.flags = Notification.FLAG_AUTO_CANCEL;// 点击后自动取消
		// 用notificationManager的notify方法通知用户生成标题栏消息通知
		notificationManager.notify(notity_type_new_benefit, notification);
	}

}
