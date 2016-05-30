///**
// * 
// */
//package cn.sx.decentworld.engine;
//
//import java.io.File;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
//import android.os.Handler;
//
//import com.activeandroid.query.Select;
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import com.android.volley.Request.Method;
//
//import cn.sx.decentworld.DecentWorldApp;
//import cn.sx.decentworld.common.ConstantNet;
//import cn.sx.decentworld.common.Constants;
//import cn.sx.decentworld.config.ConstantConfig;
//import cn.sx.decentworld.engine.ChatSingleEngine.GetHistoryMsgCallback;
//import cn.sx.decentworld.entity.dao.DWMessageDao;
//import cn.sx.decentworld.entity.db.DWMessage;
//import cn.sx.decentworld.entity.db.DWMessage.Direct;
//import cn.sx.decentworld.entity.db.DWMessage.MessageType;
//import cn.sx.decentworld.logSystem.LogUtils;
//import cn.sx.decentworld.network.SendUrl;
//import cn.sx.decentworld.network.SendUrl.HttpCallBack;
//import cn.sx.decentworld.network.entity.ResultBean;
//import cn.sx.decentworld.utils.ImageUtils;
//
///**
// * @ClassName: MsgHistoryEngine
// * @Description: 消息历史记录
// * @author: Jackchen
// * @date: 2016年5月24日 下午4:09:19
// */
//public class MsgHistoryEngine
//{
//    private static final String TAG = "MsgHistoryEngine";
//
//    /**
//     * 获取单聊历史记录
//     * 
//     * @param userID
//     * @param otherID
//     * @param chatType
//     * @param msgList
//     * @param getHistoryMsgCallback
//     */
//    public void getHistoryMsg(String userID, String otherID, int chatType, int msgAllCount, int msgvalidCount, long minID, GetHistoryMsgCallback getHistoryMsgCallback)
//    {
//        if (msgAllCount > 0)
//        {
//            // 消息列表中已经存在数据，再次从本地数据库拿取10条记录，传入列表中时间最早的消息mid
//            LogUtils.i(TAG, "getHistoryMsg...msgvalidCount = " + msgvalidCount + ",minID=" + minID);
//            getNextPageMsg(userID, otherID, minID, chatType, getHistoryMsgCallback);
//        }
//        else
//        {
//            // 消息列表中没有数据，首先从本地数据库拿取10条记录
//            getFistPageMsg(userID, otherID, chatType, getHistoryMsgCallback);
//        }
//    }
//
//    /**
//     * 第一次获取单聊历史聊天记录
//     * 
//     * @param dwID
//     * @param toID
//     * @param chatType
//     * @param getHistoryMsgCallback
//     */
//    private void getFistPageMsg(String dwID, String toID, int chatType, GetHistoryMsgCallback getHistoryMsgCallback)
//    {
//        List<DWMessage> firstPageMsg = DWMessageDao.getFirstPageMsg(toID, chatType);
//        if (firstPageMsg.size() > 0)
//        {
//            LogUtils.v(TAG, "getFistPageMsg() size=" + firstPageMsg.size());
//            getHistoryMsgCallback.onSuccess(firstPageMsg);
//        }
//        else
//        {
//            LogUtils.v(TAG, "getFistPageMsg() get history from net");
//            getUserHistoryMsg(dwID, toID, -1, chatType, getHistoryMsgCallback);
//        }
//    }
//
//    /**
//     * 手动加载十条数据
//     * 
//     * @param dwID
//     *            自己的dwID
//     * @param toID
//     *            对方的dwID
//     * @return
//     */
//    private void getNextPageMsg(String dwID, String toID, long minMsgID, int chatType, GetHistoryMsgCallback getHistoryMsgCallback)
//    {
//        if (minMsgID == -1)
//        {
//            getUserHistoryMsg(dwID, toID, -1, chatType, getHistoryMsgCallback);
//        }
//        else
//        {
//            String sql = "((userID = ? and fromDwId=? and toDwId=? and chatType = ?)" + "or" + "(userID = ? and fromDwId=? and toDwId=? and chatType = ?))" + "and mid<?";
//            List<DWMessage> temp = new Select().from(DWMessage.class).where(sql, dwID, dwID, toID, chatType, dwID, toID, dwID, chatType, minMsgID).limit(ConstantConfig.COUNT_SINGLE_PAGE)
//                    .orderBy("mid desc").execute();// ASC，添加消息id后，排序字段换成消息id
//            List<DWMessage> list = new ArrayList<DWMessage>();
//            // 选取非通知类型消息
//            for (DWMessage msg : temp)
//            {
//                if (msg.getMessageType() != MessageType.NOTIFY.getIndex())
//                {
//                    list.add(msg);
//                }
//            }
//
//            if (list.size() > 0)
//            {
//                // 从数据库拿取十条消息，判断如果大于0条，则返回
//                LogUtils.i(TAG, "getNextPageMsg...size=" + list.size());
//                getHistoryMsgCallback.onSuccess(list);
//            }
//            else
//            {
//                // 否则从网络获取
//                LogUtils.i(TAG, "getNextPageMsg...数据库中数据不足，从网络拿取");
//                getUserHistoryMsg(dwID, toID, minMsgID, chatType, getHistoryMsgCallback);
//            }
//        }
//    }
//
//    /**
//     * 计算消息数量，通知消息类型不计算在内
//     */
//    public static int getMsgCount(List<DWMessage> list)
//    {
//        int i = 0;
//        for (DWMessage msg : list)
//        {
//            if (msg.getMessageType() == MessageType.NOTIFY.getIndex())
//                continue;
//            i++;
//        }
//        return i;
//    }
//
//    public static long getMid(List<DWMessage> list)
//    {
//        long minID = 0;
//        for (DWMessage msg : list)
//        {
//            if (msg.getMessageType() != MessageType.NOTIFY.getIndex() && msg.getMid() != -1)
//            {
//                minID = msg.getMid();
//                return minID;
//            }
//        }
//        return minID;
//    }
//
////    /**
////     * 获取单聊聊天记录
////     * 
////     * @param dwID
////     *            当前用户ID
////     * @param toID
////     *            对方ID
////     * @param firstIndex
////     *            消息的最小ID
////     * @param chatType
////     *            聊天类型
////     * @param getHistoryMsgCallback
////     */
////    private void getUserHistoryMsg(String dwID, String toID, long firstIndex, int chatType, final GetHistoryMsgCallback getHistoryMsgCallback)
////    {
////        final Handler handler = new Handler()
////        {
////            public void handleMessage(android.os.Message msg)
////            {
////                // 解析下列数据
////                List<DWMessage> temp = new ArrayList<DWMessage>();
////                switch (msg.what)
////                {
////                    case -1:
////                        // 发送失败，网络错误
////                        getHistoryMsgCallback.onFailure(msg.obj.toString());
////                        break;
////                    case 0:
////                        // 成功
////                        String result = msg.obj.toString();
////                        LogUtils.d(TAG, "获取用户单聊历史记录成功");
////                        JSONObject jsonObject = JSON.parseObject(result);
////                        JSONArray array = jsonObject.getJSONArray("chatList");
////                        if (array == null || array.size() <= 0)
////                        {
////                            getHistoryMsgCallback.onSuccess(temp);
////                            return;
////                        }
////
////                        DWMessage dwMessage;
////                        for (int i = 0; i < array.size(); i++)
////                        {
////                            JSONObject ob = array.getJSONObject(i);
////                            int messageType = ob.getIntValue("messageType");
////                            int direct = ob.getIntValue("direct");// 0 接收 1发送
////                            String connectorID = ob.getString("connectorID");
////                            String sendTime = ob.getString("sendTime");
////                            String dwID = ob.getString("dwID");
////                            long id = ob.getLongValue("id");
////                            int chatType = ob.getIntValue("chatType");
////
////                            if (direct == DWMessage.Direct.RECEIVE.ordinal())
////                            {
////                                dwMessage = new DWMessage(messageType , Direct.RECEIVE);
////                                dwMessage.setFromDwId(connectorID);
////                                dwMessage.setTo(dwID);
////                            }
////                            else
////                            {
////                                dwMessage = new DWMessage(messageType , Direct.SEND);
////                                dwMessage.setTo(connectorID);
////                                dwMessage.setFromDwId(dwID);
////                            }
////                            dwMessage.setTime(sendTime);
////                            dwMessage.setMid(id);
////                            dwMessage.setChatType(chatType);
////                            dwMessage.setSendSuccess(1);
////                            if (messageType == MessageType.TEXT.getIndex())
////                            {
////                                // 文字消息
////                                dwMessage.setBody(ob.getString("message"));
////                            }
////                            else if (messageType == MessageType.VOICE.getIndex())
////                            {
////                                // 语音消息
////                                String data = ob.getString("message");
////                                JSONObject object = JSON.parseObject(data);
////                                String uri = object.getString("uri");
////                                int length = Integer.valueOf(object.getString("length"));
////                                dwMessage.setUri(uri);
////                                dwMessage.ifFromNet = 0;
////                                dwMessage.setRemoteUrl(uri);
////                                dwMessage.setVoiceTime(length);
////                            }
////                            else if (messageType == MessageType.IMAGE.getIndex())
////                            {
////                                // 图片消息
////                                String data = ob.getString("message");
////                                JSONObject object = JSON.parseObject(data);
////                                String uri = object.getString("uri");
////                                String img = object.getString("img");
////                                File file = ImageUtils.AnalyticThumbnail(img);
////                                dwMessage.setUri(file.getAbsolutePath());
////                                dwMessage.setRemoteUrl(uri);
////                            }
////                            else if (messageType == MessageType.CARD.getIndex())
////                            {
////                                // 名片消息
////                                String data = ob.getString("message");
////                                LogUtils.i(TAG, "messageType=3," + "body=" + ob.toJSONString());
////                                JSONObject object = JSON.parseObject(data);
////                                String forwardName = object.getString("nickName");
////                                String forwardDwId = object.getString("dwID");
////                                dwMessage.setForwardDwId(forwardDwId);
////                                dwMessage.setForwardName(forwardName);
////                            }
////                            dwMessage.setIsRead(DWMessage.ReadStatus.YES.getIndex());
////                            dwMessage.save();
////                            temp.add(dwMessage);
////                        }
////                        getHistoryMsgCallback.onSuccess(temp);
////                        break;
////                    case 1:
////                        // 获取失败，其它原因
////                        getHistoryMsgCallback.onFailure(msg.obj.toString());
////                        break;
////                    default:
////                        break;
////                }
////            };
////        };
////
////        HashMap<String, String> map = new HashMap<String, String>();
////        map.put("dwID", dwID);
////        map.put("toID", toID);
////        map.put("firstIndex", String.valueOf(firstIndex));
////        map.put("chatType", String.valueOf(chatType));
////        LogUtils.v(TAG, "getUserHistoryMsg...begin:" + JSON.toJSONString(map));
////        SendUrl sendUrl = new SendUrl(DecentWorldApp.getGlobalContext());
////        sendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH + ConstantNet.API_SINGLE_HISTORY_MSG, Method.POST, new HttpCallBack()
////        {
////            @Override
////            public void onSuccess(String response, ResultBean msg)
////            {
////                LogUtils.v(TAG, "getUserHistoryMsg...response=" + response);
////                if (msg.getResultCode() == 2222)
////                {
////                    sendHandlerMsg(handler, 0, msg.getData().toString());
////                }
////                if (msg.getResultCode() == 3333)
////                {
////                    sendHandlerMsg(handler, 1, msg.getMsg());
////                }
////            }
////
////            @Override
////            public void onFailure(String e)
////            {
////                LogUtils.w(TAG, "getUserHistoryMsg...onFailure,cause by:" + e);
////                sendHandlerMsg(handler, -1, "网络错误");
////            }
////        });
////    }
//
//    /**
//     * 发送通知
//     * 
//     * @param handler
//     * @param what
//     * @param info
//     */
//    private void sendHandlerMsg(Handler handler, int what, Object info)
//    {
//        android.os.Message message = android.os.Message.obtain();
//        message.what = what;
//        message.obj = info;
//        handler.sendMessage(message);
//    }
//
//}
