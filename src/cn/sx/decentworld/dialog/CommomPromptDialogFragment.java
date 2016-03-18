/**
 * 
 */
package cn.sx.decentworld.dialog;

import java.util.HashMap;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import cn.sx.decentworld.R;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.utils.FileUtils;

/**
 * 照相机与拍照选择对话框
 */
public class CommomPromptDialogFragment extends DialogFragment implements OnClickListener {
	private Dialog mDialog;
	private TextView tvEnsure, tvPrompt;
	private String prompt;
	private CheckBox cbPrompt;
	private Object object;
	private int enterStatus;
	public static final int LIKE = 0;
	public static final int FRIEND = 1;
	public static final int STRANGER = 2;
	public static final int SHENGWAN = 3;
	public static final int GO_DOUBT = 4;
	private HashMap<Integer, String> map;

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
		initMap();
		setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
	}

	private void initMap() {
		map = new HashMap<Integer, String>();
		map.put(LIKE, Constants.LIKE);
		map.put(STRANGER, Constants.STRANGER);
		map.put(FRIEND, Constants.FRIEND);
		map.put(SHENGWAN, Constants.SHENGWAN);
		map.put(GO_DOUBT, Constants.GO_DOUBT);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return View.inflate(getActivity(), R.layout.dialog_commom_prompt, null);
	}

	@Override
	public void onActivityCreated(Bundle arg0) {
		super.onActivityCreated(arg0);
		cbPrompt = (CheckBox) getView().findViewById(R.id.cb_prompt);
		tvEnsure = (TextView) getView().findViewById(R.id.tv_ensure);
		tvPrompt = (TextView) getView().findViewById(R.id.tv_prompt);
		if (null != prompt) {
			tvPrompt.setText(prompt);
		}
		tvEnsure.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		if (cbPrompt.isChecked()) {
			FileUtils.savePromptStatus(getActivity(), map.get(enterStatus), enterStatus);
		}
		if (null != onCommompromptListener) {
			onCommompromptListener.onCommomPromtClick(view);
		}
		dismiss();
	}

	private OnCommomPromptListener onCommompromptListener;

	public void setOnCommomPromptListener(OnCommomPromptListener onTrueOrFalseClickListener) {
		this.onCommompromptListener = onTrueOrFalseClickListener;
	};

	public interface OnCommomPromptListener {
		public void onCommomPromtClick(View view);
	}

	public void setTips(String tips) {
		this.prompt = tips;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public int getEnter() {
		return enterStatus;
	}

	public void setEnter(int enter) {
		this.enterStatus = enter;
	}
}
