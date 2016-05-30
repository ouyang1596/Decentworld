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
 * @ClassName: EnsureCancelDialog.java
 * @Description:
 * @author: yj
 * @date: 2016年5月23日 上午10:50:59
 */
public class EnsureCancelDialog extends DialogFragment implements OnClickListener {
	private TextView tvPrompt;
	private TextView tvCancel, tvEnsure;
	private String prompt = "尚未获取到地理位置，您是否需要先获取到地理位置？";
	private Dialog mDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return View.inflate(getActivity(), R.layout.dialog_ensure_cancel, null);
	}

	@Override
	public void onActivityCreated(Bundle arg0) {
		super.onActivityCreated(arg0);
		initView();
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		if (null == mDialog) {
			mDialog = new Dialog(getActivity(), R.style.ShouchangDialog);
		}
		mDialog.show();
		return mDialog;
	}

	private void initView() {
		tvPrompt = (TextView) getView().findViewById(R.id.tv_prompt);
		tvPrompt.setText(prompt);
		tvCancel = (TextView) getView().findViewById(R.id.tv_cancel);
		tvCancel.setOnClickListener(this);
		tvEnsure = (TextView) getView().findViewById(R.id.tv_ensure);
		tvEnsure.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// switch (v.getId()) {
		// case R.id.tv_cancel:
		//
		// break;
		//
		// case R.id.tv_ensure:
		// break;
		// }
		if (null != mOnEnsureCancelClickListener) {
			mOnEnsureCancelClickListener.OnEnsureCancelClick(v);
		}
	}

	private OnEnsureCancelClickListener mOnEnsureCancelClickListener;

	public interface OnEnsureCancelClickListener {
		public void OnEnsureCancelClick(View view);
	}

	public void setOnEnsureCancelClickListener(OnEnsureCancelClickListener onEnsureCancelClickListener) {
		mOnEnsureCancelClickListener = onEnsureCancelClickListener;
	}
}
