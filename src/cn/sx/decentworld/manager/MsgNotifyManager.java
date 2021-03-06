/**
 * 
 */
package cn.sx.decentworld.manager;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.R;
import cn.sx.decentworld.activity.ChatActivity;
import cn.sx.decentworld.activity.ChatActivity_;
import cn.sx.decentworld.activity.NewFriendsActivity_;
import cn.sx.decentworld.activity.RechargeBenefitActivity_;
import cn.sx.decentworld.bean.NewBenefit;
import cn.sx.decentworld.entity.LaunchChatEntity;
import cn.sx.decentworld.entity.MsgAndInfo;
import cn.sx.decentworld.entity.UserSessionInfo;
import cn.sx.decentworld.entity.dao.SelfExtraInfoDao;
import cn.sx.decentworld.entity.db.DWMessage;
import cn.sx.decentworld.entity.db.SelfExtraInfo;
import cn.sx.decentworld.entity.db.DWMessage.ChatRelationship;
import cn.sx.decentworld.entity.db.DWMessage.MessageType;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @ClassName: MsgNotification.java
 * @Description: 消息通知管理器
 * @author: cj onCreate():
 *          MsgNotifyManager.getInstance().Notifyclear(MsgNotifyManager.
 *          NOTIFY_TYPE_SINGLE);
 *          MsgNotifyManager.getInstance().NotifyStop(MsgNotifyManager
 *          .NOTIFY_TYPE_SINGLE); onDestory():
 *          MsgNotifyManager.getInstance().NotifyStart(MsgNotifyManager
 *          .NOTIFY_TYPE_SINGLE);
 * 
 * @date: 2015年12月17日 上午11:25:41
 */
public class MsgNotifyManager {
	private static final String TAG = "MsgNotification";
	/** 单例 **/
	private static MsgNotifyManager instance = null;
	/** 锁 **/
	private static Object INSTANCE_LOCK = new Object();
	/** 上下午对象 **/
	private static Context context;
	/** 定义NotificationManager **/
	private NotificationManager notificationManager;
	/** 通知铃声类型 **/
	private Uri sound_type_child;
	private Uri sound_type_girl;

	/** 通知是否可用 **/
	private boolean notify_enable_single = true;
	private boolean notify_enable_multi = true;
	private boolean notify_enable_other_worth_changed = true;
	private boolean notify_enable_new_benefit = true;
	private boolean notify_enable_new_friend = true;

	/** 播放的声音是否结束 **/
	private boolean single_voice_enable = true;

	/** 通知的消息类型 **/
	public static final int MSG_TYPE_SINGLE_TXT = 1;// 单聊文字信息
	public static final int MSG_TYPE_SINGLE_VOICE = 2;// 单聊语音信息
	public static final int MSG_TYPE_SINGLE_IMAGE = 3;// 单聊图片信息
	public static final int MSG_TYPE_SINGLE_CARD = 4;// 单聊名片信息

	public static final int MSG_TYPE_MULTI_TXT = 5;// 聊天室文字信息
	public static final int MSG_TYPE_MULTI_VOICE = 6;// 聊天室语音信息
	public static final int MSG_TYPE_MULTI_IMAGE = 7;// 聊天室图片信息
	public static final int MSG_TYPE_MULTI_CARD = 8;// 聊天室名片信息

	public static final int MSG_TYPE_ROOM_MENBER_ONLINE = 9;// 聊天室成员上线
	public static final int MSG_TYPE_ROOM_MENBER_OFFLINE = 10;// 聊天室成员下线
	public static final int MSG_TYPE_OTHER_WORTH_CHANGED = 11;// 对方身价改变
	public static final int MSG_TYPE_NEW_BENEFIT = 12;// 有新的收益
	public static final int MSG_TYPE_NEW_FRIEND = 13;// 添加朋友

	/** 通知的类型,用于Notification分类，可作为唯一标记 **/
	public static final int NOTIFY_TYPE_SINGLE = 1;// 单聊
	public static final int NOTIFY_TYPE_MULTI = 2;// 聊天室
	public static final int NOTIFY_TYPE_OTHER_WORTH_CHANGED = 3;// 对方身价改变
	public static final int NOTIFY_TYPE_NEW_BENEFIT = 4;// 有新的收益
	public static final int NOTIFY_TYPE_NEW_FRIEND = 5;// 新的朋友

