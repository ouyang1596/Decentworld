/**
 * 
 */
package cn.sx.decentworld.task;

import org.jivesoftware.smack.PacketCollector;
import org.jivesoftware.smack.packet.Packet;

import android.content.Context;
import cn.sx.decentworld.utils.LogUtils;

/**
 * @ClassName: CollectorThreadStarter.java
 * @Description:
 * @author: cj
 * @date: 2015年12月16日 上午8:54:59
 */
public class CollectorThreadStarter extends Thread
{
    private Context context;
    private PacketCollector collector;
    private static String TAG = "CollectorThreadStarter";

    public CollectorThreadStarter(PacketCollector _collector, Context _context)
    {
        this.collector = _collector;
        this.context = _context;
    }

    @Override
    public void run()
    {
        while (true)
        {
            Packet packet = collector.nextResultBlockForever();
//            LogUtils.i(TAG, "CollectorThreadStarter...alive,ThreadName=" + hashCode() + ",packet=" + packet.toXML());
            try
            {
                Class<? extends DWPacketHandler> executor = collector.getExecutor();
                DWPacketHandler handler = executor.newInstance();
                handler.init(packet, context);
                TaskExecutor.Execute(handler);
            }
            catch (InstantiationException e)
            {
                LogUtils.i(TAG, "error1");
                e.printStackTrace();
            }
            catch (IllegalAccessException e)
            {
                LogUtils.i(TAG, "error2");
                e.printStackTrace();
            }
        }
    }

}
