/**
 * 
 */
package cn.sx.decentworld.handleMessage;

import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;

import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.connect.XmppHelper;
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
    private static ComfirmSystem instance = new ComfirmSystem();

    private ComfirmSystem()
    {
    }

    /**
     * 获取实例
     */
    public static ComfirmSystem getInstance()
    {
        return instance;
    }

    /**
     * 收到消息后给服务器4号回执
     * 
     * @param mid
     */
    public void sendFourAck(long mid, String fromDwID, String packetID)
    {
        LogUtils.v(TAG, "sendFourAck(4号消息) params[mid=" + mid + ",fromDwID=" + fromDwID + ",packetID=" + packetID + "]");
        Message message = new Message();
        message.setSubject("RECV");
        String body = "{\"mid\":\"" + mid + "\",\"fromID\":\"" + fromDwID + "\",\"dwID\":\"" + DecentWorldApp.getInstance().getDwID() + "\"}";
        message.setBody(body);
        message.setPacketID(packetID);// 设置为3号消息的ID
        XMPPTCPConnection connection = XmppHelper.getConnection();
        if (connection != null)
        {
            try
            {
                connection.sendPacket(message);
                LogUtils.v(TAG, "sendFourAck() sendPacket");
            }
            catch (NotConnectedException e)
            {
                LogUtils.e(TAG, "sendFourAck() NotConnectedException,mid=" + mid, true);
            }
        }
        else
        {
            LogUtils.e(TAG, "sendFourAck() connection=null", true);
        }
    }

    /**
     * 接收到消息送达后给服务器回执
     */
    public void sendDeliveredAck(long mid)
    {
        LogUtils.v(TAG, "sendDeliveredAck(6号消息) params[mid=" + mid + "]");
        Message message = new Message();
        message.setSubject("RECV_CONFIRM");
        message.setBody("" + mid);
        XMPPTCPConnection connection = XmppHelper.getConnection();
        if (connection != null)
        {
            try
            {
                connection.sendPacket(message);
            }
            catch (NotConnectedException e)
            {
                LogUtils.e(TAG, "sendDeliveredAck() NotConnectedException，mid=" + mid, true);
            }
        }
    }

}
