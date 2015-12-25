/**
 * 
 */
package cn.sx.decentworld.dialog;

import cn.sx.decentworld.R;
import cn.sx.decentworld.utils.ViewUtil;
import android.R.interpolator;
import android.app.Dialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @ClassName: LoginPwdWrongIdentifyDialogFragment.java
 * @Description:
 * @author: dyq
 * @date: 2015年7月23日 上午8:49:21
 */
public class LoginPwdWrongIdentifyDialogFragment extends DialogFragment
		implements OnClickListener {
//	static String mobileNumber;
	TextView tv_mobilephone;
	Button bt_sure_of_identify_code;
	EditText et_pwd_forget_identify_code;
	LoginPwdWrongIdentifyCallbackListener listener;
	boolean loading=true;
	
	TimeCount2 time;
//	private boolean to_next = true;
	
	RelativeLayout activity_login_input_identify_code_dialog_back;
	RelativeLayout root_activity_login_input_identify_code_dialog;
	Button count_time;

	public static LoginPwdWrongIdentifyDialogFragment newInstance(String phone_num) {
		LoginPwdWrongIdentifyDialogFragment aa = new LoginPwdWrongIdentifyDialogFragment();
		Bundle bundle=new Bundle();
		bundle.putString("phone_num", phone_num);
		aa.setArguments(bundle);
		return aa;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater,
	 * android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(
				R.layout.activity_login_input_identify_code_dialog, null);
		root_activity_login_input_identify_code_dialog=(RelativeLayout) view.findViewById(R.id.root_activity_login_input_identify_code_dialog);
		ViewUtil.scaleContentView(root_activity_login_input_identify_code_dialog);
		
		tv_mobilephone = (TextView) view.findViewById(R.id.tv_mobilephone);
		tv_mobilephone.setText(getArguments().getString("phone_num"));
		et_pwd_forget_identify_code=(EditText) view.findViewById(R.id.et_pwd_forget_identify_code);
		activity_login_input_identify_code_dialog_back=(RelativeLayout) view.findViewById(R.id.activity_login_input_identify_code_dialog_back);
		activity_login_input_identify_code_dialog_back.setOnClickListener(this);
		count_time=(Button) view.findViewById(R.id.count_time);
		count_time.setOnClickListener(this);
		bt_sure_of_identify_code = (Button) view
				.findViewById(R.id.bt_sure_of_identify_code);
		bt_sure_of_identify_code.setOnClickListener(this);
		time = new TimeCount2(60000, 1000);
		time.start();
		return view;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.app.DialogFragment#onCreateDialog(android.os.Bundle)
	 */
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Dialog aa=new Dialog(getActivity(), R.style.ShouchangDialog);
		aa.show();
		return aa;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_sure_of_identify_code:
				if (listener != null) {
					if(!TextUtils.isEmpty("et_pwd_forget_identify_code")){
					listener.submitCode(et_pwd_forget_identify_code
							.getText().toString());
					}
					else{
						Toast.makeText(getActivity(), "验证码不能为空", Toast.LENGTH_SHORT);
					}
				}
			break;
		case R.id.activity_login_input_identify_code_dialog_back:
			if(listener!=null){
				listener.oncancell();
			}
			
			break;
		case R.id.count_time:
			if(!loading){
				time.start();
				listener.regetIdentifyCode();
			
			}
			break;
		default:
			break;
		}
	}

	public void setListener(LoginPwdWrongIdentifyCallbackListener listener) {
		this.listener = listener;
	}

	class TimeCount2 extends CountDownTimer {
		/**
		 * @param millisInFuture
		 * @param countDownInterval
		 */
		public TimeCount2(long millisInFuture, long countDownInterval) {
			// 参数依次为总时长,和计时的时间间隔
			super(millisInFuture, countDownInterval);
		}

		@Override
		public void onTick(long millisUntilFinished) {
			count_time.setText(millisUntilFinished / 1000 + "秒");

		}

		@Override
		public void onFinish() {
//			to_next=false;
			count_time.setText(R.string.reget_identify_code);
			loading=false;
		}

	}

	public interface LoginPwdWrongIdentifyCallbackListener {
		public void submitCode(String code);
		public void regetIdentifyCode();
		public void oncancell();
	}
}
