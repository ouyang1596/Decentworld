/**
 * 
 */
package cn.sx.decentworld.entity.db;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import cn.sx.decentworld.DecentWorldApp;

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
     * 朋友圈的发布类型
     */
    public enum MomentType
    {
        TXT(0, "文本"), VOICE(1, "语音"), IMAGE(2, "图片");
        private int index;
        private String info;

        private MomentType(int index, String info)
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

        public static MomentType getByIndex(int index)
        {
            switch (index)
            {
                case 0:
                    return MomentType.TXT;
                case 1:
                    return MomentType.VOICE;
                case 2:
                    return MomentType.IMAGE;
                default:
                    return MomentType.TXT;
            }
        }
    }

    /** 朋友圈的发布级别 **/
    public enum MomentLevel
    {
        FAMILY(2, "亲友"), FRIEND(1, "朋友"), ALL(0, "所有人");
        private int index;
        private String info;

        private MomentLevel(int index, String info)
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

    };

    /**
     * 朋友圈的发布状态
     */
    public enum MomentStatus
    {
        UNFINISHED(0, "发布中"), SUCCESSED(1, "发布成功"), FAILURED(2, "发布失败");
        private int index;
        private String info;

        private MomentStatus(int index, String info)
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
    };

    // 当前用户的ID
    @Column(name = "userID")
    private String userID;

    // 朋友圈ID，在刷新和查询记录时作为索引用
    @Column(name = "momentID")
    private long momentID;

    // 朋友圈的类型，见 enum MomentType
    @Column(name = "contentType")
    private int contentType;

    // 朋友圈的发布级别，见enum MomentLevel
    @Column(name = "level")
    private int level;

    // 朋友圈发布时间
    @Column(name = "publishTime")
    private long publishTime;

    // 发布者的ID
    @Column(name = "publisherID")
    private String publisherID;

    // 发布者的名字
    @Column(name = "publisherName")
    private String publisherName;

    // 朋友圈的文字内容，当类型为1,2时该值可为空
    @Column(name = "content")
    private String content;

    // 朋友圈的文件地址，可能是图片或者语音地址，多个地址之间以；号隔开(本地)
    @Column(name = "localUrl")
    private String localUrl;

    // 朋友圈的文件地址，可能是图片或者语音地址，多个地址之间以；号隔开（远程）
    @Column(name = "remoteUrl")
    private String remoteUrl;

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

    // 被扣的次数
    @Column(name = "downCount")
    private int downCount;

    // 被举报的次数
    @Column(name = "reportCount")
    private int reportCount;

    // 发布状态:见 enum MomentStatus
    @JSONField(serialize = false)
    @Column(name = "momentStatus")
    private int momentStatus;

    // 发布朋友圈时交的金额(全部人可见时需要此字段，否则为0)
    @Column(name = "money")
    private float money;
    
    // 语音路径(网络)
    @Column(name = "remoteVoiceBgUrl")
    private String remoteVoiceBgUrl;
    
    // 语音路径(本地)
    @Column(name = "localVoiceBgUrl")
    private String localVoiceBgUrl;

    // 评论列表
    private List<CommentEntity> commentList;

    /**
     * 构造代码块
     */
    {
        this.userID = DecentWorldApp.getInstance().getDwID();
        this.momentID = 0;
        this.level = 0;
        this.contentType = 0;
        this.publishTime = 0;
        this.publisherID = "";
        this.publisherName = "";
        this.content = "";
        this.localUrl = "";
        this.remoteUrl = "";
        this.blocklist = "";
        this.onlyshowtolist = "";
        this.commentCount = 0;
        this.likeCount = 0;
        this.dislikeCount = 0;
        this.downCount = 0;
        this.reportCount = 0;
        this.momentStatus = MomentStatus.UNFINISHED.getIndex();
        this.money = 0;
        this.remoteVoiceBgUrl = "";
        this.localVoiceBgUrl = "";
        this.commentList = new ArrayList<CommentEntity>();
        
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
     * @return the momentID
     */
    public long getMomentID()
    {
        return momentID;
    }

    /**
     * @param momentID
     *            the momentID to set
     */
    public void setMomentID(long momentID)
    {
        this.momentID = momentID;
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
     * @return the reportCount
     */
    public int getReportCount()
    {
        return reportCount;
    }

    /**
     * @param reportCount
     *            the reportCount to set
     */
    public void setReportCount(int reportCount)
    {
        this.reportCount = reportCount;
    }

    /**
     * @return the momentStatus
     */
    public int getMomentStatus()
    {
        return momentStatus;
    }

    /**
     * @param momentStatus
     *            the momentStatus to set
     */
    public void setMomentStatus(int momentStatus)
    {
        this.momentStatus = momentStatus;
    }

    /**
     * @return the money
     */
    public float getMoney()
    {
        return money;
    }

    /**
     * @param money
     *            the money to set
     */
    public void setMoney(float money)
    {
        this.money = money;
    }

    /**
     * @return the commentList
     */
    public List<CommentEntity> getCommentList()
    {
        return commentList;
    }

    /**
     * @param commentList
     *            the commentList to set
     */
    public void setCommentList(List<CommentEntity> commentList)
    {
        this.commentList = commentList;
    }
    

    /**
     * @return the remoteVoiceBgUrl
     */
    public String getRemoteVoiceBgUrl()
    {
        return remoteVoiceBgUrl;
    }

    /**
     * @param remoteVoiceBgUrl the remoteVoiceBgUrl to set
     */
    public void setRemoteVoiceBgUrl(String remoteVoiceBgUrl)
    {
        this.remoteVoiceBgUrl = remoteVoiceBgUrl;
    }

    /**
     * @return the localVoiceBgUrl
     */
    public String getLocalVoiceBgUrl()
    {
        return localVoiceBgUrl;
    }

    /**
     * @param localVoiceBgUrl the localVoiceBgUrl to set
     */
    public void setLocalVoiceBgUrl(String localVoiceBgUrl)
    {
        this.localVoiceBgUrl = localVoiceBgUrl;
    }

    /**
     * 排序
     */
    @Override
    public int compareTo(MomentEntity another)
    {
        return (int) (another.getMomentID() - this.getMomentID());
    }

    @Override
    public String toString()
    {
        return "MomentEntity [userID=" + userID + ", momentID=" + momentID + ", contentType=" + contentType + ", level=" + level + ", publishTime=" + publishTime + ", publisherID=" + publisherID
                + ", publisherName=" + publisherName + ", content=" + content + ", localUrl=" + localUrl + ", remoteUrl=" + remoteUrl + ", blocklist=" + blocklist + ", onlyshowtolist="
                + onlyshowtolist + ", commentCount=" + commentCount + ", likeCount=" + likeCount + ", dislikeCount=" + dislikeCount + ", downCount=" + downCount + ", reportCount=" + reportCount
                + ", momentStatus=" + momentStatus + ", money=" + money + ", remoteVoiceBgUrl=" + remoteVoiceBgUrl + ", localVoiceBgUrl=" + localVoiceBgUrl + ", commentList=" + commentList + "]";
    }


}
