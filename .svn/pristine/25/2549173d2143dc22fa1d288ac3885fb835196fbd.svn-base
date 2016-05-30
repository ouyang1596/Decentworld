package cn.sx.decentworld.activity;

import org.simple.eventbus.EventBus;

import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.R;
import cn.sx.decentworld.common.ConstantIntent;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.common.NotifyByEventBus;
import cn.sx.decentworld.engine.BenefitEngine;
import cn.sx.decentworld.engine.BenefitEngine.SetPaycardAccountLitener;
import cn.sx.decentworld.logSystem.LogUtils;
import cn.sx.decentworld.utils.ToastUtil;
import cn.sx.decentworld.wxapi.WXEntryActivity;

public class WeixinInComeActivity extends BaseFragmentActivity implements OnClickListener {
	private static final String TAG = "WeixinInComeActivity";
	private ImageView ivBack;
	private TextView tvBindWx;

	private String tempToken;
	private IWXAPI wxReq;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_weixin_in_come);
		WGetIntent();
		initView();
	}

	private void WGetIntent() {
		tempToken = getIntent().getStringExtra("tempToken");
	}

	private void initView() {
		ivBack = (ImageView) findViewById(R.id.iv_bind_wx_back);
		ivBack.setOnClickListener(this);
		tvBindWx = (TextView) findViewById(R.id.tv_bind_account_detail_wx);
		tvBindWx.setOnClickListener(this);
		initData();
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		wxReq = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
	}

	/**
	 * 点击事件
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_bind_account_detail_wx:
			DecentWorldApp.WX_AUTHORIZE_STATE = 1;
			SendAuth.Req req = new SendAuth.Req();
			req.scope = "snsapi_userinfo";
			req.state = "dw";
			wxReq.sendReq(req);
			break;
		case R.id.iv_bind_wx_back:
			finish();
			break;
		default:
			break;
		}
	}

	/**
	 * 提交到服务器
	 */
	private void setWx(final String code) {
		BenefitEngine benefitEngine = new BenefitEngine();
		benefitEngine.setPaycardAccount(1, code, tempToken, new SetPaycardAccountLitener() {
			@Override
			public void onSuccess(int maccountType, String account) {
				LogUtils.i(TAG, "成功");
				EventBus.getDefault().post(code, NotifyByEventBus.NT_RETURN_WX_CODE);
				finish();
			}

			@Override
			public void onFailure(String cause) {
				LogUtils.i(TAG, "失败");
				ToastUtil.showToast(cause);
				finish();
			}
		});
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		String code = intent.getStringExtra(ConstantIntent.WX_CODE);
		setWx(code);
	}
}
