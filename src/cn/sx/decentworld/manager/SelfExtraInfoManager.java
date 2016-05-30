/**
 * 
 */
package cn.sx.decentworld.manager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.simple.eventbus.EventBus;

import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.common.CommUtil;
import cn.sx.decentworld.common.NotifyByEventBus;
import cn.sx.decentworld.entity.NewMomentComment;
import cn.sx.decentworld.entity.NewWorkComment;
import cn.sx.decentworld.entity.dao.SelfExtraInfoDao;
import cn.sx.decentworld.entity.db.SelfExtraInfo;
import cn.sx.decentworld.logSystem.LogUtils;
import cn.sx.decentworld.network.utils.JsonUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @ClassName: UserExtraInfoManager
 * @Description: TODO (在这里用一句话描述类的作用)
 * @author: Jackchen
 * @date: 2016年5月18日 上午9:38:46
 */
public class SelfExtraInfoManager
{
    private static final String TAG = "UserExtraInfoManager";
    private static SelfExtraInfoManager instance;
    private SelfExtraInfo userExtraInfo;
    private List<NewMomentComment> newMomentComments;
    private List<NewWorkComment> newWorkComments;
    private String newMoment = "";
    private String newWork = "";

    public static SelfExtraInfoManager getInstance()
    {
        if (instance == null)
        {
            synchronized (SelfExtraInfoManager.class)
            {
                if (instance == null)
                    instance = new SelfExtraInfoManager();
            }
        }
        return instance;
    }
    
    /**
     * 当用户手动退出软件后，调用该函数清理内存中的数据
     */
    public static void clear()
    {
        instance = null;
    }

    /**
     * 获取实体
     */
    public SelfExtraInfo getEntity()
    {
        return userExtraInfo;
    }

    /**
     * 收到一条朋友圈新的评论
     */
    public void setNewMomentComment(String jsonStr)
    {
        LogUtils.v(TAG, "setNewMomentComment() params[jsonStr=" + jsonStr + "]");
        if (newMomentComments == null)
            loadNewMomentComment();
        // 解析数据
        JSONObject jsonObject = JSON.parseObject(jsonStr);
        String dwID = jsonObject.getString("dwID");
        String momentID = jsonObject.getString("momentID");
        float wealth = jsonObject.getFloatValue("wealth");
        SelfInfoManager.getInstance().notifyWealthChanged(wealth);
        NewMomentComment temp = new NewMomentComment(newMomentComments.size() + 1 , momentID , dwID);
        newMomentComments.add(temp);
        // 保存数据
        userExtraInfo.setNewMomentComment(JSON.toJSONString(newMomentComments));
        userExtraInfo.save();
        // 通知界面刷新
        EventBus.getDefault().post("快去拿数据吧", NotifyByEventBus.NT_NEW_MOMENT_COMMENT);
    }

    /**
     * 获取朋友圈新的评论列表
     */
    public List<NewMomentComment> getNewMomentComment()
    {
        LogUtils.v(TAG, "getNewMomentComment() ");
        if (newMomentComments == null)
            loadNewMomentComment();
        Collections.sort(newMomentComments);
        return newMomentComments;
    }

    /**
     * 去掉新的评论小红点
     */
    public void clearNewMomentComment()
    {
        userExtraInfo.setNewMomentComment("");
        userExtraInfo.save();
        LogUtils.v(TAG, "cleanNewMomentComment() ");
        if (newMomentComments == null)
            loadNewMomentComment();
        newMomentComments.clear();
    }

    /**
     * 收到一条作品圈新的评论
     */
    public void setNewWorkComment(String jsonStr)
    {
        LogUtils.v(TAG, "setNewWorkComment() params[jsonStr=" + jsonStr + "]");
        if (newWorkComments == null)
            loadNewWorkComment();
        // 解析数据
        JSONObject jsonObject = JSON.parseObject(jsonStr);
        String dwID = jsonObject.getString("dwID");
        String momentID = jsonObject.getString("workID");
        NewWorkComment temp = new NewWorkComment(newWorkComments.size() + 1 , momentID , dwID);
        newWorkComments.add(temp);
        // 保存数据
        userExtraInfo.setNewWorkComment(JSON.toJSONString(newWorkComments));
        userExtraInfo.save();
        // 通知界面刷新
        EventBus.getDefault().post("快去拿数据吧", NotifyByEventBus.NT_NEW_WORK_COMMENT);
    }

    /**
     * 获取作品圈新的评论列表
     */
    public List<NewWorkComment> getNewWorkComment()
    {
        LogUtils.v(TAG, "getNewWorkComment() ");
        if (newWorkComments == null)
            loadNewWorkComment();
        Collections.sort(newWorkComments);
        return newWorkComments;
    }

    /**
     * 去掉新的评论小红点
     */
    public void clearNewWorkComment()
    {
        LogUtils.v(TAG, "cleanNewWorkComment() ");
        if (newWorkComments == null)
            loadNewWorkComment();
        newWorkComments.clear();
        userExtraInfo.setNewWorkComment("");
        userExtraInfo.save();
    }

