/**
 * 
 */
package cn.sx.decentworld.logSystem.entity;

import cn.bmob.v3.BmobObject;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.utils.TimeUtils;

/**
 * @ClassName: LogEntity.java
 * @Description: 上传到Bmob服务器日志实体类
 * @author: cj
 * @date: 2016年4月18日 下午6:21:07
 */
public class LogEntity extends BmobObject
{
    //必要参数
    private String userID;
    private String time;
    private String tag;
    /** 不同的日志 **/
    private String vLog;
    private String dLog;
    private String iLog;
    private String wLog;
    private String eLog;

    /**
     * 
     */
    public LogEntity()
    {
        this.userID = DecentWorldApp.getInstance().getDwID();
        this.time = TimeUtils.getDateAllMessage();
        this.tag = "";
        this.vLog = "";
        this.dLog = "";
        this.iLog = "";
        this.wLog = "";
        this.eLog = "";
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
     * @return the tag
     */
    public String getTag()
    {
        return tag;
    }

    /**
     * @param tag
     *            the tag to set
     */
    public void setTag(String tag)
    {
        this.tag = tag;
    }

    /**
     * @return the vLog
     */
    public String getvLog()
    {
        return vLog;
    }

    /**
     * @param vLog
     *            the vLog to set
     */
    public void setvLog(String vLog)
    {
        this.vLog = vLog;
    }

    /**
     * @return the dLog
     */
    public String getdLog()
    {
        return dLog;
    }

    /**
     * @param dLog
     *            the dLog to set
     */
    public void setdLog(String dLog)
    {
        this.dLog = dLog;
    }

    /**
     * @return the iLog
     */
    public String getiLog()
    {
        return iLog;
    }

    /**
     * @param iLog
     *            the iLog to set
     */
    public void setiLog(String iLog)
    {
        this.iLog = iLog;
    }

    /**
     * @return the wLog
     */
    public String getwLog()
    {
        return wLog;
    }

    /**
     * @param wLog
     *            the wLog to set
     */
    public void setwLog(String wLog)
    {
        this.wLog = wLog;
    }

    /**
     * @return the eLog
     */
    public String geteLog()
    {
        return eLog;
    }

    /**
     * @param eLog
     *            the eLog to set
     */
    public void seteLog(String eLog)
    {
        this.eLog = eLog;
    }

}
