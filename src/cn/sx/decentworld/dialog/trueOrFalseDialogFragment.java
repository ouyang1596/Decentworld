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
 * 确定与取消对话框
 */
public class TrueOrFalseDialogFragment extends DialogFragment implements OnClickListener {
	private Dialog mDialog;
	private TextView tvCancel, tvEnsure, tvTips;
	private String tips;
	private Object mObject;
	public boolean hideTvCancel;// true取消false不取消

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
		if (hideTvCancel) {
			tvCancel.setVisibility(View.GONE);
		}
	}

	@Override
	public void onClick(View view) {
		if (null != onTrueOrFalseClickListener) {
			onTrueOrFalseClickListener.onTrueOrFalseClick(this, view);
		}
		dismiss();
	}

	private OnTrueOrFalseClickListener onTrueOrFalseClickListener;

	public void setOnTrueOrFalseClickListener(OnTrueOrFalseClickListener onTrueOrFalseClickListener) {
		this.onTrueOrFalseClickListener = onTrueOrFalseClickListener;
	};

	public interface OnTrueOrFalseClickListener {
		public void onTrueOrFalseClick(TrueOrFalseDialogFragment dialog, View view);
	}

	public void setTips(String tips) {
		this.tips = tips;
	}

	public void setObj(Object object) {
		mObject = object;
	}

	public Object getObj() {
		return mObject;
	}
}
