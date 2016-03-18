/**
 * 
 */
package cn.sx.decentworld.task;

import org.jivesoftware.smack.packet.Message;
import org.simple.eventbus.EventBus;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import cn.sx.decentworld.R;
import cn.sx.decentworld.activity.ExamineActivity_;
import cn.sx.decentworld.bean.NotifyByEventBus;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.utils.LogUtils;

/**
 * @ClassName: ProcessErrorMessageThread.java
 * @Description:
 * @author: oyyj
 * @date: 2016年1月24日
 */
public class ProcessMatchSuccessThread extends DWPacketHandler {

	public ProcessMatchSuccessThread(Message msg, Context context) {
		super(msg, context);
	}

	public ProcessMatchSuccessThread() {

	}

	@Override
	public void run() {
		Message message = (Message) packet;
		LogUtils.i("bm", "body--" + message.getBody());
		if (message.getSubject().equals("match")) {
			EventBus.getDefault().post(message.getBody(), NotifyByEventBus.NT_MATCH);
		}
	}

	private void notifyMe(String amount, String sex, String dwID, String name) {
		// 获得通知管理器，通知是一项系统服务
		NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		// 初始化通知对象 p1:通知的图标 p2:通知的状态栏显示的提示 p3:通知显示的时间
		Notification notification = new Notification(R.drawable.ic_launcher, "通知", System.currentTimeMillis());
		// 点击通知后的Intent，此例子点击后还是在当前界面
		Intent data = new Intent(context, ExamineActivity_.class);
		data.putExtra("sex", sex);
		data.putExtra("dwID", dwID);
		data.putExtra("name", name);
		data.putExtra("amount", amount);
		PendingIntent intent = PendingIntent.getActivity(context, 0, data, 0);
		// 设置通知信息
		notification.setLatestEventInfo(context, "您有一条消息", "有一位用户需要经过您的审核", intent);
		// 通知
		manager.notify(Constants.NOTICE_ID, notification);
	}
}
