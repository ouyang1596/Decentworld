package cn.sx.decentworld.wxapi;

import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.R;
import cn.sx.decentworld.activity.BindAccountActivity;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.network.SendUrl;
import cn.sx.decentworld.network.SendUrl.HttpCallBack;
import cn.sx.decentworld.network.entity.ResultBean;
import cn.sx.decentworld.utils.LogUtils;
import cn.sx.decentworld.utils.ToastUtils;

import com.android.volley.Request.Method;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * @ClassName: BindAccountDetailActivity.java
 * @Description: 绑定账号详细界面
 * @author: cj
 * @date: 2015年12月23日 上午11:51:03
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler,
		OnClickListener {
	private static final String TAG = "WXEntryActivity";
	private TextView tv_bind_account_detail_wx;
	private IWXAPI wxReq;
	private String title;
	private int accountType = 3;// 绑定的账号类型
	private String accountName = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bind_account_detail);
		parseData();
		initView();
		initData();
		initEvent();
	}

	/**
	 * 
	 */
	private void parseData() {
		accountType = getIntent().getIntExtra("accountType", 3);
		accountName = getIntent().getStringExtra("accountName");
		if (accountType == BindAccountActivity.ALIPAY) {
			title = "绑定支付宝账号";
		} else if (accountType == BindAccountActivity.WX) {
			title = "绑定微信账号";
		}
	}

	/**
	 * 
	 */
	private void initView() {
		tv_bind_account_detail_wx = (TextView) findViewById(R.id.tv_bind_account_detail_wx);
	}

	/**
	 * 
	 */
	private void initData() {
		// TODO Auto-generated method stub
		wxReq = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
		wxReq.handleIntent(getIntent(), this);

	}

	/**
	 * 
	 */
	private void initEvent() {
		tv_bind_account_detail_wx.setOnClickListener(this);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		LogUtils.i(TAG, "onNewIntent");
		setIntent(intent);
		wxReq.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
		LogUtils.i(TAG, "onReq");
		switch (req.getType()) {
		case 1:

			break;
		default:
			break;
		}

	}

	@Override
	public void onResp(BaseResp resp) {
		LogUtils.i(TAG, "用户同意:");
		switch (resp.errCode) {
		case BaseResp.ErrCode.ERR_OK:// 用户同意
			LogUtils.i(TAG, "用户同意:" + resp.toString());
			String code = ((SendAuth.Resp) resp).code;
			int accountType = 1;
			String dwID = DecentWorldApp.getInstance().getDwID();
			setPaycardAccount(dwID, accountType, code);
			break;
		case BaseResp.ErrCode.ERR_USER_CANCEL:// 用户取消
			LogUtils.i(TAG, "用户取消");

			break;
		case BaseResp.ErrCode.ERR_AUTH_DENIED:// 用户拒绝授权
			LogUtils.i(TAG, "用户拒绝授权");

			break;
		default:
			LogUtils.i(TAG, "");
			break;
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_bind_account_detail_wx:
			Toast.makeText(this, "微信用户授权", Toast.LENGTH_SHORT).show();
			final SendAuth.Req req = new SendAuth.Req();
			req.scope = "snsapi_userinfo";
			req.state = "dw";
			wxReq.sendReq(req);
			break;

		default:
			break;
		}
	}

	/**
	 * 设置用户提现的账号（支付宝或微信）
	 * 
	 * @param dwID
	 *            自己的id
	 * @param accountType
	 *            账号类型（支付宝 0 | 微信 1）
	 * @param account
	 *            账号
	 * @param activity
	 */
	public void setPaycardAccount(String dwID, final int accountType,
			final String account) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("dwID", dwID);
		map.put("accountType", String.valueOf(accountType));
		map.put("account", account);
		LogUtils.i(TAG, "setPaycardAccount...begin,dwID=" + dwID
				+ ",accountType=" + accountType + ",account=" + account);
		new SendUrl(this).httpRequestWithParams(map, Constants.CONTEXTPATH
				+ "/user/setAccount", Method.POST, new HttpCallBack() {
			@Override
			public void onSuccess(String response, ResultBean msg) {
				// 设置成功 设置失败（原因）
				LogUtils.i(
						TAG,
						"setPaycardAccount...msg.getResultCode="
								+ msg.getResultCode() + ",msg.getMsg="
								+ msg.getMsg() + ",msg.getData="
								+ msg.getData());
				if (msg.getResultCode() == 2222) {
					LogUtils.i(TAG, "setPaycardAccount...success");
					ToastUtils.toast(WXEntryActivity.this, "设置成功");
					// 保存到数据库
					// UserInfo info = UserInfoManager.getUserInfoInstance();
					//
					// //传递到开启界面
					// Intent intent = new Intent();
					// intent.putExtra(PrivacySettingActivity.PAYACCOUNT,
					// account);
					// setResult(activity.RESULT_OK, intent);
					// finish();
				} else if (msg.getResultCode() == 3333) {
					LogUtils.i(TAG, "setPaycardAccount...failure,case by:"
							+ msg.getMsg());
					ToastUtils.toast(WXEntryActivity.this, "设置失败");
				}
			}

			@Override
			public void onFailure(String e) {
				LogUtils.i(TAG, "setPaycardAccount...onFailure,cause by:" + e);
				ToastUtils.toast(WXEntryActivity.this, Constants.NET_WRONG);
			}
		});
	}

}
