/**
 * 
 */
package cn.sx.decentworld.engine;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import cn.sx.decentworld.entity.dao.CommentDao;
import cn.sx.decentworld.entity.dao.MomentDao;
import cn.sx.decentworld.entity.db.CommentEntity;
import cn.sx.decentworld.entity.db.MomentEntity;
import cn.sx.decentworld.entity.db.DWMessage.MessageType;
import cn.sx.decentworld.entity.server.MomentRefreshResult;
import cn.sx.decentworld.listener.CommentCallback;
import cn.sx.decentworld.listener.GetMomentEntityListener;
import cn.sx.decentworld.listener.PublishMomentCallback;
import cn.sx.decentworld.logSystem.LogUtils;
import cn.sx.decentworld.manager.MomentManager;
import cn.sx.decentworld.manager.SelfInfoManager;
import cn.sx.decentworld.network.SendUrl;
import cn.sx.decentworld.network.SendUrl.HttpCallBack;
import cn.sx.decentworld.network.entity.ResultBean;
import cn.sx.decentworld.network.utils.JsonUtils;

/**
 * @ClassName: MomentEngine.java
 * @Description: 作品圈的业务逻辑
 * @author: cj
 * @date: 2016年2月26日 下午6:17:20
 */
public class MomentEngine
{
    private static final String TAG = "MomentEngine";
    private SendUrl sendUrl;
    private String userID;

    /**
     * 构造函数
     */
    public MomentEngine()
    {
        sendUrl = new SendUrl();
        userID = DecentWorldApp.getInstance().getDwID();
    }

