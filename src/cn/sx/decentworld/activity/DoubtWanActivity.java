package cn.sx.decentworld.activity;

import java.io.File;
import java.util.HashMap;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.R;
import cn.sx.decentworld.common.ConstantNet;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.fragment.DoubtWanFirstStepFragment;
import cn.sx.decentworld.fragment.DoubtWanFirstStepFragment.OnDoubtWanFirstClickListener;
import cn.sx.decentworld.fragment.DoubtWanSecondStepFragment;
import cn.sx.decentworld.fragment.DoubtWanSecondStepFragment.OnDoubtWanSecondClickListener;
import cn.sx.decentworld.fragment.DoubtWanThirdStepFragment;
import cn.sx.decentworld.fragment.DoubtWanThirdStepFragment.OnDoubtWanThirdClickListener;
import cn.sx.decentworld.logSystem.LogUtils;
import cn.sx.decentworld.network.SendUrl;
import cn.sx.decentworld.network.SendUrl.HttpCallBack;
import cn.sx.decentworld.network.entity.ResultBean;
import cn.sx.decentworld.utils.ToastUtil;

public class DoubtWanActivity extends BaseFragmentActivity implements OnDoubtWanFirstClickListener,
		OnDoubtWanSecondClickListener, OnDoubtWanThirdClickListener, OnClickListener {
	private static final String TAG = "DoubtWanActivity";
	private ImageView ivBack;
	private DoubtWanFirstStepFragment mDoubtFirstStepFragment;
	private DoubtWanSecondStepFragment mDoubtWanSecondStepFragment;
	private DoubtWanThirdStepFragment mDoubtWanThirdStepFragment;
	private SendUrl mSendUrl;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_doubt_wan);
		initData();
	}

	private void initData() {
		mSendUrl = new SendUrl(this);
		ivBack = (ImageView) findViewById(R.id.iv_back);
		ivBack.setOnClickListener(this);
		mDoubtFirstStepFragment = new DoubtWanFirstStepFragment();
		mDoubtFirstStepFragment.setOnDoubtWanFirstClickListener(this);
		mDoubtWanSecondStepFragment = new DoubtWanSecondStepFragment();
		mDoubtWanSecondStepFragment.setOnDoubtWanSecondClickListener(this);
		mDoubtWanThirdStepFragment = new DoubtWanThirdStepFragment();
		mDoubtWanThirdStepFragment.setOnDoubtWanThirdClickListener(this);
		getSupportFragmentManager().beginTransaction().add(R.id.fl_doubt_wan_container, mDoubtFirstStepFragment, "dff").commit();
	}

	@Override
	public void OnDoubtFirstClick(View view) {
		switch (view.getId()) {
		case R.id.tv_check:
			getSupportFragmentManager().beginTransaction()
					.setCustomAnimations(R.anim.fade_out, R.anim.fade_in, R.anim.fade_out, R.anim.fade_in)
					.hide(mDoubtFirstStepFragment).add(R.id.fl_doubt_wan_container, mDoubtWanSecondStepFragment, "dsf")
					.addToBackStack(null).commit();
			break;
		}
	}

	@Override
	public void OnDoubtWanSecondClick(View view) {
		switch (view.getId()) {
		case R.id.tv_talent:
			mDoubtWanThirdStepFragment.setTalentOrMoney(1);
			getSupportFragmentManager().beginTransaction()
					.setCustomAnimations(R.anim.fade_out, R.anim.fade_in, R.anim.fade_out, R.anim.fade_in)
					.hide(mDoubtWanSecondStepFragment).add(R.id.fl_doubt_wan_container, mDoubtWanThirdStepFragment, "dtf")
					.addToBackStack(null).commit();
			break;
		case R.id.tv_money:
			mDoubtWanThirdStepFragment.setTalentOrMoney(0);
			getSupportFragmentManager().beginTransaction()
					.setCustomAnimations(R.anim.fade_out, R.anim.fade_in, R.anim.fade_out, R.anim.fade_in)
					.hide(mDoubtWanSecondStepFragment).add(R.id.fl_doubt_wan_container, mDoubtWanThirdStepFragment, "dtf")
					.addToBackStack(null).commit();
			break;
		case R.id.tv_commom_service:
			mDoubtWanThirdStepFragment.setTalentOrMoney(2);
			getSupportFragmentManager().beginTransaction()
					.setCustomAnimations(R.anim.fade_out, R.anim.fade_in, R.anim.fade_out, R.anim.fade_in)
					.hide(mDoubtWanSecondStepFragment).add(R.id.fl_doubt_wan_container, mDoubtWanThirdStepFragment, "dtf")
					.addToBackStack(null).commit();
			break;
		}
	}

	@Override
	public void OnDoubtWanThirdClick(View view) {
		switch (view.getId()) {
		case R.id.tv_ensure:
			uploadMaterial();
			break;
		default:
			break;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_back:
			if (getSupportFragmentManager().getBackStackEntryCount() <= 0) {
				finish();
				return;
			}
			getSupportFragmentManager().popBackStack();
			break;
		default:
			break;
		}
	}

	public void uploadMaterial() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put(Constants.DW_ID, DecentWorldApp.getInstance().getDwID());
		map.put("starType", "" + mDoubtWanThirdStepFragment.talentOrMoney);
		map.put("introduce", mDoubtWanThirdStepFragment.materail);
		map.put("agentCode", mDoubtWanThirdStepFragment.agentCode);
		File[] file = new File[5];
		for (int i = 0; i < mDoubtWanThirdStepFragment.mVpFragment.mPicPaths.size(); i++) {
			file[i] = new File(mDoubtWanThirdStepFragment.mVpFragment.mPicPaths.get(i));
		}
		mSendUrl.httpRequestWithImage(map, file, Constants.CONTEXTPATH + ConstantNet.API_WAN_UPLOAD_MATERIAL, new HttpCallBack() {

			@Override
			public void onSuccess(String response, ResultBean msg) {
				LogUtils.d(TAG, "uploadMaterial---" + msg.toString());
				if (2222 == msg.getResultCode()) {
					showToast("成功");
					finish();
				} else {
					showToast(msg.getMsg());
				}
			}

			@Override
			public void onFailure(String e) {
				LogUtils.d(TAG, "uploadMaterial error " + e);
				showToast(Constants.NET_WRONG);
			}
		});
	}

	private void showToast(final String data) {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				ToastUtil.showToast(data);
			}
		});
	}
}
