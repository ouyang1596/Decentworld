package cn.sx.decentworld.activity;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.os.Handler;
import android.text.Editable;
import android.text.InputType;
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
import cn.sx.decentworld.network.request.ResetPwdInfo;

import com.android.volley.Request.Method;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_forgert_pwd_reset_pwd)
public class ForgertPwdResetPwdActivity extends BaseFragmentActivity {
	@ViewById(R.id.etv_reset_pwd)
	EditText etvResetPwd;
	@ViewById(R.id.tv_header_title)
	TextView tvTitle;
	@ViewById(R.id.iv_back)
	ImageView ivBack;
	@ViewById(R.id.btn_OK)
	Button btnOk;
	@ViewById(R.id.iv_pwd_is_show)
	ImageView ivPwdIsShow;
	@ViewById(R.id.etv_reset_pwd)
	EditText etvPwd;
	@ViewById(R.id.root_activity_register_set_password)
	LinearLayout llForgetPwd;
	@Bean
	ToastComponent toast;
	@Bean
	ResetPwdInfo resetPwdInfo;
	private String token;
	private boolean pwdIsShow = false;

	@AfterViews
	public void init() {
		openKeyboard();
		tvTitle.setText("重置密码");
		ivBack.setVisibility(View.VISIBLE);
		llForgetPwd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				closeKeyBoard();
			}
		});
		token = getIntent().getStringExtra("token");
		setBtnState();
		etvResetPwd.addTextChangedListener(new TextWatcher() {

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
		ivBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				closeKeyBoard();
				finish();
			}
		});
		btnOk.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				resetPwd(etvResetPwd.getText().toString());
			}
		});
		ivPwdIsShow.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (pwdIsShow) {
					etvPwd.setInputType(InputType.TYPE_CLASS_TEXT
							| InputType.TYPE_TEXT_VARIATION_PASSWORD);
					ivPwdIsShow.setImageResource(R.drawable.hide_password);
					pwdIsShow = false;
				} else {
					etvPwd.setInputType(InputType.TYPE_CLASS_TEXT
							| InputType.TYPE_TEXT_VARIATION_NORMAL);
					ivPwdIsShow.setImageResource(R.drawable.show_password);
					pwdIsShow = true;
				}
			}
		});
	}

	private void resetPwd(String newPwd) {
		Handler handler = new Handler() {
			public void handleMessage(android.os.Message msg) {
				startActivity(new Intent(mContext, LoginActivity_.class));
			};
		};
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("newpwd", newPwd);
		map.put("phoneNum", resetPwdInfo.phoneNum);
		map.put("token", token);
		resetPwdInfo
				.resetPwd(map, Constants.API_RESET_PWD, Method.GET, handler);
	}

	private void setBtnState() {
		int length = etvResetPwd.length();
		if (length <= 0) {
			btnOk.setEnabled(false);
			btnOk.setTextColor(getResources()
					.getColor(R.color.rg_tv_blue_press));
			btnOk.setBackgroundResource(R.drawable.bg_btn_yellow_pressed_shape);
		} else {
			btnOk.setEnabled(true);
			btnOk.setTextColor(getResources().getColor(
					R.drawable.rg_tv_color_selector));
			btnOk.setBackgroundResource(R.drawable.bg_btn_yellow_selector);
		}
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
				keyboardComponent.openKeybord(etvPwd);
			}
		}, 500);
	}

	/**
	 * 关闭软键盘
	 */
	private void closeKeyBoard() {
		keyboardComponent.closeKeyboard(etvPwd);
	}
}
