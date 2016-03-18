/**
 * 
 */
package cn.sx.decentworld.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import cn.sx.decentworld.R;
import cn.sx.decentworld.utils.ViewUtil;

/**
 * @ClassName: LoginDialogFragment.java
 * @Description:
 * @author: dyq
 * @date: 2015年7月21日 下午5:49:52
 */
public class LoginDialogFragment extends DialogFragment implements OnClickListener
{
	private EditText username;
	private EditText password;
	private Button button;
	private LoginCallbackListener listerner;
	private RelativeLayout root_activity_login_dialog;

	private TimeCount time = new TimeCount(450 , 100);
	private static LoginDialogFragment adf = new LoginDialogFragment();

	public static LoginDialogFragment getInstance()
	{
		return adf;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		Dialog m_dialog = new Dialog(getActivity() , R.style.RegisterDialog);
		return m_dialog;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.activity_login_dialog, container, false);
		root_activity_login_dialog = (RelativeLayout) view.findViewById(R.id.root_activity_login_dialog);
		ViewUtil.scaleContentView(root_activity_login_dialog);
		username = (EditText) view.findViewById(R.id.et_username);
		username.setText("");
		password = (EditText) view.findViewById(R.id.et_password);
		password.setText("");
		username.setOnEditorActionListener(new OnEditorActionListener()
		{

			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
			{
				if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER))
				{
					password.requestFocus();
					return true;
				}
				return false;
			}
		});
		button = (Button) view.findViewById(R.id.bt_login);
		button.setOnClickListener(this);

		// 计时开始
		// time.start();
		/**
		 * 
		 */
		// if (DecentWorldApp.getInstance().getUserName() != null) {
		// username.setText(DecentWorldApp.getInstance().getUserName());
		// }
		return view;
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
			case R.id.bt_login:
				if (listerner != null)
				{
					listerner.save(username.getText().toString(), password.getText().toString());
				}
				break;
		}
	}

	public void setOnListerner(LoginCallbackListener listerner)
	{
		this.listerner = listerner;
	}
	
	/** 如果有最近登录的用户，将用户名显示*/
	private String userName = "";
	public void setUserName(String userName)
	{
		this.userName = userName;
	}
	
	@Override
	public void onStart()
	{
		super.onStart();
		if (userName.equals(""))
		{
			username.setText("");
		}
		else {
			username.setText(userName);
		}
	}
	/** 如果有最近登录的用户，将用户名显示*/

	class TimeCount extends CountDownTimer
	{
		public TimeCount(long millisInFuture, long countDownInterval)
		{
			// 参数依次为总时长,和计时的时间间隔
			super(millisInFuture, countDownInterval);
		}

		@Override
		public void onTick(long millisUntilFinished)
		{
			
		}

		@Override
		public void onFinish()
		{
			username.requestFocus();
			InputMethodManager imm = (InputMethodManager) username.getContext().getSystemService(getActivity().INPUT_METHOD_SERVICE);
			imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
		}

	}

}
