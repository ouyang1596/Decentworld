/**
 * 
 */
package cn.sx.decentworld.utils;

import java.io.Reader;
import java.io.StringReader;

import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.util.PacketParserUtils;
import org.xmlpull.v1.XmlPullParser;

import cn.sx.decentworld.common.CommUtil;
import cn.sx.decentworld.logSystem.LogUtils;

/**
 * @ClassName: XmppPacketParseUtils
 * @Description: XmppPacket解析工具
 * @author: Jackchen
 * @date: 2016年4月30日 下午6:21:01
 */
public class XmppPacketParseUtils
{
    private static final String TAG = "XmppPacketParseUtils";
    
    /**
     * 将xml格式的Message转化成对象
     */
    public static Message parseMessage(String xmlMessage)
    {
        Message message = null;
        if(CommUtil.isBlank(xmlMessage))
        {
            LogUtils.e(TAG, "parseMessage() params[xmlMessage="+xmlMessage+"]");
            return null;
        }
        try
        {
            XmlPullParser parser = PacketParserUtils.newXmppParser();
            Reader reader = new StringReader(xmlMessage);
            parser.setInput(reader);
            int eventType = parser.next();
            if (eventType == XmlPullParser.START_TAG)
            {
                if (parser.getName().equals("message"))
                {
                    Packet packet = PacketParserUtils.parseMessage(parser);
                    message = (org.jivesoftware.smack.packet.Message) packet;
                    LogUtils.d(TAG, "parseMessage() packet[message]:" + packet.toXML().toString());
                }
            }
        }
        catch (Exception e)
        {
            LogUtils.e(TAG, "parseMessage() ,解析数据出现异常，Exception:" + e.toString());
        }
        return message;
    }
}
