/**
 * 
 */
package cn.sx.decentworld.entity.dao;

import java.util.List;

import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.entity.db.NewFriend;

import com.activeandroid.query.Select;

/**
 * @ClassName: NewFriendDao.java
 * @Description: 
 * @author: cj
 * @date: 2016年4月23日 下午3:58:53
 */
public class NewFriendDao
{ /**
     * 查询所有
     * 
     * @return
     */
    public static List<NewFriend> queryAllList()
    {
        String userID = DecentWorldApp.getInstance().getDwID();
        return new Select().from(NewFriend.class).where("userID=?", userID).execute();
    }
    
    
    /**
     * 筛选出没有显示的列表
     * 
     * @return
     */
    public static int queryUnShow()
    {
        String userID = DecentWorldApp.getInstance().getDwID();
        List<NewFriend> list = new Select().from(NewFriend.class).where("userID = ? and isShown = ?", userID, 0).execute();
        return list.size();
    }
    
    /**
     * 查询指定dwID号的记录并返回结果
     * 
     * @param dwID
     */
    public static NewFriend query(String otherID)
    {
        String userID = DecentWorldApp.getInstance().getDwID();
        return new Select().from(NewFriend.class).where("userID = ? and otherID=?", userID, otherID).executeSingle();
    }
    

    /**
     * 删除新朋友列表
     */
    public static void deleteAll()
    {
        String userID = DecentWorldApp.getInstance().getDwID();
        List<NewFriend> list = new Select().from(NewFriend.class).where("userID=?", userID).execute();
        if (list.size() > 0)
        {
            for (NewFriend temp : list)
            {
                temp.delete();
            }
        }
    }

}
