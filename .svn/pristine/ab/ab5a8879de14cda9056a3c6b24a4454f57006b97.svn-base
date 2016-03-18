/**
 * 
 */
package cn.sx.decentworld.dialog;

import cn.sx.decentworld.R;
import cn.sx.decentworld.utils.ViewUtil;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @ClassName: RegisterNickNameDialogFragment.java
 * @Description:
 * @author: dyq
 * @date: 2015年7月22日 下午5:31:58
 */
public class RegisterNickNameDialogFragment extends DialogFragment implements
		OnClickListener {
	
	private RegisterNickNameCallbackListener listener;
	private EditText et_nickname;
	private Button bt_sure;
	private TextView nickname_repeat_tv;

	private RelativeLayout activity_register_nick_name_dialog_back;
	private RelativeLayout root_activity_register_nickname_dialog;
	public static RegisterNickNameDialogFragment newInstance() {
		RegisterNickNameDialogFragment aa = new RegisterNickNameDialogFragment();
		return aa;
	}

	public void setListener(RegisterNickNameCallbackListener listener) {
		this.listener = listener;
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
				R.layout.activity_register_nickname_dialog, null);
		root_activity_register_nickname_dialog=(RelativeLayout) view.findViewById(R.id.root_activity_register_nickname_dialog);
		ViewUtil.scaleContentView(root_activity_register_nickname_dialog);
//		nickname_repeat_tv=(TextView) view.findViewById(R.id.nickname_repeat_tv);
		et_nickname=(EditText) view.findViewById(R.id.et_nickname);
		activity_register_nick_name_dialog_back=(RelativeLayout) view.findViewById(R.id.activity_register_nick_name_dialog_back);
		activity_register_nick_name_dialog_back.setOnClickListener(this);
		bt_sure=(Button) view.findViewById(R.id.bt_sure_nickname);
		bt_sure.setOnClickListener(this);
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
		Dialog aa = new Dialog(getActivity(), R.style.ShouchangDialog);
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
		if (listener != null) {
			switch (v.getId()) {
			case R.id.bt_sure_nickname:
				if(!TextUtils.isEmpty(et_nickname.getText().toString())){
//					listener.setNickName(et_nickname.getText().toString());
					listener.identifyNickName(et_nickname.getText().toString());
					
					//当点击确定按钮后，暂时屏蔽此按钮
					bt_sure.setText("正在注册...");
					bt_sure.setEnabled(true);
					
				}
				else{
					Toast.makeText(getActivity(), "昵称不能为空", Toast.LENGTH_SHORT).show();
				}
				break;
			case R.id. activity_register_nick_name_dialog_back:
				
				listener.cancell();
				break;
			default:
				break;
			}
		}
	}
	public void showNickNameTip(){
		nickname_repeat_tv.setVisibility(View.VISIBLE);
	}
	public interface RegisterNickNameCallbackListener {
		public void cancell();

//		public void setNickName(String nickname);
		
		public void identifyNickName(String nickname);
	}
}
