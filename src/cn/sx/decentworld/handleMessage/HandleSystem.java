/**
 * 
 */
package cn.sx.decentworld.handleMessage;

import org.jivesoftware.smack.packet.Message;
import org.simple.eventbus.EventBus;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.sx.decentworld.R;
import cn.sx.decentworld.activity.ExamineActivity_;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.common.NotifyByEventBus;
import cn.sx.decentworld.entity.dao.ContactUserDao;
import cn.sx.decentworld.entity.db.ContactUser;
import cn.sx.decentworld.logSystem.LogUtils;
import cn.sx.decentworld.manager.MsgNotifyManager;
import cn.sx.decentworld.utils.SoundPoolUtils;

/**
 * @ClassName: HandleSystem
 * @Description: TODO (在这里用一句话描述类的作用)
 * @author: Jackchen
 * @date: 2016年5月12日 上午10:35:16
 */
public class HandleSystem
{
    private static final String TAG = "HandleSystem";
    private static HandleSystem instance = new HandleSystem();

    private HandleSystem()
    {
        // 防止产生多个实例对象
    }

    /**
     * 获取单例
     */
    public static HandleSystem getInstance()
    {
        return instance;
    }
    
    /**
     * 有新的收益
     */
    public void newBenefit(Message message)
    {
        LogUtils.v(TAG, "newBenefit() " + message.toString());
        // 消息通知
        MsgNotifyManager.getInstance().newBenefit(message.getBody().toString());
    }
    
    /**
     * 身价改变通知
     */
    public void worthChanged(Message message)
    {
        LogUtils.v(TAG, "worthChanged() " + message.toString());
        JSONObject jsonObject = JSON.parseObject(message.getBody());
        String dwID = jsonObject.getString("dwID");
        String worth = jsonObject.getString("worth");
        // 如果时好友，修改联系人列表中的worth字段
        ContactUser user = ContactUserDao.query(dwID);
        if (user != null)
        {
            user.setWorth(Float.valueOf(worth));
            user.save();
        }
        // 消息路由到ChatActivity中；
        EventBus.getDefault().post(message, NotifyByEventBus.NT_SYSTEM_PUSH_WORTH);
        // 消息通知
        MsgNotifyManager.getInstance().otherWorthChanged(dwID, worth);
    }
    
    /**
     * 向审核者推送去疑
     */
    public void check(Message message)
    {
        LogUtils.v(TAG, "check() " + message.toString());
        // AppearanceBean bean = JsonUtils.json2Bean(message.getBody(),
        // AppearanceBean.class);
        // notifyMe(bean.amount, bean.sex, bean.dwID, bean.name);
        SoundPoolUtils.play();
        EventBus.getDefault().post(message.getBody(), NotifyByEventBus.NT_CHECK_BEAUTIFY);

    }
    
    /**
     * 向被审核者推送去疑k
     */
    public void checkResult(Message message)
    {
        LogUtils.v(TAG, "checkResult() " + message.toString());
        SoundPoolUtils.play();
        EventBus.getDefault().post(message.getBody(), NotifyByEventBus.NT_CHECK_RESULT);
    }
    
//    private void notifyMe(String amount, String sex, String dwID, String name)
//    {
//        // 获得通知管理器，通知是一项系统服务
//        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//        // 初始化通知对象 p1:通知的图标 p2:通知的状态栏显示的提示 p3:通知显示的时间
//        Notification notification = new Notification(R.drawable.ic_launcher , "通知" , System.currentTimeMillis());
//        // 点击通知后的Intent，此例子点击后还是在当前界面
//        Intent data = new Intent(context , ExamineActivity_.class);
//        data.putExtra("sex", sex);
//        data.putExtra("dwID", dwID);
//        data.putExtra("name", name);
//        data.putExtra("amount", amount);
//        PendingIntent intent = PendingIntent.getActivity(context, 0, data, 0);
//        // 设置通知信息
//        notification.setLatestEventInfo(context, "您有一条消息", "有一位用户需要经过您的审核", intent);
//        // 通知
//        manager.notify(Constants.NOTICE_ID, notification);
//    }
}
