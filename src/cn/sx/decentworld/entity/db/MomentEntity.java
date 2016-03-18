/**
 * 
 */
package cn.sx.decentworld.entity.db;

import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.bean.DWMessage;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * @ClassName: MomentEntity.java
 * @Description: 朋友圈实体类
 * @author: cj
 * @date: 2016年2月23日 上午8:57:01
 */
@Table(name = "momentEntity")
public class MomentEntity extends Model implements Comparable<MomentEntity>
{
    /**
     * 朋友圈类型
     */
    public static final int MOMENT_TYPE_TXT = DWMessage.TXT;
    public static final int MOMENT_TYPE_VOICE = DWMessage.VOICE;
    public static final int MOMENT_TYPE_IMAGE = DWMessage.IMAGE;

    /**
     * 朋友圈发布级别
     */
    public static final int LEVEL_ALL = 0;//只有作品圈有
    public static final int LEVEL_FRIEND = 1;
    public static final int LEVEL_FAMILY = 2;
    
    /**
     * 发表朋友圈的状态
     */
    public static final int STATE_UNFINISHED = 0;
    public static final int STATE_SUCCESSED = 1;
    public static final int STATE_FAILURED = 2;

    // 当前用户的ID
    @Column(name = "userID")
    private String userID;

    // 发布者的ID
    @Column(name = "publisherID")
    private String publisherID;

    // 发布者的名字
    @Column(name = "publisherName")
    private String publisherName;

    // 朋友圈ID，在刷新和查询记录时作为索引用
    @Column(name = "remoteID")
    private long remoteID;

    // 朋友圈的类型，包括，文字(0) ，语音( 1)，图片(2)
    @Column(name = "contentType")
    private int contentType;

    // 朋友圈的文字内容，当类型为1,2时该值可为空
    @Column(name = "content")
    private String content;

    // 朋友圈的文件地址，可能是图片或者语音地址，多个地址之间以；号隔开(本地)
    @Column(name = "localUrl")
    private String localUrl;

    // 朋友圈的文件地址，可能是图片或者语音地址，多个地址之间以；号隔开（远程）
    @Column(name = "remoteUrl")
    private String remoteUrl;

    // 朋友圈的发布级别，表示全部人可见(0),朋友可见(1),亲友可见(2))
    @Column(name = "level")
    private int level;

    // 朋友圈发布时间
    @Column(name = "publishTime")
    private long publishTime;

    // 屏蔽列表，若用户选择屏蔽的人时，需将被屏蔽人的ID上传，该值可为空;多个ID之间以“；”号隔开
    @Column(name = "blocklist")
    private String blocklist;

    // 只显示给谁看的列表，若用户选择“之给谁看”时，需将被屏蔽人的ID上传，该值可为空；多个ID之间以“；”号隔开
    @Column(name = "onlyshowtolist")
    private String onlyshowtolist;

    // 评论的次数
    @Column(name = "commentCount")
    private int commentCount;

    // 喜欢的次数
    @Column(name = "likeCount")
    private int likeCount;

    // 不喜欢的次数
    @Column(name = "dislikeCount")
    private int dislikeCount;

    // 扣的次数
    @Column(name = "downCount")
    private int downCount;
    
    // 发布状态:未完成（0）、成功（1）、失败（2）
    @JSONField(serialize = false)
    @Column(name = "momentState")
    private int momentState;
    
    //发布朋友圈时交的金额(全部人可见时需要此字段，否则为0)
    @Column(name = "money")
    private float money;

    /**
     * 构造代码块
     */
    {
        this.userID = DecentWorldApp.getInstance().getDwID();
        this.publisherID = "";
        this.publisherName = "";
        this.remoteID = 0;
        this.contentType = 0;
        this.content = "";
        this.localUrl = "";
        this.remoteUrl = "";
        this.level = 0;
        this.publishTime = 0;
        this.blocklist = "";
        this.onlyshowtolist = "";
        this.commentCount = 0;
        this.likeCount = 0;
        this.dislikeCount = 0;
        this.downCount = 0;
        this.momentState = 0;
        this.money = 0;
    }

    /**
     * 默认构造函数
     */
    public MomentEntity()
    {
        // TODO Auto-generated constructor stub
    }

