/**
 * 
 */
package cn.sx.decentworld.entity.dao;

import com.activeandroid.query.Select;

import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.entity.db.MomentExtraEntity;

/**
 * @ClassName: MomentExtraDao
 * @Description: 朋友圈额外信息数据访问类
 * @author: Jackchen
 * @date: 2016年5月10日 上午11:11:59
 */
public class MomentExtraDao
{
    private static final String TAG = "MomentExtraDao";
    
    /**
     * 查询当前用户的记录
     */
    public static MomentExtraEntity query()
    {
        String userID = DecentWorldApp.getInstance().getDwID();
        String sql = "userID = ?";
        MomentExtraEntity entity = new Select().from(MomentExtraEntity.class).where(sql, userID).executeSingle();
        return entity;
    }
    
    /**
     * 查询指定用户的记录
     * @param userID 用户ID
     */
    public static MomentExtraEntity query(String userID)
    {
        String sql = "userID = ?";
        MomentExtraEntity entity = new Select().from(MomentExtraEntity.class).where(sql, userID).executeSingle();
        return entity;
    }
    
    
    
    
    
}
