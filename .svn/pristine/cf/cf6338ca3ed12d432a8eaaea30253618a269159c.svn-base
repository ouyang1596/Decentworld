/**
 * 
 */
package cn.sx.decentworld.dialog;

import com.googlecode.androidannotations.annotations.EFragment;
import com.googlecode.androidannotations.annotations.ViewById;

import cn.sx.decentworld.R;
import cn.sx.decentworld.R.id;
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
 * @ClassName: RegisterIdentifyDialogFragment.java
 * @Description:
 * @author: dyq
 * @date: 2015年7月22日 上午11:30:51
 */
public class RegisterIdentifyDialogFragment extends DialogFragment implements OnClickListener
{
	EditText et_id_card_num;
	EditText et_real_name;
	Button bt_to_identify;
	RelativeLayout activity_register_identify_dialog_back;
	RelativeLayout activity_register_identify_dialog_dismiss;

	RelativeLayout root_activity_register_identify_dialog;

	RegisterIdentifyCallbackListener listener;

	public static RegisterIdentifyDialogFragment newInstance()
	{
		RegisterIdentifyDialogFragment aa = new RegisterIdentifyDialogFragment();
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
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.activity_register_identify_dialog, null);
		root_activity_register_identify_dialog = (RelativeLayout) view.findViewById(R.id.root_activity_register_identify_dialog);
		ViewUtil.scaleContentView(root_activity_register_identify_dialog);

		et_id_card_num = (EditText) view.findViewById(R.id.et_id_card_num);
		et_id_card_num.setFocusable(true);
		et_id_card_num.setFocusableInTouchMode(true);
		et_id_card_num.requestFocus();
		et_real_name = (EditText) view.findViewById(R.id.et_real_name);
		bt_to_identify = (Button) view.findViewById(R.id.bt_to_identify);
		bt_to_identify.setOnClickListener(this);
		activity_register_identify_dialog_back = (RelativeLayout) view.findViewById(R.id.activity_register_identify_dialog_back);
		activity_register_identify_dialog_dismiss = (RelativeLayout) view.findViewById(R.id.activity_register_identify_dialog_dismiss);
		activity_register_identify_dialog_back.setOnClickListener(this);
		activity_register_identify_dialog_dismiss.setOnClickListener(this);
		return view;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		Dialog r_dialog = new Dialog(getActivity(), R.style.ShouchangDialog);
		r_dialog.show();
		return r_dialog;
	}

	public void setListener(RegisterIdentifyCallbackListener listener)
	{
		this.listener = listener;
	}

	public interface RegisterIdentifyCallbackListener
	{
		public void oncancell();

		public void toidenfity(String id_card_num, String real_name);
	}


	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.bt_to_identify:
			if (listener != null)
			{
				if (TextUtils.isEmpty(et_id_card_num.getText().toString()) || TextUtils.isEmpty(et_real_name.getText().toString()))
				{
					Toast.makeText(getActivity(), "任何一项不能为空", Toast.LENGTH_SHORT);
				} else
				{
					listener.toidenfity(et_id_card_num.getText().toString(), et_real_name.getText().toString());
				}
			}
			break;
		case R.id.activity_register_identify_dialog_back:
			if (listener != null)
			{
				listener.oncancell();
			}
			break;
		case R.id.activity_register_identify_dialog_dismiss:
			this.dismiss();
			break;
		default:
			break;
		}
	}
}
