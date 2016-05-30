/**
 * 
 */
package cn.sx.decentworld.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.sx.decentworld.entity.MessageCache;
import cn.sx.decentworld.entity.dao.DWMessageDao;
import cn.sx.decentworld.logSystem.LogUtils;

/**
 * @ClassName: ChatManager
 * @Description: 聊天管理类
 * @author: Jackchen
 * @date: 2016年5月24日 下午6:02:40
 */
public class ChatManager
{
    private static final String TAG = "ChatManager";
    private static ChatManager instance;
    private List<MessageCache> list;
    // key 为：用户ID+聊天类型
    private Map<String, MessageCache> map;
    private long maxID, minID;

    /**
     * 获取实例
     */
    public static ChatManager getInstance()
    {
        if (instance == null)
            synchronized (ChatManager.class)
            {
                if (instance == null)
                {
                    instance = new ChatManager();
                }
            }
        return instance;
    }

    /**
     * 清除单例
     */
    public static void clear()
    {
        instance = null;
    }

    /**
     * 
     */
    private ChatManager()
    {
        LogUtils.d(TAG, "ChatManager() 第一次初始化");
        list = new ArrayList<MessageCache>();
        map = new HashMap<String, MessageCache>();
        init();
    }

    /**
     * 初始化
     */
    private void init()
    {
        list.clear();
        map.clear();
        initMsgID();
    }

    /**
     * 初始化消息ID最值
     */
    private void initMsgID()
    {
        maxID = DWMessageDao.getMaxID();
        minID = DWMessageDao.getMinID();
        LogUtils.d(TAG, "initMsgID() maxID=" + maxID + ",minID=" + minID);
    }

    /**
     * 获取指定用户的缓存消息
     */
    public MessageCache getMessageCache(String otherID, int chatType)
    {
        LogUtils.d(TAG, "getMessageCache() params[otherID=" + otherID + "，chatType=" + chatType + "]");
        MessageCache messageCache = this.map.get(otherID + chatType);
        if (messageCache == null)
        {
            messageCache = new MessageCache(otherID , chatType);
            this.map.put(otherID + chatType, messageCache);
        }
        return messageCache;
    }

    /**
     * 设置最大ID
     */
    public void setMaxID(long maxID)
    {
        this.maxID = maxID;
    }

    /**
     * 设置最小ID
     */
    public void setMinID(long minID)
    {
        this.minID = minID;
    }

    public long getMaxID()
    {
        return maxID;
    }

    public long getMinID()
    {
        return minID;
    }
    
    
    

}
