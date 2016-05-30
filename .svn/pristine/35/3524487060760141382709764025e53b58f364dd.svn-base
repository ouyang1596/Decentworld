/**
 * 
 */
package cn.sx.decentworld.handleMessage;

import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;

import cn.sx.decentworld.handleMessage.MessageSubject.Subject;

/**
 * @ClassName: SubjectPacket
 * @Description: TODO (在这里用一句话描述类的作用)
 * @author: Jackchen
 * @date: 2016年5月23日 下午3:26:53
 */
public class DWPacketFilter implements PacketFilter
{
    private static final String TAG = "DWPacketFilter";

    @Override
    public boolean accept(Packet packet)
    {
        if (!(packet instanceof Message))
        {
            return false;
        }
        Message m = (Message) packet;
        Subject[] values = Subject.values();
        for (Subject subject : values)
        {
            if ((m.getSubject().equals(subject.getBody())))
            {
                return true;
            }
        }
        return false;
    }
}
