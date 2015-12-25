/**
 * 
 */
package cn.sx.decentworld.dialog;

import cn.sx.decentworld.R;
import cn.sx.decentworld.utils.ViewUtil;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @ClassName: LoginNewPwdDialogFragment.java
 * @Description:
 * @author: dyq
 * @date: 2015年7月23日 上午9:35:21
 */
public class LoginNewPwdDialogFragment extends DialogFragment implements OnClickListener{
	private TextView tv_if_id_card_correct;
	private EditText et_id_card_number;
//	private EditText et_new_pwd;
	private Button bt_sure_reset_pwd;
	private RelativeLayout activity_login_reset_pwd_dialog_dismiss;
	private RelativeLayout activity_login_reset_pwd_dialog_back;
	private RelativeLayout activity_login_reset_pwd_dialog;
	private LoginOldIDcodeCallbackListener listener;
	public static LoginNewPwdDialogFragment newInstance() {
		LoginNewPwdDialogFragment aa = new LoginNewPwdDialogFragment();
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
		View view = inflater.inflate(R.layout.activity_login_reset_pwd_dialog,
				null);
		activity_login_reset_pwd_dialog=(RelativeLayout) view.findViewById(R.id.activity_login_reset_pwd_dialog);
		ViewUtil.scaleContentView(activity_login_reset_pwd_dialog);
		
		et_id_card_number=(EditText) view.findViewById(R.id.et_id_card_number);
//		et_new_pwd=(EditText) view.findViewById(R.id.et_new_pwd);
		bt_sure_reset_pwd=(Button) view.findViewById(R.id.bt_sure_reset_pwd);
		bt_sure_reset_pwd.setOnClickListener(this);
		tv_if_id_card_correct=(TextView) view.findViewById(R.id.tv_if_id_card_correct);
		activity_login_reset_pwd_dialog_dismiss=(RelativeLayout) view.findViewById(R.id.activity_login_reset_pwd_dialog_dismiss);
		activity_login_reset_pwd_dialog_dismiss.setOnClickListener(this);
		activity_login_reset_pwd_dialog_back=(RelativeLayout) view.findViewById(R.id.activity_login_reset_pwd_dialog_back);
		activity_login_reset_pwd_dialog_back.setOnClickListener(this);
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
		Dialog dialog = new Dialog(getActivity(), R.style.ShouchangDialog);
		dialog.show();
		return dialog;

	}
	
	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.bt_sure_reset_pwd:
			if(listener!=null){
				if(!et_id_card_number.getText().toString().equals("")){
					listener.replyIDcode(et_id_card_number.getText().toString());
				}
			}
			break;
		case R.id.activity_login_reset_pwd_dialog_dismiss:
			this.dismiss();
			break;
		case R.id.activity_login_reset_pwd_dialog_back:
			if(listener!=null){
					listener.oncancel();
			}
			break;
		default:
			break;
		}
	}
	public void id_code_wrong(){
		tv_if_id_card_correct.setVisibility(View.VISIBLE);
	}
	
	public void setListener(LoginOldIDcodeCallbackListener listener){
		this.listener=listener;
	}
	public interface LoginOldIDcodeCallbackListener{
		public void replyIDcode(String id_code);
		public void oncancel();
	}
}
