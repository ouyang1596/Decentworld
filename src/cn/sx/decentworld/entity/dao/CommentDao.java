/**
 * 
 */
package cn.sx.decentworld.entity.dao;

import java.util.List;

import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.entity.db.CommentEntity;
import cn.sx.decentworld.logSystem.LogUtils;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

/**
 * @ClassName: CommentDao
 * @Description: TODO (在这里用一句话描述类的作用)
 * @author: Jackchen
 * @date: 2016年5月10日 下午4:12:51
 */
public class CommentDao
{
    private static final String TAG = "CommentDao";
    /**
     * 获取当前用户所有的朋友圈评论数据
     * @return
     */
    public static List<CommentEntity> queryAll()
    {
        String userID = DecentWorldApp.getInstance().getDwID();
        String sql = "userID = ?";
        List<CommentEntity> execute = new Select().from(CommentEntity.class).where(sql, userID).orderBy("commentID asc").execute();
        return execute;
    }
    
    /**
     * 获取当前用户所有的朋友圈评论数据
     * @return
     */
    public static List<CommentEntity> queryList(long momentID)
    {
        String userID = DecentWorldApp.getInstance().getDwID();
        String sql = "userID = ? and momentID = ?";
        List<CommentEntity> execute = new Select().from(CommentEntity.class).where(sql, userID,momentID).orderBy("commentID asc").execute();
        return execute;
    }
    
    
    /**
     * 删除
     * @param momentID 对应的朋友圈ID
     */
    public static void delete(long momentID)
    {
        String userID = DecentWorldApp.getInstance().getDwID();
        String sql = "userID = ? and momentID = ?";
        LogUtils.v(TAG, "delete() sql:"+sql);
        new Delete().from(CommentEntity.class).where(sql, userID,momentID).execute();
    }
    
    /**
     * 删除
     * @param dwID 评论者ID
     */
    public static void deleteByDwID(String dwID)
    {
        String userID = DecentWorldApp.getInstance().getDwID();
        String sql = "userID = ? and dwID = ?";
        LogUtils.v(TAG, "deleteByDwID() sql:"+sql);
        new Delete().from(CommentEntity.class).where(sql, userID,dwID).execute();
    }
    
    /**
     * 删除
     * @param dwID 评论者ID
     */
    public static void deleteByReply(String dwID)
    {
        String userID = DecentWorldApp.getInstance().getDwID();
        String sql = "userID = ? and reply = ?";
        LogUtils.v(TAG, "deleteByReply() sql:"+sql);
        new Delete().from(CommentEntity.class).where(sql, userID,dwID).execute();
    }
    
    /**
     * 删除
     * @param dwID 评论者ID
     */
    public static void deleteByCommentID(long commentID)
    {
        String userID = DecentWorldApp.getInstance().getDwID();
        String sql = "userID = ? and commentID = ?";
        LogUtils.v(TAG, "deleteByCommentID() sql:"+sql);
        new Delete().from(CommentEntity.class).where(sql, userID,commentID).execute();
    }
    
    /**
     * 查询是否存在指定的评论
     */
    public static CommentEntity query(long momentID,long commentID)
    {
        String userID = DecentWorldApp.getInstance().getDwID();
        String sql = "userID = ? and momentID = ? and commentID =?";
        CommentEntity execute = new Select().from(CommentEntity.class).where(sql, userID,momentID,commentID).executeSingle();
        return execute;
    }
}
