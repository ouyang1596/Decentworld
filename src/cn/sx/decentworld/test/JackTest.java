/**
 * 
 */
package cn.sx.decentworld.test;

import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.R;
import cn.sx.decentworld.activity.BaseFragmentActivity;
import cn.sx.decentworld.bean.UserInfo;
import cn.sx.decentworld.bean.manager.UserInfoManager;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.manager.DWAudioManager;
import cn.sx.decentworld.network.request.GetUserInfo;
import cn.sx.decentworld.utils.AES;
import cn.sx.decentworld.utils.ImageLoaderHelper;
import cn.sx.decentworld.utils.LogUtils;
import cn.sx.decentworld.utils.MsgVerify;
import cn.sx.decentworld.utils.SPUtils;
import cn.sx.decentworld.utils.ServiceUtils;
import cn.sx.decentworld.utils.ThreadUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.ViewById;

/**
 * @ClassName: JackTest.java
 * @Description:
 * @author: cj
 * @date: 2015年12月3日 上午9:42:51
 */

@EActivity(R.layout.activity_jack_test)
public class JackTest extends BaseFragmentActivity implements OnClickListener
{
    private static final String TAG = "JackTest";
    @ViewById(R.id.btn_current_thread)
    Button btn_current_thread;

    @ViewById(R.id.btn_current_service)
    Button btn_current_service;

    @ViewById(R.id.btn_play_audio)
    Button btn_play_audio;

    @ViewById(R.id.btn_remove_cache)
    Button btn_remove_cache;

    @ViewById(R.id.btn_get_contact)
    Button btn_get_contact;

    @Bean
    ToastComponent toast;
    @Bean
    GetUserInfo getUserInfo;
    private String userID;
    private String key;

    @AfterViews
    void init()
    {
        userID = DecentWorldApp.getInstance().getDwID();
        btn_current_thread.setOnClickListener(this);
        btn_current_service.setOnClickListener(this);
        btn_play_audio.setOnClickListener(this);
        btn_remove_cache.setOnClickListener(this);
        btn_get_contact.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_current_thread:
                ThreadUtils.printAllThreads();
                break;
            case R.id.btn_current_service:
//                if (ServiceUtils.isServiceRunning(JackTest.this, "cn.sx.decentworld.service.PacketListenerService"))
//                {
//                    toast.show("消息监听服务已经启动");
//                }
//                else
//                {
//                    toast.show("消息监听服务已经停止");
//                }
                //测试key
                long time1 = System.currentTimeMillis();
                String msg1 = userID + time1;
                String randomStr1 = (String) SPUtils.get(mContext, SPUtils.randomStr, "");
                LogUtils.i(TAG, "randomStr1="+randomStr1);
                String token1 = AES.encode(msg1, randomStr1);
                LogUtils.i(TAG, "加密前明文 msg="+msg1+",token="+token1);
                getUserInfo.testKey(userID, token1, msg1);
                
                break;
            case R.id.btn_play_audio:
                //测试key
                long time = System.currentTimeMillis();
                String msg = userID + time;
                LogUtils.i(TAG, "randomStr="+randomStr);
                String token = AES.encode(msg, randomStr);
                LogUtils.i(TAG, "加密前明文 msg="+msg+",token="+token);
                getUserInfo.testKey(userID, token, msg);
              
                break;
            case R.id.btn_remove_cache:
                //获取key
                getUserInfo.getKey(userID,meHandler,GET_PUBLIC_KEY);
                
                break;
            case R.id.btn_get_contact:
                //上传key
                getUserInfo.uploadKey(userID, DecentWorldApp.getInstance().getPassword(), newKey,randomStr,meHandler,GET_UPLOAD_NEWKEY);
                break;
            default:
                break;
        }
    }
    

    private static final int GET_PUBLIC_KEY = 4;
    private static final int GET_UPLOAD_NEWKEY = 5;

    private String newKey;
    private String randomStr;
    Handler meHandler = new Handler()
    {
        public void handleMessage(android.os.Message msg)
        {
            switch (msg.what)
            {
                case GET_PUBLIC_KEY:
                    String key = msg.obj.toString();
                    randomStr = MsgVerify.getSalt();//randomStr 保存在本地，用于生成token
                    try
                    {
//                        newKey = MsgVerify.encryptByPublicKey(randomStr, MsgVerify.getPublicKey(key));//tempKey 上传服务器
                        LogUtils.i(TAG, "randomStr="+randomStr+",newKey="+newKey);
                    }
                    catch (Exception e)
                    {
                        LogUtils.i(TAG, "加密异常，cause by:"+e.toString());
                    }
                    break;
                case GET_UPLOAD_NEWKEY:
                    String randomStr1 = msg.obj.toString();
                    SPUtils.put(JackTest.this, SPUtils.randomStr, randomStr1);
                    LogUtils.i(TAG, "加密后的randomStr已经上传服务器,加密前的randomStr="+randomStr1);
                    break;
                default:
                    break;
            }
        };
    };
    
    
    Handler handler = new Handler()
    {
        public void handleMessage(android.os.Message msg) {
            
        };
    };

    public static void test()
    {
        String jsonArrayData = "[{\"a1\":\"12\",\"b1\":\"112\",\"c1\":\"132\",\"d1\":\"134\"},{\"a2\":\"12\",\"b2\":\"112\",\"c2\":\"132\",\"d2\":\"134\"},{\"a3\":\"12\",\"b3\":\"112\",\"c3\":\"132\",\"d3\":\"134\"}]";
        System.out.println("STR:" + jsonArrayData);
        System.out.println("JSONSTR:" + JSON.toJSONString(jsonArrayData));
    }
    
    public void test1()
    {
        String str = "你要解析的字符串";
        JSONObject jsonObject = JSON.parseObject(str);
        JSONArray array = new JSONArray();
        JSONObject object;
        for(int i= 0;i<10;i++)
        {
            object  = new JSONObject();
            object.put("name", "jack"+i);
            object.put("age", i);
            array.add(object);
        }
        jsonObject.put("newArray", array);
    }

}
