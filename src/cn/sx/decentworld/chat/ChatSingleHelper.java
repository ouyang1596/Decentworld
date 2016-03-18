/**
 * 
 */
package cn.sx.decentworld.chat;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.packet.Message;

import android.os.Handler;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.bean.DWMessage;
import cn.sx.decentworld.bean.manager.DWSMessageManager;
import cn.sx.decentworld.chat.interfaces.SendFileListener;
import cn.sx.decentworld.chat.interfaces.SendPictureListener;
import cn.sx.decentworld.chat.interfaces.SendVoiceListener;
import cn.sx.decentworld.common.CommUtil;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.common.XmppHelper;
import cn.sx.decentworld.network.SendUrl;
import cn.sx.decentworld.network.SendUrl.HttpCallBack;
import cn.sx.decentworld.network.entity.ResultBean;
import cn.sx.decentworld.utils.AES;
import cn.sx.decentworld.utils.LogUtils;
import cn.sx.decentworld.utils.SPUtils;

/**
 * @ClassName: ChatSingleHelper.java
 * @Description: 单聊辅助类
 * @author: cj
 * @date: 2016年3月7日 上午11:21:19
 */
public class ChatSingleHelper
{
    public static final String TAG = "ChatSingleHelper";
    public static Object LOCK = new Object();
    public static ChatSingleHelper instance = null;

    /**
     * 私有构造函数，防止产生多个实例
     */
    private ChatSingleHelper()
    {
        // TODO Auto-generated constructor stub
    }

    // ///////////////////////////////初始化//////////////////////////////////////

    /**
     * 获取单聊管理者实例
     * 
     * @return
     */
    public static ChatSingleHelper getInstance()
    {
        if (instance == null)
        {
            synchronized (LOCK)
            {
                if (instance == null)
                    instance = new ChatSingleHelper();
            }
        }
        return instance;
    }

    // ///////////////////////////////私有函数//////////////////////////////////////

    /**
     * 创建一个XMPPMessage
     * 
     * @param otherID
     *            对方的ID
     * @return
     */
    private Message createXmppMessage(String otherID)
    {
        Message message = new Message(otherID , Message.Type.chat);
        message.setFrom(DecentWorldApp.getInstance().getDwID());
        message.setSubject("chat");
        return message;
    }

    /**
     * 获得临时的mid
     * 
     * @return
     */
    private long getTempMid(List<DWMessage> listMsg)
    {
        long tempMid;
        if (listMsg.size() > 0)
        {
            tempMid = listMsg.get(listMsg.size() - 1).getMid();
        }
        else
        {
            tempMid = 0;
        }
        LogUtils.i(TAG, "临时的mid=" + tempMid);
        return tempMid;
    }

    /**
     * 获取聊天管理者
     * 
     * @return
     */
    private ChatManager creatChatManager()
    {
        XMPPConnection connection = XmppHelper.getConnection();
        if (connection.isConnected() && connection.isAuthenticated())
        {
            if (LogUtils.IS_TOAST_TEST_CONN)
            {
                LogUtils.i(TAG, "已经连接和授权");
            }
        }
        else if (connection.isConnected() && !connection.isAuthenticated())
        {
            if (LogUtils.IS_TOAST_TEST_CONN)
            {
                LogUtils.i(TAG, "已经授权/没有授权");
            }
        }
        return ChatManager.getInstanceFor(connection);
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
        String randomStr = (String) SPUtils.get(DecentWorldApp.getInstance().getApplicationContext(), SPUtils.randomStr, "");
        String token = AES.encode(DecentWorldApp.getInstance().getDwID() + time, randomStr);
        dwMessage.setToken(token);
    }

    // ///////////////////////////////发送文本消息//////////////////////////////////////

    /**
     * 构造一个DWMessage消息对象
     * 
     * @param otherID
     * @param chatType
     * @param chatRelationship
     * @param textContent
     * @return
     */
    public DWMessage createTextDwMessage(String otherID, int chatType, int chatRelationship, String textContent)
    {
        DWMessage dwMessage = new DWMessage(DWMessage.TXT , DWMessage.SEND);
        dwMessage.setTo(otherID);
        dwMessage.setChatType(chatType);
        dwMessage.setChatRelationship(chatRelationship);
        dwMessage.setBody(textContent);
        return dwMessage;
    }

