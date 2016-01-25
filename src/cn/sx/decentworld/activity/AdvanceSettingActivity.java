/**
 * 
 */
package cn.sx.decentworld.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.R;
import cn.sx.decentworld.bean.UserExtraInfo;
import cn.sx.decentworld.bean.UserInfo;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.component.TitleBar;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.dialog.ReminderDialog;
import cn.sx.decentworld.dialog.ReminderDialog.ReminderListener;
import cn.sx.decentworld.network.SendUrl;
import cn.sx.decentworld.network.SendUrl.HttpCallBack;
import cn.sx.decentworld.network.entity.ResultBean;
import cn.sx.decentworld.network.request.GetUserInfo;
import cn.sx.decentworld.network.request.SetUserInfo;
import cn.sx.decentworld.utils.LogUtils;
import cn.sx.decentworld.utils.ViewUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.Request.Method;
import com.googlecode.androidannotations.annotations.*;

/**
 * @ClassName: AdvanceSettingActivity.java
 * @Description:
 * @author: cj
 * @date: 2015年12月27日 上午9:27:04
 */
@EActivity(R.layout.activity_advance_setting)
public class AdvanceSettingActivity extends BaseFragmentActivity implements OnClickListener
{
    /**
     * 常量
     */
    private static final String TAG = "AdvanceSettingActivity";
    private static final int GET_ADVANCE_AUTH = 1;
    private static final int UPLOAD_ADVANCE_AUTH = 2;
    
    /**
     * 工具类
     */
    @Bean
    GetUserInfo getUserInfo;
    @Bean
    SetUserInfo setUserInfo;
    @Bean
    ToastComponent toast;
    @Bean
    TitleBar titleBar;

    /**
     * 界面资源
     */
    @ViewById(R.id.ll_advance_setting_root)
    LinearLayout ll_advance_setting_root;

    /** 开关控件 **/
    /** 陌生人消息提醒 **/
    @ViewById(R.id.iv_advance_setting_stranger_msg)
    ImageView iv_advance_setting_stranger_msg;
    /** 接收系统推送的消息 **/
    @ViewById(R.id.iv_advance_setting_push_system)
    ImageView iv_advance_setting_push_system;
    /** 接收审核信息（包括图像和审核信息）**/
    @ViewById(R.id.iv_advance_setting_acceptCheckPush)
    ImageView iv_advance_setting_acceptCheckPush;

    /**
     * 变量
     */
    private String dwID;
    private int items = 3;
    private Boolean[] checkBoxIsChecked = new Boolean[items];
    private List<ImageView> imageViewList;
    private List<String> indexs;

