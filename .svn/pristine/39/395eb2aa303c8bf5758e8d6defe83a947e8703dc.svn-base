/**
 * 
 */
package cn.sx.decentworld.network.request;

import java.io.File;
import java.util.HashMap;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import cn.sx.decentworld.activity.PrivacySettingActivity;
import cn.sx.decentworld.bean.UserInfo;
import cn.sx.decentworld.bean.manager.UserInfoManager;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.network.SendUrl;
import cn.sx.decentworld.network.SendUrl.HttpCallBack;
import cn.sx.decentworld.network.entity.ResultBean;
import cn.sx.decentworld.utils.LogUtils;
import cn.sx.decentworld.utils.ToastUtils;

import com.android.volley.Request.Method;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.RootContext;

/**
 * @ClassName: SetUserInfo.java
 * @Description:上传用户修改和设置的信息 1.设置用户身家 2.设置用户身价 3.设置用户的绑定的支付宝或微信账号
 * 
 * 
 * @author: cj
 * @date: 2015年9月25日 上午10:28:22
 */
@EBean
public class SetUserInfo
{
    public static String TAG = "SetUserInfo";
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
     * 设置用户身价
     */
    public void setWorth(String dwID, final String worth)
    {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("dwID ", dwID);
        map.put("worth", worth);
        LogUtils.i(TAG, "setWorth...begin，dwID=" + dwID + ",worth=" + worth);
        sendUrl.httpRequestWithParams(map, Constants.SET_WORTH, Method.GET, new HttpCallBack()
        {
            @Override
            public void onFailure(String error)
            {
                LogUtils.e(TAG, "setWorth...onFailure,cause by:" + error);
            }

            @Override
            public void onSuccess(String response, ResultBean bean)
            {
                LogUtils.i(TAG, "setWorth...msg.getResultCode=" + bean.getResultCode() + ",msg.getMsg=" + bean.getMsg());
                if (bean.getResultCode() == 2222)
                {
                    Message message = new Message();
                    message.obj = worth;
                    LogUtils.i(TAG, "setWorth...success");
                }
                if (bean.getResultCode() == 3333)
                {
                    LogUtils.i(TAG, "setWorth...failure");
                }
            }
        });
    }

    /**
     * 设置用户的三张图片
     * 
     * @param dwID
     * @param icon
     * @param count
     *            代表第几张图片 count取值为 1/2/3
     * @param mHandler
     */
    public void setUserIcon(String dwID, File[] icon, final int count)
    {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("dwID", dwID);
        map.put("count", String.valueOf(count));
        LogUtils.i(TAG, "setUserIcon...begin,dwID=" + dwID);
        sendUrl.httpRequestWithImage(map, icon, Constants.CONTEXTPATH + "/set/updateImage", new HttpCallBack()
        {
            @Override
            public void onSuccess(String response, ResultBean msg)
            {
                LogUtils.i(TAG, "setUserIcon...msg.getResultCode=" + msg.getResultCode());
                if (msg.getResultCode() == 2222)
                {
                    LogUtils.i(TAG, "setUserIcon...success,第 " + count + "张");
                    showToast("上传成功");
                }
                if (msg.getResultCode() == 3333)
                {
                    LogUtils.i(TAG, "setUserIcon...failure caused by:" + msg.getMsg());
                    showToast(msg.getMsg());
                }
            }

            @Override
            public void onFailure(String e)
            {
                LogUtils.i(TAG, "setUserIcon...onFailure caused by:" + e);
                showToast(Constants.NET_WRONG);
            }
        });
    }

    private void showToast(final String msg)
    {
        activity.runOnUiThread(new Runnable()
        {

            @Override
            public void run()
            {
                toast.show(msg);
            }
        });
    }

