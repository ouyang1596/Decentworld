/**
 * 
 */
package cn.sx.decentworld.stateMachine.param;

import cn.sx.decentworld.entity.db.DWMessage.SendStatus;

/**
 * @ClassName: RefreshParam.java
 * @Description: 状态机通知界面更新时传递的参数
 * @author: cj
 * @date: 2016年4月26日 下午4:08:55
 */
public class RefreshParam
{
    //消息对应的ID
    private String packetID;
    //消息将要更新的状态
    private SendStatus sendStatus;

    /**
     * @param packetID
     * @param sendStatus
     */
    public RefreshParam(String packetID, SendStatus sendStatus)
    {
        this.packetID = packetID;
        this.sendStatus = sendStatus;
    }

    /**
     * @return the packetID
     */
    public String getPacketID()
    {
        return packetID;
    }

    /**
     * @param packetID
     *            the packetID to set
     */
    public void setPacketID(String packetID)
    {
        this.packetID = packetID;
    }

    /**
     * @return the sendStatus
     */
    public SendStatus getSendStatus()
    {
        return sendStatus;
    }

    /**
     * @param sendStatus
     *            the sendStatus to set
     */
    public void setSendStatus(SendStatus sendStatus)
    {
        this.sendStatus = sendStatus;
    }

    @Override
    public String toString()
    {
        return "RefreshParam [packetID=" + packetID + ", sendStatus=" + sendStatus + "]";
    }
    
    
    

}
