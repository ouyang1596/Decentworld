/**
 * 
 */
package cn.sx.decentworld.bean;

import java.util.List;

import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.network.utils.JsonUtils;
import cn.sx.decentworld.utils.LogUtils;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * @ClassName: DWMessage.java
 * @Description: 消息
 * @author: cj
 * @date: 2015年9月8日 上午8:16:04
 */
@Table(name = "dWMessage")
public class DWMessage extends Model implements Comparable<DWMessage>
{
	private static final String TAG = "DWMessage";
	public int ifFromNet = 0;// 0表示从网上获取 1表示本地
	/**
	 * 消息的方向类型
	 */
	public static final int RECEIVE = 0;
	public static final int SEND = 1;

	/**
	 * 聊天类型
	 */
	public static final int CHAT_TYPE_SINGLE = 0;// 单聊（好友陌生人）
	public static final int CHAT_TYPE_SINGLE_ANONYMITY = 1;// 单聊匿名
	public static final int CHAT_TYPE_MULTI = 2;// 多人（群、兴趣组、聊天室）

	/**
	 * 消息类型
	 */
	public static final int TXT = 0;//文字
	public static final int VOICE = 1;//语音
	public static final int IMAGE = 2;//图片
	public static final int CARD = 3;//名片
	public static final int NOTIFY = 4;//消息提示（对方身价改变、陌生人变朋友）

	/**
	 * 聊天关系
	 */
	public static final int CHAT_RELATIONSHIP_FRIEND = 0;// 单聊关系（朋友）
	public static final int CHAT_RELATIONSHIP_STRANGER = 1;// 单聊关系（陌生人）

	/**
	 * 聊天类型
	 */
//	@JSONField(serialize=true)
//	@JSONField(deserialize = false)
	@Column(name = "chatType")
	private int chatType;

	/**
	 * 聊天关系，默认为朋友
	 */
	@Column(name = "chatRelationship")
	private int chatRelationship;

	/**
	 * 发送、接收
	 */
	@Column(name = "direct")
	private int direct;

	/**
	 * 消息类型
	 */
	@Column(name = "messageType")
	private int messageType;

	/**
	 * 发送方dwID
	 */
	@Column(name = "fromDwId")
	private String fromDwId;
	
	/**
	 * 发送方dwID
	 */
	@Column(name = "toDwId")
	private String toDwId;
	

	/**
	 * 消息内容
	 */
	@Column(name = "body")
	private String body;

	/**
	 * 发送时间
	 */
	@Column(name = "time")
	private String time;

	/**
	 * 身家（服务器处理后携带wealth的消息）
	 */
	@Column(name = "wealth")
	private String wealth;

	/**
	 * 是否已读，0为未读，1为已读（默认为未读）
	 */
	@Column(name = "isRead")
	private int isRead;// 标记消息是否已读

	/**
	 * 语音时长
	 */
	@Column(name = "voiceTime")
	private float voiceTime;

	/**
	 * 消息是否发送成功,0代表发送失败，1代表发送成功，2代表发送中
	 */
	@Column(name = "sendSuccess")
	private int sendSuccess;

	/**
	 * 当messageType为文字消息是，该字段表示packetID，用于与配服务器返回消息传送状态匹配
	 */
	@Column(name = "txtMsgID")
	private String txtMsgID;

	/**
	 * 消息id（默认为-1，当服务器返回后，更新mid），用于与服务器同步
	 */
	@Column(name = "mid")
	private long mid;

	/**
	 * 当messageType为图片类型时，uri放缩略图，local_url放大图的本地地址，remote_url放服务器的url地址
	 * 点击图片，首先检查local_url是否为空，如果为空，则用remote_url进行网络加载
	 */
	@Column(name = "uri")
	private String uri;
	/**
	 * 图片、语音的本地地址
	 */
	@Column(name = "localUrl")
	private String localUrl;

	/**
	 * 图片、语音的服务器地址
	 */
	@Column(name = "remoteUrl")
	private String remoteUrl;
	
	/**
	 * 被转发人的DwID
	 */
	@Column(name = "forwardDwId")
	private String forwardDwId;
	
	/**
	 *被转发人的名字
	 */
	@Column(name = "forwardName")
	private String forwardName;
	
	/**
	 *用户dwID
	 */
	@JSONField(serialize=false)
	@Column(name = "userID")
	private String userID;
	

