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
 * @Description: TODO (在这里用一句话描述类的作用)
 * @author: Jackchen
 * @date: 2016年5月17日 下午8:47:08
 */
public class HandleMoment
{
    private static final String TAG = "HandleMoment";
    private static HandleMoment instance = new HandleMoment();

    private HandleMoment()
    {
        
    }

    public static HandleMoment getInstance()
    {
        return instance;
    }
    
    /**
     * 提示（用于显示小红点）
     * 朋友圈评论提示
     */
    public void newComment(Message message)
    {
        LogUtils.v(TAG, "newComment() " + message.toString());
        String result = message.getBody();
        SelfExtraInfoManager.getInstance().setNewMomentComment(result);
    }
    
    /**
     * 新的朋友圈
     */
    public void newMoment(Message message)
    {
        LogUtils.v(TAG, "newMoment() " + message.toString());
        String publisherID = message.getBody();
        SelfExtraInfoManager.getInstance().setNewMoment(publisherID);
    }
    
}
