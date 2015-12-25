/**

 * 
 */
package cn.sx.decentworld.dialog;

import java.util.HashMap;

import com.activeandroid.util.Log;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;

import cn.sx.decentworld.R;
import cn.sx.decentworld.network.SendUrl;
import cn.sx.decentworld.network.SendUrl.HttpCallBack;
import cn.sx.decentworld.network.entity.ResultBean;
import cn.sx.decentworld.network.utils.JsonUtils;
import cn.sx.decentworld.utils.ViewUtil;
import android.app.Dialog;
import android.location.GpsStatus.Listener;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TextView.OnEditorActionListener;

/**
 * @ClassName: RegisterDialogFragment.java
 * @Description:
 * @author: dyq
 * @date: 2015年7月22日 上午8:59:23
 */
public class RegisterDialogFragment extends DialogFragment implements
		OnClickListener {
	private EditText et_mobile_num;
	// private EditText password;
	private EditText et_register_identify_code;
	private Button count_time_2;
	private Button register_bt_click_next;
	private RegisterCallbackListener listerner;
	private TimeCount time = new TimeCount(60000, 1000);
	private TextView register_if_identify_code_correct;
	private RelativeLayout root_activity_register_dialog;

	/**
	 * step1为第一阶段，即获取验证码阶段，step1==false时，即获取完验证码，等待注册阶段
	 */
	public boolean step1 = true;

	public static RegisterDialogFragment newInstance() {
		RegisterDialogFragment a = new RegisterDialogFragment();

		return a;
	}

	public void setListener(RegisterCallbackListener listerner) {
		this.listerner = listerner;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Dialog m_dialog = new Dialog(getActivity(), R.style.RegisterDialog);
		m_dialog.show();
		return m_dialog;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_register_dialog,
				container, false);
		root_activity_register_dialog = (RelativeLayout) view
				.findViewById(R.id.root_activity_register_dialog);
		ViewUtil.scaleContentView(root_activity_register_dialog);

		register_if_identify_code_correct = (TextView) view
				.findViewById(R.id.register_if_identify_code_correct);
		et_mobile_num = (EditText) view.findViewById(R.id.et_mobile_num);
		et_mobile_num.setFocusable(true);
		et_mobile_num.setFocusableInTouchMode(true);
		et_mobile_num.requestFocus();

		et_register_identify_code = (EditText) view
				.findViewById(R.id.et_register_identify_code);
		count_time_2 = (Button) view.findViewById(R.id.count_time_2);
		count_time_2.setOnClickListener(this);
		register_bt_click_next = (Button) view
				.findViewById(R.id.register_bt_click_next);
		register_bt_click_next.setOnClickListener(this);
		return view;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.count_time_2:
			if (listerner != null) {
				if (!TextUtils.isEmpty(et_mobile_num.getText().toString())) {
					listerner.getIdentifyCode(et_mobile_num.getText()
							.toString());
				} else {
					Toast.makeText(getActivity(), "手机号码不能为空 ",
							Toast.LENGTH_SHORT).show();
				}

			}
			break;
		case R.id.register_bt_click_next:
			if (listerner != null) {
				if (!TextUtils.isEmpty(et_register_identify_code.getText()
						.toString())) {
					listerner.submitIndentifyCode(et_register_identify_code
							.getText().toString());

				} else {
					Toast.makeText(getActivity(), "不能为空 ", Toast.LENGTH_SHORT)
							.show();
				}
			}
			break;
		default:
			break;
		}
	}

	public void setTelNum(String num) {
		et_mobile_num.setText(num);
	}

	/**
	 * 点击验证后倒计时开始
	 */
	public void isPrepared() {
		count_time_2.setEnabled(false);
		count_time_2.setClickable(false);
		time.start();
	}

	public void showwrongText() {
		register_if_identify_code_correct.setVisibility(View.VISIBLE);
	}

	/**
	 * 倒计时类
	 * @ClassName: LoginActivity.java
	 * @Description:
	 * @author: dyq
	 * @date: 2015年7月22日 下午2:52:57
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
			count_time_2.setText(millisUntilFinished / 1000 + "秒");
			// button.append("("+ millisUntilFinished / 1000 + ")秒");

		}

		@Override
		public void onFinish() {
			step1 = true;
			count_time_2.setText(R.string.get_identify_code);
			count_time_2.setEnabled(true);
			count_time_2.setClickable(true);
		}

	}

	public interface RegisterCallbackListener {
		public void oncancel();

		public void getIdentifyCode(String tel);

		public void submitIndentifyCode(String code);
		// public void register(String mobileNumber, String password);

	}

}
