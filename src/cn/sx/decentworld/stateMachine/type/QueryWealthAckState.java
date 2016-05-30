/**
 * 
 */
package cn.sx.decentworld.stateMachine.type;

import java.util.HashMap;

import android.os.Handler;
import android.os.Message;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.common.CommUtil;
import cn.sx.decentworld.common.ConstantNet;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.logSystem.LogUtils;
import cn.sx.decentworld.manager.SelfInfoManager;
import cn.sx.decentworld.network.SendUrl;
import cn.sx.decentworld.network.SendUrl.HttpCallBack;
import cn.sx.decentworld.network.entity.ResultBean;
import cn.sx.decentworld.stateMachine.MsgContext;
import cn.sx.decentworld.stateMachine.MsgState;
import cn.sx.decentworld.stateMachine.StateConfig;
import cn.sx.decentworld.stateMachine.StateConfig.QueryWealthResult;
import cn.sx.decentworld.stateMachine.param.HandlerParam;
import cn.sx.decentworld.utils.XmppPacketParseUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.Request.Method;

/**
 * @ClassName: StartSendState.java
 * @Description: 【4号状态】
 * @author: cj
 * @date: 2016年4月27日 下午3:50:58
 */

/**
 * 
 * @ClassName: QueryWealthAckState.java
 * @Description:
 * @author: cj
 * @date: 2016年4月28日 下午12:34:51
 */
public class QueryWealthAckState extends MsgState
{
    private static final String TAG = "QueryWealthAckState";
//    // 系统错误的次数
//    private int sys_error_count = 0;
//    // 网络错误的次数
//    private int net_error_count = 0;

    /**
     * 开始发送消息
     */
    @Override
    public void startSend(String packetID)
    {
        LogUtils.d(TAG, "startSend() params[packetID=" + packetID + ",info=do nothing" + "]");
        // TODO nothing

    }

    /**
     * 等待wealth回执
     */
    @Override
    public void waitWealthAck(String packetID)
    {
        LogUtils.d(TAG, "waitWealthAck() params[packetID=" + packetID + ",info=do nothing" + "]");
        // TODO nothing

    }

    /**
     * 等待时间内接收到wealth回执
     */
    @Override
    public void endSuccess(String packetID)
    {
        LogUtils.d(TAG, "endSuccess() params[packetID=" + packetID + ",info=do nothing" + "]");
        // TODO nothing

    }

    private Handler handler;

    /**
     * 等待时间内没有收到服务器wealth回执，查询
     */
    @Override
    public void queryWealthAck(final String packetID)
    {
        LogUtils.d(TAG, "queryWealthAck() params[packetID=" + packetID + "]");
        // 查询wealth回执的逻辑
        // 请求结果又有三种
        // 1）成功，服务器收到了信息；结束；
        // 2）成功，服务器没有收到信息；重新发送消息；
        // 3）失败，继续请求，3次之后还未成功则标记为感叹号；
        // handler = new Handler()
        // {
        // @Override
        // public void handleMessage(Message msg)
        // {
        // netRequest(packetID);
        // }
        // };
        netRequest(packetID);
    }

    /**
     * 查询结果是服务器没有收到1号消息
     */
    @Override
    public void sendFailResend(String packetID)
    {
        LogUtils.d(TAG, "sendFailResend() params[packetID=" + packetID + ",info=do nothing" + "]");
        // TODO nothing

    }

    /**
     * 发送失败，用户可手动重发
     */
    @Override
    public void endFailure(String packetID)
    {
        LogUtils.d(TAG, "endFailure() params[packetID=" + packetID + ",info=do nothing" + "]");
        // TODO nothing

    }

    /**
     * 服务器无响应，用户可手动重发
     */
    @Override
    public void endServerNoResponse(String packetID)
    {
        LogUtils.d(TAG, "endServerNoResponse() params[packetID=" + packetID + ",info=do nothing" + "]");
        // TODO nothing
    }

