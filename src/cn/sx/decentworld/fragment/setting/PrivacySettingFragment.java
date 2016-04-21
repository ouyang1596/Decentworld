/**
 * 
 */
package cn.sx.decentworld.fragment.setting;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.R;
import cn.sx.decentworld.activity.ModifyPasswordActivity_;
import cn.sx.decentworld.activity.ModifyPhoneNumActivity;
import cn.sx.decentworld.activity.ModifyPhoneNumActivity_;
import cn.sx.decentworld.bean.UserInfo;
import cn.sx.decentworld.common.CommUtil;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.dialog.ReminderDialog;
import cn.sx.decentworld.dialog.ReminderDialog.ReminderListener;
import cn.sx.decentworld.dialog.VerifyLoginPwdLocalDialog;
import cn.sx.decentworld.dialog.VerifyLoginPwdLocalDialog.VerifyLoginPwdLocalListener;
import cn.sx.decentworld.engine.UserInfoEngine;
import cn.sx.decentworld.logSystem.LogUtils;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EFragment;
import com.googlecode.androidannotations.annotations.ViewById;

/**
 * @ClassName: PrivacySettingActivity.java
 * @Description: 隐私设置
 * @author: cj
 * @date: 2015年7月24日 上午10:46:33
 */
@EFragment(R.layout.activity_privacy_setting)
public class PrivacySettingFragment extends Fragment
{
    private static final String TAG = "PrivacySettingFragment";
    public static final int REQUEST_MOBILE = 2;
    public static final int REQUEST_RESET_PWD = 3;

    /**
     * 工具类
     */
    @Bean
    ToastComponent toast;

    /**
     * 界面资源
     */
    @ViewById(R.id.ll_privacy_setting_root)
    LinearLayout mLlRoot;
    @ViewById(R.id.tv_phone_number)
    TextView mTvPhoneNum;

    private FragmentManager fragmentManager;
    private VerifyLoginPwdLocalDialog checkIdentityDialog;
    private ReminderDialog reminderDialog;

    @AfterViews
    public void init()
    {
        fragmentManager = ((FragmentActivity) getActivity()).getSupportFragmentManager();
    }

    /**
     * 点击事件
     * 
     * @param view
     */
    @Click(
    { R.id.privacy_setting_modification_phone_number, R.id.privacy_setting_modification_password })
    void bankCardModification(View view)
    {
        checkIdentityDialog = new VerifyLoginPwdLocalDialog();
        checkIdentityDialog.setListener(listener);
        checkIdentityDialog.setClickView(view);
        checkIdentityDialog.show(fragmentManager, "CheckIdentityDialog");
    }

    // 认证密码监听器
    VerifyLoginPwdLocalListener listener = new VerifyLoginPwdLocalListener()
    {
        @Override
        public void onConfirm(String pwd)
        {
            if (pwd.equals(DecentWorldApp.getInstance().getPassword()))
            {
                Intent intent;
                switch (checkIdentityDialog.getClickView().getId())
                {
                    case R.id.privacy_setting_modification_phone_number:
                        intent = new Intent(getActivity() , ModifyPhoneNumActivity_.class);
                        startActivityForResult(intent, REQUEST_MOBILE);
                        break;

                    case R.id.privacy_setting_modification_password:
                        intent = new Intent(getActivity() , ModifyPasswordActivity_.class);
                        startActivity(intent);
                        break;
                }
            }
            else
            {
                // 弹出提醒对话框
                reminderDialog = new ReminderDialog();
                reminderDialog.setListener(listener2);
                reminderDialog.setInfo("密码输入错误");
                reminderDialog.show(fragmentManager, "ReminderDialog");
            }
        }
    };

    // 提醒对话框监听器
    ReminderListener listener2 = new ReminderListener()
    {
        @Override
        public void confirm()
        {
            reminderDialog.dismiss();
        }
    };

    @Click(R.id.main_header_left)
    void setBackBtn()
    {
        getActivity().finish();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mLlRoot.getWindowToken(), 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        super.onActivityResult(requestCode, resultCode, intent);
        LogUtils.i(TAG, "onActivityResult");
        if (null != intent)
        {
            switch (requestCode)
            {
                case REQUEST_MOBILE:
                    String mobile = intent.getStringExtra(ModifyPhoneNumActivity.MOBILE);
                    mTvPhoneNum.setText("*******" + mobile.substring(mobile.length() - 4));
                    break;
                case REQUEST_RESET_PWD:

                    break;
            }
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        //显示电话号码
        UserInfo userInfo = UserInfoEngine.getInstance().getUserInfo();
        String phoneNum = userInfo.getPhoneNum();
        if(CommUtil.isNotBlank(phoneNum))
            mTvPhoneNum.setText(phoneNum);
    }

}