	/** AddFriendNotify **/
	public static final int NOTIFY_ADD_FRIEND_APPLY = 1;// 申请
	public static final int NOTIFY_ADD_FRIEND_AGREE = 2;// 同意
	public static final int NOTIFY_ADD_FRIEND_REFUSE = 3;// 拒绝

	/** 小图标 **/
	private int smallIcon;
	/** 大图标 **/
	private int largeIcon;

	/**
	 * 获取单例
	 * 
	 * @return
	 */
	public static MsgNotifyManager getInstance() {
		if (instance == null) {
			synchronized (INSTANCE_LOCK) {
				if (instance == null) {
					instance = new MsgNotifyManager();
				}
			}
		}
		return instance;
	}

	/**
	 * 私有构造函数
	 */
	private MsgNotifyManager() {
		context = DecentWorldApp.getGlobalContext();
		/** 初始化声音 **/
		sound_type_child = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.child_voice);
		sound_type_girl = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.child_voice);
		notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		smallIcon = R.drawable.logo;
	}

	/**
	 * 开启通知
	 */
	public void NotifyStart(int NOTIFY_TYPE) {
		if (NOTIFY_TYPE == NOTIFY_TYPE_SINGLE) {
			notify_enable_single = true;
		} else if (NOTIFY_TYPE == NOTIFY_TYPE_MULTI) {
			notify_enable_multi = true;
		} else if (NOTIFY_TYPE == NOTIFY_TYPE_OTHER_WORTH_CHANGED) {
			notify_enable_other_worth_changed = true;
		} else if (NOTIFY_TYPE == NOTIFY_TYPE_NEW_BENEFIT) {
			notify_enable_new_benefit = true;
		} else if (NOTIFY_TYPE == NOTIFY_TYPE_NEW_FRIEND) {
			notify_enable_new_friend = true;
		}
	}

	/**
	 * 停止通知
	 */
	public void NotifyStop(int NOTIFY_TYPE) {
		if (NOTIFY_TYPE == NOTIFY_TYPE_SINGLE) {
			notify_enable_single = false;
		} else if (NOTIFY_TYPE == NOTIFY_TYPE_MULTI) {
			notify_enable_multi = false;
		} else if (NOTIFY_TYPE == NOTIFY_TYPE_OTHER_WORTH_CHANGED) {
			notify_enable_other_worth_changed = false;
		} else if (NOTIFY_TYPE == NOTIFY_TYPE_NEW_BENEFIT) {
			notify_enable_new_benefit = false;
		} else if (NOTIFY_TYPE == NOTIFY_TYPE_NEW_FRIEND) {
			notify_enable_new_friend = false;
		}
	}

	/**
	 * 清除通知
	 */
	public void Notifyclear(int NOTIFY_TYPE) {
		notificationManager.cancel(NOTIFY_TYPE);
	}

	/**
	 * 单聊消息通知（只包括单聊 接收朋友 文字、语音、图片、名片消息）
	 * 
	 * @param dwMessage
	 */
	public void SingleNotify(MsgAndInfo msgAndInfo) {
	    SelfExtraInfo extraInfo = SelfExtraInfoManager.getInstance().getEntity();
		boolean isNotify = false;
		DWMessage msg = msgAndInfo.getDwMessage();
		if (msg.getChatRelationship() == ChatRelationship.FRIEND.getIndex()) {
			if (extraInfo.getFriendNotice() != null && extraInfo.getFriendNotice())
				isNotify = true;
		}

		if (msg.getChatRelationship() == ChatRelationship.STRANGER.getIndex()) {
			if (extraInfo.getStrangerNotice() != null && extraInfo.getStrangerNotice())
				isNotify = true;
		}

		if (!isNotify)
			return;

		if (notify_enable_single) {
			DWMessage dwMessage = msgAndInfo.getDwMessage();
			UserSessionInfo userSessionInfo = msgAndInfo.getUserSessionInfo();
			// 定义通知栏展现的内容信息
			CharSequence tickerText = "";
			// 定义下拉通知栏时要展现的内容信息
			CharSequence title = "";
			CharSequence content = "消息内容";
			if (dwMessage.getMessageType() == MessageType.TEXT.getIndex()) {
				tickerText = userSessionInfo.getShowName() + "  " + dwMessage.getBody();
				title = tickerText;
				content = dwMessage.getBody();
			} else if (dwMessage.getMessageType() == MessageType.VOICE.getIndex()) {
				tickerText = userSessionInfo.getShowName() + "  发来一条语音";
				title = tickerText;
				content = "语音";
			} else if (dwMessage.getMessageType() == MessageType.IMAGE.getIndex()) {
				tickerText = userSessionInfo.getShowName() + "  发来一张图片";
				title = tickerText;
				content = "图片";
			} else if (dwMessage.getMessageType() == MessageType.CARD.getIndex()) {
				tickerText = userSessionInfo.getShowName() + "  发来一张名片";
				title = tickerText;
				content = "名片";
			}

			/** 点击打开的界面 **/
			Intent intent = new Intent(context, ChatActivity_.class);
//			intent.putExtra(ChatActivity.CHAT_RELATIONSHIP, dwMessage.getChatRelationship());
//			intent.putExtra(ChatActivity.CHAT_TYPE, dwMessage.getChatType());
//			intent.putExtra(ChatActivity.OTHER_ID, userSessionInfo.getFriendID());
//			intent.putExtra(ChatActivity.OTHER_NICKNAME, userSessionInfo.getShowName());
//			intent.putExtra(ChatActivity.OTHER_WORTH, userSessionInfo.getWorth());
			
            LaunchChatEntity entity = new LaunchChatEntity(userSessionInfo.getFriendID() , userSessionInfo.getShowName() ,
                    userSessionInfo.getWorth() ,dwMessage.getChatType() , dwMessage.getChatRelationship() ,
                    userSessionInfo.getUserType());
            intent.putExtra(ChatActivity.LAUNCH_CHAT_KEY, entity);
			/** **/
			PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

			Notification.Builder builder = new Notification.Builder(context);
			builder.setSmallIcon(smallIcon);
			builder.setTicker(tickerText);

			builder.setContentTitle(title);
			builder.setContentText(content);
			builder.setWhen(System.currentTimeMillis());
			builder.setContentIntent(pendingIntent);
			/** 避免短时间内接收到的消息过多，提示音总是中断重新播放的问题 **/
			if (single_voice_enable) {
				builder.setSound(sound_type_child);
				single_voice_enable = false;
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							Thread.sleep(4 * 1000);// 时间为声音的长度
							single_voice_enable = true;
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}).start();
			}

			builder.setAutoCancel(true);
			notificationManager.notify(NOTIFY_TYPE_SINGLE, builder.build());
		}
	}

	/**
	 * 聊天室消息通知
	 * 
	 * @param dwMessage
	 */
	public void MultiNotify(DWMessage dwMessage) {
		if (notify_enable_multi) {
			CharSequence tickerText = "亲，聊天室消息到了";
			// 定义下拉通知栏时要展现的内容信息
			CharSequence title = "消息类型";
			CharSequence content = "消息内容";
			if (dwMessage.getMessageType() == MessageType.TEXT.getIndex()) {
				title = "新的文字消息";
				content = dwMessage.getBody();
			} else if (dwMessage.getMessageType() ==MessageType.VOICE.getIndex()) {
				title = "新的语音消息";
				content = "语音";
			} else if (dwMessage.getMessageType() == MessageType.IMAGE.getIndex()) {
				title = "新的图片消息";
				content = "图片";
			} else if (dwMessage.getMessageType() == MessageType.CARD.getIndex()) {
				title = "新的名片消息";
				content = "名片";
			}

			Intent intent = new Intent();
			PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

			Notification.Builder builder = new Notification.Builder(context);
			builder.setSmallIcon(smallIcon);
			builder.setTicker(tickerText);

			builder.setContentTitle(title);
			builder.setContentText(content);
			builder.setWhen(System.currentTimeMillis());
			builder.setContentIntent(pendingIntent);

			builder.setAutoCancel(true);
			builder.setSound(sound_type_child);

			notificationManager.notify(NOTIFY_TYPE_MULTI, builder.build());
		}
	}

	/**
	 * 聊天室成员上线
	 */
	public void roomMemberOnline(String memberName, String roomName) {
		if (notify_enable_multi) {
			CharSequence tickerText = memberName + "进入了" + roomName + "聊天室";
			// 定义下拉通知栏时要展现的内容信息
			CharSequence title = "上线通知";
			CharSequence content = memberName + "进入了" + roomName + "聊天室";

			Intent intent = new Intent();
			PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

			Notification.Builder builder = new Notification.Builder(context);
			builder.setSmallIcon(smallIcon);
			builder.setTicker(tickerText);

			builder.setContentTitle(title);
			builder.setContentText(content);
			builder.setWhen(System.currentTimeMillis());
			builder.setContentIntent(pendingIntent);

			builder.setAutoCancel(true);
			// builder.setSound(sound_type_child);
			// 用notificationManager的notify方法通知用户生成标题栏消息通知
			notificationManager.notify(NOTIFY_TYPE_MULTI, builder.build());
		}
	}

	/**
	 * 聊天室成员下线
	 */
	public void roomMemberOffline(String memberName, String roomName) {
		if (notify_enable_multi) {
			CharSequence tickerText = memberName + "退出了" + roomName + "聊天室";
			// 定义下拉通知栏时要展现的内容信息
			CharSequence title = "下线通知";
			CharSequence content = memberName + "退出了" + roomName + "聊天室";
			Intent intent = new Intent();
			PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
			Notification.Builder builder = new Notification.Builder(context);
			builder.setSmallIcon(smallIcon);
			builder.setTicker(tickerText);

			builder.setContentTitle(title);
			builder.setContentText(content);
			builder.setWhen(System.currentTimeMillis());
			builder.setContentIntent(pendingIntent);

			builder.setAutoCancel(true);
			// builder.setSound(sound_type_child);
			// 用notificationManager的notify方法通知用户生成标题栏消息通知
			notificationManager.notify(NOTIFY_TYPE_MULTI, builder.build());
		}
	}

	/**
	 * 对方身价改变
	 */
	public void otherWorthChanged(String name, String worth) {
		if (notify_enable_other_worth_changed) {
			CharSequence tickerText = name + "身价变成" + worth + "￥";
			CharSequence title = "对方身价改变";
			CharSequence content = tickerText;

			Notification.Builder builder = new Notification.Builder(context);
			builder.setSmallIcon(smallIcon);
			builder.setTicker(tickerText);

			builder.setContentTitle(title);
			builder.setContentText(content);
			builder.setWhen(System.currentTimeMillis());

			builder.setAutoCancel(true);
			// builder.setSound(sound_type_child);

			// 用notificationManager的notify方法通知用户生成标题栏消息通知
			notificationManager.notify(NOTIFY_TYPE_OTHER_WORTH_CHANGED, builder.build());
		}

	}

	/**
	 * 新的收益，由被推荐人员返回
	 */
	public void newBenefit(String msg) {
		NewBenefit newBenefit = parseNewBenefit(msg);
		// CharSequence tickerText = "你有新的收益,来自：" + newBenefit.getNickName();
		// CharSequence title = "新的收益";
		// CharSequence content =
		// "你推荐的 "+newBenefit.getNickName()+" 给你返现"+newBenefit.getAcctualBenefit()+"￥";
		/** 提示信息 **/
		CharSequence tickerText = "大腕向你让利" + newBenefit.getAcctualBenefit() + "元，已在收益列表中";
		CharSequence title = "新的收益";
		CharSequence content = "大腕向你让利" + newBenefit.getAcctualBenefit() + "元，已在收益列表中";
		/** **/
		// CreateNotifyContent(newBenefit,tickerText,title,content);
		if (notify_enable_new_benefit) {
			Intent intent = new Intent(context, RechargeBenefitActivity_.class);
			intent.putExtra("isRecommend", true);
			PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

			Notification.Builder builder = new Notification.Builder(context);
			builder.setSmallIcon(smallIcon);
			builder.setTicker(tickerText);

			builder.setContentTitle(title);
			builder.setContentText(content);
			builder.setWhen(System.currentTimeMillis());
			builder.setContentIntent(pendingIntent);

			builder.setAutoCancel(true);
			// builder.setSound(sound_type_child);
			// 用notificationManager的notify方法通知用户生成标题栏消息通知
			notificationManager.notify(NOTIFY_TYPE_NEW_BENEFIT, builder.build());
		}
	}

	/**
	 * 解析成NewBenefit对象
	 * 
	 * @param msg
	 */
	private NewBenefit parseNewBenefit(String msg) {
		JSONObject object = JSON.parseObject(msg);
		NewBenefit newBenefit = new NewBenefit();
		newBenefit.setUserID(DecentWorldApp.getInstance().getDwID());
		newBenefit.setNickName(object.getString("nick"));
		newBenefit.setOtherID(object.getString("dwID"));
		newBenefit.setAccount(object.getString("account"));
		newBenefit.setBenefit(object.getFloatValue("benefit"));
		newBenefit.setAcctualBenefit(object.getFloatValue("actualBenefit"));
		newBenefit.setFee(object.getFloatValue("fee"));
		newBenefit.setEnough(object.getBooleanValue("enough"));
		newBenefit.setStored(object.getBooleanValue("stored"));
		return newBenefit;
	}

	/**
	 * 根据服务器推送的消息，构造出提示消息类容；
	 * 
	 * @param newBenefit
	 * @param tickerText
	 * @param title
	 * @param content
	 */
	private void CreateNotifyContent(NewBenefit newBenefit, CharSequence tickerText, CharSequence title, CharSequence content) {
		if (!newBenefit.isStored()) {
			/** 此时是没有存储在服务器 */

		} else {
			/** 此时存储在服务器 **/
			tickerText = "你有来自 " + newBenefit.getNickName() + " 的收益！";
			title = "新的收益";
			content = newBenefit.getNickName() + "给你返现 " + newBenefit.getBenefit() + "￥";
		}
	}

	/**
	 * 添加朋友流程通知（包括 申请、同意、拒绝）
	 * 
	 * @param info
	 *            通知信息
	 */
	public void addFriend(int type, String userName) {
		if (notify_enable_new_friend) {
			CharSequence tickerText = "";
			CharSequence title = "";
			CharSequence content = "";

			// 定义下拉通知栏时要展现的内容信息
			if (type == NOTIFY_ADD_FRIEND_APPLY) {
				tickerText = userName + " 请求加为朋友";
				title = "添加朋友";

			} else if (type == NOTIFY_ADD_FRIEND_AGREE) {
				tickerText = userName + " 同意你的好友申请";
				title = "同意";
			} else if (type == NOTIFY_ADD_FRIEND_REFUSE) {
				tickerText = userName + " 拒绝你的好友申请";
				title = "拒绝";
			}
			content = tickerText;
			Intent intent = new Intent(context, NewFriendsActivity_.class);
			PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

			Notification.Builder builder = new Notification.Builder(context);
			builder.setSmallIcon(smallIcon);
			builder.setTicker(tickerText);

			builder.setContentTitle(title);
			builder.setContentText(content);
			builder.setWhen(System.currentTimeMillis());
			builder.setContentIntent(pendingIntent);

			builder.setAutoCancel(true);
			// builder.setSound(sound_type_child);
			// 用notificationManager的notify方法通知用户生成标题栏消息通知
			notificationManager.notify(NOTIFY_TYPE_NEW_FRIEND, builder.build());
		}
	}

	/**
	 * 设置单聊消息声音
	 * 
	 * @param voiceType
	 */
	public void setSingleVoice(int voiceType) {
		sound_type_child = Uri.parse("android.resource://" + context.getPackageName() + "/" + voiceType);
	}
}
