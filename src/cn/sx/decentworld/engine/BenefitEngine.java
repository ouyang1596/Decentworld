/**
 * 
 */
package cn.sx.decentworld.engine;

import java.util.HashMap;

import android.os.Handler;
import android.os.Message;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.common.ConstantNet;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.listener.NetCallback;
import cn.sx.decentworld.network.SendUrl;
import cn.sx.decentworld.network.SendUrl.HttpCallBack;
import cn.sx.decentworld.network.entity.ResultBean;
import cn.sx.decentworld.utils.LogUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.Request.Method;

/**
 * @ClassName: IncomeEngine.java
 * @Description: 收益业务类
 * @author: cj
 * @date: 2016年3月16日 下午4:31:29
 */
public class BenefitEngine
{
    private static final String TAG = "BenefitEngine";
    private String userID = "";
    private SendUrl sendUrl;

    /**
     * 初始化函数
     */
    public BenefitEngine()
    {
        userID = DecentWorldApp.getInstance().getDwID();
        sendUrl = new SendUrl(DecentWorldApp.getInstance().getApplicationContext());
    }

    /**
     * 验证用户是否曾经设置过提现密码
     * 
     * @param listener
     */
    public void isSetBenefitPwd(final IsSetBenefitListener listener)
    {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("dwID", userID);
        LogUtils.i(TAG, "isSetIncomePwd...begin,dwID=" + userID);
        sendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH + ConstantNet.API_HAS_INCOME_PWD, Method.GET, new HttpCallBack()
        {
            @Override
            public void onSuccess(String resultJSON, ResultBean resultBean)
            {
                LogUtils.i(
                        TAG,
                        "isSetIncomePwd...onSuccess,resultBean.getResultCode=" + resultBean.getResultCode() + ",resultBean.getMsg=" + resultBean.getMsg() + ",resultBean.getData="
                                + resultBean.getData());
                if (resultBean.getResultCode() == 2222)
                {
                    LogUtils.i(TAG, "isSetIncomePwd...success");
                    String str = resultBean.getData().toString();
                    JSONObject jsonObject = JSON.parseObject(str);
                    boolean isSet = jsonObject.getBooleanValue("init");
                    listener.onSuccess(isSet);
                }
                else if (resultBean.getResultCode() == 3333)
                {
                    LogUtils.i(TAG, "isSetIncomePwd...failure");
                    listener.onFailure("请求出错");
                }
            }

            @Override
            public void onFailure(String e)
            {
                LogUtils.i(TAG, "isSetIncomePwd...onFailure");
                listener.onFailure(Constants.NET_WRONG);
            }
        });

    }

    /**
     * 验证用户是否曾经设置过提现密码的回调监听
     */
    public interface IsSetBenefitListener
    {
        public void onSuccess(boolean isSet);

        public void onFailure(String cause);
    }

    /**
     * 设置虚拟币提现的提取密码
     * 
     * @param dwID
     *            用户ID
     * @param password
     *            密码
     * @param handler
     *            回调
     * @param requestCode
     *            请求码
     */
    public void setBenefitPwd(String dwID, String password, final NetCallback listener)
    {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("dwID", dwID);
        map.put("password", password);
        LogUtils.i(TAG, "setBenefitPwd...begin,dwID=" + dwID + ",password=" + password);
        sendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH + ConstantNet.API_DRAW_CASH_SET_PWD, Method.GET, new HttpCallBack()
        {
            @Override
            public void onSuccess(String resultJSON, ResultBean resultBean)
            {
                LogUtils.i(
                        TAG,
                        "setBenefitPwd...onSuccess,resultBean.getResultCode=" + resultBean.getResultCode() + ",resultBean.getMsg=" + resultBean.getMsg() + ",resultBean.getData="
                                + resultBean.getData());
                if (resultBean.getResultCode() == 2222)
                {
                    LogUtils.i(TAG, "setBenefitPwd...success");
                    listener.onSuccess(resultBean.getMsg());
                }
                else if (resultBean.getResultCode() == 3333)
                {
                    LogUtils.i(TAG, "setBenefitPwd...failure");
                    listener.onFailure(resultBean.getMsg());
                }
                else
                {
                    LogUtils.i(TAG, "setBenefitPwd...未知返回码");
                    listener.onFailure(resultBean.getMsg());
                }
            }

            @Override
            public void onFailure(String e)
            {
                LogUtils.i(TAG, "setBenefitPwd...onFailure");
                listener.onFailure(Constants.NET_WRONG);
            }
        });
    }

    /**
     * 设置是否自动转账
     * 
     * @param userID
     * @param auto
     * @param listener
     */
    public void setAutoTransfer(String userID, boolean auto,String password,final NetCallback listener)
    {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("dwID", userID);
        if (auto)
            map.put("auto", "true");
        else
            map.put("auto", "false");
        map.put("password", password);
        LogUtils.i(TAG, "setAutoTransfer...begin,userID=" + userID + ",auto= " + auto);
        sendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH + ConstantNet.API_SET_AUTO_TRANSFER, Method.GET, new HttpCallBack()
        {
            @Override
            public void onSuccess(String response, ResultBean msg)
            {
                LogUtils.i(TAG, "setAutoTransfer...begin,msg.getResultCode=" + msg.getResultCode() + ",msg.getMsg=" + msg.getMsg() + ",msg.getData=" + msg.getData());
                if (msg.getResultCode() == 2222)
                {
                    LogUtils.i(TAG, "setAutoTransfer...success");
                    listener.onSuccess("设置成功");
                }
                if (msg.getResultCode() == 3333)
                {
                    LogUtils.i(TAG, "setAutoTransfer...failure,cause by:" + msg.getMsg());
                    listener.onFailure("设置失败");
                }
            }

            @Override
            public void onFailure(String e)
            {
                LogUtils.i(TAG, "setAutoTransfer...onFailure,cause by:" + e);
                listener.onFailure(Constants.NET_WRONG);
            }
        });
    }

}
