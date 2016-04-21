/**
 * 
 */
package cn.sx.decentworld.engine;


/**
 * @ClassName: ChatEngine.java
 * @Description: 单聊和聊天室公共
 * @author: cj
 * @date: 2016年3月26日 下午2:29:59
 */
public class ChatEngine
{
    private static final String TAG = "ChatEngine";
    private static ChatEngine mInstance;

    /**
     * 私有化构造函数
     */
    private ChatEngine()
    {
    }

    /**
     * 获取实例
     * 
     * @return
     */
    public static ChatEngine getInstance()
    {
        if (mInstance == null)
        {
            synchronized (ChatEngine.class)
            {
                if (mInstance == null)
                    mInstance = new ChatEngine();
            }
        }
        return mInstance;
    }

  

}
