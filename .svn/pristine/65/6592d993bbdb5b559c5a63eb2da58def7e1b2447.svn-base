package cn.sx.decentworld.activity;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import cn.sx.decentworld.R;
import cn.sx.decentworld.activity.RegisterMobileActivity.TimeCount;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.component.KeyboardComponent;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.network.request.ResetPwdInfo;
import cn.sx.decentworld.utils.LogUtils;
import cn.sx.decentworld.widget.ClearEditText;

import com.android.volley.Request.Method;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_register_mobile)
public class ForgetPwdMobileActivity extends BaseFragmentActivity implements
		OnClickListener, TextWatcher {
	@ViewById(R.id.tv_header_title)
	TextView tvTitle;
	@ViewById(R.id.iv_back)
	ImageView ivback;
	@ViewById(R.id.et_mobile)
	ClearEditText etMobile;
	@ViewById(R.id.et_code)
	ClearEditText etCode;
	@ViewById(R.id.btn_OK)
	Button btnOk;
	@Bean
	ToastComponent toast;
	private TimeCount timeCount = new TimeCount(60000, 1000);
	public static final String MOBILE = "mobile";
	@Bean
	ResetPwdInfo resetPwdInfo;
	// String code;
	private Handler mGetIdentifyingCodeHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 2222:
				toast.show("验证码已发送到手机");
				// JSONObject object = new JSONObject(msg.obj.toString());
				// code = object.getString("code");
				// LogUtils.e("bm", "CODE--" + code);
				break;
			}
		}
	};
	private Handler mCheckHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 2222:
				startActivity(new Intent(mContext,
						ForgetPwdIdCardActivity_.class));
				break;

			}
		};
	};
	private String mMobile = "";
	@ViewById(R.id.tv_no_receive_code)
	TextView tvNoReceive;

	@AfterViews
	public void init() {
		tvTitle.setText("忘记密码");
		ivback.setVisibility(View.VISIBLE);
		ivback.setOnClickListener(this);
		btnOk.setOnClickListener(this);
		tvNoReceive.setOnClickListener(this);
		btnOk.setText(getResources().getString(R.string.get_identify_code));
		etCode.addTextChangedListener(this);
		etMobileAddTextChangedListener();
	}

	private void etMobileAddTextChangedListener() {
		etMobile.addTextChangedListener(new TextWatcher() {
			public void onTextChanged(CharSequence data, int arg1, int arg2,
					int arg3) {
				if (!(mMobile.equals(data.toString()))) {
					etCode.setText("");
				}
			}

			public void beforeTextChanged(CharSequence data, int arg1,
					int arg2, int arg3) {
			}

			public void afterTextChanged(Editable arg0) {

			}
		});
	}

	private void getIdentifyingCode() {
		if (etMobile.length() <= 0) {
			toast.show("请输入手机号码");
			return;
		}
		if (etMobile.length() != 11) {
			toast.show("手机号码输入不正确");
			return;
		}
		String mobile = etMobile.getText().toString();
		mMobile = mobile;
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("phoneNum", mobile);
		resetPwdInfo.requestCode(map,
				Constants.API_FORGET_PWD_GET_IDENTIFYING_CODE, Method.GET,
				mGetIdentifyingCodeHandler);
		timeCount.start();
		setBtnOk(false);
	}

	private void sendIdentifyingCode() {
		if (etCode.length() <= 0) {
			toast.show("请输入验证码");
			return;
		}
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("phoneNum", mMobile);
		map.put("code", etCode.getText().toString());
		resetPwdInfo.checkCode(map, Constants.API_FORGET_PWD_CHECK_CODE,
				Method.GET, mCheckHandler);
		// if (code.equals(etCode.getText().toString())) {
		// startActivity(new Intent(mContext, ForgetPwdIdCardActivity_.class));
		// } else {
		// toast.show("验证码错误");
		// }
	}

	// 对键盘的操作
	@Bean
	KeyboardComponent keyboardComponent;

	/**
	 * 打开软键盘
	 */
	private void openKeyboard() {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run() {
				keyboardComponent.openKeybord(etMobile);
			}
		}, 500);
	}

	/**
	 * 关闭软键盘
	 */
	private void closeKeyBoard() {
		keyboardComponent.closeKeyboard(etMobile);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.iv_back:
			finish();
			break;

		case R.id.btn_OK:
			handlerBtnOk();
			break;
		}
	}

	/**
	 * 倒计时类
	 */
	class TimeCount extends CountDownTimer {
		/**
		 * @param millisInFuture
		 * @param countDownInterval
		 */
		public TimeCount(long millisInFuture, long countDownInterval) {
			// 参数依次为总时长,和计时的时间间隔
			super(millisInFuture, countDownInterval);
		}

		public void onTick(long millisUntilFinished) {
			btnOk.setText(millisUntilFinished / 1000 + "秒");
		}

		public void onFinish() {
			btnOk.setText(getResources().getString(R.string.get_identify_code));
			setBtnOk(true);
		}
	}

	private void setBtnOk(boolean flag) {
		btnOk.setEnabled(flag);
	}

	private void handlerBtnOk() {
		if (getResources().getString(R.string.register_get_identifying_code)
				.equals(btnOk.getText().toString())) {
			getIdentifyingCode();
		} else if (getResources().getString(R.string.register_next).equals(
				btnOk.getText().toString())) {
			sendIdentifyingCode();
		}
	}

	@Override
	public void afterTextChanged(Editable arg0) {

	}

	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
			int arg3) {
	}

	@Override
	public void onTextChanged(CharSequence data, int arg1, int arg2, int arg3) {
		timeCount.cancel();
		setBtnOk(true);
		if (data.length() > 0) {
			btnOk.setText(getResources().getString(R.string.register_next));
		} else {
			btnOk.setText(getResources().getString(
					R.string.register_get_identifying_code));
		}
	}
}
