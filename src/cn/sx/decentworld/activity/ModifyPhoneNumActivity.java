/**
 * 
 */
package cn.sx.decentworld.activity;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import cn.sx.decentworld.R;
import cn.sx.decentworld.abstractclass.AbstractTextWatcher;
import cn.sx.decentworld.component.TitleBar;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.dialog.ReminderDialog;
import cn.sx.decentworld.dialog.ReminderDialog.ReminderListener;
import cn.sx.decentworld.engine.SettingEngine;
import cn.sx.decentworld.listener.NetCallback;
import cn.sx.decentworld.logSystem.LogUtils;
import cn.sx.decentworld.network.request.ResetPwdInfo;
import cn.sx.decentworld.utils.DataConvertUtils;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

/**
 * @ClassName: ModificationBankCardOne.java
 * @Description:修改电话号码(该功能暂停开发)
 * @author: cj
 * @date: 2015年7月25日 下午3:00:47
 */
@EActivity(R.layout.activity_modify_phone_num)
public class ModifyPhoneNumActivity extends BaseFragmentActivity implements OnClickListener
{
    private static final String TAG = "ModificationPhoneNumOne";
    public static final String MOBILE = "mobile";
    /**
     * 工具类
     */
    @Bean
    TitleBar titleBar;
    @Bean
    ToastComponent toast;
    @Bean
    ResetPwdInfo resetPwdInfo;

    /**
     * 界面资源
     */
    @ViewById(R.id.et_modification_phone_num_one_number)
    EditText mEtPhoneNum;
    @ViewById(R.id.et_identifying_code)
    EditText mEtIdentifyingCode;
    @ViewById(R.id.btn_modification_phone_num_one_next)
    Button mBtnNext;
    @ViewById(R.id.tv_send)
    TextView mTvSendCode;

    private FragmentManager fm;
    private String tempCode;

    @AfterViews
    public void init()
    {
        titleBar.setTitleBar("返回", "更换手机号码");
        fm = getSupportFragmentManager();
        mEtPhoneNum.addTextChangedListener(new AbstractTextWatcher()
        {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                DataConvertUtils.formatPhone(s.toString(), mEtPhoneNum);
            }
        });
        mBtnNext.setOnClickListener(this);
        mTvSendCode.setOnClickListener(this);
    }


    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.tv_send:
                if (getPhoneNum().length() == 11)
                {
                    String mobile = getPhoneNum();
                    ReminderDialog reminderDialog = new ReminderDialog();
                    reminderDialog.setListener(listener);
                    reminderDialog.setInfo("确认手机号码\n我们将发送验证码到这个手机号码:" + mobile);
                    reminderDialog.show(fm, "ModificationPhoneNumOne");
                }
                else
                {
                    toast.show("电话号码格式不正确");
                }
                break;
            case R.id.btn_modification_phone_num_one_next:
                if (getPhoneNum().length() <= 0)
                {
                    toast.show("请输入手机号码");
                    return;
                }
                if (getPhoneNum().length() != 11)
                {
                    toast.show("手机号码格式不正确");
                    return;
                }

                if (mEtIdentifyingCode.length() <= 0)
                {
                    toast.show("请输入验证码");
                    return;
                }
                if (tempCode.equals(mEtIdentifyingCode.getText().toString().trim()))
                {
                    SettingEngine.getInstance().setPhoneNum(getPhoneNum(), new NetCallback()
                    {
                        @Override
                        public void onSuccess(String msg)
                        {
                            Intent intent = new Intent();
                            intent.putExtra(MOBILE, getPhoneNum());
                            setResult(0, intent);
                            finish();
                        }

                        @Override
                        public void onFailure(String cause)
                        {
                            toast.show(cause);
                        }
                    });
                }
                else
                {
                    toast.show("验证码不正确");
                }
                break;
        }
    }

    /**
     * 发送验证码提示
     */
    ReminderListener listener = new ReminderListener()
    {
        @Override
        public void confirm()
        {
            SettingEngine.getInstance().getCode(getPhoneNum(), new NetCallback()
            {
                @Override
                public void onSuccess(String code)
                {
                    tempCode = code;
                    toast.show("验证码已发送");
                }

                @Override
                public void onFailure(String cause)
                {
                    toast.show(cause);
                }
            });
        }
    };

    @Click(R.id.main_header_left)
    void back()
    {
        finish();
    }

    /**
     * 获取标准格式的电话号码
     * 
     * @return
     */
    private String getPhoneNum()
    {
        String phoneNum = mEtPhoneNum.getText().toString().replaceAll(" ", "");
        LogUtils.i(TAG, "phoneNum=" + phoneNum);
        return phoneNum;
    }

}
