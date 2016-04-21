/**
 * 
 */
package cn.sx.decentworld.activity;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.R;
import cn.sx.decentworld.component.TitleBar;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.engine.SettingEngine;
import cn.sx.decentworld.listener.NetCallback;
import cn.sx.decentworld.utils.AES;
import cn.sx.decentworld.utils.PromptUtils;
import cn.sx.decentworld.utils.sputils.UserInfoHelper;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

/**
 * @ClassName: ModificationBankCardOne.java
 * @Description:修改登录密码
 * @author: cj
 * @date: 2015年7月25日 下午3:00:47
 */
@EActivity(R.layout.activity_modify_password)
public class ModifyPasswordActivity extends BaseFragmentActivity implements OnClickListener
{
    private static final String TAG = "ModificationPasswordOne";
    @Bean
    TitleBar titleBar;
    @Bean
    ToastComponent toast;
    @ViewById(R.id.et_old_password)
    EditText et_old_password;
    @ViewById(R.id.et_new_password)
    EditText et_new_password;
    @ViewById(R.id.et_new_password_more)
    EditText et_new_password_more;
    @ViewById(R.id.btn_modification_password_one_next)
    Button btn_ensure;
    
    private String newPwd, oldpwd;
    private PromptUtils mPromptUtils;

    /**
     * 
     */
    @AfterViews
    public void init()
    {
        titleBar.setTitleBar("返回", "修改密码");
        btn_ensure.setOnClickListener(this);
        mPromptUtils = new PromptUtils(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_modification_password_one_next:
                if (et_old_password.length() <= 0)
                {
                    toast.show("旧密码不能为空");
                    return;
                }
                
                if (et_new_password_more.length() <= 0 || et_new_password.length() <= 0)
                {
                    toast.show("新密码不能为空");
                    return;
                }
                
                if(!et_new_password.getText().toString().equals(et_new_password_more.getText().toString()))
                {
                    toast.show("新密码前后两次不一致");
                    return;
                }
                
                String pwd = AES.encode(et_old_password.getText().toString());
                if (!DecentWorldApp.getInstance().getPassword().equals(pwd))
                {
                    toast.show("旧密码输入错误");
                    return;
                }
                resetPassword();
                break;
        }
    }

    /**
     * 返回
     */
    @Click(R.id.main_header_left)
    void back()
    {
        finish();
    }
    
    /**
     * 重置密码
     */
    private void resetPassword()
    {
        mPromptUtils.showProgressDialog();
        oldpwd = et_old_password.getText().toString();
        newPwd = et_new_password.getText().toString();
        SettingEngine.getInstance().resetPassword(oldpwd, newPwd, new NetCallback()
        {
            @Override
            public void onSuccess(String newPwd)
            {
                mPromptUtils.showToast("密码修改成功");
                //同时修改sp和内存中的数据
                DecentWorldApp.getInstance().setPassword(newPwd);
                UserInfoHelper.setPassword(newPwd);
                mPromptUtils.hideProgressDialog();
                finish();
            }
            
            @Override
            public void onFailure(String cause)
            {
                toast.show(cause);
                mPromptUtils.hideProgressDialog();
            }
        });
       
    }

}
