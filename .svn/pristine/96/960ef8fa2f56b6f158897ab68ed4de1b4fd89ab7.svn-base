package cn.sx.decentworld.activity;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.sx.decentworld.R;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.component.KeyboardComponent;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.dialog.GetIdentifyingCodeDialogFragment;
import cn.sx.decentworld.dialog.GetIdentifyingCodeDialogFragment.OnEnsureClickListener;
import cn.sx.decentworld.network.request.ResetPwdInfo;
import cn.sx.decentworld.utils.ViewUtil;

import com.android.volley.Request.Method;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_forget_pwd_identifying_code)
public class ForgetPwdIdentifyingCodeActivity extends BaseFragmentActivity
		implements OnEnsureClickListener {
	@ViewById(R.id.btn_OK)
	Button btnOk;
	@ViewById(R.id.etv_identifying_code)
	EditText etvIdentifyingCode;
	@ViewById(R.id.root__forget_pwd_identifying_code)
	LinearLayout llForgetPwdIdentifyingCode;
	@ViewById(R.id.iv_back)
	ImageView ivBack;
	@ViewById(R.id.time_count)
	TextView tvTimeCount;
	@ViewById(R.id.tv_re_send)
	TextView tvReSend;
	@ViewById(R.id.tv_mobile)
	TextView tvMobile;
	String code;
	@Bean
	ToastComponent toast;
	@Bean
	ResetPwdInfo resetPwdInfo;
	private TimeCount timeCount = new TimeCount(60000, 1000);
	private FragmentManager fragmentManager;
	private String mobile;
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

	@AfterViews
	public void init() {
		FGetIntent();
		tvMobile.setText("+86" + mobile);
		openKeyboard();
		ViewUtil.scaleContentView(llForgetPwdIdentifyingCode);
		llForgetPwdIdentifyingCode.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				closeKeyBoard();
			}
		});
		fragmentManager = getSupportFragmentManager();
		tvReSend.setVisibility(View.GONE);
		timeCount.start();

		setBtnState();
		ivBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				closeKeyBoard();
				finish();
			}
		});
		etvIdentifyingCode.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				setBtnState();
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {

			}

			@Override
			public void afterTextChanged(Editable arg0) {

			}
		});
		btnOk.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// if (code.equals(etvIdentifyingCode.getText().toString())) {
				// startActivity(new Intent(mContext,
				// ForgetPwdIdCardActivity_.class));
				// } else {
				// toast.show("验证码错误");
				// }
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("phoneNum", mobile);
				map.put("code", code);
				resetPwdInfo.checkCode(map,
						Constants.API_FORGET_PWD_CHECK_CODE, Method.GET,
						mCheckHandler);
			}
		});
		tvReSend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				GetIdentifyingCodeDialogFragment gif = GetIdentifyingCodeDialogFragment
						.newInstance(ResetPwdInfo.phoneNum);
				gif.setOnEnsureClickListener(ForgetPwdIdentifyingCodeActivity.this);
				gif.show(fragmentManager, "getIdentifyingcodes");
			}
		});
	}

	private void FGetIntent() {
		code = getIntent().getStringExtra("code");
		mobile = getIntent().getStringExtra(ForgetPwdMobileActivity.MOBILE);
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

		@Override
		public void onTick(long millisUntilFinished) {
			tvTimeCount.setText(millisUntilFinished / 1000 + "秒");
		}

		@Override
		public void onFinish() {
			tvTimeCount.setText(0 + "秒");
			tvReSend.setVisibility(View.VISIBLE);
		}
	}

	private void setBtnState() {
		int length = etvIdentifyingCode.length();
		if (length <= 0) {
			btnOk.setEnabled(false);
			btnOk.setTextColor(getResources()
					.getColor(R.color.rg_tv_blue_press));
			btnOk.setBackgroundResource(R.drawable.rg_btn_blue_pressed_shape);
		} else {
			btnOk.setEnabled(true);
			btnOk.setTextColor(getResources().getColor(
					R.drawable.rg_tv_color_selector));
			btnOk.setBackgroundResource(R.drawable.rg_btn_bg_selector);
		}
	}

	@Override
	public void ensureClick() {
		tvReSend.setVisibility(View.GONE);
		timeCount.start();
		requestCode(ResetPwdInfo.phoneNum);
	}

	private void requestCode(String phoneNumber) {
		Handler handler = new Handler() {
			public void handleMessage(android.os.Message msg) {
				String jsonData = msg.obj.toString();
				try {
					JSONObject object = new JSONObject(jsonData);
					String code = object.getString("code");
					Intent intent = new Intent(mContext,
							ForgetPwdIdentifyingCodeActivity_.class);
					intent.putExtra("code", code);
					startActivity(intent);
				} catch (JSONException e) {
					// toast.show("解析错误");
					return;
				}
			};
		};
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("phoneNum", phoneNumber);
		resetPwdInfo.requestCode(map, Constants.API_REQUEST_CODE, Method.GET,
				handler);
	}

	@Override
	protected void onStop() {
		super.onStop();
		timeCount.cancel();
		tvTimeCount.setText(0 + "秒");
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
				keyboardComponent.openKeybord(etvIdentifyingCode);
			}
		}, 500);
	}

	/**
	 * 关闭软键盘
	 */
	private void closeKeyBoard() {
		keyboardComponent.closeKeyboard(etvIdentifyingCode);
	}
}
