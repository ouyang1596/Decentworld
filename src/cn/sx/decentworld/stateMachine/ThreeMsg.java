/**
 * 
 */
package cn.sx.decentworld.stateMachine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.jivesoftware.smack.packet.Message;

import android.os.Handler;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.common.CommUtil;
import cn.sx.decentworld.common.ConstantNet;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.config.ConstantConfig;
import cn.sx.decentworld.connect.XmppHelper;
import cn.sx.decentworld.entity.dao.DWMessageDao;
import cn.sx.decentworld.logSystem.LogUtils;
import cn.sx.decentworld.network.SendUrl;
import cn.sx.decentworld.network.SendUrl.HttpCallBack;
import cn.sx.decentworld.network.entity.ResultBean;
import cn.sx.decentworld.utils.XmppPacketParseUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.Request.Method;

/**
 * @ClassName: ThreeMsg
 * @Description: 从网络获取三号消息
 * @author: Jackchen
 * @date: 2016年4月29日 下午6:17:17
 */
public class ThreeMsg
{
    private static final String TAG = "ThreeMsg";
    // 实例对象
    private static final ThreeMsg instance = new ThreeMsg();
    // 仅想网络请求；
    private Handler netHandler = new Handler()
    {
        public void handleMessage(android.os.Message msg)
        {
            LogUtils.v(TAG, "进入 netHandler,即将进行网络请求...");
            if(DecentWorldApp.getInstance().getIsStart().get())
            {
                getThreeMsgFromNet();
                netHandler.sendEmptyMessageDelayed(0x110, ConstantConfig.INTERVAL_TIME_GET_THREE_MSG);
            }
        };
    };
    //数据处理
    private Handler handDataHandler = new Handler()
    {
        public void handleMessage(android.os.Message msg)
        {
            LogUtils.v(TAG, "进入 handDataHandler,将数据交给XMPPTCPConnection处理");
            Message message = (Message) msg.obj;
            LogUtils.d(TAG, "handDataHandler,"+message.toString());
            XmppHelper.getConnection().handThreeMsg(message);
        };
    };


    /**
     * 获取实例
     * @param where 用于标记哪个类调用的
     */
    public static ThreeMsg getInstance(String where)
    {
        if(CommUtil.isNotBlank(where))
        LogUtils.v(TAG, "getInstance() params[where="+where+"]");
        return instance;
    }

    /**
     * 开启任务； 从服务器拿取3号消息； 发送回执；
     */
    public void startTask()
    {
        String info = "已经启动";
        if(DecentWorldApp.getInstance().getIsStart().compareAndSet(false, true))
        {
            info = "第一次启动";
            netHandler.sendEmptyMessageDelayed(0x110, ConstantConfig.INTERVAL_TIME_GET_THREE_MSG*2);
        }
        LogUtils.v(TAG, "startTask() 获取三号消息的任务:"+info);
    }

    /**
     * 从网络获取3号消息
     */
    private void getThreeMsgFromNet()
    {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("dwID", DecentWorldApp.getInstance().getDwID());
        LogUtils.v(TAG, "getThreeMsgFromNet() params:" + JSON.toJSONString(map));
        SendUrl sendUrl = new SendUrl(DecentWorldApp.getGlobalContext());
        sendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH + ConstantNet.API_SYNC_MSG_3, Method.GET, new HttpCallBack()
        {
            @Override
            public void onSuccess(String resultJSON, ResultBean resultBean)
            {
                LogUtils.v(TAG, "getThreeMsgFromNet() onSuccess,resultJSON:" + resultJSON);
                if (resultBean.getResultCode() == 2222)
                {
                    handeNetData(resultBean.getData().toString());
                }
                else if (resultBean.getResultCode() == 3333)
                {
                    //无
                }
            }

            @Override
            public void onFailure(String e)
            {
                LogUtils.e(TAG, "getThreeMsgFromNet() onFailure,cause by:" + e);
            }
        });

    }

    protected void handeNetData(String result)
    {
        LogUtils.v(TAG, "handeNetData() params[result="+result+"]");
        //解析数据 Message类型
        List<Message> messages = new ArrayList<Message>();
        JSONObject jsonObject = JSON.parseObject(result);
        JSONArray jsonArray = jsonObject.getJSONArray("messages");
        if(jsonArray!= null && jsonArray.size()>0)
        {
            for(int i=0;i<jsonArray.size();i++)
            {
               String msg = (String) jsonArray.get(i);
               Message message = XmppPacketParseUtils.parseMessage(msg);
               if(message!= null && !DWMessageDao.contains(message.getPacketID()))
               {
                   messages.add(message);
                   LogUtils.d(TAG, "handeNetData,解析的结果为："+message.toString());
               }
            }
        }
        
        //用Handler将消息发送出去
        for(Message message:messages)
        {
            android.os.Message tempMsg = android.os.Message.obtain();
            tempMsg.obj = message;
            handDataHandler.sendMessage(tempMsg);
        }
    }

}
