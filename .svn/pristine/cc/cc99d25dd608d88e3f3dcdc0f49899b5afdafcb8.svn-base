/**
 * 
 */
package cn.sx.decentworld.task;

import org.jivesoftware.smack.packet.Message;
import org.simple.eventbus.EventBus;

import android.app.Activity;
import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.bean.ContactUser;
import cn.sx.decentworld.bean.ConversationList;
import cn.sx.decentworld.bean.DWMessage;
import cn.sx.decentworld.bean.NewFriend;
import cn.sx.decentworld.bean.NotifyByEventBus;
import cn.sx.decentworld.utils.ImageUtils;
import cn.sx.decentworld.utils.LogUtils;

/**
 * @ClassName: ProcessAcceptFriendThread.java
 * @Description:
 * @author: cj
 * @date: 2015年11月28日 下午6:28:58
 */
public class ProcessFriendMessageThread extends DWPacketHandler

{
	public static final String TAG = "ProcessFriendMessageThread";

	public ProcessFriendMessageThread(Message msg,Context context) {
		super(msg, context);
	}
	/**
	 * 
	 */
	public ProcessFriendMessageThread() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public void run()
	{
		Message message=(Message)packet;
		String fromDwId = message.getFrom().split("@")[0];
		String toDwId = message.getTo().split("@")[0];
		LogUtils.i(TAG, "fromDwId=" + fromDwId + ",toDwId=" + toDwId);
		
		String subject = message.getSubject();
		if ("add_friend_reason".equals(subject))
		{
			LogUtils.i(TAG, "监听到一条消息【申请加为好友】，" + message.getBody());
			// 解析数据
			JSONObject jsonObject = JSON.parseObject(message.getBody());
			String reason = jsonObject.getString("addReason");
			String nickname = jsonObject.getString("nickName");
			String worth = jsonObject.getString("worth");
			String icon = ImageUtils.getIconByDwID(fromDwId, ImageUtils.ICON_SMALL);
			// 转化成Bean类
			NewFriend newFriend = NewFriend.queryByDwID(fromDwId);
			if (newFriend != null)
			{
				newFriend.setMessage_type(NewFriend.message_be_add);
				newFriend.setInfo_detail(reason);
				newFriend.setIsShown(0);
			}
			else
			{
				newFriend = new NewFriend();
				newFriend.setUsername(nickname);
				newFriend.setDw_id(fromDwId);
				newFriend.setAvatar(icon);
				newFriend.setWorth(worth);
				newFriend.setInfo_detail(reason);
				newFriend.setMessage_type(NewFriend.message_be_add);//被添加
				newFriend.setIsShown(0);
			}
			newFriend.save();
			// 将消息路由到ChatFragment
			EventBus.getDefault().post("申请加为好友", NotifyByEventBus.NT_SHOW_FRIENDS_ADDED);
		}
		else if ("accept_Friend".equals(subject))
		{
			// 同意加为好友
			LogUtils.i(TAG, "监听到一条消息【同意加为好友回执】，" + message.getBody());
			NewFriend newFriend = NewFriend.queryByDwID(fromDwId);
			if(newFriend!=null)
			{
				newFriend.setIsShown(1);
				newFriend.setMessage_type(NewFriend.message_add_success);
				newFriend.save();
				ContactUser cc = new ContactUser();
				cc.setUserID(DecentWorldApp.getInstance().getDwID());
				cc.setDwID(newFriend.getDw_id());
				cc.setIcon(newFriend.getAvatar());
				cc.setWorth(newFriend.getWorth());
				cc.setNickName(newFriend.getUsername());
				cc.save();
				//将陌生人处的会话列表移动到好友处
				ConversationList conversationList = ConversationList.queryByDwID(fromDwId, DWMessage.CHAT_RELATIONSHIP_STRANGER);
				if(conversationList!=null)
				{
					conversationList.setChatRelationship(DWMessage.CHAT_RELATIONSHIP_FRIEND);
					conversationList.save();
					EventBus.getDefault().post("【同意加为好友回执，更新好友会话列表】", NotifyByEventBus.NT_INIT_FRIEND_CONVERSATION);
					EventBus.getDefault().post("【同意加为好友回执，更新陌生人会话列表】", NotifyByEventBus.NT_INIT_STRANGER_CONVERSATION);
				}
				// 路由到ChatFragment(更新新的朋友Item)
				EventBus.getDefault().post("", NotifyByEventBus.NT_SHOW_FRIENDS_ADDED);
				// 路由到ChatFragment（好友列表）
				EventBus.getDefault().post(fromDwId, NotifyByEventBus.NT_REFRESH_CONTACT);
				
				//生成一条通知消息记录（DWMessage）
				///////////////////////////已经添加对方为好友
				// new 一个消息，保存
				DWMessage dwMessage = new DWMessage(DWMessage.NOTIFY, DWMessage.RECEIVE);
				dwMessage.setFrom(newFriend.getDw_id());
				dwMessage.setBody("已经添加对方为好友，,扣费方式改变，以自己的身价为基");
				dwMessage.save();
			}
		}
		else if ("refuse_Friend".equals(subject))
		{
			// 拒绝加为好友
			LogUtils.i(TAG, "监听到一条消息【拒绝加为好友】，" + message.getBody());
			JSONObject json = (JSONObject) JSON.parse(message.getBody().toString());
			NewFriend newFriend = NewFriend.queryByDwID((json.getString("from")));
			newFriend.setIsShown(0);
			newFriend.setMessage_type(NewFriend.message_add_fail);
			newFriend.save();
			// 路由到ChatFragment(更新新的朋友Item)
			EventBus.getDefault().post("", NotifyByEventBus.NT_SHOW_FRIENDS_ADDED);
		}
		else if ("delete_Friend".equals(subject))
		{
			LogUtils.i(TAG, "监听到一条消息【被人删除】，" + message.getBody());
			// 删除通讯录该人，并通知刷新
			ContactUser contactUser = ContactUser.queryByDwID(fromDwId);
			if(contactUser!=null)
			{
				contactUser.delete();
				EventBus.getDefault().post(fromDwId, NotifyByEventBus.NT_REFRESH_CONTACT);
				
				//将好友处的会话列表移动到陌生人处
				ConversationList conversationList = ConversationList.queryByDwID(fromDwId, DWMessage.CHAT_RELATIONSHIP_FRIEND);
				if(conversationList!=null)
				{
					conversationList.setChatRelationship(DWMessage.CHAT_RELATIONSHIP_STRANGER);
					conversationList.save();
					EventBus.getDefault().post("【被人删除，更新好友会话列表】", NotifyByEventBus.NT_INIT_FRIEND_CONVERSATION);
					EventBus.getDefault().post("【被人删除，更新陌生人会话列表】", NotifyByEventBus.NT_INIT_STRANGER_CONVERSATION);
				}
			}
		}
	}
}
