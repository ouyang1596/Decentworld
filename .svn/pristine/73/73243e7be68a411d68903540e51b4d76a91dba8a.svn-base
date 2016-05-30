/**
 * 
 */
package cn.sx.decentworld.handleMessage;

import java.io.File;

import org.jivesoftware.smack.packet.Message;
import org.simple.eventbus.EventBus;

import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.common.NotifyByEventBus;
import cn.sx.decentworld.engine.ConversationEngine;
import cn.sx.decentworld.entity.MsgAndInfo;
import cn.sx.decentworld.entity.UserSessionInfo;
import cn.sx.decentworld.entity.dao.ContactUserDao;
import cn.sx.decentworld.entity.dao.DWMessageDao;
import cn.sx.decentworld.entity.db.DWMessage;
import cn.sx.decentworld.entity.db.DWMessage.ChatRelationship;
import cn.sx.decentworld.entity.db.DWMessage.ChatType;
import cn.sx.decentworld.entity.db.DWMessage.Direct;
import cn.sx.decentworld.entity.db.DWMessage.MessageType;
import cn.sx.decentworld.logSystem.LogUtils;
import cn.sx.decentworld.manager.ChatManager;
import cn.sx.decentworld.manager.MsgNotifyManager;
import cn.sx.decentworld.manager.SelfInfoManager;
import cn.sx.decentworld.network.utils.JsonUtils;
import cn.sx.decentworld.utils.ImageUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @ClassName: handleChat
 * @Description: 处理聊天消息
 * @author: Jackchen
 * @date: 2016年5月12日 上午10:02:46
 */
public class HandleChat
{
    private static final String TAG = "handleChat";
    private static HandleChat instance = new HandleChat();

    private HandleChat()
    {
    }

    public static HandleChat getInstance()
    {
        return instance;
    }

    /**
     * 单聊文字
     */
    public void textSingle(Message message)
    {
        try
        {
            LogUtils.v(TAG, "textSingle() " + message.toString());
            JSONObject jsonObject = JSON.parseObject(message.getBody());
            String s_msg = jsonObject.getString("msg");
            String s_userSessionInfo = jsonObject.getString("userSessionInfo");
            // 构造UserSessionInfo
            UserSessionInfo userSessionInfo = (UserSessionInfo) JsonUtils.json2Bean(s_userSessionInfo, UserSessionInfo.class);
            JSONObject msgJson = JSONObject.parseObject(s_userSessionInfo);
            userSessionInfo.setFriendID(msgJson.getString("id"));
            // 构造DWMessage
            DWMessage dwMessage = DWMessage.toDWMessage(s_msg);
            //判断是否消息重复
            if(!isRepetition(dwMessage))
            {
                //4号回执
                sendFourAck(dwMessage.getMid(), dwMessage.getFromDwId(), message.getPacketID());
                dwMessage.setSendSuccess(DWMessage.SendStatus.SUCCESS.getIndex());// 接收成功
                dwMessage.setMessageType(MessageType.TEXT.getIndex());
                dwMessage.setDirect(Direct.RECEIVE.ordinal());
                // 更新消息列表
                ChatManager.getInstance().getMessageCache(dwMessage.getFromDwId(), dwMessage.getChatType()).addMsg(dwMessage);
                MsgAndInfo msgAndInfo = new MsgAndInfo(dwMessage , userSessionInfo);
                //更新会话列表
                ConversationEngine.getInstance().refreshConversation(msgAndInfo, "【单聊文字,发送更新会话列表的通知】");
                // 显示消息通知
                MsgNotifyManager.getInstance().SingleNotify(msgAndInfo);
            }
        }
        catch (Exception e)
        {
            LogUtils.e(TAG, "textSingle() Exception:" + e.toString() + ",message=" + message.toString(), true);
        }
    }

    /**
     * 聊天室文字
     */
    public void textMulti(Message message)
    {
        try
        {
            LogUtils.v(TAG, "textMulti() " + message.toString());
            JSONObject jsonObject = JSON.parseObject(message.getBody());
            String msg = jsonObject.getString("msg");
            String userSessionInfo = jsonObject.getString("userSessionInfo");
            String dwID = DecentWorldApp.getInstance().getDwID();
            DWMessage dwMessage = DWMessage.toDWMessage(msg);
            if (dwMessage.getChatType() == ChatType.MULTI.getIndex())
            {
                if (!dwMessage.getFromDwId().equals(dwID))
                {
                    // 别人发送过来的消息
                    dwMessage.setSendSuccess(DWMessage.SendStatus.SUCCESS.getIndex());
                    dwMessage.setDirect(DWMessage.Direct.RECEIVE.ordinal());
                    dwMessage.save();
                    EventBus.getDefault().post(dwMessage, NotifyByEventBus.NT_CHAT_ROOM_MSG);
                    // 聊天室文字消息通知
                    MsgNotifyManager.getInstance().MultiNotify(dwMessage);
                }
            }
        }
        catch (Exception e)
        {
            LogUtils.e(TAG, "textMulti() Exception:" + e.toString() + ",message=" + message.toString(), true);
        }
    }
    
