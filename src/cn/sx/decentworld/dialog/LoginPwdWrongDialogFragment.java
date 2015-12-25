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

/**
 * @ClassName: LoginPwdWrongDialogFragment.java
 * @Description: 
 * @author: dyq
 * @date: 2015年7月22日 下午7:25:31
 */
public class LoginPwdWrongDialogFragment extends DialogFragment implements OnClickListener{
	EditText et_the_phone_number;
	Button bt_sure;
	RelativeLayout activity_forget_pwd_dialog_dismiss;
	RelativeLayout activity_forget_pwd_dialog_back;
	RelativeLayout root_activity_forget_pwd_dialog;
	
	public LoginPwdWrongCallbackListener listener;
	public static LoginPwdWrongDialogFragment newInstance(){
		LoginPwdWrongDialogFragment aa=new LoginPwdWrongDialogFragment();
		return aa;
	}
	public void setListener (LoginPwdWrongCallbackListener listener){
		this.listener=listener;
	}
	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.activity_forget_pwd_dialog, null);
		root_activity_forget_pwd_dialog=(RelativeLayout) view.findViewById(R.id.root_activity_forget_pwd_dialog);
		ViewUtil.scaleContentView(root_activity_forget_pwd_dialog);
		
		et_the_phone_number=(EditText) view.findViewById(R.id.et_the_phone_number);
		bt_sure=(Button) view.findViewById(R.id.bt_sure);
		activity_forget_pwd_dialog_dismiss=(RelativeLayout) view.findViewById(R.id.activity_forget_pwd_dialog_dismiss);
		activity_forget_pwd_dialog_back=(RelativeLayout) view.findViewById(R.id.activity_forget_pwd_dialog_back);
		bt_sure.setOnClickListener(this);
		activity_forget_pwd_dialog_dismiss.setOnClickListener(this);
		activity_forget_pwd_dialog_back.setOnClickListener(this);
		return view;
	}
	/* (non-Javadoc)
	 * @see android.support.v4.app.DialogFragment#onCreateDialog(android.os.Bundle)
	 */
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Dialog aa=new Dialog(getActivity(), R.style.ShouchangDialog);
		aa.show();
		return aa;
	}
	

	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_sure:
			if(listener!=null){
				listener.inputPhoneNumber(et_the_phone_number.getText().toString());
			}
			break;
		case R.id.activity_forget_pwd_dialog_dismiss:
			this.dismiss();
			break;
		case R.id.activity_forget_pwd_dialog_back:
			listener.back();
			break;
		default:
			break;
		}
	}
	public interface LoginPwdWrongCallbackListener{
		public void inputPhoneNumber(String phone_num);
		public void back();
	}

}
