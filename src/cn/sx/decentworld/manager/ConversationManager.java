/**
 * 
 */
package cn.sx.decentworld.manager;

/**
 * @ClassName: ConversationManager.java
 * @Description: 管理会话列表
 * @author: cj
 * @date: 2015年12月11日 上午9:50:12
 */
public class ConversationManager
{
    private static final String TAG = "ConversationManager";
    private static ConversationManager instance;

    private ConversationManager()
    {

    }

    /**
     * 获得实例
     * 
     * @return
     */
    public static ConversationManager getInstance()
    {
        if (instance == null)
        {
            synchronized (ConversationManager.class)
            {
                if (instance == null)
                    instance = new ConversationManager();
            }
        }
        return instance;
    }

}
