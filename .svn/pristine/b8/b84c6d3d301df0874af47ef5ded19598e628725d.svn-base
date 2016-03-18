/**
 * 
 */
package cn.sx.decentworld.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import cn.sx.decentworld.R;

/**
 * 返回提示对话框
 */
public class GetIdentifyingCodeDialogFragment extends DialogFragment implements
		OnClickListener {
	private Dialog m_dialog;
	private static String phoneNumber;

	public static GetIdentifyingCodeDialogFragment newInstance(String tel) {
		phoneNumber = tel;
		GetIdentifyingCodeDialogFragment adf = new GetIdentifyingCodeDialogFragment();
		return adf;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		m_dialog = new Dialog(getActivity(), R.style.ShouchangDialog);
		m_dialog.show();
		return m_dialog;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = View.inflate(getActivity(),
				R.layout.dialog_get_identifying_code, null);
		TextView tvTel = (TextView) view.findViewById(R.id.tv_tel);
		Button btnEnsure = (Button) view.findViewById(R.id.btn_ensure);
		btnEnsure.setOnClickListener(this);
		tvTel.setText(phoneNumber);
		ImageView ivCancel = (ImageView) view.findViewById(R.id.iv_cancel);
		ivCancel.setOnClickListener(this);
		return view;
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.btn_ensure:
			if (null != ensureClickListener) {
				ensureClickListener.ensureClick();
			}

			break;
		case R.id.iv_cancel:

			break;
		}
		GetIdentifyingCodeDialogFragment.this.dismiss();
	}

	private OnEnsureClickListener ensureClickListener;

	public void setOnEnsureClickListener(
			OnEnsureClickListener ensureClickListener) {
		this.ensureClickListener = ensureClickListener;
	};

	public interface OnEnsureClickListener {
		public void ensureClick();
	}
}
