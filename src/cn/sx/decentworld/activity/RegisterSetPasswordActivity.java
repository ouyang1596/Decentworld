package cn.sx.decentworld.activity;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
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
import cn.sx.decentworld.bean.RegisterInfo;
import cn.sx.decentworld.component.KeyboardComponent;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.component.ui.RegisterComponent;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_register_set_password)
public class RegisterSetPasswordActivity extends BaseFragmentActivity {
	@ViewById(R.id.btn_OK)
	Button btnOk;
	@ViewById(R.id.tv_header_title)
	TextView tvTitle;
	@ViewById(R.id.iv_back)
	ImageView ivBack;
	@ViewById(R.id.etv_reset_pwd)
	EditText etvPwd;
	@ViewById(R.id.iv_pwd_is_show)
	ImageView ivPwdIsShow;
	@ViewById(R.id.root_activity_register_set_password)
	LinearLayout llRegisterSetPassword;
	private boolean pwdIsShow = false;
	@Bean
	ToastComponent toast;
	@Bean
	RegisterComponent registerComponent;
	private FragmentManager fragmentManager;

	@AfterViews
	public void init() {
		openKeyboard();
		tvTitle.setText("设置密码");
		llRegisterSetPassword.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				closeKeyBoard();

			}
		});
		fragmentManager = getSupportFragmentManager();
		int length = etvPwd.length();
		setBtnState(length);
		etvPwd.setInputType(InputType.TYPE_CLASS_TEXT
				| InputType.TYPE_TEXT_VARIATION_PASSWORD);
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
		etvPwd.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				int length = etvPwd.length();
				setBtnState(length);
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
				final String pwd = etvPwd.getText().toString();
				savePwd(pwd);
				Handler handler = new Handler() {
					public void handleMessage(android.os.Message msg) {
						startActivity(new Intent(mContext,
								RegisterPersonalMsgActivity_.class));
						finish();
					};
				};
				registerComponent.setPwd(pwd, handler);
			}

			private void savePwd(final String pwd) {
				RegisterInfo info = RegisterInfo
						.queryByDwID(registerComponent.tel);
				if (null == info) {
					info = new RegisterInfo(registerComponent.tel);
				}
				info.password = pwd;
				info.save();
			}
		});
		ivBack.setVisibility(View.VISIBLE);
		ivBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				closeKeyBoard();
				finish();
			}
		});
	}

	private void setBtnState(int length) {
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
