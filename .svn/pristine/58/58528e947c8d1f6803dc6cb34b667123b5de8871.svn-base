/**
 * 
 */
package cn.sx.decentworld.activity;

import java.io.File;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;
import cn.sx.decentworld.R;
import cn.sx.decentworld.callback.HttpCallback;
import cn.sx.decentworld.common.CommUtil;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.component.TitleBar;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.manager.VersionInfoManager;
import cn.sx.decentworld.service.DownloadListenService;
import cn.sx.decentworld.utils.LogUtils;
import cn.sx.decentworld.utils.NetworkUtils;
import cn.sx.decentworld.utils.SettingSp;
import cn.sx.decentworld.utils.ToastUtils;
import cn.sx.decentworld.utils.UpgradeVersion;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

/**
 * @ClassName: PrivacySettingActivity.java
 * @Description: 
 * @author: cj
 * @date: 2015年7月24日 上午10:46:33
 */
@EActivity(R.layout.activity_about_us)
public class AboutUsActivity extends BaseFragmentActivity
{
	private static final String TAG = "AboutUsActivity";
	
	@Bean
	TitleBar titleBar;
	@Bean
	ToastComponent toast;
	
	/**
	 * 显示软件版本号 eg.1
	 */
	@ViewById(R.id.tv_about_us_software_version_code)
	TextView tv_about_us_software_version_code;
	
	/**
	 * 显示软件版本名 eg.1.0.0
	 */
	@ViewById(R.id.tv_about_us_software_version_name)
	TextView tv_about_us_software_version_name;
	
	/**
	 * 显示数据库的版本号 eg.1
	 */
	@ViewById(R.id.tv_about_us_database_version_code)
	TextView tv_about_us_database_version_code;
	
	@AfterViews
	public void init()
	{
		titleBar.setTitleBar("设置", "关于我们");
		initData();
	}

	/**
	 * 初始化数据
	 */
	private void initData()
	{
		checkSFVersionCode();
		checkSFVersionName();
		checkDBVersionCode();
	}

	@Click(R.id.main_header_left)
	void setBackBtn()
	{
		finish();
	}
	
	/**
	 * 检查软件版本号
	 */
	private void checkSFVersionCode()
	{
		tv_about_us_software_version_code.setText(""+new VersionInfoManager(this).getAppVersionCode());
	}
	
	/**
	 * 检查软件版本名
	 */
	private void checkSFVersionName()
	{
		tv_about_us_software_version_name.setText("v"+new VersionInfoManager(this).getAppVersionName());
	}
	
	/**
	 * 检查数据库的版本号
	 */
	private void checkDBVersionCode()
	{
		int dbVersionCode = new VersionInfoManager(this).getApplicationIntMetaData("AA_DB_VERSION");
		tv_about_us_database_version_code.setText(dbVersionCode+"");
	}
	
	/**
	 *手动检查是否有新的版本
	 * @param v
	 */
	@Click(R.id.tv_about_us_check_new_version)
	void checkNewVersion(View v)
	{
		/** 如果有网，则进行网络升级检查 **/
		if (NetworkUtils.isNetWorkConnected(AboutUsActivity.this))
		{
			/**
			 * 检查是否满足更新的条件
			 */
			new UpgradeVersion(AboutUsActivity.this,true);
		}
		else
		{
			toast.show("请检查网络！");
		}
	}

}
