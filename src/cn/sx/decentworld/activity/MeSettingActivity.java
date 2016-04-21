
package cn.sx.decentworld.activity;

import android.view.View;
import cn.sx.decentworld.R;
import cn.sx.decentworld.dialog.ReminderDialog;
import cn.sx.decentworld.dialog.ReminderDialog.ReminderListener;
import cn.sx.decentworld.engine.SettingEngine;
import cn.sx.decentworld.fragment.setting.AboutUsFragment;
import cn.sx.decentworld.fragment.setting.AdvanceSettingFragment;
import cn.sx.decentworld.fragment.setting.AdvanceSettingFragment_;
import cn.sx.decentworld.fragment.setting.PrivacySettingFragment;
import cn.sx.decentworld.fragment.setting.PrivacySettingFragment_;
import cn.sx.decentworld.fragment.setting.SettingFragment;
import cn.sx.decentworld.fragment.setting.SettingFragment.OnSettingClickListener;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EActivity;

/**
 * @ClassName: MeSettingActivity.java
 * @author: yj
 * @date: 2015年7月23日 下午5:54:50
 */
@EActivity(R.layout.activity_me_setting)
public class MeSettingActivity extends BaseFragmentActivity implements OnSettingClickListener
{
    //主菜单
    private SettingFragment mSettingFragment;
    //关于我们
    private AboutUsFragment mAboutUsFragment;
    //隐私设置
    private PrivacySettingFragment mPrivacySettingFragment;
    //高级设置
    private AdvanceSettingFragment mAdvanceSettingFragment;

    /**
     * 入口
     */
    @AfterViews
    public void init()
    {
        initView();
    }

    private void initView()
    {
        initFragment();
        getFragmentManager().beginTransaction().add(R.id.fl_setting, mSettingFragment, "msf").commit();
    }

    private void initFragment()
    {
        mSettingFragment = new SettingFragment();
        mSettingFragment.setOnSettingClickListener(this);

        mAboutUsFragment = new AboutUsFragment();
        mPrivacySettingFragment = new PrivacySettingFragment_();
        mAdvanceSettingFragment = new AdvanceSettingFragment_();
        getFragmentManager().beginTransaction().add(R.id.fl_setting_content, mAboutUsFragment, "maf").add(R.id.fl_setting_content, mAdvanceSettingFragment, "maf")
                .add(R.id.fl_setting_content, mPrivacySettingFragment, "mpf").hide(mPrivacySettingFragment).hide(mAdvanceSettingFragment).commit();
    }

    @Override
    public void OnSettingClick(View view)
    {
        switch (view.getId())
        {
            case R.id.tv_about_us:
                getFragmentManager().beginTransaction().show(mAboutUsFragment).hide(mPrivacySettingFragment).hide(mAdvanceSettingFragment).commit();
                break;
            case R.id.tv_privacy_setting:
                getFragmentManager().beginTransaction().hide(mAboutUsFragment).hide(mAdvanceSettingFragment).show(mPrivacySettingFragment).commit();
                break;
            case R.id.tv_advanced_setting:
                getFragmentManager().beginTransaction().hide(mAboutUsFragment).show(mAdvanceSettingFragment).hide(mPrivacySettingFragment).commit();
                break;
            case R.id.tv_exit:
                exit();
                break;
            case R.id.iv_back:
                mAdvanceSettingFragment.uploadStatusInfo();
                finish();
                break;
        }
    }

    /**
     * 退出程序
     */
    private void exit()
    {
        ReminderDialog dialog = new ReminderDialog();
        dialog.setInfo("你真的要退出吗？");
        dialog.setListener(new ReminderListener()
        {
            @Override
            public void confirm()
            {
                SettingEngine.getInstance().loginOut(MeSettingActivity.this);
            }
        });
        dialog.show(getSupportFragmentManager(), "dialog");
    }

}
