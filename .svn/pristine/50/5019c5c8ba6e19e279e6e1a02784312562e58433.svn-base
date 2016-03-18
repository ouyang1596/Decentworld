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
import android.widget.TextView;
import cn.sx.decentworld.R;

/**
 * 照相机与拍照选择对话框
 */
public class trueOrFalseDialogFragment extends DialogFragment implements OnClickListener {
	private Dialog mDialog;
	private TextView tvCancel, tvEnsure, tvTips;
	private String tips;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		if (null == mDialog) {
			mDialog = new Dialog(getActivity(), R.style.ShouchangDialog);
		}
		mDialog.show();
		return mDialog;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return View.inflate(getActivity(), R.layout.dialog_true_false, null);
	}

	@Override
	public void onActivityCreated(Bundle arg0) {
		super.onActivityCreated(arg0);
		tvCancel = (TextView) getView().findViewById(R.id.tv_cancel);
		tvEnsure = (TextView) getView().findViewById(R.id.tv_ensure);
		tvTips = (TextView) getView().findViewById(R.id.tv_tips);
		if (null != tips) {
			tvTips.setText(tips);
		}
		tvCancel.setOnClickListener(this);
		tvEnsure.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		if (null != onTrueOrFalseClickListener) {
			onTrueOrFalseClickListener.onTrueOrFalseClick(view);
		}
		dismiss();
	}

	private OnTrueOrFalseClickListener onTrueOrFalseClickListener;

	public void setOnTrueOrFalseClickListener(OnTrueOrFalseClickListener onTrueOrFalseClickListener) {
		this.onTrueOrFalseClickListener = onTrueOrFalseClickListener;
	};

	public interface OnTrueOrFalseClickListener {
		public void onTrueOrFalseClick(View view);
	}

	public void setTips(String tips) {
		this.tips = tips;
	}
}
