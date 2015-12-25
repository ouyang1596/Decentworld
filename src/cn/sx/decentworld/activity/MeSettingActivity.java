/**
 * 
 */
package cn.sx.decentworld.activity;

import android.content.Intent;
import android.widget.LinearLayout;
import cn.sx.decentworld.R;
import cn.sx.decentworld.component.TitleBar;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.component.ui.MainFragmentComponent;
import cn.sx.decentworld.utils.ViewUtil;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
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
	@Bean
	ToastComponent toast;
	@Bean
	TitleBar titleBar;
	@Bean
	MainFragmentComponent mainComponent;

	@ViewById(R.id.ll_me_setting_root)
	LinearLayout ll_me_setting_root;

	@AfterViews
	public void init()
	{
		ViewUtil.scaleContentView(ll_me_setting_root);
		titleBar.setTitleBar("我", "设置");
	}

	/**
	 * 贵人窗口
	 */
	@Click(R.id.ll_me_setting_magnate_window)
	void magnateWindow()
	{
		Intent intent = new Intent(MeSettingActivity.this , MagnateWindowActivity_.class);
		startActivity(intent);
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
	 * 推荐收益
	 */
	@Click(R.id.ll_me_setting_recommend_benefit)
	void cashIncome()
	{
		Intent intent = new Intent(MeSettingActivity.this , RecommendBenefitActivity_.class);
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
		mainComponent.loginout();
	}

	/**
	 * 返回
	 */
	@Click(R.id.main_header_left)
	void setBackBtn()
	{
		finish();
	}

}
