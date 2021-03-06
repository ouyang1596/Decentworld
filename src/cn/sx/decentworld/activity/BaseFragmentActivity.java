/**
 * 
 */
package cn.sx.decentworld.activity;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Window;
import android.view.WindowManager;
import cn.sx.decentworld.utils.ExitAppUtils;

import com.googlecode.androidannotations.annotations.EActivity;
import com.umeng.analytics.MobclickAgent;

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

	/**
	 * Activity创建
	 */
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		ExitAppUtils.getInstance().addActivity(this);
		mContext = this;
	}

	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	/**
	 * Activity销毁
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		ExitAppUtils.getInstance().delActivity(this);
	}

	protected void startIntent(Class clazz) {
		startActivity(new Intent(mContext, clazz));
		finish();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		FragmentManager fm = getSupportFragmentManager();
		int index = requestCode >> 16;
		if (index != 0) {
			index--;
			if (fm.getFragments() == null || index < 0 || index >= fm.getFragments().size()) {
				return;
			}
			Fragment frag = fm.getFragments().get(index);
			if (frag == null) {
			} else {
				handleResult(frag, requestCode, resultCode, data);
			}
			return;
		}

	}

	/**
	 * 递归调用，对所有子Fragement生效
	 * 
	 * @param frag
	 * @param requestCode
	 * @param resultCode
	 * @param data
	 */
	private void handleResult(Fragment frag, int requestCode, int resultCode, Intent data) {
		frag.onActivityResult(requestCode & 0xffff, resultCode, data);
		List<Fragment> frags = frag.getChildFragmentManager().getFragments();
		if (frags != null) {
			for (Fragment f : frags) {
				if (f != null)
					handleResult(f, requestCode, resultCode, data);
			}
		}
	}
}
