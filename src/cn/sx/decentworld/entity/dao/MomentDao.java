/**
 * 
 */
package cn.sx.decentworld.entity.dao;

import java.util.List;

import com.activeandroid.Model;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.entity.db.CommentEntity;
import cn.sx.decentworld.entity.db.MomentEntity;
import cn.sx.decentworld.logSystem.LogUtils;

/**
 * @ClassName: MomentDao.java
 * @Description: 朋友圈数据访问
 * @author: cj
 * @date: 2016年4月11日 上午10:54:24
 */
public class MomentDao
{
    private static final String TAG = "MomentDao";
    // 每次拿取是数量
    private static final int count = 10;

    /**
     * 获取当前用户所有的朋友圈数据
     * 
     * @return
     */
    public static List<MomentEntity> getAllData()
    {
        String userID = DecentWorldApp.getInstance().getDwID();
        String sql = "userID = ?";
        List<MomentEntity> execute = new Select().from(MomentEntity.class).where(sql, userID).limit(count).orderBy("momentID desc").execute();
        return execute;
    }

    /**
     * 清除当前用户的所有朋友圈数据
     * 
     * @return
     */
    public static boolean cleanAllData()
    {
        String userID = DecentWorldApp.getInstance().getDwID();
        String sql = "userID = ?";
        new Delete().from(MomentEntity.class).where(sql, userID).execute();
        return true;
    }
    
    /**
     * 获取朋友圈列表
     */
    public static List<MomentEntity> getList(long minMomentID)
    {
        String userID = DecentWorldApp.getInstance().getDwID();
        String sql = "userID = ? and momentID < ?";
        List<MomentEntity> execute = new Select().from(MomentEntity.class).where(sql, userID,minMomentID).limit(count).orderBy("momentID desc").execute();
        return execute;
    }
    
    /**
     * 删除指定dwID发布的所有朋友圈
     * @return
     */
    public static void delete(String publisherID)
    {
        String userID = DecentWorldApp.getInstance().getDwID();
        String sql = "userID = ? and publisherID = ?";
        LogUtils.v(TAG, "delete() sql:"+sql);
        new Delete().from(MomentEntity.class).where(sql, userID,publisherID).execute();
    }
    
    /**
     * 删除指定momentID的朋友圈
     * @return
     */
    public static void deleteByMomentID(long momentID)
    {
        String userID = DecentWorldApp.getInstance().getDwID();
        String sql = "userID = ? and momentID = ?";
        LogUtils.v(TAG, "delete() sql:"+sql);
        new Delete().from(MomentEntity.class).where(sql, userID,momentID).execute();
    }
    
    /**
     * 查询是否存在指定ID的作品圈
     */
    public static MomentEntity query(long momentID)
    {
        String userID = DecentWorldApp.getInstance().getDwID();
        String sql = "userID = ? and momentID = ?";
        MomentEntity execute = new Select().from(MomentEntity.class).where(sql, userID,momentID).executeSingle();
        return execute;
    }
}
