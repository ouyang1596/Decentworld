/**
 * 
 */
package cn.sx.decentworld.entity.dao;

import com.activeandroid.query.Select;

import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.entity.db.SelfInfo;

/**
 * @ClassName: SelfInfoDao
 * @Description: 当前用户信息数据访问层
 * @author: Jackchen
 * @date: 2016年5月4日 下午3:03:38
 */
public class SelfInfoDao
{
    private static final String TAG = "SelfInfoDao";
    
    /**
     * 查询单个,当前用户
     */
    public static SelfInfo query()
    {
        String sql = "userID=?";
        SelfInfo entity = new Select().from(SelfInfo.class).where(sql, DecentWorldApp.getInstance().getDwID()).executeSingle();
        return entity;
    }
    
    /**
     * 查询单个
     */
    public static SelfInfo query(String userID)
    {
        String sql = "userID=?";
        SelfInfo entity = new Select().from(SelfInfo.class).where(sql, userID).executeSingle();
        return entity;
    }
    
    
}
