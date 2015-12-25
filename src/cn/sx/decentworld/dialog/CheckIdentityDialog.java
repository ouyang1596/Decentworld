/**
 * 
 */
package cn.sx.decentworld.dialog;

import cn.sx.decentworld.R;
import cn.sx.decentworld.utils.ViewUtil;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

/**
 * @ClassName: CheckIdentityDialog.java
 * @Description: 
 * @author: cj
 * @date: 2015年7月24日 下午5:19:30
 */
public class CheckIdentityDialog extends DialogFragment implements OnClickListener
{
	private static final String TAG = "CheckIdentityDialog";
	private CheckIdentityListener listener;
	private EditText et_me_setting_check_password_pw;
	private Button bt_me_setting_check_password_confirm;
	private View view;

	/**
	 * @param listener the listener to set
	 */
	public void setListener(CheckIdentityListener listener)
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
		View view = inflater.inflate(R.layout.dialog_me_setting_check_password, container,false);
		ViewUtil.scaleContentView((LinearLayout)view);
		et_me_setting_check_password_pw = (EditText) view.findViewById(R.id.et_me_setting_check_password_pw);
		bt_me_setting_check_password_confirm = (Button) view.findViewById(R.id.bt_me_setting_check_password_confirm);
		bt_me_setting_check_password_confirm.setOnClickListener(this);
		return view;
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.bt_me_setting_check_password_confirm:
			listener.onConfirm();
			InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(et_me_setting_check_password_pw.getWindowToken(), 0);
			dismiss();
			break;

		default:
			break;
		}
	}
	
	
	public String getPassword()
	{
		return et_me_setting_check_password_pw.getText().toString();
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
	
	public interface CheckIdentityListener
	{
		public void onConfirm();

	}

}
