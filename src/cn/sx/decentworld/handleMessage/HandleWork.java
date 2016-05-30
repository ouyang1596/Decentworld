/**
 * 
 */
package cn.sx.decentworld.handleMessage;

import org.jivesoftware.smack.packet.Message;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.sx.decentworld.logSystem.LogUtils;
import cn.sx.decentworld.manager.SelfExtraInfoManager;

/**
 * @ClassName: HandleMoment
 * @Description: 
 * @author: Jackchen
 * @date: 2016年5月17日 下午8:47:08
 */
public class HandleWork
{
    private static final String TAG = "HandleWork";
    private static HandleWork instance = new HandleWork();

    private HandleWork()
    {
        
    }

    public static HandleWork getInstance()
    {
        return instance;
    }
    
    /**
     * 提示（用于显示小红点）
     * 作品圈评论提示
     */
    public void newComment(Message message)
    {
        LogUtils.v(TAG, "newComment() " + message.toString());
        String result = message.getBody();
        SelfExtraInfoManager.getInstance().setNewWorkComment(result);
    }
    
}