	/**
	 * 无参构造函数
	 */
	public DWMessage()
	{
		this.chatType = CHAT_TYPE_SINGLE;// 默认为单聊
		this.chatRelationship = CHAT_RELATIONSHIP_FRIEND;//默认为好友
		this.direct = SEND;// 默认为发送
		this.messageType = TXT;// 默认为发送文字
		this.fromDwId = "";
		this.toDwId = "";
		this.body = "";
		this.time = String.valueOf(System.currentTimeMillis());
		this.wealth = "";
		this.isRead = 0;
		this.voiceTime = 0;
		this.sendSuccess = 2;
		this.txtMsgID = "";
		this.mid = -1;
		this.localUrl = "";
		this.remoteUrl = "";
		this.uri = "";
		this.forwardDwId = "";
		this.forwardName = "";
		this.userID = DecentWorldApp.getInstance().getDwID();
	}
	
	/**
	 * 发送消息的构造函数
	 * 
	 * @param chatType 聊天类型 
	 */
	public DWMessage(int chatType)
	{
		super();
		// 必须的
		this.chatType = chatType;
		this.chatRelationship = CHAT_RELATIONSHIP_FRIEND;//默认为好友
		this.direct = SEND;// 默认为发送
		this.messageType = TXT;// 默认为发送文字
		this.fromDwId = "";
		this.toDwId = "";
		this.body = "";
		this.time = String.valueOf(System.currentTimeMillis());
		this.wealth = "";
		this.isRead = 0;
		this.voiceTime = 0;
		this.sendSuccess = 2;
		this.txtMsgID = "";
		this.mid = -1;
		this.localUrl = "";
		this.remoteUrl = "";
		this.uri = "";
		this.forwardDwId = "";
		this.forwardName = "";
		this.userID = DecentWorldApp.getInstance().getDwID();
	}


	/**
	 * 发送消息的构造函数
	 * 
	 * @param messageType 消息类型 
	 * @param direct 发送
	 */
	public DWMessage(int messageType, int direct)
	{
		super();
		// 必须的
		this.messageType = messageType;
		this.direct = direct;
		if (direct == DWMessage.SEND)
		{
			this.fromDwId = DecentWorldApp.getInstance().getDwID();
			
		} else if (direct == DWMessage.RECEIVE)
		{
			this.toDwId = DecentWorldApp.getInstance().getDwID();
		}
		
		this.chatType = CHAT_TYPE_SINGLE;// 默认为单聊
		this.chatRelationship = CHAT_RELATIONSHIP_FRIEND;//默认为好友
		this.body = "";
		this.time = String.valueOf(System.currentTimeMillis());
		this.wealth = "";
		this.isRead = 0;
		this.voiceTime = 0;
		if(messageType == NOTIFY)
		{
			this.sendSuccess = 1;//
		}else
		{
			this.sendSuccess = 2;
		}
		
		this.txtMsgID = "";
		this.mid = -1;
		this.localUrl = "";
		this.remoteUrl = "";
		this.uri = "";
		this.forwardDwId = "";
		this.forwardName = "";
		this.userID = DecentWorldApp.getInstance().getDwID();
	}


	/**
	 * @return the chatType
	 */
	public int getChatType()
	{
		return chatType;
	}

	/**
	 * @param chatType
	 *            the chatType to set
	 */
	public void setChatType(int chatType)
	{
		this.chatType = chatType;
	}

	/**
	 * @return the direct
	 */
	public int getDirect()
	{
		return direct;
	}

	/**
	 * @param direct
	 *            the direct to set
	 */
	public void setDirect(int direct)
	{
		this.direct = direct;
	}

	/**
	 * @return the messageType
	 */
	public int getMessageType()
	{
		return messageType;
	}

	/**
	 * @param messageType
	 *            the messageType to set
	 */
	public void setMessageType(int messageType)
	{
		this.messageType = messageType;
	}

	/**
	 * 获取信息发送者
	 * 
	 * @return the from
	 */
	public String getFrom()
	{
		return fromDwId;
	}

	/**
	 * 设置信息发送者
	 * 
	 * @param from
	 *            the from to set
	 */
	public void setFrom(String fromDwId)
	{
		this.fromDwId = fromDwId;
	}

	/**
	 * 获取消息内容
	 * 
	 * @return the body
	 */
	public String getBody()
	{
		return body;
	}

	/**
	 * 设置消息内容
	 * 
	 * @param body
	 *            the body to set
	 */
	public void setBody(String body)
	{
		this.body = body;
	}