    /**
     * 语音
     */
    public void voice(Message message)
    {
        try
        {
            LogUtils.v(TAG, "voice() " + message.toString());
            JSONObject jsonObject = JSON.parseObject(message.getBody());
            String s_msg = jsonObject.getString("msg");
            String s_userSessionInfo = jsonObject.getString("userSessionInfo");
            UserSessionInfo userSessionInfo = (UserSessionInfo) JsonUtils.json2Bean(s_userSessionInfo, UserSessionInfo.class);
            JSONObject jsonUser = JSONObject.parseObject(s_userSessionInfo);
            userSessionInfo.setFriendID(jsonUser.getString("id"));
            /** 解析所需参数 **/
            JSONObject jsonMsg = JSONObject.parseObject(s_msg);
            String chatType = jsonMsg.getString("chatType");
            if (Integer.valueOf(chatType) == ChatType.MULTI.getIndex())
            {
                // 聊天室的dwID
                String fromDwID = jsonMsg.getString("fromID");
                String myDwID = DecentWorldApp.getInstance().getDwID();
                LogUtils.v(TAG, "voice() fromDwID=" + fromDwID + ",myID=" + myDwID);
                String roomID = message.getFrom().split("@")[0];
                if (!myDwID.equals(fromDwID))
                {
                    LogUtils.v(TAG, "voice() 接收到一条聊天室语音消息:" + jsonMsg.toJSONString());
                    Float length = jsonMsg.getFloat("length");
                    String url = jsonMsg.getString("uri");
                    String wealth = jsonMsg.getString("wealth");
                    long id = jsonMsg.getLongValue("id");
                    final DWMessage dwMessage = new DWMessage(MessageType.VOICE.getIndex() , DWMessage.Direct.RECEIVE);
                    dwMessage.setFromDwId(fromDwID);
                    dwMessage.setTo(roomID);
                    dwMessage.setWealth(wealth);
                    dwMessage.setChatType(Integer.valueOf(chatType));
//                    dwMessage.setUri(url);
//                    dwMessage.ifFromNet = 0;
                    dwMessage.setRemoteUrl(url);
                    dwMessage.setVoiceTime(length);
                    dwMessage.setSendSuccess(DWMessage.SendStatus.SUCCESS.getIndex());
                    dwMessage.setTxtMsgID(message.getPacketID());
                    dwMessage.setMid(id);
                    dwMessage.save();
                    EventBus.getDefault().post(dwMessage, NotifyByEventBus.NT_RECEIVE_CHATROOM_AUDIO);
                    // 聊天室语音消息通知
                    MsgNotifyManager.getInstance().MultiNotify(dwMessage);
                }
            }
            else
            {
                LogUtils.v(TAG, "voice() 接收到一条单聊语音消息:" + jsonMsg.toJSONString());
                long mid = jsonMsg.getLongValue("id");
                if(!isRepetition(mid))
                {
                    Float length = jsonMsg.getFloat("length");
                    String url = jsonMsg.getString("uri");
                    String wealth = jsonMsg.getString("wealth");
                    String fromDwID = jsonMsg.getString("fromID");
                    String chatRelationship = jsonMsg.getString("chatRelationship");
                    String currentStatus = jsonMsg.getString("currentStatus");
                    String nextStatus = jsonMsg.getString("nextStatus");
                    
                    final DWMessage dwMessage = new DWMessage(MessageType.VOICE.getIndex() , DWMessage.Direct.RECEIVE);
                    dwMessage.setFromDwId(fromDwID);
                    dwMessage.setWealth(wealth);
                    dwMessage.setChatType(Integer.valueOf(chatType));
                    //判断好友关系 
                    if (ContactUserDao.isContact(fromDwID))
                        dwMessage.setChatRelationship(ChatRelationship.FRIEND.getIndex());
                    else
                        dwMessage.setChatRelationship(ChatRelationship.STRANGER.getIndex());
//                    dwMessage.ifFromNet = 0;
//                    dwMessage.setUri(url);
                    dwMessage.setRemoteUrl(url);
                    dwMessage.setVoiceTime(length);
                    dwMessage.setSendSuccess(DWMessage.SendStatus.SUCCESS.getIndex());
                    dwMessage.setTxtMsgID(message.getPacketID());
                    dwMessage.setMid(mid);
                    dwMessage.setCurrentStatus(currentStatus);
                    dwMessage.setNextStatus(nextStatus);
                    sendFourAck(dwMessage.getMid(), dwMessage.getFromDwId(), message.getPacketID());
                    ChatManager.getInstance().getMessageCache(dwMessage.getFromDwId(), dwMessage.getChatType()).addMsg(dwMessage);
                    MsgAndInfo msgAndInfo = new MsgAndInfo(dwMessage , userSessionInfo);
                    ConversationEngine.getInstance().refreshConversation(msgAndInfo, "【单聊语音,发送更新会话列表的通知】");
                    MsgNotifyManager.getInstance().SingleNotify(msgAndInfo);
                }
            }
        }
        catch (Exception e)
        {
            LogUtils.e(TAG, "voice() Exception:" + e.toString() + ",message=" + message.toString(), true);
        }
    }

    
    /**
     * 图片
     */
    public void image(Message message)
    {
        try
        {
            LogUtils.v(TAG, "image() " + message.toString());
            JSONObject jsonObject = JSON.parseObject(message.getBody());
            String s_msg = jsonObject.getString("msg");
            String s_userSessionInfo = jsonObject.getString("userSessionInfo");
            UserSessionInfo userSessionInfo = (UserSessionInfo) JsonUtils.json2Bean(s_userSessionInfo, UserSessionInfo.class);
            JSONObject jsonObject2 = JSONObject.parseObject(s_userSessionInfo);
            userSessionInfo.setFriendID(jsonObject2.getString("id"));
            // 接收图片
            JSONObject jsonMsg = JSONObject.parseObject(s_msg);
            String chatType = jsonMsg.getString("chatType");
            
            if (Integer.valueOf(chatType) == ChatType.MULTI.getIndex())
            {
                LogUtils.v(TAG, "image() 接收到一条聊天室图片消息：" + jsonMsg.toJSONString());
                // 发图片的人的dwID
                String fromDwID = jsonMsg.getString("fromID");
                String myDwID = DecentWorldApp.getInstance().getDwID();
                if (!myDwID.equals(fromDwID))
                {
                    String img = jsonMsg.getString("img");
                    String uri = jsonMsg.getString("uri");
                    String wealth = jsonMsg.getString("wealth");
                    long id = jsonMsg.getLongValue("id");
                    String roomID = message.getFrom().split("@")[0];
                    final DWMessage dwMessage = new DWMessage(MessageType.IMAGE.getIndex() , DWMessage.Direct.RECEIVE);
                    dwMessage.setFromDwId(fromDwID);
                    dwMessage.setTo(roomID);
                    dwMessage.setWealth(wealth);
                    dwMessage.setChatType(Integer.valueOf(chatType));
                    File file = ImageUtils.AnalyticThumbnail(img);
                    dwMessage.setUri(file.getAbsolutePath());
                    dwMessage.setRemoteUrl(uri);
                    dwMessage.setSendSuccess(DWMessage.SendStatus.SUCCESS.getIndex());
                    dwMessage.setTxtMsgID(message.getPacketID());
                    dwMessage.setMid(id);
                    dwMessage.save();

                    EventBus.getDefault().post(dwMessage, NotifyByEventBus.NT_CHAT_ROOM_MSG);
                    // 聊天室图片消息通知
                    MsgNotifyManager.getInstance().MultiNotify(dwMessage);
                }
            }
            else
            {
                LogUtils.v(TAG, "image() 接收到一条单聊图片消息");
                String img = jsonMsg.getString("img");
                String uri = jsonMsg.getString("uri");
                String wealth = jsonMsg.getString("wealth");
                long mid = jsonMsg.getLongValue("id");
                String fromDwId = message.getFrom().split("@")[0];
                String chatRelationship = jsonMsg.getString("chatRelationship");
                String currentStatus = jsonMsg.getString("currentStatus");
                String nextStatus = jsonMsg.getString("nextStatus");
                if(!isRepetition(mid))
                {
                    // 构造DWMessage
                    DWMessage dwMessage = new DWMessage(MessageType.IMAGE.getIndex() , DWMessage.Direct.RECEIVE);
                    dwMessage.setFromDwId(fromDwId);
                    dwMessage.setWealth(wealth);
                    dwMessage.setChatType(Integer.valueOf(chatType));
                    /** 判断好友关系 **/
                    if (ContactUserDao.isContact(fromDwId))
                        dwMessage.setChatRelationship(ChatRelationship.FRIEND.getIndex());
                    else
                        dwMessage.setChatRelationship(ChatRelationship.STRANGER.getIndex());
                    File file = ImageUtils.AnalyticThumbnail(img);
                    dwMessage.setUri(file.getAbsolutePath());
                    dwMessage.setRemoteUrl(uri);
                    dwMessage.setSendSuccess(DWMessage.SendStatus.SUCCESS.getIndex());
                    dwMessage.setTxtMsgID(message.getPacketID());
                    dwMessage.setMid(mid);
                    dwMessage.setCurrentStatus(currentStatus);
                    dwMessage.setNextStatus(nextStatus);
                    //
                    sendFourAck(dwMessage.getMid(), dwMessage.getFromDwId(), message.getPacketID());
                    ChatManager.getInstance().getMessageCache(dwMessage.getFromDwId(), dwMessage.getChatType()).addMsg(dwMessage);
                    MsgAndInfo msgAndInfo = new MsgAndInfo(dwMessage , userSessionInfo);
                    ConversationEngine.getInstance().refreshConversation(msgAndInfo, "【单聊图片,发送更新会话列表的通知】");
                    MsgNotifyManager.getInstance().SingleNotify(msgAndInfo);
                }
            }
        }
        catch (Exception e)
        {
            LogUtils.e(TAG, "image() Exception:" + e.toString() + ",message=" + message.toString(), true);
        }
    }

  


