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
import cn.sx.decentworld.common.Constants;

/**
 * 返回提示对话框
 */
public class BackDialogFragment extends DialogFragment implements
		OnClickListener {
	private Dialog m_dialog;
	private static String mMsg;

	public static BackDialogFragment newInstance(String msg) {
		BackDialogFragment adf = new BackDialogFragment();
		mMsg = msg;
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
		View view = View.inflate(getActivity(), R.layout.dialog_back, null);
		TextView tvMsg = (TextView) view.findViewById(R.id.tv_msg);
		tvMsg.setText(mMsg);
		ImageView ivCancel = (ImageView) view.findViewById(R.id.iv_cancel);
		ivCancel.setOnClickListener(this);
		Button btnBack = (Button) view.findViewById(R.id.btn_ensure);
		Button btnCancel = (Button) view.findViewById(R.id.btn_cancel);
		btnCancel.setOnClickListener(this);
		btnBack.setOnClickListener(this);
		return view;
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.btn_ensure:
			if (backClickListener != null) {
				backClickListener.backClick(Constants.SAVE);
			}
			break;
		case R.id.btn_cancel:
			if (backClickListener != null) {
				backClickListener.backClick(Constants.CANCEL);
			}
			break;
		case R.id.iv_cancel:
			this.dismiss();
			break;
		}
		BackDialogFragment.this.dismiss();
	}

	private OnBackClickListener backClickListener;

	public void setOnBackClickListener(OnBackClickListener backClickListener) {
		this.backClickListener = backClickListener;
	};

	public interface OnBackClickListener {
		public void backClick(int state);
	}
}
