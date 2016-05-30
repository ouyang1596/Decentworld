/**
 * 
 */
package cn.sx.decentworld.handleMessage;

import org.jivesoftware.smack.packet.Message;

import cn.sx.decentworld.engine.ContactEngine;
import cn.sx.decentworld.logSystem.LogUtils;

/**
 * @ClassName: handleFriend
 * @Description: TODO (在这里用一句话描述类的作用)
 * @author: Jackchen
 * @date: 2016年5月12日 上午10:34:32
 */
public class HandleFriend
{
    private static final String TAG = "handleFriend";
    private static HandleFriend instance = new HandleFriend();

    private HandleFriend()
    {
        // 防止产生多个实例对象
    }

    /**
     * 获取单例
     */
    public static HandleFriend getInstance()
    {
        return instance;
    }

    /**
     * 添加朋友
     */
    public void add(Message message)
    {
        LogUtils.v(TAG, "add() " + message.toString());
        ContactEngine.getInstance().addByOther(message);
    }

    /**
     * 同意
     */
    public void accept(Message message)
    {
        LogUtils.v(TAG, "accept() " + message.toString());
        ContactEngine.getInstance().acceptByOther(message);
    }

    /**
     * 拒绝
     */
    public void refuse(Message message)
    {
        LogUtils.v(TAG, "refuse() " + message.toString());
        String fromDwId = message.getFrom().split("@")[0];
        ContactEngine.getInstance().refuseByOther(fromDwId);
    }

    /**
     * 删除
     */
    public void delete(Message message)
    {
        LogUtils.v(TAG, "delete() " + message.toString());
        String fromDwId = message.getFrom().split("@")[0];
        ContactEngine.getInstance().deleteByOther(fromDwId);
    }

}
