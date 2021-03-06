/**
 * 
 */
package cn.sx.decentworld.engine;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.packet.Message;

import android.os.Handler;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.chat.interfaces.SendFileListener;
import cn.sx.decentworld.chat.interfaces.SendPictureListener;
import cn.sx.decentworld.chat.interfaces.SendVoiceListener;
import cn.sx.decentworld.common.ConstantNet;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.config.ConstantConfig;
import cn.sx.decentworld.connect.XmppHelper;
import cn.sx.decentworld.entity.MsgAndInfo;
import cn.sx.decentworld.entity.UserSessionInfo;
import cn.sx.decentworld.entity.dao.ContactUserDao;
import cn.sx.decentworld.entity.db.ContactUser;
import cn.sx.decentworld.entity.db.DWMessage;
import cn.sx.decentworld.entity.db.DWMessage.ChatRelationship;
import cn.sx.decentworld.entity.db.DWMessage.ChatType;
import cn.sx.decentworld.entity.db.DWMessage.Direct;
import cn.sx.decentworld.entity.db.DWMessage.MessageType;
import cn.sx.decentworld.entity.db.DWMessage.SendStatus;
import cn.sx.decentworld.logSystem.LogUtils;
import cn.sx.decentworld.network.SendUrl;
import cn.sx.decentworld.network.SendUrl.HttpCallBack;
import cn.sx.decentworld.network.entity.ResultBean;
import cn.sx.decentworld.utils.AES;
import cn.sx.decentworld.utils.ImageUtils;
import cn.sx.decentworld.utils.sputils.UserInfoHelper;

import com.activeandroid.query.Select;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.Request.Method;

/**
 * @ClassName: ChatSingleHelper.java
 * @Description: 单聊辅助类
 * @author: cj
 * @date: 2016年3月7日 上午11:21:19
 */
public class ChatSingleEngine
{
    public static final String TAG = "ChatSingleEngine";
    public static ChatSingleEngine instance;

    /**
     * 私有构造函数，防止产生多个实例
     */
    private ChatSingleEngine()
    {
        // TODO Auto-generated constructor stub
    }

    // ///////////////////////////////初始化//////////////////////////////////////

    /**
     * 获取单聊管理者实例
     * 
     * @return
     */
    public static ChatSingleEngine getInstance()
    {
        if (instance == null)
        {
            synchronized (ChatSingleEngine.class)
            {
                if (instance == null)
                    instance = new ChatSingleEngine();
            }
        }
        return instance;
    }

    public static void clear()
    {
        instance = null;
    }

    // ///////////////////////////////私有函数//////////////////////////////////////

    /**
     * 创建一个XMPPMessage
     * 
     * @param otherID
     *            对方的ID
     * @return
     */
    public Message createXmppMessage(String otherID)
    {
        Message message = new Message(otherID + Constants.SERVER_NAME , Message.Type.chat);
        message.setFrom(DecentWorldApp.getInstance().getDwID());
        message.setSubject("chat");
        return message;
    }

    /**
     * 获得临时的tempMaxID
     * 
     * @return
     */
    public long getTempMaxID(List<DWMessage> listMsg)
    {
        long tempMaxID = 0;
        if (listMsg != null && listMsg.size() > 0)
        {
            for (int i = listMsg.size() - 1; i >= 0; i--)
            {
                if (listMsg.get(i).getMessageType() != MessageType.NOTIFY.getIndex())
                {
                    tempMaxID = listMsg.get(i).getMid();
                    return tempMaxID;
                }
            }
        }
        LogUtils.i(TAG, "获取的临时tempMaxID=" + tempMaxID);
        return tempMaxID;
    }

