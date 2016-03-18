/**
 * 
 */
package cn.sx.decentworld.bean;

import java.util.List;

import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.utils.LogUtils;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

/**
 * @ClassName: MyProtege.java
 * @Description:贵人
 * @author: cj
 * @date: 2015年9月25日 上午11:11:51
 */

@Table(name = "noblePerson")
public class NoblePerson extends Model
{
    private static final String TAG = "NoblePerson";
    /** 代表 我是谁的贵人 **/
    public static final int GR_TO_OTHER = 0;
    /** 代表 谁是我的贵人 **/
    public static final int GR_TO_ME = 1;

    /** 当前用户ID **/
    @Column(name = "userID")
    private String userID;

    /** 0代表我是谁的贵人，1代表谁是我的贵人 **/
    @Column(name = "direct")
    private int direct;

    /** 贵人ID **/
    @Column(name = "otherID")
    private String otherID;

    /** 贵人性别 **/
    @Column(name = "gender")
    private String gender;

    /** 贵人职业 **/
    @Column(name = "occupation")
    private String occupation;

    /** 贵人名字 **/
    @Column(name = "showName")
    private String showName;

    /** 贵人类型 **/
    @Column(name = "userType")
    private int userType;

    /** 贵人身价 **/
    @Column(name = "worth")
    private float worth;

    public NoblePerson(int direct)
    {
        this.userID = DecentWorldApp.getInstance().getDwID();
        this.direct = direct;
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
     * @return the gender
     */
    public String getGender()
    {
        return gender;
    }

    /**
     * @param gender
     *            the gender to set
     */
    public void setGender(String gender)
    {
        this.gender = gender;
    }

    /**
     * @return the occupation
     */
    public String getOccupation()
    {
        return occupation;
    }

    /**
     * @param occupation
     *            the occupation to set
     */
    public void setOccupation(String occupation)
    {
        this.occupation = occupation;
    }

    /**
     * @return the showName
     */
    public String getShowName()
    {
        return showName;
    }

    /**
     * @param showName
     *            the showName to set
     */
    public void setShowName(String showName)
    {
        this.showName = showName;
    }

    

    /**
     * @return the userType
     */
    public int getUserType()
    {
        return userType;
    }

    /**
     * @param userType the userType to set
     */
    public void setUserType(int userType)
    {
        this.userType = userType;
    }

    /**
     * @return the worth
     */
    public float getWorth()
    {
        return worth;
    }

    /**
     * @param worth
     *            the worth to set
     */
    public void setWorth(float worth)
    {
        this.worth = worth;
    }

    /**
     * @return the direct
     */
    public int getDirect()
    {
        return direct;
    }

    /**
     * @param direct
     *            the direct to set
     */
    public void setDirect(int direct)
    {
        this.direct = direct;
    }

    /**
     * 删除消息列表
     */
    public static void deleteAll()
    {
        new Delete().from(NoblePerson.class).execute();
    }
    
    /**
     * 删除我的贵人
     */
    public static void deleteToMe()
    {
        String userID = DecentWorldApp.getInstance().getDwID();
        int direct = GR_TO_ME;
        new Delete().from(NoblePerson.class).where("userID=? and direct=?", userID, direct).execute();
    }
    
    /**
     * 删除我最为别人的贵人
     */
    public static void deleteToOther()
    {
        String userID = DecentWorldApp.getInstance().getDwID();
        int direct = GR_TO_OTHER;
        new Delete().from(NoblePerson.class).where("userID=? and direct=?", userID, direct).execute();
    }
    
    /**
     * 查询谁是我的贵人（有问题）
     * @return
     */
    public static NoblePerson queryToMe()
    {
        String userID = DecentWorldApp.getInstance().getDwID();
        int direct = GR_TO_ME;
        LogUtils.i(TAG, "userID="+userID+",direct="+direct);
//        NoblePerson noblePerson = new Select().from(NoblePerson.class).where("userID=? and direct =?", userID, direct).executeSingle();
        List<NoblePerson> noblePerson = new Select().from(NoblePerson.class).where("userID=? and direct =?", userID, 1).execute();
        if(noblePerson.size()>0)
        {
            LogUtils.i(TAG, "查询谁是我的贵人不为空,size="+noblePerson.size());
            return noblePerson.get(0);
        }
        else
        {
            LogUtils.i(TAG, "查询谁是我的贵人为空");
            return null;
        }
    }

    /**
     * 查询我是谁的贵人
     * @return
     */
    public static List<NoblePerson> queryToOther()
    {
        String userID = DecentWorldApp.getInstance().getDwID();
        int direct = GR_TO_OTHER;
        List<NoblePerson> list = new Select().from(NoblePerson.class).where("userID=? and direct =?", userID, 0).execute();
        LogUtils.i(TAG, "查询我是谁的贵人，size="+list.size());
        return list;
    }

}
