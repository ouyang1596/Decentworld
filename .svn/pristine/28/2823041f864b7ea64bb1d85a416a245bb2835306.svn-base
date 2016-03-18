/**
 * 
 */
package cn.sx.decentworld.entity.db;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * @ClassName: CommentEntity.java
 * @Description: 评论实体
 * @author: cj
 * @date: 2016年2月26日 下午3:33:47
 */
@Table(name = "commentEntity")
public class CommentEntity extends Model
{
    /**
     * 发表评论的状态
     */
    public static final int STATE_UNFINISHED = 0;
    public static final int STATE_SUCCESSED = 1;
    public static final int STATE_FAILURED = 2;
    
    //用户ID
    @JSONField(serialize = false)
    @Column(name = "userID")
    private String userID;
    
    // 评论的ID
    @Column(name = "commentID")
    private long commentID;

    // 评论对应的朋友圈的ID
    @Column(name = "momentId")
    private long momentId;

    //朋友圈发布者ID
    @Column(name = "publisherID")
    private String publisherID;

    //评论人的ID
    @Column(name = "dwID")
    private String dwID;

    //回复人的dwID，如果是匿名回复，则创 32位字符，当无回复人时，该值可为空
    @Column(name = "reply")
    private String reply;
    
    //评论类型,包括普通文字评论，赞，踩，扣
    @Column(name = "type")
    private int type;
    
    //评论内容，当为文字评论时该值不可为空
    @Column(name = "content")
    private String content;

    //评论时间
    @Column(name = "time")
    private long time;
    
    // 发表评论的状态:未完成（0）、成功（1）、失败（2）
    @JSONField(serialize = false)
    @Column(name = "commentState")
    private int commentState;
    
    /**
     * 
     */
    public CommentEntity()
    {
        // TODO Auto-generated constructor stub
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
     * @return the commentID
     */
    public long getCommentID()
    {
        return commentID;
    }

    /**
     * @param commentID the commentID to set
     */
    public void setCommentID(long commentID)
    {
        this.commentID = commentID;
    }

    /**
     * @return the momentId
     */
    public long getMomentId()
    {
        return momentId;
    }

    /**
     * @param momentId the momentId to set
     */
    public void setMomentId(long momentId)
    {
        this.momentId = momentId;
    }

    /**
     * @return the publisherID
     */
    public String getPublisherID()
    {
        return publisherID;
    }

    /**
     * @param publisherID the publisherID to set
     */
    public void setPublisherID(String publisherID)
    {
        this.publisherID = publisherID;
    }

    /**
     * @return the dwID
     */
    public String getDwID()
    {
        return dwID;
    }

    /**
     * @param dwID the dwID to set
     */
    public void setDwID(String dwID)
    {
        this.dwID = dwID;
    }

    /**
     * @return the reply
     */
    public String getReply()
    {
        return reply;
    }

    /**
     * @param reply the reply to set
     */
    public void setReply(String reply)
    {
        this.reply = reply;
    }

    /**
     * @return the type
     */
    public int getType()
    {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(int type)
    {
        this.type = type;
    }

    /**
     * @return the content
     */
    public String getContent()
    {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(String content)
    {
        this.content = content;
    }

    /**
     * @return the time
     */
    public long getTime()
    {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(long time)
    {
        this.time = time;
    }

    /**
     * @return the commentState
     */
    public int getCommentState()
    {
        return commentState;
    }

    /**
     * @param commentState the commentState to set
     */
    public void setCommentState(int commentState)
    {
        this.commentState = commentState;
    }
    

}