    /**
     * 将消息发送出去
     * 
     * @param dwMessage
     * @param listMsg
     * @throws NotConnectedException
     */
    public void sendTextMessage(DWMessage dwMessage, List<DWMessage> listMsg) throws NotConnectedException
    {
        // 构造Message
        Message message = createXmppMessage(dwMessage.getTo());
        if (CommUtil.isNotBlank(dwMessage.getTxtMsgID()))
        {
            message.setPacketID(dwMessage.getTxtMsgID());
            dwMessage.setSendSuccess(2);
        }

        dwMessage.setTxtMsgID(message.getPacketID());
        dwMessage.setMid(getTempMid(listMsg));

        Chat chat = creatChatManager().createChat(dwMessage.getTo() + Constants.SERVER_NAME, null);
//        Chat chat = creatChatManager().createChat(dwMessage.getTo(), null);
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

    // ///////////////////////////////发送语音信息//////////////////////////////////
    /**
     * 构造一个DWMessage消息对象，在发送一条新的消息时需要调用，重发则不需要调用
     * 
     * @param otherID
     * @param chatType
     * @param chatRelationship
     * @return
     */
    public DWMessage createVoiceDwMessage(String otherID, int chatType, int chatRelationship)
    {
        DWMessage dwMessage = new DWMessage(DWMessage.VOICE , DWMessage.SEND);
        dwMessage.setTo(otherID);
        dwMessage.setChatType(chatType);
        dwMessage.setChatRelationship(chatRelationship);
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
    public void sendVoiceMessage(DWMessage dwMessage, List<DWMessage> listMsg, File voiceFile, float voiceTime, SendVoiceListener listener)
    {
        Message message = new Message();
        dwMessage.setTxtMsgID(message.getPacketID());
        dwMessage.setMid(getTempMid(listMsg));
        dwMessage.setUri(voiceFile.getAbsolutePath());
        dwMessage.setVoiceTime(voiceTime);
        dwMessage.ifFromNet = 1;
        // 回调
        listener.onStart();
        if (!XmppHelper.getConnection().isConnected())
        {
            // 出现错误，出现没有连接服务器异常
            listener.onNotConnected();
            return;
        }
        // 发送语音
        sendFileNet(dwMessage, voiceFile, voiceTime, "/sendAudio", listener);
    }

    /**
     * 重新发送语音
     * 
     * @param dwMessage
     * @param listMsg
     * @param listener
     */
    public void reSendVoiceMessage(DWMessage dwMessage, List<DWMessage> listMsg, SendVoiceListener listener)
    {
        // 将该条消息从列表中移除
        listMsg.remove(dwMessage);
        // 修改DWMessage
        dwMessage.setSendSuccess(2);
        dwMessage.setMid(getTempMid(listMsg));
        dwMessage.save();
        File voiceFile = new File(dwMessage.getUri());
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
        sendFileNet(dwMessage, voiceFile, voiceTime, "/sendAudio", listener);
    }

    // ///////////////////////////////发送图片信息//////////////////////////////////

    /**
     * 构造一个DWMessage消息对象，在发送一条新的消息时需要调用，重发则不需要调用
     * 
     * @param otherID
     * @param chatType
     * @param chatRelationship
     * @return
     */
    public DWMessage createPictureDwMessage(String otherID, int chatType, int chatRelationship)
    {
        DWMessage dwMessage = new DWMessage(DWMessage.IMAGE , DWMessage.SEND);
        dwMessage.setTo(otherID);
        dwMessage.setChatType(chatType);
        dwMessage.setChatRelationship(chatRelationship);
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
    public void sendPictureMessage(DWMessage dwMessage, List<DWMessage> listMsg, File picture, SendPictureListener listener)
    {
        // 构造Message
        Message message = new Message();
        dwMessage.setTxtMsgID(message.getPacketID());
        dwMessage.setMid(getTempMid(listMsg));
        dwMessage.setUri(picture.getAbsolutePath());
        dwMessage.setLocalUrl(picture.getAbsolutePath());
        // 回调
        listener.onStart();
        if (!XmppHelper.getConnection().isConnected())
        {
            // 出现错误，出现没有连接服务器异常
            listener.onNotConnected();
            return;
        }
        // 发送图片
        sendFileNet(dwMessage, picture, -1, "/sendPicture", listener);
    }
    
    /**
     * 重新发送图片
     * @param dwMessage
     * @param listMsg
     * @param listener
     */
    public void reSendPictureMessage(DWMessage dwMessage, List<DWMessage> listMsg, SendPictureListener listener)
    {
        // 将该条消息从列表中移除
        listMsg.remove(dwMessage);
        // 修改DWMessage
        dwMessage.setSendSuccess(2);
        dwMessage.setMid(getTempMid(listMsg));
        dwMessage.save();
        File pictureFile = new File(dwMessage.getUri());
        // 回调
        listener.onStart();
        if (!XmppHelper.getConnection().isConnected())
        {
            // 出现错误，出现没有连接服务器异常
            listener.onNotConnected();
            return;
        }
        // 发送图片
        sendFileNet(dwMessage, pictureFile, -1, "/sendPicture", listener);
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
    private void sendFileNet(DWMessage dwMessage, File file, float fileTime, String api, final SendFileListener listener)
    {
        final String packetID = dwMessage.getTxtMsgID();
        HashMap<String, String> hashmap = new HashMap<String, String>();
        hashmap.put("dwID", dwMessage.getFrom());
        hashmap.put("toID", dwMessage.getTo());
        hashmap.put("type", dwMessage.getChatType() + "");
        hashmap.put("msgID", dwMessage.getTxtMsgID());
        if (fileTime != -1)
        {
            hashmap.put("length", Math.round(fileTime) + "");
        }

        // 图片文件
        File[] images = new File[]
        { file };

        /** token = dwID + randomStr +key **/
        String dwID = hashmap.get("dwID");
        long time = System.currentTimeMillis();
        String randomStr = (String) SPUtils.get(DecentWorldApp.getInstance().getApplicationContext(), SPUtils.randomStr, "");
        String token = AES.encode(dwID + time, randomStr);
        hashmap.put("randomStr", String.valueOf(time));
        hashmap.put("token", token);

        SendUrl sendUrl = new SendUrl(DecentWorldApp.getInstance().getApplicationContext());
        sendUrl.httpRequestWithImage(hashmap, images, Constants.CONTEXTPATH_OPENFIRE + api, new HttpCallBack()
        {
            @Override
            public void onSuccess(String response, ResultBean msg)
            {
                LogUtils.i(TAG, "sendFileWithParams...begin,msg.getResultCode=" + msg.getResultCode() + ",msg.getMsg=" + msg.getMsg() + ",msg.getData=" + msg.getData().toString());
                if (msg.getResultCode() == 2222)
                {
                    LogUtils.i(TAG, "sendFileWithParams...success");
                    listener.onSuccess();
                }
                else
                {
                    LogUtils.i(TAG, "sendFileWithParams...failure,cause by:" + msg.getMsg());
                    listener.onFailure(packetID, msg.getMsg());
                }
            }

            @Override
            public void onFailure(String e)
            {
                listener.onNetWrong();

            }
        });
    }

}
