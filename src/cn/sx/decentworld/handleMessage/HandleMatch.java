/**
 * 
 */
package cn.sx.decentworld.handleMessage;

import org.jivesoftware.smack.packet.Message;
import org.simple.eventbus.EventBus;

import cn.sx.decentworld.common.NotifyByEventBus;
import cn.sx.decentworld.logSystem.LogUtils;

/**
 * @ClassName: HandleMatch
 * @Description: TODO (在这里用一句话描述类的作用)
 * @author: Jackchen
 * @date: 2016年5月12日 上午10:35:37
 */
public class HandleMatch
{
    private static final String TAG = "HandleMatch";
    
    private static HandleMatch instance = new HandleMatch();

    private HandleMatch()
    {
        // 防止产生多个实例对象
    }

    /**
     * 获取单例
     */
    public static HandleMatch getInstance()
    {
        return instance;
    }
    
    public void match(Message message)
    {
        LogUtils.v(TAG, "match() " + message.toString());
        EventBus.getDefault().post(message.getBody(), NotifyByEventBus.NT_MATCH);
    }
}