    /**
     * 获取聊天管理者
     * 
     * @return
     */
    private ChatManager creatChatManager()
    {
        XMPPConnection connection = XmppHelper.getConnection();
        LogUtils.d(TAG, "creatChatManager...isConnected=" + connection.isConnected() + ",isAuth=" + connection.isAuthenticated());
        if (connection.isConnected())
        {
            if (connection.isAuthenticated())
            {
                LogUtils.d(TAG, "creatChatManager" + connection.getConnectionID() + "已经连接和授权");
                LogUtils.d(TAG, "test : creatChatManager XMPPConnection toString():" + connection.toString());
            }
            else
            {
                LogUtils.d(TAG, "creatChatManager" + connection.getConnectionID() + "已经连接/没有授权");
            }
            return ChatManager.getInstanceFor(connection);
        }
        else
        {
            LogUtils.e(TAG, "creatChatManager" + connection.getConnectionID() + " 没有连接!");
            return null;
        }
    }

    /**
     * 为消息添加当前时间和Token
     * 
     * @param dwMessage
     */
    private void addToken(DWMessage dwMessage)
    {
        long time = System.currentTimeMillis();
        dwMessage.setTime(String.valueOf(time));
        String randomStr = UserInfoHelper.getRandomStr();
        String token = AES.encode(DecentWorldApp.getInstance().getDwID() + time, randomStr);
        dwMessage.setToken(token);
    }

    // ///////////////////////////////发送文本消息//////////////////////////////////////

    /**
     * 构造一个文本消息对象
     * 
     * @param otherID
     * @param chatType
     * @param chatRelationship
     * @param textContent
     * @return
     */
    public DWMessage createTextDwMessage(String otherID, int chatType, int chatRelationship, long maxID, String textContent)
    {
        DWMessage dwMessage = new DWMessage(MessageType.TEXT.getIndex() , DWMessage.Direct.SEND);
        dwMessage.setChatType(chatType);
        dwMessage.setChatRelationship(chatRelationship);
        dwMessage.setTo(otherID);
        dwMessage.setBody(textContent);
        dwMessage.setTxtMsgID(new Message().getPacketID());
        dwMessage.setMid(maxID);
        return dwMessage;
    }

    /**
     * 将消息发送出去
     * 
     * @param dwMessage
     * @param listMsg
     * @throws NotConnectedException
     */
    public void sendTextMessage(DWMessage dwMessage, Message message) throws NotConnectedException
    {
        LogUtils.d(TAG, "sendTextMessage() " + dwMessage.toString() + message.toString());
        addToken(dwMessage);
        message.setBody(DWMessage.toJson(dwMessage));
        XMPPConnection connection = XmppHelper.getConnection();
        try
        {
            connection.sendPacket(message);
        }
        catch (NotConnectedException e)
        {
            LogUtils.e(TAG, "sendTextMessage() NotConnectedException:" + e.toString() + ",connection=" + connection.toString() + ",dwMessage=" + dwMessage.toString());
            throw new NotConnectedException();
        }
        finally
        {
            // 保存之前 去掉Token，防止信息泄露
            dwMessage.setToken("");
            dwMessage.save();
        }
    }

    // ///////////////////////////////发送语音信息//////////////////////////////////
    /**
     * 构造一个语音消息对象，在发送一条新的消息时需要调用，重发则不需要调用
     * 
     * @param otherID
     * @param chatType
     * @param chatRelationship
     * @return
     */
    public DWMessage createVoiceDwMessage(String otherID, int chatType, int chatRelationship, long mid, String voicePath, float voiceTime)
    {
        DWMessage dwMessage = new DWMessage(MessageType.VOICE.getIndex() , DWMessage.Direct.SEND);
        dwMessage.setChatType(chatType);
        dwMessage.setChatRelationship(chatRelationship);
        dwMessage.setTo(otherID);
        dwMessage.setVoiceTime(voiceTime);
        dwMessage.setTxtMsgID(new Message().getPacketID());
        dwMessage.setMid(mid);
        dwMessage.setLocalUrl(voicePath);
        return dwMessage;
    }

    /**
     * 发送语音
     * 
     * @param dwMessage
     * @param listMsg
     * @param picture
     * @param listener
     */
    public void sendVoiceMessage(DWMessage dwMessage, SendVoiceListener listener)
    {
        // 回调
        listener.onStart();
        if (!XmppHelper.getConnection().isConnected())
        {
            // 出现错误，出现没有连接服务器异常
            listener.onNotConnected();
            return;
        }
        // 发送语音
        sendFileNet(dwMessage, "/sendAudio", listener);
    }

