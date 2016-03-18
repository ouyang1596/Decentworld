/**
 * 
 */
package cn.sx.decentworld.task;

import java.io.File;

import org.jivesoftware.smack.packet.Message;
import org.simple.eventbus.EventBus;

import android.content.Context;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.bean.ContactUser;
import cn.sx.decentworld.bean.DWMessage;
import cn.sx.decentworld.bean.MsgAndInfo;
import cn.sx.decentworld.bean.NotifyByEventBus;
import cn.sx.decentworld.bean.UserSessionInfo;
import cn.sx.decentworld.manager.MsgNotifyManager;
import cn.sx.decentworld.network.utils.JsonUtils;
import cn.sx.decentworld.utils.ImageUtils;
import cn.sx.decentworld.utils.LogUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @ClassName: ProcessPictureThread.java
 * @Description:
 * @author: cj
 * @date: 2015年11月28日 下午6:34:19
 */
public class ProcessPictureThread extends DWPacketHandler
{

    public static final String TAG = "ProcessPictureThread";

    public ProcessPictureThread(Message msg, Context context)
    {
        super(msg, context);
    }

    /**
	 * 
	 */
    public ProcessPictureThread()
    {

    }

    @Override
    public void run()
    {
        Message message;
        String s_msg = null;
        UserSessionInfo userSessionInfo;
        try
        {
            message = (Message) packet;
            JSONObject jsonObject = JSON.parseObject(message.getBody());
            LogUtils.i(TAG, "message.getBody=" + message.getBody());
            s_msg = jsonObject.getString("msg");
            String s_userSessionInfo = jsonObject.getString("userSessionInfo");

            LogUtils.i(TAG, "s_userSessionInfo=" + s_userSessionInfo);
            userSessionInfo = (UserSessionInfo) JsonUtils.json2Bean(s_userSessionInfo, UserSessionInfo.class);
            /** 添加 FriendID **/
            JSONObject jsonObject2 = JSONObject.parseObject(s_userSessionInfo);
            userSessionInfo.setFriendID(jsonObject2.getString("id"));
        }
        catch (Exception e)
        {
            LogUtils.e(TAG, "解析图片消息异常,cause by:" + e);
            e.printStackTrace();
            return;
        }

        LogUtils.i(TAG, "监听到一张图片消息：" + s_msg);
        // 接收图片
        JSONObject jsono = JSONObject.parseObject(s_msg);
        String chatType = jsono.getString("chatType");
        if (Integer.valueOf(chatType) == DWMessage.CHAT_TYPE_MULTI)
        {
            LogUtils.i(TAG, "监听到一条聊天室的图片消息");
            // 发图片的人的dwID
            String fromDwID = jsono.getString("fromID");
            String myDwID = DecentWorldApp.getInstance().getDwID();
            LogUtils.i(TAG, "fromDwID=" + fromDwID + ",myID=" + myDwID);
            if (!myDwID.equals(fromDwID))
            {
                String img = jsono.getString("img");
                String uri = jsono.getString("uri");
                String wealth = jsono.getString("wealth");
                long id = jsono.getLongValue("id");
                String roomID = message.getFrom().split("@")[0];
                final DWMessage dwMessage = new DWMessage(DWMessage.IMAGE , DWMessage.RECEIVE);
                dwMessage.setFrom(fromDwID);
                dwMessage.setTo(roomID);
                dwMessage.setWealth(wealth);
                dwMessage.setChatType(Integer.valueOf(chatType));
                File file = ImageUtils.AnalyticThumbnail(img);
                dwMessage.setUri(file.getAbsolutePath());
                dwMessage.setRemoteUrl(uri);
                dwMessage.setSendSuccess(1);
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
            LogUtils.i(TAG, "监听到一条单聊的图片消息");
            String img = jsono.getString("img");
            String uri = jsono.getString("uri");
            String wealth = jsono.getString("wealth");
            long id = jsono.getLongValue("id");
            String fromDwId = message.getFrom().split("@")[0];
            // 构造DWMessage
            final DWMessage dwMessage = new DWMessage(DWMessage.IMAGE , DWMessage.RECEIVE);
            dwMessage.setFrom(fromDwId);
            dwMessage.setWealth(wealth);
            dwMessage.setChatType(Integer.valueOf(chatType));
            /** 判断好友关系 **/
            if (ContactUser.isContact(fromDwId))
                dwMessage.setChatRelationship(DWMessage.CHAT_RELATIONSHIP_FRIEND);
            else
                dwMessage.setChatRelationship(DWMessage.CHAT_RELATIONSHIP_STRANGER);
            File file = ImageUtils.AnalyticThumbnail(img);
            dwMessage.setUri(file.getAbsolutePath());
            dwMessage.setRemoteUrl(uri);
            dwMessage.setSendSuccess(1);
            dwMessage.setTxtMsgID(message.getPacketID());
            dwMessage.setMid(id);
            dwMessage.save();
            processMsg(new MsgAndInfo(dwMessage , userSessionInfo));
        }
    }

    private void processMsg(MsgAndInfo msgAndInfo)
    {
        DWMessage dwMessage = msgAndInfo.getDwMessage();
        if (dwMessage.getChatType() != DWMessage.CHAT_TYPE_MULTI)
        {
            // 朋友间单聊
            if (dwMessage.getChatRelationship() == DWMessage.CHAT_RELATIONSHIP_FRIEND)
            {
                LogUtils.i(TAG, "消息类型为单聊,发送更新消息列表的通知");
                EventBus.getDefault().post(msgAndInfo, NotifyByEventBus.NT_REFRESH_CONVERSATION);
            }
            if (dwMessage.getChatRelationship() == DWMessage.CHAT_RELATIONSHIP_STRANGER)
            {
                EventBus.getDefault().post(msgAndInfo, NotifyByEventBus.NT_REFRESH_STRANGER_CONVERSATION);
            }
            // 消息通知
            MsgNotifyManager.getInstance().SingleNotify(msgAndInfo);
            EventBus.getDefault().post(dwMessage, NotifyByEventBus.NT_UPDATE_CHAT_LISTVIEW_RECEIVE_MSG);
        }
    }
}
