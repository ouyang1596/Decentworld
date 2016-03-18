/**
 * 
 */
package cn.sx.decentworld.dialog;

import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.R;
import cn.sx.decentworld.utils.ViewUtil;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

/**
 * @ClassName: Login2DialogFragment.java
 * @Description: 
 * @author: dyq
 * @date: 2015年8月7日 下午5:23:01
 */
public class Login2DialogFragment extends DialogFragment implements OnClickListener{

	private EditText username;
	private EditText password;
	private Button button;
	private RelativeLayout rl_forget_psw;
	private RelativeLayout login11;
//	账号密码编辑框
	private LinearLayout et_username_second_ll;
	private LinearLayout et_password_second_ll;
	
	private LoginCallbackListener listerner;

	public static Login2DialogFragment newInstance() {
		Login2DialogFragment adf = new Login2DialogFragment();
		return adf;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.app.DialogFragment#onCreateDialog(android.os.Bundle)
	 */
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Dialog m_dialog = new Dialog(getActivity(),
				R.style.Login2Dialog);
		m_dialog.show();
		return m_dialog;
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
		View view = inflater.inflate(R.layout.activity_login2_dialog, container,
				false);
		login11=(RelativeLayout) view.findViewById(R.id.login11);
		ViewUtil.scaleContentView(login11);
		
		username = (EditText) view.findViewById(R.id.et_username_second);
		password = (EditText) view.findViewById(R.id.et_password_second);
		rl_forget_psw=(RelativeLayout) view.findViewById(R.id.rl_forget_psw);
		et_username_second_ll=(LinearLayout) view.findViewById(R.id.et_username_second_ll);
		et_password_second_ll=(LinearLayout) view.findViewById(R.id.et_password_second_ll);
		
		username.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_SEND
						|| (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)){
					password.requestFocus();
					return true;
				}
				return false;
			}
		});
		button = (Button) view.findViewById(R.id.bt_login_second);
		button.setOnClickListener(this);
		rl_forget_psw.setOnClickListener(this);
		/**
		 * 
		 */
//		if (DecentWorldApp.getInstance().getUserName() != null) {
//			username.setText(DecentWorldApp.getInstance().getUserName());
//		}
		return view;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.bt_login_second:
			if (listerner != null) {
				listerner.save(username.getText().toString(), password
						.getText().toString());
			}
			break;
		case R.id.rl_forget_psw:
			listerner.oncancel();
			break;
		}
	}

	public void setOnListerner(LoginCallbackListener listerner) {
		this.listerner = listerner;
	}
//	public void setWrongBg(){
//		Message msg=new Message();
//		msg.what=1;
//		handler.sendMessage(msg);
//	}
//	Handler handler=new Handler(getActivity().getMainLooper()){
//		public void handleMessage(android.os.Message msg) {
//			switch(msg.what){
//			case 1:
//				et_username_second_ll.setBackgroundResource(R.drawable.login_et_bg_wrong);
//				et_password_second_ll.setBackgroundResource(R.drawable.login_et_bg_wrong);
//				break;
//			}
//			
//		};
//		
//	};

}
