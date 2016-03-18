/**
 * 
 */
package cn.sx.decentworld.activity;

import android.content.Intent;
import android.widget.LinearLayout;
import cn.sx.decentworld.R;
import cn.sx.decentworld.component.Common;
import cn.sx.decentworld.component.TitleBar;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.dialog.ReminderDialog;
import cn.sx.decentworld.dialog.ReminderDialog.ReminderListener;
import cn.sx.decentworld.utils.ViewUtil;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.LongClick;
import com.googlecode.androidannotations.annotations.ViewById;

/**
 * @ClassName: MeSettingActivity.java
 * @Description:
 * @author: cj
 * @date: 2015年7月23日 下午5:54:50
 */
@EActivity(R.layout.activity_me_setting)
public class MeSettingActivity extends BaseFragmentActivity
{
    private static final String TAG = "MeSettingActivity";
    /**
     * 工具类
     */
    @Bean
    ToastComponent toast;
    @Bean
    TitleBar titleBar;
    @Bean
    Common common;
    /**
     * 界面资源
     */
    @ViewById(R.id.ll_me_setting_root)
    LinearLayout ll_me_setting_root;

    /**
     * 入口
     */
    @AfterViews
    public void init()
    {
        ViewUtil.scaleContentView(ll_me_setting_root);
        titleBar.setTitleBar("我", "设置");
    }

    /**
     * 隐私设置
     */
    @Click(R.id.ll_me_setting_privacy_setting)
    void privacySetting()
    {
        Intent intent = new Intent(MeSettingActivity.this , PrivacySettingActivity_.class);
        startActivity(intent);
    }

    /**
     * 高级设置
     */
    @Click(R.id.ll_me_setting_advance_setting)
    void advanceSetting()
    {
        Intent intent = new Intent(MeSettingActivity.this , AdvanceSettingActivity_.class);
        startActivity(intent);
    }

    /**
     * 关于我们
     */
    @Click(R.id.ll_me_setting_about_us)
    void aboutUs()
    {
        Intent intent = new Intent(MeSettingActivity.this , AboutUsActivity_.class);
        startActivity(intent);
    }

    /**
     * 退出程序
     */
    @Click(R.id.ll_me_setting_exit)
    void exit()
    {
        ReminderDialog dialog = new ReminderDialog();
        dialog.setInfo("你真的要残忍退出吗？");
        dialog.setListener(new ReminderListener()
        {
            @Override
            public void confirm()
            {
                common.loginout();
            }
        });
        dialog.show(getSupportFragmentManager(), "dialog");
    }

    /**
     * 返回
     */
    @Click(R.id.main_header_left)
    void setBackBtn()
    {
        finish();
    }

    /**
     * 长按菜单设置声音（测试使用，发布时去掉）
     */
    @LongClick(R.id.ll_setting_voice)
    void setVoice()
    {
        Intent intent = new Intent(this , SettingVoice_.class);
        startActivity(intent);
    }
}