    /**
     * 获取两个圈的新评论总条数
     */
    public int getCircleCount()
    {
        int count = 0;
        List<NewMomentComment> newMomentComment = getNewMomentComment();
        if (newMomentComment != null && newMomentComment.size() > 0)
        {
            count = count + newMomentComment.size();
        }
        List<NewWorkComment> newWorkComment = getNewWorkComment();
        if (newWorkComment != null && newWorkComment.size() > 0)
        {
            count = count + newWorkComment.size();
        }
        LogUtils.v(TAG, "getCircleCount() count=" + count);
        return count;
    }

    /**
     * 获取朋友圈新评论总条数
     */
    public int getMomentCommentCount()
    {
        int count = 0;
        List<NewMomentComment> newMomentComment = getNewMomentComment();
        if (newMomentComment != null && newMomentComment.size() > 0)
        {
            count = newMomentComment.size();
        }
        LogUtils.v(TAG, "getMomentCommentCount() count=" + count);
        return count;
    }

    /**
     * 获取作品圈新评论总条数
     */
    public int getWorkCommentCount()
    {
        int count = 0;
        List<NewWorkComment> newWorkComment = getNewWorkComment();
        if (newWorkComment != null && newWorkComment.size() > 0)
        {
            count = newWorkComment.size();
        }
        LogUtils.v(TAG, "getWorkCommentCount() count=" + count);
        return count;
    }

    /**
     * 新的作品圈
     * 
     * @param publisherID
     *            作品圈的发布者
     */
    public void setNewWork(String publisherID)
    {
        LogUtils.v(TAG, "setNewWork() params[publisherID=" + publisherID + "]");
        if (CommUtil.isNotBlank(publisherID))
        {
            newWork = publisherID;
            userExtraInfo.setNewWorkPublisherID(publisherID);
            userExtraInfo.save();
            EventBus.getDefault().post("快去拿数据吧", NotifyByEventBus.NT_NEW_WORK);
        }
    }

    /**
     * 
     */
    public String getNewWorkPublisherID()
    {
        LogUtils.v(TAG, "getNewWorkPublisherID()");
        if (CommUtil.isNotBlank(newWork))
            return newWork;
        else
        {
            String newMomentPublisherID = userExtraInfo.getNewWorkPublisherID();
            if (CommUtil.isNotBlank(newMomentPublisherID))
                return newMomentPublisherID;
        }
        return "";
    }

    /**
     * 清除新的朋友圈小红点
     */
    public void clearNewWork()
    {
        LogUtils.v(TAG, "clearNewWork() ");
        newMoment = "";
        userExtraInfo.setNewMomentPublisherID("");
        userExtraInfo.save();
    }
    
    
    

    /**
     * 新的朋友圈
     * 
     * @param publisherID
     *            朋友圈的发布者
     */
    public void setNewMoment(String publisherID)
    {
        LogUtils.v(TAG, "setNewMoment() params[publisherID=" + publisherID + "]");
        if (CommUtil.isNotBlank(publisherID))
        {
            newMoment = publisherID;
            userExtraInfo.setNewMomentPublisherID(publisherID);
            userExtraInfo.save();
            EventBus.getDefault().post("快去拿数据吧", NotifyByEventBus.NT_NEW_MOMENT);
        }
    }

    /**
     * 
     */
    public String getNewMomentPublisherID()
    {
        if (CommUtil.isNotBlank(newMoment))
            return newMoment;
        else
        {
            String newMomentPublisherID = userExtraInfo.getNewMomentPublisherID();
            if (CommUtil.isNotBlank(newMomentPublisherID))
                return newMomentPublisherID;
        }
        return "";
    }

    /**
     * 清除新的朋友圈小红点
     */
    public void clearNewMoment()
    {
        LogUtils.v(TAG, "clearNewMoment() ");
        newMoment = "";
        userExtraInfo.setNewMomentPublisherID("");
        userExtraInfo.save();
    }


    // /////////////////////////////////////////私有方法////////////////////////////////////
    /**
     * 私有构造函数
     */
    private SelfExtraInfoManager()
    {
        init();
    }

    /**
     * 从数据库加载记录
     */
    private void init()
    {
        String dwID = DecentWorldApp.getInstance().getDwID();
        if (CommUtil.isNotBlank(dwID))
        {
            SelfExtraInfo query = SelfExtraInfoDao.query(dwID);
            if (query != null)
                userExtraInfo = query;
            else
                userExtraInfo = new SelfExtraInfo();
        }
    }

    /**
     * 从数据库加载新的朋友圈评论
     */
    private void loadNewMomentComment()
    {
        String newMomentComment = userExtraInfo.getNewMomentComment();
        if (CommUtil.isNotBlank(newMomentComment))
            newMomentComments = (List<NewMomentComment>) JsonUtils.json2BeanArray(newMomentComment, NewMomentComment.class);
        else
            newMomentComments = new ArrayList<NewMomentComment>();
    }

    /**
     * 从数据库加载新的作品圈评论
     */
    private void loadNewWorkComment()
    {
        String newWorkComment = userExtraInfo.getNewWorkComment();
        if (CommUtil.isNotBlank(newWorkComment))
            newWorkComments = (List<NewWorkComment>) JsonUtils.json2BeanArray(newWorkComment, NewWorkComment.class);
        else
            newWorkComments = new ArrayList<NewWorkComment>();
    }
}
