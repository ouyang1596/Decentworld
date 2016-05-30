/**
 * 
 */
package cn.sx.decentworld.entity.db;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * @ClassName: MomentExtraEntity
 * @Description: 朋友圈额外信息
 * @author: Jackchen
 * @date: 2016年5月10日 上午11:06:58
 */
@Table(name = "momentExtraEntity")
public class MomentExtraEntity extends Model
{
    private static final String TAG = "MomentExtraEntity";
    
    //当前用户ID
    @Column(name = "userID")
    private String userID;
    
    //朋友圈最大ID
    @Column(name = "momentIndex")
    private long momentIndex;
    
    //评论最大ID
    @Column(name = "commentIndex")
    private long commentIndex;
    
    /**
     * 
     */
    public MomentExtraEntity()
    {
    }
    
    public MomentExtraEntity(String userID,long momentIndex,long commentIndex)
    {
        this.userID = userID;
        this.momentIndex = momentIndex;
        this.commentIndex = commentIndex;
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
     * @return the momentIndex
     */
    public long getMomentIndex()
    {
        return momentIndex;
    }

    /**
     * @param momentIndex the momentIndex to set
     */
    public void setMomentIndex(long momentIndex)
    {
        this.momentIndex = momentIndex;
    }

    /**
     * @return the commentIndex
     */
    public long getCommentIndex()
    {
        return commentIndex;
    }

    /**
     * @param commentIndex the commentIndex to set
     */
    public void setCommentIndex(long commentIndex)
    {
        this.commentIndex = commentIndex;
    }

    @Override
    public String toString()
    {
        return "MomentExtraEntity [userID=" + userID + ", momentIndex=" + momentIndex + ", commentIndex=" + commentIndex + "]";
    }
    
    
    
    
}
