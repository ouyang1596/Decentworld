/**
 * 
 */
package cn.sx.decentworld.entity.db;

import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.network.utils.JsonUtils;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
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
//    public int ifFromNet = 0;// 0表示从网上获取 1表示本地

    /** 消息方向 **/
    public enum Direct
    {
        RECEIVE, SEND;
    }

    /** 聊天类型 **/
    public enum ChatType
    {
        SINGLE(0, "单聊"), SINGLE_ANONYMITY(1, "单聊匿名"), MULTI(2, "多人");
        private int index;
        private String info;

        private ChatType(int index, String info)
        {
            this.index = index;
            this.info = info;
        }

        public int getIndex()
        {
            return index;
        }

        public String getInfo()
        {
            return info;
        }
    }

    /** 消息类型 **/
    public enum MessageType
    {
        TEXT(0, "文字"), VOICE(1, "语音"), IMAGE(2, "图片"), CARD(3, "名片"), NOTIFY(4, "提示");
        private int index;
        private String info;

        private MessageType(int index, String info)
        {
            this.index = index;
            this.info = info;
        }

        public int getIndex()
        {
            return index;
        }

        public String getInfo()
        {
            return info;
        }

        public static MessageType getType(int value)
        {
            switch (value)
            {
                case 0:
                    return TEXT;
                case 1:
                    return VOICE;
                case 2:
                    return IMAGE;
                case 3:
                    return CARD;
                case 4:
                    return NOTIFY;
                default:
                    return TEXT;
            }
        }
    }

    /** 聊天关系 **/
    public enum ChatRelationship
    {
        FRIEND(0,"朋友"),STRANGER(1,"");
        private int index;
        private String info;
        private ChatRelationship(int index,String info)
        {
            this.index = index;
            this.info = info;
        }
        public int getIndex()
        {
            return index;
        }
        public String getInfo()
        {
            return info;
        }
        
        
    }

    /** 消息发送状态 **/
    public enum SendStatus
    {
        FAILURE(0, "发送失败"), SUCCESS(1, "发送成功"), SENDING(2, "发送中"),SUCCESS_FINISH(3,"成功不能再修改"),FAILURE_LACK_WEALTH(4,"身家不足");
        private int index;
        private String info;

        private SendStatus(int index, String info)
        {
            this.index = index;
            this.info = info;
        }

        public int getIndex()
        {
            return index;
        }

        public String getInfo()
        {
            return info;
        }
        public static SendStatus getType(int index)
        {
            switch (index)
            {
                case 0:
                    return FAILURE;
                case 1:
                    return SUCCESS;
                case 2:
                    return SENDING;
                case 3:
                    return SUCCESS_FINISH;
                case 4:
                    return FAILURE_LACK_WEALTH;
                default:
                    return SUCCESS;
            }
        }
    }

    /** 消息是否已读 **/
    public enum ReadStatus
    {
        NO(0), YES(1);
        private int index;

        private ReadStatus(int index)
        {
            this.index = index;
        }

        public int getIndex()
        {
            return index;
        }
    }

    /** 消息状态 **/
    public enum MessageStatus
    {
        FREE(0, "免费", "#00ff00","下一条消息免费"), NOMAL(1, "正常", "#0000ff","下一条消息正常收费"), OVERFLOW(2, "付费", "#0000ff","下一条消息扣费，对方收不到费用")
        ,DEFAULT(3, "默认", "#ffffff","默认状态，无提示");
        private int index;
        private String info;
        private String color;
        private String content;
        private MessageStatus(int index, String info, String color,String content)
        {
            this.index = index;
            this.info = info;
            this.color = color;
            this.content = content;
        }
        public int getIndex()
        {
            return index;
        }
        public String getInfo()
        {
            return info;
        }
        public String getColor()
        {
            return color;
        }
        public String getContent()
        {
            return content;
        }
        
        //获取类型
        public static MessageStatus getType(String status)
        {
            if(status.equals("FREE"))
                return FREE;
            else if(status.equals("NOMAL"))
                return NOMAL;
            else if(status.equals("OVERFLOW"))
                return OVERFLOW;
            else
                return FREE;
        }
    }

    /**
     * 聊天类型
     */
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
     * 被转发人的名字
     */
    @Column(name = "forwardName")
    private String forwardName;

    /**
     * 用户dwID
     */
    @JSONField(serialize = false)
    @Column(name = "userID")
    private String userID;

    /** 消息认证token **/
    @Column(name = "token")
    private String token;

    /**
     * 发送者的名字
     */
    @JSONField(serialize = false)
    @Column(name = "fromName")
    private String fromName;

    /**
     * 发送者的名字
     */
    @JSONField(serialize = false)
    @Column(name = "fromWorth")
    private float fromWorth;

    // 当前状态
    @Column(name = "currentStatus")
    private String currentStatus;

    //
    @Column(name = "nextStatus")
    private String nextStatus;

    /**
     * 构造代码块
     */
    {
        this.chatRelationship = ChatRelationship.FRIEND.getIndex();// 默认为好友 0
        this.body = "";
        this.time = String.valueOf(System.currentTimeMillis());
        this.wealth = "";
        this.isRead = ReadStatus.NO.getIndex();
        this.voiceTime = -1;
        this.txtMsgID = "";
        this.mid = -1;
        this.localUrl = "";
        this.remoteUrl = "";
        this.uri = "";
        this.forwardDwId = "";
        this.forwardName = "";
        this.userID = DecentWorldApp.getInstance().getDwID();// 自己dwID
        this.token = "";
        this.fromName = "";
        this.fromWorth = 0;
        this.currentStatus = MessageStatus.DEFAULT.name();
        this.nextStatus = MessageStatus.DEFAULT.name();
    }

    /**
     * 无参构造函数
     */
    public DWMessage()
    {
        this.chatType = ChatType.SINGLE.getIndex();// 默认为单聊 0
        this.direct = Direct.SEND.ordinal();// 默认为发送 1
        this.messageType = MessageType.TEXT.getIndex();// 默认为发送文字 0
        this.fromDwId = "";
        this.toDwId = "";
        this.sendSuccess = SendStatus.SENDING.getIndex();
    }

    /**
     * 发送消息的构造函数
     * 
     * @param chatType
     *            聊天类型
     */
    public DWMessage(int chatType)
    {
        this();
        this.chatType = chatType;
    }

    /**
     * 发送消息的构造函数
     * 
     * @param messageType
     *            消息类型
     * @param direct
     *            发送
     */
    public DWMessage(int messageType, Direct direct)
    {
        this.chatType = ChatType.SINGLE.getIndex();// 默认为单聊
        this.messageType = messageType;
        if (direct == Direct.RECEIVE)
            this.direct = Direct.RECEIVE.ordinal();
        else
            this.direct = Direct.SEND.ordinal();
        if (direct == Direct.SEND)
            this.fromDwId = DecentWorldApp.getInstance().getDwID();
        else
            this.toDwId = DecentWorldApp.getInstance().getDwID();

        if (messageType == MessageType.NOTIFY.getIndex())
            this.sendSuccess = SendStatus.SUCCESS.getIndex();//
        else
            this.sendSuccess = SendStatus.SENDING.getIndex();
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
     * @param forwardDwId
     *            the forwardDwId to set
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
     * @param forwardName
     *            the forwardName to set
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
     * @param userID
     *            the userID to set
     */
    public void setUserID(String userID)
    {
        this.userID = userID;
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
     * @return the token
     */
    public String getToken()
    {
        return token;
    }

    /**
     * @param token
     *            the token to set
     */
    public void setToken(String token)
    {
        this.token = token;
    }

    /**
     * @return the fromName
     */
    public String getFromName()
    {
        return fromName;
    }

    /**
     * @param fromName
     *            the fromName to set
     */
    public void setFromName(String fromName)
    {
        this.fromName = fromName;
    }

    /**
     * @return the fromWorth
     */
    public float getFromWorth()
    {
        return fromWorth;
    }

    /**
     * @param fromWorth
     *            the fromWorth to set
     */
    public void setFromWorth(float fromWorth)
    {
        this.fromWorth = fromWorth;
    }

    /**
     * DWMessage对象转化到JSON字符串
     * 
     * @param dwMessage
     * @return
     */
    public static String toJson(DWMessage dwMessage)
    {
        return JsonUtils.bean2json(dwMessage);
    }

    /**
     * JSON对象转化到 DWMessage字符串
     * 
     * @param jsonMessage
     * @return
     */
    public static DWMessage toDWMessage(String jsonMessage)
    {
        return JsonUtils.json2Bean(jsonMessage, DWMessage.class);
    }

    /**
     * @return the currentStatus
     */
    public String getCurrentStatus()
    {
        return currentStatus;
    }

    /**
     * @param currentStatus
     *            the currentStatus to set
     */
    public void setCurrentStatus(String currentStatus)
    {
        this.currentStatus = currentStatus;
    }

    /**
     * @return the nextStatus
     */
    public String getNextStatus()
    {
        return nextStatus;
    }

    /**
     * @param nextStatus
     *            the nextStatus to set
     */
    public void setNextStatus(String nextStatus)
    {
        this.nextStatus = nextStatus;
    }

    /**
     * 排序 首先按照 mid从小到大排序，如果mid相等，那么按照时间从小到大
     */
    @Override
    public int compareTo(DWMessage another)
    {
        if (this.getMid() == another.getMid())
        {
            return (int) (Long.valueOf(this.getTime()) - Long.valueOf(another.getTime()));
        }
        else
        {
            return (int) (this.getMid() - another.getMid());
        }
    }

    @Override
    public String toString()
    {
        return "DWMessage [chatType=" + chatType + ", chatRelationship=" + chatRelationship + ", direct=" + direct + ", messageType=" + messageType + ", fromDwId=" + fromDwId + ", toDwId=" + toDwId
                + ", body=" + body + ", time=" + time + ", wealth=" + wealth + ", isRead=" + isRead + ", voiceTime=" + voiceTime + ", sendSuccess=" + sendSuccess + ", txtMsgID=" + txtMsgID + ", mid="
                + mid + ", uri=" + uri + ", localUrl=" + localUrl + ", remoteUrl=" + remoteUrl + ", forwardDwId=" + forwardDwId + ", forwardName=" + forwardName + ", userID=" + userID + ", token="
                + token + ", fromName=" + fromName + ", fromWorth=" + fromWorth + ", currentStatus=" + currentStatus + ", nextStatus=" + nextStatus + "]";
    }

  

}
