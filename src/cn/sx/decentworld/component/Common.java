/**
 * 
 */
package cn.sx.decentworld.component;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import cn.sx.decentworld.activity.LoginActivity_;
import cn.sx.decentworld.bean.StrangerInfo;
import cn.sx.decentworld.common.LocationProvider;
import cn.sx.decentworld.common.XmppHelper;
import cn.sx.decentworld.engine.UserInfoEngine;
import cn.sx.decentworld.manager.MsgNotifyManager;
import cn.sx.decentworld.service.LocationService;
import cn.sx.decentworld.utils.ExitAppUtils;
import cn.sx.decentworld.utils.SPUtils;
import cn.sx.decentworld.utils.sputils.UserInfoHelper;

import com.googlecode.androidannotations.annotations.AfterInject;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.RootContext;

/**
 * @ClassName: Common.java
 * @Description:
 * @author: cj
 * @date: 2016年1月20日 上午10:40:13
 */
@EBean
public class Common
{

    @RootContext
    Context context;

    @RootContext
    Activity activity;

    @AfterInject
    public void init()
    {

    }

    /**
     * 登出注销操作
     */
    public void loginout()
    {
        ProgressDialog pd = null;
        try
        {
            pd = new ProgressDialog(context);
            pd.setMessage("正在退出登录...");
            pd.setCanceledOnTouchOutside(false);
            pd.show();
        }
        catch (Exception e)
        {
            Log.e(Common.class.getName(), "退出出错", e);
        }

        /** 清除SharedPre和Application中的数据 **/
        UserInfoHelper.clearLoginInfo(context);
        /** 删除数据库中的某些表 **/
        StrangerInfo.deleteAll();
        /** 停止服务(监听和定位) **/
        LocationProvider.getInstance(context).stop();
        context.stopService(new Intent(context , LocationService.class));
        //清除randomStr
        SPUtils.put(context, SPUtils.randomStr, "");
        //清除内存中的用户数据
        UserInfoEngine.getInstance().clear();
        //断开与服务器的连接
        XmppHelper.closeConnection();
        Intent intent = new Intent(context , LoginActivity_.class);
        context.startActivity(intent);
        //清除所有的通知
        for(int i=1;i<6;i++)
            MsgNotifyManager.getInstance().Notifyclear(i);
        //销毁所有Activity
        ExitAppUtils.getInstance().loginout();
        // /////////////////////////
        try
        {
            if (pd != null)
            {
                pd.dismiss();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 主界面中fragment跳转
     * 
     * @param ac
     *            跳转activity
     */
    public <T> void toActivity(Class<T> ac)
    {
        Intent intent = new Intent(activity , ac);
        activity.startActivity(intent);
    }

}