    /**
     * 重新发送语音
     * 
     * @param dwMessage
     * @param listMsg
     * @param listener
     */
    public void reSendVoiceMessage(DWMessage dwMessage, long maxID, SendVoiceListener listener)
    {
        // 修改DWMessage
        dwMessage.setSendSuccess(2);
        dwMessage.setMid(maxID);
        dwMessage.save();
        File voiceFile = new File(dwMessage.getLocalUrl());
        float voiceTime = dwMessage.getVoiceTime();

        // 回调
        listener.onStart();
        if (!XmppHelper.getConnection().isConnected())
        {
            // 出现错误，出现没有连接服务器异常
            listener.onNotConnected();
            return;
        }
        // 发送语音
        sendFileNet(dwMessage, "/sendAudio", listener);
    }

    // ///////////////////////////////发送图片信息//////////////////////////////////

    /**
     * 构造一个图片消息对象，在发送一条新的消息时需要调用，重发则不需要调用
     * 
     * @param otherID
     * @param chatType
     * @param chatRelationship
     * @return
     */
    public DWMessage createImageDwMessage(String otherID, int chatType, int chatRelationship, long mid, String picturePath)
    {
        DWMessage dwMessage = new DWMessage(MessageType.IMAGE.getIndex() , Direct.SEND);
        dwMessage.setChatType(chatType);
        dwMessage.setChatRelationship(chatRelationship);
        dwMessage.setTo(otherID);
        dwMessage.setTxtMsgID(new Message().getPacketID());
        dwMessage.setMid(mid);
        dwMessage.setUri(picturePath);
        dwMessage.setLocalUrl(picturePath);
        return dwMessage;
    }

    /**
     * 发送图片
     * 
     * @param dwMessage
     * @param listMsg
     * @param picture
     * @param listener
     */
    public void sendPictureMessage(DWMessage dwMessage, SendPictureListener listener)
    {
        // 回调
        listener.onStart();
        if (!XmppHelper.getConnection().isConnected())
        {
            // 出现错误，出现没有连接服务器异常
            listener.onNotConnected();
            return;
        }
        // 发送图片
        sendFileNet(dwMessage, "/sendPicture", listener);
    }

    /**
     * 重新发送图片
     * 
     * @param dwMessage
     * @param listMsg
     * @param listener
     */
    public void reSendPictureMessage(DWMessage dwMessage, long maxID, SendPictureListener listener)
    {
        // 修改DWMessage
        dwMessage.setSendSuccess(DWMessage.SendStatus.SENDING.getIndex());
        dwMessage.setMid(maxID);
        dwMessage.save();
        // 回调
        listener.onStart();
        if (!XmppHelper.getConnection().isConnected())
        {
            // 出现错误，出现没有连接服务器异常
            listener.onNotConnected();
            return;
        }
        // 发送图片
        sendFileNet(dwMessage, "/sendPicture", listener);
    }

