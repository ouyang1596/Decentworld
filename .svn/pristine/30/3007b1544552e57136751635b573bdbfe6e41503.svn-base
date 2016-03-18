/**
 * 
 */
package cn.sx.decentworld.engine;

import java.util.HashMap;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.common.CommUtil;
import cn.sx.decentworld.common.ConstantNet;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.network.SendUrl;
import cn.sx.decentworld.network.SendUrl.HttpCallBack;
import cn.sx.decentworld.network.entity.ResultBean;
import cn.sx.decentworld.utils.Base64Util;
import cn.sx.decentworld.utils.LogUtils;
import cn.sx.decentworld.utils.MsgVerify;
import cn.sx.decentworld.utils.SPUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.Request.Method;

/**
 * @ClassName: UserKey.java
 * @Description: 获取publicKey，然后生成一个随机字符串，用随机字符串对publicKey进行加密生成一个newKey；
 *               将newKey上传到服务器，上传成功后，将随机字符串保存在本地；
 * @author: cj
 * @date: 2016年3月9日 下午5:37:35
 */
public class UserKeyEngine
{
    private static final String TAG = "UserKey";
    private static final Object LOCK = new Object();
    private static UserKeyEngine instance = null;

    /**
     * 防止外部创建多个对象
     */
    private UserKeyEngine()
    {

    }

    /**
     * 获取实例
     * 
     * @return
     */
    public static UserKeyEngine getInstance()
    {
        if (instance == null)
            synchronized (LOCK)
            {
                if (instance == null)
                    instance = new UserKeyEngine();
            }
        return instance;
    }

    /**
     * 获得publicKey
     * 
     * @param context
     *            上下文
     * @param listener
     *            回调监听
     */
    public void getKey(final Context context, final UserKeyListener listener)
    {
        String userID = DecentWorldApp.getInstance().getDwID();
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("dwID", userID);
        LogUtils.i(TAG, "getKey...begin,userID=" + userID);
        SendUrl sendUrl = new SendUrl(context);
        final HandKey handKey = new HandKey(context, listener);
        sendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH + ConstantNet.API_GET_KEY, Method.GET, new HttpCallBack()
        {
            @Override
            public void onSuccess(String response, ResultBean msg)
            {
                LogUtils.i(TAG, "getKey...begin,msg.getResultCode=" + msg.getResultCode() + ",msg.getMsg=" + msg.getMsg() + ",msg.getData=" + msg.getData());
                if (msg.getResultCode() == 2222)
                {
                    JSONObject result = JSON.parseObject(msg.getData().toString());
                    String key = result.getString("key");
                    // 上传key
                    Message message = Message.obtain();
                    message.obj = key;
                    handKey.sendMessage(message);
                }
                else if (msg.getResultCode() == 3333)
                {
                    LogUtils.i(TAG, "getKey...failure,cause by:" + msg.getMsg());
                    if (CommUtil.isNotBlank(msg.getMsg()))
                    {
                        listener.onFailed(msg.getMsg());
                    }
                    else
                    {
                        listener.onFailed("获取key失败");
                    }
                }
                else
                {
                    LogUtils.i(TAG, "getKey...failure,cause by:未知的返回码。");
                    listener.onFailed("getKey未知返回码");
                }
            }

            @Override
            public void onFailure(String e)
            {
                listener.onFailed(Constants.NET_WRONG);
            }
        });
    }

    /**
     * 获取到服务器传回的publicKey后用生成的随机字符串进行加密，加密后上传
     */
    private class HandKey extends Handler
    {
        private UserKeyListener listener;
        private String key;
        private Context context;

        /**
         * 构造函数
         */
        public HandKey(Context context, UserKeyListener listener)
        {
            this.context = context;
            this.listener = listener;
        }

        @Override
        public void handleMessage(Message msg)
        {
            this.key = msg.obj.toString();
            // 获取随机字符串
            String randomStr = MsgVerify.getSalt();
            try
            {
                //用生成的随机字符串加密publicKey生成newKey
                String newKey = Base64Util.encodeToString(MsgVerify.encrypt(MsgVerify.getPublicKey(key), randomStr.getBytes()));
                String password = DecentWorldApp.getInstance().getPassword();
                LogUtils.i(TAG, "password=" + password+",randomStr=" + randomStr+",newKey="+newKey);
                uploadKey(context, password, newKey, randomStr, listener);
            }
            catch (Exception e)
            {
                LogUtils.i(TAG, "加密异常，cause by:" + e.toString());
                listener.onFailed("生成key失败");
            }
        }
    }

    /**
     * 将newKey上传到服务器
     * @param context
     * @param password
     * @param newKey
     * @param randomStr
     * @param listener
     */
    private void uploadKey(final Context context, String password, String newKey, final String randomStr, final UserKeyListener listener)
    {
        String userID = DecentWorldApp.getInstance().getDwID();
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("dwID", userID);
        map.put("password", password);
        map.put("newKey", newKey);
        SendUrl sendUrl = new SendUrl(context);
        LogUtils.i(TAG, "uploadKey...begin,userID=" + userID + ",password=" + password + ",newKey=" + newKey);
        sendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH + ConstantNet.API_UPLOAD_KEY, Method.GET, new HttpCallBack()
        {
            @Override
            public void onSuccess(String response, ResultBean msg)
            {
                LogUtils.i(TAG, "uploadKey...begin,msg.getResultCode=" + msg.getResultCode() + ",msg.getMsg=" + msg.getMsg() + ",msg.getData=" + msg.getData());
                if (msg.getResultCode() == 2222)
                {
                    LogUtils.i(TAG, "uploadKey...success");
                    // 保存数据
                    SPUtils.put(context, SPUtils.randomStr, randomStr);
                    LogUtils.i(TAG, "加密后的randomStr已经上传服务器,加密前的randomStr=" + randomStr);
                    // 成功回调
                    listener.onSucceed();
                }
                else if (msg.getResultCode() == 3333)
                {
                    LogUtils.i(TAG, "uploadKey...failure,cause by:" + msg.getMsg());
                    if (CommUtil.isNotBlank(msg.getMsg()))
                    {
                        listener.onFailed(msg.getMsg());
                    }
                    else
                    {
                        listener.onFailed("上传key失败");
                    }
                }
                else
                {
                    LogUtils.i(TAG, "uploadKey...failure,cause by:未知的返回码");
                    listener.onFailed("uploadKey未知的返回码");
                }
            }

            @Override
            public void onFailure(String e)
            {
                LogUtils.i(TAG, "uploadKey...onFailure,cause by:" + e);
                listener.onFailed(Constants.NET_WRONG);
            }
        });
    }

    /**
     * 回调接口
     */
    public interface UserKeyListener
    {
        //成功
        public void onSucceed();
        //失败
        public void onFailed(String casue);
    }

}
