/**
 * 
 */
package cn.sx.decentworld.task;
import org.jivesoftware.smack.packet.Message;
import org.simple.eventbus.EventBus;

import android.content.Context;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.bean.DWMessage;
import cn.sx.decentworld.bean.NotifyByEventBus;
import cn.sx.decentworld.logSystem.LogUtils;
import cn.sx.decentworld.manager.MsgNotifyManager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @ClassName: ProcessRoomPresenceThread.java
 * @Description:
 * @author: cj
 * @date: 2015年11月28日 下午6:13:46
 */
public class ProcessRoomMessageThread extends DWPacketHandler
{
	public static final String TAG = "ProcessRoomMessageThread";

	public ProcessRoomMessageThread(Message msg, Context context)
	{
		super(msg, context);
	}

	/**
	 * 
	 */
	public ProcessRoomMessageThread()
	{
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run()
	{
		Message message = (Message) packet;
		String subject = message.getSubject();
		if ("room_leave".equals(subject))
		{
			String str = message.getBody();
			LogUtils.i(TAG, "room_leave=" + str);
			EventBus.getDefault().post(message, NotifyByEventBus.NT_CHAT_ROOM_LEAVE);
		}
		else if ("room_presence".equals(subject))
		{
			String str = message.getBody();
			LogUtils.i(TAG, "room_presence=" + str);
			EventBus.getDefault().post(message, NotifyByEventBus.NT_CHAT_ROOM_JOIN);
		}
		else if ("roomMSG".equals(subject))
		{
		    String msg;
            try
            {
                LogUtils.i(TAG, "message.getBody="+message.getBody());
                JSONObject jsonObject=JSON.parseObject(message.getBody());
                msg = jsonObject.getString("msg");
                String userSessionInfo=jsonObject.getString("userSessionInfo");
            }
            catch (Exception e)
            {
                LogUtils.e(TAG, "解析聊天室消息异常,cause by:"+e);
                e.printStackTrace();
                return;
            }
	        
	        String body = msg;
			final String dwID = DecentWorldApp.getInstance().getDwID();
			DWMessage dwMessage = DWMessage.toDWMessage(body);
			if (dwMessage.getChatType() == DWMessage.CHAT_TYPE_MULTI)
			{
				LogUtils.i(TAG, "接受一条聊天室的文字消息：" + body);
				if (!dwMessage.getFrom().equals(dwID))
				{
					/** 别人发送过来的消息 **/
					dwMessage.setSendSuccess(1);// 接收成功
					dwMessage.setDirect(DWMessage.Direct.RECEIVE.getIndex());
					dwMessage.save();
					EventBus.getDefault().post(dwMessage, NotifyByEventBus.NT_CHAT_ROOM_MSG);
					/** 聊天室文字消息通知 **/
					MsgNotifyManager.getInstance().MultiNotify(dwMessage);
				}
			}
		}
	}
}
