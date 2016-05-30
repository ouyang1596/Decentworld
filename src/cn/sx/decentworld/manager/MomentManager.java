/**
 * 
 */
package cn.sx.decentworld.manager;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.simple.eventbus.EventBus;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.Request.Method;

import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.common.CommUtil;
import cn.sx.decentworld.common.ConstantNet;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.common.NotifyByEventBus;
import cn.sx.decentworld.engine.MomentEngine;
import cn.sx.decentworld.engine.MomentEngine.onDeleteCommentCallback;
import cn.sx.decentworld.engine.MomentEngine.onDeleteMomentCallback;
import cn.sx.decentworld.entity.dao.CommentDao;
import cn.sx.decentworld.entity.dao.MomentDao;
import cn.sx.decentworld.entity.dao.MomentExtraDao;
import cn.sx.decentworld.entity.db.CommentEntity;
import cn.sx.decentworld.entity.db.MomentEntity;
import cn.sx.decentworld.entity.db.MomentExtraEntity;
import cn.sx.decentworld.entity.db.DWMessage.MessageType;
import cn.sx.decentworld.entity.server.MomentRefreshResult;
import cn.sx.decentworld.listener.CommentCallback;
import cn.sx.decentworld.listener.GetMomentEntityListener;
import cn.sx.decentworld.listener.PublishMomentCallback;
import cn.sx.decentworld.logSystem.LogUtils;
import cn.sx.decentworld.network.SendUrl;
import cn.sx.decentworld.network.SendUrl.HttpCallBack;
import cn.sx.decentworld.network.entity.ResultBean;
import cn.sx.decentworld.network.utils.JsonUtils;
import cn.sx.decentworld.utils.NetworkUtils;
import cn.sx.decentworld.utils.ToastUtil;

/**
 * @ClassName: MomentManager
 * @Description: 朋友圈管理者； 1）从数据库加载数据到内存； 2）当有数据更新时，更新内存中的数据，同时保存到数据库中；
 * @author: Jackchen
 * @date: 2016年5月10日 上午10:17:46
 */
public class MomentManager
{
    private static final String TAG = "MomentManager";
    // 朋友圈管理类对象
    private static MomentManager instance;
    // 朋友圈额外信息实体对象
    private MomentExtraEntity momentExtraEntity;
    // 朋友圈最大ID
    private long momentIndex = -1;
    // 评论最大ID
    private long commentIndex = -1;
    // 保存数据<朋友圈ID,朋友圈实体>
    private Map<Long, MomentEntity> mMomentMap;
    private List<MomentEntity> mMomentList;
    private SendUrl sendUrl;
    private String userID;

    /**
     * 私有构造函数
     */
    private MomentManager()
    {
        LogUtils.v(TAG, "MomentManager() ");
        mMomentMap = new HashMap<Long, MomentEntity>();
        mMomentList = new ArrayList<MomentEntity>();
        sendUrl = new SendUrl();
        userID = DecentWorldApp.getInstance().getDwID();
        init();
    }

    /**
     * 加载数据库中的数据
     */
    private void init()
    {
        LogUtils.v(TAG, "init()");
        MomentExtraEntity query = MomentExtraDao.query();
        if (query == null)
        {
            LogUtils.v(TAG, "init() new MomentExtraEntity()");
            query = new MomentExtraEntity(DecentWorldApp.getInstance().getDwID() , -1 , -1);
            query.save();
        }
        momentExtraEntity = query;
        momentIndex = momentExtraEntity.getMomentIndex();
        commentIndex = momentExtraEntity.getCommentIndex();

        mMomentMap.clear();
        mMomentList.clear();
        List<MomentEntity> allData = MomentDao.getAllData();
        if (allData != null && allData.size() > 0)
        {
            for (MomentEntity entity : allData)
            {
                mMomentList.add(entity);
                mMomentMap.put(entity.getMomentID(), entity);
                List<CommentEntity> list = new CommentDao().queryList(entity.getMomentID());
                if (list != null && list.size() > 0)
                    entity.setCommentList(list);
            }
        }
        Collections.sort(mMomentList);
    }