	/**
	 * 获得消息接受者
	 * 
	 * @return the to
	 */
	public String getTo()
	{
		return toDwId;
	}

	/**
	 * 设置消息接收者
	 * 
	 * @param to
	 *            the to to set
	 */
	public void setTo(String toDwId)
	{
		this.toDwId = toDwId;
	}

	/**
	 * 获取消息发送的时间
	 * 
	 * @return the time
	 */
	public String getTime()
	{
		return time;
	}

	/**
	 * 设置消息发送的时间
	 * 
	 * @param time
	 *            the time to set
	 */
	public void setTime(String time)
	{
		this.time = time;
	}

	/**
	 * @return the wealth
	 */
	public String getWealth()
	{
		return wealth;
	}

	/**
	 * @param wealth
	 *            the wealth to set
	 */
	public void setWealth(String wealth)
	{
		this.wealth = wealth;
	}

	/**
	 * @return the isRead
	 */
	public int getIsRead()
	{
		return isRead;
	}

	/**
	 * @param isRead
	 *            the isRead to set
	 */
	public void setIsRead(int isRead)
	{
		this.isRead = isRead;
	}

	public static String toJson(DWMessage dwMessage)
	{
		return JsonUtils.bean2json(dwMessage);
	}

	public static DWMessage toDWMessage(String jsonMessage)
	{
		return JsonUtils.json2Bean(jsonMessage, DWMessage.class);
	}
	
	
	public static String toJsonExcepte(DWMessage dwMessage)
	{
		
		return JsonUtils.bean2json(dwMessage);
	}

	/**
	 * 对数据库的操作
	 */

	/**
	 * @return the voiceTime
	 */
	public float getVoiceTime()
	{
		return voiceTime;
	}

	/**
	 * @param voiceTime
	 *            the voiceTime to set
	 */
	public void setVoiceTime(float voiceTime)
	{
		this.voiceTime = voiceTime;
	}

	/**
	 * @return the fromDwId
	 */
	public String getFromDwId()
	{
		return fromDwId;
	}

	/**
	 * @param fromDwId
	 *            the fromDwId to set
	 */
	public void setFromDwId(String fromDwId)
	{
		this.fromDwId = fromDwId;
	}

	/**
	 * @return the toDwId
	 */
	public String getToDwId()
	{
		return toDwId;
	}

	/**
	 * @param toDwId
	 *            the toDwId to set
	 */
	public void setToDwId(String toDwId)
	{
		this.toDwId = toDwId;
	}

	/**
	 * @return the sendSuccess
	 */
	public int getSendSuccess()
	{
		return sendSuccess;
	}

	/**
	 * @param sendSuccess
	 *            the sendSuccess to set
	 */
	public void setSendSuccess(int sendSuccess)
	{
		this.sendSuccess = sendSuccess;
	}

	/**
	 * @return the uri
	 */
	public String getUri()
	{
		return uri;
	}

	/**
	 * @param uri
	 *            the uri to set
	 */
	public void setUri(String uri)
	{
		this.uri = uri;
	}

	/**
	 * @return the chatRelationship
	 */
	public int getChatRelationship()
	{
		return chatRelationship;
	}

	/**
	 * @param chatRelationship
	 *            the chatRelationship to set
	 */
	public void setChatRelationship(int chatRelationship)
	{
		this.chatRelationship = chatRelationship;
	}

	/**
	 * @return the mid
	 */
	public long getMid()
	{
		return mid;
	}

	/**
	 * @param mid
	 *            the mid to set
	 */
	public void setMid(long mid)
	{
		this.mid = mid;
	}

	/**
	 * @return the localUrl
	 */
	public String getLocalUrl()
	{
		return localUrl;
	}

	/**
	 * @param localUrl
	 *            the localUrl to set
	 */
	public void setLocalUrl(String localUrl)
	{
		this.localUrl = localUrl;
	}

	/**
	 * @return the remoteUrl
	 */
	public String getRemoteUrl()
	{
		return remoteUrl;
	}

	/**
	 * @param remoteUrl
	 *            the remoteUrl to set
	 */
	public void setRemoteUrl(String remoteUrl)
	{
		this.remoteUrl = remoteUrl;
	}
	
	

	/**
	 * @return the forwardDwId
	 */
	public String getForwardDwId()
	{
		return forwardDwId;
	}

	/**
	 * @param forwardDwId the forwardDwId to set
	 */
	public void setForwardDwId(String forwardDwId)
	{
		this.forwardDwId = forwardDwId;
	}

