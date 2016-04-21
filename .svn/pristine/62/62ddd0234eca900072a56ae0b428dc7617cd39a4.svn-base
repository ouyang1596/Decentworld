/**
 * 
 */
package cn.sx.decentworld.task;

import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;

import cn.sx.decentworld.common.XmppHelper;
import cn.sx.decentworld.logSystem.LogUtils;

/**
 * @ClassName: ComfirmSystem.java
 * @Description: 消息确认系统
 * @author: cj
 * @date: 2016年4月20日 下午3:37:12
 */
public class ComfirmSystem
{
    private static final String TAG = "ComfirmSystem";
    private static ComfirmSystem instance;
    
    /**
     * 
     */
    private ComfirmSystem()
    {
        // TODO Auto-generated constructor stub
    }
    
    /**
     * 获取实例
     * @return
     */
    public static ComfirmSystem getInstance()
    {
        if(instance == null)
        {
            synchronized (ComfirmSystem.class)
            {
                if(instance == null)
                    instance = new ComfirmSystem();
            }
        }
        return instance;
    }
    
    /**
     * 收到消息后给服务器回执
     * @param mid
     */
    public void recMsgAck(long mid)
    {
        Message message = new Message();
        message.setSubject("RECV");
        message.setBody(""+mid);
        XMPPTCPConnection connection = XmppHelper.getConnection();
        if(connection!=null)
        {
            try
            {
                connection.sendPacket(message);
            }
            catch (NotConnectedException e)
            {
                LogUtils.e(TAG, "recMsgAck...mid="+mid,true);
                e.printStackTrace();
            }
        }
    }
    
    /**
     * 接收到消息送达后给服务器回执
     */
    public void recMsgDeliveredAck(long mid)
    {
        Message message = new Message();
        message.setSubject("RECV_CONFIRM");
        message.setBody(""+mid);
        XMPPTCPConnection connection = XmppHelper.getConnection();
        if(connection!=null)
        {
            try
            {
                connection.sendPacket(message);
            }
            catch (NotConnectedException e)
            {
                LogUtils.e(TAG, "recMsgDeliveredAck...NotConnectedException，mid="+mid,true);
                e.printStackTrace();
            }
        }
    }
    

}