    /**
     * 下拉刷新
     * 
     * @param momentIndex
     *            为客户端已储存的最大的朋友圈的ID，即已储存的最新的朋友圈ID,如果没有则传默认值0；
     * @param commentIndex
     *            为客户端已储存的最大的评论的ID，即已储存的最新的评论ID,如果没有则传默认值 0；
     */
    public void downRefresh(final GetMomentEntityListener listener)
    {
        long maxMomentIndex = MomentManager.getInstance().getMaxMomentIndex();
        long maxCommentIndex = MomentManager.getInstance().getMaxCommentIndex();
        LogUtils.d(TAG, "downRefresh() maxMomentIndex=" + maxMomentIndex + ",maxCommentIndex=" + maxCommentIndex);
        final Handler handler = new Handler()
        {
            public void handleMessage(Message msg)
            {
                switch (msg.what)
                {
                    case -1:
                        // 获取错误，网络错误
                        listener.onFailure(msg.obj.toString());
                        break;
                    case 0:
                        // 获取成功
                        String result = msg.obj.toString();
                        handleDownRefreshResult(result, listener);
                        break;
                    case 1:
                        // 获取失败，其它原因
                        listener.onFailure(msg.obj.toString());
                        break;
                    default:
                        break;
                }
            };
        };
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("dwID", userID);
        map.put("index", String.valueOf(maxMomentIndex));
        map.put("commentIndex", String.valueOf(maxCommentIndex));
        LogUtils.v(TAG, "downRefresh() params:" + JSON.toJSONString(map));
        sendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH + ConstantNet.API_MOMENT_DOWN_REFRESH, Method.GET, new HttpCallBack()
        {
            @Override
            public void onSuccess(String resultJSON, ResultBean resultBean)
            {
                LogUtils.v(TAG, "downRefresh() onSuccess,resultJSON:" + resultJSON);
                if (resultBean.getResultCode() == 2222)
                {
                    LogUtils.v(TAG, "downRefresh() success");
                    sendHandlerMsg(handler, 0, resultBean.getData().toString());
                }
                else if (resultBean.getResultCode() == 3333)
                {
                    LogUtils.w(TAG, "downRefresh() no data");
                    sendHandlerMsg(handler, 1, resultBean.getMsg());
                }
            }

            @Override
            public void onFailure(String e)
            {
                LogUtils.e(TAG, "downRefresh() onFailure,cause by:" + e);
                sendHandlerMsg(handler, -1, Constants.NET_WRONG);
            }
        });
    }

    /**
     * 上拉加载
     */
    public void upRefresh(final GetMomentEntityListener listener)
    {
        Handler handler = new Handler()
        {
            public void handleMessage(android.os.Message msg)
            {
                listener.onSuccess("本地加载成功");
            };
        };

        long minMomentID = MomentManager.getInstance().getMinMomentIndex();
        LogUtils.v(TAG, "upRefresh() params[minMomentID=" + minMomentID + "]");
        List<MomentEntity> momentEntities = MomentDao.getList(minMomentID);
        if (momentEntities != null && momentEntities.size() > 0)
        {
            LogUtils.v(TAG, "upRefresh() momentID<" + minMomentID + " has " + momentEntities.size() + "条记录");
            for (MomentEntity entity : momentEntities)
            {
                List<CommentEntity> list = CommentDao.queryList(entity.getMomentID());
                if (list != null && list.size() > 0)
                    entity.setCommentList(list);
            }
            MomentManager.getInstance().addMoment(momentEntities);
            handler.sendEmptyMessage(0);
        }
        else
        {
            LogUtils.v(TAG, "upRefresh() 没有数据，从网络加载");
            upRefreshFromNet(listener);
        }
    }

    /**
     * 
     * @param entity
     *            朋友圈实体
     * @param files
     *            文件：语音（最多一个）、图片（最多9张）
     * @param blocklist
     *            不让谁看
     * @param onlyshowtolist
     *            提醒谁看
     * @param listener
     *            回调监听
     */
    public void publish(final MomentEntity entity, ArrayList<String> blocklist, ArrayList<String> onlyshowtolist, final PublishMomentCallback listener)
    {
        LogUtils.v(TAG, "publishE() " + entity.toString());
        final Handler handler = new Handler()
        {
            public void handleMessage(Message msg)
            {
                switch (msg.what)
                {
                    case -1:
                        // 获取错误，网络错误
                        listener.onFailure(msg.obj.toString());
                        break;
                    case 0:
                        // 获取成功
                        ResultBean resultBean = (ResultBean) msg.obj;
                        // 将发布成功的朋友圈的ID保存
                        JSONObject jsonObject = JSON.parseObject(resultBean.getData().toString());
                        JSONObject jsonObject2 = jsonObject.getJSONObject("moment");
                        MomentEntity parseMomentEntity = new MomentEngine().parseMomentEntity(jsonObject2);
                        LogUtils.d(TAG, "publishE() handler:" + parseMomentEntity.toString());
                        entity.setMomentID(parseMomentEntity.getMomentID());
                        entity.setRemoteUrl(parseMomentEntity.getRemoteUrl());
                        entity.setRemoteVoiceBgUrl(parseMomentEntity.getRemoteVoiceBgUrl());
                        entity.setPublishTime(parseMomentEntity.getPublishTime());
                        entity.setMomentStatus(MomentEntity.MomentStatus.SUCCESSED.getIndex());
                        MomentManager.getInstance().addMoment(entity);
                        // 提示信息
                        String info = resultBean.getMsg();
                        listener.onSuccess(entity, info);
                        break;
                    case 1:
                        // 获取失败，其它原因
                        listener.onFailure(msg.obj.toString());
                        break;
                    default:
                        break;
                }
            };
        };
        HashMap<String, String> map = new HashMap<String, String>();
        // 用户ID
        map.put("dwID", userID);
        // 发布者显示的名字（实名、昵称）
        map.put("publisherName", entity.getPublisherName());
        // 文字内容
        map.put("content", entity.getContent());
        // 类型：文字（0）、语音（1）、图片（2）
        map.put("type", entity.getContentType() + "");
        // 发布级别：所有（0）、朋友（1）、亲友（2）
        map.put("level", entity.getLevel() + "");
        // 创建文件
        HashMap<String, File> fileMap = null;
        File[] files;
        if (entity.getContentType() == MessageType.VOICE.getIndex())
        {
            files = new File[1];
            files[0] = new File(entity.getLocalUrl());

            String t = entity.getLocalVoiceBgUrl();
            if (CommUtil.isNotBlank(t))
            {
                fileMap = new HashMap<String, File>();
                fileMap.put("background", new File(t));
            }
        }
        else if (entity.getContentType() == MessageType.IMAGE.getIndex())
        {
            String[] imagePaths = entity.getLocalUrl().split(";");
            files = new File[imagePaths.length];
            for (int i = 0; i < imagePaths.length; i++)
            {
                files[i] = new File(imagePaths[i]);
            }
        }
        else
        {
            files = new File[0];
        }

        LogUtils.v(TAG, "publishE() params:" + JSON.toJSONString(map));
        // 网络请求
        sendUrl.httpRequestWithPublish(map, fileMap, files, blocklist, onlyshowtolist, Constants.CONTEXTPATH + ConstantNet.API_MOMENT_PUBLISH, new HttpCallBack()
        {
            @Override
            public void onSuccess(String resultJSON, ResultBean resultBean)
            {
                LogUtils.v(TAG, "publishE() onSuccess,resultJSON:" + resultJSON);
                if (resultBean.getResultCode() == 2222)
                {
                    // 发布成功
                    LogUtils.v(TAG, "publishE() success," + resultBean.getData());
                    sendHandlerMsg(handler, 0, resultBean);
                }
                else if (resultBean.getResultCode() == 3333)
                {
                    LogUtils.v(TAG, "publishE() failure,resultBean.getMsg=" + resultBean.getMsg());
                    entity.setMomentStatus(MomentEntity.MomentStatus.FAILURED.getIndex());
                    entity.save();
                    sendHandlerMsg(handler, 1, resultBean.getMsg());
                }
                else
                {
                    LogUtils.v(TAG, "publishE() failure,resultBean.getMsg=" + resultBean.getMsg());
                    entity.setMomentStatus(MomentEntity.MomentStatus.FAILURED.getIndex());
                    entity.save();
                    sendHandlerMsg(handler, 1, "发送朋友圈失败");
                }
            }

            @Override
            public void onFailure(String e)
            {
                LogUtils.e(TAG, "publishE() onFailure,cause by:" + e);
                entity.setMomentStatus(MomentEntity.MomentStatus.FAILURED.getIndex());
                entity.save();
                sendHandlerMsg(handler, -1, Constants.NET_WRONG);
            }
        });
    }

    /**
     * 评论
     * 
     * @param commentEntity
     * @param listener
     */
    public void comment(final CommentEntity commentEntity, final CommentCallback listener)
    {
        final Handler handler = new Handler()
        {
            public void handleMessage(Message msg)
            {
                switch (msg.what)
                {
                    case -1:
                        // 获取错误，网络错误
                        commentEntity.save();
                        listener.onFailure(msg.obj.toString(), commentEntity);
                        break;
                    case 0:
                        // 评论成功
                        CommentEntity entity = (CommentEntity) msg.obj;
                        commentEntity.setCommentID(entity.getCommentID());
                        MomentManager.getInstance().addComment(commentEntity);
                        listener.onSuccess("成功", commentEntity);
                        break;
                    case 1:
                        // 获取失败，其它原因
                        commentEntity.save();
                        listener.onFailure(msg.obj.toString(), commentEntity);
                        break;
                    default:
                        break;
                }
            };
        };
        String comment = JsonUtils.bean2json(commentEntity);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("dwID", userID);
        map.put("comment", comment);
        LogUtils.v(TAG, "commentE() params:" + JSON.toJSONString(map));
        // 网络请求
        sendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH + ConstantNet.API_MOMENT_COMMENT, Method.GET, new HttpCallBack()
        {
            @Override
            public void onSuccess(String resultJSON, ResultBean resultBean)
            {
                LogUtils.v(TAG, "commentE() resultJSON：" + resultJSON);
                if (resultBean.getResultCode() == 2222 || resultBean.getResultCode() == 4444 || resultBean.getResultCode() == 5555)
                {
                    if (resultBean.getResultCode() == 2222)
                        LogUtils.v(TAG, "commentE() success");
                    else if (resultBean.getResultCode() == 4444)
                        LogUtils.v(TAG, "commentE() success,对方身家不足");
                    else if (resultBean.getResultCode() == 5555)
                        LogUtils.v(TAG, "commentE() success,自己身家不足");
                    // 将发布成功的评论的ID保存
                    JSONObject jsonObject = JSON.parseObject(resultBean.getData().toString());
                    String commentStr = jsonObject.getString("comment");
                    CommentEntity commentEntity = JsonUtils.json2Bean(commentStr, CommentEntity.class);
                    float wealth = jsonObject.getFloatValue("wealth");
                    SelfInfoManager.getInstance().notifyWealthChanged(wealth);
                    long commentIndex = jsonObject.getLongValue("commentIndex");
                    MomentManager.getInstance().saveCommentIndex(commentIndex);
                    sendHandlerMsg(handler, 0, commentEntity);
                }
                else if (resultBean.getResultCode() == 3333)
                {
                    LogUtils.w(TAG, "commentE() failure,resultBean.getMsg=" + resultBean.getMsg());
                    sendHandlerMsg(handler, 1, resultBean.getMsg());
                }
                else
                {
                    LogUtils.w(TAG, "commentE() failure,未知返回码，resultBean.getMsg=" + resultBean.getMsg());
                    sendHandlerMsg(handler, 1, "失败");
                }
            }

            @Override
            public void onFailure(String e)
            {
                LogUtils.e(TAG, "commentE() onFailure,cause by:" + e);
                sendHandlerMsg(handler, -1, Constants.NET_WRONG);
            }
        });
    }

    /**
     * 解析成朋友圈实例
     */
    public MomentEntity parseMomentEntity(JSONObject moment)
    {
        LogUtils.v(TAG, "");
        MomentEntity momentEntity = new MomentEntity();
        momentEntity.setMomentID(moment.getLongValue("id"));
        momentEntity.setContentType(moment.getIntValue("contentType"));
        momentEntity.setLevel(moment.getIntValue("level"));
        momentEntity.setPublishTime(moment.getLongValue("date"));
        momentEntity.setPublisherID(moment.getString("dwID"));
        momentEntity.setPublisherName(moment.getString("publisherName"));
        momentEntity.setContent(moment.getString("content"));
        momentEntity.setCommentCount(moment.getIntValue("comment"));
        momentEntity.setDislikeCount(moment.getIntValue("dislike"));
        momentEntity.setDownCount(moment.getIntValue("down"));
        momentEntity.setLikeCount(moment.getIntValue("like"));
        if (momentEntity.getContentType() == MomentEntity.MomentType.TXT.getIndex())
            momentEntity.setRemoteUrl("");
        else if (momentEntity.getContentType() == MomentEntity.MomentType.VOICE.getIndex())
        {
            momentEntity.setRemoteUrl(moment.getString("audioUrl").replace(";", ""));
            String string = moment.getString("imgUrl");
            if (CommUtil.isNotBlank(string))
            {
                momentEntity.setRemoteVoiceBgUrl(string.replace(";", ""));
            }
        }
        else if (momentEntity.getContentType() == MomentEntity.MomentType.IMAGE.getIndex())
            momentEntity.setRemoteUrl(moment.getString("imgUrl"));
        momentEntity.setMomentStatus(MomentEntity.MomentStatus.SUCCESSED.getIndex());
        return momentEntity;
    }

    /**
     * 解析评论实例
     */
    public CommentEntity parseCommentEntity(JSONObject comment)
    {
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setAnonymous(comment.getBooleanValue("anonymous"));
        commentEntity.setCommentID(comment.getLongValue("commentID"));
        commentEntity.setContent(comment.getString("content"));
        commentEntity.setDwID(comment.getString("dwID"));
        commentEntity.setMomentID(comment.getLongValue("momentID"));
        commentEntity.setPublisherID(comment.getString("publisherID"));
        commentEntity.setPublisherName(comment.getString("publisherName"));
        commentEntity.setReply(comment.getString("reply"));
        commentEntity.setTime(comment.getLongValue("time"));
        commentEntity.setType(comment.getIntValue("type"));
        commentEntity.setBenefitStatus(comment.getIntValue("benefitStatus"));
        commentEntity.setReplyAnonymous(comment.getBooleanValue("replyAnonymous"));
        commentEntity.setReplyName(comment.getString("replyName"));
//        commentEntity.setReplyCommendID(comment.getString("replyCommendID"));
        commentEntity.setReplyCommentID(comment.getString("replyCommentID"));
        return commentEntity;
    }

    /**
     * 删除评论
     */
    public void deleteComment(final CommentEntity commentEntity,final onDeleteCommentCallback callback)
    {
        final Handler handler = new Handler()
        {
            public void handleMessage(Message msg)
            {
                String info = msg.obj.toString();
                LogUtils.d(TAG, "deleteComment() ");
                switch (msg.what)
                {
                    case -1:
                        callback.onFailure(info);
                        break;
                    case 0:
                        callback.onSuccess(commentEntity);
                        break;
                    case 1:
                        callback.onFailure(info);
                        break;
                    default:
                        break;
                }
            };
        };

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("commentID", commentEntity.getCommentID() + "");
        map.put("publisherID", commentEntity.getDwID() + "");
        LogUtils.d(TAG, "deleteComment() params:" + JSON.toJSONString(map));
        sendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH + ConstantNet.API_MOMENT_DELETE_COMMENT, Method.GET, new HttpCallBack()
        {
            @Override
            public void onSuccess(String resultJSON, ResultBean resultBean)
            {
                LogUtils.d(TAG, "deleteComment() onSuccess resultJSON:" + resultJSON);
                if (resultBean.getResultCode() == 2222)
                {
                    LogUtils.d(TAG, "deleteComment() success");
                    sendHandlerMsg(handler, 0, "成功");

                }
                else if (resultBean.getResultCode() == 3333)
                {
                    LogUtils.w(TAG, "deleteComment() failure resultJSON:" + resultJSON);
                    sendHandlerMsg(handler, 1, "失败" + resultBean.getMsg());
                }
                else
                {
                    LogUtils.e(TAG, "deleteComment() 未知返回码:" + resultJSON);
                    sendHandlerMsg(handler, -1, "未知返回码");
                }
            }

            @Override
            public void onFailure(String e)
            {
                LogUtils.w(TAG, "deleteComment() onFailure,caused by:" + e);
                sendHandlerMsg(handler, -1, Constants.NET_WRONG);
            }
        });

    }

    public interface onDeleteCommentCallback
    {
        void onSuccess(CommentEntity commentEntity);

        void onFailure(String info);
    }
    
    
    /**
     * 删除朋友圈
     */
    public void deleteMoment(final MomentEntity momentEntity,final onDeleteMomentCallback callback)
    {
        final Handler handler = new Handler()
        {
            public void handleMessage(Message msg)
            {
                String info = msg.obj.toString();
                LogUtils.d(TAG, "deleteMoment() ");
                switch (msg.what)
                {
                    case -1:
                        callback.onFailure(info);
                        break;
                    case 0:
                        callback.onSuccess(momentEntity);
                        break;
                    case 1:
                        callback.onFailure(info);
                        break;
                    default:
                        break;
                }
            };
        };

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("momentID", momentEntity.getMomentID() + "");
        map.put("publisherID", momentEntity.getPublisherID() + "");
        LogUtils.d(TAG, "deleteMoment() params:" + JSON.toJSONString(map));
        sendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH + ConstantNet.API_MOMENT_DELETE_MOMENT, Method.GET, new HttpCallBack()
        {
            @Override
            public void onSuccess(String resultJSON, ResultBean resultBean)
            {
                LogUtils.d(TAG, "deleteMoment() onSuccess resultJSON:" + resultJSON);
                if (resultBean.getResultCode() == 2222)
                {
                    LogUtils.d(TAG, "deleteMoment() success");
                    sendHandlerMsg(handler, 0, "成功");

                }
                else if (resultBean.getResultCode() == 3333)
                {
                    LogUtils.w(TAG, "deleteMoment() failure resultJSON:" + resultJSON);
                    sendHandlerMsg(handler, 1, "失败" + resultBean.getMsg());
                }
                else
                {
                    LogUtils.e(TAG, "deleteMoment() 未知返回码:" + resultJSON);
                    sendHandlerMsg(handler, -1, "未知返回码");
                }
            }

            @Override
            public void onFailure(String e)
            {
                LogUtils.w(TAG, "deleteMoment() onFailure,caused by:" + e);
                sendHandlerMsg(handler, -1, Constants.NET_WRONG);
            }
        });

    }

    public interface onDeleteMomentCallback
    {
        void onSuccess(MomentEntity commentEntity);

        void onFailure(String info);
    }

    // /////////////////////////////////////////私有方法//////////////////////////////
    /**
     * 发送通知
     * 
     * @param handler
     * @param what
     * @param info
     */
    private void sendHandlerMsg(Handler handler, int what, Object info)
    {
        Message message = Message.obtain();
        message.what = what;
        message.obj = info;
        handler.sendMessage(message);
    }

    /**
     * 处理下拉刷新返回的结果
     * 
     * @param resultJson
     *            从服务器请求的数据
     */
    private void handleDownRefreshResult(String resultJson, GetMomentEntityListener listener)
    {
        LogUtils.v(TAG, "handleDownRefreshResult params[resultJson=" + resultJson + "]");
        JSONObject jsonObject = JSON.parseObject(resultJson);
        //
        String str = jsonObject.getString("result");
        MomentRefreshResult momentRefreshResult = JsonUtils.json2Bean(str, MomentRefreshResult.class);
        LogUtils.d(TAG, "handleDownRefreshResult() " + momentRefreshResult.toString());
        //
        JSONObject jsonObject2 = jsonObject.getJSONObject("result");
        long momentIndex = jsonObject2.getLongValue("momentIndex");
        long commentIndex = jsonObject2.getLongValue("commentIndex");
        LogUtils.v(TAG, "handleDownRefreshResult() momentIndex=" + momentIndex + ",commentIndex=" + commentIndex);
        // 保存索引
        MomentManager.getInstance().saveIndex(momentIndex, commentIndex);

        JSONArray newMoments = jsonObject2.getJSONArray("newMoments");
        JSONArray newComments = jsonObject2.getJSONArray("newComments");
        JSONArray deleteMoments = jsonObject2.getJSONArray("deleteMoments");
        JSONArray deleteComments = jsonObject2.getJSONArray("deleteComments");

        // 解析删除的作品圈数据
        ArrayList<String> deleteMomentArray = new ArrayList<String>();
        if (deleteMoments != null && deleteMoments.size() > 0)
        {
            LogUtils.v(TAG, "handleDownRefreshResult() deleteMoments:" + deleteMoments.toJSONString());
            MomentManager.getInstance().deleteMoment(deleteMomentArray);
        }
        else
        {
            LogUtils.v(TAG, "handleDownRefreshResult() 无deleteMoments");
        }

        // 解析删除的评论数据
        ArrayList<String> deleteCommentArray = new ArrayList<String>();
        if (deleteComments != null && deleteComments.size() > 0)
        {
            LogUtils.v(TAG, "handleDownRefreshResult() deleteComments:" + deleteComments.toJSONString());
            MomentManager.getInstance().deleteComment(deleteCommentArray);
        }
        else
        {
            LogUtils.v(TAG, "handleDownRefreshResult() 无deleteComments");
        }

        // 解析新的朋友圈数据
        ArrayList<MomentEntity> momentEntities = new ArrayList<MomentEntity>();
        if (newMoments != null && newMoments.size() > 0)
        {
            LogUtils.v(TAG, "handleDownRefreshResult() newMoments:" + newMoments.toJSONString());
            for (int i = 0; i < newMoments.size(); i++)
            {
                JSONObject moment = newMoments.getJSONObject(i);
                MomentEntity momentEntity = parseMomentEntity(moment);
                momentEntities.add(momentEntity);
            }
            MomentManager.getInstance().addMoment(momentEntities);
        }
        else
        {
            LogUtils.v(TAG, "handleDownRefreshResult() 无newMoments");
        }

        // 解析新的朋友圈评论数据
        ArrayList<CommentEntity> commentEntities = new ArrayList<CommentEntity>();
        if (newComments != null && newComments.size() > 0)
        {
            LogUtils.v(TAG, "handleDownRefreshResult() newComments:" + newComments.toJSONString());
            for (int i = 0; i < newComments.size(); i++)
            {
                JSONObject comment = newComments.getJSONObject(i);
                CommentEntity commentEntity = parseCommentEntity(comment);
                commentEntities.add(commentEntity);
            }
            MomentManager.getInstance().addComment(commentEntities);
        }
        else
        {
            LogUtils.v(TAG, "handleDownRefreshResult() 无newComments");
        }
        listener.onSuccess("下拉刷新完成");
    }

    /**
     * 从网上加载朋友圈数据
     */
    private void upRefreshFromNet(final GetMomentEntityListener listener)
    {
        final Handler handler = new Handler()
        {
            public void handleMessage(Message msg)
            {
                switch (msg.what)
                {
                    case -1:
                        // 网络错误
                        listener.onFailure(msg.obj.toString());
                        break;
                    case 0:
                        // 成功
                        String data = msg.obj.toString();
                        handleMomentFromNet(data, listener);
                        break;
                    case 1:
                        // 其他错误
                        listener.onFailure(msg.obj.toString());
                        break;
                    default:
                        break;
                }
            }
        };
        int count = MomentManager.getInstance().getMoment().size();
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("dwID", DecentWorldApp.getInstance().getDwID());
        map.put("amount", "" + count);
        LogUtils.v(TAG, "upRefreshFromNet() params:" + JSON.toJSONString(map));
        Context context = DecentWorldApp.getGlobalContext();
        SendUrl sendUrl = new SendUrl(context);
        sendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH + ConstantNet.API_MOMENT_GET_TIMELINE, Method.GET, new HttpCallBack()
        {
            @Override
            public void onSuccess(String resultJSON, ResultBean resultBean)
            {
                LogUtils.v(TAG, "upRefreshFromNet() resultJSON:" + resultJSON);
                if (resultBean.getResultCode() == 2222)
                {
                    LogUtils.v(TAG, "upRefreshFromNet() success");
                    String data = resultBean.getData().toString();
                    sendHandlerMsg(handler, 0, data);
                }
                else if (resultBean.getResultCode() == 3333)
                {
                    sendHandlerMsg(handler, 1, "无历史记录");
                }
                else
                {
                    sendHandlerMsg(handler, -1, "未知返回码" + resultBean.getResultCode());
                    LogUtils.w(TAG, "upRefreshFromNet() 未知返回码：" + resultBean.getResultCode());
                }
            }

            @Override
            public void onFailure(String e)
            {
                LogUtils.e(TAG, "upRefreshFromNet() onFailure,caused by:" + e);
                sendHandlerMsg(handler, -1, Constants.NET_WRONG);
            }
        });
    }

    /**
     * 处理下拉刷新的结果
     */
    private void handleMomentFromNet(String data, GetMomentEntityListener listener)
    {
        long minMomentID = MomentManager.getInstance().getMinMomentIndex();
        JSONObject jsonObject = JSON.parseObject(data);
        JSONArray newMoments = jsonObject.getJSONArray("moments");
        JSONArray newComments = jsonObject.getJSONArray("comments");
        // 解析新的朋友圈数据
        Map<Long, MomentEntity> mMomentMap = new HashMap<Long, MomentEntity>();
        List<MomentEntity> momentEntities = new ArrayList<MomentEntity>();
        if (newMoments != null && newMoments.size() > 0)
        {
            LogUtils.v(TAG, "handleMomentFromNet() newMoments:" + newMoments.toJSONString());
            for (int i = 0; i < newMoments.size(); i++)
            {
                JSONObject moment = newMoments.getJSONObject(i);
                MomentEntity momentEntity = new MomentEngine().parseMomentEntity(moment);
                if (minMomentID > 0)
                {
                    if (momentEntity.getMomentID() < minMomentID)
                    {
                        momentEntities.add(momentEntity);
                        mMomentMap.put(momentEntity.getMomentID(), momentEntity);
                    }
                }
                else
                {
                    momentEntities.add(momentEntity);
                    mMomentMap.put(momentEntity.getMomentID(), momentEntity);
                }
            }
        }
        else
        {
            LogUtils.v(TAG, "handleMomentFromNet() 无newMoments");
        }

        // 解析新的朋友圈评论数据
        if (newComments != null && newComments.size() > 0)
        {
            LogUtils.v(TAG, "handleMomentFromNet() newComments:" + newComments.toJSONString());
            for (int i = 0; i < newComments.size(); i++)
            {
                JSONObject comment = newComments.getJSONObject(i);
                CommentEntity commentEntity = new MomentEngine().parseCommentEntity(comment);

                MomentEntity momentEntity = mMomentMap.get(commentEntity.getMomentID());
                if (momentEntity != null)
                {
                    List<CommentEntity> commentList = momentEntity.getCommentList();
                    commentList.add(commentEntity);
                    commentEntity.save();
                    Collections.sort(commentList);
                }
            }
        }
        else
        {
            LogUtils.v(TAG, "handleMomentFromNet() 无newComments");
        }
        MomentManager.getInstance().addMoment(momentEntities);
        listener.onSuccess("上拉刷新完成");
    };

}