    // ///////////////////////////////网络请求//////////////////////////////////
    /**
     * 进行网络请求发送文件
     * 
     * @param dwMessage
     * @param file
     * @param fileTime
     * @param api
     * @param listener
     */
    private void sendFileNet(final DWMessage dwMessage, String api, final SendFileListener listener)
    {
        // final String packetID = dwMessage.getTxtMsgID();
        HashMap<String, String> hashmap = new HashMap<String, String>();
        hashmap.put("dwID", dwMessage.getFromDwId());
        hashmap.put("toID", dwMessage.getTo());
        hashmap.put("type", dwMessage.getChatType() + "");
        hashmap.put("msgID", dwMessage.getTxtMsgID());
        // 如果是语音，添加语音时间参数
        if (dwMessage.getMessageType() == DWMessage.MessageType.VOICE.getIndex())
        {
            float fileTime = dwMessage.getVoiceTime();
            hashmap.put("length", Math.round(fileTime) + "");
        }
        // 图片文件
        File[] images = new File[]
        { new File(dwMessage.getLocalUrl())};

        /** token = dwID + randomStr +key **/
        String dwID = hashmap.get("dwID");
        long time = System.currentTimeMillis();
        String randomStr = UserInfoHelper.getRandomStr();
        String token = AES.encode(dwID + time, randomStr);
        hashmap.put("randomStr", String.valueOf(time));
        hashmap.put("token", token);
        LogUtils.v(TAG, "sendFileNet() params:" + JSON.toJSONString(hashmap));
        SendUrl sendUrl = new SendUrl(DecentWorldApp.getInstance().getApplicationContext());
        sendUrl.httpRequestWithImage(hashmap, images, Constants.CONTEXTPATH_OPENFIRE + api, new HttpCallBack()
        {
            @Override
            public void onSuccess(String response, ResultBean msg)
            {
                LogUtils.v(TAG, "sendFileNet() response:" + response);
                if (msg.getResultCode() == 2222)
                {
                    LogUtils.v(TAG, "sendFileNet() success");
                    listener.onSuccess();
                }
                else
                {
                    LogUtils.w(TAG, "sendFileNet() failure,cause by:" + msg.getMsg());
                    listener.onFailure(dwMessage.getTxtMsgID(), msg.getMsg());
                }
            }

            @Override
            public void onFailure(String e)
            {
                listener.onNetWrong();
                LogUtils.e(TAG, "sendFileNet() onFailure,cause by:" + e);
            }
        });
    }

    /**
     * 转发语音
     * 
     * @param dwMessage
     * @param temp
     * @param maxID
     *            最大的ID
     * @param listener
     */
    public void transmitVoiceMessage(DWMessage dwMessage, DWMessage temp, long maxID, SendVoiceListener listener)
    {
        Message message = new Message();
        dwMessage.setTxtMsgID(message.getPacketID());
        dwMessage.setMid(maxID);
        dwMessage.setUri(temp.getUri());
        dwMessage.setVoiceTime(temp.getVoiceTime());
//        dwMessage.ifFromNet = 1;
        // 回调
        listener.onStart();
        if (!XmppHelper.getConnection().isConnected())
        {
            // 出现错误，出现没有连接服务器异常
            listener.onNotConnected();
            return;
        }
        // 发送语音
        transmitFileNet(dwMessage, temp.getMid(), listener);
    }

    /**
     * 转发图片
     * 
     * @param dwMessage
     * @param temp
     * @param listMsg
     * @param listener
     */
    public void transmitImageMessage(DWMessage dwMessage, long oldMid, SendPictureListener listener)
    {
        // 回调
        listener.onStart();
        if (!XmppHelper.getConnection().isConnected())
        {
            // 出现错误，出现没有连接服务器异常
            listener.onNotConnected();
            return;
        }
        // 发送图片
        transmitFileNet(dwMessage, oldMid, listener);
    }

