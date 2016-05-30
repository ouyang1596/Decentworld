/**
 * 
 */
package cn.sx.decentworld.entity.dao;

import cn.sx.decentworld.entity.db.SelfExtraInfo;

import com.activeandroid.query.Select;

/**
 * @ClassName: UserExtraInfoDao.java
 * @Description: 用户额外信息数据访问层
 * @author: cj
 * @date: 2016年4月25日 上午11:32:59
 */
public class SelfExtraInfoDao
{
    /**
     * 通过ID号查找单条用户记录并返回
     * @param userID
     * @return
     */
    public static SelfExtraInfo query(String userID)
    {
        return new Select().from(SelfExtraInfo.class).where("userID = ?", userID).executeSingle();
    }

}
