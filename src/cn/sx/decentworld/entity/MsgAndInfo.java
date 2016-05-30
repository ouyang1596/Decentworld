/**
 * 
 */
package cn.sx.decentworld.entity;

import cn.sx.decentworld.entity.db.DWMessage;

/**
 * @ClassName: MsgCom.java
 * @Description: 获取到服务器的消息后，转化成该对象，方便消息路由；
 * @author: cj
 * @date: 2015年12月30日 下午6:16:56
 */
public class MsgAndInfo
{
    /** 消息体 **/
    private DWMessage dwMessage;
    /** 消息发送者附加信息 **/
    private UserSessionInfo userSessionInfo;
    
    /**
     * 
     */
    public MsgAndInfo()
    {
        // TODO Auto-generated constructor stub
    }

    /**
     * @param dwMessage
     * @param userSessionInfo
     */
    public MsgAndInfo(DWMessage dwMessage, UserSessionInfo userSessionInfo)
    {
        this.dwMessage = dwMessage;
        this.userSessionInfo = userSessionInfo;
    }

    /**
     * @return the dwMessage
     */
    public DWMessage getDwMessage()
    {
        return dwMessage;
    }

    /**
     * @param dwMessage the dwMessage to set
     */
    public void setDwMessage(DWMessage dwMessage)
    {
        this.dwMessage = dwMessage;
    }

    /**
     * @return the userSessionInfo
     */
    public UserSessionInfo getUserSessionInfo()
    {
        return userSessionInfo;
    }

    /**
     * @param userSessionInfo the userSessionInfo to set
     */
    public void setUserSessionInfo(UserSessionInfo userSessionInfo)
    {
        this.userSessionInfo = userSessionInfo;
    }


    @Override
    public String toString()
    {
        return "MsgAndInfo [dwMessage=" + dwMessage.toString() + ", userSessionInfo=" + userSessionInfo + "]";
    }

}
