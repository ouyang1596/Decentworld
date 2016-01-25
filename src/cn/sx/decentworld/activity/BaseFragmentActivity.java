/**
 * 
 */
package cn.sx.decentworld.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
import android.view.WindowManager;
import cn.sx.decentworld.utils.ExitAppUtils;
import cn.sx.decentworld.utils.LoginHelper;

import com.googlecode.androidannotations.annotations.EActivity;

/**
 * 
 * @ClassName: BaseFragmentActivity.java
 * @Description: FragmentActivity基础类
 * @author: cj
 * @date: 2016年1月8日 上午9:36:41
 */
@EActivity
public class BaseFragmentActivity extends FragmentActivity {
	private static final String TAG = "BaseFragmentActivity";
	public Activity mContext;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN
						| WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		ExitAppUtils.getInstance().addActivity(this);
		mContext = this;
	}

	@Override
	protected void onResume() {
		super.onResume();
		LoginHelper.isLogined(getApplicationContext());
//		DecentWorldApp.getInstance().setCurrentActivity(this);
//		LogUtils.i("BaseFragmentActivity,CurrentActivity", DecentWorldApp
//				.getInstance().getCurrentActivity().getLocalClassName());
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		ExitAppUtils.getInstance().delActivity(this);
	}

	protected void startIntent(Class clazz) {
		startActivity(new Intent(mContext, clazz));
		finish();
	}
}
