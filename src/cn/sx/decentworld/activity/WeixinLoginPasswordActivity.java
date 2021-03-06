package cn.sx.decentworld.activity;

import java.util.HashMap;

import org.simple.eventbus.EventBus;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.sx.decentworld.R;
import cn.sx.decentworld.common.CommUtil;
import cn.sx.decentworld.common.ConstantIntent;
import cn.sx.decentworld.common.ConstantNet;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.common.NotifyByEventBus;
import cn.sx.decentworld.logSystem.LogUtils;
import cn.sx.decentworld.network.SendUrl;
import cn.sx.decentworld.network.SendUrl.HttpCallBack;
import cn.sx.decentworld.network.entity.ResultBean;
import cn.sx.decentworld.utils.AES;
import cn.sx.decentworld.utils.ToastUtil;

import com.android.volley.Request.Method;

public class WeixinLoginPasswordActivity extends BaseFragmentActivity implements OnClickListener {
	private static final String TAG = "WeixinLoginPasswordActivity";
	private static final int OCCUPATION_REQUEST_CODE = 200;
	private TextView tvOccupation;
	private EditText etPassword;
	private TextView tvHeadTitle;
	private ImageView ivBack;
	private Button btnOk;// 注册过了就直接登录，没有注册就去注册一下
	private LinearLayout llOccupation;
	private boolean registered;
	private String dwID;
	private SendUrl mSendUrl;
	private RelativeLayout relWeixinLoginPassword;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_weixin_login_password);
		WGetIntent();
		initView();
		show();
	}

	private void WGetIntent() {
		registered = getIntent().getBooleanExtra(ConstantIntent.WX_REGISTERED, false);
		dwID = getIntent().getStringExtra(Constants.DW_ID);
	}

	private void initView() {
		mSendUrl = new SendUrl(this);
		tvHeadTitle = (TextView) findViewById(R.id.tv_header_title);
		tvHeadTitle.setText("填写信息");
		ivBack = (ImageView) findViewById(R.id.iv_back);
		ivBack.setVisibility(View.VISIBLE);
		ivBack.setOnClickListener(this);
		llOccupation = (LinearLayout) findViewById(R.id.ll_occupation);
		llOccupation.setOnClickListener(this);
		tvOccupation = (TextView) findViewById(R.id.tv_occupation);
		etPassword = (EditText) findViewById(R.id.et_password);
		btnOk = (Button) findViewById(R.id.btn_OK);
		btnOk.setOnClickListener(this);
		relWeixinLoginPassword = (RelativeLayout) findViewById(R.id.rel_weixin_login_password);
		relWeixinLoginPassword.setOnClickListener(this);
	}

	private void show() {
		if (registered) {
			llOccupation.setVisibility(View.GONE);
		} else {
			llOccupation.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_occupation:
			startActivityForResult(new Intent(mContext, OccupationActivity_.class), OCCUPATION_REQUEST_CODE);
			break;
		case R.id.iv_back:
			finish();
			break;
		case R.id.btn_OK:
			String occupation = tvOccupation.getText().toString();
			String password = etPassword.getText().toString();
			if (registered) {
				if (CommUtil.isBlank(password)) {
					ToastUtil.showToast("请输入密码");
					return;
				}
				password = AES.encode(password);
				String data = "{\"dwID\":\"" + dwID + "\",password:\"" + password + "\"}";
				EventBus.getDefault().post(data, NotifyByEventBus.NT_WX_LOGIN);
				finish();
			} else {
				if (CommUtil.isBlank(occupation)) {
					ToastUtil.showToast("请输入职业");
					return;
				}
				if (CommUtil.isBlank(password)) {
					ToastUtil.showToast("请输入密码");
					return;
				}
				password = AES.encode(password);
				wxRegister(occupation, password);
			}
			break;
		case R.id.rel_weixin_login_password:
			closeKeyboard(etPassword);
			break;
		}
	}

	// private Handler mWXRegisterHandler = new Handler() {
	// public void handleMessage(android.os.Message msg) {
	//
	// };
	// };

	private void wxRegister(String occupation, final String password) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("occupation", occupation);
		map.put("password", password);
		map.put("dwID", dwID);
		showProgressDialog();
		mSendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH + ConstantNet.API_WXREGISTER, Method.POST, new HttpCallBack() {

			@Override
			public void onSuccess(String resultJSON, ResultBean resultBean) {
				hideProgressDialog();
				LogUtils.d(TAG, "wxRegister " + resultJSON);
				// mWXRegisterHandler.sendEmptyMessage(0);
				if (2007 == resultBean.getResultCode()) {
					String data = "{dwID:" + dwID + ",password:" + password + "}";
					LogUtils.d(TAG, "data " + data);
					EventBus.getDefault().post(data, NotifyByEventBus.NT_WX_LOGIN);
					finish();
				} else {
					showToast(resultBean.getMsg());
				}
			}

			@Override
			public void onFailure(String e) {
				hideProgressDialog();
				LogUtils.e(TAG, "wxRegister error " + e);
				showToast(Constants.NET_WRONG);
				// mWXRegisterHandler.sendEmptyMessage(0);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (null == data) {
			return;
		}
		if (OCCUPATION_REQUEST_CODE == requestCode) {
			String occupation = data.getStringExtra(OccupationActivity.OCCUPATION);
			if (occupation.contains("全部")) {
				occupation = occupation.substring(0, occupation.indexOf("全部"));
			}
			tvOccupation.setText(occupation);
		}
	}

	private void showToast(final String data) {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				ToastUtil.showToast(data);
			}
		});
	}

	private ProgressDialog mProDialog;

	private void showProgressDialog() {
		if (null == mProDialog) {
			mProDialog = ProgressDialog.show(this, null, "loading");
		} else {
			mProDialog.show();
		}
	}

	private void hideProgressDialog() {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				if (null != mProDialog) {
					mProDialog.hide();
				}
			}
		});

	}

	/**
	 * 关闭软键盘
	 * 
	 * @param mEditText输入框
	 * @param mContext上下文
	 */
	public void closeKeyboard(EditText mEditText) {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

		imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
	}
}
