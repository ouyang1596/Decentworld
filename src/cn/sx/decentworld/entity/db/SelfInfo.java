/**
 * 
 */
package cn.sx.decentworld.entity.db;

import cn.sx.decentworld.DecentWorldApp;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * @ClassName: SelfInfoEntity
 * @Description: 个人信息实体类
 * @author: Jackchen
 * @date: 2016年5月3日 下午8:12:19
 */
@Table(name = "selfInfo")
public class SelfInfo extends Model
{
    private static final String TAG = "SelfInfoEntity";
    
    @Column(name = "userID")
    private String userID;
    
    @Column(name = "versionNum")
    private String versionNum;
    
    @Column(name = "content")
    private String content;
    
    public SelfInfo()
    {
        this.userID = DecentWorldApp.getInstance().getDwID();
        this.versionNum = "-1";
        this.content = "";
    }

    /**
     * @param userID
     * @param versionNum
     * @param content
     */
    public SelfInfo(String userID, String versionNum, String content)
    {
        this();
        this.userID = userID;
        this.versionNum = versionNum;
        this.content = content;
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
     * @return the versionNum
     */
    public String getVersionNum()
    {
        return versionNum;
    }

    /**
     * @param versionNum the versionNum to set
     */
    public void setVersionNum(String versionNum)
    {
        this.versionNum = versionNum;
    }

    /**
     * @return the content
     */
    public String getContent()
    {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(String content)
    {
        this.content = content;
    }

    @Override
    public String toString()
    {
        return "SelfInfoEntity [userID=" + userID + ", versionNum=" + versionNum + ", content=" + content + "]";
    }
    
    
    
}