    @AfterViews
    public void init()
    {
        ViewUtil.scaleContentView(ll_advance_setting_root);
        dwID = DecentWorldApp.getInstance().getDwID();
        titleBar.setTitleBar("设置", "高级设置");
        iv_advance_setting_stranger_msg.setOnClickListener(this);
        iv_advance_setting_push_system.setOnClickListener(this);
        iv_advance_setting_acceptCheckPush.setOnClickListener(this);

        imageViewList = new ArrayList<ImageView>();
        imageViewList.add(iv_advance_setting_stranger_msg);
        imageViewList.add(iv_advance_setting_push_system);
        imageViewList.add(iv_advance_setting_acceptCheckPush);

        indexs = new ArrayList<String>();
        indexs.add("strangerMessageNotice");
        indexs.add("acceptPush");
        indexs.add("acceptCheckPush");
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData()
    {
        UserExtraInfo info = UserExtraInfo.queryBy(dwID);
        if (info == null || info.getNoticeStrangerMessage() == null || info.getAcceptPush() == null
                || info.getAcceptCheckPush() ==null )
        {
            getUserInfo.getAdvanceAuth(dwID, advanceHandler, GET_ADVANCE_AUTH);
        }
        else
        {
            checkBoxIsChecked[0] = info.getNoticeStrangerMessage();
            checkBoxIsChecked[1] = info.getAcceptPush();
            checkBoxIsChecked[2] = info.getAcceptCheckPush();
            initSwitch(checkBoxIsChecked);
        }
    }

    /**
     * 搜索开关控制
     */
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.iv_advance_setting_stranger_msg:
                clickSwitch(0);
                break;
            case R.id.iv_advance_setting_push_system:
                clickSwitch(1);
                break;
            case R.id.iv_advance_setting_acceptCheckPush:
                clickSwitch(2);
                break;
            default:
                break;
        }
    }

    /**
     * 点击开关时
     * 
     * @param i
     */
    private void clickSwitch(int i)
    {
        if (checkBoxIsChecked[i])
        {
            imageViewList.get(i).setImageResource(R.drawable.switch_rectangle_close);
            checkBoxIsChecked[i] = false;
        }
        else
        {
            imageViewList.get(i).setImageResource(R.drawable.switch_rectangle_open);
            checkBoxIsChecked[i] = true;
        }
    }

    /**
     * 返回
     */
    @Click(R.id.main_header_left)
    void back()
    {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("user_id", dwID);
        for (int i = 0; i < checkBoxIsChecked.length; i++)
        {
            if (checkBoxIsChecked[i])
                map.put(indexs.get(i), "true");
            else
                map.put(indexs.get(i), "false");
        }
        String auth = JSON.toJSONString(map);
        setUserInfo.uploadAdvanceAuth(auth, advanceHandler, UPLOAD_ADVANCE_AUTH);
    }


    /**
     * 用网络请求的数据初始化开关
     * @param SW
     */
    private void initSwitch(Boolean[] SW)
    {
        for (int i = 0; i < SW.length; i++)
        {
            if (!SW[i])
                imageViewList.get(i).setImageResource(R.drawable.switch_rectangle_close);
            else
                imageViewList.get(i).setImageResource(R.drawable.switch_rectangle_open);
        }
    };
    
    
    Handler advanceHandler = new Handler()
    {
        public void handleMessage(Message msg) {
            switch (msg.what)
            {
                case GET_ADVANCE_AUTH:
                    if(msg.arg1 == 0)
                    {
                        /** 失败 **/
                        getAdvanceAuthFailure();
                        
                    }else if(msg.arg1  == 1)
                    {
                        /** 成功 **/
                        getAdvanceAuthSuccess(msg);
                    }
                    break;
                case UPLOAD_ADVANCE_AUTH:
                    if(msg.arg1 == 0)
                    {
                        /** 失败 **/
                        uploadAdvanceAuthFailure();
                        
                    }else if(msg.arg1  == 1)
                    {
                        /** 成功 **/
                        uploadAdvanceAuthSuccess();
                    }
                    break;
                default:
                    break;
            }
        };
    };

    /**
     * 从服务器获取高级设置开关权限成功
     * @param msg
     */
    protected void getAdvanceAuthSuccess(Message msg)
    {
        JSONObject jObject = JSON.parseObject(msg.obj.toString());
        UserExtraInfo info = UserExtraInfo.queryBy(dwID);
        if (info == null)
        {
            info = new UserExtraInfo();
        }

        Boolean noticeStrangerMsg = jObject.getBooleanValue("strangerMsg");
        if (noticeStrangerMsg != null)
        {
            checkBoxIsChecked[0] = noticeStrangerMsg;
            info.setNoticeStrangerMessage(noticeStrangerMsg);
        }

        Boolean acceptPush = jObject.getBooleanValue("acceptPush");
        if (acceptPush != null)
        {
            checkBoxIsChecked[1] = acceptPush;
            info.setAcceptPush(acceptPush);
        }

        Boolean acceptCheckPush = jObject.getBooleanValue("acceptCheckPush");
        if (acceptCheckPush != null)
        {
            checkBoxIsChecked[2] = acceptCheckPush;
            info.setAcceptCheckPush(acceptCheckPush);
        }
        
        info.save();
        initSwitch(checkBoxIsChecked);
    }


    /**
     * 从服务器获取高级设置开关权限失败
     */
    protected void getAdvanceAuthFailure()
    {
        for (int i = 0; i < items; i++)
        {
            checkBoxIsChecked[i] = false;
        }
    }

    /**
     * 上传高级设置开关权限到服务器成功
     */
    protected void uploadAdvanceAuthSuccess()
    {
        UserExtraInfo userInfo = UserExtraInfo.queryBy(dwID);
        if (userInfo != null)
        {
            if (checkBoxIsChecked[0])
                userInfo.setNoticeStrangerMessage(true);
            else
                userInfo.setNoticeStrangerMessage(false);

            if (checkBoxIsChecked[1])
                userInfo.setAcceptPush(true);
            else
                userInfo.setAcceptPush(false);
            
            if (checkBoxIsChecked[2])
                userInfo.setAcceptCheckPush(true);
            else
                userInfo.setAcceptCheckPush(false);
            userInfo.save();
        }
        else
        {
            LogUtils.i(TAG, "保存修改的数据时，检测到UserInfo为空");
        }
        toast.show("设置成功");
        finish();
    }
    
    
    /**
     * 上传高级设置开关权限到服务器失败
     */
    protected void uploadAdvanceAuthFailure()
    {
        toast.show("设置失败");
        finish();
    }

}
