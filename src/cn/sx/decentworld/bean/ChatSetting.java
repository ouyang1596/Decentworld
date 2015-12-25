/**
 * 
 */
package cn.sx.decentworld.bean;

import java.util.List;

import cn.sx.decentworld.DecentWorldApp;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

/**
 * @ClassName: ChatSetting.java
 * @Description: 聊天设置
 * @author: cj
 * @date: 2015年12月17日 下午4:55:33
 */
@Table(name = "chatSetting")
public class ChatSetting extends Model
{
	/** 当前用户ID **/
	@Column(name = "userID")
	String userID;
	
	/** 对方ID **/
	@Column(name = "otherID")
	String otherID;
	
	/** 聊天背景 **/
	@Column(name = "chatBg")
	String chatBg;
	
	/** 是否消息置顶 **/
	@Column(name = "isMsgTop")
	boolean isMsgTop;
	
	/** 消息置顶时间 **/
	@Column(name = "msgTopTime")
	String msgTopTime;
	
	/** 是否消息免打扰 **/
	@Column(name = "isMsgNoDisturb")
	boolean isMsgNoDisturb;
	

	/**
	 * 
	 */
	public ChatSetting()
	{
		this.userID = "";
		this.otherID = "";
		this.chatBg = "";
		this.isMsgTop = false;
		this.msgTopTime = "";
		this.isMsgNoDisturb = false;
	}

	/**
	 * @return the userID
	 */
	public String getUserID()
	{
		return userID;
	}

	/**
	 * @param userID the userID to set
	 */
	public void setUserID(String userID)
	{
		this.userID = userID;
	}

	/**
	 * @return the otherID
	 */
	public String getOtherID()
	{
		return otherID;
	}

	/**
	 * @param otherID the otherID to set
	 */
	public void setOtherID(String otherID)
	{
		this.otherID = otherID;
	}

	/**
	 * @return the chatBg
	 */
	public String getChatBg()
	{
		return chatBg;
	}

	/**
	 * @param chatBg the chatBg to set
	 */
	public void setChatBg(String chatBg)
	{
		this.chatBg = chatBg;
	}

	/**
	 * @return the isMsgTop
	 */
	public boolean isMsgTop()
	{
		return isMsgTop;
	}

	/**
	 * @param isMsgTop the isMsgTop to set
	 */
	public void setMsgTop(boolean isMsgTop)
	{
		this.isMsgTop = isMsgTop;
	}

	/**
	 * @return the msgTopTime
	 */
	public String getMsgTopTime()
	{
		return msgTopTime;
	}

	/**
	 * @param msgTopTime the msgTopTime to set
	 */
	public void setMsgTopTime(String msgTopTime)
	{
		this.msgTopTime = msgTopTime;
	}

	/**
	 * @return the isMsgNoDisturb
	 */
	public boolean isMsgNoDisturb()
	{
		return isMsgNoDisturb;
	}

	/**
	 * @param isMsgNoDisturb the isMsgNoDisturb to set
	 */
	public void setMsgNoDisturb(boolean isMsgNoDisturb)
	{
		this.isMsgNoDisturb = isMsgNoDisturb;
	}
	
	/**
	 * 查询所有记录
	 * @return
	 */
	public static List<ChatSetting> queryAll()
	{
		String dwID = DecentWorldApp.getInstance().getDwID();
		String sql = "userID = ?";
		return new Select().from(ChatSetting.class).where(sql, dwID).execute();
	}
	
	/**
	 * 根据dwID查找记录
	 * @param otherID
	 * @return
	 */
	public static ChatSetting queryByDwID(String otherID)
	{
		String dwID = DecentWorldApp.getInstance().getDwID();
		String sql = "userID = ? and otherID = ?";
		return new Select().from(ChatSetting.class).where(sql, dwID,otherID).executeSingle();
	}
	
	/**
	 * 根据dwID删除记录
	 * @param otherID
	 * @return
	 */
	public static void deleteByDwID(String otherID)
	{
		String dwID = DecentWorldApp.getInstance().getDwID();
		String sql = "userID = ? and otherID = ?";
		ChatSetting chatSetting = new Select().from(ChatSetting.class).where(sql, dwID,otherID).executeSingle();
		if(chatSetting!=null)
		{
			chatSetting.delete();
		}
	}
	
	

}
