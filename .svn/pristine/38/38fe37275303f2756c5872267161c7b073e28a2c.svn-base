/**
 * 
 */
package cn.sx.decentworld.dialog;

import cn.sx.decentworld.R;
import cn.sx.decentworld.dialog.LoginPwdWrongDialogFragment.LoginPwdWrongCallbackListener;
import cn.sx.decentworld.utils.ViewUtil;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * @ClassName: Register2DialogFragment.java
 * @Description: 
 * @author: dyq
 * @date: 2015年8月9日 下午3:44:30
 */
public class Register2DialogFragment extends DialogFragment implements OnClickListener{
	EditText et_register_pwd;
	Button register_set_pwd_bt_sure;
	RelativeLayout activity_register_setpwd_dialog_dismiss;
	RelativeLayout activity_register_setpwd_dialog_back;
	RelativeLayout root_activity_register_set_pwd_dialog;
	
	public RegisterSetPwdCallbackListener listener;
	public static Register2DialogFragment newInstance(){
		Register2DialogFragment aa=new Register2DialogFragment();
		return aa;
	}
	public void setListener (RegisterSetPwdCallbackListener listener){
		this.listener=listener;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.activity_register_set_pwd_dialog, null);
		root_activity_register_set_pwd_dialog=(RelativeLayout) view.findViewById(R.id.root_activity_register_set_pwd_dialog);
		ViewUtil.scaleContentView(root_activity_register_set_pwd_dialog);
		
		et_register_pwd=(EditText) view.findViewById(R.id.et_register_pwd);
		register_set_pwd_bt_sure=(Button) view.findViewById(R.id.register_set_pwd_bt_sure);
		activity_register_setpwd_dialog_dismiss=(RelativeLayout) view.findViewById(R.id.activity_register_setpwd_dialog_dismiss);
		activity_register_setpwd_dialog_back=(RelativeLayout) view.findViewById(R.id.activity_register_setpwd_dialog_back);
		register_set_pwd_bt_sure.setOnClickListener(this);
		activity_register_setpwd_dialog_dismiss.setOnClickListener(this);
		activity_register_setpwd_dialog_back.setOnClickListener(this);
		return view;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Dialog aa=new Dialog(getActivity(), R.style.ShouchangDialog);
		aa.show();
		return aa;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.register_set_pwd_bt_sure:
			if(listener!=null){
				if(!TextUtils.isEmpty(et_register_pwd.getText().toString()))
				{
					listener.sure_pwd(et_register_pwd.getText().toString());
				}
				else{
					Toast.makeText(getActivity(), "密码不能为空 ", Toast.LENGTH_SHORT);
				}
			}
			break;
		case R.id.activity_register_setpwd_dialog_dismiss:
			this.dismiss();
			break;
		case R.id.activity_register_setpwd_dialog_back:
			listener.back();
			break;
		default:
			break;
		}
	}
	public interface RegisterSetPwdCallbackListener{
		public void sure_pwd(String pwd);
		public void back();
	}
}