    private void transmitFileNet(final DWMessage dwMessage, long mid, final SendFileListener listener)
    {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("msgID", "" + mid);
        map.put("dwID", dwMessage.getFromDwId());
        map.put("toID", dwMessage.getTo());
        map.put("chatType", "" + dwMessage.getChatType());
        map.put("smackID", dwMessage.getTxtMsgID());
        LogUtils.d(TAG, "transmitFileNet...params:" + JSON.toJSONString(map));
        SendUrl sendUrl = new SendUrl(DecentWorldApp.getInstance().getApplicationContext());
        sendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH_OPENFIRE + Constants.RESEND, Method.GET, new HttpCallBack()
        {
            @Override
            public void onSuccess(String response, ResultBean msg)
            {
                LogUtils.i(TAG, "sendFileWithParams...begin,response:" + response);
                if (msg.getResultCode() == 2222)
                {
                    LogUtils.i(TAG, "sendFileWithParams...success");
                    listener.onSuccess();
                }
                else
                {
                    LogUtils.i(TAG, "sendFileWithParams...failure,cause by:" + msg.getMsg());
                    listener.onFailure(dwMessage.getTxtMsgID(), msg.getMsg());
                }
            }

            @Override
            public void onFailure(String e)
            {
                listener.onNetWrong();
            }
        });
    }

    // ///////////////////////////////////////////转发名片消息////////////////////////////////////

    public DWMessage createCardDwMessage(String otherID, int chatType, int chatRelationship)
    {
        DWMessage dwMessage = new DWMessage(MessageType.CARD.getIndex() , DWMessage.Direct.SEND);
        dwMessage.setChatType(chatType);
        dwMessage.setChatRelationship(chatRelationship);
        dwMessage.setTo(otherID);
        return dwMessage;
    }

    public void sendCardMessage(DWMessage dwMessage, long maxID, ContactUser contactUser) throws NotConnectedException
    {
        // 构造Message
        Message message = new Message(dwMessage.getTo() , Message.Type.chat);
        LogUtils.i(TAG, "发送一张名片，Message.packetID=" + message.getPacketID());
        message.setFrom(dwMessage.getTo());
        message.setSubject("card");

        dwMessage.setSendSuccess(SendStatus.SENDING.getIndex());
        dwMessage.setTxtMsgID(message.getPacketID());
        dwMessage.setMid(maxID);

        // 发送名片添加的
        dwMessage.setForwardDwId(contactUser.getFriendID());
        dwMessage.setForwardName(contactUser.getShowName());

        Chat chat = creatChatManager().createChat(dwMessage.getTo() + Constants.SERVER_NAME, null);
        try
        {
            /** 添加token **/
            addToken(dwMessage);
            message.setBody(DWMessage.toJson(dwMessage));
            chat.sendMessage(message);
        }
        catch (NotConnectedException e)
        {
            // 没有连接，处理
            dwMessage.setSendSuccess(0);
            throw new NotConnectedException();
        }
        finally
        {
            // 保存之前 去掉Token，防止信息泄露
            dwMessage.setToken("");
            dwMessage.save();
        }
    }

    // /////////////////////////////////////获取单聊历史记录///////////////////////////////////////

    /**
     * 获取历史记录回调
     */
    public interface GetHistoryMsgCallback
    {
        void onSuccess(List<DWMessage> msgList);

        void onFailure(String cause);
    }

    /**
     * 发送名片给指定的人（即时搜索到人或者是联系人列表中的人）
     * 
     * @param forwardDwId
     *            被转发人的ID
     * @param forwardName
     *            被转发人的名字
     * @param otherID
     *            接收人的ID
     * @param otherNickname
     *            接收人的名字
     * @param otherWorth
     *            接收人的身价
     */
    public void sendCardTo(String forwardDwId, String forwardName, String otherID, String otherNickname, float otherWorth, int userType, SendCardToListener listener)
    {
        sendCardToE(forwardDwId, forwardName, otherID, otherNickname, otherWorth, userType, listener);
    }

    /**
     * 获取历史记录回调
     */
    public interface SendCardToListener
    {
        void onSuccess(DWMessage dwMessage);

        void onFailure(String cause);
    }

    // ////////////////////////////////////////////私有方法//////////////////////////////////////////////////

    /**
     * 计算消息数量，通知消息类型不计算在内
     */
    // private int getMsgCount(List<DWMessage> list)
    // {
    // int i = 0;
    // for (DWMessage msg : list)
    // {
    // if (msg.getMessageType() == MessageType.NOTIFY.getIndex())
    // continue;
    // i++;
    // }
    // return i;
    // }

    /**
     * 获得时间最早的一条消息的mid，不包括通知类型消息
     */
    // private long getMid(List<DWMessage> list)
    // {
    // long minID = 0;
    // for (DWMessage msg : list)
    // {
    // if (msg.getMessageType() != MessageType.NOTIFY.getIndex() && msg.getMid()
    // != -1)
    // {
    // minID = msg.getMid();
    // return minID;
    // }
    // }
    // return minID;
    // }

    /**
     * 获取单聊聊天记录
     * 
     * @param dwID
     *            当前用户ID
     * @param toID
     *            对方ID
     * @param firstIndex
     *            消息的最小ID
     * @param chatType
     *            聊天类型
     * @param getHistoryMsgCallback
     */
    private void getUserHistoryMsg(String dwID, String toID, long firstIndex, int chatType, final GetHistoryMsgCallback getHistoryMsgCallback)
    {
        final Handler handler = new Handler()
        {
            public void handleMessage(android.os.Message msg)
            {
                // 解析下列数据
                List<DWMessage> temp = new ArrayList<DWMessage>();
                switch (msg.what)
                {
                    case -1:
                        // 发送失败，网络错误
                        getHistoryMsgCallback.onFailure(msg.obj.toString());
                        break;
                    case 0:
                        // 成功
                        String result = msg.obj.toString();
                        LogUtils.d(TAG, "获取用户单聊历史记录成功");
                        JSONObject jsonObject = JSON.parseObject(result);
                        JSONArray array = jsonObject.getJSONArray("chatList");
                        if (array == null || array.size() <= 0)
                        {
                            getHistoryMsgCallback.onSuccess(temp);
                            return;
                        }

                        DWMessage dwMessage;
                        for (int i = 0; i < array.size(); i++)
                        {
                            JSONObject ob = array.getJSONObject(i);
                            int messageType = ob.getIntValue("messageType");
                            int direct = ob.getIntValue("direct");// 0 接收 1发送
                            String connectorID = ob.getString("connectorID");
                            String sendTime = ob.getString("sendTime");
                            String dwID = ob.getString("dwID");
                            long id = ob.getLongValue("id");
                            int chatType = ob.getIntValue("chatType");

                            if (direct == DWMessage.Direct.RECEIVE.ordinal())
                            {
                                dwMessage = new DWMessage(messageType , Direct.RECEIVE);
                                dwMessage.setFromDwId(connectorID);
                                dwMessage.setTo(dwID);
                            }
                            else
                            {
                                dwMessage = new DWMessage(messageType , Direct.SEND);
                                dwMessage.setTo(connectorID);
                                dwMessage.setFromDwId(dwID);
                            }
                            dwMessage.setTime(sendTime);
                            dwMessage.setMid(id);
                            dwMessage.setChatType(chatType);
                            dwMessage.setSendSuccess(1);
                            if (messageType == MessageType.TEXT.getIndex())
                            {
                                // 文字消息
                                dwMessage.setBody(ob.getString("message"));
                            }
                            else if (messageType == MessageType.VOICE.getIndex())
                            {
                                // 语音消息
                                String data = ob.getString("message");
                                JSONObject object = JSON.parseObject(data);
                                String uri = object.getString("uri");
                                int length = Integer.valueOf(object.getString("length"));
//                                dwMessage.setUri(uri);
//                                dwMessage.ifFromNet = 0;
                                dwMessage.setRemoteUrl(uri);
                                dwMessage.setVoiceTime(length);
                            }
                            else if (messageType == MessageType.IMAGE.getIndex())
                            {
                                // 图片消息
                                String data = ob.getString("message");
                                JSONObject object = JSON.parseObject(data);
                                String uri = object.getString("uri");
                                String img = object.getString("img");
                                File file = ImageUtils.AnalyticThumbnail(img);
                                dwMessage.setUri(file.getAbsolutePath());
                                dwMessage.setRemoteUrl(uri);
                            }
                            else if (messageType == MessageType.CARD.getIndex())
                            {
                                // 名片消息
                                String data = ob.getString("message");
                                LogUtils.i(TAG, "messageType=3," + "body=" + ob.toJSONString());
                                JSONObject object = JSON.parseObject(data);
                                String forwardName = object.getString("nickName");
                                String forwardDwId = object.getString("dwID");
                                dwMessage.setForwardDwId(forwardDwId);
                                dwMessage.setForwardName(forwardName);
                            }
                            dwMessage.setIsRead(DWMessage.ReadStatus.YES.getIndex());
                            dwMessage.save();
                            temp.add(dwMessage);
                        }
                        getHistoryMsgCallback.onSuccess(temp);
                        break;
                    case 1:
                        // 获取失败，其它原因
                        getHistoryMsgCallback.onFailure(msg.obj.toString());
                        break;
                    default:
                        break;
                }
            };
        };

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("dwID", dwID);
        map.put("toID", toID);
        map.put("firstIndex", String.valueOf(firstIndex));
        map.put("chatType", String.valueOf(chatType));
        LogUtils.v(TAG, "getUserHistoryMsg...begin:" + JSON.toJSONString(map));
        SendUrl sendUrl = new SendUrl(DecentWorldApp.getGlobalContext());
        sendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH + ConstantNet.API_SINGLE_HISTORY_MSG, Method.POST, new HttpCallBack()
        {
            @Override
            public void onSuccess(String response, ResultBean msg)
            {
                LogUtils.v(TAG, "getUserHistoryMsg...response=" + response);
                if (msg.getResultCode() == 2222)
                {
                    sendHandlerMsg(handler, 0, msg.getData().toString());
                }
                if (msg.getResultCode() == 3333)
                {
                    sendHandlerMsg(handler, 1, msg.getMsg());
                }
            }

            @Override
            public void onFailure(String e)
            {
                LogUtils.w(TAG, "getUserHistoryMsg...onFailure,cause by:" + e);
                sendHandlerMsg(handler, -1, "网络错误");
            }
        });
    }

    // ///////////////////////////////////////////转发名片给搜索到的人或者是联系人列表中的人//////////////////////////////////////////////
    private void sendCardToE(String forwardDwId, String forwardName, String otherID, String otherNickname, float otherWorth, int userType, SendCardToListener listener)
    {
        // 获取对方的ID和当前用户的关系
        ChatSingleEngine chatSingleEngine = ChatSingleEngine.getInstance();
        int chatRelationship;
        if (ContactUserDao.isContact(otherID))
        {
            chatRelationship = ChatRelationship.FRIEND.getIndex();
        }
        else
        {
            chatRelationship = ChatRelationship.STRANGER.getIndex();
        }

        // 被转发人的ID和名字
        ContactUser contactUser = new ContactUser();
        contactUser.setFriendID(forwardDwId);
        contactUser.setShowName(forwardName);
        // 查找数据库中最大的mid
        String sql = "userID=? and messageType!=?";
        String userID = DecentWorldApp.getInstance().getDwID();
        List<DWMessage> tempList = new ArrayList<DWMessage>();
        DWMessage midDWMessage = new Select().from(DWMessage.class).where(sql, userID, MessageType.NOTIFY.getIndex()).orderBy("mid desc").executeSingle();
        if (midDWMessage != null)
        {
            tempList.add(midDWMessage);
        }
        DWMessage dwMessage = chatSingleEngine.createCardDwMessage(otherID, ChatType.SINGLE.getIndex(), chatRelationship);
        long maxID = getTempMaxID(tempList);
        try
        {
            chatSingleEngine.sendCardMessage(dwMessage, maxID, contactUser);
            // 刷新会话列表,构建一个UserSessionInfo对象
            UserSessionInfo userSessionInfo = new UserSessionInfo(otherID , otherNickname , otherWorth , userType);
            ConversationEngine.getInstance().refreshConversation(new MsgAndInfo(dwMessage , userSessionInfo), "【发送消息】");
            listener.onSuccess(midDWMessage);
        }
        catch (NotConnectedException e)
        {
            listener.onFailure(Constants.NET_WRONG);
            e.printStackTrace();
        }
    }

    // /////////////////////////////////////////////////私有方法/////////////////////////////////////////
    /**
     * 发送通知
     * 
     * @param handler
     * @param what
     * @param info
     */
    private void sendHandlerMsg(Handler handler, int what, Object info)
    {
        android.os.Message message = android.os.Message.obtain();
        message.what = what;
        message.obj = info;
        handler.sendMessage(message);
    }
}