    /**
     * 下拉刷新返回的结果
     */
    public MomentEntity(String contentType)
    {

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
     * @return the publisherID
     */
    public String getPublisherID()
    {
        return publisherID;
    }

    /**
     * @param publisherID
     *            the publisherID to set
     */
    public void setPublisherID(String publisherID)
    {
        this.publisherID = publisherID;
    }

    /**
     * @return the publisherName
     */
    public String getPublisherName()
    {
        return publisherName;
    }

    /**
     * @param publisherName
     *            the publisherName to set
     */
    public void setPublisherName(String publisherName)
    {
        this.publisherName = publisherName;
    }

    /**
     * @return the remoteID
     */
    public long getRemoteID()
    {
        return remoteID;
    }

    /**
     * @param remoteID
     *            the remoteID to set
     */
    public void setRemoteID(long remoteID)
    {
        this.remoteID = remoteID;
    }

    /**
     * @return the contentType
     */
    public int getContentType()
    {
        return contentType;
    }

    /**
     * @param contentType
     *            the contentType to set
     */
    public void setContentType(int contentType)
    {
        this.contentType = contentType;
    }

    /**
     * @return the content
     */
    public String getContent()
    {
        return content;
    }

    /**
     * @param content
     *            the content to set
     */
    public void setContent(String content)
    {
        this.content = content;
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
     * @return the level
     */
    public int getLevel()
    {
        return level;
    }

    /**
     * @param level
     *            the level to set
     */
    public void setLevel(int level)
    {
        this.level = level;
    }

    /**
     * @return the publishTime
     */
    public long getPublishTime()
    {
        return publishTime;
    }

    /**
     * @param publishTime
     *            the publishTime to set
     */
    public void setPublishTime(long publishTime)
    {
        this.publishTime = publishTime;
    }

    /**
     * @return the blocklist
     */
    public String getBlocklist()
    {
        return blocklist;
    }

    /**
     * @param blocklist
     *            the blocklist to set
     */
    public void setBlocklist(String blocklist)
    {
        this.blocklist = blocklist;
    }

    /**
     * @return the onlyshowtolist
     */
    public String getOnlyshowtolist()
    {
        return onlyshowtolist;
    }

    /**
     * @param onlyshowtolist
     *            the onlyshowtolist to set
     */
    public void setOnlyshowtolist(String onlyshowtolist)
    {
        this.onlyshowtolist = onlyshowtolist;
    }

    /**
     * @return the commentCount
     */
    public int getCommentCount()
    {
        return commentCount;
    }

    /**
     * @param commentCount
     *            the commentCount to set
     */
    public void setCommentCount(int commentCount)
    {
        this.commentCount = commentCount;
    }

    /**
     * @return the likeCount
     */
    public int getLikeCount()
    {
        return likeCount;
    }

    /**
     * @param likeCount
     *            the likeCount to set
     */
    public void setLikeCount(int likeCount)
    {
        this.likeCount = likeCount;
    }

    /**
     * @return the dislikeCount
     */
    public int getDislikeCount()
    {
        return dislikeCount;
    }

    /**
     * @param dislikeCount
     *            the dislikeCount to set
     */
    public void setDislikeCount(int dislikeCount)
    {
        this.dislikeCount = dislikeCount;
    }

    /**
     * @return the downCount
     */
    public int getDownCount()
    {
        return downCount;
    }

    /**
     * @param downCount
     *            the downCount to set
     */
    public void setDownCount(int downCount)
    {
        this.downCount = downCount;
    }
    


    /**
     * @return the momentState
     */
    public int getMomentState()
    {
        return momentState;
    }

    /**
     * @param momentState the momentState to set
     */
    public void setMomentState(int momentState)
    {
        this.momentState = momentState;
    }
    
    

    /**
     * @return the money
     */
    public float getMoney()
    {
        return money;
    }

    /**
     * @param money the money to set
     */
    public void setMoney(float money)
    {
        this.money = money;
    }

    /**
     * 排序
     */
    @Override
    public int compareTo(MomentEntity another)
    {
        return (int) (another.getRemoteID() - this.getRemoteID());
    }

}