    /**
     * 获取实例
     */
    public static MomentManager getInstance()
    {
        if (instance == null)
        {
            synchronized (MomentManager.class)
            {
                if (instance == null)
                    instance = new MomentManager();
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
     * 获取朋友圈最新
     */
    public long getMaxMomentIndex()
    {
        return momentIndex;
    }

    /**
     * 获取评论最新ID
     */
    public long getMaxCommentIndex()
    {
        return commentIndex;
    }

    /**
     * 获取朋友圈最小ID
     */
    public long getMinMomentIndex()
    {
        long minMomentIndex = -1;
        if (mMomentList != null && mMomentList.size() > 0)
        {
            Collections.sort(mMomentList);
            MomentEntity momentEntity = mMomentList.get(mMomentList.size() - 1);
            minMomentIndex = momentEntity.getMomentID();
        }
        LogUtils.i(TAG, "getMinMomentIndex() minMomentIndex=" + minMomentIndex);
        return minMomentIndex;
    }

    /**
     * 保存朋友圈最新ID
     */
    public void saveIndex(long momentIndex, long commentIndex)
    {
        LogUtils.v(TAG, "saveIndex() params[momentIndex=" + momentIndex + ",commentIndex=" + commentIndex + "]");
        if (momentIndex > 0)
        {
            // 将索引保存到内存；
            this.momentIndex = momentIndex;
            momentExtraEntity.setMomentIndex(momentIndex);
        }

        if (commentIndex > 0)
        {
            this.commentIndex = commentIndex;
            momentExtraEntity.setCommentIndex(commentIndex);
        }
        momentExtraEntity.save();
    }

    /**
     * 保存朋友圈最新ID
     */
    public void saveMomentIndex(long momentIndex)
    {
        LogUtils.v(TAG, "saveMomentIndex() params[momentIndex=" + momentIndex + "]");
        if (momentIndex > 0)
        {
            // 将索引保存到内存；
            this.momentIndex = momentIndex;
            // 将索引保存到数据库
            momentExtraEntity.setMomentIndex(momentIndex);
            momentExtraEntity.save();
        }
    }

    /**
     * 保存朋友圈最新评论ID
     */
    public void saveCommentIndex(long commentIndex)
    {
        LogUtils.v(TAG, "saveCommentIndex() params[commentIndex=" + commentIndex + "]");
        if (commentIndex > 0)
        {
            // 将索引保存到内存；
            this.commentIndex = commentIndex;
            // 将索引保存到数据库
            momentExtraEntity.setCommentIndex(commentIndex);
            momentExtraEntity.save();
        }
    }

    /**
     * 删除朋友圈
     */
    public void deleteMoment(MomentEntity entity)
    {
        // 网络请求
        // 网络请求成功后
        new MomentEngine().deleteMoment(entity, new onDeleteMomentCallback()
        {
            @Override
            public void onSuccess(MomentEntity entity)
            {
                // 1.删除内存中的数据
                long momentID = entity.getMomentID();
                MomentEntity momentEntity = mMomentMap.get(momentID);
                if (momentEntity != null)
                {
                    if (mMomentList != null && mMomentList.size() > 0)
                    {
                        Iterator<MomentEntity> iterator = mMomentList.iterator();
                        while (iterator.hasNext())
                        {
                            MomentEntity next = iterator.next();
                            if (next.getMomentID() == momentID)
                            {
                                List<CommentEntity> commentList = next.getCommentList();
                                for (CommentEntity entity2 : commentList)
                                {
                                    CommentDao.deleteByCommentID(entity2.getCommentID());
                                }
                                LogUtils.v(TAG, "deleteMoment() next" + next.toString());
                                iterator.remove();
                            }
                        }
                    }
                    mMomentMap.remove(momentID);
                }
                else
                {
                    LogUtils.d(TAG, "deleteMoment() there is no corresponing moment in memory");
                }
                // 2.删除数据库中的数据
                MomentDao.deleteByMomentID(momentID);
                LogUtils.d(TAG, "deleteMoment() 业务逻辑未完成,推送消息通知朋友圈界面进行刷新");
                EventBus.getDefault().post("", NotifyByEventBus.NT_MOMENT_ACTIVITY_REFRESH);
            }

            @Override
            public void onFailure(String info)
            {
                ToastUtil.showToast(info);
            }
        });
    }

    /**
     * 删除朋友圈
     */
    public void deleteMoment(List<String> momentIndexList)
    {
        // 业务逻辑
        // 1.删除内存中的数据
        // 2.删除数据库中的数据
        LogUtils.d(TAG, "deleteComment() 业务逻辑未完成");
    }

    /**
     * 删除朋友圈评论
     */
    public void deleteComment(CommentEntity commentEntity)
    {
        // 网络请求
        // 网络请求成功后
        new MomentEngine().deleteComment(commentEntity, new onDeleteCommentCallback()
        {
            @Override
            public void onSuccess(CommentEntity commentEntity)
            {
                // 1.删除内存中的数据
                long momentID = commentEntity.getMomentID();
                long commentID = commentEntity.getCommentID();
                MomentEntity momentEntity = mMomentMap.get(momentID);
                if (momentEntity != null)
                {
                    List<CommentEntity> commentList = momentEntity.getCommentList();
                    if (commentList != null && commentList.size() > 0)
                    {
                        Iterator<CommentEntity> iterator = commentList.iterator();
                        while (iterator.hasNext())
                        {
                            CommentEntity next = iterator.next();
                            if (next.getCommentID() == commentID)
                            {
                                iterator.remove();
                                break;
                            }
                        }
                    }
                }
                else
                {
                    LogUtils.d(TAG, "deleteComment() there is no corresponing moment in memory");
                }
                // 2.删除数据库中的数据
                CommentDao.deleteByCommentID(commentID);
                LogUtils.d(TAG, "deleteComment() 业务逻辑未完成,推送消息通知朋友圈界面进行刷新");
                EventBus.getDefault().post("", NotifyByEventBus.NT_MOMENT_ACTIVITY_REFRESH);
            }

            @Override
            public void onFailure(String info)
            {
                ToastUtil.showToast(info);
            }
        });
    }

    /**
     * 删除朋友圈评论
     */
    public void deleteComment(List<String> commentIndexList)
    {
        // 业务逻辑
        // 1.删除内存中的数据
        // 2.删除数据库中的数据
        LogUtils.d(TAG, "deleteComment() 业务逻辑未完成");
    }

    /**
     * 添加朋友圈，同时保存到数据库和内存
     */
    public void addMoment(MomentEntity entity)
    {
        LogUtils.v(TAG, "addMoment() " + entity.toString());
        if (entity != null)
        {
            if (!containsMoment(entity.getMomentID()))
            {
                entity.save();
                mMomentList.add(entity);
                mMomentMap.put(entity.getMomentID(), entity);
                Collections.sort(mMomentList);
            }
        }
    }

    /**
     * 添加朋友圈，同时保存到数据库和内存
     */
    public void addMoment(List<MomentEntity> momentEntities)
    {
        LogUtils.v(TAG, "addMoment() " + momentEntities.toString());
        if (momentEntities != null && momentEntities.size() > 0)
        {
            for (MomentEntity entity : momentEntities)
            {
                if (!containsMoment(entity.getMomentID()))
                {
                    entity.save();
                    mMomentList.add(entity);
                    mMomentMap.put(entity.getMomentID(), entity);
                }
            }
            Collections.sort(mMomentList);
        }
    }

    /**
     * 添加朋友圈评论，同时保存到数据库和内存
     */
    public void addComment(CommentEntity commentEntity)
    {
        if (commentEntity != null)
        {
            if (!containsComment(commentEntity.getMomentID(), commentEntity.getCommentID()))
            {
                commentEntity.save();
                MomentEntity momentEntity = mMomentMap.get(commentEntity.getMomentID());
                if (momentEntity != null)
                {
                    List<CommentEntity> commentList = momentEntity.getCommentList();
                    commentList.add(commentEntity);
                    Collections.sort(commentList);
                }
            }
        }
    }

    /**
     * 添加朋友圈评论，同时保存到数据库和内存
     */
    public void addComment(List<CommentEntity> commentEntities)
    {
        LogUtils.v(TAG, "addComment() ");
        if (commentEntities != null && commentEntities.size() > 0)
        {
            for (CommentEntity commentEntity : commentEntities)
            {
                LogUtils.d(TAG, "addComment() multi,commentEntity:" + commentEntity.toString());
                if (!containsComment(commentEntity.getMomentID(), commentEntity.getCommentID()))
                {
                    commentEntity.save();
                    MomentEntity momentEntity = mMomentMap.get(commentEntity.getMomentID());
                    if (momentEntity != null)
                    {
                        List<CommentEntity> commentList = momentEntity.getCommentList();
                        commentList.add(commentEntity);
                        Collections.sort(commentList);
                    }
                }
            }
        }
    }

    /**
     * 获取作品圈
     */
    public List<MomentEntity> getMoment()
    {
        Collections.sort(mMomentList);
        return mMomentList;
    }

    /**
     * 保存作品圈传递过来的朋友圈
     */
    public void saveMomentFromWork(long momentIndex, String momentJSonStr)
    {
        LogUtils.v(TAG, "saveMomentFromWork() params[momentIndex=" + momentIndex + ",momentJSonStr=" + momentJSonStr + "] ");
        JSONObject moment = JSON.parseObject(momentJSonStr);
        MomentEntity entity = new MomentEngine().parseMomentEntity(moment);
        LogUtils.v(TAG, "" + entity.toString());
        saveMomentIndex(momentIndex);
        addMoment(entity);
    }

    /**
     * 删除指定ID的朋友圈和评论
     */
    public void afterDeleteContact(String otherID)
    {
        LogUtils.v(TAG, "afterDeleteContact() params[otherID=" + otherID + "]");
        // 删除内存中的数据（List/Map）
        if (mMomentList != null && mMomentList.size() > 0)
        {
            Iterator<MomentEntity> iterator = mMomentList.iterator();
            while (iterator.hasNext())
            {
                MomentEntity next = iterator.next();
                if (otherID.equals(next.getPublisherID()))
                {
                    LogUtils.v(TAG, "afterDeleteContact() next" + next.toString());
                    mMomentMap.remove(next.getMomentID());
                    iterator.remove();
                }
                else
                {
                    List<CommentEntity> commentList = next.getCommentList();
                    Iterator<CommentEntity> iterator2 = commentList.iterator();
                    while (iterator2.hasNext())
                    {
                        CommentEntity next2 = iterator2.next();
                        if (next2.getDwID().equals(otherID))
                        {
                            iterator2.remove();
                            break;
                        }
                        String reply = next2.getReply();
                        if (CommUtil.isNotBlank(reply) && reply.equals(otherID))
                        {
                            iterator2.remove();
                            break;
                        }
                    }
                }
            }
        }
        // 删除数据库中的数据（指定dwID发布的朋友圈、评论、被回复）
        MomentDao.delete(otherID);
        CommentDao.deleteByDwID(otherID);
        CommentDao.deleteByReply(otherID);
        init();
        // 通知界面适配器发生改变
        EventBus.getDefault().post("删除好友后，通知适配器改变", NotifyByEventBus.NT_MOMENT_ACTIVITY_REFRESH);
    }

    /**
     * 判断本地是否存在指定ID的朋友圈 ，本地包括内存和数据库
     * 
     * @param momentID
     *            朋友圈ID
     * @return true(包含) | false(不包含)
     */
    private boolean containsMoment(long momentID)
    {
        MomentEntity momentEntity = mMomentMap.get(momentID);
        if (momentEntity != null)
            return true;
        MomentEntity query = MomentDao.query(momentID);
        if (query != null)
            return true;
        return false;
    }

    /**
     * 判断本地是否存在指定ID的评论 本地包括内存和数据库
     * 
     * @param momentID
     *            朋友圈ID
     * @param commentID
     *            评论ID
     * @return true(包含) | false(不包含)
     */
    private boolean containsComment(long momentID, long commentID)
    {
        LogUtils.v(TAG, "containsComment() not exit momentEntity");
        MomentEntity momentEntity = mMomentMap.get(momentID);
        if (momentEntity != null)
        {
            LogUtils.d(TAG, "containsComment() exit momentEntity");
            List<CommentEntity> commentList = momentEntity.getCommentList();
            if (commentList != null && commentList.size() > 0)
            {
                for (CommentEntity entity : commentList)
                {
                    if (entity.getCommentID() == commentID)
                        return true;
                }
            }
        }
        else
        {
            LogUtils.d(TAG, "containsComment() not exit momentEntity");
        }
        CommentEntity query = CommentDao.query(momentID, commentID);
        if (query != null)
            return true;
        return false;
    }

    /**
     * 进入朋友圈时初始化数据
     * 
     * @return
     */
//    public void initData(GetMomentEntityListener listener)
//    {
//        LogUtils.v(TAG, "initData()");
//        List<MomentEntity> allData = getMoment();
//        if (allData != null && allData.size() > 0)
//        {
//            listener.onSuccess("");
//        }
//        else
//        {
//            if (NetworkUtils.isNetWorkConnected(DecentWorldApp.getGlobalContext()))
//            {
//                // 从网络获取数据
//                new MomentEngine().downRefresh(listener);
//            }
//            else
//            {
//                listener.onSuccess("本地无数据");
//            }
//        }
//    }

    /**
     * 下拉刷新
     * 
     * @param listener
     *            回调监听
     */
    public void downRefresh(GetMomentEntityListener listener)
    {
        new MomentEngine().downRefresh(listener);
    }

    /**
     * 上拉拉刷新
     * 
     * @param listener
     *            回调监听
     */
    public void upRefresh(final GetMomentEntityListener listener)
    {
        new MomentEngine().upRefresh(listener);
    }

    /**
     * 发布朋友圈
     * 
     * @param entity
     *            朋友圈实体
     * @param files
     *            文件（音频和图片）
     * @param blocklist
     *            不让谁看
     * @param onlyshowtolist
     *            提醒谁看
     * @param listener
     *            回调监听
     */
    public void publish(MomentEntity entity, ArrayList<String> blocklist, ArrayList<String> onlyshowtolist, PublishMomentCallback listener)
    {
        new MomentEngine().publish(entity, blocklist, onlyshowtolist, listener);
    }

    /**
     * 评论
     * 
     * @param commentEntity
     * @param listener
     */
    public void comment(CommentEntity commentEntity, CommentCallback listener)
    {
        new MomentEngine().comment(commentEntity, listener);
    }

}
