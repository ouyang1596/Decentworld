/**
 * 
 */
package cn.sx.decentworld.logSystem.entity;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.utils.TimeUtils;

/**
 * @ClassName: LogFile.java
 * @Description: 上传日志文件
 * @author: cj
 * @date: 2016年4月19日 上午11:39:49
 */
public class LogFileEntity extends BmobObject
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    //必要参数
    private String userID;
    private String time;
    private String tag;
    //上传的文件
    private BmobFile logFile;
    
    
    /**
     * 
     */
    public LogFileEntity()
    {
        this.userID = DecentWorldApp.getInstance().getDwID();
        this.time = TimeUtils.getDateAllMessage();
        this.tag = "";
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
     * @return the time
     */
    public String getTime()
    {
        return time;
    }


    /**
     * @param time the time to set
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
     * @param tag the tag to set
     */
    public void setTag(String tag)
    {
        this.tag = tag;
    }


    /**
     * @return the logFile
     */
    public BmobFile getLogFile()
    {
        return logFile;
    }


    /**
     * @param logFile the logFile to set
     */
    public void setLogFile(BmobFile logFile)
    {
        this.logFile = logFile;
    }
    
    
    
    

}
