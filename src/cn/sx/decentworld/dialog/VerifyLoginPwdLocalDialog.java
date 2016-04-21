/**
 * 
 */
package cn.sx.decentworld.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import cn.sx.decentworld.R;
import cn.sx.decentworld.common.CommUtil;
import cn.sx.decentworld.utils.AES;
import cn.sx.decentworld.utils.ViewUtil;

/**
 * @ClassName: CheckIdentityDialog.java
 * @Description: 确认输入的密码是否与本地保存的登录密码相同
 * @author: cj
 * @date: 2015年7月24日 下午5:19:30
 */
public class VerifyLoginPwdLocalDialog extends DialogFragment implements OnClickListener
{
	private static final String TAG = "VerifyLoginPwdLocalDialog";
	private VerifyLoginPwdLocalListener listener;
	private EditText mEtPwd;
	private Button mBtnSubmit;
	private View view;

	/**
	 * @param listener the listener to set
	 */
	public void setListener(VerifyLoginPwdLocalListener listener)
	{
		this.listener = listener;
	}
	
	public void setClickView(View view)
	{
		this.view = view;

	}
	
	public View getClickView()
	{
		return view;
	}

	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		Dialog dialog = new Dialog(getActivity(), R.style.ShouchangDialog); 
		return dialog;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.dialog_verify_login_pwd_local, container,false);
		mEtPwd = (EditText) view.findViewById(R.id.et_verify_login_pwd_local);
		mBtnSubmit = (Button) view.findViewById(R.id.btn_belong_dialog_submit);
		mBtnSubmit.setOnClickListener(this);
		return view;
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.btn_belong_dialog_submit:
		    String pwd = mEtPwd.getText().toString().trim();
		    if(CommUtil.isNotBlank(pwd))
		    {
		        pwd =  AES.encode(pwd);
		        listener.onConfirm(pwd);
	            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
	            imm.hideSoftInputFromWindow(mEtPwd.getWindowToken(), 0);
	            dismiss();
		    }
		    else
		    {
		        Toast.makeText(getActivity(), "密码不能为空", Toast.LENGTH_SHORT).show();
		    }
			break;

		default:
			break;
		}
	}
	
	
	public String getPassword()
	{
		return mEtPwd.getText().toString();
	}
	

	@Override
	public void onPause()
	{	
		if (view != null)
		{
			InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(view.getWindowToken(), 1);  
		}
		super.onPause();
	}
	
	public interface VerifyLoginPwdLocalListener
	{
		public void onConfirm(String password);

	}

}
