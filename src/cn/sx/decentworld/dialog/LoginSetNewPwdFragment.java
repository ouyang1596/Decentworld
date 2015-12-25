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
 * @ClassName: LoginSetNewPwdFragment.java
 * @Description: 
 * @author: dyq
 * @date: 2015年8月9日 下午2:02:18
 */
public class LoginSetNewPwdFragment extends DialogFragment implements OnClickListener{
	private TextView tv_if_pwd_ok;
	private EditText et_new_pwd;
	private Button bt_sure_set_pwd;
	private LoginNewPwdCallbackListener listener;
	private RelativeLayout activity_login_set_pwd_dialog;
	public static LoginSetNewPwdFragment newInstance() {
		LoginSetNewPwdFragment aa = new LoginSetNewPwdFragment();
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
		View view = inflater.inflate(R.layout.activity_login_set_pwd_dialog,
				null);
		activity_login_set_pwd_dialog=(RelativeLayout) view.findViewById(R.id.activity_login_set_pwd_dialog);
		ViewUtil.scaleContentView(activity_login_set_pwd_dialog);
		et_new_pwd=(EditText) view.findViewById(R.id.et_new_pwd);
		bt_sure_set_pwd=(Button) view.findViewById(R.id.bt_sure_set_pwd);
		bt_sure_set_pwd.setOnClickListener(this);
		tv_if_pwd_ok=(TextView) view.findViewById(R.id.tv_if_pwd_ok);
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
		case R.id.bt_sure_set_pwd:
			if(listener!=null){
				if(et_new_pwd.getText().toString().length()!=8){
					tv_if_pwd_ok.setVisibility(View.VISIBLE);
				}
				else{
					listener.replyNewPwd(et_new_pwd.getText().toString());
				}
			}
			break;
		default:
			break;
		}
	}
	public void id_code_wrong(){
		tv_if_pwd_ok.setVisibility(View.VISIBLE);
	}
	
	public void setListener(LoginNewPwdCallbackListener listener){
		this.listener=listener;
	}
	public interface LoginNewPwdCallbackListener{
		public void replyNewPwd(String new_pwd);
	}
}
