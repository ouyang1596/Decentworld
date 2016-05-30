/**
 * 
 */
package cn.sx.decentworld.handleMessage;

import org.jivesoftware.smack.packet.Message;
import org.simple.eventbus.EventBus;

import cn.sx.decentworld.common.NotifyByEventBus;
import cn.sx.decentworld.logSystem.LogUtils;

/**
 * @ClassName: handleChatRoom
 * @Description: TODO (在这里用一句话描述类的作用)
 * @author: Jackchen
 * @date: 2016年5月12日 上午10:34:48
 */
public class HandleChatRoom
{
    private static final String TAG = "handleChatRoom";
    private static HandleChatRoom instance = new HandleChatRoom();

    private HandleChatRoom()
    {
        // 防止产生多个实例对象
    }

    /**
     * 获取单例
     */
    public static HandleChatRoom getInstance()
    {
        return instance;
    }

    /**
     * 进入聊天室
     */
    public void enter(Message message)
    {
        String str = message.getBody();
        LogUtils.i(TAG, "enter() " + message.toString());
        EventBus.getDefault().post(message, NotifyByEventBus.NT_CHAT_ROOM_JOIN);
    }

    /**
     * 离开聊天室
     */
    public void leave(Message message)
    {
        String str = message.getBody();
        LogUtils.i(TAG, "leave() " + message.toString());
        EventBus.getDefault().post(message, NotifyByEventBus.NT_CHAT_ROOM_LEAVE);
    }
}