    /**
     * 设置用户的信息
     */
    public void setUserInfo(String json, final Handler handler, final int requestCode)
    {
        // 正常情况下，将传入的要修改的个性签名内容保存，如果服务器返回成功结果码，在同步更新本地信息；
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("allUserInfo", json);
        LogUtils.i(TAG, "setUserInfo...begin,json = " + json);
        sendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH + "/set/updateUserInfo", Method.GET, new HttpCallBack()
        {
            @Override
            public void onSuccess(String response, ResultBean msg)
            {
                if (msg.getResultCode() == 2222)
                {
                    LogUtils.i(TAG, "setUserInfo...success");// 成功
                    Message message = Message.obtain();
                    message.what = requestCode;
                    message.arg1 = 1;
                    handler.sendMessage(message);
                }
                if (msg.getResultCode() == 3333)
                {
                    LogUtils.i(TAG, "setUserInfo...failure，cause by:" + msg.getMsg());// 失败
                    ToastUtils.toast(context, msg.getMsg());
                    Message message = Message.obtain();
                    message.what = requestCode;
                    message.arg1 = 0;
                    handler.sendMessage(message);
                }
            }

            @Override
            public void onFailure(String e)
            {
                LogUtils.i(TAG, "setUserInfo...failure,causer by:" + e);
                ToastUtils.toast(context, Constants.NET_WRONG);
                Message message = Message.obtain();
                message.what = requestCode;
                message.arg1 = 0;
                handler.sendMessage(message);
            }
        });
    }

    /**
     * 设置用户对外显示的权限
     * 
     * @param json
     */
    public void setUserAuthority(String dwID, String json)
    {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("dwID", dwID);
        map.put("authority", json);
        LogUtils.i(TAG, "setUserAuthority...begin,json = " + json);
        sendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH + "/user/setAuthority", Method.GET, new HttpCallBack()
        {
            @Override
            public void onSuccess(String response, ResultBean msg)
            {
                if (msg.getResultCode() == 3000)
                {
                    LogUtils.i(TAG, "setUserAuthority...success");// 成功
                }
                if (msg.getResultCode() == 3001)
                {
                    LogUtils.i(TAG, "setUserAuthority...failure,cause by:" + msg.getMsg());// 失败
                }
            }

            @Override
            public void onFailure(String e)
            {
                LogUtils.i(TAG, "setUserAuthority...onFailure,causer by:" + e);
            }
        });
    }

    /**
     * 设置谁是我的贵人 可以实现 1.设置一个新的贵人 2.修改贵人 3.删除贵人
     * 
     * @param json
     */
    public void setUserGr(String dwID, final String grID, final Handler handler, final int requestCode)
    {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("dwID", dwID);
        map.put("grID", grID);
        LogUtils.i(TAG, "setUserGr...begin,dwID = " + dwID + ",grID=" + grID);
        sendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH + "/user/setGR", Method.GET, new HttpCallBack()
        {
            @Override
            public void onSuccess(String response, ResultBean msg)
            {
                LogUtils.i(TAG, "setUserGr...msg.getResultCode=" + msg.getResultCode() + ",msg.getMsg=" + msg.getMsg());// 成功
                if (msg.getResultCode() == 2222)
                {
                    Message message = Message.obtain();
                    message.what = requestCode;
                    message.obj = grID;
                    handler.sendMessage(message);
                    LogUtils.i(TAG, "setUserGr...success");// 成功
                }
                if (msg.getResultCode() == 3333)
                {
                    ToastUtils.toast(context, msg.getMsg());
                    LogUtils.i(TAG, "setUserGr...failure,causer by:" + msg.getMsg());// 失败
                }
            }

            @Override
            public void onFailure(String e)
            {
                ToastUtils.toast(context, Constants.NET_WRONG);
                LogUtils.i(TAG, "setUserGr...onFailure,causer by:" + e);
            }
        });
    }

    private ProgressDialog mProDialog;

    private void showProgressDialog()
    {
        activity.runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                if (null == mProDialog)
                {
                    mProDialog = ProgressDialog.show(context, null, "loading");
                }
                else
                {
                    mProDialog.show();
                }
            }
        });
    }

    private void hideProgressDialog()
    {
        activity.runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                if (null != mProDialog)
                {
                    mProDialog.hide();
                }
            }
        });
    }

    /**
     * 设置用户提现的账号（支付宝或微信）
     * 
     * @param dwID
     *            自己的id
     * @param accountType
     *            账号类型（支付宝 0 | 微信 1）
     * @param account
     *            账号
     * @param activity
     */
    public void setPaycardAccount(String dwID, final int accountType, final String account, final Activity activity)
    {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("dwID", dwID);
        map.put("accountType", String.valueOf(accountType));
        map.put("account", account);
        LogUtils.i(TAG, "setPaycardAccount...begin,dwID=" + dwID + ",accountType=" + accountType + ",account=" + account);
        sendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH + "/user/setAccount", Method.POST, new HttpCallBack()
        {
            @Override
            public void onSuccess(String response, ResultBean msg)
            {
                // 设置成功 设置失败（原因）
                LogUtils.i(TAG, "setPaycardAccount...msg.getResultCode=" + msg.getResultCode() + ",msg.getMsg=" + msg.getMsg() + ",msg.getData=" + msg.getData());
                if (msg.getResultCode() == 2222)
                {
                    LogUtils.i(TAG, "setPaycardAccount...success");
                    ToastUtils.toast(context, "设置成功");
                    // 保存到数据库
                    UserInfo info = UserInfoManager.getUserInfoInstance();

                    // 传递到开启界面
                    Intent intent = new Intent();
                    intent.putExtra(PrivacySettingActivity.PAYACCOUNT, account);
                    activity.setResult(activity.RESULT_OK, intent);
                    activity.finish();
                }
                else if (msg.getResultCode() == 3333)
                {
                    LogUtils.i(TAG, "setPaycardAccount...failure,case by:" + msg.getMsg());
                    ToastUtils.toast(context, "设置失败");
                }
            }

            @Override
            public void onFailure(String e)
            {
                LogUtils.i(TAG, "setPaycardAccount...onFailure,cause by:" + e);
                ToastUtils.toast(context, Constants.NET_WRONG);
            }
        });
    }

    /**
     * 上传高级设置的开关权限
     */
    public void uploadAdvanceAuth(String auth, final Handler handler, final int requestCode)
    {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("auth", auth);
        LogUtils.i(TAG, "uploadAdvanceAuth...begin,auth=" + auth);
        sendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH + Constants.AUTH_SETTING, Method.GET, new HttpCallBack()
        {
            @Override
            public void onSuccess(String response, ResultBean msg)
            {
                LogUtils.i(TAG, "uploadAdvanceAuth...begin,msg.getResultCode=" + msg.getResultCode() + ",msg.getMsg=" + msg.getMsg() + ",msg.getData=" + msg.getData());
                if (msg.getResultCode() == 2222)
                {
                    LogUtils.i(TAG, "updateAuthtoServer...success");
                    Message message = Message.obtain();
                    message.what = requestCode;
                    message.arg1 = 1;
                    handler.sendMessage(message);
                }
                if (msg.getResultCode() == 3333)
                {
                    LogUtils.i(TAG, "updateAuthtoServer...failure,cause by:" + msg.getMsg());
                    Message message = Message.obtain();
                    message.what = requestCode;
                    message.arg1 = 0;
                    handler.sendMessage(message);
                }
            }

            @Override
            public void onFailure(String e)
            {
                LogUtils.i(TAG, "updateAuthtoServer...onFailure,cause by:" + e);
                Message message = Message.obtain();
                message.what = requestCode;
                message.arg1 = 0;
                handler.sendMessage(message);
            }
        });
    }
   
    /**
     * 设置是否自动转账
     * @param userID 用户ID
     * @param auto 
     * @param handler
     * @param requestCode
     */
    public void setAutoTransfer(String userID,boolean auto)
    {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("dwID", userID);
        if(auto)
            map.put("auto", "true");
        else
            map.put("auto", "false");
        LogUtils.i(TAG, "setAutoTransfer...begin,userID=" + userID+",auto= "+auto);
        sendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH + "/auth/setAutoTransfer", Method.GET, new HttpCallBack()
        {
            @Override
            public void onSuccess(String response, ResultBean msg)
            {
                LogUtils.i(TAG, "setAutoTransfer...begin,msg.getResultCode=" + msg.getResultCode() + ",msg.getMsg=" + msg.getMsg() + ",msg.getData=" + msg.getData());
                if (msg.getResultCode() == 2222)
                {
                    LogUtils.i(TAG, "setAutoTransfer...success");
                    ToastUtils.toast(context, "设置成功");
                }
                if (msg.getResultCode() == 3333)
                {
                    LogUtils.i(TAG, "setAutoTransfer...failure,cause by:" + msg.getMsg());
                    ToastUtils.toast(context, "设置失败");
                }
            }

            @Override
            public void onFailure(String e)
            {
                LogUtils.i(TAG, "setAutoTransfer...onFailure,cause by:" + e);
                ToastUtils.toast(context, "网络错误");
            }
        });
    }
}