	/**
	 * @return the forwardName
	 */
	public String getForwardName()
	{
		return forwardName;
	}

	/**
	 * @param forwardName the forwardName to set
	 */
	public void setForwardName(String forwardName)
	{
		this.forwardName = forwardName;
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
	 * 查找全部消息并返回
	 * 
	 * @return
	 */
	public static List<DWMessage> queryAll()
	{
		return new Select().from(DWMessage.class).orderBy("mid").execute();
	}

	/**
	 * 删除消息列表
	 */
	public static void deleteAll()
	{
		new Delete().from(DWMessage.class).execute();
	}

	/**
	 * 删除指定ID号的聊天记录
	 * 
	 * @param dwID
	 * @param dwID2
	 */
	public static void deleteBy2DwID(String dwID, String dwID2)
	{
		new Delete().from(DWMessage.class).where("fromDwId= ? and toDwId = ?", new String[]
		{ dwID, dwID2 }).execute();
		new Delete().from(DWMessage.class).where("toDwId = ? and fromDwid = ?", new String[]
		{ dwID, dwID2 }).execute();
	}

	/**
	 * 查询未读消息的条数
	 */
	public static int selectUnreadMessage()
	{
		List<DWMessage> dwMessages = new Select().from(DWMessage.class).where("isRead=?", 0).execute();
		return dwMessages.size();
	}

	public static int selectUnreadMsgBetween2(String fromDwId, String toDwId)
	{
		List<DWMessage> dwMessages1 = new Select().from(DWMessage.class).where("isRead = ? and toDwId = ? and fromDwId = ?", 0, fromDwId, toDwId).execute();
		List<DWMessage> dwMessages2 = new Select().from(DWMessage.class).where("isRead = ? and fromDwId = ? and toDwId = ?", 0, fromDwId, toDwId).execute();
		return dwMessages1.size() + dwMessages2.size();
	}

	/**
	 * @return the txtMsgID
	 */
	public String getTxtMsgID()
	{
		return txtMsgID;
	}

	/**
	 * @param txtMsgID
	 *            the txtMsgID to set
	 */
	public void setTxtMsgID(String txtMsgID)
	{
		this.txtMsgID = txtMsgID;
	}

	/**
	 * 排序
	 * 首先按照 mid从小到大排序，如果mid相等，那么按照时间从小到大
	 */
	@Override
	public int compareTo(DWMessage another)
	{
		if(this.getMid() == another.getMid())
		{
			return (int) ( Long.valueOf(this.getTime())- Long.valueOf(another.getTime()));
		}else
		{
			return (int) (this.getMid() - another.getMid());
		}
	}

	/**
	 * 获取当前用户与指定方的聊天消息条数
	 * 
	 * @param dwID
	 *            自己的dwID
	 * @param toID
	 *            对方的dwID
	 * @return 消息条数
	 */
	public static int getMsgCount(String dwID, String toID)
	{
		long startTime = System.currentTimeMillis();
		List<DWMessage> list = new Select().from(DWMessage.class).where("fromDwId and toDwId in (?,?) ", dwID, toID).execute();
		long endTime = System.currentTimeMillis();
		long time = (endTime - startTime);
		LogUtils.i(TAG, "查询数据库中的单聊历史记录" + time + "毫秒");
		return list.size();
	}

	/**
	 * 获取当聊天室消息条数
	 * 
	 * @param roomID
	 * @return
	 */
	public static int getRoomMsgCount(String roomID)
	{
		List<DWMessage> list1 = new Select().from(DWMessage.class).where("toDwId = ? ", roomID).execute();
		return list1.size();
	}

	/**
	 * 获取当前用户与指定方的聊天消息
	 * 
	 * @param dwID
	 *            自己的dwID
	 * @param toID
	 *            对方的dwID
	 * @return 消息条数
	 */
	public static List<DWMessage> getMsg(String dwID, String toID)
	{
		List<DWMessage> list = new Select().from(DWMessage.class).where("fromDwId and toDwId in (?,?) ", dwID, toID).execute();
		return list;
	}

	/**
	 * 获取当聊天室消息条数
	 * @param roomID
	 * @return
	 */
	public static List<DWMessage> getRoomMsg(String roomID)
	{
		List<DWMessage> list1 = new Select().from(DWMessage.class).where("toDwId = ? ", roomID).execute();
		return list1;
	}

}