    /**
     * 进行网络请求，查询服务器是否接受到消息
     */
    protected void netRequest(final String packetID)
    {
        final Handler handler = new Handler()
        {
            public void handleMessage(Message msg)
            {
                HandlerParam handlerParam = (HandlerParam) msg.obj;
                String packetId = (String) handlerParam.getObject();
                switch (handlerParam.getQueryWealthResult())
                {
                    case SUCCESS_WEALTH:
                        LogUtils.v(TAG, "netRequest() handler,SUCCESS_WEALTH,packetId=" + packetId);
                        QueryWealthAckState.super.msgContext.setMsgState(MsgContext.endSuccessState);
                        QueryWealthAckState.super.msgContext.getMsgState().endSuccess(packetId);
                        break;
                    case SUCCESS_ERROR:
                        LogUtils.v(TAG, "netRequest() handler,SUCCESS_ERROR,packetId=" + packetId);
                        // 身家不足，进入【7号状态】，暂定解决方法
                        QueryWealthAckState.super.msgContext.setMsgState(MsgContext.endServerNoResponseState);
                        QueryWealthAckState.super.msgContext.getMsgState().endServerNoResponse(packetId);

                        break;
                    case FAILURE:
                        LogUtils.v(TAG, "netRequest() handler,FAILURE,packetId=" + packetId);
                        QueryWealthAckState.super.msgContext.setMsgState(MsgContext.sendFailResendState);
                        QueryWealthAckState.super.msgContext.getMsgState().sendFailResend(packetId);
                        break;
                    case FAILURE_SYS_ERROR:
                        LogUtils.v(TAG, "netRequest() handler,FAILURE_SYS_ERROR,packetId=" + packetId);
                        // QueryWealthAckState.super.msgContext.setMsgState(MsgContext.queryWealthAckState);
                        // QueryWealthAckState.super.msgContext.getMsgState().queryWealthAck(packetId);
                        netRequest(packetId);
                        break;
                    case FAILURE_SYS_ERROR_OVER_3:
                        LogUtils.v(TAG, "netRequest() handler,FAILURE_SYS_ERROR_OVER_3,packetId=" + packetId);
                        QueryWealthAckState.super.msgContext.setMsgState(MsgContext.endServerNoResponseState);
                        QueryWealthAckState.super.msgContext.getMsgState().endServerNoResponse(packetId);
                        break;
                    case FAILURE_NET_ERROR:
                        LogUtils.v(TAG, "netRequest() handler,FAILURE_NET_ERROR,packetId=" + packetId);
                        // QueryWealthAckState.super.msgContext.setMsgState(MsgContext.queryWealthAckState);
                        // QueryWealthAckState.super.msgContext.getMsgState().queryWealthAck(packetId);
                        netRequest(packetId);
                        break;
                    case FAILURE_NET_ERROR_OVER_3:
                        LogUtils.v(TAG, "netRequest() handler,FAILURE_NET_ERROR_OVER_3,packetId=" + packetId);
                        QueryWealthAckState.super.msgContext.setMsgState(MsgContext.endServerNoResponseState);
                        QueryWealthAckState.super.msgContext.getMsgState().endServerNoResponse(packetId);
                        break;
                    default:
                        break;
                }
            };
        };
        SendUrl sendUrl = new SendUrl(DecentWorldApp.getGlobalContext());
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("dwID", DecentWorldApp.getInstance().getDwID());
        map.put("tempID", packetID);
        LogUtils.v(TAG, "netRequest() params:" + JSON.toJSONString(map));
        sendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH + ConstantNet.API_SYNC_RECV1, Method.GET, new HttpCallBack()
        {
            String packetID_ = packetID;
            @Override
            public void onSuccess(String resultJSON, ResultBean resultBean)
            {
                LogUtils.v(TAG, "netRequest() resultJSON:" + resultJSON + ",Thread:" + Thread.currentThread().getName());
                if (resultBean.getResultCode() == 2222)
                {
                    String jsonStr = resultBean.getData().toString();
                    JSONObject jsonObject0 = JSON.parseObject(jsonStr);
                    String recv = jsonObject0.getString("RECV");
                    if (CommUtil.isNotBlank(recv))
                    {
                        // 服务器收到了信息
                        org.jivesoftware.smack.packet.Message message = XmppPacketParseUtils.parseMessage(recv);
                        if (message != null)
                        {
                            if (message.getSubject().equals("wealth"))
                            {
                                LogUtils.d(TAG, "netRequest() onSuccess,subject = wealth");
                                String packetId = message.getPacketID();
                                JSONObject jsonObject = JSON.parseObject(message.getBody());
                                String wealth = jsonObject.getString("wealth");
                                long id = jsonObject.getLongValue("id");
                                int chatType = jsonObject.getIntValue("chatType");
                                // 更新内存中的身家
                                SelfInfoManager.getInstance().notifyWealthChanged(Float.valueOf(wealth));
                                // 服务器收到了1号信息，返回身家信息；进入【3号状态】；
                                sendHandlerMsg(handler, new HandlerParam(packetID_ , QueryWealthResult.SUCCESS_WEALTH));
                            }
                            else if (message.getSubject().equals("error"))
                            {
                                // 服务器收到了1号信息，身家不足；进入【N号状态】；
                                LogUtils.d(TAG, "netRequest() onSuccess,subject=error,身价不足,packetID_ =" + packetID_);
                                sendHandlerMsg(handler, new HandlerParam(packetID_ , QueryWealthResult.SUCCESS_ERROR));
                            }
                        }
                    }
                    else
                    {
                        // 服务器没有收到信息
                        // 重新发送信息
                        LogUtils.d(TAG, "netRequest() 服务器没有收到信息，packetID=" + packetID_);
                        sendHandlerMsg(handler, new HandlerParam(packetID_ , QueryWealthResult.FAILURE));
//                        sys_error_count = 0;
//                        net_error_count = 0;
                        int[] is = MsgContext.countMap.get(packetID_);
                        is[0] = 0;
                        is[1] = 0;
                    }
                }
                else if (resultBean.getResultCode() == 3333)
                {
                    // 系统错误，本次请求无效
                    // 重新请求
//                    sys_error_count++;
                    int[] is = MsgContext.countMap.get(packetID_);
                    is[0] = is[0]+1;
                    if (is[0] >= StateConfig.SYS_ERROR_DEFAULT_COUNT)
                    {
                        LogUtils.d(TAG, "netRequest() onSuccess，系统错误次数大于" + StateConfig.SYS_ERROR_DEFAULT_COUNT + "次，状态机结束");
                        sendHandlerMsg(handler, new HandlerParam(packetID_ , QueryWealthResult.FAILURE_SYS_ERROR_OVER_3));
                    }
                    else
                    {
                        try
                        {
                            LogUtils.d(TAG, "netRequest() onSuccess，系统错误次数为第 " + is[0] + " 次，休眠" + StateConfig.SYS_ERROR_DELAY_TIME / 1000 + "s后继续请求");
                            Thread.sleep(StateConfig.SYS_ERROR_DELAY_TIME);
                            LogUtils.d(TAG, "netRequest() 系统错误次数为第 " + is[0] + " 次，继续请求");
                            sendHandlerMsg(handler, new HandlerParam(packetID_ , QueryWealthResult.FAILURE_SYS_ERROR));
                        }
                        catch (InterruptedException e1)
                        {
                            LogUtils.e(TAG, "netRequest() onSuccess，3333 InterruptedException：" + e1.toString());
                        }
                    }
                }
            }

            @Override
            public void onFailure(String e)
            {
                LogUtils.e(TAG, "netRequest() onFailure,cause by;" + e + ",Thread:" + Thread.currentThread().getName());
                // 网络错误，本次请求无效
                // 重新请求
//                net_error_count++;
                int[] is = MsgContext.countMap.get(packetID_);
                is[1] = is[1]+1;
                if (is[1] >= StateConfig.NET_ERROR_DEFAULT_COUNT)
                {
                    LogUtils.d(TAG, "netRequest() onFailure,net_error_count>=3，状态机结束");
                    sendHandlerMsg(handler, new HandlerParam(packetID_ , QueryWealthResult.FAILURE_NET_ERROR_OVER_3));
                }
                else
                {
                    try
                    {
                        LogUtils.d(TAG, "netRequest() onFailure，网络错误次数为第 " + is[1] + " 次，休眠" + StateConfig.NET_ERROR_DELAY_TIME / 1000 + "s后继续请求");
                        Thread.sleep(StateConfig.NET_ERROR_DELAY_TIME);
                        LogUtils.d(TAG, "netRequest() onFailure，网络错误次数为第 " + is[1] + " 次，继续请求");
                        sendHandlerMsg(handler, new HandlerParam(packetID_ , QueryWealthResult.FAILURE_NET_ERROR));
                    }
                    catch (InterruptedException e1)
                    {
                        LogUtils.e(TAG, "netRequest() onFailure，InterruptedException：" + e1.toString());
                    }
                }
            }
        });
    }

    /**
     * 发送Handler消息
     */
    private void sendHandlerMsg(Handler handler, Object object)
    {
        Message message = Message.obtain();
        message.what = 0;
        message.obj = object;
        handler.sendMessage(message);
    }
}