    /**
     * 单聊名片
     */
    public void cardSingle(Message message)
    {
        try
        {
            LogUtils.v(TAG, "cardSingle() " + message.toString());
            JSONObject jsonObject = JSON.parseObject(message.getBody());
            String s_msg = jsonObject.getString("msg");
            String s_userSessionInfo = jsonObject.getString("userSessionInfo");
            UserSessionInfo userSessionInfo = (UserSessionInfo) JsonUtils.json2Bean(s_userSessionInfo, UserSessionInfo.class);
            JSONObject jsonObject2 = JSONObject.parseObject(s_userSessionInfo);
            userSessionInfo.setFriendID(jsonObject2.getString("id"));
            DWMessage dwMessage = DWMessage.toDWMessage(s_msg);
            if(!isRepetition(dwMessage))
            {
                dwMessage.setSendSuccess(DWMessage.SendStatus.SUCCESS.getIndex());
                dwMessage.setDirect(Direct.RECEIVE.ordinal());
                dwMessage.setTxtMsgID(message.getPacketID());
                
                sendFourAck(dwMessage.getMid(), dwMessage.getFromDwId(), message.getPacketID());
                ChatManager.getInstance().getMessageCache(dwMessage.getFromDwId(), dwMessage.getChatType()).addMsg(dwMessage);
                MsgAndInfo msgAndInfo = new MsgAndInfo(dwMessage , userSessionInfo);
                ConversationEngine.getInstance().refreshConversation(msgAndInfo, "【单聊名片,发送更新会话列表的通知】");
                MsgNotifyManager.getInstance().SingleNotify(msgAndInfo);
            }
        }
        catch (Exception e)
        {
            LogUtils.e(TAG, "cardSingle() Exception:" + e.toString() + ",message=" + message.toString(), true);
        }
    }

    /**
     * 发送4号回执
     */
    private void sendFourAck(long mid, String fromDwID, String packetID)
    {
        ComfirmSystem.getInstance().sendFourAck(mid, fromDwID, packetID);
    }
    
    /**
     * 是否重复
     * @return true(重复) | false(没有重复)
     */
    private boolean isRepetition(DWMessage dwMessage)
    {
        DWMessage temp = DWMessageDao.query(dwMessage.getTxtMsgID());
        if(temp!=null)
        {
            LogUtils.e(TAG, "textSingle() temp!=null,dwMessage=" + dwMessage.toString()+"\ntemp="+temp.toString());
            return true;
        }
        DWMessage temp2 = DWMessageDao.query(dwMessage.getMid());
        if(temp2!=null)
        {
            LogUtils.e(TAG, "textSingle() temp2!=null," + dwMessage.toString()+"\ntemp2="+temp2.toString());
            return true;
        }
        return false;
    }
    
    /**
     * 是否重复
     * @return true(重复) | false(没有重复)
     */
    private boolean isRepetition(long mid)
    {
        DWMessage temp = DWMessageDao.query(mid);
        if(temp!=null)
            return true;
        return false;
    }
}
