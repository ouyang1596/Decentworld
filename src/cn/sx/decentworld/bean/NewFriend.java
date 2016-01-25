/**
 * 
 */
package cn.sx.decentworld.bean;

import java.util.List;

import cn.sx.decentworld.DecentWorldApp;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

/**
 * 
 * @ClassName: NewFriend.java
 * @Description: 添加新的朋友
 * @author: cj
 * @date: 2015年12月9日 下午5:45:26
 */
@Table(name = "newFriend")
public class NewFriend extends Model implements Comparable<NewFriend>
{

    // public static final int operate_accept_success = 0;
    // public static final int operate_refuse_success = 1;
    // public static final int operate_fail = 2;

    // 好友添加消息的类型(eg.A添加B)
    public static final int message_had_apply = 3;// 添加之后（A搜索添加之后）
    public static final int message_be_add = 4;// 被添加（B收到一条消息）

    public static final int message_had_add = 5;// 已经添加（B同意）
    public static final int message_had_refused = 6;// 拒绝添加（B拒绝）

    public static final int message_add_success = 7;// 添加成功（A收到接收添加的回执）
    public static final int message_add_fail = 8;// 添加失败（A收到拒绝添加的回执）

    // 用户ID
    @Column(name = "userID")
    public String userID;

    // 对方ID
    @Column(name = "otherID")
    public String otherID;

    // 对方昵称
    @Column(name = "username")
    public String username;

    // 对方头像(暂时不用)
    @Column(name = "icon")
    public String icon;

    // 添加理由
    @Column(name = "addReason")
    public String addReason;

    // 好友添加消息类型
    @Column(name = "messageType")
    public int messageType;

    // 对方身价
    @Column(name = "worth")
    public float worth;

    // 0代表false,1代表true,代表有没有看见过该申请记录
    @Column(name = "isShown")
    public int isShown;

    // 1代表true，0代表false
    @Column(name = "outOfDate")
    public int outOfDate;

    /** 通过哪种方式加的好友 **/
    @Column(name = "searchType")
    public int searchType;// 表示搜索类型而得来的结果

    // 添加好友的时间
    @Column(name = "time")
    public String time;// 表示搜索类型而得来的结果

    /** 保留字段，用于扩展 */
    @Column(name = "remain")
    public String remain;

    /**
     * 构造函数
     */
    public NewFriend()
    {
        this.userID = DecentWorldApp.getInstance().getDwID();
        this.isShown = 0;// 默认没看过
        this.outOfDate = 0;// 默认没有过期
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
     * @return the otherID
     */
    public String getOtherID()
    {
        return otherID;
    }

    /**
     * @param otherID
     *            the otherID to set
     */
    public void setOtherID(String otherID)
    {
        this.otherID = otherID;
    }

    /**
     * @return the username
     */
    public String getUsername()
    {
        return username;
    }

    /**
     * @param username
     *            the username to set
     */
    public void setUsername(String username)
    {
        this.username = username;
    }

    /**
     * @return the icon
     */
    public String getIcon()
    {
        return icon;
    }

    /**
     * @param icon
     *            the icon to set
     */
    public void setIcon(String icon)
    {
        this.icon = icon;
    }

    /**
     * @return the addReason
     */
    public String getAddReason()
    {
        return addReason;
    }

    /**
     * @param addReason
     *            the addReason to set
     */
    public void setAddReason(String addReason)
    {
        this.addReason = addReason;
    }

    /**
     * @return the messageType
     */
    public int getMessageType()
    {
        return messageType;
    }

    /**
     * @param messageType
     *            the messageType to set
     */
    public void setMessageType(int messageType)
    {
        this.messageType = messageType;
    }



    /**
     * @return the worth
     */
    public float getWorth()
    {
        return worth;
    }

    /**
     * @param worth the worth to set
     */
    public void setWorth(float worth)
    {
        this.worth = worth;
    }

    /**
     * @return the isShown
     */
    public int getIsShown()
    {
        return isShown;
    }

    /**
     * @param isShown
     *            the isShown to set
     */
    public void setIsShown(int isShown)
    {
        this.isShown = isShown;
    }

    /**
     * @return the outOfDate
     */
    public int getOutOfDate()
    {
        return outOfDate;
    }

    /**
     * @param outOfDate
     *            the outOfDate to set
     */
    public void setOutOfDate(int outOfDate)
    {
        this.outOfDate = outOfDate;
    }

    /**
     * @return the searchType
     */
    public int getSearchType()
    {
        return searchType;
    }

    /**
     * @param searchType
     *            the searchType to set
     */
    public void setSearchType(int searchType)
    {
        this.searchType = searchType;
    }

    /**
     * @return the time
     */
    public String getTime()
    {
        return time;
    }

    /**
     * @param time
     *            the time to set
     */
    public void setTime(String time)
    {
        this.time = time;
    }

    /**
     * @return the remain
     */
    public String getRemain()
    {
        return remain;
    }

    /**
     * @param remain
     *            the remain to set
     */
    public void setRemain(String remain)
    {
        this.remain = remain;
    }

    /**
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
    public static int selectAllIsNotShown()
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
    public static NewFriend queryByDwID(String otherID)
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

    @Override
    public int compareTo(NewFriend another)
    {
        return 0;
    }

}
