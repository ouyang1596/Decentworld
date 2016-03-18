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
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.sx.decentworld.R;
import cn.sx.decentworld.utils.ViewUtil;

/**
 * @ClassName: BuildGroupNameDialog.java
 * @Description:
 * @author: dyq
 * @date: 2015年7月31日 上午11:17:48
 */
public class BuildGroupNameDialog extends DialogFragment implements
		OnClickListener {
	EditText ed_text;
	Button button;
	NameListener listener;
	TextView group_name_not_null_tip;
	RelativeLayout root_activity_build_group_name_dialog;
	public static BuildGroupNameDialog newInstance() {
		BuildGroupNameDialog aa = new BuildGroupNameDialog();
		return aa;
	}

	public void setListener(NameListener listener) {
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
		View view = inflater.inflate(R.layout.activity_build_group_name_dialog,
				null);
		root_activity_build_group_name_dialog=(RelativeLayout) view.findViewById(R.id.root_activity_build_group_name_dialog);
		ViewUtil.scaleContentView(root_activity_build_group_name_dialog);
		ed_text = (EditText) view.findViewById(R.id.ed_group_name_set);
		button = (Button) view.findViewById(R.id.bt_forsure_groupname);
		button.setOnClickListener(this);
		group_name_not_null_tip=(TextView) view.findViewById(R.id.group_name_not_null_tip);
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

		Dialog dialog = new Dialog(getActivity(), R.style.ShouchangDialog);
		return dialog;
	}

	public interface NameListener {
		void putName(String name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
			listener.putName(ed_text.getText().toString());
	}
	public void showtips(){
		group_name_not_null_tip.setVisibility(View.VISIBLE);
	}
}
