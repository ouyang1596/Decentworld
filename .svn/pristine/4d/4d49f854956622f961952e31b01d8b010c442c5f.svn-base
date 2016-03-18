/**
 * 
 */
package cn.sx.decentworld.network.request;

import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import cn.sx.decentworld.common.ConstantNet;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.network.SendUrl;
import cn.sx.decentworld.network.SendUrl.HttpCallBack;
import cn.sx.decentworld.network.entity.ResultBean;
import cn.sx.decentworld.utils.LogUtils;
import cn.sx.decentworld.utils.ToastUtil;

import com.android.volley.Request.Method;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.RootContext;

/**
 * @ClassName: MomentNetRequest.java
 * @Description: 朋友圈的网络请求接口
 * @author: cj
 * @date: 2016年2月26日 上午10:12:08
 */
@EBean
public class MomentNetRequest
{
    public static String TAG = "MomentNetRequest";
    @RootContext
    Context context;
    @RootContext
    Activity activity;
    @Bean
    ToastComponent toast;
    private SendUrl sendUrl;

    @AfterViews
    void init()
    {
        sendUrl = new SendUrl(context);
    }

    /**
     * 弹出提示信息
     * 
     * @param data
     */
    private void showToastInfo(final String data)
    {
        activity.runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                ToastUtil.showToast(data);
            }
        });
    }



    /**
     * 上拉刷新（加载历史 记录）
     * 
     * @param dwID
     *            用户ID
     * @param index
     *            客户端存储的最大朋友圈的ID
     * @param handler
     *            回调
     */
    public void history(String dwID, long index, final Handler handler, final int requestCode)
    {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("dwID", dwID);
        map.put("index", String.valueOf(index));
        LogUtils.i(TAG, "history...begin,dwID=" + dwID + ",index=" + index);
        sendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH + ConstantNet.API_MOMENT_HISTORY, Method.GET, new HttpCallBack()
        {
            @Override
            public void onSuccess(String resultJSON, ResultBean resultBean)
            {
                LogUtils.i(TAG,
                        "history...onSuccess,resultBean.getResultCode=" + resultBean.getResultCode() + ",resultBean.getMsg=" + resultBean.getMsg() + ",resultBean.getData=" + resultBean.getData());
                if (resultBean.getResultCode() == 2222)
                {
                    LogUtils.i(TAG, "history...success");
                    Message message = Message.obtain();
                    message.what = requestCode;
                    message.arg1 = ConstantNet.NET_REQUEST_SUCCESS;
                    message.obj = resultBean.getData().toString();
                    handler.sendMessage(message);

                }
                else
                {
                    LogUtils.i(TAG, "history...failure");
                    Message message = Message.obtain();
                    message.what = requestCode;
                    message.arg1 = ConstantNet.NET_REQUEST_FAILURE;
                    message.obj = resultBean.getMsg();
                    handler.sendMessage(message);
                }
            }

            @Override
            public void onFailure(String e)
            {
                LogUtils.i(TAG, "history...onFailure");
                Message message = Message.obtain();
                message.what = requestCode;
                message.arg1 = ConstantNet.NET_REQUEST_FAILURE;
                message.obj = Constants.NET_WRONG;
                handler.sendMessage(message);
            }
        });
    }

    /**
     * 发表评论
     * 
     * @param dwID
     *            用户
     * @param commentJSON
     *            评论实体的JSON对象
     * @param handler
     *            回调
     */
    public void comment(String dwID, String commentJSON, final Handler handler)
    {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("dwID", dwID);
        map.put("comment", commentJSON);
        LogUtils.i(TAG, "comment...begin,dwID=" + dwID + ",comment=" + commentJSON);
        sendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH + ConstantNet.API_MOMENT_COMMENT, Method.GET, new HttpCallBack()
        {
            @Override
            public void onSuccess(String resultJSON, ResultBean resultBean)
            {
                LogUtils.i(TAG,
                        "comment...onSuccess,resultBean.getResultCode=" + resultBean.getResultCode() + ",resultBean.getMsg=" + resultBean.getMsg() + ",resultBean.getData=" + resultBean.getData());
                if (resultBean.getResultCode() == 2222)
                {
                    LogUtils.i(TAG, "comment...success");
                    Message message = Message.obtain();
                    message.what = ConstantNet.NET_REQUEST_SUCCESS;
                    message.obj = resultBean.getData().toString();
                    handler.sendMessage(message);

                }
                else
                {
                    LogUtils.i(TAG, "comment...failure");
                    Message message = Message.obtain();
                    message.what = ConstantNet.NET_REQUEST_FAILURE;
                    message.obj = resultBean.getMsg();
                    handler.sendMessage(message);
                }
            }

            @Override
            public void onFailure(String e)
            {
                LogUtils.i(TAG, "comment...onFailure");
                Message message = Message.obtain();
                message.what = ConstantNet.NET_REQUEST_FAILURE;
                message.obj = Constants.NET_WRONG;
                handler.sendMessage(message);
            }
        });
    }

    /**
     * 订阅
     * 
     * @param dwID
     *            用户ID
     * @param publisherID
     *            被订阅人的ID
     * @param handler
     *            回调
     */
    public void subscribe(String dwID, String publisherID, final Handler handler)
    {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("dwID", dwID);
        map.put("publisherID", publisherID);
        LogUtils.i(TAG, "subscribe...begin,dwID=" + dwID + ",publisherID=" + publisherID);
        sendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH + ConstantNet.API_MOMENT_SUBSCRIBE, Method.GET, new HttpCallBack()
        {
            @Override
            public void onSuccess(String resultJSON, ResultBean resultBean)
            {
                LogUtils.i(TAG,
                        "subscribe...onSuccess,resultBean.getResultCode=" + resultBean.getResultCode() + ",resultBean.getMsg=" + resultBean.getMsg() + ",resultBean.getData=" + resultBean.getData());
                if (resultBean.getResultCode() == 2222)
                {
                    LogUtils.i(TAG, "subscribe...success");
                    Message message = Message.obtain();
                    message.what = ConstantNet.NET_REQUEST_SUCCESS;
                    message.obj = resultBean.getData().toString();
                    handler.sendMessage(message);

                }
                else
                {
                    LogUtils.i(TAG, "subscribe...failure");
                    Message message = Message.obtain();
                    message.what = ConstantNet.NET_REQUEST_FAILURE;
                    message.obj = resultBean.getMsg();
                    handler.sendMessage(message);
                }
            }

            @Override
            public void onFailure(String e)
            {
                LogUtils.i(TAG, "subscribe...onFailure");
                Message message = Message.obtain();
                message.what = ConstantNet.NET_REQUEST_FAILURE;
                message.obj = Constants.NET_WRONG;
                handler.sendMessage(message);
            }
        });
    }

    /**
     * 取消订阅
     * 
     * @param dwID
     *            用户ID
     * @param publisherID
     *            被订阅人的ID
     * @param handler
     *            回调
     */
    public void removeSubscribe(String dwID, String publisherID, final Handler handler)
    {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("dwID", dwID);
        map.put("publisherID", publisherID);
        LogUtils.i(TAG, "removeSubscribe...begin,dwID=" + dwID + ",publisherID=" + publisherID);
        sendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH + ConstantNet.API_MOMENT_REMOVE_SUBSCRIBE, Method.GET, new HttpCallBack()
        {
            @Override
            public void onSuccess(String resultJSON, ResultBean resultBean)
            {
                LogUtils.i(TAG, "removeSubscribe...onSuccess,resultBean.getResultCode=" + resultBean.getResultCode() + ",resultBean.getMsg=" + resultBean.getMsg() + ",resultBean.getData="
                        + resultBean.getData());
                if (resultBean.getResultCode() == 2222)
                {
                    LogUtils.i(TAG, "removeSubscribe...success");
                    Message message = Message.obtain();
                    message.what = ConstantNet.NET_REQUEST_SUCCESS;
                    message.obj = resultBean.getData().toString();
                    handler.sendMessage(message);

                }
                else
                {
                    LogUtils.i(TAG, "removeSubscribe...failure");
                    Message message = Message.obtain();
                    message.what = ConstantNet.NET_REQUEST_FAILURE;
                    message.obj = resultBean.getMsg();
                    handler.sendMessage(message);
                }
            }

            @Override
            public void onFailure(String e)
            {
                LogUtils.i(TAG, "removeSubscribe...onFailure");
                Message message = Message.obtain();
                message.what = ConstantNet.NET_REQUEST_FAILURE;
                message.obj = Constants.NET_WRONG;
                handler.sendMessage(message);
            }
        });
    }

}
