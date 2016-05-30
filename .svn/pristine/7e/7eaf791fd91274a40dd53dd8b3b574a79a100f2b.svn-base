package cn.sx.decentworld.entity.server;

import java.util.Date;

/**
 * 评论记录 存储评论的实际内容，而在朋友圈等中只是保留评论ID进行关联
 */
public class Comment
{
    private static final String TAG = "Comment";
    /**
     * 自增长主键
     */
    private Long commentID;

    /**
     * 作品Id
     */
    private Long momentID;

    /**
     * 朋友圈发布者ID
     */
    private String publisherID;

    /**
     * 评论人
     */
    private String dwID;

    /**
     * 回复人
     */
    private String reply;
    /**
     * 评论类型,包括普通文字评论，赞，踩，扣
     */
    private int type;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 评论时间
     */
    private Date time;

    /**
     * 评论人名字
     */
    private boolean isAnonymous;

    public Comment()
    {
    }

    /**
     * @return the commentID
     */
    public Long getCommentID()
    {
        return commentID;
    }

    /**
     * @param commentID
     *            the commentID to set
     */
    public void setCommentID(Long commentID)
    {
        this.commentID = commentID;
    }

    /**
     * @return the momentID
     */
    public Long getMomentID()
    {
        return momentID;
    }

    /**
     * @param momentID
     *            the momentID to set
     */
    public void setMomentID(Long momentID)
    {
        this.momentID = momentID;
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
     * @return the dwID
     */
    public String getDwID()
    {
        return dwID;
    }

    /**
     * @param dwID
     *            the dwID to set
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
     * @param reply
     *            the reply to set
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
     * @param type
     *            the type to set
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
     * @param content
     *            the content to set
     */
    public void setContent(String content)
    {
        this.content = content;
    }

    /**
     * @return the time
     */
    public Date getTime()
    {
        return time;
    }

    /**
     * @param time
     *            the time to set
     */
    public void setTime(Date time)
    {
        this.time = time;
    }

    /**
     * @return the isAnonymous
     */
    public boolean isAnonymous()
    {
        return isAnonymous;
    }

    /**
     * @param isAnonymous
     *            the isAnonymous to set
     */
    public void setAnonymous(boolean isAnonymous)
    {
        this.isAnonymous = isAnonymous;
    }

    @Override
    public String toString()
    {
        return "Comment [commentID=" + commentID + ", momentID=" + momentID + ", publisherID=" + publisherID + ", dwID=" + dwID + ", reply=" + reply + ", type=" + type + ", content=" + content
                + ", time=" + time + ", isAnonymous=" + isAnonymous + "]";
    }

}
