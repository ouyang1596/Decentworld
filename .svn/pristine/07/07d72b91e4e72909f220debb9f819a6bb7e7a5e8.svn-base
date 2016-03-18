/**
 * 
 */
package cn.sx.decentworld.task;

import org.jivesoftware.smack.packet.Message;
import org.simple.eventbus.EventBus;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.R;
import cn.sx.decentworld.activity.ExamineActivity_;
import cn.sx.decentworld.bean.ContactUser;
import cn.sx.decentworld.bean.DWMessage;
import cn.sx.decentworld.bean.NotifyByEventBus;
import cn.sx.decentworld.bean.manager.DWSMessageManager;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.manager.MsgNotifyManager;
import cn.sx.decentworld.utils.LogUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @ClassName: ProcessErrorMessageThread.java
 * @Description:
 * @author: cj
 * @date: 2015年11月28日 下午6:12:24
 */
public class ProcessSystemMessageThread extends DWPacketHandler
{
	public static final String TAG = "ProcessSystemMessageThread";

	public ProcessSystemMessageThread(Message msg,Context context) {
		super(msg, context);
	}
	/**
	 * 
	 */
	public ProcessSystemMessageThread() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public void run()
	{
		Message message=(Message)packet;
		String subject = message.getSubject();
		if ("error".equals(subject))
		{
			//发送消息时身价不足，服务器提示
			print(message, "【身家不足】");
			String packetId = message.getPacketID();
			//改变数据库中信息的发送状态
			DWMessage msg = DWSMessageManager.queryItem(packetId);
			if (msg != null)
			{
				msg.setSendSuccess(0);
				msg.save();
			}
			// 将消息路由到ChatActivity和ChatRoomChatActivity，将刚才发送的消息的状态改为发送失败
			EventBus.getDefault().post(packetId, NotifyByEventBus.NT_WEALTH_SHORTAGE);
		}
		else if ("broadcast_benefit".equals(subject))
		{
			print(message, "【新的收益】");
			//将消息路由到收益界面
			/**
			 * benefit 应得的收益
			 * fee 手续费
			 * account 账号类型（ALIPAY、WXPAY）
			 * acctualBenefit （已经获得的收益）
			 * stored[Boolean] （true:1,用户关闭开发 ，手动转账 2，用户的收益不足以缴纳手续费，手动转账  false:用户关闭开发，自动转账）
			 * enough[Boolean] (是否够手续费)
			 * nick（对方的昵称）
			 * dwID（对方的ID）
			 */
			//弹出界面
			StringBuffer msgBuffer = new StringBuffer();
			msgBuffer.append("新的收益提示内容：");
//			if()
//			{
//				
//			}
			msgBuffer.append("");
			//消息通知
			MsgNotifyManager.getInstance().newBenefit(msgBuffer.toString());
		}
		else if ("broadcast_worth".equals(subject))
		{
			print(message, "【对方身价改变】");
			JSONObject jsonObject = JSON.parseObject(message.getBody());
			String dwID = jsonObject.getString("dwID");
			String worth = jsonObject.getString("worth");
			// 如果时好友，修改联系人列表中的worth字段
			ContactUser user = ContactUser.queryByDwID(dwID);
			if (user != null)
			{
				user.setWorth(worth);
				user.save();
			}
			// 消息路由到ChatActivity中；
			EventBus.getDefault().post(message, NotifyByEventBus.NT_SYSTEM_PUSH_WORTH);
			//消息通知
			MsgNotifyManager.getInstance().otherWorthChanged(dwID, worth);
		}
	}
	
	/**
	 * 打印日志
	 * @param message
	 * @param context
	 */
	private void print(Message message,String context)
	{
		LogUtils.i(TAG, "监听到一条消息"+context+"：" + message.getBody() + ",packetID=" + message.getPacketID());
	}
}
