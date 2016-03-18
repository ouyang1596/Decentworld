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
public class DialogSocialStatusFragment extends DialogFragment implements
		OnClickListener {
	private Dialog m_dialog;
	private static String mMsg;

	public static DialogSocialStatusFragment newInstance(String msg) {
		DialogSocialStatusFragment adf = new DialogSocialStatusFragment();
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
		View view = View.inflate(getActivity(), R.layout.dialog_social_status,
				null);
		TextView tvMsg = (TextView) view.findViewById(R.id.tv_msg);
		tvMsg.setText(mMsg);
		ImageView ivCancel = (ImageView) view.findViewById(R.id.iv_cancel);
		ivCancel.setOnClickListener(this);
		Button btnBack = (Button) view.findViewById(R.id.btn_ensure);
		btnBack.setOnClickListener(this);
		Button btnCancel = (Button) view.findViewById(R.id.btn_cancel);
		btnCancel.setOnClickListener(this);
		return view;
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.btn_ensure:
			if (deleteClickListener != null) {
				deleteClickListener.deleteClick(Constants.ENSURE);
			}
			break;
		case R.id.btn_cancel:
			if (deleteClickListener != null) {
				deleteClickListener.deleteClick(Constants.CANCEL);
			}
		case R.id.iv_cancel:
			this.dismiss();
			break;
		}
		DialogSocialStatusFragment.this.dismiss();
	}

	private OnDeleteClickListener deleteClickListener;

	public void setOnDeleteClickListener(OnDeleteClickListener backClickListener) {
		this.deleteClickListener = backClickListener;
	};

	public interface OnDeleteClickListener {
		public void deleteClick(int state);
	}
}
